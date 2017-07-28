<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="caixa.dirid.VO.FatuMensalFinalVO"%>
<%@ page import="caixa.dirid.UTEIS.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<!-- Compatibilidade com IE antigo -->
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		
		<meta charset="utf-8">
		<meta name="author" content="phelipe662@gmail.com">
		<title>Vis&atilde;o Operacional</title>
		<%@ include file="importHeadBootstrap.jsp"%>
		<link rel="stylesheet" type="text/css" href="css/aparencia.css"/>
		<link href="imagens/caixaFav.ico" rel="shortcut icon" type="image/x-icon"/>
		
		<!-- import do Jquery -->
		<script type="text/javascript" src="jquery.jqplot.1.0.8r1250/dist/jquery.js"></script>
		
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

			#resultFatuMensal{
				color:black;
			}
			
			#carregando{
				height: 30px;
				width: 30px;
				margin-left:420px;
			}
			
			#externo {
				display: table;
				width: 740px;
				margin-left: 17%;
			}
			
			.interno {
				float: left;
			}
			#um table tbody tr td{
				text-align:right;
				padding-right: 20px;
			}
			
			.tabelaSensibilizacao tbody tr td{
				padding-left: 10px;"
			}	
			
			#produto{
				padding-left: 184px;
			}		
			
		</style>
		
		<script language="javascript" type="text/javascript" src="variavel_ip_da_aplicacao.js"></script>
		<script language="javascript" type="text/javascript" src="AjaxJS/funcoesAjax.js"></script>
		
		
		
		
		<script>
			$(document).ready(function(){
				
				$('#tipoMes').on('change', function(){
					var mes = $(this).val();
					var ano = $('#tipoAno').val();
					dadosRenovacao(ano,mes);
				});
				
				$('#tipoAno').on('change', function(){
					var ano = $(this).val();
					var mes = $('#tipoMes').val();
					dadosRenovacao(ano,mes);
				});
				
			});
		</script>
		
	</head>
	<body>
		
		<%@ include file="cabecBarraAzul.jsp"%>
		<div class="container" style="padding-left: 0px; padding-right: 0px;">
			<%@ include file="cabecImageCaixa.jsp"%>
			<%@ include file="menuEsquerdo.jsp"%>	

			<div class="content">
				<div class="page-header" style="margin-top: 0px; padding-bottom: 10px; margin-bottom: 0px; border-bottom-width: 0px;">
					<h2>Acompanhamento de Renova&ccedil;&atilde;o</h2>
					<hr style="margin-top: 0px; margin-bottom: 0px;">
				</div>
				
				<div id="comboMeses" align="center">
					Ano:
					<select id="tipoAno">
						<option selected value="zero">Selecionar</option>
						<option value="2015">2015</option>
						<option value="2016">2016</option>
						<option value="2017">2017</option>
					</select>
				</div><!-- id="comboMeses" -->
				
				<br />
				
				<div id="comboMeses" align="center">
					M&ecirc;s:&nbsp;
					<select id="tipoMes">
					<!-- select id="tipo" onchange="loadXMLDoc(this.value)"-->
						<option selected value="zero"> Selecionar</option>
						<%
							String[] mesesPagWeb = new Uteis().mesesPaginaWeb();
							for(int d = 0; d < mesesPagWeb.length; d++){
						%>
						
							<%
								if(d<9){
							%>
									<option value="0<%=d+1%>">
							<%
								}else{
							%>								
									<option value="<%=d+1%>">
							<%
								}
							%>
								<%=mesesPagWeb[d]%>
								</option>
						<%
							}
						%>
					</select>
				</div><!-- id="comboMeses" -->
				<br>
				<div id="tabelaAjax">
					<!-- img src= "imagens/loader.gif"-->
				</div>
			</div> <!-- TAG CONTEUDO -->

			<div class="footer">
				<p>Diretoria de Riscos Diversos - DIRID | Produtos Auto e RD</p>
			</div><!-- end .footer -->
		</div><!-- end .container -->
	<%@ include file="importBodyBootstrap.jsp"%>
	</body>
</html>