package Model;

public class Produto {
	private String nome;
	private String preço;
	private String validade;
	private String fornecedor;
	private int quantidade;
	
	public Produto(String nome,String preço,String validade,String fornecedor, int quantidade){
		super();
		this.setNome(nome);
		this.setPreço(preço);
		this.setValidade(validade);
		this.setFornecedor(fornecedor);
		this.setQuantidade(quantidade);
	}
	
	public Produto(String nome) {
		this.setNome(nome);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPreço() {
		return preço;
	}

	public void setPreço(String preço) {
		this.preço = preço;
	}

	public String getValidade() {
		return validade;
	}

	public void setValidade(String validade) {
		this.validade = validade;
	}

	public String getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
}
