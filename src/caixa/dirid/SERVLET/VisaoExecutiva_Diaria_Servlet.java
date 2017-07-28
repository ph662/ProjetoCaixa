package caixa.dirid.SERVLET;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import caixa.dirid.BO.VisaoExecutiva_Diaria_BO;
import caixa.dirid.UTEIS.Uteis;

public class VisaoExecutiva_Diaria_Servlet extends HttpServlet {

	public VisaoExecutiva_Diaria_Servlet() {
		super();
	}

	protected void processRequest(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

		if (request.getParameter("tipo").equals("reqFaturamentoAuto")) {
			
			if (request.getParameter("categ") != null) {
				switch (request.getParameter("categ")) {
					case "baseFaturamentoRO":
						baixarBaseROFaturamento(request,response,"AUTOMOVEIS","Autom&oacute;vel");
					break;
					case "baseSinistroPendenteFaixa":
						baixarBaseSinistroPendente(request,response,"AUTOMOVEIS","Autom&oacute;vel");
					break;
					case "baseSinistroCompleta":
						baixarBaseSinistroCompleta(request,response,"AUTOMOVEIS","Autom&oacute;vel");
					break;
				}		
			} else {
				consultaGeral(request,response,"AUTOMOVEIS","Autom&oacute;vel");
			}
			

		} else if (request.getParameter("tipo").equals("reqFaturamentoAutoTranqExclu")) {
			
			if (request.getParameter("categ") != null) {
				switch (request.getParameter("categ")) {
					case "baseFaturamentoRO":
						baixarBaseROFaturamento(request,response,"Auto Tranquilo Exclusivo","Auto Tranquilo Exclusivo");
					break;
					case "baseSinistroPendenteFaixa":
						baixarBaseSinistroPendente(request,response,"Auto Tranquilo Exclusivo","Auto Tranquilo Exclusivo");
					break;
					case "baseSinistroCompleta":
						baixarBaseSinistroCompleta(request,response,"Auto Tranquilo Exclusivo","Auto Tranquilo Exclusivo");
					break;
				}		
			} else {
				consultaGeral(request,response,"Auto Tranquilo Exclusivo","Auto Tranquilo Exclusivo");
			}
		
		} else if (request.getParameter("tipo").equals("reqFaturamentoAutoTranqCorren")) {
			
			if (request.getParameter("categ") != null) {
				switch (request.getParameter("categ")) {
					case "baseFaturamentoRO":
						baixarBaseROFaturamento(request,response,"Auto Tranquilo Correntista","Auto Tranquilo Correntista");
					break;
					case "baseSinistroPendenteFaixa":
						baixarBaseSinistroPendente(request,response,"Auto Tranquilo Correntista","Auto Tranquilo Correntista");
					break;
					case "baseSinistroCompleta":
						baixarBaseSinistroCompleta(request,response,"Auto Tranquilo Correntista","Auto Tranquilo Correntista");
					break;
				}		
			} else {
				consultaGeral(request,response,"Auto Tranquilo Correntista","Auto Tranquilo Correntista");
			}
					
		} else if (request.getParameter("tipo").equals("reqFaturamentoAutoTranqFrota")) {
			
			if (request.getParameter("categ") != null) {
				switch (request.getParameter("categ")) {
					case "baseFaturamentoRO":
						baixarBaseROFaturamento(request,response,"Auto Tranquilo Frota","Auto Tranquilo Frota");
					break;
					case "baseSinistroPendenteFaixa":
						baixarBaseSinistroPendente(request,response,"Auto Tranquilo Frota","Auto Tranquilo Frota");
					break;
					case "baseSinistroCompleta":
						baixarBaseSinistroCompleta(request,response,"Auto Tranquilo Frota","Auto Tranquilo Frota");
					break;
				}		
			} else {
				consultaGeral(request,response,"Auto Tranquilo Frota","Auto Tranquilo Frota");
			}
			
		} else if (request.getParameter("tipo").equals("reqFaturamentoAutoFacil")) {
			
			if (request.getParameter("categ") != null) {
				switch (request.getParameter("categ")) {
					case "baseFaturamentoRO":
						baixarBaseROFaturamento(request,response,"Auto Fácil","Auto F&aacute;cil");
					break;
					case "baseSinistroPendenteFaixa":
						baixarBaseSinistroPendente(request,response,"Auto Fácil","Auto F&aacute;cil");
					break;
					case "baseSinistroCompleta":
						baixarBaseSinistroCompleta(request,response,"Auto Fácil","Auto F&aacute;cil");
					break;
				}		
			} else {
				consultaGeral(request,response,"Auto Fácil","Auto F&aacute;cil");
			}
		
		} else if (request.getParameter("tipo").equals("reqFaturamentoRDPJ")) {
			
			if (request.getParameter("categ") != null) {
				switch (request.getParameter("categ")) {
					case "baseFaturamentoRO":
						baixarBaseROFaturamento(request,response,"RDPJ","RD PJ");
					break;
					case "baseSinistroPendenteFaixa":
						baixarBaseSinistroPendente(request,response,"RDPJ","RD PJ");
					break;
					case "baseSinistroCompleta":
						baixarBaseSinistroCompleta(request,response,"RDPJ","RD PJ");
					break;
				}		
			} else {
				consultaGeral(request,response,"RDPJ","RD PJ");
			}
			
		} else if (request.getParameter("tipo").equals("reqFaturamentoRdEquipamentos")) {
			
			if (request.getParameter("categ") != null) {
				switch (request.getParameter("categ")) {
					case "baseFaturamentoRO":
						baixarBaseROFaturamento(request,response,"Rd Equipamentos","RD Equipamentos");
					break;
					case "baseSinistroPendenteFaixa":
						baixarBaseSinistroPendente(request,response,"Rd Equipamentos","RD Equipamentos");
					break;
					case "baseSinistroCompleta":
						baixarBaseSinistroCompleta(request,response,"Rd Equipamentos","RD Equipamentos");
					break;
				}		
			} else {
				consultaGeral(request,response,"Rd Equipamentos","RD Equipamentos");
			}
			
		} else if (request.getParameter("tipo").equals("reqFaturamentoEmpresarial")) {
			
			if (request.getParameter("categ") != null) {
				switch (request.getParameter("categ")) {
					case "baseFaturamentoRO":
						baixarBaseROFaturamento(request,response,"MR Empresarial","MR Empresarial");
					break;
					case "baseSinistroPendenteFaixa":
						baixarBaseSinistroPendente(request,response,"MR Empresarial","MR Empresarial");
					break;
					case "baseSinistroCompleta":
						baixarBaseSinistroCompleta(request,response,"MR Empresarial","MR Empresarial");
					break;
				}		
			} else {
				consultaGeral(request,response,"MR Empresarial","MR Empresarial");
			}
			
		} else if (request.getParameter("tipo").equals("reqFaturamentoLoterico")) {
			
			if (request.getParameter("categ") != null) {
				switch (request.getParameter("categ")) {
					case "baseFaturamentoRO":
						baixarBaseROFaturamento(request,response,"Loterico","MR Lot&eacute;rico");
					break;
					case "baseSinistroPendenteFaixa":
						baixarBaseSinistroPendente(request,response,"Loterico","MR Lot&eacute;rico");
					break;
					case "baseSinistroCompleta":
						baixarBaseSinistroCompleta(request,response,"Loterico","MR Lot&eacute;rico");
					break;
				}		
			} else {
				consultaGeral(request,response,"Loterico","MR Lot&eacute;rico");
			}
			
		} else if (request.getParameter("tipo").equals("reqFaturamentoCCA")) {
			
			if (request.getParameter("categ") != null) {
				switch (request.getParameter("categ")) {
					case "baseFaturamentoRO":
						baixarBaseROFaturamento(request, response, "CCA", "MR CCA");
					break;
				case "baseSinistroPendenteFaixa":
					baixarBaseSinistroPendente(request, response, "CCA", "MR CCA");
					break;
				case "baseSinistroCompleta":
					baixarBaseSinistroCompleta(request, response, "CCA", "MR CCA");
					break;
				}
			} else {
				consultaGeral(request, response, "CCA", "MR CCA");
			}

		} else if (request.getParameter("tipo").equals("reqFaturamentoRDPF")) {

			if (request.getParameter("categ") != null) {
				switch (request.getParameter("categ")) {
				case "baseFaturamentoRO":
					baixarBaseROFaturamento(request, response, "RDPF", "RD PF");
					break;
				case "baseSinistroPendenteFaixa":
					baixarBaseSinistroPendente(request, response, "RDPF",
							"RD PF");
					break;
				case "baseSinistroCompleta":
					baixarBaseSinistroCompleta(request, response, "RDPF",
							"RD PF");
					break;
				}
			} else {
				consultaGeral(request, response, "RDPF", "RD PF");
			}

		} else if (request.getParameter("tipo").equals(
				"reqFaturamentoRdCorrentista")) {

			if (request.getParameter("categ") != null) {
				switch (request.getParameter("categ")) {
				case "baseFaturamentoRO":
					baixarBaseROFaturamento(request, response,
							"MR Residencial Correntista", "RD Correntista");
					break;
				case "baseSinistroPendenteFaixa":
					baixarBaseSinistroPendente(request, response,
							"MR Residencial Correntista", "RD Correntista");
					break;
				case "baseSinistroCompleta":
					baixarBaseSinistroCompleta(request, response,
							"MR Residencial Correntista", "RD Correntista");
					break;
				}
			} else {
				consultaGeral(request, response, "MR Residencial Correntista",
						"RD Correntista");
			}

		} else if (request.getParameter("tipo").equals("reqFaturamentoFacilRd")) {

			if (request.getParameter("categ") != null) {
				switch (request.getParameter("categ")) {
				case "baseFaturamentoRO":
					baixarBaseROFaturamento(request, response,
							"Facil Residencial", "F&aacute;cil RD");
					break;
				case "baseSinistroPendenteFaixa":
					baixarBaseSinistroPendente(request, response,
							"Facil Residencial", "F&aacute;cil RD");
					break;
				case "baseSinistroCompleta":
					baixarBaseSinistroCompleta(request, response,
							"Facil Residencial", "F&aacute;cil RD");
					break;
				}
			} else {
				consultaGeral(request, response, "Facil Residencial",
						"F&aacute;cil RD");
			}

		} else if (request.getParameter("tipo").equals(
				"reqFaturamentoRdEconomiario")) {// exclusivo

			if (request.getParameter("categ") != null) {
				switch (request.getParameter("categ")) {
				case "baseFaturamentoRO":
					baixarBaseROFaturamento(request, response,
							"MR Residencial Economiario", "RD Exclusivo");
					break;
				case "baseSinistroPendenteFaixa":
					baixarBaseSinistroPendente(request, response,
							"MR Residencial Economiario", "RD Exclusivo");
					break;
				case "baseSinistroCompleta":
					baixarBaseSinistroCompleta(request, response,
							"MR Residencial Economiario", "RD Exclusivo");
					break;
				}
			} else {
				consultaGeral(request, response, "MR Residencial Economiario",
						"RD Exclusivo");
			}

		} else if (request.getParameter("tipo").equals("reqFaturamentoLarMais")) {

			if (request.getParameter("categ") != null) {
				switch (request.getParameter("categ")) {
				case "baseFaturamentoRO":
					baixarBaseROFaturamento(request, response, "Lar Mais",
							"Lar Mais");
					break;
				case "baseSinistroPendenteFaixa":
					baixarBaseSinistroPendente(request, response, "Lar Mais",
							"Lar Mais");
					break;
				case "baseSinistroCompleta":
					baixarBaseSinistroCompleta(request, response, "Lar Mais",
							"Lar Mais");
					break;
				}
			} else {
				consultaGeral(request, response, "Lar Mais", "Lar Mais");
			}

		} else if (request.getParameter("tipo").equals("reqFaturamentoAporte")) {

			if (request.getParameter("categ") != null) {
				switch (request.getParameter("categ")) {
				case "baseFaturamentoRO":
					baixarBaseROFaturamento(request, response,
							"MR Residencial Aporte Caixa", "Aporte Caixa");
					break;
				case "baseSinistroPendenteFaixa":
					baixarBaseSinistroPendente(request, response,
							"MR Residencial Aporte Caixa", "Aporte Caixa");
					break;
				case "baseSinistroCompleta":
					baixarBaseSinistroCompleta(request, response,
							"MR Residencial Aporte Caixa", "Aporte Caixa");
					break;
				}
			} else {
				consultaGeral(request, response, "MR Residencial Aporte Caixa",
						"Aporte Caixa");
			}

		} else if (request.getParameter("tipo").equals(
				"reqFaturamentoRdPfOutros")) {

			if (request.getParameter("categ") != null) {
				switch (request.getParameter("categ")) {
				case "baseFaturamentoRO":
					baixarBaseROFaturamento(request, response, "Rd Pf Outros",
							"RD PF Outros");
					break;
				case "baseSinistroPendenteFaixa":
					baixarBaseSinistroPendente(request, response,
							"Rd Pf Outros", "RD PF Outros");
					break;
				case "baseSinistroCompleta":
					baixarBaseSinistroCompleta(request, response,
							"Rd Pf Outros", "RD PF Outros");
					break;
				}
			} else {
				consultaGeral(request, response, "Rd Pf Outros", "RD PF Outros");
			}

		} else if (request.getParameter("tipo").equals("reqFaturamentoDirid")) {

			if (request.getParameter("categ") != null) {
				switch (request.getParameter("categ")) {
				case "baseFaturamentoRO":
					baixarBaseROFaturamento(request, response, "Dirid", "DIRID");
					break;
				case "baseSinistroPendenteFaixa":
					baixarBaseSinistroPendente(request, response, "Dirid",
							"DIRID");
					break;
				case "baseSinistroCompleta":
					baixarBaseSinistroCompleta(request, response, "Dirid",
							"DIRID");
					break;
				}
			} else {
				consultaGeral(request, response, "Dirid", "DIRID");
			}

		}

	}

