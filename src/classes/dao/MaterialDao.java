package classes.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import org.postgresql.util.PSQLException;

import classes.Material;
import classes.conexao.Conexao;


@SuppressWarnings("rawtypes")
public class MaterialDao {
	
	//Material material = new Material();
	Conexao conexao;
	
	public String inserirMaterial(Material material) throws PSQLException {
		// Insere as informações no banco de dados
		conexao = new Conexao();
		String retorno;
		String sql = "select material_inserir (" + material.getCodigoMaterial()
				+ ", "
				+ conexao.prepararString(material.getDescricaoMaterial())
				+ ", " + material.getValor() + ", " + material.getPeso() + ", "
				+ material.getVidaUtil() + ", "
				+ conexao.prepararString(material.getDataFabricacao()) + ", "
				+ material.getIdUnidadeMedida() + ", "
				+ material.getIdFamilia() + ", " + material.getIdFornecedor()
				+ ", " + material.getIdMaterial() + ");";
		retorno = conexao.executarConsulta(sql);

		try {
			if (retorno == "Sucesso")
				conexao.confirmacao(true);
			else
				conexao.confirmacao(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return retorno;
	}

	public int excluirMaterial(Material material) {
		int retorno = 0;

		String sql = "DELETE FROM fornecedor_material fm "
				+ "WHERE  fm.\"idMaterial\" = " + material.getCodigoMaterial();
		conexao.executarAtualizacao(sql);
		sql = "DELETE FROM material m " + "WHERE  m.\"idMaterial\" = "
				+ material.getCodigoMaterial();
		retorno = conexao.executarAtualizacao(sql);

		try {
			if (retorno > 0)
				conexao.confirmacao(true);
			else
				conexao.confirmacao(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retorno;
	}
	
	public ArrayList<HashMap<String, String>> buscaPecas(){
		ArrayList<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "SELECT m.\"idMaterial\" as codigo, m.descricao as desc_material, "
+ "       forn.\"nomeFantasia\" as fantasia, "
+ "       tm.descricao as desc_tipo, "
+ "       f.descricao as desc_familia, "
+ "       m.valor, "
+ "       m.peso, "
+ "       um.descricao as unidade, "
+ "       um.sigla, "
+ "       m.\"vidaUtil\" as vida_util, "
+ "       to_char(m.\"dataFabricacao\", 'dd/mm/yyyy') as data_fab"
+ "		  FROM   material m "
+ "       INNER JOIN familia_material f "
+ "         ON f.\"idFamilia\" = m.\"idFamilia\" "
+ "       INNER JOIN tipo_material tm "
+ "         ON tm.\"idTipoMaterial\" = m.\"idTipoMaterial\" "
+ "       INNER JOIN unidade_medida um "
+ "         ON um.\"idUnidade\" = m.\"idUnidade\" "
+ "       INNER JOIN fornecedor_material fm "
+ "         ON fm.\"idMaterial\" = m.\"idMaterial\" "
+ "       INNER JOIN fornecedor forn "
+ "         ON forn.\"idFornecedor\" = fm.\"idFornecedor\" order by 1 ";
			System.out.println(sql);
			ResultSet res = conexao.executarSQL(sql);
			while (res.next()) {
				HashMap<String, String> listaPecas = new HashMap<String, String>();
				listaPecas.put("codigo", res.getString("codigo"));
				listaPecas.put("desc_material", res.getString("desc_material"));
				listaPecas.put("fantasia", res.getString("fantasia"));
				listaPecas.put("desc_tipo", res.getString("desc_tipo"));
				listaPecas.put("desc_familia", res.getString("desc_familia"));
				listaPecas.put("valor", res.getString("valor"));
				listaPecas.put("peso", res.getString("peso"));
				listaPecas.put("unidade", res.getString("unidade"));
				listaPecas.put("vida_util", res.getString("vida_util"));
				listaPecas.put("data_fab", res.getString("data_fab"));
				lista.add(listaPecas);
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro de manipulação dos dados: "+e);
		}
		return lista;
	}
	
	//Combobox Material
	public ArrayList<HashMap<String, String>> buscaMaterial(){
		ArrayList<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select \"idTipoMaterial\", descricao as desc_tipo from tipo_material order by descricao";
			ResultSet res = conexao.executarSQL(sql);
			
			while (res.next()) {
				HashMap<String, String> listaMaterial = new HashMap<String, String>();
				listaMaterial.put("idTipoMaterial", res.getString("idTipoMaterial"));
				listaMaterial.put("desc_tipo", res.getString("desc_tipo"));
				lista.add(listaMaterial);
			}
			
		} catch (Exception e) {
			   JOptionPane.showMessageDialog(null, "Erro de manipulação dos dados: "+e);
		}
		return lista;
	}
	
	//Combobox Familia
	public List<HashMap<String, String>> buscaFamilia(){
		List<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select \"idFamilia\", descricao as desc_familia from familia_material order by descricao";
			ResultSet res = conexao.executarSQL(sql);
			
			while (res.next()) {
				HashMap<String, String> listaFamilia = new HashMap<String, String>();
				listaFamilia.put("idFamilia", res.getString("idFamilia"));
				listaFamilia.put("desc_familia", res.getString("desc_familia"));
				lista.add(listaFamilia);				
			}
			
		} catch (Exception e) {
			   JOptionPane.showMessageDialog(null, "Erro de manipulação dos dados: "+e);
		}
		return lista;
	}
	
	public List<HashMap<String, String>> buscaUnidade(){
		List<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select \"idUnidade\", descricao as unidade from unidade_medida order by descricao";
			ResultSet res = conexao.executarSQL(sql);
			
			while (res.next()) {
				HashMap<String, String> listaUnidade = new HashMap<String, String>();
				listaUnidade.put("idUnidade", res.getString("idUnidade"));
				listaUnidade.put("unidade", res.getString("unidade"));
				lista.add(listaUnidade);				
			}
			
		} catch (Exception e) {
			   JOptionPane.showMessageDialog(null, "Erro de manipulação dos dados: "+e);
		}
		return lista;
	}
	
	public List<HashMap<String, String>> buscaFabricante(){
		List<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "select \"idFornecedor\", \"nomeFantasia\" as fantasia from fornecedor order by \"nomeFantasia\"";
			ResultSet res = conexao.executarSQL(sql);
			while (res.next()) {
				HashMap<String, String> listaFabricante = new HashMap<String, String>();
				listaFabricante.put("idFornecedor", res.getString("idFornecedor"));
				listaFabricante.put("fantasia", res.getString("fantasia"));
				lista.add(listaFabricante);				
			}
			
		} catch (Exception e) {
			   JOptionPane.showMessageDialog(null, "Erro de manipulação dos dados: "+e);
		}
		return lista;
	}
}
