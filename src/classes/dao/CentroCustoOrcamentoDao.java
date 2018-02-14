package classes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.postgresql.util.PSQLException;

import classes.CentroCustoOrcamentoMes;
import classes.conexao.Conexao;

public class CentroCustoOrcamentoDao {
	Conexao conexao;
	Connection con;// = conexao.retornaConexao();

	PreparedStatement pst;
	public boolean inserirOrcamento(CentroCustoOrcamentoMes orcamentoMes, ArrayList<CentroCustoOrcamentoMes> arrayMes) throws PSQLException{
		//Insere as informações no banco de dados
		conexao = new Conexao();
		con = conexao.retornaConexao();
		boolean retorno = false;
		String sql;
		//Primeiro verificamos se para o ano e o centro de custo selecionados já existe um cadastro
		//Caso exista a função irá adicionar o valor do id no objeto
		//Se o valor for igual a 0, significa que não existe e portanto podemos efetuar o insert
		CentroCustoOrcamentoMes teste = verificaOrcamentoAno(orcamentoMes);
		if(teste.getIdOrcamento()==0){
			try {//Faz a inserção dos valores na tabela do orçamento anual
				sql =	"INSERT INTO centro_custo_orcamento(\"idCentro\", ano, \"idFuncionario\") VALUES (?, ?, ?)";		
				pst = con.prepareStatement(sql);
				pst.setInt(1, orcamentoMes.getIdCentro());//Adicionando os valores no preparedStatment
				pst.setInt(2, orcamentoMes.getAno());
				pst.setInt(3, orcamentoMes.getIdFuncionario());

				retorno = conexao.novaInsercao(pst);//executa o insert - retornará true caso tenha inserido com sucesso
				if(retorno == true){				//Se for true então efetua o select
					//Aqui será feito um select do último id do orçamento feito pelo 
					//usuário que está logado na sessão e pelo ano que foi salvo no objeto orcamentoMes
					sql = 	"select max(\"idOrcamento\") as codigo from centro_custo_orcamento where \"idFuncionario\"=" +
							orcamentoMes.getIdFuncionario()+" and ano="+orcamentoMes.getAno();
					ResultSet res = conexao.executarSQL(sql);
					while (res.next()){
						orcamentoMes.setIdOrcamento(res.getInt("codigo"));	//O resultado sera adicionado no atributo idOrcamento 
					}														//do objeto orcamentoMes
				}else
					retorno = false;
			} catch (SQLException e1) {
				e1.printStackTrace();//Caso ocorra uma Exception retorna false
				return false;
			}
		}
		System.out.println("Valor do idOrcamento: "+orcamentoMes.getIdOrcamento());
		if (orcamentoMes.getIdOrcamento() > 0){//Se o id do orçamento for maior que 0 então faz a inserção do restante das informações
			try {
				if(verificaOrcamentoMes(orcamentoMes)==true){
					for(int i=0;i<arrayMes.size();i++){
						System.out.println("Valor do codigo do mês: "+arrayMes.get(i).getIdMes()+" "+arrayMes.get(i).getValor());
						sql =	"INSERT INTO centro_custo_mes(\"idOrcamento\", \"idMes\", valor, \"idCategoria\", \"idFuncionario\") VALUES (?, ?, ?, ?, ?)";
						pst = con.prepareStatement(sql);
						pst.setInt(1, orcamentoMes.getIdOrcamento());
						pst.setInt(2, arrayMes.get(i).getIdMes());
						pst.setDouble(3, arrayMes.get(i).getValor());
						pst.setInt(4, orcamentoMes.getIdCategoria());
						pst.setInt(5, orcamentoMes.getIdFuncionario());
						retorno = conexao.novaInsercao(pst);
						if(retorno==true){					//Caso o retorno da inserção seja true então chama a função para efetuar o commit
							conexao.confirmacao(retorno);
						}else{
							conexao.confirmacao(retorno);	//Caso contrário chama a função para efetuar o rollback
						}
					}
				}else{
					retorno = false;
					conexao.confirmacao(retorno);
				}

			} catch (SQLException e1) {
				e1.printStackTrace();//Caso ocorra uma Exception retorna false 
				return false;
			}
		}else{//se o id do orçamento não for maior do que 0 então chama a função para efetuar o rollback
			try {
				conexao.confirmacao(false);
			} catch (SQLException e) {
				e.printStackTrace();//Caso ocorra uma Exception retorna false
				return false;
			}
		}
		return retorno;
	}

