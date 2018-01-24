package caixa.dirid.BO;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.imageio.stream.IIOByteBuffer;
import javax.servlet.http.HttpServletResponse;
import caixa.dirid.DAO.VisaoExecutiva_Diaria_DAO;
import caixa.dirid.UTEIS.Uteis;
import caixa.dirid.VO.DadosDiariosVO;
import caixa.dirid.VO.FaturamentoRO_base_VO;
import caixa.dirid.VO.FaturamentoVO;
import caixa.dirid.VO.MovimentoPorCanalDeVendaVO;
import caixa.dirid.VO.SinistroPendente_FaixaVO;
import caixa.dirid.VO.SinistroPendente_base_FaixaVO;
import caixa.dirid.VO.Sinistros_base_VO;

/**
 * Faz exatamente as mesmas coisas que a classe VisaoExecutivaBO faz, porem essa
 * classe trata da geracao dos dados diarios.
 *
 */
public class VisaoExecutiva_Diaria_BO {

	private List<FaturamentoVO> dadosFaturamentoDetalhado;
	private List<FaturamentoVO> dadosFaturamentoDetalhadoAnoAnterior;

	private List<FaturamentoVO> rvneTipo2;
	private List<FaturamentoVO> rvneTipo2AnoAnterior;

	private List<FaturamentoVO> dadosFaturamentoTotal;
	private List<FaturamentoVO> dadosFaturamentoTotalAnoAnterior;

	private List<SinistroPendente_FaixaVO> listaSinistroPendenteValor;
	private List<SinistroPendente_FaixaVO> listaSinistroPendenteTempo;

	private List<MovimentoPorCanalDeVendaVO> listaEmissaoPorCanal;

	Uteis uteis;

	public VisaoExecutiva_Diaria_BO(String ano) {
		this.uteis = new Uteis();
		VisaoExecutiva_Diaria_DAO dao = new VisaoExecutiva_Diaria_DAO();
		String anoAnterior = Integer.toString((Integer.parseInt(uteis.cortaRetornaAno(ano)) - 1));

		this.dadosFaturamentoDetalhado = dao.selecionaDadosFaturamento(ano);
		this.dadosFaturamentoDetalhadoAnoAnterior = dao
				.selecionaDadosFaturamento(uteis.cortaRetornaProduto(ano) + anoAnterior);

		this.dadosFaturamentoTotal = dao.selecionaFaturamentoTotal(ano);
		this.dadosFaturamentoTotalAnoAnterior = dao
				.selecionaFaturamentoTotal(uteis.cortaRetornaProduto(ano) + anoAnterior);

		this.listaEmissaoPorCanal = dao.selecionaMovimentoPorCanal(ano);

		this.rvneTipo2 = dao.selecionaRVNE(ano, 2, "0");
		this.rvneTipo2AnoAnterior = dao.selecionaRVNE(uteis.cortaRetornaProduto(ano) + anoAnterior, 2, "0");

		this.listaSinistroPendenteTempo = dao.selecionaSinistroPendente_Faixa(1, ano);
		this.listaSinistroPendenteValor = dao.selecionaSinistroPendente_Faixa(2, ano);
	}

	public VisaoExecutiva_Diaria_BO(String ano, String categConstrutor) {
		System.out.println("VisaoExecutiva_Diaria_BO - Download de Analitico - " + categConstrutor);

	}

	/**
	 * Retorna uma lista contendo na primeira linha o BP e na segunda o Faturamento.
	 * Os dados ficam no formato '21.5'.
	 * 
	 * @arguments String
	 * 
	 * @return List<RvneVO>
	 */
	public List<FaturamentoVO> validaSelecionaTotalFaturamento(String ano, String anoTexto) {
		DecimalFormat roundForm = new DecimalFormat("0.0");

		List<FaturamentoVO> listaTratada = new ArrayList<FaturamentoVO>();
		List<FaturamentoVO> lista = null;
		List<FaturamentoVO> listaRvne = null;

		switch (anoTexto) {
		case "atual":
			lista = dadosFaturamentoDetalhado;
			listaRvne = rvneTipo2;
			break;
		case "anterior":
			lista = dadosFaturamentoDetalhadoAnoAnterior;
			listaRvne = rvneTipo2AnoAnterior;
			break;
		}

		BigDecimal totalMes = new BigDecimal(0.0);

		// Insere BP na lista
		int i = 0;
		FaturamentoVO faturamentoVObp = new FaturamentoVO();
		faturamentoVObp.setProduto(lista.get(i).getProduto());
		String mesesTotalBP[] = new String[12];

		for (int k = 0; k < 12; k++) {
			mesesTotalBP[k] = roundForm.format(Double.parseDouble(lista.get(i).getMeses()[k]) / 1000000).replace(",",
					".");
		}

		faturamentoVObp.setMeses(mesesTotalBP);
		listaTratada.add(faturamentoVObp);

		// i = 1;
		// Trata apenas do Faturamento:
		FaturamentoVO faturamentoVOtratado = new FaturamentoVO();
		String mesesTotal[] = new String[12];
		faturamentoVOtratado.setProduto("Faturamento ");
		for (int j = 0; j < 12; j++) {
			int c = 0;
			while (c < lista.size()) {
				if (lista.get(c).getProduto().equalsIgnoreCase("EMITIDO")
						|| lista.get(c).getProduto().equalsIgnoreCase("EMITIDOS CANCELADOS")
						|| lista.get(c).getProduto().equalsIgnoreCase("EMITIDOS RESTITUIDOS")) {

					totalMes = totalMes.add(new BigDecimal(lista.get(c).getMeses()[j]));
				}
				c++;
			}
			// RVNE trazido direto da tabela do banco
			if (listaRvne.get(0).getMeses()[j] != null) {
				totalMes = totalMes.add(new BigDecimal(listaRvne.get(0).getMeses()[j]));
			} else {
				totalMes = new BigDecimal(0.0);
			}
			mesesTotal[j] = roundForm.format(Double.parseDouble(totalMes.toString()) / 1000000).replace(",", ".");
			totalMes = new BigDecimal(0.0D);
		}
		faturamentoVOtratado.setMeses(mesesTotal);
		listaTratada.add(faturamentoVOtratado);

		return listaTratada;
	}// fim validaSelecionaTotalFaturamento

	/**
	 * Retorna uma lista que no indice 0 traz o acumulado do BP e no indice 1 o
	 * acumulado do ano passado pelo parâmetro. Retorna formatado com uma casa
	 * decimal. Exemplo: '31.0'
	 * 
	 * @return List<RvneVO>
	 * 
	 * @argument String
	 */

	public List<FaturamentoVO> validaSelecionaTotalFaturamentoAcumulado(String ano, int retorno, String anoTexto) {
		Uteis uteis = new Uteis();
		DecimalFormat roundForm = new DecimalFormat("0.0");
		List<FaturamentoVO> listaTratada = new ArrayList<FaturamentoVO>();

		List<FaturamentoVO> lista = null;
		List<FaturamentoVO> listaBP = null;

		switch (anoTexto) {
		case "atual":
			listaBP = dadosFaturamentoDetalhado;
			lista = dadosFaturamentoTotal;
			break;
		case "anterior":
			listaBP = dadosFaturamentoDetalhadoAnoAnterior;
			lista = dadosFaturamentoTotalAnoAnterior;
			break;
		}

		double somaAnterior = 0.0D;
		double somaAnteriorBP = 0.0D;

		FaturamentoVO faturamentoVOtratado = new FaturamentoVO();
		FaturamentoVO faturamentoVOBP = new FaturamentoVO();
		String mesesTotal[] = new String[12];
		String mesesTotalBP[] = new String[12];

		int varBP = 0;
		int i = 0;

		for (int j = 0; j < listaBP.size(); j++) {
			if (listaBP.get(j).getProduto().equals("BP")) {
				varBP = j; // pega indice do BP
			}
		}

		faturamentoVOBP.setProduto("BP - " + uteis.cortaRetornaAno(ano));
		for (int j = 0; j < 12; j++) {
			if (j == 0) {
				mesesTotalBP[j] = roundForm.format(Double.parseDouble(listaBP.get(varBP).getMeses()[j]) / 1000000)
						.replace(",", ".");
			} else {
				double somaBP = 0.0D;
				if (j >= 2) {
					somaBP = Double.parseDouble(listaBP.get(varBP).getMeses()[j]) + somaAnteriorBP;
				} else {
					// j na posicao 1
					somaBP = Double.parseDouble(listaBP.get(varBP).getMeses()[j])
							+ Double.parseDouble(listaBP.get(varBP).getMeses()[j - 1]);
				}
				somaAnteriorBP = somaBP;
				mesesTotalBP[j] = roundForm.format(somaAnteriorBP / 1000000).replace(",", ".");
			}
		}
		faturamentoVOBP.setMeses(mesesTotalBP);
		listaTratada.add(faturamentoVOBP);

		faturamentoVOtratado.setProduto("Faturamento Acumulado - " + uteis.cortaRetornaAno(ano));

		// retorno == 1 retorna no formato: '0.0'
		if (retorno == 1) {
			double faturamento = 0.0D;
			double fatuAnterior = 0.0D;
			double soma = 0.0D;
			for (int j = 0; j < 12; j++) {

				// aqui faço a criação do faturamento, junto à RVNE
				faturamento = Double.parseDouble(lista.get(i).getMeses()[j]);
				if (j == 0) {
					mesesTotal[j] = roundForm.format(faturamento / 1000000).replace(",", ".");
					fatuAnterior = faturamento;
					somaAnterior = faturamento;
				} else {
					if (j >= 2) {
						soma = faturamento + somaAnterior;
					} else {
						soma = faturamento + fatuAnterior;
					}
					// serve para completar os outros meses com 0
					if (soma != somaAnterior) {
						somaAnterior = soma;
						mesesTotal[j] = roundForm.format(somaAnterior / 1000000).replace(",", ".");
					} else {
						mesesTotal[j] = "0.0";
					}
				}

			}
			// retorno == 2 retorna no formato: '1311560.62560'
		} else if (retorno == 2) {
			double faturamento = 0.0D;
			double fatuAnterior = 0.0D;
			double soma = 0.0D;
			for (int j = 0; j < 12; j++) {

				// aqui faço a criação do faturamento, junto à RVNE
				faturamento = Double.parseDouble(lista.get(i).getMeses()[j]);

				if (j == 0) {
					mesesTotal[j] = Double.toString(faturamento);
					fatuAnterior = faturamento;
					somaAnterior = faturamento;
				} else {
					if (j >= 2) {
						soma = faturamento + somaAnterior;
					} else {
						soma = faturamento + fatuAnterior;
					}
					// serve para completar os outros meses com 0
					if (soma != somaAnterior) {
						somaAnterior = soma;
						mesesTotal[j] = Double.toString(somaAnterior);
					} else {
						mesesTotal[j] = "0.0";
					}
				}
			}
		}
		faturamentoVOtratado.setMeses(mesesTotal);
		listaTratada.add(faturamentoVOtratado);
		for (FaturamentoVO string : listaTratada) {
			// System.out.println(string.getProduto());
			for (int j = 0; j < 12; j++) {

				// System.out.println("Aqui - " + string.getMeses()[j]);
			}
		}
		return listaTratada;

	}// fim validaSelecionaTotalFaturamentoAcumulado

