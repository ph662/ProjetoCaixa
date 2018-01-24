package caixa.dirid.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import caixa.dirid.CONEXAO.Conexao;
import caixa.dirid.VO.FaturamentoVO;

public class VisaoExecutivaDAO extends VisoesDAO {

	/**
	 * Retorna lista contendo BP,EMITIDO,EMITIDOS CANCELADOS,EMITIDOS RESTITUIDOS,
	 * QT APOLICES EMITIDAS,QT SINISTROS AVISADOS,QT SINISTROS INDENIZADOS,RVNE
	 * SINISTROS AVISADOS,SINISTROS INDENIZADOS. O retorno é feito em milhoes e com
	 * varias casas decimais.
	 * 
	 * Esse metodo tambem verifica se a tabela temp_graficoFaturamento esta vazia
	 * para que novos dados sejam inseridos
	 * 
	 * @return List<FaturamentoVO>
	 * 
	 * @arguments String
	 */
	public List<FaturamentoVO> selecionaDadosFaturamento(String ano) {
		// Connection con = new Conexao().getConexaoMySqlSQLServer();
		Connection con = new Conexao().getConexaoMySql("selecionaDadosFaturamento - VisaoExecutivaDAO - " + ano);
		List<FaturamentoVO> fatu = new ArrayList<FaturamentoVO>();
		try {
			Statement statement = con.createStatement();

			// corta a string e pega o nome do parametro da var ano
			String tipo = uteis.cortaRetornaProduto(ano);

			String anoAtual = uteis.cortaRetornaAno(ano);

			/* *************************** */
			/* Utilizando sintaxe MySql */
			/* *************************** */
			String verificaTabela = "SELECT Tipo FROM temp_graficoFaturamento where ano = " + anoAtual + " LIMIT 1,1;";
			String criaTabela = "CREATE TABLE temp_graficoFaturamento(id int primary key auto_increment, Produto VARCHAR(40), Tipo VARCHAR(40), Ano VARCHAR(7), Jane VARCHAR(40),feve VARCHAR(40),marc VARCHAR(40), abri VARCHAR(40), maio VARCHAR(40), junh VARCHAR(40), julh VARCHAR(40), agos VARCHAR(40), sete VARCHAR(40), outu VARCHAR(40), nove VARCHAR(40),deze VARCHAR(40));";

			String sqlFinal = "SELECT Tipo,  	CASE WHEN jane IS NULL THEN '0.0'  		ELSE jane END AS jane,     CASE WHEN feve IS NULL THEN '0.0'  		ELSE feve END AS feve, 	CASE WHEN marc IS NULL THEN '0.0' 		ELSE marc END AS marc, 	CASE WHEN abri IS NULL THEN '0.0'  		ELSE abri END AS abri, 	CASE WHEN maio IS NULL THEN '0.0'  		ELSE maiO END AS maio,  	CASE WHEN JUNH IS NULL THEN '0.0' 		ELSE junh END AS junh, 	CASE WHEN julh IS NULL THEN '0.0'  		ELSE julh END AS julh, 	CASE WHEN agos IS NULL THEN '0.0'  		ELSE agos END AS agos, 	CASE WHEN sete IS NULL THEN '0.0'  		ELSE sete END AS sete, 	CASE WHEN outu IS NULL THEN '0.0'  		ELSE outu END AS outu, 	CASE WHEN nove IS NULL THEN '0.0' 		ELSE nove END AS nove,  	CASE WHEN deze IS NULL THEN '0.0' 		ELSE deze END AS deze FROM temp_graficoFaturamento where Ano = '"
					+ anoAtual + "' and Produto like '%" + tipo + "%' order by Tipo;";

			boolean repete = true;
			do {
				try {
					/* Faz um select simples na tabela temp_graficoFaturamento */
					ResultSet verifica = statement.executeQuery(verificaTabela);

					// se a tabela existir e NAO tiver dados
					if (!verifica.first()) {
						// insere dados na tabela
						System.out.println(new Date(System.currentTimeMillis())
								+ " Método selecionaDadosFaturamento: Inserindo dados na tabela...");

						/* ==================================== */

						/*
						 * ================= AUTO ================
						 */

						/* AUTO Tranquilo Exclusivo */
						String insereAutoTranqExcl2017 = "call `prcDadosFaturamento`('2017','Auto Tranquilo Exclusivo','3173,3174,3175')";
						statement.executeUpdate(insereAutoTranqExcl2017);
						String insereAutoTranqExcl2016 = "call `prcDadosFaturamento`('2016','Auto Tranquilo Exclusivo','3173,3174,3175')";
						statement.executeUpdate(insereAutoTranqExcl2016);
						String insereAutoTranqExcl2015 = "call `prcDadosFaturamento`('2015','Auto Tranquilo Exclusivo','3173,3174,3175')";
						statement.executeUpdate(insereAutoTranqExcl2015);

						/* AUTO Tranquilo Correntista */
						String insereAutoTranqCorren2017 = "call `prcDadosFaturamento`('2017','Auto Tranquilo Correntista','3176,3177,3178,3179,3180,3181')";
						statement.executeUpdate(insereAutoTranqCorren2017);
						String insereAutoTranqCorren2016 = "call `prcDadosFaturamento`('2016','Auto Tranquilo Correntista','3176,3177,3178,3179,3180,3181')";
						statement.executeUpdate(insereAutoTranqCorren2016);
						String insereAutoTranqCorren2015 = "call `prcDadosFaturamento`('2015','Auto Tranquilo Correntista','3176,3177,3178,3179,3180,3181')";
						statement.executeUpdate(insereAutoTranqCorren2015);

						/* AUTO Frota */
						String insereAutoFrota2017 = "call `prcDadosFaturamento`('2017','Auto Tranquilo Frota','3172')";
						statement.executeUpdate(insereAutoFrota2017);
						String insereAutoFrota2016 = "call `prcDadosFaturamento`('2016','Auto Tranquilo Frota','3172')";
						statement.executeUpdate(insereAutoFrota2016);
						String insereAutoFrota2015 = "call `prcDadosFaturamento`('2015','Auto Tranquilo Frota','3172')";
						statement.executeUpdate(insereAutoFrota2015);

						/* AUTO Facil */
						String insereAutoFacil2017 = "call `prcDadosFaturamento`('2017','Auto Fácil','5302,5303,5304')";
						statement.executeUpdate(insereAutoFacil2017);
						String insereAutoFacil2016 = "call `prcDadosFaturamento`('2016','Auto Fácil','5302,5303,5304')";
						statement.executeUpdate(insereAutoFacil2016);
						String insereAutoFacil2015 = "call `prcDadosFaturamento`('2015','Auto Fácil','5302,5303,5304')";
						statement.executeUpdate(insereAutoFacil2015);

						/*
						 * ================= RDPJ ================
						 */

						/* RDEquipamentos */
						String insereRdEquipamentos2017 = "call `prcDadosFaturamento`('2017','Rd Equipamentos','7114')";
						statement.executeUpdate(insereRdEquipamentos2017);
						String insereRdEquipamentos2016 = "call `prcDadosFaturamento`('2016','Rd Equipamentos','7114')";
						statement.executeUpdate(insereRdEquipamentos2016);
						String insereRdEquipamentos2015 = "call `prcDadosFaturamento`('2015','Rd Equipamentos','7114')";
						statement.executeUpdate(insereRdEquipamentos2015);

						/* EMPRESARIAL */
						String insereEmpresarial2017 = "call `prcDadosFaturamento`('2017','MR Empresarial','1801,1802,1804')";
						statement.executeUpdate(insereEmpresarial2017);
						String insereEmpresarial2016 = "call `prcDadosFaturamento`('2016','MR Empresarial','1801,1802,1804')";
						statement.executeUpdate(insereEmpresarial2016);
						String insereEmpresarial2015 = "call `prcDadosFaturamento`('2015','MR Empresarial','1801,1802,1804')";
						statement.executeUpdate(insereEmpresarial2015);

						/* Empresarial Prazo Curto */
						String insereEmpresarialPrazoCurto2017 = "call `prcDadosFaturamento`('2017','Empresarial Prazo Curto','1809')";
						statement.executeUpdate(insereEmpresarial2017);

						/* LOTERICO */
						String insereLoterico2017 = "call `prcDadosFaturamento_Loterico_CCA`('2017','LOTERICO','%Lote%','1803')";
						statement.executeUpdate(insereLoterico2017);
						String insereLoterico2016 = "call `prcDadosFaturamento_Loterico_CCA`('2016','LOTERICO','%Lote%','1803')";
						statement.executeUpdate(insereLoterico2016);
						String insereLoterico2015 = "call `prcDadosFaturamento_Loterico_CCA`('2015','LOTERICO','%Lote%','1803')";
						statement.executeUpdate(insereLoterico2015);

						/* CCA */
						String insereCca2017 = "call `prcDadosFaturamento_Loterico_CCA`('2017','CCA','%CCA%','1805')";
						statement.executeUpdate(insereCca2017);
						String insereCca2016 = "call `prcDadosFaturamento_Loterico_CCA`('2016','CCA','%CCA%','1805')";
						statement.executeUpdate(insereCca2016);
						String insereCca2015 = "call `prcDadosFaturamento_Loterico_CCA`('2015','CCA','%CCA%','1805')";
						statement.executeUpdate(insereCca2015);

						/*
						 * ================= RDPF ================
						 */

						/* Facil Residencial */
						String insereFacilRD2017 = "call `prcDadosFaturamento`('2017','Facil Residencial','1402,1405')";
						statement.executeUpdate(insereFacilRD2017);
						String insereFacilRD2016 = "call `prcDadosFaturamento`('2016','Facil Residencial','1402,1405')";
						statement.executeUpdate(insereFacilRD2016);
						String insereFacilRD2015 = "call `prcDadosFaturamento`('2015','Facil Residencial','1402,1405')";
						statement.executeUpdate(insereFacilRD2015);

						/* MR Residencial Correntista */
						String insereRdCorrentista2017 = "call `prcDadosFaturamentoRdCorrentista`('2017','MR Residencial Correntista')";
						statement.executeUpdate(insereRdCorrentista2017);
						String insereRdCorrentista2016 = "call `prcDadosFaturamentoRdCorrentista`('2016','MR Residencial Correntista')";
						statement.executeUpdate(insereRdCorrentista2016);
						String insereRdCorrentista2015 = "call `prcDadosFaturamentoRdCorrentista`('2015','MR Residencial Correntista')";
						statement.executeUpdate(insereRdCorrentista2015);

						/* MR Residencial Economiario */// exclusivo
						String insereRdExclusivo2017 = "call `prcDadosFaturamento`('2017','MR Residencial Economiario','1404')";
						statement.executeUpdate(insereRdExclusivo2017);
						String insereRdExclusivo2016 = "call `prcDadosFaturamento`('2016','MR Residencial Economiario','1404')";
						statement.executeUpdate(insereRdExclusivo2016);
						String insereRdExclusivo2015 = "call `prcDadosFaturamento`('2015','MR Residencial Economiario','1404')";
						statement.executeUpdate(insereRdExclusivo2015);

						/* Lar Mais */
						String insereLarMais2017 = "call `prcDadosFaturamento`('2017','Lar Mais','1409')";
						statement.executeUpdate(insereLarMais2017);
						String insereLarMais2016 = "call `prcDadosFaturamento`('2016','Lar Mais','1409')";
						statement.executeUpdate(insereLarMais2016);
						String insereLarMais2015 = "call `prcDadosFaturamento`('2015','Lar Mais','1409')";
						statement.executeUpdate(insereLarMais2015);

						/* MR Residencial Aporte Caixa */
						String insereAporte2017 = "call `prcDadosFaturamento`('2017','MR Residencial Aporte Caixa','1406,1408')";
						statement.executeUpdate(insereAporte2017);
						String insereAporte2016 = "call `prcDadosFaturamento`('2016','MR Residencial Aporte Caixa','1406,1408')";
						statement.executeUpdate(insereAporte2016);
						String insereAporte2015 = "call `prcDadosFaturamento`('2015','MR Residencial Aporte Caixa','1406,1408')";
						statement.executeUpdate(insereAporte2015);

						/* Minha Casa Minha Vida Mais Premiavel */
						String insereMCMVMaisPremiavel2017 = "call `prcDadosFaturamento`('2017','MCMV Mais Premiavel','1412')";
						statement.executeUpdate(insereMCMVMaisPremiavel2017);
						
						/* Cibrasec Securitizadora */
						String insereCibrasecSecuritizadora2017 = "call `prcDadosFaturamento`('2017','Cibrasec Securitizadora','1413')";
						statement.executeUpdate(insereCibrasecSecuritizadora2017);
						
						/* RD PF Outros */
						String insereRdPfOutros2017 = "call `prcDadosFaturamento`('2017','Rd Pf Outros','1002,1410,1411,1489,4002,7100,7101,7106,7107,7108,7109,7110,7117,7122,7123')";
						statement.executeUpdate(insereRdPfOutros2017);
						String insereRdPfOutros2016 = "call `prcDadosFaturamento`('2016','Rd Pf Outros','1002,1410,1411,1489,4002,7100,7101,7106,7107,7108,7109,7110,7117,7122,7123')";
						statement.executeUpdate(insereRdPfOutros2016);
						String insereRdPfOutros2015 = "call `prcDadosFaturamento`('2015','Rd Pf Outros','1002,1410,1411,1489,4002,7100,7101,7106,7107,7108,7109,7110,7117,7122,7123')";
						statement.executeUpdate(insereRdPfOutros2015);

						/*
						 * ============= AGRUPADOS ================
						 */
						/* AUTO */
						String insereAuto2017 = "call `prcDadosFaturamentoAutomovel`('2017','AUTOMOVEIS')";
						statement.executeUpdate(insereAuto2017);
						String insereAuto2016 = "call `prcDadosFaturamentoAutomovel`('2016','AUTOMOVEIS')";
						statement.executeUpdate(insereAuto2016);
						String insereAuto2015 = "call `prcDadosFaturamentoAutomovel`('2015','AUTOMOVEIS')";
						statement.executeUpdate(insereAuto2015);

						/* RD PJ */
						String insereRDPJ2017 = "call `prcDadosFaturamento_RDPJ`('2017','RDPJ')";
						statement.executeUpdate(insereRDPJ2017);
						String insereRDPJ2016 = "call `prcDadosFaturamento_RDPJ`('2016','RDPJ')";
						statement.executeUpdate(insereRDPJ2016);
						String insereRDPJ2015 = "call `prcDadosFaturamento_RDPJ`('2015','RDPJ')";
						statement.executeUpdate(insereRDPJ2015);

						/* RD PF */
						String insereRDPF2017 = "call `prcDadosFaturamento_RDPF`('2017','RDPF')";
						statement.executeUpdate(insereRDPF2017);
						String insereRDPF2016 = "call `prcDadosFaturamento_RDPF`('2016','RDPF')";
						statement.executeUpdate(insereRDPF2016);
						String insereRDPF2015 = "call `prcDadosFaturamento_RDPF`('2015','RDPF')";
						statement.executeUpdate(insereRDPF2015);

						/* DIRID */
						String insereDIRID2017 = "call `prcDadosFaturamentoDirid`('2017','Dirid')";
						statement.executeUpdate(insereDIRID2017);
						String insereDIRID2016 = "call `prcDadosFaturamentoDirid`('2016','Dirid')";
						statement.executeUpdate(insereDIRID2016);
						String insereDIRID2015 = "call `prcDadosFaturamentoDirid`('2015','Dirid')";
						statement.executeUpdate(insereDIRID2015);

						System.out.println(new Date(System.currentTimeMillis())
								+ " Método selecionaDadosFaturamento: ...dados inseridos.");
					}
					// Finaliza o laço "Do"
					repete = false;

					// Se a tabela temp_graficoFaturamento não existir no banco:
				} catch (SQLException e) {
					System.out.println("Exception: " + e);
					statement.executeUpdate(criaTabela); // cria a tabela
					System.out.println(new Date(System.currentTimeMillis())
							+ ": Método selecionaDadosFaturamento - Tabela criada.");
				}
			} while (repete);

			// Com a tabela temp_graficoFaturamento pronta, executa a consulta:
			ResultSet rs = null;
			rs = statement.executeQuery(sqlFinal);
			while (rs.next()) {
				FaturamentoVO fatuVO = new FaturamentoVO();
				String mesesAno[] = new String[12];

				fatuVO.setProduto(rs.getString(1));

				for (int i = 2; i <= 13; i++) {
					mesesAno[i - 2] = rs.getString(i);
				}
				fatuVO.setMeses(mesesAno);

				fatu.add(fatuVO);
			}

		} catch (SQLException SQLe) {
			SQLe.printStackTrace();
			System.out.println("ERRO metodo selecionaDadosFaturamento VisaoExecutivaDAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERRO metodo selecionaDadosFaturamento VisaoExecutivaDAO");
			}
		}

