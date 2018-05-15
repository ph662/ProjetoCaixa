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
	<style>
		#newspaper-a
		{
			font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
			font-size: 11px;
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
</head>

<body>
	<table id="newspaper-a" border=1>
		<thead>
			<tr>
				<th>Nome</th>
				<th>Login</th>
				<th>Resultado</th>
				<th>Time</th>
				<th>Aposta</th>
				<th>&nbsp;</th>
				<th>Aposta</th>
				<th>Time</th>
				<th></th>
				<th>Time</th>
				<th>Valor Real</th>
				<th>&nbsp;</th>
				<th>Valor Real</th>
				<th>Time</th>
				<th>Data</th>
				<th>Fase</th>
			</tr>
		</thead>
		<%for(int i = 0; i < partidas.size();i++){%>
		<tbody>
			<tr>
				<td><%=partidas.get(i).getNome()%></td>
				<td><%=partidas.get(i).getLogin()%></td>
				<td><%=partidas.get(i).getResultado()%></td>
				<td><%=partidas.get(i).getNomeTime1()%></td>
				<td><%=partidas.get(i).getApostaP1()%></td>
				<td>x</td>
				<td><%=partidas.get(i).getApostaP2()%></td>
				<td><%=partidas.get(i).getNomeTime2()%></td>
				<td>&nbsp;</td>
				<td><b><%=partidas.get(i).getNomeTimeReal1()%></b></td>
				<td><b><%=partidas.get(i).getRealP1()%></b></td>
				<td>x</td>
				<td><b><%=partidas.get(i).getRealP2()%></b></td>
				<td><b><%=partidas.get(i).getNomeTimeReal2()%></b></td>
				<td><%=partidas.get(i).getDataPartida()%></td>
				<td><%=partidas.get(i).getFase()%></td>
			</tr>
		</tbody>
		<%}%>
	</table>
	
	
	<br>
	
	<br><br>
		
</body>

</html>
