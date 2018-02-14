package classes;

import java.util.List;

public class CentroCustoOrcamentoMes extends CentroCustoOrcamento {
	private int idOrcamentoMes;
	private int idMes;
	private String descricaoMes;
	private double valor;
	private int idFuncionario;
	private int idCategoria;
	private List<Object> arrListaMes;
	
	public int getIdOrcamentoMes() {
		return idOrcamentoMes;
	}
	public void setIdOrcamentoMes(int idOrcamentoMes) {
		this.idOrcamentoMes = idOrcamentoMes;
	}
	public int getIdMes() {
		return idMes;
	}
	public void setIdMes(int idMes) {
		this.idMes = idMes;
	}
	public String getDescricaoMes() {
		return descricaoMes;
	}
	public void setDescricaoMes(String descricaoMes) {
		this.descricaoMes = descricaoMes;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public int getIdFuncionario() {
		return idFuncionario;
	}
	public void setIdFuncionario(int idFuncionario) {
		this.idFuncionario = idFuncionario;
	}
	public int getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}
	public List<Object> getArrListaMes() {
		return arrListaMes;
	}
	public void setArrListaMes(List<Object> arrListaMes) {
		this.arrListaMes = arrListaMes;
	}
}
