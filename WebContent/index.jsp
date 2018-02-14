<!DOCTYPE html>
<%
		String autenticado;
		//Verifica se está logado
		//if (session.getValue("loginUsuario") != null
		//		|| session.getValue("senhaUsuario") != null) {
		if (session.getAttribute("loginUsuario") != null) {
			out.println("<script>document.location.href='inicio.jsp';</script>"); //Exibe um código javascript para redireionar ao painel
		}
		
	%>
<html lang='pt_BR'>
<head>
<meta charset="UTF-8">
<!-- Basics -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Login</title>

<!-- CSS -->
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/animate.css">
<link rel="stylesheet" href="css/estilo.css">
</head>
<body>
	<!-- Begin Page Content -->

	<div id="container">

		<form name="login" method="post" action="logar.jsp">
			<label for="usuario">Usuário:</label> 
			<input type="text" id="usuario" autofocus name="usuario"> 
			<label for="senha">Senha:</label>
			
			<input type="password" id="senha" name="senha">
			<%
			String statusAutenticacao = request.getParameter("autenticado"); 
			if (statusAutenticacao != null){
				switch (Integer.parseInt(statusAutenticacao)) {
				case 0:
					out.print("<p class='invalido'>Login ou senha inválidos</p>");
					break;

				case 1:
					out.print("<p class='invalido'>Houve um problema ao conectar com o banco de dados!</p>");
					break;

				default:
					break;
				}
				if(Integer.parseInt(statusAutenticacao) == 0){
					out.print("<p class='invalido'>Login ou senha inválidos</p>");
				}
			}
			
			%>
			<div id="lower">
				<input type="submit" value="Entrar">
			</div><!--/ lower-->

		</form>

	</div><!--/ container-->
</body>
</html>