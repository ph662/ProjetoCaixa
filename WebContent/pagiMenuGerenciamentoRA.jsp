<%@ page import="caixa.dirid.VO.CoberturasVO"%>
<%@ page import="caixa.dirid.VO.RelatorioAceitacaoVO"%>
<%@ page import="caixa.dirid.UTEIS.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<!-- Compatibilidade com IE antigo -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="author" content="phelipe662@gmail.com">
<link href="imagens/caixaFav.ico" rel="shortcut icon" type="image/x-icon">
<title>Gerenciamento RRA</title>
<%@ include file="importHeadBootstrap.jsp"%>
<%@ include file="importHead_JQplot.jsp"%>
<%@ include file="importHead_JQwidgets.jsp"%>

<script type="text/javascript" src="AjaxJS/funcoesAjax.js"></script>


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
	
	<%
		List<RelatorioAceitacaoVO> relatoriosSalvos  = (List<RelatorioAceitacaoVO>) request.getAttribute("relatoriosSalvos");
		List<CoberturasVO> coberturasRelatoriosSalvos = (List<CoberturasVO>) request.getAttribute("coberturasRelatoriosSalvos");
	%>
	
	
	<script type="text/javascript">
        $(document).ready(function () {
        	
			$('#jqxTextArea').jqxTextArea({ rtl: false, height: 150, width: 510 });
			var text = '';
			$('#jqxTextArea').jqxTextArea('val', text);
        
        	var source =
            {
                localdata: [
                            <%
                            for (int i = 0; i < relatoriosSalvos.size(); i++) {
                            %>
	                            {
	                            'status':<%=relatoriosSalvos.get(i).getStatuscheck()%>,
	                            'numero_aceitacao':<%=relatoriosSalvos.get(i).getNumeroAceitacao()%>,
	                           	'numero_proposta':<%=relatoriosSalvos.get(i).getNumeroProposta()%>,
	                           	'segurado':'<%=relatoriosSalvos.get(i).getSegurado()%>',
	                           	'local_risco':'<%=relatoriosSalvos.get(i).getLocalRisco()%>',
	                            'CPF_CNPJ':'<%=relatoriosSalvos.get(i).getCpf()%>',
	                            'atividade_principal':'<%=relatoriosSalvos.get(i).getAtividadePrincipal()%>',
	                            'inicio_vigencia':'<%=relatoriosSalvos.get(i).getInicioVig()%> 00:00:00',
	                            'fim_vigencia':'<%=relatoriosSalvos.get(i).getFimVig()%> 00:00:00',
	                            'imp_Seg':<%=relatoriosSalvos.get(i).getIs()%>,
	                            'premio_Liq':<%=relatoriosSalvos.get(i).getPremioLiq()%>,
	                            'premio_Net':<%=relatoriosSalvos.get(i).getPremioNet()%>,
	                            'premio_Ced':<%=relatoriosSalvos.get(i).getPremioCedido()%>,
	                            'limite_Sinistro':<%=relatoriosSalvos.get(i).getLimiteSinistro()%>,
	                            'part_Resseg':<%=relatoriosSalvos.get(i).getPartResseg()%>,
	                            'part_CaixaSeg':<%=relatoriosSalvos.get(i).getPartCaixa()%>,
	                            'parecer_tecnico':'<%=relatoriosSalvos.get(i).getParecerTecnico()%>',
	                            'data_saida':
	                            <%
		                            if(relatoriosSalvos.get(i).getDataSaida() != null){
		                            	out.print("\'");
		                            	out.print(relatoriosSalvos.get(i).getDataSaida());
		                            	out.print(" 00:00:00\'");
		                            }else{
		                            	out.print(relatoriosSalvos.get(i).getDataSaida());
		                            }
	                            %>,
	                            'data_entrega':
	                            <%
		                            if(relatoriosSalvos.get(i).getDataEntrega() != null){
		                            	out.print("\'");
		                            	out.print(relatoriosSalvos.get(i).getDataEntrega());
		                            	out.print(" 00:00:00\'");
		                            }else{
		                            	out.print(relatoriosSalvos.get(i).getDataEntrega());
		                            }
	                            %>
	                            }
                           <%
                          		if(i != relatoriosSalvos.size() - 1){
                           %>
                           			<%=","%>
                           <%
                          		}
                            }
                           %>
                            ],
                datatype: "local",
                datafields:
                [
                 	{ name: 'numero_aceitacao', type: 'number' },
                    { name: 'numero_proposta', type: 'number' },
                    { name: 'status', type: 'bool' },
                    { name: 'data_saida', type: 'date' },
                    { name: 'data_entrega', type: 'date' },
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
            
            var cellclass = function (row, columnfield, value) {
            	
                if ($("#jqxgrid").jqxGrid('getcellvaluebyid', row,'status' ) ==  false) {
                    return 'red';
                }
            }
            
            // initialize jqxGrid
            $("#jqxgrid").jqxGrid(
            {
                width: 1260,
                height: 270,
                columnsresize: true,
                source: dataAdapter,
                showtoolbar: true,
                showfilterrow: true,
                filterable: true,
                autoshowfiltericon: true,
                editable: true,
                rendertoolbar: function (toolbar) {
                    var me = this;
                    var container = $("<div style='margin: 5px;'></div>");
                    toolbar.append(container);
                    container.append('<input id="addrowbutton" type="button" value="Adicionar novo RRA" />');
                    container.append('<input style="margin-left: 5px;" id="alterarbutton" type="button" value="Aterar Selecionado" />');
                    container.append('<input style="margin-left: 5px;" id="visualizarbutton" type="button" value="Visualizar Selecionado" />');
                    $("#addrowbutton").jqxButton();
                    $("#alterarbutton").jqxButton();
                    $("#visualizarbutton").jqxButton();
                    // update row.
                    $("#alterarbutton").on('click', function () {
                    	var selectedrowindex = $("#jqxgrid").jqxGrid('getselectedrowindex');
                    	if (selectedrowindex >= 0){
                    		var nume_aceitacao = $("#jqxgrid").jqxGrid('getcellvaluebyid', selectedrowindex,'numero_aceitacao' );
                    	}
                    	window.location.href = "http://"+ip_porta+"/ProjetoCaixa/visaoOperacional?tipo=novoRelaAceitacao&alterar=t&numAceitacao="+nume_aceitacao;
                    });

                    // create new row.
                    $("#addrowbutton").on('click', function () {
                    	window.location.href = "http://"+ip_porta+"/ProjetoCaixa/visaoOperacional?tipo=novoRelaAceitacao";
                    });
                    
                    $("#visualizarbutton").on('click', function () {
                    	
                    	var selectedrowindex = $("#jqxgrid").jqxGrid('getselectedrowindex');
                    	if (selectedrowindex >= 0){
                    		 var nume_aceitacao = $("#jqxgrid").jqxGrid('getcellvaluebyid', selectedrowindex,'numero_aceitacao' );
                    		 window.open("http://"+ip_porta+"/ProjetoCaixa/visaoOperacional?tipo=imprimeRRA&numAceitacao="+nume_aceitacao);
                    	}
                    });
                },
                columns: [
					{ text: 'Número Aceitação', datafield: 'numero_aceitacao', cellclassname: cellclass, pinned: true, width:130, editable: false},
					{ text: 'Número Proposta', datafield: 'numero_proposta', cellclassname: cellclass, pinned: true, width:150, editable: false},
					{ text: 'Status', datafield: 'status', cellclassname: cellclass, columntype: 'checkbox',filtertype: 'bool', width: 67,pinned: true,editable: false},
					{ text: 'Local Risco', datafield: 'local_risco', width:300, editable: false},
					{ text: 'CPF / CNPJ', datafield:'CPF_CNPJ', width:200, editable: false},
					{ text: 'Atividade Principal', datafield:'atividade_principal', width:300, editable: false},
					{ text: 'Início Vigência', datafield:'inicio_vigencia', filtertype: 'date', cellsformat: 'd', width:110, cellsalign: 'right', editable: false},
					{ text: 'Fim Vigência', datafield:'fim_vigencia',filtertype: 'date', cellsformat: 'd', width:100, cellsalign: 'right', editable: false},
					{ text: 'Importância Segurada', datafield:'imp_Seg',cellsalign: 'right', cellsformat: 'c2', width:160, editable: false},
					{ text: 'Prêmio Líquido', datafield:'premio_Liq',cellsalign: 'right', cellsformat: 'c2', width:130, editable: false},
					{ text: 'Segurado', datafield: 'segurado', width:450, editable: false},
					{
	                  text: 'Data Saída', datafield: 'data_saida', filtertype: 'date', columntype: 'datetimeinput', width: 110, cellsalign: 'right', cellsformat: 'd', editable: true,
	                  validation: function (cell, value) {
						if (value != "")
							return true;
	                  }
	                },
					//{ text: 'Data Entrega', datafield:'data_entrega', filtertype: 'date', cellsformat: 'd', width:110, cellsalign: 'right', editable: true},
					{
	                  text: 'Data Entrega', datafield: 'data_entrega', filtertype: 'date', columntype: 'datetimeinput', width: 110, cellsalign: 'right', cellsformat: 'd', editable: true,
	                  validation: function (cell, value) {
						if (value != "")
							return true;
	                  }
	                }
					//{ text: 'Prêmio Net', datafield:'premio_Net',cellsalign: 'right', cellsformat: 'c2' },
					//{ text: 'Prêmio Cedido', datafield:'premio_Ced', cellsalign: 'right', cellsformat: 'c2'},
					//{ text: 'Limite Sinistro', datafield:'limite_Sinistro', cellsalign: 'right', cellsformat: 'c2' },
					//{ text: 'Participação Resseguradora', datafield:'part_Resseg', cellsalign: 'right', cellsformat: 'c2' },
					//{ text: 'Participação Caixa Seg', datafield:'part_CaixaSeg', cellsalign: 'right', cellsformat: 'c2' },
					//{ text: 'Parecer Técnico', datafield:'parecer_tecnico' }
                ]
            });   
            
            $("#jqxgrid").on('cellbeginedit', function (event) {
            });

            $("#jqxgrid").on('cellendedit', function (event) {
                var aceit = event.args.row.numero_aceitacao;
                var tipo = event.args.datafield.toString();
                if (tipo == "data_entrega" || tipo == "data_saida"){
	                var data = event.args.value.toString();
	                var dataNum = Date.parse(data);
	                $.ajax({
						url: "http://"+ip_porta+"/ProjetoCaixa/visaoOperacional?tipo=insereData&data="+dataNum+"&colunaGrid="+tipo+"&numAcei="+aceit,
						global: false,
						type: "POST",
						cache: false
					});
                }else{
                	var data = event.args.value;
                	$.ajax({
						url: "http://"+ip_porta+"/ProjetoCaixa/visaoOperacional?tipo=insereData&situacao="+data+"&colunaGrid="+tipo+"&numAcei="+aceit,
						global: false,
						type: "POST",
						cache: false
					});
                }
                if (tipo == "data_entrega"){
                	
                	$("#jqxgrid").jqxGrid('setcellvalue', event.args.rowindex, "status", true);
                	
                	$.ajax({
						url: "http://"+ip_porta+"/ProjetoCaixa/visaoOperacional?tipo=insereData&situacao=true&colunaGrid=status&numAcei="+aceit,
						global: false,
						type: "POST",
						cache: false
					});
                }
               //alert(event.args.row.numero_aceitacao+" | "+ event.args.datafield+" | "+event.args.rowindex+" | "+event.args.value );
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
                localdata: [
 							<%
                            	for (int i = 0; i < coberturasRelatoriosSalvos.size(); i++) {
                            %>
                            
                            		{"numeroAceitacao":<%=coberturasRelatoriosSalvos.get(i).getNumAceitacao()%>, 
                            		"LMI":<%=coberturasRelatoriosSalvos.get(i).getLMI()%>, 
                            		"Cobertura": "<%=coberturasRelatoriosSalvos.get(i).getCobertura()%>", 
                            		"Franquia": "<%=coberturasRelatoriosSalvos.get(i).getFranquia()%>"}
                            	<%
                              		if(i != coberturasRelatoriosSalvos.size() - 1){
                               	%>
                               			<%=","%>
                         	
                         	<%
                              		}
                            	}
                         	%>
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
                $("#cobertuGrid").jqxGrid({ source: adapter });

                var text = event.args.row.parecer_tecnico;
    			$('#jqxTextArea').jqxTextArea('val', text);
            });
            
            
            $("#cobertuGrid").on('rowselect', function (event) {
           		$("#cobertuGrid").jqxGrid('autoresizecolumns');
           	});
            
            $("#cobertuGrid").jqxGrid(
            {
                width: 630,
                height: 150,
                keyboardnavigation: false,
                columns: [
                    { text: 'LMI', datafield: 'LMI',cellsalign: 'right', cellsformat: 'c2', width: 130},
                    { text: 'Cobertura', datafield: 'Cobertura', width: 250 },
                    { text: 'Franquia', datafield: 'Franquia',  width: 250 }
                ]
            });
            
            $("#jqxgrid").jqxGrid('selectrow', 0);
         
        <%    
        	if(request.getAttribute("imprime") != null){
    		String numAceitacao = (String) request.getAttribute("numAceitacao");
    	%>		
    			var num = "<%=numAceitacao%>";
    			sessionStorage.setItem("numeroAceitacao", num); 
    			window.open('http://'+ip_porta+'/ProjetoCaixa/visaoOperacional?tipo=imprimeRRA&numAceitacao='+num);
		        setTimeout(function(){window.location.href='http://'+ip_porta+'/ProjetoCaixa/visaoOperacional?tipo=menuRelAceitacao';},4000);
    	<%
    		}
    	%>  
        });
        
    </script>
</head>

<body>
	<style>     
	.red {
	    color: black\9;
	    background-color: #aaa\9;
	}
	.red:not(.jqx-grid-cell-hover):not(.jqx-grid-cell-selected), .jqx-widget .red:not(.jqx-grid-cell-hover):not(.jqx-grid-cell-selected) {
	    color: black;
	    background-color: #aaa;
	}
	</style>
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
		<br/>
		<div class="page-header">
			<h2>Gerenciamento RRA</h2>
			<!--  <br>
			<h3><small>Autom&oacute;vel - Faturamento</small></h3>-->
		</div>
		<div id="jqxgrid" style="margin-left: -50px;">
		</div>
		<div id="externo">
			<div class="interno">
				<h4> Coberturas</h4>
	        	<div id="cobertuGrid"></div>
	        </div>
	        <div class="interno" style="margin-left: 15px;">
				<h4> Parecer T&eacute;cnico</h4>
	        	<textarea id="jqxTextArea"></textarea>
	        </div>
       	</div>
	</div>
	<%@ include file="importBodyBootstrap.jsp"%>
</body>
</html>