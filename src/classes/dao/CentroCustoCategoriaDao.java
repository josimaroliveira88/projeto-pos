package classes.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.postgresql.util.PSQLException;

import classes.CentroCustoCategoria;
import classes.conexao.Conexao;

public class CentroCustoCategoriaDao {
	Conexao conexao;// = new Conexao();
	
	public boolean inserirCategoria(CentroCustoCategoria categoria) throws PSQLException {
		// Insere as informações no banco de dados
		conexao = new Conexao();
		boolean retorno = false;
		String sql;
		sql = "INSERT INTO centro_custo_categoria(descricao, \"dataCadastro\") VALUES ("
				+ conexao.prepararString(categoria.getDescricao())
				+ ", "
				+ conexao.prepararString(categoria.getDataCadastro()) + ");";
		System.out.println(sql);
		retorno = conexao.executarInsercao(sql);
		try {
			if (retorno == true) {
				conexao.confirmacao(retorno);
			} else {
				conexao.confirmacao(retorno);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retorno;
	}
	
	public int atualizarCategoria(CentroCustoCategoria categoria){
		int retorno = 0;
		String sql = 			"UPDATE centro_custo_categoria SET "+
								"descricao="+conexao.prepararString(categoria.getDescricao())+", "+
								"\"dataCadastro\"="+conexao.prepararString(categoria.getDataCadastro())+
								" WHERE \"idCategoria\"="+categoria.getIdCategoria()+";";
		retorno = conexao.executarAtualizacao(sql);
		
		try {
			if (retorno > 0) {
				conexao.confirmacao(true);
			} else {
				conexao.confirmacao(false);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return retorno;
	}
	
	public int excluirCategoria(CentroCustoCategoria categoria) {
		int retorno = 0;
		try {

			String sql = "select * from centro_custo_orcamento where \"idCategoria\"="
					+ categoria.getIdCategoria();
			ResultSet res = conexao.executarSQL(sql); 	// Primeiro verifica se existe algum orçamento vinculado a categoria a ser excluída
			if (res.getFetchSize() == 0) { 				// Caso exista a categoria não será excluída
				sql = "DELETE FROM centro_custo_categoria "
						+ "WHERE \"idCategoria\" = "
						+ categoria.getIdCategoria();
				retorno = conexao.executarAtualizacao(sql);

				if (retorno > 0) {
					conexao.confirmacao(true);
				} else {
					conexao.confirmacao(false);
				}
				return retorno;
			} else
				return retorno;
		} catch (SQLException e) {
			e.printStackTrace();
			return retorno;
		}
	}
	
	public ArrayList<HashMap<String, String>> buscaCategoria(){
		ArrayList<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		try {
			String sql = 	"SELECT c.\"idCategoria\" as codigo, "+
							"c.descricao, "+
							"to_char(c.\"dataCadastro\",'dd/mm/yyyy') as data_cadastro "+
							"FROM centro_custo_categoria c "+
							"order by 1;";
			ResultSet res = conexao.executarSQL(sql);
			while (res.next()) {
				HashMap<String, String> hmCategoria = new HashMap<String, String>();
				hmCategoria.put("codigo", res.getString("codigo"));
				hmCategoria.put("descricao", res.getString("descricao"));
				hmCategoria.put("data_cadastro", res.getString("data_cadastro"));
				lista.add(hmCategoria);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
}
