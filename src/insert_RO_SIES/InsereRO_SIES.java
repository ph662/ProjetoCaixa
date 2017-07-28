package insert_RO_SIES;

import insert_RO_SIAS_SIES_diaria.Conexao;

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

public class InsereRO_SIES {

	public static void main(String[] args) {
		String anoCompletoMes;

		try {
			// anoCompletoMes = args[0];
			anoCompletoMes = "20170428";

			// insereRoSIAS(anoCompletoMes);

			// insereRoSiesEmissao(anoCompletoMes);// SIES

			// insereRoSiesCancelamento(anoCompletoMes);// SIES
			//
			// insereRoSiesRestituido(anoCompletoMes);// SIES
			//
			// insereROSinistroSIAS(anoCompletoMes);
			//
			// insereROSinistroSIES(anoCompletoMes);

			// insereM2087(anoCompletoMes);

			// insere_rComp();

			// insere_S_Comp();

			// insereRg0012b_comParcela(anoCompletoMes);
			// insereRg0022b_comParcela(anoCompletoMes);

			insereVigentesAUTO_RD(anoCompletoMes);

			Date date = Calendar.getInstance().getTime();
			System.out.println("Hora do término - " + date);
			System.out.println();
			System.out.println(">>> Linhas inseridas!!! <<<");
			System.out.println();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.out.println("ERRO NO SQL");
		} catch (Exception e) {
			e.printStackTrace();
			System.out
					.println("Insira o parametro 'anoMes' no seguinte formato 'AAAAmmDD'");
		}

	}

