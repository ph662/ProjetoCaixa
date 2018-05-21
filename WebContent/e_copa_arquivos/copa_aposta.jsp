<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="caixa.dirid.VO.CopaUsuarioVO"%>
<%@ page import="caixa.dirid.VO.CopaPartidasVO"%>
<%@ page import="caixa.dirid.VO.CopaApostasRealizadasVO"%>
<%@ page import="caixa.dirid.UTEIS.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<%
		/***********/
		/*VARIAVEIS*/
		/***********/
	
		//Recebem o objeto "VO" da servlet "VisaoExecutivaServlet.java"
		CopaUsuarioVO usuario = (CopaUsuarioVO) request.getAttribute("objetoUsuario");
	
		String login = (String) request.getAttribute("usuario");
		String senha = (String) request.getAttribute("senha");
		
		List<CopaPartidasVO> partidas = (List<CopaPartidasVO>) request.getAttribute("dadosPartidas");
		List<CopaApostasRealizadasVO> apostasRealizadas = (List<CopaApostasRealizadasVO>) request.getAttribute("apostasRealizadas");	
		
		List<CopaPartidasVO> partidasF2 = (List<CopaPartidasVO>) request.getAttribute("dadosPartidasF2");
		List<CopaApostasRealizadasVO> apostasRealizadasF2 = (List<CopaApostasRealizadasVO>) request.getAttribute("apostasRealizadasF2");
		
		List<CopaPartidasVO> partidasF3 = (List<CopaPartidasVO>) request.getAttribute("dadosPartidasF3");
		List<CopaApostasRealizadasVO> apostasRealizadasF3 = (List<CopaApostasRealizadasVO>) request.getAttribute("apostasRealizadasF3");
		
		List<CopaPartidasVO> partidasF4 = (List<CopaPartidasVO>) request.getAttribute("dadosPartidasF4");
		List<CopaApostasRealizadasVO> apostasRealizadasF4 = (List<CopaApostasRealizadasVO>) request.getAttribute("apostasRealizadasF4");
	
		List<CopaPartidasVO> partidasF5 = (List<CopaPartidasVO>) request.getAttribute("dadosPartidasF5");
		List<CopaApostasRealizadasVO> apostasRealizadasF5 = (List<CopaApostasRealizadasVO>) request.getAttribute("apostasRealizadasF5");
		
		List<CopaPartidasVO> partidasF6 = (List<CopaPartidasVO>) request.getAttribute("dadosPartidasF6");
		List<CopaApostasRealizadasVO> apostasRealizadasF6 = (List<CopaApostasRealizadasVO>) request.getAttribute("apostasRealizadasF6");
		
		
				
		
		
		
		String dataSistema = (String) request.getAttribute("dataHoje");
		
		if(usuario.getPermissaoBean() == 1){
			dataSistema="2014-06-12";
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dataSistemaObj = sdf.parse(dataSistema);

	%>
	

<head>
	<script type="text/javascript" src="jquery.jqplot.1.0.8r1250/dist/jquery.js"></script>
	
	<meta charset="ISO-8859-1">
	<meta http-equiv=Content-Type content="text/html; charset=windows-1252">
	<meta name=ProgId content=Excel.Sheet>
	<meta name=Generator content="Microsoft Excel 15">
	<link rel=Stylesheet href=e_copa_arquivos/stylesheet.css>
	
	<style>
		#externo {
			display: table;
			width: 100%;
		}
		
		.interno {
			float: left;
			width:49%;
		}
		
		#cadastroUser{
			display:none;
		}
		
		#mudarSenha{
			display:none;
		}
	</style>
	
	<script type="text/javascript">
		$(function() {
			//alert('Página em manutenção, as apostas não serão gravadas, aguarde um momento!');
			if('<%=senha%>' == 'copa2018'){
				alert('Lembre-se de alterar a sua senha!');
			}
		});
		
		function visualizarDados(){
			var usuario = '<%=login%>';
			var senha = '<%=senha%>';
			
			teste = '<%=senha%>';
			alert('Usuário: '+usuario+'\nSenha: '+teste);
		}
		
		function mostraQuadroAlterarSenha(){
			
			$('#mudarSenha').show();
			
		}
		
		function alterarSenha(){
			var senhaAtual = $('#senhaAtual').val();
			var novaSenha = $('#novaSenha').val();
			var novaSenhaRepeat = $('#novaSenhaRepeat').val();
			var idUser = <%=usuario.getIdBean()%>;
			if(senhaAtual == '<%=senha%>'){
				if(novaSenha != '' && novaSenhaRepeat != ''){
					if(novaSenha == novaSenhaRepeat){
						
						$.ajax({
							url: "copaServlet?tipo=alterarSenha&novaSenha="+novaSenhaRepeat+"&id="+idUser,
							global: false,
							type: "POST",
							cache: false
						});
						
						alert('Senha alterada com sucesso!');
						window.location.href = "e_copa_arquivos/copa_menu.htm";
						//$('#senhaAtual').val(''); 
						//$('#novaSenha').val('');
						//$('#novaSenhaRepeat').val('');
						//$('#mudarSenha').hide();
						//teste = novaSenhaRepeat;
					}else{
						alert('Senhas novas não coincidem.');
					}
				}else{
					alert('Digite a nova senha!');
				}
				
			}else{
				alert('Senha atual e senha digitada não coincidem.')
			}
			
		}
		
		function fecharMudaSenha(){
			$('#senhaAtual').val(''); 
			$('#novaSenha').val('');
			$('#novaSenhaRepeat').val('');
			$('#mudarSenha').hide();
		}
	
		<%if(usuario.getPermissaoBean() == 1){%>
		
				function gravaValoresAdmin(){
					var url="copaServlet";
					var value="id="+<%=usuario.getIdBean()%>;
					
					var input, index;
					var textInputs = [];
					var unfiltered = document.getElementsByTagName("input");
			        
					var i = unfiltered.length;
					
				    while(i--) {
				        input = unfiltered[i];
				        if (!input.type || input.type === 'text'){
				            textInputs.push(input);
				        }
				    }
					
				    //alert(textInputs.length);
					for (index = 0; index < textInputs.length; index++) {
						input = textInputs[index];
						if(input.value != ''){
							value+="&"+input.id+"="+input.value;
							//alert(input.value);
							//alert(input.id);
						}
					}
					//alert(url);		
					value+="&admin=t&tipo=gravar";
					var request = new XMLHttpRequest();
					request.open('POST', url, true);
					request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
					//alert(value);
					request.send(value);
					
					alert('Valores gravados com sucesso, o resultado final foi calculado!');
					//window.location.href = "e_copa_arquivos/copa_menu.htm";
				}
				
				function abrirTelaCriacao(){
					$('#cadastroUser').show();
				}
				
				function fecharCadastro(){
					
					$('#cadastroUser').hide();
					
				}
				
				function visualizarTodasApostas(){
					
					var num = "";
	    			sessionStorage.setItem("numeroAceitacao", num); 
	    			window.open('copaServlet?tipo=verTodasApostas','_blank','scrollbars=yes,resizable=yes,top=100,left=70,width=1000,height=500');
					
				}
				
				function cadastrarUsuario(){
					
					var login = $('#login').val();
					var nome = $('#nome').val(); 
					var senha = $('#senha').val();
					
					if ((login == null || login == "") || (nome == null || nome == "") || (senha == null || senha == "") ) {
						alert("Preencha tudo!");
						return false;
					}else{
						
						$.ajax({
							url: "copaServlet?tipo=cadastrarUsuario&nLoginAdmin="+login+"&nNomeAdmin="+nome+"&nSenhaAdmin="+senha,
							global: false,
							type: "POST",
							cache: false
						});
						
						alert("Usuário: "+login+"\nNome: "+nome+"\nSenha: "+senha+"\n\nCadastrado com sucesso!");
						
						$('#login').val('');
						$('#nome').val('');
						$('#senha').val('');
						$('#cadastroUser').hide();
						return true;
					}
					
					
				}
				
				
		<%}else{%>
				function gravaValores(){
					var url="copaServlet";
					var value="id="+<%=usuario.getIdBean()%>;
					
					var input, index;
					var textInputs = [];
					var unfiltered = document.getElementsByTagName("input");
			        
					var i = unfiltered.length;
					
				    while(i--) {
				        input = unfiltered[i];
				        if (!input.type || input.type === 'text'){
				            textInputs.push(input);
				        }
				    }
					
					for (index = 0; index < textInputs.length; index++) {
						input = textInputs[index];
	
						if(input.value != ''){
							eval('data = document.getElementById("data_'+(input.id.substring(8,input.id.length))+'").innerText;')
							value+="&"+input.id+"="+input.value+"&data_"+(input.id.substring(8,input.id.length))+"="+data;
							//value+="&"+input.id+"="+input.value;
						}
					}
					
					value+='&tipo=gravar';
					var request = new XMLHttpRequest();
					request.open('POST', url, true);
					request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
					
					//alert(value);
					request.send(value);
					
					alert('Valores gravados com sucesso!');
					window.location.href = "e_copa_arquivos/copa_menu.htm";
				}
	<%}%>
	</script>