	/**
	 * Retorna uma lista contendo os Tipos: BP(para ano atual), Emitido, Emitidos
	 * Cancelados, Emitidos Restituidos, RVNE e o Total. O Total se trata da soma
	 * entre todos os citados com exceção do BP. No caso do ano passado por
	 * parâmetro ser o ano atual, irá retornar na primeira linha o BP. O Retorno é
	 * feito em milhões com 2 casas decimais. Exemplo: "2.000.000,00"
	 * 
	 * @arguments String
	 * 
	 * @return List<RvneVO>
	 */
	public List<FaturamentoVO> validaSelecionaFaturamentoDetalhado(String ano, String anoTexto) {
		DecimalFormat roundForm = new DecimalFormat("0.00");
		Uteis uteis = new Uteis();
		List<FaturamentoVO> listaTratada = new ArrayList<FaturamentoVO>();
		List<FaturamentoVO> listaRvne = null;
		List<FaturamentoVO> lista = null;

		switch (anoTexto) {
		case "atual":
			lista = dadosFaturamentoDetalhado;
			listaRvne = rvneTipo2;
			break;
		case "anterior":
			lista = dadosFaturamentoDetalhadoAnoAnterior;
			listaRvne = rvneTipo2AnoAnterior;
			break;
		}

		FaturamentoVO faturamentoVOtotal = new FaturamentoVO();

		for (int i = 0; i < lista.size(); i++) {

			if (lista.get(i).getProduto().equals("QT APOLICES EMITIDAS")) {

				FaturamentoVO faturamentoVOtratado = new FaturamentoVO();
				String mesesTratado[] = new String[13];
				faturamentoVOtratado.setProduto(lista.get(i).getProduto());

				int somaTotal = 0;
				for (int k = 0; k <= 12; k++) {

					if (k != 12) {
						somaTotal += Integer.parseInt(lista.get(i).getMeses()[k]);

						mesesTratado[k] = lista.get(i).getMeses()[k];

					} else {// total
						mesesTratado[k] = String.valueOf(somaTotal);
					}

					faturamentoVOtratado.setMeses(mesesTratado);
				}
				listaTratada.add(faturamentoVOtratado);

			} else if (lista.get(i).getProduto().equals("EMITIDO")
					|| lista.get(i).getProduto().equals("EMITIDOS CANCELADOS")
					|| lista.get(i).getProduto().equals("EMITIDOS RESTITUIDOS")
					|| lista.get(i).getProduto().equals("BP")) {
				FaturamentoVO faturamentoVOtratado = new FaturamentoVO();
				String mesesTratado[] = new String[13];

				faturamentoVOtratado.setProduto(lista.get(i).getProduto());

				double somaTotal = 0.0;
				for (int k = 0; k <= 12; k++) {

					if (k != 12) {
						somaTotal += Double.parseDouble(lista.get(i).getMeses()[k]);

						String tratada = roundForm.format(Double.parseDouble(lista.get(i).getMeses()[k]));

						mesesTratado[k] = uteis.insereSeparadores(tratada);

					} else {// total
						String tratada = roundForm.format(somaTotal);

						mesesTratado[k] = uteis.insereSeparadores(tratada);
					}
					faturamentoVOtratado.setMeses(mesesTratado);
				}
				listaTratada.add(faturamentoVOtratado);

			}
		}

		for (int i = 0; i < listaRvne.size(); i++) {

			FaturamentoVO apenasRVNE = new FaturamentoVO();
			String mesesRVNE[] = new String[13];
			apenasRVNE.setProduto(listaRvne.get(i).getProduto());

			double somaTotal = 0.0D;
			for (int k = 0; k <= 12; k++) {

				if (k != 12) {

					String tratada = "";
					if (listaRvne.get(i).getMeses()[k] != null) {
						tratada = roundForm.format(Double.parseDouble(listaRvne.get(i).getMeses()[k]));
					} else {
						tratada = roundForm.format(Double.parseDouble("0.0"));
					}

					somaTotal += Double.parseDouble(tratada.replace(",", "."));
					mesesRVNE[k] = uteis.insereSeparadores(tratada);

				} else { // total

					String tratada = roundForm.format(somaTotal);
					mesesRVNE[k] = uteis.insereSeparadores(tratada);

				}

				apenasRVNE.setMeses(mesesRVNE);
			}
			listaTratada.add(apenasRVNE);
		}

		faturamentoVOtotal.setProduto("<b>Total</b>");
		String mesesTotal[] = new String[13];

		for (int j = 0; j <= 12; j++) {
			double total1 = 0.0D;
			int k = 0;

			while (k < listaTratada.size()) {
				if (listaTratada.get(k).getProduto().toString().contains("BP")
						|| listaTratada.get(k).getProduto().toString().contains("QT")) {
					k++;
				}
				total1 += Double.parseDouble(listaTratada.get(k).getMeses()[j].replace(".", "").replace(",", "."));
				k++;
			}
			mesesTotal[j] = uteis.insereSeparadores(roundForm.format(total1));
		}
		faturamentoVOtotal.setMeses(mesesTotal);
		listaTratada.add(faturamentoVOtotal);

		return listaTratada;
	}// fim validaSelecionaFaturamentoDetalhado

	/**
	 * Retorna uma lista com a divisão do Emitido Cancelado por Emitidos do ano
	 * passado por parâmetro. O Retorno é feito em porcentagem.
	 * 
	 * @return List<FaturamentoVO>
	 * @arguments String
	 * 
	 */
	public List<FaturamentoVO> validaCanceladosDividoPorEmitidos(String ano, String anoTexto) {
		DecimalFormat percentForm = new DecimalFormat("0%");
		List<FaturamentoVO> listaTratada = new ArrayList<FaturamentoVO>();
		List<FaturamentoVO> lista = null;

		switch (anoTexto) {
		case "atual":
			lista = dadosFaturamentoDetalhado;
			break;
		case "anterior":
			lista = dadosFaturamentoDetalhadoAnoAnterior;
			break;
		}

		FaturamentoVO faturamentoVO = new FaturamentoVO();
		String mesesTotal[] = new String[12];

		faturamentoVO.setProduto("Representacao Cancelados ");
		for (int i = 0; i < 12; i++) {
			double total = 0.0D;

			int j = 1;
			BigDecimal bigAnoAtual = new BigDecimal(lista.get(j + 1).getMeses()[i]);
			try {
				total = Double.parseDouble(
						(bigAnoAtual.divide(new BigDecimal(lista.get(j).getMeses()[i]), 4, RoundingMode.HALF_DOWN))
								.toString());
			} catch (Exception e) {
				total = 0D;
			}
			mesesTotal[i] = percentForm.format(total);
			faturamentoVO.setMeses(mesesTotal);
		}
		listaTratada.add(faturamentoVO);

		return listaTratada;
	} // fim validaCanceladosDividoPorEmitidos

	/**
	 * Retorna uma lista contendo valores da divisao entre o ano atual acumulado
	 * pelo ano anterior acumulado - 1. Este valor é convertido para a forma
	 * percentual.
	 * 
	 * @return List<FaturamentoVO>
	 */
	public List<FaturamentoVO> validaAnoAtualDividoPorAnoAnteriorAcumulado(String ano) {
		DecimalFormat percentForm = new DecimalFormat("0%");
		List<FaturamentoVO> listaTratada = new ArrayList<FaturamentoVO>();
		Uteis uteis = new Uteis();
		String tipo = uteis.cortaRetornaProduto(ano);
		String anoAtual = uteis.cortaRetornaAno(ano);
		String anoAnterior = Integer.toString((Integer.parseInt(anoAtual) - 1));

		List<FaturamentoVO> listaAnoAtual = validaSelecionaTotalFaturamentoAcumulado(tipo + anoAtual, 2, "atual");
		List<FaturamentoVO> listaAnoAnterior = validaSelecionaTotalFaturamentoAcumulado(tipo + anoAnterior, 2,
				"anterior");
		FaturamentoVO faturamentoVO = new FaturamentoVO();
		String mesesTotal[] = new String[12];

		faturamentoVO.setProduto("anoAtual/anoAnterior % ");

		for (int i = 0; i < 12; i++) {
			BigDecimal bigAnoAtual = new BigDecimal(listaAnoAtual.get(1).getMeses()[i]);
			double total = 0.0D;

			/*
			 * total = (Double.parseDouble(listaAnoAtual.get(1).getMeses()[i]) / Double
			 * .parseDouble(listaAnoAnterior.get(1).getMeses()[i])) - 1;
			 */
			try {
				total = Double.parseDouble((bigAnoAtual.divide(new BigDecimal(listaAnoAnterior.get(1).getMeses()[i]), 4,
						RoundingMode.HALF_DOWN)).toString()) - 1;
			} catch (Exception e) {
				total = 0D;
			}
			mesesTotal[i] = percentForm.format(total);

			faturamentoVO.setMeses(mesesTotal);
		}
		listaTratada.add(faturamentoVO);
		return listaTratada;
	}// fim validaAnoAtualDividoPorAnoAnteriorAcumulado

