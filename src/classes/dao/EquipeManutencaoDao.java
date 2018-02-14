package classes.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.postgresql.util.PSQLException;

import classes.EquipeManutencao;
import classes.LocalInstalacao;
import classes.conexao.Conexao;

public class EquipeManutencaoDao {
	Conexao conexao;

	public boolean inserirEquipe(EquipeManutencao equipeManutencao) throws PSQLException {
		// Insere as informações no banco de dados
		conexao = new Conexao();
		boolean retorno = false;
		String sql;
		sql = "INSERT INTO equipe_manutencao(descricao, \"alocacaoMaxima\", \"alocacaoMinima\", sigla, \"dataCadastro\", \"idFuncionario\") VALUES ("
				+ conexao.prepararString(equipeManutencao.getDescricao().toUpperCase()) + ", "
				+ equipeManutencao.getAlocacaoMaxima() + ", "
				+ equipeManutencao.getAlocacaoMinima() + ", "
				+ conexao.prepararString(equipeManutencao.getSigla().toUpperCase()) + ", "
				+ conexao.prepararString(equipeManutencao.getDataCadastro()) + ", "
				+ equipeManutencao.getIdFuncionario()
				+ ");";
		System.out.println(sql);
		retorno = conexao.executarInsercao(sql);

		try {
			if(retorno == true){
				sql = 	"select max(\"idEquipe\") as codigo from equipe_manutencao where \"idFuncionario\"=" +
						equipeManutencao.getIdFuncionario();
				ResultSet res = conexao.executarSQL(sql);
				while (res.next()){
					equipeManutencao.setIdEquipe(res.getInt("codigo"));	//O resultado sera adicionado no atributo idEquipe 
				}														//do objeto equipeManutencao
				if(equipeManutencao.getIdEquipe()>0)
					sql = "INSERT INTO equipe_especialidade(\"idEspecialidade\", \"idEquipe\") VALUES ("
							+ equipeManutencao.getIdEspecialidade() + ", "
							+ equipeManutencao.getIdEquipe()
							+ ");";
				System.out.println(sql);
				retorno = conexao.executarInsercao(sql);//Faz a inserção na tabela equipe_especialidade somente caso a anterior tenha sucesso
				if(retorno==true){						//Caso contrário faz o rollback
					conexao.confirmacao(retorno);	
				}
			}else
				conexao.confirmacao(retorno);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return retorno;
	}

	public int atualizarEquipe(EquipeManutencao equipeManutencao) {
		int retorno = 0;
		String sql = "UPDATE equipe_manutencao SET descricao=" +
				conexao.prepararString(equipeManutencao.getDescricao().toUpperCase())+", \"alocacaoMaxima\"=" +
				equipeManutencao.getAlocacaoMaxima()+", \"alocacaoMinima\"=" +
				equipeManutencao.getAlocacaoMinima()+", sigla=" +
				conexao.prepararString(equipeManutencao.getSigla().toUpperCase())+", \"dataCadastro\"=" +
				conexao.prepararString(equipeManutencao.getDataCadastro())+", \"idFuncionario\"=" +
				equipeManutencao.getIdFuncionario()+"" +
				" WHERE \"idEquipe\"=" + equipeManutencao.getIdEquipe()+";";
		retorno = conexao.executarAtualizacao(sql);

		try {
			if (retorno > 0) {
				sql = "UPDATE equipe_especialidade SET \"idEspecialidade\"=" +
						equipeManutencao.getIdEspecialidade()+
						" WHERE \"idEquipe\"=" + equipeManutencao.getIdEquipe()+";";
				retorno = conexao.executarAtualizacao(sql);
				if (retorno > 0) {
					conexao.confirmacao(true);	
				}
			} else {
				conexao.confirmacao(false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retorno;
	}

	public ArrayList<HashMap<String, String>> buscaEquipeManutencao() {
		ArrayList<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select em.\"idEquipe\" as codigo," +
					" em.descricao as desc_equipe," +
					" em.\"alocacaoMaxima\" as maxima," +
					" em.\"alocacaoMinima\" as minima," +
					" em.sigla," +
					" to_char(em.\"dataCadastro\", 'dd/MM/yyyy') as data_cadastro," +
					" e.\"descricao\" as desc_esp" +
					" from equipe_manutencao em" +
					" inner join equipe_especialidade ee" +
					" on em.\"idEquipe\"=ee.\"idEquipe\"" +
					" inner join especialidade e" +
					" on ee.\"idEspecialidade\"=e.\"idEspecialidade\"" +
					" order by 1 ";
			System.out.println(sql);
			ResultSet res = conexao.executarSQL(sql);
			while (res.next()) {
				HashMap<String, String> hash = new HashMap<String, String>();
				hash.put("codigo", res.getString("codigo"));
				hash.put("desc_equipe", res.getString("desc_equipe"));
				hash.put("maxima", res.getString("maxima"));
				hash.put("minima", res.getString("minima"));
				hash.put("sigla", res.getString("sigla"));
				hash.put("data_cadastro", res.getString("data_cadastro"));
				hash.put("desc_esp", res.getString("desc_esp"));
				lista.add(hash);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public EquipeManutencao buscaEquipeManutencao(int idEquipe){
		EquipeManutencao equipe = new EquipeManutencao();
		try {
			String sql = "select em.\"idEquipe\" as codigo," +
					" em.descricao as desc_equipe," +
					" em.\"alocacaoMaxima\" as maxima," +
					" em.\"alocacaoMinima\" as minima," +
					" em.sigla," +
					" to_char(em.\"dataCadastro\", 'dd/MM/yyyy') as data_cadastro," +
					" e.\"descricao\" as desc_esp" +
					" from equipe_manutencao em" +
					" inner join equipe_especialidade ee" +
					" on em.\"idEquipe\"=ee.\"idEquipe\"" +
					" inner join especialidade e" +
					" on ee.\"idEspecialidade\"=e.\"idEspecialidade\"" +
					" where em.\"idEquipe\" = "+ idEquipe;
			System.out.println(sql);
			ResultSet res = conexao.executarSQL(sql);
			while (res.next()) {
				equipe.setIdEquipe(res.getInt("codigo"));
				equipe.setDescricao(res.getString("desc_equipe"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return equipe;
	}
	
	public ArrayList<HashMap<String, String>> buscaEspecialidade() {
		ArrayList<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "SELECT \"idEspecialidade\" as codigo, descricao as desc_esp FROM especialidade order by 1;";
			System.out.println(sql);
			ResultSet res = conexao.executarSQL(sql);
			while (res.next()) {
				HashMap<String, String> hash = new HashMap<String, String>();
				hash.put("codigo", res.getString("codigo"));
				hash.put("desc_esp", res.getString("desc_esp"));
				lista.add(hash);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
}
