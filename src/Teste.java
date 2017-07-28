import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import caixa.dirid.BO.VisaoExecutiva_Diaria_BO;
import caixa.dirid.DAO.VisaoExecutiva_Diaria_DAO;

/*Classe utilizada para realizar testes*/
public class Teste {

	public static void main(String[] args) throws IOException {

		/*
		 * VisaoExecutiva_Diaria_BO bo = new
		 * VisaoExecutiva_Diaria_BO("MR Empresarial2016");
		 * bo.validaSelecionaEmissaoPorCanal();
		 */

	}

	public static Calendar getUltimoDiaUtilDoMes(Calendar calendar) {
		// muda a data da variável para o último dia do mês
		System.out.println("antes " + calendar.getTime());
		calendar.add(Calendar.MONTH, 1);
		System.out.println("1- " + calendar.getTime());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		System.out.println("2- " + calendar.getTime());
		calendar.add(Calendar.DATE, -1);
		System.out.println("3- " + calendar.getTime());
		// enquanto for sábado, domingo ou feriado
		while (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
				|| calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
				|| isFeriado(calendar)) {
			// decrementa a data em um dia
			calendar.add(Calendar.DATE, -1);
		}
		return calendar;
	}

	public static Calendar getPrimeiroDiaUtilDoMes(Calendar calendar) {
		// muda a data da variável para o último dia do mês
		calendar.set(Calendar.DAY_OF_MONTH, 1);

		// enquanto for sábado, domingo ou feriado
		while (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
				|| calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
				|| isFeriado(calendar)) {
			// decrementa a data em um dia
			calendar.add(Calendar.DATE, 1);
		}
		return calendar;
	}

	public static boolean isFeriado(Calendar calendar) {
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

		} else if (calendar.get(Calendar.YEAR) == 2018) {

			feriado = Calendar.getInstance();
			// ano novo
			feriado.set(calendar.get(Calendar.YEAR), Calendar.JANUARY, 1);
			listaFeriados.add(feriado);

			feriado = Calendar.getInstance();
			feriado.set(calendar.get(Calendar.YEAR), Calendar.MARCH, 30);
			listaFeriados.add(feriado);

			feriado = Calendar.getInstance();
			feriado.set(calendar.get(Calendar.YEAR), Calendar.MAY, 1);
			listaFeriados.add(feriado);

			feriado = Calendar.getInstance();
			feriado.set(calendar.get(Calendar.YEAR), Calendar.MAY, 30);
			listaFeriados.add(feriado);

			feriado = Calendar.getInstance();
			// ano novo
			feriado.set(calendar.get(Calendar.YEAR), Calendar.JANUARY, 1);
			listaFeriados.add(feriado);

		}
		for (int i = 0; i < listaFeriados.size(); i++) {
			if (calendar.get(Calendar.DAY_OF_YEAR) == listaFeriados.get(i).get(
					Calendar.DAY_OF_YEAR)) {
				return true;
			}
		}
		return false;
	}

	public static void splitFile(File f) throws IOException {
		int partCounter = 1;// I like to name parts from 001, 002, 003, ...
							// you can change it to 0 if you want 000, 001, ...

		// int sizeOfFiles = 477487360;// 400MB
		int sizeOfFiles = 487360;// 400MB
		byte[] buffer = new byte[sizeOfFiles];

		try (BufferedInputStream bis = new BufferedInputStream(
				new FileInputStream(f))) {// try-with-resources to ensure
											// closing stream
			String name = f.getName();

			int tmp = 0;
			while ((tmp = bis.read(buffer)) > 0) {
				// write each chunk of data into separate file with different
				// number in name
				File newFile = new File(f.getParent(), name + "."
						+ String.format("%03d", partCounter++));
				try (FileOutputStream out = new FileOutputStream(newFile)) {
					out.write(buffer, 0, tmp);// tmp is chunk size
				}
			}
		}
	}
}
