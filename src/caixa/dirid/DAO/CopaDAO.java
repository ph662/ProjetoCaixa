package caixa.dirid.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import caixa.dirid.CONEXAO.Conexao;
import caixa.dirid.VO.CopaApostasRealizadasVO;
import caixa.dirid.VO.CopaPartidasVO;
import caixa.dirid.VO.CopaResultadosCompletoVO;
import caixa.dirid.VO.CopaUsuarioVO;

public class CopaDAO {

	public String selecionarClienteParaLogin(String cpf) {
		Connection con = new Conexao()
				.getConexaoMySql("UsuarioDAO - selecionarClienteParaLogin");
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

	/**
	 * Recebe request e faz select do usuario e senha, caso o usuario ainda nao
	 * exista, insere este usuario recebido na request na tabela euro_usuarios.
	 * 
	 * @return CopaUsuarioVO
	 * 
	 * @arguments HttpServletRequest request
	 */
	public CopaUsuarioVO logarDAO(HttpServletRequest request) {
		Connection con = new Conexao().getConexaoMySql("logarDAO - CopaDAO");
		CopaUsuarioVO usuarioVO = new CopaUsuarioVO();
		try {
			Statement statement = con.createStatement();

			// String login = request.getParameter("login").replaceAll("[\\W]",
			// "");
			String login = request.getParameter("nLogin");
			String senha = request.getParameter("nSenha");

			String sqlSelect = "select * from euro_usuarios where login = '"
					+ login + "' and senha = '" + senha + "';";

			ResultSet rs = statement.executeQuery(sqlSelect);

			rs.next();
			usuarioVO.setIdBean(rs.getInt(1));
			usuarioVO.setLogin(rs.getString(2));
			usuarioVO.setNomeBean(rs.getString(3));
			usuarioVO.setSenhaBean(rs.getString(4));
			usuarioVO.setPermissaoBean(rs.getInt(5));

		} catch (SQLException SQLe) {

			usuarioVO.setLogin("false");

		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERRO2 metodo logarDAO CopaDAO");
			}
		}

		return usuarioVO;
	}

