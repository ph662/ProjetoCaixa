<%@ page import="caixa.dirid.VO.VisoesVO"%>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MENU</title>
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
		</style>	
	</head>
	<body>
		<%@ include file="cabecBarraAzul.jsp"%>
		<div class="container">
			<%@ include file="cabecImageCaixa.jsp"%>
			<%@ include file="menuEsquerdo.jsp"%>
			
			<div class="content">
				<div>
					<div>
						<h1><center>Apolices</center></h1>
						<table id="tabelaApolice">
							<thead>
								<tr>
									<th>PROD</th>
									<th>RAMO</th>
									<th>QTD</th>
								</tr>
							</thead>	
							<tbody>
								<%
									VisoesVO menu = new VisoesVO();
									List<VisoesVO> lista = (List<VisoesVO>) request.getAttribute("apolice");
									for (int i = 0; i < lista.size(); i++) {
								%>
										<tr>
											<td><%=lista.get(i).getProd()%></td>
											<td><%=lista.get(i).getRamo()%></td>
											<td><%=lista.get(i).getQtd()%></td>
										</tr>
								<%
									}
								%>
							</tbody>
						</table>
						<br> 
						<br> 
						<a href="index.jsp">
							<button type="button">Voltar</button>
						</a>
					</div>
				</div>
			</div> <!-- TAG CONTEUDO -->
			
			<div class="footer">
				<p>Este rodapé possui a declaração position:relative; para
					fornecer o hasLayout do Internet Explorer 6 ao rodapé e ocasionar a
					limpeza corretamente. Se não for solicitado a ter suporte do IE6,
					você pode removê-lo.</p>
			</div><!-- end .footer -->
		</div><!-- end .container -->
	</body>
</html>