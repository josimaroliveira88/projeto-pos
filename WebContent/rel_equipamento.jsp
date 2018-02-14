<!DOCTYPE HTML>
<%@page import="classes.TipoEquipamento"%>
<%@page import="classes.LocalInstalacao"%>
<%@page import="classes.servico.ServicoLocalInstalacao"%>
<%@page import="classes.servico.ServicoEquipamento"%>
<%@page import="java.util.ArrayList"%>
<%@page import="classes.Equipamento"%>
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
	Equipamento equipamento = new Equipamento();
	ServicoEquipamento servico = new ServicoEquipamento();
	ArrayList<Equipamento> lista = new ArrayList<Equipamento>();
	boolean ativo = true;
	String statusDescricao = "";
	if(request.getParameter("status") != null){
		if(request.getParameter("status").equals("ativo")){
			statusDescricao = "Ativos";
			lista = servico.listaEquipamento();
		}else if(request.getParameter("status").equals("inativo")){
			statusDescricao = "Inativos";
			lista = servico.listaEquipamentoInativo();
		}else{
			statusDescricao = "";
			lista = servico.listaEquipamentoTodos();
		}
	}else{
		statusDescricao = "Ativos";
		lista = servico.listaEquipamentoTodos();
	}
	
	//Carrega as listas para preencher as tags select
	/*ServicoLocalInstalacao localServico = new ServicoLocalInstalacao();
    ArrayList<EquipeManutencao> equipe = localServico.listaEquipe();
    ArrayList<LocalInstalacao> locais = localServico.listaLocalInstalacao();
    LocalInstalacao local = new LocalInstalacao();
    if (request.getAttribute("local") == null){
     	local = new LocalInstalacao();
     	local = localServico.limparLocal();
    }else{
     	local = (LocalInstalacao)request.getAttribute("local");
    }*/
%>
<head>
  <title>Relatório de Equipamentos</title>
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
          <h1><a href="index.jsp">SIS<span class="logo_colour">GerPCM</span></a></h1>
          <h2>Relatório de Equipamentos</h2>
        </div>
      </div>
      <nav>
        <ul class="sf-menu" id="nav">
          <li><a href="inicio.jsp">Iní­cio</a></li>
          <li><a href="#">Cadastros</a>
            <ul>
              <li><a href="equipamentos.jsp">Equipamentos</a></li>
              <li><a href="localinstalacao.jsp">Local de Instalação</a>
              </li>
            </ul>
          </li>
          <li class="selected"><a href="#">Relatórios</a>
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
      <%
      
      %>
      <div class="content">
      <table class="tabela"><tr>
      	<td><p><a href="EquipamentoControle?status=ativo">Listar Ativos</a></p></td>
      	<td><p><a href="EquipamentoControle?status=inativo">Listar Inativos</a></p></td>
      	<td><p><a href="EquipamentoControle?status=todos">Listar Todos</a></p></td>
      </tr></table>
        <h1>Relatório de Equipamentos <%out.println(statusDescricao);%> </h1>
        
        <table class="trelatorio">
	        <tr>
	        	<th>Cod</th>
	        	<th>TAG</th>
	        	<th>Desc</th>
	        	<th>Mod</th>
	        	<th>Ano Fabr.</th>
	        	<th>Aquisicao</th>
	        	<th>Local Inst</th>
	        	<th>Tipo</th>
	        	<th>Fornecedor</th>
	        	<th>Valor</th>
	        	<th>Serie</th>
	        	<th>Patrimonio</th>
	        	<th>Pais</th>
	        </tr>
        	<%
        	for(Equipamento e:lista){
        		
        	%>
        	
          <tr><td><%out.println(e.getIdEquipamento()); %></td>
          		<td><%out.println(e.getTag()); %></td>
          		<td><%out.println(e.getDescricao()); %></td>
          		<td><%out.println(e.getModelo()); %></td>
          		<td><%out.println(e.getAnoFabricacao()); %></td>
          		<td><%out.println(e.getDataAquisicao()); %></td>
          		<td><%out.println(e.getLocal()); %></td>
          		<td><%out.println(e.getTipo()); %></td>
          		<td><%out.println(e.getFantasia()); %></td>
          		<td><%out.println(e.getValor()); %></td>
          		<td><%out.println(e.getNumSerie()); %></td>
          		<td><%out.println(e.getNumPatrimonio()); %></td>
          		<td><%out.println(e.getPais()); %></td>
          </tr>
          <%}%>
        </table>
        
      </div>
    </div>
    
    <footer>
      <p>Trabalho apresentado à UNOPAR, como requisito parcial para a obtenção do título de Especialização em Tecnologias para Aplicações Web.</p>
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
