package caixa.dirid.UTEIS;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Uteis {

	/**
	 * Round to certain number of decimals
	 * 
	 * @param numero
	 * @param casasDecimais
	 * @return
	 */
	public double roundCima(double d, int decimalPlace) {
		BigDecimal bd = new BigDecimal(Double.toString(d));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}

	public double roundBaixo(double d, int decimalPlace) {
		BigDecimal bd = new BigDecimal(Double.toString(d));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_DOWN);
		return bd.doubleValue();
	}

	public String[] mesesParaConsultas() {
		String[] meses = { "Janeiro", "Fevereiro", "Mar�o", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro",
				"Outubro", "Novembro", "Dezembro" };

		return meses;
	}

	public String[] mesesPaginaWeb() {
		String[] meses = { "Janeiro", "Fevereiro", "Mar&ccedil;o", "Abril", "Maio", "Junho", "Julho", "Agosto",
				"Setembro", "Outubro", "Novembro", "Dezembro" };

		return meses;
	}

	public String cortaRetornaAno(String ano) {
		int tamanho = ano.length();
		String apenasAno = ano.substring(tamanho - 4, tamanho);

		return apenasAno;
	}

	public String cortaRetornaProduto(String ano) {
		int limite = ano.indexOf("2");
		String produto = ano.substring(0, limite);

		return produto;
	}

	/**
	 * Retorna a data do parametro formatada de acordo com os CASE's
	 * 
	 */
	public String cortaData(String dataParam, int tipo) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		Date date = Calendar.getInstance().getTime();
		try {
			if (dataParam != " ") {
				cal.setTime(sdf.parse(dataParam));
			}
		} catch (ParseException e) {
			e.printStackTrace();
			System.err.println("Classe UTEIS - metodo dataCortada - Erro na conversao");
		}

		switch (tipo) {
		case 1: // retorna apenas o ano
			DateFormat dateFormat = new SimpleDateFormat("YYYY");
			return dateFormat.format(date);
		case 2: // retorna apenas o m�s
			DateFormat dateFormat2 = new SimpleDateFormat("MM");
			return dateFormat2.format(date);
		case 3: // retorna apenas o dia
			DateFormat dateFormat3 = new SimpleDateFormat("dd");
			return dateFormat3.format(date);
		case 4: // retorna a data completa
			DateFormat dateFormat4 = new SimpleDateFormat("dd/MM/YYYY");
			return dateFormat4.format(date);
		}
		return "";
	}

	/**
	 * Retorna a data do sistema formatada de acordo com os CASE's
	 * 
	 */
	public String dataAtual(int tipo) {
		Calendar cal = Calendar.getInstance();
		Date date = Calendar.getInstance().getTime();
		// System.out.println("- "+date);
		switch (tipo) {
		case 1: // retorna apenas o ano
			DateFormat dateFormat = new SimpleDateFormat("YYYY");
			return dateFormat.format(date);
		case 2: // retorna apenas o m�s
			DateFormat dateFormat2 = new SimpleDateFormat("MM");
			return dateFormat2.format(date);
		case 3: // retorna apenas o dia
			DateFormat dateFormat3 = new SimpleDateFormat("dd");
			return dateFormat3.format(date);
		case 4: // retorna a data completa
			DateFormat dateFormat4 = new SimpleDateFormat("dd/MM/YYYY");
			return dateFormat4.format(date);
		}
		return "";
	}

	/**
	 * Recebe data no modelo "YYYY-MM-dd" e retorna "dd/MM/YYYY".
	 * 
	 * @param data
	 * @return String
	 */
	public String editaData(String data) {
		String ano = data.substring(0, 4);
		String mes = data.substring(5, 7);
		String dia = data.substring(8, 10);
		String dataFim = dia + "/" + mes + "/" + ano;
		return dataFim;
	}

	public String insereSeparadoresMoeda(String n) {

		if (n.contains(",")) {
			String[] cortada = n.split(",");
			StringBuilder numero = new StringBuilder(cortada[0]);
			StringBuilder segundoPonto = null;
			StringBuilder primeiroPonto = null;
			StringBuilder retorno = null;
			if (numero.length() > 3) {
				primeiroPonto = numero.reverse().insert(3, ".");
				if (primeiroPonto.length() > 7) {
					segundoPonto = primeiroPonto.insert(7, ".");
					retorno = segundoPonto.reverse();
				} else {
					retorno = primeiroPonto.reverse();
				}
				if (cortada[1].equals("0")) {
					cortada[1] = "00";
				} else if (cortada[1].length() == 1) {
					cortada[1] = cortada[1] + "0";
				}
				String tratada = retorno + "," + cortada[1];
				return tratada.replace("-.", "-");
			} else {
				return n;
			}
		} else if (n.contains(".")) {
			n = n.replace(".", ",");
			String[] cortada = n.split(",");
			StringBuilder numero = new StringBuilder(cortada[0]);
			StringBuilder segundoPonto = null;
			StringBuilder primeiroPonto = null;
			StringBuilder retorno = null;

			if (numero.length() > 3) {
				primeiroPonto = numero.reverse().insert(3, ".");
				if (primeiroPonto.length() > 7) {
					segundoPonto = primeiroPonto.insert(7, ".");
					retorno = segundoPonto.reverse();
				} else {
					retorno = primeiroPonto.reverse();
				}
				if (cortada[1].equals("0")) {
					cortada[1] = "00";
				} else if (cortada[1].length() == 1) {
					cortada[1] = cortada[1] + "0";
				}
				String tratada = retorno + "," + cortada[1];
				return tratada.replace("-.", "-");
			} else {
				if (cortada[1].length() == 1) {
					cortada[1] = cortada[1] + "0";
				}
				return cortada[0] + "," + cortada[1];
			}
		} else {
			StringBuilder numero = new StringBuilder(n);
			StringBuilder segundoPonto = null;
			StringBuilder primeiroPonto = null;
			StringBuilder retorno = null;
			if (numero.length() > 3) {
				primeiroPonto = numero.reverse().insert(3, ".");
				if (primeiroPonto.length() > 7) {
					segundoPonto = primeiroPonto.insert(7, ".");
					retorno = segundoPonto.reverse();
				} else {
					retorno = primeiroPonto.reverse();
				}
				String tratada = retorno.toString();
				return tratada.replace("-.", "-") + ",00";
			} else {
				return n;
			}
		}
	}

	public String insereSeparadores(String n) {

		if (n.contains(",")) {
			String[] cortada = n.split(",");
			StringBuilder numero = new StringBuilder(cortada[0]);
			StringBuilder segundoPonto = null;
			StringBuilder primeiroPonto = null;
			StringBuilder retorno = null;
			if (numero.length() > 3) {
				primeiroPonto = numero.reverse().insert(3, ".");
				if (primeiroPonto.length() > 7) {
					segundoPonto = primeiroPonto.insert(7, ".");
					retorno = segundoPonto.reverse();
				} else {
					retorno = primeiroPonto.reverse();
				}
				if (cortada[1].equals("0")) {
					cortada[1] = "00";
				} else if (cortada[1].length() == 1) {
					cortada[1] = cortada[1];
				}
				String tratada = retorno + "," + cortada[1];
				return tratada.replace("-.", "-");
			} else {
				return n;
			}
		} else if (n.contains(".")) {
			n = n.replace(".", ",");
			String[] cortada = n.split(",");
			StringBuilder numero = new StringBuilder(cortada[0]);
			StringBuilder segundoPonto = null;
			StringBuilder primeiroPonto = null;
			StringBuilder retorno = null;
			if (numero.length() > 3) {
				primeiroPonto = numero.reverse().insert(3, ".");
				if (primeiroPonto.length() > 7) {
					segundoPonto = primeiroPonto.insert(7, ".");
					retorno = segundoPonto.reverse();
				} else {
					retorno = primeiroPonto.reverse();
				}
				if (cortada[1].equals("0")) {
					cortada[1] = "00";
				} else if (cortada[1].length() == 1) {
					cortada[1] = cortada[1];
				}
				String tratada = retorno + "," + cortada[1];
				return tratada.replace("-.", "-");
			} else {
				return n;
			}
		} else {
			StringBuilder numero = new StringBuilder(n);
			StringBuilder segundoPonto = null;
			StringBuilder primeiroPonto = null;
			StringBuilder retorno = null;
			if (numero.length() > 3) {
				primeiroPonto = numero.reverse().insert(3, ".");
				if (primeiroPonto.length() > 7) {
					segundoPonto = primeiroPonto.insert(7, ".");
					retorno = segundoPonto.reverse();
				} else {
					retorno = primeiroPonto.reverse();
				}
				String tratada = retorno.toString();
				return tratada.replace("-.", "-");
			} else {
				return n;
			}
		}
	}

	public static void main(String[] args) {

		Uteis utei = new Uteis();
		System.out.println("hello");
	}

	public String selecionaCodigosProduto(String cortaRetornaProduto) {
		String codigosProd = "";
		switch (cortaRetornaProduto) {
		case "AUTOMOVEIS":
			codigosProd = "5302,5303,5304,5388,3120,3172,3133,3136,3138,3173,3174,3175,3142,3143,3144,3145,3146,3147,3148,3149,3176,3177,3178,3179,3180,3181,3182,3183";
			break;
		case "Auto Tranquilo Exclusivo":
			codigosProd = "3133,3136,3138,3173,3174,3175";
			break;
		case "Auto Tranquilo Correntista":
			codigosProd = "3142,3143,3144,3145,3146,3147,3148,3149,3176,3177,3178,3179,3180,3181,3182,3183";
			break;
		case "Auto Tranquilo Frota":
			codigosProd = "3120,3172";
			break;
		case "Auto F�cil":
			codigosProd = "5302,5303,5304,5388";
			break;
		case "RDPJ":
			codigosProd = "7114,1801,1802,1804,1803,1805,1809";
			break;
		case "Rd Equipamentos":
			codigosProd = "7114";
			break;
		case "MR Empresarial":
			codigosProd = "1801,1802,1804";
			break;
		case "Empresarial Prazo Curto":
			codigosProd = "1809";
			break;
		case "Loterico":
			codigosProd = "1803";
			break;
		case "CCA":
			codigosProd = "1805";
			break;
		case "RDPF":
			codigosProd = "1412,1413,1402,1405,1409,1406,1408,1403,1407,1404,1002,1410,1411,1489,4002,7100,7101,7106,7107,7108,7109,7110,7117,7122,7123";
			break;
		case "Facil Residencial":
			codigosProd = "1402,1405";
			break;
		case "Lar Mais":
			codigosProd = "1409";
			break;
		case "MR Residencial Aporte Caixa":
			codigosProd = "1406,1408";
			break;
		case "MR Residencial Correntista":
			codigosProd = "1403,1407";
			break;
		case "MR Residencial Economiario":
			codigosProd = "1404";
			break;
		case "MCMV Mais Premiavel":// Minha Casa Minha Vida
			codigosProd = "1412";
			break;
		case "Cibrasec Securitizadora":
			codigosProd = "1413";
			break;
		case "Rd Pf Outros":
			codigosProd = "1002,1410,1411,1489,4002,7100,7101,7106,7107,7108,7109,7110,7117,7122,7123";
			break;
		case "Dirid":
			codigosProd = "5302,5303,5304,5388,3120,3172,3133,3136,3138,3173,3174,3175,3142,3143,3144,3145,3146,3147,3148,3149,3176,3177,3178,3179,3180,3181,3182,3183,7114,1801,1802,1804,1803,1805,1809,1412,1413,1402,1405,1409,1406,1408,1403,1407,1404,1002,1410,1411,1489,4002,7100,7101,7106,7107,7108,7109,7110,7117,7122,7123";
			break;
		}

		return codigosProd;
	}

	public String selecionaCodigosProduto_sinistros(String cortaRetornaProduto) {
		String codigosProd = "";
		switch (cortaRetornaProduto) {
		case "AUTOMOVEIS":
			codigosProd = "1001,3001,3101,3102,3103,3104,3105,3107,3108,3109,3110,3112,3114,3117,3119,3121,3122,3188,3189,3199,3299,4001,5001,5301,5399,5302,5303,5304,5388,3120,3172,3133,3136,3138,3173,3174,3175,3142,3143,3144,3145,3146,3147,3148,3149,3176,3177,3178,3179,3180,3181,3182,3183";
			break;
		case "Auto Tranquilo Exclusivo":
			codigosProd = "3133,3136,3138,3173,3174,3175";
			break;
		case "Auto Tranquilo Correntista":
			codigosProd = "3142,3143,3144,3145,3146,3147,3148,3149,3176,3177,3178,3179,3180,3181,3182,3183";
			break;
		case "Auto Tranquilo Frota":
			codigosProd = "3120,3172";
			break;
		case "Auto F�cil":
			codigosProd = "5302,5303,5304,5388";
			break;
		case "RDPJ":
			codigosProd = "7114,1801,1802,1804,1803,1805,1809";
			break;
		case "Rd Equipamentos":
			codigosProd = "7114";
			break;
		case "MR Empresarial":
			codigosProd = "1801,1802,1804";
			break;
		case "Empresarial Prazo Curto":
			codigosProd = "1809";
			break;
		case "Loterico":
			codigosProd = "1803";
			break;
		case "CCA":
			codigosProd = "1805";
			break;
		case "RDPF":
			codigosProd = "1412,1413,1402,1405,1409,1406,1408,1403,1407,1404,1002,1410,1411,1489,4002,7100,7101,7106,7107,7108,7109,7110,7117,7122,7123";
			break;
		case "Facil Residencial":
			codigosProd = "1402,1405";
			break;
		case "Lar Mais":
			codigosProd = "1409";
			break;
		case "MR Residencial Aporte Caixa":
			codigosProd = "1406,1408";
			break;
		case "MR Residencial Correntista":
			codigosProd = "1403,1407";
			break;
		case "MR Residencial Economiario":
			codigosProd = "1404";
			break;
		case "MCMV Mais Premiavel":// Minha Casa Minha Vida
			codigosProd = "1412";
			break;
		case "Cibrasec Securitizadora":
			codigosProd = "1413";
			break;
		case "Rd Pf Outros":
			codigosProd = "1002,1410,1411,1489,4002,7100,7101,7106,7107,7108,7109,7110,7117,7122,7123";
			break;
		case "Dirid":
			codigosProd = "5302,5303,5304,5388,3120,3172,3133,3136,3138,3173,3174,3175,3142,3143,3144,3145,3146,3147,3148,3149,3176,3177,3178,3179,3180,3181,3182,3183,7114,1801,1802,1804,1803,1805,1809,1412,1413,1402,1405,1409,1406,1408,1403,1407,1404,1002,1410,1411,1489,4002,7100,7101,7106,7107,7108,7109,7110,7117,7122,7123";
			break;
		}

		return codigosProd;
	}

	/**
	 * Retorna ultimo dia util do mes no formato '28/04/2017'
	 */
	public String getUltimoDiaUtilDoMes(Calendar calendar, int tipo) {

		// muda a data da vari�vel para o �ltimo dia do m�s
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);
		// enquanto for s�bado, domingo ou feriado
		while (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
				|| calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || isFeriado(calendar)) {
			// decrementa a data em um dia
			calendar.add(Calendar.DATE, -1);
		}

		switch (tipo) {
		case 1: // retorna apenas o ano
			DateFormat DATE_FORMAT = new SimpleDateFormat("YYYY");
			return DATE_FORMAT.format(calendar.getTime());
		case 2: // retorna apenas o m�s
			DateFormat DATE_FORMAT2 = new SimpleDateFormat("MM");
			return DATE_FORMAT2.format(calendar.getTime());
		case 3: // retorna apenas o dia
			DateFormat DATE_FORMAT3 = new SimpleDateFormat("dd");
			return DATE_FORMAT3.format(calendar.getTime());
		case 4: // retorna a data completa
			DateFormat DATE_FORMAT4 = new SimpleDateFormat("dd/MM/YYYY");
			return DATE_FORMAT4.format(calendar.getTime());
		}
		return "";
	}

	/**
	 * Retorna primeiro dia util do mes no formato '03/04/2017'
	 */
	public String getPrimeiroDiaUtilDoMes(Calendar calendar, int tipo) {
		// muda a data da vari�vel para o �ltimo dia do m�s
		calendar.set(Calendar.DAY_OF_MONTH, 1);

		// enquanto for s�bado, domingo ou feriado
		while (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
				|| calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || isFeriado(calendar)) {
			// decrementa a data em um dia
			calendar.add(Calendar.DATE, 1);
		}
		switch (tipo) {
		case 1: // retorna apenas o ano
			DateFormat DATE_FORMAT = new SimpleDateFormat("YYYY");
			return DATE_FORMAT.format(calendar.getTime());
		case 2: // retorna apenas o m�s
			DateFormat DATE_FORMAT2 = new SimpleDateFormat("MM");
			return DATE_FORMAT2.format(calendar.getTime());
		case 3: // retorna apenas o dia
			DateFormat DATE_FORMAT3 = new SimpleDateFormat("dd");
			return DATE_FORMAT3.format(calendar.getTime());
		case 4: // retorna a data completa
			DateFormat DATE_FORMAT4 = new SimpleDateFormat("dd/MM/YYYY");
			return DATE_FORMAT4.format(calendar.getTime());
		}
		return "";
	}

	/**
	 * M�todo para verificar feriados utilizado nos metodos getPrimeiroDiaUtilDoMes
	 * e getUltimoDiaUtilDoMes e retornaDiasUteisMes
	 * 
	 */
	private boolean isFeriado(Calendar calendar) {
		ArrayList<Calendar> listaFeriados = new ArrayList<Calendar>();

		/* Feriados de 2017 */
		Calendar feriado = Calendar.getInstance();

		if (calendar.get(Calendar.YEAR) == 2017) {

			// Carnaval
			feriado.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, 28);
			listaFeriados.add(feriado);

			feriado = Calendar.getInstance();
			// dia do trabalho
			feriado.set(calendar.get(Calendar.YEAR), Calendar.MAY, 1);
			listaFeriados.add(feriado);

			feriado = Calendar.getInstance();
			// Corpus Christi
			feriado.set(calendar.get(Calendar.YEAR), Calendar.JUNE, 15);
			listaFeriados.add(feriado);

			feriado = Calendar.getInstance();
			// Independencia
			feriado.set(calendar.get(Calendar.YEAR), Calendar.SEPTEMBER, 7);
			listaFeriados.add(feriado);

			feriado = Calendar.getInstance();
			// Independencia
			feriado.set(calendar.get(Calendar.YEAR), Calendar.OCTOBER, 12);
			listaFeriados.add(feriado);

			feriado = Calendar.getInstance();
			// Finados
			feriado.set(calendar.get(Calendar.YEAR), Calendar.NOVEMBER, 2);
			listaFeriados.add(feriado);

			feriado = Calendar.getInstance();
			// ProclamacaoRepublica
			feriado.set(calendar.get(Calendar.YEAR), Calendar.NOVEMBER, 15);
			listaFeriados.add(feriado);

			feriado = Calendar.getInstance();
			// ProclamacaoRepublica
			feriado.set(calendar.get(Calendar.YEAR), Calendar.DECEMBER, 25);
			listaFeriados.add(feriado);

		} else if (calendar.get(Calendar.YEAR) == 2018) {

			feriado = Calendar.getInstance();
			// ano novo
			feriado.set(calendar.get(Calendar.YEAR), Calendar.JANUARY, 1);
			listaFeriados.add(feriado);

			feriado = Calendar.getInstance();
			feriado.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, 13);
			listaFeriados.add(feriado);

			feriado = Calendar.getInstance();
			feriado.set(calendar.get(Calendar.YEAR), Calendar.MARCH, 30);
			listaFeriados.add(feriado);

			feriado = Calendar.getInstance();
			feriado.set(calendar.get(Calendar.YEAR), Calendar.APRIL, 21);
			listaFeriados.add(feriado);

			feriado = Calendar.getInstance();
			feriado.set(calendar.get(Calendar.YEAR), Calendar.MAY, 1);
			listaFeriados.add(feriado);

			feriado = Calendar.getInstance();
			feriado.set(calendar.get(Calendar.YEAR), Calendar.MAY, 30);
			listaFeriados.add(feriado);

		}
		for (int i = 0; i < listaFeriados.size(); i++) {
			if (calendar.get(Calendar.DAY_OF_YEAR) == listaFeriados.get(i).get(Calendar.DAY_OF_YEAR)) {
				return true;
			}
		}
		return false;
	}

	public int retornaDiasUteisMes(int numeroMes) {
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.MONTH, numeroMes);

		int diasUteis = Integer.parseInt(getUltimoDiaUtilDoMes(calendar, 3));

		for (int i = 1; i <= Integer.parseInt(getUltimoDiaUtilDoMes(calendar, 3)); i++) {
			calendar.set(Calendar.DAY_OF_MONTH, i);
			if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
					|| calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || isFeriado(calendar)) {
				diasUteis--;
			}
		}

		return diasUteis;
	}

}