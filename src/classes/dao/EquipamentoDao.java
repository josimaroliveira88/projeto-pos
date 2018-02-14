package classes.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.postgresql.util.PSQLException;

import classes.Equipamento;
import classes.Fornecedor;
import classes.LocalInstalacao;
import classes.TipoEquipamento;
import classes.conexao.Conexao;

public class EquipamentoDao {
	Conexao conexao;

	public boolean inserirEquipamento(Equipamento equipamento) throws PSQLException {
		// Insere as informações no banco de dados
		conexao = new Conexao();
		boolean retorno = false;
		String sql;
		sql = "INSERT INTO equipamento(\"tagEquipamento\", descricao, \"anoFabricacao\", peso, modelo, \"dataAquisicao\"," +
				" \"idLocal\", \"idTipo\", \"idFornecedor\", valor, \"numSerie\", \"numPatrimonio\", \"dataCadastro\", \"idFuncionario\", pais) VALUES ("
				+ conexao.prepararString(equipamento.getTag().toUpperCase()) + ", "
				+ conexao.prepararString(equipamento.getDescricao().toUpperCase()) + ", "
				+ equipamento.getAnoFabricacao() + ", "
				+ equipamento.getPeso() + ", "
				+ conexao.prepararString(equipamento.getModelo().toUpperCase()) + ", "
				+ conexao.prepararString(equipamento.getDataAquisicao()) + ", "
				+ equipamento.getIdLocal() + ", "
				+ equipamento.getIdTipoEquipamento()+ ", "
				+ equipamento.getIdFornecedor() + ", "
				+ equipamento.getValor() + ", "
				+ conexao.prepararString(equipamento.getNumSerie().toUpperCase()) + ", "
				+ conexao.prepararString(equipamento.getNumPatrimonio().toUpperCase()) + ", "
				+ conexao.prepararString(equipamento.getDataCadastro()) + ", "
				+ equipamento.getIdFuncionario() + ", "
				+ conexao.prepararString(equipamento.getPais().toUpperCase())
				+ ");";
		System.out.println(sql);
		retorno = conexao.executarInsercao(sql);

		try {
			if(retorno == true){
				sql = 	"select max(\"idEquipamento\") as codigo from equipamento where \"idFuncionario\"=" +
						equipamento.getIdFuncionario();
				ResultSet res = conexao.executarSQL(sql);
				while (res.next()){
					equipamento.setIdEquipamento(res.getInt("codigo"));	//O resultado sera adicionado no atributo idEquipamento 
				}														//do objeto equipamento
				if(equipamento.getIdEquipamento()>0)
					sql = "INSERT INTO especialidade_equipamento(\"idEspecialidade\", \"idEquipamento\") VALUES ("
							+ equipamento.getIdEspecialidade() + ", "
							+ equipamento.getIdEquipamento()
							+ ");";
				System.out.println(sql);
				retorno = conexao.executarInsercao(sql);//Faz a inserção na tabela especialidade_equipamento somente caso a anterior tenha sucesso
				if(retorno==true){						//Caso contrário faz o rollback
					conexao.confirmacao(retorno);	
				}else
					conexao.confirmacao(retorno);
			}else
				conexao.confirmacao(retorno);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return retorno;
	}
	
	public int atualizarEquipamento(Equipamento equipamento) {
		int retorno = 0;

		String sql = "UPDATE equipamento SET \"tagEquipamento\"=" +
				conexao.prepararString(equipamento.getTag().toUpperCase())+", descricao=" +
				conexao.prepararString(equipamento.getDescricao().toUpperCase())+", \"anoFabricacao\"=" +
				equipamento.getAnoFabricacao()+", peso=" +
				equipamento.getPeso()+", modelo=" +
				conexao.prepararString(equipamento.getModelo().toUpperCase())+", \"dataAquisicao\"=" +
				conexao.prepararString(equipamento.getDataAquisicao())+", \"idLocal\"=" +
				equipamento.getIdLocal()+", \"idTipo\"=" +
				equipamento.getIdTipoEquipamento()+", \"idFornecedor\"=" +
				equipamento.getIdFornecedor()+", valor=" +
				equipamento.getValor()+", \"numSerie\"=" +
				conexao.prepararString(equipamento.getNumSerie())+", \"numPatrimonio\"=" +
				conexao.prepararString(equipamento.getNumPatrimonio())+", pais=" +
				conexao.prepararString(equipamento.getPais().toUpperCase())+", \"idFuncionario\"=" +
				equipamento.getIdFuncionario()+" WHERE \"idEquipamento\"=" +
				equipamento.getIdEquipamento()+";";
		System.out.println(sql);
		retorno = conexao.executarAtualizacao(sql);

		try {
			if (retorno > 0) {
				sql = "UPDATE especialidade_equipamento SET \"idEspecialidade\"=" +
						equipamento.getIdEspecialidade()+
						" WHERE \"idEquipamento\"=" + equipamento.getIdEquipamento()+";";
				System.out.println(sql);
				retorno = conexao.executarAtualizacao(sql);
				if (retorno > 0) {
					conexao.confirmacao(true);	
				}
			} else {
				conexao.confirmacao(false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retorno;
	}
	
	public int desativarEquipamento(Equipamento equipamento){
		int retorno = 0;
		String sql = "UPDATE equipamento SET status_ativo='false'" + " WHERE \"idEquipamento\"=" + equipamento.getIdEquipamento();
		System.out.println(sql);
		retorno = conexao.executarAtualizacao(sql);
		try {
			if (retorno > 0) {
				conexao.confirmacao(true);
			}else {
				conexao.confirmacao(false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		return retorno;
	}
	
	public ArrayList<Equipamento> buscaEquipamento() {
		ArrayList<Equipamento> lista = new ArrayList<Equipamento>();
		try {
			String sql = "SELECT e.\"idEquipamento\"  AS codigo, "
					+ "       e.\"tagEquipamento\" AS tag_equip, "
					+ "       e.descricao        AS desc_equipamento, "
					+ "       e.\"anoFabricacao\"  AS ano_fab, "
					+ "       e.peso, "
					+ "       e.modelo, "
					+ "       to_char(e.\"dataAquisicao\", 'dd/MM/yyyy')  AS data_aquisicao, "
					+ "       l.\"tagLocal\"       AS tag_local, "
					+ "       e.\"idLocal\"        AS id_local, "
					+ "       e.\"idTipo\"        AS id_tipo, "
					+ "       e.\"idFornecedor\"        AS id_fornecedor, "
					+ "       e.pais        AS desc_pais, "
					+ "       te.descricao       AS desc_tipo, "
					+ "       f.\"nomeFantasia\"   AS fantasia, "
					+ "       e.valor, "
					+ "       e.\"numSerie\"       AS num_serie, "
					+ "       e.\"numPatrimonio\"  AS num_patrimonio, "
					+ "       to_char(e.\"dataCadastro\", 'dd/MM/yyyy') AS data_cadastro, "
					+ "       esp.descricao as desc_esp "
					+ "FROM   equipamento e "
					+ "       INNER JOIN local_instalacao l "
					+ "               ON e.\"idLocal\" = l.\"idLocal\" "
					+ "       INNER JOIN fornecedor f "
					+ "               ON e.\"idFornecedor\" = f.\"idFornecedor\" "
					+ "       INNER JOIN tipo_equipamento te "
					+ "               ON e.\"idTipo\" = te.\"idTipo\" "
					+ "       INNER JOIN especialidade_equipamento ee "
					+ "               ON e.\"idEquipamento\" = ee.\"idEquipamento\" "
					+ "       INNER JOIN especialidade esp "
					+ "               ON ee.\"idEspecialidade\" = esp.\"idEspecialidade\" "+
					" WHERE e.status_ativo = 'true' order by 1 ";
					
			System.out.println(sql);
			ResultSet res = conexao.executarSQL(sql);
			while (res.next()) {
				Equipamento equipamento = new Equipamento();
				equipamento.setIdEquipamento(res.getInt("codigo"));
				equipamento.setTag(res.getString("tag_equip"));
				equipamento.setDescricao(res.getString("desc_equipamento"));
				equipamento.setAnoFabricacao(res.getInt("ano_fab"));
				equipamento.setPeso(res.getDouble("peso"));
				equipamento.setModelo(res.getString("modelo"));
				equipamento.setDataAquisicao(res.getString("data_aquisicao"));
				equipamento.setIdLocal(res.getInt("id_local"));
				equipamento.setLocal(res.getString("tag_local"));
				equipamento.setIdTipoEquipamento(res.getInt("id_tipo"));
				equipamento.setTipo(res.getString("desc_tipo"));
				equipamento.setIdFornecedor(res.getInt("id_fornecedor"));
				equipamento.setFantasia(res.getString("fantasia"));
				equipamento.setPais(res.getString("desc_pais"));
				equipamento.setValor(res.getDouble("valor"));
				equipamento.setNumSerie(res.getString("num_serie"));
				equipamento.setEspecialidade(res.getString("desc_esp"));
				equipamento.setNumPatrimonio(res.getString("num_patrimonio"));
				equipamento.setDataCadastro(res.getString("data_cadastro"));
				lista.add(equipamento);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public ArrayList<Equipamento> buscaEquipamentoInativo() {
		ArrayList<Equipamento> lista = new ArrayList<Equipamento>();
		try {
			String sql = "SELECT e.\"idEquipamento\"  AS codigo, "
					+ "       e.\"tagEquipamento\" AS tag_equip, "
					+ "       e.descricao        AS desc_equipamento, "
					+ "       e.\"anoFabricacao\"  AS ano_fab, "
					+ "       e.peso, "
					+ "       e.modelo, "
					+ "       to_char(e.\"dataAquisicao\", 'dd/MM/yyyy')  AS data_aquisicao, "
					+ "       l.\"tagLocal\"       AS tag_local, "
					+ "       e.\"idLocal\"        AS id_local, "
					+ "       e.\"idTipo\"        AS id_tipo, "
					+ "       e.\"idFornecedor\"        AS id_fornecedor, "
					+ "       e.pais        AS desc_pais, "
					+ "       te.descricao       AS desc_tipo, "
					+ "       f.\"nomeFantasia\"   AS fantasia, "
					+ "       e.valor, "
					+ "       e.\"numSerie\"       AS num_serie, "
					+ "       e.\"numPatrimonio\"  AS num_patrimonio, "
					+ "       to_char(e.\"dataCadastro\", 'dd/MM/yyyy') AS data_cadastro, "
					+ "       esp.descricao as desc_esp "
					+ "FROM   equipamento e "
					+ "       INNER JOIN local_instalacao l "
					+ "               ON e.\"idLocal\" = l.\"idLocal\" "
					+ "       INNER JOIN fornecedor f "
					+ "               ON e.\"idFornecedor\" = f.\"idFornecedor\" "
					+ "       INNER JOIN tipo_equipamento te "
					+ "               ON e.\"idTipo\" = te.\"idTipo\" "
					+ "       INNER JOIN especialidade_equipamento ee "
					+ "               ON e.\"idEquipamento\" = ee.\"idEquipamento\" "
					+ "       INNER JOIN especialidade esp "
					+ "               ON ee.\"idEspecialidade\" = esp.\"idEspecialidade\" "+
					" WHERE e.status_ativo = 'false' order by 1 ";
					
			System.out.println(sql);
			ResultSet res = conexao.executarSQL(sql);
			while (res.next()) {
				Equipamento equipamento = new Equipamento();
				equipamento.setIdEquipamento(res.getInt("codigo"));
				equipamento.setTag(res.getString("tag_equip"));
				equipamento.setDescricao(res.getString("desc_equipamento"));
				equipamento.setAnoFabricacao(res.getInt("ano_fab"));
				equipamento.setPeso(res.getDouble("peso"));
				equipamento.setModelo(res.getString("modelo"));
				equipamento.setDataAquisicao(res.getString("data_aquisicao"));
				equipamento.setIdLocal(res.getInt("id_local"));
				equipamento.setLocal(res.getString("tag_local"));
				equipamento.setIdTipoEquipamento(res.getInt("id_tipo"));
				equipamento.setTipo(res.getString("desc_tipo"));
				equipamento.setIdFornecedor(res.getInt("id_fornecedor"));
				equipamento.setFantasia(res.getString("fantasia"));
				equipamento.setPais(res.getString("desc_pais"));
				equipamento.setValor(res.getDouble("valor"));
				equipamento.setNumSerie(res.getString("num_serie"));
				equipamento.setEspecialidade(res.getString("desc_esp"));
				equipamento.setNumPatrimonio(res.getString("num_patrimonio"));
				equipamento.setDataCadastro(res.getString("data_cadastro"));
				lista.add(equipamento);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public ArrayList<Equipamento> buscaEquipamentoTodos() {
		ArrayList<Equipamento> lista = new ArrayList<Equipamento>();
		try {
			String sql = "SELECT e.\"idEquipamento\"  AS codigo, "
					+ "       e.\"tagEquipamento\" AS tag_equip, "
					+ "       e.descricao        AS desc_equipamento, "
					+ "       e.\"anoFabricacao\"  AS ano_fab, "
					+ "       e.peso, "
					+ "       e.modelo, "
					+ "       to_char(e.\"dataAquisicao\", 'dd/MM/yyyy')  AS data_aquisicao, "
					+ "       l.\"tagLocal\"       AS tag_local, "
					+ "       e.\"idLocal\"        AS id_local, "
					+ "       e.\"idTipo\"        AS id_tipo, "
					+ "       e.\"idFornecedor\"        AS id_fornecedor, "
					+ "       e.pais        AS desc_pais, "
					+ "       te.descricao       AS desc_tipo, "
					+ "       f.\"nomeFantasia\"   AS fantasia, "
					+ "       e.valor, "
					+ "       e.\"numSerie\"       AS num_serie, "
					+ "       e.\"numPatrimonio\"  AS num_patrimonio, "
					+ "       to_char(e.\"dataCadastro\", 'dd/MM/yyyy') AS data_cadastro, "
					+ "       esp.descricao as desc_esp "
					+ "FROM   equipamento e "
					+ "       INNER JOIN local_instalacao l "
					+ "               ON e.\"idLocal\" = l.\"idLocal\" "
					+ "       INNER JOIN fornecedor f "
					+ "               ON e.\"idFornecedor\" = f.\"idFornecedor\" "
					+ "       INNER JOIN tipo_equipamento te "
					+ "               ON e.\"idTipo\" = te.\"idTipo\" "
					+ "       INNER JOIN especialidade_equipamento ee "
					+ "               ON e.\"idEquipamento\" = ee.\"idEquipamento\" "
					+ "       INNER JOIN especialidade esp "
					+ "               ON ee.\"idEspecialidade\" = esp.\"idEspecialidade\" "+
					" order by 1 ";
					
			System.out.println(sql);
			ResultSet res = conexao.executarSQL(sql);
			while (res.next()) {
				Equipamento equipamento = new Equipamento();
				equipamento.setIdEquipamento(res.getInt("codigo"));
				equipamento.setTag(res.getString("tag_equip"));
				equipamento.setDescricao(res.getString("desc_equipamento"));
				equipamento.setAnoFabricacao(res.getInt("ano_fab"));
				equipamento.setPeso(res.getDouble("peso"));
				equipamento.setModelo(res.getString("modelo"));
				equipamento.setDataAquisicao(res.getString("data_aquisicao"));
				equipamento.setIdLocal(res.getInt("id_local"));
				equipamento.setLocal(res.getString("tag_local"));
				equipamento.setIdTipoEquipamento(res.getInt("id_tipo"));
				equipamento.setTipo(res.getString("desc_tipo"));
				equipamento.setIdFornecedor(res.getInt("id_fornecedor"));
				equipamento.setFantasia(res.getString("fantasia"));
				equipamento.setPais(res.getString("desc_pais"));
				equipamento.setValor(res.getDouble("valor"));
				equipamento.setNumSerie(res.getString("num_serie"));
				equipamento.setEspecialidade(res.getString("desc_esp"));
				equipamento.setNumPatrimonio(res.getString("num_patrimonio"));
				equipamento.setDataCadastro(res.getString("data_cadastro"));
				lista.add(equipamento);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public Equipamento buscaEquipamento(int idEquipamento) {
		Equipamento equipamento = new Equipamento();
		try {
			String sql = "SELECT e.\"idEquipamento\"  AS codigo, "
					+ "       e.\"tagEquipamento\" AS tag_equip, "
					+ "       e.descricao        AS desc_equipamento, "
					+ "       e.\"anoFabricacao\"  AS ano_fab, "
					+ "       e.peso, "
					+ "       e.modelo, "
					+ "       to_char(e.\"dataAquisicao\", 'yyyy-MM-dd')  AS data_aquisicao, "
					+ "       l.\"tagLocal\"       AS tag_local, "
					+ "       e.\"idLocal\"        AS id_local, "
					+ "       e.\"idTipo\"        AS id_tipo, "
					+ "       e.\"idFornecedor\"        AS id_fornecedor, "
					+ "       e.pais        AS desc_pais, "
					+ "       te.descricao       AS desc_tipo, "
					+ "       f.\"nomeFantasia\"   AS fantasia, "
					+ "       e.valor, "
					+ "       e.\"numSerie\"       AS num_serie, "
					+ "       e.\"numPatrimonio\"  AS num_patrimonio, "
					+ "       to_char(e.\"dataCadastro\", 'dd/MM/yyyy') AS data_cadastro, "
					+ "       esp.descricao as desc_esp "
					+ "FROM   equipamento e "
					+ "       INNER JOIN local_instalacao l "
					+ "               ON e.\"idLocal\" = l.\"idLocal\" "
					+ "       INNER JOIN fornecedor f "
					+ "               ON e.\"idFornecedor\" = f.\"idFornecedor\" "
					+ "       INNER JOIN tipo_equipamento te "
					+ "               ON e.\"idTipo\" = te.\"idTipo\" "
					+ "       INNER JOIN especialidade_equipamento ee "
					+ "               ON e.\"idEquipamento\" = ee.\"idEquipamento\" "
					+ "       INNER JOIN especialidade esp "
					+ "               ON ee.\"idEspecialidade\" = esp.\"idEspecialidade\" "+
					" WHERE e.status_ativo = 'true' and e.\"idEquipamento\" = "+idEquipamento;
					
			System.out.println(sql);
			ResultSet res = conexao.executarSQL(sql);
			while (res.next()) {
				equipamento.setIdEquipamento(res.getInt("codigo"));
				equipamento.setTag(res.getString("tag_equip"));
				equipamento.setDescricao(res.getString("desc_equipamento"));
				equipamento.setAnoFabricacao(res.getInt("ano_fab"));
				equipamento.setPeso(res.getDouble("peso"));
				equipamento.setModelo(res.getString("modelo"));
				equipamento.setDataAquisicao(res.getString("data_aquisicao"));
				equipamento.setIdLocal(res.getInt("id_local"));
				equipamento.setLocal(res.getString("tag_local"));
				equipamento.setIdTipoEquipamento(res.getInt("id_tipo"));
				equipamento.setTipo(res.getString("desc_tipo"));
				equipamento.setIdFornecedor(res.getInt("id_fornecedor"));
				equipamento.setFantasia(res.getString("fantasia"));
				equipamento.setPais(res.getString("desc_pais"));
				equipamento.setValor(res.getDouble("valor"));
				equipamento.setNumSerie(res.getString("num_serie"));
				equipamento.setEspecialidade(res.getString("desc_esp"));
				equipamento.setNumPatrimonio(res.getString("num_patrimonio"));
				equipamento.setDataCadastro(res.getString("data_cadastro"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return equipamento;
	}
	
	public ArrayList<Fornecedor> buscaFabricante() {
		ArrayList<Fornecedor> lista = new ArrayList<Fornecedor>();
		try {
			String sql = "SELECT \"idFornecedor\" as codigo, \"nomeFantasia\" as fantasia FROM fornecedor order by 1";
			System.out.println(sql);
			ResultSet res = conexao.executarSQL(sql);
			while (res.next()) {
				Fornecedor fornecedor = new Fornecedor();
				fornecedor.setIdFornecedor(res.getInt("codigo"));
				fornecedor.setNomeFantasia(res.getString("fantasia"));
				lista.add(fornecedor);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public ArrayList<TipoEquipamento> buscaTipo() {
		ArrayList<TipoEquipamento> lista = new ArrayList<TipoEquipamento>();
		try {
			String sql = "select \"idTipo\" as codigo, descricao as desc_tipo from tipo_equipamento order by 1";
			System.out.println(sql);
			ResultSet res = conexao.executarSQL(sql);
			while (res.next()) {
				TipoEquipamento tipo = new TipoEquipamento();
				tipo.setIdTipo(res.getInt("codigo"));
				tipo.setDescricaoTipo(res.getString("desc_tipo"));
				lista.add(tipo);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public TipoEquipamento buscaTipo(int idTipo) {
		TipoEquipamento tipo = new TipoEquipamento();
		try {
			String sql = "select \"idTipo\" as codigo, descricao as desc_tipo from tipo_equipamento where \"idTipo\"="+idTipo;
			System.out.println(sql);
			ResultSet res = conexao.executarSQL(sql);
			while (res.next()) {
				//tipo = new TipoEquipamento();
				tipo.setIdTipo(res.getInt("codigo"));
				tipo.setDescricaoTipo(res.getString("desc_tipo"));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return tipo;
	}
	
	public ArrayList<HashMap<String, String>> buscaEspecialidade() {
		ArrayList<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "SELECT \"idEspecialidade\" as codigo, descricao as desc_esp FROM especialidade order by 1;";
			System.out.println(sql);
			ResultSet res = conexao.executarSQL(sql);
			while (res.next()) {
				HashMap<String, String> hash = new HashMap<String, String>();
				hash.put("codigo", res.getString("codigo"));
				hash.put("desc_esp", res.getString("desc_esp"));
				lista.add(hash);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public ArrayList<LocalInstalacao> buscaLocal() {
		ArrayList<LocalInstalacao> lista = new ArrayList<LocalInstalacao>();
		//LocalInstalacao local = new LocalInstalacao();
		try {
			String sql = "SELECT \"idLocal\" as codigo, \"tagLocal\" as tag_local, descricao as desc_local FROM local_instalacao order by 1;";
			System.out.println(sql);
			ResultSet res = conexao.executarSQL(sql);
			while (res.next()) {
				LocalInstalacao local = new LocalInstalacao();
				local.setIdLocal(res.getInt("codigo"));
				local.setTagLocal(res.getString("tag_local"));
				local.setDescricao(res.getString("desc_local"));
				lista.add(local);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
}
