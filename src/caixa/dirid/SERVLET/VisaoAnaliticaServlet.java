package caixa.dirid.SERVLET;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import caixa.dirid.BO.VisaoAnaliticaBO;
import caixa.dirid.UTEIS.Uteis;

public class VisaoAnaliticaServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		VisaoAnaliticaBO boVisoes = new VisaoAnaliticaBO();
		Uteis uteis = new Uteis();

		if (req.getParameter("tipo").equals("premios")) {
			req.setAttribute("apolice",
					boVisoes.validaSelecionaApolicesEmitidas());
			req.getRequestDispatcher("pagiListaApolice.jsp").forward(req, resp);

		} else if (req.getParameter("tipo").equals("fatuMensal")) {
			req.setAttribute("mrLista", boVisoes.validaFatuMensal("mr2015",
					req.getParameter("mes")));
			req.setAttribute(
					"autoLista",
					boVisoes.validaFatuMensal("auto2015",
							req.getParameter("mes")));
			req.setAttribute(
					"mrListaAcumulada",
					boVisoes.validaFatuMensalAcumulada("mr2015",
							req.getParameter("mes")));
			req.setAttribute(
					"autoListaAcumulada",
					boVisoes.validaFatuMensalAcumulada("auto2015",
							req.getParameter("mes")));

			// utilizado para evitar o erro:
			// "Motivo: o cabeçalho CORS 'Access-Control-Allow-Origin' não está presente"
			// gerado quando acesso a aplicação pelo localhost para o ip local
			// 10.125.7.51
			resp.setHeader("Access-Control-Allow-Origin", "*");

			req.getRequestDispatcher("ajaxTabelaFatuAnalitico.jsp").forward(
					req, resp);

		} else if (req.getParameter("tipo").equals("rvne")) {
			// req.setAttribute("rvne",boVisoes.validaSelecionaRVNE(uteis.dataAtual(1)));
			req.setAttribute("rvne",
					boVisoes.validaSelecionaRVNE(req.getParameter("ano")));
			req.setAttribute("ano", req.getParameter("ano"));
			req.getRequestDispatcher("ajaxListaRVNE.jsp").forward(req, resp);

		} else if (req.getParameter("tipo").equals("rvneUnica")) {
			req.setAttribute("rvne", boVisoes.validaBuscaRVNE(req));
			req.setAttribute("id", req.getParameter("id"));
			req.setAttribute("coluna", req.getParameter("coluna"));
			req.getRequestDispatcher("ajaxBotaoAlterarRVNE.jsp").forward(req,
					resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		VisaoAnaliticaBO boVisoes = new VisaoAnaliticaBO();
		System.out.println("posttt");
		if (req.getParameter("tipo") != null) {

			if (req.getParameter("tipo").equals("delete")) {
				boVisoes.validaApagaRVNE(req);
				// resp.sendRedirect("visaoAnalitica?tipo=rvne");
			}

		} else {
			boVisoes.validaAtualizaRVNE(req);
			req.setAttribute("rvne",boVisoes.validaSelecionaRVNE(req.getParameter("ano")));
			req.setAttribute("ano", req.getParameter("ano"));
			req.getRequestDispatcher("pagiListaRVNE.jsp").forward(req, resp);
			// resp.sendRedirect("visaoAnalitica?tipo=rvne");
		}
	}
}
