<%@page import="com.sun.org.apache.bcel.internal.generic.DADD"%>
<%@ page import="caixa.dirid.VO.CoberturasVO"%>
<%@ page import="caixa.dirid.VO.FaturamentoVO"%>
<%@ page import="caixa.dirid.VO.RelatorioAceitacaoVO"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="caixa.dirid.UTEIS.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<head>
<!-- Compatibilidade com IE antigo -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="author" content="phelipe662@gmail.com">
<link href="imagens/caixaFav.ico" rel="shortcut icon" type="image/x-icon">
<title>RRA</title>
<%@ include file="importHeadBootstrap.jsp"%>
<%@ include file="importHead_JQplot.jsp"%>
<%@ include file="importHead_JQwidgets.jsp"%>
<script type="text/javascript">
	$(document).ready(
			function() {
				var url = "RelatorioAceitacao/atividadePrincipal.txt";
				// prepare the data
				var source = {
					datatype : "json",
					datafields : [ {
						name : 'nome'
					} ],
					url : url,
					async : false,
					contentType : 'application/json; charset=utf-8'
				};
				var dataAdapter = new $.jqx.dataAdapter(source);
				

				
				<%
				//dataI[2]//ano dataI[1]//mes dataI[0]//dia
				if (request.getAttribute("alterar") != null ){
				List<RelatorioAceitacaoVO> dadosRelatorio = (List<RelatorioAceitacaoVO>) request.getAttribute("relatorioSalvo");
				String numAceitacao = (String) request.getAttribute("numAceitacao");
				String[] dataI = dadosRelatorio.get(0).getInicioVig().split("/");
				String[] dataF = dadosRelatorio.get(0).getFimVig().split("/");
				int mesI = Integer.parseInt(dataI[1]) - 1;//fiz o calculo pq nao estava funcinando
				int mesF = Integer.parseInt(dataF[1]) - 1;//fiz o calculo pq nao estava funcinando 
					
				%>
					$("#inicioVig").jqxDateTimeInput({
						width : 200,
						height : 22,
						value : new Date(<%=dataI[2]%>, <%=mesI%>, <%=dataI[0]%>)
					});
					$("#fimVig").jqxDateTimeInput({
						width : 200,
						height : 22,
						value : new Date(<%=dataF[2]%>, <%=mesF%>, <%=dataF[0]%>)
					});
					
					// Create a jqxComboBox
					$("#jqxWidget").jqxComboBox({
						selectedIndex : 0,
						source : dataAdapter,
						displayMember : "nome",
						valueMember : "nome",
						width : 200,
						height : 25
					});
				<%
				}else{
				%>
					$("#inicioVig").jqxDateTimeInput({
						width : 200,
						height : 22,
						value : new Date(2016, 1, 1)
					});
					$("#fimVig").jqxDateTimeInput({
						width : 200,
						height : 22,
						value : new Date(2016, 1, 1)
					});
					
					// Create a jqxComboBox
					$("#jqxWidget").jqxComboBox({
						selectedIndex : 0,
						source : dataAdapter,
						displayMember : "nome",
						valueMember : "nome",
						width : 200,
						height : 25
					});
				<%
				}
				%>
				
				
				
				var valorIsStr;
				var LIMITE_SINISTRO = parseFloat("1000000");

				function calcula() {
					valorIsStr = $("#is input.jqx-widget-content").val();

					valorIsStr = valorIsStr.replace("R$", "");

					while (valorIsStr.search("_") != -1) {
						valorIsStr = valorIsStr.replace("_", "");
					}
					while (valorIsStr.search("[.]") != -1) {
						valorIsStr = valorIsStr.replace(".", "");
					}
					valorIsStr = valorIsStr.replace(",", ".");

					var valorStr = $("#premioLiq input.jqx-widget-content").val();

					valorStr = valorStr.replace("R$", "");

					while (valorStr.search("_") != -1) {
						valorStr = valorStr.replace("_", "");
					}
					while (valorStr.search("[.]") != -1) {
						valorStr = valorStr.replace(".", "");
					}
					valorStr = valorStr.replace(",", ".");

					var valorNetF = parseFloat(valorStr);
					valorNetF = valorNetF * (1 - 0.275);

					var LIMITE_RETENCAO = parseFloat("1000000");
					var VALOR_BASICA = parseFloat(valorIsStr);
					var CUSTO_ED_1_FAIXA = parseFloat("0.0");
					var CUSTO_ED_2_FAIXA = parseFloat("0.064");
					var CUSTO_ED_3_FAIXA = parseFloat("0.0498");
					var CUSTO_ED_4_FAIXA = parseFloat("0.0349");
					var CUSTO_ED_5_FAIXA = parseFloat("0.0193");
					var LMI1 = parseFloat("80000000");
					var LMI2 = parseFloat("80000000");

					var PRIORIDADE = parseFloat("1000000");
					var FAIXA_1 = parseFloat("5000000");
					var FAIXA_2 = parseFloat("20000000");
					var FAIXA_3 = parseFloat("40000000");
					var FAIXA_4 = parseFloat("80000000");

					var premioCedido1 = parseFloat("0");
					var premioCedido2 = valorNetF * CUSTO_ED_2_FAIXA;
					var premioCedido3 = valorNetF * CUSTO_ED_3_FAIXA;
					var premioCedido4 = valorNetF * CUSTO_ED_4_FAIXA;
					var premioCedido5 = valorNetF * CUSTO_ED_5_FAIXA;
					var premioCedido6 = ((LMI1 - LMI2) / LMI1) * valorNetF;

					//alert(premioCedido1+" "+premioCedido2+" "+premioCedido3+" "+premioCedido4+" "+premioCedido5+" "+premioCedido6);

					var premioCedidoF;

					if (VALOR_BASICA > FAIXA_4) {
						premioCedidoF = parseFloat("9999999");
					} else {
						if (VALOR_BASICA > FAIXA_3) {
							premioCedidoF = premioCedido1 + premioCedido2
									+ premioCedido3 + premioCedido4
									+ premioCedido5;
						} else {
							if (VALOR_BASICA > FAIXA_2) {
								premioCedidoF = premioCedido1 + premioCedido2
										+ premioCedido3 + premioCedido4;
							} else {
								if (VALOR_BASICA > FAIXA_1) {
									premioCedidoF = premioCedido1
											+ premioCedido2 + premioCedido3;
								} else {
									if (VALOR_BASICA > PRIORIDADE) {
										premioCedidoF = premioCedido1
												+ premioCedido2;
									} else {
										premioCedidoF = premioCedido1;
									}
								}
							}

						}
					}

					var premioRetidoF = valorNetF - premioCedidoF;
					//alert(premioCedidoF);

					var partRessegF;
					if ((VALOR_BASICA - LIMITE_SINISTRO) < 0) {
						partRessegF = 0;
					} else {
						partRessegF = VALOR_BASICA - LIMITE_SINISTRO;
					}

					var premioNet = valorNetF.toFixed(2).toString();
					var premioRetido = premioRetidoF.toFixed(2).toString();
					var premioCedido = premioCedidoF.toFixed(2).toString();
					var partResseg = partRessegF.toFixed(2).toString();
					$('#premioNet').jqxNumberInput('setDecimal', premioNet);
					$('#premioRetido').jqxNumberInput('setDecimal',
							premioRetido);
					$('#premioCedido').jqxNumberInput('setDecimal',
							premioCedido);
					$('#partResseg').jqxNumberInput('setDecimal', partResseg);
				}

				
				function apenasNumeros(){
					alert('dooh');
				}

				
				$("#is").jqxNumberInput({
					width : '200px',
					height : '25px',
					symbol : 'R$',
					<%
					if (request.getAttribute("alterar") != null ){
					List<RelatorioAceitacaoVO> dadosRelatorio = (List<RelatorioAceitacaoVO>) request.getAttribute("relatorioSalvo");
						out.print("value:'"+dadosRelatorio.get(0).getIs().replace(".", "").replace(",", ".")+"',");
					}
					%>
					spinButtons : false,
					decimalSeparator : ",",
					groupSeparator : "."
				});
				$("#is input.jqx-widget-content").blur(function() {
					calcula();
				});

				$("#premioLiq").jqxNumberInput({
					width : '200px',
					height : '25px',
					symbol : 'R$',
					<%
					if (request.getAttribute("alterar") != null ){
					List<RelatorioAceitacaoVO> dadosRelatorio = (List<RelatorioAceitacaoVO>) request.getAttribute("relatorioSalvo");
						out.print("value:'"+dadosRelatorio.get(0).getPremioLiq().replace(".", "").replace(",", ".")+"',");
					}
					%>
					spinButtons : false,
					decimalSeparator : ",",
					groupSeparator : "."
				});
				$("#premioLiq input.jqx-widget-content").blur(function() {
					calcula();
				});
					
				$("#premioNet").jqxNumberInput({
					width : '200px',
					height : '25px',
					symbol : 'R$',
					<%
					if (request.getAttribute("alterar") != null ){
					List<RelatorioAceitacaoVO> dadosRelatorio = (List<RelatorioAceitacaoVO>) request.getAttribute("relatorioSalvo");
						out.print("value:'"+dadosRelatorio.get(0).getPremioNet().replace(".", "").replace(",", ".")+"',");
					}
					%>
					spinButtons : false,
					decimalSeparator : ",",
					groupSeparator : "."
				});
				$("#premioNet").jqxNumberInput({
					disabled : true
				});

				$("#premioRetido").jqxNumberInput({
					width : '200px',
					height : '25px',
					symbol : 'R$',
					<%
					if (request.getAttribute("alterar") != null ){
					List<RelatorioAceitacaoVO> dadosRelatorio = (List<RelatorioAceitacaoVO>) request.getAttribute("relatorioSalvo");
						out.print("value:'"+dadosRelatorio.get(0).getPremioRetido().replace(".", "").replace(",", ".")+"',");
					}
					%>
					spinButtons : false,
					decimalSeparator : ",",
					groupSeparator : "."
				});
				$("#premioRetido").jqxNumberInput({
					disabled : true
				});

				$("#premioCedido").jqxNumberInput({
					width : '200px',
					height : '25px',
					symbol : 'R$',
					<%
					if (request.getAttribute("alterar") != null ){
					List<RelatorioAceitacaoVO> dadosRelatorio = (List<RelatorioAceitacaoVO>) request.getAttribute("relatorioSalvo");
						out.print("value:'"+dadosRelatorio.get(0).getPremioCedido().replace(".", "").replace(",", ".")+"',");
					}
					%>
					spinButtons : false,
					decimalSeparator : ",",
					groupSeparator : "."
				});
				$("#premioCedido").jqxNumberInput({
					disabled : true
				});

				$("#limiteSinistro").jqxNumberInput({
					width : '200px',
					height : '25px',
					symbol : 'R$',
					spinButtons : false,
					value : LIMITE_SINISTRO.toString(),
					decimalSeparator : ",",
					groupSeparator : "."
				});
				$("#limiteSinistro").jqxNumberInput({
					disabled : true
				});

				$("#partResseg").jqxNumberInput({
					width : '200px',
					height : '25px',
					symbol : 'R$',
					<%
					if (request.getAttribute("alterar") != null ){
					List<RelatorioAceitacaoVO> dadosRelatorio = (List<RelatorioAceitacaoVO>) request.getAttribute("relatorioSalvo");
						out.print("value:'"+dadosRelatorio.get(0).getPartResseg().replace(".", "").replace(",", ".")+"',");
					}
					%>
					spinButtons : false,
					decimalSeparator : ",",
					groupSeparator : "."
				});
				$("#partResseg").jqxNumberInput({
					disabled : true
				});

				$("#partCaixa").jqxNumberInput({
					width : '200px',
					height : '25px',
					symbol : 'R$',
					spinButtons : false,
					value : '1000000',
					decimalSeparator : ",",
					groupSeparator : "."
				});
				$("#partCaixa").jqxNumberInput({
					disabled : true
				});

				$('#editor1').jqxEditor({
					tools : 'bold italic underline',
					width: 600,
					height : 300
				});
				
				<%
				if (request.getAttribute("alterar") != null ){
				List<RelatorioAceitacaoVO> dadosRelatorio = (List<RelatorioAceitacaoVO>) request.getAttribute("relatorioSalvo");
				%>
					$("#editor1").jqxEditor('val', '<%=dadosRelatorio.get(0).getParecerTecnico()%>');
					
				<%
				}
				%>
				
				$("#nAceitacao").jqxInput({
					placeHolder : "Número de Aceitação",
					height : 25,
					width : 200,
					disabled : true,
					minLength : 1
				});
				$("#nProposta").jqxInput({
					placeHolder : "Número de Proposta",
					height : 25,
					width : 200,
					minLength : 1
				});
				

				
				$("#segurado").jqxInput({
					placeHolder : "Segurado",
					height : 25,
					width : 200,
					minLength : 1
				});
				$("#localRisco").jqxInput({
					placeHolder : "Local do Risco",
					height : 25,
					width : 200,
					minLength : 1
				});
				$("#cpf").jqxInput({
					placeHolder : "CPF/CNPJ",
					height : 25,
					width : 200,
					minLength : 1
				});

				//==============================
				//======== coberturas ==========
				//==============================
				
				<%
					List<CoberturasVO> lista = (List<CoberturasVO>) request.getAttribute("coberturas");
					String numeroAceitacao = (String) request.getAttribute("novoNumeroAceitacao");
					
					
					if (request.getAttribute("alterar") != null ){
						List<CoberturasVO> dadosRelatorioCoberturas = (List<CoberturasVO>) request.getAttribute("coberturasRelatorioSalvo");
						
						for (int i = 1; i < lista.size(); i++) {
				%>
							$("#<%=lista.get(i).getIdCobertura()%>").jqxNumberInput({
								width : '200px',
								height : '25px',
								symbol : 'R$',
								spinButtons : false,
								disabled : true,
				<%
							for(int k = 0 ; k < dadosRelatorioCoberturas.size();k++ ){
								if (lista.get(i).getIdCobertura() == dadosRelatorioCoberturas.get(k).getIdCobertura()){
				%>
								disabled : false,
								value:'<%=dadosRelatorioCoberturas.get(k).getLMI().replace(".", "").replace(",", ".")%>',
				<%
								}
							}
				%>
								decimalSeparator : ",",
								groupSeparator : "."
							});
				<%
						}
					}else{
						for (int i = 1; i < lista.size(); i++) {
				%>	
							$("#<%=lista.get(i).getIdCobertura()%>").jqxNumberInput({
								width : '200px',
								height : '25px',
								symbol : 'R$',
								spinButtons : false,
								disabled : true,
								decimalSeparator : ",",
								groupSeparator : "."
							});
				<%
						}
					}
				%>
				
			});
