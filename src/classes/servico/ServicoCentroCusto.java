package classes.servico;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.postgresql.util.PSQLException;

import classes.CentroCusto;
import classes.dao.CentroCustoDao;

public class ServicoCentroCusto {
	CentroCusto centroCusto;
	CentroCustoDao centroCustoDao;
	Date data = new Date();
	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

	public boolean insereCentroCusto(String codCentro, String descricao,
			String dataCadastro) {
		centroCusto = new CentroCusto();
		centroCustoDao = new CentroCustoDao();
		boolean retorno = false;
		// Primeiro seta os dados no objeto centroCusto
		centroCusto.setCodCentro(codCentro);
		centroCusto.setDescricao(descricao.toUpperCase());
		if (dataCadastro == "" || dataCadastro == null)
			centroCusto.setDataCadastro(formato.format(data));
		else
			centroCusto.setDataCadastro(dataCadastro);

		// Chama o método que irá fazer a inserção no banco de dados
		try {
			retorno = centroCustoDao.inserirCentroCusto(centroCusto);
		} catch (PSQLException e) {
			e.printStackTrace();
			retorno = false;
		}
		return retorno;
	}

	public boolean atualizaCentroCusto(int idCentro, String codCentro,
			String descricao, String dataCadastro) {
		// Primeiro seta os dados no objeto centroCusto
		centroCusto.setIdCentro(idCentro);
		centroCusto.setCodCentro(codCentro);
		centroCusto.setDescricao(descricao.toUpperCase());
		centroCusto.setDataCadastro(dataCadastro);
		// Depois verifica se atualizou pelo menos uma linha no banco de dados e
		// retorna para o flex
		if (centroCustoDao.atualizarCentroCusto(centroCusto) == 1)
			return true;
		else
			return false;
	}

	public boolean excluiCentroCusto(int idCentro) {
		centroCusto.setIdCentro(idCentro);
		if (centroCustoDao.excluirCentroCusto(centroCusto) == 1)
			return true;
		else
			return false;
	}

	public List listaCentroCusto() {
		List lista = new ArrayList();
		lista = centroCustoDao.buscaCentroCusto();
		return lista;
	}
}
