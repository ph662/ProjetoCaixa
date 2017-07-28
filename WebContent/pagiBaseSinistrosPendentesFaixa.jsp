<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="caixa.dirid.VO.SinistroPendente_base_FaixaVO"%>
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
		List<SinistroPendente_base_FaixaVO> baseSinPendentes = (List<SinistroPendente_base_FaixaVO>) request.getAttribute("baseSinPendente");
	%>
	

<head>
	<style>
		#newspaper-a
		{
			font-size: 10px;
		}
	</style>
</head>

<body>
	<table id="newspaper-a" border=1>
		<thead>
			<tr>
				<th>LEGADO</th>
				<th>ANOMES_REF</th>
				<th>ORG</th>
				<th>MOVIMENTO</th>
				<th>RAMO</th>
				<th>PROD</th>
				<th>DIA</th>
				<th>SINISTRO</th>
				<th>APOLICE</th>
				<th>COMUNICADO</th>
				<th>OCORRENCIA</th>
				<th>FAVORECIDO</th>
				<th>VL_LIDER</th>
				<th>VL_COSSEGURO</th>
				<th>VL_RESSEGURO</th>
				<th>VL_TOTAL</th>
				<th>COD_OPERACAO</th>
				<th>OPERACAO</th>
				<th>FTE_PREM</th>
				<th>FTE_AVIS</th>
				<th>AVISO</th>
				<th>SEGURADO</th>
				<th>CAUSA</th>
				<th>GRUPO_CAUSA</th>
				<th>GRUPO</th>
				<th>TIPO</th>
				<th>FAIXA_DE_VALOR</th>
				<th>data_aviso</th>
				<th>hoje</th>
				<th>TEMPO_PENDENTE</th>
				<th>FAIXA_TEMPO_PENDENTE</th>
			</tr>
		</thead>
		<%for(int i = 0; i < baseSinPendentes.size();i++){%>
		<tbody>
			<tr>
				<td><%=baseSinPendentes.get(i).getLEGADO()%></td>
				<td><%=baseSinPendentes.get(i).getANOMES_REF()%></td>
				<td><%=baseSinPendentes.get(i).getORG()%></td>
				<td><%=baseSinPendentes.get(i).getMOVIMENTO()%></td>
				<td><%=baseSinPendentes.get(i).getRAMO()%></td>
				<td><%=baseSinPendentes.get(i).getPROD()%></td>
				<td><%=baseSinPendentes.get(i).getDIA()%></td>
				<td><%=baseSinPendentes.get(i).getSINISTRO()%></td>
				<td><%=baseSinPendentes.get(i).getAPOLICE()%></td>
				<td><%=baseSinPendentes.get(i).getCOMUNICADO()%></td>
				<td><%=baseSinPendentes.get(i).getOCORRENCIA()%></td>
				<td><%=baseSinPendentes.get(i).getFAVORECIDO()%></td>
				<td><%=baseSinPendentes.get(i).getVL_LIDER()%></td>
				<td><%=baseSinPendentes.get(i).getVL_COSSEGURO()%></td>
				<td><%=baseSinPendentes.get(i).getVL_RESSEGURO()%></td>
				<td><%=baseSinPendentes.get(i).getVL_TOTAL()%></td>
				<td><%=baseSinPendentes.get(i).getCOD_OPERACAO()%></td>
				<td><%=baseSinPendentes.get(i).getOPERACAO()%></td>
				<td><%=baseSinPendentes.get(i).getFTE_PREM()%></td>
				<td><%=baseSinPendentes.get(i).getFTE_AVIS()%></td>
				<td><%=baseSinPendentes.get(i).getAVISO()%></td>
				<td><%=baseSinPendentes.get(i).getSEGURADO()%></td>
				<td><%=baseSinPendentes.get(i).getCAUSA()%></td>
				<td><%=baseSinPendentes.get(i).getGRUPO_CAUSA()%></td>
				<td><%=baseSinPendentes.get(i).getGRUPO()%></td>
				<td><%=baseSinPendentes.get(i).getTIPO()%></td>
				<td><%=baseSinPendentes.get(i).getFAIXA_DE_VALOR()%></td>
				<td><%=baseSinPendentes.get(i).getData_aviso()%></td>
				<td><%=baseSinPendentes.get(i).getHoje()%></td>
				<td><%=baseSinPendentes.get(i).getTEMPO_PENDENTE()%></td>
				<td><%=baseSinPendentes.get(i).getFAIXA_TEMPO_PENDENTE()%></td>
			</tr>
		</tbody>
		<%}%>
	</table>
	
	
	<br>
	
	<br><br>
		
</body>

</html>
