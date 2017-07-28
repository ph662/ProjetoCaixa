<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="caixa.dirid.VO.RvneVO"%>
<%@ page import="caixa.dirid.VO.SensibilizacaoVO"%>
<%@ page import="caixa.dirid.UTEIS.*"%>
<%@ page import="java.util.*"%>
	
	<%
	String[] mesesPagWeb = new Uteis().mesesPaginaWeb();
	String ano = (String) request.getAttribute("ano");
	%>
	
	<div>
		<div>
			<br>
			<h1><center>RVNE <%=ano%></center></h1>
			<table class="tabelaApolice" style="border: 1px solid;">
				<thead>
					<tr bgcolor="#8DB4E2" style = "border-bottom: 1px solid;">
						<th>&nbsp;Produto&nbsp;</th>
						<%
							for(int d = 0; d < mesesPagWeb.length; d++){
						%>
							<th style="text-align: center;">									
								&nbsp;<%=mesesPagWeb[d]%>&nbsp;
							</th>
						<%
							}
						%>
					</tr>
				</thead>	
				<tbody>
					<%
						List<RvneVO> lista = (List<RvneVO>) request.getAttribute("rvne");
						
						for (int i = 0; i < lista.size(); i++) {
							if(! ( lista.get(i).getProduto().contains("Lar Mais") || lista.get(i).getProduto().contains("MR Residencial Aporte Caixa") || lista.get(i).getProduto().contains("Rd Pf Outros") || lista.get(i).getProduto().contains("Dirid") ) ){ 		
					%>
								<tr>
									<input id="<%=lista.get(i).getProduto()%>" type="hidden" value="<%=lista.get(i).getId() %>" />
									<td style="text-align:left">&nbsp;<%=lista.get(i).getProduto()%>&nbsp;&nbsp;&nbsp;</td>
										<%
											for(int j = 0; j < 12; j++){
												if(lista.get(i).getMeses()[j] == null){
										%>
												<td style="text-align: center;">
													<button type="button" class="btn btn-default" aria-label="Left Align" onclick="inserirRVNE('<%=j+1%>_<%=lista.get(i).getId() %>_<%=lista.get(i).getProduto().replace(" ", "")%>')">
																<span value="<%=j+1%>_<%=lista.get(i).getProduto().replace(" ", "")%>" class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
													</button>
												</td>	
											<%
												} else {
											%>
													<td id="linha<%=i%>_coluna<%=j+1%>" class="alteraExclui"  onmouseover="getid(this);" onmouseout="outmouse(this);" >
														<input id="produto_lin<%=i%>_col<%=j+1%>" type="hidden" value="<%=lista.get(i).getId() %>">
														<%=lista.get(i).getMeses()[j]%>&nbsp;<br>
														
														<form id="teste" method="post" name="form">
															<span class="botoes">
																<a id="lin<%=i%>_col<%=j+1%>" onclick="selecionarRVNE(this)">
																	<img class="button" src="imagens/btEditar.png">
																</a>
																<a id="lin<%=i%>_col<%=j+1%>" onclick="excluirRVNE(this)">
																	<img class="button" src="imagens/btExcluir.png">
																</a>
															</span>
														</form>
													</td>
										<%
												}
											}
										%>
								</tr>
					<%
							}
						}
					%>
				</tbody>
			</table>
		</div>
		<br/>
		<div id="fieldInserir" style="display: block; margin-left: 51px;">
			<div class="navbar-form navbar-left">
				<div class="form-group">
					<input id="fieldInsere" type="text" class="form-control" placeholder="Digite a RVNE" name="" >
				</div>
				<button type="button" class="btn btn-default" onClick="inserir();">Inserir</button>
			</div>
		</div>
		<div id="botaoAlterarAjax">
		
		</div>
		
	</div>