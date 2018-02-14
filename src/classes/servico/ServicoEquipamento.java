package classes.servico;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import org.joda.time.DateTime;
import org.postgresql.util.PSQLException;

import classes.Equipamento;
import classes.Fornecedor;
import classes.LocalInstalacao;
import classes.TipoEquipamento;
import classes.dao.EquipamentoDao;

public class ServicoEquipamento {
		EquipamentoDao equipamentoDao; 
		Date data = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

		public ServicoEquipamento() {
			inicializaObjetos();
		}
		
		public boolean insereEquipamento(Equipamento equipamento) {
//			inicializaObjetos();
			boolean retorno = false;

			if (equipamento.getDataCadastro() == "" || equipamento.getDataCadastro() == null)
				equipamento.setDataCadastro(formato.format(data));

			// Chama o método que irá fazer a inserção no banco de dados
			try {
				retorno = equipamentoDao.inserirEquipamento(equipamento);
			} catch (PSQLException e) {
				e.printStackTrace();
				retorno = false;
			}
			return retorno;
		}

		private void inicializaObjetos() {
			equipamentoDao = new EquipamentoDao();
		}

		public boolean atualizaEquipamento(Equipamento equipamento) {
			// Depois verifica se atualizou pelo menos uma linha no banco de dados e
			// retorna para o flex
			if (equipamentoDao.atualizarEquipamento(equipamento) == 1)
				return true;
			else
				return false;
		}
		
		public boolean excluirEquipamento(Equipamento equipamento){
			System.out.println("Excluindo equipamento");
			if (equipamentoDao.desativarEquipamento(equipamento) == 1)
				return true;
			else
				return false;
		}
		
		public DateTime retornaData(){
			DateTime data = new DateTime();
			//JOptionPane.showMessageDialog(null, data.getYear());
			return data;
		}

		public ArrayList<Equipamento> listaEquipamento() {
			ArrayList<Equipamento> lista = new ArrayList<Equipamento>();
			lista = equipamentoDao.buscaEquipamento();
			return lista;
		}
		
		public ArrayList<Equipamento> listaEquipamentoInativo() {
			ArrayList<Equipamento> lista = new ArrayList<Equipamento>();
			lista = equipamentoDao.buscaEquipamentoInativo();
			return lista;
		}
		
		public ArrayList<Equipamento> listaEquipamentoTodos() {
			ArrayList<Equipamento> lista = new ArrayList<Equipamento>();
			lista = equipamentoDao.buscaEquipamentoTodos();
			return lista;
		}
		
		public Equipamento buscaEquipamento(int idEquipamento){
			Equipamento equipamento = new Equipamento();
			equipamento = equipamentoDao.buscaEquipamento(idEquipamento);
			return equipamento;
		}
		
		public ArrayList<Fornecedor> listaFabricante() {
			ArrayList<Fornecedor> lista = new ArrayList<Fornecedor>();
			lista = equipamentoDao.buscaFabricante();
			return lista;
		}
		
		public ArrayList<TipoEquipamento> listaTipo() {
			ArrayList<TipoEquipamento> lista = equipamentoDao.buscaTipo();
			return lista;
		}
		
		public TipoEquipamento listaTipo(int idTipo) {
			TipoEquipamento tipo = equipamentoDao.buscaTipo(idTipo);
			return tipo;
		}
		
		public List listaEspecialidade() {
			List lista = new ArrayList();
			lista = equipamentoDao.buscaEspecialidade();
			return lista;
		}
		
		public ArrayList<LocalInstalacao> listaLocal() {
			ArrayList<LocalInstalacao> lista = equipamentoDao.buscaLocal();
			return lista;
		}
		
		public Equipamento limparEquipamento(){
			Equipamento equipamento = new Equipamento();
			equipamento.setIdEquipamento(0);
			equipamento.setTag("");
			equipamento.setDescricao("");
			equipamento.setAnoFabricacao(0);
			equipamento.setPeso(0);
			equipamento.setModelo("");
			equipamento.setDataAquisicao("");
			equipamento.setIdLocal(0);
			equipamento.setIdTipoEquipamento(0);
			equipamento.setIdFornecedor(0);
			equipamento.setPais("");
			equipamento.setValor(0);
			equipamento.setNumSerie("");
			equipamento.setNumPatrimonio("");
			equipamento.setDataCadastro("");
			return equipamento;
		}
}
