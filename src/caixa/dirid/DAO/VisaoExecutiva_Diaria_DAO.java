package caixa.dirid.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import caixa.dirid.CONEXAO.Conexao;
import caixa.dirid.UTEIS.Uteis;
import caixa.dirid.VO.DadosDiariosVO;
import caixa.dirid.VO.MovimentoPorCanalDeVendaVO;
import caixa.dirid.VO.FaturamentoRO_base_VO;
import caixa.dirid.VO.FaturamentoVO;
import caixa.dirid.VO.SinistroPendente_FaixaVO;
import caixa.dirid.VO.SinistroPendente_base_FaixaVO;
import caixa.dirid.VO.Sinistros_base_VO;

/**
 * Faz exatamente as mesmas coisas que a classe VisaoExecutivaBO faz, porem essa
 * classe trata da geracao dos dados diarios.
 *
 */
public class VisaoExecutiva_Diaria_DAO extends VisaoExecutivaDAO {

	/**
	 * Retorna lista contendo BP,EMITIDO,EMITIDOS CANCELADOS,EMITIDOS RESTITUIDOS,
	 * QT APOLICES EMITIDAS,QT SINISTROS AVISADOS,QT SINISTROS INDENIZADOS,RVNE
	 * SINISTROS AVISADOS,SINISTROS INDENIZADOS. O retorno é feito em milhoes e com
	 * varias casas decimais.
	 * 
	 * 
	 * @return List<FaturamentoVO>
	 * 
	 * @arguments String
	 */
	public List<FaturamentoVO> selecionaDadosFaturamento(String ano) {
		Connection con = new Conexao()
				.getConexaoMySql("selecionaDadosFaturamento - VisaoExecutiva_Diaria_DAO - " + ano);
		List<FaturamentoVO> fatu = new ArrayList<FaturamentoVO>();
		try {
			Statement statement = con.createStatement();

			// corta a string e pega o nome do parametro da var ano
			String tipo = uteis.cortaRetornaProduto(ano);
			String anoAtual = uteis.cortaRetornaAno(ano);

			String sqlFinal = "SELECT Tipo,  	CASE WHEN jane IS NULL THEN '0.0'  		ELSE jane END AS jane,     CASE WHEN feve IS NULL THEN '0.0'  		ELSE feve END AS feve, 	CASE WHEN marc IS NULL THEN '0.0' 		ELSE marc END AS marc, 	CASE WHEN abri IS NULL THEN '0.0'  		ELSE abri END AS abri, 	CASE WHEN maio IS NULL THEN '0.0'  		ELSE maiO END AS maio,  	CASE WHEN JUNH IS NULL THEN '0.0' 		ELSE junh END AS junh, 	CASE WHEN julh IS NULL THEN '0.0'  		ELSE julh END AS julh, 	CASE WHEN agos IS NULL THEN '0.0'  		ELSE agos END AS agos, 	CASE WHEN sete IS NULL THEN '0.0'  		ELSE sete END AS sete, 	CASE WHEN outu IS NULL THEN '0.0'  		ELSE outu END AS outu, 	CASE WHEN nove IS NULL THEN '0.0' 		ELSE nove END AS nove,  	CASE WHEN deze IS NULL THEN '0.0' 		ELSE deze END AS deze FROM temp_graficoFaturamento_diaria where Ano = '"
					+ anoAtual + "' and Produto like '%" + tipo + "%' order by Tipo;";

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
			System.out.println("ERRO metodo selecionaDadosFaturamento VisaoExecutiva_Diaria_DAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERRO metodo selecionaDadosFaturamento VisaoExecutiva_Diaria_DAO");
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

		List<FaturamentoVO> fatu = new ArrayList<FaturamentoVO>();
		Connection con = new Conexao()
				.getConexaoMySql("selecionaFaturamentoTotal - VisaoExecutiva_Diaria_DAO - " + ano);

		try {
			Statement statement = con.createStatement();

			String anoAtual = uteis.cortaRetornaAno(ano);

			String sqlFinal = "call prcFaturamentoTotal_diaria('" + anoAtual + "','%" + uteis.cortaRetornaProduto(ano)
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
			System.out.println("ERRO metodo selecionaFaturamentoTotal VisaoExecutiva_Diaria_DAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERRO metodo selecionaFaturamentoTotal VisaoExecutiva_Diaria_DAO");
			}
		}

		return (List<FaturamentoVO>) fatu;

	}// fim selecionaFaturamentoTotal
		// -----------------

