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
			$(window).ready(function(){
				
				$('#tipoAno').on('change', function(){
					var ano = $(this).val();
					//alert($('#tipo').val());
					totalizadorSensibilizacao(ano);
				});
				
			});
			
			function totalizadorSensibilizacao(ano){
				var url = "http://"+ip_porta+"/ProjetoCaixa/visaoOperacional?tipo=sensibilizacao&categ=totalizador&anoParam="+ano;
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
		</script>
	</head>
	
	<body>
		<%@ include file="cabecBarraAzul.jsp"%>
		<div class="container" style="padding-left: 0px; padding-right: 0px;">
			<%@ include file="cabecImageCaixa.jsp"%>
			<%@ include file="menuEsquerdo.jsp"%>	

			<div class="content">
				<div class="page-header" style="margin-top: 0px; padding-bottom: 10px; margin-bottom: 0px; border-bottom-width: 0px;">
					<h2>Totalizador Sensibiliza&ccedil;&atilde;o</h2>
				</div>
				<br>
				<br>
				<br>
		
				<div style="padding-left: 15px;">
					<table>
						<tr>
							<td>
								<div id="comboMeses" align="center" style="margin-left: 280px;">
									Ano:
									<select id="tipoAno">
										<option selected value="zero">Selecionar</option>
										<option value="2016">2016</option>
										<option value="2017">2017</option>
										<option value="2018">2018</option>
									</select>
								</div><!-- id="comboMeses" -->
							</td>
						</tr>
					</table>
					<br/>		
				</div>
				
				<br>
				
				<div id="tabelaAjax" style="margin-top: -75px;">
					<!-- img src= "imagens/loader.gif"-->
				</div>
				
				<br/>
				<br/>
						
				<div id="tabelaAjaxRenovacao">
				
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