</script>


<style>
table.stats-table td, table.highlighted-stats-table td {
	background-color: rgb(230, 230, 230);
	padding: 0.5em;
}

col.label {
	width: 14em;
}

col.value {
	width: 7em;
}

td.quintile-value {
	text-align: center;
}

table.stats-table td.tooltip-header, table.highlighted-stats-table td.tooltip-header
	{
	background-color: rgb(200, 200, 200);
}

td.contour-cell {
	height: 1.5em;
	padding-left: 20px;
	padding-bottom: 1.5em;
}

table.highlighted-stats-table {
	margin-top: 15px;
}

#externo {
	display: table;
	width: 1160px;
}

.interno {
	float: left;
}

.navbar {
	background-image: linear-gradient(to bottom, #0080c0 0px, #002855 100%);
	background-repeat: repeat-x;
	box-shadow: 0px 3px 9px rgba(0, 0, 0, 0.25) inset;
}

#botaoHome {
	background-image: linear-gradient(to bottom, #BC0808 0px, #CF4B4B 100%);
	background-repeat: repeat-x;
	box-shadow: 0px 3px 9px rgba(0, 0, 0, 0.25) inset;
}

#titulo {
	color: white;
}

.square {
	width: 10px;
	height: 10px;
}

.tabelas{
	font-family: verdana,arial,sans-serif;
	font-size:11px;
	margin: auto;
	color:#333333;
	border-width: 1px;
	border-color: #999999;
	border-collapse: collapse;
}

