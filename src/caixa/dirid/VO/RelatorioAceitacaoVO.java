package caixa.dirid.VO;

import java.util.List;

public class RelatorioAceitacaoVO {

	private String numeroAceitacao;
	private String numeroProposta;
	private String segurado;
	private String localRisco;
	private String cpf;
	private String atividadePrincipal;
	private String inicioVig;
	private String fimVig;
	private String is;
	private String premioLiq;
	private String premioNet;
	private String premioRetido;
	private String premioCedido;
	private String limiteSinistro;
	private String partResseg;
	private String partCaixa;
	private List<String> cobertura;
	private String parecerTecnico;
	private String dataSaida;
	private String dataEntrega;
	private int statuscheck;

	public String getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(String dataSaida) {
		this.dataSaida = dataSaida;
	}

	public String getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(String dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public int getStatuscheck() {
		return statuscheck;
	}

	public void setStatuscheck(int statuscheck) {
		this.statuscheck = statuscheck;
	}

	public String getAtividadePrincipal() {
		return atividadePrincipal;
	}

	public void setAtividadePrincipal(String atividadePrincipal) {
		this.atividadePrincipal = atividadePrincipal;
	}

	public String getNumeroAceitacao() {
		return numeroAceitacao;
	}

	public void setNumeroAceitacao(String numeroAceitacao) {
		this.numeroAceitacao = numeroAceitacao;
	}

	public String getNumeroProposta() {
		return numeroProposta;
	}

	public void setNumeroProposta(String numeroProposta) {
		this.numeroProposta = numeroProposta;
	}

	public String getSegurado() {
		return segurado;
	}

	public void setSegurado(String segurado) {
		this.segurado = segurado;
	}

	public String getLocalRisco() {
		return localRisco;
	}

	public void setLocalRisco(String localRisco) {
		this.localRisco = localRisco;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getInicioVig() {
		return inicioVig;
	}

	public void setInicioVig(String inicioVig) {
		this.inicioVig = inicioVig;
	}

	public String getFimVig() {
		return fimVig;
	}

	public void setFimVig(String fimVig) {
		this.fimVig = fimVig;
	}

	public String getIs() {
		return is;
	}

	public void setIs(String is) {
		this.is = is;
	}

	public String getPremioLiq() {
		return premioLiq;
	}

	public void setPremioLiq(String premioLiq) {
		this.premioLiq = premioLiq;
	}

	public String getPremioNet() {
		return premioNet;
	}

	public void setPremioNet(String premioNet) {
		this.premioNet = premioNet;
	}

	public String getPremioRetido() {
		return premioRetido;
	}

	public void setPremioRetido(String premioRetido) {
		this.premioRetido = premioRetido;
	}

	public String getPremioCedido() {
		return premioCedido;
	}

	public void setPremioCedido(String premioCedido) {
		this.premioCedido = premioCedido;
	}

	public String getLimiteSinistro() {
		return limiteSinistro;
	}

	public void setLimiteSinistro(String limiteSinistro) {
		this.limiteSinistro = limiteSinistro;
	}

	public String getPartResseg() {
		return partResseg;
	}

	public void setPartResseg(String partResseg) {
		this.partResseg = partResseg;
	}

	public String getPartCaixa() {
		return partCaixa;
	}

	public void setPartCaixa(String partCaixa) {
		this.partCaixa = partCaixa;
	}

	public List<String> getCobertura() {
		return cobertura;
	}

	public void setCobertura(List<String> cobertura) {
		this.cobertura = cobertura;
	}

	public String getParecerTecnico() {
		return parecerTecnico;
	}

	public void setParecerTecnico(String parecerTecnico) {
		this.parecerTecnico = parecerTecnico;
	}

}
