package classes.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.postgresql.util.PSQLException;

import classes.Equipamento;
import classes.Funcionario;
import classes.conexao.Conexao;

public class FuncionarioDao {
	Conexao conexao;

	public boolean inserirFuncionario(Funcionario funcionario) throws PSQLException {
		// Insere as informações no banco de dados
		conexao = new Conexao();
		boolean retorno = false;
		String sql;
		sql = "INSERT INTO funcionario(nome, sobrenome, cpf, rg, \"dataNascimento\", \"dataAdmissao\"," +
				" \"idCargo\", \"idEquipe\", \"idPerfil\", usuario, senha, \"statusFuncionario\", \"idUsuario\", \"idEspecialidade\", \"dataCadastro\") VALUES (" +
				conexao.prepararString(funcionario.getNome().toUpperCase())+", " +
				conexao.prepararString(funcionario.getSobrenome().toUpperCase())+", " +
				conexao.prepararString(funcionario.getCpf())+", " +
				conexao.prepararString(funcionario.getRg().toUpperCase())+", " +
				conexao.prepararString(funcionario.getDataNascimento())+", " +
				conexao.prepararString(funcionario.getDataAdmissao())+", " +
				funcionario.getIdCargo()+", " +
				funcionario.getIdEquipe()+", " +
				funcionario.getIdPerfil()+", " +
				conexao.prepararString(funcionario.getUsuario())+", " +
				conexao.prepararString(funcionario.getSenha())+", " +
				funcionario.isStatusFuncionario()+", " +
				funcionario.getIdUsuario()+", "+
				funcionario.getIdEspecialidade()+", "+
				conexao.prepararString(funcionario.getDataCadastro())+")";
		System.out.println(sql);
		retorno = conexao.executarInsercao(sql);

		try {
			if(retorno == true){
				sql = 	"select max(\"idFuncionario\") as codigo from funcionario where \"idUsuario\"=" +
						funcionario.getIdUsuario();
				ResultSet res = conexao.executarSQL(sql);
				while (res.next()){
					funcionario.setIdFuncionario(res.getInt("codigo"));	//O resultado sera adicionado no atributo idEquipamento 
				}														//do objeto equipamento
				if(funcionario.getIdFuncionario()>0)
					sql = "INSERT INTO funcionario_tipo(\"idTipo\", \"idFuncionario\") VALUES ("
							+ funcionario.getIdTipo() + ", "
							+ funcionario.getIdFuncionario()
							+ ");";
				System.out.println(sql);
				retorno = conexao.executarInsercao(sql);//Faz a inserção na tabela especialidade_equipamento somente caso a anterior tenha sucesso
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

	//Continuar a partir daqui
	public int atualizarFuncionario(Funcionario funcionario) {
		int retorno = 0;

		String sql = "UPDATE funcionario SET nome=" +
				conexao.prepararString(funcionario.getNome().toUpperCase())+", sobrenome=" +
				conexao.prepararString(funcionario.getSobrenome().toUpperCase())+", cpf=" +
				conexao.prepararString(funcionario.getCpf())+", rg=" +
				conexao.prepararString(funcionario.getRg().toUpperCase())+", \"dataNascimento\"=" +
				conexao.prepararString(funcionario.getDataNascimento())+", \"dataAdmissao\"=" +
				conexao.prepararString(funcionario.getDataAdmissao())+", \"idCargo\"=" +
				funcionario.getIdCargo()+", \"idEquipe\"=" +
				funcionario.getIdEquipe()+", \"idPerfil\"=" +
				funcionario.getIdPerfil()+", usuario=" +
				conexao.prepararString(funcionario.getUsuario())+", senha=" +
				conexao.prepararString(funcionario.getSenha())+", \"statusFuncionario\"=" +
				funcionario.isStatusFuncionario()+", \"idUsuario\"=" +
				funcionario.getIdUsuario()+", \"idEspecialidade\"="+
				funcionario.getIdEspecialidade()+", \"dataCadastro\"="+
				conexao.prepararString(funcionario.getDataCadastro())+" WHERE \"idFuncionario\"=" +
				funcionario.getIdFuncionario()+";";
		System.out.println(sql);
		retorno = conexao.executarAtualizacao(sql);

		try {
			if (retorno > 0) {
				sql = "UPDATE funcionario_tipo SET \"idTipo\"=" +
						funcionario.getIdTipo()+
						" WHERE \"idFuncionario\"=" + funcionario.getIdFuncionario()+";";
				System.out.println(sql);
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

	public ArrayList<HashMap<String, String>> buscaFuncionario() {
		ArrayList<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "SELECT f.\"idFuncionario\" as codigo, "
					+ "       f.nome, "
					+ "       f.sobrenome, "
					+ "       f.cpf, "
					+ "       f.rg, "
					+ "       to_char(f.\"dataNascimento\", 'dd/MM/yyyy') as data_nasc, "
					+ "       to_char(f.\"dataAdmissao\", 'dd/MM/yyyy') as data_adm, "
					+ "       c.descricao  AS desc_cargo, "
					+ "       em.descricao AS desc_equipe, "
					+ "       pu.descricao AS desc_perfil, "
					+ "		  tf.descricao as desc_tipo, "
					+ "       to_char(f.\"dataCadastro\", 'dd/MM/yyyy') AS data_cadastro, "
					+ "       usuario, "
					+ "       senha, "
					+ "       \"statusFuncionario\" as status_func, "
					+ "       e.descricao  AS desc_esp "
					+ "FROM   funcionario f "
					+ "       INNER JOIN cargo c "
					+ "               ON c.\"idCargo\" = f.\"idCargo\" "
					+ "       INNER JOIN equipe_manutencao em "
					+ "               ON em.\"idEquipe\" = f.\"idEquipe\" "
					+ "       INNER JOIN perfil_usuario pu "
					+ "               ON pu.\"idPerfil\" = f.\"idPerfil\" "
					+ "       INNER JOIN especialidade e "
					+ "               ON e.\"idEspecialidade\" = f.\"idEspecialidade\" "
					+ "       INNER JOIN funcionario_tipo ft "
					+ "               ON ft.\"idFuncionario\" = f.\"idFuncionario\" "
					+ "       INNER JOIN tipo_funcionario tf "
					+ "               ON ft.\"idTipo\" = tf.\"idTipo\""+
					" order by 1 ";

			System.out.println(sql);
			ResultSet res = conexao.executarSQL(sql);
			while (res.next()) {
				HashMap<String, String> hash = new HashMap<String, String>();
				hash.put("codigo", res.getString("codigo"));
				hash.put("nome", res.getString("nome"));
				hash.put("sobrenome", res.getString("sobrenome"));
				hash.put("cpf", res.getString("cpf"));
				hash.put("rg", res.getString("rg"));
				hash.put("data_nasc", res.getString("data_nasc"));
				hash.put("data_adm", res.getString("data_adm"));
				hash.put("desc_cargo", res.getString("desc_cargo"));
				hash.put("desc_tipo", res.getString("desc_tipo"));
				hash.put("desc_equipe", res.getString("desc_equipe"));
				hash.put("desc_perfil", res.getString("desc_perfil"));
				hash.put("status_func", res.getString("status_func"));
				hash.put("desc_esp", res.getString("desc_esp"));
				hash.put("data_cadastro", res.getString("data_cadastro"));
				lista.add(hash);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public ArrayList<HashMap<String, String>> buscaCargo() {
		ArrayList<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "SELECT \"idCargo\" as codigo, descricao as desc_cargo FROM cargo order by 1";
			System.out.println(sql);
			ResultSet res = conexao.executarSQL(sql);
			while (res.next()) {
				HashMap<String, String> hash = new HashMap<String, String>();
				hash.put("codigo", res.getString("codigo"));
				hash.put("desc_cargo", res.getString("desc_cargo"));
				lista.add(hash);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public ArrayList<HashMap<String, String>> buscaTipo() {
		ArrayList<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select \"idTipo\" as codigo, descricao as desc_tipo from tipo_funcionario order by 1";
			System.out.println(sql);
			ResultSet res = conexao.executarSQL(sql);
			while (res.next()) {
				HashMap<String, String> hash = new HashMap<String, String>();
				hash.put("codigo", res.getString("codigo"));
				hash.put("desc_tipo", res.getString("desc_tipo"));
				lista.add(hash);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
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

	public ArrayList<HashMap<String, String>> buscaEquipe() {
		ArrayList<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "SELECT \"idEquipe\" as codigo, descricao as desc_equipe FROM equipe_manutencao order by 1;";
			System.out.println(sql);
			ResultSet res = conexao.executarSQL(sql);
			while (res.next()) {
				HashMap<String, String> hash = new HashMap<String, String>();
				hash.put("codigo", res.getString("codigo"));
				hash.put("desc_equipe", res.getString("desc_equipe"));
				lista.add(hash);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public ArrayList<HashMap<String, String>> buscaPerfil() {
		ArrayList<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "SELECT \"idPerfil\" as codigo, descricao as desc_perfil FROM perfil_usuario order by 1;";
			System.out.println(sql);
			ResultSet res = conexao.executarSQL(sql);
			while (res.next()) {
				HashMap<String, String> hash = new HashMap<String, String>();
				hash.put("codigo", res.getString("codigo"));
				hash.put("desc_perfil", res.getString("desc_perfil"));
				lista.add(hash);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
}
