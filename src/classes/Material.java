package classes;

public class Material {
	private int codigoMaterial;
	private String descricaoMaterial;
	private double valor;
	private double peso;
	private int vidaUtil;
	private String dataFabricacao;
	private int idUnidadeMedida;
	private int idFamilia;
	private int idFornecedor;
	private int idMaterial;
	
	//Métodos getter's e setter's
	public int getCodigoMaterial() {
		return codigoMaterial;
	}
	public void setCodigoMaterial(int codigoMaterial) {
		this.codigoMaterial = codigoMaterial;
	}
	
	public String getDescricaoMaterial() {
		return descricaoMaterial;
	}
	public void setDescricaoMaterial(String descricaoMaterial) {
		this.descricaoMaterial = descricaoMaterial;
	}
	
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	
	public int getVidaUtil() {
		return vidaUtil;
	}
	public void setVidaUtil(int vidaUtil) {
		this.vidaUtil = vidaUtil;
	}
	
	public String getDataFabricacao() {
		return dataFabricacao;
	}
	public void setDataFabricacao(String dataFabricacao) {
		this.dataFabricacao = dataFabricacao;
	}
	
	public int getIdUnidadeMedida() {
		return idUnidadeMedida;
	}
	public void setIdUnidadeMedida(int idUnidadeMedida) {
		this.idUnidadeMedida = idUnidadeMedida;
	}
	
	public int getIdFamilia() {
		return idFamilia;
	}
	public void setIdFamilia(int idFamilia) {
		this.idFamilia = idFamilia;
	}
	
	public int getIdFornecedor() {
		return idFornecedor;
	}
	public void setIdFornecedor(int idFornecedor) {
		this.idFornecedor = idFornecedor;
	}
	
	public int getIdMaterial() {
		return idMaterial;
	}
	public void setIdMaterial(int idMaterial) {
		this.idMaterial = idMaterial;
	}
}