	/**
	 * Recebe request e faz select do usuario e senha, caso o usuario ainda nao
	 * exista, insere este usuario recebido na request na tabela euro_usuarios.
	 * 
	 * @return CopaUsuarioVO
	 * 
	 * @arguments HttpServletRequest request
	 */
	public void cadastrarUsuarioDAO(HttpServletRequest request) {
		Connection con = new Conexao()
				.getConexaoMySql("cadastrarUsuarioDAO - CopaDAO");

		try {

			String login = request.getParameter("nLoginAdmin");
			String nome = request.getParameter("nNomeAdmin");
			String senha = request.getParameter("nSenhaAdmin");

			PreparedStatement ps = null;

			System.out.println("insereeUsuario");
			String sqlInsert = "insert into euro_usuarios (login,nome,senha,adminPermissao) values ('"
					+ login + "','" + nome + "','" + senha + "',0);";

			ps = con.prepareStatement(sqlInsert);
			ps.execute();

		} catch (Exception e) {
			e.printStackTrace();
			System.out
					.println("Metodo cadastrarUsuarioDAO - classe CopaDAO - erro ao inserir");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERRO1 metodo cadastrarUsuarioDAO CopaDAO");
			}
		}
	}

	/**
	 * 
	 * 
	 * @return CopaUsuarioVO
	 * 
	 * @arguments HttpServletRequest request
	 */
	public List<CopaResultadosCompletoVO> buscarTodasApostas() {
		Connection con = new Conexao()
				.getConexaoMySql("buscarTodasApostas - CopaDAO");

		List<CopaResultadosCompletoVO> dados = new ArrayList<CopaResultadosCompletoVO>();
		try {
			Statement statement = con.createStatement();

			String sqlSelect = "select eu.nome, eu.login ,er.Resultado,  y.Nome as time1, ea.Aposta_Player1,ea.Aposta_Player2,z.Nome as time2,  y.Nome as timeReal1,ec.ResultadoReal_Player1,ec.ResultadoReal_Player2,z.Nome as timeReal2,  ec.data_partida,ea.fase  from euro_resultado er  join euro_apostas ea on er.idapostas_FK = ea.idApostas  join euro_campeonato ec on ea.idCampeonato_FK = ec.IdCampeonato  JOIN euro_clubes y ON y.idclubes = ec.idclubes_player1   JOIN euro_clubes z ON z.idclubes = ec.idclubes_player2  join euro_usuarios eu on eu.idUsuarios = ea.idUsuarios_FK  order by eu.nome,er.resultado desc, ec.data_partida;";

			ResultSet rs = statement.executeQuery(sqlSelect);

			while (rs.next()) {
				CopaResultadosCompletoVO partidaVO = new CopaResultadosCompletoVO();

				partidaVO.setNome(rs.getString(1));
				partidaVO.setLogin(rs.getString(2));
				partidaVO.setResultado(rs.getString(3));
				partidaVO.setNomeTime1(rs.getString(4));
				partidaVO.setApostaP1(rs.getString(5));
				partidaVO.setApostaP2(rs.getString(6));
				partidaVO.setNomeTime2(rs.getString(7));
				partidaVO.setNomeTimeReal1(rs.getString(8));
				partidaVO.setRealP1(rs.getString(9));
				partidaVO.setRealP2(rs.getString(10));
				partidaVO.setNomeTimeReal2(rs.getString(11));
				partidaVO.setDataPartida(rs.getString(12));
				partidaVO.setFase(rs.getString(13));

				dados.add(partidaVO);
			}
		} catch (SQLException SQLe) {
			System.out
					.println("ERRO1 Classe EURODAO - metodo buscarTodasApostas");
			SQLe.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out
						.println("ERRO2 Classe EURODAO - metodo buscarTodasApostas");
			}
		}

		return dados;
	}

	/**
	 * 
	 * 
	 * @return CopaUsuarioVO
	 * 
	 * @arguments HttpServletRequest request
	 */
	public List<CopaResultadosCompletoVO> buscarRank() {
		Connection con = new Conexao().getConexaoMySql("buscarRank - CopaDAO");

		List<CopaResultadosCompletoVO> dados = new ArrayList<CopaResultadosCompletoVO>();
		try {
			Statement statement = con.createStatement();

			String sqlSelect = "select eu.nome,sum(er.Resultado) as pontuacao      "
					+ "  from euro_resultado er join euro_apostas ea on er.idapostas_FK = ea.idApostas "
					+ "join euro_campeonato ec on ea.idCampeonato_FK = ec.IdCampeonato join euro_usuarios eu on eu.idUsuarios = ea.idUsuarios_FK "
					+ "group by eu.nome order by pontuacao desc;";

			ResultSet rs = statement.executeQuery(sqlSelect);

			while (rs.next()) {
				CopaResultadosCompletoVO partidaVO = new CopaResultadosCompletoVO();

				partidaVO.setNome(rs.getString(1));
				partidaVO.setResultado(rs.getString(2));

				dados.add(partidaVO);
			}
		} catch (SQLException SQLe) {
			System.out.println("ERRO1 Classe EURODAO - metodo buscarRank");
			SQLe.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERRO2 Classe EURODAO - metodo buscarRank");
			}
		}

		return dados;
	}

	/**
	 *
	 * Seleciona informacoes das partidas e suas datas, tabela euro_campeonato.
	 *
	 * 
	 * @return List<FatuMensalVO>
	 * 
	 * @arguments String ano, String mes
	 */
	public List<CopaPartidasVO> buscaDadosPartidas(int fase) {
		Connection con = new Conexao().getConexaoMySql("logarDAO - CopaDAO");

		List<CopaPartidasVO> dados = new ArrayList<CopaPartidasVO>();
		try {
			Statement statement = con.createStatement();

			String sqlSelect = "SELECT ca.idCampeonato,y.Nome as time1,z.Nome as time2, ca.data_partida FROM sistemadirid.euro_campeonato ca join sistemadirid.euro_clubes y on y.idClubes = ca.idClubes_Player1 join sistemadirid.euro_clubes z on z.idClubes = ca.idClubes_Player2 and fase = "
					+ fase + " ORDER BY 1;";

			ResultSet rs = statement.executeQuery(sqlSelect);

			while (rs.next()) {
				CopaPartidasVO partidaVO = new CopaPartidasVO();
				partidaVO.setIdCampeonato(rs.getInt(1));
				partidaVO.setTime1(rs.getString(2));
				partidaVO.setTime2(rs.getString(3));
				partidaVO.setData(rs.getString(4));
				// System.out.println(rs.getString(2));
				dados.add(partidaVO);
			}
		} catch (SQLException SQLe) {
			System.out
					.println("ERRO1 Classe EURODAO - metodo buscaDadosPartidas");
			SQLe.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out
						.println("ERRO2 Classe EURODAO - metodo buscaDadosPartidas");
			}
		}

		return dados;
	}

	/**
	 *
	 *
	 *
	 * 
	 * @return List<FatuMensalVO>
	 * 
	 * @arguments String ano, String mes
	 */
	public void gravarApostas(String nomeReq, HttpServletRequest request) {
		Connection con = new Conexao()
				.getConexaoMySql("gravarApostas - CopaDAO");
		CopaUsuarioVO usuarioVO = new CopaUsuarioVO();
		try {
			Statement statement = con.createStatement();

			// String login = request.getParameter("login").replaceAll("[\\W]",
			// "");
			String id = request.getParameter("id");
			// id = "3";
			PreparedStatement ps = null;
			// for (Enumeration<String> e = request.getParameterNames(); e
			// .hasMoreElements();) {

			String enumRequest = nomeReq;
			if (!(enumRequest.contains("id") || enumRequest.contains("tipo") || enumRequest
					.contains("data"))) {
				String[] enumRequestArray = enumRequest.split("_");
				System.out.println(enumRequestArray[0] + " "
						+ enumRequestArray[1] + " AQIU ");

				String sqlSelect = "select idApostas from euro_apostas where idCampeonato_FK = "
						+ enumRequestArray[1]
						+ " and idUsuarios_FK = "
						+ id
						+ ";";
				System.out.println(sqlSelect);

				int idApostas;
				ResultSet rs = statement.executeQuery(sqlSelect);
				if (rs.next()) {
					idApostas = rs.getInt(1);
					if (idApostas != 0) {

						String sqlUpdate = "update euro_apostas set Aposta_"
								+ enumRequestArray[0] + " = "
								+ request.getParameter(enumRequest)
								+ " where idCampeonato_FK = "
								+ enumRequestArray[1] + " and idUsuarios_FK = "
								+ id + ";";

						System.out.println(sqlUpdate);
						ps = con.prepareStatement(sqlUpdate);
						ps.execute();

						System.out.println("aqui " + idApostas);
					}
				} else {// VALOR NULL

					System.out.println("AQU2 "
							+ request.getParameter(enumRequest));
					String sqlInsert = "";
					if (Integer.parseInt(enumRequestArray[1]) > 48
							&& Integer.parseInt(enumRequestArray[1]) <= 56 ) {
						sqlInsert = "insert into euro_apostas (idUsuarios_FK,idCampeonato_FK,Aposta_"
								+ enumRequestArray[0]
								+ ",Fase) "
								+ "values ("
								+ id
								+ ","
								+ enumRequestArray[1]
								+ ","
								+ request.getParameter(enumRequest) + ",2);";

					} else if (Integer.parseInt(enumRequestArray[1]) > 56
							&& Integer.parseInt(enumRequestArray[1]) <= 60) {
						sqlInsert = "insert into euro_apostas (idUsuarios_FK,idCampeonato_FK,Aposta_"
								+ enumRequestArray[0]
								+ ",Fase) "
								+ "values ("
								+ id
								+ ","
								+ enumRequestArray[1]
								+ ","
								+ request.getParameter(enumRequest) + ",3);";

					} else if (Integer.parseInt(enumRequestArray[1]) > 60
							&& Integer.parseInt(enumRequestArray[1]) <= 62) {
						sqlInsert = "insert into euro_apostas (idUsuarios_FK,idCampeonato_FK,Aposta_"
								+ enumRequestArray[0]
								+ ",Fase) "
								+ "values ("
								+ id
								+ ","
								+ enumRequestArray[1]
								+ ","
								+ request.getParameter(enumRequest) + ",4);";

					} else if (Integer.parseInt(enumRequestArray[1]) == 63) {//final
						sqlInsert = "insert into euro_apostas (idUsuarios_FK,idCampeonato_FK,Aposta_"
								+ enumRequestArray[0]
								+ ",Fase) "
								+ "values ("
								+ id
								+ ","
								+ enumRequestArray[1]
								+ ","
								+ request.getParameter(enumRequest) + ",5);";
					
					} else if (Integer.parseInt(enumRequestArray[1]) == 64) {//final
						sqlInsert = "insert into euro_apostas (idUsuarios_FK,idCampeonato_FK,Aposta_"
								+ enumRequestArray[0]
								+ ",Fase) "
								+ "values ("
								+ id
								+ ","
								+ enumRequestArray[1]
								+ ","
								+ request.getParameter(enumRequest) + ",6);";
					} else {
						sqlInsert = "insert into euro_apostas (idUsuarios_FK,idCampeonato_FK,Aposta_"
								+ enumRequestArray[0]
								+ ",Fase) "
								+ "values ("
								+ id
								+ ","
								+ enumRequestArray[1]
								+ ","
								+ request.getParameter(enumRequest) + ",1);";
					}
					System.out.println(sqlInsert);
					ps = con.prepareStatement(sqlInsert);
					ps.execute();
				}

			}

			// }

		} catch (SQLException SQLe) {
			SQLe.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERRO2 metodo logarDAO CopaDAO");
			}
		}
	}

	/**
	 *
	 *
	 *
	 * 
	 * @return List<FatuMensalVO>
	 * 
	 * @arguments String ano, String mes
	 */
	public void gravarApostasAdmin(HttpServletRequest request) {

		Connection con = new Conexao()
				.getConexaoMySql("gravarApostas - CopaDAO");
		CopaUsuarioVO usuarioVO = new CopaUsuarioVO();
		try {
			Statement statement = con.createStatement();

			// String login = request.getParameter("login").replaceAll("[\\W]",
			// "");
			String id = request.getParameter("id");
			// id = "3";
			PreparedStatement ps = null;
			int i = 1;
			for (Enumeration<String> e = request.getParameterNames(); e
					.hasMoreElements();) {
				String idCampeonato, player;
				String enumRequest = e.nextElement();
				if (!(enumRequest.contains("id")
						|| enumRequest.contains("admin")
						|| enumRequest.contains("senha") || enumRequest
							.contains("tipo"))) {

					String[] enumRequestArray = enumRequest.split("_");

					System.out.println("player " + enumRequestArray[0]
							+ " | idCampeo " + enumRequestArray[1] + "  ");

					idCampeonato = enumRequestArray[1];
					player = enumRequestArray[0];

					String sqlUpdate = "update euro_campeonato set ResultadoReal_"
							+ player
							+ " = "
							+ request.getParameter(enumRequest)
							+ " where idCampeonato = " + idCampeonato + ";";
					System.out.println(sqlUpdate);
					ps = con.prepareStatement(sqlUpdate);
					ps.execute();
					if (i == 2) {
						String geraResultadoApostas = "call `prcEuroCalculaApostas`('"
								+ idCampeonato + "','3')";
						System.out.println(geraResultadoApostas);
						statement.executeUpdate(geraResultadoApostas);
						i = 0;
					}
					i++;
				}
			}

		} catch (SQLException SQLe) {
			SQLe.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERRO2 metodo gravarApostasAdmin CopaDAO");
			}
		}
	}

	/**
	 *
	 *
	 *
	 * 
	 * @return List<FatuMensalVO>
	 * 
	 * @arguments String ano, String mes
	 */

	public List<CopaApostasRealizadasVO> buscaApostasRealizadas(
			CopaUsuarioVO usuario, int fase) {

		Connection con = new Conexao()
				.getConexaoMySql("buscaApostasRealizadas - CopaDAO");

		List<CopaApostasRealizadasVO> dados = new ArrayList<CopaApostasRealizadasVO>();
		try {
			Statement statement = con.createStatement();

			String sqlSelect = "select idCampeonato_FK,Aposta_player1,aposta_player2 from euro_apostas where idUsuarios_FK = "
					+ usuario.getIdBean()
					+ " and fase = "
					+ fase
					+ " order by idCampeonato_FK;";

			ResultSet rs = statement.executeQuery(sqlSelect);
			System.out.println(sqlSelect);
			while (rs.next()) {
				CopaApostasRealizadasVO partidaVO = new CopaApostasRealizadasVO();
				partidaVO.setIdCampeonato(rs.getInt(1));
				partidaVO.setPlayer1(rs.getInt(2));
				partidaVO.setPlayer2(rs.getInt(3));
				dados.add(partidaVO);
			}
		} catch (SQLException SQLe) {
			System.out
					.println("ERRO1 Classe EURODAO - metodo buscaApostasRealizadas");
			SQLe.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out
						.println("ERRO2 Classe EURODAO - metodo buscaApostasRealizadas");
			}
		}

		return dados;
	}

	/**
	 *
	 *
	 *
	 * 
	 * @return List<FatuMensalVO>
	 * 
	 * @arguments String ano, String mes
	 */
	public List<CopaApostasRealizadasVO> buscaApostasRealizadasAdmin(int fase) {
		Connection con = new Conexao()
				.getConexaoMySql("buscaApostasRealizadasAdmin - CopaDAO");

		List<CopaApostasRealizadasVO> dados = new ArrayList<CopaApostasRealizadasVO>();
		try {
			Statement statement = con.createStatement();

			String sqlSelect = "select idCampeonato,ResultadoReal_player1,ResultadoReal_player2 from euro_campeonato where (ResultadoReal_player1 OR ResultadoReal_player2) is NOT null and fase = "
					+ fase + " order by idCampeonato;";

			ResultSet rs = statement.executeQuery(sqlSelect);
			System.out.println(sqlSelect);
			while (rs.next()) {
				CopaApostasRealizadasVO partidaVO = new CopaApostasRealizadasVO();
				partidaVO.setIdCampeonato(rs.getInt(1));
				partidaVO.setPlayer1(rs.getInt(2));
				partidaVO.setPlayer2(rs.getInt(3));
				dados.add(partidaVO);
			}
		} catch (SQLException SQLe) {
			System.out
					.println("ERRO1 Classe EURODAO - metodo buscaApostasRealizadasAdmin");
			SQLe.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out
						.println("ERRO2 Classe EURODAO - metodo buscaApostasRealizadasAdmin");
			}
		}
		return dados;
	}

	/**
	 *
	 *
	 *
	 * 
	 * @return List<FatuMensalVO>
	 * 
	 * @arguments String ano, String mes
	 */

	public void alterarSenha(HttpServletRequest request) {
		Connection con = new Conexao()
				.getConexaoMySql("alterarSenha - CopaDAO");
		try {
			Statement statement = con.createStatement();

			// String login = request.getParameter("login").replaceAll("[\\W]",
			// "");
			String id = request.getParameter("id");
			// id = "3";
			PreparedStatement ps = null;

			if (Integer.parseInt(id) != 0) {

				String sqlUpdate = "update euro_usuarios set senha = '"
						+ request.getParameter("novaSenha")
						+ "' where idUsuarios = " + id + ";";

				System.out.println(sqlUpdate);
				ps = con.prepareStatement(sqlUpdate);
				ps.execute();

			}

		} catch (SQLException SQLe) {
			SQLe.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("ERRO2 metodo alterarSenha CopaDAO");
			}
		}
	}
}