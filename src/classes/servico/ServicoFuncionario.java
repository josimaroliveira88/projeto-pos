package classes.servico;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.postgresql.util.PSQLException;

import classes.Funcionario;
import classes.dao.FuncionarioDao;

public class ServicoFuncionario {
	FuncionarioDao funcionarioDao; 
	Date data = new Date();
	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

	public ServicoFuncionario() {
		funcionarioDao = new FuncionarioDao();
	}
	
	public boolean insereFuncionario(Funcionario funcionario) {
		boolean retorno = false;

		if (funcionario.getDataCadastro() == "" || funcionario.getDataCadastro() == null)
			funcionario.setDataCadastro(formato.format(data));

		// Chama o método que irá fazer a inserção no banco de dados
		try {
			retorno = funcionarioDao.inserirFuncionario(funcionario);
		} catch (PSQLException e) {
			e.printStackTrace();
			retorno = false;
		}
		return retorno;
	}

	public boolean atualizaFuncionario(Funcionario funcionario) {
		// Depois verifica se atualizou pelo menos uma linha no banco de dados e
		// retorna para o flex
		if (funcionarioDao.atualizarFuncionario(funcionario) == 1)
			return true;
		else
			return false;
	}

	public List<HashMap<String, String>> listaFuncionario() {
		List<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		lista = funcionarioDao.buscaFuncionario();
		return lista;
	}

	public List<HashMap<String, String>> listaCargo() {
		List<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		lista = funcionarioDao.buscaCargo();
		return lista;
	}

	public List<HashMap<String, String>> listaTipo() {
		List<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		lista = funcionarioDao.buscaTipo();
		return lista;
	}

	public List<HashMap<String, String>> listaEspecialidade() {
		List<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		lista = funcionarioDao.buscaEspecialidade();
		return lista;
	}

	public List<HashMap<String, String>> listaEquipe() {
		List<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		lista = funcionarioDao.buscaEquipe();
		return lista;
	}

	public List<HashMap<String, String>> listaPerfil() {
		List<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		lista = funcionarioDao.buscaPerfil();
		return lista;
	}
}
