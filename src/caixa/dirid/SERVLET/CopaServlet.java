package caixa.dirid.SERVLET;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import caixa.dirid.DAO.CopaDAO;
import caixa.dirid.VO.CopaUsuarioVO;

public class CopaServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("chegou no GET");
		CopaDAO dao = new CopaDAO();

		if (request.getParameter("tipo").equals("verTodasApostas")) {

			request.setAttribute("objetoUsuario", dao.buscarTodasApostas());
			request.getRequestDispatcher(
					"e_copa_arquivos/copa_acompanharTodasAposta.jsp").forward(
					request, response);
		} else if (request.getParameter("tipo").equals("buscarRank")) {

			request.setAttribute("objetoUsuario", dao.buscarRank());
			request.getRequestDispatcher(
					"e_copa_arquivos/copa_acompanharAposta.jsp").forward(request,
					response);
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("chegou no POST");

		CopaDAO dao = new CopaDAO();
		System.out.println(request.getParameter("tipo"));
		if (request.getParameter("tipo").equals("log")) {

			System.out.println(request.getParameter("nLogin"));
			System.out.println(request.getParameter("nSenha"));

			/*
			 * if (boUsuario.comparaLogin(req.getParameter("CPF"))) { usuario =
			 * boUsuario.buscaPorCPF(req.getParameter("CPF"));
			 * resp.sendRedirect("pagiMenuPrincipal.jsp?id=" + usuario.getId());
			 * } else { resp.sendRedirect("index.jsp?error=s"); }
			 */

			CopaUsuarioVO usuario = dao.logarDAO(request);

			if (usuario.getLogin().equals("false")) {

				response.sendRedirect("http://192.168.1.108:8082/ProjetoCaixa/e_copa_arquivos/copa_login.jsp?error=s");

			} else {
				if (usuario.getPermissaoBean() == 1) {
					System.out.println("admin");
					request.setAttribute("objetoUsuario", usuario);

					request.setAttribute("dadosPartidas",
							dao.buscaDadosPartidas(1));
					request.setAttribute("apostasRealizadas",
							dao.buscaApostasRealizadasAdmin(1));

					/*request.setAttribute("dadosPartidasF2",
							dao.buscaDadosPartidas(2));
					request.setAttribute("apostasRealizadasF2",
							dao.buscaApostasRealizadasAdmin(2));

					request.setAttribute("dadosPartidasF3",
							dao.buscaDadosPartidas(3));
					request.setAttribute("apostasRealizadasF3",
							dao.buscaApostasRealizadasAdmin(3));

					request.setAttribute("dadosPartidasF4",
							dao.buscaDadosPartidas(4));
					request.setAttribute("apostasRealizadasF4",
							dao.buscaApostasRealizadasAdmin(4));

					request.setAttribute("dadosPartidasF5",
							dao.buscaDadosPartidas(5));
					request.setAttribute("apostasRealizadasF5",
							dao.buscaApostasRealizadasAdmin(5));
*/
					request.setAttribute("dataHoje", new SimpleDateFormat(
							"yyyy-MM-dd").format(Calendar.getInstance()
							.getTime()));
					request.setAttribute("usuario",
							request.getParameter("nLogin"));
					request.setAttribute("senha",
							request.getParameter("nSenha"));
					request.getRequestDispatcher(
							"e_copa_arquivos/copa_aposta.jsp").forward(request,
							response);
				} else {
					System.out.println("apostador");
					request.setAttribute("objetoUsuario", usuario);
					request.setAttribute("dadosPartidas",
							dao.buscaDadosPartidas(1));
					request.setAttribute("apostasRealizadas",
							dao.buscaApostasRealizadas(usuario, 1));

					/*request.setAttribute("dadosPartidasF2",
							dao.buscaDadosPartidas(2));
					request.setAttribute("apostasRealizadasF2",
							dao.buscaApostasRealizadas(usuario, 2));

					request.setAttribute("dadosPartidasF3",
							dao.buscaDadosPartidas(3));
					request.setAttribute("apostasRealizadasF3",
							dao.buscaApostasRealizadas(usuario, 3));

					request.setAttribute("dadosPartidasF4",
							dao.buscaDadosPartidas(4));
					request.setAttribute("apostasRealizadasF4",
							dao.buscaApostasRealizadas(usuario, 4));

					request.setAttribute("dadosPartidasF5",
							dao.buscaDadosPartidas(5));
					request.setAttribute("apostasRealizadasF5",
							dao.buscaApostasRealizadas(usuario, 5));*/

					request.setAttribute("dataHoje", new SimpleDateFormat(
							"yyyy-MM-dd").format(Calendar.getInstance()
							.getTime()));
					request.setAttribute("usuario",
							request.getParameter("nLogin"));
					request.setAttribute("senha",
							request.getParameter("nSenha"));
					request.getRequestDispatcher(
							"e_copa_arquivos/copa_aposta.jsp").forward(request,
							response);
				}
			}
		} else if (request.getParameter("tipo").equals("cadastrarUsuario")) {

			System.out.println(request.getParameter("nLoginAdmin"));
			System.out.println(request.getParameter("nNomeAdmin"));
			System.out.println(request.getParameter("nSenhaAdmin"));

			dao.cadastrarUsuarioDAO(request);

		} else if (request.getParameter("tipo").equals("alterarSenha")) {

			System.out.println(request.getParameter("novaSenha"));
			System.out.println(request.getParameter("id"));

			dao.alterarSenha(request);

		} else if (request.getParameter("tipo").equals("gravar")) {

			if (request.getParameter("admin") != null) {
				System.out.println("chegou aqui admi");
				dao.gravarApostasAdmin(request);
			} else {
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				for (Enumeration<String> e = request.getParameterNames(); e
						.hasMoreElements();) {

					String varReq = e.nextElement();

					if (varReq.contains("player")) {
						String idReq = varReq.substring(8, varReq.length());
						Date dtAposta;
						try {
							dtAposta = df.parse(request.getParameter("data_"
									+ idReq));
							Date dtSistema = df.parse(new SimpleDateFormat(
									"dd/MM/yyyy").format(Calendar.getInstance()
									.getTime()));

							if (dtSistema.compareTo(dtAposta) == 0
									|| dtSistema.compareTo(dtAposta) > 0) {
								System.out.println("apost " + dtAposta);
								System.out.println("sistem " + dtSistema);
								System.out.println("ignora!!!!!!!!");
							} else {

								dao.gravarApostas(varReq, request);

							}

						} catch (ParseException e1) {
							e1.printStackTrace();
						}

					}

				}

			}

		}

	}
}
