package classes.servico;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.postgresql.util.PSQLException;

import classes.Equipamento;
import classes.EquipeManutencao;
import classes.LocalInstalacao;
import classes.dao.LocalInstalacaoDao;

public class ServicoLocalInstalacao {
	LocalInstalacaoDao localInstalacaoDao; 
	Date data = new Date();
	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

	public ServicoLocalInstalacao() {
		localInstalacaoDao = new LocalInstalacaoDao();
	}
	
	public boolean insereLocalInstalacao(LocalInstalacao localInstalacao) {
		boolean retorno = false;

		if (localInstalacao.getDataCadastro() == "" || localInstalacao.getDataCadastro() == null)
			localInstalacao.setDataCadastro(formato.format(data));

		// Chama o método que irá fazer a inserção no banco de dados
		try {
			retorno = localInstalacaoDao.inserirLocalInstalacao(localInstalacao);
		} catch (PSQLException e) {
			e.printStackTrace();
			retorno = false;
		}
		return retorno;
	}

	public boolean atualizaLocalInstalacao(LocalInstalacao localInstalacao) {
		// Depois verifica se atualizou pelo menos uma linha no banco de dados e
		// retorna para o flex
		if (localInstalacaoDao.atualizarLocalInstalacao(localInstalacao) == 1)
			return true;
		else
			return false;
	}
	
	public boolean excluirLocalInstalacao(LocalInstalacao localInstalacao){
		if (localInstalacaoDao.desativarLocalInstalacao(localInstalacao) == 1)
			return true;
		else
			return false;
	}

	public ArrayList<LocalInstalacao> listaLocalInstalacao() {
		ArrayList<LocalInstalacao> lista = new ArrayList<LocalInstalacao>();
		lista = localInstalacaoDao.buscaLocalInstalacao();
		return lista;
	}
	
	public ArrayList<LocalInstalacao> listaLocalInstalacaoInativo() {
		ArrayList<LocalInstalacao> lista = new ArrayList<LocalInstalacao>();
		lista = localInstalacaoDao.buscaLocalInstalacaoInativo();
		return lista;
	}
	
	public ArrayList<LocalInstalacao> listaLocalInstalacaoTodos() {
		ArrayList<LocalInstalacao> lista = new ArrayList<LocalInstalacao>();
		lista = localInstalacaoDao.buscaLocalInstalacaoTodos();
		return lista;
	}
	
	public LocalInstalacao buscaLocalInstalacao(int idLocal){
		LocalInstalacao local = new LocalInstalacao();
		local = localInstalacaoDao.buscaLocalInstalacao(idLocal);
		return local;
	}
	
	public ArrayList<EquipeManutencao> listaEquipe() {
		ArrayList<EquipeManutencao> lista = new ArrayList<EquipeManutencao>();
		lista = localInstalacaoDao.buscaEquipe();
		return lista;
	}
	
	public List<HashMap<String, String>> listaCentro() {
		List<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		lista = localInstalacaoDao.buscaCentro();
		return lista;
	}
	
	public LocalInstalacao limparLocal(){
		LocalInstalacao local = new LocalInstalacao();
		local.setIdLocal(0);
		local.setTagLocal("");
		local.setDescricao("");
		local.setIdEquipe(0);
		return local;
	}
}