	/**
	 * Retorna uma lista contendo valores da divisao entre o ano atual pelo ano
	 * anterior - 1. Este valor é convertido para a forma percentual.
	 * 
	 * @return List<FaturamentoVO>
	 */
	public List<FaturamentoVO> validaAnoAtualDividoPorAnoAnterior(String ano) {
		DecimalFormat percentForm = new DecimalFormat("0%");
		List<FaturamentoVO> listaTratada = new ArrayList<FaturamentoVO>();
		List<FaturamentoVO> listaAnoAtual = dadosFaturamentoTotal;
		List<FaturamentoVO> listaAnoAnterior = dadosFaturamentoTotalAnoAnterior;
		FaturamentoVO faturamentoVO = new FaturamentoVO();
		String mesesTotal[] = new String[12];

		faturamentoVO.setProduto("atual/anterior % ");
		for (int i = 0; i < 12; i++) {

			BigDecimal bigAnoAtual = new BigDecimal(listaAnoAtual.get(0).getMeses()[i]);
			double total = 0.0D;

			try {
				total = Double.parseDouble((bigAnoAtual.divide(new BigDecimal(listaAnoAnterior.get(0).getMeses()[i]), 4,
						RoundingMode.HALF_DOWN)).toString()) - 1;
			} catch (Exception e) {
				total = 0D;
			}
			mesesTotal[i] = percentForm.format(total);
			faturamentoVO.setMeses(mesesTotal);
		}
		listaTratada.add(faturamentoVO);

		return listaTratada;
	}// fim validaAnoAtualDividoPorAnoAnterior

	/**
	 * Retorna uma lista contendo os seguintes itens: Qtd. Sinistros Avisados
	 * AnoParam | Valor Sinistros Avisados AnoParam | Qtd. Sinistros Avisados
	 * AnoAnterior | Valor Sinistros Avisados Ano Anterior | Variacao Qtd | Variacao
	 * Valor | Aviso Medio AnoParam | Aviso Medio AnoAnterior | Variacao Aviso Medio
	 * 
	 * 
	 * @return List<FaturamentoVO>
	 */
	public List<FaturamentoVO> validaSelecionaSinistros(String ano, int tipo) {
		DecimalFormat roundForm = new DecimalFormat("0.00");

		DecimalFormat percentForm = new DecimalFormat("0%");

		Uteis uteis = new Uteis();
		String anoAnterior = Integer.toString((Integer.parseInt(uteis.cortaRetornaAno(ano)) - 1));

		List<FaturamentoVO> listaTratada = new ArrayList<FaturamentoVO>();

		List<FaturamentoVO> listaAnoParam = dadosFaturamentoDetalhado;
		List<FaturamentoVO> listaAnoAnterior = dadosFaturamentoDetalhadoAnoAnterior;

		String quantidadeTextoBancoDados = "";
		String quantidadeTextoApresentacao = "";
		String valorTextoBancoDados = "";
		String valorTextoApresentacao = "";

		switch (tipo) {
		case 1: // AVISADO
			quantidadeTextoBancoDados = "QT SINISTROS AVISADOS";
			quantidadeTextoApresentacao = "&nbsp;&nbsp;&nbsp;Quantidade&nbsp;&nbsp;&nbsp;";
			valorTextoBancoDados = "SINISTROS AVISADOS";
			valorTextoApresentacao = "Valor";
			break;
		case 2: // INDENIZADO
			quantidadeTextoBancoDados = "QT SINISTROS INDENIZADOS";
			quantidadeTextoApresentacao = "&nbsp;&nbsp;&nbsp;Quantidade&nbsp;&nbsp;&nbsp;";
			valorTextoBancoDados = "SINISTROS INDENIZADOS";
			valorTextoApresentacao = "Valor";
			break;
		case 3: // PENDENTE
			quantidadeTextoBancoDados = "QT SINISTROS PENDENTES";
			quantidadeTextoApresentacao = "&nbsp;&nbsp;&nbsp;Quantidade&nbsp;&nbsp;&nbsp;";
			valorTextoBancoDados = "SINISTROS PENDENTES";
			valorTextoApresentacao = "Valor";
			break;
		case 4: // DESPESA
			quantidadeTextoBancoDados = "QT SINISTROS - DESPESAS";
			quantidadeTextoApresentacao = "&nbsp;&nbsp;&nbsp;Quantidade&nbsp;&nbsp;&nbsp;";
			valorTextoBancoDados = "SINISTROS - DESPESAS";
			valorTextoApresentacao = "Valor";
			break;

		}

		for (int i = 0; i < listaAnoParam.size(); i++) {
			if (listaAnoParam.get(i).getProduto().equals(quantidadeTextoBancoDados)) {

				FaturamentoVO sinistroVOtratado = new FaturamentoVO();
				String mesesTratado[] = new String[13];

				sinistroVOtratado.setAno(uteis.cortaRetornaAno(ano));
				sinistroVOtratado.setProduto(quantidadeTextoApresentacao);

				int somaTotal = 0;
				for (int k = 0; k <= 12; k++) {

					if (k != 12) {
						somaTotal += Integer.parseInt(listaAnoParam.get(i).getMeses()[k]);

						mesesTratado[k] = uteis.insereSeparadores(listaAnoParam.get(i).getMeses()[k]);

					} else { // total

						mesesTratado[k] = uteis.insereSeparadores(String.valueOf(somaTotal));

					}
					sinistroVOtratado.setMeses(mesesTratado);
				}
				listaTratada.add(sinistroVOtratado);

			} else if (listaAnoParam.get(i).getProduto().equals(valorTextoBancoDados)) {

				FaturamentoVO faturamentoVOtratado = new FaturamentoVO();
				String mesesTratado[] = new String[13];

				faturamentoVOtratado.setAno(uteis.cortaRetornaAno(ano));
				faturamentoVOtratado.setProduto(valorTextoApresentacao);

				double somaTotal = 0.0;
				for (int k = 0; k <= 12; k++) {

					if (k != 12) {

						somaTotal += Double.parseDouble(listaAnoParam.get(i).getMeses()[k]);

						String tratada = roundForm.format(Double.parseDouble(listaAnoParam.get(i).getMeses()[k]));

						mesesTratado[k] = uteis.insereSeparadores(tratada);

					} else { // total

						String tratada = roundForm.format(somaTotal);

						mesesTratado[k] = uteis.insereSeparadores(tratada);

					}

					faturamentoVOtratado.setMeses(mesesTratado);
				}
				listaTratada.add(faturamentoVOtratado);

			}
		} // for anoParam

		for (int i = 0; i < listaAnoAnterior.size(); i++) {
			if (listaAnoAnterior.get(i).getProduto().equals(quantidadeTextoBancoDados)) {

				FaturamentoVO sinistroVOtratado = new FaturamentoVO();
				String mesesTratado[] = new String[13];

				sinistroVOtratado.setAno(anoAnterior);
				sinistroVOtratado.setProduto(quantidadeTextoApresentacao);

				int somaTotal = 0;
				for (int k = 0; k <= 12; k++) {

					if (k != 12) {

						somaTotal += Integer.parseInt(listaAnoAnterior.get(i).getMeses()[k]);

						mesesTratado[k] = uteis.insereSeparadores(listaAnoAnterior.get(i).getMeses()[k]);

					} else { // total

						mesesTratado[k] = uteis.insereSeparadores(String.valueOf(somaTotal));

					}
					sinistroVOtratado.setMeses(mesesTratado);

				}
				listaTratada.add(sinistroVOtratado);

			} else if (listaAnoAnterior.get(i).getProduto().equals(valorTextoBancoDados)) {
				FaturamentoVO faturamentoVOtratado = new FaturamentoVO();
				String mesesTratado[] = new String[13];

				faturamentoVOtratado.setAno(anoAnterior);
				faturamentoVOtratado.setProduto(valorTextoApresentacao);

				double somaTotal = 0.0;
				for (int k = 0; k <= 12; k++) {

					if (k != 12) {

						somaTotal += Double.parseDouble(listaAnoAnterior.get(i).getMeses()[k]);

						String tratada = roundForm.format(Double.parseDouble(listaAnoAnterior.get(i).getMeses()[k]));

						mesesTratado[k] = uteis.insereSeparadores(tratada);

					} else { // total

						String tratada = roundForm.format(somaTotal);

						mesesTratado[k] = uteis.insereSeparadores(tratada);

					}

					faturamentoVOtratado.setMeses(mesesTratado);
				}
				listaTratada.add(faturamentoVOtratado);

			}
		} // for anoAnterior

		byte qtdAnoParam = 0;
		byte qtdAnoAnterior = 2;

		byte vlrAnoParam = 1;
		byte vlrAnoAnterior = 3;

		// *******************************************
		// *******************************************
		// Variacao 16/15 QTD
		String[] mesesQtdTratado = new String[13];
		double totalQtd = 0.0D;
		FaturamentoVO variacaoQtdVO = new FaturamentoVO();

		for (int j = 0; j <= 12; j++) {
			BigDecimal bigQtdAnoParam = new BigDecimal(
					Double.parseDouble(listaTratada.get(qtdAnoParam).getMeses()[j].replace(".", "")));

			try {
				totalQtd = Double.parseDouble((bigQtdAnoParam.divide(
						new BigDecimal(listaTratada.get(qtdAnoAnterior).getMeses()[j].replace(".", "")), 4,
						RoundingMode.HALF_DOWN)).toString()) - 1;
			} catch (Exception e) {
				totalQtd = 0D;
			}

			mesesQtdTratado[j] = percentForm.format(totalQtd);

		}
		variacaoQtdVO.setProduto("Varia&ccedil;&atilde;o Qtd.");
		variacaoQtdVO.setMeses(mesesQtdTratado);
		listaTratada.add(variacaoQtdVO);

		// *******************************************
		// *******************************************
		// Variacao 16/15 Valor
		String[] mesesVlrTratado = new String[13];
		double totalVlr = 0.0D;
		FaturamentoVO variacaoVlrVO = new FaturamentoVO();

		for (int j = 0; j <= 12; j++) {

			BigDecimal bigVlrAnoParam = new BigDecimal(
					Double.parseDouble(listaTratada.get(vlrAnoParam).getMeses()[j].replace(".", "").replace(",", ".")));

			try {
				totalVlr = Double.parseDouble((bigVlrAnoParam.divide(
						new BigDecimal(
								listaTratada.get(vlrAnoAnterior).getMeses()[j].replace(".", "").replace(",", ".")),
						4, RoundingMode.HALF_DOWN)).toString()) - 1;
			} catch (Exception e) {
				totalVlr = 0D;
			}

			mesesVlrTratado[j] = percentForm.format(totalVlr);

		}
		variacaoVlrVO.setProduto("Varia&ccedil;&atilde;o Valor");
		variacaoVlrVO.setMeses(mesesVlrTratado);
		listaTratada.add(variacaoVlrVO);

		// *******************************************
		// *******************************************
		// Aviso Medio anoParam - anoAtual
		String[] mesesAvisoMedioAnoParamTratado = new String[13];
		double totalAvisoMedioAnoParam = 0.0D;
		FaturamentoVO variacaoAvisoMedioAnoParamVO = new FaturamentoVO();

		for (int j = 0; j <= 12; j++) {

			BigDecimal bigVlrAnoParam = new BigDecimal(
					Double.parseDouble(listaTratada.get(vlrAnoParam).getMeses()[j].replace(".", "").replace(",", ".")));

			try {
				totalAvisoMedioAnoParam = Double.parseDouble((bigVlrAnoParam.divide(
						new BigDecimal(listaTratada.get(qtdAnoParam).getMeses()[j].replace(".", "").replace(",", ".")),
						2, RoundingMode.HALF_DOWN)).toString());
			} catch (Exception e) {
				totalAvisoMedioAnoParam = 0D;
			}
			mesesAvisoMedioAnoParamTratado[j] = uteis.insereSeparadoresMoeda(roundForm.format(totalAvisoMedioAnoParam));

		}
		variacaoAvisoMedioAnoParamVO.setAno(uteis.cortaRetornaAno(ano));
		variacaoAvisoMedioAnoParamVO.setProduto("Aviso M&eacute;dio");
		variacaoAvisoMedioAnoParamVO.setMeses(mesesAvisoMedioAnoParamTratado);
		listaTratada.add(variacaoAvisoMedioAnoParamVO);

		// *******************************************
		// *******************************************
		// Aviso Medio anoAnterior
		String[] mesesAvisoMedioAnoAnteriorTratado = new String[13];
		double totalAvisoMedioAnoAnterior = 0.0D;
		FaturamentoVO variacaoAvisoMedioAnoAnteriorVO = new FaturamentoVO();

		for (int j = 0; j <= 12; j++) {

			BigDecimal bigVlrAnoAnterior = new BigDecimal(Double
					.parseDouble(listaTratada.get(vlrAnoAnterior).getMeses()[j].replace(".", "").replace(",", ".")));

			try {
				totalAvisoMedioAnoAnterior = Double.parseDouble((bigVlrAnoAnterior.divide(
						new BigDecimal(listaTratada.get(qtdAnoAnterior).getMeses()[j].replace(".", "")), 2,
						RoundingMode.HALF_DOWN)).toString());
			} catch (Exception e) {
				totalAvisoMedioAnoAnterior = 0D;
			}
			mesesAvisoMedioAnoAnteriorTratado[j] = uteis
					.insereSeparadoresMoeda(roundForm.format(totalAvisoMedioAnoAnterior));

		}
		variacaoAvisoMedioAnoAnteriorVO.setAno(anoAnterior);
		variacaoAvisoMedioAnoAnteriorVO.setProduto("Aviso M&eacute;dio");
		variacaoAvisoMedioAnoAnteriorVO.setMeses(mesesAvisoMedioAnoAnteriorTratado);
		listaTratada.add(variacaoAvisoMedioAnoAnteriorVO);

		// *******************************************
		// *******************************************
		// Variacao Media
		short avisoMedioAnoParam = 6;
		short avisoMedioAnoAnterior = 7;

		String[] meses_AM_Tratado = new String[13];// AM -aviso medio
		double total_AM = 0.0D;
		FaturamentoVO variacao_AM_VO = new FaturamentoVO();

		for (int j = 0; j <= 12; j++) {

			BigDecimal big_AM_AnoParam = new BigDecimal(Double.parseDouble(
					listaTratada.get(avisoMedioAnoParam).getMeses()[j].replace(".", "").replace(",", ".")));

			try {
				total_AM = Double
						.parseDouble((big_AM_AnoParam
								.divide(new BigDecimal(listaTratada.get(avisoMedioAnoAnterior).getMeses()[j]
										.replace(".", "").replace(",", ".")), 4, RoundingMode.HALF_DOWN)).toString())
						- 1;
			} catch (Exception e) {
				total_AM = 0D;
			}

			meses_AM_Tratado[j] = percentForm.format(total_AM);

		}
		variacao_AM_VO.setProduto("Varia&ccedil;&atilde;o M&eacute;dia");
		variacao_AM_VO.setMeses(meses_AM_Tratado);
		listaTratada.add(variacao_AM_VO);

		return listaTratada;
	}// fim validaSelecionaSinistrosAvisados

