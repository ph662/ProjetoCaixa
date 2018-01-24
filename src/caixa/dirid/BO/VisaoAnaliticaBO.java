package caixa.dirid.BO;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import caixa.dirid.DAO.VisaoAnaliticaDAO;
import caixa.dirid.DAO.VisoesDAO;
import caixa.dirid.UTEIS.Uteis;
import caixa.dirid.VO.FatuMensalFinalVO;
import caixa.dirid.VO.FatuMensalVO;
import caixa.dirid.VO.FaturamentoVO;
import caixa.dirid.VO.RvneVO;
import caixa.dirid.VO.VisoesVO;

public class VisaoAnaliticaBO extends VisoesBO {

	public List<VisoesVO> validaSelecionaApolicesEmitidas() {
		VisoesDAO dao = new VisoesDAO();
		return dao.selecionaApolicesEmitidas();
	}

	/**
	 * Retorna uma lista com
	 * "Produto | Fat 2014 | Fat 2015 | BP 2015 | 15/14R$ | 15/14% | 15/BP R$ | 15/BP% | % Fat2015"
	 * . Dados do faturamento mensal, o retorno é feito com numeros com virgula
	 * '15260903,90' ou porcentagem '67%'.
	 * 
	 * @arguments String, String
	 * @return List<FatuMensalFinalVO>
	 */
	public List<FatuMensalFinalVO> validaFatuMensal(String ano, String mes) {
		VisaoAnaliticaDAO dao = new VisaoAnaliticaDAO();
		Uteis uteis = new Uteis();
		DecimalFormat roundForm = new DecimalFormat("0.00");
		DecimalFormat percentForm = new DecimalFormat("0%");

		// pega o parametro ano e obtem o ano anterior ao recebido
		String anoAnterior = Integer.toString((Integer.parseInt(uteis
				.cortaRetornaAno(ano)) - 1));

		List<FatuMensalVO> listaAnoAtual = dao.selecionaFatuMensal(ano, mes);
		List<FatuMensalVO> listaAnoAnterior = dao.selecionaFatuMensal(
				uteis.cortaRetornaProduto(ano) + anoAnterior, mes);

		List<FatuMensalVO> listaTratadaAnoAtual = new ArrayList<FatuMensalVO>();
		List<FatuMensalVO> listaTratadaAnoAnterior = new ArrayList<FatuMensalVO>();
		List<FatuMensalFinalVO> listaFinal = new ArrayList<FatuMensalFinalVO>();

		List<FaturamentoVO> listaRvne = dao.selecionaRVNE(ano, 3, mes);

		FatuMensalVO fatuVO = null;

		/*
		 * Este trecho trata os casos em que não existiu faturamento no mês.
		 * Neste caso ele verifica se não possui o nome na lista e então cria o
		 * objeto com o nome do produto e o valor zerado
		 */
		int flag = 0;

		for (int j = 0; j < listaAnoAtual.size(); j++) {
			if (listaAnoAtual.get(0).getProduto().contains("Auto")) {
				// se o produto da lista for diferente do nome passado..
				if (!listaAnoAtual.get(j).getProduto()
						.equalsIgnoreCase("Auto Tranquilo Frota")) {
					flag++;
				}
			} else if (listaAnoAtual.get(0).getProduto().contains("MR")) {
				if (!listaAnoAtual.get(j).getProduto().equalsIgnoreCase("CCA")) {
					flag++;
				}
			}
		}
		// se a flag for igual ao tamanho da lista quer dizer que nao tem o
		// produto na lista

		if (flag == listaAnoAtual.size()) {
			if (!listaAnoAtual.isEmpty()) {
				if (listaAnoAtual.get(0).getProduto().contains("Auto")) {
					FatuMensalVO fatuMensalVo = new FatuMensalVO();
					fatuMensalVo.setProduto("Auto Tranquilo Frota");
					fatuMensalVo.setFaturamentoSemRVNE("0.0");
					listaAnoAtual.add(fatuMensalVo);
				}
			}
		}

		/*
		 * Cria objeto com AnoMes, Produto, Faturamento total e BP para o Ano
		 * Atual
		 */
		if (listaAnoAtual.size() == 0) {
			for (int i = 0; i < listaRvne.size(); i++) {
				fatuVO = new FatuMensalVO();
				double total = 0.0D;

				for (int j = 0; j < listaRvne.size(); j++) {
					String rvne = listaRvne.get(j).getMeses()[0];

					/*
					 * se o nome do produto da lista rvne for igual ao nome do
					 * produto da lista ano atual
					 */
					if (listaRvne.get(j).getProduto()
							.contains(listaRvne.get(i).getProduto())
							|| listaRvne.get(i).getProduto()
									.contains(listaRvne.get(j).getProduto())) {
						total = (Double.parseDouble(rvne == null ? "0.0" : rvne
								.replace(",", ".")) * 1000);
					} else if (listaRvne.get(j).getProduto().equals("CCA")
							&& listaRvne.get(i).getProduto()
									.equals("MR Caixa Aqui")) {
						total = (Double.parseDouble(rvne == null ? "0.0" : rvne
								.replace(",", ".")) * 1000);
					}
				}
				// System.out.println(listaRvne.get(i).getProduto() + " " +
				// roundForm.format(total));

				fatuVO.setProduto(listaRvne.get(i).getProduto());
				fatuVO.setFaturamentoTotal(roundForm.format(total));

				listaTratadaAnoAtual.add(fatuVO);
			}// fim for

		} else {

			for (int i = 0; i < listaAnoAtual.size(); i++) {
				fatuVO = new FatuMensalVO();
				double faturaSemRvne = Double.parseDouble(listaAnoAtual.get(i)
						.getFaturamentoSemRVNE());
				// double rvne =
				// Double.parseDouble(listaAnoAtual.get(i).getRVNE());
				// double total = faturaSemRvne + rvne;
				double total = 0.0D;

				for (int j = 0; j < listaRvne.size(); j++) {
					String rvne = listaRvne.get(j).getMeses()[0];

					/*
					 * se o nome do produto da lista rvne for igual ao nome do
					 * produto da lista ano atual
					 */
					if (listaRvne.get(j).getProduto()
							.contains(listaAnoAtual.get(i).getProduto())
							|| listaAnoAtual.get(i).getProduto()
									.contains(listaRvne.get(j).getProduto())) {
						total = faturaSemRvne
								+ (Double.parseDouble(rvne == null ? "0.0"
										: rvne.replace(",", ".")) * 1000);
					} else if (listaRvne.get(j).getProduto().equals("CCA")
							&& listaAnoAtual.get(i).getProduto()
									.equals("MR Caixa Aqui")) {
						total = faturaSemRvne
								+ (Double.parseDouble(rvne == null ? "0.0"
										: rvne.replace(",", ".")) * 1000);
					}
				}
				// System.out.println(listaAnoAtual.get(i).getProduto() + " " +
				// roundForm.format(total));

				fatuVO.setAnoMes(listaAnoAtual.get(i).getAnoMes());
				fatuVO.setProduto(listaAnoAtual.get(i).getProduto());
				fatuVO.setFaturamentoTotal(roundForm.format(total));

				fatuVO.setBusinessPlan(listaAnoAtual.get(i).getBusinessPlan());

				listaTratadaAnoAtual.add(fatuVO);
			}// fim for

		}// fim else

		// Cria objeto com AnoMes, Produto, Faturamento total e BP para o
		// AnoAnterior
		for (int i = 0; i < listaAnoAnterior.size(); i++) {

			fatuVO = new FatuMensalVO();
			double faturaSemRvne = Double.parseDouble(listaAnoAnterior.get(i)
					.getFaturamentoSemRVNE());
			double rvne = Double.parseDouble(listaAnoAnterior.get(i).getRVNE());
			double total = faturaSemRvne + rvne;

			fatuVO.setAnoMes(listaAnoAnterior.get(i).getAnoMes());
			fatuVO.setProduto(listaAnoAnterior.get(i).getProduto());
			fatuVO.setFaturamentoTotal(roundForm.format(total));
			fatuVO.setBusinessPlan(listaAnoAnterior.get(i).getBusinessPlan());
			listaTratadaAnoAnterior.add(fatuVO);
		}

		// Verifica qual a maior lista e...
		List<FatuMensalVO> lista = null;
		if (listaTratadaAnoAnterior.size() > listaTratadaAnoAtual.size()) {
			lista = listaTratadaAnoAnterior;
		} else {
			lista = listaTratadaAnoAtual;
		}

		// ... insere apenas os nomes na lista FINAL
		for (int i = 0; i < lista.size(); i++) {
			fatuVO = new FatuMensalFinalVO();
			fatuVO.setProduto(lista.get(i).getProduto());
			listaFinal.add((FatuMensalFinalVO) fatuVO);
		}

		for (int i = 0; i < listaFinal.size(); i++) {

			String produto = listaFinal.get(i).getProduto();

			for (int j = 0; j < listaTratadaAnoAnterior.size(); j++) {
				// se o nome do produto da interação atual da listaFinal estiver
				// na lista AnoAnterior...
				if (produto.equalsIgnoreCase(listaTratadaAnoAnterior.get(j)
						.getProduto())) {
					// adiciona o valor na listaFinal
					listaFinal.get(i).setFaturamento2014(
							listaTratadaAnoAnterior.get(j)
									.getFaturamentoTotal());
					/*
					 * o BP 2015 também é inserido à lista do ano anterior
					 */
					listaFinal.get(i).setBusinessPlan2015(
							listaTratadaAnoAnterior.get(j).getBusinessPlan()
									.replace(".", ","));
				}
			}

			for (int k = 0; k < listaTratadaAnoAtual.size(); k++) {
				// se o nome do produto da interação atual da listaFinal estiver
				// na lista AnoAtual...
				if (produto.equalsIgnoreCase(listaTratadaAnoAtual.get(k)
						.getProduto())) {
					// adiciona o valor na listaFinal
					listaFinal.get(i).setFaturamento2015(
							listaTratadaAnoAtual.get(k).getFaturamentoTotal());
				}
			}

			// trata os casos que os valores ficaram nulos
			if (listaFinal.get(i).getFaturamento2014() == null) {
				listaFinal.get(i).setFaturamento2014("0,0");
			}
			if (listaFinal.get(i).getFaturamento2015() == null) {
				listaFinal.get(i).setFaturamento2015("0,0");
			}
			if (listaFinal.get(i).getBusinessPlan2015() == null) {
				listaFinal.get(i).setBusinessPlan2015("0,0");
			}
		}

		// a lista FINAL terá as colunas na seguinte ordem: NomeDoProduto, Fatu
		// 2014, Fatu 2015, BP 2015, 15/14R$, 15/14%, 15/BP%
		// System.out.println("final");

		for (int i = 0; i < listaFinal.size(); i++) {
			listaFinal.get(i).setReal15_14(
					roundForm.format(Double.parseDouble(listaFinal.get(i)
							.getFaturamento2015().replace(",", "."))
							- Double.parseDouble(listaFinal.get(i)
									.getFaturamento2014().replace(",", "."))));

			double percent15_14 = (Double.parseDouble(listaFinal.get(i)
					.getFaturamento2015().replace(",", ".")) / Double
					.parseDouble(listaFinal.get(i).getFaturamento2014()
							.replace(",", "."))) - 1;

			listaFinal.get(i)
					.setPercent15_14(
							Double.toString(percent15_14).contains("Infinity")
									|| Double.toString(percent15_14).contains(
											"NaN") ? "0%" : percentForm
									.format(percent15_14));

			if (!listaFinal.get(i).getBusinessPlan2015().replace(",", ".")
					.equalsIgnoreCase("0.0")) {
				listaFinal.get(i).setReal15_bp(
						roundForm.format(Double.parseDouble(listaFinal.get(i)
								.getFaturamento2015().replace(",", "."))
								- Double.parseDouble(listaFinal.get(i)
										.getBusinessPlan2015()
										.replace(",", "."))));
				listaFinal.get(i)
						.setPercent15_bp(
								percentForm.format(((Double
										.parseDouble(listaFinal.get(i)
												.getFaturamento2015()
												.replace(",", ".")) / Double
										.parseDouble(listaFinal.get(i)
												.getBusinessPlan2015()
												.replace(",", ".")))) - 1));
			} else {
				listaFinal.get(i).setReal15_bp("0,0");
				listaFinal.get(i).setPercent15_bp("0%");
			}
		}

		// Faz os calculos para os totais e atribui para o objeto da lista
		FatuMensalFinalVO total = new FatuMensalFinalVO();
		total.setProduto("Total");
		double resultFatu2014 = 0.0D;
		double resultFatu2015 = 0.0D;
		double resultBP2015 = 0.0D;
		for (int i = 0; i < listaFinal.size(); i++) {
			if (i == 0) {
				resultFatu2014 = Double.parseDouble(listaFinal.get(i)
						.getFaturamento2014().replace(",", "."));
				resultFatu2015 = Double.parseDouble(listaFinal.get(i)
						.getFaturamento2015().replace(",", "."));
				resultBP2015 = Double.parseDouble(listaFinal.get(i)
						.getBusinessPlan2015().replace(",", "."));

			} else {
				resultFatu2014 += Double.parseDouble(listaFinal.get(i)
						.getFaturamento2014().replace(",", "."));
				resultFatu2015 += Double.parseDouble(listaFinal.get(i)
						.getFaturamento2015().replace(",", "."));
				resultBP2015 += Double.parseDouble(listaFinal.get(i)
						.getBusinessPlan2015().replace(",", "."));
			}
		}
		total.setFaturamento2014(roundForm.format(resultFatu2014));
		total.setFaturamento2015(roundForm.format(resultFatu2015));
		total.setBusinessPlan2015(roundForm.format(resultBP2015));
		total.setReal15_14(roundForm.format(resultFatu2015 - resultFatu2014));

		total.setPercent15_14(Double.toString(
				((resultFatu2015 / resultFatu2014) - 1)).equals("NaN") ? "0%"
				: percentForm.format(((resultFatu2015 / resultFatu2014) - 1)));

		total.setReal15_bp(roundForm.format(resultFatu2015 - resultBP2015));

		if (resultBP2015 != 0) {
			total.setPercent15_bp(percentForm
					.format((resultFatu2015 / resultBP2015) - 1));
		} else {
			total.setPercent15_bp(percentForm.format(0.0D));
		}

		listaFinal.add(total);

		for (int i = 0; i < listaFinal.size(); i++) {
			if (!listaFinal.get(i).getProduto().equalsIgnoreCase("Total")) {

				double percentFatura = Double.parseDouble(listaFinal.get(i)
						.getFaturamento2015().replace(",", "."))
						/ Double.parseDouble(listaFinal
								.get(listaFinal.size() - 1)
								.getFaturamento2015().replace(",", "."));
				listaFinal.get(i).setPercentFatura(
						Double.toString(percentFatura).contains("NaN") ? "0%"
								: percentForm.format(percentFatura));
			}
		}
		// -----------------------------------------------PRINT
		// System.out
		// .println("Produto | Fat 2014 | Fat 2015 | BP 2015 | 15/14R$ | 15/14% | 15/BP R$ | 15/BP% | % Fat2015");
		// for (int i = 0; i < listaFinal.size(); i++) {
		// System.out.print(listaFinal.get(i).getProduto() + " | ");
		// System.out.print(listaFinal.get(i).getFaturamento2014() + " | ");
		// System.out.print(listaFinal.get(i).getFaturamento2015() + " | ");
		// System.out.print(listaFinal.get(i).getBusinessPlan2015() + " | ");
		// System.out.print(listaFinal.get(i).getReal15_14() + " | ");
		// System.out.print(listaFinal.get(i).getPercent15_14() + " | ");
		// System.out.print(listaFinal.get(i).getReal15_bp() + " | ");
		// System.out.print(listaFinal.get(i).getPercent15_bp() + " | ");
		// System.out.print(listaFinal.get(i).getPercentFatura());
		//
		// System.out.println(" ");
		// }
		return listaFinal;
	}// fim validaFatuMensal

