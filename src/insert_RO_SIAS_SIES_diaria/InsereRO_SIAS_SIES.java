package insert_RO_SIAS_SIES_diaria;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class InsereRO_SIAS_SIES {

	public static void main(String[] args) {
		String anoCompletoMes;
		String anoMesDia_M2087;

		// for (int i = 1; i <= 30; i++) {
		//
		// // Novembro
		// if (i == 2 || i == 4 || i == 5 || i == 11 || i == 12 || i == 15 || i == 18 ||
		// i == 19 || i == 25
		// || i == 26) {
		// System.out.println("pulou o dia " + i);
		// continue;
		// }
		//
		// if (i < 10) {
		// anoCompletoMes = anoMesDia_M2087 = "2017110" + i;
		// } else {
		// anoCompletoMes = anoMesDia_M2087 = "201711" + i;
		// }
		// String dat[] = { "20160129", "20160229", "20160331", "20160429", "20160531",
		// "20160630", "20160729",
		// "20160831", "20160930", "20161031", "20161130", "20161230" };
		// anoCompletoMes = anoMesDia_M2087 = dat[i];
		try {

			Date date = Calendar.getInstance().getTime();

			// anoCompletoMes = args[0];
			// anoCompletoMes = anoMesDia_M2087 = retornaDataOntem();
			anoCompletoMes = anoMesDia_M2087 = "20180123";

			String anoCut = anoCompletoMes.substring(0, 4);
			String mesCut = anoCompletoMes.substring(4, 6);
			String diaCut = anoCompletoMes.substring(6, 8);

			System.out.println("Hora do início - " + date);

			// ******************
			// ******************

			insereRoSIASdiario(anoCompletoMes);
			insereRoSinistroSIASdiario(anoCompletoMes);
			/*
			 * // apenas no primeiro dia util o RG0012B.TXT eh gerado // se for primeiro dia
			 * util if (DATE_FORMAT4.format(anoCompletoMes_Calendar.getTime())
			 * .equalsIgnoreCase( uteis.getPrimeiroDiaUtilDoMes( anoCompletoMes_Calendar,
			 * 4))) { // SE A DATA 'anoCompletoMes' QUE FOR RODAR A ROTINA FOR O // PRIMEIRO
			 * DIA UTIL DO MES, APAGA O GL52B E INSERE O RO DE // FECHAMENTO DE SINISTROS
			 * RORG52(mes anterior) // insereRG0052B_Mensal(anoCompletoMes);
			 * System.out.println("GL52bMensal"); } else {
			 * 
			 * // insereRoSinistroSIASdiario(anoCompletoMes);
			 * 
			 * System.out.println("insereSinistroSIAS");
			 * 
			 * }
			 */
			insereRoSIESdiario(anoCompletoMes, anoMesDia_M2087);
			insereRoSinistroSIESdiario(anoCompletoMes);
			excluiDadosTemp_graficofaturamento(anoCompletoMes);
			atualizaDadosTemp_graficoFaturamento(anoCompletoMes);
			atualizaHistoricoTemp(anoCompletoMes);

			// ****************
			// ******************

			// insereM2087(anoCompletoMes);

			// ****************
			// ******************

			date = Calendar.getInstance().getTime();
			System.out.println("Hora do término - " + date);
			System.out.println();
			System.out.println(">>> Finalizado sem erros!!! <<<");
			System.out.println();
			// } catch (SQLException sqle) {
			// sqle.printStackTrace();
			// System.out.println("ERRO NO SQL");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Insira o parametro 'anoMesDia' no seguinte formato 'AAAAmmDD'");
		}

		// }

	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * DIARIO
	 */

	private static void insereRoSIASdiario(String anoCompletoMes) throws SQLException {

		String YYYYmm = anoCompletoMes.substring(0, 6);
		String YYmmdd = anoCompletoMes.substring(2, 8);
		File file = new File("\\\\ntservercpd\\Registros_OFICIAIS\\D" + YYmmdd + ".GL0012B.TXT");
		// File file = new File("D:\\roEmtSies/ROEMTLDR.D" + YYmmdd + ".csv");
		// File file = new File("D:\\roEmtSies/arquivos cortados/1610/D" + YYmmdd +
		// ".GL0012B.TXT");
		/* ******************************************************************* */

		FileInputStream fis = null;
		DataInputStream dis = null;
		BufferedInputStream bis = null;
		try {

			if (file.exists()) {
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				dis = new DataInputStream(bis);

				Connection con = new Conexao().getConexaoMySql(
						"insereRoSIAS_SIES.java - \\\\ntservercpd\\Registros_OFICIAIS\\D" + YYmmdd + ".GL0012B.TXT");

				System.out.println("Inserindo linhas...");

				con.setAutoCommit(false);

				while (dis.available() != 0) {
					@SuppressWarnings("deprecation")
					String[] part = dis.readLine().split(";");
					if (part[0].matches("\\d+")) {
						// 40 colunas
						@SuppressWarnings("deprecation")
						String sql = "INSERT INTO sistemadirid.ro_GL0012B VALUES (?,?,?,?,?,?,?,?,?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

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
									ps.setDouble(i + 1,
											Double.parseDouble(part[i - 1].trim().replace(".", "").replace(",", ".")));
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
					} // if
				} // WHILE

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
				System.err.println("Arquivo GL0012B SIAS não encotrado.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// *****************************************************
		// roda a procedure para inserir em ro_sias_diaria
		// *****************************************************
		// *****************************************************
		// *****************************************************
		try {
			Connection con = new Conexao().getConexaoMySql("insereRoSIAS_SIES.java - prcSiasFiltraGL12Faturamento()");
			Statement statement = con.createStatement();
			/* Faz um select simples na tabela temp_graficoFaturamento */

			String insertSelect = "call `prcSiasFiltraGL12Faturamento`('" + YYYYmm + "')";
			statement.executeUpdate(insertSelect);

			String truncateGL0012 = "TRUNCATE TABLE ro_gl0012b;";
			statement.executeUpdate(truncateGL0012);
		} catch (Exception e) {
			System.out.println("Problema em insereRoSIASdiario");
			e.printStackTrace();
		}

	}

	private static void insereRoSinistroSIASdiario(String anoCompletoMes) throws SQLException {

		String YYYYmm = anoCompletoMes.substring(0, 6);
		String YYmmdd = anoCompletoMes.substring(2, 8);

		File file = new File("\\\\ntservercpd\\Registros_OFICIAIS\\D" + YYmmdd + ".GL0052B.TXT");
		// File file = new File("D:\\roEmtSies/arquivos cortados/1610/D" + YYmmdd +
		// ".GL0052B.TXT");
		/* ******************************************************************* */

		FileInputStream fis = null;
		DataInputStream dis = null;
		BufferedInputStream bis = null;

		try {
			if (file.exists()) {

				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				dis = new DataInputStream(bis);

				Connection con = new Conexao().getConexaoMySql(
						"insereRoSIAS_SIES.java - \\\\ntservercpd\\Registros_OFICIAIS\\D" + YYmmdd + ".GL0052B.TXT");

				System.out.println("\nDeletando os sinistros Pendentes...");
				try {
					String sqlDelete = "DELETE from ro_sinistro_sias_diaria where MOVIMENTO IN ('SINISTROS PENDENTES','PENDENTE DO AVISO JUDICIAL','PENDENTE DO AVISO JUDICIAL DE SUCUMBENCIA') AND anomes_ref = '"
							+ YYYYmm + "'";
					PreparedStatement ps = con.prepareStatement(sqlDelete);
					ps.execute();
					ps.close();
					ps = null;
				} catch (Exception e) {
					System.out.println("Problema em insereROSinistroSIASdiario");
					e.printStackTrace();
				}
				System.out.println("Sinistros deletados.\n");

				System.out.println("Inserindo linhas...");
				con.setAutoCommit(false);

				while (dis.available() != 0) {
					String[] part = dis.readLine().split(";");

					if (part[0].matches("\\d+")) {

						if (part[1].equalsIgnoreCase("0114") || part[1].equalsIgnoreCase("0118")
								|| part[1].equalsIgnoreCase("0351") || part[1].equalsIgnoreCase("0141")
								|| part[1].equalsIgnoreCase("0167") || part[1].equalsIgnoreCase("0171")
								|| part[1].equalsIgnoreCase("0520") || part[1].equalsIgnoreCase("0553")
								|| part[1].equalsIgnoreCase("0531") || part[1].equalsIgnoreCase("0542")) {

							// 23 colunas
							String sql = "insert into ro_sinistro_sias_diaria(anomes_ref, ORG, RAMO, PROD, COD_MOVIMENTO, MOVIMENTO, DIA, FTE_PREM, FTE_AVIS, SINISTRO, FAVORECIDO, AVISO, COMUNICADO, OCORRENCIA, SEGURADO, APOLICE, COD_OPERACAO, OPERACAO, VL_LIDER, VL_COSSEGURO, VL_RESSEGURO, VL_TOTAL, CAUSA, GRUPO_CAUSA) "
									+ "values (?,?,?,?,?,?,?,?,?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,?,?,?,?,?,?,?)";

							// /********************************************************************/
							int i = 0;
							try {
								/*
								 * insert que será feito na tabela de 'ro_sinistro_sias_diaria'
								 */
								PreparedStatement ps = con.prepareStatement(sql);

								ps.setInt(1, Integer.parseInt(YYYYmm));
								for (i = 1; i <= 23; i++) {

									if (i <= 4) {
										ps.setInt(i + 1, Integer.parseInt(part[i - 1].trim()));
									} else if (i == 5) {// MOVIMENTO
										ps.setString(i + 1, part[i - 1].trim());
									} else if (i == 6) {// DIA
										ps.setDouble(i + 1, Double.parseDouble(part[i - 1].trim()));
									} else if (i >= 7 && i <= 9) {// FTE.PREM-FTE.AVIS-SINISTRO
										ps.setDouble(i + 1, Double.parseDouble(part[i - 1]));
									} else if (i >= 10 && i <= 14) {// favore-aviso-comuni-ocorrenc-seg
										ps.setString(i + 1, part[i - 1].trim());
									} else if (i == 15) {// APOLICE
										ps.setDouble(i + 1, Double.parseDouble(part[i - 1]));
									} else if (i == 16) {// OPERACAO
										ps.setInt(i + 1, Integer.parseInt(part[i - 1].trim()));
									} else if (i == 17) {// DESCRICAO DA
															// OPERACAO
										ps.setString(i + 1, part[i - 1].trim());
									} else if (i >= 18 && i <= 21) {// lider-cosseg-ress-total
										ps.setDouble(i + 1,
												Double.parseDouble(part[i - 1].replace(".", "").replace(",", ".")));
									} else {
										ps.setString(i + 1, part[i - 1].trim());// causa-DESCRICAoCAUSA
									}

								} // for

								ps.executeUpdate();
								ps.close();
								ps = null;

							} catch (SQLException e) {
								e.printStackTrace();
								System.out.println("coluna " + (i - 1) + " " + part[i - 1]);
							}
							part = null;
							sql = null;
						} // if ramos
					} // if

				} // WHILE

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
				System.err.println("Arquivo GL0052B SIAS não encotrado.");
			} // if file exist

		} catch (IOException e) {
			e.printStackTrace();
		}
	} // FIM SINISTRO SIAS

	/**
	 * Apos uma analise no arquivo 'DYYmmdd.GL0052B.TXT' verificamos que alguns
	 * movimentos nao estao nele. Para isso este metodo apaga o movimento do GL que
	 * esta sendo inserido diariamente e insere o conteudo do arquivo de fechamento
	 * de sinistros mensal 'RG0052B_DYYmmdd.TXT'
	 */
	private static void insereRG0052B_Mensal(String anoCompletoMes) throws SQLException {

		String YYYYmm = anoCompletoMes.substring(0, 6);
		String YYmmdd = anoCompletoMes.substring(2, 8);
		// APAGA O GL52 DE FEVEREIRO E INSERE O RORG52
		// INSERE O RO DO MES ANTERIOR, NO CASO FEVEREIRO

		// PAREI AQUI

		File file = new File("\\\\ntservercpd\\Registros_OFICIAIS\\RG0052B_D" + YYmmdd + ".TXT");

		/* ******************************************************************* */

		FileInputStream fis = null;
		DataInputStream dis = null;
		BufferedInputStream bis = null;

		try {
			if (file.exists()) {

				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				dis = new DataInputStream(bis);

				Connection con = new Conexao().getConexaoMySql(
						"insereRoSIAS_SIES.java - \\\\ntservercpd\\Registros_OFICIAIS\\D" + YYmmdd + ".GL0052B.TXT");

				System.out.println("\nDeletando os sinistros Pendentes...");
				try {
					String sqlDelete = "DELETE from ro_sinistro_sias_diaria where MOVIMENTO IN ('SINISTROS PENDENTES','PENDENTE DO AVISO JUDICIAL','PENDENTE DO AVISO JUDICIAL DE SUCUMBENCIA') AND anomes_ref = '"
							+ YYYYmm + "'";
					PreparedStatement ps = con.prepareStatement(sqlDelete);
					ps.execute();
					ps.close();
					ps = null;
				} catch (Exception e) {
					System.out.println("Problema em insereROSinistroSIASdiario");
					e.printStackTrace();
				}
				System.out.println("Sinistros deletados.\n");

				System.out.println("Inserindo linhas...");
				con.setAutoCommit(false);

				while (dis.available() != 0) {
					String[] part = dis.readLine().split(";");

					if (part[0].matches("\\d+")) {

						if (part[1].equalsIgnoreCase("0114") || part[1].equalsIgnoreCase("0118")
								|| part[1].equalsIgnoreCase("0351") || part[1].equalsIgnoreCase("0141")
								|| part[1].equalsIgnoreCase("0167") || part[1].equalsIgnoreCase("0171")
								|| part[1].equalsIgnoreCase("0520") || part[1].equalsIgnoreCase("0553")
								|| part[1].equalsIgnoreCase("0531") || part[1].equalsIgnoreCase("0542")) {

							// 23 colunas
							String sql = "insert into ro_sinistro_sias_diaria(anomes_ref, ORG, RAMO, PROD, COD_MOVIMENTO, MOVIMENTO, DIA, FTE_PREM, FTE_AVIS, SINISTRO, FAVORECIDO, AVISO, COMUNICADO, OCORRENCIA, SEGURADO, APOLICE, COD_OPERACAO, OPERACAO, VL_LIDER, VL_COSSEGURO, VL_RESSEGURO, VL_TOTAL, CAUSA, GRUPO_CAUSA) "
									+ "values (?,?,?,?,?,?,?,?,?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,?,?,?,?,?,?,?)";

							// /********************************************************************/
							int i = 0;
							try {
								/*
								 * insert que será feito na tabela de 'ro_sinistro_sias_diaria'
								 */
								PreparedStatement ps = con.prepareStatement(sql);

								ps.setInt(1, Integer.parseInt(YYYYmm));
								for (i = 1; i <= 23; i++) {

									if (i <= 4) {
										ps.setInt(i + 1, Integer.parseInt(part[i - 1].trim()));
									} else if (i == 5) {// MOVIMENTO
										ps.setString(i + 1, part[i - 1].trim());
									} else if (i == 6) {// DIA
										ps.setDouble(i + 1, Double.parseDouble(part[i - 1].trim()));
									} else if (i >= 7 && i <= 9) {// FTE.PREM-FTE.AVIS-SINISTRO
										ps.setDouble(i + 1, Double.parseDouble(part[i - 1]));
									} else if (i >= 10 && i <= 14) {// favore-aviso-comuni-ocorrenc-seg
										ps.setString(i + 1, part[i - 1].trim());
									} else if (i == 15) {// APOLICE
										ps.setDouble(i + 1, Double.parseDouble(part[i - 1]));
									} else if (i == 16) {// OPERACAO
										ps.setInt(i + 1, Integer.parseInt(part[i - 1].trim()));
									} else if (i == 17) {// DESCRICAO DA
															// OPERACAO
										ps.setString(i + 1, part[i - 1].trim());
									} else if (i >= 18 && i <= 21) {// lider-cosseg-ress-total
										ps.setDouble(i + 1,
												Double.parseDouble(part[i - 1].replace(".", "").replace(",", ".")));
									} else {
										ps.setString(i + 1, part[i - 1].trim());// causa-DESCRICAoCAUSA
									}

								} // for

								ps.executeUpdate();
								ps.close();
								ps = null;

							} catch (SQLException e) {
								e.printStackTrace();
								System.out.println("coluna " + (i - 1) + " " + part[i - 1]);
							}
							part = null;
							sql = null;
						} // if ramos
					} // if

				} // WHILE

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
				System.err.println("Arquivo GL0052B SIAS não encotrado.");
			} // if file exist

		} catch (IOException e) {
			e.printStackTrace();
		}
	} // FIM SINISTRO SIAS

	private static void insereRoEmissaoSIESdiario(String anoCompletoMes, String anoMesDia_M2087) throws SQLException {

		String YYYYmm = anoCompletoMes.substring(0, 6);
		String YYmmdd = anoCompletoMes.substring(2, 8);

		String YYmmdd_M2087 = anoMesDia_M2087.substring(2, 8);

		File file = new File("\\\\ntservercpd\\SIES\\DADOS_GECONT\\RO\\ROEMTLDR.D" + YYmmdd + ".csv");

		String caminhoNome = "\\\\ntservercpd\\SUPIM\\SIES_M2087_" + YYmmdd_M2087 + ".txt";

		File fileM2087 = new File(caminhoNome);
		/* ******************************************************************* */

		FileInputStream fis = null;
		BufferedInputStream bis = null;
		DataInputStream dis = null;
		DataInputStream disM20 = null;

		try {

			if (file.exists()) {

				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				dis = new DataInputStream(bis);
				boolean M2087existe = true;
				if (fileM2087.exists()) {
					fis = new FileInputStream(fileM2087);
					bis = new BufferedInputStream(fis);
					disM20 = new DataInputStream(bis);
				} else {
					M2087existe = false;
				}

				List<M2087_CanalVenda> listaM20 = new ArrayList<M2087_CanalVenda>();

				Connection con = new Conexao().getConexaoMySql(
						"InsereRO_SIES.java - \\\\ntservercpd\\SIES\\DADOS_GECONT\\RO\\ROEMTLDR.D" + YYmmdd + ".csv");
				Connection con2 = new Conexao().getConexaoMySql("InsereRO_SIES.java insereRoEmissaoSIESdiario");

				System.out.println("Inserindo linhas...");

				String deleteROSIES = "delete from ro_sies_diaria where anomes_ref = '" + YYYYmm
						+ "' AND movimento = 'APOLICE DE SEGURO';";
				PreparedStatement psDel = con.prepareStatement(deleteROSIES);
				psDel.executeUpdate();

				String deleteROSIES_uf = "delete from ro_uf_sies_diaria where anomes_ref = '" + YYYYmm + "'";
				PreparedStatement psDel_uf = con.prepareStatement(deleteROSIES_uf);
				psDel_uf.executeUpdate();

				con.setAutoCommit(false);
				con2.setAutoCommit(false);

				while (dis.available() != 0) {
					String[] part = dis.readLine().split(";");

					if (part[0].matches("\\d+")) {

						@SuppressWarnings("deprecation")
						String sql = "INSERT INTO ro_sies_diaria (anomes_REF, COD_RAMO, RAMO, PRODUTO, MOVIMENTO, APOLICE, ENDOSSO, PARCELA,"
								+ " DT_EMISSAO, DT_INICIO, DT_TERMINO, CPFCNPJ, SEGURADO, LIDER_IMP_SEGURADA, COSS_IMP_SEGURADA, "
								+ "RESS_IMP_SEGURADA, LIDER_PREMIO_TARIF, COSS_PREMIO_TARIF, RESS_PREMIO_TARIF, LIDER_DESCONTO, "
								+ "COSS_DESCONTO, LIDER_ADICIONAL, COSS_ADICIONAL, LIDER_CUSTO, LIDER_IOF, LIDER_COMISSAO, COSS_COMISSAO,"
								+ " RESS_COMISSAO, LIDER_PREMIO_TOTAL, COSS_PREMIO_TOTAL, RESS_PREMIO_TOTAL, canal_venda, UF, AG_VENDA) VALUES (?,?,?,?,?,?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

						String sqlUfSies = "insert into ro_uf_sies_diaria (anomes_ref, UF, APOLICE, ENDOSSO) values (?,?,?,?);";

						// /********************************************************************/
						int i = 0;
						try {
							/*
							 * insert que sera feito na tabela de 'RO_SIES_diaria'
							 */
							PreparedStatement ps = con.prepareStatement(sql);

							/* insert que sera feito na tabela `RO_UF_SIES` */
							PreparedStatement psUF = con2.prepareStatement(sqlUfSies);
							String canalVenda = null;
							int agenciaVenda = 0;
							for (i = 1; i <= 34; i++) {

								switch (i) {
								case 1:// anomes_REF
									ps.setInt(i, Integer.parseInt(YYYYmm));
									psUF.setInt(i, Integer.parseInt(YYYYmm));
									break;
								case 2: // COD_RAMO
									ps.setInt(i, Integer.parseInt(part[3]));// cod_ramo
									psUF.setString(i, part[7].trim());
									break;
								case 3: // RAMO
									ps.setString(i, part[4]);// ramo
									psUF.setDouble(i, Double.parseDouble(part[8].trim()));// apolice
									break;
								case 4: // PRODUTO
									ps.setString(i, part[6]);// produto
									psUF.setDouble(i, Double.parseDouble(part[9].trim()));
									break;
								case 5: // MOVIMENTO
									ps.setString(i, "APOLICE DE SEGURO");
									break;
								case 6: // APOLICE
									ps.setDouble(i, Double.parseDouble(part[8]));

									if (M2087existe) {

										while (disM20.available() != 0) {

											M2087_CanalVenda m20 = new M2087_CanalVenda();

											String[] partM20 = disM20.readLine().split(";");
											if (partM20[13].trim().equalsIgnoreCase("Emissão")) {
												m20.setAgencia(partM20[2]);
												m20.setApolice(partM20[4]);
												m20.setCanal(partM20[23]);
											}

											listaM20.add(m20);

										}
										for (int j = 0; j < listaM20.size(); j++) {
											if (part[8].equals(listaM20.get(j).getApolice())) {
												canalVenda = listaM20.get(j).getCanal();
												agenciaVenda = Integer.parseInt(listaM20.get(j).getAgencia());
												break;
											} else {
												canalVenda = "-";
												agenciaVenda = 0;
											}
										}
									} else {
										canalVenda = "m2087_" + YYmmdd_M2087 + "_NaoEncontrado";
										agenciaVenda = 0;
									}

									break;
								case 7: // ENDOSSO
									ps.setDouble(i, Double.parseDouble(part[9]));
									break;
								case 8: // PARCELA
									ps.setDouble(i, Double.parseDouble(part[11]));
									break;
								case 9: // DT_EMISSAO
									ps.setString(i, part[13].replace(" 00:00:00", "").replace(" 00:00", ""));
									break;
								case 10: // DT_INICIO
									ps.setString(i, part[14].replace(" 00:00:00", "").replace(" 00:00", ""));
									break;
								case 11: // DT_TERMINO
									ps.setString(i, part[15].replace(" 00:00:00", "").replace(" 00:00", ""));
									break;
								case 12: // CPFCNPJ
									ps.setString(i, part[16].trim());
									break;
								case 13: // SEGURADO
									ps.setString(i, part[17].trim());
									break;
								case 32: // canalVenda
									ps.setString(i, canalVenda.trim());
									break;
								case 33: // UF
									ps.setString(i, part[7].trim());
									break;
								case 34: // Agencia
									ps.setInt(i, agenciaVenda);
									break;
								default:
									ps.setDouble(i,
											Double.parseDouble(part[i + 6].trim().replace(".", "").replace(",", ".")));
									break;
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

					} // if

				} // WHILE

				con.commit();
				con2.commit();
				con.close();
				con2.close();

				try {
					fis.close();
					dis.close();
					bis.close();
					disM20.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			} else {
				System.out.println("Arquivo RO SIES Emissao não encontrado.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void insereRoEmissaoSIESdiario_LOCAL(String anoCompletoMes, String anoMesDia_M2087,
			String anoMesDIADiretorio) throws SQLException {

		String YYYYmm = anoCompletoMes.substring(0, 6);
		String YYmmdd = anoCompletoMes.substring(2, 8);

		String YYmmdd_M2087 = anoMesDia_M2087.substring(2, 8);

		// File file = new File("\\\\ntservercpd\\SIES\\DADOS_GECONT\\RO\\ROEMTLDR.D" +
		// YYmmdd + ".csv");
		//
		// String caminhoNome = "\\\\ntservercpd\\SUPIM\\SIES_M2087_" + YYmmdd_M2087 +
		// ".txt";

		File file = new File("D:\\roEmtSies/arquivos cortados/" + anoMesDIADiretorio + "/ROEMTLDR.D" + YYmmdd + ".csv");

		String caminhoNome = "D:\\roEmtSies/arquivos cortados/" + anoMesDIADiretorio + "/SIES_M2087_"
				+ anoMesDIADiretorio + ".txt";

		File fileM2087 = new File(caminhoNome);
		/* ******************************************************************* */

		FileInputStream fis = null;
		BufferedInputStream bis = null;
		DataInputStream dis = null;
		DataInputStream disM20 = null;

		try {

			if (file.exists()) {

				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				dis = new DataInputStream(bis);
				boolean M2087existe = true;
				if (fileM2087.exists()) {
					fis = new FileInputStream(fileM2087);
					bis = new BufferedInputStream(fis);
					disM20 = new DataInputStream(bis);
				} else {
					M2087existe = false;
				}

				List<M2087_CanalVenda> listaM20 = new ArrayList<M2087_CanalVenda>();

				Connection con = new Conexao().getConexaoMySql(
						"InsereRO_SIES.java - \\\\ntservercpd\\SIES\\DADOS_GECONT\\RO\\ROEMTLDR.D" + YYmmdd + ".csv");
				Connection con2 = new Conexao().getConexaoMySql("InsereRO_SIES.java insereRoEmissaoSIESdiario");

				System.out.println("Inserindo linhas...");

				String deleteROSIES = "delete from ro_sies_diaria where anomes_ref = '" + YYYYmm
						+ "' AND movimento = 'APOLICE DE SEGURO';";
				PreparedStatement psDel = con.prepareStatement(deleteROSIES);
				psDel.executeUpdate();

				String deleteROSIES_uf = "delete from ro_uf_sies_diaria where anomes_ref = '" + YYYYmm + "'";
				PreparedStatement psDel_uf = con.prepareStatement(deleteROSIES_uf);
				psDel_uf.executeUpdate();

				con.setAutoCommit(false);
				con2.setAutoCommit(false);

				while (dis.available() != 0) {
					String[] part = dis.readLine().split(";");

					if (part[0].matches("\\d+")) {

						@SuppressWarnings("deprecation")
						String sql = "INSERT INTO ro_sies_diaria (anomes_REF, COD_RAMO, RAMO, PRODUTO, MOVIMENTO, APOLICE, ENDOSSO, PARCELA,"
								+ " DT_EMISSAO, DT_INICIO, DT_TERMINO, CPFCNPJ, SEGURADO, LIDER_IMP_SEGURADA, COSS_IMP_SEGURADA, "
								+ "RESS_IMP_SEGURADA, LIDER_PREMIO_TARIF, COSS_PREMIO_TARIF, RESS_PREMIO_TARIF, LIDER_DESCONTO, "
								+ "COSS_DESCONTO, LIDER_ADICIONAL, COSS_ADICIONAL, LIDER_CUSTO, LIDER_IOF, LIDER_COMISSAO, COSS_COMISSAO,"
								+ " RESS_COMISSAO, LIDER_PREMIO_TOTAL, COSS_PREMIO_TOTAL, RESS_PREMIO_TOTAL, canal_venda, UF, AG_VENDA) VALUES (?,?,?,?,?,?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

						String sqlUfSies = "insert into ro_uf_sies_diaria (anomes_ref, UF, APOLICE, ENDOSSO) values (?,?,?,?);";

						// /********************************************************************/
						int i = 0;
						try {
							/*
							 * insert que sera feito na tabela de 'RO_SIES_diaria'
							 */
							PreparedStatement ps = con.prepareStatement(sql);

							/* insert que sera feito na tabela `RO_UF_SIES` */
							PreparedStatement psUF = con2.prepareStatement(sqlUfSies);
							String canalVenda = null;
							int agenciaVenda = 0;
							for (i = 1; i <= 34; i++) {

								switch (i) {
								case 1:// anomes_REF
									ps.setInt(i, Integer.parseInt(YYYYmm));
									psUF.setInt(i, Integer.parseInt(YYYYmm));
									break;
								case 2: // COD_RAMO
									ps.setInt(i, Integer.parseInt(part[3]));// cod_ramo
									psUF.setString(i, part[7].trim());
									break;
								case 3: // RAMO
									ps.setString(i, part[4]);// ramo
									psUF.setDouble(i, Double.parseDouble(part[8].trim()));// apolice
									break;
								case 4: // PRODUTO
									ps.setString(i, part[6]);// produto
									psUF.setDouble(i, Double.parseDouble(part[9].trim()));
									break;
								case 5: // MOVIMENTO
									ps.setString(i, "APOLICE DE SEGURO");
									break;
								case 6: // APOLICE
									ps.setDouble(i, Double.parseDouble(part[8]));

									if (M2087existe) {

										while (disM20.available() != 0) {

											M2087_CanalVenda m20 = new M2087_CanalVenda();

											String[] partM20 = disM20.readLine().split(";");
											if (partM20[13].trim().equalsIgnoreCase("Emissão")) {
												m20.setAgencia(partM20[2]);
												m20.setApolice(partM20[4]);
												m20.setCanal(partM20[23]);
											}

											listaM20.add(m20);

										}
										for (int j = 0; j < listaM20.size(); j++) {
											if (part[8].equals(listaM20.get(j).getApolice())) {
												canalVenda = listaM20.get(j).getCanal();
												agenciaVenda = Integer.parseInt(listaM20.get(j).getAgencia());
												break;
											} else {
												canalVenda = "-";
												agenciaVenda = 0;
											}
										}
									} else {
										canalVenda = "m2087_" + YYmmdd_M2087 + "_NaoEncontrado";
										agenciaVenda = 0;
									}

									break;
								case 7: // ENDOSSO
									ps.setDouble(i, Double.parseDouble(part[9]));
									break;
								case 8: // PARCELA
									ps.setDouble(i, Double.parseDouble(part[11]));
									break;
								case 9: // DT_EMISSAO
									ps.setString(i, part[13].replace(" 00:00:00", "").replace(" 00:00", ""));
									break;
								case 10: // DT_INICIO
									ps.setString(i, part[14].replace(" 00:00:00", "").replace(" 00:00", ""));
									break;
								case 11: // DT_TERMINO
									ps.setString(i, part[15].replace(" 00:00:00", "").replace(" 00:00", ""));
									break;
								case 12: // CPFCNPJ
									ps.setString(i, part[16].trim());
									break;
								case 13: // SEGURADO
									ps.setString(i, part[17].trim());
									break;
								case 32: // canalVenda
									ps.setString(i, canalVenda.trim());
									break;
								case 33: // UF
									ps.setString(i, part[7].trim());
									break;
								case 34: // Agencia
									ps.setInt(i, agenciaVenda);
									break;
								default:
									ps.setDouble(i,
											Double.parseDouble(part[i + 6].trim().replace(".", "").replace(",", ".")));
									break;
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

					} // if

				} // WHILE

				con.commit();
				con2.commit();
				con.close();
				con2.close();

				try {
					fis.close();
					dis.close();
					bis.close();
					disM20.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			} else {
				System.out.println("Arquivo RO SIES Emissao não encontrado.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void insereRoCancelamentoSIESdiario(String anoCompletoMes, String anoMesDia_M2087)
			throws SQLException {

		String YYYYmm = anoCompletoMes.substring(0, 6);
		String YYmmdd = anoCompletoMes.substring(2, 8);

		String YYmmdd_M2087 = anoMesDia_M2087.substring(2, 8);

		File file = new File("\\\\ntservercpd\\SIES\\DADOS_GECONT\\RO\\ROCANLDR.D" + YYmmdd + ".csv");
		File fileM2087 = new File("\\\\ntservercpd\\SUPIM\\SIES_M2087_" + YYmmdd_M2087 + ".txt");

		/* ******************************************************************* */

		FileInputStream fis = null;
		DataInputStream dis = null;
		BufferedInputStream bis = null;
		DataInputStream disM20 = null;

		try {

			if (file.exists()) {

				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				dis = new DataInputStream(bis);

				boolean M2087existe = true;
				if (fileM2087.exists()) {
					fis = new FileInputStream(fileM2087);
					bis = new BufferedInputStream(fis);
					disM20 = new DataInputStream(bis);
				} else {
					M2087existe = false;
				}

				List<M2087_CanalVenda> listaM20 = new ArrayList<M2087_CanalVenda>();

				Connection con = new Conexao().getConexaoMySql(
						"InsereRO_SIES.java - \\\\ntservercpd\\SIES\\DADOS_GECONT\\RO\\ROCANLDR.D" + YYmmdd + ".csv");

				System.out.println("Inserindo linhas...");

				String deleteROSIES = "delete from ro_sies_diaria where anomes_ref = '" + YYYYmm
						+ "' AND movimento = 'ENDOSSOS DE CANCELAMENTO';";
				PreparedStatement psDel = con.prepareStatement(deleteROSIES);
				psDel.executeUpdate();

				con.setAutoCommit(false);

				while (dis.available() != 0) {
					String[] part = dis.readLine().split(";");

					if (part[0].matches("\\d+")) {

						@SuppressWarnings("deprecation")
						String sql = "INSERT INTO ro_sies_diaria (anomes_REF, COD_RAMO, RAMO, PRODUTO, MOVIMENTO, APOLICE, ENDOSSO, PARCELA,"
								+ " DT_EMISSAO, DT_INICIO, DT_TERMINO, CPFCNPJ, SEGURADO, LIDER_IMP_SEGURADA, COSS_IMP_SEGURADA, "
								+ "RESS_IMP_SEGURADA, LIDER_PREMIO_TARIF, COSS_PREMIO_TARIF, RESS_PREMIO_TARIF, LIDER_DESCONTO, "
								+ "COSS_DESCONTO, LIDER_ADICIONAL, COSS_ADICIONAL, LIDER_CUSTO, LIDER_IOF, LIDER_COMISSAO, COSS_COMISSAO,"
								+ " RESS_COMISSAO, LIDER_PREMIO_TOTAL, COSS_PREMIO_TOTAL, RESS_PREMIO_TOTAL, canal_venda, UF, AG_VENDA) VALUES (?,?,?,?,?,?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

						// /********************************************************************/
						int i = 0;
						try {
							/*
							 * insert que sera feito na tabela de 'RO_SIES_diaria'
							 */
							PreparedStatement ps = con.prepareStatement(sql);

							String canalVenda = null;
							String UF = null;
							int agenciaVenda = 0;

							for (i = 1; i <= 34; i++) {

								switch (i) {
								case 1:// anomes_REF
									ps.setInt(i, Integer.parseInt(YYYYmm));
									break;
								case 2: // COD_RAMO
									ps.setInt(i, Integer.parseInt(part[3]));// cod_ramo
									break;
								case 3: // RAMO
									ps.setString(i, part[4]);// ramo
									break;
								case 4: // PRODUTO
									ps.setString(i, part[6]);// produto
									break;
								case 5: // MOVIMENTO
									ps.setString(i, "ENDOSSOS DE CANCELAMENTO");
									break;
								case 6: // APOLICE
									ps.setDouble(i, Double.parseDouble(part[7]));

									if (M2087existe) {

										while (disM20.available() != 0) {

											M2087_CanalVenda m20 = new M2087_CanalVenda();

											String[] partM20 = disM20.readLine().split(";");

											if (partM20[13].trim().equalsIgnoreCase("Canc. Indevi")
													|| partM20[13].trim().equalsIgnoreCase("Cancelamento")) {

												m20.setAgencia(partM20[2]);
												m20.setApolice(partM20[4]);
												m20.setCanal(partM20[23]);
												m20.setUf("NN");
											}

											listaM20.add(m20);
										}
										for (int j = 0; j < listaM20.size(); j++) {

											if (part[7].equals(listaM20.get(j).getApolice())) {
												canalVenda = listaM20.get(j).getCanal();
												agenciaVenda = Integer.parseInt(listaM20.get(j).getAgencia());
												UF = listaM20.get(j).getUf();
												break;
											} else {
												canalVenda = "-";
												agenciaVenda = 0;
											}
										}
									} else {
										canalVenda = "m2087_" + YYmmdd_M2087 + "_NaoEncontrado";
										agenciaVenda = 0;
									}

									break;
								case 7: // ENDOSSO
									ps.setDouble(i, Double.parseDouble(part[8]));
									break;
								case 8: // PARCELA
									ps.setDouble(i, Double.parseDouble(part[10]));
									break;
								case 9: // DT_EMISSAO
									ps.setString(i, part[12].replace(" 00:00:00", "").replace(" 00:00", ""));
									break;
								case 10: // DT_INICIO
									ps.setString(i, part[13].replace(" 00:00:00", "").replace(" 00:00", ""));
									break;
								case 11: // DT_TERMINO
									ps.setString(i, part[14].replace(" 00:00:00", "").replace(" 00:00", ""));
									break;
								case 12: // CPFCNPJ
									ps.setString(i, part[15].trim());
									break;
								case 13: // SEGURADO
									ps.setString(i, part[16].trim());
									break;
								case 32: // canalVenda
									ps.setString(i, canalVenda.trim());
									break;
								case 33: // UF
									ps.setString(i, UF);
									break;
								case 34: // Agencia
									ps.setInt(i, agenciaVenda);
									break;
								default:
									ps.setDouble(i,
											Double.parseDouble(part[i + 5].trim().replace(".", "").replace(",", ".")));
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

					} // if

				} // WHILE

				con.commit();
				con.close();

				try {
					fis.close();
					dis.close();
					bis.close();
					disM20.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			} else {
				System.out.println("Arquivo RO SIES Cancelamento não encontrado.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void insereRoCancelamentoSIESdiario_LOCAL(String anoCompletoMes, String anoMesDia_M2087,
			String anomesAPAGAR) throws SQLException {

		String YYYYmm = anoCompletoMes.substring(0, 6);
		String YYmmdd = anoCompletoMes.substring(2, 8);

		String YYmmdd_M2087 = anoMesDia_M2087.substring(2, 8);

		// File file = new File("\\\\ntservercpd\\SIES\\DADOS_GECONT\\RO\\ROCANLDR.D" +
		// YYmmdd + ".csv");
		// File fileM2087 = new File("\\\\ntservercpd\\SUPIM\\SIES_M2087_" +
		// YYmmdd_M2087 + ".txt");

		File file = new File("D:\\roEmtSies/arquivos cortados/" + anomesAPAGAR + "/ROCANLDR.D" + YYmmdd + ".csv");

		String caminhoNome = "D:\\roEmtSies/arquivos cortados/" + anomesAPAGAR + "/SIES_M2087_" + anomesAPAGAR + ".txt";
		File fileM2087 = new File(caminhoNome);
		/* ******************************************************************* */

		FileInputStream fis = null;
		DataInputStream dis = null;
		BufferedInputStream bis = null;
		DataInputStream disM20 = null;

		try {

			if (file.exists()) {

				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				dis = new DataInputStream(bis);

				boolean M2087existe = true;
				if (fileM2087.exists()) {
					fis = new FileInputStream(fileM2087);
					bis = new BufferedInputStream(fis);
					disM20 = new DataInputStream(bis);
				} else {
					M2087existe = false;
				}

				List<M2087_CanalVenda> listaM20 = new ArrayList<M2087_CanalVenda>();

				Connection con = new Conexao().getConexaoMySql(
						"InsereRO_SIES.java - \\\\ntservercpd\\SIES\\DADOS_GECONT\\RO\\ROCANLDR.D" + YYmmdd + ".csv");

				System.out.println("Inserindo linhas...");

				String deleteROSIES = "delete from ro_sies_diaria where anomes_ref = '" + YYYYmm
						+ "' AND movimento = 'ENDOSSOS DE CANCELAMENTO';";
				PreparedStatement psDel = con.prepareStatement(deleteROSIES);
				psDel.executeUpdate();

				con.setAutoCommit(false);

				while (dis.available() != 0) {
					String[] part = dis.readLine().split(";");

					if (part[0].matches("\\d+")) {

						@SuppressWarnings("deprecation")
						String sql = "INSERT INTO ro_sies_diaria (anomes_REF, COD_RAMO, RAMO, PRODUTO, MOVIMENTO, APOLICE, ENDOSSO, PARCELA,"
								+ " DT_EMISSAO, DT_INICIO, DT_TERMINO, CPFCNPJ, SEGURADO, LIDER_IMP_SEGURADA, COSS_IMP_SEGURADA, "
								+ "RESS_IMP_SEGURADA, LIDER_PREMIO_TARIF, COSS_PREMIO_TARIF, RESS_PREMIO_TARIF, LIDER_DESCONTO, "
								+ "COSS_DESCONTO, LIDER_ADICIONAL, COSS_ADICIONAL, LIDER_CUSTO, LIDER_IOF, LIDER_COMISSAO, COSS_COMISSAO,"
								+ " RESS_COMISSAO, LIDER_PREMIO_TOTAL, COSS_PREMIO_TOTAL, RESS_PREMIO_TOTAL, canal_venda, UF, AG_VENDA) VALUES (?,?,?,?,?,?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

						// /********************************************************************/
						int i = 0;
						try {
							/*
							 * insert que sera feito na tabela de 'RO_SIES_diaria'
							 */
							PreparedStatement ps = con.prepareStatement(sql);

							String canalVenda = null;
							String UF = null;
							int agenciaVenda = 0;

							for (i = 1; i <= 34; i++) {

								switch (i) {
								case 1:// anomes_REF
									ps.setInt(i, Integer.parseInt(YYYYmm));
									break;
								case 2: // COD_RAMO
									ps.setInt(i, Integer.parseInt(part[3]));// cod_ramo
									break;
								case 3: // RAMO
									ps.setString(i, part[4]);// ramo
									break;
								case 4: // PRODUTO
									ps.setString(i, part[6]);// produto
									break;
								case 5: // MOVIMENTO
									ps.setString(i, "ENDOSSOS DE CANCELAMENTO");
									break;
								case 6: // APOLICE
									ps.setDouble(i, Double.parseDouble(part[7]));

									if (M2087existe) {

										while (disM20.available() != 0) {

											M2087_CanalVenda m20 = new M2087_CanalVenda();

											String[] partM20 = disM20.readLine().split(";");

											if (partM20[13].trim().equalsIgnoreCase("Canc. Indevi")
													|| partM20[13].trim().equalsIgnoreCase("Cancelamento")) {

												m20.setAgencia(partM20[2]);
												m20.setApolice(partM20[4]);
												m20.setCanal(partM20[23]);
												m20.setUf("NN");
											}

											listaM20.add(m20);
										}
										for (int j = 0; j < listaM20.size(); j++) {

											if (part[7].equals(listaM20.get(j).getApolice())) {
												canalVenda = listaM20.get(j).getCanal();
												agenciaVenda = Integer.parseInt(listaM20.get(j).getAgencia());
												UF = listaM20.get(j).getUf();
												break;
											} else {
												canalVenda = "-";
												agenciaVenda = 0;
											}
										}
									} else {
										canalVenda = "m2087_" + YYmmdd_M2087 + "_NaoEncontrado";
										agenciaVenda = 0;
									}

									break;
								case 7: // ENDOSSO
									ps.setDouble(i, Double.parseDouble(part[8]));
									break;
								case 8: // PARCELA
									ps.setDouble(i, Double.parseDouble(part[10]));
									break;
								case 9: // DT_EMISSAO
									ps.setString(i, part[12].replace(" 00:00:00", "").replace(" 00:00", ""));
									break;
								case 10: // DT_INICIO
									ps.setString(i, part[13].replace(" 00:00:00", "").replace(" 00:00", ""));
									break;
								case 11: // DT_TERMINO
									ps.setString(i, part[14].replace(" 00:00:00", "").replace(" 00:00", ""));
									break;
								case 12: // CPFCNPJ
									ps.setString(i, part[15].trim());
									break;
								case 13: // SEGURADO
									ps.setString(i, part[16].trim());
									break;
								case 32: // canalVenda
									ps.setString(i, canalVenda.trim());
									break;
								case 33: // UF
									ps.setString(i, UF);
									break;
								case 34: // Agencia
									ps.setInt(i, agenciaVenda);
									break;
								default:
									ps.setDouble(i,
											Double.parseDouble(part[i + 5].trim().replace(".", "").replace(",", ".")));
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

					} // if

				} // WHILE

				con.commit();
				con.close();

				try {
					fis.close();
					dis.close();
					bis.close();
					disM20.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			} else {
				System.out.println("Arquivo RO SIES Cancelamento não encontrado.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void insereRoRestituicaoSIESdiario(String anoCompletoMes) throws SQLException {

		String YYYYmm = anoCompletoMes.substring(0, 6);
		String YYmmdd = anoCompletoMes.substring(2, 8);

		File file = new File("\\\\ntservercpd\\SIES\\DADOS_GECONT\\RO\\RORSTLDR.D" + YYmmdd + ".csv");
		// File file = new File("D:\\roEmtSies/arquivos cortados/" + anomesAPAGAR +
		// "/RORSTLDR.D" + YYmmdd + ".csv");

		/* ******************************************************************* */
		System.out.println("STRING" + file.toString());
		FileInputStream fis = null;
		DataInputStream dis = null;
		BufferedInputStream bis = null;
		try {

			if (file.exists()) {

				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				dis = new DataInputStream(bis);

				Connection con = new Conexao().getConexaoMySql(
						"InsereRO_SIES.java - \\\\ntservercpd\\SIES\\DADOS_GECONT\\RO\\RORSTLDR.D" + YYmmdd + ".csv");

				String deleteROSIES = "delete from ro_sies_diaria where anomes_ref = '" + YYYYmm
						+ "' AND movimento = 'ENDOSSOS DE RESTITUICAO';";
				PreparedStatement psDel = con.prepareStatement(deleteROSIES);
				psDel.executeUpdate();

				System.out.println("Inserindo linhas...");

				con.setAutoCommit(false);

				while (dis.available() != 0) {
					String[] part = dis.readLine().split(";");

					if (part[0].matches("\\d+")) {
						if (part[2].equalsIgnoreCase("ENDOSSOS DE RESTITUICAO")) {
							@SuppressWarnings("deprecation")
							String sql = "INSERT INTO ro_sies_diaria (anomes_REF, COD_RAMO, RAMO, PRODUTO, MOVIMENTO, APOLICE, ENDOSSO, PARCELA,"
									+ " DT_EMISSAO, DT_INICIO, DT_TERMINO, CPFCNPJ, SEGURADO, LIDER_IMP_SEGURADA, COSS_IMP_SEGURADA, "
									+ "RESS_IMP_SEGURADA, LIDER_PREMIO_TARIF, COSS_PREMIO_TARIF, RESS_PREMIO_TARIF, LIDER_DESCONTO, "
									+ "COSS_DESCONTO, LIDER_ADICIONAL, COSS_ADICIONAL, LIDER_CUSTO, LIDER_IOF, LIDER_COMISSAO, COSS_COMISSAO,"
									+ " RESS_COMISSAO, LIDER_PREMIO_TOTAL, COSS_PREMIO_TOTAL, RESS_PREMIO_TOTAL) VALUES (?,?,?,?,?,?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

							// /********************************************************************/
							int i = 0;
							try {
								/*
								 * insert que sera feito na tabela de 'RO_SIES_diaria'
								 */
								PreparedStatement ps = con.prepareStatement(sql);

								for (i = 1; i <= 31; i++) {

									switch (i) {
									case 1:// anomes_ref
										ps.setInt(i, Integer.parseInt(YYYYmm));
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
										ps.setString(i, part[12].replace(" 00:00:00", "").replace(" 00:00", ""));
										break;
									case 10:// DT_INICIO
										ps.setString(i, part[13].replace(" 00:00:00", "").replace(" 00:00", ""));
										break;
									case 11:// DT_TERMINO
										ps.setString(i, part[14].replace(" 00:00:00", "").replace(" 00:00", ""));
										break;
									case 12:// CPFCNPJ
										ps.setString(i, part[15].trim());
										break;
									case 13:// SEGURADO
										ps.setString(i, part[16].trim());
										break;
									case 14:// LIDER_IMP_SEGURADA
										ps.setDouble(i,
												Double.parseDouble(part[19].trim().replace(".", "").replace(",", ".")));
										break;
									case 15:// COSS_IMP_SEGURADA
										ps.setDouble(i,
												Double.parseDouble(part[20].trim().replace(".", "").replace(",", ".")));
										break;
									default:
										ps.setDouble(i, Double
												.parseDouble(part[i + 5].trim().replace(".", "").replace(",", ".")));
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
					} // if

				} // WHILE

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
				System.out.println("Arquivo RO SIES Restituicao não encontrado.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void insereRoRestituicaoSIESdiario_LOCAL(String anoCompletoMes, String anomesAPAGAR)
			throws SQLException {

		String YYYYmm = anoCompletoMes.substring(0, 6);
		String YYmmdd = anoCompletoMes.substring(2, 8);

		// File file = new File("\\\\ntservercpd\\SIES\\DADOS_GECONT\\RO\\RORSTLDR.D" +
		// YYmmdd + ".csv");
		File file = new File("D:\\roEmtSies/arquivos cortados/" + anomesAPAGAR + "/RORSTLDR.D" + YYmmdd + ".csv");

		/* ******************************************************************* */
		System.out.println("STRING" + file.toString());
		FileInputStream fis = null;
		DataInputStream dis = null;
		BufferedInputStream bis = null;
		try {

			if (file.exists()) {

				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				dis = new DataInputStream(bis);

				Connection con = new Conexao().getConexaoMySql(
						"InsereRO_SIES.java - \\\\ntservercpd\\SIES\\DADOS_GECONT\\RO\\RORSTLDR.D" + YYmmdd + ".csv");

				String deleteROSIES = "delete from ro_sies_diaria where anomes_ref = '" + YYYYmm
						+ "' AND movimento = 'ENDOSSOS DE RESTITUICAO';";
				PreparedStatement psDel = con.prepareStatement(deleteROSIES);
				psDel.executeUpdate();

				System.out.println("Inserindo linhas...");

				con.setAutoCommit(false);

				while (dis.available() != 0) {
					String[] part = dis.readLine().split(";");

					if (part[0].matches("\\d+")) {
						if (part[2].equalsIgnoreCase("ENDOSSOS DE RESTITUICAO")) {
							@SuppressWarnings("deprecation")
							String sql = "INSERT INTO ro_sies_diaria (anomes_REF, COD_RAMO, RAMO, PRODUTO, MOVIMENTO, APOLICE, ENDOSSO, PARCELA,"
									+ " DT_EMISSAO, DT_INICIO, DT_TERMINO, CPFCNPJ, SEGURADO, LIDER_IMP_SEGURADA, COSS_IMP_SEGURADA, "
									+ "RESS_IMP_SEGURADA, LIDER_PREMIO_TARIF, COSS_PREMIO_TARIF, RESS_PREMIO_TARIF, LIDER_DESCONTO, "
									+ "COSS_DESCONTO, LIDER_ADICIONAL, COSS_ADICIONAL, LIDER_CUSTO, LIDER_IOF, LIDER_COMISSAO, COSS_COMISSAO,"
									+ " RESS_COMISSAO, LIDER_PREMIO_TOTAL, COSS_PREMIO_TOTAL, RESS_PREMIO_TOTAL) VALUES (?,?,?,?,?,?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

							// /********************************************************************/
							int i = 0;
							try {
								/*
								 * insert que sera feito na tabela de 'RO_SIES_diaria'
								 */
								PreparedStatement ps = con.prepareStatement(sql);

								for (i = 1; i <= 31; i++) {

									switch (i) {
									case 1:// anomes_ref
										ps.setInt(i, Integer.parseInt(YYYYmm));
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
										ps.setString(i, part[12].replace(" 00:00:00", "").replace(" 00:00", ""));
										break;
									case 10:// DT_INICIO
										ps.setString(i, part[13].replace(" 00:00:00", "").replace(" 00:00", ""));
										break;
									case 11:// DT_TERMINO
										ps.setString(i, part[14].replace(" 00:00:00", "").replace(" 00:00", ""));
										break;
									case 12:// CPFCNPJ
										ps.setString(i, part[15].trim());
										break;
									case 13:// SEGURADO
										ps.setString(i, part[16].trim());
										break;
									case 14:// LIDER_IMP_SEGURADA
										ps.setDouble(i,
												Double.parseDouble(part[19].trim().replace(".", "").replace(",", ".")));
										break;
									case 15:// COSS_IMP_SEGURADA
										ps.setDouble(i,
												Double.parseDouble(part[20].trim().replace(".", "").replace(",", ".")));
										break;
									default:
										ps.setDouble(i, Double
												.parseDouble(part[i + 5].trim().replace(".", "").replace(",", ".")));
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
					} // if

				} // WHILE

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
				System.out.println("Arquivo RO SIES Restituicao não encontrado.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void insereRoSinistroSIESdiario(String anoCompletoMes) throws SQLException {
		String YYYYmm = anoCompletoMes.substring(0, 6);
		String YYmmdd = anoCompletoMes.substring(2, 8);

		File file = new File("\\\\ntservercpd\\SIES\\DADOS_GECONT\\RO\\ROSINLDR.D" + YYmmdd + ".csv");

		/* ******************************************************************* */

		FileInputStream fis = null;
		DataInputStream dis = null;
		BufferedInputStream bis = null;
		try {

			if (file.exists()) {

				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				dis = new DataInputStream(bis);

				Connection con = new Conexao().getConexaoMySql(
						"InsereRO_SIES.java - \\\\ntservercpd\\SIES\\DADOS_GECONT\\RO\\ROSINLDR.D" + YYmmdd + ".csv");

				System.out.println("Inserindo linhas...");

				String deleteROSIES = "delete from ro_sinistro_sies_diaria where anomes_ref = '" + YYYYmm + "';";
				PreparedStatement psDel = con.prepareStatement(deleteROSIES);
				psDel.executeUpdate();

				con.setAutoCommit(false);

				while (dis.available() != 0) {
					String[] part = dis.readLine().split(";");

					if (part[0].matches("\\d+")) {

						@SuppressWarnings("deprecation")
						String sql = "insert into ro_sinistro_sies_diaria(anomes_ref,LIVRO,PAGINA,ORGAO,MOVIMENTO,RAMO,PRODUTO,DIA,N_SINISTRO,N_APOLICE,DATA_COMUNICADO,"
								+ "DATA_OCORRENCIA,SEGURADO_FAVORECIDO,VALOR_LIDER,VALOR_COSSEGURO,VALOR_RESSEGURO,VALOR_TOTAL)"
								+ " values (?,?,?,?,?,?,?,?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,?,?);";

						// /********************************************************************/
						int i = 0;
						try {
							/*
							 * insert que sera feito na tabela de 'RO_SIES_diaria'
							 */
							PreparedStatement ps = con.prepareStatement(sql);

							for (i = 1; i <= 17; i++) {
								if (i == 1) {// YYYYmm
									ps.setInt(i, Integer.parseInt(YYYYmm));
								} else if (i == 2 || i == 3) {// livro-pagi
									ps.setInt(i, Integer.parseInt(part[i - 2]));
								} else if (i >= 4 && i <= 7) {// orgao-movim-ramo-produ
									ps.setString(i, part[i - 2]);
								} else if (i == 8) {
									ps.setInt(i, Integer.parseInt(part[i - 2]));
								} else if (i == 9 || i == 10) {// nsinis-napolic
									ps.setString(i, part[i - 2]);
								} else if (i == 11 || i == 12) {// dtcomuni-dtocorre
									ps.setString(i, part[i - 2].replaceAll("\\s\\d{2}:\\d{2}:\\d{2}", ""));
								} else if (i == 13) {// SEGURADO_Favor
									ps.setString(i, part[i - 2]);
								} else {
									ps.setDouble(i,
											Double.parseDouble(part[i - 2].trim().replace(".", "").replace(",", ".")));
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

					} // if

				} // WHILE

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
				System.out.println("Arquivo RO SIES SINISTROS não encontrado.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void insereRoSinistroSIESdiario_LOCAL(String anoCompletoMes, String anomesdiaAPAGAR)
			throws SQLException {
		String YYYYmm = anoCompletoMes.substring(0, 6);
		String YYmmdd = anoCompletoMes.substring(2, 8);

		// File file = new File("\\\\ntservercpd\\SIES\\DADOS_GECONT\\RO\\ROSINLDR.D" +
		// YYmmdd + ".csv");
		File file = new File("D:\\roEmtSies/arquivos cortados/" + anomesdiaAPAGAR + "/ROSINLDR.D" + YYmmdd + ".csv");

		/* ******************************************************************* */

		FileInputStream fis = null;
		DataInputStream dis = null;
		BufferedInputStream bis = null;
		try {

			if (file.exists()) {

				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				dis = new DataInputStream(bis);

				Connection con = new Conexao().getConexaoMySql(
						"InsereRO_SIES.java - \\\\ntservercpd\\SIES\\DADOS_GECONT\\RO\\ROSINLDR.D" + YYmmdd + ".csv");

				System.out.println("Inserindo linhas...");

				String deleteROSIES = "delete from ro_sinistro_sies_diaria where anomes_ref = '" + YYYYmm + "';";
				PreparedStatement psDel = con.prepareStatement(deleteROSIES);
				psDel.executeUpdate();

				con.setAutoCommit(false);

				while (dis.available() != 0) {
					String[] part = dis.readLine().split(";");

					if (part[0].matches("\\d+")) {

						@SuppressWarnings("deprecation")
						String sql = "insert into ro_sinistro_sies_diaria(anomes_ref,LIVRO,PAGINA,ORGAO,MOVIMENTO,RAMO,PRODUTO,DIA,N_SINISTRO,N_APOLICE,DATA_COMUNICADO,"
								+ "DATA_OCORRENCIA,SEGURADO_FAVORECIDO,VALOR_LIDER,VALOR_COSSEGURO,VALOR_RESSEGURO,VALOR_TOTAL)"
								+ " values (?,?,?,?,?,?,?,?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,?,?);";

						// /********************************************************************/
						int i = 0;
						try {
							/*
							 * insert que sera feito na tabela de 'RO_SIES_diaria'
							 */
							PreparedStatement ps = con.prepareStatement(sql);

							for (i = 1; i <= 17; i++) {
								if (i == 1) {// YYYYmm
									ps.setInt(i, Integer.parseInt(YYYYmm));
								} else if (i == 2 || i == 3) {// livro-pagi
									ps.setInt(i, Integer.parseInt(part[i - 2]));
								} else if (i >= 4 && i <= 7) {// orgao-movim-ramo-produ
									ps.setString(i, part[i - 2]);
								} else if (i == 8) {
									ps.setInt(i, Integer.parseInt(part[i - 2]));
								} else if (i == 9 || i == 10) {// nsinis-napolic
									ps.setString(i, part[i - 2]);
								} else if (i == 11 || i == 12) {// dtcomuni-dtocorre
									ps.setString(i, part[i - 2].replaceAll("\\s\\d{2}:\\d{2}:\\d{2}", ""));
								} else if (i == 13) {// SEGURADO_Favor
									ps.setString(i, part[i - 2]);
								} else {
									ps.setDouble(i,
											Double.parseDouble(part[i - 2].trim().replace(".", "").replace(",", ".")));
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

					} // if

				} // WHILE

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
				System.out.println("Arquivo RO SIES SINISTROS não encontrado.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void excluiDadosTemp_graficofaturamento(String anoCompletoMes) {
		String YYYY = anoCompletoMes.substring(0, 4);
		Connection con = new Conexao().getConexaoMySql("insereRoSIAS_SIES.java - excluiDadosTemp");
		System.out.println("\nDeletando dados do ano de " + YYYY + " da tabela temp_graficofaturamento_diaria...");
		try {
			String sqlDelete = "DELETE from temp_graficofaturamento_diaria where ano = '" + YYYY + "'";
			PreparedStatement ps = con.prepareStatement(sqlDelete);
			ps.execute();
			ps.close();
			ps = null;
			System.out.println("Dados da temp_graficofaturamento_diaria deletados.\n");
		} catch (Exception e) {
			System.out.println("Problema em - excluiDadosTemp - InsereRO_SIAS_SIES");
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERRO metodo excluiDadosTemp InsereRO_SIAS_SIES");
			}
		}
	}

	private static void excluiDados_MovimentoPorCanalVenda(String anoCompletoMes, String movimento) {
		String YYYY = anoCompletoMes.substring(0, 4);
		Connection con = new Conexao().getConexaoMySql("insereRoSIAS_SIES.java - excluiDados_MovimentoPorCanalVenda");
		System.out.println(
				"\nDeletando dados do ano de " + YYYY + " da tabela temp_graficofaturamento_movimentoporcanal...");
		try {
			String sqlDelete = "DELETE from temp_graficofaturamento_movimentoporcanal where ano = '" + YYYY
					+ "' and movimento = '" + movimento + "';";
			PreparedStatement ps = con.prepareStatement(sqlDelete);
			ps.execute();
			ps.close();
			ps = null;
			System.out.println("Dados da temp_graficofaturamento_movimentoporcanal deletados.\n");
		} catch (Exception e) {
			System.out.println("Problema em - excluiDados_MovimentoPorCanalVenda - InsereRO_SIAS_SIES");
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERRO metodo excluiDados_MovimentoPorCanalVenda InsereRO_SIAS_SIES");
			}
		}
	}

	private static void atualizaDadosTemp_graficoFaturamento(String anoCompletoMes) {
		String YYYY = anoCompletoMes.substring(0, 4);

		System.out.println(
				new Date(System.currentTimeMillis()) + " Método atualizaDadosTemp: Inserindo dados na tabela...");

		Connection con = new Conexao().getConexaoMySql("insereRoSIAS_SIES.java - atualizaDadosTemp");
		System.out.println("Executando as procedures...");
		try {
			Statement statement = con.createStatement();
			/* ==================================== */

			/*
			 * ================= AUTO ================
			 */
			/* AUTO Tranquilo Exclusivo */
			String insereAutoTranqExcl = "call `prcDados_DIARIA_Faturamento`('" + YYYY
					+ "','Auto Tranquilo Exclusivo','3133,3136,3138,3173,3174,3175')";
			statement.executeUpdate(insereAutoTranqExcl);

			/* AUTO Tranquilo Correntista */
			String insereAutoTranqCorren = "call `prcDados_DIARIA_Faturamento`('" + YYYY
					+ "','Auto Tranquilo Correntista','3142,3143,3144,3145,3146,3147,3148,3149,3176,3177,3178,3179,3180,3181,3182,3183')";
			statement.executeUpdate(insereAutoTranqCorren);

			/* AUTO Frota */
			String insereAutoFrota = "call `prcDados_DIARIA_Faturamento`('" + YYYY
					+ "','Auto Tranquilo Frota','3120,3172')";
			statement.executeUpdate(insereAutoFrota);

			/* AUTO Facil */
			String insereAutoFacil = "call `prcDados_DIARIA_Faturamento`('" + YYYY
					+ "','Auto Fácil','5302,5303,5304,5388')";
			statement.executeUpdate(insereAutoFacil);

			/*
			 * ================= RDPJ ================
			 */

			/* RDEquipamentos */
			String insereRdEquipamentos = "call `prcDados_DIARIA_Faturamento`('" + YYYY + "','Rd Equipamentos','7114')";
			statement.executeUpdate(insereRdEquipamentos);

			/* EMPRESARIAL */
			String insereEmpresarial = "call `prcDados_DIARIA_Faturamento`('" + YYYY
					+ "','MR Empresarial','1801,1802,1804')";
			statement.executeUpdate(insereEmpresarial);

			/* Empresarial Prazo Curto */
			String insereEmpresarialPrazoCurto = "call `prcDados_DIARIA_Faturamento`('" + YYYY
					+ "','Empresarial Prazo Curto','1809')";
			statement.executeUpdate(insereEmpresarialPrazoCurto);

			/* LOTERICO */
			String insereLoterico = "call `prcDados_DIARIA_Faturamento`('" + YYYY + "','Loterico','1803')";
			statement.executeUpdate(insereLoterico);

			/* CCA */
			String insereCca = "call `prcDados_DIARIA_Faturamento_Loterico_CCA`('" + YYYY + "','CCA','%CCA%','1805')";
			statement.executeUpdate(insereCca);

			/*
			 * ================= RDPF ================
			 */

			/* Facil Residencial */
			String insereFacilRD = "call `prcDados_DIARIA_Faturamento`('" + YYYY + "','Facil Residencial','1402,1405')";
			statement.executeUpdate(insereFacilRD);

			/* MR Residencial Correntista */
			String insereRdCorrentista = "call `prcDados_DIARIA_FaturamentoRdCorrentista`('" + YYYY
					+ "','MR Residencial Correntista')";
			statement.executeUpdate(insereRdCorrentista);

			/* MR Residencial Economiario */// exclusivo
			String insereRdExclusivo = "call `prcDados_DIARIA_Faturamento`('" + YYYY
					+ "','MR Residencial Economiario','1404')";
			statement.executeUpdate(insereRdExclusivo);

			/* Lar Mais */
			String insereLarMais = "call `prcDados_DIARIA_Faturamento`('" + YYYY + "','Lar Mais','1409')";
			statement.executeUpdate(insereLarMais);

			/* MR Residencial Aporte Caixa */
			String insereAporte = "call `prcDados_DIARIA_Faturamento`('" + YYYY
					+ "','MR Residencial Aporte Caixa','1406,1408')";
			statement.executeUpdate(insereAporte);

			/* Minha Casa Minha Vida Mais Premiavel */
			String insereMCMVMaisPremiavel = "call `prcDados_DIARIA_Faturamento`('" + YYYY
					+ "','MCMV Mais Premiavel','1412')";
			statement.executeUpdate(insereMCMVMaisPremiavel);

			/* Cibrasec Securitizadora */
			String insereCibrasecSecuritizadora = "call `prcDados_DIARIA_Faturamento`('" + YYYY
					+ "','Cibrasec Securitizadora','1413')";
			statement.executeUpdate(insereCibrasecSecuritizadora);

			/* RD PF Outros */
			String insereRdPfOutros = "call `prcDados_DIARIA_Faturamento`('" + YYYY
					+ "','Rd Pf Outros','1002,1410,1411,1489,4002,7100,7101,7106,7107,7108,7109,7110,7117,7122,7123')";
			statement.executeUpdate(insereRdPfOutros);

			/*
			 * ============= AGRUPADOS ================
			 */
			/* AUTO */
			String insereAuto = "call `prcDados_DIARIA_FaturamentoAutomovel`('" + YYYY + "','AUTOMOVEIS')";
			statement.executeUpdate(insereAuto);

			/* RD PJ */
			String insereRDPJ = "call `prcDados_DIARIA_Faturamento_RDPJ`('" + YYYY + "','RDPJ')";
			statement.executeUpdate(insereRDPJ);

			/* RD PF */
			String insereRDPF = "call `prcDados_DIARIA_Faturamento_RDPF`('" + YYYY + "','RDPF')";
			statement.executeUpdate(insereRDPF);

			/* DIRID */
			String insereDIRID = "call `prcDados_DIARIA_FaturamentoDirid`('" + YYYY + "','Dirid')";
			statement.executeUpdate(insereDIRID);

			System.out.println(new Date(System.currentTimeMillis()) + " Método atualizaDadosTemp: ...dados inseridos.");

		} catch (Exception e) {
			System.out.println("ERRO metodo atualizaDadosTemp InsereRO_SIAS_SIES");
			e.printStackTrace();
		}
	}

	private static void atualizaHistoricoTemp(String anoCompletoMes) {

		String YYYY = anoCompletoMes.substring(0, 4);
		String mm = anoCompletoMes.substring(4, 6);
		String dd = anoCompletoMes.substring(6, 8);

		Connection con = new Conexao().getConexaoMySql("InsereRO_SIAS_SIES.java");
		try {

			System.out.println("atualizaHistoricoTemp - Inserindo linhas...");

			String sql = "INSERT INTO historico_graficofaturamento_diaria (data_registro_historico,Produto,Tipo,Ano,Jane,feve,marc,abri,maio,junh,julh,agos,sete,outu,nove,deze) SELECT '"
					+ YYYY + "-" + mm + "-" + dd
					+ "',Produto,Tipo,Ano,Jane,feve,marc,abri,maio,junh,julh,agos,sete,outu,nove,deze from temp_graficofaturamento_diaria where ano='"
					+ YYYY + "';";

			PreparedStatement ps = con.prepareStatement(sql);

			ps.executeUpdate();
			ps.close();
			ps = null;
			System.out.println("...Inserido - atualizaHistoricoTemp");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERRO metodo atualizaHistoricoTemp InsereRO_SIAS_SIES");
			}
		}
	}

	private static void insereRoSIESdiario_Local(String anoCompletoMes, String anoMesDia_M2087, String anoAPAGAR)
			throws SQLException {

		insereRoEmissaoSIESdiario_LOCAL(anoCompletoMes, anoMesDia_M2087, anoAPAGAR);
		insereMovimentoPorCanalVenda(anoCompletoMes, "Emissão");

		insereRoCancelamentoSIESdiario_LOCAL(anoCompletoMes, anoMesDia_M2087, anoAPAGAR);
		insereMovimentoPorCanalVenda(anoCompletoMes, "Cancelamento");

		insereRoRestituicaoSIESdiario_LOCAL(anoCompletoMes, anoAPAGAR);
	}

	private static void insereRoSIESdiario(String anoCompletoMes, String anoMesDia_M2087) throws SQLException {

		insereRoEmissaoSIESdiario(anoCompletoMes, anoMesDia_M2087);
		insereMovimentoPorCanalVenda(anoCompletoMes, "Emissão");

		insereRoCancelamentoSIESdiario(anoCompletoMes, anoMesDia_M2087);
		insereMovimentoPorCanalVenda(anoCompletoMes, "Cancelamento");

		insereRoRestituicaoSIESdiario(anoCompletoMes);
	}

	/**
	 * Apenas produtos emitidos no SIES
	 */
	private static void insereMovimentoPorCanalVenda(String anoCompletoMes, String movimento) {
		excluiDados_MovimentoPorCanalVenda(anoCompletoMes, movimento);
		String YYYY = anoCompletoMes.substring(0, 4);
		System.out.println(new Date(System.currentTimeMillis()) + " Método insereMovimentoPorCanalVenda - " + movimento
				+ ": Inserindo dados na tabela...");

		Connection con = new Conexao().getConexaoMySql("insereRoSIAS_SIES.java - insereMovimentoPorCanalVenda");
		System.out.println("Executando as procedures...");
		try {
			Statement statement = con.createStatement();
			/* ==================================== */

			/*
			 * ================= RDPJ ================
			 */

			/* RDEquipamentos */
			String insereRdEquipamentos = "call `prcDados_DIARIA_MovimentoPorCanal`('" + movimento + "','" + YYYY
					+ "','Rd Equipamentos','7114')";
			statement.executeUpdate(insereRdEquipamentos);

			/* EMPRESARIAL */
			String insereEmpresarial = "call `prcDados_DIARIA_MovimentoPorCanal`('" + movimento + "','" + YYYY
					+ "','MR Empresarial','1801,1802,1804')";
			statement.executeUpdate(insereEmpresarial);

			/* Empresarial Prazo Curto */
			String insereEmpresarialPrazoCurto = "call `prcDados_DIARIA_MovimentoPorCanal`('" + movimento + "','" + YYYY
					+ "','Empresarial Prazo Curto','1809')";
			statement.executeUpdate(insereEmpresarialPrazoCurto);
			/*
			 * ================= RDPF ================
			 */

			/* Facil Residencial */
			String insereFacilRD = "call `prcDados_DIARIA_MovimentoPorCanal`('" + movimento + "','" + YYYY
					+ "','Facil Residencial','1402,1405')";
			statement.executeUpdate(insereFacilRD);

			/* MR Residencial Correntista */
			String insereRdCorrentista = "call `prcDados_DIARIA_MovimentoPorCanal`('" + movimento + "','" + YYYY
					+ "','MR Residencial Correntista','1403,1407')";
			statement.executeUpdate(insereRdCorrentista);

			/* MR Residencial Economiario */// exclusivo
			String insereRdExclusivo = "call `prcDados_DIARIA_MovimentoPorCanal`('" + movimento + "','" + YYYY
					+ "','MR Residencial Economiario','1404')";
			statement.executeUpdate(insereRdExclusivo);

			/* RD PF Outros */
			String insereRdPfOutros = "call `prcDados_DIARIA_MovimentoPorCanal`('" + movimento + "','" + YYYY
					+ "','Rd Pf Outros','1002,1410,1411,1489,4002,7100,7101,7106,7107,7108,7109,7110,7117,7122,7123')";
			statement.executeUpdate(insereRdPfOutros);

			System.out.println(new Date(System.currentTimeMillis()) + " Método atualizaDadosTemp: ...dados inseridos.");

		} catch (SQLException e) {
			System.out.println("ERRO metodo insereMovimentoPorCanalVenda InsereRO_SIAS_SIES");
			e.printStackTrace();
		}
	}

	public static void insereM2087(String anoCompletoMes) throws SQLException {

		String yyMMdd = anoCompletoMes.replace("20", "");
		String yyyyMM = anoCompletoMes.substring(0, 6);

		String caminhoNome = "\\\\ntservercpd\\SUPIM\\SIES_M2087_" + yyMMdd + ".txt";
		File file = new File(caminhoNome);

		/* ******************************************************************* */

		FileInputStream fis = null;
		DataInputStream dis = null;
		BufferedInputStream bis = null;
		try {

			if (file.exists()) {

				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				dis = new DataInputStream(bis);

				Connection con = new Conexao().getConexaoMySql("\\\\ntservercpd\\SUPIM\\SIES_M2087_" + yyMMdd + ".txt");

				System.out.println("Inserindo linhas...");

				String deleteM2087 = "delete from sies_m2087 where anomes_ref = " + yyyyMM + ";";
				PreparedStatement psDel = con.prepareStatement(deleteM2087);
				psDel.executeUpdate();

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
									ps.setDouble(i, Double.parseDouble(part[i - 2].replace(".", "").replace(",", ".")));
								} else if (i == 12) {// DT_CAD
									ps.setString(i, part[i - 2]);
								} else if (i == 13) {// VALOR_TOTAL
									ps.setDouble(i, Double.parseDouble(part[i - 2].replace(".", "").replace(",", ".")));
								} else if (i >= 14 && i <= 16) {// PARCELAS-NATUREZA-TIPO_DA_OPERAÇÃO
									ps.setString(i, part[i - 2].trim());
								} else if (i == 17) {// DT_OPERACAO
									ps.setString(i, part[i - 2]);
								} else {
									if (i == 28 && part.length == 26) {
										ps.setString(i, "-");
									} else {
										ps.setString(i, part[i - 2].trim());
									}
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
					} // if
				} // WHILE

				con.commit();
				con.close();
			} else {
				System.out.println("Arquivo SIES_M2087_" + yyMMdd + ".txt não encontrado.");
			}
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

	private static Date formataOntem() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

	private static String retornaDataOntem() {
		DateFormat dateFormat = new SimpleDateFormat("YYYYMMdd");
		return dateFormat.format(formataOntem());
	}

}

class M2087_CanalVenda {

	private String agencia;
	private String apolice;
	private String canal;
	private String uf;

	public String getUf() {
		return this.uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getAgencia() {
		return this.agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getApolice() {
		return this.apolice;
	}

	public void setApolice(String apolice) {
		this.apolice = apolice;
	}

	public String getCanal() {
		return this.canal;
	}

	public void setCanal(String canal) {
		this.canal = canal;
	}

}