	/**
	 * Retorna uma lista contendo as colunas: GRUPO(nome do produto); FAIXA(faixa
	 * Tempo ou Valor); QTD Administrativo;Porcentagem QTD Admin; Valor
	 * Administrativo; Porcentagem Valor Admin; QTD Judicial;Porcentagem QTD Judic;
	 * Valor Judicial; Porcentagem Valor Judici; QTD Total;Porcentagem QTD Total;
	 * Valor Total; Porcentagem Valor Total;
	 * 
	 * @return List<FaturamentoVO>
	 */
	public List<SinistroPendente_FaixaVO> validaSelecionaSinistroPendente_Faixa(int tipo) {

		DecimalFormat percentForm = new DecimalFormat("0.00%");
		DecimalFormat roundForm = new DecimalFormat("0.00");

		List<SinistroPendente_FaixaVO> listaTratadaTotais = new ArrayList<SinistroPendente_FaixaVO>();
		List<SinistroPendente_FaixaVO> listaSinistroPendente = null;
		List<SinistroPendente_FaixaVO> listaFinal = new ArrayList<SinistroPendente_FaixaVO>();

		switch (tipo) {
		case 1: // faixa tempo
			listaSinistroPendente = listaSinistroPendenteTempo;
			break;
		case 2:// faixa valor
			listaSinistroPendente = listaSinistroPendenteValor;
			break;
		}

		int totalNumSinistrosPendentes_Administrativo = 0;
		int totalNumSinistrosPendentes_Judicial = 0;
		int totalNumSinistrosPendentes_Total = 0;

		BigDecimal totalValorSinistrosPendentes_Administrativo = new BigDecimal("0");
		BigDecimal totalValorSinistrosPendentes_Judicial = new BigDecimal("0");
		BigDecimal totalValorSinistrosPendentes_Total = new BigDecimal("0");

		String textoGrupoAnterior = "";

		// ============================
		// esse obj serve apenas para a lista nao ficar vazia
		SinistroPendente_FaixaVO totaNulo = new SinistroPendente_FaixaVO();
		totaNulo.setGrupo("vazio");
		// ============================

		listaTratadaTotais.add(totaNulo);
		for (int i = 0; i < listaSinistroPendente.size(); i++) {

			if (i == 0) {

				textoGrupoAnterior = listaSinistroPendente.get(i).getGrupo();

				totalNumSinistrosPendentes_Administrativo += listaSinistroPendente.get(i)
						.getNumSinistrosPendentes_Administrativo();
				totalNumSinistrosPendentes_Judicial += listaSinistroPendente.get(i).getNumSinistrosPendentes_Judicial();
				totalNumSinistrosPendentes_Total += listaSinistroPendente.get(i).getNumSinistrosPendentes_Total();

				totalValorSinistrosPendentes_Administrativo = totalValorSinistrosPendentes_Administrativo
						.add(new BigDecimal(listaSinistroPendente.get(i).getValorSinistrosPendentes_Administrativo()));
				totalValorSinistrosPendentes_Judicial = totalValorSinistrosPendentes_Judicial
						.add(new BigDecimal(listaSinistroPendente.get(i).getValorSinistrosPendentes_Judicial()));
				totalValorSinistrosPendentes_Total = totalValorSinistrosPendentes_Total
						.add(new BigDecimal(listaSinistroPendente.get(i).getValorSinistrosPendentes_Total()));

			} else if (listaSinistroPendente.get(i).getGrupo().equals(textoGrupoAnterior)) {

				textoGrupoAnterior = listaSinistroPendente.get(i).getGrupo();

				totalNumSinistrosPendentes_Administrativo += listaSinistroPendente.get(i)
						.getNumSinistrosPendentes_Administrativo();
				totalNumSinistrosPendentes_Judicial += listaSinistroPendente.get(i).getNumSinistrosPendentes_Judicial();
				totalNumSinistrosPendentes_Total += listaSinistroPendente.get(i).getNumSinistrosPendentes_Total();

				totalValorSinistrosPendentes_Administrativo = totalValorSinistrosPendentes_Administrativo
						.add(new BigDecimal(listaSinistroPendente.get(i).getValorSinistrosPendentes_Administrativo()));
				totalValorSinistrosPendentes_Judicial = totalValorSinistrosPendentes_Judicial
						.add(new BigDecimal(listaSinistroPendente.get(i).getValorSinistrosPendentes_Judicial()));
				totalValorSinistrosPendentes_Total = totalValorSinistrosPendentes_Total
						.add(new BigDecimal(listaSinistroPendente.get(i).getValorSinistrosPendentes_Total()));

			} else if (!(listaSinistroPendente.get(i).getGrupo().equals(textoGrupoAnterior))) {

				SinistroPendente_FaixaVO totalVO = new SinistroPendente_FaixaVO();
				totalVO.setGrupo(textoGrupoAnterior);
				totalVO.setFaixa("Total");
				totalVO.setValorSinistrosPendentes_Administrativo(
						totalValorSinistrosPendentes_Administrativo.toString());
				totalVO.setValorSinistrosPendentes_Judicial(totalValorSinistrosPendentes_Judicial.toString());
				totalVO.setValorSinistrosPendentes_Total(totalValorSinistrosPendentes_Total.toString());
				totalVO.setNumSinistrosPendentes_Administrativo(totalNumSinistrosPendentes_Administrativo);
				totalVO.setNumSinistrosPendentes_Judicial(totalNumSinistrosPendentes_Judicial);
				totalVO.setNumSinistrosPendentes_Total(totalNumSinistrosPendentes_Total);
				listaTratadaTotais.add(totalVO);

				textoGrupoAnterior = listaSinistroPendente.get(i).getGrupo();

				totalValorSinistrosPendentes_Administrativo = new BigDecimal("0");
				totalValorSinistrosPendentes_Administrativo = totalValorSinistrosPendentes_Administrativo
						.add(new BigDecimal(listaSinistroPendente.get(i).getValorSinistrosPendentes_Administrativo()));

				totalValorSinistrosPendentes_Judicial = new BigDecimal("0");
				totalValorSinistrosPendentes_Judicial = totalValorSinistrosPendentes_Judicial
						.add(new BigDecimal(listaSinistroPendente.get(i).getValorSinistrosPendentes_Judicial()));

				totalValorSinistrosPendentes_Total = new BigDecimal("0");
				totalValorSinistrosPendentes_Total = totalValorSinistrosPendentes_Total
						.add(new BigDecimal(listaSinistroPendente.get(i).getValorSinistrosPendentes_Total()));

				totalNumSinistrosPendentes_Administrativo = 0;
				totalNumSinistrosPendentes_Administrativo += listaSinistroPendente.get(i)
						.getNumSinistrosPendentes_Administrativo();

				totalNumSinistrosPendentes_Judicial = 0;
				totalNumSinistrosPendentes_Judicial += listaSinistroPendente.get(i).getNumSinistrosPendentes_Judicial();

				totalNumSinistrosPendentes_Total = 0;
				totalNumSinistrosPendentes_Total += listaSinistroPendente.get(i).getNumSinistrosPendentes_Total();

			}

		}
		boolean insere = false;
		for (int i = 0; i < listaTratadaTotais.size(); i++) {
			if (listaTratadaTotais.get(i).getGrupo().equalsIgnoreCase(textoGrupoAnterior)) {
				insere = false;
				break;
			} else {
				insere = true;
			}
		}
		if (insere) {
			SinistroPendente_FaixaVO totaVO = new SinistroPendente_FaixaVO();
			totaVO.setGrupo(textoGrupoAnterior);
			totaVO.setFaixa("Total");
			totaVO.setValorSinistrosPendentes_Administrativo(totalValorSinistrosPendentes_Administrativo.toString());
			totaVO.setValorSinistrosPendentes_Judicial(totalValorSinistrosPendentes_Judicial.toString());
			totaVO.setValorSinistrosPendentes_Total(totalValorSinistrosPendentes_Total.toString());

			totaVO.setNumSinistrosPendentes_Administrativo(totalNumSinistrosPendentes_Administrativo);
			totaVO.setNumSinistrosPendentes_Judicial(totalNumSinistrosPendentes_Judicial);
			totaVO.setNumSinistrosPendentes_Total(totalNumSinistrosPendentes_Total);
			listaTratadaTotais.add(totaVO);
		}
		listaTratadaTotais.remove(0);// remove o obj inserido acima com o texto
										// "nulo"

		// ###################################################
		// ###################################################
		// parte para calcular as porcentagens
		// ###################################################

		// este 'for' serve para vincular a lista de pendentes com a lista de
		// totais atraves do indice da lista de totais
		textoGrupoAnterior = "";
		int tamLista = listaSinistroPendente.size();
		for (int i = 0; i < tamLista; i++) {
			for (int j = 0; j < listaTratadaTotais.size(); j++) {
				if (listaSinistroPendente.get(i).getGrupo().equalsIgnoreCase(listaTratadaTotais.get(j).getGrupo())) {
					// Exemplo: Na listaSinistroPendente na posicao i=5 o
					// produto eh "Auto Correntista". Na listaTratadaTotais
					// esse produto "Auto Correntista" eh j=1. Entao assim
					// saberei onde esta o total de "Auto Correntista" na
					// listaTratadaTotais.
					listaSinistroPendente.get(i).setIndiceListaTotais(j);

				}
			}
		}

		// este 'for' serve para organizar as listas de pendentes e a lista de
		// totais
		int inseriu = 0;
		int ultimoIndice = 0;
		for (int j = 0; j < listaSinistroPendente.size(); j++) {

			if (listaSinistroPendente.get(j).getIndiceListaTotais() != ultimoIndice) {
				inseriu = 0;
			}
			if (inseriu == 0) {
				listaFinal.add(listaTratadaTotais.get(listaSinistroPendente.get(j).getIndiceListaTotais()));
				ultimoIndice = listaSinistroPendente.get(j).getIndiceListaTotais();
				inseriu = 1;
			}

			listaFinal.add(listaSinistroPendente.get(j));

		}

		for (int i = 0; i < listaFinal.size(); i++) {
			if (!(listaFinal.get(i).getFaixa().equalsIgnoreCase("Total"))) {

				int indice = listaFinal.get(i).getIndiceListaTotais();
				try {
					listaFinal.get(i).setNumPercentSinistrosPendentes_Administrativo(
							percentForm.format((double) (listaFinal.get(i).getNumSinistrosPendentes_Administrativo())
									/ (listaTratadaTotais.get(indice).getNumSinistrosPendentes_Administrativo())));
					listaFinal.get(i).setValorPercentSinistrosPendentes_Administrativo(percentForm.format(
							new BigDecimal(listaFinal.get(i).getValorSinistrosPendentes_Administrativo()).divide(
									new BigDecimal(
											listaTratadaTotais.get(indice).getValorSinistrosPendentes_Administrativo()),
									6, RoundingMode.HALF_DOWN)));

				} catch (ArithmeticException ae) {
					listaFinal.get(i).setNumPercentSinistrosPendentes_Administrativo("0%");
					listaFinal.get(i).setValorPercentSinistrosPendentes_Administrativo("0%");

				}

				try {
					listaFinal.get(i).setNumPercentSinistrosPendentes_Judicial(
							percentForm.format((double) (listaFinal.get(i).getNumSinistrosPendentes_Judicial())
									/ (listaTratadaTotais.get(indice).getNumSinistrosPendentes_Judicial())));

					listaFinal.get(i).setValorPercentSinistrosPendentes_Judicial(percentForm
							.format(new BigDecimal(listaFinal.get(i).getValorSinistrosPendentes_Judicial()).divide(
									new BigDecimal(
											listaTratadaTotais.get(indice).getValorSinistrosPendentes_Judicial()),
									6, RoundingMode.HALF_DOWN)));

				} catch (ArithmeticException ae) {
					listaFinal.get(i).setNumPercentSinistrosPendentes_Judicial("0%");
					listaFinal.get(i).setValorPercentSinistrosPendentes_Judicial("0%");
				}

				try {
					listaFinal.get(i).setNumPercentSinistrosPendentes_Total(
							percentForm.format((double) (listaFinal.get(i).getNumSinistrosPendentes_Total())
									/ (listaTratadaTotais.get(indice).getNumSinistrosPendentes_Total())));

					listaFinal.get(i).setValorPercentSinistrosPendentes_Total(percentForm
							.format(new BigDecimal(listaFinal.get(i).getValorSinistrosPendentes_Total()).divide(
									new BigDecimal(listaTratadaTotais.get(indice).getValorSinistrosPendentes_Total()),
									6, RoundingMode.HALF_DOWN)));

				} catch (ArithmeticException ae) {
					listaFinal.get(i).setNumPercentSinistrosPendentes_Total("0%");
					listaFinal.get(i).setValorPercentSinistrosPendentes_Total("0%");
				}

			} // if
		} // for

		for (int i = 0; i < listaFinal.size(); i++) {

			listaFinal.get(i).setValorSinistrosPendentes_Administrativo(uteis.insereSeparadoresMoeda(roundForm
					.format(Double.parseDouble(listaFinal.get(i).getValorSinistrosPendentes_Administrativo()))));
			listaFinal.get(i).setValorSinistrosPendentes_Judicial(uteis.insereSeparadoresMoeda(
					roundForm.format(Double.parseDouble(listaFinal.get(i).getValorSinistrosPendentes_Judicial()))));
			listaFinal.get(i).setValorSinistrosPendentes_Total(uteis.insereSeparadoresMoeda(
					roundForm.format(Double.parseDouble(listaFinal.get(i).getValorSinistrosPendentes_Total()))));

		}

		return listaFinal;
	}// fim validaSelecionaSinistroPendente_Faixa