	/**
	 * Retorna uma lista com
	 * "Produto | Fat 2014 | Fat 2015 | BP 2015 | 15/14R$ | 15/14% | 15/BP R$ | 15/BP% | % Fat2015"
	 * Dados do faturamento ACUMULADO até o mês passado por parâmetro, o retorno
	 * é feito com numeros com vírgula '15260903,90' ou porcentagem '67%'.
	 * 
	 * @arguments String, String
	 * @return List<FatuMensalFinalVO>
	 */
	public List<FatuMensalFinalVO> validaFatuMensalAcumulada(String ano,
			String mes) {
		VisaoAnaliticaDAO dao = new VisaoAnaliticaDAO();
		Uteis uteis = new Uteis();

		// pega o parametro ano e obtem o ano anterior ao recebido
		String anoAnterior = Integer.toString((Integer.parseInt(uteis
				.cortaRetornaAno(ano)) - 1));
		List<FatuMensalVO> listaAnoAtual = dao.selecionaFatuAcumulada(ano, mes);
		List<FatuMensalVO> listaAnoAnterior = dao.selecionaFatuAcumulada(
				uteis.cortaRetornaProduto(ano) + anoAnterior, mes);
		List<FatuMensalVO> listaTratadaAnoAtual = new ArrayList<FatuMensalVO>();
		List<FatuMensalVO> listaTratadaAnoAnterior = new ArrayList<FatuMensalVO>();
		List<FatuMensalFinalVO> listaFinal = new ArrayList<FatuMensalFinalVO>();

		List<FaturamentoVO> listaRvne = dao.selecionaRVNE(ano, 4, mes);

		//
		// System.out.println("");
		// System.out.println("aquiii RVNE acumul- ");
		// for (int j = 0; j < listaRvne.size(); j++) {
		// String rvne = listaRvne.get(j).getMeses()[0];
		// if (rvne == null) {
		// System.out.print(listaRvne.get(j).getProduto() + " ");
		// System.out.println("0.0");
		// } else {
		// System.out.print(listaRvne.get(j).getProduto() + " ");
		// System.out.println(Double.parseDouble(listaRvne.get(j)
		// .getMeses()[0].replace(",", ".")) * 1000);
		// }
		// }

		DecimalFormat roundForm = new DecimalFormat("0.00");
		DecimalFormat percentForm = new DecimalFormat("0%");
		FatuMensalVO fatuVO = null;

		/*
		 * Este trecho trata os casos em que não existiu faturamento no mês.
		 * Neste caso ele verifica se não possui o nome na lista e então cria o
		 * objeto com o nome do produto e o valor zerado
		 */
		int flag = 0;

		for (int j = 0; j < listaAnoAtual.size(); j++) {
			if (listaAnoAtual.get(0).getProduto().contains("Auto")) {
				// se o produto da lista for diferente do nome passado..
				if (!listaAnoAtual.get(j).getProduto()
						.equalsIgnoreCase("Auto Tranquilo Frota")) {
					flag++;
				}
			} else if (listaAnoAtual.get(0).getProduto().contains("MR")) {
				if (!listaAnoAtual.get(j).getProduto().equalsIgnoreCase("CCA")) {
					flag++;
				}
			}
		}

		// se a flag for igual ao tamanho da lista quer dizer que nao tem o
		// produto na lista
		if (flag == listaAnoAtual.size()) {
			if (listaAnoAtual.get(0).getProduto().contains("Auto")) {
				FatuMensalVO fatuMensalVo = new FatuMensalVO();
				fatuMensalVo.setAnoMes(uteis.cortaRetornaAno(ano) + mes);
				fatuMensalVo.setProduto("Auto Tranquilo Frota");
				fatuMensalVo.setFaturamentoSemRVNE("0.0");
				listaAnoAtual.add(fatuMensalVo);
			}
		}

		/*
		 * Cria objeto com AnoMes, Produto, Faturamento total e BP para o Ano
		 * Atual
		 */
		for (int i = 0; i < listaAnoAtual.size(); i++) {
			fatuVO = new FatuMensalVO();
			double faturaSemRvne = Double.parseDouble(listaAnoAtual.get(i)
					.getFaturamentoSemRVNE());
			// double rvne = Double.parseDouble(listaAnoAtual.get(i).getRVNE());
			// double total = faturaSemRvne + rvne;
			double total = 0.0D;

			for (int j = 0; j < listaRvne.size(); j++) {
				String rvne = listaRvne.get(j).getMeses()[0];
				/*
				 * se o nome do produto da lista rvne for igual ao nome do
				 * produto da lista ano atual
				 */
				if (listaRvne.get(j).getProduto()
						.contains(listaAnoAtual.get(i).getProduto())
						|| listaAnoAtual.get(i).getProduto()
								.contains(listaRvne.get(j).getProduto())) {
					total = faturaSemRvne
							+ (Double.parseDouble(rvne == null ? "0.0" : rvne
									.replace(",", ".")) * 1000);
				} else if (listaRvne.get(j).getProduto().equals("CCA")
						&& listaAnoAtual.get(i).getProduto()
								.equals("MR Caixa Aqui")) {
					total = faturaSemRvne
							+ (Double.parseDouble(rvne == null ? "0.0" : rvne
									.replace(",", ".")) * 1000);
				}
			}
			fatuVO.setAnoMes(listaAnoAtual.get(i).getAnoMes());
			fatuVO.setProduto(listaAnoAtual.get(i).getProduto());
			fatuVO.setFaturamentoTotal(roundForm.format(total));
			fatuVO.setBusinessPlan(listaAnoAtual.get(i).getBusinessPlan());

			listaTratadaAnoAtual.add(fatuVO);
		}

		// Cria objeto com AnoMes, Produto, Faturamento total e BP para o Ano
		// Anterior
		for (int i = 0; i < listaAnoAnterior.size(); i++) {
			fatuVO = new FatuMensalVO();
			double faturaSemRvne = Double.parseDouble(listaAnoAnterior.get(i)
					.getFaturamentoSemRVNE());
			double rvne = Double.parseDouble(listaAnoAnterior.get(i).getRVNE());
			double total = faturaSemRvne + rvne;

			fatuVO.setAnoMes(listaAnoAnterior.get(i).getAnoMes());
			fatuVO.setProduto(listaAnoAnterior.get(i).getProduto());
			fatuVO.setFaturamentoTotal(roundForm.format(total));
			fatuVO.setBusinessPlan(listaAnoAnterior.get(i).getBusinessPlan());

			listaTratadaAnoAnterior.add(fatuVO);
		}

		// Verifica qual a maior lista e...
		List<FatuMensalVO> lista = null;
		if (listaTratadaAnoAnterior.size() > listaTratadaAnoAtual.size()) {
			lista = listaTratadaAnoAnterior;
		} else {
			lista = listaTratadaAnoAtual;
		}

		// ... insere apenas os nomes na lista FINAL
		for (int i = 0; i < lista.size(); i++) {
			fatuVO = new FatuMensalFinalVO();
			fatuVO.setProduto(lista.get(i).getProduto());
			listaFinal.add((FatuMensalFinalVO) fatuVO);
		}

		for (int i = 0; i < listaFinal.size(); i++) {
			String produto = listaFinal.get(i).getProduto();
			for (int j = 0; j < listaTratadaAnoAnterior.size(); j++) {
				// se o nome do produto da interação atual da listaFinal estiver
				// na lista AnoAnterior...
				if (produto.equalsIgnoreCase(listaTratadaAnoAnterior.get(j)
						.getProduto())) {
					// adiciona o valor na listaFinal
					listaFinal.get(i).setFaturamento2014(
							listaTratadaAnoAnterior.get(j)
									.getFaturamentoTotal());
					listaFinal.get(i).setBusinessPlan2015(
							listaTratadaAnoAnterior.get(j).getBusinessPlan()
									.replace(".", ","));// o
														// BP
														// 2015
														// também
														// é
					// vinculado à lista do
					// ano anterior
				}
			}
			for (int k = 0; k < listaTratadaAnoAtual.size(); k++) {
				// se o nome do produto da interação atual da listaFinal estiver
				// na lista AnoAtual...
				if (produto.equalsIgnoreCase(listaTratadaAnoAtual.get(k)
						.getProduto())) {
					// adiciona o valor na listaFinal
					listaFinal.get(i).setFaturamento2015(
							listaTratadaAnoAtual.get(k).getFaturamentoTotal());

				}
			}

			// trata os casos que os valores ficaram nulos
			if (listaFinal.get(i).getFaturamento2014() == null) {
				listaFinal.get(i).setFaturamento2014("0,0");
			}
			if (listaFinal.get(i).getFaturamento2015() == null) {
				listaFinal.get(i).setFaturamento2015("0,0");
			}
			if (listaFinal.get(i).getBusinessPlan2015() == null) {
				listaFinal.get(i).setBusinessPlan2015("0,0");
			}
		}

		// a lista FINAL terá as colunas na seguinte ordem: NomeDoProduto, Fatu
		// 2014, Fatu 2015, BP 2015, 15/14R$, 15/14%, 15/BP%
		// System.out.println("final");

		for (int i = 0; i < listaFinal.size(); i++) {
			listaFinal.get(i).setReal15_14(
					roundForm.format(Double.parseDouble(listaFinal.get(i)
							.getFaturamento2015().replace(",", "."))
							- Double.parseDouble(listaFinal.get(i)
									.getFaturamento2014().replace(",", "."))));

			double percent15_14 = (Double.parseDouble(listaFinal.get(i)
					.getFaturamento2015().replace(",", ".")) / Double
					.parseDouble(listaFinal.get(i).getFaturamento2014()
							.replace(",", "."))) - 1;

			listaFinal.get(i).setPercent15_14(
					Double.toString(percent15_14).contains("Infinity") ? "0%"
							: percentForm.format(percent15_14));

			if (!listaFinal.get(i).getBusinessPlan2015().replace(",", ".")
					.equalsIgnoreCase("0.0")) {
				listaFinal.get(i).setReal15_bp(
						roundForm.format(Double.parseDouble(listaFinal.get(i)
								.getFaturamento2015().replace(",", "."))
								- Double.parseDouble(listaFinal.get(i)
										.getBusinessPlan2015()
										.replace(",", "."))));

				double percent15_bp = (Double.parseDouble(listaFinal.get(i)
						.getFaturamento2015().replace(",", ".")) / Double
						.parseDouble(listaFinal.get(i).getBusinessPlan2015()
								.replace(",", "."))) - 1;
				listaFinal.get(i).setPercent15_bp(
						Double.toString(percent15_bp).contains("Infinity")
								|| Double.toString(percent15_bp)
										.equalsIgnoreCase("NaN") ? "0%"
								: percentForm.format(percent15_bp));

			} else {
				listaFinal.get(i).setReal15_bp("0,0");
				listaFinal.get(i).setPercent15_bp("0%");
			}

		}

		// Faz os calculos para os totais e atribui para o objeto da lista
		FatuMensalFinalVO total = new FatuMensalFinalVO();
		total.setProduto("Total");
		double resultFatu2014 = 0.0D;
		double resultFatu2015 = 0.0D;
		double resultBP2015 = 0.0D;
		for (int i = 0; i < listaFinal.size(); i++) {
			if (i == 0) {
				resultFatu2014 = Double.parseDouble(listaFinal.get(i)
						.getFaturamento2014().replace(",", "."));
				resultFatu2015 = Double.parseDouble(listaFinal.get(i)
						.getFaturamento2015().replace(",", "."));
				resultBP2015 = Double.parseDouble(listaFinal.get(i)
						.getBusinessPlan2015().replace(",", "."));
			} else {
				resultFatu2014 += Double.parseDouble(listaFinal.get(i)
						.getFaturamento2014().replace(",", "."));
				resultFatu2015 += Double.parseDouble(listaFinal.get(i)
						.getFaturamento2015().replace(",", "."));
				resultBP2015 += Double.parseDouble(listaFinal.get(i)
						.getBusinessPlan2015().replace(",", "."));
			}
		}
		total.setFaturamento2014(roundForm.format(resultFatu2014));
		total.setFaturamento2015(roundForm.format(resultFatu2015));
		total.setBusinessPlan2015(roundForm.format(resultBP2015));
		total.setReal15_14(roundForm.format(resultFatu2015 - resultFatu2014));
		total.setPercent15_14(percentForm
				.format((resultFatu2015 / resultFatu2014) - 1));
		total.setReal15_bp(roundForm.format(resultFatu2015 - resultBP2015));
		if (resultBP2015 != 0) {
			total.setPercent15_bp(percentForm
					.format((resultFatu2015 / resultBP2015) - 1));
		} else {
			total.setPercent15_bp(percentForm.format(0.0D));
		}
		listaFinal.add(total);

		for (int i = 0; i < listaFinal.size(); i++) {
			if (!listaFinal.get(i).getProduto().equalsIgnoreCase("Total")) {

				double percentFatura = Double.parseDouble(listaFinal.get(i)
						.getFaturamento2015().replace(",", "."))
						/ Double.parseDouble(listaFinal
								.get(listaFinal.size() - 1)
								.getFaturamento2015().replace(",", "."));

				listaFinal.get(i).setPercentFatura(
						percentForm.format(Double.toString(percentFatura)
								.contains("Infinity") ? "0%" : percentFatura));
			}
		}
		// -----------------------------------------------PRINT
		// System.out.println("Produto | Fat 2014 | Fat 2015 | BP 2015 | 15/14R$
		// | 15/14% | 15/BP R$ | 15/BP% | % Fat2015");
		// for (int i = 0; i < listaFinal.size(); i++) {
		// System.out.print(listaFinal.get(i).getProduto() + " | ");
		// System.out.print(listaFinal.get(i).getFaturamento2014() + " | ");
		// System.out.print(listaFinal.get(i).getFaturamento2015() + " | ");
		// System.out.print(listaFinal.get(i).getBusinessPlan2015() + " | ");
		// System.out.print(listaFinal.get(i).getReal15_14() + " | ");
		// System.out.print(listaFinal.get(i).getPercent15_14() + " | ");
		// System.out.print(listaFinal.get(i).getReal15_bp() + " | ");
		// System.out.print(listaFinal.get(i).getPercent15_bp() + " | ");
		// System.out.print(listaFinal.get(i).getPercentFatura());
		//
		// System.out.println(" ");
		// }
		return listaFinal;
	}// fim validaFatuMensalAcumulada

