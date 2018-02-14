package classes.dao;
//Controle de sessão adapatado conforme tutorial desse site: http://www.cauirs.com.br/rafael/?p=119
import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgresql.util.PSQLException;

import classes.Funcionario;
import classes.conexao.Conexao;

public class UsuarioDao {
	Conexao conexao;// = new Conexao();
	Funcionario funcionario = new Funcionario();
	public Funcionario autenticar(Funcionario usuario) throws PSQLException{
		String sql;
		
		conexao = new Conexao();
		try {
			sql = "SELECT \"idFuncionario\", nome, usuario, senha FROM funcionario where usuario=" +
					 conexao.prepararString(usuario.getUsuario())+" and senha=" +
					 conexao.prepararString(usuario.getSenha())+";";
			ResultSet res = conexao.executarSQL(sql);
			
			while (res.next()){
				funcionario.setNome(res.getString("nome"));
				funcionario.setIdFuncionario(res.getInt("idFuncionario"));
				funcionario.setUsuario(res.getString("usuario"));
				funcionario.setSenha(res.getString("senha"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro na consulta: "+e);
		}
		return funcionario;
	}
}