	/**
	 * Carrega os dados da base de sinistros pendentes por faixa.
	 * 
	 * @return List<SinistroPendente_base_FaixaVO>
	 */
	public void validaSeleciona_Base_SinistroPendente_Faixa(String ano, HttpServletResponse response) {

		VisaoExecutiva_Diaria_DAO dao = new VisaoExecutiva_Diaria_DAO();
		List<SinistroPendente_base_FaixaVO> lista = dao.seleciona_Base_SinistroPendente_Faixa(ano);
		try {
			// Write the header line
			OutputStream out = response.getOutputStream();
			String header = "LEGADO;ANOMES_REF;ORG;MOVIMENTO;RAMO;PROD;DIA;SINISTRO;APOLICE;COMUNICADO;OCORRENCIA;FAVORECIDO;VL_LIDER;VL_COSSEGURO;VL_RESSEGURO;VL_TOTAL;COD_OPERACAO;OPERACAO;FTE_PREM;FTE_AVIS;AVISO;SEGURADO;CAUSA;GRUPO_CAUSA;GRUPO;TIPO;FAIXA_DE_VALOR;DATA_AVISO;HOJE;TEMPO_PENDENTE;FAIXA_TEMPO_PENDENTE\n";
			out.write(header.getBytes());
			// Write the content
			for (int i = 0; i < lista.size(); i++) {

				String line = new String(lista.get(i).getLEGADO() + ";" + lista.get(i).getANOMES_REF() + ";"
						+ lista.get(i).getORG() + ";" + lista.get(i).getMOVIMENTO() + ";" + lista.get(i).getRAMO() + ";"
						+ lista.get(i).getPROD() + ";" + lista.get(i).getDIA() + ";" + lista.get(i).getSINISTRO() + ";"
						+ lista.get(i).getAPOLICE() + ";" + lista.get(i).getCOMUNICADO() + ";"
						+ lista.get(i).getOCORRENCIA() + ";" + lista.get(i).getFAVORECIDO() + ";"
						+ lista.get(i).getVL_LIDER() + ";" + lista.get(i).getVL_COSSEGURO() + ";"
						+ lista.get(i).getVL_RESSEGURO() + ";" + lista.get(i).getVL_TOTAL() + ";"
						+ lista.get(i).getCOD_OPERACAO() + ";" + lista.get(i).getOPERACAO() + ";"
						+ lista.get(i).getFTE_PREM() + ";" + lista.get(i).getFTE_AVIS() + ";" + lista.get(i).getAVISO()
						+ ";" + lista.get(i).getSEGURADO() + ";" + lista.get(i).getCAUSA() + ";"
						+ lista.get(i).getGRUPO_CAUSA() + ";" + lista.get(i).getGRUPO() + ";" + lista.get(i).getTIPO()
						+ ";" + lista.get(i).getFAIXA_DE_VALOR() + ";" + lista.get(i).getData_aviso() + ";"
						+ lista.get(i).getHoje() + ";" + lista.get(i).getTEMPO_PENDENTE() + ";"
						+ lista.get(i).getFAIXA_TEMPO_PENDENTE() + ";\n");
				out.write(line.toString().getBytes());

			}
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}// fim validaSeleciona_Base_SinistroPendente_Faixa

	/**
	 * Carrega os dados da base de faturamento RO.
	 * 
	 * @return
	 * 
	 * @return List<SinistroPendente_base_FaixaVO>
	 */
	public void validaSeleciona_Base_faturamentoRO(String ano, HttpServletResponse response, String params) {

		String[] cortado = params.split("_");
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; filename=baseAnalitica_RO_" + cortado[0] + "-"
				+ cortado[1] + "-" + cortado[2] + "_" + cortado[3] + ".csv");

		VisaoExecutiva_Diaria_DAO dao = new VisaoExecutiva_Diaria_DAO();

		List<FaturamentoRO_base_VO> lista = dao.seleciona_Base_faturamentoRO(ano);

		try (OutputStream out = response.getOutputStream();) {
			// Write the header line
			String header = "LEGADO;ANOMES_REF;ORG;RAMO;PROD;MOVIMENTO;CANAL_VENDA;UF;DIA;FONTE;PONTO_VENDA;APOLICE;ENDOSSO;ENDS_CANC;DT_EMISSAO;INIC_VIG;TERM_VIG;CPF_CNPJ;SEGURADO;IMP_SEG;IS_COS;IS_RES;PREM_TARIFARIO;PREM_TARIFARIO_COS;PREM_TARIFARIO_RES;DESCONTO;DESCONTO_COS;DESCONTO_RES;PREM_LIQUIDO;PREM_LIQUIDO_COS;PREM_LIQUIDO_RES;ADICIONAL;ADICIONAL_COS;ADICIONAL_RES;CUSTO;IOF;LIDER_COMISSAO;VLR_COMISS_COSSG;VLR_COMISS_RESSG;PREM_TOT;COSS_PREMIO_TOTAL;RESS_PREMIO_TOTAL\n";
			out.write(header.getBytes());

			// Write the content
			for (int i = 0; i < lista.size(); i++) {

				String line = new String(lista.get(i).getLEGADO() + ";" + lista.get(i).getANOMES_REF() + ";"
						+ lista.get(i).getORG() + ";" + lista.get(i).getRAMO() + ";" + lista.get(i).getPROD() + ";"
						+ lista.get(i).getMOVIMENTO() + ";" + lista.get(i).getCANAL_VENDA() + ";" + lista.get(i).getUF()
						+ ";" + lista.get(i).getDIA() + ";" + lista.get(i).getFONTE() + ";"
						+ lista.get(i).getPONTO_VENDA() + ";" + lista.get(i).getAPOLICE() + ";"
						+ lista.get(i).getENDOSSO() + ";" + lista.get(i).getENDS_CANC() + ";"
						+ lista.get(i).getDT_EMISSAO() + ";" + lista.get(i).getINIC_VIG() + ";"
						+ lista.get(i).getTERM_VIG() + ";" + lista.get(i).getCPF_CNPJ() + ";"
						+ lista.get(i).getSEGURADO() + ";" + lista.get(i).getIMP_SEG() + ";" + lista.get(i).getIS_COS()
						+ ";" + lista.get(i).getIS_RES() + ";" + lista.get(i).getPREM_TARIFARIO() + ";"
						+ lista.get(i).getPREM_TARIFARIO_COS() + ";" + lista.get(i).getPREM_TARIFARIO_RES() + ";"
						+ lista.get(i).getDESCONTO() + ";" + lista.get(i).getDESCONTO_COS() + ";"
						+ lista.get(i).getDESCONTO_RES() + ";" + lista.get(i).getPREM_LIQUIDO() + ";"
						+ lista.get(i).getPREM_LIQUIDO_COS() + ";" + lista.get(i).getPREM_LIQUIDO_RES() + ";"
						+ lista.get(i).getADICIONAL() + ";" + lista.get(i).getADICIONAL_COS() + ";"
						+ lista.get(i).getADICIONAL_RES() + ";" + lista.get(i).getCUSTO() + ";" + lista.get(i).getIOF()
						+ ";" + lista.get(i).getLIDER_COMISSAO() + ";" + lista.get(i).getVLR_COMISS_COSSG() + ";"
						+ lista.get(i).getVLR_COMISS_RESSG() + ";" + lista.get(i).getPREM_TOT() + ";"
						+ lista.get(i).getCOSS_PREMIO_TOTAL() + ";" + lista.get(i).getRESS_PREMIO_TOTAL() + ";\n");
				out.write(line.getBytes());
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}// fim validaSeleciona_Base_faturamentoRO

	/**
	 * Carrega os dados da base de sinistros.
	 * 
	 * @return List<Sinistros_base_VO>
	 */
	public void validaSeleciona_Base_SinistrosCompleta(String ano, HttpServletResponse response) {

		VisaoExecutiva_Diaria_DAO dao = new VisaoExecutiva_Diaria_DAO();

		List<Sinistros_base_VO> lista = dao.seleciona_Base_sinistroCompleta(ano);
		try {
			// Write the header line
			OutputStream out = response.getOutputStream();
			String header = "LEGADO;ANOMES_REF;ORG;MOVIMENTO;RAMO;PROD;DIA;SINISTRO;APOLICE;COMUNICADO;OCORRENCIA;FAVORECIDO;VL_LIDER;VL_COSSEGURO;VL_RESSEGURO;VL_TOTAL;COD_OPERACAO;OPERACAO;FTE_PREM;FTE_AVIS;AVISO;SEGURADO;CAUSA;GRUPO_CAUSA\n";
			out.write(header.getBytes());
			// Write the content
			for (int i = 0; i < lista.size(); i++) {

				String line = new String(lista.get(i).getLEGADO() + ";" + lista.get(i).getANOMES_REF() + ";"
						+ lista.get(i).getORG() + ";" + lista.get(i).getMOVIMENTO() + ";" + lista.get(i).getRAMO() + ";"
						+ lista.get(i).getPROD() + ";" + lista.get(i).getDIA() + ";" + lista.get(i).getSINISTRO() + ";"
						+ lista.get(i).getAPOLICE() + ";" + lista.get(i).getCOMUNICADO() + ";"
						+ lista.get(i).getOCORRENCIA() + ";" + lista.get(i).getFAVORECIDO() + ";"
						+ lista.get(i).getVL_LIDER() + ";" + lista.get(i).getVL_COSSEGURO() + ";"
						+ lista.get(i).getVL_RESSEGURO() + ";" + lista.get(i).getVL_TOTAL() + ";"
						+ lista.get(i).getCOD_OPERACAO() + ";" + lista.get(i).getOPERACAO() + ";"
						+ lista.get(i).getFTE_PREM() + ";" + lista.get(i).getFTE_AVIS() + ";" + lista.get(i).getAVISO()
						+ ";" + lista.get(i).getSEGURADO() + ";" + lista.get(i).getCAUSA() + ";"
						+ lista.get(i).getGRUPO_CAUSA() + ";\n");
				out.write(line.toString().getBytes());

			}
			out.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}// fim validaSeleciona_Base_SinistrosCompleta

	/**
	 * 
	 * 
	 * @return List<DadosDiariosVO>
	 */
	public List<DadosDiariosVO> validaSelecionaDetalhesDadosDiarios(String ano) {
		Uteis uteis = new Uteis();
		String dataAtual = new SimpleDateFormat("dd/MM/yyyy").format(new Date(System.currentTimeMillis()));
		String dataCut[] = dataAtual.split("/");
		String ano_SO, mes_SO;
		// ano_SO = dataCut[2];
		ano_SO = uteis.cortaRetornaAno(ano);
		mes_SO = dataCut[1];

		List<DadosDiariosVO> listaFinalDadosDiarios = new ArrayList<DadosDiariosVO>();

		VisaoExecutiva_Diaria_DAO dao = new VisaoExecutiva_Diaria_DAO();
		List<DadosDiariosVO> listaCompletaDadosDiarios = dao.selecionaDetalhesDadosDiarios(ano_SO, mes_SO);

		for (int i = 0; i < listaCompletaDadosDiarios.size(); i++) {

			if (listaCompletaDadosDiarios.get(i).getProduto().equalsIgnoreCase(uteis.cortaRetornaProduto(ano))
					&& (listaCompletaDadosDiarios.get(i).getTipo().equals("EMITIDO")
							|| listaCompletaDadosDiarios.get(i).getTipo().equals("EMITIDOS CANCELADOS")
							|| listaCompletaDadosDiarios.get(i).getTipo().equals("EMITIDOS RESTITUIDOS"))) {
				DadosDiariosVO dados = new DadosDiariosVO();

				dados.setAnoMesDia(listaCompletaDadosDiarios.get(i).getAnoMesDia());
				dados.setProduto(listaCompletaDadosDiarios.get(i).getProduto());
				dados.setTipo(listaCompletaDadosDiarios.get(i).getTipo());
				dados.setValorDoDia(listaCompletaDadosDiarios.get(i).getValorDoDia());

				listaFinalDadosDiarios.add(dados);

			}
		}

		return listaFinalDadosDiarios;
	}

	/**
	 * Constroi a visao de faturamento diario
	 * 
	 * @return List<DadosDiariosVO>
	 */
	public List<DadosDiariosVO> validaSelecionaAcumuladoDadosDiarios(String ano) {

		List<DadosDiariosVO> listaDadosDiarios = validaSelecionaDetalhesDadosDiarios(ano);

		List<DadosDiariosVO> listaFiltradaDadosDiarios = new ArrayList<DadosDiariosVO>();
		List<DadosDiariosVO> listaFaturamentoDiario = new ArrayList<DadosDiariosVO>();
		List<DadosDiariosVO> listaFaturamentoAcumulado = new ArrayList<DadosDiariosVO>();

		/* Ordena a lista em EMITIDOS;EMITIDOS CANCELADOS;EMITIDOS RESTITUIDOS */

		for (int i = 0; i < listaDadosDiarios.size(); i++) {
			String dataParaComparacao = listaDadosDiarios.get(i).getAnoMesDia();

			for (int j = 0; j < listaDadosDiarios.size(); j++) {

				if (dataParaComparacao.equals(listaDadosDiarios.get(j).getAnoMesDia())) {
					DadosDiariosVO dados = new DadosDiariosVO();
					dados.setAnoMesDia(listaDadosDiarios.get(j).getAnoMesDia());
					dados.setProduto(listaDadosDiarios.get(j).getProduto());
					dados.setTipo(listaDadosDiarios.get(j).getTipo());
					dados.setValorDoDia(listaDadosDiarios.get(j).getValorDoDia());

					listaFiltradaDadosDiarios.add(dados);
				}
			}
		}

		outer: for (int i = listaFiltradaDadosDiarios.size() - 1; i >= 0; i--) {
			for (int j = 0; j < listaFiltradaDadosDiarios.size(); j++) {
				if (listaFiltradaDadosDiarios.get(i).getAnoMesDia()
						.equalsIgnoreCase(listaFiltradaDadosDiarios.get(j).getAnoMesDia())
						&& listaFiltradaDadosDiarios.get(i).getProduto()
								.equalsIgnoreCase(listaFiltradaDadosDiarios.get(j).getProduto())
						&& listaFiltradaDadosDiarios.get(i).getTipo()
								.equalsIgnoreCase(listaFiltradaDadosDiarios.get(j).getTipo())
						&& listaFiltradaDadosDiarios.get(i).getValorDoDia()
								.equalsIgnoreCase(listaFiltradaDadosDiarios.get(j).getValorDoDia())) {
					if (i != j) {

						listaFiltradaDadosDiarios.remove(i);
						continue outer;
					}
				}
			}
		}

		/* Ordena por data */
		Collections.sort(listaFiltradaDadosDiarios, DadosDiariosVO.anoMesDiaCoparator);

		String dataTemp = "";
		BigDecimal somaAcumulada = new BigDecimal("0.0");

		/* abaixo a visao da faturamento de cada dia */
		DadosDiariosVO faturamentoDiario = new DadosDiariosVO();

		try {

			for (int i = 0; i <= listaFiltradaDadosDiarios.size(); i++) {

				if (i == 0) {
					dataTemp = listaFiltradaDadosDiarios.get(i).getAnoMesDia();

					faturamentoDiario.setAnoMesDia(listaFiltradaDadosDiarios.get(i).getAnoMesDia());
					faturamentoDiario.setProduto(listaFiltradaDadosDiarios.get(i).getProduto());
					faturamentoDiario.setTipo("FATURAMENTO");
				}

				if ((i != listaFiltradaDadosDiarios.size())
						&& dataTemp.equals(listaFiltradaDadosDiarios.get(i).getAnoMesDia())) {

					somaAcumulada = new BigDecimal(listaFiltradaDadosDiarios.get(i).getValorDoDia()).add(somaAcumulada);

				} else {
					if (listaFiltradaDadosDiarios.size() == i) {
						faturamentoDiario.setValorDoDia(somaAcumulada.toString());
						listaFaturamentoDiario.add(faturamentoDiario);
						break;
					} else {
						faturamentoDiario.setValorDoDia(somaAcumulada.toString());
						listaFaturamentoDiario.add(faturamentoDiario);
					}

					dataTemp = listaFiltradaDadosDiarios.get(i).getAnoMesDia();
					somaAcumulada = new BigDecimal(listaFiltradaDadosDiarios.get(i).getValorDoDia());

					faturamentoDiario = new DadosDiariosVO();
					faturamentoDiario.setAnoMesDia(listaFiltradaDadosDiarios.get(i).getAnoMesDia());
					faturamentoDiario.setProduto(listaFiltradaDadosDiarios.get(i).getProduto());
					faturamentoDiario.setTipo("FATURAMENTO");
				}
			}
		} catch (IndexOutOfBoundsException ioobe) {
			System.err.println(
					"VisãoExecutiva_Diaria_BO - método validaSelecionaAcumuladoDadosDiarios - adicionando dados fictícios...");
			DadosDiariosVO dados = new DadosDiariosVO();
			dados.setAnoMesDia("2015-10-02");
			dados.setProduto("TESTE");
			dados.setTipo("FATURAMENTO");
			dados.setValorDoDia("0.0");
			listaFaturamentoDiario.add(dados);
			System.err.println("... dados inseridos");
		}

		/* abaixo construimos a visao acumulada */
		BigDecimal somaAnterior = new BigDecimal("0.0");
		int quantidadeDias = 0;
		for (int i = 0; i < listaFaturamentoDiario.size(); i++) {

			DadosDiariosVO faturamentoDiarioAcumulado = new DadosDiariosVO();
			if (i == 0) {
				faturamentoDiarioAcumulado.setAnoMesDia(listaFaturamentoDiario.get(i).getAnoMesDia());
				faturamentoDiarioAcumulado.setProduto(listaFaturamentoDiario.get(i).getProduto());
				faturamentoDiarioAcumulado.setTipo(listaFaturamentoDiario.get(i).getTipo());
				faturamentoDiarioAcumulado.setValorDoDia(listaFaturamentoDiario.get(i).getValorDoDia());

				somaAnterior = somaAnterior.add(new BigDecimal(listaFaturamentoDiario.get(i).getValorDoDia()));
				quantidadeDias++;
				listaFaturamentoAcumulado.add(faturamentoDiarioAcumulado);

			} else {
				faturamentoDiarioAcumulado.setAnoMesDia(listaFaturamentoDiario.get(i).getAnoMesDia());
				faturamentoDiarioAcumulado.setProduto(listaFaturamentoDiario.get(i).getProduto());
				faturamentoDiarioAcumulado.setTipo(listaFaturamentoDiario.get(i).getTipo());
				faturamentoDiarioAcumulado.setValorDoDia(
						somaAnterior.add(new BigDecimal(listaFaturamentoDiario.get(i).getValorDoDia())).toString());

				somaAnterior = somaAnterior.add(new BigDecimal(listaFaturamentoDiario.get(i).getValorDoDia()));
				quantidadeDias++;
				listaFaturamentoAcumulado.add(faturamentoDiarioAcumulado);
			}
		}

		Uteis uteis = new Uteis();
		String dataAtualSistema = new SimpleDateFormat("dd/MM/yyyy").format(new Date(System.currentTimeMillis()));
		String dataCut[] = dataAtualSistema.split("/");
		String mes_SO;
		mes_SO = dataCut[1];

		/* BP */
		List<FaturamentoVO> listaBP = dadosFaturamentoDetalhado;
		int mes_SO_int = Integer.parseInt(mes_SO);
		String bpMes = listaBP.get(0).getMeses()[mes_SO_int - 1];
		int diasUteis = uteis.retornaDiasUteisMes(mes_SO_int - 1);
		BigDecimal bpPorDia = new BigDecimal(bpMes).divide(new BigDecimal(diasUteis), 6, RoundingMode.HALF_DOWN);

		BigDecimal somaAnteriorBp = new BigDecimal("0.0");
		for (int i = 0; i < quantidadeDias; i++) {
			DadosDiariosVO faturamentoDiarioAcumulado = new DadosDiariosVO();
			if (i == 0) {
				faturamentoDiarioAcumulado.setAnoMesDia(listaFaturamentoAcumulado.get(i).getAnoMesDia());
				faturamentoDiarioAcumulado.setProduto(listaFaturamentoAcumulado.get(i).getProduto());
				faturamentoDiarioAcumulado.setTipo("BP");
				faturamentoDiarioAcumulado.setValorDoDia(bpPorDia.toString());

				somaAnteriorBp = somaAnteriorBp.add(bpPorDia);
				listaFaturamentoAcumulado.add(faturamentoDiarioAcumulado);

			} else {
				faturamentoDiarioAcumulado.setAnoMesDia(listaFaturamentoAcumulado.get(i).getAnoMesDia());
				faturamentoDiarioAcumulado.setProduto(listaFaturamentoAcumulado.get(i).getProduto());
				faturamentoDiarioAcumulado.setTipo("BP");

				somaAnteriorBp = somaAnteriorBp.add(bpPorDia);

				faturamentoDiarioAcumulado.setValorDoDia(somaAnteriorBp.toString());

				listaFaturamentoAcumulado.add(faturamentoDiarioAcumulado);
			}
		}

		return listaFaturamentoAcumulado;
	}

	/**
	 * Retorna a maior data dos registros inseridos em ro_sias e ro_sies
	 * 
	 * @return String
	 */
	public String validaSelecionaPeriodo(String ano) {

		VisaoExecutiva_Diaria_DAO dao = new VisaoExecutiva_Diaria_DAO();
		String retorno = "";
		String periodo = dao.selecionaPeriodo(ano);
		try {
			String anoStr = periodo.substring(0, 4);
			String mesStr = periodo.substring(4, 6);
			String diaStr = periodo.substring(6, 8);

			retorno = diaStr + "/" + mesStr + "/" + anoStr;

		} catch (Exception e) {
			retorno = " ";
		}
		return retorno;

	}

	/**
	 * Retorna uma lista com canal de venda da emissao; Retorna a quantidade de
	 * emitidos e o valor e a suas respectivas porcentagens sobre o total agrupadas
	 * por canal de venda.
	 * 
	 * @return
	 * 
	 * @return String
	 */

	public List<MovimentoPorCanalDeVendaVO> validaSelecionaEmissaoPorCanal(String movimento) {

		List<MovimentoPorCanalDeVendaVO> list = new ArrayList<MovimentoPorCanalDeVendaVO>(listaEmissaoPorCanal);

		List<MovimentoPorCanalDeVendaVO> listTotal = new ArrayList<MovimentoPorCanalDeVendaVO>();

		String[] mesesTotaisValor = { "0.0", "0.0", "0.0", "0.0", "0.0", "0.0", "0.0", "0.0", "0.0", "0.0", "0.0",
				"0.0" };

		String[] mesesTotaisQuantidade = { "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0" };

		String anoTotal = null;
		String produtoTotal = null;

		int qtdQuantidade = 0;
		int qtdValor = 0;

		for (int i = 0; i < list.size(); i++) {
			if (!(list.get(i).getMovimento().trim().equalsIgnoreCase(movimento.trim()))) {
				list.remove(i);
				i--;
			}
		}

		for (MovimentoPorCanalDeVendaVO objLista : list) {
			if (objLista.getTipo().equalsIgnoreCase("Valor")) {
				qtdValor++;
			} else if (objLista.getTipo().equalsIgnoreCase("Quantidade")) {
				qtdQuantidade++;
			}
		}
		int indiceElementoNaoEncontrado = 0;
		if (qtdValor != qtdQuantidade) {

			if (qtdValor > qtdQuantidade) {// Valor eh maior
				outer: for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getTipo().equalsIgnoreCase("Valor")) {

						// System.out.println();
						//
						// System.out.print(" 1 | "
						// + list.get(i).getCanalDeVenda());
						// System.out.println();

						for (int j = 0; j < list.size(); j++) {
							int achou = -1;
							if (list.get(j).getTipo().equalsIgnoreCase("Quantidade")) {

								// System.out.print(" 2 | "
								// + list.get(j).getCanalDeVenda());
								// System.out.println();

								if (list.get(i).getCanalDeVenda().equals(list.get(j).getCanalDeVenda())) {
									achou = j;
									continue outer;
								}

								if (j == list.size() - 1 && achou == -1) {
									indiceElementoNaoEncontrado = i;
								}

							}
						}
					}
				}

				String[] meses = { "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0" };

				MovimentoPorCanalDeVendaVO canalVendaVazio = new MovimentoPorCanalDeVendaVO();
				canalVendaVazio.setCanalDeVenda(list.get(indiceElementoNaoEncontrado).getCanalDeVenda());
				canalVendaVazio.setProduto(list.get(indiceElementoNaoEncontrado).getProduto());
				canalVendaVazio.setTipo("Quantidade");
				canalVendaVazio.setMeses(meses);

				list.add(((list.size() + 1) / 2 + indiceElementoNaoEncontrado), canalVendaVazio);// aqui
				/* estou ordenando tudo que é tipo 'Valor' antes de 'Quantidade' */
			} else {// Qtd eh maior
				outer: for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getTipo().equalsIgnoreCase("Quantidade")) {

						// System.out.println();
						//
						// System.out.print(" 1 | "
						// + list.get(i).getCanalDeVenda());
						// System.out.println();

						for (int j = 0; j < list.size(); j++) {
							int achou = -1;
							if (list.get(j).getTipo().equalsIgnoreCase("Valor")) {

								// System.out.print(" 2 | "+
								// list.get(j).getCanalDeVenda());
								// System.out.println();

								if (list.get(i).getCanalDeVenda().equals(list.get(j).getCanalDeVenda())) {
									achou = j;
									continue outer;
								}

								if (j == list.size() - 1 && achou == -1) {
									indiceElementoNaoEncontrado = i;
								}
							}
						}
					}
				}

				String[] meses = { "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0" };

				MovimentoPorCanalDeVendaVO canalVendaVazio = new MovimentoPorCanalDeVendaVO();
				canalVendaVazio.setCanalDeVenda(list.get(indiceElementoNaoEncontrado).getCanalDeVenda());
				canalVendaVazio.setProduto(list.get(indiceElementoNaoEncontrado).getProduto());
				canalVendaVazio.setTipo("Valor");
				canalVendaVazio.setMeses(meses);

				list.add(((list.size() + 1) / 2 + indiceElementoNaoEncontrado), canalVendaVazio);// aqui
				/* estou ordenando tudo que é tipo 'Valor' antes de 'Quantidade' */

			}

		}

