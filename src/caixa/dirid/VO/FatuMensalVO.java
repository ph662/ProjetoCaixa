package caixa.dirid.VO;

public class FatuMensalVO {
	private String anoMes;
	private String produto;
	private String tipo;
	private String faturamentoSemRVNE;
	private String faturamentoTotal;
	private String RVNE;
	private String businessPlan;

	public String getFaturamentoTotal() {
		return faturamentoTotal;
	}

	public void setFaturamentoTotal(String faturamentoTotal) {
		this.faturamentoTotal = faturamentoTotal;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getAnoMes() {
		return anoMes;
	}

	public void setAnoMes(String anoMes) {
		this.anoMes = anoMes;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public String getFaturamentoSemRVNE() {
		return faturamentoSemRVNE;
	}

	public void setFaturamentoSemRVNE(String faturamentoSemRVNE) {
		this.faturamentoSemRVNE = faturamentoSemRVNE;
	}

	public String getRVNE() {
		return RVNE;
	}

	public void setRVNE(String rVNE) {
		RVNE = rVNE;
	}

	public String getBusinessPlan() {
		return businessPlan;
	}

	public void setBusinessPlan(String businessPlan) {
		this.businessPlan = businessPlan;
	}

}
