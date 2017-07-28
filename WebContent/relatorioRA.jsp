<%@ page import="caixa.dirid.VO.CoberturasVO"%>
<%@ page import="caixa.dirid.VO.RelatorioAceitacaoVO"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="caixa.dirid.UTEIS.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=windows-1252">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 15">
<title>RRA</title>
<link rel=File-List href="imagens/RRA_arquivos/filelist.xml">
<!--[if !mso]>
		<style>
		v\:* {behavior:url(#default#VML);}
		o\:* {behavior:url(#default#VML);}
		x\:* {behavior:url(#default#VML);}
		.shape {behavior:url(#default#VML);}
		</style>
		<![endif]-->
<style id="RRA_24691_Styles">
<!--
table {
	mso-displayed-decimal-separator: "\,";
	mso-displayed-thousand-separator: "\.";
	
}

.xl6924691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 6.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: general;
	vertical-align: middle;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl7024691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 8.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: general;
	vertical-align: middle;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl7124691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 8.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: general;
	vertical-align: middle;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl7224691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 10.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: general;
	vertical-align: middle;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl7324691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 10.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: general;
	vertical-align: middle;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl7424691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: general;
	vertical-align: middle;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl7524691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: general;
	vertical-align: middle;
	border-top: none;
	border-right: .5pt solid windowtext;
	border-bottom: none;
	border-left: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl7624691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: general;
	vertical-align: middle;
	background: silver;
	mso-pattern: black none;
	white-space: normal;
}

.xl7724691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: general;
	vertical-align: middle;
	border-top: none;
	border-right: .5pt solid windowtext;
	border-bottom: none;
	border-left: none;
	background: silver;
	mso-pattern: black none;
	white-space: normal;
}

.xl7824691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: justify;
	vertical-align: middle;
	border-top: none;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl7924691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: right;
	vertical-align: middle;
	border-top: none;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl8024691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: "Short Date";
	text-align: center;
	vertical-align: middle;
	border-top: none;
	border-right: .5pt solid windowtext;
	border-bottom: .5pt solid windowtext;
	border-left: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl8124691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 10.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: general;
	vertical-align: middle;
	border-top: .5pt solid windowtext;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl8224691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl8324691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: right;
	vertical-align: middle;
	border-top: none;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl8424691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border-top: .5pt solid windowtext;
	border-right: .5pt solid windowtext;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl8524691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: general;
	vertical-align: middle;
	border-top: none;
	border-right: none;
	border-bottom: none;
	border-left: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl8624691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 8.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: general;
	vertical-align: middle;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl8724691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: "Short Date";
	text-align: center;
	vertical-align: middle;
	border-top: none;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl8824691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl8924691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 6.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: general;
	vertical-align: middle;
	border-top: .5pt solid windowtext;
	border-right: none;
	border-bottom: none;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl9024691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: "\0022R$\0022\\ \#\,\#\#0\.00";
	text-align: center;
	vertical-align: middle;
	border: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl9124691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl9224691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border-top: none;
	border-right: .5pt solid windowtext;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl9324691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 10.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: "\#\\ ?\/?";
	text-align: general;
	vertical-align: middle;
	border-top: .5pt solid windowtext;
	border-right: none;
	border-bottom: none;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl9424691 {
	padding-left: 2px;
	padding-right: 2px;
	mso-ignore: padding;
	color: windowtext;
	font-size: 10.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: "\#\\ ?\/?";
	text-align: center;
	vertical-align: middle;
	border-top: none;
	border-right: .5pt solid windowtext;
	border-bottom: .5pt solid windowtext;
	border-left: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl9524691 {
	padding-left: 2px;
	padding-right: 2px;
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border-top: none;
	border-right: .5pt solid windowtext;
	border-bottom: .5pt solid windowtext;
	border-left: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl9624691 {
	padding-left: 2px;
	padding-right: 2px;
	mso-ignore: padding;
	color: windowtext;
	font-size: 10.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: "Short Date";
	text-align: center;
	vertical-align: middle;
	border-top: none;
	border-right: .5pt solid windowtext;
	border-bottom: .5pt solid windowtext;
	border-left: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl9724691 {
	padding-left: 2px;
	padding-right: 2px;
	mso-ignore: padding;
	color: windowtext;
	font-size: 10.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl9824691 {
	padding-left: 2px;
	padding-right: 2px;
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border-top: none;
	border-right: none;
	border-bottom: none;
	border-left: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl9924691 {
	padding-left: 2px;
	padding-right: 2px;
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format:
		"\0022R$ \0022\#\,\#\#0\.00_\)\;\[Red\]\\\(\0022R$ \0022\#\,\#\#0\.00\\\)";
	text-align: center;
	vertical-align: middle;
	border: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl10024691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 10.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: general;
	vertical-align: middle;
	border-top: .5pt solid windowtext;
	border-right: .5pt solid windowtext;
	border-bottom: none;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl10124691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 11.5pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: general;
	vertical-align: middle;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl10224691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border-top: none;
	border-right: .5pt solid windowtext;
	border-bottom: .5pt solid windowtext;
	border-left: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl10324691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border-top: none;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl10424691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border-top: none;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl10524691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl10624691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border-top: none;
	border-right: .5pt solid windowtext;
	border-bottom: none;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl10724691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 11.5pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border-top: none;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}

.xl10824691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: left;
	vertical-align: middle;
	border: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl10924691 {
	padding-left: 3px; 
	padding-right: 3px;
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: left;
	vertical-align: middle;
	border-top: none;
	border-right: none;
	border-bottom: none;
	border-left: .5pt solid windowtext;
	background: silver;
	mso-pattern: black none;
	white-space: normal;
}

.xl11024691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: left;
	vertical-align: middle;
	background: silver;
	mso-pattern: black none;
	white-space: normal;
}

.xl11124691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: general;
	vertical-align: middle;
	border-top: none;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl11224691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: general;
	vertical-align: middle;
	border-top: none;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl11324691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: right;
	vertical-align: bottom;
	border-top: none;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl11424691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: right;
	vertical-align: bottom;
	border-top: none;
	border-right: .5pt solid windowtext;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl11524691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: general;
	vertical-align: middle;
	border-top: none;
	border-right: none;
	border-bottom: none;
	border-left: .5pt solid windowtext;
	background: silver;
	mso-pattern: black none;
	white-space: normal;
}

.xl11624691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: general;
	vertical-align: middle;
	background: silver;
	mso-pattern: black none;
	white-space: normal;
}

.xl11724691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: general;
	vertical-align: middle;
	border-top: none;
	border-right: .5pt solid windowtext;
	border-bottom: none;
	border-left: none;
	background: silver;
	mso-pattern: black none;
	white-space: normal;
}

.xl11824691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: justify;
	vertical-align: middle;
	border-top: none;
	border-right: none;
	border-bottom: none;
	border-left: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl11924691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: justify;
	vertical-align: middle;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl12024691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: justify;
	vertical-align: middle;
	border-top: none;
	border-right: .5pt solid windowtext;
	border-bottom: none;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl12124691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: general;
	vertical-align: middle;
	border-top: none;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl12224691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: general;
	vertical-align: middle;
	border-top: none;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl12324691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: right;
	vertical-align: bottom;
	border-top: none;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl12424691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: right;
	vertical-align: bottom;
	border-top: none;
	border-right: .5pt solid windowtext;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl12524691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: justify;
	vertical-align: middle;
	border-top: none;
	border-right: none;
	border-bottom: none;
	border-left: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl12624691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: justify;
	vertical-align: middle;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl12724691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: justify;
	vertical-align: middle;
	border-top: none;
	border-right: .5pt solid windowtext;
	border-bottom: none;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl12824691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: general;
	vertical-align: middle;
	border-top: none;
	border-right: none;
	border-bottom: none;
	border-left: .5pt solid windowtext;
	background: silver;
	mso-pattern: black none;
	white-space: normal;
}

.xl12924691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: "Short Date";
	text-align: left;
	vertical-align: middle;
	border-top: none;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl13024691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: "Short Date";
	text-align: left;
	vertical-align: middle;
	border-top: none;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl13124691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: left;
	vertical-align: middle;
	border-top: none;
	border-right: none;
	border-bottom: none;
	border-left: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl13224691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: left;
	vertical-align: middle;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl13324691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: left;
	vertical-align: middle;
	border-top: none;
	border-right: .5pt solid windowtext;
	border-bottom: none;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl13424691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: left;
	vertical-align: top;
	border-top: none;
	border-right: none;
	border-bottom: none;
	border-left: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl13524691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: left;
	vertical-align: top;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl13624691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: left;
	vertical-align: top;
	border-top: none;
	border-right: .5pt solid windowtext;
	border-bottom: none;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl13724691 {
	padding-left: 2px; 
	padding-right: 2px;
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: general;
	vertical-align: middle;
	border: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl13824691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border-top: .5pt solid windowtext;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl13924691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: left;
	vertical-align: middle;
	border-top: none;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl14024691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: left;
	vertical-align: middle;
	border-top: none;
	border-right: .5pt solid windowtext;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl14124691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: general;
	vertical-align: middle;
	border-top: none;
	border-right: .5pt solid windowtext;
	border-bottom: none;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl14224691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: general;
	vertical-align: middle;
	border-top: .5pt solid windowtext;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl14324691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: general;
	vertical-align: middle;
	border-top: .5pt solid windowtext;
	border-right: .5pt solid windowtext;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl14424691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: general;
	vertical-align: middle;
	border-top: .5pt solid windowtext;
	border-right: none;
	border-bottom: none;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl14524691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border-top: none;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl14624691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: left;
	vertical-align: middle;
	border-top: none;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl14724691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: general;
	vertical-align: middle;
	border-top: .5pt solid windowtext;
	border-right: none;
	border-bottom: none;
	border-left: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl14824691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border-top: none;
	border-right: .5pt solid windowtext;
	border-bottom: .5pt solid windowtext;
	border-left: .5pt solid windowtext;
	background: silver;
	mso-pattern: black none;
	white-space: normal;
}

.xl14924691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border-top: .5pt solid windowtext;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl15024691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 6.0pt;
	font-weight: 700;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl15124691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: Standard;
	text-align: center;
	vertical-align: middle;
	border-top: .5pt solid windowtext;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl15224691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: Standard;
	text-align: center;
	vertical-align: middle;
	border-top: .5pt solid windowtext;
	border-right: .5pt solid windowtext;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl15324691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: left;
	vertical-align: middle;
	border-top: .5pt solid windowtext;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: .5pt solid windowtext;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl15424691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: left;
	vertical-align: middle;
	border-top: .5pt solid windowtext;
	border-right: none;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl15524691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: left;
	vertical-align: middle;
	border-top: .5pt solid windowtext;
	border-right: .5pt solid windowtext;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: normal;
}

.xl15624691 {
	padding-left: 3px;  padding-right: 3px; 
	mso-ignore: padding;
	color: windowtext;
	font-size: 9.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: Arial, sans-serif;
	mso-font-charset: 0;
	mso-number-format: General;
	text-align: center;
	vertical-align: middle;
	border-top: none;
	border-right: .5pt solid windowtext;
	border-bottom: .5pt solid windowtext;
	border-left: none;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}
-->
</style>


</head>

<body>
	<!--[if !excel]>&nbsp;&nbsp;<![endif]-->
	<!--As seguintes informações foram geradas pelo Assistente para Publicação como
	Página da Web do Microsoft Excel.-->
	<!--Se o mesmo item for republicado a partir do Excel, todas as informações
	entre as marcas DIV serão substituídas.-->
	<!----------------------------->
	<!--INÍCIO DA SAÍDA DO 'ASSISTENTE PARA PUBLICAÇÃO COMO PÁGINA DA WEB' DO EXCEL
	-->
	<!----------------------------->
		<%
			List<RelatorioAceitacaoVO> dadosRelatorio = (List<RelatorioAceitacaoVO>) request.getAttribute("relatorioSalvo");
			List<CoberturasVO> coberturas = (List<CoberturasVO>) request.getAttribute("coberturasRelatorioSalvo");
			Uteis util = new Uteis();
		%>
		
	<div id="RRA_24691" align=center x:publishsource="Excel">

		<table border=0 cellpadding=0 cellspacing=0 width=795 class=xl7224691 style='border-collapse: collapse; table-layout: fixed; width: 595pt'>
			<col class=xl7224691 width=131 span=5 style='mso-width-source: userset; mso-width-alt: 4790; width: 98pt'>
			<col class=xl7224691 width=140 style='mso-width-source: userset; mso-width-alt: 5120; width: 105pt'>
			<tr height=20 style='height: 15.0pt'>
				<td height=20 width=131 style='height: 15.0pt; width: 98pt'	align=left valign=top>
					<span	style='mso-ignore: vglayout; position: absolute; z-index: 1; margin-left: 0px; margin-top: 0px; width: 220px; height: 80px'>
						<img width=220 height=80 src="imagens/RRA_arquivos/RRA_24691_image002.png" alt="caixa_seguradora_cor_rgb_positiva" v:shapes="Imagem_x0020_2">
					</span> 
					<span style='mso-ignore: vglayout2'>
						<table cellpadding=0 cellspacing=0>
							<tr>
								<td height=20 class=xl7224691 width=131	style='height: 15.0pt; width: 98pt'>
									<a name="RANGE!A1:F64"></a>
								</td>
							</tr>
						</table>
					</span>
				</td>
				<td class=xl7224691 width=131 style='width: 98pt'></td>
				<td class=xl7224691 width=131 style='width: 98pt'></td>
				<td class=xl7224691 width=131 style='width: 98pt'></td>
				<td class=xl7224691 width=131 style='width: 98pt'></td>
				<td class=xl7224691 width=140 style='width: 105pt'></td>
			</tr>
			<tr height=20 style='mso-height-source: userset; height: 15.0pt'>
				<td height=20 class=xl10124691 style='height: 15.0pt'></td>
				<td class=xl10124691></td>
				<td class=xl10124691></td>
				<td class=xl10124691></td>
				<td class=xl10124691></td>
				<td class=xl7224691></td>
			</tr>
			<tr height=23 style='mso-height-source: userset; height: 17.25pt'>
				<td height=23 class=xl10124691 style='height: 17.25pt'></td>
				<td class=xl10124691></td>
				<td class=xl10124691></td>
				<td class=xl10124691></td>
				<td class=xl10124691></td>
				<td class=xl7224691></td>
			</tr>
			<tr height=20 style='mso-height-source: userset; height: 15.0pt'>
				<td colspan=6 height=20 class=xl10724691 style='height: 15.0pt'>RELATORIO
					RESUMIDO DE ACEITAÇÃO DE RISCOS PATRIMONIAIS</td>
			</tr>
			<tr height=17 style='mso-height-source: userset; height: 12.75pt'>
				<td height=17 class=xl9724691
					style='height: 12.75pt; border-top: none'>Data:</td>
				<td class=xl8824691 width=131
					style='border-top: none; border-left: none; width: 98pt'>Nº da
					Aceitação</td>
				<td class=xl8824691 width=131
					style='border-top: none; border-left: none; width: 98pt'>Nº da
					Proposta</td>
				<td colspan=2 class=xl13824691 width=262
					style='border-right: .5pt solid black; border-left: none; width: 196pt'>Produto
					- Ramo</td>
				<td class=xl8424691 width=140 style='border-top: none; width: 105pt'>Processo
					SUSEP:</td>
			</tr>
			<tr height=17 style='mso-height-source: userset; height: 12.75pt'>
				<td height=17 class=xl9624691 style='height: 12.75pt'><%= util.dataAtual(4)%></td>
				<td class=xl9524691 width=131 style='border-left: none; width: 98pt'><%=dadosRelatorio.get(0).getNumeroAceitacao()%></td>
				<td class=xl9424691 width=131 style='border-left: none; width: 98pt'><a
					name="RANGE!C6"><%=dadosRelatorio.get(0).getNumeroProposta()%></a></td>
				<td colspan=2 class=xl14524691 width=262
					style='border-right: .5pt solid black; border-left: none; width: 196pt'><a
					name="RANGE!D6">MULTIRISCO EMPRESARIAL - 1804</a></td>
				<td class=xl9224691 width=140 style='width: 105pt'>15.414.000.427/2007-06</td>
			</tr>
			<tr height=17 style='mso-height-source: userset; height: 12.75pt'>
				<td colspan=2 height=17 class=xl14724691 width=262
					style='height: 12.75pt; width: 196pt'>Segurado:</td>
				<td class=xl7424691 width=131 style='width: 98pt'></td>
				<td class=xl7424691 width=131 style='width: 98pt'></td>
				<td colspan=2 class=xl8524691 width=271
					style='border-right: .5pt solid black; width: 203pt'>CNPJ/CPF</td>
			</tr>
			<tr height=19 style='mso-height-source: userset; height: 14.25pt'>
				<td colspan=4 height=19 class=xl13924691 width=524
					style='border-right: .5pt solid black; height: 14.25pt; width: 392pt'><%=dadosRelatorio.get(0).getSegurado()%></td>
				<td colspan=2 class=xl13924691 width=271
					style='border-right: .5pt solid black; border-left: none; width: 203pt'><%=dadosRelatorio.get(0).getCpf()%></td>
			</tr>
			<tr height=15 style='mso-height-source: userset; height: 11.25pt'>
				<td colspan=2 height=15 class=xl8524691 width=262
					style='height: 11.25pt; width: 196pt'>Local do Risco:</td>
				<td class=xl7424691 width=131 style='width: 98pt'></td>
				<td class=xl8524691 width=131 style='width: 98pt'>Atividade
					Principal</td>
				<td class=xl7424691 width=131 style='width: 98pt'></td>
				<td class=xl10024691 style='border-top: none'>&nbsp;</td>
			</tr>
			<tr height=28 style='mso-height-source: userset; height: 21.0pt'>
				<td colspan=3 height=28 class=xl13924691 width=393
					style='height: 21.0pt; width: 294pt'><%=dadosRelatorio.get(0).getLocalRisco()%></td>
				<td colspan=3 class=xl13924691 width=402
					style='border-right: .5pt solid black; width: 301pt'><%=dadosRelatorio.get(0).getAtividadePrincipal()%></td>
			</tr>
			<tr class=xl8124691 height=8
				style='mso-height-source: userset; height: 6.0pt'>
				<td colspan=2 height=8 class=xl14424691 width=262
					style='height: 6.0pt; width: 196pt'>&nbsp;</td>
				<td class=xl9324691 width=131 style='border-top: none; width: 98pt'>&nbsp;</td>
				<td colspan=2 class=xl8924691 width=262 style='width: 196pt'>&nbsp;</td>
				<td class=xl8924691 width=140 style='border-top: none; width: 105pt'>&nbsp;</td>
			</tr>
			<tr height=20 style='mso-height-source: userset; height: 15.0pt'>
				<td colspan=6 height=20 class=xl14824691 width=795
					style='height: 15.0pt; width: 595pt'>DEMONSTRATIVO DE PRÊMIO -
					VALORES EM R$</td>
			</tr>
			<tr height=20 style='mso-height-source: userset; height: 15.0pt'>
				<td colspan=2 height=20 class=xl13724691 width=262
					style='height: 15.0pt; width: 196pt'>Prêmio Líquido</td>
				<td class=xl9924691 width=131
					style='border-top: none; border-left: none; width: 98pt'>R$ <%=dadosRelatorio.get(0).getPremioLiq()%></td>
				<td colspan=3 class=xl13824691 width=402
					style='border-right: .5pt solid black; border-left: none; width: 301pt'>&nbsp;</td>
			</tr>
			<tr height=20 style='mso-height-source: userset; height: 15.0pt'>
				<td colspan=2 height=20 class=xl13724691 width=262
					style='height: 15.0pt; width: 196pt'>Prêmio NET</td>
				<td class=xl9924691 width=131
					style='border-top: none; border-left: none; width: 98pt'>R$ <%=dadosRelatorio.get(0).getPremioNet()%></td>
				<td colspan=2 class=xl14224691 width=262
					style='border-right: .5pt solid black; border-left: none; width: 196pt'>Limite
					de Sinistro</td>
				<td class=xl9924691 width=140
					style='border-top: none; border-left: none; width: 105pt'>R$ <%=dadosRelatorio.get(0).getLimiteSinistro()%></td>
			</tr>
			<tr height=20 style='mso-height-source: userset; height: 15.0pt'>
				<td colspan=2 height=20 class=xl13724691 width=262
					style='height: 15.0pt; width: 196pt'>Prêmio Retido</td>
				<td class=xl9924691 width=131
					style='border-top: none; border-left: none; width: 98pt'>R$ <%=dadosRelatorio.get(0).getPremioRetido()%></td>
				<td colspan=2 class=xl14224691 width=262
					style='border-right: .5pt solid black; border-left: none; width: 196pt'>Participação
					do Ressegurador</td>
				<td class=xl9924691 width=140
					style='border-top: none; border-left: none; width: 105pt'>R$ <%=dadosRelatorio.get(0).getPartResseg()%></td>
			</tr>
			<tr height=20 style='mso-height-source: userset; height: 15.0pt'>
				<td colspan=2 height=20 class=xl13724691 width=262
					style='height: 15.0pt; width: 196pt'>Prêmio Cedido</td>
				<td class=xl9924691 width=131
					style='border-top: none; border-left: none; width: 98pt'>R$ <%=dadosRelatorio.get(0).getPremioCedido()%></td>
				<td colspan=2 class=xl14224691 width=262
					style='border-right: .5pt solid black; border-left: none; width: 196pt'>Participação
					Caixa Seguros</td>
				<td class=xl9924691 width=140
					style='border-top: none; border-left: none; width: 105pt'>R$ <%=dadosRelatorio.get(0).getPartCaixa()%></td>
			</tr>
			<tr height=10 style='mso-height-source: userset; height: 7.5pt'>
				<td colspan=2 height=10 class=xl6924691 width=262
					style='height: 7.5pt; width: 196pt'></td>
				<td class=xl6924691 width=131 style='width: 98pt'></td>
				<td colspan=2 class=xl8924691 width=262 style='width: 196pt'>&nbsp;</td>
				<td class=xl8924691 width=140 style='border-top: none; width: 105pt'>&nbsp;</td>
			</tr>
			<tr height=20 style='mso-height-source: userset; height: 15.0pt'>
				<td height=20 class=xl9824691 width=131
					style='height: 15.0pt; width: 98pt'>Inicio de Vigência</td>
				<td class=xl7524691 width=131 style='width: 98pt'>Termino da
					Vigência</td>
				<td class=xl7224691></td>
				<td colspan=2 class=xl8624691 width=262 style='width: 196pt'></td>
				<td class=xl8624691 width=140 style='width: 105pt'></td>
			</tr>
			<tr height=17 style='mso-height-source: userset; height: 12.75pt'>
				<td height=17 class=xl8724691 width=131
					style='height: 12.75pt; width: 98pt'><%=dadosRelatorio.get(0).getInicioVig()%></td>
				<td class=xl8024691 width=131 style='width: 98pt'><%=dadosRelatorio.get(0).getFimVig()%></td>
				<td class=xl7224691></td>
				<td colspan=2 class=xl8624691 width=262 style='width: 196pt'></td>
				<td class=xl8624691 width=140 style='width: 105pt'></td>
			</tr>
			<tr height=10 style='mso-height-source: userset; height: 7.5pt'>
				<td colspan=3 height=10 class=xl6924691 width=393
					style='height: 7.5pt; width: 294pt'></td>
				<td colspan=2 class=xl6924691 width=262 style='width: 196pt'></td>
				<td class=xl6924691 width=140 style='width: 105pt'></td>
			</tr>
			<tr height=20 style='mso-height-source: userset; height: 15.0pt'>
				<td colspan=6 height=20 class=xl14824691 width=795
					style='height: 15.0pt; width: 595pt'>COBERTURAS CONTRATADAS E
					VALORES MÁXIMOS DE INDENIZAÇÃO</td>
			</tr>
			<tr height=20 style='mso-height-source: userset; height: 15.0pt'>
				<td colspan=3 height=20 class=xl9124691 width=393
					style='height: 15.0pt; width: 294pt'>Coberturas</td>
				<td class=xl9124691 width=131
					style='border-top: none; border-left: none; width: 98pt'>LMI</td>
				<td colspan=2 class=xl9124691 width=271
					style='border-left: none; width: 203pt'>Franquias</td>
			</tr>
			<tr class=xl7024691 height=32
				style='mso-height-source: userset; height: 24.0pt'>
				<td colspan=3 height=32 class=xl10824691 width=393
					style='height: 24.0pt; width: 294pt'><a name="RANGE!A23">Incêndio,
						Raio e Explosão</a></td>
				<td class=xl9024691 width=131
					style='border-top: none; border-left: none; width: 98pt'>R$
					5.500.000,00</td>
				<td colspan=2 class=xl15124691 width=271
					style='border-right: .5pt solid black; border-left: none; width: 203pt'>Não
					há</td>
			</tr>
			<%
				for(int i = 0; i < coberturas.size(); i++){
					if(coberturas.get(i) != null){
			%>
						<tr class=xl7024691 height=32 style='mso-height-source: userset; height: 24.0pt'>
							<td colspan=3 height=32 class=xl10824691 width=393 style='height: 24.0pt; width: 294pt'><a name="RANGE!A23"><%= coberturas.get(i).getCobertura()%></a></td>
							<td class=xl9024691 width=131 style='border-top: none; border-left: none; width: 98pt'>R$ <%= coberturas.get(i).getLMI()%> </td>
							<td colspan=2 class=xl15124691 width=271 style='border-right: .5pt solid black; border-left: none; width: 203pt'> <%= coberturas.get(i).getFranquia()%></td>
						</tr>
			<%
					}
				}
			%>
			
			<tr height=13 style='mso-height-source: userset; height: 9.75pt'>
				<td colspan=6 height=13 class=xl15024691 width=795
					style='height: 9.75pt; width: 595pt'></td>
			</tr>
			<tr height=20 style='mso-height-source: userset; height: 15.0pt'>
				<td colspan=6 height=20 class=xl12824691 width=795
					style='border-right: .5pt solid black; height: 15.0pt; width: 595pt'>Parecer
					Técnico</td>
			</tr>
			<tr height=114 style='mso-height-source: userset; height: 85.5pt'>
				<td colspan=6 height=114 class=xl13424691 width=795
					style='border-right: .5pt solid black; height: 85.5pt; width: 595pt'><%=dadosRelatorio.get(0).getParecerTecnico()%>
				</td>
			</tr>
			<tr height=19 style='mso-height-source: userset; height: 14.25pt'>
				<td colspan=4 height=19 class=xl12924691 width=524 style='height: 14.25pt; width: 392pt'><%=util.dataAtual(4)%></td>
				<td colspan=2 class=xl11324691 width=271 style='border-right: .5pt solid black; width: 203pt'>Assinatura e Carimbo do Técnico</td>
			</tr>
			<tr height=0 style='display: none; mso-height-source: userset; mso-height-alt: 195'>
				<td colspan=6 class=xl8224691 width=795 style='width: 595pt'></td>
			</tr>
			<tr height=17 style='mso-height-source: userset; height: 12.75pt'>
				<td colspan=6 height=17 class=xl12824691 width=795
					style='border-right: .5pt solid black; height: 12.75pt; width: 595pt'>Parecer
					da Gerência de Riscos Diversos</td>
			</tr>
			<tr height=54 style='mso-height-source: userset; height: 40.5pt'>
				<td colspan=6 height=54 class=xl13124691 width=795
					style='border-right: .5pt solid black; height: 40.5pt; width: 595pt'>&nbsp;</td>
			</tr>
			<tr height=17 style='height: 12.75pt'>
				<td colspan=3 height=17 class=xl11124691 width=393
					style='height: 12.75pt; width: 294pt'><a name="RANGE!A52">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></td>
				<td class=xl7824691 width=131 style='width: 98pt'>&nbsp;</td>
				<td colspan=2 class=xl11324691 width=271
					style='border-right: .5pt solid black; width: 203pt'>Assinatura
					e Carimbo do Gerente</td>
			</tr>
			<tr height=0
				style='display: none; mso-height-source: userset; mso-height-alt: 180'>
				<td colspan=6 class=xl8224691 width=795 style='width: 595pt'></td>
			</tr>
			<tr height=17 style='mso-height-source: userset; height: 12.75pt'>
				<td colspan=6 height=17 class=xl12824691 width=795
					style='border-right: .5pt solid black; height: 12.75pt; width: 595pt'>Parecer
					da Diretora da Diretoria de Riscos Diversos</td>
			</tr>
			<tr height=54 style='mso-height-source: userset; height: 40.5pt'>
				<td colspan=6 height=54 class=xl12524691 width=795
					style='border-right: .5pt solid black; height: 40.5pt; width: 595pt'><a
					name="RANGE!A55">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></td>
			</tr>
			<tr height=17 style='height: 12.75pt'>
				<td colspan=3 height=17 class=xl11124691 width=393
					style='height: 12.75pt; width: 294pt'><a name="RANGE!A56">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></td>
				<td class=xl7924691 width=131 style='width: 98pt'>&nbsp;</td>
				<td colspan=2 class=xl11324691 width=271
					style='border-right: .5pt solid black; width: 203pt'>Assinatura
					e Carimbo da Diretoria - DIRID</td>
			</tr>
			<tr height=0
				style='display: none; mso-height-source: userset; mso-height-alt: 180'>
				<td colspan=6 class=xl8224691 width=795 style='width: 595pt'></td>
			</tr>
			<tr height=17 style='mso-height-source: userset; height: 12.75pt'>
				<td colspan=6 height=17 class=xl11524691 width=795
					style='border-right: .5pt solid black; height: 12.75pt; width: 595pt'>Parecer
					da Diretora de Seguros</td>
			</tr>
			<tr height=54 style='mso-height-source: userset; height: 40.5pt'>
				<td colspan=6 height=54 class=xl11824691 width=795
					style='border-right: .5pt solid black; height: 40.5pt; width: 595pt'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			</tr>
			<tr height=17 style='height: 12.75pt'>
				<td colspan=3 height=17 class=xl12124691 width=393
					style='height: 12.75pt; width: 294pt'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td class=xl8324691 width=131 style='width: 98pt'>&nbsp;</td>
				<td colspan=2 class=xl12324691 width=271
					style='border-right: .5pt solid black; width: 203pt'>Assinatura
					e Carimbo da Diretoria de Seguros</td>
			</tr>
			<tr height=0
				style='display: none; mso-height-source: userset; mso-height-alt: 180'>
				<td class=xl8224691 width=131 style='width: 98pt'></td>
				<td class=xl8224691 width=131 style='width: 98pt'></td>
				<td class=xl8224691 width=131 style='width: 98pt'></td>
				<td class=xl8224691 width=131 style='width: 98pt'></td>
				<td class=xl8224691 width=131 style='width: 98pt'></td>
				<td class=xl8224691 width=140 style='width: 105pt'></td>
			</tr>
			<tr height=17 style='mso-height-source: userset; height: 12.75pt'>
				<td colspan=3 height=17 class=xl10924691 width=393
					style='height: 12.75pt; width: 294pt'><a name="RANGE!A62">Parecer
						da Diretoria Executiva</a></td>
				<td class=xl7624691 width=131 style='width: 98pt'>&nbsp;</td>
				<td class=xl7624691 width=131 style='width: 98pt'>&nbsp;</td>
				<td class=xl7724691 width=140 style='width: 105pt'>&nbsp;</td>
			</tr>
			<tr height=81 style='mso-height-source: userset; height: 60.75pt'>
				<td colspan=2 height=81 class=xl9824691 width=262
					style='height: 60.75pt; width: 196pt'><a name="RANGE!A63">&nbsp;</a></td>
				<td colspan=2 class=xl10524691 width=262 style='width: 196pt'></td>
				<td colspan=2 class=xl10524691 width=271
					style='border-right: .5pt solid black; width: 203pt'></td>
			</tr>
			<tr height=17 style='mso-height-source: userset; height: 12.75pt'>
				<td colspan=2 height=17 class=xl10224691 style='height: 12.75pt; border-right-width: 0px;'>Assinatura
					e Carimbo da DIRFI</td>
				<td colspan=2 class=xl10424691 width=262 style='width: 196pt'>Assinatura
					e Carimbo DIROC</td>
				<td colspan=2 class=xl15624691>Assinatura e Carimbo da PRESI</td>
			</tr>
			<tr height=20 style='mso-height-source: userset; height: 15.0pt'>
				<td height=20 class=xl7324691 width=131
					style='height: 15.0pt; width: 98pt'></td>
				<td class=xl7324691 width=131 style='width: 98pt'></td>
				<td class=xl7324691 width=131 style='width: 98pt'></td>
				<td class=xl7324691 width=131 style='width: 98pt'></td>
				<td class=xl7324691 width=131 style='width: 98pt'></td>
				<td class=xl7324691 width=140 style='width: 105pt'></td>
			</tr>
			<tr height=20 style='mso-height-source: userset; height: 15.0pt'>
				<td height=20 class=xl7124691 style='height: 15.0pt'></td>
				<td class=xl7224691></td>
				<td class=xl7224691></td>
				<td class=xl7224691></td>
				<td class=xl7224691></td>
				<td class=xl7224691></td>
			</tr>
			<![if supportMisalignedColumns]>
			<tr height=0 style='display: none'>
				<td width=131 style='width: 98pt'></td>
				<td width=131 style='width: 98pt'></td>
				<td width=131 style='width: 98pt'></td>
				<td width=131 style='width: 98pt'></td>
				<td width=131 style='width: 98pt'></td>
				<td width=140 style='width: 105pt'></td>
			</tr>
			<![endif]>
		</table>

	</div>


	<!----------------------------->
	<!--FIM DA SAÍDA DO 'ASSISTENTE PARA PUBLICAÇÃO COMO PÁGINA DA WEB' DO EXCEL-->
	<!----------------------------->
</body>

</html>
