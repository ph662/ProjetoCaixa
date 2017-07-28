import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/*Exemplo de como criar arquivo em excel pelo Java*/
public class CreateExlFile {
	public static void main(String[] args) {
		try {
			String filename = "Downloads/NewExcelFile.xls";
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("FirstSheet");

			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell(0).setCellValue("No.");
			rowhead.createCell(1).setCellValue("Name");
			rowhead.createCell(2).setCellValue("Address");
			rowhead.createCell(3).setCellValue("Email");

			HSSFRow row = sheet.createRow((short) 1);
			row.createCell(0).setCellValue("1");
			row.createCell(1).setCellValue("Sankumarsingh");
			row.createCell(2).setCellValue("India");
			row.createCell(3).setCellValue("sankumarsingh@gmail.com");

			HSSFRow row2 = sheet.createRow((short) 2);
			row2.createCell(0).setCellValue("2");
			row2.createCell(1).setCellValue("Sankumarsingh");
			row2.createCell(2).setCellValue("India");
			row2.createCell(3).setCellValue("sankumarsingh@gmail.com");

			FileOutputStream fileOut = new FileOutputStream(filename);
			workbook.write(fileOut);
			fileOut.close();
			System.out.println("Your excel file has been generated!");

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex);
		}
	}
}

/*
 * public static void main(String[] args) { String argss =
 * "movimentacaoEmissao151116.csv"; String[] nomeExtensao; String nome, data;
 * Uteis uti = new Uteis();
 * 
 * nomeExtensao = argss.split("15"); for (int i = 0; i < nomeExtensao.length;
 * i++) { // System.out.println(nomeExtensao[i]); }
 * 
 * nome = nomeExtensao[0]; data = (uti.dataAtual(1).replace("20", "") +
 * nomeExtensao[1].replace( ".csv", ""));
 * 
 * System.out.println(nome); System.out.println(data);
 * 
 * insereBanco(nome, data); }
 * 
 * public static void insereBanco(String nome, String data) { switch (nome) {
 * case "movimentacaoEmissao":
 * 
 * File file = new File( "D:\\Relatorio Sensibilizacao/movimentacaoEmissao" +
 * data + ".csv");
 * 
 * /* ******************************************************************* /
 * 
 * FileInputStream fis = null; BufferedInputStream bis = null; DataInputStream
 * dis = null;
 * 
 * try { fis = new FileInputStream(file); bis = new BufferedInputStream(fis);
 * dis = new DataInputStream(bis);
 * 
 * Connection con = new Conexao() .getConexaoMySql("hello fileInput");
 * System.out.println("Inserindo linhas..."); // int j = 1; while
 * (dis.available() != 0) {
 * 
 * @SuppressWarnings("deprecation") String[] part = dis.readLine().split(";");
 * 
 * String sql =
 * "insert into movimentacao_emissao values (STR_TO_DATE(?,'%d-%m-%Y'),?,?,?,?,?);"
 * ;
 * 
 * // /******************************************************************** /
 * 
 * try { PreparedStatement ps = con.prepareStatement(sql);
 * 
 * String anoCut = data.substring(0, 2); String mesCut = data.substring(2, 4);
 * String diaCut = data.substring(4, 6); String dataNova = diaCut + "-" + mesCut
 * + "-" + "20" + anoCut;
 * 
 * for (int i = 1; i <= 6; i++) { if (i == 1) { ps.setString(i, dataNova); }
 * else { ps.setString(i, part[i - 2].trim()); }
 * 
 * } ps.execute(); ps.close(); ps = null; } catch (SQLException e) {
 * e.printStackTrace(); } part = null; sql = null; }
 * System.out.println("INSERT DO ARQUIVO FINALIZADO"); } catch (IOException e) {
 * e.printStackTrace(); } finally { try { fis.close(); bis.close(); dis.close();
 * } catch (IOException ex) { ex.printStackTrace(); } } break;
 * 
 * case "movimentacaoFinanceira":
 * 
 * break; } } }
 */
