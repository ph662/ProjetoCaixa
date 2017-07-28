<%@page import="caixa.dirid.VO.SensibilizacaoTotalizadorVO"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="caixa.dirid.VO.SensibilizacaoVO"%>
<%@ page import="caixa.dirid.UTEIS.*"%>
<%@ page import="java.util.*"%>

	<center><h4>Totalizador Sensibiliza&ccedil;&atilde;o</h4></center>
		
	<%
	/***********/
	/*VARIAVEIS*/
	/***********/
	
	DecimalFormat percentForm = new DecimalFormat("#.#%");
	DecimalFormat roundForm = new DecimalFormat("0.0");
	Uteis uteis = new Uteis();
	//Recebem o objeto "VO" da servlet "MenuServlet.java"
	//List<SensibilizacaoVO> listaSensibilizacao = (List<SensibilizacaoVO>) request.getAttribute("listaSensibilizacao");
	List<SensibilizacaoTotalizadorVO> listaTotalizadorMensal = (List<SensibilizacaoTotalizadorVO>) request.getAttribute("listaSensibilizacaoTotalizadorMensal");
	List<SensibilizacaoVO> listaPeriodo = (List<SensibilizacaoVO>) request.getAttribute("listaPeriodoSensibilizacao");
	//int max = (int) request.getAttribute("paginacaoMax");
	//String categoria =  (String) request.getAttribute("categoria");
	//String mes =  (String) request.getAttribute("mes");
	//String ano =  (String) request.getAttribute("ano");
	//String codProduto = (String) request.getAttribute("codigoProduto");
	%>
	<div style="width:70%; margin-left: 315px;">
		<div style="float:left;width:60%;">
			<table class="tabelaSensibilizacao" border="0"  width="200">
				<tr>
					<td>Per&iacute;odo</td>
					<td>01/<%= listaPeriodo.get(0).getDataFim().substring(6,10)%> :: <%= listaPeriodo.get(0).getDataFim()%></td>
				</tr>
			</table>	
			<br/>

			<table class="tabelaSensibilizacao" style="margin-left: -215px;" border="1" width="900">
				<thead>
					<tr bgcolor="#C5D9F1">
						<th colspan="7">
							Mensal
						</th>
					</tr>
					<tr bgcolor="#C5D9F1">
						<th rowspan="2">M&ecirc;s Refer&ecirc;ncia</th>
						<th colspan="3">Empresarial</th>
						<th colspan="3">Residencial</th>
					</tr>
					<tr>
						<th>Objetivo</th>
						<th>Realizado</th>
						<th>% Ating</th>
						<th>Objetivo</th>
						<th>Realizado</th>
						<th>% Ating</th>
					</tr>
				</thead>
				<tbody>
					<%
						for(int i = 0; i < 12 ; i++){			
					%>
					<tr>
						<td><%=uteis.mesesPaginaWeb()[i]%></td>
						<td><%=listaTotalizadorMensal.get(8).getMeses()[i]%></td>
						<td><%=listaTotalizadorMensal.get(2).getMeses()[i]%></td>
						<td><%=listaTotalizadorMensal.get(9).getMeses()[i]%></td>
						<td><%=listaTotalizadorMensal.get(6).getMeses()[i]%></td>
						<td><%=listaTotalizadorMensal.get(3).getMeses()[i]%></td>
						<td><%=listaTotalizadorMensal.get(7).getMeses()[i]%></td>
					</tr>
					<%
						}
					%>
					
				</tbody>
			</table>
	
			<br>	
			<center><h4>Renova&ccedil;&atilde;o - Sensibiliza&ccedil;&atilde;o</h4></center>
		
			<table class="tabelaSensibilizacao" style="margin-left: -215px;" border="1" width="900">
				<thead>
					<tr bgcolor="#C5D9F1">
						<th colspan="7">
							Mensal
						</th>
					</tr>
					<tr bgcolor="#C5D9F1">
						<th rowspan="2">M&ecirc;s Refer&ecirc;ncia</th>
						<th colspan="3">Empresarial</th>
						<th colspan="3">Residencial</th>
					</tr>
					<tr>
						<th>Realizado</th>
						<th>Renova&ccedil;&atilde;o</th>
						<th>% Ating</th>
						<th>Realizado</th>
						<th>Renova&ccedil;&atilde;o</th>
						<th>% Ating</th>
					</tr>
				</thead>
				<tbody>
					<%
						for(int i = 0; i < 12 ; i++){		
					%>
					<tr>
						<td><%=uteis.mesesPaginaWeb()[i]%></td>
						<td><%=listaTotalizadorMensal.get(2).getMeses()[i]%></td>
						<td><%=listaTotalizadorMensal.get(0).getMeses()[i]%></td>
						<td><%=listaTotalizadorMensal.get(4).getMeses()[i]%></td>
						<td><%=listaTotalizadorMensal.get(3).getMeses()[i]%></td>
						<td><%=listaTotalizadorMensal.get(1).getMeses()[i]%></td>
						<td><%=listaTotalizadorMensal.get(5).getMeses()[i]%></td>
					</tr>
					<%
						}
					%>
					
				</tbody>

			</table>
				
		</div>
	</div>
