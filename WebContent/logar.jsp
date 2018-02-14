
<%@page import="org.postgresql.util.PSQLException"%>
<%@page import="javax.swing.JOptionPane"%>
<%@page import="classes.dao.UsuarioDao"%>
<%@page import="classes.Funcionario"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Sistema de Login :: JSP</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<style>
body, td, a:link, a:visited {
	font-family: Verdana;
	font-size: 10px;
	color: #000000;
	text-decoration: none;
}

a:hover {
	color: #FF0000;
}

input {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-size: 10px;
	background-color: #FFFFFF;
	border: 1px solid #000000;
}
</style>
</head>
<body>
	<%
		String login_form = request.getParameter("usuario"); // Pega o Login vindo do formulário
		String senha_form = request.getParameter("senha"); //Pega a senha vinda do formulário
		Funcionario usuario = new Funcionario();
		UsuarioDao usuarioDao = new UsuarioDao();
		usuario.setUsuario(login_form);
		usuario.setSenha(senha_form);
		
		try{
			usuario = usuarioDao.autenticar(usuario);
		}catch (PSQLException e){
			out.println("<script>document.location.href='index.jsp?autenticado=1';</script>"); //Exibe um código javascript para redireionar ao painel
		}finally{
			if (usuario.getIdFuncionario() > 0){
			
			//if (login_form.equals(usuario) && senha_form.equals(senha)) { //Caso login e senha estejam corretos...
				out.println("Logado com sucesso."); //Mostra na tela que foi logado com sucesso
				session.setAttribute("loginUsuario", usuario); //Grava a session com o Nome
				//session.putValue("senhaUsuario", senha); //Grava a session com a Senha
				out.println("<script>document.location.href='inicio.jsp';</script>"); //Exibe um código javascript para redireionar ao painel
			} else { //Se estiverem incorretos...
				out.println("<script>document.location.href='index.jsp?autenticado=0';</script>"); //Exibe um código javascript para redireionar ao painel
			}
		}
	%>
	<br />
	<br />
</body>
</html>