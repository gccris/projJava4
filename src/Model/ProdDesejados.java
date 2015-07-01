package Model;

import java.io.Serializable;

public class ProdDesejados implements Serializable {

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
	
	public boolean equals(Object obj){
		ProdDesejados pd = (ProdDesejados) obj;
		if(pd.getIdUsuario().compareTo(this.getIdUsuario()) == 0 && pd.getNomeProduto().compareTo(this.getNomeProduto()) == 0)
			return true;
		return false;
	}
}