	/**
	 * 
	 * 
	 * @return List<DadosDiariosVO>
	 * 
	 * @arguments String
	 */
	public List<DadosDiariosVO> selecionaDetalhesDadosDiarios(String ano, String mes) {

		List<DadosDiariosVO> listDados = new ArrayList<DadosDiariosVO>();

		String mesQuery = "";

		switch (mes) {
		case "01":
			mesQuery = "Jane";
			break;
		case "02":
			mesQuery = "feve";
			break;
		case "03":
			mesQuery = "marc";
			break;
		case "04":
			mesQuery = "abri";
			break;
		case "05":
			mesQuery = "maio";
			break;
		case "06":
			mesQuery = "junh";
			break;
		case "07":
			mesQuery = "julh";
			break;
		case "08":
			mesQuery = "agos";
			break;
		case "09":
			mesQuery = "sete";
			break;
		case "10":
			mesQuery = "outu";
			break;
		case "11":
			mesQuery = "nove";
			break;
		case "12":
			mesQuery = "deze";
			break;
		}
		// mesQuery = "jane";
		Connection con = new Conexao()
				.getConexaoMySql("selecionaDetalhesDadosDiarios - VisaoExecutiva_Diaria_DAO - " + ano);

		Calendar data = GregorianCalendar.getInstance();
		data.set(Integer.parseInt(ano), (Integer.parseInt(mes) - 1), 10);
		String sql = "select data_registro_historico as data_registro, produto, tipo, valor_do_dia from (select his.data_registro_historico, his.produto, his.tipo, IF (DAY(his.data_registro_historico) = "
				+ uteis.getPrimeiroDiaUtilDoMes(data, 3) + ",his." + mesQuery
				+ " , if( (@ultimoDia+INTERVAL 1 DAY = his.data_registro_historico) or (@ultimoDia+INTERVAL 2 DAY = his.data_registro_historico) or (@ultimoDia+INTERVAL 3 DAY = his.data_registro_historico)  or (@ultimoDia+INTERVAL 4 DAY = his.data_registro_historico)  or (@ultimoDia+INTERVAL 5 DAY = his.data_registro_historico)  or (@ultimoDia+INTERVAL 6 DAY = his.data_registro_historico)  or (@ultimoDia+INTERVAL 7 DAY = his.data_registro_historico), his."
				+ mesQuery
				+ " - @ultimoValor, 0.00)) as valor_do_dia, @ultimoDia := his.data_registro_historico, @ultimoValor := his."
				+ mesQuery
				+ " from historico_graficofaturamento_diaria his, (select @ultimoDia := null, @ultimoValor := null) var where ano = "
				+ ano + " AND MONTH(his.data_registro_historico) = " + mes
				+ " order by tipo,produto,data_registro_historico, ano ) as tot WHERE valor_do_dia <> 0 order by produto,tipo,data_registro;";
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			// System.out.println(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				DadosDiariosVO voDados = new DadosDiariosVO();

				voDados.setAnoMesDia(rs.getString(1));
				voDados.setProduto(rs.getString(2));
				voDados.setTipo(rs.getString(3));
				voDados.setValorDoDia(rs.getString(4));

				listDados.add(voDados);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("erro no metodo selecionaDetalhesDadosDiarios DAO diario");
		} finally {
			try {
				con.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listDados;
	}

	/**
	 * Busca a ultima e maior data entre as bases do RO_SIAS e do RO_SIES
	 * 
	 * @return String
	 * 
	 * @arguments String
	 */
	public String selecionaPeriodo(String ano) {
		Uteis util = new Uteis();
		String anoAtual = util.dataAtual(1);
		String mesAtual = util.dataAtual(2);
		String data = "";
		Connection con = new Conexao().getConexaoMySql("selecionaPeriodo - VisaoExecutiva_Diaria_DAO");
		String sql = "select max(maior_periodo)as maior_periodo from ( select concat(max(anomes_ref),lpad(max(dia),2,'0')) as maior_periodo from ro_sias_diaria where anomes_ref = ( select max(anomes_ref) as maior_periodo from ro_sias_diaria where anomes_ref like '"
				+ util.cortaRetornaAno(ano)
				+ "%')  union select concat( max(anomes_ref),lpad(day(max(dt_emissao)),2,'0')) as maior_periodo   from ro_sies_diaria where anomes_ref = (select max(anomes_ref) as maior_periodo from ro_sies_diaria where anomes_ref like '"
				+ util.cortaRetornaAno(ano) + "%') ) as t";
		PreparedStatement ps = null;
		// System.out.println("data " + sql);
		try {
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				data = rs.getString("maior_periodo");
			}

			if (data != null) {
				if (!(data.substring(0, 4).equalsIgnoreCase(anoAtual))) {
					data = "";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("erro no metodo selecionaPeriodo DAO diario");
		} finally {
			try {
				con.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return data;
	}

	/**
	 * Retorna uma lista em que cada linha possui o nome do produto, a Faixa de
	 * tempo ou de valor, a qtd Adm de sinistros, o valor Adm de sinist, a qtd Jud
	 * de sinistros, o valor Jud de sinist, a qtd Total, o valor Total. '.
	 * 
	 * @return List<SinistroPendente_FaixaVO>
	 * 
	 * @arguments String String
	 */
	public List<SinistroPendente_FaixaVO> selecionaSinistroPendente_Faixa(int tipo, String anoMes) {

		List<SinistroPendente_FaixaVO> listaSinistroPendente = new ArrayList<SinistroPendente_FaixaVO>();
		Connection con = new Conexao().getConexaoMySql("selecionaSinistroPendente_Faixa - VisaoExecutiva_Diaria_DAO");

		String dataAtual = uteis.dataAtual(4);
		String dataCut[] = dataAtual.split("/");
		String mes = dataCut[1];
		String ano = dataCut[2];
		String codigosProd = uteis.selecionaCodigosProduto_sinistros(uteis.cortaRetornaProduto(anoMes));

		try {
			Statement statement = con.createStatement();

			String sqlConsulta = "";

			switch (tipo) {
			case 1:// faixa tempo
				sqlConsulta = "call prcSinistroPendente_FaixaTempo('" + codigosProd + "','" + ano + mes + "');";
				break;
			case 2:// faixa valor
				sqlConsulta = "call prcSinistroPendente_FaixaValor('" + codigosProd + "','" + ano + mes + "');";
				break;
			default:
				System.out.println("Erro em metodo selecionaSinistroPendente_Faixa - VisaoExecutiva_Diaria_DAO");
				throw new SQLException();
			}
			// System.out.println(sqlConsulta);
			ResultSet rs = null;
			rs = statement.executeQuery(sqlConsulta);

			while (rs.next()) {
				SinistroPendente_FaixaVO sinistroPendenteVO = new SinistroPendente_FaixaVO();

				sinistroPendenteVO.setGrupo(rs.getString(1));
				sinistroPendenteVO.setFaixa(rs.getString(2));
				sinistroPendenteVO.setNumSinistrosPendentes_Administrativo(rs.getInt(3));
				sinistroPendenteVO.setValorSinistrosPendentes_Administrativo(rs.getString(4));
				sinistroPendenteVO.setNumSinistrosPendentes_Judicial(rs.getInt(5));
				sinistroPendenteVO.setValorSinistrosPendentes_Judicial(rs.getString(6));
				sinistroPendenteVO.setNumSinistrosPendentes_Total(rs.getInt(7));
				sinistroPendenteVO.setValorSinistrosPendentes_Total(rs.getString(8));

				listaSinistroPendente.add(sinistroPendenteVO);
			}
		} catch (SQLException SQLe) {
			SQLe.printStackTrace();
			System.out.println("ERRO metodo selecionaSinistroPendente_Faixa VisaoExecutiva_Diaria_DAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERRO metodo selecionaSinistroPendente_Faixa VisaoExecutiva_Diaria_DAO");
			}
		}

		return (List<SinistroPendente_FaixaVO>) listaSinistroPendente;

	}// fim selecionaSinistroPendente_FaixaTempo

	/**
	 * Retorna a base de sinistros pendentes.
	 * 
	 * @return List<SinistroPendente_base_FaixaVO>
	 * 
	 * @arguments String
	 */
	public List<SinistroPendente_base_FaixaVO> seleciona_Base_SinistroPendente_Faixa(String anoMes) {

		List<SinistroPendente_base_FaixaVO> lista_BASE_SinistroPendente = new ArrayList<SinistroPendente_base_FaixaVO>();
		Connection con = new Conexao()
				.getConexaoMySql("seleciona_Base_SinistroPendente_Faixa - VisaoExecutiva_Diaria_DAO");
		String dataAtual = uteis.dataAtual(4);
		String dataCut[] = dataAtual.split("/");
		String mes = dataCut[1];
		String ano = dataCut[2];

		String codigosProd = uteis.selecionaCodigosProduto_sinistros(uteis.cortaRetornaProduto(anoMes));

		try {
			Statement statement = con.createStatement();

			String sqlConsulta = "";

			sqlConsulta = "call prcSinistroPendente_Faixa_Completo('" + codigosProd + "','" + ano + mes + "');";

			ResultSet rs = null;
			rs = statement.executeQuery(sqlConsulta);

			while (rs.next()) {
				SinistroPendente_base_FaixaVO sinistroPendente_base_VO = new SinistroPendente_base_FaixaVO();

				sinistroPendente_base_VO.setLEGADO(rs.getString(1));
				sinistroPendente_base_VO.setANOMES_REF(rs.getString(2));
				sinistroPendente_base_VO.setORG(rs.getString(3));
				sinistroPendente_base_VO.setMOVIMENTO(rs.getString(4));
				sinistroPendente_base_VO.setRAMO(rs.getString(5));
				sinistroPendente_base_VO.setPROD(rs.getString(6));
				sinistroPendente_base_VO.setDIA(rs.getString(7));
				sinistroPendente_base_VO.setSINISTRO(rs.getString(8));
				sinistroPendente_base_VO.setAPOLICE(rs.getString(9));
				sinistroPendente_base_VO.setCOMUNICADO(rs.getString(10));
				sinistroPendente_base_VO.setOCORRENCIA(rs.getString(11));
				sinistroPendente_base_VO.setFAVORECIDO(rs.getString(12));
				sinistroPendente_base_VO.setVL_LIDER(rs.getString(13));
				sinistroPendente_base_VO.setVL_COSSEGURO(rs.getString(14));
				sinistroPendente_base_VO.setVL_RESSEGURO(rs.getString(15));
				sinistroPendente_base_VO.setVL_TOTAL(rs.getString(16));
				sinistroPendente_base_VO.setCOD_OPERACAO(rs.getString(17));
				sinistroPendente_base_VO.setOPERACAO(rs.getString(18));
				sinistroPendente_base_VO.setFTE_PREM(rs.getString(19));
				sinistroPendente_base_VO.setFTE_AVIS(rs.getString(20));
				sinistroPendente_base_VO.setAVISO(rs.getString(21));
				sinistroPendente_base_VO.setSEGURADO(rs.getString(22));
				sinistroPendente_base_VO.setCAUSA(rs.getString(23));
				sinistroPendente_base_VO.setGRUPO_CAUSA(rs.getString(24));
				sinistroPendente_base_VO.setGRUPO(rs.getString(25));
				sinistroPendente_base_VO.setTIPO(rs.getString(26));
				sinistroPendente_base_VO.setFAIXA_DE_VALOR(rs.getString(27));
				sinistroPendente_base_VO.setData_aviso(rs.getString(28));
				sinistroPendente_base_VO.setHoje(rs.getString(29));
				sinistroPendente_base_VO.setTEMPO_PENDENTE(rs.getString(30));
				sinistroPendente_base_VO.setFAIXA_TEMPO_PENDENTE(rs.getString(31));

				lista_BASE_SinistroPendente.add(sinistroPendente_base_VO);
			}

		} catch (SQLException SQLe) {
			SQLe.printStackTrace();
			System.out.println("ERRO metodo seleciona_Base_SinistroPendente_Faixa VisaoExecutiva_Diaria_DAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERRO metodo seleciona_Base_SinistroPendente_Faixa VisaoExecutiva_Diaria_DAO");
			}
		}

		return (List<SinistroPendente_base_FaixaVO>) lista_BASE_SinistroPendente;

	}// fim seleciona_Base_SinistroPendente_Faixa

	/**
	 * Retorna a base faturamentoRO.
	 * 
	 * @return List<FaturamentoRO_base_VO>
	 * 
	 * @arguments String
	 */
	public List<FaturamentoRO_base_VO> seleciona_Base_faturamentoRO(String anoMes) {

		List<FaturamentoRO_base_VO> lista_BASE_faturamentoRO = new ArrayList<FaturamentoRO_base_VO>();

		String codigosProd = uteis.selecionaCodigosProduto(uteis.cortaRetornaProduto(anoMes));

		try (Connection con = new Conexao()
				.getConexaoMySql("seleciona_Base_faturamentoRO - VisaoExecutiva_Diaria_DAO");) {
			Statement statement = con.createStatement();

			String sqlConsulta = "";

			sqlConsulta = "call prcBaseAnalitica_FaturamentoRO('" + codigosProd + "','" + uteis.cortaRetornaAno(anoMes)
					+ "');";

			ResultSet rs = null;
			rs = statement.executeQuery(sqlConsulta);

			while (rs.next()) {
				FaturamentoRO_base_VO faturamentoRO_base_VO = new FaturamentoRO_base_VO();

				faturamentoRO_base_VO.setLEGADO(rs.getString(1));
				faturamentoRO_base_VO.setANOMES_REF(rs.getString(2));
				faturamentoRO_base_VO.setORG(rs.getString(3));
				faturamentoRO_base_VO.setRAMO(rs.getString(4));
				faturamentoRO_base_VO.setPROD(rs.getString(5));
				faturamentoRO_base_VO.setMOVIMENTO(rs.getString(6));
				faturamentoRO_base_VO.setDIA(rs.getString(7));
				faturamentoRO_base_VO.setFONTE(rs.getString(8));
				faturamentoRO_base_VO.setPONTO_VENDA(rs.getString(9));
				faturamentoRO_base_VO.setAPOLICE(rs.getString(10));
				faturamentoRO_base_VO.setENDOSSO(rs.getString(11));
				faturamentoRO_base_VO.setENDS_CANC(rs.getString(12));
				faturamentoRO_base_VO.setDT_EMISSAO(rs.getString(13));
				faturamentoRO_base_VO.setINIC_VIG(rs.getString(14));
				faturamentoRO_base_VO.setTERM_VIG(rs.getString(15));
				faturamentoRO_base_VO.setCPF_CNPJ(rs.getString(16));
				faturamentoRO_base_VO.setSEGURADO(rs.getString(17));
				faturamentoRO_base_VO.setIMP_SEG(rs.getString(18));
				faturamentoRO_base_VO.setIS_COS(rs.getString(19));
				faturamentoRO_base_VO.setIS_RES(rs.getString(20));
				faturamentoRO_base_VO.setPREM_TARIFARIO(rs.getString(21));
				faturamentoRO_base_VO.setPREM_TARIFARIO_COS(rs.getString(22));
				faturamentoRO_base_VO.setPREM_TARIFARIO_RES(rs.getString(23));
				faturamentoRO_base_VO.setDESCONTO(rs.getString(24));
				faturamentoRO_base_VO.setDESCONTO_COS(rs.getString(25));
				faturamentoRO_base_VO.setDESCONTO_RES(rs.getString(26));
				faturamentoRO_base_VO.setPREM_LIQUIDO(rs.getString(27));
				faturamentoRO_base_VO.setPREM_LIQUIDO_COS(rs.getString(28));
				faturamentoRO_base_VO.setPREM_LIQUIDO_RES(rs.getString(29));
				faturamentoRO_base_VO.setADICIONAL(rs.getString(30));
				faturamentoRO_base_VO.setADICIONAL_COS(rs.getString(31));
				faturamentoRO_base_VO.setADICIONAL_RES(rs.getString(32));
				faturamentoRO_base_VO.setCUSTO(rs.getString(33));
				faturamentoRO_base_VO.setIOF(rs.getString(34));
				faturamentoRO_base_VO.setLIDER_COMISSAO(rs.getString(35));
				faturamentoRO_base_VO.setVLR_COMISS_COSSG(rs.getString(36));
				faturamentoRO_base_VO.setVLR_COMISS_RESSG(rs.getString(37));
				faturamentoRO_base_VO.setPREM_TOT(rs.getString(38));
				faturamentoRO_base_VO.setCOSS_PREMIO_TOTAL(rs.getString(39));
				faturamentoRO_base_VO.setRESS_PREMIO_TOTAL(rs.getString(40));
				faturamentoRO_base_VO.setCANAL_VENDA(rs.getString(41));
				faturamentoRO_base_VO.setUF(rs.getString(42));

				lista_BASE_faturamentoRO.add(faturamentoRO_base_VO);
			}
			rs.close();
		} catch (SQLException SQLe) {
			SQLe.printStackTrace();
			System.out.println("ERRO metodo seleciona_Base_faturamentoRO VisaoExecutiva_Diaria_DAO");
		}

		return (List<FaturamentoRO_base_VO>) lista_BASE_faturamentoRO;

	}// fim seleciona_Base_faturamentoRO

	/**
	 * Retorna a base faturamentoRO.
	 * 
	 * @return List<Sinistros_base_VO>
	 * 
	 * @arguments String
	 */
	public List<Sinistros_base_VO> seleciona_Base_sinistroCompleta(String anoMes) {

		List<Sinistros_base_VO> lista_BASE_faturamentoRO = new ArrayList<Sinistros_base_VO>();
		Connection con = new Conexao().getConexaoMySql("seleciona_Base_faturamentoRO - VisaoExecutiva_Diaria_DAO");

		String codigosProd = uteis.selecionaCodigosProduto(uteis.cortaRetornaProduto(anoMes));

		try {
			Statement statement = con.createStatement();

			String sqlConsulta = "";

			sqlConsulta = "call prcBaseAnalitica_Sinistros('" + codigosProd + "','" + uteis.cortaRetornaAno(anoMes)
					+ "');";

			ResultSet rs = null;
			rs = statement.executeQuery(sqlConsulta);

			while (rs.next()) {
				Sinistros_base_VO sinistro_base_VO = new Sinistros_base_VO();

				sinistro_base_VO.setLEGADO(rs.getString(1));
				sinistro_base_VO.setANOMES_REF(rs.getString(2));
				sinistro_base_VO.setORG(rs.getString(3));
				sinistro_base_VO.setMOVIMENTO(rs.getString(4));
				sinistro_base_VO.setRAMO(rs.getString(5));
				sinistro_base_VO.setPROD(rs.getString(6));
				sinistro_base_VO.setDIA(rs.getString(7));
				sinistro_base_VO.setSINISTRO(rs.getString(8));
				sinistro_base_VO.setAPOLICE(rs.getString(9));
				sinistro_base_VO.setCOMUNICADO(rs.getString(10));
				sinistro_base_VO.setOCORRENCIA(rs.getString(11));
				sinistro_base_VO.setFAVORECIDO(rs.getString(12));
				sinistro_base_VO.setVL_LIDER(rs.getString(13));
				sinistro_base_VO.setVL_COSSEGURO(rs.getString(14));
				sinistro_base_VO.setVL_RESSEGURO(rs.getString(15));
				sinistro_base_VO.setVL_TOTAL(rs.getString(16));
				sinistro_base_VO.setCOD_OPERACAO(rs.getString(17));
				sinistro_base_VO.setOPERACAO(rs.getString(18));
				sinistro_base_VO.setFTE_PREM(rs.getString(19));
				sinistro_base_VO.setFTE_AVIS(rs.getString(20));
				sinistro_base_VO.setAVISO(rs.getString(21));
				sinistro_base_VO.setSEGURADO(rs.getString(22));
				sinistro_base_VO.setCAUSA(rs.getString(23));
				sinistro_base_VO.setGRUPO_CAUSA(rs.getString(24));

				lista_BASE_faturamentoRO.add(sinistro_base_VO);
			}

		} catch (SQLException SQLe) {
			SQLe.printStackTrace();
			System.out.println("ERRO metodo seleciona_Base_sinistroCompleta VisaoExecutiva_Diaria_DAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERRO metodo seleciona_Base_sinistroCompleta VisaoExecutiva_Diaria_DAO");
			}
		}

		return (List<Sinistros_base_VO>) lista_BASE_faturamentoRO;

	}// fim seleciona_Base_sinistroCompleta

	public List<FaturamentoVO> testeSQLSERVER() {
		Connection con = new Conexao().getConexaoSQLServer();
		List<FaturamentoVO> fatu = new ArrayList<FaturamentoVO>();
		try {
			Statement statement = con.createStatement();

			// corta a string e pega o nome do parametro da var ano

			String sqlFinal = "select * from teste;";

			ResultSet rs = null;
			rs = statement.executeQuery(sqlFinal);
			while (rs.next()) {
				System.out.println(rs.getString(1));
			}

		} catch (SQLException SQLe) {
			SQLe.printStackTrace();
			System.out.println("ERRO metodo selecionaDadosFaturamento VisaoExecutiva_Diaria_DAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERRO metodo selecionaDadosFaturamento VisaoExecutiva_Diaria_DAO");
			}
		}

		return (List<FaturamentoVO>) fatu;
	}// fim selecionaDadosFaturamento

