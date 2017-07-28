package caixa.dirid.VO;

public class CoberturasVO {
	private int idCobertura;
	private String cobertura;
	private String franquia;
	private String LMI;
	private String numAceitacao;

	public String getLMI() {
		return LMI;
	}

	public void setLMI(String lMI) {
		LMI = lMI;
	}

	public String getNumAceitacao() {
		return numAceitacao;
	}

	public void setNumAceitacao(String numAceitacao) {
		this.numAceitacao = numAceitacao;
	}

	public int getIdCobertura() {
		return idCobertura;
	}

	public void setIdCobertura(int idCobertura) {
		this.idCobertura = idCobertura;
	}

	public String getCobertura() {
		return cobertura;
	}

	public void setCobertura(String cobertura) {
		this.cobertura = cobertura;
	}

	public String getFranquia() {
		return franquia;
	}

	public void setFranquia(String franquia) {
		this.franquia = franquia;
	}
}