</head>
<body link="#0563C1" vlink="#954F72">
	<%if(usuario.getPermissaoBean() == 1){%>
	<h1> 
		Bem vindo Administrador!
	</h1>
	<%} %>
	<div id="externo">
		<div id="fatuMensal" class="interno">
			<table border=0 cellpadding=0 cellspacing=0 width=622
				style='border-collapse: collapse; table-layout: fixed; width: 468pt'>
				<col width=29Gru
					style='mso-width-source: userset; mso-width-alt: 1060; width: 22pt'>
				<col width=16
					style='mso-width-source: userset; mso-width-alt: 585; width: 12pt'>
				<col width=6
					style='mso-width-source: userset; mso-width-alt: 219; width: 5pt'>
				<col width=105
					style='mso-width-source: userset; mso-width-alt: 3840; width: 79pt'>
				<col width=132
					style='mso-width-source: userset; mso-width-alt: 4827; width: 99pt'>
				<col width=28
					style='mso-width-source: userset; mso-width-alt: 1024; width: 21pt'>
				<col width=17
					style='mso-width-source: userset; mso-width-alt: 621; width: 13pt'>
				<col width=28
					style='mso-width-source: userset; mso-width-alt: 1024; width: 21pt'>
				<col width=132
					style='mso-width-source: userset; mso-width-alt: 4827; width: 99pt'>
				<col width=107
					style='mso-width-source: userset; mso-width-alt: 3913; width: 80pt'>
				<col width=6
					style='mso-width-source: userset; mso-width-alt: 219; width: 5pt'>
				<col width=16
					style='mso-width-source: userset; mso-width-alt: 585; width: 12pt'>
				<tr height=0 style='display: none'>
					<td width=29 style='width: 22pt'></td>
					<td width=16 style='width: 12pt'></td>
					<td width=6 style='width: 5pt'></td>
					<td width=105 style='width: 79pt'></td>
					<td width=132 style='width: 99pt'></td>
					<td width=28 style='width: 21pt'></td>
					<td width=17 style='width: 13pt'></td>
					<td width=28 style='width: 21pt'></td>
					<td width=132 style='width: 99pt'></td>
					<td width=107 style='width: 80pt'></td>
					<td width=6 style='width: 5pt'></td>
					<td width=16 style='width: 12pt'></td>
				</tr>
				<tr height=0 style='display: none'>
					<td height=0 colspan=12 style='mso-ignore: colspan'></td>
				</tr>
				<tr height=0 style='display: none'>
					<td height=0 colspan=12 style='mso-ignore: colspan'></td>
				</tr>
				<tr height=0 style='display: none'>
					<td height=0 colspan=12 style='mso-ignore: colspan'></td>
				</tr>
				<tr height=0 style='display: none'>
					<td height=0 colspan=12 style='mso-ignore: colspan'></td>
				</tr>
				<tr height=25 style='mso-height-source: userset; height: 10.75pt'>
					<td height=25 colspan=12 style='height: 10.75pt; mso-ignore: colspan'></td>
				</tr>
				<tr height=16 style='mso-height-source: userset; height: 12.0pt'>
					<td height=16 style='height: 12.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=6 style='mso-height-source: userset; height: 4.5pt'>
					<td height=6 style='height: 4.5pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td colspan=3 rowspan=3 height=60 width=265 style='height: 45.0pt; width: 199pt' align=left valign=top>
					
					<span style='mso-ignore: vglayout; position: absolute; z-index: 1; margin-left: 229px; margin-top: 2px; width: 221px; height: 80px'>
						<img width=221 height=76 src="e_copa_arquivos/copa.jpg">
					</span>
						<span style='mso-ignore: vglayout2'>
							<table cellpadding=0 cellspacing=0>
								<tr>
									<td colspan=3 rowspan=3 height=60 class=xl92 width=265 style='height: 45.0pt; width: 199pt'>Tabela da Copa 2018</td>
								</tr>
							</table>
						</span>
					</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td colspan=7 class=xl93>Primeira Fase</td>
					<td></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=10 style='mso-height-source: userset; height: 7.5pt'>
					<td height=10 style='height: 7.5pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=10 style='mso-height-source: userset; height: 7.5pt'>
					<td height=10 style='height: 7.5pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td colspan=2 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				
				<% 
					int j = 1;
					for(int i = 0;i < 6; i++){
				%>
					<tr height=20 style='height: 15.0pt'>
						<td height=20 style='height: 15.0pt'></td>
						<td class=xl77>&nbsp;</td>
						<td></td>
						<%if(j==3){ %>
							<td class=xl90>Grupo A</td>
						<%}else {
									if(j==1){//linha topo						
									%>
										<td class=xl88>&nbsp;</td>
								<%}else if(j==6){//linha final
								%>
										<td class=xl91>&nbsp;</td>
								<%}else{ %>
										<td class=xl89>&nbsp;</td>
							<%	} %>
						<%} %>
						<td class=xl87><%=partidas.get(i).getTime1()%></td>
						<td class=xl86 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2" 
						value="<% for(int k = 0;k < apostasRealizadas.size();k++){
									if(apostasRealizadas.get(k).getIdCampeonato() == partidas.get(i).getIdCampeonato()){
										out.print(apostasRealizadas.get(k).getPlayer1());
									}
								}%>" id="player1_<%=partidas.get(i).getIdCampeonato()%>"   
								<%
								Date dataPartida = sdf.parse(partidas.get(i).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
						<td class=xl86 style='border-left: none'>x</td>
						<td class=xl86 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2" 
						value="<% for(int k = 0;k < apostasRealizadas.size();k++){
									if(apostasRealizadas.get(k).getIdCampeonato() == partidas.get(i).getIdCampeonato()){
										out.print(apostasRealizadas.get(k).getPlayer2());
									}
								}%>" id="player2_<%=partidas.get(i).getIdCampeonato()%>"
								<% 
								dataPartida = sdf.parse(partidas.get(i).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
				
						<td class=xl86 style='border-left: none'><%=partidas.get(i).getTime2()%></td>
						<td class=xl94 style='border-left: none'><span id="data_<%=partidas.get(i).getIdCampeonato()%>"><%=partidas.get(i).getData().replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2/$1")%></span></td>
						<td></td>
						<td class=xl77>&nbsp;</td>
					</tr>
				<% j++;
					} %>
					
				<tr height=10 style='mso-height-source: userset; height: 7.5pt'>
					<td height=10 style='height: 7.5pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td colspan=2 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				
				
				<% 
					j = 1;
					for(int i = 0;i < 6; i++){
				%>
					<tr height=20 style='height: 15.0pt'>
						<td height=20 style='height: 15.0pt'></td>
						<td class=xl77>&nbsp;</td>
						<td></td>
						<%if(j==3){ %>
							<td class=xl85>Grupo B</td>
						<%}else {
									if(j==1){//linha topo						
									%>
										<td class=xl81>&nbsp;</td>
								<%}else if(j==6){//linha final
								%>
										<td class=xl80>&nbsp;</td>
								<%}else{ %>
										<td class=xl79>&nbsp;</td>
							<%	} %>
						<%} %>
						<td class=xl83><%=partidas.get(i+6).getTime1()%></td>
						<td class=xl84 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2" 
						value="<% for(int k = 0;k < apostasRealizadas.size();k++){
									if(apostasRealizadas.get(k).getIdCampeonato() == partidas.get(i+6).getIdCampeonato()){
										out.print(apostasRealizadas.get(k).getPlayer1());
									}
								}%>" id="player1_<%=partidas.get(i+6).getIdCampeonato()%>"
								<%
								Date dataPartida = sdf.parse(partidas.get(i+6).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
						<td class=xl82 style='border-left: none'>x</td>
						<td class=xl84 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2" 
						value="<% for(int k = 0;k < apostasRealizadas.size();k++){
									if(apostasRealizadas.get(k).getIdCampeonato() == partidas.get(i+6).getIdCampeonato()){
										out.print(apostasRealizadas.get(k).getPlayer2());
									}
								}%>" id="player2_<%=partidas.get(i+6).getIdCampeonato()%>"
								<%
								dataPartida = sdf.parse(partidas.get(i+6).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
						<td class=xl84 style='border-left: none'><%=partidas.get(i+6).getTime2()%></td>
						<td class=xl95 style='border-left: none'><span id="data_<%=partidas.get(i+6).getIdCampeonato()%>"><%=partidas.get(i+6).getData().replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2/$1")%></span></td>
						<td></td>
						<td class=xl77>&nbsp;</td>
					</tr>
				<% j++;
					} %>
					
					
				<tr height=10 style='mso-height-source: userset; height: 7.5pt'>
					<td height=10 style='height: 7.5pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td colspan=2 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				
				<%
					j = 1;
					for(int i = 0;i < 6; i++){
				%>
					<tr height=20 style='height: 15.0pt'>
						<td height=20 style='height: 15.0pt'></td>
						<td class=xl77>&nbsp;</td>
						<td></td>
						<%if(j==3){ %>
							<td class=xl90>Grupo C</td>
						<%}else {
									if(j==1){//linha topo						
									%>
										<td class=xl88>&nbsp;</td>
								<%}else if(j==6){//linha final
								%>
										<td class=xl91>&nbsp;</td>
								<%}else{ %>
										<td class=xl89>&nbsp;</td>
							<%	} %>
						<%} %>
						<td class=xl87><%=partidas.get(i+12).getTime1()%></td>
						<td class=xl86 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2" 
						value="<% for(int k = 0;k < apostasRealizadas.size();k++){
									if(apostasRealizadas.get(k).getIdCampeonato() == partidas.get(i+12).getIdCampeonato()){
										out.print(apostasRealizadas.get(k).getPlayer1());
									}
								}%>" id="player1_<%=partidas.get(i+12).getIdCampeonato()%>"
								<%
								Date dataPartida = sdf.parse(partidas.get(i+12).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
						<td class=xl86 style='border-left: none'>x</td>
						<td class=xl86 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2" 
						value="<% for(int k = 0;k < apostasRealizadas.size();k++){
									if(apostasRealizadas.get(k).getIdCampeonato() == partidas.get(i+12).getIdCampeonato()){
										out.print(apostasRealizadas.get(k).getPlayer2());
									}
								}%>" id="player2_<%=partidas.get(i+12).getIdCampeonato()%>"
								<%
								dataPartida = sdf.parse(partidas.get(i+12).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
						<td class=xl86 style='border-left: none'><%=partidas.get(i+12).getTime2()%></td>
						<td class=xl94 style='border-left: none'><span id="data_<%=partidas.get(i+12).getIdCampeonato()%>"><%=partidas.get(i+12).getData().replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2/$1")%></span></td>
						<td></td>
						<td class=xl77>&nbsp;</td>
					</tr>
				<% j++;
					} %>
				
				
				
				<tr height=10 style='mso-height-source: userset; height: 7.5pt'>
					<td height=10 style='height: 7.5pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td colspan=2 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				
				
				<% 
					j = 1;
					for(int i = 0;i < 6; i++){
				%>
					<tr height=20 style='height: 15.0pt'>
						<td height=20 style='height: 15.0pt'></td>
						<td class=xl77>&nbsp;</td>
						<td></td>
						<%if(j==3){ %>
							<td class=xl85>Grupo D</td>
						<%}else {
									if(j==1){//linha topo						
									%>
										<td class=xl81>&nbsp;</td>
								<%}else if(j==6){//linha final
								%>
										<td class=xl80>&nbsp;</td>
								<%}else{ %>
										<td class=xl79>&nbsp;</td>
							<%	} %>
						<%} %>
						<td class=xl83><%=partidas.get(i+18).getTime1()%></td>
						<td class=xl84 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2"
						value="<% for(int k = 0;k < apostasRealizadas.size();k++){
									if(apostasRealizadas.get(k).getIdCampeonato() == partidas.get(i+18).getIdCampeonato()){
										out.print(apostasRealizadas.get(k).getPlayer1());
									}
								}%>" id="player1_<%=partidas.get(i+18).getIdCampeonato()%>"
								<%
								Date dataPartida = sdf.parse(partidas.get(i+18).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
						<td class=xl82 style='border-left: none'>x</td>
						<td class=xl84 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2" 
						value="<% for(int k = 0;k < apostasRealizadas.size();k++){
									if(apostasRealizadas.get(k).getIdCampeonato() == partidas.get(i+18).getIdCampeonato()){
										out.print(apostasRealizadas.get(k).getPlayer2());
									}
								}%>" id="player2_<%=partidas.get(i+18).getIdCampeonato()%>"
								<%
								dataPartida = sdf.parse(partidas.get(i+18).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
						<td class=xl84 style='border-left: none'><%=partidas.get(i+18).getTime2()%></td>
						<td class=xl95 style='border-left: none'><span id="data_<%=partidas.get(i+18).getIdCampeonato()%>"><%=partidas.get(i+18).getData().replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2/$1")%></span></td>
						<td></td>
						<td class=xl77>&nbsp;</td>
					</tr>
				<% j++;
					} %>
				
				
				
				<tr height=10 style='mso-height-source: userset; height: 7.5pt'>
					<td height=10 style='height: 7.5pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td colspan=2 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				
				
				<%
					j = 1;
					for(int i = 0;i < 6; i++){
				%>
					<tr height=20 style='height: 15.0pt'>
						<td height=20 style='height: 15.0pt'></td>
						<td class=xl77>&nbsp;</td>
						<td></td>
						<%if(j==3){ %>
							<td class=xl90>Grupo E</td>
						<%}else {
									if(j==1){//linha topo						
									%>
										<td class=xl88>&nbsp;</td>
								<%}else if(j==6){//linha final
								%>
										<td class=xl91>&nbsp;</td>
								<%}else{ %>
										<td class=xl89>&nbsp;</td>
							<%	} %>
						<%} %>
						<td class=xl87><%=partidas.get(i+24).getTime1()%></td>
						<td class=xl86 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2"
						value="<% for(int k = 0;k < apostasRealizadas.size();k++){
									if(apostasRealizadas.get(k).getIdCampeonato() == partidas.get(i+24).getIdCampeonato()){
										out.print(apostasRealizadas.get(k).getPlayer1());
									}
								}%>" id="player1_<%=partidas.get(i+24).getIdCampeonato()%>"
								<%
								Date dataPartida = sdf.parse(partidas.get(i+24).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
						<td class=xl86 style='border-left: none'>x</td>
						<td class=xl86 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2" 
						value="<% for(int k = 0;k < apostasRealizadas.size();k++){
									if(apostasRealizadas.get(k).getIdCampeonato() == partidas.get(i+24).getIdCampeonato()){
										out.print(apostasRealizadas.get(k).getPlayer2());
									}
								}%>" id="player2_<%=partidas.get(i+24).getIdCampeonato()%>"
								<%
								dataPartida = sdf.parse(partidas.get(i+24).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
						<td class=xl86 style='border-left: none'><%=partidas.get(i+24).getTime2()%></td>
						<td class=xl94 style='border-left: none'><span id="data_<%=partidas.get(i+24).getIdCampeonato()%>"><%=partidas.get(i+24).getData().replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2/$1").replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2/$1")%></span></td>
						<td></td>
						<td class=xl77>&nbsp;</td>
					</tr>
				<% j++;
					} %>
				
				
				<tr height=10 style='mso-height-source: userset; height: 7.5pt'>
					<td height=10 style='height: 7.5pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td colspan=2 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				
				
				<% 
					j = 1;
					for(int i = 0;i < 6; i++){
				%>
					<tr height=20 style='height: 15.0pt'>
						<td height=20 style='height: 15.0pt'></td>
						<td class=xl77>&nbsp;</td>
						<td></td>
						<%if(j==3){ %>
							<td class=xl85>Grupo F</td>
						<%}else {
									if(j==1){//linha topo						
									%>
										<td class=xl81>&nbsp;</td>
								<%}else if(j==6){//linha final
								%>
										<td class=xl80>&nbsp;</td>
								<%}else{ %>
										<td class=xl79>&nbsp;</td>
							<%	} %>
						<%} %>
						<td class=xl83><%=partidas.get(i+30).getTime1()%></td>
						<td class=xl84 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2" 
						value="<% for(int k = 0;k < apostasRealizadas.size();k++){
									if(apostasRealizadas.get(k).getIdCampeonato() == partidas.get(i+30).getIdCampeonato()){
										out.print(apostasRealizadas.get(k).getPlayer1());
									}
								}%>" id="player1_<%=partidas.get(i+30).getIdCampeonato()%>"
								<%
								Date dataPartida = sdf.parse(partidas.get(i+30).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
						<td class=xl82 style='border-left: none'>x</td>
						<td class=xl84 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2" 
						value="<% for(int k = 0;k < apostasRealizadas.size();k++){
									if(apostasRealizadas.get(k).getIdCampeonato() == partidas.get(i+30).getIdCampeonato()){
										out.print(apostasRealizadas.get(k).getPlayer2());
									}
								}%>" id="player2_<%=partidas.get(i+30).getIdCampeonato()%>"
								<%
								dataPartida = sdf.parse(partidas.get(i+30).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
						<td class=xl84 style='border-left: none'><%=partidas.get(i+30).getTime2()%></td>
						<td class=xl95 style='border-left: none'><span id="data_<%=partidas.get(i+30).getIdCampeonato()%>"><%=partidas.get(i+30).getData().replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2/$1")%></span></td>
						<td></td>
						<td class=xl77>&nbsp;</td>
					</tr>
				<% j++;
					} %>

                
                <tr height=10 style='mso-height-source: userset; height: 7.5pt'>
                    <td height=10 style='height: 7.5pt'></td>
                    <td class=xl77>&nbsp;</td>
                    <td></td>
                    <td class=xl65></td>
                    <td class=xl65></td>
                    <td class=xl65></td>
                    <td class=xl65></td>
                    <td class=xl65></td>
                    <td class=xl65></td>
                    <td colspan=2 style='mso-ignore: colspan'></td>
                    <td class=xl77>&nbsp;</td>
                </tr>
           
                
            	<%
                    j = 1;
                    for(int i = 0;i < 6; i++){
                %>
                    <tr height=20 style='height: 15.0pt'>
                        <td height=20 style='height: 15.0pt'></td>
                        <td class=xl77>&nbsp;</td>
                        <td></td>
                        <%if(j==3){ %>
                            <td class=xl90>Grupo G</td>
                        <%}else {
                                    if(j==1){//linha topo                        
                                    %>
                                        <td class=xl88>&nbsp;</td>
                                <%}else if(j==6){//linha final
                                %>
                                        <td class=xl91>&nbsp;</td>
                                <%}else{ %>
                                        <td class=xl89>&nbsp;</td>
                            <%    } %>
                        <%} %>
                        <td class=xl87><%=partidas.get(i+36).getTime1()%></td>
                        <td class=xl86 style='border-left: none'>
                        <input type="text" style="height:1em; width:1.5em;" maxlength="2"
                        value="<% for(int k = 0;k < apostasRealizadas.size();k++){
                                    if(apostasRealizadas.get(k).getIdCampeonato() == partidas.get(i+36).getIdCampeonato()){
                                        out.print(apostasRealizadas.get(k).getPlayer1());
                                    }
                                }%>" id="player1_<%=partidas.get(i+36).getIdCampeonato()%>"
                                <%
                                Date dataPartida = sdf.parse(partidas.get(i+36).getData());
                                if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
                                    out.print("disabled readonly='true'");
                                }
                                %>
                                ></td>
                        <td class=xl86 style='border-left: none'>x</td>
                        <td class=xl86 style='border-left: none'>
                        <input type="text" style="height:1em; width:1.5em;" maxlength="2"
                        value="<% for(int k = 0;k < apostasRealizadas.size();k++){
                                    if(apostasRealizadas.get(k).getIdCampeonato() == partidas.get(i+36).getIdCampeonato()){
                                        out.print(apostasRealizadas.get(k).getPlayer2());
                                    }
                                }%>" id="player2_<%=partidas.get(i+36).getIdCampeonato()%>"
                                <%
                                dataPartida = sdf.parse(partidas.get(i+36).getData());
                                if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
                                    out.print("disabled readonly='true'");
                                }
                                %>
                                ></td>
                        <td class=xl86 style='border-left: none'><%=partidas.get(i+36).getTime2()%></td>
                        <td class=xl94 style='border-left: none'><span id="data_<%=partidas.get(i+36).getIdCampeonato()%>"><%=partidas.get(i+36).getData().replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2/$1").replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2/$1")%></span></td>
                        <td></td>
                        <td class=xl77>&nbsp;</td>
                    </tr>
                <% j++;
                    } %>
                
                
                <tr height=10 style='mso-height-source: userset; height: 7.5pt'>
                    <td height=10 style='height: 7.5pt'></td>
                    <td class=xl77>&nbsp;</td>
                    <td></td>
                    <td class=xl65></td>
                    <td class=xl65></td>
                    <td class=xl65></td>
                    <td class=xl65></td>
                    <td class=xl65></td>
                    <td class=xl65></td>
                    <td colspan=2 style='mso-ignore: colspan'></td>
                    <td class=xl77>&nbsp;</td>
                </tr>

				
				
				<% 
					j = 1;
					for(int i = 0;i < 6; i++){
				%>
					<tr height=20 style='height: 15.0pt'>
						<td height=20 style='height: 15.0pt'></td>
						<td class=xl77>&nbsp;</td>
						<td></td>
						<%if(j==3){ %>
							<td class=xl85>Grupo H</td>
						<%}else {
									if(j==1){//linha topo						
									%>
										<td class=xl81>&nbsp;</td>
								<%}else if(j==6){//linha final
								%>
										<td class=xl80>&nbsp;</td>
								<%}else{ %>
										<td class=xl79>&nbsp;</td>
							<%	} %>
						<%} %>
						<td class=xl83><%=partidas.get(i+42).getTime1()%></td>
						<td class=xl84 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2" 
						value="<% for(int k = 0;k < apostasRealizadas.size();k++){
									if(apostasRealizadas.get(k).getIdCampeonato() == partidas.get(i+42).getIdCampeonato()){
										out.print(apostasRealizadas.get(k).getPlayer1());
									}
								}%>" id="player1_<%=partidas.get(i+42).getIdCampeonato()%>"
								<%
								Date dataPartida = sdf.parse(partidas.get(i+42).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
						<td class=xl82 style='border-left: none'>x</td>
						<td class=xl84 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2" 
						value="<% for(int k = 0;k < apostasRealizadas.size();k++){
									if(apostasRealizadas.get(k).getIdCampeonato() == partidas.get(i+42).getIdCampeonato()){
										out.print(apostasRealizadas.get(k).getPlayer2());
									}
								}%>" id="player2_<%=partidas.get(i+42).getIdCampeonato()%>"
								<%
								dataPartida = sdf.parse(partidas.get(i+42).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
						<td class=xl84 style='border-left: none'><%=partidas.get(i+42).getTime2()%></td>
						<td class=xl95 style='border-left: none'><span id="data_<%=partidas.get(i+42).getIdCampeonato()%>"><%=partidas.get(i+42).getData().replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2/$1")%></span></td>
						<td></td>
						<td class=xl77>&nbsp;</td>
					</tr>
				<% j++;
					} %>



