package caixa.dirid.VO;

import java.util.Comparator;

public class DadosDiariosVO {

	private String anoMesDia;
	private String produto;
	private String tipo;
	private String valorDoDia;

	public String getAnoMesDia() {
		return anoMesDia;
	}

	public void setAnoMesDia(String anoMesDia) {
		this.anoMesDia = anoMesDia;
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

	public String getValorDoDia() {
		return valorDoDia;
	}

	public void setValorDoDia(String valorDoDia) {
		this.valorDoDia = valorDoDia;
	}

	public static Comparator<DadosDiariosVO> anoMesDiaCoparator = new Comparator<DadosDiariosVO>() {
		@Override
		public int compare(DadosDiariosVO d1, DadosDiariosVO d2) {
			String data1 = d1.getAnoMesDia();
			String data2 = d2.getAnoMesDia();

			return data1.compareTo(data2);
		}
	};

}