	private static void insereRg0012b_comParcela(String anoCompletoMes)
			throws SQLException {

		String YYYYmm = anoCompletoMes.substring(0, 6);
		String YYmm = anoCompletoMes.substring(2, 6);

		File file = new File("\\\\ntservercpd\\Registros_OFICIAIS\\M" + YYmm
				+ ".RG0012B.TXT");

		/* ******************************************************************* */

		FileInputStream fis = null;
		DataInputStream dis = null;
		BufferedInputStream bis = null;
		try {

			if (file.exists()) {
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				dis = new DataInputStream(bis);

				Connection con = new Conexao()
						.getConexaoMySql("\\\\ntservercpd\\Registros_OFICIAIS\\M"
								+ YYmm + ".RG0012B.TXT");

				System.out.println("Inserindo linhas...");

				con.setAutoCommit(false);

				while (dis.available() != 0) {
					@SuppressWarnings("deprecation")
					String[] part = dis.readLine().split(";");
					if (part[0].matches("\\d+")) {
						// 40 colunas
						@SuppressWarnings("deprecation")
						String sql = "INSERT INTO sistemadirid.rg0012b_ComParcela VALUES (?,?,?,?,?,?,?,?,?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

						// /********************************************************************/
						int i = 0;

						try {
							/*
							 * insert que sera feito na tabela de 'RO_SIES'
							 */
							PreparedStatement ps = con.prepareStatement(sql);

							ps.setInt(1, Integer.parseInt(YYYYmm));
							for (i = 1; i <= 40; i++) {
								if (i >= 17 && i < 40) {
									ps.setDouble(
											i + 1,
											Double.parseDouble(part[i - 1]
													.trim().replace(".", "")
													.replace(",", ".")));
								} else {
									ps.setString(i + 1, part[i - 1].trim());
								}
							}
							ps.executeUpdate();
							ps.close();
							ps = null;

						} catch (SQLException e) {
							e.printStackTrace();
							System.out.println("coluna " + i + " " + part[i]);

						}
						part = null;
						sql = null;

					}// if

				}// WHILE

				con.commit();
				con.close();

				try {
					fis.close();
					dis.close();
					bis.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			} else {
				System.out.println("Arquivo m-rg0012b SIAS não encotrado.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void insereRg0022b_comParcela(String anoCompletoMes)
			throws SQLException {

		String YYYYmm = anoCompletoMes.substring(0, 6);
		String YYmm = anoCompletoMes.substring(2, 6);

		File file = new File("\\\\ntservercpd\\Registros_OFICIAIS\\M" + YYmm
				+ ".RG0022B.TXT");

		/* ******************************************************************* */

		FileInputStream fis = null;
		DataInputStream dis = null;
		BufferedInputStream bis = null;
		try {

			if (file.exists()) {
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				dis = new DataInputStream(bis);

				Connection con = new Conexao()
						.getConexaoMySql("\\\\ntservercpd\\Registros_OFICIAIS\\M"
								+ YYmm + ".RG0022B.TXT");

				System.out.println("Inserindo linhas...");

				con.setAutoCommit(false);

				while (dis.available() != 0) {
					@SuppressWarnings("deprecation")
					String[] part = dis.readLine().split(";");
					if (part[0].matches("\\d+")) {
						// 40 colunas
						@SuppressWarnings("deprecation")
						String sql = "INSERT INTO sistemadirid.rg0022b_ComParcela VALUES (?,?,?,?,?,?,?,?,?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

						// /********************************************************************/
						int i = 0;

						try {
							/*
							 * insert que sera feito na tabela de 'RO_SIES'
							 */
							PreparedStatement ps = con.prepareStatement(sql);

							ps.setInt(1, Integer.parseInt(YYYYmm));
							for (i = 1; i <= 39; i++) {
								if (i >= 17 && i < 40) {
									ps.setDouble(
											i + 1,
											Double.parseDouble(part[i - 1]
													.trim().replace(".", "")
													.replace(",", ".")));
								} else {
									ps.setString(i + 1, part[i - 1].trim());
								}
							}
							ps.executeUpdate();
							ps.close();
							ps = null;

						} catch (SQLException e) {
							e.printStackTrace();
							System.out.println("coluna " + i + " " + part[i]);

						}
						part = null;
						sql = null;

					}// if

				}// WHILE

				con.commit();
				con.close();

				try {
					fis.close();
					dis.close();
					bis.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			} else {
				System.out.println("Arquivo m-rg0022b SIAS não encotrado.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	} // insereRg0022b_comParcela

	public static void insereRoSIAS(String anoCompletoMes) throws SQLException {

		String anoMes = anoCompletoMes.substring(0, 6);

		File file = new File("D:\\base_Sias/extracao" + anoMes + "_RO_RG12.rpt");

		/* ******************************************************************* */

		FileInputStream fis = null;
		DataInputStream dis = null;
		BufferedInputStream bis = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);

			Connection con = new Conexao()
					.getConexaoMySql("insereRoSIAS.java - D:\\base_Sias/extracao"
							+ anoMes + "_RO_RG12.rpt");

			System.out.println("Inserindo linhas...");

			con.setAutoCommit(false);

			while (dis.available() != 0) {
				@SuppressWarnings("deprecation")
				String[] part = dis.readLine().split(";");

				if (part[0].contains("20")) {

					@SuppressWarnings("deprecation")
					String sql = "INSERT INTO sistemadirid.RO_RG0012B VALUES (?,?,?,?,?,?,?,?,?,?,?, STR_TO_DATE(?,'%Y-%m-%d'), STR_TO_DATE(?,'%Y-%m-%d'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

					// /********************************************************************/
					int i = 0;

					try {
						/*
						 * insert que sera feito na tabela de 'RO_SIES'
						 */
						PreparedStatement ps = con.prepareStatement(sql);

						for (i = 1; i <= 39; i++) {
							if (i == 1) {
								ps.setString(i, part[i - 1].replace("ï»¿", ""));
							} else if (i == 12) {
								ps.setString(
										i,
										part[i - 1].trim().replace(
												" 00:00:00.000", ""));
							} else if (i == 13) {
								ps.setString(
										i,
										part[i - 1].trim().replace(
												" 00:00:00.000", ""));
							} else {
								ps.setString(i, part[i - 1].trim());
							}
						}
						ps.executeUpdate();
						ps.close();
						ps = null;

					} catch (SQLException e) {
						e.printStackTrace();
						System.out.println("coluna " + i + " " + part[i]);

					}
					part = null;
					sql = null;

				}// if

			}// WHILE

			con.commit();
			con.close();
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
	}

	public static void insereRoSiesEmissao(String anoCompletoMes)
			throws SQLException {

		String anoMes = anoCompletoMes.substring(0, 6);

		File file = new File("D:\\base_Sies/" + anoMes + "_RO_SIES_EMTLDR.rpt");

		/* ******************************************************************* */

		FileInputStream fis = null;
		DataInputStream dis = null;
		BufferedInputStream bis = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);

			Connection con = new Conexao()
					.getConexaoMySql("InsereRO_SIES.java - D:\\base_Sies/"
							+ anoCompletoMes + "_RO_SIES_EMTLDR.rpt");
			Connection con2 = new Conexao()
					.getConexaoMySql("InsereRO_SIES.java movimentacaoEmissao");

			System.out.println("Inserindo linhas...");

			con.setAutoCommit(false);
			con2.setAutoCommit(false);

			while (dis.available() != 0) {
				String[] part = dis.readLine().split(";");

				if (part[0].contains("20")) {

					@SuppressWarnings("deprecation")
					String sql = "INSERT INTO RO_SIES (ANOMES_REF, COD_RAMO, RAMO, PRODUTO, MOVIMENTO, APOLICE, ENDOSSO, PARCELA,"
							+ " DT_EMISSAO, DT_INICIO, DT_TERMINO, CPFCNPJ, SEGURADO, LIDER_IMP_SEGURADA, COSS_IMP_SEGURADA, "
							+ "RESS_IMP_SEGURADA, LIDER_PREMIO_TARIF, COSS_PREMIO_TARIF, RESS_PREMIO_TARIF, LIDER_DESCONTO, "
							+ "COSS_DESCONTO, LIDER_ADICIONAL, COSS_ADICIONAL, LIDER_CUSTO, LIDER_IOF, LIDER_COMISSAO, COSS_COMISSAO,"
							+ " RESS_COMISSAO, LIDER_PREMIO_TOTAL, COSS_PREMIO_TOTAL, RESS_PREMIO_TOTAL) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

					String sqlUfSies = "insert into RO_UF_SIES (ANOMES_REF, UF, APOLICE, ENDOSSO) values (?,?,?,?);";

					// /********************************************************************/
					int i = 0;
					try {
						/*
						 * insert que sera feito na tabela de 'RO_SIES'
						 */
						PreparedStatement ps = con.prepareStatement(sql);

						/* insert que sera feito na tabela `RO_UF_SIES` */
						PreparedStatement psUF = con2
								.prepareStatement(sqlUfSies);

						for (i = 1; i <= 31; i++) {
							if (i == 1) {
								ps.setInt(i, Integer.parseInt(part[i - 1]
										.replace("ï»¿", "")));
								psUF.setInt(i, Integer.parseInt(part[i - 1]
										.replace("ï»¿", "")));
							} else if (i == 2) {
								ps.setInt(i, Integer.parseInt(part[1]));
								psUF.setString(i, part[5].trim());
							} else if (i == 3) {
								ps.setString(i, part[2]);
								psUF.setLong(i, Long.parseLong(part[4]));
							} else if (i == 4) {// PRODUTO
								ps.setString(i, part[3]);
								psUF.setString(i, part[6]);
							} else if (i == 5) {
								ps.setString(i, "APOLICE DE SEGURO");
							} else if (i == 6) {// APOLICE
								ps.setLong(i, Long.parseLong(part[4]));
							} else if (i == 7) {// ENDOSSO
								ps.setInt(i, Integer.parseInt(part[6]));
							} else if (i == 8) {// PARCELA
								ps.setInt(i, Integer.parseInt(part[7]));
							} else if (i == 9) {// DT_EMISSAO
								ps.setString(i,
										part[8].replace(" 00:00:00.000", ""));
							} else if (i == 10) {// DT_INICIO
								ps.setString(i,
										part[9].replace(" 00:00:00.000", ""));
							} else if (i == 11) {// DT_TERMINO
								ps.setString(i,
										part[10].replace(" 00:00:00.000", ""));
							} else if (i == 12) {// CPFCNPJ
								ps.setString(i, part[11].trim());
							} else if (i == 13) {// SEGURADO
								ps.setString(i, part[12].trim());
							} else {
								ps.setDouble(i, Double.parseDouble(part[i - 1]
										.trim().replace(",", ".")));
							}
						}
						ps.executeUpdate();
						ps.close();
						ps = null;

						psUF.executeUpdate();
						psUF.close();
						psUF = null;

					} catch (SQLException e) {
						e.printStackTrace();
						System.out.println("coluna " + i + " " + part[i]);

					}
					part = null;
					sql = null;

				}// if

			}// WHILE

			con.commit();
			con2.commit();
			con.close();
			con2.close();
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
	}

	public static void insereRoSiesCancelamento(String anoCompletoMes)
			throws SQLException {

		String yyMMdd = anoCompletoMes.replace("20", "");
		String yyyyMM = anoCompletoMes.substring(0, 6);
		File file = new File("D:\\base_Sies/ROCANLDR.D" + yyMMdd + ".csv");

		/* ******************************************************************* */

		FileInputStream fis = null;
		DataInputStream dis = null;
		BufferedInputStream bis = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);

			Connection con = new Conexao()
					.getConexaoMySql("InsereRO_SIES.java - D:\\base_Sies/ROCANLDR.D"
							+ yyMMdd + ".csv");

			System.out.println("Inserindo linhas...");

			con.setAutoCommit(false);

			while (dis.available() != 0) {

				String[] part = dis.readLine().split(";");

				if (!(part[0].contains("LIVRO"))) {
					@SuppressWarnings("deprecation")
					String sql = "INSERT INTO RO_SIES (ANOMES_REF, COD_RAMO, RAMO, PRODUTO, MOVIMENTO, APOLICE, ENDOSSO, PARCELA,"
							+ " DT_EMISSAO, DT_INICIO, DT_TERMINO, CPFCNPJ, SEGURADO, LIDER_IMP_SEGURADA, COSS_IMP_SEGURADA, "
							+ "RESS_IMP_SEGURADA, LIDER_PREMIO_TARIF, COSS_PREMIO_TARIF, RESS_PREMIO_TARIF, LIDER_DESCONTO, "
							+ "COSS_DESCONTO, LIDER_ADICIONAL, COSS_ADICIONAL, LIDER_CUSTO, LIDER_IOF, LIDER_COMISSAO, COSS_COMISSAO,"
							+ " RESS_COMISSAO, LIDER_PREMIO_TOTAL, COSS_PREMIO_TOTAL, RESS_PREMIO_TOTAL) "
							+ "VALUES (?,?,?,?,?,?,?,?, STR_TO_DATE(?,'%d/%m/%Y') , STR_TO_DATE(?,'%d/%m/%Y') , STR_TO_DATE(?,'%d/%m/%Y') ,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

					// /********************************************************************/
					int i = 0;
					try {
						/*
						 * insert que sera feito na tabela de 'RO_SIES'
						 */
						PreparedStatement ps = con.prepareStatement(sql);

						for (i = 1; i <= 31; i++) {

							switch (i) {
							case 1:// ANOMES_REF
								ps.setInt(i, Integer.parseInt(yyyyMM));
								break;
							case 2: // COD_RAMO
								ps.setInt(i, Integer.parseInt(part[3]));
								break;
							case 3: // RAMO
								ps.setString(i, part[4]);
								break;
							case 4: // PRODUTO
								ps.setString(i, part[6]);
								break;
							case 5: // MOVIMENTO
								ps.setString(i, "ENDOSSOS DE CANCELAMENTO");
								break;
							case 6: // APOLICE
								ps.setLong(i, Long.parseLong(part[7]));
								break;
							case 7: // ENDOSSO
								ps.setInt(i, Integer.parseInt(part[8]));
								break;
							case 8: // PARCELA
								ps.setInt(i, Integer.parseInt(part[10]));
								break;
							case 9: // DT_EMISSAO
								ps.setString(i,
										part[12].replace(" 00:00:00", "")
												.replace(" 00:00", ""));
								break;
							case 10: // DT_INICIO
								ps.setString(i,
										part[13].replace(" 00:00:00", "")
												.replace(" 00:00", ""));
								break;
							case 11: // DT_TERMINO
								ps.setString(i,
										part[14].replace(" 00:00:00", "")
												.replace(" 00:00", ""));
								break;
							case 12: // CPFCNPJ
								ps.setString(i, part[15].trim());
								break;
							case 13: // SEGURADO
								ps.setString(i, part[16].trim());
								break;
							default:
								ps.setDouble(
										i,
										Double.parseDouble(part[i + 5].trim()
												.replace(".", "")
												.replace(",", ".")));
								break;
							}

						}
						ps.executeUpdate();
						ps.close();
						ps = null;

					} catch (SQLException e) {
						e.printStackTrace();
						System.out.println("coluna " + i + " " + part[i]);
					}
					part = null;
					sql = null;
				}// if
			}// WHILE

			con.commit();
			con.close();
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
	}

	public static void insereRoSiesRestituido(String anoCompletoMes)
			throws SQLException {
		String yyMMdd = anoCompletoMes.replace("20", "");
		String yyyyMM = anoCompletoMes.substring(0, 6);
		File file = new File("D:\\base_Sies/RORSTLDR.D" + yyMMdd + ".csv");

		/* ******************************************************************* */

		FileInputStream fis = null;
		DataInputStream dis = null;
		BufferedInputStream bis = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);

			Connection con = new Conexao()
					.getConexaoMySql("InsereRO_SIES.java - D:\\base_Sies/RORSTLDR.D"
							+ yyMMdd + ".csv");

			System.out.println("Inserindo linhas...");

			con.setAutoCommit(false);

			while (dis.available() != 0) {

				String[] part = dis.readLine().split(";");

				if (!(part[0].contains("LIVRO"))) {
					if (part[2].equalsIgnoreCase("ENDOSSOS DE RESTITUICAO")) {
						@SuppressWarnings("deprecation")
						String sql = "INSERT INTO RO_SIES (ANOMES_REF, COD_RAMO, RAMO, PRODUTO, MOVIMENTO, APOLICE, ENDOSSO, PARCELA,"
								+ " DT_EMISSAO, DT_INICIO, DT_TERMINO, CPFCNPJ, SEGURADO, LIDER_IMP_SEGURADA, COSS_IMP_SEGURADA, "
								+ "RESS_IMP_SEGURADA, LIDER_PREMIO_TARIF, COSS_PREMIO_TARIF, RESS_PREMIO_TARIF, LIDER_DESCONTO, "
								+ "COSS_DESCONTO, LIDER_ADICIONAL, COSS_ADICIONAL, LIDER_CUSTO, LIDER_IOF, LIDER_COMISSAO, COSS_COMISSAO,"
								+ " RESS_COMISSAO, LIDER_PREMIO_TOTAL, COSS_PREMIO_TOTAL, RESS_PREMIO_TOTAL) "
								+ "VALUES (?,?,?,?,?,?,?,?, STR_TO_DATE(?,'%d/%m/%Y') , STR_TO_DATE(?,'%d/%m/%Y') , STR_TO_DATE(?,'%d/%m/%Y') ,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

						// /********************************************************************/
						int i = 0;
						try {
							/*
							 * insert que sera feito na tabela de 'RO_SIES'
							 */
							PreparedStatement ps = con.prepareStatement(sql);

							for (i = 1; i <= 31; i++) {

								switch (i) {
								case 1:// ANOMES_REF
									ps.setInt(i, Integer.parseInt(yyyyMM));
									break;
								case 2:// COD_RAMO
									ps.setInt(i, Integer.parseInt(part[4]));
									break;
								case 3:// RAMO
									ps.setString(i, part[5]);
									break;
								case 4:// PRODUTO
									ps.setString(i, part[7]);
									break;
								case 5:// MOVIMENTO
									ps.setString(i, "ENDOSSOS DE RESTITUICAO");
									break;
								case 6:// APOLICE
									ps.setLong(i, Long.parseLong(part[8]));
									break;
								case 7:// ENDOSSO
									ps.setInt(i, Integer.parseInt(part[9]));
									break;
								case 8:// PARCELA
									ps.setInt(i, Integer.parseInt(part[10]));
									break;
								case 9:// DT_EMISSAO
									ps.setString(i,
											part[12].replace(" 00:00:00", "")
													.replace(" 00:00", ""));
									break;
								case 10:// DT_INICIO
									ps.setString(i,
											part[13].replace(" 00:00:00", "")
													.replace(" 00:00", ""));
									break;
								case 11:// DT_TERMINO
									ps.setString(i,
											part[14].replace(" 00:00:00", "")
													.replace(" 00:00", ""));
									break;
								case 12:// CPFCNPJ
									ps.setString(i, part[15].trim());
									break;
								case 13:// SEGURADO
									ps.setString(i, part[16].trim());
									break;
								case 14:// LIDER_IMP_SEGURADA
									ps.setDouble(
											i,
											Double.parseDouble(part[19].trim()
													.replace(".", "")
													.replace(",", ".")));
									break;
								case 15:// COSS_IMP_SEGURADA
									ps.setDouble(
											i,
											Double.parseDouble(part[20].trim()
													.replace(".", "")
													.replace(",", ".")));
									break;
								default:
									ps.setDouble(
											i,
											Double.parseDouble(part[i + 5]
													.trim().replace(".", "")
													.replace(",", ".")));
									break;
								}

							}
							ps.executeUpdate();
							ps.close();
							ps = null;

						} catch (SQLException e) {
							e.printStackTrace();
							System.out.println("coluna " + i + " " + part[i]);

						}
						part = null;
						sql = null;
					}
				}// if
			}// WHILE

			con.commit();
			con.close();
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
	}

	public static void insereROSinistroSIAS(String anoCompletoMes)
			throws SQLException {

		String anoMes = anoCompletoMes.substring(0, 6);
		System.out.println("parametro " + anoMes);
		File file = new File("D:\\base_Sias/sinistro_RORG0052B_" + anoMes
				+ ".rpt");

		/* ******************************************************************* */

		FileInputStream fis = null;
		DataInputStream dis = null;
		BufferedInputStream bis = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);

			Connection con = new Conexao()
					.getConexaoMySql("InsereRO_SIES.java - D:\\base_Sias/sinistro_RORG0052B_"
							+ anoMes + ".rpt");

			System.out.println("Inserindo linhas...");

			con.setAutoCommit(false);

			while (dis.available() != 0) {
				String[] part = dis.readLine().split(";");

				if (part[0].contains("20")) {

					String sql = "insert into ro_sinistro_sias(ANOMES_REF, ORG, RAMO, PROD, COD_MOVIMENTO, MOVIMENTO, COD_OPERACAO, OPERACAO, DIA, FTE_PREM, FTE_AVIS, SINISTRO, FAVORECIDO, AVISO, COMUNICADO, OCORRENCIA, SEGURADO, APOLICE, VL_LIDER, VL_COSSEGURO, VL_RESSEGURO, VL_TOTAL, CAUSA, GRUPO_CAUSA) "
							+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,STR_TO_DATE(?,'%Y-%m-%d'),STR_TO_DATE(?,'%Y-%m-%d'),STR_TO_DATE(?,'%Y-%m-%d'),?,?,?,?,?,?,?,?)";

					// /********************************************************************/
					int i = 0;
					try {
						/*
						 * insert que será feito na tabela de 'ro_sinistro_sias'
						 */
						PreparedStatement ps = con.prepareStatement(sql);

						for (i = 1; i <= 24; i++) {

							if (i <= 5) {
								ps.setInt(i, Integer.parseInt(part[i - 1]
										.replace("ï»¿", "")));
							} else if (i == 6) {// MOVIMENTO
								ps.setString(i, part[i - 1].trim());
							} else if (i == 7) {// COD_OPERACAO
								ps.setInt(i, Integer.parseInt(part[i - 1]));
							} else if (i == 8) {// OPERACAO
								ps.setString(i, part[i - 1].trim());
							} else if (i >= 9 && i <= 11) {// DIA-PREM-AVIS
								ps.setFloat(i, Float.parseFloat(part[i - 1]));
							} else if (i == 12) {// SINISTRO
								ps.setDouble(i, Double.parseDouble(part[i - 1]));
							} else if (i == 13) {// FAVORECIDO
								ps.setString(i, part[i - 1].trim());
							} else if (i >= 14 && i <= 16) {// aviso-comuni-ocorrenc
								ps.setString(i, part[i - 1].replace(
										" 00:00:00.000", ""));
							} else if (i == 17) {// SEGURADO
								ps.setString(i, part[i - 1].trim());
							} else if (i == 18) {// APOLICE
								ps.setDouble(i, Double.parseDouble(part[i - 1]));
							} else if (i >= 19 && i <= 22) {// lider-cosseg-ress-total
								ps.setFloat(i, Float.parseFloat(part[i - 1]
										.replace(",", ".")));
							} else {
								ps.setString(i, part[i - 1].trim());// causa-grupocausa
							}
						}
						ps.executeUpdate();
						// System.out.println(ps.toString());
						ps.close();
						ps = null;

					} catch (SQLException e) {
						e.printStackTrace();
						System.out.println("coluna " + i + " " + part[i]);

					}
					part = null;
					sql = null;

				}// if

			}// WHILE

			con.commit();
			con.close();
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
	}

	public static void insereROSinistroSIES(String anoCompletoMes)
			throws SQLException {

		String yyMMdd = anoCompletoMes.replace("20", "");
		String yyyyMM = anoCompletoMes.substring(0, 6);

		File file = new File("D:\\base_Sies/ROSINLDR.D" + yyMMdd + ".csv");

		/* ******************************************************************* */

		FileInputStream fis = null;
		DataInputStream dis = null;
		BufferedInputStream bis = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);

			Connection con = new Conexao()
					.getConexaoMySql("D:\\base_Sies/ROSINLDR.D" + yyMMdd
							+ ".csv");

			System.out.println("Inserindo linhas...");

			con.setAutoCommit(false);

			while (dis.available() != 0) {
				String[] part = dis.readLine().split(";");
				if (!part[0].contains("LIVRO")) {
					@SuppressWarnings("deprecation")
					String sql = "insert into ro_sinistro_sies(ANOMES_REF,LIVRO,PAGINA,ORGAO,MOVIMENTO,RAMO,PRODUTO,DIA,N_SINISTRO,N_APOLICE,DATA_COMUNICADO,"
							+ "DATA_OCORRENCIA,SEGURADO_FAVORECIDO,VALOR_LIDER,VALOR_COSSEGURO,VALOR_RESSEGURO,VALOR_TOTAL)"
							+ " values (?,?,?,?,?,?,?,?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,?,?);";

					// /********************************************************************/
					int i = 0;
					try {
						/*
						 * insert que sera feito na tabela de 'RO_SIES'
						 */
						PreparedStatement ps = con.prepareStatement(sql);
						for (i = 1; i <= 17; i++) {
							if (i == 1) {// anomes
								ps.setInt(i, Integer.parseInt(yyyyMM));
							} else if (i == 2 || i == 3) {// livro-pagi
								ps.setInt(i, Integer.parseInt(part[i - 2]));
							} else if (i >= 4 && i <= 7) {// orgao-movim-ramo-produ
								ps.setString(i, part[i - 2]);
							} else if (i == 8) {
								ps.setInt(i, Integer.parseInt(part[i - 2]));
							} else if (i == 9 || i == 10) {// nsinis-napolic
								ps.setString(i, part[i - 2]);
							} else if (i == 11 || i == 12) {// dtcomuni-dtocorre
								ps.setString(i,
										part[i - 2].replace(" 00:00", "")
												.replace(":00", ""));
							} else if (i == 13) {// SEGURADO_Favor
								ps.setString(i, part[i - 2]);
							} else {
								ps.setDouble(
										i,
										Double.parseDouble(part[i - 2].trim()
												.replace(".", "")
												.replace(",", ".")));
							}
						}
						ps.executeUpdate();
						ps.close();
						ps = null;

					} catch (SQLException e) {
						e.printStackTrace();
						System.out.println("coluna " + i + " " + part[i]);

					}
					part = null;
					sql = null;
				}// if
			}// WHILE

			con.commit();
			con.close();
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
	}

	public static void insereM2087(String anoCompletoMes) throws SQLException {

		String yyMMdd = anoCompletoMes.replace("20", "");
		String yyyyMM = anoCompletoMes.substring(0, 6);

		String caminhoNome = "\\\\ntservercpd\\SUPIM\\SIES_M2087_" + yyMMdd
				+ ".txt";
		File file = new File(caminhoNome);

		/* ******************************************************************* */

		FileInputStream fis = null;
		DataInputStream dis = null;
		BufferedInputStream bis = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);

			Connection con = new Conexao().getConexaoMySql("D:\\SIES_M2087_"
					+ yyMMdd + ".txt");

			System.out.println("Inserindo linhas...");

			con.setAutoCommit(false);

			while (dis.available() != 0) {
				String[] part = dis.readLine().split(";");
				if (!part[0].contains("USUARIO")) {
					@SuppressWarnings("deprecation")
					String sql = "insert into sies_m2087 "
							+ "(anomes_ref,USUARIO,FONTE,AG_VENDA,PRODUTO,APOLICE,ENDOSSO,DT_EMISSAO,INI_VIGENCIA,FIM_VIGENCIA,I_S,DT_CAD,VALOR_TOTAL,QTD_PARCELAS,NATUREZA_DA_OPERACAO,TIPO_DA_OPERAÇÃO,DT_OPERACAO,TIPO_ADESAO,FORMA_COBRANCA,TIPO_COB_ADESAO,EMISSAO,CPF_CNPJ,SUREG,N_PROPOSTA,CANAL_DE_VENDA,MELHOR_DATA,MATRICULA_INDICADOR,NOME_INDICADOR)"
							+ " values (?,?,?,?,?,?,?, STR_TO_DATE(?,'%d/%m/%Y'), STR_TO_DATE(?,'%d/%m/%Y'), STR_TO_DATE(?,'%d/%m/%Y'),?, STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,?, STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,?,?,?,?,?,?,?,?);";

					// /********************************************************************/
					int i = 0;
					try {
						/*
						 * insert que sera feito na tabela de 'sies_m2087'
						 */
						PreparedStatement ps = con.prepareStatement(sql);
						for (i = 1; i <= 28; i++) {
							if (i == 1) {// anomes
								ps.setInt(i, Integer.parseInt(yyyyMM));
							} else if (i == 2 || i == 3) {// usuar-font
								ps.setString(i, part[i - 2].trim());
							} else if (i == 4 || i == 5) {// agvenda - produ
								ps.setString(i, part[i - 2]);
							} else if (i == 6 || i == 7) {// apolic-endosso
								ps.setString(i, part[i - 2]);
							} else if (i >= 8 && i <= 10) {// emissao-vigenc-fimvig
								ps.setString(i, part[i - 2].trim());
							} else if (i == 11) {// IS
								ps.setDouble(
										i,
										Double.parseDouble(part[i - 2].replace(
												".", "").replace(",", ".")));
							} else if (i == 12) {// DT_CAD
								ps.setString(i, part[i - 2]);
							} else if (i == 13) {// VALOR_TOTAL
								ps.setDouble(
										i,
										Double.parseDouble(part[i - 2].replace(
												".", "").replace(",", ".")));
							} else if (i >= 14 && i <= 16) {// PARCELAS-NATUREZA-TIPO_DA_OPERAÇÃO
								ps.setString(i, part[i - 2].trim());
							} else if (i == 17) {// DT_OPERACAO
								ps.setString(i, part[i - 2]);
							} else {

								ps.setString(i, part[i - 2].trim());

							}
						}
						System.out.println(ps);
						ps.executeUpdate();
						ps.close();
						ps = null;

					} catch (SQLException e) {
						e.printStackTrace();
						System.out.println("coluna " + i + " " + part[i]);

					}
					part = null;
					sql = null;
				}// if
			}// WHILE

			con.commit();
			con.close();
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
	}

	public static void insere_rComp() throws SQLException {

		File file = new File("D:\\rcomp/R_COMP.TXT");

		/* ******************************************************************* */

		FileInputStream fis = null;
		DataInputStream dis = null;
		BufferedInputStream bis = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);

			Connection con = new Conexao()
					.getConexaoMySql("D:\\rcomp/R_COMP.TXT");

			System.out.println("Inserindo linhas...");

			con.setAutoCommit(false);

			while (dis.available() != 0) {
				String[] part = dis.readLine().split(";");
				if (!part[0].contains("USUARIO")) {
					@SuppressWarnings("deprecation")
					String sql = "insert into r_comp2 "
							+ "(COD_SEG, PROCESSO, TIPO, CLASSE, APOLICE, ENDOSSO, COD_END, ITEM, COBERTURA,UF, INICIO_VIG, FIM_VIG, TIPO_FRANQ, VAL_FRANQ, IMP_SEG, PREMIO, CORRETAGEM, PERC_DESC)"
							+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

					// /********************************************************************/
					int i = 0;
					try {

						PreparedStatement ps = con.prepareStatement(sql);
						for (i = 1; i <= 18; i++) {

							ps.setString(i, part[i - 1].trim());

						}
						// System.out.println(ps);
						ps.executeUpdate();
						ps.close();
						ps = null;

					} catch (SQLException e) {
						e.printStackTrace();
						System.out.println("coluna " + i + " " + part[i]);

					}
					part = null;
					sql = null;
				}// if
			}// WHILE

			con.commit();
			con.close();
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
	}

	public static void insere_S_Comp() throws SQLException {

		File file = new File("D:\\rcomp/S_COMP2.CSV");

		/* ******************************************************************* */

		FileInputStream fis = null;
		DataInputStream dis = null;
		BufferedInputStream bis = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);

			Connection con = new Conexao()
					.getConexaoMySql("D:\\rcomp/S_COMP2.CSV");

			System.out.println("Inserindo linhas...");

			con.setAutoCommit(false);

			while (dis.available() != 0) {
				String[] part = dis.readLine().split(";");
				if (!part[0].contains("USUARIO")) {
					@SuppressWarnings("deprecation")
					String sql = "insert into s_comp2 "
							+ "(COD_SEG,TIPO,CLASSE,APOLICE,ENDOSSO,ITEM,COBERTURA,UF,VAL_FRANQ,INDENIZ,D_AVISO,D_LIQ,D_OCORR)"
							+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?);";

					// /********************************************************************/
					int i = 0;
					try {

						PreparedStatement ps = con.prepareStatement(sql);
						for (i = 1; i <= 13; i++) {

							ps.setString(i, part[i - 1].trim());

						}
						// System.out.println(ps);
						ps.executeUpdate();
						ps.close();
						ps = null;

					} catch (SQLException e) {
						e.printStackTrace();
						System.out.println("coluna " + i + " " + part[i]);

					}
					part = null;
					sql = null;
				}// if
			}// WHILE

