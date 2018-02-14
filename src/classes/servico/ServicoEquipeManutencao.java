package classes.servico;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.postgresql.util.PSQLException;

import classes.EquipeManutencao;
import classes.dao.EquipeManutencaoDao;

public class ServicoEquipeManutencao {

	EquipeManutencaoDao equipeManutencaoDao; 
	Date data = new Date();
	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

	public ServicoEquipeManutencao() {
		equipeManutencaoDao = new EquipeManutencaoDao();
	}
	
	public boolean insereEquipe(EquipeManutencao equipeManutencao) {
		boolean retorno = false;

		if (equipeManutencao.getDataCadastro() == "" || equipeManutencao.getDataCadastro() == null)
			equipeManutencao.setDataCadastro(formato.format(data));

		// Chama o método que irá fazer a inserção no banco de dados
		try {
			retorno = equipeManutencaoDao.inserirEquipe(equipeManutencao);
		} catch (PSQLException e) {
			e.printStackTrace();
			retorno = false;
		}
		return retorno;
	}

	public boolean atualizaEquipe(EquipeManutencao equipeManutencao) {
		// Depois verifica se atualizou pelo menos uma linha no banco de dados e
		// retorna para o flex
		if (equipeManutencaoDao.atualizarEquipe(equipeManutencao) == 1)
			return true;
		else
			return false;
	}
	
	public EquipeManutencao listaEquipe(int idEquipe){
		EquipeManutencao equipe = new EquipeManutencao();
		equipe = equipeManutencaoDao.buscaEquipeManutencao(idEquipe);
		return equipe;
	}

	public List<HashMap<String, String>> listaEquipe() {
		List<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		lista = equipeManutencaoDao.buscaEquipeManutencao();
		return lista;
	}
	
	public List<HashMap<String, String>> listaEspecialidade() {
		List<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		lista = equipeManutencaoDao.buscaEspecialidade();
		return lista;
	}
}
