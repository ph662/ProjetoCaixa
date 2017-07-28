<%@page import="caixa.dirid.VO.SensibilizacaoTotalizadorVO"%>
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
	Uteis uteis = new Uteis();

	List<SensibilizacaoTotalizadorVO> listaTotalizadorMensal = (List<SensibilizacaoTotalizadorVO>) request.getAttribute("listaSensibilizacaoTotalizadorRenovacaoMensal");
	
	%>
	
		
	<div style="width:70%; margin-left: 315px;">
		<div style="float:left;width:60%;">
	<center><h4>Renova&ccedil;&atilde;o + Vendas Novas - Sensibiliza&ccedil;&atilde;o</h4></center>

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
						<th>Objetivo(MPE)</th>
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
						<td><%=listaTotalizadorMensal.get(0).getMeses()[i]%></td>
						<td><%=listaTotalizadorMensal.get(1).getMeses()[i]%></td>
						<td><%=listaTotalizadorMensal.get(4).getMeses()[i]%></td>
						<td><%=listaTotalizadorMensal.get(2).getMeses()[i]%></td>
						<td><%=listaTotalizadorMensal.get(3).getMeses()[i]%></td>
						<td><%=listaTotalizadorMensal.get(5).getMeses()[i]%></td>
					</tr>
					<%
						}
					%>
					
				</tbody>
			</table>
			<br>
			<br>
		
		</div>
	</div>