			con.commit();
			con.close();
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
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * DIARIO
	 */

	public static void insereRoSIASdiario(String anoCompletoMes)
			throws SQLException {

		String anoMes = anoCompletoMes.substring(0, 6);

		// File file = new
		// File("\\\\ntservercpd\\Registros_OFICIAIS\\D"+anoMes+".GL0012B.TXT");

		File file = new File(
				"\\\\ntservercpd\\Registros_OFICIAIS\\D160812.GL0012B.TXT");

		/* ******************************************************************* */

		FileInputStream fis = null;
		DataInputStream dis = null;
		BufferedInputStream bis = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);

			Connection con = new Conexao()
					.getConexaoMySql("insereRoSIAS.java - \\\\ntservercpd\\Registros_OFICIAIS\\D"
							+ anoMes + ".GL0012B.TXT");

			System.out.println("Inserindo linhas...");

			con.setAutoCommit(false);

			while (dis.available() != 0) {
				@SuppressWarnings("deprecation")
				String[] part = dis.readLine().split(";");

				if (part[0].matches("\\d")) {

					@SuppressWarnings("deprecation")
					String sql = "INSERT INTO sistemadirid.RO_RG0012B VALUES (?,?,?,?,?,?,?,?,?,?,?, STR_TO_DATE(?,'%Y-%m-%d'), STR_TO_DATE(?,'%Y-%m-%d'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

					// /********************************************************************/
					int i = 0;

					try {
						/*
						 * insert que sera feito na tabela de 'RO_SIES'
						 */
						PreparedStatement ps = con.prepareStatement(sql);

						for (i = 1; i <= 39; i++) {
							if (i == 1) {
								ps.setString(i, part[i - 1].replace("ï»¿", ""));
							} else if (i == 12) {
								ps.setString(
										i,
										part[i - 1].trim().replace(
												" 00:00:00.000", ""));
							} else if (i == 13) {
								ps.setString(
										i,
										part[i - 1].trim().replace(
												" 00:00:00.000", ""));
							} else {
								ps.setString(i, part[i - 1].trim());
							}
						}
						ps.executeUpdate();
						ps.close();
						ps = null;

					} catch (SQLException e) {
						e.printStackTrace();
						System.out.println("coluna " + i + " " + part[i]);

					}
					part = null;
					sql = null;

				}// if

			}// WHILE