	public List validaSelecionaRVNE(String ano) {
		VisaoAnaliticaDAO dao = new VisaoAnaliticaDAO();
		return dao.selecionaRVNE(ano, 1, "0");
	}

	/**
	 * Recebe a request da página cria um objeto e envia para o DAO fazer um
	 * SELECT trazendo apenas um valor.
	 * 
	 */
	public FatuMensalVO validaBuscaRVNE(HttpServletRequest req) {
		RvneVO rvne = new RvneVO();
		VisaoAnaliticaDAO dao = new VisaoAnaliticaDAO();

		rvne.setId(Integer.parseInt(req.getParameter("id")));
		rvne.setMes(req.getParameter("coluna"));

		return dao.buscaUnicaRVNE(rvne);
	}

	/**
	 * Recebe a request da página e verifica qual "name" e "value" do campo que
	 * vem da form da página. Cria um objeto e envia para o DAO fazer um UPDATE.
	 * 
	 */
	public void validaAtualizaRVNE(HttpServletRequest req) {
		RvneVO rvne = new RvneVO();
		VisaoAnaliticaDAO dao = new VisaoAnaliticaDAO();

		// Obtem o mes e o idProduto
		for (Enumeration<String> e = req.getParameterNames(); e
				.hasMoreElements();) {
			System.out.println(e.nextElement().toString());

		}

		String[] partes = req.getParameter("nome").split("_");

		String mes = partes[0];
		rvne.setMes(mes);

		String idProduto = partes[1];
		rvne.setId(Integer.parseInt(idProduto));

		System.out.println("Mes: " + mes + " IdProduto: " + idProduto);
		for (String string : partes) {
			System.out.println(string);
		}

		try {
			rvne.setValor(req.getParameter("valor"));
			// System.out.println("FOR 2 - " + req.getParameter((String)
			// e.nextElement()));
		} catch (NoSuchElementException nsee) {
			nsee.printStackTrace();
		}
		System.out.println(rvne.getValor());
		dao.atualizaRVNE(rvne);

	}

	/**
	 * Recebe a request da página cria um objeto e envia para o DAO fazer um
	 * UPDATE.
	 * 
	 */
	public void validaApagaRVNE(HttpServletRequest req) {
		RvneVO rvne = new RvneVO();
		VisaoAnaliticaDAO dao = new VisaoAnaliticaDAO();

		rvne.setId(Integer.parseInt(req.getParameter("id")));
		rvne.setMes(req.getParameter("coluna"));

		dao.apagaRVNE(rvne);
	}

}
