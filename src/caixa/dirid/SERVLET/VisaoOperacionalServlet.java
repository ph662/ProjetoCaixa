package caixa.dirid.SERVLET;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import caixa.dirid.BO.VisaoOperacionalBO;

public class VisaoOperacionalServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		VisaoOperacionalBO boOperacional = new VisaoOperacionalBO();
		/*
		 * =============================================
		 * 
		 * RENOVACAO
		 * 
		 * =============================================
		 */
		if (req.getParameter("tipo").equals("renovacao")) {
			req.setAttribute("listaRenovacao",boOperacional.validaSelecionaAnaliseRenovacao(req.getParameter("mes"), req.getParameter("ano"), req.getParameter("codProd")));
			req.setAttribute("listaCanal",boOperacional.validaSelecionaCanalApolicesEmitidas(req.getParameter("mes"), req.getParameter("ano"), req.getParameter("codProd")));
			req.setAttribute("listaRenovacaoCanal",boOperacional.validaSelecionaRenovacoesRealizadas(req.getParameter("mes"), req.getParameter("ano"), req.getParameter("codProd")));
			// utilizado para evitar o erro:
			// "Motivo: o cabeçalho CORS 'Access-Control-Allow-Origin' não está presente"
			// gerado quando acesso a aplicação pelo localhost para o ip local
			// 10.125.7.49
			resp.setHeader("Access-Control-Allow-Origin", "*");

			req.getRequestDispatcher("ajaxTabelaRenovacao.jsp").forward(req,resp);
			/*
			 * =============================================
			 * 
			 * SENSIBILIZACAO
			 * 
			 * =============================================
			 */
		} else if (req.getParameter("tipo").equals("sensibilizacao")) {

			if (req.getParameter("download") != null) { // faz download
				resp.setHeader("Content-Disposition",
						"attachment; filename=\"teste.xlsl\"");

				String filePath = "D:\\eclipse-workspace/ProjetoCaixaLocal_1.3/WebContent/Downloads/"
						+ req.getParameter("categoria")
						+ "/"
						+ req.getParameter("categoria")
						+ req.getParameter("ano")
						+ req.getParameter("mes")
						+ ".xls";
				File downloadFile = new File(filePath);
				FileInputStream inStream = new FileInputStream(downloadFile);

				// if you want to use a relative path to context root:
				String relativePath = getServletContext().getRealPath("");
				System.out.println("relativePath = " + relativePath);

				// obtains ServletContext
				ServletContext context = getServletContext();

				// gets MIME type of the file
				String mimeType = context.getMimeType(filePath);
				if (mimeType == null) {
					// set to binary type if MIME mapping not found
					mimeType = "application/octet-stream";
				}
				// System.out.println("MIME type: " + mimeType);

				// modifies resp
				resp.setContentType(mimeType);
				resp.setContentLength((int) downloadFile.length());

				// forces download
				String headerKey = "Content-Disposition";
				String headerValue = String.format(
						"attachment; filename=\"%s\"", downloadFile.getName());
				resp.setHeader(headerKey, headerValue);

				// obtains resp's output stream
				OutputStream outStream = resp.getOutputStream();

				byte[] buffer = new byte[4096];
				int bytesRead = -1;

				while ((bytesRead = inStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
				}

				inStream.close();
				outStream.close();
			} else { // nao faz download

				if (!(req.getParameter("categ").equals("totalizador") || req
						.getParameter("categ").equals("totalizadorRenovacao"))) { // se
					// nao
					// for
					// totalizador
					if (req.getParameter("paginacao") == null) {
						if (req.getParameter("prod") == null) {
							boOperacional.validaGeraRelatorioExcel("*",
									req.getParameter("anoSensibilizacao"),
									req.getParameter("mesSensibilizacao"),
									req.getParameter("categ"));
						} else {
							boOperacional.validaGeraRelatorioExcel(
									req.getParameter("cod"),
									req.getParameter("anoSensibilizacao"),
									req.getParameter("mesSensibilizacao"),
									req.getParameter("categ"));
						}
					}
				}
				if (req.getParameter("categ").equals("vendasNovas")) {
					if (req.getParameter("prod") == null) {

						try {

							if (req.getParameter("paginacao") != null) {
								req.setAttribute(
										"listaPorAgenciaQuantidadeValorSensibilizacao",
										boOperacional
												.validaSelecionaTotalAgrupadoPorAgenciaSensibilizacao(
														"*",
														req.getParameter("anoSensibilizacao"),
														req.getParameter("mesSensibilizacao"),
														req.getParameter("categ"),
														Integer.parseInt(req
																.getParameter("pagina"))));
								req.getRequestDispatcher(
										"ajaxTabelaSensibilizacaoApenasDados.jsp")
										.forward(req, resp);
							}

							req.setAttribute(
									"listaPeriodoSensibilizacao",
									boOperacional
											.validaSelecionaPeriodoSensibilizacao(
													req.getParameter("anoSensibilizacao"),
													req.getParameter("mesSensibilizacao"),
													req.getParameter("categ")));
							req.setAttribute(
									"listaSensibilizacao",
									boOperacional
											.validaSelecionaTotalSensibilizacao(
													"*",
													req.getParameter("anoSensibilizacao"),
													req.getParameter("mesSensibilizacao"),
													req.getParameter("categ"),
													""));

							req.setAttribute(
									"listaPorAgenciaQuantidadeValorSensibilizacao",
									boOperacional
											.validaSelecionaTotalAgrupadoPorAgenciaSensibilizacao(
													"*",
													req.getParameter("anoSensibilizacao"),
													req.getParameter("mesSensibilizacao"),
													req.getParameter("categ"),
													Integer.parseInt(req
															.getParameter("pagina"))));

							req.setAttribute("categoria",
									req.getParameter("categ"));
							req.setAttribute(
									"paginacaoMax",
									boOperacional
											.validaSelecionaQuantidadeRegistros(
													"*",
													req.getParameter("anoSensibilizacao"),
													req.getParameter("mesSensibilizacao"),
													req.getParameter("categ")));
							req.setAttribute("mes",
									req.getParameter("mesSensibilizacao"));
							req.setAttribute("ano",
									req.getParameter("anoSensibilizacao"));

							// utilizado para evitar o erro:
							// "Motivo: o cabeçalho CORS 'Access-Control-Allow-Origin' não está presente"
							// gerado quando acesso a aplicação pelo localhost
							// para
							// o ip local
							// 10.125.7.49
							resp.setHeader("Access-Control-Allow-Origin", "*");

							req.getRequestDispatcher(
									"ajaxTabelaSensibilizacao.jsp").forward(
									req, resp);

						} catch (IllegalStateException ise) {
							// ise.printStackTrace();
							System.out
									.println("VisaoOperacionalServlet linha 136 - IllegalStateException");
						}

					} else { // prod != null
						try {

							if (req.getParameter("paginacao") != null) {
								req.setAttribute(
										"listaPorAgenciaQuantidadeValorSensibilizacao",
										boOperacional
												.validaSelecionaTotalAgrupadoPorAgenciaSensibilizacao(
														req.getParameter("cod"),
														req.getParameter("anoSensibilizacao"),
														req.getParameter("mesSensibilizacao"),
														req.getParameter("categ"),
														Integer.parseInt(req
																.getParameter("pagina"))));
								req.getRequestDispatcher(
										"ajaxTabelaSensibilizacaoApenasDados.jsp")
										.forward(req, resp);
							}

							req.setAttribute(
									"listaPeriodoSensibilizacao",
									boOperacional
											.validaSelecionaPeriodoSensibilizacao(
													req.getParameter("anoSensibilizacao"),
													req.getParameter("mesSensibilizacao"),
													req.getParameter("categ")));
							req.setAttribute(
									"listaSensibilizacao",
									boOperacional
											.validaSelecionaTotalSensibilizacao(
													req.getParameter("cod"),
													req.getParameter("anoSensibilizacao"),
													req.getParameter("mesSensibilizacao"),
													req.getParameter("categ"),
													""));
							req.setAttribute(
									"listaPorAgenciaQuantidadeValorSensibilizacao",
									boOperacional
											.validaSelecionaTotalAgrupadoPorAgenciaSensibilizacao(
													req.getParameter("cod"),
													req.getParameter("anoSensibilizacao"),
													req.getParameter("mesSensibilizacao"),
													req.getParameter("categ"),
													Integer.parseInt(req
															.getParameter("pagina"))));
							req.setAttribute("categoria",
									req.getParameter("categ"));
							req.setAttribute(
									"paginacaoMax",
									boOperacional
											.validaSelecionaQuantidadeRegistros(
													req.getParameter("cod"),
													req.getParameter("anoSensibilizacao"),
													req.getParameter("mesSensibilizacao"),
													req.getParameter("categ")));
							req.setAttribute("mes",
									req.getParameter("mesSensibilizacao"));
							req.setAttribute("ano",
									req.getParameter("anoSensibilizacao"));
							req.setAttribute("codigoProduto",
									req.getParameter("cod"));
							// utilizado para evitar o erro:
							// "Motivo: o cabeçalho CORS 'Access-Control-Allow-Origin' não está presente"
							// gerado quando acesso a aplicação pelo localhost
							// para
							// o ip local
							// 10.125.7.49
							resp.setHeader("Access-Control-Allow-Origin", "*");

							req.getRequestDispatcher(
									"ajaxTabelaSensibilizacao.jsp").forward(
									req, resp);

						} catch (IllegalStateException ise) {
							// ise.printStackTrace();
							System.out
									.println("VisaoOperacionalServlet linha 168 - IllegalStateException");
						}
					}
				} else if (req.getParameter("categ").equals("fluxoFinanceiro")) {
					if (req.getParameter("prod") == null) {

						try {

							if (req.getParameter("paginacao") != null) {
								req.setAttribute(
										"listaPorAgenciaQuantidadeValorSensibilizacao",
										boOperacional
												.validaSelecionaTotalAgrupadoPorAgenciaSensibilizacao(
														"*",
														req.getParameter("anoSensibilizacao"),
														req.getParameter("mesSensibilizacao"),
														req.getParameter("categ"),
														Integer.parseInt(req
																.getParameter("pagina"))));
								req.getRequestDispatcher(
										"ajaxTabelaSensibilizacaoApenasDados.jsp")
										.forward(req, resp);
							}

							req.setAttribute(
									"listaPeriodoSensibilizacao",
									boOperacional
											.validaSelecionaPeriodoSensibilizacao(
													req.getParameter("anoSensibilizacao"),
													req.getParameter("mesSensibilizacao"),
													req.getParameter("categ")));
							req.setAttribute(
									"listaSensibilizacao",
									boOperacional
											.validaSelecionaTotalSensibilizacao(
													"*",
													req.getParameter("anoSensibilizacao"),
													req.getParameter("mesSensibilizacao"),
													req.getParameter("categ"),
													""));

							req.setAttribute(
									"listaPorAgenciaQuantidadeValorSensibilizacao",
									boOperacional
											.validaSelecionaTotalAgrupadoPorAgenciaSensibilizacao(
													"*",
													req.getParameter("anoSensibilizacao"),
													req.getParameter("mesSensibilizacao"),
													req.getParameter("categ"),
													Integer.parseInt(req
															.getParameter("pagina"))));

							req.setAttribute("categoria",
									req.getParameter("categ"));
							req.setAttribute(
									"paginacaoMax",
									boOperacional
											.validaSelecionaQuantidadeRegistros(
													"*",
													req.getParameter("anoSensibilizacao"),
													req.getParameter("mesSensibilizacao"),
													req.getParameter("categ")));
							req.setAttribute("mes",
									req.getParameter("mesSensibilizacao"));
							req.setAttribute("ano",
									req.getParameter("anoSensibilizacao"));

							// utilizado para evitar o erro:
							// "Motivo: o cabeçalho CORS 'Access-Control-Allow-Origin' não está presente"
							// gerado quando acesso a aplicação pelo localhost
							// para
							// o ip local
							// 10.125.7.49
							resp.setHeader("Access-Control-Allow-Origin", "*");

							req.getRequestDispatcher(
									"ajaxTabelaSensibilizacao.jsp").forward(
									req, resp);

						} catch (IllegalStateException ise) {
							// ise.printStackTrace();
							System.out
									.println("VisaoOperacionalServlet linha 213 - IllegalStateException");
						}

					} else {// prod != null

						try {

							if (req.getParameter("paginacao") != null) {
								req.setAttribute(
										"listaPorAgenciaQuantidadeValorSensibilizacao",
										boOperacional
												.validaSelecionaTotalAgrupadoPorAgenciaSensibilizacao(
														req.getParameter("cod"),
														req.getParameter("anoSensibilizacao"),
														req.getParameter("mesSensibilizacao"),
														req.getParameter("categ"),
														Integer.parseInt(req
																.getParameter("pagina"))));
								req.getRequestDispatcher(
										"ajaxTabelaSensibilizacaoApenasDados.jsp")
										.forward(req, resp);
							}

							req.setAttribute(
									"listaPeriodoSensibilizacao",
									boOperacional
											.validaSelecionaPeriodoSensibilizacao(
													req.getParameter("anoSensibilizacao"),
													req.getParameter("mesSensibilizacao"),
													req.getParameter("categ")));

							req.setAttribute(
									"listaSensibilizacao",
									boOperacional
											.validaSelecionaTotalSensibilizacao(
													req.getParameter("cod"),
													req.getParameter("anoSensibilizacao"),
													req.getParameter("mesSensibilizacao"),
													req.getParameter("categ"),
													""));

							req.setAttribute(
									"listaPorAgenciaQuantidadeValorSensibilizacao",
									boOperacional
											.validaSelecionaTotalAgrupadoPorAgenciaSensibilizacao(
													req.getParameter("cod"),
													req.getParameter("anoSensibilizacao"),
													req.getParameter("mesSensibilizacao"),
													req.getParameter("categ"),
													Integer.parseInt(req
															.getParameter("pagina"))));
							req.setAttribute("categoria",
									req.getParameter("categ"));
							req.setAttribute(
									"paginacaoMax",
									boOperacional
											.validaSelecionaQuantidadeRegistros(
													req.getParameter("cod"),
													req.getParameter("anoSensibilizacao"),
													req.getParameter("mesSensibilizacao"),
													req.getParameter("categ")));
							req.setAttribute("mes",
									req.getParameter("mesSensibilizacao"));
							req.setAttribute("ano",
									req.getParameter("anoSensibilizacao"));
							req.setAttribute("codigoProduto",
									req.getParameter("cod"));
							// utilizado para evitar o erro:
							// "Motivo: o cabeçalho CORS 'Access-Control-Allow-Origin' não está presente"
							// gerado quando acesso a aplicação pelo localhost
							// para
							// o ip local
							// 10.125.7.49
							resp.setHeader("Access-Control-Allow-Origin", "*");

							req.getRequestDispatcher(
									"ajaxTabelaSensibilizacao.jsp").forward(
									req, resp);

						} catch (IllegalStateException ise) {
							// ise.printStackTrace();
							System.out
									.println("VisaoOperacionalServlet linha 168 - IllegalStateException");
						}
					}
				} else if (req.getParameter("categ").equals("totalizador")) {

					String anoParam = req.getParameter("anoParam");

					req.setAttribute("listaSensibilizacaoTotalizadorMensal",boOperacional.validaSelecionaTotalizadorMensal(boOperacional.validaSelecionaTotalizadorMensal("", anoParam), anoParam));

					String dataAtual = new SimpleDateFormat("dd/MM/yyyy").format(new Date(System.currentTimeMillis()));
					String dataCut[] = dataAtual.split("/");

					if (!anoParam.equals(dataCut[2])) { //se for ano anterior
						dataCut[1] = "12";
					}
					req.setAttribute("listaPeriodoSensibilizacao",boOperacional.validaSelecionaPeriodoSensibilizacao(anoParam, dataCut[1], "vendasNovas"));

					// req.setAttribute("listaTotalSensibilizacao",boOperacional.validaSelecionaListaTotalizadorSensibilizacao(prod,ano,
					// mes, categoria));
					req.getRequestDispatcher("ajaxTabelaSensibilizacaoTotalizador.jsp").forward(req, resp);

					// RENOVACAO
				}/*else if (req.getParameter("categ").equals(
						"totalizadorRenovacao")) {

					String anoParam = req.getParameter("categ");
					req.setAttribute(
							"listaSensibilizacaoTotalizadorRenovacaoMensal",
							boOperacional.validaSelecionaTotalizadorMensal(
									"renovacao", anoParam));
					// req.setAttribute(
					// "listaSensibilizacaoTotalizadorRenovacaoAcumulado",
					// boOperacional.validaSelecionaTotalizadorAcumulado());

					String dataAtual = new SimpleDateFormat("dd/MM/yyyy")
							.format(new Date(System.currentTimeMillis()));
					String dataCut[] = dataAtual.split("/");

					// req.setAttribute("listaPeriodoSensibilizacaoRenovacao",boOperacional.validaSelecionaPeriodoSensibilizacao(dataCut[2],
					// dataCut[1], "vendasNovas"));

					// req.setAttribute("listaTotalSensibilizacao",boOperacional.validaSelecionaListaTotalizadorSensibilizacao(prod,
					// ano, mes, categoria));
					req.getRequestDispatcher(
							"ajaxTabelaSensibilizacaoTotalizadorRenovacao.jsp")
							.forward(req, resp);

				}*/
			}
			/*
			 * ========================================================
			 * 
			 * RELATORIO RESUMIDO ACEITACAO RRA
			 * 
			 * ========================================================
			 */
		} else if (req.getParameter("tipo").equals("novoRelaAceitacao")) {

			if (req.getParameter("alterar") != null) {
				req.setAttribute("relatorioSalvo", boOperacional
						.validaSelecionaRelatoriosSalvos(req
								.getParameter("numAceitacao")));
				req.setAttribute("coberturasRelatorioSalvo", boOperacional
						.validaSelecionaCoberturasRelatoriosSalvos(req
								.getParameter("numAceitacao")));
				req.setAttribute("alterar", "t");
				req.setAttribute("numAceitacao",
						req.getParameter("numAceitacao"));
				req.setAttribute("coberturas",
						boOperacional.validaSelecionaCoberturas());
				req.getRequestDispatcher("pagiRelatorioResumidoAceitacao.jsp")
						.forward(req, resp);
			} else {
				req.setAttribute("novoNumeroAceitacao",
						boOperacional.validaSelecionaMaiorNumeroAceitacao());
				req.setAttribute("coberturas",
						boOperacional.validaSelecionaCoberturas());
				req.getRequestDispatcher("pagiRelatorioResumidoAceitacao.jsp")
						.forward(req, resp);
			}

		} else if (req.getParameter("tipo").equals("menuRelAceitacao")) {
			req.setAttribute("relatoriosSalvos",
					boOperacional.validaSelecionaRelatoriosSalvos(null));
			req.setAttribute("coberturasRelatoriosSalvos", boOperacional
					.validaSelecionaCoberturasRelatoriosSalvos(null));
			req.getRequestDispatcher("pagiMenuGerenciamentoRA.jsp").forward(
					req, resp);

		} else if (req.getParameter("tipo").equals("imprimeRRA")) {
			String numAceitacao = req.getParameter("numAceitacao");
			req.setAttribute("relatorioSalvo",
					boOperacional.validaSelecionaRelatoriosSalvos(numAceitacao));
			req.setAttribute("coberturasRelatorioSalvo", boOperacional
					.validaSelecionaCoberturasRelatoriosSalvos(numAceitacao));
			req.setAttribute("numAceitacao", req.getParameter("numAceitacao"));
			req.getRequestDispatcher("relatorioRA.jsp").forward(req, resp);

		} else if (req.getParameter("tipo").equals("teste")) {

			// PAREI AQUIIIIIIIIIIIIIII
			// UMA FORMA DE PELO NAVEGADOR SALVAR arquivos NO SERVIDOR
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		VisaoOperacionalBO boOperacional = new VisaoOperacionalBO();
		/*
		 * ========================================================
		 * 
		 * RELATORIO RESUMIDO ACEITACAO RRA
		 * 
		 * ========================================================
		 */
		if (req.getParameter("tipo").equals("relAceitacao")) {
			req.setAttribute("imprime", "t");
			req.setAttribute("numAceitacao",
					boOperacional.validaTrataDadosAceitacao(req));
			req.setAttribute("relatoriosSalvos",
					boOperacional.validaSelecionaRelatoriosSalvos(null));
			req.setAttribute("coberturasRelatoriosSalvos", boOperacional
					.validaSelecionaCoberturasRelatoriosSalvos(null));
			req.getRequestDispatcher("pagiMenuGerenciamentoRA.jsp").forward(
					req, resp);

		}
		if (req.getParameter("tipo").equals("relAceitacaoAlterar")) {
			req.setAttribute("numAceitacao",
					boOperacional.validaTrataDadosAceitacaoAlterar(req));
			req.setAttribute("relatoriosSalvos",
					boOperacional.validaSelecionaRelatoriosSalvos(null));
			req.setAttribute("coberturasRelatoriosSalvos", boOperacional
					.validaSelecionaCoberturasRelatoriosSalvos(null));
			req.getRequestDispatcher("pagiMenuGerenciamentoRA.jsp").forward(
					req, resp);

		} else if (req.getParameter("tipo").equals("insereData")) {
			System.out.println("chegou");
			boOperacional.validaGravaAlteracoesGrid(req);
		}
	}
}