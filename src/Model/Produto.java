package Model;

public class Produto {
	private String nome;
	private String pre�o;
	private String validade;
	private String fornecedor;
	private int quantidade;
	
	public Produto(String nome,String pre�o,String validade,String fornecedor, int quantidade){
		super();
		this.setNome(nome);
		this.setPre�o(pre�o);
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

	public String getPre�o() {
		return pre�o;
	}

	public void setPre�o(String pre�o) {
		this.pre�o = pre�o;
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
