<!DOCTYPE HTML>
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
		ServicoEquipamento equipamentoServico = new ServicoEquipamento();
        ArrayList<LocalInstalacao> local = equipamentoServico.listaLocal();
        ArrayList<TipoEquipamento> tipo = equipamentoServico.listaTipo();
        ArrayList<Fornecedor> fornecedor = equipamentoServico.listaFabricante();
        ArrayList<Equipamento> equipamento = equipamentoServico.listaEquipamento();
        Equipamento equip = new Equipamento();
        if (request.getAttribute("equipamento") == null){
        	equip = new Equipamento();
        	equip = equipamentoServico.limparEquipamento();
     %>
       <script>
        	document.getElementById("cod").value="";
        	//document.getElementsByName("ano_fabricacao").value="";
        	document.getElementById("ano_fabricacao").value="";
        	document.getElementById("peso").value="";
        	document.getElementById("valor").value="";
        	</script>	
     <%   }else{
        	equip = (Equipamento)request.getAttribute("equipamento");
        }
	%>
<head>
  <title>Cadastro de equipamentos</title>
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
          <h2>Tela para Cadastro de Equipamentos</h2>
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
        <%
        DateTime max = equipamentoServico.retornaData();
        %>
        <h2>Cadastro de Equipamentos</h2>
        <form action="EquipamentoControle" method="post">
        	<input type="hidden" name="codigo" value="<%=equip.getIdEquipamento()%>">
          <div class="form_settings">
			<table class="tabela">
				<tr>
				<td><p>Tag</p><input type="text" size="6" name="tag" value="<%=equip.getTag() %>" required="required" autofocus="autofocus" /></td> 
				<td colspan="2"><p>Descrição</p><input type="text" size="50" name="descricao" value="<%=equip.getDescricao() %>" required="required" /></td>
				<td><p>Modelo</p><input type="text" size="20" name="modelo" value="<%=equip.getModelo() %>" required="required" /></td>
				<td colspan="2"><p>Código</p><input class="texto" type="text" size="10" id="cod" name="cod" disabled="disabled" value="<%=equip.getIdEquipamento() %>"/></td>
				</tr>
	            
	            <tr>
	            <td><p>Nro de Série</p><input type="text" size="10" name="serie" value="<%=equip.getNumSerie() %>" /></td>
	            <td><p>Número Patrimônio</p><input type="text" size="10" id="patrimonio" name="patrimonio" value="<%=equip.getNumPatrimonio() %>" /></td>
	            <td><p>Data de Aquisição</p><input type="date" size="4"  name="data_aquisicao" min="1901-01-01" value="<%=equip.getDataAquisicao() %>" required="required" /></td>
	            <td><p>Ano de Fabricação</p><input type="number" style="width:100px;" id="ano_fabricacao" name="ano_fabricacao" min="1901" max="<%out.println(max.getYear());%>" value="<%=equip.getAnoFabricacao() %>"  required="required" /></td>
	            <td colspan="2"><p>País</p><input type="text" size="10" name="pais" value="<%=equip.getPais() %>" /></td>
	            </tr>
	            
	            <tr>
	            <td><p>Peso</p><input type="number" style="width: 100px;" id="peso" name="peso" min="0" step="0.01" value="<%=equip.getPeso() %>" /></td>
	            <td><p>Local Instalação</p>
	        	<select name="local" id="local"  required="required">
	        	<%
	        		if(equip.getIdEquipamento() == 0 || equip == null){
	        	%>
	            		<option selected="selected" value="">Selecione um local</option>
	            	<%
	            		for(LocalInstalacao l:local){ 
            		%>
            				<option value=<%out.println(l.getIdLocal());%>><%out.println(l.getTagLocal()); %></option>
            		<%			
	            		}
	        		}else{
	            		for(LocalInstalacao l:local){ 
	            			if(equip.getIdLocal() == l.getIdLocal()){%>
	            				<option selected="selected" value=<%out.println(l.getIdLocal());%>><%out.println(l.getTagLocal()); %></option>
	            	<%
	            			}else{%>
	            				<option value=<%out.println(l.getIdLocal());%>><%out.println(l.getTagLocal()); %></option>
	            	<%		}
	            		}
	        		}
	            	%>
	            </select></td>
	            <td><p>Tipo</p>
	            <select name="tipo" id="tipo" required="required">
	            <%
	        		if(equip.getIdEquipamento() == 0 || equip == null){
	        	%>
	            		<option selected="selected" value="">Selecione um tipo</option>
	            	<%
	            		for(TipoEquipamento t:tipo){
	            	%>
	            			<option value=<%out.println(t.getIdTipo());%>><%out.println(t.getDescricaoTipo()); %></option>
	            	<%
	            		}
	        		}else{
	            		for(TipoEquipamento t:tipo){ 
	            			if(equip.getIdTipoEquipamento() == t.getIdTipo()){%>
	            				<option selected="selected" value=<%out.println(t.getIdTipo());%>><%out.println(t.getDescricaoTipo()); %></option>
	            	<%		
	            			}else{%>
	            				<option value=<%out.println(t.getIdTipo());%>><%out.println(t.getDescricaoTipo()); %></option>
	            	<%		}
	            		}
	            	}%>
	            </select></td>
	            <td><p>Fornecedor</p>
	            <select name="fornecedor" id="fornecedor" required="required">
	            <%
	        		if(equip.getIdEquipamento() == 0 || equip == null){
	        	%>
	            		<option selected="selected" value="">Selecione um fornecedor</option>
	            	<%
	            		for(Fornecedor f:fornecedor){
	            	%>
	            			<option value=<%out.println(f.getIdFornecedor());%>><%out.println(f.getNomeFantasia()); %></option>
	            	<%
	            		}
	        		}else{
	        			for(Fornecedor f:fornecedor){
	        				if(equip.getIdFornecedor() == f.getIdFornecedor()){%>
	            				<option selected="selected" value=<%out.println(f.getIdFornecedor());%>><%out.println(f.getNomeFantasia()); %></option>
	            	<%
	            			}else{%>
	            				<option value=<%out.println(f.getIdFornecedor());%>><%out.println(f.getNomeFantasia()); %></option>
	            	<%		}
	        			}
	        		}
	        		%>
	            </select></td>
	            <td colspan="2"><p>Valor</p><input type="number" style="width: 100px;" id="valor" name="valor" min="0" step="0.01" value="<%=equip.getValor() %>" /></td>
	            
	            </tr>
	            <tr>
	            <td><input class="submit" type="submit" name="action" value="Salvar" /></td>
