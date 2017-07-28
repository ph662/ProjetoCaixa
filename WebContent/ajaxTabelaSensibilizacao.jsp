<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="caixa.dirid.VO.SensibilizacaoVO"%>
<%@ page import="caixa.dirid.UTEIS.*"%>
<%@ page import="java.util.*"%>

	<center><h4>Relat&oacute;rio de Sensibiliza&ccedil;&atilde;o</h4></center>
		
	<%
	/***********/
	/*VARIAVEIS*/
	/***********/
	
	DecimalFormat percentForm = new DecimalFormat("#.#%");
	DecimalFormat roundForm = new DecimalFormat("0.0");
	
	//Recebem o objeto "VO" da servlet "MenuServlet.java"
	List<SensibilizacaoVO> listaSensibilizacao = (List<SensibilizacaoVO>) request.getAttribute("listaSensibilizacao");
	List<SensibilizacaoVO> listaPorAgenciaValor = (List<SensibilizacaoVO>) request.getAttribute("listaPorAgenciaQuantidadeValorSensibilizacao");
	List<SensibilizacaoVO> listaPeriodo = (List<SensibilizacaoVO>) request.getAttribute("listaPeriodoSensibilizacao");
	int max = (int) request.getAttribute("paginacaoMax");
	String categoria =  (String) request.getAttribute("categoria");
	String mes =  (String) request.getAttribute("mes");
	String ano =  (String) request.getAttribute("ano");
	String codProduto = (String) request.getAttribute("codigoProduto");
	%>
	
	
	<script>
	$(document).ready(function(){
		var mes = $('#tipo').val();
		var ano = $('#tipoAno').val();
		dadosSensibilizacaoPaginacao(ano,mes,"&categ=<%= categoria %>","1","<%= codProduto%>");
		
		$('.pagination').jqPagination({
			//link_string	: '/?page={page_number}',
			max_page	: <%=max %>,
			paged		: function(page) {
				$('.log').prepend('<li>Requested page ' + page + '</li>');
				mes = $('#tipo').val();
				ano = $('#tipoAno').val();
				dadosSensibilizacaoPaginacao(ano,mes,"&categ=<%= categoria %>",page,"<%=codProduto%>");
			}
		});
	});
	
	</script>
	<div style="width:70%; margin-left: 315px;">
		<div style="float:left;width:60%;">
		
			<table class="tabelaSensibilizacao" border="0"  width="200">
				<tr>
					<td>Per&iacute;odo</td>
					<td><%= listaPeriodo.get(0).getDataInicio()%> :: <%= listaPeriodo.get(0).getDataFim()%></td>
				</tr>
			</table>	
			<br/>
			<table class="tabelaSensibilizacao" border="0" width="300">
				<thead>
					<tr bgcolor="#C5D9F1">
						<th>Descri&ccedil;&atilde;o</th>
						<th>Totalizador</th>
					</tr>
				</thead>
				<tbody>
					<%for(int i = 0;i < listaSensibilizacao.size();i++){ %>
						<tr>
							<td><%=listaSensibilizacao.get(i).getProduto()%></td>
							<td><%=listaSensibilizacao.get(i).getQuantidade()%></td>
						</tr>
					<%} %>
				</tbody>
			</table>
			<br>
	
			<div id="ajaxSensibilizacaoPaginada" style="margin-left: -78px;">
			<!-- style="margin-left: -78px; -->
			
			</div>
	
		</div>
		<div style="float:right;width:10%;margin-right: 75px;">
			<!-- a href="Downloads/<%=categoria%>/<%=categoria%><%=mes%>.xls"-->
			<a href="visaoOperacional?tipo=sensibilizacao&download=t&categoria=<%=categoria%>&mes=<%=mes%>&ano=<%=ano%>">
				<span>
					Download<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<img class="download" src="imagens/excel.png"/>
				</span>
			</a>
		</div>
		<div id="pagination" class="pagination" style="margin-left: -769px; margin-top: 90px; position: fixed;">
		    <a href="#" class="first" data-action="first" onclick='pegaDados(this)'>&laquo;</a>
		    <a href="#" class="previous" data-action="previous" onclick='pegaDados(this)'>&lsaquo;</a>
		    <input type="text" data-max-page="<%= max%>" />
		    <a href="#" class="next" data-action="next"onclick='pegaDados(this)'>&rsaquo;</a>
		    <a href="#" class="last" data-action="last" onclick='pegaDados(this)'>&raquo;</a>
		</div>
	</div>
