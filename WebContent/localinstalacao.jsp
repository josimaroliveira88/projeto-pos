<!DOCTYPE HTML>
<%@page import="classes.servico.ServicoEquipeManutencao"%>
<%@page import="classes.EquipeManutencao"%>
<%@page import="org.joda.time.DateTime"%>
<%@page import="classes.servico.ServicoLocalInstalacao"%>
<%@page import="classes.Funcionario"%>
<%@page import="classes.Fornecedor"%>
<%@page import="classes.TipoEquipamento"%>
<%@page import="classes.LocalInstalacao"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="classes.servico.ServicoEquipamento"%>
<%@page import="classes.Equipamento"%>
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
		//Carrega as listas para preencher as tags select
		ServicoLocalInstalacao localServico = new ServicoLocalInstalacao();
        ArrayList<EquipeManutencao> equipe = localServico.listaEquipe();
        ArrayList<LocalInstalacao> locais = localServico.listaLocalInstalacao();
        LocalInstalacao local = new LocalInstalacao();
        if (request.getAttribute("local") == null){
        	local = new LocalInstalacao();
        	local = localServico.limparLocal();
        }else{
        	local = (LocalInstalacao)request.getAttribute("local");
        }
	%>
<head>
  <title>Cadastro de Local de Instalação</title>
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
          <h2>Tela para Cadastro de Locais de Instalação</h2>
        </div>
      </div>
      <nav>
        <ul class="sf-menu" id="nav">
          <li><a href="inicio.jsp">Iní­cio</a></li>
          <li class="selected"><a href="#">Cadastros</a>
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
          
        </div>
      </div>
      <div class="content">
        <h2>Cadastro de Locais de Instalação</h2>
        <form action="LocalInstalacaoControle" method="post">
        	<input type="hidden" name="codigo" value="<%=local.getIdLocal()%>">
          <div class="form_settings">
			<table class="tabela">
				<tr>
				<td><p>Código</p><input class="texto" type="text" size="4" name="cod" value="<%=local.getIdLocal() %>" disabled="disabled" required="required" /></td>
				<td><p>TAG</p><input type="text" size="22" name="tag" value="<%=local.getTagLocal() %>" required="required" autofocus="autofocus" /></td>
				</tr>
				<tr>
				<td colspan="2"><p>Descrição</p><input type="text" size="40" name="descricao" value="<%=local.getDescricao() %>" required="required" /></td>
				<td><p>Equipe Responsável</p>
	        	<select name="equipe" id="equipe" required="required">
	        	<%
	        		if(local.getIdLocal() == 0 || local == null){
	        	%>
	            		<option selected="selected" value="">Selecione uma equipe</option>
	            	<%
	            		for(EquipeManutencao e:equipe){ 
            		%>
            				<option value=<%out.println(e.getIdEquipe());%>><%out.println(e.getDescricao()); %></option>
            		<%			
	            		}
	        		}else{
	            		for(EquipeManutencao e:equipe){ 
	            			if(local.getIdEquipe() == e.getIdEquipe()){%>
	            				<option selected="selected" value=<%out.println(e.getIdEquipe());%>><%out.println(e.getDescricao()); %></option>
	            	<%
	            			}else{%>
	            				<option value=<%out.println(e.getIdEquipe());%>><%out.println(e.getDescricao()); %></option>
	            	<%		}
	            		}
	        		}
	            	%>
	            </select></td>
				</tr>
	            
	            <tr>
	            <td><input class="submit" type="submit" name="action" value="Salvar" /></td>
<!-- 	            <td colspan="5"><input class="submit" type="reset" name="action" onclick="limpar()" value="Cancelar" /></td> -->
	            </tr>
	        </table>
          </div>
        </form>
        
        <h2>Locais de Instalação</h2>
        <div style="height: 300px; overflow: auto;"><table style="width:100%; border-spacing:0;">
          <tr><th>Código</th><th>TAG</th><th>Descrição</th><th>Equipe</th>
          <%
          for(LocalInstalacao l:locais){
        	  ServicoEquipeManutencao equipeServico = new ServicoEquipeManutencao();
        	  EquipeManutencao e = new EquipeManutencao();
        	  e = equipeServico.listaEquipe(l.getIdEquipe());
          %>
          <tr><td align="center"><%out.println(l.getIdLocal()); %></td>
          		<td style="width:100px;"><%out.println(l.getTagLocal()); %></td>
          		<td style="width:200px;"><%out.println(l.getDescricao()); %></td>
          		<td style="width:250px;"><%out.println(e.getDescricao()); %></td>
          		<td><a href="LocalInstalacaoControle?action=editar&codigo=<%out.println(l.getIdLocal()); %>" >Editar</a></td>
          		<td><a href="LocalInstalacaoControle?action=excluir&codigo=<%out.println(l.getIdLocal()); %>" >Excluir</a></td>
          </tr>
          <%} %>
        </table></div>
        

      </div>
    </div>
    <footer>
      <p>Trabalho apresentado à UNOPAR, como requisito parcial para a obtenção do título de Especialização em Tecnologias para Aplicações Web.</p>
    </footer>
  </div>
  <p>&nbsp;</p>
  <!-- javascript at the bottom for fast page loading -->
  <%if(request.getAttribute("retorno")!= null){%>
  <script type="text/javascript">alert('<%out.print(request.getAttribute("retorno"));%>')</script>
  <%} %>
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
    
    function confirmacao(){	
    	if(confirm("Tem certeza que deseja excluir?") == false){
    		alert("Exclusão cancelada.");
    	}
    };
  </script>
</body>
</html>