<!-- OUTRAS FASES AQUI -->





<!-- SEGUNDA FASE -->




				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td colspan=7 class=xl93>Oitavas de Final</td>
					<td></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=10 style='mso-height-source: userset; height: 7.5pt'>
					<td height=10 style='height: 7.5pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=10 style='mso-height-source: userset; height: 7.5pt'>
					<td height=10 style='height: 7.5pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td colspan=2 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				
				<% 
					 j = 1;
					for(int i = 0;i < 2; i++){
				%>
					<tr height=20 style='height: 15.0pt'>
						<td height=20 style='height: 15.0pt'></td>
						<td class=xl77>&nbsp;</td>
						<td></td>
						<%if(j==1){ %>
							<td class=xl90>Jogo 49</td>
						<%}else {
									if(j==1){//linha topo						
									%>
										<td class=xl88>&nbsp;</td>
								<%}else if(j==2){//linha final
								%>
										<td class=xl91><b>Jogo 50</b></td>
								<%}else{ %>
										<td class=xl89>&nbsp;</td>
							<%	} %>
						<%} %>
						<td class=xl87><%=partidasF2.get(i).getTime1()%></td>
						<td class=xl86 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2" 
						value="<% for(int k = 0;k < apostasRealizadasF2.size();k++){
									if(apostasRealizadasF2.get(k).getIdCampeonato() == partidasF2.get(i).getIdCampeonato()){
										out.print(apostasRealizadasF2.get(k).getPlayer1());
									}
								}%>" id="player1_<%=partidasF2.get(i).getIdCampeonato()%>"   
								<%
								Date dataPartida = sdf.parse(partidasF2.get(i).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
						<td class=xl86 style='border-left: none'>x</td>
						<td class=xl86 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2" 
						value="<% for(int k = 0;k < apostasRealizadasF2.size();k++){
									if(apostasRealizadasF2.get(k).getIdCampeonato() == partidasF2.get(i).getIdCampeonato()){
										out.print(apostasRealizadasF2.get(k).getPlayer2());
									}
								}%>" id="player2_<%=partidasF2.get(i).getIdCampeonato()%>"
								<% 
								dataPartida = sdf.parse(partidasF2.get(i).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
				
						<td class=xl86 style='border-left: none'><%=partidasF2.get(i).getTime2()%></td>
						<td class=xl94 style='border-left: none'><span id="data_<%=partidasF2.get(i).getIdCampeonato()%>"><%=partidasF2.get(i).getData().replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2/$1")%></span></td>
						<td></td>
						<td class=xl77>&nbsp;</td>
					</tr>
				<% j++;
					} %>
					
				<tr height=10 style='mso-height-source: userset; height: 7.5pt'>
					<td height=10 style='height: 7.5pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td colspan=2 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				
		
			
				<% 
					j = 1;
					for(int i = 0;i < 2; i++){
				%>
					<tr height=20 style='height: 15.0pt'>
						<td height=20 style='height: 15.0pt'></td>
						<td class=xl77>&nbsp;</td>
						<td></td>
						<%if(j==1){ %>
							<td class=xl85>Jogo 51</td>
						<%}else {
									if(j==1){//linha topo						
									%>
										<td class=xl81>&nbsp;</td>
								<%}else if(j==2){//linha final
								%>
										<td class=xl80><b>Jogo 52</b></td>
								<%}else{ %>
										<td class=xl79>&nbsp;</td>
							<%	} %>
						<%} %>
						<td class=xl83><%=partidasF2.get(i+2).getTime1()%></td>
						<td class=xl84 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2" 
						value="<% for(int k = 0;k < apostasRealizadasF2.size();k++){
									if(apostasRealizadasF2.get(k).getIdCampeonato() == partidasF2.get(i+2).getIdCampeonato()){
										out.print(apostasRealizadasF2.get(k).getPlayer1());
									}
								}%>" id="player1_<%=partidasF2.get(i+2).getIdCampeonato()%>"
								<%
								Date dataPartida = sdf.parse(partidasF2.get(i+2).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
						<td class=xl82 style='border-left: none'>x</td>
						<td class=xl84 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2" 
						value="<% for(int k = 0;k < apostasRealizadasF2.size();k++){
									if(apostasRealizadasF2.get(k).getIdCampeonato() == partidasF2.get(i+2).getIdCampeonato()){
										out.print(apostasRealizadasF2.get(k).getPlayer2());
									}
								}%>" id="player2_<%=partidasF2.get(i+2).getIdCampeonato()%>"
								<%
								dataPartida = sdf.parse(partidasF2.get(i+2).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
						<td class=xl84 style='border-left: none'><%=partidasF2.get(i+2).getTime2()%></td>
						<td class=xl95 style='border-left: none'><span id="data_<%=partidasF2.get(i+2).getIdCampeonato()%>"><%=partidasF2.get(i+2).getData().replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2/$1")%></span></td>
						<td></td>
						<td class=xl77>&nbsp;</td>
					</tr>
				<% j++;
					} %>
					
					
				<tr height=10 style='mso-height-source: userset; height: 7.5pt'>
					<td height=10 style='height: 7.5pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td colspan=2 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				
				<%
					j = 1;
					for(int i = 0;i < 2; i++){
				%>
					<tr height=20 style='height: 15.0pt'>
						<td height=20 style='height: 15.0pt'></td>
						<td class=xl77>&nbsp;</td>
						<td></td>
						<%if(j==1){ %>
							<td class=xl90>Jogo 53</td>
						<%}else {
									if(j==1){//linha topo						
									%>
										<td class=xl88>&nbsp;</td>
								<%}else if(j==2){//linha final
								%>
										<td class=xl91><b>Jogo 54</b></td>
								<%}else{ %>
										<td class=xl89>&nbsp;</td>
							<%	} %>
						<%} %>
						<td class=xl87><%=partidasF2.get(i+4).getTime1()%></td>
						<td class=xl86 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2" 
						value="<% for(int k = 0;k < apostasRealizadasF2.size();k++){
									if(apostasRealizadasF2.get(k).getIdCampeonato() == partidasF2.get(i+4).getIdCampeonato()){
										out.print(apostasRealizadasF2.get(k).getPlayer1());
									}
								}%>" id="player1_<%=partidasF2.get(i+4).getIdCampeonato()%>"
								<%
								Date dataPartida = sdf.parse(partidasF2.get(i+4).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
						<td class=xl86 style='border-left: none'>x</td>
						<td class=xl86 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2" 
						value="<% for(int k = 0;k < apostasRealizadasF2.size();k++){
									if(apostasRealizadasF2.get(k).getIdCampeonato() == partidasF2.get(i+4).getIdCampeonato()){
										out.print(apostasRealizadasF2.get(k).getPlayer2());
									}
								}%>" id="player2_<%=partidasF2.get(i+4).getIdCampeonato()%>"
								<%
								dataPartida = sdf.parse(partidasF2.get(i+4).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
						<td class=xl86 style='border-left: none'><%=partidasF2.get(i+4).getTime2()%></td>
						<td class=xl94 style='border-left: none'><span id="data_<%=partidasF2.get(i+4).getIdCampeonato()%>"><%=partidasF2.get(i+4).getData().replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2/$1")%></span></td>
						<td></td>
						<td class=xl77>&nbsp;</td>
					</tr>
				<% j++;
					} %>
				
				
				
				<tr height=10 style='mso-height-source: userset; height: 7.5pt'>
					<td height=10 style='height: 7.5pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td colspan=2 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				
				
				<% 
					j = 1;
					for(int i = 0;i < 2; i++){
				%>
					<tr height=20 style='height: 15.0pt'>
						<td height=20 style='height: 15.0pt'></td>
						<td class=xl77>&nbsp;</td>
						<td></td>
						<%if(j==1){ %>
							<td class=xl85>Jogo 55</td>
						<%}else {
									if(j==1){//linha topo						
									%>
										<td class=xl81>&nbsp;</td>
								<%}else if(j==2){//linha final
								%>
										<td class=xl80><b>Jogo 56</b></td>
								<%}else{ %>
										<td class=xl79>&nbsp;</td>
							<%	} %>
						<%} %>
						<td class=xl83><%=partidasF2.get(i+6).getTime1()%></td>
						<td class=xl84 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2"
						value="<% for(int k = 0;k < apostasRealizadasF2.size();k++){
									if(apostasRealizadasF2.get(k).getIdCampeonato() == partidasF2.get(i+6).getIdCampeonato()){
										out.print(apostasRealizadasF2.get(k).getPlayer1());
									}
								}%>" id="player1_<%=partidasF2.get(i+6).getIdCampeonato()%>"
								<%
								Date dataPartida = sdf.parse(partidasF2.get(i+6).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
						<td class=xl82 style='border-left: none'>x</td>
						<td class=xl84 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2" 
						value="<% for(int k = 0;k < apostasRealizadasF2.size();k++){
									if(apostasRealizadasF2.get(k).getIdCampeonato() == partidasF2.get(i+6).getIdCampeonato()){
										out.print(apostasRealizadasF2.get(k).getPlayer2());
									}
								}%>" id="player2_<%=partidasF2.get(i+6).getIdCampeonato()%>"
								<%
								dataPartida = sdf.parse(partidasF2.get(i+6).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
						<td class=xl84 style='border-left: none'><%=partidasF2.get(i+6).getTime2()%></td>
						<td class=xl95 style='border-left: none'><span id="data_<%=partidasF2.get(i+6).getIdCampeonato()%>"><%=partidasF2.get(i+6).getData().replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2/$1")%></span></td>
						<td></td>
						<td class=xl77>&nbsp;</td>
					</tr>
				<% j++;
					} %>
				
				





