package classes.controle;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.Equipamento;
import classes.Funcionario;
import classes.servico.ServicoEquipamento;

/**
 * Servlet implementation class EquipamentoControle
 */
@WebServlet("/EquipamentoControle")
public class EquipamentoControle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pagina = "/equipamentos.jsp";
		Funcionario usuario = (Funcionario)request.getSession().getAttribute("loginUsuario");
		/*
		 * Se um dos atributos local, tipo ou fornecedor estiverem vazios reencaminha a solicitação para equipamentos.jsp
		 */
		if(request.getParameter("local") == "" || request.getParameter("tipo") == "" || request.getParameter("fornecedor") == ""){
			request.getRequestDispatcher(pagina).forward(request, response);
		}else{

			Equipamento equipamento = new Equipamento();
			System.out.println("Parametro codigo: "+ request.getParameter("codigo"));
			if(request.getParameter("codigo") != null){
				equipamento.setIdEquipamento(Integer.parseInt(request.getParameter("codigo")));
				System.out.println("Entrou no if");
			}
			System.out.println(request.getParameter("tag"));
			equipamento.setTag(request.getParameter("tag"));
			equipamento.setDescricao(request.getParameter("descricao"));
			equipamento.setModelo(request.getParameter("modelo"));
			equipamento.setNumSerie(request.getParameter("serie"));
			equipamento.setNumPatrimonio(request.getParameter("patrimonio"));
			System.out.println("Aquisição: "+request.getParameter("data_aquisicao"));
			equipamento.setDataAquisicao(request.getParameter("data_aquisicao"));
			equipamento.setAnoFabricacao(Integer.parseInt(request.getParameter("ano_fabricacao")));
			equipamento.setPais(request.getParameter("pais"));
			System.out.println("Peso: " + request.getParameter("peso"));
			if(request.getParameter("peso") != ""){
				equipamento.setPeso(Double.parseDouble(request.getParameter("peso")));
			}
			equipamento.setIdLocal(Integer.parseInt(request.getParameter("local")));
			equipamento.setIdTipoEquipamento(Integer.parseInt(request.getParameter("tipo")));
			equipamento.setIdFornecedor(Integer.parseInt(request.getParameter("fornecedor")));
			if(request.getParameter("valor") != ""){
				equipamento.setValor(Double.parseDouble(request.getParameter("valor")));
			}
			equipamento.setIdFuncionario(usuario.getIdFuncionario());
			equipamento.setIdEspecialidade(2);

			ServicoEquipamento equipamentoServico = new ServicoEquipamento();

			if(equipamento.getIdEquipamento() > 0){
				if(equipamentoServico.atualizaEquipamento(equipamento)){
					request.setAttribute("retorno", "Equipamento atualizado com sucesso!");
				}else{
					request.setAttribute("retorno", "Erro na atualização do Equipamento!");
				}
			}else{
				if(equipamentoServico.insereEquipamento(equipamento)){
					request.setAttribute("retorno", "Equipamento inserido com sucesso!");
				}else{
					request.setAttribute("retorno", "Erro na inserção do Equipamento!");
				}
			}
			request.getRequestDispatcher(pagina).forward(request, response);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		ServicoEquipamento equipamentoServico = new ServicoEquipamento();
		if(request.getParameter("action") != null){
			if(request.getParameter("action").equals("editar")){
				int idEquipamento = Integer.parseInt(request.getParameter("codigo"));
				Equipamento equipamento = equipamentoServico.buscaEquipamento(idEquipamento);
				request.setAttribute("equipamento", equipamento);
				request.getRequestDispatcher("equipamentos.jsp").forward(request, response);
			}else if(request.getParameter("action").equals("excluir")){
				Equipamento equipamento = new Equipamento();
				equipamento.setIdEquipamento(Integer.parseInt(request.getParameter("codigo")));
				System.out.println("Exluindo código: "+equipamento.getIdEquipamento());
				if(equipamentoServico.excluirEquipamento(equipamento)){
					request.setAttribute("retorno", "Equipamento excluído com sucesso!");
				}else{
					request.setAttribute("retorno", "Erro na exclusão do Equipamento!");
				}
				request.getRequestDispatcher("/equipamentos.jsp").forward(request, response);
			}
		}else{
			if(request.getParameter("status").equals("ativo")){
				request.getRequestDispatcher("/rel_equipamento.jsp?status=ativo").forward(request, response);
			}else if(request.getParameter("status").equals("inativo")){
				request.getRequestDispatcher("/rel_equipamento.jsp?status=inativo").forward(request, response);
			}else if(request.getParameter("status").equals("todos")){
				request.getRequestDispatcher("/rel_equipamento.jsp?").forward(request, response);
			}
		}
	}
}
