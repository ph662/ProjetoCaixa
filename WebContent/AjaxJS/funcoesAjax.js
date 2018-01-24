function enviaAjax(dados){
	if (!(dados === "zero")){
		$.ajax({
			//url: "http://"+ip_porta+"/ProjetoCaixa/visaoAnalitica",
			url: "http://"+ip_porta+"/ProjetoCaixa/visaoAnalitica?tipo=fatuMensal&mes="+dados,
			global: false,
			type: "GET",
			//data: (dadosTratados),
			cache: false,
			beforeSend: function() {
				$('#tabelaAjax').html("<span class='carregando'>Aguarde...</span><br>&nbsp;&nbsp;&nbsp;<img class='carregando' src='imagens/loader.gif'/>");
			},
			success: function(html) {
				$('#tabelaAjax').html(html);
			}
		});
	}
}


function dadosRenovacao(ano,mes){
	if (!(mes === "zero") && !(ano === "zero")){
		$.ajax({
			url: "http://"+ip_porta+"/ProjetoCaixa/visaoOperacional?tipo=renovacao&mes="+mes+"&ano="+ano+"&codProd=*",
			global: false,
			type: "GET",
			//data: (dadosTratados),
			cache: false,
			beforeSend: function() {
				$('#tabelaAjax').html("<span class='carregando'>Aguarde...</span><br>&nbsp;&nbsp;&nbsp;<img class='carregando' src='imagens/loader.gif'/>");
			},
			success: function(html) {
				$('#tabelaAjax').html(html);
			}
		});
	}else{
		alert('Selecione o Ano e o Mês.');
	}
}

function dadosSensibilizacao(ano,mes,categ){
	
	if(mes !== "zero" && ano !== "zero"){
		$.ajax({
			url: "http://"+ip_porta+"/ProjetoCaixa/visaoOperacional?tipo=sensibilizacao"+categ+"&mesSensibilizacao="+mes+"&anoSensibilizacao="+ano+"&pagina=1",
			global: false,
			type: "GET",
			//data: (dadosTratados),
			cache: false,
			beforeSend: function() {
				$('#tabelaAjax').html("<span class='carregando'>Aguarde...</span><br>&nbsp;&nbsp;&nbsp;<img class='carregando' src='imagens/loader.gif'/>");
			},
			success: function(html) {
				$('#tabelaAjax').html(html);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
		        //alert("Status: " + textStatus);
		        //alert("Error: " + errorThrown); 
		        $('#tabelaAjax').html("<span style='color:red;margin-left: 439px;'>M&ecirc;s selecionado n&atilde;o possui dados.</span><br>");
		    }       
		});
	}
}

function dadosSensibilizacaoPaginacao(ano,mes,categ,pagina,codigo){
	if(mes !== "zero" && ano !== "zero"){
		if(codigo === "null"){
			codigo = " ";
		}else{
			codigo = codigo+"&prod=t";
		}
		$.ajax({
			url: "http://"+ip_porta+"/ProjetoCaixa/visaoOperacional?tipo=sensibilizacao"+categ+"&mesSensibilizacao="+mes+"&anoSensibilizacao="+ano+"&pagina="+pagina+"&paginacao=t&cod="+codigo,
			global: false,
			type: "GET",
			//data: (dadosTratados),
			cache: false,
			beforeSend: function() {
				$('#ajaxSensibilizacaoPaginada').html("<span style='color:#10CEF0;margin-left: 145px;'>Aguarde...</span>&nbsp;&nbsp;&nbsp;<br /><img style='margin-left: 153px;height: 30px;width: 30px;' src='imagens/loader.gif'/>");
			},
			success: function(html) {
				$('#ajaxSensibilizacaoPaginada').html(html);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) { 
		        //alert("Status: " + textStatus);
		        //alert("Error: " + errorThrown); 
		        $('#ajaxSensibilizacaoPaginada').html("<span style='color:red;margin-left: 136px;'>M&ecirc;s selecionado n&atilde;o possui dados.</span><br>");
		    }       
		});
	}
}

function teste(){
	alert('Página em manutenção!');
}