<!-- TERCEIRA FASE QUARTAS DE FINAL -->




				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td colspan=7 class=xl93>Quartas de Final</td>
					<td></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=10 style='mso-height-source: userset; height: 7.5pt'>
					<td height=10 style='height: 7.5pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=10 style='mso-height-source: userset; height: 7.5pt'>
					<td height=10 style='height: 7.5pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td colspan=2 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				
				<% 
					 j = 1;
					for(int i = 0;i < 2; i++){
				%>
					<tr height=20 style='height: 15.0pt'>
						<td height=20 style='height: 15.0pt'></td>
						<td class=xl77>&nbsp;</td>
						<td></td>
						<%if(j==1){ %>
							<td class=xl90>Jogo 57</td>
						<%}else {
									if(j==1){//linha topo						
									%>
										<td class=xl88>&nbsp;</td>
								<%}else if(j==2){//linha final
								%>
										<td class=xl91><b>Jogo 58</b></td>
								<%}else{ %>
										<td class=xl89>&nbsp;</td>
							<%	} %>
						<%} %>
						<td class=xl87><%=partidasF3.get(i).getTime1()%></td>
						<td class=xl86 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2" 
						value="<% for(int k = 0;k < apostasRealizadasF3.size();k++){
									if(apostasRealizadasF3.get(k).getIdCampeonato() == partidasF3.get(i).getIdCampeonato()){
										out.print(apostasRealizadasF3.get(k).getPlayer1());
									}
								}%>" id="player1_<%=partidasF3.get(i).getIdCampeonato()%>"   
								<%
								Date dataPartida = sdf.parse(partidasF3.get(i).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
						<td class=xl86 style='border-left: none'>x</td>
						<td class=xl86 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2" 
						value="<% for(int k = 0;k < apostasRealizadasF3.size();k++){
									if(apostasRealizadasF3.get(k).getIdCampeonato() == partidasF3.get(i).getIdCampeonato()){
										out.print(apostasRealizadasF3.get(k).getPlayer2());
									}
								}%>" id="player2_<%=partidasF3.get(i).getIdCampeonato()%>"
								<% 
								dataPartida = sdf.parse(partidasF3.get(i).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
				
						<td class=xl86 style='border-left: none'><%=partidasF3.get(i).getTime2()%></td>
						<td class=xl94 style='border-left: none'><span id="data_<%=partidasF3.get(i).getIdCampeonato()%>"><%=partidasF3.get(i).getData().replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2/$1")%></span></td>
						<td></td>
						<td class=xl77>&nbsp;</td>
					</tr>
				<% j++;
					} %>
					
				<tr height=10 style='mso-height-source: userset; height: 7.5pt'>
					<td height=10 style='height: 7.5pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td colspan=2 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				
				
				<% 
					j = 1;
					for(int i = 0;i < 2; i++){
				%>
					<tr height=20 style='height: 15.0pt'>
						<td height=20 style='height: 15.0pt'></td>
						<td class=xl77>&nbsp;</td>
						<td></td>
						<%if(j==1){ %>
							<td class=xl85>Jogo 59</td>
						<%}else {
									if(j==1){//linha topo						
									%>
										<td class=xl81>&nbsp;</td>
								<%}else if(j==2){//linha final
								%>
										<td class=xl80><b>Jogo 60</b></td>
								<%}else{ %>
										<td class=xl79>&nbsp;</td>
							<%	} %>
						<%} %>
						<td class=xl83><%=partidasF3.get(i+2).getTime1()%></td>
						<td class=xl84 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2" 
						value="<% for(int k = 0;k < apostasRealizadasF3.size();k++){
									if(apostasRealizadasF3.get(k).getIdCampeonato() == partidasF3.get(i+2).getIdCampeonato()){
										out.print(apostasRealizadasF3.get(k).getPlayer1());
									}
								}%>" id="player1_<%=partidasF3.get(i+2).getIdCampeonato()%>"
								<%
								Date dataPartida = sdf.parse(partidasF3.get(i+2).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
						<td class=xl82 style='border-left: none'>x</td>
						<td class=xl84 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2" 
						value="<% for(int k = 0;k < apostasRealizadasF3.size();k++){
									if(apostasRealizadasF3.get(k).getIdCampeonato() == partidasF3.get(i+2).getIdCampeonato()){
										out.print(apostasRealizadasF3.get(k).getPlayer2());
									}
								}%>" id="player2_<%=partidasF3.get(i+2).getIdCampeonato()%>"
								<%
								dataPartida = sdf.parse(partidasF3.get(i+2).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
						<td class=xl84 style='border-left: none'><%=partidasF3.get(i+2).getTime2()%></td>
						<td class=xl95 style='border-left: none'><span id="data_<%=partidasF3.get(i+2).getIdCampeonato()%>"><%=partidasF3.get(i+2).getData().replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2/$1")%></span></td>
						<td></td>
						<td class=xl77>&nbsp;</td>
					</tr>
				<% j++;
					} %>
					
					



