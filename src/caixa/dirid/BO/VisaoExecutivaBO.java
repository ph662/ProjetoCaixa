package caixa.dirid.BO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import caixa.dirid.DAO.VisaoExecutivaDAO;
import caixa.dirid.UTEIS.Uteis;
import caixa.dirid.VO.FaturamentoVO;

public class VisaoExecutivaBO extends VisoesBO {

	/**
	 * Retorna uma lista contendo na primeira linha o BP e na segunda o
	 * Faturamento. Os dados ficam no formato '21.5'.
	 * 
	 * @arguments String
	 * 
	 * @return List<RvneVO>
	 */
	public List<FaturamentoVO> validaSelecionaTotalFaturamento(String ano) {
		DecimalFormat roundForm = new DecimalFormat("0.0");
		VisaoExecutivaDAO dao = new VisaoExecutivaDAO();

		List<FaturamentoVO> listaTratada = new ArrayList<FaturamentoVO>();
		List<FaturamentoVO> lista = dao.selecionaDadosFaturamento(ano);

		List<FaturamentoVO> listaRvne = dao.selecionaRVNE(ano, 2, "0");

		BigDecimal totalMes = new BigDecimal(0.0);

		// Insere BP na lista
		int i = 0;
		FaturamentoVO faturamentoVObp = new FaturamentoVO();
		faturamentoVObp.setProduto(lista.get(i).getProduto());
		String mesesTotalBP[] = new String[12];

		for (int k = 0; k < 12; k++) {
			mesesTotalBP[k] = roundForm.format(
					Double.parseDouble(lista.get(i).getMeses()[k]) / 1000000)
					.replace(",", ".");
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
						|| lista.get(c).getProduto()
								.equalsIgnoreCase("EMITIDOS CANCELADOS")
						|| lista.get(c).getProduto()
								.equalsIgnoreCase("EMITIDOS RESTITUIDOS")) {

					totalMes = totalMes.add(new BigDecimal(lista.get(c)
							.getMeses()[j]));
				}
				c++;
			}
			// RVNE trazido direto da tabela do banco
			if (listaRvne.get(0).getMeses()[j] != null) {
				totalMes = totalMes.add(new BigDecimal(listaRvne.get(0)
						.getMeses()[j]));
			} else {
				totalMes = new BigDecimal(0.0);
			}
			mesesTotal[j] = roundForm.format(
					Double.parseDouble(totalMes.toString()) / 1000000).replace(
					",", ".");
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
	public List<FaturamentoVO> validaSelecionaTotalFaturamentoAcumulado(
			String ano, int retorno) {
		Uteis uteis = new Uteis();
		DecimalFormat roundForm = new DecimalFormat("0.0");
		VisaoExecutivaDAO dao = new VisaoExecutivaDAO();
		List<FaturamentoVO> listaTratada = new ArrayList<FaturamentoVO>();

		List<FaturamentoVO> lista = dao.selecionaFaturamentoTotal(ano);
		List<FaturamentoVO> listaBP = dao.selecionaDadosFaturamento(uteis
				.cortaRetornaProduto(ano) + uteis.cortaRetornaAno(ano));

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
				mesesTotalBP[j] = roundForm
						.format(Double.parseDouble(listaBP.get(varBP)
								.getMeses()[j]) / 1000000).replace(",", ".");
			} else {
				double somaBP = 0.0D;
				if (j >= 2) {
					somaBP = Double
							.parseDouble(listaBP.get(varBP).getMeses()[j])
							+ somaAnteriorBP;
				} else {
					// j na posicao 1
					somaBP = Double
							.parseDouble(listaBP.get(varBP).getMeses()[j])
							+ Double.parseDouble(listaBP.get(varBP).getMeses()[j - 1]);
				}
				somaAnteriorBP = somaBP;
				mesesTotalBP[j] = roundForm.format(somaAnteriorBP / 1000000)
						.replace(",", ".");
			}
		}
		faturamentoVOBP.setMeses(mesesTotalBP);
		listaTratada.add(faturamentoVOBP);

		faturamentoVOtratado.setProduto("Faturamento Acumulado - "
				+ uteis.cortaRetornaAno(ano));

		// retorno == 1 retorna no formato: '0.0'
		if (retorno == 1) {
			double faturamento = 0.0D;
			double fatuAnterior = 0.0D;
			double soma = 0.0D;
			for (int j = 0; j < 12; j++) {

				// aqui faço a criação do faturamento, junto à RVNE
				faturamento = Double.parseDouble(lista.get(i).getMeses()[j]);
				if (j == 0) {
					mesesTotal[j] = roundForm.format(faturamento / 1000000)
							.replace(",", ".");
					fatuAnterior = faturamento;
				} else {
					if (j >= 2) {
						soma = faturamento + somaAnterior;
					} else {
						soma = faturamento + fatuAnterior;
					}
					// serve para completar os outros meses com 0
					if (soma != somaAnterior) {
						somaAnterior = soma;
						mesesTotal[j] = roundForm
								.format(somaAnterior / 1000000).replace(",",
										".");
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
	 * Retorna uma lista contendo os Tipos: BP(para ano atual), Emitido,
	 * Emitidos Cancelados, Emitidos Restituidos, RVNE e o Total. O Total se
	 * trata da soma entre todos os citados com exceção do BP. No caso do ano
	 * passado por parâmetro ser o ano atual, irá retornar na primeira linha o
	 * BP. O Retorno é feito em milhões com 2 casas decimais. Exemplo:
	 * "2.000.000,00"
	 * 
	 * @arguments String
	 * 
	 * @return List<RvneVO>
	 */
	public List<FaturamentoVO> validaSelecionaFaturamentoDetalhado(String ano) {
		DecimalFormat roundForm = new DecimalFormat("0.00");
		VisaoExecutivaDAO dao = new VisaoExecutivaDAO();
		Uteis uteis = new Uteis();
		List<FaturamentoVO> listaTratada = new ArrayList<FaturamentoVO>();
		List<FaturamentoVO> lista = dao.selecionaDadosFaturamento(ano);

		List<FaturamentoVO> listaRvne = dao.selecionaRVNE(ano, 2, "0");

		FaturamentoVO faturamentoVOtotal = new FaturamentoVO();

		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getProduto().equals("QT APOLICES EMITIDAS")) {

				FaturamentoVO faturamentoVOtratado = new FaturamentoVO();
				String mesesTratado[] = new String[12];
				faturamentoVOtratado.setProduto(lista.get(i).getProduto());

				for (int k = 0; k < 12; k++) {

					mesesTratado[k] = lista.get(i).getMeses()[k];

					faturamentoVOtratado.setMeses(mesesTratado);
				}
				listaTratada.add(faturamentoVOtratado);

			} else if (lista.get(i).getProduto().equals("EMITIDO")
					|| lista.get(i).getProduto().equals("EMITIDOS CANCELADOS")
					|| lista.get(i).getProduto().equals("EMITIDOS RESTITUIDOS")
					|| lista.get(i).getProduto().equals("BP")) {
				FaturamentoVO faturamentoVOtratado = new FaturamentoVO();
				String mesesTratado[] = new String[12];

				faturamentoVOtratado.setProduto(lista.get(i).getProduto());

				for (int k = 0; k < 12; k++) {
					String tratada = roundForm.format(Double.parseDouble(lista
							.get(i).getMeses()[k]));

					mesesTratado[k] = uteis.insereSeparadores(tratada);

					faturamentoVOtratado.setMeses(mesesTratado);
				}
				listaTratada.add(faturamentoVOtratado);

			}
		}

		for (int i = 0; i < listaRvne.size(); i++) {

			FaturamentoVO apenasRVNE = new FaturamentoVO();
			String mesesRVNE[] = new String[12];
			apenasRVNE.setProduto(listaRvne.get(i).getProduto());

			for (int k = 0; k < 12; k++) {

				String tratada = "";
				if (listaRvne.get(i).getMeses()[k] != null) {
					tratada = roundForm.format(Double.parseDouble(listaRvne
							.get(i).getMeses()[k]));
				} else {
					tratada = roundForm.format(Double.parseDouble("0.0"));
				}

				mesesRVNE[k] = uteis.insereSeparadores(tratada);

				apenasRVNE.setMeses(mesesRVNE);
			}
			listaTratada.add(apenasRVNE);
		}

		faturamentoVOtotal.setProduto("<b>Total</b>");
		String mesesTotal[] = new String[12];

		for (int j = 0; j < 12; j++) {
			double total1 = 0.0D;
			int k = 0;

			while (k < listaTratada.size()) {
				if (listaTratada.get(k).getProduto().toString().contains("BP")
						|| listaTratada.get(k).getProduto().toString()
								.contains("QT")) {
					k++;
				}
				total1 += Double.parseDouble(listaTratada.get(k).getMeses()[j]
						.replace(".", "").replace(",", "."));
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
	public List<FaturamentoVO> validaCanceladosDividoPorEmitidos(String ano) {
		VisaoExecutivaDAO dao = new VisaoExecutivaDAO();
		DecimalFormat percentForm = new DecimalFormat("0%");
		List<FaturamentoVO> listaTratada = new ArrayList<FaturamentoVO>();
		List<FaturamentoVO> lista = dao.selecionaDadosFaturamento(ano);
		FaturamentoVO faturamentoVO = new FaturamentoVO();
		String mesesTotal[] = new String[12];

		faturamentoVO.setProduto("Representacao Cancelados ");
		for (int i = 0; i < 12; i++) {
			double total = 0.0D;

			int j = 1;
			BigDecimal bigAnoAtual = new BigDecimal(
					lista.get(j + 1).getMeses()[i]);
			try {
				total = Double
						.parseDouble((bigAnoAtual.divide(new BigDecimal(lista
								.get(j).getMeses()[i]), 4,
								RoundingMode.HALF_DOWN)).toString());
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
	public List<FaturamentoVO> validaAnoAtualDividoPorAnoAnteriorAcumulado(
			String ano) {
		DecimalFormat percentForm = new DecimalFormat("0%");
		List<FaturamentoVO> listaTratada = new ArrayList<FaturamentoVO>();
		Uteis uteis = new Uteis();
		String tipo = uteis.cortaRetornaProduto(ano);
		String anoAtual = uteis.cortaRetornaAno(ano);
		String anoAnterior = Integer.toString((Integer.parseInt(uteis
				.cortaRetornaAno(ano)) - 1));

		List<FaturamentoVO> listaAnoAtual = validaSelecionaTotalFaturamentoAcumulado(
				tipo + anoAtual, 2);
		List<FaturamentoVO> listaAnoAnterior = validaSelecionaTotalFaturamentoAcumulado(
				tipo + anoAnterior, 2);
		FaturamentoVO faturamentoVO = new FaturamentoVO();
		String mesesTotal[] = new String[12];

		faturamentoVO.setProduto("anoAtual/anoAnterior % ");

		for (int i = 0; i < 12; i++) {
			BigDecimal bigAnoAtual = new BigDecimal(listaAnoAtual.get(1)
					.getMeses()[i]);
			double total = 0.0D;

			/*
			 * total = (Double.parseDouble(listaAnoAtual.get(1).getMeses()[i]) /
			 * Double .parseDouble(listaAnoAnterior.get(1).getMeses()[i])) - 1;
			 */try {
				total = Double.parseDouble((bigAnoAtual.divide(new BigDecimal(
						listaAnoAnterior.get(1).getMeses()[i]), 4,
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
		VisaoExecutivaDAO dao = new VisaoExecutivaDAO();
		DecimalFormat percentForm = new DecimalFormat("0%");
		List<FaturamentoVO> listaTratada = new ArrayList<FaturamentoVO>();
		Uteis uteis = new Uteis();

		String anoAnterior = Integer.toString((Integer.parseInt(uteis
				.cortaRetornaAno(ano)) - 1));

		List<FaturamentoVO> listaAnoAtual = dao.selecionaFaturamentoTotal(ano);
		List<FaturamentoVO> listaAnoAnterior = dao
				.selecionaFaturamentoTotal(uteis.cortaRetornaProduto(ano)
						+ anoAnterior);
		FaturamentoVO faturamentoVO = new FaturamentoVO();
		String mesesTotal[] = new String[12];

		faturamentoVO.setProduto("atual/anterior % ");
		for (int i = 0; i < 12; i++) {

			BigDecimal bigAnoAtual = new BigDecimal(listaAnoAtual.get(0)
					.getMeses()[i]);
			double total = 0.0D;

			try {
				total = Double.parseDouble((bigAnoAtual.divide(new BigDecimal(
						listaAnoAnterior.get(0).getMeses()[i]), 4,
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
	 * AnoAnterior | Valor Sinistros Avisados Ano Anterior | Variacao Qtd |
	 * Variacao Valor | Aviso Medio AnoParam | Aviso Medio AnoAnterior |
	 * Variacao Aviso Medio
	 * 
	 * 
	 * @return List<FaturamentoVO>
	 */
	public List<FaturamentoVO> validaSelecionaSinistros(String ano, int tipo) {
		DecimalFormat roundForm = new DecimalFormat("0.00");

		DecimalFormat percentForm = new DecimalFormat("0%");

		VisaoExecutivaDAO dao = new VisaoExecutivaDAO();

		Uteis uteis = new Uteis();
		String anoAnterior = Integer.toString((Integer.parseInt(uteis
				.cortaRetornaAno(ano)) - 1));

		List<FaturamentoVO> listaTratada = new ArrayList<FaturamentoVO>();

		List<FaturamentoVO> listaAnoParam = dao.selecionaDadosFaturamento(ano);
		List<FaturamentoVO> listaAnoAnterior = dao
				.selecionaDadosFaturamento(uteis.cortaRetornaProduto(ano)
						+ anoAnterior);

		String quantidadeTextoBancoDados = "";
		String quantidadeTextoApresentacao = "";
		String valorTextoBancoDados = "";
		String valorTextoApresentacao = "";

		switch (tipo) {
		case 1: // AVISADO
			quantidadeTextoBancoDados = "QT SINISTROS AVISADOS";
			quantidadeTextoApresentacao = "Qtd. Sinistros Avisados";
			valorTextoBancoDados = "SINISTROS AVISADOS";
			valorTextoApresentacao = "Valor Sinistros Avisados";
			break;
		case 2: // INDENIZADO
			quantidadeTextoBancoDados = "QT SINISTROS INDENIZADOS";
			quantidadeTextoApresentacao = "Qtd. Sinistros Indenizados";
			valorTextoBancoDados = "SINISTROS INDENIZADOS";
			valorTextoApresentacao = "Valor Sinistros Indenizados";
			break;
		case 3: // PENDENTE
			quantidadeTextoBancoDados = "QT SINISTROS PENDENTES";
			quantidadeTextoApresentacao = "Qtd. Sinistros Pendentes";
			valorTextoBancoDados = "SINISTROS PENDENTES";
			valorTextoApresentacao = "Valor Sinistros Pendentes";
			break;
		case 4: // DESPESA
			quantidadeTextoBancoDados = "QT SINISTROS - DESPESAS";
			quantidadeTextoApresentacao = "Qtd. Sinistros - Despesas";
			valorTextoBancoDados = "SINISTROS - DESPESAS";
			valorTextoApresentacao = "Valor Sinistros Despesas";
			break;		
		
		}

		for (int i = 0; i < listaAnoParam.size(); i++) {
			if (listaAnoParam.get(i).getProduto()
					.equals(quantidadeTextoBancoDados)) {

				FaturamentoVO sinistroVOtratado = new FaturamentoVO();
				String mesesTratado[] = new String[12];

				sinistroVOtratado.setAno(uteis.cortaRetornaAno(ano));
				sinistroVOtratado.setProduto(quantidadeTextoApresentacao);

				for (int k = 0; k < 12; k++) {

					mesesTratado[k] = uteis.insereSeparadores(listaAnoParam
							.get(i).getMeses()[k]);

					sinistroVOtratado.setMeses(mesesTratado);
				}
				listaTratada.add(sinistroVOtratado);

			} else if (listaAnoParam.get(i).getProduto()
					.equals(valorTextoBancoDados)) {
				FaturamentoVO faturamentoVOtratado = new FaturamentoVO();
				String mesesTratado[] = new String[12];

				faturamentoVOtratado.setAno(uteis.cortaRetornaAno(ano));
				faturamentoVOtratado.setProduto(valorTextoApresentacao);

				for (int k = 0; k < 12; k++) {
					String tratada = roundForm.format(Double
							.parseDouble(listaAnoParam.get(i).getMeses()[k]));

					mesesTratado[k] = uteis.insereSeparadores(tratada);

					faturamentoVOtratado.setMeses(mesesTratado);
				}
				listaTratada.add(faturamentoVOtratado);

			}
		}// for anoParam

		for (int i = 0; i < listaAnoAnterior.size(); i++) {
			if (listaAnoAnterior.get(i).getProduto()
					.equals(quantidadeTextoBancoDados)) {

				FaturamentoVO sinistroVOtratado = new FaturamentoVO();
				String mesesTratado[] = new String[12];

				sinistroVOtratado.setAno(anoAnterior);
				sinistroVOtratado.setProduto(quantidadeTextoApresentacao);

				for (int k = 0; k < 12; k++) {

					mesesTratado[k] = uteis.insereSeparadores(listaAnoAnterior
							.get(i).getMeses()[k]);

					sinistroVOtratado.setMeses(mesesTratado);
				}
				listaTratada.add(sinistroVOtratado);

			} else if (listaAnoAnterior.get(i).getProduto()
					.equals(valorTextoBancoDados)) {
				FaturamentoVO faturamentoVOtratado = new FaturamentoVO();
				String mesesTratado[] = new String[12];

				faturamentoVOtratado.setAno(anoAnterior);
				faturamentoVOtratado.setProduto(valorTextoApresentacao);

				for (int k = 0; k < 12; k++) {
					String tratada = roundForm
							.format(Double.parseDouble(listaAnoAnterior.get(i)
									.getMeses()[k]));

					mesesTratado[k] = uteis.insereSeparadores(tratada);

					faturamentoVOtratado.setMeses(mesesTratado);
				}
				listaTratada.add(faturamentoVOtratado);

			}
		}// for anoAnterior

		byte qtdAnoParam = 0;
		byte qtdAnoAnterior = 2;

		byte vlrAnoParam = 1;
		byte vlrAnoAnterior = 3;

		// *******************************************
		// *******************************************
		// Variacao 16/15 QTD
		String[] mesesQtdTratado = new String[12];
		double totalQtd = 0.0D;
		FaturamentoVO variacaoQtdVO = new FaturamentoVO();

		for (int j = 0; j < 12; j++) {
			BigDecimal bigQtdAnoParam = new BigDecimal(
					Double.parseDouble(listaTratada.get(qtdAnoParam).getMeses()[j]
							.replace(".", "")));

			try {
				totalQtd = Double.parseDouble((bigQtdAnoParam.divide(
						new BigDecimal(listaTratada.get(qtdAnoAnterior)
								.getMeses()[j].replace(".", "")), 4,
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
		String[] mesesVlrTratado = new String[12];
		double totalVlr = 0.0D;
		FaturamentoVO variacaoVlrVO = new FaturamentoVO();

		for (int j = 0; j < 12; j++) {

			BigDecimal bigVlrAnoParam = new BigDecimal(
					Double.parseDouble(listaTratada.get(vlrAnoParam).getMeses()[j]
							.replace(".", "").replace(",", ".")));

			try {
				totalVlr = Double.parseDouble((bigVlrAnoParam.divide(
						new BigDecimal(listaTratada.get(vlrAnoAnterior)
								.getMeses()[j].replace(".", "").replace(",",
								".")), 4, RoundingMode.HALF_DOWN)).toString()) - 1;
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
		// Aviso Medio anoParam
		String[] mesesAvisoMedioAnoParamTratado = new String[12];
		double totalAvisoMedioAnoParam = 0.0D;
		FaturamentoVO variacaoAvisoMedioAnoParamVO = new FaturamentoVO();

		for (int j = 0; j < 12; j++) {

			BigDecimal bigVlrAnoParam = new BigDecimal(
					Double.parseDouble(listaTratada.get(vlrAnoParam).getMeses()[j]
							.replace(".", "").replace(",", ".")));

			try {
				totalAvisoMedioAnoParam = Double.parseDouble((bigVlrAnoParam
						.divide(new BigDecimal(listaTratada.get(qtdAnoParam)
								.getMeses()[j].replace(".", "").replace(",",
								".")), 2, RoundingMode.HALF_DOWN)).toString());
			} catch (Exception e) {
				totalAvisoMedioAnoParam = 0D;
			}
			mesesAvisoMedioAnoParamTratado[j] = uteis
					.insereSeparadoresMoeda(roundForm
							.format(totalAvisoMedioAnoParam));

		}
		variacaoAvisoMedioAnoParamVO.setAno(uteis.cortaRetornaAno(ano));
		variacaoAvisoMedioAnoParamVO.setProduto("Aviso M&eacute;dio");
		variacaoAvisoMedioAnoParamVO.setMeses(mesesAvisoMedioAnoParamTratado);
		listaTratada.add(variacaoAvisoMedioAnoParamVO);

		// *******************************************
		// *******************************************
		// Aviso Medio anoAnterior
		String[] mesesAvisoMedioAnoAnteriorTratado = new String[12];
		double totalAvisoMedioAnoAnterior = 0.0D;
		FaturamentoVO variacaoAvisoMedioAnoAnteriorVO = new FaturamentoVO();

		for (int j = 0; j < 12; j++) {

			BigDecimal bigVlrAnoAnterior = new BigDecimal(
					Double.parseDouble(listaTratada.get(vlrAnoAnterior)
							.getMeses()[j].replace(".", "").replace(",", ".")));

			try {
				totalAvisoMedioAnoAnterior = Double
						.parseDouble((bigVlrAnoAnterior.divide(
								new BigDecimal(listaTratada.get(qtdAnoAnterior)
										.getMeses()[j].replace(".", "")), 2,
								RoundingMode.HALF_DOWN)).toString());
			} catch (Exception e) {
				totalAvisoMedioAnoAnterior = 0D;
			}
			mesesAvisoMedioAnoAnteriorTratado[j] = uteis
					.insereSeparadoresMoeda(roundForm
							.format(totalAvisoMedioAnoAnterior));

		}
		variacaoAvisoMedioAnoAnteriorVO.setAno(anoAnterior);
		variacaoAvisoMedioAnoAnteriorVO.setProduto("Aviso M&eacute;dio");
		variacaoAvisoMedioAnoAnteriorVO
				.setMeses(mesesAvisoMedioAnoAnteriorTratado);
		listaTratada.add(variacaoAvisoMedioAnoAnteriorVO);

		// *******************************************
		// *******************************************
		// Variacao Media
		short avisoMedioAnoParam = 6;
		short avisoMedioAnoAnterior = 7;

		String[] meses_AM_Tratado = new String[12];// AM -aviso medio
		double total_AM = 0.0D;
		FaturamentoVO variacao_AM_VO = new FaturamentoVO();

		for (int j = 0; j < 12; j++) {

			BigDecimal big_AM_AnoParam = new BigDecimal(
					Double.parseDouble(listaTratada.get(avisoMedioAnoParam)
							.getMeses()[j].replace(".", "").replace(",", ".")));

			try {
				total_AM = Double.parseDouble((big_AM_AnoParam.divide(
						new BigDecimal(listaTratada.get(avisoMedioAnoAnterior)
								.getMeses()[j].replace(".", "").replace(",",
								".")), 4, RoundingMode.HALF_DOWN)).toString()) - 1;
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

}
