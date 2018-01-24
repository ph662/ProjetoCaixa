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
		<link href="imagens/caixaFav.ico" rel="shortcut icon" type="image/x-icon">
		<title>Erro 500</title>
		<%@ include file="importHeadBootstrap.jsp"%>

		<!-- import do Jquery -->
		<script type="text/javascript" src="jquery.jqplot.1.0.8r1250/dist/jquery.js"></script>

		<style>
			.texto{
				color:white;
			}
			a{
				color:#e6d500;
			}
		</style>
	<script>	
$(document).ready(function () {
	
	 var a = sessionStorage.getItem("numeroAceitacao");
	    alert(a);
//	var thisis ='tess';
	//var popwin = window.open('http://10.125.7.49:8080/ProjetoCaixa/pagiMenuPrincipal.jsp');
	//popwin.vari = thisis;
	
	var id = "loucuraaaaaa";

	    //the sessionStorage.setItem(); is the predefined function in javascript
	    //which will support for every browser that will store the sessions.
	    sessionStorage.setItem("sent", id); 

	    //this is to open a window in new tab
	    window.open("http://10.125.7.49:8080/ProjetoCaixa/pagiMenuPrincipal.jsp","_blank");

	
    setTimeout(function(){window.location.href='http://10.125.7.49:8080/ProjetoCaixa/pagiRelatorioSensibilizacaoFinanceiro.jsp';},4000);
    
    
	$('.searchType').click(function() {
   alert($(this).attr('id'));  //-->this will alert id of checked checkbox.
      if(this.checked){
           $.ajax({
               type: "POST",
               url: 'searchOnType.jsp',
               data: $(this).attr('id'), //--> send id of checked checkbox on other page
               success: function(data) {
                   alert('it worked');
                   alert(data);
                   $('#container').html(data);
               },
                error: function() {
                   alert('it broke');
               },
               complete: function() {
                   alert('it completed');
               }
           });

           }
     });
});
		</script>
		
	</head>


	<body role="document" background="imagens/background.jpg">
	
	<%
	for (Enumeration<String> e = request.getAttributeNames(); e.hasMoreElements();) {

		out.println("FOR 1 - "+ e.nextElement());
		//out.println("FOR 2 - "+e.toString());
		
		
	}
	
	
	
	
	%>
		<div class="search-inputs">
			<input class="searchName" type="text" placeholder="Search Here.."></input>

			<input class="searchType" type="checkbox" name="emailNotification" id="emailNotification"> <label class="searchtype1label">Email Notification</label>
			<input class="searchType" type="checkbox" name="SharingNotification" id="SharingNotification"> <label class="searchtype2label">File Sharing Notification</label>
			
			<input class="searchDateFrom" type="text" placeholder="Search From"></input>
			<input class="searchDateTo" type="text" placeholder="Search To"></input>
			<input class="Datesubmit" type="button" value="Search"></input>
			
			<div id="container">
			</div>
		</div>
		<%@ include file="importBodyBootstrap.jsp"%>
	</body>
</html>