<%--@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    --%>
<!DOCTYPE html>
<%@page import="java.sql.*"%>
<%@page import="classes.conexao.Conexao"%>
<%
	
%>
<html lang='pt_BR'>
<head>
<meta charset="UTF-8">
<title>SisGerPCM</title>
<%
	/*out.println("Teste servidor tomcat <br>");
	String retorno;
	Conexao conexao = new Conexao();
	retorno = conexao.Conecta();
	Connection con;
	con = conexao.retornaConexao();
	if (retorno != "") {
		out.println("Erro na conex�o com o banco de dados: " + retorno);
	} else {
		out.println("Conex�o efetuada com sucesso");
	}*/
	/* 
	 Statement sta;
	 String jdbc = "jdbc:postgresql://";
	 String host = "localhost:5432/";
	 String banco = "unopar";
	 String usuario = "postgres";
	 String senha = "123456";

	 try{
	 Class.forName("org.postgresql.Driver");
	 con = DriverManager.getConnection(jdbc+host+banco, usuario, senha);
	 sta = con.createStatement();
	 out.println("Conex�o efetuada com sucesso");
	 } catch (Exception e) {
	 out.println("N�o foi poss�vel efetuar a conex�o: " + e);
	 } */
%>
</head>
<body>

</body>
</html>