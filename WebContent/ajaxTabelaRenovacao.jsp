<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="caixa.dirid.VO.RenovacaoVO"%>
<%@ page import="caixa.dirid.UTEIS.*"%>
<%@ page import="java.util.*"%>

	<center><h4>Relat&oacute;rio de Renova&ccedil;&atilde;o</h4></center>
		
	<%
	/***********/
	/*VARIAVEIS*/
	/***********/
	
	DecimalFormat percentForm = new DecimalFormat("#.#%");
	DecimalFormat roundForm = new DecimalFormat("0.0");
	
	//Recebem o objeto "VO" da servlet "MenuServlet.java"
	List<RenovacaoVO> listaRenovacao = (List<RenovacaoVO>) request.getAttribute("listaRenovacao");
	List<RenovacaoVO> listaPorCanal = (List<RenovacaoVO>) request.getAttribute("listaCanal");
	List<RenovacaoVO> listaRenovacaoCanal = (List<RenovacaoVO>) request.getAttribute("listaRenovacaoCanal");
	%>
	
		<div id="produto">
			Produto - 1403, 1404, 1804
		</div>
		
		<div id="externo">
			<div id="um" class="interno">
				<table class="tabelaSensibilizacao" border="0" width="350">
					<thead>
						<tr bgcolor="#8DB4E2">
							<th colspan="3" style="text-align:center">Propostas</th>
						</tr>
						<tr bgcolor="#C5D9F1">
							<th>Quantidade</th>
							<th>Tipo</th>
						</tr>
					</thead>
					<tbody>
						<tr><!-- Total Geral - Vincendos Mês Referência -->
							<td><%=listaRenovacao.get(0).getValor()%></td>
							<td style="text-align:left"><%=listaRenovacao.get(0).getDescricao()%></td>
						</tr>
						<tr><!-- Geral Canceladas - Cancelamentos -->
							<td><%=listaRenovacao.get(4).getValor()%></td>
							<td style="text-align:left"><%=listaRenovacao.get(4).getDescricao()%></td>
						</tr>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
						<tr><!-- Geral Para Cancelar - Vicendos Para Renovar -->
							<td><%=listaRenovacao.get(2).getValor()%></td>
							<td style="text-align:left"><%=listaRenovacao.get(2).getDescricao()%></td>
						</tr>
						<tr><!--Propostas não geradas - Analisar no SIES-->
							<td><%=listaRenovacao.get(7).getValor()%></td>
							<td style="text-align:left"><%=listaRenovacao.get(7).getDescricao()%></td>
						</tr>
						<tr><!--Indice de Falha na Geracao da Proposta-->
							<td><%=listaRenovacao.get(10).getValor()%></td>
							<td style="text-align:left"><%=listaRenovacao.get(10).getDescricao()%></td>
						</tr>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
						<tr><!-- Propostas Geradas -->
							<td><%=listaRenovacao.get(6).getValor()%></td>
							<td style="text-align:left"><%=listaRenovacao.get(6).getDescricao()%></td>
						</tr>
						<tr><!-- Emitidos -->
							<td><%=listaRenovacao.get(8).getValor()%></td>
							<td style="text-align:left"><%=listaRenovacao.get(8).getDescricao()%></td>
						</tr>
						<tr><!-- Nao Emitidos -->
							<td><%=listaRenovacao.get(9).getValor()%></td>
							<td style="text-align:left"><%=listaRenovacao.get(9).getDescricao()%></td>
						</tr>
						<tr><!-- Indice de Renovacao -->
							<td><%=listaRenovacao.get(11).getValor()%></td>
							<td style="text-align:left"><%=listaRenovacao.get(11).getDescricao()%></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div id="dois" class="interno" style="margin-left: 4%;">
				<table class="tabelaSensibilizacao" border="0" width="350">
					<thead>
						<tr bgcolor="#8DB4E2">
							<th colspan="3" style="text-align:center">Ap&oacute;lices Emitidas - Canal de Emiss&atilde;o</th>
						</tr>
						<tr bgcolor="#C5D9F1">
							<th>Canal</th>
							<th>Quantidade</th>
							<th>Percentual</th>
						</tr>
					</thead>
					<tbody>
					<%for(int i = 0;i < listaPorCanal.size();i++){ %>
						<tr>
							<td style="text-align:left"><%=listaPorCanal.get(i).getDescricao()%></td>
							<td style="text-align:right;padding-right: 25px;"><%=listaPorCanal.get(i).getValor()%></td>
							<td style="text-align:right;padding-right: 20px;"><%=listaPorCanal.get(i).getPercentual()%></td>
						</tr>
					<%}%>
					</tbody>
				</table>
			</div>
			<br />
			<br/>
			<!-- div id="tres" class="interno" style="margin-left: 4%; margin-top: 25px;">
				<table class="tabelaSensibilizacao" border="0" width="350">
					<thead>
						<tr bgcolor="#8DB4E2">
							<th colspan="3" style="text-align:center">Propostas Geradas</th>
						</tr>
						<tr bgcolor="#C5D9F1">
							<th>Renova&ccedil;&atilde;o Canal</th>
							<th>Quantidade</th>
						</tr>
					</thead>
					<tbody>
					<%for(int i = 0;i < listaRenovacaoCanal.size();i++){ %>
						<tr>
							<td style="text-align:left"><%=listaRenovacaoCanal.get(i).getDescricao()%></td>
							<td style="text-align:right;padding-right: 25px;"><%=listaRenovacaoCanal.get(i).getValor()%></td>
						</tr>
					<%}%>
					</tbody>
				</table>
			</div-->
		</div>