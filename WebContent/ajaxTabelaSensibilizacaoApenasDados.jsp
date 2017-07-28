<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="caixa.dirid.VO.SensibilizacaoVO"%>
<%@ page import="caixa.dirid.UTEIS.*"%>
<%@ page import="java.util.*"%>

	<%
	/***********/
	/*VARIAVEIS*/
	/***********/
	DecimalFormat percentForm = new DecimalFormat("#.#%");
	DecimalFormat roundForm = new DecimalFormat("0.0");
	//Recebem o objeto "VO" da servlet "MenuServlet.java"
	List<SensibilizacaoVO> listaPorAgenciaValor = (List<SensibilizacaoVO>) request.getAttribute("listaPorAgenciaQuantidadeValorSensibilizacao");
	%>
	<table class="tabelaSensibilizacao" border="0" width="605">
		<thead>
			<tr bgcolor="#C5D9F1">
				<th>C&oacute;digo Ag&ecirc;ncia</th>
				<th>Ag&ecirc;ncia</th>
				<th>Produto</th>
				<th>SR</th>
				<th style="padding-right: 3px;">SUAT</th>
				<th>Quantidade</th>
				<th>Pr&ecirc;mio</th>
			</tr>
		</thead>
		<tbody>
		<%for(int i = 0;i < listaPorAgenciaValor.size();i++){ %>
			<tr>
				<td><%=listaPorAgenciaValor.get(i).getCodAgencia()%></td>
				<td><%=listaPorAgenciaValor.get(i).getAgencia()%></td>
				<td><%=listaPorAgenciaValor.get(i).getProduto()%></td>
				<%if(listaPorAgenciaValor.get(i).getSR() == null){ %>
				<td>Vazio</td>
				<%}else{ %>
				<td><%=listaPorAgenciaValor.get(i).getSR()%></td>
				<%} %>
				<%if(listaPorAgenciaValor.get(i).getSUAT() == null){ %>
				<td>Vazio</td>
				<%}else{ %>
				<td><%=listaPorAgenciaValor.get(i).getSUAT()%></td>
				<%} %>
				<td><%=listaPorAgenciaValor.get(i).getQuantidade()%></td>
				<td style="text-align:right;padding-right: 8px;"><%=listaPorAgenciaValor.get(i).getValor()%></td>
			</tr>
		<%}%>
		</tbody>
	</table>	
	