package insert_RO_SIAS_SIES_diaria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class Conexao {

	public Connection getConexaoMySql(String metodo) {
		try {
			Date date = Calendar.getInstance().getTime();
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println(date + " MySQL conectado - Método " + metodo);
			return DriverManager
					.getConnection("jdbc:mysql://localhost:3306/SistemaDirid?user=root&password=root");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