<!-- QUARTA FASE SEMI FINAL -->




				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td colspan=7 class=xl93>Semifinal</td>
					<td></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=10 style='mso-height-source: userset; height: 7.5pt'>
					<td height=10 style='height: 7.5pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=10 style='mso-height-source: userset; height: 7.5pt'>
					<td height=10 style='height: 7.5pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td colspan=2 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				
				<% 
					 j = 1;
					for(int i = 0;i < 2; i++){
				%>
					<tr height=20 style='height: 15.0pt'>
						<td height=20 style='height: 15.0pt'></td>
						<td class=xl77>&nbsp;</td>
						<td></td>
						<%if(j==1){ %>
							<td class=xl90><b>Jogo 61</b></td>
						<%}else {
									if(j==1){//linha topo						
									%>
										<td class=xl88>&nbsp;</td>
								<%}else if(j==2){//linha final
								%>
										<td class=xl91><b>Jogo 62</b></td>
								<%}else{ %>
										<td class=xl89>&nbsp;</td>
							<%	} %>
						<%} %>
						<td class=xl87><%=partidasF4.get(i).getTime1()%></td>
						<td class=xl86 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2" 
						value="<% for(int k = 0;k < apostasRealizadasF4.size();k++){
									if(apostasRealizadasF4.get(k).getIdCampeonato() == partidasF4.get(i).getIdCampeonato()){
										out.print(apostasRealizadasF4.get(k).getPlayer1());
									}
								}%>" id="player1_<%=partidasF4.get(i).getIdCampeonato()%>"   
								<%
								Date dataPartida = sdf.parse(partidasF4.get(i).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
						<td class=xl86 style='border-left: none'>x</td>
						<td class=xl86 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2" 
						value="<% for(int k = 0;k < apostasRealizadasF4.size();k++){
									if(apostasRealizadasF4.get(k).getIdCampeonato() == partidasF4.get(i).getIdCampeonato()){
										out.print(apostasRealizadasF4.get(k).getPlayer2());
									}
								}%>" id="player2_<%=partidasF4.get(i).getIdCampeonato()%>"
								<% 
								dataPartida = sdf.parse(partidasF4.get(i).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
				
						<td class=xl86 style='border-left: none'><%=partidasF4.get(i).getTime2()%></td>
						<td class=xl94 style='border-left: none'><span id="data_<%=partidasF4.get(i).getIdCampeonato()%>"><%=partidasF4.get(i).getData().replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2/$1")%></span></td>
						<td></td>
						<td class=xl77>&nbsp;</td>
					</tr>
				<% j++;
					} %>
					
		
			
