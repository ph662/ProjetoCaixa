<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<!-- Compatibilidade com IE antigo -->
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<%@ include file="importHeadBootstrap.jsp"%>
		<meta charset="utf-8">
		<meta name="author" content="phelipe662@gmail.com">
		
		<title>MENU</title>
		<%@ include file="importHeadBootstrap.jsp"%>
		
		<link rel="stylesheet" type="text/css" href="css/aparencia.css"/>
		<link href="imagens/caixaFav.ico" rel="shortcut icon" type="image/x-icon"/>
		
		<style type="text/css">
			ul li a{
				text-decoration: none;
				color: black;
			}

			ul li a:hover{
				text-decoration: none;
				color: red;
			}

			ul li a span{
				font-size: 30px;	
			}
		</style>	
	</head>
	
	<body>
	
		<%@ include file="cabecBarraAzul.jsp"%>
		<div class="container" style="padding-left: 0px; padding-right: 0px;">
			<%@ include file="cabecImageCaixa.jsp"%>
			<%@ include file="menuEsquerdo.jsp"%>	
			
			<div class="content" height="100%">
				<table width="95%" height="100%" align="center" border="0" cellSpacing="10" cellPadding="5" style="margin-top: 40px;">
					<tbody>
						<tr>
							<td align="center">
								<!--a href="pagiAnaliticoFatuMes.jsp"-->
								<a href="#">
									<img src="imagens/search.png" width="110">
									<p>Vis&atilde;o Anal&iacute;tica</p>
								</a>
							</td>

							<td align="center">
								<!-- a href="pagiGraficoMes.jsp"-->
								<a href="pagiGraficoDiario.jsp">
									<img src="imagens/bar_chart.png" width="110">
									<p>Vis&atilde;o Executiva</p>
								</a>
							</td>

							<td align="center">
								<a href="pagiVisaoOperacional.jsp">
									<img src="imagens/engineer.png" width="110">
									<p>Vis&atilde;o Operacional</p>
								</a>
							</td>
						</tr>
					</tbody>
				</table>
			</div> <!-- TAG CONTEUDO -->

			<div class="footer">
				<p>Diretoria de Riscos Diversos - DIRID | Produtos Auto e RD</p>
			</div><!-- end .footer -->
		</div><!-- end .container -->
	</body>
</html>