package caixa.dirid.VO;

public class MovimentoPorCanalDeVendaVO {

	String movimento;
	String canalDeVenda;
	String produto;
	String tipo;
	String ano;
	String[] meses;

	public String getMovimento() {
		return movimento;
	}

	public void setMovimento(String movimento) {
		this.movimento = movimento;
	}

	public String getCanalDeVenda() {
		return canalDeVenda;
	}

	public void setCanalDeVenda(String canalDeVenda) {
		this.canalDeVenda = canalDeVenda;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String[] getMeses() {
		return meses;
	}

	public void setMeses(String[] meses) {
		this.meses = meses;
	}

}
