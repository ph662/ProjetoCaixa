package caixa.dirid.VO;

public class FaturamentoVO {
	private int id;
	private String produto;
	private String ano;
	private String meses[];

	public String[] getMeses() {
		return meses;
	}

	public void setMeses(String meses[]) {
		this.meses = meses;
	}

	public int getId() {
		return id;
	}

	public void setId(int i) {
		this.id = i;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}
}
