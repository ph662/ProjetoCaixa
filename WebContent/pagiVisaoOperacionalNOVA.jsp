<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="caixa.dirid.VO.SensibilizacaoVO"%>
<%@ page import="caixa.dirid.UTEIS.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="phelipe662@gmail.com">
	<link href="imagens/caixaFav.ico" rel="shortcut icon" type="image/x-icon">
    <title>Vis&atilde;o Operacional</title>
    <script language="javascript" type="text/javascript" src="AjaxJS/funcoesAjax.js"></script>
		
	<%@ include file="importHeadBootstrap.jsp"%>
	<%@ include file="importHead_JQplot.jsp"%>
	<style>
	
		/*
		 * Base structure
		 */
		
		/* Move down content because we have a fixed navbar that is 50px tall */
		body {
		  padding-top: 50px;
		}
		
		
		/*
		 * Global add-ons
		 */
		
		.sub-header {
		  padding-bottom: 10px;
		  border-bottom: 1px solid #eee;
		}
		
		/*
		 * Top navigation
		 * Hide default border to remove 1px line.
		 */
		.navbar-fixed-top {
		  border: 0;
		}
		
		/*
		 * Sidebar
		 */
		
		/* Hide for mobile, show later */
		.sidebar {
		  display: none;
		}
		@media (min-width: 768px) {
		  .sidebar {
		    position: fixed;
		    top: 51px;
		    bottom: 0;
		    left: 0;
		    z-index: 1000;
		    display: block;
		    padding: 20px;
		    overflow-x: hidden;
		    overflow-y: auto; /* Scrollable contents if viewport is shorter than content. */
		    background-color: #f5f5f5;
		    border-right: 1px solid #eee;
		  }
		}
		
		/* Sidebar navigation */
		.nav-sidebar {
		  margin-right: -21px; /* 20px padding + 1px border */
		  margin-bottom: 20px;
		  margin-left: -20px;
		}
		.nav-sidebar > li > a {
		  padding-right: 20px;
		  padding-left: 20px;
		}
		.nav-sidebar > .active > a,
		.nav-sidebar > .active > a:hover,
		.nav-sidebar > .active > a:focus {
		  color: #fff;
		  background-color: #428bca;
		}
		
		
		/*
		 * Main content
		 */
		
		.main {
		  padding: 20px;
		}
		@media (min-width: 768px) {
		  .main {
		    padding-right: 40px;
		    padding-left: 40px;
		  }
		}
		.main .page-header {
		  margin-top: 0;
		}
		
		
		/*
		 * Placeholder dashboard ideas
		 */
		
		.placeholders {
		  margin-bottom: 30px;
		  text-align: center;
		}
		.placeholders h4 {
		  margin-bottom: 0;
		}
		.placeholder {
		  margin-bottom: 20px;
		}
		.placeholder img {
		  display: inline-block;
		  border-radius: 50%;
		}
		
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
	
	</style>
  </head>

  <body onload="dadosSensibilizacao()">

   <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#" id="titulo">DIRID - Operacional</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="active" id="botaoHome">
            	<a href="pagiMenuPrincipal.jsp">Menu</a>
           	</li>
            <li><a href="#about">About</a></li>
            <li><a href="#contact">Contact</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li><a href="#">Action</a></li>
                <li><a href="#">Another action</a></li>
                <li><a href="#">Something else here</a></li>
                <li role="separator" class="divider"></li>
                <li class="dropdown-header">Nav header</li>
                <li><a href="#">Separated link</a></li>
                <li><a href="#">One more separated link</a></li>
              </ul>
            </li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>


    <div class="container-fluid">
      <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
          <ul class="nav nav-sidebar">
            <li class="active"><a href="#">Overview <span class="sr-only">(current)</span></a></li>
            <li><a href="#">Reports</a></li>
            <li><a href="#">Analytics</a></li>
            <li><a href="#">Export</a></li>
          </ul>
          <ul class="nav nav-sidebar">
            <li><a href="">Nav item</a></li>
            <li><a href="">Nav item again</a></li>
            <li><a href="">One more nav</a></li>
            <li><a href="">Another nav item</a></li>
            <li><a href="">More navigation</a></li>
          </ul>
          <ul class="nav nav-sidebar">
            <li><a href="">Nav item again</a></li>
            <li><a href="">One more nav</a></li>
            <li><a href="">Another nav item</a></li>
          </ul>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
          <h1 class="page-header">Dashboard</h1>

          <div class="row placeholders">
            <div class="col-xs-6 col-sm-3 placeholder">
              <img data-src="holder.js/200x200/auto/sky" class="img-responsive" alt="Generic placeholder thumbnail">
              <h4>Label</h4>
              <span class="text-muted">Something else</span>
            </div>
            <div class="col-xs-6 col-sm-3 placeholder">
              <img data-src="holder.js/200x200/auto/vine" class="img-responsive" alt="Generic placeholder thumbnail">
              <h4>Label</h4>
              <span class="text-muted">Something else</span>
            </div>
            <div class="col-xs-6 col-sm-3 placeholder">
              <img data-src="holder.js/200x200/auto/sky" class="img-responsive" alt="Generic placeholder thumbnail">
              <h4>Label</h4>
              <span class="text-muted">Something else</span>
            </div>
            <div class="col-xs-6 col-sm-3 placeholder">
              <img data-src="holder.js/200x200/auto/vine" class="img-responsive" alt="Generic placeholder thumbnail">
              <h4>Label</h4>
              <span class="text-muted">Something else</span>
            </div>
          </div>

          <h2 class="sub-header">Section title</h2>
          <div class="table-responsive">
          	<%
			/***********/
			/*VARIAVEIS*/
			/***********/
			
			DecimalFormat percentForm = new DecimalFormat("#.#%");
			DecimalFormat roundForm = new DecimalFormat("0.0");
			
			//Recebem o objeto "VO" da servlet "MenuServlet.java"
			List<SensibilizacaoVO> listaSensibilizacao = (List<SensibilizacaoVO>) request.getAttribute("listaSensibilizacao");
			List<SensibilizacaoVO> listaPorAgenciaValor = (List<SensibilizacaoVO>) request.getAttribute("listaPorAgenciaQuantidadeValorSensibilizacao");
			%>
          
          
            <table class="table table-striped">
              <thead>
                <tr>
                  <th>#</th>
                  <th>Header</th>
                  <th>Header</th>
                  <th>Header</th>
                  <th>Header</th>
                </tr>
              </thead>
              <tbody>
    			<%for(int i = 0;i < listaPorAgenciaValor.size();i++){ %>
				<tr>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;<%=listaPorAgenciaValor.get(i).getAgencia()%>&nbsp;&nbsp;&nbsp;</td>
					
					<%if(listaPorAgenciaValor.get(i).getSR() == null){ %>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;Vazio&nbsp;</td>
					<%}else{ %>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;<%=listaPorAgenciaValor.get(i).getSR()%>&nbsp;&nbsp;&nbsp;</td>
					<%} %>
					
					<%if(listaPorAgenciaValor.get(i).getSUAT() == null){ %>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;Vazio&nbsp;</td>
					<%}else{ %>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;<%=listaPorAgenciaValor.get(i).getSUAT()%>&nbsp;&nbsp;&nbsp;</td>
					<%} %>
					
					<td style="text-align:right"><%=listaPorAgenciaValor.get(i).getQuantidade()%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td><%=listaPorAgenciaValor.get(i).getValor()%>&nbsp;&nbsp;</td>
				</tr>
			<%}%>
    
    
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>

	<%@ include file="importBodyBootstrap.jsp"%>
  </body>
</html>