<!-- 	            <td colspan="5"><input class="submit" type="reset" name="action" onclick="limpar()" value="Cancelar" /></td> -->
	            </tr>
	        </table>
	        <%
	        if (request.getAttribute("equipamento") == null){
	        	//equip = new Equipamento();
	        	//equip = equipamentoServico.limparEquipamento();
	        %>
	        <script>
	        function limpar(){
	        	document.getElementById("codigo").value="";
	        	//document.getElementsByName("ano_fabricacao").value="";
	        	document.getElementById("ano_fabricacao").value="";
	        	document.getElementById("peso").value="";
	        	document.getElementById("valor").value="";
	        	document.getElementById("patrimonio").value="";
	        }
        	document.getElementById("codigo").value="";
        	//document.getElementsByName("ano_fabricacao").value="";
        	document.getElementById("ano_fabricacao").value="";
        	document.getElementById("peso").value="";
        	document.getElementById("valor").value="";
        	</script>
        	<%} %>
          </div>
        </form>
        
        <h2>Equipamentos</h2>
        <div style="width:100%; height: 300px; overflow: auto;"><table style="width:100%; border-spacing:0;">
          <tr><th>TAG</th><th>Descrição</th><th>Modelo</th><th>Local Instalação</th>
          <%
          for(Equipamento e:equipamento){
        	  ServicoLocalInstalacao servicoLocal = new ServicoLocalInstalacao();
        	  LocalInstalacao l = new LocalInstalacao();
        	  l = servicoLocal.buscaLocalInstalacao(e.getIdLocal());
          %>
          <tr><td><%out.println(e.getTag()); %></td>
          		<td><%out.println(e.getDescricao()); %></td>
          		<td><%out.println(e.getModelo()); %></td>
          		<td><%out.println(l.getTagLocal()); %></td>
          		<td><a href="EquipamentoControle?action=editar&codigo=<%out.println(e.getIdEquipamento()); %>" >Editar</a></td>
          		<td><a href="EquipamentoControle?action=excluir&codigo=<%out.println(e.getIdEquipamento()); %>" >Excluir</a></td>
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