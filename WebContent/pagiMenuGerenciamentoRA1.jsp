<%@ page import="caixa.dirid.VO.CoberturasVO"%>
<%@ page import="caixa.dirid.VO.FaturamentoVO"%>
<%@ page import="caixa.dirid.UTEIS.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<head>
<!-- Compatibilidade com IE antigo -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="author" content="phelipe662@gmail.com">
<link href="imagens/caixaFav.ico" rel="shortcut icon"
	type="image/x-icon">
<title>Gerenciamento RRA</title>
<%@ include file="importHeadBootstrap.jsp"%>
<%@ include file="importHead_JQplot.jsp"%>
<%@ include file="importHead_JQwidgets.jsp"%>

	<style>
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
		
		table.stats-table td.tooltip-header, table.highlighted-stats-table td.tooltip-header
			{
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
		
		.navbar {
			background-image: linear-gradient(to bottom, #0080c0 0px, #002855 100%);
			background-repeat: repeat-x;
			box-shadow: 0px 3px 9px rgba(0, 0, 0, 0.25) inset;
		}
		
		#botaoHome {
			background-image: linear-gradient(to bottom, #BC0808 0px, #CF4B4B 100%);
			background-repeat: repeat-x;
			box-shadow: 0px 3px 9px rgba(0, 0, 0, 0.25) inset;
		}
		
		#titulo {
			color: white;
		}

		
	</style>
	<script type="text/javascript">
        $(document).ready(function () {
        
        	var source =
            {
                localdata: [
                            {"numero_aceitacao":1,"numero_proposta":10,"segurado":"phelipe","local_risco":"d",
                            "CPF_CNPJ":"e","atividade_principal":"teste","inicio_vigencia":"1996-07-09 00:00:00",
                            "fim_vigencia":"1996-07-09 00:00:00","imp_Seg":10,"premio_Liq":10,
                            "premio_Net":10,"premio_Ced":10,"limite_Sinistro":10,"part_Resseg":10,
                            "part_CaixaSeg":10,"parecer_tecnico":"algm perguntou"},
                            {"numero_aceitacao":2,"numero_proposta":10,"segurado":"c","local_risco":"d",
                            "CPF_CNPJ":"e","atividade_principal":"ola como vai","inicio_vigencia":"1996-07-09 00:00:00",
                            "fim_vigencia":"1996-07-09 00:00:00","imp_Seg":10,"premio_Liq":10,
                            "premio_Net":10,"premio_Ced":10,"limite_Sinistro":10,"part_Resseg":10,
                            "part_CaixaSeg":10,"parecer_tecnico":"p"},
                       	   	{"numero_aceitacao":3,"numero_proposta":10,"segurado":"jose","local_risco":"d",
                           	"CPF_CNPJ":"e","atividade_principal":"f","inicio_vigencia":"1996-07-09 00:00:00",
                           	"fim_vigencia":"1996-07-09 00:00:00","imp_Seg":10,"premio_Liq":10,
                           	"premio_Net":10,"premio_Ced":10,"limite_Sinistro":10,"part_Resseg":10,
                           	"part_CaixaSeg":10,"parecer_tecnico":"p"}
                            ],
                datatype: "local",
                datafields:
                [
                 	{ name: 'numero_aceitacao', type: 'number' },
                    { name: 'numero_proposta', type: 'number' },
                    { name: 'segurado', type: 'string' },
                    { name: 'local_risco', type: 'string' },
                    { name: 'CPF_CNPJ', type: 'string' },
                    { name: 'atividade_principal', type: 'string' },
                    { name: 'inicio_vigencia', type: 'date' },
                    { name: 'fim_vigencia', type: 'date' },
                    { name: 'imp_Seg', type: 'number' },
                    { name: 'premio_Liq', type: 'number' },
                    { name: 'premio_Net', type: 'number' },
                    { name: 'premio_Ced', type: 'number' },
                    { name: 'limite_Sinistro', type: 'number' },
                    { name: 'part_Resseg', type: 'number' },
                    { name: 'part_CaixaSeg', type: 'number' },
                    { name: 'parecer_tecnico', type: 'string' }
                ],
                addrow: function (rowid, rowdata, position, commit) {
                    // synchronize with the server - send insert command
                    // call commit with parameter true if the synchronization with the server is successful 
                    //and with parameter false if the synchronization failed.
                    // you can pass additional argument to the commit callback which represents the new ID if it is generated from a DB.
                    commit(true);
                },
              
                updaterow: function (rowid, newdata, commit) {
                    // synchronize with the server - send update command
                    // call commit with parameter true if the synchronization with the server is successful 
                    // and with parameter false if the synchronization failed.
                    commit(true);                }
            };
            var dataAdapter = new $.jqx.dataAdapter(source);

            // initialize jqxGrid
            $("#jqxgrid").jqxGrid(
            {
                width: 1150,
                height: 350,
                source: dataAdapter,
                showtoolbar: true,
                showfilterrow: true,
                filterable: true,
                rendertoolbar: function (toolbar) {
                    var me = this;
                    var container = $("<div style='margin: 5px;'></div>");
                    toolbar.append(container);
                    container.append('<input id="addrowbutton" type="button" value="Adicionar novo RRA" />');
                    container.append('<input style="margin-left: 5px;" id="updaterowbutton" type="button" value="Aterar Selecionado" />');
                    $("#addrowbutton").jqxButton();
                    $("#updaterowbutton").jqxButton();
                   
                    // update row.
                    $("#updaterowbutton").on('click', function () {
                        var datarow = generaterow();
                        var selectedrowindex = $("#jqxgrid").jqxGrid('getselectedrowindex');
                        var rowscount = $("#jqxgrid").jqxGrid('getdatainformation').rowscount;
                        if (selectedrowindex >= 0 && selectedrowindex < rowscount) {
                            var id = $("#jqxgrid").jqxGrid('getrowid', selectedrowindex);
                            var commit = $("#jqxgrid").jqxGrid('updaterow', id, datarow);
                            $("#jqxgrid").jqxGrid('ensurerowvisible', selectedrowindex);
                        }
                    });

                    // create new row.
                    $("#addrowbutton").on('click', function () {
                        var datarow = generaterow();
                        var commit = $("#jqxgrid").jqxGrid('addrow', null, datarow);
                    });


                },
                columns: [
					{ text: 'Número Aceitação', datafield: 'numero_aceitacao'},
					{ text: 'Número Proposta', datafield: 'numero_proposta'},
					{ text: 'Segurado', datafield: 'segurado'},
					{ text: 'Local Risco', datafield: 'local_risco'},
					{ text: 'CPF / CNPJ', datafield:'CPF_CNPJ'},
					{ text: 'Atividade Principal', datafield:'atividade_principal'},
					{ text: 'Início Vigência', datafield:'inicio_vigencia', cellsformat: 'd'},
					{ text: 'Fim Vigência', datafield:'fim_vigencia', cellsformat: 'd'},
					{ text: 'Importância Segurada', datafield:'imp_Seg',cellsalign: 'right', cellsformat: 'c2'},
					{ text: 'Prêmio Líquido', datafield:'premio_Liq',cellsalign: 'right', cellsformat: 'c2'},
					{ text: 'Prêmio Net', datafield:'premio_Net',cellsalign: 'right', cellsformat: 'c2' },
					{ text: 'Prêmio Cedido', datafield:'premio_Ced', cellsalign: 'right', cellsformat: 'c2'},
					{ text: 'Limite Sinistro', datafield:'limite_Sinistro', cellsalign: 'right', cellsformat: 'c2' },
					{ text: 'Participação Resseguradora', datafield:'part_Resseg', cellsalign: 'right', cellsformat: 'c2' },
					{ text: 'Participação Caixa Seg', datafield:'part_CaixaSeg', cellsalign: 'right', cellsformat: 'c2' },
					{ text: 'Parecer Técnico', datafield:'parecer_tecnico' }
                ]
            });   
            
            //=========================//=========================//=========================//=========================//=========================
            // Coberturas Grid
            // prepare the data
            var dataFields = [
						{ name: 'numeroAceitacao', type:'number'},
                        { name: 'LMI', type:'number'},
                        { name: 'Cobertura', type:'string' },
                        { name: 'Franquia', type: 'string' }
                    ];

            var source =
            {
                datafields: dataFields,
                localdata: [{"numeroAceitacao":2, "LMI": 1054548.52, "Cobertura": "Incendio Raio", "Franquia": "10% de Danos"},
                            {"numeroAceitacao":3, "LMI": 155448.72, "Cobertura": "Roubo de Bens", "Franquia": "Danos Cruzados"},
                            {"numeroAceitacao":2, "LMI": 1548.72, "Cobertura": "Roubo de BensRoubo de BensRoubo de BensRoubo de BensRoubo de BensRoubo de BensRoubo de BensRoubo de BensRoubo de BensRoubo de Bens", "Franquia": "Danos Cruzados"},
                            {"numeroAceitacao":2, "LMI": 154832.72, "Cobertura": "Roubo de Trens", "Franquia": "Danos Zerados"},
                            {"numeroAceitacao":1, "LMI": 15548.72, "Cobertura": "Roubo de Trens", "Franquia": "Danos Cruzados"}
                          ]
            };

            var dataAdapter = new $.jqx.dataAdapter(source);
            dataAdapter.dataBind();

            $("#jqxgrid").on('rowselect', function (event) {
            	
            	 var numero_aceitacao = event.args.row.numero_aceitacao;
                 var records = new Array();
                 var length = dataAdapter.records.length;
                 for (var i = 0; i < length; i++) {
                     var record = dataAdapter.records[i];
                     if (record.numeroAceitacao == numero_aceitacao) {
                         records[records.length] = record;
                     }
                 }

                var dataSource = {
                    datafields: dataFields,
                    localdata: records
                }
                var adapter = new $.jqx.dataAdapter(dataSource);
        
                // update data source.
                $("#ordersGrid").jqxGrid({ source: adapter });
            });
            
            $("#jqxgrid").on('rowselect', function (event) {
            	$("#jqxgrid").jqxGrid('autoresizecolumns');
           	});
            
            $('.jqx-widget, .jqx-input, .jqx-rc-all, .jqx-widget-content').on('blur change click dblclick error focus focusin focusout hover keydown keypress keyup load mousedown mouseenter mouseleave mousemove mouseout mouseover mouseup resize scroll select submit', function(){
            	$("#jqxgrid").jqxGrid('autoresizecolumns');
            });
            
            $("#ordersGrid").on('rowselect', function (event) {
           		$("#ordersGrid").jqxGrid('autoresizecolumns');
           	});
            
            $("#ordersGrid").jqxGrid(
            {
                width: 1150,
                height: 150,
                keyboardnavigation: false,
                columns: [
                    { text: 'LMI', datafield: 'LMI',cellsalign: 'right', cellsformat: 'c2', width: 130},
                    { text: 'Cobertura', datafield: 'Cobertura', width: 250 },
                    { text: 'Franquia', datafield: 'Franquia',  width: 250 }
                ]
            });

            $("#jqxgrid").jqxGrid('selectrow', 0);
            
           
         

        });
    </script>
</head>

<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<span class="navbar-brand" id="titulo">GERID - RRA</span>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li class="active" id="botaoHome">
						<a href="pagiMenuPrincipal.jsp">Menu</a>
					</li>
				</ul>
			</div>
		</div>
	</nav>
	
	<div class="container theme-showcase" role="main" style="padding-left: 0px;">
		<br/><br/><br/><br/><br/><br/>
			<div id="jqxgrid">
			</div>
			<h3> Coberturas</h3>
        	<div id="ordersGrid"></div>
	</div>
	<%@ include file="importBodyBootstrap.jsp"%>
</body>
</html>