			con.commit();
			con.close();
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
	}

	public static void insereVigentesAUTO_RD(String anomes_ref)
			throws SQLException {

		String anomes = anomes_ref.substring(0, 6);
		File file = new File("D:\\vigente/VIGENTES_AUTO_" + anomes + ".csv");

		/* ******************************************************************* */

		FileInputStream fis = null;
		DataInputStream dis = null;
		BufferedInputStream bis = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);

			Connection con = new Conexao()
					.getConexaoMySql("insereRoSIAS.java - D:\\vigente/VIGENTES_AUTO_"
							+ anomes + ".csv");

			System.out.println("Inserindo linhas...");

			con.setAutoCommit(false);

			while (dis.available() != 0) {
				@SuppressWarnings("deprecation")
				String[] part = dis.readLine().split(";");

				@SuppressWarnings("deprecation")
				String sql = "INSERT INTO sistemadirid.temp_vigentes_auto_rd (produto,apolice,cpf_cnpj,tipo_pessoa,anomes_ref) VALUES (?,?,?,?,?);";

				// /********************************************************************/

				try {
					/*
					 * insert que sera feito na tabela de
					 * 'temp_vigentes_auto_rd'
					 */
					PreparedStatement ps = con.prepareStatement(sql);

					for (int i = 1; i <= 4; i++) {
						ps.setString(i, part[i - 1]);
					}
					ps.setString(5, anomes);
					// System.out.println(ps);
					ps.executeUpdate();
					ps.close();
					ps = null;

				} catch (SQLException e) {
					e.printStackTrace();
				}
				part = null;
				sql = null;

			}// WHILE

			con.commit();
			con.close();
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

		file = new File("D:\\vigente/VIGENTES_RD_" + anomes + ".csv");

		/* ******************************************************************* */

		fis = null;
		dis = null;
		bis = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);

			Connection con = new Conexao()
					.getConexaoMySql("insereRoSIAS.java - D:\\vigente/VIGENTES_RD_"
							+ anomes + ".csv");

			System.out.println("Inserindo linhas...");

			con.setAutoCommit(false);

			while (dis.available() != 0) {
				@SuppressWarnings("deprecation")
				String[] part = dis.readLine().split(";");

				@SuppressWarnings("deprecation")
				String sql = "INSERT INTO sistemadirid.temp_vigentes_auto_rd (produto,apolice,cpf_cnpj,tipo_pessoa,anomes_ref) VALUES (?,?,?,?,?);";

				// /********************************************************************/

				try {
					/*
					 * insert que sera feito na tabela de
					 * 'temp_vigentes_auto_rd'
					 */
					PreparedStatement ps = con.prepareStatement(sql);

					for (int i = 1; i <= 4; i++) {
						ps.setString(i, part[i - 1]);
					}
					ps.setString(5, anomes);
					// System.out.println(ps);
					ps.executeUpdate();
					ps.close();
					ps = null;

				} catch (SQLException e) {
					e.printStackTrace();
				}
				part = null;
				sql = null;

			}// WHILE

			con.commit();
			con.close();
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

	}
}
