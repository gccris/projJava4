package Model;

public class ProdDesejados {
	private String idUsuario;
	private String nomeProduto;  //
	
	public ProdDesejados(String id,String nome){
		this.setIdUsuario(id);
		this.setNomeProduto(nome);
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
}
