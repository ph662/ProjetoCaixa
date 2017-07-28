package caixa.dirid.BO;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import caixa.dirid.DAO.VisaoOperacionalDAO;
import caixa.dirid.UTEIS.Uteis;
import caixa.dirid.VO.CoberturasVO;
import caixa.dirid.VO.RelatorioAceitacaoVO;
import caixa.dirid.VO.RenovacaoVO;
import caixa.dirid.VO.SensibilizacaoTotalizadorVO;
import caixa.dirid.VO.SensibilizacaoVO;

public class VisaoOperacionalBO extends VisoesBO {
	Uteis uti = new Uteis();
	DecimalFormat percentForm = new DecimalFormat("0.0%");

	/**************************/
	/* Relatorio de RENOVACAO */
	/**************************/

	/**
	 * Retorna uma lista contendo o quantitativo de apolices para analisar. E a
	 * soma de alguns destes valores.
	 *
	 * @return List<RenovacaoVO>
	 * 
	 * */
	public List<RenovacaoVO> validaSelecionaAnaliseRenovacao(String mes,
			String ano) {
		VisaoOperacionalDAO dao = new VisaoOperacionalDAO();
		List<RenovacaoVO> lista = dao.selecionaAnaliseRenovacao(mes, ano);
		List<RenovacaoVO> listaTratada = new ArrayList<RenovacaoVO>();

		int totalGeral = Integer.parseInt(lista.get(0).getValor())
				+ Integer.parseInt(lista.get(2).getValor());

		int totalGeralSemPrazoCurto = Integer.parseInt(lista.get(1).getValor())
				+ Integer.parseInt(lista.get(3).getValor());

		RenovacaoVO totalGeralVo = new RenovacaoVO();
		totalGeralVo.setDescricao("Vincendos - M&ecirc;s Refer&ecirc;ncia");
		totalGeralVo.setValor(uti.insereSeparadores(Integer
				.toString(totalGeral)));

		RenovacaoVO totalGeralSemPrazoCurtoVo = new RenovacaoVO();
		totalGeralSemPrazoCurtoVo
				.setDescricao("Vincendos - M&ecirc;s Refer&ecirc;ncia - Sem Prazo Curto");
		totalGeralSemPrazoCurtoVo.setValor(uti.insereSeparadores(Integer
				.toString(totalGeralSemPrazoCurto)));

		listaTratada.add(totalGeralVo);
		listaTratada.add(totalGeralSemPrazoCurtoVo);

		for (int i = 0; i < lista.size(); i++) {
			RenovacaoVO renovacaoVo = new RenovacaoVO();
			renovacaoVo.setDescricao(lista.get(i).getDescricao());
			renovacaoVo
					.setValor(uti.insereSeparadores(lista.get(i).getValor()));
			listaTratada.add(renovacaoVo);
		}

		BigDecimal nGeradas = new BigDecimal(listaTratada.get(7).getValor());
		RenovacaoVO indiceFalhaProposta = new RenovacaoVO();
		indiceFalhaProposta
				.setDescricao("&Iacute;ndice de Falha na Gera&ccedil;&atilde;o da Proposta");

		if (listaTratada.get(7).getValor().equals("0")
				|| listaTratada.get(2).getValor().equals("0")) {
			indiceFalhaProposta.setValor("0%");
		} else {
			// System.out.println(listaTratada.get(2).getValor().replace(".",
			// ""));
			indiceFalhaProposta.setValor(percentForm.format(Double
					.parseDouble((nGeradas.divide(
							new BigDecimal(listaTratada.get(2).getValor()
									.replace(".", "").replace(",", ".")), 4,
							RoundingMode.HALF_DOWN).toString()))));
		}
		listaTratada.add(indiceFalhaProposta);

		RenovacaoVO indiceRenovacao = new RenovacaoVO();

		BigDecimal emitidas = new BigDecimal(listaTratada.get(8).getValor()
				.replace(".", "").replace(",", "."));

		indiceRenovacao
				.setDescricao("&Iacute;ndice de Renova&ccedil;&atilde;o");

		if (listaTratada.get(7).getValor().equals("0")
				|| listaTratada.get(2).getValor().equals("0")) {
			indiceRenovacao.setValor("0%");
		} else {
			indiceRenovacao.setValor(percentForm.format(Double
					.parseDouble((emitidas.divide(
							new BigDecimal(listaTratada.get(6).getValor()
									.replace(".", "").replace(",", ".")), 4,
							RoundingMode.HALF_DOWN).toString()))));
		}
		listaTratada.add(indiceRenovacao);
		return listaTratada;
	}

