package caixa.dirid.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import caixa.dirid.CONEXAO.Conexao;
import caixa.dirid.UTEIS.Uteis;
import caixa.dirid.VO.FatuMensalVO;
import caixa.dirid.VO.FaturamentoVO;
import caixa.dirid.VO.RvneVO;
import caixa.dirid.VO.VisoesVO;

public class VisoesDAO {
	Uteis uteis = new Uteis();

	/***********************************************************************************/
	/********** SELECT - Métodos de extração de dados das tabelas de negócio ***********/
	/***********************************************************************************/

	public List<VisoesVO> selecionaApolicesEmitidas() {

		Connection con = new Conexao()
				.getConexaoMySql("selecionaApolicesEmitidas - VisoesDAO");

		List<VisoesVO> apolicesEmitidas = new ArrayList<VisoesVO>();

		try {
			Statement statement = con.createStatement();

			/***************************/
			/* Query em T-SQL - SQLServer */
			// String sql =
			// "IF OBJECT_ID('tempdb..#temp_RO_RG') IS NOT NULL BEGIN     DROP TABLE #temp_RO_RG  END SELECT * INTO #temp_RO_RG FROM [dbo].[RO_RG0012B] WHERE ANOMES_REF = 201504 AND MOVIMENTO IN ('ENDOSSOS DE COBRANCA', 'APOLICE DE SEGURO', 'ENDOSSOS SEM MOVIMENTO') AND PROD IN (1406,1408,1409,1410,1803,1804,1805,3120,3133,3136,3138,3142,3143,3144,3145,3147,3148,3149,3172,3173,3174,3175,3176,3177,3178,3179,3180,3181,4000,4005,4006,4007,4505,4506,4507,4587,5103,5302,5303,5304,6701,7114,7123,7501,7587,7589,7590)/****************************************APOLICES EMITIDAS - SIAS******************************************/SELECT DISTINCT PROD,RAMO, COUNT(APOLICE) AS qtd FROM #temp_RO_RG WHERE PARC IN (0,1)AND RAMO IN (114,118,167,171,520,531,542,553)AND MOVIMENTO IN ('APOLICE DE SEGURO')GROUP BY PROD, RAMO ORDER BY PROD, RAMO";
			/***************************/

			/* Query em MySql */
			String tempTable = "CREATE TEMPORARY TABLE temporar_RO_RG AS (SELECT PROD, RAMO, APOLICE,MOVIMENTO FROM ro_rg0012b WHERE MOVIMENTO IN ('ENDOSSOS DE COBRANCA', 'APOLICE DE SEGURO', 'ENDOSSOS SEM MOVIMENTO') AND PROD IN (1406,1408,1409,1410,1803,1804,1805,3120,3133,3136,3138,3142,3143,3144,3145,3147,3148,3149,3172,3173,3174,3175,3176,3177,3178,3179,3180,3181,4000,4005,4006,4007,4505,4506,4507,4587,5103,5302,5303,5304,6701,7114,7123,7501,7587,7589,7590));";
			/***************************/

			String sqlQuery = "SELECT DISTINCT PROD,RAMO,COUNT(APOLICE) AS qtd FROM temporar_RO_RG WHERE RAMO IN (114,118,167,171,520,531,542,553) AND MOVIMENTO IN ('APOLICE DE SEGURO') GROUP BY PROD, RAMO ORDER BY PROD, RAMO;";

			int table = statement.executeUpdate(tempTable);
			System.out.println("Temporary table size: " + table);
			ResultSet rs = statement.executeQuery(sqlQuery);

			while (rs.next()) {
				VisoesVO VisoesVO = new VisoesVO();
				VisoesVO.setProd(rs.getString(1));
				VisoesVO.setRamo(rs.getString(2));
				VisoesVO.setQtd(rs.getString(3));
				// System.out.println("consultou");
				apolicesEmitidas.add(VisoesVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out
					.println("ERRO metodo selecionaApolicesEmitidas VisoesDAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out
						.println("ERRO metodo selecionaApolicesEmitidas VisoesDAO");
			}
		}
		return (List<VisoesVO>) apolicesEmitidas;
	}// fim selecionaApolicesEmitidas
		// -----------------

	/**
	 * No caso do parametro 'tipo' ser '1' retorna todo os dados da tabela
	 * 'rvne_gecof_tec' do ano atual. No caso do parametro 'tipo' ser '2'
	 * retorna todos os meses de 'RVNE_TOTAL' do produto e ano passados. No caso
	 * do parametro 'tipo' ser '3' retorna apenas o produto e o mês passado de
	 * 'rvne_gecof_tec'. No caso do parametro 'tipo' ser '4' retorna apenas o
	 * produto e a soma dos meses(o acumulado) de acordo com o parametro passado
	 * de 'rvne_gecof_tec'.
	 * 
	 * @return List<FaturamentoVO>
	 * 
	 * @arguments String, int, String
	 * 
	 */
	public List<FaturamentoVO> selecionaRVNE(String ano, int tipo, String mes) {

		Connection con = new Conexao()
				.getConexaoMySql("selecionaRVNE - VisoesDAO");

		switch (tipo) {
		case 1:// retorna todo o RVNE
			List listaRVNE = new ArrayList<RvneVO>();
			try {
				Statement statement = con.createStatement();

				String sqlQuery = "SELECT * FROM rvne_gecof_tec where ano = "
						+ ano;

				ResultSet rs = statement.executeQuery(sqlQuery);

				while (rs.next()) {
					RvneVO rvneVO = new RvneVO();
					rvneVO.setId(rs.getInt(1));
					rvneVO.setProduto(rs.getString(2));
					rvneVO.setAno(rs.getString(3));

					String mesesAno[] = new String[12];

					for (int i = 4; i <= 15; i++) {
						mesesAno[i - 4] = rs.getString(i);
					}
					rvneVO.setMeses(mesesAno);

					listaRVNE.add(rvneVO);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out
						.println("ERRO metodo selecionaRVNE VisoesDAO case 1");
			} finally {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
					System.out
							.println("ERRO metodo selecionaRVNE VisoesDAO case 1");
				}
			}

			return (List<FaturamentoVO>) listaRVNE;

		case 2: // retorna todos os meses de RVNE do produto e ano passados
			List<FaturamentoVO> fatu = new ArrayList<FaturamentoVO>();
			try {
				Statement statement = con.createStatement();
				String sqlQuery = "";

				if (uteis.cortaRetornaProduto(ano).equalsIgnoreCase(
						"AUTOMOVEIS")
						|| uteis.cortaRetornaProduto(ano).equalsIgnoreCase(
								"loterico")
						|| uteis.cortaRetornaProduto(ano).equalsIgnoreCase(
								"rdpj")
						|| uteis.cortaRetornaProduto(ano).equalsIgnoreCase(
								"rdpf")
						|| uteis.cortaRetornaProduto(ano).equalsIgnoreCase(
								"cca")) {

					sqlQuery = "SELECT 'RVNE' as Tipo, CASE WHEN janeiro*1000 IS NULL THEN '0' ELSE SUM(janeiro*1000) END,		CASE WHEN fevereiro*1000 IS NULL THEN '0' ELSE SUM(fevereiro*1000) END,		CASE WHEN março*1000 IS NULL THEN '0' ELSE SUM(março*1000) END,		CASE WHEN abril*1000 IS NULL THEN '0' ELSE SUM(abril*1000) END,		CASE WHEN maio*1000 IS NULL THEN '0' ELSE SUM(maio*1000) END,		CASE WHEN junho*1000 IS NULL THEN '0' ELSE SUM(junho*1000) END,		CASE WHEN julho*1000 IS NULL THEN '0' ELSE SUM(julho*1000) END,		CASE WHEN agosto*1000 IS NULL THEN '0' ELSE SUM(agosto*1000) END,		CASE WHEN setembro*1000 IS NULL THEN '0' ELSE SUM(setembro*1000) END,		CASE WHEN outubro*1000 IS NULL THEN '0' ELSE SUM(outubro*1000) END,		CASE WHEN novembro*1000 IS NULL THEN '0' ELSE SUM(novembro*1000) END,		CASE WHEN dezembro*1000 IS NULL THEN '0' ELSE SUM(dezembro*1000) END"
							+ " from rvne_total where"
							+ " produto like '%"
							+ uteis.cortaRetornaProduto(ano)
							+ "%' and ano = "
							+ uteis.cortaRetornaAno(ano) + ";";

				} else if (uteis.cortaRetornaProduto(ano).equals("Dirid")) {

					sqlQuery = "SELECT 'RVNE' AS Tipo,	CASE WHEN janeiro*1000 IS NULL THEN '0' ELSE SUM(janeiro*1000) END,		CASE WHEN fevereiro*1000 IS NULL THEN '0' ELSE SUM(fevereiro*1000) END,		CASE WHEN março*1000 IS NULL THEN '0' ELSE SUM(março*1000) END,		CASE WHEN abril*1000 IS NULL THEN '0' ELSE SUM(abril*1000) END,		CASE WHEN maio*1000 IS NULL THEN '0' ELSE SUM(maio*1000) END,		CASE WHEN junho*1000 IS NULL THEN '0' ELSE SUM(junho*1000) END,		CASE WHEN julho*1000 IS NULL THEN '0' ELSE SUM(julho*1000) END,		CASE WHEN agosto*1000 IS NULL THEN '0' ELSE SUM(agosto*1000) END,		CASE WHEN setembro*1000 IS NULL THEN '0' ELSE SUM(setembro*1000) END,		CASE WHEN outubro*1000 IS NULL THEN '0' ELSE SUM(outubro*1000) END,		CASE WHEN novembro*1000 IS NULL THEN '0' ELSE SUM(novembro*1000) END,		CASE WHEN dezembro*1000 IS NULL THEN '0' ELSE SUM(dezembro*1000) END FROM rvne_total WHERE Ano = "
							+ uteis.cortaRetornaAno(ano)
							+ "	and produto in ('AUTOMOVEIS','RDPJ','RDPF');";

				} else {

					sqlQuery = "SELECT 'RVNE' AS Tipo, CASE WHEN REPLACE(REPLACE(Janeiro, '.', ''),',','.') *1000 IS NULL THEN '0' ELSE REPLACE(REPLACE(Janeiro, '.', ''),',','.') *1000 END,    CASE WHEN REPLACE(REPLACE(Fevereiro, '.', ''),',','.')*1000 IS NULL THEN '0' ELSE REPLACE(REPLACE(Fevereiro, '.', ''),',','.')*1000 END,    CASE WHEN REPLACE(REPLACE(março, '.', ''),',','.')*1000 IS NULL THEN '0' ELSE REPLACE(REPLACE(março, '.', ''),',','.')*1000 END,    CASE WHEN REPLACE(REPLACE(abril, '.', ''),',','.')*1000 IS NULL THEN '0' ELSE REPLACE(REPLACE(abril, '.', ''),',','.')*1000 END,    CASE WHEN REPLACE(REPLACE(maio, '.', ''),',','.')*1000 IS NULL THEN '0' ELSE REPLACE(REPLACE(maio, '.', ''),',','.')*1000 END,    CASE WHEN REPLACE(REPLACE(Junho, '.', ''),',','.')*1000 IS NULL THEN '0' ELSE REPLACE(REPLACE(Junho, '.', ''),',','.')*1000 END,    CASE WHEN REPLACE(REPLACE(Julho, '.', ''),',','.')*1000 IS NULL THEN '0' ELSE REPLACE(REPLACE(Julho, '.', ''),',','.')*1000 END,    CASE WHEN REPLACE(REPLACE(Agosto, '.', ''),',','.')*1000 IS NULL THEN '0' ELSE REPLACE(REPLACE(Agosto, '.', ''),',','.')*1000 END,    CASE WHEN REPLACE(REPLACE(Setembro, '.', ''),',','.')*1000 IS NULL THEN '0' ELSE REPLACE(REPLACE(Setembro, '.', ''),',','.')*1000 END,    CASE WHEN REPLACE(REPLACE(Outubro, '.', ''),',','.')*1000 IS NULL THEN '0' ELSE REPLACE(REPLACE(Outubro, '.', ''),',','.')*1000 END,    CASE WHEN REPLACE(REPLACE(Novembro, '.', ''),',','.')*1000 IS NULL THEN '0' ELSE REPLACE(REPLACE(Novembro, '.', ''),',','.')*1000 END,    CASE WHEN REPLACE(REPLACE(Dezembro, '.', ''),',','.')*1000 IS NULL THEN '0' ELSE REPLACE(REPLACE(Dezembro, '.', ''),',','.')*1000 END  FROM rvne_gecof_tec  WHERE produto"
							+ " LIKE '%"
							+ uteis.cortaRetornaProduto(ano)
							+ "%' AND ano = "
							+ uteis.cortaRetornaAno(ano)
							+ ";";
				}
				ResultSet rs = statement.executeQuery(sqlQuery);

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
			} catch (SQLException e) {
				e.printStackTrace();
				System.out
						.println("ERRO metodo selecionaRVNE VisoesDAO case 2");
			} finally {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
					System.out
							.println("ERRO metodo selecionaRVNE VisoesDAO case 2");
				}
			}
			return (List<FaturamentoVO>) fatu;

		case 3:// retorna apenas o produto e o mês passado
			List listaRVNE2 = new ArrayList<RvneVO>();

			String produto = uteis.cortaRetornaProduto(ano);
			ano = uteis.cortaRetornaAno(ano);

			String mesParametro = uteis.mesesParaConsultas()[Integer
					.parseInt(mes) - 1];
			String order = produto.equals("mr") ? " and produto not like '%auto%' order by produto;"
					: " and produto like '%auto%' order by produto;";
			try {
				Statement statement = con.createStatement();

				String sqlQuery = "SELECT produto," + mesParametro
						+ " FROM rvne_gecof_tec where ano = " + ano + order;
				ResultSet rs = statement.executeQuery(sqlQuery);

				while (rs.next()) {
					RvneVO rvneVO = new RvneVO();

					rvneVO.setProduto(rs.getString(1));
					String mesesAno[] = new String[12];
					mesesAno[0] = rs.getString(2);

					rvneVO.setMeses(mesesAno);

					listaRVNE2.add(rvneVO);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out
						.println("ERRO metodo selecionaRVNE VisoesDAO case 3");
			} finally {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
					System.out
							.println("ERRO metodo selecionaRVNE VisoesDAO case 3");
				}
			}
			return (List<FaturamentoVO>) listaRVNE2;

		case 4:
			List listaRVNEAcumulada = new ArrayList<RvneVO>();

			String produtoAcumulada = uteis.cortaRetornaProduto(ano);
			ano = uteis.cortaRetornaAno(ano);

			String mesParametroAcumulada = "";
			String orderAcumulada = produtoAcumulada.equals("mr") ? " and produto not like '%auto%' order by produto;"
					: " and produto like '%auto%' order by produto;";
			try {
				Statement statement = con.createStatement();
				for (int i = 0; i < Integer.parseInt(mes); i++) {
					if (i == 0) {
						mesParametroAcumulada = "CASE WHEN "
								+ uteis.mesesParaConsultas()[i]
								+ " IS NULL THEN 0 ELSE "
								+ uteis.mesesParaConsultas()[i] + " END";
					} else {
						mesParametroAcumulada += "+CASE WHEN "
								+ uteis.mesesParaConsultas()[i]
								+ " IS NULL THEN 0 ELSE "
								+ uteis.mesesParaConsultas()[i] + " END";
					}
				}

				String sqlQuery = "SELECT produto,(" + mesParametroAcumulada
						+ ")as rvne FROM rvne_gecof_tec where ano = " + ano
						+ orderAcumulada;
				ResultSet rs = statement.executeQuery(sqlQuery);
				while (rs.next()) {
					RvneVO rvneVO = new RvneVO();

					rvneVO.setProduto(rs.getString(1));
					String mesesAno[] = new String[12];
					mesesAno[0] = rs.getString(2);

					rvneVO.setMeses(mesesAno);

					listaRVNEAcumulada.add(rvneVO);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out
						.println("ERRO metodo selecionaRVNE VisoesDAO case 4");
			} finally {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
					System.out
							.println("ERRO metodo selecionaRVNE VisoesDAO case 4");
				}
			}
			return (List<FaturamentoVO>) listaRVNEAcumulada;
		}
		return null;

	}// fim selecionaRVNE

	/**
	 * Recebe o objeto por parametro e executa o update na tabela
	 * rvne_gecof_tec.
	 * 
	 * @return void
	 * 
	 * @arguments RvneVO
	 */
	public void atualizaRVNE(RvneVO rvne) {
		Connection con = new Conexao()
				.getConexaoMySql("atualizaRVNE - VisoesDAO");
		String ano = uteis.dataAtual(1);
		String sql = null;
		PreparedStatement ps = null;

		try {
			switch (rvne.getMes()) {
			case "1":
				sql = "update rvne_gecof_tec set Janeiro = ?	where  id_RVNE = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, rvne.getValor());
				ps.setString(2, Integer.toString(rvne.getId()));
				ps.execute();
				break;

			case "2":
				sql = "update rvne_gecof_tec set Fevereiro = ? where  id_RVNE = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, rvne.getValor());
				ps.setString(2, Integer.toString(rvne.getId()));
				ps.execute();
				break;
			case "3":
				sql = "update rvne_gecof_tec set Março = ? where id_RVNE = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, rvne.getValor());
				ps.setString(2, Integer.toString(rvne.getId()));
				ps.execute();
				break;
			case "4":
				sql = "update rvne_gecof_tec set Abril = ? where id_RVNE = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, rvne.getValor());
				ps.setString(2, Integer.toString(rvne.getId()));
				ps.execute();
				break;
			case "5":
				sql = "update rvne_gecof_tec set Maio = ? where id_RVNE = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, rvne.getValor());
				ps.setString(2, Integer.toString(rvne.getId()));
				ps.execute();
				break;
			case "6":
				sql = "update rvne_gecof_tec set Junho = ? where id_RVNE = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, rvne.getValor());
				ps.setString(2, Integer.toString(rvne.getId()));
				ps.execute();
				break;
			case "7":
				sql = "update rvne_gecof_tec set Julho = ? where id_RVNE = ?";
				ps = con.prepareStatement(sql);
				System.out.println("aaaaaa " + rvne.getValor());
				ps.setString(1, rvne.getValor());
				ps.setString(2, Integer.toString(rvne.getId()));
				ps.execute();
				break;
			case "8":
				sql = "update rvne_gecof_tec set Agosto = ? where id_RVNE = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, rvne.getValor());
				ps.setString(2, Integer.toString(rvne.getId()));
				ps.execute();
				break;
			case "9":
				sql = "update rvne_gecof_tec set Setembro = ? where  id_RVNE = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, rvne.getValor());
				ps.setString(2, Integer.toString(rvne.getId()));
				System.out.println(ps.toString());
				ps.execute();
				break;
			case "10":
				sql = "update rvne_gecof_tec set Outubro = ? where  id_RVNE = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, rvne.getValor());
				ps.setString(2, Integer.toString(rvne.getId()));
				ps.execute();
				break;
			case "11":
				sql = "update rvne_gecof_tec set Novembro = ? where  id_RVNE = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, rvne.getValor());
				ps.setString(2, Integer.toString(rvne.getId()));
				ps.execute();
				break;
			case "12":
				sql = "update rvne_gecof_tec set Dezembro = ? where  id_RVNE = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, rvne.getValor());
				ps.setString(2, Integer.toString(rvne.getId()));
				ps.execute();
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERRO metodo atualizaRVNE VisoesDAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERRO metodo atualizaRVNE VisoesDAO");
			}
		}
	}// fim atualizaRVNE

	/**
	 * Recebe o objeto por parametro e busca apenas o RVNE do mês passado.
	 * Exemplo: Agosto - '-77,91319'
	 * 
	 * @return FatuMensalVO
	 * 
	 * @arguments RvneVO
	 */
	public FatuMensalVO buscaUnicaRVNE(RvneVO rvne) {
		Connection con = new Conexao()
				.getConexaoMySql("buscaUnicaRVNE - VisoesDAO");
		Uteis uteis = new Uteis();
		FatuMensalVO fatuMensalVo = new FatuMensalVO();
		try {
			Statement statement = con.createStatement();

			/* *************************** */
			/* Utilizando sintaxe MySql */
			/* *************************** */
			String sqlConsulta = "SELECT "
					+ uteis.mesesParaConsultas()[Integer
							.parseInt(rvne.getMes()) - 1]
					+ " from rvne_gecof_tec where id_RVNE = " + rvne.getId();

			ResultSet rs = null;
			rs = statement.executeQuery(sqlConsulta);
			rs.next();
			fatuMensalVo.setRVNE(rs.getString(1));
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERRO metodo buscaUnicaRVNE VisoesDAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERRO metodo buscaUnicaRVNE VisoesDAO");
			}
		}
		return fatuMensalVo;
	}// fim buscaUnicaRVNE