	//##########################################################################################################################
	//###################################### FIM DA FUNÇÃO PARA INSERÇÃO ############### #######################################
	//##########################################################################################################################


	//##########################################################################################################################
	//###################################### INÍCIO DA FUNÇÃO PARA ALTERAÇÃO ############### ###################################
	//##########################################################################################################################

	public boolean alterarOrcamento(CentroCustoOrcamentoMes orcamentoMes, ArrayList<CentroCustoOrcamentoMes> arrayMes){
		//Insere as informações no banco de dados
		boolean retorno = false;
		String sql;
		//A princípio vai somente pegar os valores do mês e fazer a atualização
		if (orcamentoMes.getIdOrcamento() > 0){//Se o id do orçamento for maior que 0 então faz a inserção do restante das informações
			try {
				for(int i=0;i<arrayMes.size();i++){
					System.out.println("Valor do codigo do mês: "+arrayMes.get(i).getIdMes()+" "+arrayMes.get(i).getValor());
					
					sql = "UPDATE centro_custo_mes SET \"idFuncionario\"=?, valor=? WHERE \"idOrcamentoMes\"=?";
					pst = con.prepareStatement(sql);
					pst.setInt(1, orcamentoMes.getIdFuncionario());
					pst.setDouble(2, arrayMes.get(i).getValor());
					pst.setInt(3, arrayMes.get(i).getIdOrcamentoMes());
					retorno = conexao.novaInsercao(pst);
					System.out.println(con.nativeSQL(sql));
					if(retorno==true){					//Caso o retorno da inserção seja true então chama a função para efetuar o commit
						conexao.confirmacao(retorno);
					}else{
						conexao.confirmacao(retorno);	//Caso contrário chama a função para efetuar o rollback
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();//Caso ocorra uma Exception retorna false 
				return false;
			}
		}else{//se o id do orçamento não for maior do que 0 então chama a função para efetuar o rollback
			try {
				conexao.confirmacao(false);
			} catch (SQLException e) {
				e.printStackTrace();//Caso ocorra uma Exception retorna false
				return false;
			}
		}
		return retorno;
	}

	//##########################################################################################################################
	//###################################### FIM DA FUNÇÃO PARA ALTERAÇÃO ######################################################
	//##########################################################################################################################


	public CentroCustoOrcamentoMes verificaOrcamentoAno(CentroCustoOrcamentoMes orcamentoMes){
		try {
			//Select para verificar se já existe um orçamento cadastrado com o centro de custo e ano informado
			String sql = 	"select \"idOrcamento\" as codigo from centro_custo_orcamento where" +
					"\"idCentro\"="+orcamentoMes.getIdCentro()+
					" and ano="+orcamentoMes.getAno();
			ResultSet res = conexao.executarSQL(sql);
			while(res.next()){
				orcamentoMes.setIdOrcamento(res.getInt("codigo"));	//O resultado sera adicionado no atributo idOrcamento
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orcamentoMes;
	}

	public boolean verificaOrcamentoMes(CentroCustoOrcamentoMes orcamentoMes){
		//Fazer um select para verificar se já existe orçamento cadastrado para a categoria no idOrçamento informado
		boolean retorno=false;
		try {
			String sql = "select \"idOrcamentoMes\" from centro_custo_mes where " +
					"\"idCategoria\"=" +orcamentoMes.getIdCategoria()+
					" and \"idOrcamento\"="+orcamentoMes.getIdOrcamento()+";";
			ResultSet res =  conexao.executarSQL(sql);
			res.last();
			if(res.getRow()>0)
				retorno = false;
			else
				retorno = true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return retorno;
	}



	//##########################################################################################################################
	//###################################### FUNÇÃO PARA PREENCHER COMBOBOX CENTRO CUSTO #######################################
	//##########################################################################################################################

	public ArrayList<HashMap<String, String>> buscaCentroCusto(){
		ArrayList<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "SELECT \"idCentro\" as codigo, descricao, \"centroCusto\" as centro_custo FROM centro_custo order by centro_custo";
			ResultSet res = conexao.executarSQL(sql);

			while (res.next()) {
				HashMap<String, String> listaCentro = new HashMap<String, String>();
				listaCentro.put("codigo", res.getString("codigo"));
				listaCentro.put("descricao", res.getString("descricao"));
				listaCentro.put("centro_custo", res.getString("centro_custo"));
				lista.add(listaCentro);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}


	//##########################################################################################################################
	//###################################### FUNÇÃO PARA PREENCHER COMBOBOX CATEGORIA ##########################################
	//##########################################################################################################################

	public ArrayList<HashMap<String, String>> buscaCategoria(){
		ArrayList<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "SELECT \"idCategoria\" as codigo, descricao FROM centro_custo_categoria order by descricao";
			ResultSet res = conexao.executarSQL(sql);

			while (res.next()) {
				HashMap<String, String> listaCategoria = new HashMap<String, String>();
				listaCategoria.put("codigo", res.getString("codigo"));
				listaCategoria.put("descricao", res.getString("descricao"));
				lista.add(listaCategoria);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}


	//##########################################################################################################################
	//###################################### FUNÇÃO PARA PREENCHER COMBOBOX MES ################################################
	//##########################################################################################################################

	public ArrayList<HashMap<String, String>> buscaMes(){
		ArrayList<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "SELECT \"idMes\" as codigo, descricao FROM mes order by codigo";
			ResultSet res = conexao.executarSQL(sql);

			while (res.next()) {
				HashMap<String, String> listaMes = new HashMap<String, String>();
				listaMes.put("codigo", res.getString("codigo"));
				listaMes.put("descricao", res.getString("descricao"));
				lista.add(listaMes);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}


	//##########################################################################################################################
	//###################################### FUNÇÃO PARA PREENCHER DATAGRID ORCAMENTO###########################################
	//##########################################################################################################################

	public ArrayList<HashMap<String, String>> buscaOrcamento(int ano){
		ArrayList<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select ccc.\"idCategoria\" as id_categoria, ccc.descricao, sum(ccm.valor) as valor from centro_custo_mes ccm"+
					" inner join centro_custo_categoria ccc"+
					" on ccc.\"idCategoria\"=ccm.\"idCategoria\""+
					" inner join centro_custo_orcamento cco"+
					" on cco.\"idOrcamento\"=ccm.\"idOrcamento\""+
					" where cco.ano="+ano+
					" group by ccc.descricao, ccc.\"idCategoria\" order by ccc.\"idCategoria\"";

			String sql2 = "select ccc.\"idCategoria\" as id_anterior, ccc.descricao, sum(ccm.valor) as valor_anterior from centro_custo_mes ccm"+
					" inner join centro_custo_categoria ccc"+
					" on ccc.\"idCategoria\"=ccm.\"idCategoria\""+
					" inner join centro_custo_orcamento cco"+
					" on cco.\"idOrcamento\"=ccm.\"idOrcamento\""+
					" where cco.ano="+(ano-1)+
					" group by ccc.\"idCategoria\", ccc.descricao order by ccc.\"idCategoria\"";

			//Crio um novo statement para poder executar duas consultas, não é possível ter 2 ResultSet abertos no mesmo statement
			Statement st = con.createStatement();
			ResultSet res = conexao.executarSQL(sql);

			//Não foi usada a classe conexão apenas para poder usar 2 ResultSet 
			ResultSet res2 = st.executeQuery(sql2);

			while (res.next()) {
				HashMap<String, String> listaOrc = new HashMap<String, String>();
				//Se o res2 tiver algum resultado então faz a checagem se tem algum id da categoria igual.
				//Se tiver então seta o valor do ano anteior para a categoria
				//Caso contrário seta o valor para 0.00
				if(res2.next()){
					if(res.getString("id_categoria").equals(res2.getString("id_anterior"))){
						listaOrc.put("id_categoria", res.getString("id_categoria"));
						listaOrc.put("descricao", res.getString("descricao"));
						listaOrc.put("valor", res.getString("valor"));
						listaOrc.put("valor_anterior", res2.getString("valor_anterior"));
					}else{
						listaOrc.put("id_categoria", res.getString("id_categoria"));
						listaOrc.put("descricao", res.getString("descricao"));
						listaOrc.put("valor", res.getString("valor"));
						listaOrc.put("valor_anterior", "0.00");
					}

					//Entra nesse else se o res2 não tiver valores e seta o valor do ano anterior para 0.00
				}else{
					listaOrc.put("id_categoria", res.getString("id_categoria"));
					listaOrc.put("descricao", res.getString("descricao"));
					listaOrc.put("valor", res.getString("valor"));
					listaOrc.put("valor_anterior", "0.00");
				}
				lista.add(listaOrc);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}


	//##########################################################################################################################
	//###################################### FUNÇÃO PARA CAPTURAR OS VALORES DOS MESES #########################################
	//##########################################################################################################################

	public ArrayList<HashMap<String, String>> buscaOrcadoMes(CentroCustoOrcamentoMes orcamentoMes){
		ArrayList<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();

		try {
			String sql = "SELECT cm.\"idOrcamento\" AS codigo_geral, "
					+ "       cm.\"idMes\"       AS mes, "
					+ "       cm.\"idOrcamentoMes\" AS codigo_mes, "
					+ "       cm.valor "
					+ "FROM   centro_custo_mes cm "
					+ "       INNER JOIN centro_custo_orcamento co "
					+ "               ON cm.\"idOrcamento\" = co.\"idOrcamento\" "
					+ "WHERE  \"idCategoria\" = "+orcamentoMes.getIdCategoria()
					+ "       AND co.ano = "+orcamentoMes.getAno()
					+ "       AND co.\"idCentro\" = "+orcamentoMes.getIdCentro()+" order by cm.\"idOrcamentoMes\"";
			ResultSet res = conexao.executarSQL(sql);
			while (res.next()) {
				HashMap<String, String> listaOrc = new HashMap<String, String>();
				listaOrc.put("codigo_geral", res.getString("codigo_geral"));
				listaOrc.put("codigo_mes", res.getString("codigo_mes"));
				listaOrc.put("mes", res.getString("mes"));
				listaOrc.put("valor", res.getString("valor"));
				lista.add(listaOrc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}


	//##########################################################################################################################
	//###################################### FUNÇÃO PARA CAPTURAR O ORÇADO DO ANO ANTERIOR #####################################
	//##########################################################################################################################

	public double buscaAnoAnterior(int ano){
		double orcado = 0.00;
		try {
			String sql = "select cco.ano, sum(ccm.valor) as valor from centro_custo_mes ccm "+
					"inner join centro_custo_orcamento cco "+
					"on cco.\"idOrcamento\"=ccm.\"idOrcamento\"" +
					"where cco.ano="+ano+" group by cco.ano;";
			ResultSet res = conexao.executarSQL(sql);

			while (res.next()) {
				orcado = res.getDouble("valor");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return orcado;
	}
}
