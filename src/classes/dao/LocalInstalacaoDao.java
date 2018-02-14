package classes.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.postgresql.util.PSQLException;

import classes.EquipeManutencao;
import classes.LocalInstalacao;
import classes.conexao.Conexao;

public class LocalInstalacaoDao {
	Conexao conexao;

	public boolean inserirLocalInstalacao(LocalInstalacao localInstalacao) throws PSQLException {
		// Insere as informações no banco de dados
		conexao = new Conexao();
		boolean retorno = false;
		String sql;
		sql = "INSERT INTO local_instalacao(\"tagLocal\", descricao, \"idEquipe\",  \"dataCadastro\", \"idFuncionario\") VALUES ("
				+ conexao.prepararString(localInstalacao.getTagLocal().toUpperCase()) + ", "
				+ conexao.prepararString(localInstalacao.getDescricao().toUpperCase()) + ", "
				+ localInstalacao.getIdEquipe() + ", "
				+ conexao.prepararString(localInstalacao.getDataCadastro()) + ", "
				+ localInstalacao.getIdFuncionario()
				+ ");";
		System.out.println(sql);
		retorno = conexao.executarInsercao(sql);

		try {
			if(retorno == true)
				conexao.confirmacao(retorno);
			else
				conexao.confirmacao(retorno);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return retorno;
	}
	
	public int atualizarLocalInstalacao(LocalInstalacao localInstalacao) {
		int retorno = 0;

		String sql = "UPDATE local_instalacao SET \"tagLocal\"=" +
				conexao.prepararString(localInstalacao.getTagLocal().toUpperCase())+", descricao=" +
				conexao.prepararString(localInstalacao.getDescricao().toUpperCase())+", \"idEquipe\"=" +
				localInstalacao.getIdEquipe()+", \"idFuncionario\"=" +
				localInstalacao.getIdFuncionario()+" " +
				"WHERE \"idLocal\"="+localInstalacao.getIdLocal()+";";
		retorno = conexao.executarAtualizacao(sql);

		try {
			if (retorno > 0) {
				conexao.confirmacao(true);
			} else {
				conexao.confirmacao(false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retorno;
	}
	
	public int desativarLocalInstalacao(LocalInstalacao localInstalacao){
		int retorno = 0;

		String sql = "UPDATE local_instalacao SET status_ativo='false'" + " WHERE \"idLocal\"=" + localInstalacao.getIdLocal();
		System.out.println(sql);
		retorno = conexao.executarAtualizacao(sql);
		try {
			if (retorno > 0) {
				conexao.confirmacao(true);
			}else {
				conexao.confirmacao(false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return retorno;
	}
	
	public ArrayList<LocalInstalacao> buscaLocalInstalacao() {
		ArrayList<LocalInstalacao> lista = new ArrayList<LocalInstalacao>();
		try {
			String sql = "SELECT li.\"idLocal\" as codigo," +
					" li.\"tagLocal\" as tag," +
					" li.descricao as desc_local," +
					" em.descricao as desc_equipe," +
					" li.\"idEquipe\" as id_equipe," +
					" to_char(li.\"dataCadastro\", 'dd/MM/yyyy') as data_cadastro" +
					" FROM local_instalacao li" +
					" INNER JOIN equipe_manutencao em" +
					" ON li.\"idEquipe\"=em.\"idEquipe\""+
					" WHERE status_ativo='true' order by 1 ";
			System.out.println(sql);
			ResultSet res = conexao.executarSQL(sql);
			while (res.next()) {
				LocalInstalacao local = new LocalInstalacao();
				local.setIdLocal(res.getInt("codigo"));
				local.setTagLocal(res.getString("tag"));
				local.setDescricao(res.getString("desc_local"));
				local.setIdEquipe(res.getInt("id_equipe"));
				local.setDataCadastro(res.getString("data_cadastro"));
				local.setEquipe(res.getString("desc_equipe"));
				lista.add(local);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public ArrayList<LocalInstalacao> buscaLocalInstalacaoInativo() {
		ArrayList<LocalInstalacao> lista = new ArrayList<LocalInstalacao>();
		try {
			String sql = "SELECT li.\"idLocal\" as codigo," +
					" li.\"tagLocal\" as tag," +
					" li.descricao as desc_local," +
					" em.descricao as desc_equipe," +
					" li.\"idEquipe\" as id_equipe," +
					" to_char(li.\"dataCadastro\", 'dd/MM/yyyy') as data_cadastro" +
					" FROM local_instalacao li" +
					" INNER JOIN equipe_manutencao em" +
					" ON li.\"idEquipe\"=em.\"idEquipe\""+
					" WHERE status_ativo='false' order by 1 ";
			System.out.println(sql);
			ResultSet res = conexao.executarSQL(sql);
			while (res.next()) {
				LocalInstalacao local = new LocalInstalacao();
				local.setIdLocal(res.getInt("codigo"));
				local.setTagLocal(res.getString("tag"));
				local.setDescricao(res.getString("desc_local"));
				local.setIdEquipe(res.getInt("id_equipe"));
				local.setDataCadastro(res.getString("data_cadastro"));
				local.setEquipe(res.getString("desc_equipe"));
				lista.add(local);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public ArrayList<LocalInstalacao> buscaLocalInstalacaoTodos() {
		ArrayList<LocalInstalacao> lista = new ArrayList<LocalInstalacao>();
		try {
			String sql = "SELECT li.\"idLocal\" as codigo," +
					" li.\"tagLocal\" as tag," +
					" li.descricao as desc_local," +
					" em.descricao as desc_equipe," +
					" li.\"idEquipe\" as id_equipe," +
					" to_char(li.\"dataCadastro\", 'dd/MM/yyyy') as data_cadastro" +
					" FROM local_instalacao li" +
					" INNER JOIN equipe_manutencao em" +
					" ON li.\"idEquipe\"=em.\"idEquipe\""+
					" order by 1 ";
			System.out.println(sql);
			ResultSet res = conexao.executarSQL(sql);
			while (res.next()) {
				LocalInstalacao local = new LocalInstalacao();
				local.setIdLocal(res.getInt("codigo"));
				local.setTagLocal(res.getString("tag"));
				local.setDescricao(res.getString("desc_local"));
				local.setIdEquipe(res.getInt("id_equipe"));
				local.setDataCadastro(res.getString("data_cadastro"));
				local.setEquipe(res.getString("desc_equipe"));
				lista.add(local);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public LocalInstalacao buscaLocalInstalacao(int idLocal){
		LocalInstalacao local = new LocalInstalacao();
		try {
			String sql = "SELECT \"idLocal\"  AS id_local, "
					+ "       \"tagLocal\" AS tag_local, "
					+ "       descricao "
					+ "FROM   local_instalacao WHERE \"idLocal\" = "
					+ idLocal
					+ " ORDER  BY 1";
			System.out.println(sql);
			ResultSet res = conexao.executarSQL(sql);
			while (res.next()) {
				local.setIdLocal(res.getInt("id_local"));
				local.setTagLocal(res.getString("tag_local"));
				local.setDescricao(res.getString("descricao"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return local;
	}
	
	public ArrayList<EquipeManutencao> buscaEquipe() {
		ArrayList<EquipeManutencao> lista = new ArrayList<EquipeManutencao>();
		try {
			String sql = "SELECT \"idEquipe\" as codigo, descricao as desc_equipe FROM equipe_manutencao order by 1";
			System.out.println(sql);
			ResultSet res = conexao.executarSQL(sql);
			while (res.next()) {
				EquipeManutencao equipe = new EquipeManutencao();
				equipe.setIdEquipe(res.getInt("codigo"));
				equipe.setDescricao(res.getString("desc_equipe"));
				lista.add(equipe);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public ArrayList<HashMap<String, String>> buscaCentro() {
		ArrayList<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "SELECT \"idCentro\" as codigo, descricao as desc_centro, \"centroCusto\" as centro_custo FROM centro_custo order by 1";
			System.out.println(sql);
			ResultSet res = conexao.executarSQL(sql);
			while (res.next()) {
				HashMap<String, String> hash = new HashMap<String, String>();
				hash.put("codigo", res.getString("codigo"));
				hash.put("desc_centro", res.getString("desc_centro"));
				hash.put("centro_custo", res.getString("centro_custo"));
				lista.add(hash);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
}
