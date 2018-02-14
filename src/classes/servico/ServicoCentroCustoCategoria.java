package classes.servico;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.postgresql.util.PSQLException;

import classes.CentroCustoCategoria;
import classes.dao.CentroCustoCategoriaDao;

public class ServicoCentroCustoCategoria {
	CentroCustoCategoria categoria;
	CentroCustoCategoriaDao categoriaDao;
	Date data = new Date();
	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	
	public boolean insereCategoria(String descricao, String dataCadastro){
		inicializaObjetos();
		boolean retorno = false;
		//Primeiro seta os dados no objeto categoria
		categoria.setDescricao(descricao.toUpperCase());
		if (dataCadastro == "" || dataCadastro == null)
			categoria.setDataCadastro(formato.format(data));
		else
			categoria.setDataCadastro(dataCadastro);

		//Chama o método que irá fazer a inserção no banco de dados
		try {
			retorno = categoriaDao.inserirCategoria(categoria);
		} catch (PSQLException e) {
			e.printStackTrace();
			retorno = false;
		}
		return retorno;
	}
	
	public boolean atualizaCategoria(int idCategoria, String descricao, String dataCadastro){
		//Primeiro seta os dados no objeto categoria
		inicializaObjetos();
		categoria.setIdCategoria(idCategoria);
		categoria.setDescricao(descricao.toUpperCase());
		categoria.setDataCadastro(dataCadastro);
		//Depois verifica se atualizou pelo menos uma linha no banco de dados e retorna para o flex
		if(categoriaDao.atualizarCategoria(categoria)==1)
			return true;
		else
			return false;
	}

	private void inicializaObjetos() {
		categoria = new CentroCustoCategoria();
		categoriaDao = new CentroCustoCategoriaDao();
	}
	
	public boolean excluiCategoria(int idCategoria){
		inicializaObjetos();
		categoria.setIdCategoria(idCategoria);
		if(categoriaDao.excluirCategoria(categoria)==1)
			return true;
		else
			return false;
	}
	
	public List<HashMap<String, String>> listaCategoria(){
		inicializaObjetos();
		List<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		lista = categoriaDao.buscaCategoria();
		return lista;
	}
}
