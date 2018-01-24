<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="caixa.dirid.VO.SensibilizacaoVO"%>
<%@ page import="caixa.dirid.UTEIS.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<!-- Compatibilidade com IE antigo -->
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		
		<meta charset="utf-8">
		<meta name="author" content="phelipe662@gmail.com">
		<title>Sensibiliza&ccedil;&atilde;o</title>
		<%@ include file="importHeadBootstrap.jsp"%>
		
		<!-- import do Jquery do pagination-->
		<%@ include file="importHead_JqPagination.jsp"%>
		
		<link rel="stylesheet" type="text/css" href="css/aparencia.css"/>
		<link href="imagens/caixaFav.ico" rel="shortcut icon" type="image/x-icon"/>
		
		
		<style type="text/css">

			ul li a{
				text-decoration: none;
				color: black;
			}

			ul li a:hover{
				text-decoration: none;
				color: red;
			}

			ul li a span{
				font-size: 30px;	
			}

			#resultFatuMensal{
				color:black;
			}
			
			table tr td {
  			   text-align: center;
 			}
 			
 			#legenda{
 				position: fixed;
			    margin-top: 50px;
 			}
 			
 			#filters{
 				visibility:hidden;
 			}
 		
		</style>
		
		<script language="javascript" type="text/javascript" src="variavel_ip_da_aplicacao.js"></script>
		<script language="javascript" type="text/javascript" src="AjaxJS/funcoesAjax.js"></script>

		
		<script>
		$(document).ready(function(){
			
			$('#tipo').on('change', function(){
				var mes = $(this).val();
				var ano = $('#tipoAno').val();
				//alert($('#tipoAno').val());
				dadosSensibilizacao(ano,mes,"&categ=vendasNovas");
				$('#filters').css("visibility", "visible");
			});
			
			$('#tipoAno').on('change', function(){
				var ano = $(this).val();
				var mes = $('#tipo').val();
				//alert($('#tipo').val());
				dadosSensibilizacao(ano,mes,"&categ=vendasNovas");
			});
			
			$('.category').on('change', function(){
				var category_list = [];
				var mes = $('#tipo').val();
				var ano = $('#tipoAno').val();
				$('#filters :input:checked').each(function(){
					var category = $(this).val();
					//alert(category);
					category_list.push(category);
				});
				
				if(category_list.length == 0){
					$('.resultblock').fadeIn();
				}else {
			    	//variavel ip_porta vem do arquivo variavel_ip_da_aplicacao.js
					var url = "http://"+ip_porta+"/ProjetoCaixa/visaoOperacional?tipo=sensibilizacao&categ=vendasNovas&pagina=1&mesSensibilizacao="+mes+"&anoSensibilizacao="+ano;
					for(var i = 0; i < category_list.length; i++){
						
						if(i == 0){
							url += "&prod=t&cod=";
						}
						
						if(category_list.length == 1){
							url += category_list[i];
						}else{
							url += category_list[i]+"p";
						}
			    		
					}
					
					$.ajax({
						url: url,
						global: false,
						type: "GET",
						//data: (dadosTratados),
						cache: false,
						beforeSend: function() {
							$('#tabelaAjax').html("<span class='carregando'>Aguarde...</span><br>&nbsp;&nbsp;&nbsp;<img class='carregando' src='imagens/loader.gif'/>");
						},
						success: function(html) {
							$('#tabelaAjax').html(html);
						}
					});
				}
			});
		});
		</script>
	</head>
	
	<body>
		<%@ include file="cabecBarraAzul.jsp"%>
		<div class="container" style="padding-left: 0px; padding-right: 0px;">
			<%@ include file="cabecImageCaixa.jsp"%>
			<%@ include file="menuEsquerdo.jsp"%>	

			<div class="content">
				<div class="page-header" style="margin-top: 0px; padding-bottom: 10px; margin-bottom: 0px; border-bottom-width: 0px;">
					<h2>Relat&oacute;rio de Sensibiliza&ccedil;&atilde;o</h2>
					<h4 style="color:red">Vendas Novas</h4>
					<hr style="margin-top: 0px; margin-bottom: 0px;">
				</div>
				
				<div style="padding-left: 15px;">
					<table>
						<tr>
							<td>
								<div id="filters" style="margin-left: 60px;">
									<div class="filterblock">
										<input id ="check1" type="checkbox" name="check" value="1403" class="category">
										<label for="check1">1403::Residencial</label>
									</div>
									
									<div class="filterblock" style="margin-right: -27px;">
										<input id ="check2" type="checkbox" name="check" value="1404" class="category">
										<label for="check2">1404::Residencial Excl.</label>
									</div>
									
									<div class="filterblock" style="margin-left: -22px;">
										<input id ="check3" type="checkbox" name="check" value="1405" class="category">
										<label for="check3">1405::F&aacute;cil RD</label>
									</div>
									
									<div class="filterblock">
										<input id ="check4" type="checkbox" name="check" value="1804" class="category">
										<label for="check4">1804::Empresarial</label>
									</div>
								</div>
							</td>
							<td>
								<div id="comboMeses" align="center" style="margin-left: 280px;">
									Ano:
									<select id="tipoAno">
										<option value="zero">Selecionar</option>
										<option value="2015">2015</option>
										<option value="2016">2016</option>
										<option value="2017">2017</option>
										<option selected value="2018">2018</option>
									</select>
								</div><!-- id="comboMeses" -->
								<br />
								<div id="comboMeses" align="center" style="margin-left: 280px;">
									M&ecirc;s:&nbsp;
									<select id="tipo">
										<option selected value="zero"> Selecionar</option>
										<%
											String[] mesesPagWeb = new Uteis().mesesPaginaWeb();
											for(int d = 0; d < mesesPagWeb.length; d++){
										%>
										
											<%
												if(d<9){
											%>
													<option value="0<%=d+1%>">
											<%
												}else{
											%>								
													<option value="<%=d+1%>">
											<%
												}
											%>
												<%=mesesPagWeb[d]%>
												</option>
										<%
											}
										%>
									</select>
								</div><!-- id="comboMeses" -->
							</td>
						</tr>
					</table>
					<br/>		
				</div>
				<br>
				<br>
				<br>
				<div id="tabelaAjax" style="margin-top: -75px;">
					<!-- img src= "imagens/loader.gif"-->
				</div>
			
			</div> <!-- TAG CONTEUDO -->
		<style>
			.footer{
			  width:100%;
			  height:10px;  
			  display: table; 
			}
			
			p {
			  text-align:left; 
			  vertical-align: left;
			  display: table-cell;   
			}
			#duvida {
			  text-align:right; 
			  vertical-align: middle;
			  display: table-cell;   
			}
		</style>
			<div class="footer">
				<p>Diretoria de Riscos Diversos - DIRID | GERID</p>
				<p id="duvida">Em caso de problemas ou dúvidas, entrar em contato com o ramal 2370. Phelipe.</p>
			</div><!-- end .footer -->
		</div><!-- end .container -->
	<%@ include file="importBodyBootstrap.jsp"%>
	</body>
</html>