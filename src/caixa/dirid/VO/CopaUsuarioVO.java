package caixa.dirid.VO;

/**
 * Classe Bean utilizada no sistema de apostas do campeonato da Euro copa para o
 * Celso Nagata.
 * 
 * */
public class CopaUsuarioVO {

	int idBean;
	String login;
	String nomeBean;
	String senhaBean;
	int permissaoBean;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public int getIdBean() {
		return idBean;
	}

	public void setIdBean(int idBean) {
		this.idBean = idBean;
	}

	public String getNomeBean() {
		return nomeBean;
	}

	public void setNomeBean(String nomeBean) {
		this.nomeBean = nomeBean;
	}

	public String getSenhaBean() {
		return senhaBean;
	}

	public void setSenhaBean(String senhaBean) {
		this.senhaBean = senhaBean;
	}

	public int getPermissaoBean() {
		return permissaoBean;
	}

	public void setPermissaoBean(int permissaoBean) {
		this.permissaoBean = permissaoBean;
	}

}