<!-- QUINTA FASE FINAL -->




				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td colspan=7 class=xl93>3º Lugar</td>
					<td></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=10 style='mso-height-source: userset; height: 7.5pt'>
					<td height=10 style='height: 7.5pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=10 style='mso-height-source: userset; height: 7.5pt'>
					<td height=10 style='height: 7.5pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td colspan=2 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				
				<% 
					 j = 1;
					for(int i = 0;i < 1; i++){
				%>
					<tr height=20 style='height: 15.0pt'>
						<td height=20 style='height: 15.0pt'></td>
						<td class=xl77>&nbsp;</td>
						<td></td>
						<%if(j==1){ %>
							<td class=xl90>Jogo 63</td>
						<%}else {
									if(j==1){//linha topo						
									%>
										<td class=xl88>&nbsp;</td>
								<%}else if(j==2){//linha final
								%>
										<td class=xl91>&nbsp;</td>
								<%}else{ %>
										<td class=xl89>&nbsp;</td>
							<%	} %>
						<%} %>
						<td class=xl87><%=partidasF5.get(i).getTime1()%></td>
						<td class=xl86 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2" 
						value="<% for(int k = 0;k < apostasRealizadasF5.size();k++){
									if(apostasRealizadasF5.get(k).getIdCampeonato() == partidasF5.get(i).getIdCampeonato()){
										out.print(apostasRealizadasF5.get(k).getPlayer1());
									}
								}%>" id="player1_<%=partidasF5.get(i).getIdCampeonato()%>"   
								<%
								Date dataPartida = sdf.parse(partidasF5.get(i).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
						<td class=xl86 style='border-left: none'>x</td>
						<td class=xl86 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2" 
						value="<% for(int k = 0;k < apostasRealizadasF5.size();k++){
									if(apostasRealizadasF5.get(k).getIdCampeonato() == partidasF5.get(i).getIdCampeonato()){
										out.print(apostasRealizadasF5.get(k).getPlayer2());
									}
								}%>" id="player2_<%=partidasF5.get(i).getIdCampeonato()%>"
								<% 
								dataPartida = sdf.parse(partidasF5.get(i).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
				
						<td class=xl86 style='border-left: none'><%=partidasF5.get(i).getTime2()%></td>
						<td class=xl94 style='border-left: none'><span id="data_<%=partidasF5.get(i).getIdCampeonato()%>"><%=partidasF5.get(i).getData().replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2/$1")%></span></td>
						<td></td>
						<td class=xl77>&nbsp;</td>
					</tr>
				<% j++;
					} %>
					
			

				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td colspan=7 class=xl93>Final</td>
					<td></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=10 style='mso-height-source: userset; height: 7.5pt'>
					<td height=10 style='height: 7.5pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=10 style='mso-height-source: userset; height: 7.5pt'>
					<td height=10 style='height: 7.5pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td class=xl65></td>
					<td colspan=2 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				
				<% 
					 j = 1;
					for(int i = 0;i < 1; i++){
				%>
					<tr height=20 style='height: 15.0pt'>
						<td height=20 style='height: 15.0pt'></td>
						<td class=xl77>&nbsp;</td>
						<td></td>
						<%if(j==1){ %>
							<td class=xl90>Jogo 64</td>
						<%}else {
									if(j==1){//linha topo						
									%>
										<td class=xl88>&nbsp;</td>
								<%}else if(j==2){//linha final
								%>
										<td class=xl91>&nbsp;</td>
								<%}else{ %>
										<td class=xl89>&nbsp;</td>
							<%	} %>
						<%} %>
						<td class=xl87><%=partidasF6.get(i).getTime1()%></td>
						<td class=xl86 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2" 
						value="<% for(int k = 0;k < apostasRealizadasF6.size();k++){
									if(apostasRealizadasF6.get(k).getIdCampeonato() == partidasF6.get(i).getIdCampeonato()){
										out.print(apostasRealizadasF6.get(k).getPlayer1());
									}
								}%>" id="player1_<%=partidasF6.get(i).getIdCampeonato()%>"   
								<%
								Date dataPartida = sdf.parse(partidasF6.get(i).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
						<td class=xl86 style='border-left: none'>x</td>
						<td class=xl86 style='border-left: none'>
						<input type="text" style="height:1em; width:1.5em;" maxlength="2" 
						value="<% for(int k = 0;k < apostasRealizadasF6.size();k++){
									if(apostasRealizadasF6.get(k).getIdCampeonato() == partidasF6.get(i).getIdCampeonato()){
										out.print(apostasRealizadasF6.get(k).getPlayer2());
									}
								}%>" id="player2_<%=partidasF6.get(i).getIdCampeonato()%>"
								<% 
								dataPartida = sdf.parse(partidasF6.get(i).getData());
								if(dataSistemaObj.compareTo(dataPartida) == 0 || dataSistemaObj.compareTo(dataPartida) > 0){
									out.print("disabled readonly='true'");
								}
								%>
								></td>
				
						<td class=xl86 style='border-left: none'><%=partidasF6.get(i).getTime2()%></td>
						<td class=xl94 style='border-left: none'><span id="data_<%=partidasF6.get(i).getIdCampeonato()%>"><%=partidasF6.get(i).getData().replaceAll("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2/$1")%></span></td>
						<td></td>
						<td class=xl77>&nbsp;</td>
					</tr>
				<% j++;
					} %>
					
	
