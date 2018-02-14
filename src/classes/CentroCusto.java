package classes;

import java.util.Date;

import classes.conexao.Conexao;

public class CentroCusto {
	private int idCentro;
	private String codCentro;
	private String descricao;
	private String dataCadastro;
	
	public int getIdCentro() {
		return idCentro;
	}
	public void setIdCentro(int idCentro) {
		this.idCentro = idCentro;
	}
	public String getCodCentro() {
		return codCentro;
	}
	public void setCodCentro(String codCentro) {
		this.codCentro = codCentro;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
}
