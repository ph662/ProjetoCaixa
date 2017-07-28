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
import caixa.dirid.VO.RvneVO;

public class VisaoAnaliticaDAO extends VisoesDAO {

	/**
	 * Utilizado na Visão Analítica, traz uma lista com os campos id, AnoMes,
	 * PRODUTO, Tipo, FaturamentoSemRVNE, RVNE, BP. Os valores não estão
	 * tratados, podem vir valores como este: '5869.749999999888'
	 * 
	 * O parâmetro 'mes' deve ir no formato '01'-'12'
	 * 
	 * @return List<FatuMensalVO>
	 * 
	 * @arguments String ano, String mes
	 */
	public List<FatuMensalVO> selecionaFatuMensal(String ano, String mes) {

		Connection con = new Conexao().getConexaoMySql("VisaoAnaliticaDAO - selecionaFatuMensal");
		List<FatuMensalVO> fatu = new ArrayList<FatuMensalVO>();
		Uteis uteis = new Uteis();

		try {
			Statement statement = con.createStatement();

			/* *************************** */
			/*   Utilizando sintaxe MySql  */
			/* *************************** */
			String verificaTabela = "SELECT anoMes FROM temp_faturamentoMensal where AnoMes ="
					+ uteis.cortaRetornaAno(ano)
					+ mes
					+ " AND Tipo LIKE '%"
					+ uteis.cortaRetornaProduto(ano) + "%';";

			String criaTabela = "CREATE TABLE temp_faturamentoMensal(  id int auto_increment primary key,  AnoMes VARCHAR(6),  PRODUTO VARCHAR(100), Tipo VARCHAR(10), FaturamentoSemRVNE VARCHAR(40),  RVNE VARCHAR(40),  BP VARCHAR(40)  );";

			String sqlConsulta = "SELECT DISTINCT * from temp_faturamentoMensal where anomes ="
					+ uteis.cortaRetornaAno(ano)
					+ mes
					+ " AND Tipo LIKE '%"
					+ uteis.cortaRetornaProduto(ano) + "%';";

			boolean repete = true;
			do {
				try {

					/* Faz um select simples na tabela temp_faturamentoMensal */
					ResultSet verifica = statement.executeQuery(verificaTabela);

					// se a tabela existir e NAO tiver dados
					if (!verifica.first()) {

						// insere dados na tabela
						String codigos = null;
						String casos = null;

						if (uteis.cortaRetornaProduto(ano).equals("auto")) {
							casos = "WHEN RORG.PROD IN (3173,3174,3175) THEN 'Auto Tranquilo Exclusivo'    		 WHEN RORG.PROD IN (3176,3177,3178,3179,3180,3181) THEN 'Auto Tranquilo Correntista'   		 WHEN RORG.PROD IN (3172) THEN 'Auto Tranquilo Frota'   		 WHEN RORG.PROD IN (5302,5303,5304) THEN 'Auto Fácil'  		 ELSE RORG.PROD ";
							codigos = "3173,3174,3175,3176,3177,3178,3179,3180,3181,3172,5302,5303,5304";
						} else if (uteis.cortaRetornaProduto(ano).equals("mr")) {
							casos = "WHEN RORG.PROD IN (1805) THEN 'MR Caixa Aqui'   		WHEN RORG.PROD IN (1801,1802,1804) THEN 'MR Empresarial'   		WHEN RORG.PROD IN (1803) THEN 'MR Lotérico'   		ELSE RORG.PROD ";
							codigos = "1801,1802,1804,1803,1805";
						}

						String insereAuto = "INSERT INTO sistemadirid.temp_faturamentoMensal (AnoMes,PRODUTO,Tipo,FaturamentoSemRVNE,RVNE,BP) SELECT DISTINCT  	ANOMES_REF AS AnoMes,  	CASE "
								+ casos
								+ " END AS PRODUTO     , '"
								+ uteis.cortaRetornaProduto(ano)
								+ "' as Tipo, SUM(	  		REPLACE(REPLACE(RORG.PREM_LIQUIDO, '.', ''), ',', '.')   	  + REPLACE(REPLACE(RORG.PREM_LIQUIDO_COS, '.', ''), ',', '.')  	  + REPLACE(REPLACE(RORG.PREM_LIQUIDO_RES, '.', ''), ',', '.')  	) AS FaturamentoSemRVNE  	, CASE WHEN rvne."
								+ uteis.mesesParaConsultas()[Integer
										.parseInt(mes) - 1]
								+ " IS NULL THEN '0.0' ELSE (REPLACE(REPLACE(rvne."
								+ uteis.mesesParaConsultas()[Integer
										.parseInt(mes) - 1]
								+ ", '.', ''), ',', '.')*1000) END as RVNE  	,CASE WHEN bp."
								+ uteis.mesesParaConsultas()[Integer
										.parseInt(mes) - 1]
								+ " IS NULL THEN '0.0'  		  ELSE REPLACE(REPLACE(bp."
								+ uteis.mesesParaConsultas()[Integer
										.parseInt(mes) - 1]
								+ ", '.', ''), ',', '.')  	END AS BP  FROM ro_rg0012b RORG  LEFT JOIN sistemadirid.codigos_bp_RVNE_2015 cod ON RORG.PROD = cod.PROD   left JOIN sistemadirid.BP_2015_DIRID bp ON cod.categProdBP = bp.Produtos  LEFT JOIN sistemadirid.rvne_gecof_tec rvne ON rvne.id_Rvne = cod.id_Rvne_"
								+ uteis.cortaRetornaAno(ano)
								+ "  WHERE RORG.ANOMES_REF = "
								+ uteis.cortaRetornaAno(ano)
								+ mes
								+ "  AND RORG.PROD IN ("
								+ codigos
								+ ")  GROUP BY PRODUTO  ORDER BY PRODUTO;";

						String verificaNaRoRG = "SELECT DISTINCT ANOMES_REF FROM sistemadirid.ro_rg0012b where ANOMES_REF = "
								+ uteis.cortaRetornaAno(ano) + mes + ";";

						/*
						 * o próximo insert se trata de uma consulta grande para
						 * depois inserir na "temp_faturamentoMensal", para
						 * evitar que essa consulta ocorra toda vez, utilizo o
						 * bloco try abaixo
						 */
						try {
							ResultSet rsRoRg = statement
									.executeQuery(verificaNaRoRG);
							if (rsRoRg.next()) {// se tiver na RORG insere
								// System.out.println("Inseriu - " +
								// insereAuto);
								statement.executeUpdate(insereAuto);
							}
						} catch (Exception e) {
							e.printStackTrace();
							System.out
									.println("ERRO método selecionaFatuMensal VisoesDAO");
						}
					}
					// Finaliza o laço "Do"
					repete = false;

					// Se a tabela temp_faturamentoMensal não existir no banco:
				} catch (SQLException sqle) {
					System.out.println("Exception: " + sqle);
					statement.executeUpdate(criaTabela); // cria a tabela
					System.out.println("Tabela criada.");
				}
			} while (repete);

			// Com a tabela temp_faturamentoMensal pronta, executa a consulta:

			// System.err.println(sqlConsulta);
			ResultSet rs = null;
			rs = statement.executeQuery(sqlConsulta);

			while (rs.next()) {
				FatuMensalVO fatuMensalVo = new FatuMensalVO();

				fatuMensalVo.setAnoMes(rs.getString(2));
				fatuMensalVo.setProduto(rs.getString(3));
				fatuMensalVo.setTipo(rs.getString(4));
				fatuMensalVo.setFaturamentoSemRVNE(rs.getString(5));
				fatuMensalVo.setRVNE(rs.getString(6));
				fatuMensalVo.setBusinessPlan(rs.getString(7));
				fatu.add(fatuMensalVo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERRO metodo selecionaFatuMensal VisoesDAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERRO metodo selecionaFatuMensal VisoesDAO");
			}
		}
		return (List<FatuMensalVO>) fatu;
	}// fim selecionaFatuMensal

	/**
	 * Utilizado na Visão Analítica, traz uma lista com os campos id, AnoMes,
	 * PRODUTO, Tipo, SOMA de FaturamentoSemRVNE, SOMA de RVNE, SOMA de BP. O
	 * valor é contabilizado desde o primeiro mês até o mês enviado pelo
	 * parâmetro. Ex: Between 201501 201505. Os valores não estão tratados,
	 * podem vir valores como este: '5869.749999999888'.
	 * 
	 * O parâmetro 'mes' deve ir no formato '01'-'12'
	 * 
	 * @return List<FatuMensalVO>
	 * 
	 * @arguments String ano, String mes
	 */
	public List<FatuMensalVO> selecionaFatuAcumulada(String ano, String mes) {

		Connection con = new Conexao().getConexaoMySql("VisaoAnaliticaDAO - selecionaFatuAcumulada");
		List<FatuMensalVO> fatu = new ArrayList<FatuMensalVO>();
		Uteis uteis = new Uteis();

		try {
			Statement statement = con.createStatement();

			/* *************************** */
			/* Utilizando sintaxe MySql */
			/* *************************** */

			String sqlConsulta = "SELECT DISTINCT id, PRODUTO, Tipo, Sum(FaturamentoSemRVNE), Sum(RVNE), Sum(BP), AnoMes from temp_faturamentoMensal where anomes BETWEEN "
					+ uteis.cortaRetornaAno(ano)
					+ "01 AND "
					+ uteis.cortaRetornaAno(ano)
					+ mes
					+ " AND Tipo LIKE '%"
					+ uteis.cortaRetornaProduto(ano) + "%' GROUP BY PRODUTO;";

			// Com a tabela temp_faturamentoMensal pronta, executa a consulta:
			ResultSet rs = null;
			// System.err.println("DAO metodo - selecionaFatuAcumulada "
			// + sqlConsulta);
			rs = statement.executeQuery(sqlConsulta);

			while (rs.next()) {
				FatuMensalVO fatuMensalVo = new FatuMensalVO();

				fatuMensalVo.setProduto(rs.getString(2));
				fatuMensalVo.setTipo(rs.getString(3));
				fatuMensalVo.setFaturamentoSemRVNE(rs.getString(4));
				fatuMensalVo.setRVNE(rs.getString(5));
				fatuMensalVo.setBusinessPlan(rs.getString(6));
				fatuMensalVo.setAnoMes(rs.getString(7));
				fatu.add(fatuMensalVo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERRO metodo selecionaFatuAcumulada VisoesDAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out
						.println("ERRO metodo selecionaFatuAcumulada VisoesDAO");
			}
		}
		return (List<FatuMensalVO>) fatu;
	}// fim selecionaFatuAcumulada

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
		Connection con = new Conexao().getConexaoMySql("VisaoAnaliticaDAO - apagaRVNE");
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
