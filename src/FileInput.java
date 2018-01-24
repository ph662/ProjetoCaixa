import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import caixa.dirid.CONEXAO.Conexao;

/**
 * Desenvolvido por Phelipe Lima - phelipe662@gmail.com
 * 
 * Este programa serve para inserir dados vindos de um arquivo '.csv' usando o
 * delimitador ';'. Ele abre uma conexão ao banco local MySql, cria os INSERTS
 * em um bloco de transaction e os insere de uma so vez
 * 
 */
public class FileInput {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

		/********************************************************************
		 * Neste trecho é feita a declaração do local do arquivo, os arquivos comentados
		 * são arquivos que já foram usados.
		 */

		String dat[] = { "180131" };

		for (int j = 0; j < dat.length; j++) {

			String anoCompletoMes = dat[j];
			// File file = new File("D:\\renovacao/3/D" + anoCompletoMes +
			// "_RelAcompRenovacao.csv");
			File file = new File("\\\\ntservercpd\\GERID\\D" + anoCompletoMes + "_RelAcompRenovacao.csv");
			// File file = new File("D:\\renovacao/D170228_RelAcompRenovacao.csv");

			// File file = new File("D:\\Relatorio Sensibilizacao/relatorio.csv");//
			// File file = new File("D:\\extracao201512_RO_RG12.rpt");
			// File file = new File("D:\\extracao201604_RO_RG12.rpt");
			// File file = new File("D:\\extracao2011_RO_RG.rpt");
			// File file = new File("D:\\extracao2012_RO_RG.rpt");
			// File file = new File("D:\\extracao2013_RO_RG.rpt");
			// File file = new File("D:\\extracao2010_RO_RG.rpt");
			// File file = new File("D:\\extracao201601_RO_RG12.rpt");
			// File file = new File("D:\\EF_HABIT0000_PREMIOS_ES006204_022015.txt");
			// File file = new File("D:\\extracao2014_2015_RO_RG12.rpt");
			// File file = new File("D:\\M1503.RG0012B.txt");
			// File file = new File("D:\\BP 2015 - DIRID.csv");
			// File file = new File("D:\\M1504.RG0012B.txt");
			// File file = new File("D:\\ROEMTLDR.D150430.csv");
			// File file = new File("D:\\Vigentes Dez2015.csv");

			// String dat = "151105";
			// File file = new
			// File("D:\\Relatorio Sensibilizacao/movimentacaoEmissao"
			// + dat + ".csv");

			// File file = new
			// File("D:\\Relatorio Sensibilizacao/movimentacaoEmissao151116.csv");

			// File file = new File("D:\\FILIAIS_SR_SUAT_ABR2017.csv");

			/* ******************************************************************* */

			FileInputStream fis = null;
			BufferedInputStream bis = null;
			DataInputStream dis = null;

			try {
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				dis = new DataInputStream(bis);

				Connection con = new Conexao().getConexaoMySql("FileInput"); // Abre

				System.out.println("Inserindo linhas...");

				// String data = dat;
				// String anoCut = data.substring(0, 2);
				// String mesCut = data.substring(2, 4);
				// String diaCut = data.substring(4, 6);
				con.setAutoCommit(false);
				while (dis.available() != 0) { // Enquanto não for o fim do arquivo

					/*
					 * A Linha atual é cortada utilizando o delimitador e atribuida a um array
					 * String
					 */
					String[] part = dis.readLine().split(";");
					/********************************************************************
					 * Neste trecho é feita a declaração de como será o INSERT, os arquivos
					 * comentados são arquivos que já foram usados. A quantidade de colunas
					 * declaradas aqui deve ser a mesma do 'for' mais abaixo.
					 */

					// /*Arquivo "M1504.RG0012B.txt" 41 Colunas*/
					// String sql =
					// "INSERT INTO SistemaDirid.MesAbril_RG0012B
					// (ORG,RAMO,PROD,MOVIMENTO,DIA,FONTE,APOLICE,ENDOSSO,END_MOVTO,PARC,INIC_VIG,TERM_VIG,SEGURADO,CNPJ_CPF,TIPO_INDEX,DT_VENCTO,IMP_SEGURADA_LIDER,IMP_SEG_COSSEG_CEDIDO,IMP_SEG_RESSEG_CEDIDO,PREM_TARIFARIO_LIDER,PRM_TARIF_COSSEG_CED,PRM_TARIF_RESSEG_CED,VLR_DESCTO_LIDER,DESCTO_COSSG_CED,DESCTO_RESSG_CED,PREMIO_LIQUIDO_LIDER,PREM_LIQD_COSSEG_CED,PREM_LIQD_RESSEG_CED,VLR_ADIC_LIDER,ADIC_COSSG_CED,ADIC_RESSG_CED,VLR_CUSTO_EMISS,VALOR_DO_IOF,VALOR_PREMIO_TOTAL,VLR_COMIS_CORRT,VLR_COMIS_ADMN,VLR_COMIS_AGENC,VLR_COMIS_COSSG,VLR_COMIS_RESSG,PONTO_DE_VENDA)
					// VALUES
					// (?,?,?,?,?,?,?,?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
					// /**************************/

					// /*Arquivo "M1404.RG0012B.txt" 41 Colunas*/
					// String sql =
					// "INSERT INTO SistemaDirid.M1404_RG0012B
					// (ORG,RAMO,PROD,MOVIMENTO,DIA,FONTE,APOLICE,ENDOSSO,END_MOVTO,PARC,INIC_VIG,TERM_VIG,SEGURADO,CNPJ_CPF,TIPO_INDEX,DT_VENCTO,IMP_SEGURADA_LIDER,IMP_SEG_COSSEG_CEDIDO,IMP_SEG_RESSEG_CEDIDO,PREM_TARIFARIO_LIDER,PRM_TARIF_COSSEG_CED,PRM_TARIF_RESSEG_CED,VLR_DESCTO_LIDER,DESCTO_COSSG_CED,DESCTO_RESSG_CED,PREMIO_LIQUIDO_LIDER,PREM_LIQD_COSSEG_CED,PREM_LIQD_RESSEG_CED,VLR_ADIC_LIDER,ADIC_COSSG_CED,ADIC_RESSG_CED,VLR_CUSTO_EMISS,VALOR_DO_IOF,VALOR_PREMIO_TOTAL,VLR_COMIS_CORRT,VLR_COMIS_ADMN,VLR_COMIS_AGENC,VLR_COMIS_COSSG,VLR_COMIS_RESSG,PONTO_DE_VENDA)
					// VALUES
					// (?,?,?,?,?,?,?,?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
					// /**************************/

					// /*Arquivo "ROEMTLDR.D150430.csv" 40 Colunas*/
					// String sql =
					// "INSERT INTO
					// SistemaDirid.MesAbril_ROEMTLDR(LIVRO,PAGINA,ORGAO,COD_RAMO,RAMO,MES_DE_COMPETENCIA,PRODUTO,UF,APOLICE,ENDOSSO,CERTIFICADO,PARCELA,MOEDA,DT_EMISSAO,DT_INICIO,DT_TERMINO,CPF_CNPJ,SEGURADO,COD_SUSEP,CORRETOR,LIDER_IMP_SEGURADA,COSS_IMP_SEGURADA,RESS_IMP_SEGURADA,LIDER_PREMIO_TARIF,COSS_PREMIO_TARIF,RESS_PREMIO_TARIF,LIDER_DESCONTO,COSS_DESCONTO,LIDER_ADICIONAL,COSS_ADICIONAL,LIDER_CUSTO,LIDER_IOF,LIDER_COMISSAO,COSS_COMISSAO,RESS_COMISSAO,LIDER_PREMIO_TOTAL,COSS_PREMIO_TOTAL,RESS_PREMIO_TOTAL,REEMISSÃO)
					// VALUES
					// (?,?,?,?,?,?,?,?,?,?,?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

					// /*Arquivo "BP 2015 - DIRID.csv" 15 Colunas*/
					// String sql =
					// "INSERT INTO sistemadirid.BP_2015_DIRID VALUES
					// (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

					/* Arquivo "EF_HABIT0000_PREMIOS_ES006204_022015.txt" 39 Colunas */
					// String sql =
					// "INSERT INTO sistemadirid.EF_HABIT0000_PREMIOS VALUES
					// (?,?,?,?,?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

					/* Arquivo "extracao2011_RO_RG.rpt" 39 Colunas */
					// String sql =
					// "INSERT INTO sistemadirid.RO_RG0012B VALUES (?,?,?,?,?,?,?,?,?,?,?,
					// STR_TO_DATE(?,'%Y-%m-%d'),
					// STR_TO_DATE(?,'%Y-%m-%d'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

					/* Arquivo "extracao201603_RO_RG12.rpt" 39 Colunas */
					// String sql =
					// "INSERT INTO sistemadirid.RO_RG0012B VALUES (?,?,?,?,?,?,?,?,?,?,?,
					// STR_TO_DATE(?,'%Y-%m-%d'),
					// STR_TO_DATE(?,'%Y-%m-%d'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

					/* Arquivo "D160131_RelAcompRenovacao.csv" 45 Colunas */
					// String sql =
					// "INSERT INTO sistemadirid.renovacao_acompanhamento
					// (Produto,Contrato,Proposta,Apólice,Ag_Venda,Canal,Renov_Automatica,Ini_Vig,Fim_Vig,Sit_Apolice,Plurianual,Emissão,Inicio_Vig_Endosso,Fim_Vig_Endosso,Sit_Endosso,Cancelada,Data_Cancelamento,CPF_CNPJ,Segurado,CEP_Risco,Premio_Apol,Renov_Contrato,Renov_Proposta,Renov_Apólice,Renov_Canal,Renov_Início,Renov_Fim,Renov_Premio_Total,Renov_Adesão,Renov_CEP_Risco,Renov_Cancelado,Dt_Cancelamento,Renov_Banco,Renov_Agência,Renov_Conta,Vig_Contrato,Vig_Proposta,Vig_Apólice,Vig_Canal,Vig_Início,Vig_Fim,Vig_Situação,Vig_CEP,Vig_Cancelado,Vig_Cancelamento)
					// "
					// +
					// "VALUES
					// (?,?,?,?,?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),?,?,STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,?,?,?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),?,STR_TO_DATE(?,'%d/%m/%Y'),?,?,STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,?,?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,?);";

					/* Arquivo "relatorio.csv" 3 Colunas */
					// String sql =
					// "insert into sistemadirid.agencia_vigentes
					// (NumeroDaProposta,NumeroDaAgenciaDeVenda,CodigoProduto) values(?,?,?);";

					/* Arquivo "D151130_RelAcompRenovacao.csv" 45 Colunas */
					String sql = "INSERT INTO sistemadirid.renovacao_acompanhamento (Produto,Contrato,Proposta,Apólice,Ag_Venda,Canal,Renov_Automatica,Ini_Vig,Fim_Vig,Sit_Apolice,Plurianual,Emissão,Inicio_Vig_Endosso,Fim_Vig_Endosso,Sit_Endosso,Cancelada,Data_Cancelamento,CPF_CNPJ,Segurado,CEP_Risco,Premio_Apol,Renov_Contrato,Renov_Proposta,Renov_Apólice,Renov_Canal,Renov_Início,Renov_Fim,Renov_Premio_Total,Renov_Adesão,Renov_CEP_Risco,Renov_Cancelado,Dt_Cancelamento,Renov_Banco,Renov_Agência,Renov_Conta,Vig_Contrato,Vig_Proposta,Vig_Apólice,Vig_Canal,Vig_Início,Vig_Fim,Vig_Situação,Vig_CEP,Vig_Cancelado,Vig_Cancelamento) "
							+ "VALUES (?,?,?,?,?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),?,?,STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,?,?,?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),?,STR_TO_DATE(?,'%d/%m/%Y'),?,?,STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,?,?,?,?,STR_TO_DATE(?,'%d/%m/%Y'),STR_TO_DATE(?,'%d/%m/%Y'),?,?,?,?);";

					/* Arquivo "Vigentes Dez2015.csv" 7 Colunas */
					// String sql =
					// "INSERT INTO
					// agencia_vigentes_apolices(DataArquivo,CodigoProduto,`NumeroApolice`,`AgenciaDeVenda`,`NumeroDaSuperintendenciaRegional`,`NumeroDaFilial`)
					// VALUES (STR_TO_DATE(?,'%d-%m-%Y'),?,?,?,?,?)";

					/* Arquivo "vendaSumarizado.csv" 3 Colunas */
					// String sql =
					// "insert into agencia_vigentes(DataArquivo,NumeroProposta, CodigoProduto,
					// AgenciaDeVenda) values (?,?,?,?)";

					// String sql =
					// "insert into
					// movimentacao_financeira(DataArquivo,NumeroProposta,CodigoSituacao,ValorParcela)
					// values (?,?,?,?);";
					// String sql =
					// "insert into movimentacao_emissao
					// (DataArquivo,NumeroProposta,AgenciaDeVenda,ValorPagoSICOB,SituacaoProposta,TipoMovimento,NumeroProdutoSIGPF)
					// values (STR_TO_DATE(?,'%d-%m-%Y'),?,?,?,?,?,?);";
					// String sqlAgenciaVigentes =
					// "insert into agencia_vigentes
					// (DataArquivo,NumeroProposta,AgenciaDeVenda,CodigoProduto) values
					// (STR_TO_DATE(?,'%d-%m-%Y'),?,?,?);";

					/* Arquivo "movimentacaoEmissao151105.csv" 6 Colunas */
					// String sql =
					// "insert into movimentacao_emissao values
					// (STR_TO_DATE(?,'%d-%m-%Y'),?,?,?,?,?);";

					/* Arquivo "movimentacaoFinanceira151105.csv" 5 Colunas */
					// String sql =
					// "insert into movimentacao_financeira
					// (DataArquivo,NumeroProposta,CodigoSituacao,ValorParcela) values
					// (STR_TO_DATE(?,'%d-%m-%Y'),?,?,?)";

					/* Arquivo "FILIAIS_SR_SUAT.csv" 16 Colunas */
					// String sql =
					// "INSERT INTO agencias_filiais_SR_SUAT VALUES
					// (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					// /********************************************************************/

					try {
						PreparedStatement ps = con.prepareStatement(sql);

						/*
						 * Troque o número que limita este 'for' de acordo com as colunas do INSERT
						 */

						/*
						 * for (int i = 1; i <= 16; i++) { if (i == 1) { ps.setInt(i,
						 * Integer.parseInt(part[i - 1].trim() .replaceAll("COD_AG", "9999"))); } else {
						 * ps.setString(i, part[i - 1].trim()); } }
						 */

						// extracao201604_RO_RG12

						for (int i = 1; i <= 45; i++) {

							ps.setString(i, part[i - 1].trim());

						}

						ps.execute();
						ps.close();
						ps = null;

					} catch (SQLException e) {
						e.printStackTrace();
					}
					part = null;
					sql = null;
				}
				con.commit();
				con.close();
				System.out.println("INSERT DO ARQUIVO FINALIZADO");

			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				try {
					fis.close();
					bis.close();
					dis.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}

		}
	}
}