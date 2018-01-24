<%@ page import="caixa.dirid.VO.RvneVO"%>
<%@ page import="caixa.dirid.VO.FaturamentoVO"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="caixa.dirid.UTEIS.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<!-- Compatibilidade com IE antigo -->
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		
		<meta charset="utf-8">
		
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
		<meta name="author" content="phelipe662@gmail.com">
		<link href="imagens/caixaFav.ico" rel="shortcut icon" type="image/x-icon">
		<title>Visão Executiva</title>
		<%@ include file="importHeadBootstrap.jsp"%>
		<%@ include file="importHead_JQplot.jsp"%>
		<%@ include file="importHead_JQwidgets.jsp"%>
		
		<script type="text/javascript" src="AjaxJS/funcoesAjax.js"></script>


		<script type="text/javascript">
			$(document).ready(function () {
				//alert('Página em manutenção. Qualquer dúvida ligar no ramal 2370. Phelipe.');
				//teste();
				// Handler for .ready() called.

				$('#selectAno').on('change', function(){
					var ano = $(this).val();
					var produto = $('#jqxTree').jqxTree('getSelectedItem');
					trocaGrafico(ano,produto.value);
				});
				
				
				
				var source = [
					{ icon: "imagens/dirid.png", label: "DIRID", value:"reqFaturamentoDirid", expanded: false, items: 
						[
							{ icon: "imagens/auto.png", label: "Automóvel", value:"reqFaturamentoAuto", items:
								[
								    { icon: "imagens/auto.png", label: "Auto Tranquilo Exclusivo",value:"reqFaturamentoAutoTranqExclu"},
								    { icon: "imagens/auto.png", label: "Auto Tranquilo Correntista",value:"reqFaturamentoAutoTranqCorren"},
								    { icon: "imagens/auto.png", label: "Auto Tranquilo Frota",value:"reqFaturamentoAutoTranqFrota"},
								    { icon: "imagens/auto.png", label: "Auto Fácil",value:"reqFaturamentoAutoFacil"}
								]
							},
							{ icon: "imagens/pj.png", label: "RDPJ", value:"reqFaturamentoRDPJ", items:
								[
									{ icon: "imagens/pj.png", label: "RD Equipamentos",value:"reqFaturamentoRdEquipamentos"},
									{ icon: "imagens/pj.png", label: "MR Empresarial",value:"reqFaturamentoEmpresarial"},
									{ icon: "imagens/pj.png", label: "MR CCA",value:"reqFaturamentoCCA"},
									{ icon: "imagens/pj.png", label: "MR Lotérico",value:"reqFaturamentoLoterico"},
								]
							},
							{ icon: "imagens/pf.png", label: "RDPF",value:"reqFaturamentoRDPF", items:
								[
									{ icon: "imagens/pf.png", label: "F&aacute;cil Residencial", value:"reqFaturamentoFacilRd"},
									{ icon: "imagens/pf.png", label: "MR Residencial Correntista", value:"reqFaturamentoRdCorrentista"},
									{ icon: "imagens/pf.png", label: "MR Residencial Exclusivo", value:"reqFaturamentoRdEconomiario"},
									{ icon: "imagens/pf.png", label: "Lar Mais", value:"reqFaturamentoLarMais"},
									{ icon: "imagens/pf.png", label: "MR Residencial Aporte Caixa", value:"reqFaturamentoAporte"},
									{ icon: "imagens/pf.png", label: "RD PF Outros", value:"reqFaturamentoRdPfOutros"}
								]
							}
						]
					}
				];
				
				// create jqxTree
				$('#jqxTree').jqxTree({ source: source, enableHover: true });
				
				//$('#Events').jqxPanel({ height: '250px', width: '170px' });
				
				//$('#Events').css('border', 'none');
				
				// on to 'expand', 'collapse' and 'select' events.
				$('#jqxTree').on('expand', function (event) {
					var args = event.args;
					var item = $('#jqxTree').jqxTree('getItem', args.element);
					//$('#Events').jqxPanel('prepend', '<div style="margin-top: 5px;">Expanded: ' + item.label + '</div>');
				});
				
				$('#jqxTree').on('collapse', function (event) {
					var args = event.args;
					var item = $('#jqxTree').jqxTree('getItem', args.element);
					//$('#Events').jqxPanel('prepend', '<div style="margin-top: 5px;">Collapsed: ' + item.label + '</div>');
				});
				
				$('#jqxTree').on('select', function (event) {
					var args = event.args;
					var item = $('#jqxTree').jqxTree('getItem', args.element);
					
					var produto = item.value;
					var ano = $('#selectAno').val();
					
					//alert(produto);
					trocaGrafico(ano, produto);
					//$('#Events').jqxPanel('prepend', '<div style="margin-top: 5px;">Selected: ' + item.label + '</div>');
				});
				
				
			}); 

			//function trocaGrafico(ano,produto){
			//	if(ano !== "zero" && produto !== "zero"){
			//		var url = "visaoExecutiva?ano="+ano+"&tipo=";
			//		var id = document.getElementById("selectForm");
			//		var Index = id.selectTipo.options[id.selectTipo.selectedIndex].value;
			//		//alert(url + Index);
			//		id.action = url + Index;
			//		id.submit();
			//	}
			//}
			
			function trocaGrafico(ano,produto){
				
				if(ano !== "zero" && produto !== "zero"){
					
					var url = "visaoExecutiva?ano="+ano+"&tipo=";
					var id = document.getElementById("selectForm");
					var Index = produto;
								
					$.ajax({
						// have to use synchronous here, else the function 
					    // will return before the data is fetched
						async: false,
						url: "http://"+ip_porta+"/ProjetoCaixa/"+url+Index,
						global: false,
						type: "POST",
						//data: (dadosTratados),
						cache: false,
						beforeSend: function() {
							$('#tabelaAjax').html("<span style='color:#10CEF0;margin-left: 145px;'>Aguarde...</span>&nbsp;&nbsp;&nbsp;<br /><img style='margin-left: 153px;height: 30px;width: 30px;' src='imagens/loader.gif'/>");
						},
						success: function(html) {
							$('#tabelaAjax').html(html);
							
							//alert('hmmmSucess');
							
						},
						complete: function(){
							
					
							//alert('hmmmComplete');
							graficos();
						},
						error: function(XMLHttpRequest, textStatus, errorThrown) {
					        //alert("Status: " + textStatus);
					        //alert("Error: " + errorThrown); 
					        $('#tabelaAjax').html("<span style='color:red;margin-left: 136px;'>ERRO!.</span><br>");
					    }    
					});
				}
			}
			
			function reverse (s) {
				for (var i = s.length - 1, o = ''; i >= 0; o += s[i--]) { }
				return o;
			}
			
			String.prototype.insert = function (index, string) {
				if (index > 0)
					return this.substring(0, index) + string + this.substring(index, this.length);
				else
					return string + this;
			};
			
			function pontuaNumero(numero){
				if(numero.length > 3){
					var primeiroPonto = reverse(numero);
					return reverse(primeiroPonto.insert(3,"."));
				}else{
					return numero;
				} 
			}
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
	
	        table.stats-table td.tooltip-header, table.highlighted-stats-table td.tooltip-header {
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
			
			.corLink{
				color: white;
			}
			
			.interno {
				float: left;
			}

			.navbar{
				background-image: linear-gradient(to bottom, #0080c0 0px, #002855 100%);
				background-repeat: repeat-x;
				box-shadow: 0px 3px 9px rgba(0, 0, 0, 0.25) inset;
			}
			
			#botaoHome{
				background-image: linear-gradient(to bottom, #BC0808 0px, #CF4B4B 100%);
				background-repeat: repeat-x;
				box-shadow: 0px 3px 9px rgba(0, 0, 0, 0.25) inset;
			}

			#titulo{
				color: white;
			}
			
			.square {
				width: 10px;
				height: 10px;
			}
			
			body {
				background-image:url("imagens/informeDirid/fundo.jpg");
			}
		
		</style>
	</head>

	<body role="document" background="imagens/fundo.jpg">
		<nav class="navbar navbar-inverse navbar-fixed-top">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<span class="navbar-brand" id="titulo">DIRID - Todos os Produtos</span>
				</div>
				<div id="navbar" class="navbar-collapse collapse">
					<ul class="nav navbar-nav">
						<li class="active" id="botaoHome">
							<a href="pagiMenuPrincipal.jsp">Menu</a>
						</li>
					</ul>
					<ul class="nav navbar-nav">
						<li id="botaoDiario">
							<a href="pagiGraficoDiario.jsp"><span>Acompanhamento Di&aacute;rio</span></a>
						</li>
					</ul>
				</div>
			</div>
		</nav>
		<div class="container theme-showcase" role="main" style="padding-left: 0px;">
			<br>
			<div class="page-header">
				<h2>Gest&atilde;o &agrave; Vista - Faturamento - <span style="color:blue;">Acompanhamento Mensal</span></h2>
				<!--  <br>
				<h3><small>Autom&oacute;vel - Faturamento</small></h3>-->
			</div>
			<form action="visaoExecutiva?tipo=" id="selectForm">
				<div id="comboAno" align="center" style="margin-left: 0px; margin-right: 225px;">
					Ano:&nbsp;
					<select id="selectAno" name="ano">
						<option selected value="zero"> Selecionar</option>
						<option value="2017">2017</option>
						<option value="2016">2016</option>
						<option value="2015">2015</option>
					</select>
				</div>
				<div id="comboProduto" align="center" style="margin-left: 145px; margin-bottom: 0px; margin-top: -22px;">
					<div style='float: left;margin-left: 400px;'>
						<span style="float:left">Produto:&nbsp;</span>
						<div id='jqxTree' style='float: left; '>
						</div>
						
						<!-- div style='margin-left: 20px; float: left;'>
						<!--  div>
						<span>
						Events:</span>
						<div id='Events'>
						</div>
						</div ->
						</div -->
					</div>
					<!--  select id="selectTipo" name="tipo">
						<option selected value="zero"> Selecionar</option>
						<option value="reqFaturamentoAuto">Autom&oacute;vel</option>
						<option value="reqFaturamentoLoterico">Lot&eacute;rico</option>
						<option value="reqFaturamentoCCA">CCA</option>
						<option value="reqFaturamentoRDPJ">RD PJ</option>
						<option value="reqFaturamentoRDPF">RD PF</option>
					</select -->
				</div>
				
			</form>
			
			
			<!-- Trecho do funcionamento do gráfico -->
			<div class="col1" id="example-content">
				<!-- div>
					<span>Moused Over: </span>
					<span id="info2">Nothing</span>
				</div-->
				<br>
				<div id="tabelaAjax">
				
				</div>
				<%@ include file="importBody_JQplot.jsp"%>
	
			</div><!-- end id="example-content"  -->


		</div>
		<%@ include file="importBodyBootstrap.jsp"%>
	</body>
</html>