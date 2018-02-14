package classes.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.postgresql.util.PSQLException;
import org.postgresql.util.PSQLState;

public class Conexao {
	private Connection con = null;
	private Statement stm = null;
	private String jdbc = "jdbc:postgresql://";
	private String host = "localhost:5432/";
	private String banco = "unopar";
	private String usuario = "postgres";
	private String senha = "123456";
	public Conexao() throws PSQLException{
		try{
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(jdbc+host+banco, usuario, senha);
			stm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			con.setAutoCommit(false);
			//JOptionPane.showMessageDialog(null, "Conexão efetuada com sucesso");
		}catch (PSQLException e) {
//			throw new PSQLException("Erro ao conectar com o banco de dados.", e.getSQLState(), e.getCause());
			throw new PSQLException("", new PSQLState(e.getSQLState()), e.getCause());
		}catch (ClassNotFoundException e) {
			Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, e);
		}catch (SQLException e) {
			e.printStackTrace();
			//JOptionPane.showMessageDialog(null, "Falha na conexão com o banco de dados. Mensagem: "+e);
			
		}
	}
	
	public String Conecta(){
		try{
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(jdbc+host+banco, usuario, senha);
			stm = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			con.setAutoCommit(false);
			//JOptionPane.showMessageDialog(null, "Conexão efetuada com sucesso");
			return "";
		}catch (ClassNotFoundException e) {
			Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, e);
			return e.getMessage();
		}catch (SQLException e) {
			e.printStackTrace();
			//JOptionPane.showMessageDialog(null, "Falha na conexão com o banco de dados. Mensagem: "+e);
			return e.getMessage();
			
		}
	}
	
	public Connection retornaConexao(){
		return con;
	}
	public String prepararString(String texto){
		return "'"+texto+"'";
	}
	
	public ResultSet executarSQL(String sql){
		ResultSet res = null;
		try {
			res = stm.executeQuery(sql);
		} catch (SQLException e) {
			//JOptionPane.showMessageDialog(null, "Falha na conexão com o banco de dados. Mensagem: "+e);
			System.out.println("Mensagem aqui: "+e);
			e.printStackTrace();	
		}
		return res;
	}
	
	public boolean executarInsercao(String sql){
		boolean res = false;
		try {
			stm.execute(sql);
			if (stm.getUpdateCount()>0)//Verifica a quantidade de linhas afetadas e retorna verdadeiro se pelo menos uma foi afetada
				res = true;
			else
				res = false;
		} catch (SQLException e) {
			//JOptionPane.showMessageDialog(null, "Erro: " + e);
			System.out.println("Mensagem aqui: "+e);
			e.printStackTrace();
		}
		return res;
	}
	
	public boolean novaInsercao(PreparedStatement pst){
		boolean res = false;
		try{
			pst.executeUpdate();
			System.out.println("Linhas na inserção (com pst): "+pst.getUpdateCount());
			if (pst.getUpdateCount()>0)
				res = true;
			else
				res = false;
		} catch(SQLException e) {
			System.out.println("Erro: "+e);
			e.printStackTrace();
		}
		return res;
	}
	
	public String executarConsulta(String sql){
		try {
			stm.executeQuery(sql);
			return "Sucesso";
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Mensagem: "+e);
			return "Mensagem: "+e;
		}
	}
	
	public int executarAtualizacao(String sql){
		int retorno;
		try {
			retorno = stm.executeUpdate(sql);
			return retorno;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Mensagem: "+e);
			return retorno = 0;
		}
	}
	
	public String sqlInicio(){
		return "select * from ";
	}
	
	public boolean confirmacao(boolean confirma) throws SQLException{
		boolean retorno = false;
		if (confirma == true){
			con.commit();
			retorno = true;
		}
		else{
			con.rollback();
			retorno = false;
		}
		return retorno;
	}
}
