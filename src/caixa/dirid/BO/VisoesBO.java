package caixa.dirid.BO;

import java.util.Enumeration;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import caixa.dirid.DAO.VisaoExecutivaDAO;
import caixa.dirid.VO.FatuMensalVO;
import caixa.dirid.VO.RvneVO;


public class VisoesBO {

	/******************** COMPARA*ADMIN *********************/
	public boolean comparaAdmin(String CPF) {
		if (CPF.equals("147"))
			return true;
		else
			return false;
	}

	

	
	/**
	 * Recebe a request da página e verifica qual "name" e "value" do campo que
	 * vem da form da página. Cria um objeto e envia para o DAO fazer um UPDATE.
	 * 
	 */
	public void validaAtualizaRVNE(HttpServletRequest req) {

		RvneVO rvne = new RvneVO();
		VisaoExecutivaDAO dao = new VisaoExecutivaDAO();

		// Obtem o mes e o idProduto
		for (Enumeration<String> e = req.getParameterNames(); e
				.hasMoreElements();) {
			String[] partes = e.nextElement().split("_");

			String mes = partes[0];
			rvne.setMes(mes);

			String idProduto = partes[1];
			rvne.setId(Integer.parseInt(idProduto));

			// System.out.println("Mes: " + mes + " IdProduto: " + idProduto);
			// for (String string : partes) {
			// System.out.println(string);
			// }
		}

		for (Enumeration<String> e = req.getParameterNames(); e
				.hasMoreElements();) {

			try {
				rvne.setValor(req.getParameter((String) e.nextElement()));
				// System.out.println("FOR 2 - "
				// + req.getParameter((String) e.nextElement()));
			} catch (NoSuchElementException nsee) {
				nsee.printStackTrace();
			}
		}

		dao.atualizaRVNE(rvne);

	}

	/**
	 * Recebe a request da página cria um objeto e envia para o DAO fazer um
	 * UPDATE.
	 * 
	 */
	public void validaApagaRVNE(HttpServletRequest req) {
		RvneVO rvne = new RvneVO();
		VisaoExecutivaDAO dao = new VisaoExecutivaDAO();

		rvne.setId(Integer.parseInt(req.getParameter("id")));
		rvne.setMes(req.getParameter("coluna"));

		dao.apagaRVNE(rvne);
	}

	/**
	 * Recebe a request da página cria um objeto e envia para o DAO fazer um
	 * SELECT trazendo apenas um valor.
	 * 
	 */
	public FatuMensalVO validaBuscaRVNE(HttpServletRequest req) {
		RvneVO rvne = new RvneVO();
		VisaoExecutivaDAO dao = new VisaoExecutivaDAO();

		rvne.setId(Integer.parseInt(req.getParameter("id")));
		rvne.setMes(req.getParameter("coluna"));

		return dao.buscaUnicaRVNE(rvne);
	}

	
	
	
	
	
	
	
	
	
}