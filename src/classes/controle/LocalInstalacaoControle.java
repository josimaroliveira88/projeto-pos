package classes.controle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.Funcionario;
import classes.LocalInstalacao;
import classes.servico.ServicoLocalInstalacao;

/**
 * Servlet implementation class LocalInstalacaoControle
 */
@WebServlet("/LocalInstalacaoControle")
public class LocalInstalacaoControle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pagina = "/localinstalacao.jsp";
		Funcionario usuario = (Funcionario)request.getSession().getAttribute("loginUsuario");

		LocalInstalacao local = new LocalInstalacao();
		if(request.getParameter("codigo") != null){
			local.setIdLocal(Integer.parseInt(request.getParameter("codigo")));
			System.out.println("Entrou no if local controle codigo = "+local.getIdLocal());
		}
		local.setTagLocal(request.getParameter("tag"));
		local.setDescricao(request.getParameter("descricao"));
		local.setIdEquipe(Integer.parseInt(request.getParameter("equipe")));
		local.setIdFuncionario(usuario.getIdFuncionario());
		
		ServicoLocalInstalacao localServico = new ServicoLocalInstalacao();

		if(local.getIdLocal() > 0){
			if(localServico.atualizaLocalInstalacao(local)){
				request.setAttribute("retorno", "Local de Instalação atualizado com sucesso!");
			}else{
				request.setAttribute("retorno", "Erro na atualização do Local de Instalação!");
			}
		}else{
			if(localServico.insereLocalInstalacao(local)){
				request.setAttribute("retorno", "Local de Instalação inserido com sucesso!");
			}else{
				request.setAttribute("retorno", "Erro na inserção do Local de Instalação!");
			}
		}
		request.getRequestDispatcher(pagina).forward(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		ServicoLocalInstalacao localServico = new ServicoLocalInstalacao();

		if(request.getParameter("action") != null){
			if(request.getParameter("action").equals("editar")){
				int idLocal = Integer.parseInt(request.getParameter("codigo"));
				LocalInstalacao local = localServico.buscaLocalInstalacao(idLocal);
				request.setAttribute("local", local);
				request.getRequestDispatcher("localinstalacao.jsp").forward(request, response);
			}else if(request.getParameter("action").equals("excluir")){
				LocalInstalacao local = new LocalInstalacao();
				local.setIdLocal(Integer.parseInt(request.getParameter("codigo")));
				System.out.println("Exluindo código: "+local.getIdLocal());
				if(localServico.excluirLocalInstalacao(local)){
					request.setAttribute("retorno", "Local de Instalação excluído com sucesso!");
				}else{
					request.setAttribute("retorno", "Erro na exclusão do Local de Instalação!");
				}
				request.getRequestDispatcher("/localinstalacao.jsp").forward(request, response);
			}
		}else{
			if(request.getParameter("status").equals("ativo")){
				request.getRequestDispatcher("/rel_local.jsp?status=ativo").forward(request, response);
			}else if(request.getParameter("status").equals("inativo")){
				request.getRequestDispatcher("/rel_local.jsp?status=inativo").forward(request, response);
			}else if(request.getParameter("status").equals("todos")){
				request.getRequestDispatcher("/rel_local.jsp?").forward(request, response);
			}

		}
	}
}