	/**
	 * 
	 * Retorna as emissoes ou cancelamentos agrupadas pelo canal de venda. Retorna
	 * duas extracoes unidas pelo UNION, as colunas retornadas sao: CANAL_VENDA,
	 * Produto, Tipo, Ano, e os meses de janeiro a dezembro. O valor das emissoes e
	 * a quantidade de emissoes esta unida e agrupada por canal de venda
	 * 
	 * @return List<movimentoporcanalDeVendaVO>
	 * 
	 * @arguments String
	 */
	public List<MovimentoPorCanalDeVendaVO> selecionaMovimentoPorCanal(String ano) {

		List<MovimentoPorCanalDeVendaVO> listaMovimentoCanal = new ArrayList<MovimentoPorCanalDeVendaVO>();
		Connection con = new Conexao()
				.getConexaoMySql("selecionaFaturamentoTotal - VisaoExecutiva_Diaria_DAO - " + ano);

		try {
			Statement statement = con.createStatement();

			String sqlFinal = "select movimento, canal_venda, Produto,    Tipo,    Ano,    Jane,    feve,    marc,    abri,    maio,    junh,    julh,    agos,    sete,    outu,    nove,    deze from temp_graficofaturamento_movimentoporcanal where produto = '"
					+ uteis.cortaRetornaProduto(ano) + "' and ano = " + uteis.cortaRetornaAno(ano) + ";";

			ResultSet rs = null;
			// System.out.println(sqlFinal);
			rs = statement.executeQuery(sqlFinal);

			while (rs.next()) {
				MovimentoPorCanalDeVendaVO movimentoVO = new MovimentoPorCanalDeVendaVO();
				String mesesAno[] = new String[12];

				movimentoVO.setMovimento(rs.getString(1));
				movimentoVO.setCanalDeVenda(rs.getString(2));
				movimentoVO.setProduto(rs.getString(3));
				movimentoVO.setTipo(rs.getString(4));
				movimentoVO.setAno(rs.getString(5));

				for (int i = 6; i <= 17; i++) {

					mesesAno[i - 6] = rs.getString(i);
				}

				movimentoVO.setMeses(mesesAno);
				listaMovimentoCanal.add(movimentoVO);
			}

		} catch (SQLException SQLe) {
			SQLe.printStackTrace();
			System.out.println("ERRO metodo selecionamovimentoporcanal VisaoExecutiva_Diaria_DAO");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERRO metodo selecionamovimentoporcanal VisaoExecutiva_Diaria_DAO");
			}
		}

		return (List<MovimentoPorCanalDeVendaVO>) listaMovimentoCanal;

	}

}