<!-- OUTRAS FASES AQUI --> 


			</table>
		</div>
		
		
		<div id="direita" class="interno">
		
			
			
			<%if(usuario.getPermissaoBean() == 1){%>
			<table  id="cadastroUser" border=0 cellpadding=0 cellspacing=0 width=326 style="margin-left: 140px;" >
				<col width=29
					style='mso-width-source: userset; mso-width-alt: 1060; width: 22pt'>
				<col width=16
					style='mso-width-source: userset; mso-width-alt: 585; width: 12pt'>
				<col width=6
					style='mso-width-source: userset; mso-width-alt: 219; width: 5pt'>
				<col width=105
					style='mso-width-source: userset; mso-width-alt: 3840; width: 79pt'>
				<col width=132
					style='mso-width-source: userset; mso-width-alt: 4827; width: 99pt'>
				<col width=28
					style='mso-width-source: userset; mso-width-alt: 1024; width: 21pt'>
				<col width=17
					style='mso-width-source: userset; mso-width-alt: 621; width: 13pt'>
				<col width=28
					style='mso-width-source: userset; mso-width-alt: 1024; width: 21pt'>
				<col width=132
					style='mso-width-source: userset; mso-width-alt: 4827; width: 99pt'>
				<col width=11
					style='mso-width-source: userset; mso-width-alt: 402; width: 8pt'>
				<col width=6
					style='mso-width-source: userset; mso-width-alt: 219; width: 5pt'>
				<col width=16
					style='mso-width-source: userset; mso-width-alt: 585; width: 12pt'>
				<tr height=0 style='display: none'>
					<td width=29 style='width: 22pt'></td>
					<td width=16 style='width: 12pt'></td>
					<td width=6 style='width: 5pt'></td>
					<td width=105 style='width: 79pt'></td>
					<td width=132 style='width: 99pt'></td>
					<td width=28 style='width: 21pt'></td>
					<td width=17 style='width: 13pt'></td>
					<td width=28 style='width: 21pt'></td>
					<td width=132 style='width: 99pt'></td>
					<td width=11 style='width: 8pt'></td>
					<td width=6 style='width: 5pt'></td>
					<td width=16 style='width: 12pt'></td>
				</tr>
				<tr height=0 style='display: none'>
					<td height=0 colspan=12 style='mso-ignore: colspan'></td>
				</tr>
				<tr height=0 style='display: none'>
					<td height=0 colspan=12 style='mso-ignore: colspan'></td>
				</tr>
				<tr height=0 style='display: none'>
					<td height=0 colspan=12 style='mso-ignore: colspan'></td>
				</tr>
				<tr height=0 style='display: none'>
					<td height=0 colspan=12 style='mso-ignore: colspan'></td>
				</tr>
				<tr height=5 style='mso-height-source: userset; height: 3.75pt'>
					<td height=5 colspan=12 style='height: 3.75pt; mso-ignore: colspan'></td>
				</tr>
				<tr height=16 style='mso-height-source: userset; height: 12.0pt'>
					<td height=16 style='height: 12.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=6 style='mso-height-source: userset; height: 4.5pt'>
					<td height=6 style='height: 4.5pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				
		
		
				
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td colspan=6 class=xl105>USUÁRIO:<input type="text" name="nLoginAdmin" id="login"></td>
					<td colspan=2 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
			
				
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				
				
				
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td colspan=6 class=xl105>NOME:<input type="text" name="nNomeAdmin" id="nome"></td>
					<td colspan=2 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				
				
				<tr height=10 style='mso-height-source: userset; height: 7.5pt'>
					<td height=10 style='height: 7.5pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>	
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				
				
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td colspan=6 class=xl105>SENHA:<input type="text" name="nSenhaAdmin" value="copa2018" id="senha"></td>
					<td colspan=2 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				
				
				<tr height=10 style='mso-height-source: userset; height: 7.5pt'>
					<td height=10 style='height: 7.5pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				
				
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td colspan=6 class=xl105><input type="submit" value="Cadastrar usuário!" onclick = "cadastrarUsuario();">&nbsp;&nbsp;<input type="submit" value="Sair" onclick = "fecharCadastro();"></td>
					<td colspan=2 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=10 style='mso-height-source: userset; height: 7.5pt'>
					<td height=10 style='height: 7.5pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
		
		
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
		
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=20 style='height: 15.0pt'>
					<td height=20 colspan=4 style='height: 15.0pt; mso-ignore: colspan'></td>
					<td class=xl78></td>
					<td colspan=7 style='mso-ignore: colspan'></td>
				</tr>
				<tr height=20 style='height: 15.0pt'>
					<td height=20 colspan=4 style='height: 15.0pt; mso-ignore: colspan'></td>
					<td class=xl78></td>
					<td colspan=7 style='mso-ignore: colspan'></td>
				</tr>
				<tr height=20 style='height: 15.0pt'>
					<td height=20 colspan=4 style='height: 15.0pt; mso-ignore: colspan'></td>
					<td class=xl78></td>
					<td colspan=7 style='mso-ignore: colspan'></td>
				</tr>
				<tr height=20 style='height: 15.0pt'>
					<td height=20 colspan=4 style='height: 15.0pt; mso-ignore: colspan'></td>
					<td class=xl78></td>
					<td colspan=7 style='mso-ignore: colspan'></td>
				</tr>
				<![if supportMisalignedColumns]>
				<tr height=0 style='display: none'>
					<td width=29 style='width: 22pt'></td>
					<td width=16 style='width: 12pt'></td>
					<td width=6 style='width: 5pt'></td>
					<td width=105 style='width: 79pt'></td>
					<td width=132 style='width: 99pt'></td>
					<td width=28 style='width: 21pt'></td>
					<td width=17 style='width: 13pt'></td>
					<td width=28 style='width: 21pt'></td>
					<td width=132 style='width: 99pt'></td>
					<td width=11 style='width: 8pt'></td>
					<td width=6 style='width: 5pt'></td>
					<td width=16 style='width: 12pt'></td>
				</tr>
				<![endif]>
			</table>
			
			<br><br><br>
			<br><br><br>
			<div style="margin-left: 170px;">
				<input type="button" value ="Gravar Resultado Real" onClick="gravaValoresAdmin();">
			</div>
			<br>
			<div style="margin-left: 170px;">
				<input type="button" value ="Criar novo usuário" onClick='abrirTelaCriacao();'>
			</div>
			<br>
			<div style="margin-left: 170px;">
				<input type="button"  value ="Visualizar todas as apostas" onClick='visualizarTodasApostas()'>
			</div>
			<br>
			<%}else{%>
			<br><br><br>
			<br><br><br>
			<div style="margin-left: 170px;">
				<input type="button"  value ="Gravar aposta!" onClick="gravaValores();">
			</div>
			<br>
			<%}%>
			<br><br><br><br>
			<div style="margin-left: 170px;">
				<input type="button"  value ="Voltar ao menu" onClick='window.location.href = "e_copa_arquivos/copa_menu.htm";'>
			</div>
			<br>
			<div style="margin-left: 170px;">
				<input type="button"  value ="Visualizar sua senha" onClick='visualizarDados()'>
			</div>
			<br><br><br><br><br>
			<div style="margin-left: 170px;">
				<input type="button"  value ="Alterar sua senha" onClick='mostraQuadroAlterarSenha()'>
			</div>
			
			<br><br>
			<table  id="mudarSenha" border=0 cellpadding=0 cellspacing=0 width=326 style="margin-left: 160px;" >
				<col width=29
					style='mso-width-source: userset; mso-width-alt: 1060; width: 22pt'>
				<col width=16
					style='mso-width-source: userset; mso-width-alt: 585; width: 12pt'>
				<col width=6
					style='mso-width-source: userset; mso-width-alt: 219; width: 5pt'>
				<col width=105
					style='mso-width-source: userset; mso-width-alt: 3840; width: 79pt'>
				<col width=132
					style='mso-width-source: userset; mso-width-alt: 4827; width: 99pt'>
				<col width=28
					style='mso-width-source: userset; mso-width-alt: 1024; width: 21pt'>
				<col width=17
					style='mso-width-source: userset; mso-width-alt: 621; width: 13pt'>
				<col width=28
					style='mso-width-source: userset; mso-width-alt: 1024; width: 21pt'>
				<col width=132
					style='mso-width-source: userset; mso-width-alt: 4827; width: 99pt'>
				<col width=11
					style='mso-width-source: userset; mso-width-alt: 402; width: 8pt'>
				<col width=6
					style='mso-width-source: userset; mso-width-alt: 219; width: 5pt'>
				<col width=16
					style='mso-width-source: userset; mso-width-alt: 585; width: 12pt'>
				<tr height=0 style='display: none'>
					<td width=29 style='width: 22pt'></td>
					<td width=16 style='width: 12pt'></td>
					<td width=6 style='width: 5pt'></td>
					<td width=105 style='width: 79pt'></td>
					<td width=132 style='width: 99pt'></td>
					<td width=28 style='width: 21pt'></td>
					<td width=17 style='width: 13pt'></td>
					<td width=28 style='width: 21pt'></td>
					<td width=132 style='width: 99pt'></td>
					<td width=11 style='width: 8pt'></td>
					<td width=6 style='width: 5pt'></td>
					<td width=16 style='width: 12pt'></td>
				</tr>
				<tr height=0 style='display: none'>
					<td height=0 colspan=12 style='mso-ignore: colspan'></td>
				</tr>
				<tr height=0 style='display: none'>
					<td height=0 colspan=12 style='mso-ignore: colspan'></td>
				</tr>
				<tr height=0 style='display: none'>
					<td height=0 colspan=12 style='mso-ignore: colspan'></td>
				</tr>
				<tr height=0 style='display: none'>
					<td height=0 colspan=12 style='mso-ignore: colspan'></td>
				</tr>
				<tr height=5 style='mso-height-source: userset; height: 3.75pt'>
					<td height=5 colspan=12 style='height: 3.75pt; mso-ignore: colspan'></td>
				</tr>
				<tr height=16 style='mso-height-source: userset; height: 12.0pt'>
					<td height=16 style='height: 12.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=6 style='mso-height-source: userset; height: 4.5pt'>
					<td height=6 style='height: 4.5pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				
		
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td colspan=6 class=xl105>Senha Atual:<input type="password" name="nSenhaAtual" id="senhaAtual"></td>
					<td colspan=2 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
			
				
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				
								
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td colspan=6 class=xl105>Nova Senha:<input type="password" name="nNovaSenha" id="novaSenha"></td>
					<td colspan=2 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				
	
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				
				
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td colspan=6 class=xl105>Repita a nova Senha:<input type="password" name="nNovaSenhaRepeat"  id="novaSenhaRepeat"></td>
					<td colspan=2 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				
				
				<tr height=10 style='mso-height-source: userset; height: 7.5pt'>
					<td height=10 style='height: 7.5pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				
				
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td></td>
					<td colspan=6 class=xl105><input type="submit" value="Mudar senha!" onclick = "alterarSenha();">&nbsp;&nbsp;<input type="submit" value="Sair" onclick = "fecharMudaSenha();"></td>
					<td colspan=2 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=10 style='mso-height-source: userset; height: 7.5pt'>
					<td height=10 style='height: 7.5pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
		
		
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td colspan=9 style='mso-ignore: colspan'></td>
					<td class=xl77>&nbsp;</td>
				</tr>
		
				<tr height=20 style='height: 15.0pt'>
					<td height=20 style='height: 15.0pt'></td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
					<td class=xl77>&nbsp;</td>
				</tr>
				<tr height=20 style='height: 15.0pt'>
					<td height=20 colspan=4 style='height: 15.0pt; mso-ignore: colspan'></td>
					<td class=xl78></td>
					<td colspan=7 style='mso-ignore: colspan'></td>
				</tr>
				<tr height=20 style='height: 15.0pt'>
					<td height=20 colspan=4 style='height: 15.0pt; mso-ignore: colspan'></td>
					<td class=xl78></td>
					<td colspan=7 style='mso-ignore: colspan'></td>
				</tr>
				<tr height=20 style='height: 15.0pt'>
					<td height=20 colspan=4 style='height: 15.0pt; mso-ignore: colspan'></td>
					<td class=xl78></td>
					<td colspan=7 style='mso-ignore: colspan'></td>
				</tr>
				<tr height=20 style='height: 15.0pt'>
					<td height=20 colspan=4 style='height: 15.0pt; mso-ignore: colspan'></td>
					<td class=xl78></td>
					<td colspan=7 style='mso-ignore: colspan'></td>
				</tr>
				<![if supportMisalignedColumns]>
				<tr height=0 style='display: none'>
					<td width=29 style='width: 22pt'></td>
					<td width=16 style='width: 12pt'></td>
					<td width=6 style='width: 5pt'></td>
					<td width=105 style='width: 79pt'></td>
					<td width=132 style='width: 99pt'></td>
					<td width=28 style='width: 21pt'></td>
					<td width=17 style='width: 13pt'></td>
					<td width=28 style='width: 21pt'></td>
					<td width=132 style='width: 99pt'></td>
					<td width=11 style='width: 8pt'></td>
					<td width=6 style='width: 5pt'></td>
					<td width=16 style='width: 12pt'></td>
				</tr>
				<![endif]>
			</table>
			
			
			
			
			
		</div>
	
	</div>
</body>

</html>
