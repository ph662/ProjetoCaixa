<%@ page import="caixa.dirid.VO.FatuMensalVO"%>
<%@ page import="java.util.*"%>
		<%
			FatuMensalVO valor = (FatuMensalVO) request.getAttribute("rvne");
		%>					
		
		<div id="fieldAlterar" style="display: block; margin-left: 51px;">
			<div class="navbar-form navbar-left">
				<div class="form-group">
					<input id="fieldAltera" type="text" class="form-control" name="<%=request.getAttribute("coluna")%>_<%=request.getAttribute("id")%>_Prod" value="<%=valor.getRVNE()%>" >
				</div>
					<button type="button" class="btn btn-default" onclick="alterar();">Alterar</button>
			</div>
		</div>