	private void consultaGeral(HttpServletRequest request,
			HttpServletResponse response, String produto, String produtoHTML)
			throws ServletException, IOException {

		String anoAnterior = Integer.toString(Integer.parseInt(request
				.getParameter("ano")) - 1);
		String anoAtual = request.getParameter("ano");

		VisaoExecutiva_Diaria_BO boVisoes = new VisaoExecutiva_Diaria_BO(
				produto + anoAtual);

		request.setAttribute(
				"periodo",
				boVisoes.validaSelecionaPeriodo("teste"
						+ request.getParameter("ano")));

		request.setAttribute("faturamentoAnoAtual", boVisoes
				.validaSelecionaTotalFaturamento(produto + anoAtual, "atual"));
		request.setAttribute("faturamentoAnoAnterior", boVisoes
				.validaSelecionaTotalFaturamento(produto + anoAnterior,
						"anterior"));

		request.setAttribute("faturamentoDetalhadoAnoAtual", boVisoes
				.validaSelecionaFaturamentoDetalhado(produto + anoAtual,
						"atual"));
		request.setAttribute(
				"faturamentoDetalhadoAnoAnterior",
				boVisoes.validaSelecionaFaturamentoDetalhado(produto
						+ anoAnterior, "anterior"));

		request.setAttribute("canceladosDivididoPorEmitidosAnoAtual", boVisoes
				.validaCanceladosDividoPorEmitidos(produto + anoAtual, "atual"));
		request.setAttribute(
				"canceladosDivididoPorEmitidosAnoAnterior",
				boVisoes.validaCanceladosDividoPorEmitidos(produto
						+ anoAnterior, "anterior"));

		request.setAttribute(
				"faturaAnoAtualAcumulado",
				boVisoes.validaSelecionaTotalFaturamentoAcumulado(produto
						+ anoAtual, 1, "atual"));
		request.setAttribute(
				"faturaAnoAnteriorAcumulado",
				boVisoes.validaSelecionaTotalFaturamentoAcumulado(produto
						+ anoAnterior, 1, "anterior"));

		request.setAttribute("analiseSinistrosAvisados",
				boVisoes.validaSelecionaSinistros(produto + anoAtual, 1));
		request.setAttribute("analiseSinistrosIndenizados",
				boVisoes.validaSelecionaSinistros(produto + anoAtual, 2));
		request.setAttribute("analiseSinistrosPendentes",
				boVisoes.validaSelecionaSinistros(produto + anoAtual, 3));
		request.setAttribute("analiseSinistrosDespesas",
				boVisoes.validaSelecionaSinistros(produto + anoAtual, 4));

		request.setAttribute("sinistrosPendentes_FaixaValor",
				boVisoes.validaSelecionaSinistroPendente_Faixa(2));
		request.setAttribute("sinistrosPendentes_FaixaTempo",
				boVisoes.validaSelecionaSinistroPendente_Faixa(1));
		request.setAttribute("dadosDiarios", boVisoes
				.validaSelecionaDetalhesDadosDiarios(produto + anoAtual));

		request.setAttribute("emissaoPorCanal",
				boVisoes.validaSelecionaEmissaoPorCanal());

		request.setAttribute(
				"anoAtualDivididoPorAnteriorAcumulado",
				boVisoes.validaAnoAtualDividoPorAnoAnteriorAcumulado(produto
						+ anoAtual));

		request.setAttribute("anoAtualDivididoPorAnterior",
				boVisoes.validaAnoAtualDividoPorAnoAnterior(produto + anoAtual));
		request.setAttribute("anoAtualParametro", anoAtual);
		request.setAttribute("anoAnteriorParametro", anoAnterior);

		request.setAttribute("titulo", produtoHTML);
		request.getRequestDispatcher("ajaxPagGraficoDiario.jsp").forward(
				request, response);
	}

