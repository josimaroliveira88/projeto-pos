package classes.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.postgresql.util.PSQLException;

import classes.EquipeManutencao;
import classes.Especialidade;
import classes.Material;
import classes.conexao.Conexao;

public class EspecialidadeDao {
	Conexao conexao;

	public boolean inserirEspecialidade(Especialidade especialidade) throws PSQLException {
		// Insere as informações no banco de dados
		conexao = new Conexao();
		boolean retorno = false;
		String sql;
		sql = "INSERT INTO especialidade(descricao, \"dataCadastro\")VALUES ("
				+ conexao.prepararString(especialidade.getDescricao().toUpperCase()) + ", "
				+ conexao.prepararString(especialidade.getDataCadastro())
				+ ");";
		System.out.println(sql);
		retorno = conexao.executarInsercao(sql);

		try {
			if(retorno==true)						//Caso contrário faz o rollback
				conexao.confirmacao(retorno);	
			else
				conexao.confirmacao(retorno);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return retorno;
	}

	public int atualizarEspecialidade(Especialidade especialidade) {
		int retorno = 0;
		String sql = "UPDATE especialidade SET descricao=" +
				conexao.prepararString(especialidade.getDescricao().toUpperCase())+", \"dataCadastro\"=" +
				conexao.prepararString(especialidade.getDataCadastro())+
				" WHERE \"idEspecialidade\"=" + especialidade.getIdEspecialidade();
		retorno = conexao.executarAtualizacao(sql);

		try {
			if (retorno > 0)
				conexao.confirmacao(true);
			else {
				conexao.confirmacao(false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retorno;
	}
	
	public int excluirEspecialidade(Especialidade especialidade) {
		int retorno = 0;
		String sql = "select * from equipe_especialidade where \"idEspecialidade\"="+especialidade.getIdEspecialidade();
		ResultSet res = conexao.executarSQL(sql);
		try {
			if(res.next()){
				retorno = 0;
			}else{
				sql = "DELETE FROM especialidade es "
						+ "WHERE  es.\"idEspecialidade\" = " + especialidade.getIdEspecialidade();
				retorno = conexao.executarAtualizacao(sql);
			}

			if (retorno > 0)
				conexao.confirmacao(true);
			else
				conexao.confirmacao(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retorno;
	}

	public ArrayList<HashMap<String, String>> buscaEspecialidade() {
		ArrayList<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "SELECT \"idEspecialidade\" as codigo, " +
					"descricao, " +
					"to_char(\"dataCadastro\", 'dd/MM/yyyy') as data_cadastro " +
					"FROM especialidade " +
					"order by 1";
			System.out.println(sql);
			ResultSet res = conexao.executarSQL(sql);
			while (res.next()) {
				HashMap<String, String> hash = new HashMap<String, String>();
				hash.put("codigo", res.getString("codigo"));
				hash.put("descricao", res.getString("descricao"));
				hash.put("data_cadastro", res.getString("data_cadastro"));
				lista.add(hash);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
}