		return (List<FaturamentoVO>) fatu;
	}// fim selecionaDadosFaturamento

	/***************************************/
	/***************************************/
	/***************************************/

	/**
	 * Retorna APENAS o Faturamento sem RVNE do ano/produto passado por parametro O
	 * Retorno eh feito em milhoes e com varias casas decimais.Exemplo:
	 * '25011566.14000021'.
	 * 
	 * @return List<FaturamentoVO>
	 * 
	 * @arguments String
	 */
	public List<FaturamentoVO> selecionaFaturamentoTotal(String ano) {
		// Connection con = new Conexao().getConexaoMySqlSQLServer();

		List<FaturamentoVO> fatu = new ArrayList<FaturamentoVO>();
		Connection con = new Conexao().getConexaoMySql("selecionaFaturamentoTotal - VisaoExecutivaDAO");

		try {
			Statement statement = con.createStatement();

			String anoAtual = uteis.cortaRetornaAno(ano);

			String sqlFinal = "call prcFaturamentoTotal('" + anoAtual + "','%" + uteis.cortaRetornaProduto(ano)
					+ "%');";

			List<FaturamentoVO> listaRvne = selecionaRVNE(ano, 2, "0");

			ResultSet rs = null;

			rs = statement.executeQuery(sqlFinal);

			while (rs.next()) {
				FaturamentoVO fatuVO = new FaturamentoVO();
				String mesesAno[] = new String[12];

				fatuVO.setAno(uteis.cortaRetornaAno(ano));
				fatuVO.setProduto(rs.getString(1));

				for (int i = 2; i <= 13; i++) {

					mesesAno[i - 2] = Double.toString(Double.parseDouble(rs.getString(i))
							+ Double.parseDouble(listaRvne.get(0).getMeses()[i - 2]));
				}

				fatuVO.setMeses(mesesAno);
				fatu.add(fatuVO);
			}

		} catch (SQLException SQLe) {
			SQLe.printStackTrace();
			System.out.println("ERRO metodo selecionaFaturamentoTotal VisaoExecutivaDAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERRO metodo selecionaFaturamentoTotal VisaoExecutivaDAO");
			}
		}

		return (List<FaturamentoVO>) fatu;

	}// fim selecionaFaturamentoTotal
		// -----------------

}