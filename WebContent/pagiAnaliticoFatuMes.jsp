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
		<meta name="author" content="Phelipe de Carvalho Lima">
		<title>Vis&atilde;o Anal&iacute;tica</title>
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
		</style>
		<script language="javascript" type="text/javascript" src="AjaxJS/funcoesAjax.js"></script>
	</head>
	<body>
		<!-- script>
			function loadXMLDoc(mes){
				var xmlhttp;
				if (window.XMLHttpRequest)
				  {// code for IE7+, Firefox, Chrome, Opera, Safari
				  xmlhttp=new XMLHttpRequest();
				  }
				else
				  {// code for IE6, IE5
				  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
				  }
				xmlhttp.onreadystatechange=function()
				  {
				  if (xmlhttp.readyState==4 && xmlhttp.status==200)
				    {
				    document.getElementById("tabelaAjax").innerHTML=xmlhttp.responseText;
				    }
				  }
				xmlhttp.open("GET","http://localhost:8080/ProjetoCaixa/visaoAnalitica?tipo=fatuMensal&mes="+mes,true);
				xmlhttp.send();
			}
		</script -->
	
		<%@ include file="cabecBarraAzul.jsp"%>
		<div class="container" style="padding-left: 0px; padding-right: 0px;">
			<%@ include file="cabecImageCaixa.jsp"%>
			<%@ include file="menuEsquerdo.jsp"%>	

			<div class="content">
				<div class="page-header" style="margin-top: 0px; padding-bottom: 10px; margin-bottom: 0px; border-bottom-width: 0px;">
					<h2>Faturamento por produto</h2>
					<hr style="margin-top: 0px; margin-bottom: 0px;">
					<!--  <br>
					<h3><small>Autom&oacute;vel - Faturamento</small></h3>-->
				</div>
				
				<div id="comboMeses" align="center">
					M&ecirc;s:&nbsp;
					<select id="tipo" onchange="enviaAjax(this.value)">
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