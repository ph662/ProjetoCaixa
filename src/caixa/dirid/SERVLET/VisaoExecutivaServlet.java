package caixa.dirid.SERVLET;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import caixa.dirid.BO.VisaoExecutivaBO;

public class VisaoExecutivaServlet extends HttpServlet {

	public VisaoExecutivaServlet() {
		super();
	}

	protected void processRequest(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

		VisaoExecutivaBO boVisoes = new VisaoExecutivaBO();
		String anoAnterior = Integer.toString(Integer.parseInt(request.getParameter("ano")) - 1);
		String anoAtual = request.getParameter("ano");
		
		if (request.getParameter("tipo").equals("reqFaturamentoAuto")) {
			request.setAttribute("faturamentoAnoAtual",boVisoes.validaSelecionaTotalFaturamento("AUTOMOVEIS"+anoAtual));
			request.setAttribute("faturamentoAnoAnterior",boVisoes.validaSelecionaTotalFaturamento("AUTOMOVEIS"+anoAnterior));
			request.setAttribute("faturamentoDetalhadoAnoAtual",boVisoes.validaSelecionaFaturamentoDetalhado("AUTOMOVEIS"+anoAtual));
			request.setAttribute("faturamentoDetalhadoAnoAnterior",boVisoes.validaSelecionaFaturamentoDetalhado("AUTOMOVEIS"+anoAnterior));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAtual",boVisoes.validaCanceladosDividoPorEmitidos("AUTOMOVEIS"+anoAtual));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAnterior",boVisoes.validaCanceladosDividoPorEmitidos("AUTOMOVEIS"+anoAnterior));
			request.setAttribute("faturaAnoAtualAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("AUTOMOVEIS"+anoAtual,1));
			request.setAttribute("faturaAnoAnteriorAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("AUTOMOVEIS"+anoAnterior,1));
			request.setAttribute("analiseSinistrosAvisados", boVisoes.validaSelecionaSinistros("AUTOMOVEIS"+anoAtual,1));
			request.setAttribute("analiseSinistrosIndenizados", boVisoes.validaSelecionaSinistros("AUTOMOVEIS"+anoAtual,2));
			request.setAttribute("analiseSinistrosPendentes", boVisoes.validaSelecionaSinistros("AUTOMOVEIS"+anoAtual,3));
			request.setAttribute("analiseSinistrosDespesas", boVisoes.validaSelecionaSinistros("AUTOMOVEIS"+anoAtual,4));
			request.setAttribute("anoAtualDivididoPorAnteriorAcumulado",boVisoes.validaAnoAtualDividoPorAnoAnteriorAcumulado("AUTOMOVEIS"+anoAtual));
			request.setAttribute("anoAtualDivididoPorAnterior",boVisoes.validaAnoAtualDividoPorAnoAnterior("AUTOMOVEIS"+anoAtual));
			request.setAttribute("anoAtualParametro", anoAtual);
			request.setAttribute("anoAnteriorParametro", anoAnterior);
			
			request.setAttribute("titulo", "Autom&oacute;vel");
			request.getRequestDispatcher("ajaxPagGraficoMes.jsp").forward(request,response);

		} else if (request.getParameter("tipo").equals("reqFaturamentoAutoTranqExclu")) {
			request.setAttribute("faturamentoAnoAtual",boVisoes.validaSelecionaTotalFaturamento("Auto Tranquilo Exclusivo"+anoAtual));
			request.setAttribute("faturamentoAnoAnterior",boVisoes.validaSelecionaTotalFaturamento("Auto Tranquilo Exclusivo"+anoAnterior));
			request.setAttribute("faturamentoDetalhadoAnoAtual",boVisoes.validaSelecionaFaturamentoDetalhado("Auto Tranquilo Exclusivo"+anoAtual));
			request.setAttribute("faturamentoDetalhadoAnoAnterior",boVisoes.validaSelecionaFaturamentoDetalhado("Auto Tranquilo Exclusivo"+anoAnterior));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAtual",boVisoes.validaCanceladosDividoPorEmitidos("Auto Tranquilo Exclusivo"+anoAtual));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAnterior",boVisoes.validaCanceladosDividoPorEmitidos("Auto Tranquilo Exclusivo"+anoAnterior));
			request.setAttribute("faturaAnoAtualAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("Auto Tranquilo Exclusivo"+anoAtual,1));
			request.setAttribute("faturaAnoAnteriorAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("Auto Tranquilo Exclusivo"+anoAnterior,1));
			request.setAttribute("analiseSinistrosAvisados", boVisoes.validaSelecionaSinistros("Auto Tranquilo Exclusivo"+anoAtual,1));
			request.setAttribute("analiseSinistrosIndenizados", boVisoes.validaSelecionaSinistros("Auto Tranquilo Exclusivo"+anoAtual,2));
			request.setAttribute("analiseSinistrosPendentes", boVisoes.validaSelecionaSinistros("Auto Tranquilo Exclusivo"+anoAtual,3));
			request.setAttribute("analiseSinistrosDespesas", boVisoes.validaSelecionaSinistros("Auto Tranquilo Exclusivo"+anoAtual,4));
			request.setAttribute("anoAtualDivididoPorAnteriorAcumulado",boVisoes.validaAnoAtualDividoPorAnoAnteriorAcumulado("Auto Tranquilo Exclusivo"+anoAtual));
			request.setAttribute("anoAtualDivididoPorAnterior",boVisoes.validaAnoAtualDividoPorAnoAnterior("Auto Tranquilo Exclusivo"+anoAtual));
			request.setAttribute("anoAtualParametro", anoAtual);
			request.setAttribute("anoAnteriorParametro", anoAnterior);
			
			request.setAttribute("titulo", "Auto Tranquilo Exclusivo");
			request.getRequestDispatcher("ajaxPagGraficoMes.jsp").forward(request,response);
		
		} else if (request.getParameter("tipo").equals("reqFaturamentoAutoTranqCorren")) {
			request.setAttribute("faturamentoAnoAtual",boVisoes.validaSelecionaTotalFaturamento("Auto Tranquilo Correntista"+anoAtual));
			request.setAttribute("faturamentoAnoAnterior",boVisoes.validaSelecionaTotalFaturamento("Auto Tranquilo Correntista"+anoAnterior));
			request.setAttribute("faturamentoDetalhadoAnoAtual",boVisoes.validaSelecionaFaturamentoDetalhado("Auto Tranquilo Correntista"+anoAtual));
			request.setAttribute("faturamentoDetalhadoAnoAnterior",boVisoes.validaSelecionaFaturamentoDetalhado("Auto Tranquilo Correntista"+anoAnterior));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAtual",boVisoes.validaCanceladosDividoPorEmitidos("Auto Tranquilo Correntista"+anoAtual));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAnterior",boVisoes.validaCanceladosDividoPorEmitidos("Auto Tranquilo Correntista"+anoAnterior));
			request.setAttribute("faturaAnoAtualAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("Auto Tranquilo Correntista"+anoAtual,1));
			request.setAttribute("faturaAnoAnteriorAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("Auto Tranquilo Correntista"+anoAnterior,1));
			request.setAttribute("analiseSinistrosAvisados", boVisoes.validaSelecionaSinistros("Auto Tranquilo Correntista"+anoAtual,1));
			request.setAttribute("analiseSinistrosIndenizados", boVisoes.validaSelecionaSinistros("Auto Tranquilo Correntista"+anoAtual,2));
			request.setAttribute("analiseSinistrosPendentes", boVisoes.validaSelecionaSinistros("Auto Tranquilo Correntista"+anoAtual,3));
			request.setAttribute("analiseSinistrosDespesas", boVisoes.validaSelecionaSinistros("Auto Tranquilo Correntista"+anoAtual,4));
			request.setAttribute("anoAtualDivididoPorAnteriorAcumulado",boVisoes.validaAnoAtualDividoPorAnoAnteriorAcumulado("Auto Tranquilo Correntista"+anoAtual));
			request.setAttribute("anoAtualDivididoPorAnterior",boVisoes.validaAnoAtualDividoPorAnoAnterior("Auto Tranquilo Correntista"+anoAtual));
			request.setAttribute("anoAtualParametro", anoAtual);
			request.setAttribute("anoAnteriorParametro", anoAnterior);
			
			request.setAttribute("titulo", "Auto Tranquilo Correntista");
			request.getRequestDispatcher("ajaxPagGraficoMes.jsp").forward(request,response);
		
		} else if (request.getParameter("tipo").equals("reqFaturamentoAutoTranqFrota")) {
			request.setAttribute("faturamentoAnoAtual",boVisoes.validaSelecionaTotalFaturamento("Auto Tranquilo Frota"+anoAtual));
			request.setAttribute("faturamentoAnoAnterior",boVisoes.validaSelecionaTotalFaturamento("Auto Tranquilo Frota"+anoAnterior));
			request.setAttribute("faturamentoDetalhadoAnoAtual",boVisoes.validaSelecionaFaturamentoDetalhado("Auto Tranquilo Frota"+anoAtual));
			request.setAttribute("faturamentoDetalhadoAnoAnterior",boVisoes.validaSelecionaFaturamentoDetalhado("Auto Tranquilo Frota"+anoAnterior));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAtual",boVisoes.validaCanceladosDividoPorEmitidos("Auto Tranquilo Frota"+anoAtual));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAnterior",boVisoes.validaCanceladosDividoPorEmitidos("Auto Tranquilo Frota"+anoAnterior));
			request.setAttribute("faturaAnoAtualAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("Auto Tranquilo Frota"+anoAtual,1));
			request.setAttribute("faturaAnoAnteriorAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("Auto Tranquilo Frota"+anoAnterior,1));
			request.setAttribute("analiseSinistrosAvisados", boVisoes.validaSelecionaSinistros("Auto Tranquilo Frota"+anoAtual,1));
			request.setAttribute("analiseSinistrosIndenizados", boVisoes.validaSelecionaSinistros("Auto Tranquilo Frota"+anoAtual,2));
			request.setAttribute("analiseSinistrosPendentes", boVisoes.validaSelecionaSinistros("Auto Tranquilo Frota"+anoAtual,3));
			request.setAttribute("analiseSinistrosDespesas", boVisoes.validaSelecionaSinistros("Auto Tranquilo Frota"+anoAtual,4));
			request.setAttribute("anoAtualDivididoPorAnteriorAcumulado",boVisoes.validaAnoAtualDividoPorAnoAnteriorAcumulado("Auto Tranquilo Frota"+anoAtual));
			request.setAttribute("anoAtualDivididoPorAnterior",boVisoes.validaAnoAtualDividoPorAnoAnterior("Auto Tranquilo Frota"+anoAtual));
			request.setAttribute("anoAtualParametro", anoAtual);
			request.setAttribute("anoAnteriorParametro", anoAnterior);
			
			request.setAttribute("titulo", "Auto Tranquilo Frota");
			request.getRequestDispatcher("ajaxPagGraficoMes.jsp").forward(request,response);
		
		} else if (request.getParameter("tipo").equals("reqFaturamentoAutoFacil")) {
			request.setAttribute("faturamentoAnoAtual",boVisoes.validaSelecionaTotalFaturamento("Auto Fácil"+anoAtual));
			request.setAttribute("faturamentoAnoAnterior",boVisoes.validaSelecionaTotalFaturamento("Auto Fácil"+anoAnterior));
			request.setAttribute("faturamentoDetalhadoAnoAtual",boVisoes.validaSelecionaFaturamentoDetalhado("Auto Fácil"+anoAtual));
			request.setAttribute("faturamentoDetalhadoAnoAnterior",boVisoes.validaSelecionaFaturamentoDetalhado("Auto Fácil"+anoAnterior));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAtual",boVisoes.validaCanceladosDividoPorEmitidos("Auto Fácil"+anoAtual));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAnterior",boVisoes.validaCanceladosDividoPorEmitidos("Auto Fácil"+anoAnterior));
			request.setAttribute("faturaAnoAtualAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("Auto Fácil"+anoAtual,1));
			request.setAttribute("faturaAnoAnteriorAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("Auto Fácil"+anoAnterior,1));
			request.setAttribute("analiseSinistrosAvisados", boVisoes.validaSelecionaSinistros("Auto Fácil"+anoAtual,1));
			request.setAttribute("analiseSinistrosIndenizados", boVisoes.validaSelecionaSinistros("Auto Fácil"+anoAtual,2));
			request.setAttribute("analiseSinistrosPendentes", boVisoes.validaSelecionaSinistros("Auto Fácil"+anoAtual,3));
			request.setAttribute("analiseSinistrosDespesas", boVisoes.validaSelecionaSinistros("Auto Fácil"+anoAtual,4));
			request.setAttribute("anoAtualDivididoPorAnteriorAcumulado",boVisoes.validaAnoAtualDividoPorAnoAnteriorAcumulado("Auto Fácil"+anoAtual));
			request.setAttribute("anoAtualDivididoPorAnterior",boVisoes.validaAnoAtualDividoPorAnoAnterior("Auto Fácil"+anoAtual));
			request.setAttribute("anoAtualParametro", anoAtual);
			request.setAttribute("anoAnteriorParametro", anoAnterior);
			
			request.setAttribute("titulo", "Auto F&aacute;cil");
			request.getRequestDispatcher("ajaxPagGraficoMes.jsp").forward(request,response);
		
		} else if (request.getParameter("tipo").equals("reqFaturamentoRDPJ")) {
			request.setAttribute("faturamentoAnoAtual",boVisoes.validaSelecionaTotalFaturamento("rdpj"+anoAtual));
			request.setAttribute("faturamentoAnoAnterior",boVisoes.validaSelecionaTotalFaturamento("rdpj"+anoAnterior));
			request.setAttribute("faturamentoDetalhadoAnoAtual",boVisoes.validaSelecionaFaturamentoDetalhado("rdpj"+anoAtual));
			request.setAttribute("faturamentoDetalhadoAnoAnterior",boVisoes.validaSelecionaFaturamentoDetalhado("rdpj"+anoAnterior));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAtual",boVisoes.validaCanceladosDividoPorEmitidos("rdpj"+anoAtual));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAnterior",boVisoes.validaCanceladosDividoPorEmitidos("rdpj"+anoAnterior));
			request.setAttribute("faturaAnoAtualAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("rdpj"+anoAtual,1));
			request.setAttribute("faturaAnoAnteriorAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("rdpj"+anoAnterior,1));
			request.setAttribute("analiseSinistrosAvisados", boVisoes.validaSelecionaSinistros("rdpj"+anoAtual,1));
			request.setAttribute("analiseSinistrosIndenizados", boVisoes.validaSelecionaSinistros("rdpj"+anoAtual,2));
			request.setAttribute("analiseSinistrosPendentes", boVisoes.validaSelecionaSinistros("rdpj"+anoAtual,3));
			request.setAttribute("analiseSinistrosDespesas", boVisoes.validaSelecionaSinistros("rdpj"+anoAtual,4));
			request.setAttribute("anoAtualDivididoPorAnteriorAcumulado",boVisoes.validaAnoAtualDividoPorAnoAnteriorAcumulado("rdpj"+anoAtual));
			request.setAttribute("anoAtualDivididoPorAnterior",boVisoes.validaAnoAtualDividoPorAnoAnterior("rdpj"+anoAtual));
			request.setAttribute("anoAtualParametro", anoAtual);
			request.setAttribute("anoAnteriorParametro", anoAnterior);
			
			request.setAttribute("titulo","RD PJ");
			request.getRequestDispatcher("ajaxPagGraficoMes.jsp").forward(request,response);
		
		} else if (request.getParameter("tipo").equals("reqFaturamentoRdEquipamentos")) {
			request.setAttribute("faturamentoAnoAtual",boVisoes.validaSelecionaTotalFaturamento("Rd Equipamentos"+anoAtual));
			request.setAttribute("faturamentoAnoAnterior",boVisoes.validaSelecionaTotalFaturamento("Rd Equipamentos"+anoAnterior));
			request.setAttribute("faturamentoDetalhadoAnoAtual",boVisoes.validaSelecionaFaturamentoDetalhado("Rd Equipamentos"+anoAtual));
			request.setAttribute("faturamentoDetalhadoAnoAnterior",boVisoes.validaSelecionaFaturamentoDetalhado("Rd Equipamentos"+anoAnterior));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAtual",boVisoes.validaCanceladosDividoPorEmitidos("Rd Equipamentos"+anoAtual));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAnterior",boVisoes.validaCanceladosDividoPorEmitidos("Rd Equipamentos"+anoAnterior));
			request.setAttribute("faturaAnoAtualAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("Rd Equipamentos"+anoAtual,1));
			request.setAttribute("faturaAnoAnteriorAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("Rd Equipamentos"+anoAnterior,1));
			request.setAttribute("analiseSinistrosAvisados", boVisoes.validaSelecionaSinistros("Rd Equipamentos"+anoAtual,1));
			request.setAttribute("analiseSinistrosIndenizados", boVisoes.validaSelecionaSinistros("Rd Equipamentos"+anoAtual,2));
			request.setAttribute("analiseSinistrosPendentes", boVisoes.validaSelecionaSinistros("Rd Equipamentos"+anoAtual,3));
			request.setAttribute("analiseSinistrosDespesas", boVisoes.validaSelecionaSinistros("Rd Equipamentos"+anoAtual,4));
			request.setAttribute("anoAtualDivididoPorAnteriorAcumulado",boVisoes.validaAnoAtualDividoPorAnoAnteriorAcumulado("Rd Equipamentos"+anoAtual));
			request.setAttribute("anoAtualDivididoPorAnterior",boVisoes.validaAnoAtualDividoPorAnoAnterior("Rd Equipamentos"+anoAtual));
			request.setAttribute("anoAtualParametro", anoAtual);
			request.setAttribute("anoAnteriorParametro", anoAnterior);
			
			request.setAttribute("titulo", "RD Equipamentos");
			request.getRequestDispatcher("ajaxPagGraficoMes.jsp").forward(request,response);
		
		} else if (request.getParameter("tipo").equals("reqFaturamentoEmpresarial")) {
			request.setAttribute("faturamentoAnoAtual",boVisoes.validaSelecionaTotalFaturamento("MR Empresarial"+anoAtual));
			request.setAttribute("faturamentoAnoAnterior",boVisoes.validaSelecionaTotalFaturamento("MR Empresarial"+anoAnterior));
			request.setAttribute("faturamentoDetalhadoAnoAtual",boVisoes.validaSelecionaFaturamentoDetalhado("MR Empresarial"+anoAtual));
			request.setAttribute("faturamentoDetalhadoAnoAnterior",boVisoes.validaSelecionaFaturamentoDetalhado("MR Empresarial"+anoAnterior));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAtual",boVisoes.validaCanceladosDividoPorEmitidos("MR Empresarial"+anoAtual));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAnterior",boVisoes.validaCanceladosDividoPorEmitidos("MR Empresarial"+anoAnterior));
			request.setAttribute("faturaAnoAtualAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("MR Empresarial"+anoAtual,1));
			request.setAttribute("faturaAnoAnteriorAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("MR Empresarial"+anoAnterior,1));
			request.setAttribute("analiseSinistrosAvisados", boVisoes.validaSelecionaSinistros("MR Empresarial"+anoAtual,1));
			request.setAttribute("analiseSinistrosIndenizados", boVisoes.validaSelecionaSinistros("MR Empresarial"+anoAtual,2));
			request.setAttribute("analiseSinistrosPendentes", boVisoes.validaSelecionaSinistros("MR Empresarial"+anoAtual,3));
			request.setAttribute("analiseSinistrosDespesas", boVisoes.validaSelecionaSinistros("MR Empresarial"+anoAtual,4));
			request.setAttribute("anoAtualDivididoPorAnteriorAcumulado",boVisoes.validaAnoAtualDividoPorAnoAnteriorAcumulado("MR Empresarial"+anoAtual));
			request.setAttribute("anoAtualDivididoPorAnterior",boVisoes.validaAnoAtualDividoPorAnoAnterior("MR Empresarial"+anoAtual));
			request.setAttribute("anoAtualParametro", anoAtual);
			request.setAttribute("anoAnteriorParametro", anoAnterior);
			
			request.setAttribute("titulo", "MR Empresarial");
			request.getRequestDispatcher("ajaxPagGraficoMes.jsp").forward(request,response);
		
		} else if (request.getParameter("tipo").equals("reqFaturamentoLoterico")) {
			request.setAttribute("faturamentoAnoAtual",boVisoes.validaSelecionaTotalFaturamento("loterico"+anoAtual));
			request.setAttribute("faturamentoAnoAnterior",boVisoes.validaSelecionaTotalFaturamento("loterico"+anoAnterior));
			request.setAttribute("faturamentoDetalhadoAnoAtual",boVisoes.validaSelecionaFaturamentoDetalhado("loterico"+anoAtual));
			request.setAttribute("faturamentoDetalhadoAnoAnterior",boVisoes.validaSelecionaFaturamentoDetalhado("loterico"+anoAnterior));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAtual",boVisoes.validaCanceladosDividoPorEmitidos("loterico"+anoAtual));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAnterior",boVisoes.validaCanceladosDividoPorEmitidos("loterico"+anoAnterior));
			request.setAttribute("faturaAnoAtualAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("loterico"+anoAtual,1));
			request.setAttribute("faturaAnoAnteriorAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("loterico"+anoAnterior,1));
			request.setAttribute("analiseSinistrosAvisados", boVisoes.validaSelecionaSinistros("loterico"+anoAtual,1));
			request.setAttribute("analiseSinistrosIndenizados", boVisoes.validaSelecionaSinistros("loterico"+anoAtual,2));
			request.setAttribute("analiseSinistrosPendentes", boVisoes.validaSelecionaSinistros("loterico"+anoAtual,3));
			request.setAttribute("analiseSinistrosDespesas", boVisoes.validaSelecionaSinistros("loterico"+anoAtual,4));
			request.setAttribute("anoAtualDivididoPorAnteriorAcumulado",boVisoes.validaAnoAtualDividoPorAnoAnteriorAcumulado("loterico"+anoAtual));
			request.setAttribute("anoAtualDivididoPorAnterior",boVisoes.validaAnoAtualDividoPorAnoAnterior("loterico"+anoAtual));
			request.setAttribute("anoAtualParametro", anoAtual);
			request.setAttribute("anoAnteriorParametro", anoAnterior);
			
			request.setAttribute("titulo", "MR Lot&eacute;rico");
			request.getRequestDispatcher("ajaxPagGraficoMes.jsp").forward(request,response);
		
		} else if (request.getParameter("tipo").equals("reqFaturamentoCCA")) {
			request.setAttribute("faturamentoAnoAtual",boVisoes.validaSelecionaTotalFaturamento("cca"+anoAtual));
			request.setAttribute("faturamentoAnoAnterior",boVisoes.validaSelecionaTotalFaturamento("cca"+anoAnterior));
			request.setAttribute("faturamentoDetalhadoAnoAtual",boVisoes.validaSelecionaFaturamentoDetalhado("cca"+anoAtual));
			request.setAttribute("faturamentoDetalhadoAnoAnterior",boVisoes.validaSelecionaFaturamentoDetalhado("cca"+anoAnterior));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAtual",boVisoes.validaCanceladosDividoPorEmitidos("cca"+anoAtual));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAnterior",boVisoes.validaCanceladosDividoPorEmitidos("cca"+anoAnterior));
			request.setAttribute("faturaAnoAtualAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("cca"+anoAtual,1));
			request.setAttribute("faturaAnoAnteriorAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("cca"+anoAnterior,1));
			request.setAttribute("analiseSinistrosAvisados", boVisoes.validaSelecionaSinistros("cca"+anoAtual,1));
			request.setAttribute("analiseSinistrosIndenizados", boVisoes.validaSelecionaSinistros("cca"+anoAtual,2));
			request.setAttribute("analiseSinistrosPendentes", boVisoes.validaSelecionaSinistros("cca"+anoAtual,3));
			request.setAttribute("analiseSinistrosDespesas", boVisoes.validaSelecionaSinistros("cca"+anoAtual,4));
			request.setAttribute("anoAtualDivididoPorAnteriorAcumulado",boVisoes.validaAnoAtualDividoPorAnoAnteriorAcumulado("cca"+anoAtual));
			request.setAttribute("anoAtualDivididoPorAnterior",boVisoes.validaAnoAtualDividoPorAnoAnterior("cca"+anoAtual));
			request.setAttribute("anoAtualParametro", anoAtual);
			request.setAttribute("anoAnteriorParametro", anoAnterior);
			
			request.setAttribute("titulo","MR CCA");
			request.getRequestDispatcher("ajaxPagGraficoMes.jsp").forward(request,response);
		
		} else if (request.getParameter("tipo").equals("reqFaturamentoRDPF")) {
			request.setAttribute("faturamentoAnoAtual",boVisoes.validaSelecionaTotalFaturamento("rdpf"+anoAtual));
			request.setAttribute("faturamentoAnoAnterior",boVisoes.validaSelecionaTotalFaturamento("rdpf"+anoAnterior));
			request.setAttribute("faturamentoDetalhadoAnoAtual",boVisoes.validaSelecionaFaturamentoDetalhado("rdpf"+anoAtual));
			request.setAttribute("faturamentoDetalhadoAnoAnterior",boVisoes.validaSelecionaFaturamentoDetalhado("rdpf"+anoAnterior));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAtual",boVisoes.validaCanceladosDividoPorEmitidos("rdpf"+anoAtual));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAnterior",boVisoes.validaCanceladosDividoPorEmitidos("rdpf"+anoAnterior));
			request.setAttribute("faturaAnoAtualAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("rdpf"+anoAtual,1));
			request.setAttribute("faturaAnoAnteriorAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("rdpf"+anoAnterior,1));
			request.setAttribute("analiseSinistrosAvisados", boVisoes.validaSelecionaSinistros("rdpf"+anoAtual,1));
			request.setAttribute("analiseSinistrosIndenizados", boVisoes.validaSelecionaSinistros("rdpf"+anoAtual,2));
			request.setAttribute("analiseSinistrosPendentes", boVisoes.validaSelecionaSinistros("rdpf"+anoAtual,3));
			request.setAttribute("analiseSinistrosDespesas", boVisoes.validaSelecionaSinistros("rdpf"+anoAtual,4));
			request.setAttribute("anoAtualDivididoPorAnteriorAcumulado",boVisoes.validaAnoAtualDividoPorAnoAnteriorAcumulado("rdpf"+anoAtual));
			request.setAttribute("anoAtualDivididoPorAnterior",boVisoes.validaAnoAtualDividoPorAnoAnterior("rdpf"+anoAtual));
			request.setAttribute("anoAtualParametro", anoAtual);
			request.setAttribute("anoAnteriorParametro", anoAnterior);
			
			request.setAttribute("titulo","RD PF");
			request.getRequestDispatcher("ajaxPagGraficoMes.jsp").forward(request,response);
			
		} else if (request.getParameter("tipo").equals("reqFaturamentoRdCorrentista")) {
			request.setAttribute("faturamentoAnoAtual",boVisoes.validaSelecionaTotalFaturamento("MR Residencial Correntista"+anoAtual));
			request.setAttribute("faturamentoAnoAnterior",boVisoes.validaSelecionaTotalFaturamento("MR Residencial Correntista"+anoAnterior));
			request.setAttribute("faturamentoDetalhadoAnoAtual",boVisoes.validaSelecionaFaturamentoDetalhado("MR Residencial Correntista"+anoAtual));
			request.setAttribute("faturamentoDetalhadoAnoAnterior",boVisoes.validaSelecionaFaturamentoDetalhado("MR Residencial Correntista"+anoAnterior));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAtual",boVisoes.validaCanceladosDividoPorEmitidos("MR Residencial Correntista"+anoAtual));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAnterior",boVisoes.validaCanceladosDividoPorEmitidos("MR Residencial Correntista"+anoAnterior));
			request.setAttribute("faturaAnoAtualAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("MR Residencial Correntista"+anoAtual,1));
			request.setAttribute("faturaAnoAnteriorAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("MR Residencial Correntista"+anoAnterior,1));
			request.setAttribute("analiseSinistrosAvisados", boVisoes.validaSelecionaSinistros("MR Residencial Correntista"+anoAtual,1));
			request.setAttribute("analiseSinistrosIndenizados", boVisoes.validaSelecionaSinistros("MR Residencial Correntista"+anoAtual,2));
			request.setAttribute("analiseSinistrosPendentes", boVisoes.validaSelecionaSinistros("MR Residencial Correntista"+anoAtual,3));
			request.setAttribute("analiseSinistrosDespesas", boVisoes.validaSelecionaSinistros("MR Residencial Correntista"+anoAtual,4));
			request.setAttribute("anoAtualDivididoPorAnteriorAcumulado",boVisoes.validaAnoAtualDividoPorAnoAnteriorAcumulado("MR Residencial Correntista"+anoAtual));
			request.setAttribute("anoAtualDivididoPorAnterior",boVisoes.validaAnoAtualDividoPorAnoAnterior("MR Residencial Correntista"+anoAtual));
			request.setAttribute("anoAtualParametro", anoAtual);
			request.setAttribute("anoAnteriorParametro", anoAnterior);
			
			request.setAttribute("titulo","RD Correntista");
			request.getRequestDispatcher("ajaxPagGraficoMes.jsp").forward(request,response);
			
		} else if (request.getParameter("tipo").equals("reqFaturamentoFacilRd")) {
			request.setAttribute("faturamentoAnoAtual",boVisoes.validaSelecionaTotalFaturamento("Facil Residencial"+anoAtual));
			request.setAttribute("faturamentoAnoAnterior",boVisoes.validaSelecionaTotalFaturamento("Facil Residencial"+anoAnterior));
			request.setAttribute("faturamentoDetalhadoAnoAtual",boVisoes.validaSelecionaFaturamentoDetalhado("Facil Residencial"+anoAtual));
			request.setAttribute("faturamentoDetalhadoAnoAnterior",boVisoes.validaSelecionaFaturamentoDetalhado("Facil Residencial"+anoAnterior));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAtual",boVisoes.validaCanceladosDividoPorEmitidos("Facil Residencial"+anoAtual));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAnterior",boVisoes.validaCanceladosDividoPorEmitidos("Facil Residencial"+anoAnterior));
			request.setAttribute("faturaAnoAtualAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("Facil Residencial"+anoAtual,1));
			request.setAttribute("faturaAnoAnteriorAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("Facil Residencial"+anoAnterior,1));
			request.setAttribute("analiseSinistrosAvisados", boVisoes.validaSelecionaSinistros("Facil Residencial"+anoAtual,1));
			request.setAttribute("analiseSinistrosIndenizados", boVisoes.validaSelecionaSinistros("Facil Residencial"+anoAtual,2));
			request.setAttribute("analiseSinistrosPendentes", boVisoes.validaSelecionaSinistros("Facil Residencial"+anoAtual,3));
			request.setAttribute("analiseSinistrosDespesas", boVisoes.validaSelecionaSinistros("Facil Residencial"+anoAtual,4));
			request.setAttribute("anoAtualDivididoPorAnteriorAcumulado",boVisoes.validaAnoAtualDividoPorAnoAnteriorAcumulado("Facil Residencial"+anoAtual));
			request.setAttribute("anoAtualDivididoPorAnterior",boVisoes.validaAnoAtualDividoPorAnoAnterior("Facil Residencial"+anoAtual));
			request.setAttribute("anoAtualParametro", anoAtual);
			request.setAttribute("anoAnteriorParametro", anoAnterior);
			
			request.setAttribute("titulo","F&aacute;cil RD");
			request.getRequestDispatcher("ajaxPagGraficoMes.jsp").forward(request,response);
			
		} else if (request.getParameter("tipo").equals("reqFaturamentoRdEconomiario")) {//exclusivo
			request.setAttribute("faturamentoAnoAtual",boVisoes.validaSelecionaTotalFaturamento("MR Residencial Economiario"+anoAtual));
			request.setAttribute("faturamentoAnoAnterior",boVisoes.validaSelecionaTotalFaturamento("MR Residencial Economiario"+anoAnterior));
			request.setAttribute("faturamentoDetalhadoAnoAtual",boVisoes.validaSelecionaFaturamentoDetalhado("MR Residencial Economiario"+anoAtual));
			request.setAttribute("faturamentoDetalhadoAnoAnterior",boVisoes.validaSelecionaFaturamentoDetalhado("MR Residencial Economiario"+anoAnterior));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAtual",boVisoes.validaCanceladosDividoPorEmitidos("MR Residencial Economiario"+anoAtual));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAnterior",boVisoes.validaCanceladosDividoPorEmitidos("MR Residencial Economiario"+anoAnterior));
			request.setAttribute("faturaAnoAtualAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("MR Residencial Economiario"+anoAtual,1));
			request.setAttribute("faturaAnoAnteriorAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("MR Residencial Economiario"+anoAnterior,1));
			request.setAttribute("analiseSinistrosAvisados", boVisoes.validaSelecionaSinistros("MR Residencial Economiario"+anoAtual,1));
			request.setAttribute("analiseSinistrosIndenizados", boVisoes.validaSelecionaSinistros("MR Residencial Economiario"+anoAtual,2));
			request.setAttribute("analiseSinistrosPendentes", boVisoes.validaSelecionaSinistros("MR Residencial Economiario"+anoAtual,3));
			request.setAttribute("analiseSinistrosDespesas", boVisoes.validaSelecionaSinistros("MR Residencial Economiario"+anoAtual,4));
			request.setAttribute("anoAtualDivididoPorAnteriorAcumulado",boVisoes.validaAnoAtualDividoPorAnoAnteriorAcumulado("MR Residencial Economiario"+anoAtual));
			request.setAttribute("anoAtualDivididoPorAnterior",boVisoes.validaAnoAtualDividoPorAnoAnterior("MR Residencial Economiario"+anoAtual));
			request.setAttribute("anoAtualParametro", anoAtual);
			request.setAttribute("anoAnteriorParametro", anoAnterior);
			
			request.setAttribute("titulo","RD Exclusivo");
			request.getRequestDispatcher("ajaxPagGraficoMes.jsp").forward(request,response);
			
		} else if (request.getParameter("tipo").equals("reqFaturamentoLarMais")) {
			request.setAttribute("faturamentoAnoAtual",boVisoes.validaSelecionaTotalFaturamento("Lar Mais"+anoAtual));
			request.setAttribute("faturamentoAnoAnterior",boVisoes.validaSelecionaTotalFaturamento("Lar Mais"+anoAnterior));
			request.setAttribute("faturamentoDetalhadoAnoAtual",boVisoes.validaSelecionaFaturamentoDetalhado("Lar Mais"+anoAtual));
			request.setAttribute("faturamentoDetalhadoAnoAnterior",boVisoes.validaSelecionaFaturamentoDetalhado("Lar Mais"+anoAnterior));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAtual",boVisoes.validaCanceladosDividoPorEmitidos("Lar Mais"+anoAtual));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAnterior",boVisoes.validaCanceladosDividoPorEmitidos("Lar Mais"+anoAnterior));
			request.setAttribute("faturaAnoAtualAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("Lar Mais"+anoAtual,1));
			request.setAttribute("faturaAnoAnteriorAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("Lar Mais"+anoAnterior,1));
			request.setAttribute("analiseSinistrosAvisados", boVisoes.validaSelecionaSinistros("Lar Mais"+anoAtual,1));
			request.setAttribute("analiseSinistrosIndenizados", boVisoes.validaSelecionaSinistros("Lar Mais"+anoAtual,2));
			request.setAttribute("analiseSinistrosPendentes", boVisoes.validaSelecionaSinistros("Lar Mais"+anoAtual,3));
			request.setAttribute("analiseSinistrosDespesas", boVisoes.validaSelecionaSinistros("Lar Mais"+anoAtual,4));
			request.setAttribute("anoAtualDivididoPorAnteriorAcumulado",boVisoes.validaAnoAtualDividoPorAnoAnteriorAcumulado("Lar Mais"+anoAtual));
			request.setAttribute("anoAtualDivididoPorAnterior",boVisoes.validaAnoAtualDividoPorAnoAnterior("Lar Mais"+anoAtual));
			request.setAttribute("anoAtualParametro", anoAtual);
			request.setAttribute("anoAnteriorParametro", anoAnterior);
			
			request.setAttribute("titulo","Lar Mais");
			request.getRequestDispatcher("ajaxPagGraficoMes.jsp").forward(request,response);
			
		} else if (request.getParameter("tipo").equals("reqFaturamentoAporte")) {
			request.setAttribute("faturamentoAnoAtual",boVisoes.validaSelecionaTotalFaturamento("Aporte Caixa"+anoAtual));
			request.setAttribute("faturamentoAnoAnterior",boVisoes.validaSelecionaTotalFaturamento("Aporte Caixa"+anoAnterior));
			request.setAttribute("faturamentoDetalhadoAnoAtual",boVisoes.validaSelecionaFaturamentoDetalhado("Aporte Caixa"+anoAtual));
			request.setAttribute("faturamentoDetalhadoAnoAnterior",boVisoes.validaSelecionaFaturamentoDetalhado("Aporte Caixa"+anoAnterior));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAtual",boVisoes.validaCanceladosDividoPorEmitidos("Aporte Caixa"+anoAtual));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAnterior",boVisoes.validaCanceladosDividoPorEmitidos("Aporte Caixa"+anoAnterior));
			request.setAttribute("faturaAnoAtualAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("Aporte Caixa"+anoAtual,1));
			request.setAttribute("faturaAnoAnteriorAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("Aporte Caixa"+anoAnterior,1));
			request.setAttribute("analiseSinistrosAvisados", boVisoes.validaSelecionaSinistros("Aporte Caixa"+anoAtual,1));
			request.setAttribute("analiseSinistrosIndenizados", boVisoes.validaSelecionaSinistros("Aporte Caixa"+anoAtual,2));
			request.setAttribute("analiseSinistrosPendentes", boVisoes.validaSelecionaSinistros("Aporte Caixa"+anoAtual,3));
			request.setAttribute("analiseSinistrosDespesas", boVisoes.validaSelecionaSinistros("Aporte Caixa"+anoAtual,4));
			request.setAttribute("anoAtualDivididoPorAnteriorAcumulado",boVisoes.validaAnoAtualDividoPorAnoAnteriorAcumulado("Aporte Caixa"+anoAtual));
			request.setAttribute("anoAtualDivididoPorAnterior",boVisoes.validaAnoAtualDividoPorAnoAnterior("Aporte Caixa"+anoAtual));
			request.setAttribute("anoAtualParametro", anoAtual);
			request.setAttribute("anoAnteriorParametro", anoAnterior);
			
			request.setAttribute("titulo","Aporte Caixa");
			request.getRequestDispatcher("ajaxPagGraficoMes.jsp").forward(request,response);
			
		} else if (request.getParameter("tipo").equals("reqFaturamentoRdPfOutros")) {
			request.setAttribute("faturamentoAnoAtual",boVisoes.validaSelecionaTotalFaturamento("Rd Pf Outros"+anoAtual));
			request.setAttribute("faturamentoAnoAnterior",boVisoes.validaSelecionaTotalFaturamento("Rd Pf Outros"+anoAnterior));
			request.setAttribute("faturamentoDetalhadoAnoAtual",boVisoes.validaSelecionaFaturamentoDetalhado("Rd Pf Outros"+anoAtual));
			request.setAttribute("faturamentoDetalhadoAnoAnterior",boVisoes.validaSelecionaFaturamentoDetalhado("Rd Pf Outros"+anoAnterior));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAtual",boVisoes.validaCanceladosDividoPorEmitidos("Rd Pf Outros"+anoAtual));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAnterior",boVisoes.validaCanceladosDividoPorEmitidos("Rd Pf Outros"+anoAnterior));
			request.setAttribute("faturaAnoAtualAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("Rd Pf Outros"+anoAtual,1));
			request.setAttribute("faturaAnoAnteriorAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("Rd Pf Outros"+anoAnterior,1));
			request.setAttribute("analiseSinistrosAvisados", boVisoes.validaSelecionaSinistros("Rd Pf Outros"+anoAtual,1));
			request.setAttribute("analiseSinistrosIndenizados", boVisoes.validaSelecionaSinistros("Rd Pf Outros"+anoAtual,2));
			request.setAttribute("analiseSinistrosPendentes", boVisoes.validaSelecionaSinistros("Rd Pf Outros"+anoAtual,3));
			request.setAttribute("analiseSinistrosDespesas", boVisoes.validaSelecionaSinistros("Rd Pf Outros"+anoAtual,4));
			request.setAttribute("anoAtualDivididoPorAnteriorAcumulado",boVisoes.validaAnoAtualDividoPorAnoAnteriorAcumulado("Rd Pf Outros"+anoAtual));
			request.setAttribute("anoAtualDivididoPorAnterior",boVisoes.validaAnoAtualDividoPorAnoAnterior("Rd Pf Outros"+anoAtual));
			request.setAttribute("anoAtualParametro", anoAtual);
			request.setAttribute("anoAnteriorParametro", anoAnterior);
			
			request.setAttribute("titulo","RD PF Outros");
			request.getRequestDispatcher("ajaxPagGraficoMes.jsp").forward(request,response);
			
		} else if (request.getParameter("tipo").equals("reqFaturamentoDirid")) {
			request.setAttribute("faturamentoAnoAtual",boVisoes.validaSelecionaTotalFaturamento("Dirid"+anoAtual));
			request.setAttribute("faturamentoAnoAnterior",boVisoes.validaSelecionaTotalFaturamento("Dirid"+anoAnterior));
			request.setAttribute("faturamentoDetalhadoAnoAtual",boVisoes.validaSelecionaFaturamentoDetalhado("Dirid"+anoAtual));
			request.setAttribute("faturamentoDetalhadoAnoAnterior",boVisoes.validaSelecionaFaturamentoDetalhado("Dirid"+anoAnterior));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAtual",boVisoes.validaCanceladosDividoPorEmitidos("Dirid"+anoAtual));
			request.setAttribute("canceladosDivididoPorEmitidosAnoAnterior",boVisoes.validaCanceladosDividoPorEmitidos("Dirid"+anoAnterior));
			request.setAttribute("faturaAnoAtualAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("Dirid"+anoAtual,1));
			request.setAttribute("faturaAnoAnteriorAcumulado", boVisoes.validaSelecionaTotalFaturamentoAcumulado("Dirid"+anoAnterior,1));
			request.setAttribute("analiseSinistrosAvisados", boVisoes.validaSelecionaSinistros("Dirid"+anoAtual,1));
			request.setAttribute("analiseSinistrosIndenizados", boVisoes.validaSelecionaSinistros("Dirid"+anoAtual,2));
			request.setAttribute("analiseSinistrosPendentes", boVisoes.validaSelecionaSinistros("Dirid"+anoAtual,3));
			request.setAttribute("analiseSinistrosDespesas", boVisoes.validaSelecionaSinistros("Dirid"+anoAtual,4));
			request.setAttribute("anoAtualDivididoPorAnteriorAcumulado",boVisoes.validaAnoAtualDividoPorAnoAnteriorAcumulado("Dirid"+anoAtual));
			request.setAttribute("anoAtualDivididoPorAnterior",boVisoes.validaAnoAtualDividoPorAnoAnterior("Dirid"+anoAtual));
			request.setAttribute("anoAtualParametro", anoAtual);
			request.setAttribute("anoAnteriorParametro", anoAnterior);
			
			request.setAttribute("titulo","DIRID");
			request.getRequestDispatcher("ajaxPagGraficoMes.jsp").forward(request,response);
		}

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