		/*
		 * ===Primeiro crio os objetos com os totais=========
		 */
		for (MovimentoPorCanalDeVendaVO emi : list) {

			if (emi.getTipo().equals("Valor")) {

				for (int i = 0; i < emi.getMeses().length; i++) {
					mesesTotaisValor[i] = new BigDecimal(mesesTotaisValor[i]).add(new BigDecimal(emi.getMeses()[i]))
							.toString();
				}

			} else if (emi.getTipo().equals("Quantidade")) {

				for (int i = 0; i < emi.getMeses().length; i++) {
					mesesTotaisQuantidade[i] = Integer.toString(
							(Integer.parseInt(mesesTotaisQuantidade[i]) + Integer.parseInt(emi.getMeses()[i])));
				}
			}

			anoTotal = emi.getAno();
			produtoTotal = emi.getProduto();

		}

		MovimentoPorCanalDeVendaVO totalValor = new MovimentoPorCanalDeVendaVO();
		MovimentoPorCanalDeVendaVO totalQuantidade = new MovimentoPorCanalDeVendaVO();

		totalValor.setCanalDeVenda("Total");
		totalValor.setProduto(produtoTotal);
		totalValor.setTipo("Valor");
		totalValor.setAno(anoTotal);
		totalValor.setMeses(mesesTotaisValor);
		listTotal.add(totalValor);