	private void baixarBaseSinistroPendente(HttpServletRequest request,
			HttpServletResponse response, String produto, String produtoHTML)
			throws ServletException, IOException {
		Uteis uteis = new Uteis();
		String dataAtual = uteis.dataAtual(4);
		String dataCut[] = dataAtual.split("/");
		String anoAtual = dataCut[2];
		String mes_SO = dataCut[1];
		String dia_SO = dataCut[0];

		VisaoExecutiva_Diaria_BO boVisoes = new VisaoExecutiva_Diaria_BO(
				produto + anoAtual, "baixarBaseSinistroPendente");

		response.setContentType("application/ms-excel"); // or you can use
															// text/csv
		response.setHeader("Content-Disposition",
				"attachment; filename=baseAnaliticaSinPendentes_" + dia_SO
						+ "-" + mes_SO + "-" + anoAtual + ".csv");

		boVisoes.validaSeleciona_Base_SinistroPendente_Faixa(
				produto + anoAtual, response);
	}

	private void baixarBaseSinistroCompleta(HttpServletRequest request,
			HttpServletResponse response, String produto, String produtoHTML)
			throws ServletException, IOException {
		Uteis uteis = new Uteis();
		String dataAtual = uteis.dataAtual(4);
		String dataCut[] = dataAtual.split("/");
		String anoParam = request.getParameter("ano");
		String anoAtual = dataCut[2];
		String mes_SO = dataCut[1];
		String dia_SO = dataCut[0];

		VisaoExecutiva_Diaria_BO boVisoes = new VisaoExecutiva_Diaria_BO(
				produto + anoParam, "baixarBaseSinistroCompleta");

		response.setContentType("application/ms-excel"); // or you can use
															// text/csv
		response.setHeader("Content-Disposition",
				"attachment; filename=baseAnaliticaSinistros_" + dia_SO + "-"
						+ mes_SO + "-" + anoAtual + ".csv");

		boVisoes.validaSeleciona_Base_SinistrosCompleta(produto + anoParam,
				response);
	}

	private void baixarBaseROFaturamento(HttpServletRequest request,HttpServletResponse response, String produto, String produtoHTML)throws ServletException, IOException {
		Uteis uteis = new Uteis();
		String dataAtual = uteis.dataAtual(4);
		String dataCut[] = dataAtual.split("/");
		String anoParam = request.getParameter("ano");
		String anoAtual = dataCut[2];
		String mes_SO = dataCut[1];
		String dia_SO = dataCut[0];

		VisaoExecutiva_Diaria_BO boVisoes = new VisaoExecutiva_Diaria_BO(produto + anoParam, "baixarBaseROFaturamento");
		String params = dia_SO+"_"+mes_SO+"_"+anoAtual+"_"+produto;
		boVisoes.validaSeleciona_Base_faturamentoRO(produto + anoParam,	response,params);
		System.out.println("buffer " + response.getBufferSize());

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
}
