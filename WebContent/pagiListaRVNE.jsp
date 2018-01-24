<%@ page import="caixa.dirid.VO.RvneVO"%>
<%@ page import="caixa.dirid.UTEIS.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<!-- Compatibilidade com IE antigo -->
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		
		<meta charset="utf-8">
		<meta name="author" content="phelipe662@gmail.com">
		
		<title>RVNE</title>
		<%@ include file="importHeadBootstrap.jsp"%>
		
		<!-- import do Jquery -->
		<script type="text/javascript" src="jquery.jqplot.1.0.8r1250/dist/jquery.js"></script>
		<script language="javascript" type="text/javascript" src="variavel_ip_da_aplicacao.js"></script>
		
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
			.botoes{
				margin-right: 10px;
			}
			.button {
				width: 16px;
				heigth: 5px;
			}
			.button:hover {
				border-top-color: #e1e1e1;
				background: #dfdfdf;
				color: #ccc;
			}
			.button:active {
				border-top-color: #c0c0c0;
				background: #c0c0c0;
			}
			.alteraExclui{
				text-align: right;
			}
			#carregando{
				height: 30px;
				width: 30px;
			}
		</style>
		
		
		<script type="text/javascript">
			
			// Handler for .ready() called.
			$(document).ready(function(){
			
				$('#tipoAno').on('change', function(){
					var ano = $('#tipoAno').val();
					//alert($('#tipoAno').val());
					
					var url = "http://"+ip_porta+"/ProjetoCaixa/visaoAnalitica?tipo=rvne&ano="+ano;
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
								$('#fieldInserir').hide();
								$('.botoes').hide();
							}
					});

				});
				
			}); 
		
			
			function inserirRVNE(mesNome){
				$('#fieldAlterar').hide();
				$('#fieldInserir').show();
				
				$("#fieldInserir div div input").each(function() {
					$(this).attr('name', mesNome);
				});
			}
			
			
			function inserir(){
				var url = "http://"+ip_porta+"/ProjetoCaixa/visaoAnalitica?";
				url += "valor="+$("#fieldInsere").val();
				url += "&nome="+$("#fieldInsere").attr("name");
				url += "&ano="+$('#tipoAno').val();
				$.ajax({
					url: url,
					global: false,
					type: "POST",
					//data: (dadosTratados),
					cache: false,
					beforeSend: function() {
						$('#tabelaAjax').html("<span class='carregando'>Aguarde...</span><br>&nbsp;&nbsp;&nbsp;<img class='carregando' src='imagens/loader.gif'/>");
					},
					success: function(html) {
						var ano = $('#tipoAno').val();
						
						var url = "http://"+ip_porta+"/ProjetoCaixa/visaoAnalitica?tipo=rvne&ano="+ano;
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
									$('#fieldInserir').hide();
									$('.botoes').hide();
								}
						});
					}
				});
			}
			
			
			function getid(obj) {
				$('#'+obj.id+' span').show();
	        }
			
			
			function outmouse(obj){
				$('.botoes').hide();
			}
			
			
			//Botao buscar valor para alterar
			function selecionarRVNE(obj){
				$('#fieldInserir').hide();
				var idProd = $('#produto_'+obj.id).val();
				var arr = obj.id.split("_");
				var coluna = arr[1].substring(3);
				
				$.ajax({
					url: "http://"+ip_porta+"/ProjetoCaixa/visaoAnalitica?tipo=rvneUnica&id="+idProd+"&coluna="+coluna,
					global: false,
					type: "GET",
					cache: false,
					beforeSend: function() {
						$('#botaoAlterarAjax').html("<img id='carregando' src='imagens/loader.gif'/>");
					},
					success: function(html) {
						$('#botaoAlterarAjax').html(html);
					}
				});				
			}
		
			
			function alterar(){
				var url = "http://"+ip_porta+"/ProjetoCaixa/visaoAnalitica?";
				url += "valor="+$("#fieldAltera").val();
				url += "&nome="+$("#fieldAltera").attr("name");
				url += "&ano="+$('#tipoAno').val();
				$.ajax({
					url: url,
					global: false,
					type: "POST",
					//data: (dadosTratados),
					cache: false,
					beforeSend: function() {
						$('#tabelaAjax').html("<span class='carregando'>Aguarde...</span><br>&nbsp;&nbsp;&nbsp;<img class='carregando' src='imagens/loader.gif'/>");
					},
					success: function(html) {
						var ano = $('#tipoAno').val();
						//alert($('#tipoAno').val());
						
						var url = "http://"+ip_porta+"/ProjetoCaixa/visaoAnalitica?tipo=rvne&ano="+ano;
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
									$('#fieldInserir').hide();
									$('.botoes').hide();
								}
						});
					}
				});
			}
			
			//Botao excluir
			function excluirRVNE(obj){
				if (confirm('Deseja realmente excluir?')){
					var idProd = $('#produto_'+obj.id).val();
					var arr = obj.id.split("_");
					var coluna = arr[1].substring(3);
					
					url = "http://"+ip_porta+"/ProjetoCaixa/visaoAnalitica?tipo=delete&id="+idProd+"&coluna="+coluna;
					
					$.ajax({
						url: url,
						global: false,
						type: "POST",
						//data: (dadosTratados),
						cache: false,
						beforeSend: function() {
							$('#tabelaAjax').html("<span class='carregando'>Aguarde...</span><br>&nbsp;&nbsp;&nbsp;<img class='carregando' src='imagens/loader.gif'/>");
						},
						success: function(html) {
							var ano = $('#tipoAno').val();
							//alert($('#tipoAno').val());
							
							var url = "http://"+ip_porta+"/ProjetoCaixa/visaoAnalitica?tipo=rvne&ano="+ano;
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
										$('#fieldInserir').hide();
										$('.botoes').hide();
									}
							});
						}
					});
				}else{
					return false;
				}
			}
			
		</script>	
	</head>
	<body>
		<%@ include file="cabecBarraAzul.jsp"%>
		<div class="container" style="padding-left: 0px; padding-right: 0px;">
		<%@ include file="cabecImageCaixa.jsp"%>
		<%@ include file="menuEsquerdo.jsp"%>
			
			<div class="content">
				<br>
				<div id="comboMeses" align="center" >
					Ano:
					<select id="tipoAno">
						<option selected value="zero">Selecionar</option>
						<option value="2015">2015</option>
						<option value="2016">2016</option>
						<option value="2017">2017</option>
						<option value="2018">2018</option>
					</select>
				</div><!-- id="comboMeses" -->
				<br>
				<br>
				<br>
				<br>
				<br>
				<div id="tabelaAjax" style="margin-top: -75px;">
					<!-- img src= "imagens/loader.gif"-->
				</div>
			</div> <!-- TAG CONTEUDO -->
			<div class="footer">
				<p>Diretoria de Riscos Diversos - DIRID | Produtos Auto e RD</p>
			</div><!-- end .footer -->
		</div><!-- end .container -->
		<%@ include file="importBodyBootstrap.jsp"%>
	</body>
</html>






