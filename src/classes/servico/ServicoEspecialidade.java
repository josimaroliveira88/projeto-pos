package classes.servico;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.postgresql.util.PSQLException;

import classes.EquipeManutencao;
import classes.Especialidade;
import classes.dao.EquipeManutencaoDao;
import classes.dao.EspecialidadeDao;

public class ServicoEspecialidade {

	EspecialidadeDao especialidadeDao; 
	Date data = new Date();
	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

	public ServicoEspecialidade() {
		especialidadeDao = new EspecialidadeDao();
	}
	
	public boolean insereEspecialidade(Especialidade especialidade) {
		boolean retorno = false;

		if (especialidade.getDataCadastro() == "" || especialidade.getDataCadastro() == null)
			especialidade.setDataCadastro(formato.format(data));

		// Chama o método que irá fazer a inserção no banco de dados
		try {
			retorno = especialidadeDao.inserirEspecialidade(especialidade);
		} catch (PSQLException e) {
			e.printStackTrace();
			retorno = false;
		}
		return retorno;
	}

	public boolean atualizaEspecialidade(Especialidade especialidade) {
		// Depois verifica se atualizou pelo menos uma linha no banco de dados e
		// retorna para o flex
		if (especialidadeDao.atualizarEspecialidade(especialidade) == 1)
			return true;
		else
			return false;
	}
	
	public boolean excluiEspecialidade(Especialidade especialidade){
		if(especialidadeDao.excluirEspecialidade(especialidade)==1)
			return true;
		else
			return false;
	}

	public List<HashMap<String, String>> listaEspecialidade() {
		List<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		lista = especialidadeDao.buscaEspecialidade();
		return lista;
	}
}
