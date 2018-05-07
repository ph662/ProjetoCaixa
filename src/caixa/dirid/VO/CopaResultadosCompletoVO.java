package caixa.dirid.VO;

/**
 * Classe Bean utilizada no sistema de apostas do campeonato da Euro copa para o
 * Celso Nagata.
 * 
 * */
public class CopaResultadosCompletoVO {
	String nome;
	String login;
	String resultado;
	String nomeTime1;
	String nomeTime2;
	String apostaP1;
	String apostaP2;
	String nomeTimeReal1;
	String nomeTimeReal2;
	String realP1;
	String realP2;
	String dataPartida;
	String fase;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFase() {
		return fase;
	}

	public void setFase(String fase) {
		this.fase = fase;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public String getNomeTime1() {
		return nomeTime1;
	}

	public void setNomeTime1(String nomeTime1) {
		this.nomeTime1 = nomeTime1;
	}

	public String getNomeTime2() {
		return nomeTime2;
	}

	public void setNomeTime2(String nomeTime2) {
		this.nomeTime2 = nomeTime2;
	}

	public String getApostaP1() {
		return apostaP1;
	}

	public void setApostaP1(String apostaP1) {
		this.apostaP1 = apostaP1;
	}

	public String getApostaP2() {
		return apostaP2;
	}

	public void setApostaP2(String apostaP2) {
		this.apostaP2 = apostaP2;
	}

	public String getNomeTimeReal1() {
		return nomeTimeReal1;
	}

	public void setNomeTimeReal1(String nomeTimeReal1) {
		this.nomeTimeReal1 = nomeTimeReal1;
	}

	public String getNomeTimeReal2() {
		return nomeTimeReal2;
	}

	public void setNomeTimeReal2(String nomeTimeReal2) {
		this.nomeTimeReal2 = nomeTimeReal2;
	}

	public String getRealP1() {
		return realP1;
	}

	public void setRealP1(String realP1) {
		this.realP1 = realP1;
	}

	public String getRealP2() {
		return realP2;
	}

	public void setRealP2(String realP2) {
		this.realP2 = realP2;
	}

	public String getDataPartida() {
		return dataPartida;
	}

	public void setDataPartida(String dataPartida) {
		this.dataPartida = dataPartida;
	}
}
