<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<html>

<head>

<meta http-equiv=Content-Type content="text/html; charset=windows-1252">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 15">

<link rel=File-List href=filelist.xml>

<link rel=Stylesheet href=stylesheetold.css>
<style>
<!--
table {
	mso-displayed-decimal-separator: "\,";
	mso-displayed-thousand-separator: "\.";
}

@page {
	margin: .79in .51in .79in .51in;
	mso-header-margin: .31in;
	mso-footer-margin: .31in;
}
-->
</style>

<script type="text/javascript" src="../jquery.jqplot.1.0.8r1250/dist/jquery.js"></script>

<script type="text/javascript">
	$(function() {
		//alert('teste');
	});

	function validateForm() {
		
		var log = document.forms["formLogin"]["nLogin"].value;
		var senha = document.forms["formLogin"]["nSenha"].value;
		
		if ((log == null || log == "") || (senha == null || senha == "") ) {
			alert("Preencha tudo!");
			return false;
		}
	}
</script>


</head>


<body link="#0563C1" vlink="#954F72">

	<table border=0 cellpadding=0 cellspacing=0 width=526
		style='border-collapse: collapse; table-layout: fixed; width: 396pt'>
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
			<td colspan=3 rowspan=3 height=60 width=265	style='height: 45.0pt; width: 199pt' align=left valign=top>
				<span style='mso-ignore: vglayout; position: absolute; z-index: 1; margin-left: 229px; margin-top: 2px; width: 221px; height: 79px'>
					<img width=221 height=75 src=image002.png>
				</span>
				<span style='mso-ignore: vglayout2'>
					<table cellpadding=0 cellspacing=0>
						<tr>
							<td colspan=3 rowspan=3 height=60 class=xl81 width=265 style='height: 45.0pt; width: 199pt'>Tabela da Euro 2016</td>
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

<form action="../euroServlet" name="formLogin" onsubmit="return validateForm()" method="POST">
<input type="hidden" name="tipo" value="log">
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
			<td colspan=6 class=xl105>LOGIN:<input type="text" name="nLogin" id="login"></td>
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
			<%
				if (request.getParameter("error") != null) {
			%>
				<td colspan=9 style='mso-ignore: colspan'><span style='color:red;margin-left: 167px;'>&nbsp;&nbsp;&nbsp;Login inválido!</span></td>
			<%
				}else{
			%>
				<td colspan=9 style='mso-ignore: colspan'></td>
			<%
				}
			%>
			
			<td class=xl77>&nbsp;</td>
		</tr>
		<tr height=20 style='height: 15.0pt'>
			<td height=20 style='height: 15.0pt'></td>
			<td class=xl77>&nbsp;</td>
			<td></td>
			<td colspan=6 class=xl105>SENHA:<input type="password" name="nSenha" id="senha"></td>
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
			<td colspan=6 class=xl105><input type="submit"  value="LOGAR!"></td>
			<td colspan=2 style='mso-ignore: colspan'></td>
			<td class=xl77>&nbsp;</td>
		</tr>
		<tr height=10 style='mso-height-source: userset; height: 7.5pt'>
			<td height=10 style='height: 7.5pt'></td>
			<td class=xl77>&nbsp;</td>
			<td colspan=9 style='mso-ignore: colspan'></td>
			<td class=xl77>&nbsp;</td>
		</tr>
</form>


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

</body>

</html>