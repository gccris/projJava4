package Model;

public class Estoque {
	private Produto produto;
	private int quantidade;
	
	public Estoque(Produto p, int quant){
		super();
		this.setProduto(p);
		this.setQuantidade(quant);
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
}
