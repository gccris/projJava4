package Model;

import java.util.Date;

public class Venda {
	private String nomeUsuario;
	private String nomeProduto;
	private int quantidade;
	private Date data;
	
	public Venda(String nomeUsuario, String nomeProduto, int quantidade, Date date) {
		super();
		this.nomeUsuario = nomeUsuario;
		this.nomeProduto = nomeProduto;
		this.quantidade = quantidade;
		this.data = date;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}
	
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	
	public String getProduto() {
		return nomeProduto;
	}
	
	public void setProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	
	
	
}
