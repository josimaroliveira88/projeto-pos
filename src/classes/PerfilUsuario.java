package classes;

public class PerfilUsuario {
	private int codigoPerfil;
	private String descricao;
	private boolean statusUsuario;
	private int poder;
	private String dataCadastro;
	
	public int getCodigoPerfil() {
		return codigoPerfil;
	}
	public void setCodigoPerfil(int codigoPerfil) {
		this.codigoPerfil = codigoPerfil;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public boolean getStatusUsuario() {
		return statusUsuario;
	}
	public void setStatusUsuario(boolean statusUsuario) {
		this.statusUsuario = statusUsuario;
	}
	public int getPoder() {
		return poder;
	}
	public void setPoder(int poder) {
		this.poder = poder;
	}
	public String getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
}
