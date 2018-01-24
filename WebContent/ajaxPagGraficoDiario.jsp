<%@ page import="caixa.dirid.VO.RvneVO"%>
<%@ page import="caixa.dirid.VO.FaturamentoVO"%>
<%@ page import="caixa.dirid.VO.DadosDiariosVO"%>
<%@ page import="caixa.dirid.VO.MovimentoPorCanalDeVendaVO"%>
<%@ page import="caixa.dirid.VO.SinistroPendente_FaixaVO"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="caixa.dirid.UTEIS.*"%>
<%@ page import="java.util.*"%>

				<% 
				String perio = (String) request.getAttribute("periodo");
				
				if (!perio.equals(" ")){ %>
				<div class="categoria">
					<h4>Informa&ccedil;&otilde;es at&eacute; <%=perio %></h4>
				</div>
				<% } %>
				<div id="tabs">
					<ul>
						<li><a href="#tabs-1">Faturamento</a></li>
						<li><a href="#tabs-2">Pr&ecirc;mios Emitidos</a></li>
						<!-- li><a href="#tabs-3">Ticket M&eacute;dio</a></li-->
						<li><a href="#tabs-4">Pr&ecirc;mios Cancelados</a></li>
						<li><a href="#tabs-5">&Iacute;ndice de Cancelamento</a></li>
						<li><a href="#tabs-6">Sinistros</a></li>
					</ul>
					
					<div id="tabs-1">
						<div id="externo">
							<div id="escala" class="jqplot-target jqplot-axis"><b>Em milh&otilde;es R$</b></div>
							<div id="fatuMensal" class="interno">
								<div id="grafiFatuMensal" style="width:560px;height:265px;margin-left: 22px;">
								</div>

								<div style="padding-left: 0px; border-left-style: solid; border-left-width: 0px; margin-left: 22px;">
									<%
										/***********/
										/*VARIAVEIS*/
										/***********/

										//Recebem o objeto "VO" da servlet "VisaoExecutiva_Diaria_Servlet.java"
										List<FaturamentoVO> anoAtual = (List<FaturamentoVO>) request.getAttribute("faturamentoAnoAtual");
										List<FaturamentoVO> anoAnterior = (List<FaturamentoVO>) request.getAttribute("faturamentoAnoAnterior");
										List<FaturamentoVO> anoAtualDivididoPorAnoAnterior = (List<FaturamentoVO>) request.getAttribute("anoAtualDivididoPorAnterior");
										List<FaturamentoVO> anoAtualDivididoPorAnoAnteriorAcumulado = (List<FaturamentoVO>) request.getAttribute("anoAtualDivididoPorAnteriorAcumulado");
										String anoAtualTexto = (String) request.getAttribute("anoAtualParametro");
										String anoAnteriorTexto = (String) request.getAttribute("anoAnteriorParametro");
										
										List<DadosDiariosVO> dadosDiariosAnoAtual = (List<DadosDiariosVO>) request.getAttribute("dadosDiariosAnoAtual");
										List<DadosDiariosVO> dadosDiariosAnoAnterior = (List<DadosDiariosVO>) request.getAttribute("dadosDiariosAnoAnterior");
										List<DadosDiariosVO> dadosDiariosFaturamentoAnoAtual = (List<DadosDiariosVO>) request.getAttribute("dadosDiariosFaturamentoAnoAtual");
										List<DadosDiariosVO> dadosDiariosFaturamentoAnoAnterior = (List<DadosDiariosVO>) request.getAttribute("dadosDiariosFaturamentoAnoAnterior");
										
										List<SinistroPendente_FaixaVO> sinistrosPendentes_FaixaValor = (List<SinistroPendente_FaixaVO>) request.getAttribute("sinistrosPendentes_FaixaValor");
										List<SinistroPendente_FaixaVO> sinistrosPendentes_FaixaTempo = (List<SinistroPendente_FaixaVO>) request.getAttribute("sinistrosPendentes_FaixaTempo");
										
										String cinza = "#808080";
										String laranja="#E9AB74";
										String azul = "#7094CC";
									%>
									<table class = "jqplot-target jqplot-axis" style="margin-left: -40px;">
										<tbody>
											<tr>
												<td style = "border-top: 1px solid;">
													<div class="square" style="background:<%=laranja%>; margin-right:5px; ">
													</div> 
												</td>
												<td width="58" style = "border-top: 1px solid;">
													<%=anoAnteriorTexto %>
												</td>
												<%
													int i = 1;
													for(int j = 0; j < 12; j++){
												%>
														<td width="43" style="border-left: 1px solid;text-align:right" id="mensal_serie_0_point_<%=j %>">
															<%=anoAnterior.get(i).getMeses()[j]%>
															&nbsp;
														</td>   
												<%
													}
												%>
											</tr>
											<tr>
												<td>
													<div class="square" style="background:<%=azul%>;">
													</div>
												</td>
												<td>
													<%=anoAtualTexto %>
												</td>
												<%
													i = 1;
													for(int j = 0; j < 12; j++){
												%>
														<td style="border-left: 1px solid;text-align:right" id="mensal_serie_1_point_<%=j %>">
															<%=anoAtual.get(i).getMeses()[j]%>
															&nbsp;
														</td>
												<%
													} 
												%>
											</tr>
											<tr>
												<td>
													<div class="square" style="background:<%=cinza%>;">
													</div>
												</td>
												<td>
													B.P. <%=anoAtualTexto %>
												</td>
												<%
													i=0;
													for(int j = 0; j < 12; j++){ 
												%>
														<td style="border-left: 1px solid;text-align:right" id="mensal_serie_2_point_<%=j %>">
															<%=anoAtual.get(i).getMeses()[j]%>
															&nbsp;
														</td>
												<%  
													} 
												%>
											</tr>
											<tr>
												<td style = "border-bottom: 1px solid;">
												</td>
												<td style = "border-bottom: 1px solid;">
													%<%=anoAtualTexto.replace("20", "") %>/<%=anoAnteriorTexto.replace("20", "") %>
												</td>
												<%
													for(int j = 0; j < 12; j++){
												%>
														
														<%if(anoAtualDivididoPorAnoAnterior.get(0).getMeses()[j].length() <= 5){ %>
														
															<td style="border-left: 1px solid;text-align:right">
																<%=anoAtualDivididoPorAnoAnterior.get(0).getMeses()[j] %>
																&nbsp;
															</td>
														<%}else{ %>
															<td style="border-left: 1px solid;text-align:right;font-size: .65em;">
																<%=anoAtualDivididoPorAnoAnterior.get(0).getMeses()[j] %>
																&nbsp;
															</td>
														<%} %>
												<%
													}
												%>
											</tr>
										</tbody>
									</table>
								</div>
							</div> <!--  id="fatuMensal" -->

							<div id="fatuAcumulada" class="interno">
								<div id="grafiFatuAcumulada" style="width:563px;height:265px;margin-left: 0px;">
								</div>
								<%
									/***********/
									/*VARIAVEIS*/
									/***********/
									//As duas variaveis abaixo contem o acumulado do BP no indice 0 da lista 
									//e o faturamento acumulado do ano respectivo no indice 1
									List<FaturamentoVO> autoAnoAtualAcumulado = (List<FaturamentoVO>) request.getAttribute("faturaAnoAtualAcumulado");
									List<FaturamentoVO> autoAnoAnteriorAcumulado = (List<FaturamentoVO>) request.getAttribute("faturaAnoAnteriorAcumulado");

									i = 1;//FATURAMENTO ACUMULADO
								%>
								<div style="padding-left: 0px;">
									<table class = "jqplot-target jqplot-axis" style="margin-left: 39px;">
										<tbody>
											<tr>
												<% // anoAnteriorTexto
													for(int j = 0; j < 12; j++){
												%>
														<td width="43" style="border-left: 1px solid;text-align:right" id="acumulada_serie_0_point_<%=j %>">
															<%=autoAnoAnteriorAcumulado.get(i).getMeses()[j]%>
															&nbsp;
														</td>   
												<%
													}
												%>
											</tr>
											<tr>
												<% //anoAtualTexto
													for(int j = 0; j < 12; j++){
												%>
														<td style="border-left: 1px solid;text-align:right" id="acumulada_serie_1_point_<%=j %>">
															<%=autoAnoAtualAcumulado.get(i).getMeses()[j]%>
															&nbsp;
														</td>
												<%
													} 
												%>
											</tr>
											<tr>
												<%
													i=0; //indice 0 -> BP
													for(int j = 0; j < 12; j++){
												%>
														<td style="border-left: 1px solid;text-align:right" id="acumulada_serie_2_point_<%=j %>">
														<%if(request.getAttribute("titulo") == "Lot&eacute;rico" || request.getAttribute("titulo") == "CCA"){ %>
															<!-- repete o bp para todos os meses -->
															<%=anoAtual.get(i).getMeses()[0] %>
														<%}else if(request.getAttribute("titulo") == "Autom&oacute;vel"){ %>
															<%=autoAnoAtualAcumulado.get(i).getMeses()[j]%>
														<%}else{%>
															<%=autoAnoAtualAcumulado.get(i).getMeses()[j] %>
														<%} %>
															&nbsp;
														</td>
												<%  
													} 
												%>
											</tr>
											<tr>
												<%
													for(int j = 0; j < 12; j++){
												%>
														<%if(anoAtualDivididoPorAnoAnteriorAcumulado.get(0).getMeses()[j].length() <= 5){ %>
														
															<td style="border-left: 1px solid;text-align:right">
																<%=anoAtualDivididoPorAnoAnteriorAcumulado.get(0).getMeses()[j] %>
																&nbsp;
															</td>
														<%}else{ %>
															<td style="border-left: 1px solid;text-align:right;font-size: .65em;">
																<%=anoAtualDivididoPorAnoAnteriorAcumulado.get(0).getMeses()[j] %>
																&nbsp;
															</td>
														<%} %>
												<%  
													}
												%>
											</tr>
										</tbody>
									</table>
								</div>
							</div> <!-- id="fatuAcumulada" -->
						</div>

						<%
							List<FaturamentoVO> autoFatuDetalhadaAnoAtual = (List<FaturamentoVO>) request.getAttribute("faturamentoDetalhadoAnoAtual");
							List<FaturamentoVO> autoFatuDetalhadaAnoAnterior = (List<FaturamentoVO>) request.getAttribute("faturamentoDetalhadoAnoAnterior");
						%>
						
						<div id="tabelaDetalhada">
							<br/>
							<table class = "jqplot-target jqplot-axis" align="center">
								<thead style="border: 1px solid;color:black;" bgcolor="#8DB4E2">
									<tr bgcolor="#ffffff">										
										<th style="text-align: center; border-top: 1px solid #ffffff;border-right: 1px solid #ffffff;border-left: 1px solid #ffffff;border-bottom: 1px solid #000000;" colspan="14">
											<%=anoAtualTexto%>
										</th>
									</tr>
									<tr>
										<th style="text-align: center;">
											Tipo	
										</th>
										<%
											String[] mesesPagWeb = new Uteis().mesesPaginaWeb();
											for(int d = 0; d < mesesPagWeb.length; d++){
										%>
												<th style="text-align: center;" WIDTH="80">									
													<%=mesesPagWeb[d]%>
												</th>
										<%
											}
										%>
										<th style="text-align: center;" bgcolor="#c0c0c0">
											Total
										</th>
									</tr>
								</thead>
								<tbody>
									<%
										for(int k = 1; k < autoFatuDetalhadaAnoAtual.size(); k++){
									%>
											<%
												if(autoFatuDetalhadaAnoAtual.get(k).getProduto().contains("Total")){
											%>	<tr bgcolor="#c0c0c0">
													<td style="border-left: 1px solid; border-bottom: 1px solid;">
														&nbsp;<%=autoFatuDetalhadaAnoAtual.get(k).getProduto() %>&nbsp;
													</td>
													<%
														for(int j = 0; j <= 12; j++){
													%>
															
															<td style="border-left: 1px solid; border-bottom: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
																<%=autoFatuDetalhadaAnoAtual.get(k).getMeses()[j] %>
															</td>
													<%
														}
													%>
												</tr>
													<%
												} else{
													if(!autoFatuDetalhadaAnoAtual.get(k).getProduto().contains("QT")){
													%>
											<tr>
												<td style="border-left: 1px solid;padding-bottom: 2px">
													&nbsp;<%=autoFatuDetalhadaAnoAtual.get(k).getProduto().replace("EMITIDOS CANCELADOS","CANCELADO").replace("EMITIDOS RESTITUIDOS","RESTITU&Iacute;DO") %>&nbsp;
												</td>
												<%
													for(int j = 0; j <= 12; j++){
												%>
														
														<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
															<%=autoFatuDetalhadaAnoAtual.get(k).getMeses()[j] %>
														</td>
											<%
													}
											%>
											</tr>
											<%
													}
												}
											%>
									<%
										}
									%>
								</tbody>
							</table>
						</div><!-- id=tabelaDetalhada -->
						<br/>
						
						
						<% if (!(request.getAttribute("titulo").equals("DIRID") || request.getAttribute("titulo").equals("Autom&oacute;vel") || request.getAttribute("titulo").equals("Auto Tranquilo Correntista")  || request.getAttribute("titulo").equals("RD PJ") || request.getAttribute("titulo").equals("RD PF")  ) ){ %>
						<div align="center">
						
							<input type="button" value="Analítico" onclick="baixarBaseFaturamentoRo()">
						</div>
						<br/>
						<% } %>
						
						<%if (!perio.equals(" ")){ %>
						<div id="grafiFatuAcumulada_diario"  style="width:1010px;height:200px;margin-left:50px;">
						</div>
						<% } %>
						<!-- div id="tabelaAnalitica">
							<br/>
							<table class = "jqplot-target jqplot-axis" align="center">
								<thead style="border: 1px solid;color:black;" bgcolor="#8DB4E2">
									<tr bgcolor="#ffffff">										
										<th style="text-align: center; border-top: 1px solid #ffffff;border-right: 1px solid #ffffff;border-left: 1px solid #ffffff;border-bottom: 1px solid #000000;" colspan="13">
											<%=anoAtualTexto%>
										</th>
									</tr>
									<tr>
										<th style="text-align: center;">
											Tipo	
										</th>
										<%
											for(int d = 0; d < mesesPagWeb.length; d++){
										%>
												<th style="text-align: center;" WIDTH="80">									
													<%=mesesPagWeb[d]%>
												</th>
										<%
											}
										%>
									</tr>
								</thead>
								<tbody>
									<%
										for(int k = 1; k < autoFatuDetalhadaAnoAtual.size(); k++){
									%>
											<%
												if(autoFatuDetalhadaAnoAtual.get(k).getProduto().contains("Total")){
											%>	<tr bgcolor="#c0c0c0">
													<td style="border-left: 1px solid; border-bottom: 1px solid;">
														&nbsp;<%=autoFatuDetalhadaAnoAtual.get(k).getProduto() %>&nbsp;
													</td>
													<%
														for(int j = 0; j < 12; j++){
													%>
															
															<td style="border-left: 1px solid; border-bottom: 1px solid; border-right: 1px solid; text-align:right">
																&nbsp;<%=autoFatuDetalhadaAnoAtual.get(k).getMeses()[j] %>&nbsp;
															</td>
													<%
														}
													%>
												</tr>
													<%
												} else{
													if(!autoFatuDetalhadaAnoAtual.get(k).getProduto().contains("QT")){
													%>
											<tr>
												<td style="border-left: 1px solid;">
													&nbsp;<%=autoFatuDetalhadaAnoAtual.get(k).getProduto() %>&nbsp;
												</td>
												<%
													for(int j = 0; j < 12; j++){
												%>
														
														<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
															&nbsp;<%=autoFatuDetalhadaAnoAtual.get(k).getMeses()[j] %>&nbsp;
														</td>
											<%
													}
											%>
											</tr>
											<%
													}
												}
											%>
									<%
										}
									%>
								</tbody>
							</table>
						</div><!- id=tabelaDetalhada -->
					</div><!-- tabs-1 -->
				<%
					DecimalFormat roundForm = new DecimalFormat("0.0");
					DecimalFormat formMuitasCasasDecimais = new DecimalFormat("0.0000000000000000000");
				%>
					<div id="tabs-2">
					
					
					<%if (!perio.equals(" ")){ %>
						<div class="jqplot-target jqplot-title">
							<b><%=request.getAttribute("titulo")%></b> - Vis&atilde;o Di&aacute;ria
						</div>
						
						<div>
							<div id="premioEmitidoPorDia" style="width:1010px;height:200px;margin-left:50px;"></div>
						</div>
						
						<hr>
						<br>
					<%} %>
						
						<div>
							<div id="emitidos">
							
								<div class="jqplot-target jqplot-title">
									<b><%=request.getAttribute("titulo")%></b> - Vis&atilde;o Mensal
								</div>
								<br>
								<table>
									<tr>
										<td class="jqplot-target jqplot-axis" width="90%"><b>Em milh&otilde;es R$</b></td>
										<td class="jqplot-target jqplot-axis"><b>Em milhares</b></td>
									</tr>
								</table>
								<!-- div id="escal" class="jqplot-target jqplot-axis"><b>Em milh&otilde;es R$</b></div-->
								
								<div id= "legenda"  style="position: absolute;margin-left: 10px;margin-top: 100px;">
									<table class="jqplot-table-legend jqplot-target jqplot-axis">
										<tbody>
											<tr class="jqplot-table-legend">
												<td class="jqplot-table-legend jqplot-table-legend-swatch" style="text-align: center;">
													<div class="jqplot-table-legend-swatch-outline">
														<div class="jqplot-table-legend-swatch" style="border-color: rgb(233, 171, 116); background-color: rgb(233, 171, 116);">
														</div>
													</div>
												</td>
												<td class="jqplot-table-legend jqplot-table-legend-label">
													<%=anoAnteriorTexto %>
												</td>
											</tr>
											<tr class="jqplot-table-legend">
												<td class="jqplot-table-legend jqplot-table-legend-swatch" style="text-align: center; padding-top: 0px;">
													<div class="jqplot-table-legend-swatch-outline">
														<div class="jqplot-table-legend-swatch" style="border-color: rgb(63, 116, 146); background-color: rgb(63, 116, 146);">
														</div>
													</div>
												</td>
												<td class="jqplot-table-legend jqplot-table-legend-label" style="padding-top: 0px;">
													<%=anoAtualTexto %>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
								<div id="piramideEmissao" style="width:1034px;height:265px;margin-left:70px; ">
								</div>
								<br>
							
							<%
								//DecimalFormat roundForm = new DecimalFormat("0.0");
							%>
											
										
							</div> <!--  id="emitidos" -->
							
							
							<div id="detalhesPiramide" style="margin-left:65px">
								<table width="100%">
									<tr>
										<td>
											<!-- Emitidos -->
											<table class="highlighted-stats-table jqplot-target jqplot-axis">
					                			<colgroup>
					                    			<col class="label">
				                    				<col class="value">
					                			</colgroup>
								                <tbody>
								                    <tr class="tooltip-header">
								                        <td class="tooltip-header ui-corner-top" colspan="2">M&ecirc;s: <b><span class="tooltip-item tooltipMes">&nbsp;</span></b></td>
								                    </tr>
								                    <tr>
								                        <td width="340">Pr&ecirc;mios Emitidos <%=anoAnteriorTexto %>: </td>
								                        <td width="100" class="quintile-value"><span class="tooltip-item" id="tooltipEmiti<%=anoAnteriorTexto %>">&nbsp;</span></td>
								                    </tr>
								                    <tr>
								                        <td width="340"><b>Pr&ecirc;mios Emitidos <%=anoAtualTexto %></b>: </td>
								                        <td width="70" class="quintile-value"><span class="tooltip-item" id="tooltipEmiti<%=anoAtualTexto %>">&nbsp;</span></td>
								                    </tr>
													<tr>
														<td style="background-color:#ffffff;line-height:0.8;">&nbsp;</td>
														<td style="background-color:#ffffff;line-height:0.8;">&nbsp;</td>
								                    </tr>
								            		<tr>
								                        <td width="340">Total Pr&ecirc;m. Emitidos <%=anoAnteriorTexto %>: </td>
								                        <td width="100" class="quintile-value"><span class="tooltip-item" id="tooltipTotalPremEmiti<%=anoAnteriorTexto %>">&nbsp;</span></td>
								                    </tr>
								                    <tr>
								                        <td width="340"><b>Total Pr&ecirc;m. Emitidos <%=anoAtualTexto %></b>: </td>
								                        <td width="70" class="quintile-value"><span class="tooltip-item" id="tooltipTotalPremEmiti<%=anoAtualTexto %>">&nbsp;</span></td>
								                    </tr>
								                </tbody>
							                </table>
										</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>
											<!-- TicketMedio -->
											<table class="highlighted-stats-table jqplot-target jqplot-axis">
					                			<colgroup>
					                    			<col class="label">
				                    				<col class="value">
					                			</colgroup>
								                <tbody>
								                    <tr class="tooltip-header">
								                        <td class="tooltip-header ui-corner-top" colspan="2">M&ecirc;s: <b><span class="tooltip-item tooltipMes">&nbsp;</span></b></td>
								                    </tr>
								                    <tr>
								                        <td width="340">Ticket M&eacute;dio <%=anoAnteriorTexto %>: </td>
								                        <td width="100" class="quintile-value"><span class="tooltip-item" id="tooltipTicketMedio<%=anoAnteriorTexto %>">&nbsp;</span></td>
								                    </tr>
								                    <tr>
								                        <td width="340"><b>Ticket M&eacute;dio <%=anoAtualTexto %></b>: </td>
								                        <td class="quintile-value"><span class="tooltip-item" id="tooltipTicketMedio<%=anoAtualTexto %>">&nbsp;</span></td>
								                    </tr>
													<tr>
														<td style="background-color:#ffffff;line-height:0.8;">&nbsp;</td>
														<td style="background-color:#ffffff;line-height:0.8;">&nbsp;</td>
								                    </tr>
								            		<tr>
								                        <td width="340">Ticket M&eacute;dio Totais <%=anoAnteriorTexto %>: </td>
								                        <td width="100" class="quintile-value"><span class="tooltip-item" id="tooltipTicketMedioTotais<%=anoAnteriorTexto %>">&nbsp;</span></td>
								                    </tr>
								                    <tr>
								                        <td width="340"><b>Ticket M&eacute;dio Totais <%=anoAtualTexto %></b>: </td>
								                        <td width="70" class="quintile-value"><span class="tooltip-item" id="tooltipTicketMedioTotais<%=anoAtualTexto %>">&nbsp;</span></td>
								                    </tr>
								                </tbody>
							                </table>
										</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td>
											<!-- NumeroApolicesEmitidas -->
											<table class="highlighted-stats-table jqplot-target jqplot-axis">
								                <colgroup>
								                    <col class="label">
								                    <col class="value">
								                </colgroup>
								                <tbody>
								                    <tr class="tooltip-header">
								                        <td class="tooltip-header ui-corner-top" colspan="2">M&ecirc;s: <b><span class="tooltip-item tooltipMes">&nbsp;</span></b></td>
								                    </tr>
								                    <tr>
								                        <td width="350">Ap&oacute;lices Emitidas <%=anoAnteriorTexto %>: </td>
								                        <td width="70" class="quintile-value"><span class="tooltip-item" id="tooltipApol<%=anoAnteriorTexto %>">&nbsp;</span></td>
								                    </tr>
								                    <tr>
								                        <td width="350"><b>Ap&oacute;lices Emitidas <%=anoAtualTexto %></b>: </td>
								                         <td width="70" class="quintile-value"><b><span class="tooltip-item" id="tooltipApol<%=anoAtualTexto %>">&nbsp;</span></b></td>
								                    </tr>
								                    <tr>
														<td style="background-color:#ffffff;line-height:0.8;">&nbsp;</td>
														<td style="background-color:#ffffff;line-height:0.8;">&nbsp;</td>
								                    </tr>
								            		<tr>
								                        <td width="340">Total Ap&oacute;l. Emitidas <%=anoAnteriorTexto %>: </td>
								                        <td width="100" class="quintile-value"><span class="tooltip-item" id="tooltipTotalQtdApol<%=anoAnteriorTexto %>">&nbsp;</span></td>
								                    </tr>
								                    <tr>
								                        <td width="340"><b>Total Ap&oacute;l. Emitidas <%=anoAtualTexto %></b>: </td>
								                        <td width="70" class="quintile-value"><span class="tooltip-item" id="tooltipTotalQtdApol<%=anoAtualTexto %>">&nbsp;</span></td>
								                    </tr>
								                </tbody>
							                </table>			
										</td>
									</tr>
								</table>
								
							</div>



							<% if ( (request.getAttribute("titulo").equals("RD Equipamentos") || request.getAttribute("titulo").equals("MR Empresarial") || request.getAttribute("titulo").equals("F&aacute;cil RD")  || request.getAttribute("titulo").equals("RD Correntista") || request.getAttribute("titulo").equals("RD Exclusivo") || request.getAttribute("titulo").equals("RD Correntista") ) ) { %> 

										<hr>
										<br>
										<div class="jqplot-target jqplot-title">
											<b><%=request.getAttribute("titulo")%></b> - Emiss&atilde;o por Canal
										</div>
								
										<!-- Emissao por Canal -->
										<table class = "jqplot-target jqplot-axis" align="center" >
											<thead style="border: 1px solid;color:black;" bgcolor="#8DB4E2">
												<tr bgcolor="#ffffff">										
													<th style="text-align: center; border-top: 1px solid #ffffff;border-right: 1px solid #ffffff;border-left: 1px solid #ffffff;border-bottom: 1px solid #000000;" colspan="14">
														<%=anoAtualTexto%>
													</th>
												</tr>
												<tr>
													<th style="text-align: center;" WIDTH="100">
														Canal
													</th>
													<th style="text-align: center;">
														Tipo	
													</th>
													<%
														mesesPagWeb = new Uteis().mesesPaginaWeb();
														for(int d = 0; d < mesesPagWeb.length; d++){
													%>
															<th style="text-align: center;" WIDTH="80">									
																<%=mesesPagWeb[d]%>
															</th>
													<%
														}
													%>
												</tr>
											</thead>
											<tbody>
												<%
												
													List<MovimentoPorCanalDeVendaVO> emissaoPorCanalVenda = (List<MovimentoPorCanalDeVendaVO>) request.getAttribute("emissaoPorCanal");
												
													for(int k = 0; k < emissaoPorCanalVenda.size(); k++){
												%>
														<tr>
														
															<%
																if(k % 4 == 0){
															%>
																<td rowspan="4" style="border-left: 1px solid;border-bottom: 1px solid;padding-bottom: 2px" >
																	&nbsp;<%= emissaoPorCanalVenda.get(k).getCanalDeVenda() %>&nbsp;
																</td>
															<%
																}
															%>
															
															<%
																if(emissaoPorCanalVenda.get(k).getTipo().equals("Valor")){
															%>
																<td style="text-align:right; padding-right: 3px; padding-left: 3px;" nowrap>&nbsp;R$&nbsp;</td>
															<%
																} else if(emissaoPorCanalVenda.get(k).getTipo().equals("% Valor")){
															%>
																<td style="text-align:right; padding-right: 3px; padding-left: 3px;" nowrap>&nbsp;% R$&nbsp;</td>
															<%
																} else if(emissaoPorCanalVenda.get(k).getTipo().equals("Quantidade")){
															%>
																<td style="text-align:right; padding-right: 3px; padding-left: 3px;" nowrap>&nbsp;QTD&nbsp;</td>
															<%
																} else if(emissaoPorCanalVenda.get(k).getTipo().equals("% Quantidade")){
															%>
																<td style="border-bottom: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;" nowrap>&nbsp;% QTD&nbsp;</td>
															<%
																}
															%>
								
															<%
																for(int f = 0; f < emissaoPorCanalVenda.get(k).getMeses().length; f++){
															%>
															
																<%
																	if(k % 4 == 0){
																%>
																	<td style="border-left: 1px solid; border-right: 1px solid;border-top: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px; padding-bottom: 3px">
																		<%= emissaoPorCanalVenda.get(k).getMeses()[f] %>
																	</td>
																<%
																	} else {
																%>
																	<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px; padding-bottom: 3px">
																		<%= emissaoPorCanalVenda.get(k).getMeses()[f] %>
																	</td>
																<%
																	}
																%>
																
															<%
																}
															%>
															
														</tr>
														
												<%
													}
												%>
														<tr>
															<td style="border-left: 1px solid; border-right: 1px solid;border-top: 1px solid;"></td>
															<td style="border-left: 1px solid; border-right: 1px solid;border-top: 1px solid;"></td>
															<td style="border-left: 1px solid; border-right: 1px solid;border-top: 1px solid;"></td>
															<td style="border-left: 1px solid; border-right: 1px solid;border-top: 1px solid;"></td>
															<td style="border-left: 1px solid; border-right: 1px solid;border-top: 1px solid;"></td>
															<td style="border-left: 1px solid; border-right: 1px solid;border-top: 1px solid;"></td>
															<td style="border-left: 1px solid; border-right: 1px solid;border-top: 1px solid;"></td>
															<td style="border-left: 1px solid; border-right: 1px solid;border-top: 1px solid;"></td>
															<td style="border-left: 1px solid; border-right: 1px solid;border-top: 1px solid;"></td>
															<td style="border-left: 1px solid; border-right: 1px solid;border-top: 1px solid;"></td>
															<td style="border-left: 1px solid; border-right: 1px solid;border-top: 1px solid;"></td>
															<td style="border-left: 1px solid; border-right: 1px solid;border-top: 1px solid;"></td>
															<td style="border-left: 1px solid; border-right: 1px solid;border-top: 1px solid;"></td>
															<td style="border-left: 1px solid; border-right: 1px solid;border-top: 1px solid;"></td>
														<tr>
											</tbody>
										</table>
							<%
								}
							%>
						</div>
					</div>
					
					<!-- div id="tabs-3">
					
						<span>Em desenvolvimento</span>
					
					</div-->
					
					<div id="tabs-4">
						
						<%if (!perio.equals(" ")){ %>					
						<div class="jqplot-target jqplot-title">
							<b><%=request.getAttribute("titulo")%></b> - Vis&atilde;o Di&aacute;ria
						</div>
		
						<div id="premioCanceladodoPorDia" style="width:1010px;height:200px;margin-left:50px;"></div>
						
						<br>
						
						<div id="premioRestituidoPorDia" style="width:1010px;height:200px;margin-left:50px;"></div>
						
						<hr>
						<br>
						<% } %>
						<div>
							<div class="jqplot-target jqplot-title">
								<b><%=request.getAttribute("titulo")%></b> - Vis&atilde;o Mensal
							</div>
							<br>
							<div id="escala" class="jqplot-target jqplot-axis"><b>Em milh&otilde;es R$</b></div>
							<div id="cancelados">
								<div id="grafiCancelados" style="width:1110px;height:260px;margin-left:50px;"></div>
	
								<div style="padding-left: 0px; border-left-style: solid; border-left-width: 0px; margin-left: -22px;">
									<table class = "jqplot-target jqplot-axis">
										<tbody>
											<tr>
												<td style = "border-top: 1px solid;">
													<div class="square" style="background:<%=laranja%>; margin-right:5px; "></div> 
												</td>
												<td width="89" style = "border-top: 1px solid;"><%=anoAnteriorTexto %></td>
												<%
													for(int j = 0; j < 12; j++){
												%>
														<td width="89" style="border-left: 1px solid;text-align:right">
															<%=roundForm.format(Double.parseDouble(autoFatuDetalhadaAnoAnterior.get(2).getMeses()[j].replace(".","").replace(",", ".")) / 1000000).replace(",", ".")%>
															&nbsp;&nbsp;&nbsp;
															&nbsp;&nbsp;
															&nbsp;&nbsp;
														</td>   
												<%
													}
												%>
											</tr>
											<tr>
												<td>
													<div class="square" style="background:<%=azul%>; margin-right:5px; "></div> 
												</td>
												<td width="89"><%=anoAtualTexto %></td>
												<%
													for(int j = 0; j < 12; j++){
												%>
														<td width="89" style="border-left: 1px solid;text-align:right">
															<%=roundForm.format(Double.parseDouble(autoFatuDetalhadaAnoAtual.get(2).getMeses()[j].replace(".","").replace(",", ".")) / 1000000).replace(",", ".")%>
															&nbsp;&nbsp;&nbsp;
															&nbsp;&nbsp;
															&nbsp;&nbsp;
														</td>   
												<%
													}
												%>
											</tr>
										</tbody>
									</table>
								</div>
							</div> <!-- id="cancelados" -->
							
							<% if ( (request.getAttribute("titulo").equals("RD Equipamentos") || request.getAttribute("titulo").equals("MR Empresarial") || request.getAttribute("titulo").equals("F&aacute;cil RD")  || request.getAttribute("titulo").equals("RD Correntista") || request.getAttribute("titulo").equals("RD Exclusivo") || request.getAttribute("titulo").equals("RD Correntista") ) ) { %> 

								<hr>
								<br>
								<div class="jqplot-target jqplot-title">
									<b><%=request.getAttribute("titulo")%></b> - Cancelamento por Canal
								</div>
						
								<!-- Cancelamento por Canal -->
								<table class = "jqplot-target jqplot-axis" align="center" >
									<thead style="border: 1px solid;color:black;" bgcolor="#8DB4E2">
										<tr bgcolor="#ffffff">										
											<th style="text-align: center; border-top: 1px solid #ffffff;border-right: 1px solid #ffffff;border-left: 1px solid #ffffff;border-bottom: 1px solid #000000;" colspan="14">
												<%=anoAtualTexto%>
											</th>
										</tr>
										<tr>
											<th style="text-align: center;" WIDTH="100">
												Canal
											</th>
											<th style="text-align: center;">
												Tipo	
											</th>
											<%
												mesesPagWeb = new Uteis().mesesPaginaWeb();
												for(int d = 0; d < mesesPagWeb.length; d++){
											%>
													<th style="text-align: center;" WIDTH="80">									
														<%=mesesPagWeb[d]%>
													</th>
											<%
												}
											%>
										</tr>
									</thead>
									<tbody>
										<%
										
											List<MovimentoPorCanalDeVendaVO> cancelamentoPorCanalVenda = (List<MovimentoPorCanalDeVendaVO>) request.getAttribute("cancelamentoPorCanal");
										
											for(int k = 0; k < cancelamentoPorCanalVenda.size(); k++){
										%>
												<tr>
												
													<%
														if(k % 4 == 0){
													%>
														<td rowspan="4" style="border-left: 1px solid;border-bottom: 1px solid;padding-bottom: 2px" >
															&nbsp;<%= cancelamentoPorCanalVenda.get(k).getCanalDeVenda() %>&nbsp;
														</td>
													<%
														}
													%>
													
													<%
														if(cancelamentoPorCanalVenda.get(k).getTipo().equals("Valor")){
													%>
														<td style="text-align:right; padding-right: 3px; padding-left: 3px;" nowrap>&nbsp;R$&nbsp;</td>
													<%
														} else if(cancelamentoPorCanalVenda.get(k).getTipo().equals("% Valor")){
													%>
														<td style="text-align:right; padding-right: 3px; padding-left: 3px;" nowrap>&nbsp;% R$&nbsp;</td>
													<%
														} else if(cancelamentoPorCanalVenda.get(k).getTipo().equals("Quantidade")){
													%>
														<td style="text-align:right; padding-right: 3px; padding-left: 3px;" nowrap>&nbsp;QTD&nbsp;</td>
													<%
														} else if(cancelamentoPorCanalVenda.get(k).getTipo().equals("% Quantidade")){
													%>
														<td style="border-bottom: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;" nowrap>&nbsp;% QTD&nbsp;</td>
													<%
														}
													%>
						
													<%
														for(int f = 0; f < cancelamentoPorCanalVenda.get(k).getMeses().length; f++){
													%>
													
														<%
															if(k % 4 == 0){
														%>
															<td style="border-left: 1px solid; border-right: 1px solid;border-top: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px; padding-bottom: 3px">
																<%= cancelamentoPorCanalVenda.get(k).getMeses()[f] %>
															</td>
														<%
															} else {
														%>
															<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px; padding-bottom: 3px">
																<%= cancelamentoPorCanalVenda.get(k).getMeses()[f] %>
															</td>
														<%
															}
														%>
														
													<%
														}
													%>
													
												</tr>
												
										<%
											}
										%>
												<tr>
													<td style="border-left: 1px solid; border-right: 1px solid;border-top: 1px solid;"></td>
													<td style="border-left: 1px solid; border-right: 1px solid;border-top: 1px solid;"></td>
													<td style="border-left: 1px solid; border-right: 1px solid;border-top: 1px solid;"></td>
													<td style="border-left: 1px solid; border-right: 1px solid;border-top: 1px solid;"></td>
													<td style="border-left: 1px solid; border-right: 1px solid;border-top: 1px solid;"></td>
													<td style="border-left: 1px solid; border-right: 1px solid;border-top: 1px solid;"></td>
													<td style="border-left: 1px solid; border-right: 1px solid;border-top: 1px solid;"></td>
													<td style="border-left: 1px solid; border-right: 1px solid;border-top: 1px solid;"></td>
													<td style="border-left: 1px solid; border-right: 1px solid;border-top: 1px solid;"></td>
													<td style="border-left: 1px solid; border-right: 1px solid;border-top: 1px solid;"></td>
													<td style="border-left: 1px solid; border-right: 1px solid;border-top: 1px solid;"></td>
													<td style="border-left: 1px solid; border-right: 1px solid;border-top: 1px solid;"></td>
													<td style="border-left: 1px solid; border-right: 1px solid;border-top: 1px solid;"></td>
													<td style="border-left: 1px solid; border-right: 1px solid;border-top: 1px solid;"></td>
												<tr>
									</tbody>
								</table>
							<%
								}
							%>
							
						</div>
					</div> <!--  id="tabs-4" -->
					
					
					
					<%
						List<FaturamentoVO> cancelDivididoPorEmitiAnoAtual = (List<FaturamentoVO>) request.getAttribute("canceladosDivididoPorEmitidosAnoAtual");
						List<FaturamentoVO> cancelDivididoPorEmitiAnoAnterior = (List<FaturamentoVO>) request.getAttribute("canceladosDivididoPorEmitidosAnoAnterior");
					%>
					
					<div id="tabs-5">
						<div id="representacaoCancelados">
							<div id="grafiRepresentacaoCancelados" style="width:1110px;height:260px;margin-left:50px;"></div>

							<div style="padding-left: 0px; border-left-style: solid; border-left-width: 0px; margin-left: -22px;">
								<table class = "jqplot-target jqplot-axis">
									<tbody>
										<tr>
											<td style = "border-top: 1px solid;">
												<div class="square" style="background:<%=laranja%>; margin-right:5px; "></div> 
											</td>
											<td width="89" style = "border-top: 1px solid;"><%=anoAnteriorTexto %></td>
											<%
												for(int j = 0; j < 12; j++){
											%>
													<td width="89" style="border-left: 1px solid;text-align:right">
														<%=cancelDivididoPorEmitiAnoAnterior.get(0).getMeses()[j]%>
														&nbsp;&nbsp;&nbsp;
														&nbsp;&nbsp;
														&nbsp;&nbsp;
													</td>   
											<%
												}
											%>
										</tr>
										<tr>
											<td>
												<div class="square" style="background:<%=azul%>; margin-right:5px; "></div> 
											</td>
											<td width="89"><%=anoAtualTexto %></td>
											<%
												for(int j = 0; j < 12; j++){
											%>
													<td width="89" style="border-left: 1px solid;text-align:right">
														<%=cancelDivididoPorEmitiAnoAtual.get(0).getMeses()[j]%>
														&nbsp;&nbsp;&nbsp;
														&nbsp;&nbsp;
														&nbsp;&nbsp;
													</td>   
											<%
												}
											%>
										</tr>
									</tbody>
								</table>
							</div>
							<br>
							<div style="padding-left: 0px; border-left-style: solid; border-left-width: 0px; margin-left: -22px;">
							<span class = "jqplot-target jqplot-axis">&nbsp;&nbsp;&nbsp;Metodologia: O &iacute;ndice de cancelamento &eacute; calculado pela divis&atilde;o entre os valores de pr&ecirc;mios cancelados e pr&ecirc;mios emitidos. O &iacute;ndice representa o quanto de nossa emiss&atilde;o est&aacute; sendo cancelada. </span>
							</div>
						</div> <!-- id="representacao cancelados" -->
					</div> <!--  id="tabs-5" -->
					
					<div id="tabs-6">
						<!--h1>Em constru&ccedil;&atilde;o</h1 -->
					
					
					<%
						List<FaturamentoVO> analiseSinistrosAvisados = (List<FaturamentoVO>) request.getAttribute("analiseSinistrosAvisados");
						List<FaturamentoVO> analiseSinistrosIndenizados = (List<FaturamentoVO>) request.getAttribute("analiseSinistrosIndenizados");
						List<FaturamentoVO> analiseSinistrosPendentes = (List<FaturamentoVO>) request.getAttribute("analiseSinistrosPendentes");
						List<FaturamentoVO> analiseSinistrosDespesas = (List<FaturamentoVO>) request.getAttribute("analiseSinistrosDespesas");
						
						short qtdAnoAnteri = 2, qtdAnoAtual = 0, variQtd = 4, vlrAnoAnteri = 3, vlrAnoAtual = 1, variVlr = 5, avisoMedAnoAtual = 6, avisoMedAnoAnteri = 7, variAvisMed = 8;
						
					%>
					
					
						<% if (!(request.getAttribute("titulo").equals("DIRID") || request.getAttribute("titulo").equals("Autom&oacute;vel")  ) ){ %>
							<div align="right">
								<input type="button" value="Analítico Sinistros" onclick="baixarBaseSinistrosCompleta();">
							</div>
						<% } %>					
						<div id="tabelaSinistroAvisado">
							<div class="jqplot-title" style="font-family:'Trebuchet MS',Arial,Helvetica,sans-serif; position: relative; color:#666; top: 0px; left: 0px; width: 100%; text-align: center;"><%=request.getAttribute("titulo")%> - Sinistros Avisados</div>
							
							<table class = "jqplot-target jqplot-axis" align="center">
								<thead style="border: 1px solid;color:black;" bgcolor="#8DB4E2">
									<tr>
									
										<th style="text-align: center;white-space: nowrap;">
											Tipo	
										</th>
										<th style="text-align: center;">
											Ano	
										</th>
										<%
											for(int d = 0; d < mesesPagWeb.length; d++){
										%>
												<th style="text-align: center;" WIDTH="80">									
													<%=mesesPagWeb[d]%>
												</th>
										<%
											}
										%>
										<th style="text-align: center;" WIDTH="80" bgcolor="#c0c0c0">									
											Total
										</th>
										
									</tr>
								</thead>
								<tbody>
									<tr>
										<td style="border-left: 1px solid;white-space: nowrap;" rowspan="2">
											&nbsp;<%=analiseSinistrosAvisados.get(qtdAnoAnteri).getProduto() %>&nbsp;
										</td>
										<td style="border-left: 1px solid;">
											&nbsp;<%=analiseSinistrosAvisados.get(qtdAnoAnteri).getAno() %>&nbsp;
										</td>
										<%
											for(int j = 0; j <= 12; j++){
										%>
												
												<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosAvisados.get(qtdAnoAnteri).getMeses()[j] %>
												</td>
										<%
											}
										%>
									</tr>
									
									<tr>
										<td style="border-left: 1px solid;">
											&nbsp;<%=analiseSinistrosAvisados.get(qtdAnoAtual).getAno() %>&nbsp;
										</td>
										<%
											for(int j = 0; j <= 12; j++){
										%>
												
												<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosAvisados.get(qtdAnoAtual).getMeses()[j] %>
												</td>
										<%
											}
										%>
									</tr>
									
									
									<tr>
										<td style="border-left: 1px solid;" bgcolor="#c0c0c0">
											&nbsp;<%=analiseSinistrosAvisados.get(variQtd).getProduto() %>&nbsp;
										</td>
										<td style="border-left: 1px solid;" bgcolor="#c0c0c0">
											&nbsp;%&nbsp;
										</td>
										<%
											for(int j = 0; j <= 12; j++){
										%>
												
												<td bgcolor="#c0c0c0" style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosAvisados.get(variQtd).getMeses()[j] %>
												</td>
										<%
											}
										%>
									</tr>
									
									<tr><td>&nbsp;</td></tr>
									
									<tr>
										<td style="text-align: center;border-left: 1px solid;white-space: nowrap;" rowspan="2">
											&nbsp;<%=analiseSinistrosAvisados.get(vlrAnoAnteri).getProduto() %>&nbsp;
										</td>
										<td style="border-left: 1px solid;">
											&nbsp;<%=analiseSinistrosAvisados.get(vlrAnoAnteri).getAno() %>&nbsp;
										</td>
										<%
											for(int j = 0; j <= 12; j++){
										%>
												
												<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosAvisados.get(vlrAnoAnteri).getMeses()[j] %>
												</td>
										<%
											}
										%>
									</tr>
									
									
									
									<tr>
										<td style="border-left: 1px solid;">
											&nbsp;<%=analiseSinistrosAvisados.get(vlrAnoAtual).getAno() %>&nbsp;
										</td>
										<%
											for(int j = 0; j <= 12; j++){
										%>
												
												<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px; ">
													<%=analiseSinistrosAvisados.get(vlrAnoAtual).getMeses()[j] %>
												</td>
										<%
											}
										%>
									</tr>
									
									
									<tr>
										<td style="border-left: 1px solid;" bgcolor="#c0c0c0">
											&nbsp;<%=analiseSinistrosAvisados.get(variVlr).getProduto() %>&nbsp;
										</td>
										<td style="border-left: 1px solid;" bgcolor="#c0c0c0">
											&nbsp;%&nbsp;
										</td>
										<%
											for(int j = 0; j <= 12; j++){
										%>
												
												<td bgcolor="#c0c0c0" style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosAvisados.get(variVlr).getMeses()[j] %>
												</td>
										<%
											}
										%>
									</tr>
									
									<tr><td>&nbsp;</td></tr>
									
									
									
									<tr>
										<td style="border-left: 1px solid;" rowspan="2">
											&nbsp;<%=analiseSinistrosAvisados.get(avisoMedAnoAnteri).getProduto() %>&nbsp;
										</td>
										<td style="border-left: 1px solid;">
											&nbsp;<%=analiseSinistrosAvisados.get(avisoMedAnoAnteri).getAno() %>&nbsp;
										</td>
										<%
											for(int j = 0; j <= 12; j++){
										%>
												
												<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosAvisados.get(avisoMedAnoAnteri).getMeses()[j] %>
												</td>
										<%
											}
										%>
									</tr>
									
									
									<tr>
										<td style="border-left: 1px solid;">
											&nbsp;<%=analiseSinistrosAvisados.get(avisoMedAnoAtual).getAno() %>&nbsp;
										</td>
										<%
											for(int j = 0; j <= 12; j++){
										%>
												
												<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosAvisados.get(avisoMedAnoAtual).getMeses()[j] %>
												</td>
										<%
											}
										%>
									</tr>
									
									
									<tr>
										<td style="border-left: 1px solid;" bgcolor="#c0c0c0">
											&nbsp;<%=analiseSinistrosAvisados.get(variAvisMed).getProduto() %>&nbsp;
										</td>
										<td bgcolor="#c0c0c0" style="border-left: 1px solid;">
											&nbsp;%&nbsp;
										</td>
										<%
											for(int j = 0; j <= 12; j++){
										%>
												
												<td bgcolor="#c0c0c0" style="border-left: 1px solid; border-right: 1px solid; text-align:right;padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosAvisados.get(variAvisMed).getMeses()[j] %>
												</td>
										<%
											}
										%>
									</tr>
								</tbody>
							</table>
						</div><!-- id=tabelaSinistroAvisado -->





						<br/><br/>
						<div id="tabelaSinistroIndenizado">
							<div class="jqplot-title" style="font-family:'Trebuchet MS',Arial,Helvetica,sans-serif; position: relative; color:#666; top: 0px; left: 0px; width: 100%; text-align: center;"><%=request.getAttribute("titulo")%> - Sinistros Indenizados</div>
		
							<table class = "jqplot-target jqplot-axis" align="center">
								<thead style="border: 1px solid;color:black;" bgcolor="#8DB4E2">
									<tr>
										<th style="text-align: center;white-space: nowrap;">
											Tipo	
										</th>
										<th style="text-align: center;">
											Ano	
										</th>
										<%
											for(int d = 0; d < mesesPagWeb.length; d++){
										%>
												<th style="text-align: center;" WIDTH="80">									
													<%=mesesPagWeb[d]%>
												</th>
										<%
											}
										%>
										<th style="text-align: center;" WIDTH="80" bgcolor="#c0c0c0">									
											Total
										</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td style="border-left: 1px solid;white-space: nowrap;" rowspan="2">
											&nbsp;<%=analiseSinistrosIndenizados.get(qtdAnoAnteri).getProduto() %>&nbsp;
										</td>
										<td style="border-left: 1px solid;">
											&nbsp;<%=analiseSinistrosIndenizados.get(qtdAnoAnteri).getAno() %>&nbsp;
										</td>
										<%
											for(int j = 0; j <= 12; j++){
										%>
												<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosIndenizados.get(qtdAnoAnteri).getMeses()[j] %>
												</td>
										<%
											}
										%>
									</tr>
									
									<tr>
										<td style="border-left: 1px solid;">
											&nbsp;<%=analiseSinistrosIndenizados.get(qtdAnoAtual).getAno() %>&nbsp;
										</td>
										<%
											for(int j = 0; j <= 12; j++){
										%>
												
												<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosIndenizados.get(qtdAnoAtual).getMeses()[j] %>
												</td>
										<%
											}
										%>
									</tr>
									
									
									<tr>
										<td style="border-left: 1px solid;" bgcolor="#c0c0c0">
											&nbsp;<%=analiseSinistrosIndenizados.get(variQtd).getProduto() %>&nbsp;
										</td>
										<td style="border-left: 1px solid;" bgcolor="#c0c0c0">
											&nbsp;%&nbsp;
										</td>
										<%
											for(int j = 0; j <= 12; j++){
										%>
												
												<td bgcolor="#c0c0c0" style="border-left: 1px solid; border-right: 1px solid; text-align:right;padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosIndenizados.get(variQtd).getMeses()[j] %>
												</td>
										<%
											}
										%>
									</tr>
									
									<tr><td>&nbsp;</td></tr>
									
									<tr>
										<td style="text-align: center;border-left: 1px solid;white-space: nowrap;" rowspan="2">
											&nbsp;<%=analiseSinistrosIndenizados.get(vlrAnoAnteri).getProduto() %>&nbsp;
										</td>
										<td style="border-left: 1px solid;">
											&nbsp;<%=analiseSinistrosIndenizados.get(vlrAnoAnteri).getAno() %>&nbsp;
										</td>
										<%
											for(int j = 0; j <= 12; j++){
										%>
												
												<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosIndenizados.get(vlrAnoAnteri).getMeses()[j] %>
												</td>
										<%
											}
										%>
									</tr>
									
									
									
									<tr>
										<td style="border-left: 1px solid;">
											&nbsp;<%=analiseSinistrosIndenizados.get(vlrAnoAtual).getAno() %>&nbsp;
										</td>
										<%
											for(int j = 0; j <= 12; j++){
										%>
												
												<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosIndenizados.get(vlrAnoAtual).getMeses()[j] %>
												</td>
										<%
											}
										%>
									</tr>
									
									
									<tr>
										<td style="border-left: 1px solid;" bgcolor="#c0c0c0">
											&nbsp;<%=analiseSinistrosIndenizados.get(variVlr).getProduto() %>&nbsp;
										</td>
										<td style="border-left: 1px solid;" bgcolor="#c0c0c0">
											&nbsp;%&nbsp;
										</td>
										<%
											for(int j = 0; j <= 12; j++){
										%>
												
												<td bgcolor="#c0c0c0" style="border-left: 1px solid; border-right: 1px solid; text-align:right;padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosIndenizados.get(variVlr).getMeses()[j] %>
												</td>
										<%
											}
										%>
									</tr>
									
									<tr><td>&nbsp;</td></tr>
									
									
									
									<tr>
										<td style="border-left: 1px solid;" rowspan="2">
											<!-- &nbsp;<%=analiseSinistrosIndenizados.get(avisoMedAnoAnteri).getProduto() %>&nbsp;-->
											&nbsp;Indeniza&ccedil;&atilde;o M&eacute;dia&nbsp;
										</td>
										<td style="border-left: 1px solid;">
											&nbsp;<%=analiseSinistrosIndenizados.get(avisoMedAnoAnteri).getAno() %>&nbsp;
										</td>
										<%
											for(int j = 0; j <= 12; j++){
										%>
												
												<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosIndenizados.get(avisoMedAnoAnteri).getMeses()[j] %>
												</td>
										<%
											}
										%>
									</tr>
									
									
									<tr>
										<td style="border-left: 1px solid;">
											&nbsp;<%=analiseSinistrosIndenizados.get(avisoMedAnoAtual).getAno() %>&nbsp;
										</td>
										<%
											for(int j = 0; j <= 12; j++){
										%>
												
												<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosIndenizados.get(avisoMedAnoAtual).getMeses()[j] %>
												</td>
										<%
											}
										%>
									</tr>
									
									
									<tr>
										<td style="border-left: 1px solid;" bgcolor="#c0c0c0">
											&nbsp;<%=analiseSinistrosIndenizados.get(variAvisMed).getProduto() %>&nbsp;
										</td>
										<td bgcolor="#c0c0c0" style="border-left: 1px solid;">
											&nbsp;%&nbsp;
										</td>
										<%
											for(int j = 0; j <= 12; j++){
										%>
												
												<td bgcolor="#c0c0c0" style="border-left: 1px solid; border-right: 1px solid; text-align:right;padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosIndenizados.get(variAvisMed).getMeses()[j] %>
												</td>
										<%
											}
										%>
									</tr>
								</tbody>
							</table>
						</div><!-- id=tabelaSinistroIndenizado -->					

						<br/><br>
						
						<div id="tabelaSinistroDespesa">
						
							<div class="jqplot-title" style="font-family:'Trebuchet MS',Arial,Helvetica,sans-serif; position: relative; color:#666; top: 0px; left: 0px; width: 100%; text-align: center;"><%=request.getAttribute("titulo")%> - Sinistros Despesas</div>
		
							<table class = "jqplot-target jqplot-axis" align="center">
								<thead style="border: 1px solid;color:black;" bgcolor="#8DB4E2">
									<tr>
										<th style="text-align: center;white-space: nowrap;">
											Tipo	
										</th>
										<th style="text-align: center;">
											Ano	
										</th>
										<%
											for(int d = 0; d < mesesPagWeb.length; d++){
										%>
												<th style="text-align: center;" WIDTH="80">									
													<%=mesesPagWeb[d]%>
												</th>
										<%
											}
										%>
										<th style="text-align: center;" WIDTH="80" bgcolor="#c0c0c0">									
											Total
										</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td style="border-left: 1px solid;white-space: nowrap;" rowspan="2">
											&nbsp;<%=analiseSinistrosDespesas.get(qtdAnoAnteri).getProduto() %>&nbsp;
										</td>
										<td style="border-left: 1px solid;">
											&nbsp;<%=analiseSinistrosDespesas.get(qtdAnoAnteri).getAno() %>&nbsp;
										</td>
										<%
											for(int j = 0; j <= 12; j++){
										%>
												
												<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosDespesas.get(qtdAnoAnteri).getMeses()[j] %>
												</td>
										<%
											}
										%>
									</tr>
									
									<tr>
										<td style="border-left: 1px solid;">
											&nbsp;<%=analiseSinistrosDespesas.get(qtdAnoAtual).getAno() %>&nbsp;
										</td>
										<%
											for(int j = 0; j <= 12; j++){
										%>
												
												<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosDespesas.get(qtdAnoAtual).getMeses()[j] %>
												</td>
										<%
											}
										%>
									</tr>
									
									
									<tr>
										<td style="border-left: 1px solid;" bgcolor="#c0c0c0">
											&nbsp;<%=analiseSinistrosDespesas.get(variQtd).getProduto() %>&nbsp;
										</td>
										<td style="border-left: 1px solid;" bgcolor="#c0c0c0">
											&nbsp;%&nbsp;
										</td>
										<%
											for(int j = 0; j <= 12; j++){
										%>
												
												<td bgcolor="#c0c0c0" style="border-left: 1px solid; border-right: 1px solid; text-align:right;padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosDespesas.get(variQtd).getMeses()[j] %>
												</td>
										<%
											}
										%>
									</tr>
									
									<tr><td>&nbsp;</td></tr>
									
									<tr>
										<td style="text-align: center;border-left: 1px solid;white-space: nowrap;" rowspan="2">
											&nbsp;<%=analiseSinistrosDespesas.get(vlrAnoAnteri).getProduto() %>&nbsp;
										</td>
										<td style="border-left: 1px solid;">
											&nbsp;<%=analiseSinistrosDespesas.get(vlrAnoAnteri).getAno() %>&nbsp;
										</td>
										<%
											for(int j = 0; j <= 12; j++){
										%>
												
												<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosDespesas.get(vlrAnoAnteri).getMeses()[j] %>
												</td>
										<%
											}
										%>
									</tr>
									
									
									
									<tr>
										<td style="border-left: 1px solid;">
											&nbsp;<%=analiseSinistrosDespesas.get(vlrAnoAtual).getAno() %>&nbsp;
										</td>
										<%
											for(int j = 0; j <= 12; j++){
										%>
												
												<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosDespesas.get(vlrAnoAtual).getMeses()[j] %>
												</td>
										<%
											}
										%>
									</tr>
									
									
									<tr>
										<td style="border-left: 1px solid;" bgcolor="#c0c0c0">
											&nbsp;<%=analiseSinistrosDespesas.get(variVlr).getProduto() %>&nbsp;
										</td>
										<td style="border-left: 1px solid;" bgcolor="#c0c0c0">
											&nbsp;%&nbsp;
										</td>
										<%
											for(int j = 0; j <= 12; j++){
										%>
												
												<td bgcolor="#c0c0c0" style="border-left: 1px solid; border-right: 1px solid; text-align:right;padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosDespesas.get(variVlr).getMeses()[j] %>
												</td>
										<%
											}
										%>
									</tr>
									
									<!-- tr><td>&nbsp;</td></tr>
									
									
									
									<tr>
										<td style="border-left: 1px solid;" rowspan="2">
											&nbsp;<%=analiseSinistrosDespesas.get(avisoMedAnoAnteri).getProduto() %>&nbsp;
										</td>
										<td style="border-left: 1px solid;">
											&nbsp;<%=analiseSinistrosDespesas.get(avisoMedAnoAnteri).getAno() %>&nbsp;
										</td>
										<%
											for(int j = 0; j < 12; j++){
										%>
												
												<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													&nbsp;<%=analiseSinistrosDespesas.get(avisoMedAnoAnteri).getMeses()[j] %>&nbsp;
												</td>
										<%
											}
										%>
									</tr>
									
									
									<tr>
										<td style="border-left: 1px solid;">
											&nbsp;<%=analiseSinistrosDespesas.get(avisoMedAnoAtual).getAno() %>&nbsp;
										</td>
										<%
											for(int j = 0; j < 12; j++){
										%>
												
												<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													&nbsp;<%=analiseSinistrosDespesas.get(avisoMedAnoAtual).getMeses()[j] %>&nbsp;
												</td>
										<%
											}
										%>
									</tr>
									
									
									<tr>
										<td style="border-left: 1px solid;" bgcolor="#c0c0c0">
											&nbsp;<%=analiseSinistrosDespesas.get(variAvisMed).getProduto() %>&nbsp;
										</td>
										<td bgcolor="#c0c0c0" style="border-left: 1px solid;">
											&nbsp;%&nbsp;
										</td>
										<%
											for(int j = 0; j < 12; j++){
										%>
												
												<td bgcolor="#c0c0c0" style="border-left: 1px solid; border-right: 1px solid; text-align:right">
													&nbsp;<%=analiseSinistrosDespesas.get(variAvisMed).getMeses()[j] %>&nbsp;
												</td>
										<%
											}
										%>
									</tr -->
								</tbody>
							</table>
						</div><!-- id=tabelaSinistroDespesas -->					
						
						<br/>
						<br/>
						
						<div id="tabelaSinistroPendente">
						
							<div class="jqplot-title" style="font-family:'Trebuchet MS',Arial,Helvetica,sans-serif; position: relative; color:#666; top: 0px; left: 0px; width: 100%; text-align: center;"><%=request.getAttribute("titulo")%> - Sinistros Pendentes</div>
		
							<table class = "jqplot-target jqplot-axis" align="center">
								<thead style="border: 1px solid;color:black;" bgcolor="#8DB4E2">
									<tr>
										<th style="text-align: center;white-space: nowrap;">
											Tipo	
										</th>
										<th style="text-align: center;">
											Ano	
										</th>
										<%
											for(int d = 0; d < mesesPagWeb.length; d++){
										%>
												<th style="text-align: center;" WIDTH="80">									
													<%=mesesPagWeb[d]%>
												</th>
										<%
											}
										%>
										<th style="text-align: center;" WIDTH="80" bgcolor="#c0c0c0">									
											Total
										</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td style="border-left: 1px solid;white-space: nowrap;" rowspan="2">
											&nbsp;<%=analiseSinistrosPendentes.get(qtdAnoAnteri).getProduto() %>&nbsp;
										</td>
										<td style="border-left: 1px solid;">
											&nbsp;<%=analiseSinistrosPendentes.get(qtdAnoAnteri).getAno() %>&nbsp;
										</td>
										<%
											for(int j = 0; j <= 12; j++){
												
												if (j == 12) {
										%>
												<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosPendentes.get(qtdAnoAnteri).getMeses()[Integer.parseInt(new Uteis().cortaData(perio,2))-1] %>
												</td>
										<%
												} else {
										%>
												<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosPendentes.get(qtdAnoAnteri).getMeses()[j] %>
												</td>
										<%
												}
											}
										%>
									</tr>
									
									<tr>
										<td style="border-left: 1px solid;">
											&nbsp;<%=analiseSinistrosPendentes.get(qtdAnoAtual).getAno() %>&nbsp;
										</td>
										<%
											for(int j = 0; j <= 12; j++){
												
												if (j == 12) {
										%>
												<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosPendentes.get(qtdAnoAtual).getMeses()[Integer.parseInt(new Uteis().cortaData(perio,2))-1] %>
												</td>
										<%
												} else {
										%>
												<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosPendentes.get(qtdAnoAtual).getMeses()[j] %>
												</td>
										<%
												}
											}
										%>
									</tr>
									
									<tr>
										<td style="border-left: 1px solid;" bgcolor="#c0c0c0">
											&nbsp;<%=analiseSinistrosPendentes.get(variQtd).getProduto() %>&nbsp;
										</td>
										<td style="border-left: 1px solid;" bgcolor="#c0c0c0">
											&nbsp;%&nbsp;
										</td>
										<%
											for(int j = 0; j <= 12; j++){
												
												if (j == 12) {
										%>
												<td bgcolor="#c0c0c0" style="border-left: 1px solid; border-right: 1px solid; text-align:right;padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosPendentes.get(variQtd).getMeses()[Integer.parseInt(new Uteis().cortaData(perio,2))-1] %>
												</td>
										<%
												} else {
										%>
												<td bgcolor="#c0c0c0" style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosPendentes.get(variQtd).getMeses()[j] %>
												</td>
										<%
												}
											}
										%>
									</tr>
									
									<tr><td>&nbsp;</td></tr>
									
									<tr>
										<td style="text-align: center;border-left: 1px solid;white-space: nowrap;" rowspan="2">
											&nbsp;<%=analiseSinistrosPendentes.get(vlrAnoAnteri).getProduto() %>&nbsp;
										</td>
										<td style="border-left: 1px solid;">
											&nbsp;<%=analiseSinistrosPendentes.get(vlrAnoAnteri).getAno() %>&nbsp;
										</td>
										<%
											for(int j = 0; j <= 12; j++){
												
												if (j == 12) {
										%>
												<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosPendentes.get(vlrAnoAnteri).getMeses()[Integer.parseInt(new Uteis().cortaData(perio,2))-1] %>
												</td>
										<%
												} else {
										%>
												<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosPendentes.get(vlrAnoAnteri).getMeses()[j] %>
												</td>
										<%
												}
											}
										%>
									</tr>
									
									<tr>
										<td style="border-left: 1px solid;">
											&nbsp;<%=analiseSinistrosPendentes.get(vlrAnoAtual).getAno() %>&nbsp;
										</td>
										<%
											for(int j = 0; j <= 12; j++){
												
												if (j == 12) {
										%>
												<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosPendentes.get(vlrAnoAtual).getMeses()[Integer.parseInt(new Uteis().cortaData(perio,2))-1] %>
												</td>
										<%
												} else {
										%>
												<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosPendentes.get(vlrAnoAtual).getMeses()[j] %>
												</td>
										<%
												}
											}
										%>
									</tr>
									
									<tr>
										<td style="border-left: 1px solid;" bgcolor="#c0c0c0">
											&nbsp;<%=analiseSinistrosPendentes.get(variVlr).getProduto() %>&nbsp;
										</td>
										<td style="border-left: 1px solid;" bgcolor="#c0c0c0">
											&nbsp;%&nbsp;
										</td>
										<%
											for(int j = 0; j <= 12; j++){
												
												if (j == 12) {
										%>
												<td bgcolor="#c0c0c0" style="border-left: 1px solid; border-right: 1px solid; text-align:right;padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosPendentes.get(variVlr).getMeses()[Integer.parseInt(new Uteis().cortaData(perio,2))-1] %>
												</td>
										<%
												} else {
										%>
												<td bgcolor="#c0c0c0" style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													<%=analiseSinistrosPendentes.get(variVlr).getMeses()[j] %>
												</td>
										<%
												}
											}
										%>
									</tr>
									
									<!-- tr><td>&nbsp;</td></tr>
									
									
									
									<tr>
										<td style="border-left: 1px solid;" rowspan="2">
											&nbsp;<%=analiseSinistrosPendentes.get(avisoMedAnoAnteri).getProduto() %>&nbsp;
										</td>
										<td style="border-left: 1px solid;">
											&nbsp;<%=analiseSinistrosPendentes.get(avisoMedAnoAnteri).getAno() %>&nbsp;
										</td>
										<%
											for(int j = 0; j < 12; j++){
										%>
												
												<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													&nbsp;<%=analiseSinistrosPendentes.get(avisoMedAnoAnteri).getMeses()[j] %>&nbsp;
												</td>
										<%
											}
										%>
									</tr>
									
									
									<tr>
										<td style="border-left: 1px solid;">
											&nbsp;<%=analiseSinistrosPendentes.get(avisoMedAnoAtual).getAno() %>&nbsp;
										</td>
										<%
											for(int j = 0; j < 12; j++){
										%>
												
												<td style="border-left: 1px solid; border-right: 1px solid; text-align:right; padding-right: 3px; padding-left: 3px;">
													&nbsp;<%=analiseSinistrosPendentes.get(avisoMedAnoAtual).getMeses()[j] %>&nbsp;
												</td>
										<%
											}
										%>
									</tr>
									
									
									<tr>
										<td style="border-left: 1px solid;" bgcolor="#c0c0c0">
											&nbsp;<%=analiseSinistrosPendentes.get(variAvisMed).getProduto() %>&nbsp;
										</td>
										<td bgcolor="#c0c0c0" style="border-left: 1px solid;">
											&nbsp;%&nbsp;
										</td>
										<%
											for(int j = 0; j < 12; j++){
										%>
												
												<td bgcolor="#c0c0c0" style="border-left: 1px solid; border-right: 1px solid; text-align:right">
													&nbsp;<%=analiseSinistrosPendentes.get(variAvisMed).getMeses()[j] %>&nbsp;
												</td>
										<%
											}
										%>
									</tr -->
								</tbody>
							</table>
						</div><!-- id=tabelaSinistroPendentes -->					
						
						
						<% if (!perio.equals(" ")){ //usado apenas nos dados diarios%>
						
						<br>
						
						<div id="tabelaPendenteFaixa">
				
							<table class = "jqplot-target jqplot-axis" align="center" style="left: 62px;" >
								<thead style="border: 1px solid;color:black;" bgcolor="#8DB4E2">
								
									<tr>
										<th style="text-align: center;white-space: nowrap;" rowspan=3>
											&nbsp;&nbsp;&nbsp;PRODUTO / FAIXA VALOR&nbsp;&nbsp;&nbsp;
										</th>
										<th style="text-align: center;white-space: nowrap; border-left: 1px solid;" rowspan=2 colspan=4>
											Pend. Administrativo
										</th>
										<th style="text-align: center; border-left: 1px solid;" rowspan=2 colspan=4>
											Pend. Judicial
										</th>
										<th style="text-align: center; border-left: 1px solid;border-right: 1px solid;" rowspan=2 colspan=4>
											Total Geral
										</th>
										
										<th style="border-top: 1px solid #ffffff ;border-right: 1px solid #ffffff;" bgcolor="#ffffff">&nbsp;</th>
									</tr>
									
									<tr bgcolor="#ffffff">
										<th style="border-right: 1px solid #ffffff;">&nbsp;</th>
										<th style="border-right: 1px solid #ffffff;">&nbsp;</th>
										<th style="border-right: 1px solid #ffffff;">&nbsp;</th>
										<th style="border-right: 1px solid #ffffff;">&nbsp;</th>
										<th style="border-right: 1px solid #ffffff;">
											<input type="button" value="Analítico Pendentes" onclick="baixarBaseSinistrosPendentes()">
										</th>
									</tr>
							
									<style>
										.borderTop th {
											border-right: 1px solid;
											border-top: 1px solid;
											text-align: center;
										}
										
										.centroText td {
											text-align: center;
										}
										
										.formataPosicaoDadosNumericos td {
											border-left: 1px solid; 
											white-space: nowrap;								
											border-right: 1px solid; 
											text-align:right; 
											padding-left: 10px;		
											padding-right: 3px;
										}
										
										.formataPosicaoDados > td:first-child{
											text-align:right;
										}
									</style>
									<tr class="borderTop">
										<th style="border-left: 1px solid;">&nbsp;Qtd.&nbsp;</th>
										<th>&nbsp;%&nbsp;</th>
										<th>&nbsp;Valor&nbsp;</th>
										<th>&nbsp;%&nbsp;</th>
										
										<th>&nbsp;Qtd.&nbsp;</th>
										<th>&nbsp;%&nbsp;</th>
										<th>&nbsp;Valor&nbsp;</th>
										<th>&nbsp;%&nbsp;</th>
										
										<th>&nbsp;Qtd.&nbsp;</th>
										<th>&nbsp;%&nbsp;</th>
										<th>&nbsp;Valor&nbsp;</th>
										<th>&nbsp;%&nbsp;</th>
										
									</tr>

								</thead>
								
								<tbody>
								
								<%
								String produtoLoop = "";
								for(int p = 0; p < sinistrosPendentes_FaixaValor.size(); p++){
									
									if(sinistrosPendentes_FaixaValor.get(p).getFaixa().equalsIgnoreCase("Total")){
										produtoLoop = sinistrosPendentes_FaixaValor.get(p).getGrupo();
								%>
										<tr class="centroText" bgcolor="#c0c0c0"> <!-- Total -->
											<td style="border-left: 1px solid;white-space: nowrap;">
												<%= sinistrosPendentes_FaixaValor.get(p).getGrupo() %>
											</td>
											<td style="border-left: 1px solid;white-space: nowrap;" colspan="2">
												<%= sinistrosPendentes_FaixaValor.get(p).getNumSinistrosPendentes_Administrativo() %>
											</td>
											<td style="border-left: 1px solid;white-space: nowrap;" colspan="2">
												<%= sinistrosPendentes_FaixaValor.get(p).getValorSinistrosPendentes_Administrativo() %>
											</td>
											<td style="border-left: 1px solid;white-space: nowrap;" colspan="2">
												<%= sinistrosPendentes_FaixaValor.get(p).getNumSinistrosPendentes_Judicial() %>
											</td>
											<td style="border-left: 1px solid;white-space: nowrap;" colspan="2">
												<%= sinistrosPendentes_FaixaValor.get(p).getValorSinistrosPendentes_Judicial() %>
											</td>
											<td style="border-left: 1px solid;white-space: nowrap;" colspan="2">
												<%= sinistrosPendentes_FaixaValor.get(p).getNumSinistrosPendentes_Total() %>
											</td>
											<td style="border-left: 1px solid;border-right: 1px solid;white-space: nowrap;" colspan="2">
												<%= sinistrosPendentes_FaixaValor.get(p).getValorSinistrosPendentes_Total() %>
											</td>
										</tr>
							<% 		
									}else{
							%>					
								
											<tr class="formataPosicaoDadosNumericos">
												<td style="text-align:left;">
													<%= sinistrosPendentes_FaixaValor.get(p).getFaixa().replaceAll("\\d*\\.", "")  %>
												</td>
												<td>
													<%= sinistrosPendentes_FaixaValor.get(p).getNumSinistrosPendentes_Administrativo() %>
												</td>
												<td>
													<%= sinistrosPendentes_FaixaValor.get(p).getNumPercentSinistrosPendentes_Administrativo() %>
												</td>
												<td>
													<%= sinistrosPendentes_FaixaValor.get(p).getValorSinistrosPendentes_Administrativo() %>
												</td>
												<td>
													<%= sinistrosPendentes_FaixaValor.get(p).getValorPercentSinistrosPendentes_Administrativo() %>
												</td>
												<td>
													<%= sinistrosPendentes_FaixaValor.get(p).getNumSinistrosPendentes_Judicial() %>
												</td>
												<td>
													<%= sinistrosPendentes_FaixaValor.get(p).getNumPercentSinistrosPendentes_Judicial() %>
												</td>
												<td>
													<%= sinistrosPendentes_FaixaValor.get(p).getValorSinistrosPendentes_Judicial() %>
												</td>
												<td>
													<%= sinistrosPendentes_FaixaValor.get(p).getValorPercentSinistrosPendentes_Judicial() %>
												</td>
												<td>
													<%= sinistrosPendentes_FaixaValor.get(p).getNumSinistrosPendentes_Total() %>
												</td>
												<td>
													<%= sinistrosPendentes_FaixaValor.get(p).getNumPercentSinistrosPendentes_Total() %>
												</td>
												<td>
													<%= sinistrosPendentes_FaixaValor.get(p).getValorSinistrosPendentes_Total() %>
												</td>
												<td style="border-left: 1px solid;border-right: 1px solid;white-space: nowrap;">
													<%= sinistrosPendentes_FaixaValor.get(p).getValorPercentSinistrosPendentes_Total() %>
												</td>
											</tr>								
								
							<%
									}
								}
							%>	
									
									<tr>
										<td style="border-top: 1px solid" colspan="13"></td>
									</tr>
								</tbody>
								
							</table>
							
							
							<br/>
							
							
							
							<table class = "jqplot-target jqplot-axis" align="center" >
								<thead style="border: 1px solid;color:black;" bgcolor="#8DB4E2">
								
									<tr>
										<th style="text-align: center;white-space: nowrap;" rowspan=3>
											&nbsp;&nbsp;&nbsp;PRODUTO / FAIXA TEMPO&nbsp;&nbsp;&nbsp;
										</th>
										<th style="text-align: center;white-space: nowrap; border-left: 1px solid;" rowspan=2 colspan=4>
											Pend. Administrativo
										</th>
										<th style="text-align: center; border-left: 1px solid;" rowspan=2 colspan=4>
											Pend. Judicial
										</th>
										<th style="text-align: center; border-left: 1px solid;border-right: 1px solid;" rowspan=2 colspan=4>
											Total Geral
										</th>
										
										<th style="border-top: 1px solid #ffffff ;border-right: 1px solid #ffffff;" bgcolor="#ffffff">&nbsp;</th>
									</tr>
									
									<tr bgcolor="#ffffff">
										<th style="border-right: 1px solid #ffffff;">&nbsp;</th>
									</tr>
							
									<style>
										.borderTop th {
											border-right: 1px solid;
											border-top: 1px solid;
											text-align: center;
										}
									</style>
									<tr class="borderTop">
										<th style="border-left: 1px solid;">&nbsp;Qtd.&nbsp;</th>
										<th>&nbsp;%&nbsp;</th>
										<th>&nbsp;Valor&nbsp;</th>
										<th>&nbsp;%&nbsp;</th>
										
										<th>&nbsp;Qtd.&nbsp;</th>
										<th>&nbsp;%&nbsp;</th>
										<th>&nbsp;Valor&nbsp;</th>
										<th>&nbsp;%&nbsp;</th>
										
										<th>&nbsp;Qtd.&nbsp;</th>
										<th>&nbsp;%&nbsp;</th>
										<th>&nbsp;Valor&nbsp;</th>
										<th>&nbsp;%&nbsp;</th>
										
									</tr>

								</thead>
								
								<tbody>
								
								<%
								 produtoLoop = "";
								for(int q = 0; q < sinistrosPendentes_FaixaTempo.size(); q++){
									
									if(sinistrosPendentes_FaixaTempo.get(q).getFaixa().equalsIgnoreCase("Total")){
										produtoLoop = sinistrosPendentes_FaixaTempo.get(q).getGrupo();
								%>
										<tr class="centroText" bgcolor="#c0c0c0"> <!-- Total -->
											<td style="border-left: 1px solid;white-space: nowrap;">
												<%= sinistrosPendentes_FaixaTempo.get(q).getGrupo() %>
											</td>
											<td style="border-left: 1px solid;white-space: nowrap;" colspan="2">
												<%= sinistrosPendentes_FaixaTempo.get(q).getNumSinistrosPendentes_Administrativo() %>
											</td>
											<td style="border-left: 1px solid;white-space: nowrap;" colspan="2">
												<%= sinistrosPendentes_FaixaTempo.get(q).getValorSinistrosPendentes_Administrativo() %>
											</td>
											<td style="border-left: 1px solid;white-space: nowrap;" colspan="2">
												<%= sinistrosPendentes_FaixaTempo.get(q).getNumSinistrosPendentes_Judicial() %>
											</td>
											<td style="border-left: 1px solid;white-space: nowrap;" colspan="2">
												<%= sinistrosPendentes_FaixaTempo.get(q).getValorSinistrosPendentes_Judicial() %>
											</td>
											<td style="border-left: 1px solid;white-space: nowrap;" colspan="2">
												<%= sinistrosPendentes_FaixaTempo.get(q).getNumSinistrosPendentes_Total() %>
											</td>
											<td style="border-left: 1px solid;border-right: 1px solid;white-space: nowrap;" colspan="2">
												<%= sinistrosPendentes_FaixaTempo.get(q).getValorSinistrosPendentes_Total() %>
											</td>
										</tr>
							<% 		
									}else{
							%>					
								
											<tr class="formataPosicaoDadosNumericos">
												<td style="text-align:left;">
													<%= sinistrosPendentes_FaixaTempo.get(q).getFaixa().replaceAll("\\d*\\.", "") %>
												</td>
												<td>
													<%= sinistrosPendentes_FaixaTempo.get(q).getNumSinistrosPendentes_Administrativo() %>
												</td>
												<td>
													<%= sinistrosPendentes_FaixaTempo.get(q).getNumPercentSinistrosPendentes_Administrativo() %>
												</td>
												<td>
													<%= sinistrosPendentes_FaixaTempo.get(q).getValorSinistrosPendentes_Administrativo() %>
												</td>
												<td>
													<%= sinistrosPendentes_FaixaTempo.get(q).getValorPercentSinistrosPendentes_Administrativo() %>
												</td>
												<td>
													<%= sinistrosPendentes_FaixaTempo.get(q).getNumSinistrosPendentes_Judicial() %>
												</td>
												<td>
													<%= sinistrosPendentes_FaixaTempo.get(q).getNumPercentSinistrosPendentes_Judicial() %>
												</td>
												<td>
													<%= sinistrosPendentes_FaixaTempo.get(q).getValorSinistrosPendentes_Judicial() %>
												</td>
												<td>
													<%= sinistrosPendentes_FaixaTempo.get(q).getValorPercentSinistrosPendentes_Judicial() %>
												</td>
												<td>
													<%= sinistrosPendentes_FaixaTempo.get(q).getNumSinistrosPendentes_Total() %>
												</td>
												<td>
													<%= sinistrosPendentes_FaixaTempo.get(q).getNumPercentSinistrosPendentes_Total() %>
												</td>
												<td>
													<%= sinistrosPendentes_FaixaTempo.get(q).getValorSinistrosPendentes_Total() %>
												</td>
												<td style="border-left: 1px solid;border-right: 1px solid;white-space: nowrap;">
													<%= sinistrosPendentes_FaixaTempo.get(q).getValorPercentSinistrosPendentes_Total() %>
												</td>
											</tr>								
								
							<%
									}
								}
							%>	
									
									<tr>
										<td style="border-top: 1px solid" colspan="13"></td>
									</tr>
								</tbody>
								
							</table>
						</div>
						<% } %>
					</div> <!--  id="tabs-6" -->
				</div> <!--  id="tabs" --> 


				<script class="code" type="text/javascript">

					function graficos(){
						$.jqplot.config.enablePlugins = false;
	
						<%
							i = 0;
						
							out.print("var bp = [");
							for(int j = 0; j< 12; j++){			
								out.print(anoAtual.get(i).getMeses()[j]);
								if(j == 11){
									out.print("];"); 
								}else{
									out.print(",");
								}
							}
						%>
						<%
							out.print("var var"+anoAtualTexto+" = [");
							for(int j = 0; j< 12; j++){					
								out.print(anoAtual.get(i + 1).getMeses()[j]);
								if(j == 11){
									out.print("];"); 
								}else{
									out.print(",");
								}
							}
						%>
						<%
							out.print("var var"+anoAnteriorTexto+" = [");
							for(int j = 0; j< 12; j++){				
								out.print(anoAnterior.get(i+1).getMeses()[j]);
								if(j == 11){
									out.print("];"); 
								}else{
									out.print(",");
								}
							}
						%>
						<%
							out.print("var AcumuladoBP = [");
							for(int j = 0; j< 12; j++){						
								out.print(autoAnoAtualAcumulado.get(i).getMeses()[j]);
								if(j == 11){
									out.print("];"); 
								}else{
									out.print(",");
								}
							}
						%>									
						<%
							out.print("var acumulado"+anoAtualTexto+" = [");
							for(int j = 0; j< 12; j++){						
								out.print(autoAnoAtualAcumulado.get(i+1).getMeses()[j]);
								if(j == 11){
									out.print("];"); 
								}else{
									out.print(",");
								}
							}
						%>
						<%
							out.print("var acumulado"+anoAnteriorTexto+" = [");
							for(int j = 0; j< 12; j++){						
								out.print(autoAnoAnteriorAcumulado.get(i+1).getMeses()[j]);
								if(j == 11){
									out.print("];"); 
								}else{
									out.print(",");
								}
							}
						%>
						<%
							out.print("var emitidos"+anoAnteriorTexto+" = [");
							for(int j = 11; j >= 0; j--){								
								out.print(roundForm.format(Double.parseDouble(autoFatuDetalhadaAnoAnterior.get(1).getMeses()[j].replace(".","").replace(",",".")) / 1000000).replace(",", "."));
								if(j == 0){
									out.print("];"); 
								}else{
									out.print(",");
								}
							}
						%>
						<%
							out.print("var emitidos"+anoAtualTexto+" = [");
							for(int j = 11; j >= 0; j--){						
								out.print(roundForm.format(Double.parseDouble(autoFatuDetalhadaAnoAtual.get(1).getMeses()[j].replace(".","").replace(",",".")) / 1000000).replace(",", "."));
								if(j == 0){
									out.print("];"); 
								}else{
									out.print(",");
								}
							}
						%>
						<%
							out.print("var emitidosDetalhado"+anoAnteriorTexto+" = [");
							
							double totalPremioEmitidoDetalhado = 0.0D;
							for(int j = 12; j >= 0; j--){							
								
								if(j == 12) {//total
									totalPremioEmitidoDetalhado = Double.parseDouble(autoFatuDetalhadaAnoAnterior.get(1).getMeses()[j].replace(".","").replace(",","."))/1000000; 
								} else {
									out.print(formMuitasCasasDecimais.format(Double.parseDouble(autoFatuDetalhadaAnoAnterior.get(1).getMeses()[j].replace(".","").replace(",","."))/1000000 ).replace(",", "."));
								}
								
								if(j == 0){
									out.print("];"); 
								} else if(j==12){
									out.print("");
								} else {
									out.print(",");
								}
							}
							out.print("\n\t\t\t\t\t\tvar totalPremioEmitidoDetalhado"+anoAnteriorTexto+" = ["+totalPremioEmitidoDetalhado+"];");
						%>
						<%
							out.print("var emitidosDetalhado"+anoAtualTexto+" = [");
							
							totalPremioEmitidoDetalhado = 0.0D;
							for(int j = 12; j >= 0; j--){							
								
								if(j == 12) {//total
									totalPremioEmitidoDetalhado = Double.parseDouble(autoFatuDetalhadaAnoAtual.get(1).getMeses()[j].replace(".","").replace(",","."))/1000000; 
								} else {
									out.print(formMuitasCasasDecimais.format(Double.parseDouble(autoFatuDetalhadaAnoAtual.get(1).getMeses()[j].replace(".","").replace(",","."))/1000000 ).replace(",", "."));
								}
								
								if(j == 0){
									out.print("];"); 
								} else if(j==12){
									out.print("");
								} else {
									out.print(",");
								}
							}
							out.print("\n\t\t\t\t\t\tvar totalPremioEmitidoDetalhado"+anoAtualTexto+" = ["+totalPremioEmitidoDetalhado+"];");
						%>						
						<%
							out.print("var apolicesEmitidas"+anoAtualTexto+" = [");
							
							double totalApolicesEmitidas = 0.0D;
							for(int j = 12; j >= 0; j--){
								
								if(j == 12) {//total
									totalApolicesEmitidas = Double.parseDouble(autoFatuDetalhadaAnoAtual.get(4).getMeses()[j].replace(".", "").replace(",", "."))/1000;
								} else {
									out.print(Double.parseDouble(autoFatuDetalhadaAnoAtual.get(4).getMeses()[j].replace(".", "").replace(",", "."))/1000);
								}
								
								if(j == 0) {
									out.print("];"); 
								} else if(j==12){
									out.print("");
								} else {
									out.print(",");
								}
							}
							out.print("\n\t\t\t\t\t\tvar totalApolicesEmitidas"+anoAtualTexto+" = ["+totalApolicesEmitidas+"];");
						%>
						<%
							out.print("var apolicesEmitidas"+anoAnteriorTexto+" = [");
						
							totalApolicesEmitidas = 0.0D;
							for(int j = 12; j >= 0; j--){
								
								if(j == 12) {//total
									totalApolicesEmitidas = Double.parseDouble(autoFatuDetalhadaAnoAnterior.get(4).getMeses()[j].replace(".", "").replace(",", "."))/1000;
								} else {
									out.print(Double.parseDouble(autoFatuDetalhadaAnoAnterior.get(4).getMeses()[j].replace(".", "").replace(",", "."))/1000);
								}
								
								if(j == 0) {
									out.print("];"); 
								} else if(j==12){
									out.print("");
								} else {
									out.print(",");
								}
								
							}
							out.print("\n\t\t\t\t\t\tvar totalApolicesEmitidas"+anoAnteriorTexto+" = ["+totalApolicesEmitidas+"];");
						%>
						
						<%
							out.print("var cancelados"+anoAnteriorTexto+" = [");
							for(int j = 0; j< 12; j++){						
								out.print(roundForm.format(Double.parseDouble(autoFatuDetalhadaAnoAnterior.get(2).getMeses()[j].replace(".","").replace(",",".")) / 1000000).replace(",", "."));
								if(j == 11){
									out.print("];"); 
								}else{
									out.print(",");
								}
							}
						%>
						<%
							out.print("var cancelados"+anoAtualTexto+" = [");
							for(int j = 0; j< 12; j++){					
								out.print(roundForm.format(Double.parseDouble(autoFatuDetalhadaAnoAtual.get(2).getMeses()[j].replace(".","").replace(",",".")) / 1000000).replace(",", "."));
								if(j == 11){
									out.print("];"); 
								}else{
									out.print(",");
								}
							}
						%>
						<%
							out.print("var canceladosPorEmitidos"+anoAnteriorTexto+" = [");
							for(int j = 0; j< 12; j++){					
								out.print(cancelDivididoPorEmitiAnoAnterior.get(0).getMeses()[j].replace("%",""));
								if(j == 11){
									out.print("];"); 
								}else{
									out.print(",");
								}
							}
						%>
						<%
							out.print("var canceladosPorEmitidos"+anoAtualTexto+" = [");
							for(int j = 0; j< 12; j++){						
								out.print(cancelDivididoPorEmitiAnoAtual.get(0).getMeses()[j].replace("%",""));
								if(j == 11){
									out.print("];"); 
								}else{
									out.print(",");
								}
							}
						%>

						<%
							out.print("var varPremEmitidoDiariaAnoAnterior = [");
							
							if(dadosDiariosAnoAnterior.size() != 0){
								int k = 0;
								for(int j = 0; j < dadosDiariosAnoAnterior.size(); j++){
									if(dadosDiariosAnoAnterior.get(j).getTipo().equalsIgnoreCase("EMITIDO")){
										k++;
									}
								}
								int kk = 0;
								for(int j = 0; j < dadosDiariosAnoAnterior.size(); j++){
									if(dadosDiariosAnoAnterior.get(j).getTipo().equalsIgnoreCase("EMITIDO")){
										kk++;
										out.print("[\""+dadosDiariosAnoAnterior.get(j).getAnoMesDia().replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2")+"\","+dadosDiariosAnoAnterior.get(j).getValorDoDia());
										if(kk == k){
											out.print("]];"); 
										}else{
											out.print("],");
										}
									}
								}
								if (k == 0){
									out.print("null];");
								}
							}else{
								out.print("null];");
							}
						%>
						
						<%
							out.print("var varPremEmitidoDiariaAnoAtual = [");
							
							if(dadosDiariosAnoAtual.size() != 0){
								int k = 0;
								for(int j = 0; j < dadosDiariosAnoAtual.size(); j++){
									if(dadosDiariosAnoAtual.get(j).getTipo().equalsIgnoreCase("EMITIDO")){
										k++;
									}
								}
								int kk = 0;
								for(int j = 0; j < dadosDiariosAnoAtual.size(); j++){
									if(dadosDiariosAnoAtual.get(j).getTipo().equalsIgnoreCase("EMITIDO")){
										kk++;
										out.print("[\""+dadosDiariosAnoAtual.get(j).getAnoMesDia().replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2")+"\","+dadosDiariosAnoAtual.get(j).getValorDoDia());
										if(kk == k){
											out.print("]];"); 
										}else{
											out.print("],");
										}
									}
								}
								if (k == 0){
									out.print("null];");
								}
							}else{
								out.print("null];");
							}
						%>
						
						<%
							out.print("var varPremCanceladoDiaria = [");
						
							if(dadosDiariosAnoAtual.size() != 0){
								int k1 = 0;
								for(int j = 0; j < dadosDiariosAnoAtual.size(); j++){
									if(dadosDiariosAnoAtual.get(j).getTipo().equalsIgnoreCase("EMITIDOS CANCELADOS")){
										k1++;
									}
								}
								int kk1 = 0;
								for(int j = 0; j < dadosDiariosAnoAtual.size(); j++){
									if(dadosDiariosAnoAtual.get(j).getTipo().equalsIgnoreCase("EMITIDOS CANCELADOS")){
										kk1++;
										out.print("[\""+dadosDiariosAnoAtual.get(j).getAnoMesDia().replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2")+"\","+dadosDiariosAnoAtual.get(j).getValorDoDia());
										if(kk1 == k1){
											out.print("]];"); 
										}else{
											out.print("],");
										}
									}
								}
								if (k1 == 0){
									out.print("null];");
								}
							}else{
								out.print("null];");
							}
						%>
					
						<%
							out.print("var varPremRestituidoDiaria = [");
							
							if(dadosDiariosAnoAtual.size() != 0){
								int k2 = 0;
								for(int j = 0; j < dadosDiariosAnoAtual.size(); j++){
									if(dadosDiariosAnoAtual.get(j).getTipo().equalsIgnoreCase("EMITIDOS RESTITUIDOS")){
										k2++;
									}
								}
								int kk2=0;
								for(int j = 0; j < dadosDiariosAnoAtual.size(); j++){
									if(dadosDiariosAnoAtual.get(j).getTipo().equalsIgnoreCase("EMITIDOS RESTITUIDOS")){
										kk2++;
										out.print("[\""+dadosDiariosAnoAtual.get(j).getAnoMesDia().replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2")+"\","+dadosDiariosAnoAtual.get(j).getValorDoDia());
										if(kk2 == k2){
											out.print("]];"); 
										}else{
											out.print("],");
										}
									}
								}
								if (k2 == 0){
									out.print("null];");
								}
							}else{
								out.print("null];");
							}
						%>

						<%
							out.print("var varDadosDiariosFaturamentoAnoAtual = [");
							
							if(dadosDiariosFaturamentoAnoAtual.size() != 0){
								int k = 0;
								for(int j = 0; j < dadosDiariosFaturamentoAnoAtual.size(); j++){
									if(dadosDiariosFaturamentoAnoAtual.get(j).getTipo().equalsIgnoreCase("FATURAMENTO")){
										k++;
									}
								}
								int kk = 0;
								for(int j = 0; j < dadosDiariosFaturamentoAnoAtual.size(); j++){
									if(dadosDiariosFaturamentoAnoAtual.get(j).getTipo().equalsIgnoreCase("FATURAMENTO")){
										kk++;
										out.print("[\""+dadosDiariosFaturamentoAnoAtual.get(j).getAnoMesDia().replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2")+"\","+dadosDiariosFaturamentoAnoAtual.get(j).getValorDoDia());
										if(kk == k){
											out.print("]];"); 
										}else{
											out.print("],");
										}
									}
								}
								if (k == 0){
									out.print("null];");
								}
							}else{
								out.print("null];");
							}
						%>
						<%
							out.print("var varDadosDiariosFaturamentoAnoAnterior = [");
							
							if(dadosDiariosFaturamentoAnoAnterior.size() != 0){
								int k = 0;
								for(int j = 0; j < dadosDiariosFaturamentoAnoAnterior.size(); j++){
									if(dadosDiariosFaturamentoAnoAnterior.get(j).getTipo().equalsIgnoreCase("FATURAMENTO")){
										k++;
									}
								}
								int kk = 0;
								for(int j = 0; j < dadosDiariosFaturamentoAnoAnterior.size(); j++){
									if(dadosDiariosFaturamentoAnoAnterior.get(j).getTipo().equalsIgnoreCase("FATURAMENTO")){
										kk++;
										out.print("[\""+dadosDiariosFaturamentoAnoAnterior.get(j).getAnoMesDia().replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2")+"\","+dadosDiariosFaturamentoAnoAnterior.get(j).getValorDoDia());
										if(kk == k){
											out.print("]];"); 
										}else{
											out.print("],");
										}
									}
								}
								if (k == 0){
									out.print("null];");
								}
							}else{
								out.print("null];");
							}
						%>				

						<%
							out.print("var varDadosDiarios_BP_FaturamentoAnoAtual = [");
							
							if(dadosDiariosFaturamentoAnoAtual.size() != 0){
								int k = 0;
								for(int j = 0; j < dadosDiariosFaturamentoAnoAtual.size(); j++){
									if(dadosDiariosFaturamentoAnoAtual.get(j).getTipo().equalsIgnoreCase("BP")){
										k++;
									}
								}
								int kk = 0;
								for(int j = 0; j < dadosDiariosFaturamentoAnoAtual.size(); j++){
									if(dadosDiariosFaturamentoAnoAtual.get(j).getTipo().equalsIgnoreCase("BP")){
										kk++;
										out.print("[\""+dadosDiariosFaturamentoAnoAtual.get(j).getAnoMesDia().replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2")+"\","+dadosDiariosFaturamentoAnoAtual.get(j).getValorDoDia());
										if(kk == k){
											out.print("]];"); 
										}else{
											out.print("],");
										}
									}
								}
								if (k == 0){
									out.print("null];");
								}
							}else{
								out.print("null];");
							}
						%>				
						
						
						var ticks  = ['Jan', 'Fev', 'Mar', 'Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'];
						
						<%
						String titulo = (String)request.getAttribute("titulo");
						switch (titulo){

						  case "Autom&oacute;vel":%>
						  	var ticks1 = ['0.0','10.0','20.0','30.0','45.0'];
							var ticks2 = ['0.0','100.0','200.0','300.0','400.0'];
						  	var ticks3 = ['0.0','5.0','10.0','15.0','20.0','25.0','30.0','35.0'];
							var ticks4 = ['0.0','-1.0','-2.0','-3.0','-6.0'];
							var ticks5 = ['0','-15','-30'];
						<%break;
							  case "Auto Tranquilo Exclusivo":%>
							  	var ticks1 = ['0.0','3.0','5.0','7.0','10.0','13.0'];
								var ticks2 = ['0.0','30.0','50.0','70.0','110.0'];
							  	var ticks3 = ['0.0','5.0','10.0','15.0','20.0','25.0','30.0','35.0'];
								var ticks4 = ['0.0','-0.2','-0.3','-0.4','-0.5'];
								var ticks5 = ['0','-20'];
							<%break;
							  case "Auto Tranquilo Correntista":%>
							  	var ticks1 = ['0.0','5.0','10.0','15.0','20.0','30.0'];
								var ticks2 = ['0.0','50.0','100.0','150.0','200.0','245.0'];
							  	var ticks3 = ['0.0','5.0','10.0','15.0','20.0','25.0','30.0','35.0'];
								var ticks4 = ['0.0','-0.5','-1.0','-1.5','-2.0','-2.5','-4.0'];
								var ticks5 = ['0','-15','-30'];
							<%break;
							  case "Auto Tranquilo Frota":%>
							  	var ticks1 = ['0.0','0.1','0.3'];
								var ticks2 = ['0.0','0.4','0.8'];
							  	var ticks3 = ['0.0','5.0','10.0','15.0','20.0','25.0','30.0','35.0'];
								var ticks4 = ['0.0','-1.0'];
								var ticks5 = ['0','-1100'];
							<%break;
							  case "Auto F&aacute;cil":%>
							  	var ticks1 = ['0.0','1.0'];
								var ticks2 = ['0.0','0.2','0.4'];
							  	var ticks3 = ['0.0','5.0','10.0','15.0','20.0','25.0','30.0','35.0'];
								var ticks4 = ['0.0','-0.5','-1.0','-1.5','-2.0','-2.5','-3.0','-3.5'];
								var ticks5 = ['0','-250'];
							<%break;
						
						  case "RD PJ":%>
						  	var ticks1 = ['0.0','5.0','10.0','30.0','50.0'];
							var ticks2 = ['0.0','50.0','100.0','150.0','200.0','250.0'];
						  	var ticks3 = ['0.0','5.0','10.0','15.0','20.0','25.0','30.0','35.0'];
							var ticks4 = ['0.0','-0.5','-1.0','-1.5','-2.0','-3.0','-5.0'];
							var ticks5 = ['0','-50'];
						<%break;
							  case "RD Equipamentos":%>
							  	var ticks1 = ['-0.3','0.0','0.5','0.8'];
								var ticks2 = ['0.0','0.5','1.0','2.0','3.0'];
							  	var ticks3 = ['0.0','5.0','10.0','15.0','20.0','25.0','30.0','35.0'];
								var ticks4 = ['0.0','-0.5','-1.0'];
								var ticks5 = ['0','-160'];
							<%break;
							  case "MR Empresarial":%>
							  	var ticks1 = ['0.0','5.0','10.0','23.0'];
								var ticks2 = ['0.0','50.0','100.0','150.0','200.0'];
							  	var ticks3 = ['0.0','5.0','10.0','15.0','20.0','25.0','30.0','35.0'];
								var ticks4 = ['0.0','-0.5','-1.0','-1.5','-2.0','-3.0','-5.0'];
								var ticks5 = ['0','-60'];
							<%break;
							  case "Empresarial Prazo Curto":%>
							  	var ticks1 = ['0.0','5.0','10.0','23.0'];
								var ticks2 = ['0.0','50.0','100.0','150.0','200.0'];
							  	var ticks3 = ['0.0','5.0','10.0','15.0','20.0','25.0','30.0','35.0'];
								var ticks4 = ['0.0','-0.5','-1.0','-1.5','-2.0','-3.0','-5.0'];
								var ticks5 = ['0','-60'];
							<%break;
							  case "MR CCA":%>
								var ticks1 = ['-0.3','0.0','0.5','1.0','2.0','4.0','6.0'];
								var ticks2 = ['-0.5','0.0','1.0','5.0','7.0'];
								var ticks3 = ['0.0','0.5','1.0','1.5','2.0','2.5','4.0'];
								var ticks4 = ['0.0','-0.1','-0.2','-0.3','-0.4'];
								var ticks5 = ['0','-1200'];
							<%break;
							  case "MR Lot&eacute;rico":%>
							  	var ticks1 = ['-2.0','0.0','5.0','20.0','37.0'];
								var ticks2 = ['20.0','30.0','37.0'];
								var ticks3 = ['0.0','2.5','5.0','20.0','40.0'];
								var ticks4 = ['0.0','-0.1','-0.2','-0.3','-0.4','-0.5','-0.6','-0.7','-0.8'];
								var ticks5 = ['0','-50','-100','-200','-300'];
							<%break;

						  case "RD PF":%>
						  	var ticks1 = ['0.0','5.0','10.0','15.0','20.0','30.0'];
							var ticks2 = ['0.0','50.0','100.0','150.0','200.0','230.0','270.0'];
						  	var ticks3 = ['0.0','5.0','10.0','15.0','20.0','25.0','30.0','35.0'];
							var ticks4 = ['0.0','-1.5','-3.0','-7.0','-10.0','-15.0'];
							var ticks5 = ['0','-10','-30','-100'];
 						<%break;
							  case "RD Correntista":%>
							  	var ticks1 = ['0.0','5.0','10.0','17.0','27.0'];
								var ticks2 = ['0.0','50.0','100.0','150.0','200.0','270.0'];
							  	var ticks3 = ['0.0','5.0','10.0','15.0','20.0','25.0','30.0','35.0'];
								var ticks4 = ['0.0','-2.0','-8.0'];
								var ticks5 = ['0','-120'];
							<%break;
							  case "RD Exclusivo":%>
							  	var ticks1 = ['0.0','1.0','2.0','3.0'];
								var ticks2 = ['0.0','9.0','18.0'];
							  	var ticks3 = ['0.0','5.0','10.0','15.0','20.0','25.0','30.0','35.0'];
								var ticks4 = ['0.0','-0.5'];
								var ticks5 = ['0','-20'];
							<%break;
							  case "F&aacute;cil RD":%>
							  	var ticks1 = ['0.0','0.3','0.6','1.0'];
								var ticks2 = ['0.0','0.8','1.6','2.4','3.2','5.0'];
							  	var ticks3 = ['0.0','5.0','10.0','15.0','20.0','25.0','30.0','35.0'];
								var ticks4 = ['0.0','-0.5','-1.0','-1.5','-2.0','-3.0','-5.0'];
								var ticks5 = ['0','-20'];
							<%break;
							  case "Lar Mais":%>
							  	var ticks1 = ['0.0','1.0','2.5'];
								var ticks2 = ['0.0','10.0','25.0'];
							  	var ticks3 = ['0.0','5.0','10.0','15.0','20.0','25.0','30.0','35.0'];
								var ticks4 = ['0.0','-0.5','-1.0','-1.5','-2.0','-2.5'];
								var ticks5 = ['0','-100'];
							<%break;
							  case "Aporte Caixa":%>
							  	var ticks1 = ['0.0','1.5','3.0'];
								var ticks2 = ['0.0','10.0','25.0'];
							  	var ticks3 = ['0.0','5.0','10.0','15.0','20.0','25.0','30.0','35.0'];
								var ticks4 = ['0.0','-1.0','-2.0','-3.0','-4.0'];
								var ticks5 = ['0','-100'];
							<%break;
							  case "MCMV Mais Premi&aacute;vel":%>
							  	var ticks1 = ['0.0','1.5','3.0'];
								var ticks2 = ['0.0','10.0','25.0'];
							  	var ticks3 = ['0.0','5.0','10.0','15.0','20.0','25.0','30.0','35.0'];
								var ticks4 = ['0.0','-1.0','-2.0','-3.0','-4.0'];
								var ticks5 = ['0','-100'];
							<%break;
							  case "Cibrasec Securitizadora":%>
							  	var ticks1 = ['0.0','1.5','3.0'];
								var ticks2 = ['0.0','10.0','25.0'];
							  	var ticks3 = ['0.0','5.0','10.0','15.0','20.0','25.0','30.0','35.0'];
								var ticks4 = ['0.0','-1.0','-2.0','-3.0','-4.0'];
								var ticks5 = ['0','-100'];
							<%break;
							  case "RD PF Outros":%>
							  	var ticks1 = ['0.0','0.2'];
								var ticks2 = ['0.0','0.3'];
							  	var ticks3 = ['0.0','5.0','10.0','15.0','20.0','25.0','30.0','35.0'];
								var ticks4 = ['0.0','-0.2'];
								var ticks5 = ['0','-10'];
							<%break;
						case "DIRID":%>
						  	var ticks1 = ['0.0','20.0','50.0','80.0','100.0'];
							var ticks2 = ['0.0','150.0','300.0','500.0','700.0','900.0'];
						  	var ticks3 = ['0.0','5.0','10.0','15.0','20.0','25.0','30.0','35.0'];
							var ticks4 = ['0.0','-10.0','-40.0'];
							var ticks5 = ['0','-75'];
						<%break;
						}
						%>

					
		
						
						$("#tabs").tabs();
						$("#accordion").accordion();

						plot1 = $.jqplot('grafiFatuMensal', [var<%=anoAnteriorTexto %>, var<%=anoAtualTexto %>, bp], {
							animate:true, //gráfico animado
							animateReplot: true,//gráfico animado
							
							seriesDefaults: {
								pointLabels: {
									show: false
								},
								
								rendererOptions: {
									fillToZero: true,
									barPadding: 2,
									barMargin: 2
								}
							},
							
				            series:[
			                    {
			                    	renderer: $.jqplot.BarRenderer,
			                    	label:'<%=anoAnteriorTexto %>',
			                        pointLabels: {
			                            show: false
			                        },
			                        
			                        showHighlight: true,
			                        rendererOptions: {
			                            // Speed up the animation a little bit.
			                            // This is a number of milliseconds.  
			                            // Default for bar series is 3000.  
			                            animation: {
			                                speed: 2000
			                            },
			                            barWidth: 15,
			                            barPadding: 1,
			                            barMargin: 20,
			                            highlightMouseOver: false
			                        }
			                    },  
			                    {
		                        	pointLabels: {
			                            show: false
			                        },
			                        renderer: $.jqplot.BarRenderer,
			                        label:'<%=anoAtualTexto %>',
			                        showHighlight: true,
			                        rendererOptions: {
			                            // Speed up the animation a little bit.
			                            // This is a number of milliseconds.  
			                            // Default for bar series is 3000.  
			                            animation: {
			                                speed: 2500
			                            },
			                            barWidth: 15,
			                            barPadding: -1,
			                            barMargin: 0,
			                            highlightMouseOver: false
			                        }
			                    }
			                ],
							title:'<%=request.getAttribute("titulo")%> - Faturamento Mensal',
							seriesColors:['<%=laranja%>', '<%=azul%>', '<%=cinza%>'],
							negativeSeriesColors:['<%=laranja%>', '<%=azul%>', '<%=cinza%>'],
							rendererOptions: {
								varyBarColor: true
							},
							axes: {
								xaxis: {
									renderer: $.jqplot.CategoryAxisRenderer,
									ticks: ticks
								},
								yaxis:{
									ticks: ticks1
								}
							},
							highlighter: {
								show: true, 
								showMarker:false,
								tooltipLocation: 'n',
								tooltipAxes: 'y',
							},      
							cursor:{
						          zoom:false,
						          tooltipOffset: 10,
						          tooltipLocation: 'nw'
						      }
						});
						
					<% if(request.getAttribute("titulo") == "Lot&eacute;rico" || request.getAttribute("titulo") == "CCA"){ %>
					
					<%
						i = 0;
						out.print("var bpAcumu = [");
						double val, valAnterior=0, total;
						for(int j = 0; j< 12; j++){
							val = Double.parseDouble(anoAtual.get(i).getMeses()[j]);
							if(j == 0){
								valAnterior = Double.parseDouble(anoAtual.get(i).getMeses()[j]);
								out.print(anoAtual.get(i).getMeses()[j]);
							}else {
								total = val + valAnterior;
								valAnterior = total;
								out.print(String.valueOf(total));
							}
							if(j == 11){
								out.print("];"); 
							}else{
								out.print(",");
							}
						}
					%>
					
					
					
						plot2 = $.jqplot('grafiFatuAcumulada', [acumulado<%=anoAnteriorTexto %>, acumulado<%=anoAtualTexto %>, bpAcumu],
								
					<% }else{ %>
						plot2 = $.jqplot('grafiFatuAcumulada', [acumulado<%=anoAnteriorTexto %>, acumulado<%=anoAtualTexto %>, AcumuladoBP],
								
					<% } %>
							{
							animate:true, //gráfico animado
							animateReplot: true,//gráfico animado
							
							seriesDefaults: {
								pointLabels: {
									show: false
								},
								
								rendererOptions: {
									fillToZero: true,
									barPadding: 2,
									barMargin: 2
								}
							},
							
				            series:[
			                    {
			                    	renderer: $.jqplot.BarRenderer,
			                    	label:'<%=anoAnteriorTexto %>',
			                        pointLabels: {
			                            show: false
			                        },
			                        
			                        showHighlight: true,
			                        rendererOptions: {
			                            // Speed up the animation a little bit.
			                            // This is a number of milliseconds.  
			                            // Default for bar series is 3000.  
			                            animation: {
			                                speed: 2000
			                            },
			                            barWidth: 15,
			                            barPadding: 1,
			                            barMargin: 20,
			                            highlightMouseOver: false
			                        }
			                    },  
			                    {
		                        	pointLabels: {
			                            show: false
			                        },
			                        renderer: $.jqplot.BarRenderer,
			                        label:'<%=anoAtualTexto %>',
			                        showHighlight: true,
			                        rendererOptions: {
			                            // Speed up the animation a little bit.
			                            // This is a number of milliseconds.  
			                            // Default for bar series is 3000.  
			                            animation: {
			                                speed: 2500
			                            },
			                            barWidth: 15,
			                            barPadding: -1,
			                            barMargin: 0,
			                            highlightMouseOver: false
			                        }
			                    }
			                ],
							title:'<%=request.getAttribute("titulo")%> - Faturamento Acumulado',
							seriesColors:['<%=laranja%>', '<%=azul%>', '<%=cinza%>'], 
							negativeSeriesColors:['<%=laranja%>', '<%=azul%>', '<%=cinza%>'],
							rendererOptions: {
								varyBarColor: true
							},
							axes: {
								xaxis: {
									renderer: $.jqplot.CategoryAxisRenderer,
									ticks: ticks
								},
								yaxis:{
									ticks: ticks2
								}
							},
							highlighter: {
								show: true, 
								showMarker:false,
								tooltipLocation: 'n',
								tooltipAxes: 'y',
							}
						});

						
						//plot 3 gráfico pirâmide
				       
				        var ticksMesesPiramide = ["Dezembro", "Novembro", "Outubro", "Setembro", "Agosto", "Julho", "Junho", "Maio", "Abril", "Março", "Fevereiro", "Janeiro"];
						//var ticks = ["Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"];
						
				        var blueColors = ["#3F7492", "#3F7492", "<%=laranja%>", "#f9AB68"];

						
						var plotOptions = {
				            // We set up a customized title which acts as labels for the left and right sides of the pyramid.
				            title: '<div style="float:left;width:50%;text-align:center">Valor de Pr&ecirc;mios Emitidos</div><div style="float:right;width:50%;text-align:center">Quantidade de Ap&oacute;lices Emitidas</div>',
				            seriesColors: blueColors,
				            animate: true,
				            // Will animate plot on calls to plot1.replot({resetAxes:true})
				            animateReplot: true,
				           
				             grid:{
				                drawBorder: true,
				                shadow: true,
				                background: "#FFFDF6",
				                rendererOptions: {
				                    // plotBands is an option of the pyramidGridRenderer.
				                    // it will put banding at starting at a specified value
				                    // along the y axis with an adjustable interval.
				                    plotBands: {
				                        show: true,
				                        interval: 1,
				                        color: '#dbdbdb'
				                    }
				                },
				            },

				            // This makes the effective starting value of the axes 0 instead of 1.
				            // For display, the y axis will use the ticks we supplied.
				            seriesDefaults: {
				                renderer: $.jqplot.PyramidRenderer,
				                rendererOptions: {
				                    barPadding: 4
				                },
				                yaxis: "yMidAxis",
				                shadow: true
				            },

				            // We have 4 series, the left and right pyramid bars and
				            // the left and rigt overlay lines.
				            series: [
				                // For pyramid plots, the default side is right.
				                // We want to override here to put first set of bars
				                // on left.
				                
				                {//barra azul grafico esquerda
				                	ticks: [0,30,52,62,78,89],
				                    rendererOptions:{
				                        side: 'left',
				                        synchronizeHighlight: 1
				                    }
				                },
				                {//barra azul grafico direita
				                	
				                    yaxis: "yMidAxis",
				                    rendererOptions:{
				                        synchronizeHighlight: 0
				                    }
				                },
				                // Pyramid series are filled bars by default.
				                // The overlay series will be unfilled lines.
				                {
				                	//barra laranja grafico esquerda
				                	
				                    rendererOptions: {
				                        fill: false,
				                        side: "left"
				                    }
				                },
				                {//barra laranja grafico direita
				                    yaxis: "yMidAxis",
				                    rendererOptions: {
				                        fill: false
				                    }
				                },
				                {
				                	show:false
				                },
				                {
				                	show:false
				                }
				            ],

				            // Set up all the y axes, since users are allowed to switch between them.
				            // The only axis that will show is the one that the series are "attached" to.
				            // We need the appropriate options for the others for when the user switches.
				            axes: {
				                yMidAxis: {
				                    // include empty tick options, they will be used
				                    // as users set options with plot controls.
				                    tickOptions: {},
				                    showMinorTicks: true,
				                    ticks: ticksMesesPiramide,
				                    rendererOptions: {
				                        category: true,
				                        baselineWidth: 2
				                    }
				                }
				            }
				        };
						
						plot3 = $.jqplot("piramideEmissao", [emitidosDetalhado<%=anoAtualTexto %>,apolicesEmitidas<%=anoAtualTexto %>,emitidosDetalhado<%=anoAnteriorTexto %>,apolicesEmitidas<%=anoAnteriorTexto %>], plotOptions);
						//plot3 = $.jqplot("piramide", [male,female,male2,female2], plotOptions);
						
						
						   // bind to the data highlighting event to make custom tooltip:
				        $(".jqplot-target").bind("jqplotDataHighlight", function(evt, seriesIndex, pointIndex, data) {
				            // Here, assume first series is male poulation and second series is female population.
				            // Adjust series indices as appropriate.
				            var emiti<%=anoAtualTexto %> = Math.abs(plot3.series[0].data[pointIndex][1]*1000000);
				            var qtApol<%=anoAtualTexto %> = Math.abs(plot3.series[1].data[pointIndex][1]);
				            var emiti<%=anoAnteriorTexto %> = Math.abs(plot3.series[2].data[pointIndex][1]*1000000);
				            var qtApol<%=anoAnteriorTexto %> = Math.abs(plot3.series[3].data[pointIndex][1]);
				         
				            $("#tooltipEmiti<%=anoAtualTexto %>").stop(true, true).fadeIn(250).html('R$ '+ (emiti<%=anoAtualTexto %>).formatMoney(0,',','.'));
				            $("#tooltipEmiti<%=anoAnteriorTexto %>").stop(true, true).fadeIn(250).html('R$ '+ (emiti<%=anoAnteriorTexto %>).formatMoney(0,',','.'));
				            
				            $("#tooltipApol<%=anoAtualTexto %>").stop(true, true).fadeIn(250).html(pontuaNumero(Math.round(qtApol<%=anoAtualTexto %>*1000).toString()));
				            $("#tooltipApol<%=anoAnteriorTexto %>").stop(true, true).fadeIn(250).html(pontuaNumero(Math.round(qtApol<%=anoAnteriorTexto %>*1000).toString()));

				            $("#tooltipTicketMedio<%=anoAtualTexto %>").stop(true, true).fadeIn(250).html('R$ '+ ( parseInt( emiti<%=anoAtualTexto%>/(qtApol<%=anoAtualTexto%>*1000).toFixed(2) ).formatMoney(0,',','.') ) );
				            $("#tooltipTicketMedio<%=anoAnteriorTexto %>").stop(true, true).fadeIn(250).html('R$ '+ ( parseInt( emiti<%=anoAnteriorTexto%>/(qtApol<%=anoAnteriorTexto%>*1000).toFixed(2) ).formatMoney(0,',','.') ) );

				            //totais
				            $("#tooltipTotalPremEmiti<%=anoAtualTexto %>").stop(true, true).fadeIn(250).html('R$ '+ (Math.abs(totalPremioEmitidoDetalhado<%=anoAtualTexto %>)*1000000).formatMoney(0,',','.'));
				            $("#tooltipTotalPremEmiti<%=anoAnteriorTexto %>").stop(true, true).fadeIn(250).html('R$ '+ (Math.abs(totalPremioEmitidoDetalhado<%=anoAnteriorTexto %>)*1000000).formatMoney(0,',','.'));
				            
				            $("#tooltipTotalQtdApol<%=anoAtualTexto %>").stop(true, true).fadeIn(250).html(pontuaNumero(Math.round(totalApolicesEmitidas<%=anoAtualTexto %>*1000).toString()));
				            $("#tooltipTotalQtdApol<%=anoAnteriorTexto %>").stop(true, true).fadeIn(250).html(pontuaNumero(Math.round(totalApolicesEmitidas<%=anoAnteriorTexto %>*1000).toString()));

				            $("#tooltipTicketMedioTotais<%=anoAtualTexto %>").stop(true, true).fadeIn(250).html('R$ '+ ( parseInt( (Math.abs(totalPremioEmitidoDetalhado<%=anoAtualTexto %>)*1000000) / (Math.round(totalApolicesEmitidas<%=anoAtualTexto %>*1000)).toFixed(2) ).formatMoney(0,',','.') ) );
				            $("#tooltipTicketMedioTotais<%=anoAnteriorTexto %>").stop(true, true).fadeIn(250).html('R$ '+ ( parseInt( (Math.abs(totalPremioEmitidoDetalhado<%=anoAnteriorTexto %>)*1000000) /(Math.round(totalApolicesEmitidas<%=anoAnteriorTexto %>*1000)).toFixed(2) ).formatMoney(0,',','.') ) );
				            

				            // Since we don't know which axis is rendererd and acive with out a little extra work,
				            // just use the supplied ticks array to get the age label.
				            $(".tooltipMes").stop(true, true).fadeIn(250).html(ticksMesesPiramide[pointIndex]);
				        });

				        // bind to the data highlighting event to make custom tooltip:
				        $(".jqplot-target").bind("jqplotDataUnhighlight", function(evt, seriesIndex, pointIndex, data) {
				            // clear out all the tooltips.
				            //$(".tooltip-item").stop(true, true).fadeOut(200).html('');
				        });
											
						
						plot4 = $.jqplot('grafiCancelados', [cancelados<%=anoAnteriorTexto %>, cancelados<%=anoAtualTexto %>], {
							animate:true, //gráfico animado
							animateReplot: true,//gráfico animado
							seriesDefaults: {
								renderer:$.jqplot.BarRenderer,
								pointLabels: { show: true },
								rendererOptions: {
									animation: {
		                                speed: 1500
		                            },
									barPadding: 1,
									barMargin: 10
								}
							},
							title:'Prêmios Cancelados',
							seriesColors:['<%=laranja%>','<%=azul%>'],
							rendererOptions: {
								varyBarColor: true
							},
							axes: {
								xaxis: {
									renderer: $.jqplot.CategoryAxisRenderer,
									ticks: ticks
								},
								yaxis:{
									ticks: ticks4
								}
							}
						});
						
						plot5 = $.jqplot('grafiRepresentacaoCancelados', [canceladosPorEmitidos<%=anoAnteriorTexto %>, canceladosPorEmitidos<%=anoAtualTexto %>], {
							animate:true, //gráfico animado
							animateReplot: true,//gráfico animado
							seriesDefaults: {
								renderer:$.jqplot.BarRenderer,
								pointLabels: { show: true },
								rendererOptions: {
									barPadding: 1,
									barMargin: 10,
									animation: {
		                                speed: 1500
		                            }
								}
							},
							title:'<%=request.getAttribute("titulo")%> - Representação Cancelados',
							seriesColors:['<%=laranja%>','<%=azul%>'],
							rendererOptions: {	varyBarColor: true	},
							axes: {
								xaxis: {
									renderer: $.jqplot.CategoryAxisRenderer,
									ticks: ticks
								},
								yaxis:{
									ticks: ticks5,
									tickOptions: {	formatString: "%d\%"  }
								}
							}
						});
					
				
				<% if (!perio.equals(" ")){ //usado apenas nos dados diarios%>
				
				
						plotFaturamentoAcumulado_Diario = $.jqplot('grafiFatuAcumulada_diario', [varDadosDiariosFaturamentoAnoAnterior,varDadosDiariosFaturamentoAnoAtual, varDadosDiarios_BP_FaturamentoAnoAtual],
							{
							seriesColors: ['<%=laranja%>','<%=azul%>','<%=cinza%>'],
							negativeSeriesColors:['<%=laranja%>','<%=azul%>', '<%=cinza%>'],
					        title: '<%=request.getAttribute("titulo")%> - Faturamento Diário',
					        highlighter: {
					            show: true,
					            sizeAdjust: 5,
					            tooltipOffset: 0,
					            tooltipLocation:'ne',
					            tooltipAxes: 'y'
					        },
					        grid: {
					            background: '#f4f4f4',
					            drawBorder: true,
					            shadow: true,
					            gridLineColor: '#888',
					            gridLineWidth: 0.6
					        },
					        legend: {
					            show: false,
					            placement: 'outside'
					        },
					        seriesDefaults: {
								pointLabels: {
									show: false
								},
					        },
					        series: [
					        	 	{
				                    	renderer: $.jqplot.BarRenderer,
				                    	label:'<%=anoAnteriorTexto %>',
				                        pointLabels: {
				                            show: false
				                        },
				                        showHighlight: true,
				                        rendererOptions: {
				                        	fillToZero: true,
				                            // Speed up the animation a little bit.
				                            // This is a number of milliseconds.  
				                            // Default for bar series is 3000.  
				                            animation: {
				                                speed: 2000
				                            },
				                            barWidth: 15,
				                            barPadding: 1,
				                            barMargin: 20,
				                            highlightMouseOver: false
				                        }
				                    },						        	
					        	 	{
				                    	renderer: $.jqplot.BarRenderer,
				                    	label:'<%=anoAtualTexto %>',
				                        pointLabels: {
				                            show: false
				                        },
				                        showHighlight: true,
				                        rendererOptions: {
				                        	fillToZero: true,
				                            // Speed up the animation a little bit.
				                            // This is a number of milliseconds.  
				                            // Default for bar series is 3000.  
				                            animation: {
				                                speed: 2000
				                            },
				                            barWidth: 15,
				                            barPadding: 1,
				                            barMargin: 20,
				                            highlightMouseOver: false
				                        }
				                    },
						            {
										showMarker: true,
										label: 'BP'
						            }
					        ],
					        legend: {
			                    show: true,
			                    placement: 'outside'
			                },
					        axesDefaults: {
					            rendererOptions: {
					                baselineWidth: 1.5,
					                baselineColor: '#444',
					                drawBaseline: false
					            }
					        },
					        axes: {
					            xaxis: {
									renderer: $.jqplot.CategoryAxisRenderer,
					                drawMajorGridlines: true,
					                sortMergedLabels: true
					            },
					            yaxis: {
					                pad: 0,
					                rendererOptions: {
					                    minorTicks: 1
					                },
					                tickOptions: {
					                    formatString: "R$%'.2f",
					                    showMark: true
					                }
					            }
					        },
					        highlighter: {
								show: true, 
								showMarker:false,
								tooltipLocation: 'n',
								tooltipAxes: 'y',
							}
						});
					
					
				
						plotEmissaoDiaria = $.jqplot('premioEmitidoPorDia', [varPremEmitidoDiariaAnoAnterior, varPremEmitidoDiariaAnoAtual], {
							seriesColors: ['<%=laranja%>','<%=azul%>'],
					        title: 'Prêmios Emitidos por dia',
					        highlighter: {
					            show: true,
					            sizeAdjust: 5,
					            tooltipOffset: 0,
					            tooltipLocation:'ne',
					            tooltipAxes: 'y'
					        },
					        grid: {
					            background: '#f4f4f4',
					            drawBorder: true,
					            shadow: true,
					            gridLineColor: '#888',
					            gridLineWidth: 0.6
					        },
					        legend: {
					            show: false,
					            placement: 'outside'
					        },
					        seriesDefaults: {
					            rendererOptions: {
					                smooth: true,
					                animation: {
					                    show: true
					                }
					            },
					            showMarker: false
					        },
					        series: [
						            {
										showMarker: false,
										label: '<%=anoAnteriorTexto %>'
						            },
						            {
										showMarker: true,
										label: '<%=anoAtualTexto %>'
						            }
					        ],
					        legend: {
			                    show: true,
			                    placement: 'outside'
			                },
					        axesDefaults: {
					            rendererOptions: {
					                baselineWidth: 1.5,
					                baselineColor: '#444',
					                drawBaseline: false
					            }
					        },
					        axes: {
					            xaxis: {
									renderer: $.jqplot.CategoryAxisRenderer,
					                drawMajorGridlines: true,
					                sortMergedLabels: true
					            },
					            yaxis: {
					                pad: 0,
					                rendererOptions: {
					                    minorTicks: 1,
					                    forceTickAt0: false,
					                    forceTickAt100: true
					                },
					                tickOptions: {
					                    formatString: "R$%'.2f",
					                    showMark: true
					                }
					            }
					        }
						});

						
						plotCancelamentoDiaria = $.jqplot('premioCanceladodoPorDia', [varPremCanceladoDiaria], {
							seriesColors: ["#3f89b5"],
					        title: 'Prêmios Cancelados por dia',
					        highlighter: {
					            show: true,
					            sizeAdjust: 5,
					            tooltipOffset: 0,
					            tooltipLocation:'ne',
					            tooltipAxes: 'y'
					        },
					        grid: {
					            background: '#f4f4f4',
					            drawBorder: true,
					            shadow: true,
					            gridLineColor: '#888',
					            gridLineWidth: 0.6
					        },
					        legend: {
					            show: false,
					            placement: 'outside'
					        },
					        seriesDefaults: {
					            rendererOptions: {
					                smooth: true,
					                animation: {
					                    show: true
					                }
					            },
					            showMarker: false
					        },
					        series: [
						            {
										showMarker: true
						            }
					        ],
					        axesDefaults: {
					            rendererOptions: {
					                baselineWidth: 1.5,
					                baselineColor: '#444',
					                drawBaseline: false
					            }
					        },
					        axes: {
					            xaxis: {
									renderer: $.jqplot.CategoryAxisRenderer,

					                drawMajorGridlines: true
					            },
					            yaxis: {
					                pad: 0,
					                rendererOptions: {
					                    minorTicks: 1
					                },
					                tickOptions: {
					                    formatString: "R$%'.2f",
					                    showMark: true
					                }
					            }
					        }
						});
						
						
						plotRestituidoDiaria = $.jqplot('premioRestituidoPorDia', [varPremRestituidoDiaria], {
							seriesColors: ["#3f89b5"],
					        title: 'Prêmios Restituídos por dia',
					        highlighter: {
					            show: true,
					            sizeAdjust: 5,
					            tooltipOffset: 0,
					            tooltipLocation:'ne',
					            tooltipAxes: 'y'
					        },
					        grid: {
					            background: '#f4f4f4',
					            drawBorder: true,
					            shadow: true,
					            gridLineColor: '#888',
					            gridLineWidth: 0.6
					        },
					        legend: {
					            show: false,
					            placement: 'outside'
					        },
					        seriesDefaults: {
					            rendererOptions: {
					                smooth: true,
					                animation: {
					                    show: true
					                }
					            },
					            showMarker: false
					        },
					        series: [
						            {
										showMarker: true
						            }
					        ],
					        axesDefaults: {
					            rendererOptions: {
					                baselineWidth: 1.5,
					                baselineColor: '#444',
					                drawBaseline: false
					            }
					        },
					        axes: {
					            xaxis: {
									renderer: $.jqplot.CategoryAxisRenderer,

					                drawMajorGridlines: true
					            },
					            yaxis: {
					                pad: 0,
					                rendererOptions: {
					                    minorTicks: 1
					                },
					                tickOptions: {
					                    formatString: "R$%'.2f",
					                    showMark: true
					                }
					            }
					        }
						});
						
				<% } %>
						
						
						$('#grafiFatuMensal').bind('jqplotDataHighlight', function (ev, seriesIndex, pointIndex, data) {
							var tamanho = escape(data).length;

							//if (escape(data).indexOf(".") === 6 || escape(data).indexOf(".") === 7 || escape(data).indexOf(".") === 8){
								//$('#mensal_serie_'+seriesIndex+'_point_'+pointIndex).html('<span style="color:red;">'+escape(data).substring(escape(data).indexOf("2C")+2,tamanho)+'&nbsp;&nbsp;</span>');
//								document.getElementById("valor").value = escape(data).substring(escape(data).indexOf("2C")+2,tamanho);
	//							document.getElementById("serie").value = seriesIndex;
		//						document.getElementById("point").value = pointIndex;
			//				}else if(escape(data).indexOf(".") === -1 ){
				//				document.getElementById("valor").value = escape(data).substring(escape(data).indexOf("2C")+2,tamanho)+".0";
					//			document.getElementById("serie").value = seriesIndex;
						//		document.getElementById("point").value = pointIndex;
							//}else {
								//$('#mensal_serie_'+seriesIndex+'_point_'+pointIndex).html('<span style="color:red;font-weight:bold;">'+escape(data).substring(escape(data).indexOf("2C")+2,tamanho)+'.0&nbsp;&nbsp;</span>');
//								// $('#valor').html(escape(data).substring(escape(data).indexOf("2C")+2,tamanho)+'&nbsp;&nbsp;');
	//							document.getElementById("valor").value = escape(data).substring(escape(data).indexOf("2C")+2,tamanho);
		//						document.getElementById("serie").value = seriesIndex;
			//					document.getElementById("point").value = pointIndex;
				//			}
						});
						
						$('#grafiFatuMensal').bind('jqplotDataUnhighlight',	function (ev) {
							//var valor = document.getElementById('valor').value;
							//var point = document.getElementById('point').value;
							//var serie = document.getElementById('serie').value;
							//$('#mensal_serie_'+serie+'_point_'+point).html(valor.substring(0,4)+'&nbsp;&nbsp;');
						});

						$('#grafiFatuAcumulada').bind('jqplotDataHighlight', function (ev, seriesIndex, pointIndex, data) {
							//$('#info2').html('series: '+seriesIndex+', point: '+pointIndex+', data: '+data);
						});

						$('#grafiFatuAcumulada').bind('jqplotDataUnhighlight', function (ev) {
							//alert(tamanho);
						});


						$('#tabs').bind('tabsactivate', function(event, ui) {
						//	alert(ui.newTab.index());
							//alert(plot4._drawCount);
							if (ui.newTab.index() === 0 && plot1._drawCount === 0) {
								plot1.replot();
								plot2.replot();
								plotFaturamentoAcumulado_Diario.replot();
							}else if (ui.newTab.index() === 1 && plot3._drawCount === 0) {
								plot3.replot();
								plotEmissaoDiaria.replot();
							//}else if (ui.newTab.index() === 2 && plot4._drawCount === 0) {
							//	console.log('ticket');
							}else if (ui.newTab.index() === 2 && plot4._drawCount === 0) {
								plot4.replot();
								plotCancelamentoDiaria.replot();
								plotRestituidoDiaria.replot();
							}else if (ui.newTab.index() === 3 && plot5._drawCount === 0) {
								plot5.replot();
							}
						});

						$('#accordion').bind('accordionactivate', function(event, ui) {
							var index = $(this).find("h3").index ( ui.newHeader[0] );
							if (index === 1) {
								plot3.replot();
							}
						});
					};
						
				</script>