package caixa.dirid.SERVLET;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import caixa.dirid.BO.UsuarioBO;
import caixa.dirid.VO.UsuarioVO;

public class LoginServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		UsuarioBO boUsuario = new UsuarioBO();
		UsuarioVO usuario = new UsuarioVO();

		try {
			if (boUsuario.comparaLogin(req.getParameter("CPF"))) {
				usuario = boUsuario.buscaPorCPF(req.getParameter("CPF"));
				resp.sendRedirect("pagiMenuPrincipal.jsp?id=" + usuario.getId());
			} else {
				resp.sendRedirect("index.jsp?error=s");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
