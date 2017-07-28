package caixa.dirid.BO;

import caixa.dirid.DAO.UsuarioDAO;
import caixa.dirid.VO.UsuarioVO;

public class UsuarioBO {

	/******************** SELECT *****************************/
	public boolean comparaLogin(String cpf) {
		UsuarioDAO dao = new UsuarioDAO();
		String clienteCPF = dao.selecionarClienteParaLogin(cpf);
		System.out.println(clienteCPF);
		if (cpf.equals(clienteCPF))
			return true;
		else
			return false;
	}

	public UsuarioVO buscaPorCPF(String cpf) {
		UsuarioDAO dao = new UsuarioDAO();
		UsuarioVO usuarioDados = new UsuarioVO();

		usuarioDados = dao.buscaTodosDadosPeloCpf(cpf);
		return usuarioDados;
	}

	/******************** COMPARA*ADMIN *********************/
	public boolean comparaAdmin(String CPF) {
		if (CPF.equals("147"))
			return true;
		else
			return false;
	}
}