		totalQuantidade.setCanalDeVenda("Total");
		totalQuantidade.setProduto(produtoTotal);
		totalQuantidade.setTipo("Quantidade");
		totalQuantidade.setAno(anoTotal);
		totalQuantidade.setMeses(mesesTotaisQuantidade);
		listTotal.add(totalQuantidade);

		/*
		 * ===Agora calculo os percentuais=========
		 */

		final int VALOR = 0;
		final int QUANTIDADE = 1;

		DecimalFormat percentForm = new DecimalFormat("0.00%");
		DecimalFormat roundForm = new DecimalFormat("0.00");
		Uteis uteis = new Uteis();
		List<MovimentoPorCanalDeVendaVO> listFinal = new ArrayList<MovimentoPorCanalDeVendaVO>();

		for (int i = 0; i < list.size() / 2; i++) {
			MovimentoPorCanalDeVendaVO emissaoValor = new MovimentoPorCanalDeVendaVO();
			/* ===VALOR==== */
			emissaoValor.setCanalDeVenda(list.get(i).getCanalDeVenda());
			emissaoValor.setTipo(list.get(i).getTipo());
			emissaoValor.setMeses(list.get(i).getMeses());

			MovimentoPorCanalDeVendaVO emissaoValorPercent = new MovimentoPorCanalDeVendaVO();
			/* ===%=VALOR==== */
			emissaoValorPercent.setCanalDeVenda(list.get(i).getCanalDeVenda());
			emissaoValorPercent.setTipo("% " + list.get(i).getTipo());

			String[] mesesPercentValor = new String[12];
			for (int k = 0; k < list.get(i).getMeses().length; k++) {

				try {
					double total = Double.parseDouble(new BigDecimal(list.get(i).getMeses()[k])
							.divide(new BigDecimal(listTotal.get(VALOR).getMeses()[k]), 5, RoundingMode.HALF_DOWN)
							.toString());
					mesesPercentValor[k] = percentForm.format(total);

				} catch (ArithmeticException e) {
					mesesPercentValor[k] = "0%";
					continue;
				}

			}
			emissaoValorPercent.setMeses(mesesPercentValor);

			MovimentoPorCanalDeVendaVO emissaoQuantidade = new MovimentoPorCanalDeVendaVO();
			/* ===QUANTIDADE==== */
			int j = list.size() / 2;
			emissaoQuantidade.setCanalDeVenda(list.get(j + i).getCanalDeVenda());
			emissaoQuantidade.setTipo(list.get(j + i).getTipo());
			emissaoQuantidade.setMeses(list.get(j + i).getMeses());

			MovimentoPorCanalDeVendaVO emissaoQuantidadePercent = new MovimentoPorCanalDeVendaVO();
			/* ===%=QUANTIDADE==== */
			emissaoQuantidadePercent.setCanalDeVenda(list.get(j + i).getCanalDeVenda());
			emissaoQuantidadePercent.setTipo("% " + list.get(j + i).getTipo());

			String[] mesesPercentQuantidade = new String[12];
			for (int k = 0; k < list.get(j + i).getMeses().length; k++) {

				try {

					double total = Double.parseDouble(list.get(j + i).getMeses()[k])
							/ Double.parseDouble(listTotal.get(QUANTIDADE).getMeses()[k]);
					mesesPercentQuantidade[k] = percentForm
							.format(Double.toString(total).equalsIgnoreCase("NaN") ? 0.0 : total);

				} catch (ArithmeticException e) {
					mesesPercentQuantidade[k] = "0%";
					continue;
				}

			}
			emissaoQuantidadePercent.setMeses(mesesPercentQuantidade);

			String[] valorFormatado = new String[12];
			for (int k = 0; k < emissaoValor.getMeses().length; k++) {
				valorFormatado[k] = uteis
						.insereSeparadoresMoeda(roundForm.format(Double.parseDouble(emissaoValor.getMeses()[k])));

			}
			emissaoValor.setMeses(valorFormatado);

			String[] valorFormatado2 = new String[12];
			for (int k = 0; k < emissaoQuantidade.getMeses().length; k++) {
				valorFormatado2[k] = uteis.insereSeparadores(emissaoQuantidade.getMeses()[k]);
			}
			emissaoQuantidade.setMeses(valorFormatado2);

			listFinal.add(emissaoValor);
			listFinal.add(emissaoValorPercent);
			listFinal.add(emissaoQuantidade);
			listFinal.add(emissaoQuantidadePercent);

		}

		return listFinal;

	}
}
