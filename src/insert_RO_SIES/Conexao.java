package insert_RO_SIES;

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

	public Connection getConexaoSQLServer() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			System.out.println("conectado SQLServer!!");
			return DriverManager
					.getConnection("jdbc:sqlserver://10.100.8.243:1550/CX_SEG?user=est10815&password=tyfhx5za");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
