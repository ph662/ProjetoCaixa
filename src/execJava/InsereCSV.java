package execJava;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

/**
 * Classe construida para inserir arquivos de sensibilizacao no banco de dados
 * local.
 * 
 * 
 * */
public class InsereCSV {

	public static void main(String[] args) {
		String nomeExtensao;
		String nome, data;

		nome = args[0].substring(0, args[0].length() - 10);
		nomeExtensao = args[0].substring(args[0].length() - 10);
		data = (nomeExtensao.replace(".csv", ""));

		System.out.println(nome);
		System.out.println(data);

		try {
			insereBanco(nome, data);
			Date date = Calendar.getInstance().getTime();
			System.out.println("Hora do término - " + date);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void insereBanco(String nome, String data)
			throws SQLException {
		switch (nome) {
		case "movimentacaoEmissao":

			File file = new File(
					"D:\\Relatorio Sensibilizacao/movimentacaoEmissao" + data
							+ ".csv");

			/* ******************************************************************* */

			FileInputStream fis = null;
			DataInputStream dis = null;
			BufferedInputStream bis = null;
			try {
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				dis = new DataInputStream(bis);

				Connection con = new Conexao()
						.getConexaoMySql("InsereCSV.java movimentacaoEmissao");
				Connection con2 = new Conexao()
						.getConexaoMySql("InsereCSV.java movimentacaoEmissao");
				System.out.println("Inserindo linhas...");

				con.setAutoCommit(false);
				con2.setAutoCommit(false);

				int repetidoApolice = 0;
				int repetidoProposta = 0;

				while (dis.available() != 0) {

					@SuppressWarnings("deprecation")
					String[] part = dis.readLine().split(";");

					String sql = "insert into movimentacao_emissao (DataArquivo,NumeroProposta,AgenciaDeVenda,ValorPagoSICOB,SituacaoProposta,TipoMovimento,NumeroProdutoSIGPF) values (STR_TO_DATE(?,'%d-%m-%Y'),?,?,?,?,?,?);";
					String sqlAgenciaVigentes = "";

					int tipo = 0;/* 0 apolice - 1 proposta */

					if (part[0].substring(0, 3).equalsIgnoreCase("120")) {
						sqlAgenciaVigentes = "insert into agencia_vigentes_apolices (DataArquivo,NumeroApolice,AgenciaDeVenda,CodigoProduto) values (STR_TO_DATE(?,'%d-%m-%Y'),?,?,?);";
						tipo = 0;
					} else {
						sqlAgenciaVigentes = "insert into agencia_vigentes_propostas (DataArquivo,NumeroProposta,AgenciaDeVenda,CodigoProduto) values (STR_TO_DATE(?,'%d-%m-%Y'),?,?,?);";
						tipo = 1;
					}

					// /********************************************************************/

					try {
						/*
						 * insert que sera feito na tabela de
						 * 'movimentacao_emissao'
						 */
						PreparedStatement ps = con.prepareStatement(sql);

						/* insert que sera feito na tabela `agencia_vigentes` */
						PreparedStatement psAgenciaVigentes = con2
								.prepareStatement(sqlAgenciaVigentes);

						String anoCut = data.substring(0, 2);
						String mesCut = data.substring(2, 4);
						String diaCut = data.substring(4, 6);
						String dataNova = diaCut + "-" + mesCut + "-" + "20"
								+ anoCut;

						for (int i = 1; i <= 7; i++) {
							if (i == 1) {
								ps.setString(i, dataNova);
								psAgenciaVigentes.setString(i, dataNova);
							} else if (i == 2) {
								if (part[i - 2].substring(0, 3)
										.equalsIgnoreCase("120")) {
									ps.setString(i, part[0].substring(0, 13));
									psAgenciaVigentes.setString(i,
											part[0].substring(0, 13));
								} else {
									ps.setString(i, part[i - 2].trim());
									psAgenciaVigentes.setString(i,
											part[i - 2].trim());
								}
							} else if (i == 3) {
								try {
									ps.setInt(i, Integer.parseInt(part[i - 2]
											.trim()));
									psAgenciaVigentes.setInt(i, Integer
											.parseInt(part[i - 2].trim()));
								} catch (NumberFormatException efe) {
									ps.setInt(i, 00000);
									psAgenciaVigentes.setInt(i, 00000);
								}
							} else if (i == 4) {
								try {
									ps.setDouble(i, Double
											.parseDouble(part[i - 2].replace(
													",", ".")));
								} catch (NumberFormatException efe) {
									ps.setInt(i, 00000);
								}
							} else if (i == 7) {
								try {
									ps.setString(
											i,
											part[i - 2]
													.trim()
													.replace("0071", "1403")
													.replace("0072", "1404")
													.replace("0050", "1804")
													.replace("0010", "1405")
													.replace(
															"Numero do produto(SIGPF)",
															"Prod"));
									psAgenciaVigentes
											.setString(
													4,
													part[i - 2]
															.trim()
															.replace("0071",
																	"1403")
															.replace("0072",
																	"1404")
															.replace("0050",
																	"1804")
															.replace("0010",
																	"1405")
															.replace(
																	"Numero do produto(SIGPF)",
																	"Prod"));
								} catch (Exception ex) {
									ps.setString(i, "aaaaaaaa");
									psAgenciaVigentes.setString(4, "aaaaaaaa");
								}
							} else {
								ps.setString(i, part[i - 2].trim());
							}
						}
						ps.execute();
						ps.close();
						ps = null;

						try {
							psAgenciaVigentes.execute();
						} catch (MySQLIntegrityConstraintViolationException m) {
							if (tipo == 0) {/* 0 apolice - 1 proposta */
								repetidoApolice++;
							} else {
								repetidoProposta++;
							}

						} finally {
							psAgenciaVigentes.close();
							psAgenciaVigentes = null;
						}

					} catch (SQLException e) {
						e.printStackTrace();
					}
					part = null;
					sql = null;
					sqlAgenciaVigentes = null;
				}// WHILE

				try {
					String sqlDelete = "delete FROM sistemadirid.movimentacao_emissao where NumeroProposta like 'Numero da Proposta';";
					PreparedStatement ps = con.prepareStatement(sqlDelete);
					ps.execute();
					ps.close();
					ps = null;
				} catch (Exception e) {
					// TODO: handle exception
				}

				con.commit();
				con2.commit();
				con.close();
				con2.close();
				System.out
						.println("Casos não inseridos na tabela agencia_Vigentes \n Apolices >>"
								+ repetidoApolice
								+ "<< \n Propostas >>"
								+ repetidoProposta
								+ "<< \n Duplicate entry for key 'PRIMARY'.");
				System.out.println("INSERT DO ARQUIVO FINALIZADO");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					fis.close();
					dis.close();
					bis.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			break;
		case "movimentacaoFinanceira":
			File fileF = new File(
					"D:\\Relatorio Sensibilizacao/movimentacaoFinanceira"
							+ data + ".csv");

			/* ******************************************************************* */

			FileInputStream fisF = null;
			BufferedInputStream bisF = null;
			DataInputStream disF = null;

			try {
				fisF = new FileInputStream(fileF);
				bisF = new BufferedInputStream(fisF);
				disF = new DataInputStream(bisF);

				Connection con = new Conexao()
						.getConexaoMySql("InsereCSV.java movimentacaoFinanceira");
				System.out.println("Inserindo linhas...");

				con.setAutoCommit(false);

				while (disF.available() != 0) {
					@SuppressWarnings("deprecation")
					String[] part = disF.readLine().split(";");

					String sql = "insert into movimentacao_financeira(DataArquivo,NumeroProposta,CodigoSituacao,ValorParcela) values (STR_TO_DATE(?,'%d-%m-%Y'),?,?,?);";

					// /********************************************************************/

					try {
						PreparedStatement ps = con.prepareStatement(sql);

						String anoCut = data.substring(0, 2);
						String mesCut = data.substring(2, 4);
						String diaCut = data.substring(4, 6);
						String dataNova = diaCut + "-" + mesCut + "-" + "20"
								+ anoCut;

						for (int i = 1; i <= 4; i++) {
							if (i == 1) {
								ps.setString(i, dataNova);
							} else if (i == 2) {
								if (part[i - 2].substring(0, 3)
										.equalsIgnoreCase("120")) {
									ps.setString(i, part[0].substring(0, 13));
								} else {
									ps.setString(i, part[i - 2].trim());
								}
							} else if (i == 3) {
								ps.setString(i, part[i - 2].trim());
							} else if (i == 4) {
								try {
									ps.setDouble(i, Double
											.parseDouble(part[i - 2].replace(
													",", ".")));
								} catch (NumberFormatException efe) {
									ps.setInt(i, 0);
								}
							}
						}
						ps.execute();
						ps.close();
						ps = null;

					} catch (SQLException e) {
						e.printStackTrace();
					}
					part = null;
					sql = null;
				}// while

				try {
					String sqlDelete = "delete FROM sistemadirid.movimentacao_financeira where NumeroProposta like 'Numero da Proposta';";
					PreparedStatement ps = con.prepareStatement(sqlDelete);
					ps.execute();
					ps.close();
					ps = null;
				} catch (Exception e) {
					e.printStackTrace();
				}

				con.commit();
				con.close();
				System.out.println("INSERT DO ARQUIVO FINALIZADO");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					fisF.close();
					bisF.close();
					disF.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			break;
		case "status_TipoDeMovimento":
			File fileS = new File(
					"D:\\Relatorio Sensibilizacao/status_TipoDeMovimento"
							+ data + ".csv");

			/* ******************************************************************* */

			FileInputStream fisS = null;
			BufferedInputStream bisS = null;
			DataInputStream disS = null;

			try {
				fisS = new FileInputStream(fileS);
				bisS = new BufferedInputStream(fisS);
				disS = new DataInputStream(bisS);

				Connection con = new Conexao()
						.getConexaoMySql("InsereCSV.java status_TipoDeMovimento");
				System.out.println("Inserindo linhas...");

				con.setAutoCommit(false);

				while (disS.available() != 0) {
					@SuppressWarnings("deprecation")
					String[] part = disS.readLine().split(";");

					String sql = "insert into movimentacao_status_TipoDeMovimento(DataArquivo,NumeroProposta,SituacaoProposta,TipoMovimento) values (STR_TO_DATE(?,'%d-%m-%Y'),?,?,?);";
					// /********************************************************************/

					try {
						PreparedStatement ps = con.prepareStatement(sql);

						String anoCut = data.substring(0, 2);
						String mesCut = data.substring(2, 4);
						String diaCut = data.substring(4, 6);
						String dataNova = diaCut + "-" + mesCut + "-" + "20"
								+ anoCut;

						for (int i = 1; i <= 4; i++) {

							if (i == 1) {

								ps.setString(i, dataNova);

							} else if (i == 2) {
								if (part[i - 2].substring(0, 3)
										.equalsIgnoreCase("120")) {
									ps.setString(i,
											part[i - 2].substring(0, 13));
								} else {
									ps.setString(i, part[i - 2].trim());
								}
							} else {
								ps.setString(i, part[i - 2].trim());
							}

						}

						ps.execute();
						ps.close();
						ps = null;

					} catch (SQLException e) {
						e.printStackTrace();
					}
					part = null;
					sql = null;
				}// while

				try {
					String sqlDelete = "delete FROM sistemadirid.movimentacao_status_TipoDeMovimento where NumeroProposta like 'Numero da Proposta';";
					PreparedStatement ps = con.prepareStatement(sqlDelete);
					ps.execute();
					ps.close();
					ps = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}

				con.commit();
				con.close();
				System.out.println("INSERT DO ARQUIVO FINALIZADO");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					fisS.close();
					bisS.close();
					disS.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			break;
		case "movimentacaoVenda":
			File fileV = new File(
					"D:\\Relatorio Sensibilizacao/movimentacaoVenda" + data
							+ ".csv");

			/* ******************************************************************* */

			FileInputStream fisV = null;
			BufferedInputStream bisV = null;
			DataInputStream disV = null;

			try {
				fisV = new FileInputStream(fileV);
				bisV = new BufferedInputStream(fisV);
				disV = new DataInputStream(bisV);

				Connection con = new Conexao()
						.getConexaoMySql("InsereCSV.java movimentacaoVenda");
				System.out.println("Inserindo linhas...");

				int repetido = 0;

				con.setAutoCommit(false);

				while (disV.available() != 0) {
					@SuppressWarnings("deprecation")
					String[] part = disV.readLine().split(";");

					String sql = "insert into agencia_vigentes_propostas(DataArquivo,NumeroProposta, CodigoProduto, AgenciaDeVenda) values (STR_TO_DATE(?,'%d-%m-%Y'),?,?,?)";

					// /********************************************************************/

					try {
						PreparedStatement ps = con.prepareStatement(sql);

						String anoCut = data.substring(0, 2);
						String mesCut = data.substring(2, 4);
						String diaCut = data.substring(4, 6);
						String dataNova = diaCut + "-" + mesCut + "-" + "20"
								+ anoCut;

						for (int i = 1; i <= 4; i++) {
							if (i == 1) {
								ps.setString(i, dataNova);
							} else if (i == 3) {
								try {
									ps.setString(
											i,
											part[1].trim()
													.replace("0071", "1403")
													.replace("0072", "1404")
													.replace("0050", "1804")
													.replace("0010", "1405")
													.replace(
															"Numero do produto(SIGPF)",
															"Prod"));
								} catch (Exception ex) {
									ps.setString(i, "aaaaaaaa");
								}

							} else if (i == 4) {
								try {
									ps.setInt(i,
											Integer.parseInt(part[2].trim()));
								} catch (NumberFormatException efe) {
									ps.setInt(i, 1000000);
								}
							} else {
								ps.setString(i, part[i - 2].trim());
							}
						}

						try {
							ps.execute();
						} catch (MySQLIntegrityConstraintViolationException m) {
							repetido++;
						} finally {
							ps.close();
							ps = null;
						}

					} catch (SQLException e) {
						e.printStackTrace();
					}
					part = null;
					sql = null;
				}// while

				try {
					String sqlDelete = "delete FROM sistemadirid.agencia_vigentes_propostas where NumeroProposta like 'Numero da Proposta';";
					PreparedStatement ps = con.prepareStatement(sqlDelete);
					ps.execute();
					ps.close();
					ps = null;
				} catch (Exception e) {
					e.printStackTrace();
				}

				con.commit();
				con.close();
				System.out
						.println(repetido
								+ " casos não inseridos na tabela agencia_Vigentes_propostas. Duplicate entry for key 'PRIMARY'.");
				System.out.println("INSERT DO ARQUIVO FINALIZADO");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					fisV.close();
					bisV.close();
					disV.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			break;
		}
	}
}
