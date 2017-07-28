package caixa.dirid.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import caixa.dirid.CONEXAO.Conexao;
import caixa.dirid.VO.UsuarioVO;

public class UsuarioDAO {

	/******************** SELECT ***************************************/
	public UsuarioVO buscaTodosDadosPeloCpf(String cpf) {
		Connection con = new Conexao().getConexaoMySql("UsuarioDAO - buscaTodosDadosPeloCpf");
		String sql = "SELECT * FROM usuario where cpf = " + cpf + ";";
		PreparedStatement ps = null;
		UsuarioVO usuario = new UsuarioVO();
		try {
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				usuario.setId(rs.getInt("idUsuario"));
				usuario.setNome(rs.getString("nome"));
				usuario.setTelefone(rs.getString("telefone"));
				usuario.setEmail(rs.getString("email"));
				usuario.setCpf(rs.getString("cpf"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro no metodo buscaTodosDadosPeloCpf DAO");
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return usuario;
	}

	public UsuarioVO selecionaClientePorId(String id) {
		Connection con = new Conexao().getConexaoMySql("UsuarioDAO - selecionaClientePorId");
		UsuarioVO usuariovo = new UsuarioVO();
		try {
			Statement statement = con.createStatement();
			ResultSet rs = statement
					.executeQuery("select * from usuario where idUsuario ="
							+ id + ";");
			rs.next();
			usuariovo.setId(rs.getInt(1));
			usuariovo.setNome(rs.getString(2));
			usuariovo.setTelefone(rs.getString(3));
			usuariovo.setEmail(rs.getString(4));
			usuariovo.setCpf(rs.getString(5));

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("erro no metodo selecionaClientePorId DAO");
		}
		return usuariovo;
	}

	public String selecionarClienteParaLogin(String cpf) {
		Connection con = new Conexao().getConexaoMySql("UsuarioDAO - selecionarClienteParaLogin");
		String sql = "SELECT DISTINCT cpf FROM usuario where cpf like '" + cpf
				+ "'";
		String cpfBanco = null;
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				cpfBanco = rs.getString("cpf");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("erro no metodo selecionarClienteParaLogin DAO");
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cpfBanco;
	}

}
