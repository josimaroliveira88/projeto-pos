<!DOCTYPE HTML>
<%@page import="teste.Arquivo"%>
<%@page import="classes.Funcionario"%>
<html lang='pt_BR'>
<%
		//Verifica se está logado
		//if (session.getValue("loginUsuario") != null
		//		|| session.getValue("senhaUsuario") != null) {
		Funcionario usuario = (Funcionario)session.getAttribute("loginUsuario");
		if (session.getAttribute("loginUsuario") != null) {
			out.println("<p class='cabecalho'>Você está logado no sistema como "
					+ usuario.getNome() + "</p>");
					//+ ". Clique <a href='logoff.jsp'>aqui</a> para sair do sistema");
		} else {
			out.println("<script>document.location.href='logado.jsp';</script>");
		}
		Arquivo arquivo = new Arquivo();
		arquivo.processa();
	%>
<head>
  <title>SisGerPCM</title>
  <meta name="description" content="website description" />
  <meta name="keywords" content="website keywords, website keywords" />
  <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
  <link rel="stylesheet" type="text/css" href="css/style.css" />
  <!-- modernizr enables HTML5 elements and feature detects -->
  <script type="text/javascript" src="js/modernizr-1.5.min.js"></script>
</head>

<body>
  <div id="main">
    <header>
      <div id="logo">
        <div id="logo_text">
          <!-- class="logo_colour", allows you to change the colour of the text -->
          <h1><a href="inicio.jsp">SIS<span class="logo_colour">GerPCM</span></a></h1>
          <h2>Sistema para cadastros de Equipamentos e Locais de Instalação</h2>
        </div>
      </div>
      <nav>
        <ul class="sf-menu" id="nav">
          <li class="selected"><a href="inicio.jsp">Iní­cio</a></li>
          <li><a href="#">Cadastros</a>
            <ul>
              <li><a href="equipamentos.jsp">Equipamentos</a></li>
              <li><a href="localinstalacao.jsp">Local de Instalação</a>
              </li>
            </ul>
          </li>
          <li><a href="#">Relatórios</a>
            <ul>
              <li><a href="EquipamentoControle?status=ativo">Equipamentos</a></li>
              <li><a href="LocalInstalacaoControle?status=ativo">Local de Instalação</a>
              </li>
            </ul>
          </li>
          <li><a href="logoff.jsp">Sair</a></li>
        </ul>
      </nav>
    </header>
    <div id="site_content">
      <ul id="images">
        <li><img src="images/manutencao1.jpg" width="600" height="300" alt="seascape_one" /></li>
        <li><img src="images/manutencao2.jpg" width="600" height="300" alt="seascape_two" /></li>
        <li><img src="images/manutencao3.jpg" width="600" height="300" alt="seascape_three" /></li>
        <li><img src="images/manutencao4.jpg" width="600" height="300" alt="seascape_four" /></li>
        <li><img src="images/manutencao5.jpg" width="600" height="300" alt="seascape_five" /></li>
        <li><img src="images/manutencao6.jpg" width="600" height="300" alt="seascape_seascape" /></li>
      </ul>
      <div id="sidebar_container">
        <div class="sidebar">
          <h3>Cadastros e Relatórios</h3>
          <h4>Cadastro de Equipamentos</h4>
          <p>Efetue o cadastro dos equipamentos da sua empresa aqui.<br /><a href="equipamentos.jsp">Continuar...</a></p>
          <h4>Cadastros de Local de Instalação</h4>
          <p>Efetue o cadastro dos locais onde os seus equipamentos serão alocados.<br /><a href="localinstalacao.jsp">Continuar...</a></p>
          <h4>Relatório de Equipamentos</h4>
          <p>Lista por ordem de código de todos os equipamentos, podendo separar também por ativos e inativos.<br /><a href="EquipamentoControle?status=ativo">Continuar...</a></p>
          <h4>Relatório de Local de Instalação</h4>
          <p>Lista por ordem de código de todos os Locais de instalação, podendo separar também por ativos e inativos.<br /><a href="LocalInstalacaoControle?status=ativo">Continuar...</a></p>
        </div>
      </div>
      <div class="content">
        <h1>Bem vindo <strong><%if(session.getAttribute("loginUsuario")!=null) out.print(usuario.getNome()); %></strong>, ao Sistema para cadastro de equipamentos e locais de instalação</h1>
        <p>Através dessa ferramenta, será possível efetuar o cadastro dos equipamentos que fazem parte da empresa e que serão 
           objeto de manutenção. Quanto mais informações sobre o equipamento forem inseridas, mais prático será para o manutentor realizar o serviço.</p>
        <p>Também será possível efetuar o cadastro dos locais de instalação. <strong>Local de Instalação</strong> é o setor ou departamento onde um equipamento está alocado.
           Caso seja uma empresa pequena e não possua muitos equipamentos, basta cadastrar apenas um Local. No entanto, para grandes empresas, é interessante separar os equipamentos por setores
           para facilitar a divisão dos serviços a diversos manutentores. <strong>Não é possível cadastrar um equipamento sem que haja ao menos um Local de Instalação para que seja alocado.</strong></p>
        <p>Por fim, temos os relatórios. Os relatórios são extremamente simples, conforme abaixo:</p>
        <ul>
          <li>Lista por ordem de código de todos os equipamentos, podendo separar também por ativos e inativos.</li>
          <li>Lista por ordem de código de todos os Locais de instalação, podendo separar também por ativos e inativos.</li>
        </ul>
      </div>
    </div>
    <footer>
      <p>Trabalho apresentado à UNOPAR, como requisito parcial para a obtenção do título de Especialização em Tecnologias para Aplicações Web.
      </p>
    </footer>
  </div>
  <p>&nbsp;</p>
  <!-- javascript at the bottom for fast page loading -->
  <script type="text/javascript" src="js/jquery.js"></script>
  <script type="text/javascript" src="js/jquery.easing-sooper.js"></script>
  <script type="text/javascript" src="js/jquery.sooperfish.js"></script>
  <script type="text/javascript" src="js/jquery.kwicks-1.5.1.js"></script>
  <script type="text/javascript">
    $(document).ready(function() {
      $('#images').kwicks({
        max : 600,
        spacing : 2
      });
      $('ul.sf-menu').sooperfish();
    });
  </script>
</body>
</html>
