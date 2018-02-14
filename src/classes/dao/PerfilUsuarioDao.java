package classes.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.postgresql.util.PSQLException;

import classes.Material;
import classes.PerfilUsuario;
import classes.conexao.Conexao;

public class PerfilUsuarioDao {
	Conexao conexao;

	public boolean inserirPerfil(PerfilUsuario perfil) throws PSQLException{
		//Insere as informações no banco de dados
		conexao = new Conexao();
		boolean retorno;
		String sql ="INSERT INTO perfil_usuario(descricao, \"statusUsuario\", poder, \"dataCadastro\") VALUES ("+
				conexao.prepararString(perfil.getDescricao())+", "+
				perfil.getStatusUsuario()+", "+
				perfil.getPoder()+", "+
				conexao.prepararString(perfil.getDataCadastro())+");";
		retorno = conexao.executarInsercao(sql);

		try {
			if(retorno==true)
				conexao.confirmacao(retorno);
			else
				conexao.confirmacao(retorno);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retorno;
	}

	public int atualizarPerfil(PerfilUsuario perfil){
		int retorno = 0;
		String sql = "UPDATE perfil_usuario SET "+
				"descricao="+conexao.prepararString(perfil.getDescricao())+", "+
				"\"statusUsuario\"="+perfil.getStatusUsuario()+", "+
				"poder="+perfil.getPoder()+", "+
				"\"dataCadastro\"="+conexao.prepararString(perfil.getDataCadastro())+
				" WHERE \"idPerfil\"="+perfil.getCodigoPerfil()+";";
		retorno = conexao.executarAtualizacao(sql);

		try {
			if(retorno>0)
				conexao.confirmacao(true);
			else
				conexao.confirmacao(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retorno;
	}

	public int excluirPerfil(PerfilUsuario perfil){
		int retorno = 0;
		try {
			String sql = "select * from funcionario where \"idPerfil\"="+perfil.getCodigoPerfil();
			ResultSet res = conexao.executarSQL(sql);	//Primeiro verifica se existe algum funcionário vinculado ao perfil a ser excluído
			if(res.getFetchSize()==0){					//Caso exista o perfil não será excluído
				sql = "DELETE FROM perfil_usuario pu "
						+ "WHERE  pu.\"idPerfil\" = "+perfil.getCodigoPerfil();
				retorno = conexao.executarAtualizacao(sql);
				if(retorno>0)
					conexao.confirmacao(true);
				else
					conexao.confirmacao(false);
				return retorno;
			}else
				return retorno;
		} catch (SQLException e) {
			e.printStackTrace();
			return retorno;
		}
	}

	public ArrayList<HashMap<String, String>> buscaPerfil(){
		ArrayList<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		try {
			String sql = 	"SELECT pu.\"idPerfil\" as codigo, "+
					"pu.descricao, "+
					"pu.\"statusUsuario\" as status, "+
					"pu.poder, "+
					"to_char(pu.\"dataCadastro\", 'dd/mm/yyyy') as data_cadastro "+
					"FROM perfil_usuario pu " +
					"order by 1 ";
			ResultSet res = conexao.executarSQL(sql);
			while (res.next()) {
				HashMap<String, String> hmPerfil = new HashMap<String, String>();
				if(res.getBoolean("status")==true)
					hmPerfil.put("status", "ATIVO");
				else
					hmPerfil.put("status", "INATIVO");
				hmPerfil.put("codigo", res.getString("codigo"));
				hmPerfil.put("descricao", res.getString("descricao"));
				hmPerfil.put("poder", res.getString("poder"));
				hmPerfil.put("data_cadastro", res.getString("data_cadastro"));
				lista.add(hmPerfil);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
}
