package classes.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.postgresql.util.PSQLException;

import classes.CentroCusto;
import classes.conexao.Conexao;

public class CentroCustoDao {
	Conexao conexao;

	public boolean inserirCentroCusto(CentroCusto centroCusto) throws PSQLException {
		conexao = new Conexao();
		// Insere as informações no banco de dados
		boolean retorno = false;
		String sql;
		sql = "select * from centro_custo where \"centroCusto\"="
				+ conexao.prepararString(centroCusto.getCodCentro());
		System.out.println(sql);
		ResultSet res = conexao.executarSQL(sql);

		try {
			if (res.next()) {
				System.out.println("Quantidade retorno " + res.getFetchSize());
				retorno = false;
			} else {
				sql = "INSERT INTO centro_custo(descricao, \"centroCusto\", \"dataCadastro\") VALUES ("
						+ conexao.prepararString(centroCusto.getDescricao())
						+ ", "
						+ conexao.prepararString(centroCusto.getCodCentro())
						+ ", "
						+ conexao.prepararString(centroCusto.getDataCadastro())
						+ ");";
				System.out.println(sql);
				retorno = conexao.executarInsercao(sql);
				if (retorno == true) {
					conexao.confirmacao(retorno);
				} else {
					conexao.confirmacao(retorno);
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retorno;
	}

	public int atualizarCentroCusto(CentroCusto centroCusto) {
		int retorno = 0;
		String sql = "UPDATE centro_custo SET " + "descricao="
				+ conexao.prepararString(centroCusto.getDescricao()) + ", "
				+ "\"centroCusto\"="
				+ conexao.prepararString(centroCusto.getCodCentro()) + ", "
				+ "\"dataCadastro\"="
				+ conexao.prepararString(centroCusto.getDataCadastro())
				+ " WHERE \"idCentro\"=" + centroCusto.getIdCentro() + ";";
		retorno = conexao.executarAtualizacao(sql);

		try {
			if (retorno > 0) {
				conexao.confirmacao(true);
			} else {
				conexao.confirmacao(false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retorno;
	}

	public int excluirCentroCusto(CentroCusto centroCusto) {
		int retorno = 0;
		try {
			String sql = "select * from centro_custo_orcamento where \"idCentro\"="
					+ centroCusto.getIdCentro();
			ResultSet res = conexao.executarSQL(sql); // Primeiro verifica se
														// existe algum
														// orçamento vinculado
														// ao centro de custo a
														// ser excluído
			if (res.getFetchSize() == 0) { // Caso exista o centro de custo não
											// será excluído
				sql = "DELETE FROM centro_custo cc "
						+ "WHERE  cc.\"idCentro\" = "
						+ centroCusto.getIdCentro();
				retorno = conexao.executarAtualizacao(sql);
				if (retorno > 0) {
					conexao.confirmacao(true);
				} else {
					conexao.confirmacao(false);
				}
				return retorno;
			} else
				return retorno;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return retorno;
		}
	}

	public ArrayList<HashMap<String, String>> buscaCentroCusto() {
		ArrayList<HashMap<String, String>> lista = new ArrayList<HashMap<String, String>>();
		try {
			String sql = "SELECT cc.\"idCentro\" as codigo, "
					+ "cc.descricao, "
					+ "cc.\"centroCusto\" as centro_custo, "
					+ "to_char(cc.\"dataCadastro\", 'dd/mm/yyyy') as data_cadastro "
					+ " FROM centro_custo cc " + "order by 1 ";
			ResultSet res = conexao.executarSQL(sql);
			while (res.next()) {
				HashMap<String, String> hmCentroCusto = new HashMap<String, String>();
				hmCentroCusto.put("codigo", res.getString("codigo"));
				hmCentroCusto.put("descricao", res.getString("descricao"));
				hmCentroCusto
						.put("centro_custo", res.getString("centro_custo"));
				hmCentroCusto.put("data_cadastro",
						res.getString("data_cadastro"));
				lista.add(hmCentroCusto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
}
