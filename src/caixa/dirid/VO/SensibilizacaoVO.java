package caixa.dirid.VO;

public class SensibilizacaoVO {
	private String codAgencia;
	private String Agencia;
	private String produto;
	private String SR;
	private String SUAT;
	private String quantidade;
	private String valor;
	private String dataInicio;
	private String dataFim;

	public String getAgencia() {
		return Agencia;
	}

	public void setAgencia(String agencia) {
		Agencia = agencia;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public String getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getDataFim() {
		return dataFim;
	}

	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}

	public String getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}

	public String getCodAgencia() {
		return codAgencia;
	}

	public void setCodAgencia(String codAgencia) {
		this.codAgencia = codAgencia;
	}

	public String getSR() {
		return SR;
	}

	public void setSR(String sR) {
		SR = sR;
	}

	public String getSUAT() {
		return SUAT;
	}

	public void setSUAT(String sUAT) {
		SUAT = sUAT;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}