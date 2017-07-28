package caixa.dirid.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import caixa.dirid.CONEXAO.Conexao;
import caixa.dirid.VO.CoberturasVO;
import caixa.dirid.VO.RelatorioAceitacaoVO;
import caixa.dirid.VO.RenovacaoVO;
import caixa.dirid.VO.SensibilizacaoTotalizadorVO;
import caixa.dirid.VO.SensibilizacaoVO;

public class VisaoOperacionalDAO extends VisoesDAO {

	/*******************************/
	/* Relatorio de RENOVACAO */
	/*******************************/

	/**
	 * Retorna uma lista contendo o quantitativo de apolices para analisar.
	 * Tabela 'renovacao_acompanhamento' do banco.
	 * 
	 * @return List<RenovacaoVO>
	 * 
	 * */

	public List<RenovacaoVO> selecionaAnaliseRenovacao(String mes, String ano) {
		Connection con = new Conexao()
				.getConexaoMySql("VisaoOperacionalDAO - selecionaAnaliseRenovacao");
		List<RenovacaoVO> fatu = new ArrayList<RenovacaoVO>();

		try {
			Statement statement = con.createStatement();

			String sqldadosRenovacao = "select 'Vicendos Para Renovar' as Produto, count(Apólice) as TotalGeral from renovacao_acompanhamento ra where Sit_Apolice = 'Emitida' and MONTH(fim_vig) = "
					+ mes
					+ " and YEAR(fim_vig) = "
					+ ano
					+ "  UNION select 'Emitidas sem prazo curto do empresarial' as Produto, sum(TotalGeral) as TotalGeral from  ( select 'Empresarial sem prazo curto' as Produto, count(Apólice) as TotalGeral from renovacao_acompanhamento ra where Sit_Apolice = 'Emitida' and produto = '1804' and MONTH(fim_vig) = "
					+ mes
					+ " and YEAR(fim_vig) = "
					+ ano
					+ " and plurianual = '1' union select 'Residencial' as Produto, count(Apólice) as TotalGeral from renovacao_acompanhamento ra where Sit_Apolice = 'Emitida' and MONTH(fim_vig) = "
					+ mes
					+ " and YEAR(fim_vig) = "
					+ ano
					+ " and produto in ('1403','1404') )as EmitidasSemPrazoCurto UNION select 'Cancelamentos' as Produto, count(Apólice) as TotalGeral from renovacao_acompanhamento ra where Sit_Apolice = 'Cancelada' and MONTH(fim_vig) = "
					+ mes
					+ " and YEAR(fim_vig) = "
					+ ano
					+ " UNION select 'Canceladas sem prazo curto do empresarial' as Produto, sum(TotalGeral) as TotalGeral from ( select 'Empresarial sem prazo curto' as Produto, count(Apólice) as TotalGeral from renovacao_acompanhamento ra where Sit_Apolice = 'Cancelada' and produto = '1804' and MONTH(fim_vig) = "
					+ mes
					+ " and YEAR(fim_vig) = "
					+ ano
					+ " and plurianual = '1' union select 'Residencial' as Produto, count(Apólice) as TotalGeral from renovacao_acompanhamento ra where Sit_Apolice = 'Cancelada' and MONTH(fim_vig) = "
					+ mes
					+ " and YEAR(fim_vig) = "
					+ ano
					+ " and produto in ('1403','1404') )as EmitidasSemPrazoCurto UNION select 'Propostas Geradas' as Produto, count(Apólice) as TotalGeral from renovacao_acompanhamento ra where Sit_Apolice = 'Emitida' and Renov_Canal in ('Renovação Automática','Renovação Mala Direta') and MONTH(fim_vig) = "
					+ mes
					+ " and YEAR(fim_vig) = "
					+ ano
					+ " UNION select 'Propostas não geradas - Analisar no SIES' as Produto, count(Apólice) as TotalGeral from renovacao_acompanhamento ra where Sit_Apolice = 'Emitida' and Renov_Canal NOT in ('Renovação Automática','Renovação Mala Direta') and MONTH(fim_vig) = "
					+ mes
					+ " and YEAR(fim_vig) = "
					+ ano
					+ " UNION select 'Emitidos' as Produtos, count(Apólice) as TotalGeral from renovacao_acompanhamento ra where Sit_Apolice = 'Emitida' and Renov_Canal in ('Renovação Automática','Renovação Mala Direta') and Vig_Canal NOT in('') and MONTH(fim_vig) = "
					+ mes
					+ " and YEAR(fim_vig) = "
					+ ano
					+ " UNION select 'Não Emitidos' as Produtos, count(Apólice) as TotalGeral from renovacao_acompanhamento ra where Sit_Apolice = 'Emitida' and Renov_Canal in ('Renovação Automática','Renovação Mala Direta') and Vig_Canal in ('') and MONTH(fim_vig) = "
					+ mes + " and YEAR(fim_vig) = " + ano + ";";

			System.err.println("metodo selecionaAnaliseRenovacao: "
					+ sqldadosRenovacao);

			ResultSet rsDadosRenovacao = null;

			rsDadosRenovacao = statement.executeQuery(sqldadosRenovacao);

			while (rsDadosRenovacao.next()) {
				RenovacaoVO renovacaoVo = new RenovacaoVO();
				renovacaoVo.setDescricao(rsDadosRenovacao.getString(1));
				renovacaoVo.setValor(rsDadosRenovacao.getString(2));
				fatu.add(renovacaoVo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out
					.println("ERRO metodo selecionaAnaliseRenovacao VisaoOperacionalDAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out
						.println("ERRO metodo selecionaAnaliseRenovacao VisaoOperacionalDAO");
			}
		}
		return (List<RenovacaoVO>) fatu;
	}

	/**
	 * Retorna uma lista contendo o quantitativo de apolices para analisar
	 * agrupado pelo codigo de Produto. Tabela 'renovacao_acompanhamento' do
	 * banco.
	 * 
	 * @return List<RenovacaoVO>
	 * 
	 * */

	public List<RenovacaoVO> selecionaAnaliseRenovacaoPorProduto(String mes) {
		Connection con = new Conexao()
				.getConexaoMySql("VisaoOperacionalDAO - selecionaAnaliseRenovacaoPorProduto");
		List<RenovacaoVO> fatu = new ArrayList<RenovacaoVO>();

		try {
			Statement statement = con.createStatement();

			String sqldadosRenovacao = "select Produto, 'Vincendos Para Renovar' as Descricao, count(Apólice) as TotalGeral from renovacao_acompanhamento ra where Sit_Apolice = 'Emitida' and MONTH(fim_vig) = "
					+ mes
					+ " group by Produto UNION select Produto, 'Emitidas sem prazo curto do empresarial' as Descricao, sum(TotalGeral) as TotalGeral from  ( select Produto, 'Empresarial sem prazo curto' as Descricao, count(Apólice) as TotalGeral from renovacao_acompanhamento ra where Sit_Apolice = 'Emitida' and Produto = '1804' and MONTH(fim_vig) = "
					+ mes
					+ " and plurianual = '1' union select Produto, 'Residencial' as Descricao, count(Apólice) as TotalGeral from renovacao_acompanhamento ra where Sit_Apolice = 'Emitida' and MONTH(fim_vig) = "
					+ mes
					+ " and Produto in ('1403','1404') )as EmitidasSemPrazoCurto group by Produto UNION select Produto, 'Cancelamentos' as Descricao, count(Apólice) as TotalGeral from renovacao_acompanhamento ra where Sit_Apolice = 'Cancelada' and MONTH(fim_vig) = "
					+ mes
					+ " group by Produto UNION select Produto, 'Canceladas sem prazo curto do empresarial' as Descricao, sum(TotalGeral) as TotalGeral from ( select Produto, 'Empresarial sem prazo curto' as Descricao, count(Apólice) as TotalGeral from renovacao_acompanhamento ra where Sit_Apolice = 'Cancelada' and Produto = '1804' and MONTH(fim_vig) = "
					+ mes
					+ " and plurianual = '1' union select Produto, 'Residencial' as Descricao, count(Apólice) as TotalGeral from renovacao_acompanhamento ra where Sit_Apolice = 'Cancelada' and MONTH(fim_vig) = "
					+ mes
					+ " and Produto in ('1403','1404') )as EmitidasSemPrazoCurto group by Produto UNION select Produto, 'Propostas Geradas' as Descricao, count(Apólice) as TotalGeral from renovacao_acompanhamento ra where Sit_Apolice = 'Emitida' and Renov_Canal in ('Renovação Automática','Renovação Mala Direta') and MONTH(fim_vig) = "
					+ mes
					+ " group by Produto UNION select Produto, 'Propostas não geradas - Analisar no SIES' as Descricao, count(Apólice) as TotalGeral from renovacao_acompanhamento ra where Sit_Apolice = 'Emitida' and Renov_Canal NOT in ('Renovação Automática','Renovação Mala Direta') and MONTH(fim_vig) = "
					+ mes
					+ " group by Produto UNION select Produto, 'Emitidos' as Descricao, count(Apólice) as TotalGeral from renovacao_acompanhamento ra where Sit_Apolice = 'Emitida' and Renov_Canal in ('Renovação Automática','Renovação Mala Direta') and Vig_Canal NOT in('') and MONTH(fim_vig) = "
					+ mes
					+ "  group by Produto UNION select Produto, 'Não Emitidos' as Descricao, count(Apólice) as TotalGeral from renovacao_acompanhamento ra where Sit_Apolice = 'Emitida' and Renov_Canal in ('Renovação Automática','Renovação Mala Direta') and Vig_Canal in ('') and MONTH(fim_vig) = "
					+ mes + " group by Produto;";

			System.err.println("@2 " + sqldadosRenovacao);
			ResultSet rsDadosRenovacao = null;

			rsDadosRenovacao = statement.executeQuery(sqldadosRenovacao);

			while (rsDadosRenovacao.next()) {
				RenovacaoVO renovacaoVo = new RenovacaoVO();
				renovacaoVo.setProduto(rsDadosRenovacao.getString(1));
				renovacaoVo.setDescricao(rsDadosRenovacao.getString(2));
				renovacaoVo.setValor(rsDadosRenovacao.getString(3));
				fatu.add(renovacaoVo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out
					.println("ERRO metodo selecionaAnaliseRenovacaoPorProduto VisaoOperacionalDAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out
						.println("ERRO metodo selecionaAnaliseRenovacaoPorProduto VisaoOperacionalDAO");
			}
		}
		return (List<RenovacaoVO>) fatu;
	}

	/**
	 * Retorna uma lista contendo o Canal e a quantidade de apolices das que
	 * foram geradas e emitidas. Tabela 'renovacao_acompanhamento' do banco.
	 * 
	 * @return List<RenovacaoVO>
	 * 
	 * */

	public List<RenovacaoVO> selecionaCanalApolicesEmitidas(String mes,
			String ano) {
		Connection con = new Conexao()
				.getConexaoMySql("VisaoOperacionalDAO - selecionaCanalApolicesEmitidas");
		List<RenovacaoVO> fatu = new ArrayList<RenovacaoVO>();

		try {
			Statement statement = con.createStatement();

			String sqldadosRenovacao = "select Vig_Canal, count(Apólice) as TotalGeral from renovacao_acompanhamento ra where Sit_Apolice = 'Emitida' and Renov_Canal in ('Renovação Automática','Renovação Mala Direta') and MONTH(fim_vig) = "
					+ mes
					+ " and YEAR(fim_vig) = "
					+ ano
					+ " and Vig_Canal NOT in('') group by Vig_Canal;";

			// System.err.println(sqlConsulta);
			ResultSet rsDadosRenovacao = null;

			rsDadosRenovacao = statement.executeQuery(sqldadosRenovacao);

			while (rsDadosRenovacao.next()) {
				RenovacaoVO renovacaoVo = new RenovacaoVO();
				renovacaoVo.setDescricao(rsDadosRenovacao.getString(1));
				renovacaoVo.setValor(rsDadosRenovacao.getString(2));
				fatu.add(renovacaoVo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out
					.println("ERRO metodo selecionaCanalApolicesEmitidas VisaoOperacionalDAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out
						.println("ERRO metodo selecionaCanalApolicesEmitidas VisaoOperacionalDAO");
			}
		}
		return (List<RenovacaoVO>) fatu;
	}

	/**
	 * 
	 * Teste - Pedido da Dani Retorna uma lista contendo a principio o Canal
	 * 'Renovação Automática' e logo abaixo o valor separado em outros canais.
	 * Em seguida retorna o canal 'Renovação Mala Direta' e abaixo o valor
	 * separado em outros canais. Tabela 'renovacao_acompanhamento' do banco.
	 * 
	 * @return List<RenovacaoVO>
	 * 
	 * */

	public List<RenovacaoVO> selecionaRenovacoesRealizadas(String mes,
			String ano) {
		Connection con = new Conexao()
				.getConexaoMySql("VisaoOperacionalDAO - selecionaRenovacoesRealizadas");
		List<RenovacaoVO> fatu = new ArrayList<RenovacaoVO>();

		try {
			Statement statement = con.createStatement();

			String sqldadosRenovacao = "/* ***** utiliza CAMPO VIG_SITUAÇÃO **** */select Renov_Canal, count(Apólice) as TotalGeral from renovacao_acompanhamento ra where  Vig_Situação = 'Emitida' and Renov_Canal in ('Renovação Automática') and MONTH(fim_vig) = "
					+ mes
					+ " and YEAR(fim_vig) = "
					+ ano
					+ " and Renov_Canal NOT in('') group by Renov_Canal union/* *****automatica***** */select Vig_Canal, count(Apólice) as TotalGeral from renovacao_acompanhamento ra where  Vig_Situação = 'Emitida' and Renov_Canal in ('Renovação Automática') and MONTH(fim_vig) = "
					+ mes
					+ " and YEAR(fim_vig) = "
					+ ano
					+ " and Renov_Canal NOT in('') group by Vig_Canal/* ***************************** *//* ********* */UNION/* ***************************** */select Renov_Canal, count(Apólice) as TotalGeral from renovacao_acompanhamento ra where Vig_Situação = 'Emitida' and Renov_Canal in ('Renovação Mala Direta') and MONTH(fim_vig) = "
					+ mes
					+ " and YEAR(fim_vig) = "
					+ ano
					+ " and Renov_Canal NOT in('') group by Renov_Canal union/* *****Mala Direta***** */select Vig_Canal, count(Apólice) as TotalGeral from renovacao_acompanhamento ra where Vig_Situação = 'Emitida' and Renov_Canal in ('Renovação Mala Direta') and MONTH(fim_vig) = "
					+ mes
					+ " and YEAR(fim_vig) = "
					+ ano
					+ " and Renov_Canal NOT in('') group by Vig_Canal;";

			System.err.println(sqldadosRenovacao);
			ResultSet rsDadosRenovacao = null;

			rsDadosRenovacao = statement.executeQuery(sqldadosRenovacao);

			while (rsDadosRenovacao.next()) {
				RenovacaoVO renovacaoVo = new RenovacaoVO();
				renovacaoVo.setDescricao(rsDadosRenovacao.getString(1));
				renovacaoVo.setValor(rsDadosRenovacao.getString(2));
				fatu.add(renovacaoVo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out
					.println("ERRO metodo selecionaRenovacoesRealizadas VisaoOperacionalDAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out
						.println("ERRO metodo selecionaRenovacoesRealizadas VisaoOperacionalDAO");
			}
		}
		return (List<RenovacaoVO>) fatu;
	}

	/************************************************/
	/* Relatorio de SENSIBILIZACAO */
	/************************************************/

	/**
	 * Retorna uma lista com 2 linhas, a primeira traz o somatório de todo o
	 * premio, a segunda traz a quantidade de apolices deste premio.
	 * 
	 * @return List<SensibilizacaoVO>
	 * @param String
	 *            prod
	 * @param String
	 *            ano
	 * @param String
	 *            mes
	 * @param String
	 *            categoria
	 * */

	public List<SensibilizacaoVO> selecionaTotalSensibilizacao(String prod,
			String ano, String mes, String categoria, String tipoMovimento) {
		Connection con = new Conexao()
				.getConexaoMySql("VisaoOperacionalDAO - selecionaTotalSensibilizacao");
		List<SensibilizacaoVO> fatu = new ArrayList<SensibilizacaoVO>();

		try {
			Statement statement = con.createStatement();

			String sqlDadosSensibilizacao = null;

			switch (categoria) {
			case "vendasNovas":

				sqlDadosSensibilizacao = "call prcSensibilizacaoDadosMesDeReferencia_vendasNovas('"
						+ ano + "','" + mes + "','" + prod + "')";

				break;
			case "fluxoFinanceiro":

				sqlDadosSensibilizacao = "call prcSensibilizacaoDadosMesDeReferencia_fluxoFinanceiro('"
						+ ano
						+ "','"
						+ mes
						+ "','"
						+ prod
						+ "','"
						+ tipoMovimento + "')";

				break;
			}

			// System.err.println("Query 1 -" + categoria + " "+
			// sqlDadosSensibilizacao);
			ResultSet rsDadosSensibilizacao = null;

			rsDadosSensibilizacao = statement
					.executeQuery(sqlDadosSensibilizacao);

			while (rsDadosSensibilizacao.next()) {
				SensibilizacaoVO sensibilizacaoVo = new SensibilizacaoVO();
				sensibilizacaoVo.setProduto(rsDadosSensibilizacao.getString(1));
				sensibilizacaoVo.setQuantidade(rsDadosSensibilizacao
						.getString(2));
				fatu.add(sensibilizacaoVo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out
					.println("ERRO metodo selecionaTotalSensibilizacao VisaoOperacionalDAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out
						.println("ERRO metodo selecionaTotalSensibilizacao VisaoOperacionalDAO");
			}
		}
		return (List<SensibilizacaoVO>) fatu;
	}// fim selecionaTotalSensibilizacao

	/**
	 * Retorna uma lista contendo a Agencia, SR, SUAT, quantidade de propostas e
	 * o valor dessas proposta.
	 * 
	 * @return List<SensibilizacaoVO>
	 * 
	 * */

	public List<SensibilizacaoVO> selecionaTotalAgrupadoPorAgenciaSensibilizacao(
			String prod, String ano, String mes, String categoria, int pagina) {
		Connection con = new Conexao()
				.getConexaoMySql("VisaoOperacionalDAO - selecionaTotalAgrupadoPorAgenciaSensibilizacao");
		List<SensibilizacaoVO> fatu = new ArrayList<SensibilizacaoVO>();

		String limit = "";
		if (pagina == 9999) {
			limit = " ";
		} else {
			limit = " LIMIT " + Integer.toString(((pagina - 1) * 1000))
					+ ",1000";
		}

		try {
			Statement statement = con.createStatement();

			String sqlDadosSensibilizacao = null;

			switch (categoria) {
			case "vendasNovas":
				sqlDadosSensibilizacao = "select case when em.agenciadevenda is null then 'Vazio' else em.agenciadevenda end as agenciadevenda, case when fi.agencia is null then 'Vazio' else fi.agencia end as agencia, case when em.NumeroProdutoSIGPF is null then 'Vazio' else em.NumeroProdutoSIGPF end as NumeroProdutoSIGPF,case when fi.cod_SR is null then 'Vazio' else fi.cod_SR end as cod_SR, case when fi.SUAT is null then 'Vazio' else fi.SUAT end as SUAT, count(distinct em.NumeroProposta) as Quantidade, round(sum(em.ValorPagoSICOB),2) as Total from movimentacao_emissao em left join agencias_filiais_sr_suat fi on em.AgenciaDeVenda = fi.cod_ag  where em.SituacaoProposta = 'EMT' and (em.NumeroProposta LIKE '8%' OR em.NumeroProposta LIKE '2%' OR em.NumeroProposta LIKE '4%') "
						+ prod
						+ " and MONTH(em.dataarquivo) = '"
						+ mes
						+ "' and year(em.dataArquivo) = "
						+ ano
						+ " group by em.agenciadevenda, em.NumeroProdutoSIGPF order by em.agenciadevenda, em.NumeroProdutoSIGPF desc "
						+ limit + ";";
				break;
			case "fluxoFinanceiro":
				sqlDadosSensibilizacao = "select case when AgenciaDeVenda is null then 'Vazio' else AgenciaDeVenda end as CodAgencia, case when Agencia is null then 'Vazio' else Agencia end as AgenciaDeVenda,  case when codigoProduto is null then 'Vazio' else codigoProduto end as codigoProduto, case when cod_sr is null then 'Vazio' else cod_sr end as SR, case when suat is null then 'Vazio' else suat end as suat, count(distinct numeroApolice) as qtApolice, round( sum( case codigosituacao when '242' then valorparcela * -1 when '251' then valorparcela * -1 when '250' then valorparcela when '235' then valorparcela end ),2 ) as valorPremio   from ( select * from ( select mv.DataArquivo, mv.NumeroApolice, av.AgenciaDeVenda,  fi.AGENCIA, fi.COD_SR, fi.SUAT, av.CodigoProduto, mv.CodigoSituacao, mv.ValorParcela from ( select DataArquivo, numeroproposta as NumeroApolice, CodigoSituacao, ValorParcela from movimentacao_financeira 		/* Traz apenas as Apolices*/ where numeroproposta like '120%' and month(dataArquivo) = "
						+ mes
						+ " and year(dataArquivo) = "
						+ ano
						+ " ) mv left join agencia_vigentes_apolices av on mv.NumeroApolice = av.NumeroApolice left join agencias_filiais_sr_suat fi on av.AgenciaDeVenda = fi.cod_ag UNION all select mv.DataArquivo, mv.NumeroProposta as NumeroApolice, av.AgenciaDeVenda, fi.AGENCIA, fi.COD_SR, fi.SUAT, av.CodigoProduto, mv.CodigoSituacao, mv.ValorParcela from ( select DataArquivo, numeroproposta as numeroproposta, CodigoSituacao, ValorParcela from movimentacao_financeira /* Traz apenas as Propostas*/ where NumeroProposta not like '120%' and month(dataArquivo) = "
						+ mes
						+ " and year(dataArquivo) = "
						+ ano
						+ "  				) mv   			left join agencia_vigentes_propostas av on mv.NumeroProposta = av.NumeroProposta 				left join agencias_filiais_sr_suat fi on av.AgenciaDeVenda = fi.cod_ag  		) as filtroProduto "
						+ prod
						+ " ) as financeiro group by agenciadevenda,codigoproduto "
						+ limit + ";";
				break;
			default:
				sqlDadosSensibilizacao = "select 'nada';";
				break;
			}

			System.err.println("Query 2 " + categoria + " "
					+ sqlDadosSensibilizacao);
			ResultSet rsDadosSensibilizacao = null;

			rsDadosSensibilizacao = statement
					.executeQuery(sqlDadosSensibilizacao);

			while (rsDadosSensibilizacao.next()) {
				SensibilizacaoVO sensibilizacaoVo = new SensibilizacaoVO();
				sensibilizacaoVo.setCodAgencia(rsDadosSensibilizacao
						.getString(1));
				sensibilizacaoVo.setAgencia(rsDadosSensibilizacao.getString(2));
				sensibilizacaoVo.setProduto(rsDadosSensibilizacao.getString(3));
				sensibilizacaoVo.setSR(rsDadosSensibilizacao.getString(4));
				sensibilizacaoVo.setSUAT(rsDadosSensibilizacao.getString(5));
				sensibilizacaoVo.setQuantidade(rsDadosSensibilizacao
						.getString(6));
				sensibilizacaoVo.setValor(rsDadosSensibilizacao.getString(7));

				fatu.add(sensibilizacaoVo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out
					.println("ERRO metodo selecionaTotalAgrupadoPorAgenciaSensibilizacao VisaoOperacionalDAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out
						.println("ERRO metodo selecionaTotalAgrupadoPorAgenciaSensibilizacao VisaoOperacionalDAO");
			}
		}
		return (List<SensibilizacaoVO>) fatu;
	}// fim selecionaTotalAgrupadoPorAgenciaSensibilizacao

	/**
	 * Retorna o período. Data Inicial e Data Final das informações.
	 * 
	 * @return List<SensibilizacaoVO>
	 * 
	 * */

	public List<SensibilizacaoVO> selecionaPeriodoSensibilizacao(String ano,
			String mes, String categoria) {
		Connection con = new Conexao()
				.getConexaoMySql("VisaoOperacionalDAO - selecionaPeriodoSensibilizacao");
		List<SensibilizacaoVO> fatu = new ArrayList<SensibilizacaoVO>();

		try {
			Statement statement = con.createStatement();
			String sqlPeriodoSensibilizacao = null;
			switch (categoria) {
			case "vendasNovas":
				sqlPeriodoSensibilizacao = "select min(dataarquivo) as minin, max(dataarquivo) as maxi from movimentacao_emissao where month(dataarquivo) = "
						+ mes + " and year(dataArquivo) = " + ano + ";";
				break;
			case "fluxoFinanceiro":
				sqlPeriodoSensibilizacao = "select min(dataarquivo) as minin,max(dataarquivo) as maxi from movimentacao_financeira where month(dataarquivo) = "
						+ mes + " and year(dataArquivo) = " + ano + ";";

				break;

			}

			ResultSet rsPeriodo = null;

			// System.err.println("Query 3 " + sqlPeriodoSensibilizacao);
			rsPeriodo = statement.executeQuery(sqlPeriodoSensibilizacao);

			rsPeriodo.next();
			if (rsPeriodo.getString(1) == null) {
				mes = Integer.toString((Integer.parseInt(mes) - 1));
				if (mes.equals("0")) {
					mes = "12";
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out
					.println("ERRO metodo selecionaPeriodoSensibilizacao VisaoOperacionalDAO");
		}

		try {
			Statement statement = con.createStatement();
			String sqlPeriodoSensibilizacao = null;
			switch (categoria) {
			case "vendasNovas":
				sqlPeriodoSensibilizacao = "select min(dataarquivo) as minin, max(dataarquivo) as maxi from movimentacao_emissao where month(dataarquivo) = "
						+ mes + " and year(dataArquivo) = " + ano + ";";
				break;
			case "fluxoFinanceiro":
				sqlPeriodoSensibilizacao = "select min(dataarquivo) as minin,max(dataarquivo) as maxi from movimentacao_financeira where month(dataarquivo) = "
						+ mes + " and year(dataArquivo) = " + ano + ";";

				break;

			}

			ResultSet rsPeriodo = null;

			// System.err.println("Query 4 " + sqlPeriodoSensibilizacao);
			// System.out.println("AQUI "+sqlPeriodoSensibilizacao);
			rsPeriodo = statement.executeQuery(sqlPeriodoSensibilizacao);

			while (rsPeriodo.next()) {
				SensibilizacaoVO sensibilizacaoVo = new SensibilizacaoVO();
				sensibilizacaoVo.setDataInicio(rsPeriodo.getString(1));
				sensibilizacaoVo.setDataFim(rsPeriodo.getString(2));
				fatu.add(sensibilizacaoVo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out
					.println("ERRO metodo selecionaPeriodoSensibilizacao VisaoOperacionalDAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out
						.println("ERRO metodo selecionaPeriodoSensibilizacao VisaoOperacionalDAO");
			}
		}
		return (List<SensibilizacaoVO>) fatu;
	}// fim selecionaPeriodoSensibilizacao

	/**
	 * Retorna o valor total de registros da mesma consulta do metodo
	 * selecionaTotalAgrupadoPorAgenciaSensibilizacao"
	 * 
	 * @return int
	 * 
	 * */
	public int selecionaQuantidadeRegistros(String prod, String ano,
			String mes, String categoria) {
		Connection con = new Conexao()
				.getConexaoMySql("VisaoOperacionalDAO - selecionaQuantidadeRegistros");

		int count = 0;
		try {
			Statement statement = con.createStatement();

			String sqlDadosSensibilizacao = null;

			switch (categoria) {

			case "vendasNovas":
				sqlDadosSensibilizacao = "select em.agenciadevenda,	em.NumeroProdutoSIGPF, fi.cod_SR,fi.SUAT, count(distinct em.NumeroProposta) as Quantidade, round(sum(em.ValorPagoSICOB),2) as Total from movimentacao_emissao em left join agencias_filiais_sr_suat fi on em.AgenciaDeVenda = fi.cod_ag  where em.SituacaoProposta = 'EMT' and (em.NumeroProposta LIKE '8%' OR em.NumeroProposta LIKE '2%' OR em.NumeroProposta LIKE '4%') "
						+ prod
						+ " and MONTH(em.dataarquivo) = '"
						+ mes
						+ "' and year(em.dataArquivo) = "
						+ ano
						+ " group by em.agenciadevenda, em.NumeroProdutoSIGPF order by em.agenciadevenda, em.NumeroProdutoSIGPF desc;";
				break;
			case "fluxoFinanceiro":
				sqlDadosSensibilizacao = "select case when AgenciaDeVenda is null then 'Vazio' else AgenciaDeVenda end as AgenciaDeVenda, case when codigoProduto is null then 'Vazio' else codigoProduto end as codigoProduto, case when cod_sr is null then 'Vazio' else cod_sr end as SR, case when suat is null then 'Vazio' else suat end as suat, count(distinct numeroApolice) as qtApolice, round( sum( case codigosituacao when '242' then valorparcela * -1 when '251' then valorparcela * -1 when '250' then valorparcela when '235' then valorparcela end ),2 ) as valorPremio   from ( select * from ( select mv.DataArquivo, mv.NumeroApolice, av.AgenciaDeVenda, fi.COD_SR, fi.SUAT, av.CodigoProduto, mv.CodigoSituacao, mv.ValorParcela from ( select DataArquivo, numeroproposta as NumeroApolice, CodigoSituacao, ValorParcela from movimentacao_financeira 		/* Traz apenas as Apolices*/ where numeroproposta like '120%' and month(dataArquivo) = "
						+ mes
						+ " and year(dataArquivo) = "
						+ ano
						+ " ) mv left join agencia_vigentes_apolices av on mv.NumeroApolice = av.NumeroApolice left join agencias_filiais_sr_suat fi on av.AgenciaDeVenda = fi.cod_ag UNION all select mv.DataArquivo, mv.NumeroProposta as NumeroApolice, av.AgenciaDeVenda, fi.COD_SR, fi.SUAT, av.CodigoProduto, mv.CodigoSituacao, mv.ValorParcela from ( select DataArquivo, numeroproposta as numeroproposta, CodigoSituacao, ValorParcela from movimentacao_financeira /* Traz apenas as Propostas*/ where NumeroProposta not like '120%' and month(dataArquivo) = "
						+ mes
						+ " and year(dataArquivo) = "
						+ ano
						+ "  				) mv   			left join agencia_vigentes_propostas av on mv.NumeroProposta = av.NumeroProposta 				left join agencias_filiais_sr_suat fi on av.AgenciaDeVenda = fi.cod_ag  		) as filtroProduto "
						+ prod
						+ " ) as financeiro group by agenciadevenda,codigoproduto;";
				break;
			default:
				break;
			}

			// System.err.println("Query 4 " + categoria + " "+
			// sqlDadosSensibilizacao);
			ResultSet rsDadosSensibilizacao = null;

			rsDadosSensibilizacao = statement
					.executeQuery(sqlDadosSensibilizacao);

			while (rsDadosSensibilizacao.next()) {
				++count;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out
					.println("ERRO metodo selecionaQuantidadeRegistros VisaoOperacionalDAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out
						.println("ERRO metodo selecionaQuantidadeRegistros VisaoOperacionalDAO");
			}
		}

		return count;
	}// fim selecionaQuantidadeRegistros

	/**
	 * Totalizador Acumulado.
	 * 
	 * @return List<SensibilizacaoVO>
	 * 
	 * */

	public List<SensibilizacaoTotalizadorVO> selecionaSensibilizacaoTotalizadorAcumulado(
			String ano) {
		Connection con = new Conexao()
				.getConexaoMySql("VisaoOperacionalDAO - selecionaSensibilizacaoTotalizadorAcumulado");
		List<SensibilizacaoTotalizadorVO> fatu = new ArrayList<SensibilizacaoTotalizadorVO>();

		try {
			Statement statement = con.createStatement();
			String sqlTotalizadorAcumulado = "call prcSensibilizacaoTotalizadorAcumulado('"
					+ uteis.cortaRetornaAno(ano) + "')";
			ResultSet rsTotalizador = statement
					.executeQuery(sqlTotalizadorAcumulado);

			while (rsTotalizador.next()) {
				SensibilizacaoTotalizadorVO sensiTotalizadorVo = new SensibilizacaoTotalizadorVO();
				sensiTotalizadorVo.setProduto(rsTotalizador.getString(1));
				String[] meses = new String[12];

				for (int i = 2; i < 14; i++) {
					meses[i - 2] = rsTotalizador.getString(i);
				}

				sensiTotalizadorVo.setMeses(meses);

				fatu.add(sensiTotalizadorVo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out
					.println("ERRO metodo selecionaSensibilizacaoTotalizadorAcumulado VisaoOperacionalDAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out
						.println("ERRO metodo selecionaSensibilizacaoTotalizadorAcumulado VisaoOperacionalDAO");
			}
		}
		return (List<SensibilizacaoTotalizadorVO>) fatu;
	}// fim selecionaPeriodoSensibilizacao

	/**
	 * Totalizador Mensal. Retorna (VENDAS NOVAS + FLUXO FINANCEIRO) o BP
	 * Empresarial de todos os meses, o realizado de todos os meses, o BP
	 * residencial de todos os meses e o realizado residencial de todos os meses
	 * 
	 * @return List<SensibilizacaoVO>
	 * 
	 * */

	public List<SensibilizacaoTotalizadorVO> selecionaSensibilizacaoTotalizadorMensal(
			String mes, String tipoMovimento, String anoParam) {
		Connection con = new Conexao()
				.getConexaoMySql("VisaoOperacionalDAO - selecionaSensibilizacaoTotalizadorMensal");
		List<SensibilizacaoTotalizadorVO> fatu = new ArrayList<SensibilizacaoTotalizadorVO>();

		if (tipoMovimento.equals("")) {
			tipoMovimento = "*"; // busca todos os movimentos
		}

		String dataAtual = new SimpleDateFormat("dd/MM/yyyy").format(new Date(
				System.currentTimeMillis()));
		String dataCut[] = dataAtual.split("/");
		String anoAtual = dataCut[2];

		try {
			Statement statement = con.createStatement();

			if (anoParam.equals(anoAtual)) {// se for ano atual

				/*
				 * aqui verifico se o mes anterior esta vazio, se estiver quer
				 * dizer que o mes virou e entao deve ser inserido o valor total
				 * do mes anterior Exemplo: estamos no mes 04, quando virar pro
				 * mes 05 o mes 04 ficara NULL na tabela
				 * temp_sensibilizacao_totalizador
				 */
				int mesAnterior = Integer.parseInt(mes) - 1;

				if (mesAnterior == 0) {
					mesAnterior = 12; // dezembro
				}

				String strMes = uteis.mesesParaConsultas()[mesAnterior - 1]
						.replace("ç", "c");
				String sqlVerificaMesAnterior = "select "
						+ strMes
						+ " from temp_sensibilizacao_totalizador where tipoMovimento = '"
						+ tipoMovimento + "' AND Ano = " + anoParam
						+ " limit 1,1;";

				// System.out.println(sqlVerificaMesAnterior);

				ResultSet verifica = statement
						.executeQuery(sqlVerificaMesAnterior);

				verifica.next();
				// se a consulta NAO tiver dados
				if (verifica.getString(1) == null) {
					System.out
							.println("Método selecionaSensibilizacaoTotalizadorMensal DAO - inserindo dados de "
									+ strMes + anoParam);

					// insere dados na tabela
					String insereSensibilizacaoMes = "call prcSensibilizacaoInsereDadosMesAnterior('"
							+ anoParam
							+ "','"
							+ strMes
							+ "','"
							+ mesAnterior
							+ "','" + tipoMovimento + "')";
					statement.executeUpdate(insereSensibilizacaoMes);

					// insere dados na tabela
					String insereSensibilizacaoMesRenovacao = "call prcSensibilizacaoInsereDadosMesAnterior('"
							+ anoParam
							+ "','"
							+ strMes
							+ "','"
							+ mesAnterior
							+ "','renovacao')";
					statement.executeUpdate(insereSensibilizacaoMesRenovacao);
				}

			}
			String sqlTotalizadorMensal = "call prcSensibilizacaoTotalizadorMensal('"
					+ anoParam + "','" + tipoMovimento + "')";

			ResultSet rsTotalizador = statement
					.executeQuery(sqlTotalizadorMensal);

			while (rsTotalizador.next()) {
				SensibilizacaoTotalizadorVO sensiTotalizadorVo = new SensibilizacaoTotalizadorVO();
				sensiTotalizadorVo.setProduto(rsTotalizador.getString(1));
				String[] meses = new String[12];

				for (int i = 2; i < 14; i++) {
					meses[i - 2] = rsTotalizador.getString(i);
				}

				sensiTotalizadorVo.setMeses(meses);

				fatu.add(sensiTotalizadorVo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out
					.println("ERRO metodo selecionaSensibilizacaoTotalizadorMensal VisaoOperacionalDAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out
						.println("ERRO metodo selecionaSensibilizacaoTotalizadorMensal VisaoOperacionalDAO");
			}
		}
		return (List<SensibilizacaoTotalizadorVO>) fatu;
	}// fim selecionaSensibilizacaoTotalizadorMensal

	/**
	 * Totalizador Mensal. Retorna o realizado EMPRESARIAL de todos os meses e o
	 * realizado residencial de todos os meses apenas do FLUXO FINANCEIRO
	 * 
	 * @return List<SensibilizacaoTotalizadorVO>
	 * 
	 * */

	public List<SensibilizacaoTotalizadorVO> selecionaFluxoFinanceiroTotalizadorMensal(
			String ano, String tipoMovimento) {
		Connection con = new Conexao()
				.getConexaoMySql("VisaoOperacionalDAO - selecionaFluxoFinanceiroTotalizadorMensal");
		List<SensibilizacaoTotalizadorVO> fatu = new ArrayList<SensibilizacaoTotalizadorVO>();

		if (tipoMovimento.equals("")) {
			tipoMovimento = "*"; // busca todos os movimentos
		}

		try {
			Statement statement = con.createStatement();

			String sqlTotalizadorMensal = "call prcSensibilizacaoFluxoFinanceiroTotalizadorMensal('"
					+ uteis.cortaRetornaAno(ano) + "','" + tipoMovimento + "')";

			ResultSet rsFluxoFinanceiro = statement
					.executeQuery(sqlTotalizadorMensal);

			while (rsFluxoFinanceiro.next()) {
				SensibilizacaoTotalizadorVO sensiTotalizadorVo = new SensibilizacaoTotalizadorVO();
				sensiTotalizadorVo.setProduto(rsFluxoFinanceiro.getString(1));

				String[] meses = new String[12];

				for (int i = 2; i < 14; i++) {
					meses[i - 2] = rsFluxoFinanceiro.getString(i);
				}

				sensiTotalizadorVo.setMeses(meses);

				fatu.add(sensiTotalizadorVo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out
					.println("ERRO metodo selecionaFluxoFinanceiroTotalizadorMensal VisaoOperacionalDAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out
						.println("ERRO metodo selecionaFluxoFinanceiroTotalizadorMensal VisaoOperacionalDAO");
			}
		}
		return (List<SensibilizacaoTotalizadorVO>) fatu;
	}// fim selecionaFluxoFinanceiroTotalizadorMensal

	/**********************************************/
	/* Relatorio de ACEITACAO */
	/**********************************************/
	/**
	 * Retorna lista de coberturas, tabela `coberturas_aceitacao`.
	 * 
	 * @return List<CoberturasVO>
	 * 
	 * */

	public List<CoberturasVO> selecionaCoberturas() {
		Connection con = new Conexao()
				.getConexaoMySql("selecionaCoberturas - visaoOperacionalDAO");
		List<CoberturasVO> cobe = new ArrayList<CoberturasVO>();
		try {
			Statement statement = con.createStatement();

			String query = "select * from rra_coberturas_aceitacao";

			ResultSet rsCoberturas = statement.executeQuery(query);

			while (rsCoberturas.next()) {
				CoberturasVO coberturas = new CoberturasVO();
				coberturas.setIdCobertura(rsCoberturas.getInt(1));
				coberturas.setCobertura(rsCoberturas.getString(2));
				coberturas.setFranquia(rsCoberturas.getString(3));
				cobe.add(coberturas);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.out
					.println("ERRO metodo selecionaCoberturas VisaoOperacionalDAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out
						.println("ERRO metodo selecionaCoberturas VisaoOperacionalDAO");
			}
		}
		return (List<CoberturasVO>) cobe;
	}

	/**
	 * Retorna lista com dados de relarórios que já foram gerados`.
	 * 
	 * @return List<CoberturasVO>
	 * 
	 * */

	public List<RelatorioAceitacaoVO> selecionaRelatoriosSalvos(
			String numAceitacao) {
		Connection con = new Conexao()
				.getConexaoMySql("selecionaRelatoriosSalvos - visaoOperacionalDAO");
		List<RelatorioAceitacaoVO> cobe = new ArrayList<RelatorioAceitacaoVO>();
		try {
			Statement statement = con.createStatement();

			String query = "";
			if (numAceitacao != null) {
				// select para os casos da pag 'relatorioRA.jsp'
				query = "select * from rra_relatorio_aceitacao where numero_aceitacao = "
						+ numAceitacao;
			} else {
				query = "select * from rra_relatorio_aceitacao";
			}
			ResultSet rsCoberturas = statement.executeQuery(query);

			while (rsCoberturas.next()) {
				RelatorioAceitacaoVO raVO = new RelatorioAceitacaoVO();
				raVO.setNumeroAceitacao(Integer.toString(rsCoberturas.getInt(1)));
				raVO.setNumeroProposta(rsCoberturas.getString(2));
				raVO.setSegurado(rsCoberturas.getString(3));
				raVO.setLocalRisco(rsCoberturas.getString(4));
				raVO.setCpf(rsCoberturas.getString(5));
				raVO.setAtividadePrincipal(rsCoberturas.getString(6));
				raVO.setInicioVig(rsCoberturas.getString(7));
				raVO.setFimVig(rsCoberturas.getString(8));
				raVO.setIs(rsCoberturas.getString(9));
				raVO.setPremioLiq(rsCoberturas.getString(10));
				raVO.setPremioNet(rsCoberturas.getString(11));
				raVO.setPremioRetido(rsCoberturas.getString(12));
				raVO.setPremioCedido(rsCoberturas.getString(13));
				raVO.setLimiteSinistro(rsCoberturas.getString(14));
				raVO.setPartResseg(rsCoberturas.getString(15));
				raVO.setPartCaixa(rsCoberturas.getString(16));
				raVO.setParecerTecnico(rsCoberturas.getString(17));
				raVO.setDataSaida(rsCoberturas.getString(18));
				raVO.setDataEntrega(rsCoberturas.getString(19));
				raVO.setStatuscheck(rsCoberturas.getInt(20));
				cobe.add(raVO);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.out
					.println("ERRO metodo selecionaRelatoriosSalvos VisaoOperacionalDAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out
						.println("ERRO metodo selecionaRelatoriosSalvos VisaoOperacionalDAO");
			}
		}
		return (List<RelatorioAceitacaoVO>) cobe;
	}

	/**
	 * Retorna lista com coberturas dos relarórios que já foram gerados`.
	 * 
	 * @return List<CoberturasVO>
	 * 
	 * */

	public List<CoberturasVO> selecionaCoberturasRelatoriosSalvos(
			String numAceitacao) {
		Connection con = new Conexao()
				.getConexaoMySql("selecionaCoberturasRelatoriosSalvos - visaoOperacionalDAO");
		List<CoberturasVO> cobe = new ArrayList<CoberturasVO>();
		try {
			Statement statement = con.createStatement();

			String query = "";
			if (numAceitacao != null) {
				query = "select  fk_numero_aceitacao,  		valorLMI,          cobertura,          franquias,fk_id_coberturas_aceitacao as idCobertura  from rra_coberturas_aceitacao ca  inner join rra_relatorio_aceitacao_coberturas rac   on ca.id_cobertura = rac.fk_id_coberturas_aceitacao where fk_numero_aceitacao = "
						+ numAceitacao + ";";
			} else {
				query = "select  fk_numero_aceitacao,  		valorLMI,          cobertura,          franquias,fk_id_coberturas_aceitacao as idCobertura  from rra_coberturas_aceitacao ca  inner join rra_relatorio_aceitacao_coberturas rac   on ca.id_cobertura = rac.fk_id_coberturas_aceitacao;";
			}
			ResultSet rsCoberturas = statement.executeQuery(query);

			while (rsCoberturas.next()) {
				CoberturasVO cobVO = new CoberturasVO();

				cobVO.setNumAceitacao(Integer.toString(rsCoberturas.getInt(1)));
				cobVO.setLMI(rsCoberturas.getString(2));
				cobVO.setCobertura(rsCoberturas.getString(3));
				cobVO.setFranquia(rsCoberturas.getString(4));
				cobVO.setIdCobertura(Integer.parseInt(rsCoberturas.getString(5)));

				cobe.add(cobVO);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.out
					.println("ERRO metodo selecionaCoberturasRelatoriosSalvos VisaoOperacionalDAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out
						.println("ERRO metodo selecionaCoberturasRelatoriosSalvos VisaoOperacionalDAO");
			}
		}
		return (List<CoberturasVO>) cobe;
	}

	/**
	 * Retorna lista com coberturas dos relarórios que já foram gerados`.
	 * 
	 * @return List<CoberturasVO>
	 * 
	 * */

	public List<CoberturasVO> atualizaDataStatus(String dado, int numAceit,
			String coluna) {
		Connection con = new Conexao()
				.getConexaoMySql("atualizaDataStatus - visaoOperacionalDAO");
		List<CoberturasVO> cobe = new ArrayList<CoberturasVO>();
		String sql = "";
		PreparedStatement ps = null;
		try {
			sql = "update rra_relatorio_aceitacao set " + coluna
					+ "= ? where numero_aceitacao = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, dado);
			ps.setInt(2, numAceit);
			ps.execute();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.out
					.println("ERRO metodo atualizaDataStatus VisaoOperacionalDAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out
						.println("ERRO metodo atualizaDataStatus VisaoOperacionalDAO");
			}
		}
		return (List<CoberturasVO>) cobe;
	}

	/**
	 * Grava dados do relatorio de aceitacao.
	 * 
	 * 
	 */
	public String insereDadosRelatorioAceitacao(
			RelatorioAceitacaoVO relatorioAce) {
		Connection con = new Conexao()
				.getConexaoMySql("insereDadosRelatorioAceitacao - visaoOperacionalDAO");
		List<CoberturasVO> cobe = new ArrayList<CoberturasVO>();
		PreparedStatement ps = null;
		PreparedStatement psCobertura = null;
		String idAceitacao = "";
		try {
			String insertRA = "INSERT INTO rra_relatorio_aceitacao (numero_proposta,segurado,local_risco,CPF_CNPJ,atividade_principal,"
					+ "inicio_vigencia,fim_vigencia,imp_Seg, premio_Liq,premio_Net,premio_Ret,premio_Ced,limite_Sinistro, "
					+ "part_Resseg,part_CaixaSeg,parecer_tecnico,status_checkbox) VALUES(?,?,?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,?,?,?,?,?,?,0);";
			ps = (PreparedStatement) con.prepareStatement(insertRA);
			ps.setString(1, relatorioAce.getNumeroProposta());
			ps.setString(2, relatorioAce.getSegurado());
			ps.setString(3, relatorioAce.getLocalRisco());
			ps.setString(4, relatorioAce.getCpf());
			ps.setString(5, relatorioAce.getAtividadePrincipal());
			ps.setString(6, relatorioAce.getInicioVig());
			ps.setString(7, relatorioAce.getFimVig());
			ps.setDouble(8, Double.parseDouble(relatorioAce.getIs()));
			ps.setDouble(9, Double.parseDouble(relatorioAce.getPremioLiq()));
			ps.setDouble(10, Double.parseDouble(relatorioAce.getPremioNet()));
			ps.setDouble(11, Double.parseDouble(relatorioAce.getPremioRetido()));
			ps.setDouble(12, Double.parseDouble(relatorioAce.getPremioCedido()));
			ps.setDouble(13,
					Double.parseDouble(relatorioAce.getLimiteSinistro()));
			ps.setDouble(14, Double.parseDouble(relatorioAce.getPartResseg()));
			ps.setDouble(15, Double.parseDouble(relatorioAce.getPartCaixa()));
			ps.setString(16, relatorioAce.getParecerTecnico());
			ps.execute();

			idAceitacao = selecionaMaiorNumeroAceitacao();

			String insertCobertura = "";
			for (int i = 0; i < relatorioAce.getCobertura().size(); i++) {
				String part[] = relatorioAce.getCobertura().get(i).split(";");
				insertCobertura = "INSERT INTO rra_relatorio_aceitacao_Coberturas(fk_numero_aceitacao,fk_id_coberturas_aceitacao,valorLMI ) VALUES ("
						+ idAceitacao + "," + part[0] + "," + part[2] + ")";
				psCobertura = (PreparedStatement) con
						.prepareStatement(insertCobertura);
				// System.out.println(insertCobertura);
				psCobertura.execute();
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.out
					.println("ERRO metodo insereDadosRelatorioAceitacao VisaoOperacionalDAO");
		} finally {
			try {
				con.close();
				ps.close();
				psCobertura.close();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				System.out
						.println("ERRO metodo insereDadosRelatorioAceitacao VisaoOperacionalDAO");
			} catch (Exception e) {
				e.printStackTrace();
				System.out
						.println("ERRO metodo insereDadosRelatorioAceitacao VisaoOperacionalDAO");
			}
		}
		return idAceitacao;
	}

	/**
	 * Altera dados do relatorio de aceitacao se baseando no numero de
	 * Aceitação(id).
	 * 
	 * 
	 */
	public String alteraDadosRelatorioAceitacao(
			RelatorioAceitacaoVO relatorioAce) {
		Connection con = new Conexao()
				.getConexaoMySql("alteraDadosRelatorioAceitacao - visaoOperacionalDAO");
		List<CoberturasVO> cobe = new ArrayList<CoberturasVO>();
		PreparedStatement ps = null;
		PreparedStatement psCobertura = null;
		String idAceitacao = "";
		try {
			String alteraRA = "UPDATE rra_relatorio_aceitacao set numero_proposta = ?, segurado = ?, local_risco = ?, CPF_CNPJ = ?,"
					+ " atividade_principal = ?,inicio_vigencia = STR_TO_DATE(?,'%d/%m/%Y'), fim_vigencia = STR_TO_DATE(?,'%d/%m/%Y'), imp_Seg = ?, premio_Liq = ?, premio_Net = ?,"
					+ " premio_Ret = ?, premio_Ced = ?, limite_Sinistro = ?,part_Resseg = ?, part_CaixaSeg = ?, parecer_tecnico = ? "
					+ " where numero_aceitacao = ?";
			ps = con.prepareStatement(alteraRA);
			ps.setString(1, relatorioAce.getNumeroProposta());
			ps.setString(2, relatorioAce.getSegurado());
			ps.setString(3, relatorioAce.getLocalRisco());
			ps.setString(4, relatorioAce.getCpf());
			ps.setString(5, relatorioAce.getAtividadePrincipal());
			ps.setString(6, relatorioAce.getInicioVig());
			ps.setString(7, relatorioAce.getFimVig());
			ps.setDouble(8, Double.parseDouble(relatorioAce.getIs()));
			ps.setDouble(9, Double.parseDouble(relatorioAce.getPremioLiq()));
			ps.setDouble(10, Double.parseDouble(relatorioAce.getPremioNet()));
			ps.setDouble(11, Double.parseDouble(relatorioAce.getPremioRetido()));
			ps.setDouble(12, Double.parseDouble(relatorioAce.getPremioCedido()));
			ps.setDouble(13,
					Double.parseDouble(relatorioAce.getLimiteSinistro()));
			ps.setDouble(14, Double.parseDouble(relatorioAce.getPartResseg()));
			ps.setDouble(15, Double.parseDouble(relatorioAce.getPartCaixa()));
			ps.setString(16, relatorioAce.getParecerTecnico());
			ps.setString(17, relatorioAce.getNumeroAceitacao());
			ps.execute();

			idAceitacao = relatorioAce.getNumeroAceitacao();

			String deleteCoberturas = "DELETE FROM rra_relatorio_aceitacao_Coberturas WHERE fk_numero_aceitacao = "
					+ idAceitacao;
			psCobertura = con.prepareStatement(deleteCoberturas);
			// System.out.println(deleteCoberturas);
			psCobertura.execute();

			String insertCobertura = "";
			for (int i = 0; i < relatorioAce.getCobertura().size(); i++) {
				String part[] = relatorioAce.getCobertura().get(i).split(";");
				insertCobertura = "INSERT INTO rra_relatorio_aceitacao_Coberturas(fk_numero_aceitacao,fk_id_coberturas_aceitacao,valorLMI ) VALUES ("
						+ idAceitacao + "," + part[0] + "," + part[2] + ")";
				psCobertura = con.prepareStatement(insertCobertura);
				// System.out.println(insertCobertura);
				psCobertura.execute();
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.out
					.println("ERRO metodo alteraDadosRelatorioAceitacao VisaoOperacionalDAO");
		} finally {
			try {
				con.close();
				ps.close();
				psCobertura.close();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				System.out
						.println("ERRO metodo alteraDadosRelatorioAceitacao VisaoOperacionalDAO");
			} catch (Exception e) {
				e.printStackTrace();
				System.out
						.println("ERRO metodo alteraDadosRelatorioAceitacao VisaoOperacionalDAO");
			}
		}
		return idAceitacao;
	}

	/**
	 * Retorna o maior NUMERO_ACEITACAO(id) da tabela relatorio_aceitacao.
	 * 
	 * @return String
	 * 
	 * */
	public String selecionaMaiorNumeroAceitacao() {
		Connection con = new Conexao()
				.getConexaoMySql("selecionaMaiorNumeroAceitacao - visaoOperacionalDAO");
		String numeroAceitacao = "";
		try {
			Statement statement = con.createStatement();

			String query = "select max(numero_aceitacao) from rra_relatorio_aceitacao;";

			ResultSet rsCoberturas = statement.executeQuery(query);

			rsCoberturas.next();
			numeroAceitacao = rsCoberturas.getString(1);

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			System.out
					.println("ERRO metodo selecionaMaiorNumeroAceitacao VisaoOperacionalDAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out
						.println("ERRO metodo selecionaMaiorNumeroAceitacao VisaoOperacionalDAO");
			}
		}
		return numeroAceitacao;
	}

}
