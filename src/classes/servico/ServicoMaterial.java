package classes.servico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;

import org.postgresql.util.PSQLException;

import classes.Material;
import classes.dao.MaterialDao;

public class ServicoMaterial {
	Material material;
	MaterialDao materialDao;
	
	public ServicoMaterial() {
		material = new Material();
		materialDao = new MaterialDao();
	}
	
	public String insereMaterial(int codigoMaterial, String descricaoMaterial, double valor, double peso, int vidaUtil, 
								String dataFabricacao, int idTipoMaterial, int idUnidadeMedida, int idFamilia, int idFornecedor){
		String retorno;
		//Seta os dados no objeto material
		if (codigoMaterial != 0)
		material.setCodigoMaterial(codigoMaterial);
		material.setDescricaoMaterial(descricaoMaterial.toUpperCase());
		material.setValor(valor);
		material.setPeso(peso);
		material.setVidaUtil(vidaUtil);
		material.setDataFabricacao(dataFabricacao);
		material.setIdMaterial(idTipoMaterial);
		material.setIdUnidadeMedida(idUnidadeMedida);
		material.setIdFamilia(idFamilia);
		material.setIdFornecedor(idFornecedor);
		try {
			retorno = materialDao.inserirMaterial(material);
		} catch (PSQLException e) {
			e.printStackTrace();
			retorno = "Erro ao inserir material. Entre em contato com o suporte e informe a seguinte mensagem: " + e.getMessage();
		}
		return retorno;
	}
	
	public boolean excluiMaterial(int codigoMaterial){
		material.setCodigoMaterial(codigoMaterial);
		if(materialDao.excluirMaterial(material)==1)
			return true;
		else
			return false;
	}
	
	public List<HashMap<String, String>> listaMaterial(){
		List<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		lista = materialDao.buscaMaterial();
		return lista;
	}
	
	public List<HashMap<String, String>> listaPecas(){
		List<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		lista = materialDao.buscaPecas();
		return lista;
	}
	
	public List<HashMap<String, String>> listaFamilia(){
		List<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		lista = materialDao.buscaFamilia();
		return lista;
	}
	
	public List<HashMap<String, String>> listaUnidade(){
		List<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		lista = materialDao.buscaUnidade();
		return lista;
	}
	
	public List<HashMap<String, String>> listaFabricante(){
		List<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		lista = materialDao.buscaFabricante();
		return lista;
	}
	
}
