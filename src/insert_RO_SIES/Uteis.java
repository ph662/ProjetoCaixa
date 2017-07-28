package insert_RO_SIES;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
		String[] meses = { "Janeiro", "Fevereiro", "Março", "Abril", "Maio",
				"Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro",
				"Dezembro" };

		return meses;
	}

	public String[] mesesPaginaWeb() {
		String[] meses = { "Janeiro", "Fevereiro", "Mar&ccedil;o", "Abril",
				"Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro",
				"Novembro", "Dezembro" };

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

	public String dataAtual(int tipo) {
		Calendar cal = Calendar.getInstance();
		Date date = Calendar.getInstance().getTime();
		switch (tipo) {
		case 1: // retorna apenas o ano
			DateFormat dateFormat = new SimpleDateFormat("YYYY");
			return dateFormat.format(date);
		case 2: // retorna apenas o mês
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
		System.out.println(utei.insereSeparadores("17000,00"));

	}

}