	/**
	 * Retorna uma lista contendo o quantitativo de apolices para analisar
	 * AGRUPADO POR Produto 14 e 18. E a soma de alguns destes valores.
	 *
	 * @return List<RenovacaoVO>
	 * 
	 * */
	public List<RenovacaoVO> validaSelecionaAnaliseRenovacaoPorProduto(
			String mes) {
		VisaoOperacionalDAO dao = new VisaoOperacionalDAO();
		List<RenovacaoVO> lista = dao.selecionaAnaliseRenovacaoPorProduto(mes);
		List<RenovacaoVO> listaTratada = new ArrayList<RenovacaoVO>();
		List<RenovacaoVO> listaFinal = new ArrayList<RenovacaoVO>();

		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getProduto().contains("14")
					&& lista.get(i + 1).getProduto().contains("14")) {
				if (lista.get(i).getDescricao()
						.equalsIgnoreCase(lista.get(i + 1).getDescricao())) {

					RenovacaoVO renovacaoVo = new RenovacaoVO();
					renovacaoVo.setProduto("14");
					renovacaoVo.setDescricao(lista.get(i).getDescricao());
					renovacaoVo
							.setValor(uti.insereSeparadores(Integer
									.toString((Integer.parseInt(lista.get(i)
											.getValor()) + Integer
											.parseInt(lista.get(i + 1)
													.getValor())))));

					listaTratada.add(renovacaoVo);
					i++;
				}
			} else {
				RenovacaoVO renovacaoVo = new RenovacaoVO();
				if (lista.get(i).getProduto().contains("14")) {

					renovacaoVo.setProduto("14");
					renovacaoVo.setDescricao(lista.get(i).getDescricao());
					renovacaoVo
							.setValor(uti.insereSeparadores(Integer
									.toString(Integer.parseInt(lista.get(i)
											.getValor()))));

				} else {

					renovacaoVo.setProduto("18");
					renovacaoVo.setDescricao(lista.get(i).getDescricao());
					renovacaoVo
							.setValor(uti.insereSeparadores(Integer
									.toString(Integer.parseInt(lista.get(i)
											.getValor()))));
				}
				listaTratada.add(renovacaoVo);

			}
		}

		int totalGeral = 0;
		for (int i = 0; i < listaTratada.size(); i++) {
			if (listaTratada.get(i).getDescricao().equals("Geral Para Renovar")
					|| listaTratada.get(i).getDescricao()
							.equals("Geral Canceladas")) {
				totalGeral += Integer.parseInt(listaTratada.get(i).getValor()
						.replace(".", ""));
			}
		}

		int totalGeralSemPrazoCurto = 0;
		for (int i = 0; i < listaTratada.size(); i++) {
			if (listaTratada.get(i).getDescricao()
					.equals("Emitidas sem prazo curto do empresarial")
					|| listaTratada
							.get(i)
							.getDescricao()
							.equals("Canceladas sem prazo curto do empresarial")) {
				totalGeralSemPrazoCurto += Integer.parseInt(listaTratada.get(i)
						.getValor().replace(".", ""));
			}
		}

		RenovacaoVO totalGeralVo = new RenovacaoVO();
		totalGeralVo.setProduto("14 e 18");
		totalGeralVo.setDescricao("Total Geral de Ap&oacute;lices");
		totalGeralVo.setValor(uti.insereSeparadores(Integer
				.toString(totalGeral)));

		RenovacaoVO totalGeralSemPrazoCurtoVo = new RenovacaoVO();
		totalGeralSemPrazoCurtoVo.setProduto("14 e 18");
		totalGeralSemPrazoCurtoVo
				.setDescricao("Total Geral de Ap&oacute;lices - Sem Prazo Curto");
		totalGeralSemPrazoCurtoVo.setValor(uti.insereSeparadores(Integer
				.toString(totalGeralSemPrazoCurto)));

		listaFinal.add(totalGeralVo);
		listaFinal.add(totalGeralSemPrazoCurtoVo);

		for (int i = 0; i < listaTratada.size(); i++) {

			listaFinal.add(listaTratada.get(i));
		}

		return listaFinal;
	}

	/**
	 * Retorna uma lista contendo o Canal e a quantidade de apolices das que
	 * foram geradas e emitidas.
	 *
	 * @return List<RenovacaoVO>
	 * 
	 * */
	public List<RenovacaoVO> validaSelecionaCanalApolicesEmitidas(String mes,
			String ano) {

		VisaoOperacionalDAO dao = new VisaoOperacionalDAO();
		List<RenovacaoVO> lista = dao.selecionaCanalApolicesEmitidas(mes, ano);
		List<RenovacaoVO> listaTratada = new ArrayList<RenovacaoVO>();
		// for (int i = 0; i < lista.size(); i++) {
		// System.out.println();
		// }
		BigDecimal total = new BigDecimal("0");
		BigDecimal valor = null;

		for (int i = 0; i < lista.size(); i++) {
			valor = new BigDecimal(lista.get(i).getValor());
			total = total.add(valor);
		}
		// System.out.println(total);

		for (int i = 0; i < lista.size(); i++) {
			RenovacaoVO renovacaoVo = new RenovacaoVO();
			renovacaoVo.setDescricao(lista.get(i).getDescricao());
			renovacaoVo.setPercentual(percentForm.format(Double
					.parseDouble(new BigDecimal(lista.get(i).getValor())
							.divide(total, 3, RoundingMode.HALF_DOWN)
							.toString())));
			renovacaoVo
					.setValor(uti.insereSeparadores(lista.get(i).getValor()));
			listaTratada.add(renovacaoVo);
		}
		return listaTratada;
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
	public List<RenovacaoVO> validaSelecionaRenovacoesRealizadas(String mes,
			String ano) {

		VisaoOperacionalDAO dao = new VisaoOperacionalDAO();
		List<RenovacaoVO> lista = dao.selecionaRenovacoesRealizadas(mes, ano);
		List<RenovacaoVO> listaTratada = new ArrayList<RenovacaoVO>();
		// for (int i = 0; i < lista.size(); i++) {
		// System.out.println();
		// }

		double valorRA = 0D;
		double valorMD = 0D;
		for (int i = 0; i < lista.size(); i++) {

			if (lista.get(i).getDescricao()
					.equalsIgnoreCase("Renovação Automática")) {
				if (valorRA == 0) {
					valorRA = Double.parseDouble(lista.get(i).getValor());
				} else {
					if (Double.parseDouble(lista.get(i).getValor()) > valorRA) {
						valorRA = Double.parseDouble(lista.get(i).getValor());
					}
				}

			} else if (lista.get(i).getDescricao()
					.equalsIgnoreCase("Renovação Mala Direta")) {
				if (valorMD == 0) {
					valorMD = Double.parseDouble(lista.get(i).getValor());
				} else {
					if (Double.parseDouble(lista.get(i).getValor()) > valorMD) {
						valorMD = Double.parseDouble(lista.get(i).getValor());
					}
				}
			}
		}
		for (int i = 0; i < lista.size(); i++) {
			if (Double.parseDouble(lista.get(i).getValor()) == valorRA) {
				lista.get(i).setDescricao(
						"<b>" + lista.get(i).getDescricao() + "</b>");
			} else if (Double.parseDouble(lista.get(i).getValor()) == valorMD) {
				lista.get(i).setDescricao(
						"<b>" + lista.get(i).getDescricao() + "</b>");
			}

			RenovacaoVO renovacaoVo = new RenovacaoVO();
			renovacaoVo.setDescricao(lista.get(i).getDescricao());
			renovacaoVo
					.setValor(uti.insereSeparadores(lista.get(i).getValor()));
			listaTratada.add(renovacaoVo);
		}

		return listaTratada;
	}

	/***********************************************************/
	/***********************************************************/
	/*******************************/
	/* Relatorio de SENSIBILIZACAO */
	/*******************************/

	/**
	 * Retorna uma lista de 2 linhas, a primeira contem a quantidade de premio,
	 * a segunda a quantidade de apolices. Pr&ecirc;mio Acumulado - 1.480.445,43
	 * - Total de Propostas - 11.001
	 * 
	 *
	 * @return List<SensibilizacaoVO>
	 * 
	 * */
	public List<SensibilizacaoVO> validaSelecionaTotalSensibilizacao(
			String prod, String ano, String mes, String categoria,
			String tipoMovimento) {
		// System.out.println(prod + " " + ano + " " + mes + " " + categoria);
		if (prod.equals("*")) {
			prod = "";
		} else {
			switch (categoria) {
			case "vendasNovas":
				prod = prod.replace("p", ",");
				break;

			case "fluxoFinanceiro":
				prod = prod.replace("p", ",");
				break;
			}
		}

		VisaoOperacionalDAO dao = new VisaoOperacionalDAO();

		List<SensibilizacaoVO> lista = dao.selecionaTotalSensibilizacao(prod,
				ano, mes, categoria, tipoMovimento);

		List<SensibilizacaoVO> listaTratada = new ArrayList<SensibilizacaoVO>();
		try {
			for (int i = 0; i < lista.size(); i++) {
				if (i == 0) { // premio acumulado
					SensibilizacaoVO sensibilizacaoVo = new SensibilizacaoVO();
					sensibilizacaoVo.setProduto(lista.get(i).getProduto());
					sensibilizacaoVo.setQuantidade(uti.insereSeparadores(lista
							.get(i).getQuantidade() == null ? "0.0" : lista
							.get(i).getQuantidade()));
					listaTratada.add(sensibilizacaoVo);
				} else {
					SensibilizacaoVO sensibilizacaoVo = new SensibilizacaoVO();
					sensibilizacaoVo.setProduto(lista.get(i).getProduto());
					sensibilizacaoVo.setQuantidade(uti.insereSeparadores(lista
							.get(i).getQuantidade().replace(".00", "")));

					listaTratada.add(sensibilizacaoVo);
				}
			}
		} catch (NullPointerException npe) {
			npe.printStackTrace();
			System.out
					.println("Metodo - validaSelecionaTotalSensibilizacao BO - NullPointerException.");
		}
		return listaTratada;
	}

	/**
	 * Retorna uma lista com as colunas Agencia, Produto, SR, SUAT, Quantidade
	 * de apolices e total de premio agrupado por Agencia.
	 *
	 * @return List<SensibilizacaoVO>
	 * 
	 * */
	public List<SensibilizacaoVO> validaSelecionaTotalAgrupadoPorAgenciaSensibilizacao(
			String prod, String ano, String mes, String categoria, int pagina) {

		if (prod.equals("*")) {
			prod = " ";
		} else {

			switch (categoria) {
			case "vendasNovas":
				prod = "and NumeroProdutoSIGPF in (" + prod.replace("p", ",")
						+ ")";
				prod = prod.replace(",)", ")");
				// System.out.println(prod);
				break;

			case "fluxoFinanceiro":
				prod = "where codigoproduto in (" + prod.replace("p", ",")
						+ ")";
				prod = prod.replace(",)", ")");
				// System.out.println(prod);
				break;
			}
		}

		VisaoOperacionalDAO dao = new VisaoOperacionalDAO();
		// System.out.println("@1");
		List<SensibilizacaoVO> lista = dao
				.selecionaTotalAgrupadoPorAgenciaSensibilizacao(prod, ano, mes,
						categoria, pagina);
		List<SensibilizacaoVO> listaTratada = new ArrayList<SensibilizacaoVO>();

		for (int i = 0; i < lista.size(); i++) {
			SensibilizacaoVO sensibilizacaoVo = new SensibilizacaoVO();
			sensibilizacaoVo.setAgencia(lista.get(i).getAgencia());
			sensibilizacaoVo.setCodAgencia(lista.get(i).getCodAgencia());
			switch (lista.get(i).getProduto()) {
			case "0050":
				sensibilizacaoVo.setProduto("1804");
				break;
			case "0071":
				sensibilizacaoVo.setProduto("1403");
				break;
			case "0072":
				sensibilizacaoVo.setProduto("1404");
				break;
			case "0010":
				sensibilizacaoVo.setProduto("1405");
				break;
			default:
				sensibilizacaoVo.setProduto(lista.get(i).getProduto());
				break;
			}
			sensibilizacaoVo.setSR(lista.get(i).getSR());
			sensibilizacaoVo.setSUAT(lista.get(i).getSUAT());
			sensibilizacaoVo.setQuantidade(lista.get(i).getQuantidade());
			sensibilizacaoVo.setValor(uti.insereSeparadores(lista.get(i)
					.getValor()));
			listaTratada.add(sensibilizacaoVo);
		}

		return listaTratada;
	}

	/**
	 * Retorna o total de registros da consulta.
	 *
	 * @return int
	 * 
	 * */
	public int validaSelecionaQuantidadeRegistros(String prod, String ano,
			String mes, String categoria) {

		if (prod.equals("*")) {
			prod = " ";
		} else {

			switch (categoria) {
			case "vendasNovas":
				prod = "and NumeroProdutoSIGPF in (" + prod.replace("p", ",")
						+ ")";
				prod = prod.replace(",)", ")");
				// System.out.println(prod);
				break;

			case "fluxoFinanceiro":
				prod = "where codigoproduto in (" + prod.replace("p", ",")
						+ ")";
				prod = prod.replace(",)", ")");
				// System.out.println(prod);
				break;
			}
		}

		VisaoOperacionalDAO dao = new VisaoOperacionalDAO();
		return (dao.selecionaQuantidadeRegistros(prod, ano, mes, categoria) / 1000) + 1;
	}

	/**
	 * Gera um relatorio Excel com o mesmo conteudo que esta indo para a pagina
	 * web.
	 * 
	 * @return List<SensibilizacaoVO>
	 * 
	 * */
	public void validaGeraRelatorioExcel(String prod, String anoParam,
			String mesParam, String categoria) {

		String filename = " ";

		if (prod.equals("*")) {
			prod = " ";

			switch (categoria) {
			case "vendasNovas":
				filename = "D:\\workspace/ProjetoCaixaLocal_1.3/WebContent/Downloads/vendasNovas/"
						+ categoria + anoParam + mesParam + ".xls";
				break;

			case "fluxoFinanceiro":
				filename = "D:\\workspace/ProjetoCaixaLocal_1.3/WebContent/Downloads/fluxoFinanceiro/"
						+ categoria + anoParam + mesParam + ".xls";
				break;
			}
		} else {
			switch (categoria) {
			case "vendasNovas":
				filename = "D:\\workspace/ProjetoCaixaLocal_1.3/WebContent/Downloads/vendasNovas/"
						+ categoria + anoParam + mesParam + ".xls";
				prod = "and NumeroProdutoSIGPF in (" + prod.replace("p", ",")
						+ ")";
				prod = prod.replace(",)", ")");
				// System.out.println(prod);
				break;

			case "fluxoFinanceiro":
				filename = "D:\\workspace/ProjetoCaixaLocal_1.3/WebContent/Downloads/fluxoFinanceiro/"
						+ categoria + anoParam + mesParam + ".xls";
				prod = "where codigoproduto in (" + prod.replace("p", ",")
						+ ")";
				prod = prod.replace(",)", ")");
				// System.out.println(prod);
				break;
			}
		}

		VisaoOperacionalDAO dao = new VisaoOperacionalDAO();
		// System.out.println("@2");
		List<SensibilizacaoVO> lista = dao
				.selecionaTotalAgrupadoPorAgenciaSensibilizacao(prod, anoParam,
						mesParam, categoria, 9999);
		List<SensibilizacaoVO> listaPeriodo = dao
				.selecionaPeriodoSensibilizacao(anoParam, mesParam, categoria);
		List<SensibilizacaoVO> listaTratada = new ArrayList<SensibilizacaoVO>();
		List<SensibilizacaoVO> listaTratadaPeriodo = new ArrayList<SensibilizacaoVO>();

		for (int i = 0; i < lista.size(); i++) {
			SensibilizacaoVO sensibilizacaoVo = new SensibilizacaoVO();
			sensibilizacaoVo.setAgencia(lista.get(i).getAgencia());
			sensibilizacaoVo.setCodAgencia(lista.get(i).getCodAgencia());
			switch (lista.get(i).getProduto()) {
			case "0050":
				sensibilizacaoVo.setProduto("1804");
				break;
			case "0071":
				sensibilizacaoVo.setProduto("1403");
				break;
			case "0072":
				sensibilizacaoVo.setProduto("1404");
				break;
			case "0010":
				sensibilizacaoVo.setProduto("1405");
				break;
			default:
				sensibilizacaoVo.setProduto(lista.get(i).getProduto());
				break;
			}
			sensibilizacaoVo.setSR(lista.get(i).getSR());
			sensibilizacaoVo.setSUAT(lista.get(i).getSUAT());
			sensibilizacaoVo.setQuantidade(lista.get(i).getQuantidade());
			sensibilizacaoVo.setValor(uti.insereSeparadores(lista.get(i)
					.getValor()));

			listaTratada.add(sensibilizacaoVo);
		}
		for (int i = 0; i < listaPeriodo.size(); i++) {
			try {
				String ano = listaPeriodo.get(i).getDataInicio()
						.substring(0, 4);
				String mes = listaPeriodo.get(i).getDataInicio()
						.substring(5, 7);
				String dia = listaPeriodo.get(i).getDataInicio()
						.substring(8, 10);
				String dataI = dia + "/" + mes + "/" + ano;

				ano = listaPeriodo.get(i).getDataFim().substring(0, 4);
				mes = listaPeriodo.get(i).getDataFim().substring(5, 7);
				dia = listaPeriodo.get(i).getDataFim().substring(8, 10);
				String dataF = dia + "/" + mes + "/" + ano;

				SensibilizacaoVO sensibilizacaoVo = new SensibilizacaoVO();
				sensibilizacaoVo.setDataInicio(dataI);
				sensibilizacaoVo.setDataFim(dataF);
				listaTratadaPeriodo.add(sensibilizacaoVo);
			} catch (NullPointerException npe) {
				npe.printStackTrace();
				System.out
						.println("Metodo - validaGeraRelatorioExcel - BO - NullPointerException.");
			}
		}
		try {
			// String filename =
			// "Y://DIRETORIA DE RISCOS DIVERSOS/PUBLICA/PHELIPE/RelatorioSensibilizacaoMensal/NewExcelFile.xls";

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Mês " + mesParam);

			HSSFRow rowhead = sheet.createRow((short) 0);

			rowhead.createCell(0).setCellValue("Código Agência");
			rowhead.createCell(1).setCellValue("Agência");
			rowhead.createCell(2).setCellValue("Produto");
			rowhead.createCell(3).setCellValue("SR");
			rowhead.createCell(4).setCellValue("SUAT");
			rowhead.createCell(5).setCellValue("Quantidade");
			rowhead.createCell(6).setCellValue("Prêmio");

			for (int i = 0; i < listaTratada.size(); i++) {

				HSSFRow row = sheet.createRow((short) i + 1);

				if (i == 4) {
					row.createCell(7).setCellValue("Período");
					row.createCell(8).setCellValue(
							listaTratadaPeriodo.get(0).getDataInicio());
					row.createCell(9).setCellValue(
							listaTratadaPeriodo.get(0).getDataFim());
				}

				row.createCell(0).setCellValue(
						listaTratada.get(i).getCodAgencia());
				row.createCell(1)
						.setCellValue(listaTratada.get(i).getAgencia());
				row.createCell(2)
						.setCellValue(listaTratada.get(i).getProduto());
				row.createCell(3).setCellValue(listaTratada.get(i).getSR());
				row.createCell(4).setCellValue(listaTratada.get(i).getSUAT());
				row.createCell(5).setCellValue(
						listaTratada.get(i).getQuantidade());
				row.createCell(6).setCellValue(listaTratada.get(i).getValor());

			}

			System.out.println(filename);

			FileOutputStream fileOut = new FileOutputStream(filename);
			workbook.write(fileOut);
			fileOut.close();
			// System.out.println("Your excel file has been generated!");

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out
					.println("ERRO metodo validaGeraRelatorioExcel VisaoOperacionalBO");
		}

	}

	/**
	 * Retorna o período. Data Inicial e Data Final das informações no formato
	 * DD/MM/AAAA.
	 * 
	 * @return List<SensibilizacaoVO>
	 * 
	 * */

	public List<SensibilizacaoVO> validaSelecionaPeriodoSensibilizacao(
			String anoParam, String mesParam, String categoria) {
		VisaoOperacionalDAO dao = new VisaoOperacionalDAO();
		List<SensibilizacaoVO> lista = dao.selecionaPeriodoSensibilizacao(
				anoParam, mesParam, categoria);
		List<SensibilizacaoVO> listaTratada = new ArrayList<SensibilizacaoVO>();
		for (int i = 0; i < lista.size(); i++) {
			String ano = lista.get(i).getDataInicio().substring(0, 4);
			String mes = lista.get(i).getDataInicio().substring(5, 7);
			String dia = lista.get(i).getDataInicio().substring(8, 10);
			String dataI = dia + "/" + mes + "/" + ano;

			ano = lista.get(i).getDataFim().substring(0, 4);
			mes = lista.get(i).getDataFim().substring(5, 7);
			dia = lista.get(i).getDataFim().substring(8, 10);
			String dataF = dia + "/" + mes + "/" + ano;

			SensibilizacaoVO sensibilizacaoVo = new SensibilizacaoVO();
			sensibilizacaoVo.setDataInicio(dataI);
			sensibilizacaoVo.setDataFim(dataF);
			listaTratada.add(sensibilizacaoVo);
		}
		return listaTratada;
	}

	/******************************/
	/* SENSIBILIZACAO TOTALIZADOR */

	/**
	 * 
	 * Recebe o mesmo metodo com os valores do realizado do mes, utiliza o que
	 * for renovado para gerar informacoes do tipo de movimento especifico, o
	 * renovado e o quanto foi atingido em porcentagem entre os dois.
	 * 
	 * @return List<SensibilizacaoTotalizadorVO>
	 * 
	 * */
	public List<SensibilizacaoTotalizadorVO> validaSelecionaTotalizadorMensal(
			List<SensibilizacaoTotalizadorVO> realizado, String anoParam) {
		List<SensibilizacaoTotalizadorVO> sensiVO = new ArrayList<SensibilizacaoTotalizadorVO>();
		DecimalFormat percentForm = new DecimalFormat("0%");
		String dataAtual = new SimpleDateFormat("dd/MM/yyyy").format(new Date(
				System.currentTimeMillis()));
		String dataCut[] = dataAtual.split("/");
		String mes = dataCut[1];
		String ano = anoParam;

		// aqui eu tenho o fluxo financeiro todos os meses select * from
		// temp_sensibilizacao
		VisaoOperacionalDAO dao = new VisaoOperacionalDAO();
		sensiVO = dao.selecionaFluxoFinanceiroTotalizadorMensal(ano,
				"renovacao");

		List<SensibilizacaoVO> fluxoFinanceiroEmpresarial = validaSelecionaTotalSensibilizacao(
				"1804", anoParam, mes, "fluxoFinanceiro", "renovacao");

		List<SensibilizacaoVO> fluxoFinanceiroResidencial = validaSelecionaTotalSensibilizacao(
				"1403p1404p1405", anoParam, mes, "fluxoFinanceiro", "renovacao");

		String[] arrayFluxoFina = sensiVO.get(0).getMeses();// EMPRESARIAL
		arrayFluxoFina[Integer.parseInt(mes) - 1] = fluxoFinanceiroEmpresarial
				.get(0).getQuantidade().toString().replace(".", "")
				.replace(",", ".");
		sensiVO.get(0).setMeses(arrayFluxoFina);

		arrayFluxoFina = sensiVO.get(1).getMeses();// RESIDENCIAL
		arrayFluxoFina[Integer.parseInt(mes) - 1] = fluxoFinanceiroResidencial
				.get(0).getQuantidade().toString().replace(".", "")
				.replace(",", ".");
		sensiVO.get(1).setMeses(arrayFluxoFina);

		// Abaixo vou juntar o mes atual na tabela temp_sensi
		int realizadoEmpre = 1;
		int realizadoResid = 3;

		SensibilizacaoTotalizadorVO voRealizadoEmp = new SensibilizacaoTotalizadorVO();

		String[] mesArray = realizado.get(realizadoEmpre).getMeses();

		for (int i = 0; i < 12; i++) {
			if (mesArray[i] == null) {
				mesArray[i] = "-";
			}
			mesArray[i] = mesArray[i].replace(".", "").replace(",", ".");
		}
		voRealizadoEmp.setProduto("Realizado Empresarial");
		voRealizadoEmp.setMeses(mesArray);
		sensiVO.add(voRealizadoEmp);

		SensibilizacaoTotalizadorVO voRealizadoRes = new SensibilizacaoTotalizadorVO();

		mesArray = realizado.get(realizadoResid).getMeses();

		for (int i = 0; i < 12; i++) {
			if (mesArray[i] == null) {
				mesArray[i] = "-";
			}
			mesArray[i] = mesArray[i].replace(".", "").replace(",", ".");
		}
		voRealizadoRes.setProduto("Realizado Residencial");
		voRealizadoRes.setMeses(mesArray);
		sensiVO.add(voRealizadoRes);

		int EMPRESARIAL = 0;
		int RESIDENCIAL = 1;

		String[] atingidoEmpresarial = new String[12];
		String[] atingidoResidencial = new String[12];
		for (int i = 0; i < sensiVO.size(); i++) {
			for (int j = 0; j < 12; j++) {

				if (i == EMPRESARIAL) { // empresarial
					BigDecimal bigFluxoFinanceiroEmpresarial;
					try {
						bigFluxoFinanceiroEmpresarial = new BigDecimal(
								sensiVO.get(EMPRESARIAL).getMeses()[j]
										.contains(".") ? sensiVO.get(
										EMPRESARIAL).getMeses()[j] : sensiVO
										.get(EMPRESARIAL).getMeses()[j] + ".00");
					} catch (Exception npe) {
						bigFluxoFinanceiroEmpresarial = new BigDecimal("0.00");
					}

					BigDecimal bigRealizadoEmpresarial;

					try {
						bigRealizadoEmpresarial = new BigDecimal(sensiVO.get(
								i + 2).getMeses()[j].contains(".") ? sensiVO
								.get(i + 2).getMeses()[j] : sensiVO.get(i + 2)
								.getMeses()[j] + ".00");
					} catch (Exception npe) {
						bigRealizadoEmpresarial = new BigDecimal("0.00");
					}

					try {

						atingidoEmpresarial[j] = percentForm.format(Double
								.parseDouble(bigFluxoFinanceiroEmpresarial
										.toString().equals("0.00") ? "0.00"
										: bigFluxoFinanceiroEmpresarial.divide(
												bigRealizadoEmpresarial,
												BigDecimal.ROUND_HALF_UP)
												.toString()));
					} catch (Exception e) {

						atingidoEmpresarial[j] = percentForm.format(0.0D);

					}
				}

				if (i == RESIDENCIAL) { // residencial

					BigDecimal bigFluxoFinanceiroResidencial;
					try {
						bigFluxoFinanceiroResidencial = new BigDecimal(
								sensiVO.get(RESIDENCIAL).getMeses()[j]
										.contains(".") ? sensiVO.get(
										RESIDENCIAL).getMeses()[j] : sensiVO
										.get(RESIDENCIAL).getMeses()[j] + ".00");
					} catch (Exception e) {
						bigFluxoFinanceiroResidencial = new BigDecimal("0.00");
					}

					BigDecimal bigRealizadoResidencial;

					try {
						bigRealizadoResidencial = new BigDecimal(sensiVO.get(
								i + 2).getMeses()[j].contains(".") ? sensiVO
								.get(i + 2).getMeses()[j] : sensiVO.get(i + 2)
								.getMeses()[j] + ".00");
					} catch (Exception e) {
						bigRealizadoResidencial = new BigDecimal("0.00");
					}

					try {
						atingidoResidencial[j] = percentForm.format(Double
								.parseDouble(bigFluxoFinanceiroResidencial
										.toString().equals("0.00") ? "0.00"
										: bigFluxoFinanceiroResidencial.divide(
												bigRealizadoResidencial,
												BigDecimal.ROUND_HALF_UP)
												.toString()));
					} catch (Exception npe) {
						atingidoResidencial[j] = percentForm.format(0.0D);
					}
				}

			}
		}

		SensibilizacaoTotalizadorVO atingidoRD = new SensibilizacaoTotalizadorVO();
		SensibilizacaoTotalizadorVO atingidoEM = new SensibilizacaoTotalizadorVO();

		atingidoEM.setProduto("Atingido Empresarial Fluxo Finan");
		atingidoEM.setMeses(atingidoEmpresarial);

		atingidoRD.setProduto("Atingido Residencial Fluxo Finan");
		atingidoRD.setMeses(atingidoResidencial);

		for (int i = 0; i < sensiVO.size(); i++) {
			String[] arrayFormatadoMoeda = new String[12];
			for (int j = 0; j < 12; j++) {

				try {
					arrayFormatadoMoeda[j] = uti.insereSeparadoresMoeda(sensiVO
							.get(i).getMeses()[j]);
				} catch (Exception e) {
					arrayFormatadoMoeda[j] = uti.insereSeparadoresMoeda("0.0");
				}

			}
			sensiVO.get(i).setMeses(arrayFormatadoMoeda);
		}

		sensiVO.add(atingidoEM);
		sensiVO.add(atingidoRD);

		/***** insiro na lista o BP e o atingido entre BP e realizado ***********************/
		SensibilizacaoTotalizadorVO residencial_BP = new SensibilizacaoTotalizadorVO();
		SensibilizacaoTotalizadorVO atingidoRD_BP = new SensibilizacaoTotalizadorVO();
		SensibilizacaoTotalizadorVO empresarial_BP = new SensibilizacaoTotalizadorVO();
		SensibilizacaoTotalizadorVO atingidoEM_BP = new SensibilizacaoTotalizadorVO();
		/************************************************************************************/

		residencial_BP.setProduto(realizado.get(2).getProduto());
		residencial_BP.setMeses(realizado.get(2).getMeses());
		atingidoRD_BP.setProduto(realizado.get(5).getProduto());
		atingidoRD_BP.setMeses(realizado.get(5).getMeses());

		empresarial_BP.setProduto(realizado.get(0).getProduto());
		empresarial_BP.setMeses(realizado.get(0).getMeses());
		atingidoEM_BP.setProduto(realizado.get(4).getProduto());
		atingidoEM_BP.setMeses(realizado.get(4).getMeses());

		sensiVO.add(residencial_BP);
		sensiVO.add(atingidoRD_BP);

		sensiVO.add(empresarial_BP);
		sensiVO.add(atingidoEM_BP);

		// for (int i = 0; i < sensiVO.size(); i++) {
		// System.out.println(i);
		// System.out.println(sensiVO.get(i).getProduto());
		// }

		return sensiVO;
	}

	/**
	 * Retorna BP, o valor Realizado durante o ano e a media entre BP e o
	 * realizado.
	 *
	 * @return List<SensibilizacaoTotalizadorVO>
	 * 
	 * */
	public List<SensibilizacaoTotalizadorVO> validaSelecionaTotalizadorMensal(
			String tipoMovimento, String anoParam) {

		DecimalFormat percentForm = new DecimalFormat("0%");
		String dataAtual = new SimpleDateFormat("dd/MM/yyyy").format(new Date(
				System.currentTimeMillis()));
		String dataCut[] = dataAtual.split("/");
		String mes = dataCut[1];

		List<SensibilizacaoTotalizadorVO> listaFinal = new ArrayList<SensibilizacaoTotalizadorVO>();
		String[] mesesFinal = new String[12];

		// aqui eu tenho o select * from temp_sensibilizacao
		VisaoOperacionalDAO dao = new VisaoOperacionalDAO();
		List<SensibilizacaoTotalizadorVO> sensiVO = new ArrayList<SensibilizacaoTotalizadorVO>();
		sensiVO = dao.selecionaSensibilizacaoTotalizadorMensal(mes,
				tipoMovimento, anoParam);

		// for (int i = 0; i < sensiVO.size(); i++) {
		// System.out.println(sensiVO.get(i).getProduto());
		// for (int j = 0; j < 12; j++) {
		// System.out.println("bbbb " + sensiVO.get(i).getMeses()[j]);
		//
		// }
		//
		// }

		/*
		 * aqui eu tenho o select das tabelas no mes atual - emissao e
		 * financeiro
		 */

		// Empresarial
		List<SensibilizacaoVO> vendasNovasEmpresarial = validaSelecionaTotalSensibilizacao(
				"1804", anoParam, mes, "vendasNovas", tipoMovimento);
		List<SensibilizacaoVO> fluxoFinanceiroEmpresarial = validaSelecionaTotalSensibilizacao(
				"1804", anoParam, mes, "fluxoFinanceiro", tipoMovimento);

		// Residencial
		List<SensibilizacaoVO> vendasNovasResidencial = validaSelecionaTotalSensibilizacao(
				"1403p1404p1405", anoParam, mes, "vendasNovas", tipoMovimento);
		List<SensibilizacaoVO> fluxoFinanceiroResidencial = validaSelecionaTotalSensibilizacao(
				"1403p1404p1405", anoParam, mes, "fluxoFinanceiro",
				tipoMovimento);

		// Abaixo vou juntar o mes atual na tabela temp_sensi

		BigDecimal venNovaEmpre = new BigDecimal(vendasNovasEmpresarial.get(0)
				.getQuantidade().replace(".", "").replace(",", "."));
		BigDecimal fluFinaEmpre = new BigDecimal(fluxoFinanceiroEmpresarial
				.get(0).getQuantidade().replace(".", "").replace(",", "."));
		BigDecimal venNovaResid = new BigDecimal(vendasNovasResidencial.get(0)
				.getQuantidade().replace(".", "").replace(",", "."));
		BigDecimal fluFinaResid = new BigDecimal(fluxoFinanceiroResidencial
				.get(0).getQuantidade().replace(".", "").replace(",", "."));

		// get 0 BP-EMPRESARIAL
		String[] mesArray = sensiVO.get(0).getMeses();
		for (int i = 0; i < 12; i++) {
			if (mesArray[i] == null) {
				mesArray[i] = "-";
			}
		}

		// get 2 BP-RESIDENCIAL
		mesArray = sensiVO.get(2).getMeses();
		for (int i = 0; i < 12; i++) {
			if (mesArray[i] == null) {
				mesArray[i] = "-";
			}
		}

		// get 1 EMPRESARIAL
		int empresarial = 1;

		// get 3 RESIDENCIAL
		int residencial = 3;
		mesArray = sensiVO.get(empresarial).getMeses();

		/* FACO AQUI A SOMA DO FLUXOfINANCEIRO + VENDASnOVAS EMPRESARIAL */
		mesArray[Integer.parseInt(mes) - 1] = venNovaEmpre.add(fluFinaEmpre)
				.toString();
		for (int i = 0; i < 12; i++) {
			if (mesArray[i] == null) {
				mesArray[i] = "-";
			}
		}

		sensiVO.get(empresarial).setMeses(mesArray);
		mesArray = sensiVO.get(residencial).getMeses();

		/* FACO AQUI A SOMA DO FLUXOfINANCEIRO + VENDASnOVAS RESIDENCIAL */
		mesArray[Integer.parseInt(mes) - 1] = venNovaResid.add(fluFinaResid)
				.toString();
		for (int i = 0; i < 12; i++) {
			if (mesArray[i] == null) {
				mesArray[i] = "-";
			}
		}

		sensiVO.get(residencial).setMeses(mesArray);

		String[] atingidoEmpresarial = new String[12];
		String[] atingidoResidencial = new String[12];
		for (int i = 0; i < sensiVO.size(); i++) {
			for (int j = 0; j < 12; j++) {

				if (i == 0) { // bp empresarial
					BigDecimal bpEmpresarial = new BigDecimal(sensiVO.get(i)
							.getMeses()[j].contains(".") ? sensiVO.get(i)
							.getMeses()[j] : sensiVO.get(i).getMeses()[j]
							+ ".00");
					BigDecimal valorEmpresarial = new BigDecimal(sensiVO.get(
							empresarial).getMeses()[j].contains(".") ? sensiVO
							.get(empresarial).getMeses()[j] : sensiVO.get(
							empresarial).getMeses()[j]
							+ ".00");

					atingidoEmpresarial[j] = percentForm.format(Double
							.parseDouble(valorEmpresarial.toString().equals(
									"0.00") ? "0.00" : valorEmpresarial.divide(
									bpEmpresarial, BigDecimal.ROUND_DOWN)
									.toString()));
				}

				if (i == 2) { // bp residencial
					BigDecimal bpResidencial = new BigDecimal(sensiVO.get(i)
							.getMeses()[j].contains(".") ? sensiVO.get(i)
							.getMeses()[j] : sensiVO.get(i).getMeses()[j]
							+ ".00");
					BigDecimal valorResidencial = new BigDecimal(sensiVO.get(
							residencial).getMeses()[j].contains(".") ? sensiVO
							.get(residencial).getMeses()[j] : sensiVO.get(
							residencial).getMeses()[j]
							+ ".00");

					atingidoResidencial[j] = percentForm.format(Double
							.parseDouble(valorResidencial.toString().equals(
									"0.00") ? "0.00" : valorResidencial.divide(
									bpResidencial, BigDecimal.ROUND_DOWN)
									.toString()));
				}
			}
		}

		SensibilizacaoTotalizadorVO atingidoRD = new SensibilizacaoTotalizadorVO();
		SensibilizacaoTotalizadorVO atingidoEM = new SensibilizacaoTotalizadorVO();

		atingidoEM.setProduto("Atingido Empresarial");
		atingidoEM.setMeses(atingidoEmpresarial);

		atingidoRD.setProduto("Atingido Residencial");
		atingidoRD.setMeses(atingidoResidencial);

		for (int i = 0; i < sensiVO.size(); i++) {
			String[] arrayFormatadoMoeda = new String[12];
			for (int j = 0; j < 12; j++) {

				arrayFormatadoMoeda[j] = uti.insereSeparadoresMoeda(sensiVO
						.get(i).getMeses()[j]);

			}
			sensiVO.get(i).setMeses(arrayFormatadoMoeda);
		}

		sensiVO.add(atingidoEM);
		sensiVO.add(atingidoRD);

		return sensiVO;
	}

	/*
	 * .............................................................
	 * .............................................
	 * ................................. ....................
	 */
	/*
	 * public List<SensibilizacaoTotalizadorVO> validaSelecionaTotalizadorFACIL(
	 * String mesP) {
	 * 
	 * DecimalFormat percentForm = new DecimalFormat("0%"); String dataAtual =
	 * new SimpleDateFormat("dd/MM/yyyy").format(new Date(
	 * System.currentTimeMillis())); String dataCut[] = dataAtual.split("/");
	 * String mes = dataCut[1]; String ano = dataCut[2]; mes = mesP;
	 * List<SensibilizacaoTotalizadorVO> listaFinal = new
	 * ArrayList<SensibilizacaoTotalizadorVO>(); String[] mesesFinal = new
	 * String[12];
	 * 
	 * // aqui eu tenho o select * from temp_sensibilizacao VisaoOperacionalDAO
	 * dao = new VisaoOperacionalDAO(); List<SensibilizacaoTotalizadorVO>
	 * sensiVO = new ArrayList<SensibilizacaoTotalizadorVO>(); sensiVO =
	 * dao.selecionaSensibilizacaoTotalizadorMensal(ano, mes);
	 * 
	 * /* aqui eu tenho o select das tabelas no mes atual - emissao e financeiro
	 * /
	 * 
	 * // Empresarial List<SensibilizacaoVO> vendasNovasEmpresarial =
	 * validaSelecionaTotalSensibilizacao( "1804", "2017", mes, "vendasNovas");
	 * List<SensibilizacaoVO> fluxoFinanceiroEmpresarial =
	 * validaSelecionaTotalSensibilizacao( "1804", "2017", mes,
	 * "fluxoFinanceiro");
	 * 
	 * // Residencial List<SensibilizacaoVO> vendasNovasResidencial =
	 * validaSelecionaTotalSensibilizacao( "1405", "2017", mes, "vendasNovas");
	 * List<SensibilizacaoVO> fluxoFinanceiroResidencial =
	 * validaSelecionaTotalSensibilizacao( "1405", "2017", mes,
	 * "fluxoFinanceiro");
	 * 
	 * // aqui vou tentar juntar o mes atual na tabela temp_sensi
	 * 
	 * BigDecimal venNovaEmpre = new BigDecimal(vendasNovasEmpresarial.get(0)
	 * .getQuantidade().replace(".", "").replace(",", ".")); BigDecimal
	 * fluFinaEmpre = new BigDecimal(fluxoFinanceiroEmpresarial
	 * .get(0).getQuantidade().replace(".", "").replace(",", ".")); BigDecimal
	 * venNovaResid = new BigDecimal(vendasNovasResidencial.get(0)
	 * .getQuantidade().replace(".", "").replace(",", ".")); BigDecimal
	 * fluFinaResid = new BigDecimal(fluxoFinanceiroResidencial
	 * .get(0).getQuantidade().replace(".", "").replace(",", "."));
	 * 
	 * // get 0 BP-EMPRESARIAL String[] mesArray = sensiVO.get(0).getMeses();
	 * for (int i = 0; i < 12; i++) { if (mesArray[i] == null) { mesArray[i] =
	 * "-"; } }
	 * 
	 * // get 2 BP-RESIDENCIAL mesArray = sensiVO.get(2).getMeses(); for (int i
	 * = 0; i < 12; i++) { if (mesArray[i] == null) { mesArray[i] = "-"; } }
	 * 
	 * // get 1 EMPRESARIAL int empresarial = 1;
	 * 
	 * // get 3 RESIDENCIAL int residencial = 3; mesArray =
	 * sensiVO.get(empresarial).getMeses();
	 * 
	 * /* FACO AQUI A SOMA DO FLUXOfINANCEIRO + VENDASnOVAS EMPRESARIAL *
	 * mesArray[Integer.parseInt(mes) - 1] = venNovaEmpre.add(fluFinaEmpre)
	 * .toString(); for (int i = 0; i < 12; i++) { if (mesArray[i] == null) {
	 * mesArray[i] = "-"; } }
	 * 
	 * sensiVO.get(empresarial).setMeses(mesArray);
	 * 
	 * mesArray = sensiVO.get(residencial).getMeses();
	 * 
	 * /* FACO AQUI A SOMA DO FLUXOfINANCEIRO + VENDASnOVAS RESIDENCIAL *
	 * mesArray[Integer.parseInt(mes) - 1] = venNovaResid.add(fluFinaResid)
	 * .toString(); for (int i = 0; i < 12; i++) { if (mesArray[i] == null) {
	 * mesArray[i] = "-"; } }
	 * 
	 * sensiVO.get(residencial).setMeses(mesArray);
	 * 
	 * String[] atingidoEmpresarial = new String[12]; String[]
	 * atingidoResidencial = new String[12]; for (int i = 0; i < sensiVO.size();
	 * i++) { for (int j = 0; j < 12; j++) {
	 * 
	 * if (i == 0) { // bp empresarial BigDecimal bpEmpresarial = new
	 * BigDecimal(sensiVO.get(i) .getMeses()[j].contains(".") ? sensiVO.get(i)
	 * .getMeses()[j] : sensiVO.get(i).getMeses()[j] + ".00"); BigDecimal
	 * valorEmpresarial = new BigDecimal(sensiVO.get(
	 * empresarial).getMeses()[j].contains(".") ? sensiVO
	 * .get(empresarial).getMeses()[j] : sensiVO.get( empresarial).getMeses()[j]
	 * + ".00");
	 * 
	 * atingidoEmpresarial[j] = percentForm.format(Double
	 * .parseDouble(valorEmpresarial.toString().equals( "0.00") ? "0.00" :
	 * valorEmpresarial.divide( bpEmpresarial, BigDecimal.ROUND_HALF_UP)
	 * .toString())); }
	 * 
	 * if (i == 2) { // bp residencial BigDecimal bpResidencial = new
	 * BigDecimal(sensiVO.get(i) .getMeses()[j].contains(".") ? sensiVO.get(i)
	 * .getMeses()[j] : sensiVO.get(i).getMeses()[j] + ".00"); BigDecimal
	 * valorResidencial = new BigDecimal(sensiVO.get(
	 * residencial).getMeses()[j].contains(".") ? sensiVO
	 * .get(residencial).getMeses()[j] : sensiVO.get( residencial).getMeses()[j]
	 * + ".00");
	 * 
	 * atingidoResidencial[j] = percentForm.format(Double
	 * .parseDouble(valorResidencial.toString().equals( "0.00") ? "0.00" :
	 * valorResidencial.divide( bpResidencial, BigDecimal.ROUND_HALF_UP)
	 * .toString())); } } }
	 * 
	 * SensibilizacaoTotalizadorVO atingidoRD = new
	 * SensibilizacaoTotalizadorVO(); SensibilizacaoTotalizadorVO atingidoEM =
	 * new SensibilizacaoTotalizadorVO();
	 * 
	 * atingidoEM.setProduto("Atingido Empresarial");
	 * atingidoEM.setMeses(atingidoEmpresarial);
	 * 
	 * atingidoRD.setProduto("Atingido Residencial");
	 * atingidoRD.setMeses(atingidoResidencial);
	 * 
	 * for (int i = 0; i < sensiVO.size(); i++) { String[] arrayFormatadoMoeda =
	 * new String[12]; for (int j = 0; j < 12; j++) {
	 * 
	 * arrayFormatadoMoeda[j] = uti.insereSeparadoresMoeda(sensiVO
	 * .get(i).getMeses()[j]);
	 * 
	 * } sensiVO.get(i).setMeses(arrayFormatadoMoeda); }
	 * 
	 * sensiVO.add(atingidoEM); sensiVO.add(atingidoRD);
	 * 
	 * return sensiVO; }
	 */
	/*
	 * .............................................................
	 * .............................................
	 * ................................. ....................
	 */

	/**
	 * Retorna apenas renovacao do fluxoFinanceiro.
	 *
	 * @return List<SensibilizacaoTotalizadorVO>
	 * 
	 * */
	public List<SensibilizacaoTotalizadorVO> validaSelecionaApenasRenovacao(
			String tipoMovimento) {
		DecimalFormat percentForm = new DecimalFormat("0%");
		String dataAtual = new SimpleDateFormat("dd/MM/yyyy").format(new Date(
				System.currentTimeMillis()));
		String dataCut[] = dataAtual.split("/");
		String mes = dataCut[1];
		String ano = dataCut[2];

		// aqui eu tenho o select * from temp_sensibilizacao
		VisaoOperacionalDAO dao = new VisaoOperacionalDAO();
		List<SensibilizacaoTotalizadorVO> sensiVO = new ArrayList<SensibilizacaoTotalizadorVO>();
		sensiVO = dao.selecionaSensibilizacaoTotalizadorMensal(mes,
				tipoMovimento, ano);

		// Empresarial
		List<SensibilizacaoVO> fluxoFinanceiroEmpresarial_renovacao = validaSelecionaTotalSensibilizacao(
				"1804", "2017", mes, "fluxoFinanceiro", tipoMovimento);

		// Residencial
		List<SensibilizacaoVO> fluxoFinanceiroResidencial_renovacao = validaSelecionaTotalSensibilizacao(
				"1403p1404p1405", "2017", mes, "fluxoFinanceiro", tipoMovimento);

		// Abaixo vou juntar o mes atual na tabela temp_sensi

		BigDecimal fluFinaEmpre = new BigDecimal(
				fluxoFinanceiroEmpresarial_renovacao.get(0).getQuantidade()
						.replace(".", "").replace(",", "."));
		BigDecimal fluFinaResid = new BigDecimal(
				fluxoFinanceiroResidencial_renovacao.get(0).getQuantidade()
						.replace(".", "").replace(",", "."));

		// get 0 BP-EMPRESARIAL
		String[] mesArray = sensiVO.get(0).getMeses();
		for (int i = 0; i < 12; i++) {
			if (mesArray[i] == null) {
				mesArray[i] = "-";
			}
		}

		// get 2 BP-RESIDENCIAL
		mesArray = sensiVO.get(2).getMeses();
		for (int i = 0; i < 12; i++) {
			if (mesArray[i] == null) {
				mesArray[i] = "-";
			}
		}

		// get 1 EMPRESARIAL
		int empresarial = 1;

		// get 3 RESIDENCIAL
		int residencial = 3;
		mesArray = sensiVO.get(empresarial).getMeses();

		/* AQUI O FLUXOfINANCEIRO EMPRESARIAL */
		mesArray[Integer.parseInt(mes) - 1] = fluFinaEmpre.toString();
		for (int i = 0; i < 12; i++) {
			if (mesArray[i] == null) {
				mesArray[i] = "-";
			}
		}

		sensiVO.get(empresarial).setMeses(mesArray);

		mesArray = sensiVO.get(residencial).getMeses();

		/* AQUI O FLUXOfINANCEIRO RESIDENCIAL */
		mesArray[Integer.parseInt(mes) - 1] = fluFinaResid.toString();
		for (int i = 0; i < 12; i++) {
			if (mesArray[i] == null) {
				mesArray[i] = "-";
			}
		}

		sensiVO.get(residencial).setMeses(mesArray);

		String[] atingidoEmpresarial = new String[12];
		String[] atingidoResidencial = new String[12];
		for (int i = 0; i < sensiVO.size(); i++) {
			for (int j = 0; j < 12; j++) {

				if (i == 0) { // bp empresarial
					BigDecimal bpEmpresarial = new BigDecimal(sensiVO.get(i)
							.getMeses()[j].contains(".") ? sensiVO.get(i)
							.getMeses()[j] : sensiVO.get(i).getMeses()[j]
							+ ".00");
					BigDecimal valorEmpresarial = new BigDecimal(sensiVO.get(
							empresarial).getMeses()[j].contains(".") ? sensiVO
							.get(empresarial).getMeses()[j] : sensiVO.get(
							empresarial).getMeses()[j]
							+ ".00");

					atingidoEmpresarial[j] = percentForm.format(Double
							.parseDouble(valorEmpresarial.toString().equals(
									"0.00") ? "0.00" : valorEmpresarial.divide(
									bpEmpresarial, BigDecimal.ROUND_HALF_UP)
									.toString()));
				}

				if (i == 2) { // bp residencial
					BigDecimal bpResidencial = new BigDecimal(sensiVO.get(i)
							.getMeses()[j].contains(".") ? sensiVO.get(i)
							.getMeses()[j] : sensiVO.get(i).getMeses()[j]
							+ ".00");
					BigDecimal valorResidencial = new BigDecimal(sensiVO.get(
							residencial).getMeses()[j].contains(".") ? sensiVO
							.get(residencial).getMeses()[j] : sensiVO.get(
							residencial).getMeses()[j]
							+ ".00");

					atingidoResidencial[j] = percentForm.format(Double
							.parseDouble(valorResidencial.toString().equals(
									"0.00") ? "0.00" : valorResidencial.divide(
									bpResidencial, BigDecimal.ROUND_HALF_UP)
									.toString()));
				}
			}
		}

		SensibilizacaoTotalizadorVO atingidoRD = new SensibilizacaoTotalizadorVO();
		SensibilizacaoTotalizadorVO atingidoEM = new SensibilizacaoTotalizadorVO();

		atingidoEM.setProduto("Atingido Empresarial");
		atingidoEM.setMeses(atingidoEmpresarial);

		atingidoRD.setProduto("Atingido Residencial");
		atingidoRD.setMeses(atingidoResidencial);

		for (int i = 0; i < sensiVO.size(); i++) {
			String[] arrayFormatadoMoeda = new String[12];
			for (int j = 0; j < 12; j++) {

				arrayFormatadoMoeda[j] = uti.insereSeparadoresMoeda(sensiVO
						.get(i).getMeses()[j]);

			}
			sensiVO.get(i).setMeses(arrayFormatadoMoeda);
		}

		sensiVO.add(atingidoEM);
		sensiVO.add(atingidoRD);

		return sensiVO;
	}

	/**
	 * Retorna TOTALIZADOR ACUMULADO.
	 *
	 * @return List<SensibilizacaoTotalizadorVO>
	 * 
	 * */
	public List<SensibilizacaoTotalizadorVO> validaSelecionaTotalizadorAcumulado(
			String tipoMovimento) {

		DecimalFormat percentForm = new DecimalFormat("0%");
		String dataAtual = new SimpleDateFormat("dd/MM/yyyy").format(new Date(
				System.currentTimeMillis()));
		String dataCut[] = dataAtual.split("/");
		String mes = dataCut[1];
		String ano = dataCut[2];

		List<SensibilizacaoTotalizadorVO> listaFinal = new ArrayList<SensibilizacaoTotalizadorVO>();
		String[] mesesFinal = new String[12];

		// aqui eu tenho o select * from temp_sensibilizacao
		VisaoOperacionalDAO dao = new VisaoOperacionalDAO();
		List<SensibilizacaoTotalizadorVO> sensiVO = new ArrayList<SensibilizacaoTotalizadorVO>();
		sensiVO = dao.selecionaSensibilizacaoTotalizadorAcumulado(ano);

		/*
		 * aqui eu tenho o select das tabelas no mes atual - emissao e
		 * financeiro
		 */

		// Empresarial
		List<SensibilizacaoVO> vendasNovasEmpresarial = validaSelecionaTotalSensibilizacao(
				"1804", "2017", mes, "vendasNovas", tipoMovimento);
		List<SensibilizacaoVO> fluxoFinanceiroEmpresarial = validaSelecionaTotalSensibilizacao(
				"1804", "2017", mes, "fluxoFinanceiro", tipoMovimento);

		// Residencial
		List<SensibilizacaoVO> vendasNovasResidencial = validaSelecionaTotalSensibilizacao(
				"1403p1404p1405", "2017", mes, "vendasNovas", tipoMovimento);
		List<SensibilizacaoVO> fluxoFinanceiroResidencial = validaSelecionaTotalSensibilizacao(
				"1403p1404p1405", "2017", mes, "fluxoFinanceiro", tipoMovimento);

		// aqui vou tentar juntar o mes atual na tabela temp_sensi

		BigDecimal venNovaEmpre = new BigDecimal(vendasNovasEmpresarial.get(0)
				.getQuantidade().replace(".", "").replace(",", "."));
		BigDecimal fluFinaEmpre = new BigDecimal(fluxoFinanceiroEmpresarial
				.get(0).getQuantidade().replace(".", "").replace(",", "."));
		BigDecimal venNovaResid = new BigDecimal(vendasNovasResidencial.get(0)
				.getQuantidade().replace(".", "").replace(",", "."));
		BigDecimal fluFinaResid = new BigDecimal(fluxoFinanceiroResidencial
				.get(0).getQuantidade().replace(".", "").replace(",", "."));

		// get 0 BP-EMPRESARIAL
		String[] mesArray = sensiVO.get(0).getMeses();
		for (int i = 0; i < 12; i++) {
			if (mesArray[i] == null) {
				mesArray[i] = "-";
			}
		}

		// get 2 BP-RESIDENCIAL
		mesArray = sensiVO.get(2).getMeses();
		for (int i = 0; i < 12; i++) {
			if (mesArray[i] == null) {
				mesArray[i] = "-";
			}
		}

		// get 1 EMPRESARIAL
		int empresarial = 1;

		// get 3 RESIDENCIAL
		int residencial = 3;
		mesArray = sensiVO.get(empresarial).getMeses();

		/* FACO AQUI A SOMA DO FLUXOfINANCEIRO + VENDASnOVAS EMPRESARIAL */
		mesArray[Integer.parseInt(mes) - 1] = venNovaEmpre.add(fluFinaEmpre)
				.toString();
		for (int i = 0; i < 12; i++) {
			if (mesArray[i] == null) {
				mesArray[i] = "-";
			}
		}

		sensiVO.get(empresarial).setMeses(mesArray);

		mesArray = sensiVO.get(residencial).getMeses();

		/* FACO AQUI A SOMA DO FLUXOfINANCEIRO + VENDASnOVAS RESIDENCIAL */
		mesArray[Integer.parseInt(mes) - 1] = venNovaResid.add(fluFinaResid)
				.toString();
		for (int i = 0; i < 12; i++) {
			if (mesArray[i] == null) {
				mesArray[i] = "-";
			}
		}

		sensiVO.get(residencial).setMeses(mesArray);

		String[] atingidoEmpresarial = new String[12];
		String[] atingidoResidencial = new String[12];
		for (int i = 0; i < sensiVO.size(); i++) {
			for (int j = 0; j < 12; j++) {

				if (i == 0) { // bp empresarial
					BigDecimal bpEmpresarial = new BigDecimal(sensiVO.get(i)
							.getMeses()[j].contains(".") ? sensiVO.get(i)
							.getMeses()[j] : sensiVO.get(i).getMeses()[j]
							+ ".00");
					BigDecimal valorEmpresarial = new BigDecimal(sensiVO.get(
							empresarial).getMeses()[j].contains(".") ? sensiVO
							.get(empresarial).getMeses()[j] : sensiVO.get(
							empresarial).getMeses()[j]
							+ ".00");

					atingidoEmpresarial[j] = percentForm.format(Double
							.parseDouble(valorEmpresarial.toString().equals(
									"0.00") ? "0.00" : valorEmpresarial.divide(
									bpEmpresarial, BigDecimal.ROUND_HALF_UP)
									.toString()));
				}

				if (i == 2) { // bp residencial
					BigDecimal bpResidencial = new BigDecimal(sensiVO.get(i)
							.getMeses()[j].contains(".") ? sensiVO.get(i)
							.getMeses()[j] : sensiVO.get(i).getMeses()[j]
							+ ".00");
					BigDecimal valorResidencial = new BigDecimal(sensiVO.get(
							residencial).getMeses()[j].contains(".") ? sensiVO
							.get(residencial).getMeses()[j] : sensiVO.get(
							residencial).getMeses()[j]
							+ ".00");

					atingidoResidencial[j] = percentForm.format(Double
							.parseDouble(valorResidencial.toString().equals(
									"0.00") ? "0.00" : valorResidencial.divide(
									bpResidencial, BigDecimal.ROUND_HALF_UP)
									.toString()));
				}
			}
		}

		SensibilizacaoTotalizadorVO atingidoRD = new SensibilizacaoTotalizadorVO();
		SensibilizacaoTotalizadorVO atingidoEM = new SensibilizacaoTotalizadorVO();

		atingidoEM.setProduto("Atingido Empresarial");
		atingidoEM.setMeses(atingidoEmpresarial);

		atingidoRD.setProduto("Atingido Residencial");
		atingidoRD.setMeses(atingidoResidencial);

		for (int i = 0; i < sensiVO.size(); i++) {
			String[] arrayFormatadoMoeda = new String[12];
			for (int j = 0; j < 12; j++) {

				arrayFormatadoMoeda[j] = uti.insereSeparadoresMoeda(sensiVO
						.get(i).getMeses()[j]);

			}
			sensiVO.get(i).setMeses(arrayFormatadoMoeda);
		}

		sensiVO.add(atingidoEM);
		sensiVO.add(atingidoRD);

		return sensiVO;

	}

	/***********************************************************/
	/***********************************************************/
	/*******************************/
	/**** Relatorio de ACEITACAO ***/
	/*******************************/

	/**
	 * Retorna lista de coberturas e suas franquias
	 * 
	 * @return List<CoberturasVO>
	 * 
	 * */
	public List<CoberturasVO> validaSelecionaCoberturas() {
		VisaoOperacionalDAO dao = new VisaoOperacionalDAO();
		List<CoberturasVO> lista = dao.selecionaCoberturas();

		return lista;
	}

	/**
	 * Recebe a requisição dos dados inseridos na interface, faz alguns
	 * tratamentos para enviar um objeto para ser inserido no banco.
	 * 
	 * @return void
	 * 
	 */

	public String validaTrataDadosAceitacao(HttpServletRequest req) {

		for (Enumeration<String> e = req.getParameterNames(); e
				.hasMoreElements();) {
			System.out.println(e.nextElement());
		}
		// System.out.println(req.getParameter("nAceitacao"));
		// System.out.println(req.getParameter("nProposta"));
		// System.out.println(req.getParameter("segurado"));
		// System.out.println(req.getParameter("localRisco"));
		// System.out.println(req.getParameter("cpf"));
		// System.out.println(req.getParameter("combo"));
		// System.out.println(req.getParameter("inicioVig"));
		// System.out.println(req.getParameter("fimVig"));
		// System.out.println("is "
		// + req.getParameter("is").replace("_", "").replace("R$", "")
		// .replaceAll("^[.]+", ""));
		// System.out.println("premioLiq "
		// + req.getParameter("premioLiq").replace("_", "")
		// .replace("R$", "").replaceAll("^[.]+", ""));
		// System.out.println("premioNet "
		// + req.getParameter("premioNet").replace("_", "")
		// .replace("R$", "").replaceAll("^[.]+", ""));
		// System.out.println("premioRetido "
		// + req.getParameter("premioRetido").replace("_", "")
		// .replace("R$", "").replaceAll("^[.]+", ""));
		// System.out.println("premioCedido "
		// + req.getParameter("premioCedido").replace("_", "")
		// .replace("R$", "").replaceAll("^[.]+", ""));
		// System.out.println("limiS "
		// + req.getParameter("limiteSinistro").replace("_", "")
		// .replace("R$", "").replaceAll("^[.]+", ""));
		// System.out.println("Resseg "
		// + req.getParameter("partResseg").replace("_", "")
		// .replace("R$", "").replaceAll("^[.]+", ""));
		// System.out.println("partCaixa "
		// + req.getParameter("partCaixa").replace("_", "")
		// .replace("R$", "").replaceAll("^[.]+", ""));

		RelatorioAceitacaoVO relatorioAce = new RelatorioAceitacaoVO();

		relatorioAce.setNumeroAceitacao(req.getParameter("nAceitacao"));
		relatorioAce.setNumeroProposta(req.getParameter("nProposta")
				.replaceAll("[^0-9]", ""));
		relatorioAce.setSegurado(req.getParameter("segurado"));
		relatorioAce.setLocalRisco(req.getParameter("localRisco"));
		relatorioAce.setCpf(req.getParameter("cpf"));
		relatorioAce.setAtividadePrincipal(req.getParameter("combo"));
		relatorioAce.setInicioVig(req.getParameter("inicioVig"));
		relatorioAce.setFimVig(req.getParameter("fimVig"));
		relatorioAce
				.setIs(req.getParameter("is").replace("_", "")
						.replace("R$", "").replaceAll("[.]", "")
						.replaceAll("[,]", "."));
		relatorioAce.setPremioLiq(req.getParameter("premioLiq")
				.replace("_", "").replace("R$", "").replaceAll("[.]", "")
				.replaceAll("[,]", "."));
		relatorioAce.setPremioNet(req.getParameter("premioNet")
				.replace("_", "").replace("R$", "").replaceAll("[.]", "")
				.replaceAll("[,]", "."));
		relatorioAce.setPremioRetido(req.getParameter("premioRetido")
				.replace("_", "").replace("R$", "").replaceAll("[.]", "")
				.replaceAll("[,]", "."));
		relatorioAce.setPremioCedido(req.getParameter("premioCedido")
				.replace("_", "").replace("R$", "").replaceAll("[.]", "")
				.replaceAll("[,]", "."));
		relatorioAce.setLimiteSinistro(req.getParameter("limiteSinistro")
				.replace("_", "").replace("R$", "").replaceAll("[.]", "")
				.replaceAll("[,]", "."));
		relatorioAce.setPartResseg(req.getParameter("partResseg")
				.replace("_", "").replace("R$", "").replaceAll("[.]", "")
				.replaceAll("[,]", "."));
		relatorioAce.setPartCaixa(req.getParameter("partCaixa")
				.replace("_", "").replace("R$", "").replaceAll("[.]", "")
				.replaceAll("[,]", "."));

		String coberturas[] = req.getParameterValues("cobertura");
		// for (int i = 0; i < coberturas.length; i++) {
		// System.out.println(coberturas[i]);
		// }
		VisaoOperacionalDAO dao = new VisaoOperacionalDAO();
		List<CoberturasVO> listaCoberturas = dao.selecionaCoberturas();

		List<String> temp = new ArrayList<>();
		int i = 0;

		/* COBERTURAS */
		for (String idCobertura : coberturas) {
			// System.out.print(idCobertura + " ");
			// System.out.println(req.getParameter("cobertura" + i).replace("_",
			// "").replace("R$", "").replaceAll("^[.]+", ""));

			for (int j = 0; j < listaCoberturas.size(); j++) {
				if (Integer.parseInt(idCobertura) == listaCoberturas.get(j)
						.getIdCobertura()) {
					temp.add(idCobertura
							+ ";"
							+ listaCoberturas.get(j).getCobertura()
							+ ";"
							+ req.getParameter("cobertura" + i)
									.replace("_", "").replace("R$", "")
									.replaceAll("[.]", "")
									.replaceAll("[,]", ".") + ";"
							+ listaCoberturas.get(j).getFranquia());
				}
			}
			i++;
		}
		relatorioAce.setCobertura(temp);
		/* ------------- */

		/* PARECER TECNICO */
		/*
		 * Por algum motivo estava vindo o caractere "%u200B" do editor de
		 * texto, o processo a seguir retira este caractere:
		 */

		String parecer = req.getParameter("editor1").toString();
		// System.out.println("editor! " + parecer);
		String result = null;

		try {
			String jsEscapedString = parecer.replace("%u200B", "");
			ScriptEngineManager factory = new ScriptEngineManager();
			ScriptEngine engine = factory.getEngineByName("JavaScript");
			result = (String) engine
					.eval("unescape('" + jsEscapedString + "')");
		} catch (ScriptException e) {
			e.printStackTrace();
			System.out
					.println("ERRO metodo validaTrataDadosAceitacao VisaoOperacionalBO");
		}

		relatorioAce.setParecerTecnico(result);
		/* ------------ */

		// System.out.println(relatorioAce.getParecerTecnico());
		// for (int k = 0; k < relatorioAce.getCobertura().size(); k++) {
		// System.out.println(relatorioAce.getCobertura().get(k));
		// }

		String numeroAceitacao = dao
				.insereDadosRelatorioAceitacao(relatorioAce);
		System.out.println("inseriu");
		return numeroAceitacao;
	}

	/**
	 * Recebe a requisição dos dados inseridos na interface, faz alguns
	 * tratamentos para enviar um objeto para ser alterado no banco.
	 * 
	 * @return void
	 * 
	 */

	public String validaTrataDadosAceitacaoAlterar(HttpServletRequest req) {

		// for (Enumeration<String> e = req.getParameterNames(); e
		// .hasMoreElements();) {
		// System.out.println(e.nextElement());
		// }

		RelatorioAceitacaoVO relatorioAce = new RelatorioAceitacaoVO();
		relatorioAce.setNumeroAceitacao(req.getParameter("nAceitacao"));
		relatorioAce.setNumeroProposta(req.getParameter("nProposta")
				.replaceAll("[^0-9]", ""));
		relatorioAce.setSegurado(req.getParameter("segurado"));
		relatorioAce.setLocalRisco(req.getParameter("localRisco"));
		relatorioAce.setCpf(req.getParameter("cpf"));
		relatorioAce.setAtividadePrincipal(req.getParameter("combo"));
		relatorioAce.setInicioVig(req.getParameter("inicioVig"));
		relatorioAce.setFimVig(req.getParameter("fimVig"));
		relatorioAce
				.setIs(req.getParameter("is").replace("_", "")
						.replace("R$", "").replaceAll("[.]", "")
						.replaceAll("[,]", "."));
		relatorioAce.setPremioLiq(req.getParameter("premioLiq")
				.replace("_", "").replace("R$", "").replaceAll("[.]", "")
				.replaceAll("[,]", "."));
		relatorioAce.setPremioNet(req.getParameter("premioNet")
				.replace("_", "").replace("R$", "").replaceAll("[.]", "")
				.replaceAll("[,]", "."));
		relatorioAce.setPremioRetido(req.getParameter("premioRetido")
				.replace("_", "").replace("R$", "").replaceAll("[.]", "")
				.replaceAll("[,]", "."));
		relatorioAce.setPremioCedido(req.getParameter("premioCedido")
				.replace("_", "").replace("R$", "").replaceAll("[.]", "")
				.replaceAll("[,]", "."));
		relatorioAce.setLimiteSinistro(req.getParameter("limiteSinistro")
				.replace("_", "").replace("R$", "").replaceAll("[.]", "")
				.replaceAll("[,]", "."));
		relatorioAce.setPartResseg(req.getParameter("partResseg")
				.replace("_", "").replace("R$", "").replaceAll("[.]", "")
				.replaceAll("[,]", "."));
		relatorioAce.setPartCaixa(req.getParameter("partCaixa")
				.replace("_", "").replace("R$", "").replaceAll("[.]", "")
				.replaceAll("[,]", "."));

		String coberturas[] = req.getParameterValues("cobertura");
		// for (int i = 0; i < coberturas.length; i++) {
		// System.out.println(coberturas[i]);
		// }
		VisaoOperacionalDAO dao = new VisaoOperacionalDAO();
		List<CoberturasVO> listaCoberturas = dao.selecionaCoberturas();

		List<String> temp = new ArrayList<>();
		int i = 0;

		/* COBERTURAS */
		for (String idCobertura : coberturas) {
			// System.out.print(idCobertura + " ");
			// System.out.println(req.getParameter("cobertura" + i).replace("_",
			// "").replace("R$", "").replaceAll("^[.]+", ""));

			for (int j = 0; j < listaCoberturas.size(); j++) {
				if (Integer.parseInt(idCobertura) == listaCoberturas.get(j)
						.getIdCobertura()) {
					temp.add(idCobertura
							+ ";"
							+ listaCoberturas.get(j).getCobertura()
							+ ";"
							+ req.getParameter("cobertura" + i)
									.replace("_", "").replace("R$", "")
									.replaceAll("[.]", "")
									.replaceAll("[,]", ".") + ";"
							+ listaCoberturas.get(j).getFranquia());
				}
			}
			i++;
		}
		relatorioAce.setCobertura(temp);
		/* ------------- */

		/* PARECER TECNICO */
		/*
		 * Por algum motivo estava vindo o caractere "%u200B" do editor de
		 * texto, o processo a seguir retira este caractere:
		 */

		String parecer = req.getParameter("editor1").toString();
		// System.out.println("editor! " + parecer);
		String result = null;

		try {
			String jsEscapedString = parecer.replace("%u200B", "");
			ScriptEngineManager factory = new ScriptEngineManager();
			ScriptEngine engine = factory.getEngineByName("JavaScript");
			result = (String) engine
					.eval("unescape('" + jsEscapedString + "')");
		} catch (ScriptException e) {
			e.printStackTrace();
			System.out
					.println("ERRO metodo validaTrataDadosAceitacaoAlterar VisaoOperacionalBO");
		}

		relatorioAce.setParecerTecnico(result);
		/* ------------ */

		// System.out.println(relatorioAce.getParecerTecnico());
		// for (int k = 0; k < relatorioAce.getCobertura().size(); k++) {
		// System.out.println(relatorioAce.getCobertura().get(k));
		// }

		String numeroAceitacao = dao
				.alteraDadosRelatorioAceitacao(relatorioAce);
		System.out.println("alterou");
		return numeroAceitacao;
	}

	/**
	 * Retorna o maior id da tabela 'rra_relatorio_aceitacao' e somado com 1.
	 * 
	 * @return String
	 * 
	 */
	public String validaSelecionaMaiorNumeroAceitacao() {
		VisaoOperacionalDAO dao = new VisaoOperacionalDAO();
		String num = "";
		try {
			num = Integer.toString(Integer.parseInt(dao
					.selecionaMaiorNumeroAceitacao()) + 1);
		} catch (NumberFormatException nfe) {
			num = "40";
		}

		return num;
	}

	/**
	 * Retorna lista com dados de relarórios que já foram gerados`.
	 * 
	 * @return List<CoberturasVO>
	 * 
	 * */
	public List<RelatorioAceitacaoVO> validaSelecionaRelatoriosSalvos(
			String numAceitacao) {
		Uteis uteis = new Uteis();
		VisaoOperacionalDAO dao = new VisaoOperacionalDAO();
		List<RelatorioAceitacaoVO> lista = null;

		if (numAceitacao != null) {
			lista = dao.selecionaRelatoriosSalvos(numAceitacao);
			for (int i = 0; i < lista.size(); i++) {

				// System.out.println(lista.get(i).getPremioLiq());

				lista.get(i).setIs(
						uteis.insereSeparadoresMoeda(lista.get(i).getIs()));
				lista.get(i).setPremioLiq(
						uteis.insereSeparadoresMoeda(lista.get(i)
								.getPremioLiq()));
				lista.get(i).setPremioNet(
						uteis.insereSeparadoresMoeda(lista.get(i)
								.getPremioNet()));
				lista.get(i).setPremioRetido(
						uteis.insereSeparadoresMoeda(lista.get(i)
								.getPremioRetido()));
				lista.get(i).setPremioCedido(
						uteis.insereSeparadoresMoeda(lista.get(i)
								.getPremioCedido()));
				lista.get(i).setLimiteSinistro(
						uteis.insereSeparadoresMoeda(lista.get(i)
								.getLimiteSinistro()));
				lista.get(i).setPartResseg(
						uteis.insereSeparadoresMoeda(lista.get(i)
								.getPartResseg()));
				lista.get(i).setPartCaixa(
						uteis.insereSeparadoresMoeda(lista.get(i)
								.getPartCaixa()));
				lista.get(i).setInicioVig(
						uteis.editaData(lista.get(i).getInicioVig()));
				lista.get(i).setFimVig(
						uteis.editaData(lista.get(i).getFimVig()));
			}
		} else {
			lista = dao.selecionaRelatoriosSalvos(null);
		}

		for (int i = 0; i < lista.size(); i++) {
			lista.get(i).setParecerTecnico(
					lista.get(i).getParecerTecnico().replace("\'", "\""));
			lista.get(i).setCpf(lista.get(i).getCpf().replace("\'", "\""));
		}

		return lista;
	}

	/**
	 * Retorna lista com coberturas dos relarórios que já foram gerados`.
	 * 
	 * @return List<CoberturasVO>
	 * 
	 * */
	public List<CoberturasVO> validaSelecionaCoberturasRelatoriosSalvos(
			String numAceitacao) {
		Uteis uteis = new Uteis();
		VisaoOperacionalDAO dao = new VisaoOperacionalDAO();
		List<CoberturasVO> lista = null;
		if (numAceitacao != null) {
			lista = dao.selecionaCoberturasRelatoriosSalvos(numAceitacao);
			for (int i = 0; i < lista.size(); i++) {
				lista.get(i).setLMI(
						uteis.insereSeparadoresMoeda(lista.get(i).getLMI()));
			}
		} else {
			lista = dao.selecionaCoberturasRelatoriosSalvos(null);
		}

		for (int i = 0; i < lista.size(); i++) {
			lista.get(i).setCobertura(lista.get(i).getCobertura().trim());
		}

		return lista;
	}

	/**
	 * Retorna lista com coberturas dos relarórios que já foram gerados`.
	 * 
	 * @return List<CoberturasVO>
	 * 
	 * */
	public void validaGravaAlteracoesGrid(HttpServletRequest req) {
		VisaoOperacionalDAO dao = new VisaoOperacionalDAO();

		String colunaGrid = req.getParameter("colunaGrid");
		String numAcei = req.getParameter("numAcei");
		if (colunaGrid.equalsIgnoreCase("data_entrega")
				|| colunaGrid.equalsIgnoreCase("data_saida")) {
			long mili = Long.parseLong(req.getParameter("data"));
			String date = new java.text.SimpleDateFormat("yyyy-MM-dd")
					.format(new java.util.Date(mili));
			// /insere data
			dao.atualizaDataStatus(date, Integer.parseInt(numAcei), colunaGrid);
		} else {
			// System.out.println(numAcei + " " + req.getParameter("situacao"));
			int situacao = 0;

			switch (req.getParameter("situacao")) {
			case "true":
				situacao = 1;
				break;
			case "false":
				situacao = 0;
				break;
			}
			dao.atualizaDataStatus(Integer.toString(situacao),
					Integer.parseInt(numAcei),
					colunaGrid.replace("status", "status_checkbox"));
		}

	}

}
