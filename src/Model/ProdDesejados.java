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
	
	public boolean equals(ProdDesejados obj){
		if(obj.getIdUsuario().compareTo(this.getIdUsuario()) == 0 && obj.getNomeProduto().compareTo(this.getNomeProduto()) == 0)
			return true;
		return false;
	}
}
