<%@ page import="caixa.dirid.VO.RvneVO"%>
<%@ page import="caixa.dirid.VO.FaturamentoVO"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="caixa.dirid.UTEIS.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<!-- Compatibilidade com IE antigo -->
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		
		<meta charset="utf-8">
		
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
		<link href="imagens/caixaFav.ico" rel="shortcut icon" type="image/x-icon">
		<title>Erro 500</title>
		<%@ include file="importHeadBootstrap.jsp"%>

		<!-- import do Jquery -->
		<script type="text/javascript" src="jquery.jqplot.1.0.8r1250/dist/jquery.js"></script>

		<style>
			.texto{
				color:white;
			}
			a{
				color:#e6d500;
			}
		</style>
	</head>

	<body role="document" background="imagens/background.jpg">
		<nav class="navbar navbar-inverse navbar-fixed-top">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<span class="navbar-brand" id="titulo"></span>
				</div>
				<div id="navbar" class="navbar-collapse collapse">
					<ul class="nav navbar-nav">
						
					</ul>
				</div>
			</div>
		</nav>
		<div class="container theme-showcase" role="main" style="padding-left: 0px;">
			<br> <br> <br> <br> <br> <br>
			<div class="page-header">
				<h1 class="texto">Erro interno</h1>
			</div>
			<h3 class="texto">
				Por gentileza tente novamente por este <a href="http://10.125.7.49:8080/ProjetoCaixa/pagiMenuPrincipal.jsp">link</a>.
			</h3>
			<h5>Se o problema permanecer entre em contato com a GERID. Ramal 2370 - Phelipe.</h5>
			<img id="img" src="imagens/trabalhando.png">
		</div>

		<%@ include file="importBodyBootstrap.jsp"%>
	</body>
</html>