<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<!-- Compatibilidade com IE antigo -->
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Sistema Interno - DIRID</title>
		<!-- import do Jquery -->
		<script type="text/javascript" src="jquery.jqplot.1.0.8r1250/dist/jquery.js"></script>
	
		<%@ include file="importHeadBootstrap.jsp"%>
		<link rel="stylesheet" type="text/css" href="css/aparencia.css"/>
		<link href="imagens/caixaFav.ico" rel="shortcut icon" type="image/x-icon"/>
	</head>

	<body>
		<%@ include file="cabecBarraAzul.jsp"%>

		<div class="container" style="padding-left: 0px; padding-right: 0px;">
			<div class="header">
				<a href="">
					<img src="imagens/caixa_seguradora_cor_rgb_negativa.png" alt="Logotipo"	name="Logo" width="20%" id="logoCaixa"/>
				</a>
			</div><!-- end .header -->

			<div class="content" align="center">

				<br/> <br/> <br/>
				DIGITE '037' PARA LOGAR
				<form method="post" action="loginServlet" class="form-inline">
					<div class="form-group">
						<label for="exampleInputName2">CPF:    </label>
						<input type="text" class="form-control" name="CPF" placeholder="3 Dígitos" />
					</div>   
					<button type="submit" class="btn btn-default">Entrar</button>
				</form>
				
				<%
					if (request.getParameter("error") != null) {
						out.println("<p style='color:red;'>CPF Inválido</p>");
					}
				%>
				
				<br/> <br/> <br/> <br/>
							
			</div> <!-- TAG CONTEUDO -->

			<div class="footer">
				<p>Diretoria de Riscos Diversos - DIRID | Produtos Auto e RD</p>
				<!-- end .footer -->
			</div>
		<!-- end .container -->
		</div>
		<%@ include file="importBodyBootstrap.jsp"%>
	</body>
	
</html>
