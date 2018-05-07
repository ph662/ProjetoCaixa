package caixa.dirid.VO;

/**
 * Classe Bean utilizada no sistema de apostas do campeonato da Euro copa para o
 * Celso Nagata.
 * 
 * */
public class CopaPartidasVO {

	int idCampeonato;
	String time1;
	String time2;
	String data;

	public int getIdCampeonato() {
		return idCampeonato;
	}

	public void setIdCampeonato(int idCampeonato) {
		this.idCampeonato = idCampeonato;
	}

	public String getTime1() {
		return time1;
	}

	public void setTime1(String time1) {
		this.time1 = time1;
	}

	public String getTime2() {
		return time2;
	}

	public void setTime2(String time2) {
		this.time2 = time2;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
