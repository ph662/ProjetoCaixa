<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="caixa.dirid.VO.FatuMensalFinalVO"%>
<%@ page import="caixa.dirid.UTEIS.*"%>
<%@ page import="java.util.*"%>


				<center><h4>Detalhamento Acumulado</h4></center>
					<table class="tabelaApolice" border="1">
						<thead>
							<tr bgcolor="#8DB4E2">
								<th>&nbsp;Grupo de Produtos&nbsp;</th>
								<th>&nbsp;Real 2014&nbsp;</th>
								<th>&nbsp;Real 2015&nbsp;</th>
								<th>&nbsp;BP 2015&nbsp;</th>
								<th>&nbsp;15/14 R$&nbsp;</th>
								<th>&nbsp;15/14 %&nbsp;</th>
								<th>&nbsp;15/BP R$&nbsp;</th>
								<th>&nbsp;15/BP %&nbsp;</th>
								<th>&nbsp;% Fat.2015&nbsp;</th>
							</tr>	
						</thead>	
						<tbody>
							<%
								/***********/
								/*VARIAVEIS*/
								/***********/
	
								DecimalFormat percentForm = new DecimalFormat("#.#%");
								DecimalFormat roundForm = new DecimalFormat("0.0");
	
								//Recebem o objeto "VO" da servlet "MenuServlet.java"
								List<FatuMensalFinalVO> autoListaAcumulada = (List<FatuMensalFinalVO>) request.getAttribute("autoListaAcumulada");
								List<FatuMensalFinalVO> mrListaAcumulada = (List<FatuMensalFinalVO>) request.getAttribute("mrListaAcumulada");
	
								//criando a tabela..
								for (int i = 0; i < autoListaAcumulada.size(); i++) {
							%>
								<%if(autoListaAcumulada.get(i).getProduto().contains("Total")){ %>
									<tr bgcolor="#C5D9F1">
								<%}else{ %>
									<tr>
								<%}%>
								<td style="text-align:left"><!-- GRUPO -->
									&nbsp;<%=autoListaAcumulada.get(i).getProduto()%>&nbsp;
								</td>
								<td><!-- real 2014 -->
									&nbsp;<%=roundForm.format(Double.parseDouble(autoListaAcumulada.get(i).getFaturamento2014().replace(",", ".")) / 1000000)%>&nbsp;
								</td>
								<td><!-- real 2015 -->
									&nbsp;<%=roundForm.format(Double.parseDouble(autoListaAcumulada.get(i).getFaturamento2015().replace(",", ".")) / 1000000)%>&nbsp;
								</td>
								<td><!--BP 2015-->
									&nbsp;<%=roundForm.format(Double.parseDouble(autoListaAcumulada.get(i).getBusinessPlan2015().replace(",", ".")) / 1000000)%> &nbsp;
								</td>
								<td><!--15/14 R$-->
									&nbsp;<%=roundForm.format( Double.parseDouble(autoListaAcumulada.get(i).getReal15_14().replace(",", "."))  / 1000000)%>&nbsp;
								</td>
								<td><!--15/14 %-->
									&nbsp;<%=autoListaAcumulada.get(i).getPercent15_14().replace(",", ".")%>&nbsp;
								</td>
								<td><!--15 BP R$-->
									&nbsp;<%=roundForm.format( Double.parseDouble(autoListaAcumulada.get(i).getReal15_bp().replace(",", ".")) /1000000 )%>&nbsp;
								</td>
								<td><!--15 BP %-->
									&nbsp;<%=autoListaAcumulada.get(i).getPercent15_bp().replace(",", ".")%>&nbsp;
								</td>
								<td><!--% fatura 2015-->
									&nbsp;<%=autoListaAcumulada.get(i).getPercentFatura()%>&nbsp;
								</td>
							</tr>
	
							<%
								} //fim da parte de automoveis, partindo para os totais..
							%>
	
							<!-- TRECHO MR -->
							<%
								for (int i = 0; i < mrListaAcumulada.size(); i++) {
							%>
								<%if(mrListaAcumulada.get(i).getProduto().contains("Total")){%>
									<tr bgcolor="#C5D9F1">
								<%}else{%>
									<tr>
								<%}%>
								<td style="text-align:left"><!-- GRUPO -->
									&nbsp;<%=mrListaAcumulada.get(i).getProduto()%>&nbsp;
								</td>
								<td><!-- real 2014 -->
									&nbsp;<%=roundForm.format(Double.parseDouble(mrListaAcumulada.get(i).getFaturamento2014().replace(",",".")) / 1000000 )%>&nbsp;
								</td>
								<td><!-- real 2015 -->
									&nbsp;<%=roundForm.format(Double.parseDouble(mrListaAcumulada.get(i).getFaturamento2015().replace(",",".")) / 1000000 )%>&nbsp;
								</td>
								<td><!--BP 2015-->
									&nbsp;<%=roundForm.format(Double.parseDouble(mrListaAcumulada.get(i).getBusinessPlan2015().replace(",",".")) / 1000000)%> &nbsp;
								</td>
								<td><!--15/14 R$-->
									&nbsp;<%=roundForm.format(Double.parseDouble(mrListaAcumulada.get(i).getReal15_14().replace(",","."))  / 1000000)%>&nbsp;
								</td>
								<td><!--15/14 %-->
									&nbsp;<%=mrListaAcumulada.get(i).getPercent15_14()%>&nbsp;
								</td>
								<td><!--15 BP R$-->
									&nbsp;<%=roundForm.format( Double.parseDouble(mrListaAcumulada.get(i).getReal15_bp().replace(",",".")) /1000000 )%>&nbsp;
								</td>
								<td><!--15 BP %-->
									&nbsp;<%=mrListaAcumulada.get(i).getPercent15_bp()%>&nbsp;
								</td>
								<td><!--% fatura 2015-->
									&nbsp;<%=mrListaAcumulada.get(i).getPercentFatura()%>&nbsp;
								</td>
							</tr>
							<% 
								}
							%>
						</tbody>
					</table><br><br><br>
				<center><h4>Detalhamento Mensal</h4></center>
					<table class="tabelaApolice" border="1">
						<thead>
							<tr bgcolor="#8DB4E2">
								<th>&nbsp;Grupo de Produtos&nbsp;</th>
								<th>&nbsp;Real 2014&nbsp;</th>
								<th>&nbsp;Real 2015&nbsp;</th>
								<th>&nbsp;BP 2015&nbsp;</th>
								<th>&nbsp;15/14 R$&nbsp;</th>
								<th>&nbsp;15/14 %&nbsp;</th>
								<th>&nbsp;15/BP R$&nbsp;</th>
								<th>&nbsp;15/BP %&nbsp;</th>
								<th>&nbsp;% Fat.2015&nbsp;</th>
							</tr>	
						</thead>	
						<tbody>
							<%

								//Recebem o objeto "VO" da servlet "MenuServlet.java"
								List<FatuMensalFinalVO> autoLista = (List<FatuMensalFinalVO>) request.getAttribute("autoLista");
								List<FatuMensalFinalVO> mrLista = (List<FatuMensalFinalVO>) request.getAttribute("mrLista");
	
								//criando a tabela..
								for (int i = 0; i < autoLista.size(); i++) {
							%>
								<%if(autoLista.get(i).getProduto().contains("Total")){ %>
									<tr bgcolor="#C5D9F1">
								<%}else{ %>
									<tr>
								<%}%>
								<td style="text-align:left"><!-- GRUPO -->
									&nbsp;<%=autoLista.get(i).getProduto()%>&nbsp;
								</td>
								<td><!-- real 2014 -->
									&nbsp;<%=roundForm.format(Double.parseDouble(autoLista.get(i).getFaturamento2014().replace(",", ".")) / 1000000)%>&nbsp;
								</td>
								<td><!-- real 2015 -->
									&nbsp;<%=roundForm.format(Double.parseDouble(autoLista.get(i).getFaturamento2015().replace(",", ".")) / 1000000)%>&nbsp;
								</td>
								<td><!--BP 2015-->
									&nbsp;<%=roundForm.format(Double.parseDouble(autoLista.get(i).getBusinessPlan2015().replace(",", ".")) / 1000000)%> &nbsp;
								</td>
								<td><!--15/14 R$-->
									&nbsp;<%=roundForm.format(Double.parseDouble(autoLista.get(i).getReal15_14().replace(",", "."))  / 1000000)%>&nbsp;
								</td>
								<td><!--15/14 %-->
									&nbsp;<%=autoLista.get(i).getPercent15_14().replace(",", ".")%>&nbsp;
								</td>
								<td><!--15 BP R$-->
									&nbsp;<%=roundForm.format(Double.parseDouble(autoLista.get(i).getReal15_bp().replace(",", ".")) /1000000)%>&nbsp;
								</td>
								<td><!--15 BP %-->
									&nbsp;<%=autoLista.get(i).getPercent15_bp().replace(",", ".")%>&nbsp;
								</td>
								<td><!--% fatura 2015-->
									&nbsp;<%=autoLista.get(i).getPercentFatura()%>&nbsp;
								</td>
							</tr>
	
							<%
								} //fim da parte de automoveis, partindo para os totais..
							%>
	
							<!-- TRECHO MR -->
							<%
								for (int i = 0; i < mrLista.size(); i++) {
							%>
								<%if(mrLista.get(i).getProduto().contains("Total")){%>
									<tr bgcolor="#C5D9F1">
								<%}else{%>
									<tr>
								<%}%>
								<td style="text-align:left"><!-- GRUPO -->
									&nbsp;<%=mrLista.get(i).getProduto()%>&nbsp;
								</td>
								<td><!-- real 2014 -->
									&nbsp;<%=roundForm.format(Double.parseDouble(mrLista.get(i).getFaturamento2014().replace(",",".")) / 1000000 )%>&nbsp;
								</td>
								<td><!-- real 2015 -->
									&nbsp;<%=roundForm.format(Double.parseDouble(mrLista.get(i).getFaturamento2015().replace(",",".")) / 1000000 )%>&nbsp;
								</td>
								<td><!--BP 2015-->
									&nbsp;<%=roundForm.format(Double.parseDouble(mrLista.get(i).getBusinessPlan2015().replace(",",".")) / 1000000)%> &nbsp;
								</td>
								<td><!--15/14 R$-->
									&nbsp;<%=roundForm.format(Double.parseDouble(mrLista.get(i).getReal15_14().replace(",","."))  / 1000000)%>&nbsp;
								</td>
								<td><!--15/14 %-->
									&nbsp;<%=mrLista.get(i).getPercent15_14()%>&nbsp;
								</td>
								<td><!--15 BP R$-->
									&nbsp;<%=roundForm.format( Double.parseDouble(mrLista.get(i).getReal15_bp().replace(",",".")) /1000000 )%>&nbsp;
								</td>
								<td><!--15 BP %-->
									&nbsp;<%=mrLista.get(i).getPercent15_bp()%>&nbsp;
								</td>
								<td><!--% fatura 2015-->
									&nbsp;<%=mrLista.get(i).getPercentFatura()%>&nbsp;
								</td>
							</tr>
							<% 
								}
							%>
						</tbody>
					</table>