#tabelaCampos tr td:nth-child(1){
	text-align: right;
	padding-right: 15px;
}
#tabelaCampos tr td:nth-child(3){
	text-align: right;
	padding-right: 15px;
	
}

#tabelaCobertura thead tr:nth-child(1){
	text-align: center;
	font-size:15px;
}

#tabelaCobertura tbody tr td{
	padding: 10px;
}

#tabelaParecer tr td:nth-child(1){
	padding-right: 8px;
	text-align: right;
}

#btnGerar{
	margin-left: 96px;
}

</style>
</head>

<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<span class="navbar-brand" id="titulo">GERID - RRA</span>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li class="active" id="botaoHome">
						<script>
							document.write('<a href="http://"+ip_porta+"/ProjetoCaixa/visaoOperacional?tipo=menuRelAceitacao">Menu RRA</a>');
						</script>
					</li>
				</ul>
			</div>
		</div>
	</nav>
	<div class="container theme-showcase" role="main"
		style="padding-left: 0px;">
		<br>
		<div class="page-header">
			<h2>Preenchimento Relat&oacute;rio de Aceita&ccedil;&atilde;o</h2>
		</div>


		<div class="col1" id="example-content">
			<form id="testForm" onsubmit="return validador()" method="POST" action="visaoOperacional">
				
			<%
				if (request.getAttribute("alterar") != null ){
				List<RelatorioAceitacaoVO> dadosRelatorio = (List<RelatorioAceitacaoVO>) request.getAttribute("relatorioSalvo");
				String numAceitacao = (String) request.getAttribute("numAceitacao");
				//String numAceitacao = (String) request.getAttribute("numAceitacao");
				//List<RelatorioAceitacaoVO> dadosRelatorio = (List<RelatorioAceitacaoVO>) request.getAttribute("relatorioSalvo");
			%>
				<script type="text/javascript">
				$(document).ready(function() {
					$('#jqxWidget input').val("<%=dadosRelatorio.get(0).getAtividadePrincipal()%>");
				});
				</script>
					<table id="tabelaCampos" class="tabelas" width="75%">
						<tr>
							<td>N.º da Aceita&ccedil;&atilde;o:</td>
							<td><input type="text" id="nAceitacao" name="nAceitacao" pattern="\d" value="<%=numAceitacao%>"/></td>
							<td>IS:</td>
							<td>
								<div style="margin-top: 3px;" id='is'></div>
							</td>
						</tr>
						<tr>
							<td>N.º da Proposta:</td>
							<td><input type="text" id="nProposta" name="nProposta" value="<%=dadosRelatorio.get(0).getNumeroProposta()%>"/></td>
							<td>Pr&ecirc;mio L&iacute;quido:</td>
							<td>
								<div style='margin-top: 3px;' id='premioLiq'></div>
							</td>
						</tr>
						<tr>
							<td>Segurado:</td>
							<td><input type="text" id="segurado" name="segurado" value="<%=dadosRelatorio.get(0).getSegurado()%>" /></td>
							<td>Pr&ecirc;mio NET:</td>
							<td>
								<div style='margin-top: 3px;' id='premioNet'></div>
							</td>
						</tr>
						<tr>
							<td>Local do Risco:</td>
							<td><input type="text" id="localRisco" name="localRisco" value="<%= dadosRelatorio.get(0).getLocalRisco()%>"></td>
							<td>Pr&ecirc;mio Retido:</td>
							<td>
								<div style='margin-top: 3px;' id='premioRetido'></div>
							</td>
						</tr>
						<tr>
							<td>CPF/CNPJ:</td>
							<td><input type="text" id="cpf" name="cpf" value="<%= dadosRelatorio.get(0).getCpf()%>"></td>
							<td>Pr&ecirc;mio Cedido:</td>
							<td>
								<div style='margin-top: 3px;' id='premioCedido'></div>
							</td>
						</tr>
						<tr>
							<td>Atividade Principal:</td>
							<td>
								<div id="jqxWidget"></div>
							</td>
							<td>Limite de Sinistro:</td>
							<td>
								<div style='margin-top: 3px;' id='limiteSinistro'></div>
							</td>
						</tr>
						<tr>
							<td>In&iacute;cio de Vig&ecirc;ncia:</td>
							<td>
								<div id="inicioVig"></div>
							</td>
							<td>Participa&ccedil;&atilde;o do Ressegurador:</td>
							<td>
								<div style='margin-top: 3px;' id='partResseg'></div>
							</td>
						</tr>
						<tr>
							<td>Fim de Vig&ecirc;ncia:</td>
							<td>
								<div id="fimVig"></div>
							</td>
							<td>Participa&ccedil;&atilde;o da Caixa Seguradora:</td>
							<td>
								<div style='margin-top: 3px;' id='partCaixa'></div>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
					</table>
			
			<%
				}else{
			%>								
					
					<table id="tabelaCampos" class="tabelas" width="75%">
						<tr>
							<td>N.º da Aceita&ccedil;&atilde;o:</td>
							<td><input type="text" id="nAceitacao" name="nAceitacao" value="<%=numeroAceitacao%>"/></td>
							<td>IS:</td>
							<td>
								<div style="margin-top: 3px;" id='is'></div>
							</td>
						</tr>
						<tr>
							<td>N.º da Proposta:</td>
							<td><input type="text" id="nProposta" name="nProposta" /></td>
							<td>Pr&ecirc;mio L&iacute;quido:</td>
							<td>
								<div style='margin-top: 3px;' id='premioLiq'></div>
							</td>
						</tr>
						<tr>
							<td>Segurado:</td>
							<td><input type="text" id="segurado" name="segurado" /></td>
							<td>Pr&ecirc;mio NET:</td>
							<td>
								<div style='margin-top: 3px;' id='premioNet'></div>
							</td>
						</tr>
						<tr>
							<td>Local do Risco:</td>
							<td><input type="text" id="localRisco" name="localRisco"></td>
							<td>Pr&ecirc;mio Retido:</td>
							<td>
								<div style='margin-top: 3px;' id='premioRetido'></div>
							</td>
						</tr>
						<tr>
							<td>CPF/CNPJ:</td>
							<td><input type="text" id="cpf" name="cpf"></td>
							<td>Pr&ecirc;mio Cedido:</td>
							<td>
								<div style='margin-top: 3px;' id='premioCedido'></div>
							</td>
						</tr>
						<tr>
							<td>Atividade Principal:</td>
							<td>
								<div id="jqxWidget"></div>
							</td>
							<td>Limite de Sinistro:</td>
							<td>
								<div style='margin-top: 3px;' id='limiteSinistro'></div>
							</td>
						</tr>
						<tr>
							<td>In&iacute;cio de Vig&ecirc;ncia:</td>
							<td>
								<div id="inicioVig"></div>
							</td>
							<td>Participa&ccedil;&atilde;o do Ressegurador:</td>
							<td>
								<div style='margin-top: 3px;' id='partResseg'></div>
							</td>
						</tr>
						<tr>
							<td>Fim de Vig&ecirc;ncia:</td>
							<td>
								<div id="fimVig"></div>
							</td>
							<td>Participa&ccedil;&atilde;o da Caixa Seguradora:</td>
							<td>
								<div style='margin-top: 3px;' id='partCaixa'></div>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
					</table>
			<%
				}
			%>	
				
				<br />
				<br />
				<table id="tabelaCobertura" class="tabelas" width="80%">
					<thead>
						<tr>
							<td>&nbsp;</td>
							<td>Coberturas</td>
							<td>LMI</td>
							<td>Franquias</td>
						</tr>
					</thead>
					<tbody>
						<%
						if (request.getAttribute("alterar") != null ){
							List<CoberturasVO> dadosRelatorioCoberturas = (List<CoberturasVO>) request.getAttribute("coberturasRelatorioSalvo");
							
							for (int i = 1; i < lista.size(); i++) {
						%>
								<tr>
									<td><input type="checkbox" name="cobertura" value="<%=lista.get(i).getIdCobertura()%>" 
									<%
									for(int k = 0 ; k < dadosRelatorioCoberturas.size();k++ ){
										if (lista.get(i).getIdCobertura() == dadosRelatorioCoberturas.get(k).getIdCobertura()){
											out.print("checked");
										}
									}
									%>
									></td>
									<td><%=lista.get(i).getCobertura()%></td>
									<td><div style='margin-top: 3px;' id='<%=lista.get(i).getIdCobertura()%>'></div></td>
									<td><%=lista.get(i).getFranquia()%></td>
								</tr>
						<%
							}
						}else{
						
							for (int i = 1; i < lista.size(); i++) {
						%>
						<tr>
							<td><input type="checkbox" name="cobertura" value="<%=lista.get(i).getIdCobertura()%>"></td>
							<td><%=lista.get(i).getCobertura()%></td>
							<td><div style='margin-top: 3px;' id='<%=lista.get(i).getIdCobertura()%>'></div></td>
							<td><%=lista.get(i).getFranquia()%></td>
						</tr>
						<%	
							}
						}
						%>
					</tbody>
				</table>
				<script type="text/javascript">
				
		
				
					checkboxes = document.getElementsByName("cobertura");

					for (var i = 0; i < checkboxes.length; i++) {
						var checkbox = checkboxes[i];
						var todos = [];
						checkbox.onclick = function() {
							var currentRow = this.parentNode.parentNode;
							var secondColumn = currentRow
									.getElementsByTagName("td")[1];
							var fourthColumn = currentRow
									.getElementsByTagName("td")[3];
//							alert(currentRow.rowIndex + 1);
	//						alert("My text is: " + secondColumn.textContent);
		//					alert(currentRow.getElementsByTagName("td")[2].textContent);
							//alert("My text is:" + fourthColumn.textContent);
							//alert(currentRow.getElementsByTagName("input")[0].checked);
							var checado = currentRow.getElementsByTagName("input")[0].checked;
							//alert(checado);
							//alert(checado.value);
							if(checado){
								$("#"+(currentRow.rowIndex + 1)).jqxNumberInput({
									disabled : false
								});
							}else{
								$("#"+(currentRow.rowIndex + 1)).jqxNumberInput({
									disabled : true,
									value : '0'
								});	
							}
						};
					}
				</script>
				<br />
				<br />
				<table id="tabelaParecer" class="tabelas" width="60%">
					<tr>
						<td>Parecer T&eacute;cnico:</td>
						<td>
							<div id="editor1" width="300"></div>
						</td>
					</tr>
				</table>
				<br />
				<br />
				<%
				if (request.getAttribute("alterar") != null ){
				%>
					<input type="submit" id="btnGerar" value="Alterar Relat&oacute;rio" onClick="alteraValores();" />
				<%
				}else{
				%>
					<input type="submit" id="btnGerar" value="Gerar Relat&oacute;rio" onClick="pegaValores();" />
				<%
				}
				%>
			</form>
			<br>
			<script>
			<%
				if (request.getAttribute("alterar") != null ){
			%>	
					function alteraValores(){
						
						var tipo = document.createElement("input");
						tipo.setAttribute("type", "hidden");
						tipo.setAttribute("name", "tipo");
						tipo.setAttribute("value", "relAceitacaoAlterar");
						document.getElementById("testForm").appendChild(tipo);
						
						var nAceitacao = document.createElement("input");
						nAceitacao.setAttribute("type", "hidden");
						nAceitacao.setAttribute("name", "nAceitacao");
						nAceitacao.setAttribute("value", $('#nAceitacao').val());
						document.getElementById("testForm").appendChild(nAceitacao);
						
						//======================
						//campos numericos
						//======================
						var numerico1 = document.createElement("input");
						numerico1.setAttribute("type", "hidden");
						numerico1.setAttribute("name", "is");
						numerico1.setAttribute("value", $('#is input').val());
						document.getElementById("testForm").appendChild(numerico1);
						
						var numerico2 = document.createElement("input");
						numerico2.setAttribute("type", "hidden");
						numerico2.setAttribute("name", "premioLiq");
						numerico2.setAttribute("value", $('#premioLiq input').val());
						document.getElementById("testForm").appendChild(numerico2);
						
						var numerico3 = document.createElement("input");
						numerico3.setAttribute("type", "hidden");
						numerico3.setAttribute("name", "premioNet");
						numerico3.setAttribute("value", $('#premioNet input').val());
						document.getElementById("testForm").appendChild(numerico3);
						
						var numerico4 = document.createElement("input");
						numerico4.setAttribute("type", "hidden");
						numerico4.setAttribute("name", "premioRetido");
						numerico4.setAttribute("value", $('#premioRetido input').val());
						document.getElementById("testForm").appendChild(numerico4);
						
						var numerico5 = document.createElement("input");
						numerico5.setAttribute("type", "hidden");
						numerico5.setAttribute("name", "premioCedido");
						numerico5.setAttribute("value", $('#premioCedido input').val());
						document.getElementById("testForm").appendChild(numerico5);
						
						var numerico6 = document.createElement("input");
						numerico6.setAttribute("type", "hidden");
						numerico6.setAttribute("name", "limiteSinistro");
						numerico6.setAttribute("value", $('#limiteSinistro input').val());
						document.getElementById("testForm").appendChild(numerico6);
					
						var numerico7 = document.createElement("input");
						numerico7.setAttribute("type", "hidden");
						numerico7.setAttribute("name", "partResseg");
						numerico7.setAttribute("value", $('#partResseg input').val());
						document.getElementById("testForm").appendChild(numerico7);
						
						var numerico8 = document.createElement("input");
						numerico8.setAttribute("type", "hidden");
						numerico8.setAttribute("name", "partCaixa");
						numerico8.setAttribute("value", $('#partCaixa input').val());
						document.getElementById("testForm").appendChild(numerico8);
						//======================
						//======================
						
						var x = document.createElement("input");
						x.setAttribute("type", "hidden");
						x.setAttribute("name", "combo");
						x.setAttribute("value", $('#jqxWidget input').val());
						document.getElementById("testForm").appendChild(x);
						
						var z = document.createElement("input");
						z.setAttribute("type", "hidden");
						z.setAttribute("name", "inicioVig");
						z.setAttribute("value", $('#inicioVig div input').val());
						document.getElementById("testForm").appendChild(z);
						
						var a = document.createElement("input");
						a.setAttribute("type", "hidden");
						a.setAttribute("name", "fimVig");
						a.setAttribute("value", $('#fimVig div input').val());
						document.getElementById("testForm").appendChild(a);
						
				
						//$('#cobertura').val()
						//alert($('#tabelaCobertura :input[type="checkbox"]:checked').length);
						
						var selectedLanguage = new Array();
						$('#tabelaCobertura :input[type="checkbox"]:checked').each(function() {
							selectedLanguage.push(this.value);
						});
						//alert("Number of selected Languages: "+selectedLanguage.length+"\n"+"And, they are: "+selectedLanguage);
						for(var i = 1; i <= selectedLanguage.length; i++ ){
							eval("var numerico"+i+"= document.createElement('input');");
							eval("numerico"+i+".setAttribute('type', 'hidden');");
							eval("numerico"+i+".setAttribute('id', 'cobertura"+[i-1]+"');");
							eval("numerico"+i+".setAttribute('name', 'cobertura"+[i-1]+"');");
							var valorCampo = $('#'+selectedLanguage[i-1]+' input').val();
							eval("numerico"+i+".setAttribute('value','"+valorCampo+"');");
							eval("document.getElementById('testForm').appendChild(numerico"+i+");");
						}
						
						var a = document.createElement("input");
						a.setAttribute("type", "hidden");
						a.setAttribute("name", "editor1");
						a.setAttribute("value", escape($('#editor1').val()));
						document.getElementById("testForm").appendChild(a);
					}
			<%
				}else{
			%>
					function pegaValores(){
						
						var tipo = document.createElement("input");
						tipo.setAttribute("type", "hidden");
						tipo.setAttribute("name", "tipo");
						tipo.setAttribute("value", "relAceitacao");
						document.getElementById("testForm").appendChild(tipo);
						
						//======================
						//campos numericos
						//======================
						var numerico1 = document.createElement("input");
						numerico1.setAttribute("type", "hidden");
						numerico1.setAttribute("name", "is");
						numerico1.setAttribute("value", $('#is input').val());
						document.getElementById("testForm").appendChild(numerico1);
						
						var numerico2 = document.createElement("input");
						numerico2.setAttribute("type", "hidden");
						numerico2.setAttribute("name", "premioLiq");
						numerico2.setAttribute("value", $('#premioLiq input').val());
						document.getElementById("testForm").appendChild(numerico2);
						
						var numerico3 = document.createElement("input");
						numerico3.setAttribute("type", "hidden");
						numerico3.setAttribute("name", "premioNet");
						numerico3.setAttribute("value", $('#premioNet input').val());
						document.getElementById("testForm").appendChild(numerico3);
						
						var numerico4 = document.createElement("input");
						numerico4.setAttribute("type", "hidden");
						numerico4.setAttribute("name", "premioRetido");
						numerico4.setAttribute("value", $('#premioRetido input').val());
						document.getElementById("testForm").appendChild(numerico4);
						
						var numerico5 = document.createElement("input");
						numerico5.setAttribute("type", "hidden");
						numerico5.setAttribute("name", "premioCedido");
						numerico5.setAttribute("value", $('#premioCedido input').val());
						document.getElementById("testForm").appendChild(numerico5);
						
						var numerico6 = document.createElement("input");
						numerico6.setAttribute("type", "hidden");
						numerico6.setAttribute("name", "limiteSinistro");
						numerico6.setAttribute("value", $('#limiteSinistro input').val());
						document.getElementById("testForm").appendChild(numerico6);
					
						var numerico7 = document.createElement("input");
						numerico7.setAttribute("type", "hidden");
						numerico7.setAttribute("name", "partResseg");
						numerico7.setAttribute("value", $('#partResseg input').val());
						document.getElementById("testForm").appendChild(numerico7);
						
						var numerico8 = document.createElement("input");
						numerico8.setAttribute("type", "hidden");
						numerico8.setAttribute("name", "partCaixa");
						numerico8.setAttribute("value", $('#partCaixa input').val());
						document.getElementById("testForm").appendChild(numerico8);
						//======================
						//======================
						
						var x = document.createElement("input");
						x.setAttribute("type", "hidden");
						x.setAttribute("name", "combo");
						x.setAttribute("value", $('#jqxWidget input').val());
						document.getElementById("testForm").appendChild(x);
						
						var z = document.createElement("input");
						z.setAttribute("type", "hidden");
						z.setAttribute("name", "inicioVig");
						z.setAttribute("value", $('#inicioVig div input').val());
						document.getElementById("testForm").appendChild(z);
						
						var a = document.createElement("input");
						a.setAttribute("type", "hidden");
						a.setAttribute("name", "fimVig");
						a.setAttribute("value", $('#fimVig div input').val());
						document.getElementById("testForm").appendChild(a);
						
				
						//$('#cobertura').val()
						//alert($('#tabelaCobertura :input[type="checkbox"]:checked').length);
						
						var selectedLanguage = new Array();
						$('#tabelaCobertura :input[type="checkbox"]:checked').each(function() {
							selectedLanguage.push(this.value);
						});
						//alert("Number of selected Languages: "+selectedLanguage.length+"\n"+"And, they are: "+selectedLanguage);
						for(var i = 1; i <= selectedLanguage.length; i++ ){
							eval("var numerico"+i+"= document.createElement('input');");
							eval("numerico"+i+".setAttribute('type', 'hidden');");
							eval("numerico"+i+".setAttribute('id', 'cobertura"+[i-1]+"');");
							eval("numerico"+i+".setAttribute('name', 'cobertura"+[i-1]+"');");
							var valorCampo = $('#'+selectedLanguage[i-1]+' input').val();
							eval("numerico"+i+".setAttribute('value','"+valorCampo+"');");
							eval("document.getElementById('testForm').appendChild(numerico"+i+");");
						}
						
						var a = document.createElement("input");
						a.setAttribute("type", "hidden");
						a.setAttribute("name", "editor1");
						a.setAttribute("value", escape($('#editor1').val()));
						document.getElementById("testForm").appendChild(a);
					}
			<%
				}
			%>
			
			function validador(){
				var selectedLanguage = new Array();
				$('#tabelaCobertura :input[type="checkbox"]:checked').each(function() {
					selectedLanguage.push(this.value);
				});
				
				$("#nProposta").on('change', 
						function (event) {
						var type = event.args.type; // keyboard, mouse or null depending on how the value was changed.
						var value = $('#nProposta').val();
						alert(value);
					});
				
				var nProposta = $('#nProposta').val();
				nProposta = nProposta.replace(/[^0-9\.]+/g, '');
				
				if(selectedLanguage.length == 0){
					alert("Por gentileza selecione alguma cobertura.");
					return false;
				}else if(nProposta == '' || $('#segurado').val() == '' || $('#localRisco').val() == '' || $('#cpf').val() == '' || $('#editor1').val() == '' ){
					
					if(nProposta == ''){
						$('#nProposta').focus();
						alert('Campo \'N.º da Proposta\' deve ser preenchido com números.');
						return false;
					}else{
						alert("Por gentileza preencha todos os campos.");
						return false;
					}
				}
				else{
					return true;
				}
			}
			</script>
			<%@ include file="importBody_JQplot.jsp"%>
		</div>
		<!-- end id="example-content"  -->
	</div>
	<%@ include file="importBodyBootstrap.jsp"%>
</body>
</html>