package classes.servico;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import org.postgresql.util.PSQLException;

import classes.PerfilUsuario;
import classes.dao.PerfilUsuarioDao;

public class ServicoPerfilUsuario {
	PerfilUsuario perfil;
	PerfilUsuarioDao perfilDao;
	Date data = new Date();
	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	
	public ServicoPerfilUsuario() {
		perfil = new PerfilUsuario();
		perfilDao = new PerfilUsuarioDao();
	}
	
	public boolean inserePerfil(String descricao, boolean statusUsuario, int poder, String dataCadastro){
		boolean retorno = false;
		//Primeiro seta os dados no objeto perfil
		perfil.setDescricao(descricao.toUpperCase());
		perfil.setStatusUsuario(statusUsuario);
		perfil.setPoder(poder);
		if (dataCadastro == "")
			perfil.setDataCadastro(formato.format(data));
		else
			perfil.setDataCadastro(dataCadastro);

		//Chama o método que irá fazer a inserção no banco de dados
		try {
			retorno = perfilDao.inserirPerfil(perfil);
		} catch (PSQLException e) {
			e.printStackTrace();
			retorno = false;
		}
		return retorno;
	}
	
	public boolean atualizaPerfil(int codigoPerfil, String descricao, boolean statusUsuario, int poder, String dataCadastro){
		//Primeiro seta os dados no objeto perfil
		perfil.setCodigoPerfil(codigoPerfil);
		perfil.setDescricao(descricao.toUpperCase());
		perfil.setStatusUsuario(statusUsuario);
		perfil.setPoder(poder);
		perfil.setDataCadastro(dataCadastro);
		//Depois verifica se atualizou pelo menos uma linha no banco de dados e retorna para o flex
		if(perfilDao.atualizarPerfil(perfil)==1)
			return true;
		else
			return false;
	}
	
	public boolean excluiPerfil(int codigoPerfil){
		perfil.setCodigoPerfil(codigoPerfil);
		if(perfilDao.excluirPerfil(perfil)==1)
			return true;
		else
			return false;
	}
	
	public List<HashMap<String, String>> listaPerfil(){
		List<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		lista = perfilDao.buscaPerfil();
		return lista;
	}
}
