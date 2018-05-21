<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="caixa.dirid.VO.CopaResultadosCompletoVO"%>
<%@ page import="caixa.dirid.UTEIS.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<%
		/***********/
		/*VARIAVEIS*/
		/***********/
		List<CopaResultadosCompletoVO> partidas = (List<CopaResultadosCompletoVO>) request.getAttribute("objetoUsuario");
	%>
	

<head>
	<script type="text/javascript" src="jquery.jqplot.1.0.8r1250/dist/jquery.js"></script>
	<meta charset="ISO-8859-1">
	<meta http-equiv=Content-Type content="text/html; charset=windows-1252">
	<meta name=ProgId content=Excel.Sheet>
	<meta name=Generator content="Microsoft Excel 15">
		
	<style>
	
		#externo {
			display: table;
			width: 100%;
		}
		
		.interno {
			float: left;
			width:49%;
		}
		
		#cadastroUser{
			display:none;
		}
		
		#mudarSenha{
			display:none;
		}
		
		a:link, a:visited {
		    background-color: #D0DAFD;
		    color: white;
		    padding: 14px 25px;
		    text-align: center;
		    text-decoration: none;
		    display: inline-block;
		    margin-left: 43px;
		}
		
		
		a:hover, a:active {
		    background-color: blue;
		}
				
		#newspaper-a
		{
			font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
			font-size: 12px;
			margin: 45px;
			width: 480px;
			text-align: left;
			border-collapse: collapse;
			border: 1px solid #69c;
		}
		
		#newspaper-a th
		{
			padding: 12px 17px 12px 17px;
			font-weight: normal;
			font-size: 14px;
			color: #039;
			border-bottom: 1px dashed #69c;
		}
		#newspaper-a td
		{
			padding: 7px 17px 7px 17px;
			color: #669;
		}
		#newspaper-a tbody tr:hover td
		{
			color: #339;
			background: #d0dafd;
		}

	</style>
	<script>
	$(document).ready(function() {
		
	//alert('Os seguintes resultados foram apenas um teste, após o dia 10 será pra valer!');	
		
	});
	
	
	function visualizarTodasApostas(){
		
		var num = "";
		sessionStorage.setItem("numeroAceitacao", num); 
		window.open('copaServlet?tipo=verTodasApostas','_blank','scrollbars=yes,resizable=yes,top=100,left=70,width=1000,height=500');
		
	}
	
	</script>
</head>

<body>
	<a href="e_copa_arquivos/copa_menu.htm">Menu</a>
	<br>
	<br>
	<div style="margin-left: 70px;">
				<input type="button"  value ="Visualizar todas as apostas" onClick='visualizarTodasApostas()'>
	</div>
	<br>
	<table id="newspaper-a" border=1>
		<thead>
			<tr>
				<th>&nbsp;</th>
				<th>Nome</th>
				<th>Pontua&ccedil;&atilde;o</th>
			</tr>
		</thead>
		<%for(int i = 0; i < partidas.size();i++){%>
		<tbody>
			<tr>
				<td><%=i+1%>º</td>
				<td><%=partidas.get(i).getNome()%></td>
				<td><%=partidas.get(i).getResultado()%></td>
			</tr>
		</tbody>
		<%}%>
	</table>


</body>

</html>