	/**
	 * Recebe o objeto com id e mês por parametro e executa o UPDATE para NULL
	 * na tabela rvne_gecof_tec.
	 * 
	 * @return void
	 * @throws SQLException
	 * 
	 * @arguments RvneVO
	 */
	public void apagaRVNE(RvneVO rvne) {
		Connection con = new Conexao().getConexaoMySql("apagaRVNE - VisoesDAO");
		String sql = null;
		PreparedStatement ps = null;
		try {
			switch (rvne.getMes()) {
			case "1":
				sql = "UPDATE rvne_gecof_tec set Janeiro=NULL where id_RVNE = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, Integer.toString(rvne.getId()));
				ps.execute();
				break;

			case "2":
				sql = "update rvne_gecof_tec set Fevereiro = NULL where id_RVNE = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, Integer.toString(rvne.getId()));
				ps.execute();
				break;

			case "3":
				sql = "update rvne_gecof_tec set Março = NULL where id_RVNE = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, Integer.toString(rvne.getId()));
				ps.execute();
				break;
			case "4":
				sql = "update rvne_gecof_tec set Abril = NULL where id_RVNE = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, Integer.toString(rvne.getId()));
				ps.execute();
				break;
			case "5":
				sql = "update rvne_gecof_tec set Maio = NULL where id_RVNE = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, Integer.toString(rvne.getId()));
				ps.execute();
				break;
			case "6":
				sql = "update rvne_gecof_tec set Junho = NULL where id_RVNE = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, Integer.toString(rvne.getId()));
				ps.execute();
				break;
			case "7":
				sql = "update rvne_gecof_tec set Julho = NULL where id_RVNE = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, Integer.toString(rvne.getId()));
				ps.execute();
				break;
			case "8":
				sql = "update rvne_gecof_tec set Agosto = NULL where id_RVNE = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, Integer.toString(rvne.getId()));
				ps.execute();
				break;
			case "9":
				sql = "update rvne_gecof_tec set Setembro = NULL where id_RVNE = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, Integer.toString(rvne.getId()));
				ps.execute();
				break;
			case "10":
				sql = "update rvne_gecof_tec set Outubro = NULL where id_RVNE = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, Integer.toString(rvne.getId()));
				ps.execute();
				break;
			case "11":
				sql = "update rvne_gecof_tec set Novembro = NULL where id_RVNE = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, Integer.toString(rvne.getId()));
				ps.execute();
				break;
			case "12":
				sql = "update rvne_gecof_tec set Dezembro = NULL where id_RVNE = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, Integer.toString(rvne.getId()));
				ps.execute();
				break;
			}
		} catch (SQLException SQLE) {
			SQLE.printStackTrace();
			System.out.println("ERRO metodo apagaRVNE VisoesDAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERRO metodo apagaRVNE VisoesDAO");
			}
		}
	}// fim apagaRVNE

}
