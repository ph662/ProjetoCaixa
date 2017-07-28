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
		<meta name="author" content="phelipe662@gmail.com">
		<link href="imagens/caixaFav.ico" rel="shortcut icon" type="image/x-icon">
		<title>InformeDIRID</title>
		<%@ include file="importHeadBootstrap.jsp"%>
		<%@ include file="importHead_JQplot.jsp"%>
		
		

		<style>
		
	        table.stats-table td, table.highlighted-stats-table td {
	            background-color: rgb(230, 230, 230);
	            padding: 0.5em;
	        }
	
	        col.label {
	            width: 14em;
	        }
	
	        col.value {
	            width: 7em;
	        }
	
	        td.quintile-value {
	            text-align: center;
	        }
	
	        table.stats-table td.tooltip-header, table.highlighted-stats-table td.tooltip-header {
	            background-color: rgb(200, 200, 200);
	        }
	
	        td.contour-cell {
	            height: 1.5em;
	            padding-left: 20px;
	            padding-bottom: 1.5em;
	        }
	
	        table.highlighted-stats-table {
	            margin-top: 15px;
	        }
		
			#externo {
				display: table;
				width: 100%;
			}
			
			.interno {
				float: left;
			}

			.navbar{
				background-image: linear-gradient(to bottom, #0080c0 0px, #002855 100%);
				background-repeat: repeat-x;
				box-shadow: 0px 3px 9px rgba(0, 0, 0, 0.25) inset;
			}
			
			#botaoHome{
				background-image: linear-gradient(to bottom, #BC0808 0px, #CF4B4B 100%);
				background-repeat: repeat-x;
				box-shadow: 0px 3px 9px rgba(0, 0, 0, 0.25) inset;
			}

			#titulo{
				color: white;
			}
			
			.square {
				width: 10px;
				height: 10px;
			}
			
			.center {
			    width: 90%;
			    border: 1px solid orange;
			    padding: 10px;
			    background-color: white;
			}
			
			.titulo {
			    margin: auto;
			    border: 1px solid orange;
			    background-color: white;
			}
			
			.categoria{
				width: 30%;
			    
			    background-color: white;
			}
						
			img {
				max-width:100%;
				max-height:100%;
			
			}
			
			body {
				background-image:url("imagens/informeDirid/fundo.jpg");
				
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
					<span class="navbar-brand" id="titulo">Informe DIRID</span>
				</div>
				<div id="navbar" class="navbar-collapse collapse">
					<ul class="nav navbar-nav">
						<li class="active" id="botaoHome">
							<!-- a href="pagiMenuPrincipal.jsp">Menu</a-->
						</li>
					</ul>
				</div>
			</div>
		</nav>
		<div class="container theme-showcase" role="main" style="padding-left: 0px;">
			<br>
			<div class="page-header">
				<h1>Informe DIRID - Hist&oacute;rico</h1>
			</div>
			
			
			<div id="externo">
				<div class="interno" align="center" style="width:50%;">
					<div class="categoria">
						<h3>Quinzenais</h3>
					</div>
					<div class="center">
						<h4>Edi&ccedil;&atilde;o 1</h4>
						<img src="imagens/informeDirid/ed1.jpg">
					</div>
					<br>
					<br>
					<br>
					<br>
					<div class="center">
						<h4>Edi&ccedil;&atilde;o Especial</h4>
						<img src="imagens/informeDirid/especial.jpg">
					</div>
				</div>
				<div class="interno" align="center" style="width:50%;">
					<div class="categoria">
						<h3>Semanais</h3>
					</div>
					<div class="center">
						<h4>Edi&ccedil;&atilde;o de F&eacute;rias</h4>
						<img src="imagens/informeDirid/ed2.jpg">
					</div>
					<br>
					<br>
					<br>
					<br>
				</div>
			</div>
			

			
		</div>
		<%@ include file="importBodyBootstrap.jsp"%>
	</body>
</html>