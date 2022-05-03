package model;

public class ItemRetirada {
	
	private int produto,retirada,quantidade;
	
	public ItemRetirada(int produto, int retirada, int quantidade) {
		this.produto = produto;
		this.retirada = retirada;
		this.quantidade = quantidade;
	}

	public ItemRetirada() {
		
	}

	public int getProduto() {
		return produto;
	}

	public void setProduto(int produto) {
		this.produto = produto;
	}

	public int getRetirada() {
		return retirada;
	}

	public void setRetirada(int retirada) {
		this.retirada = retirada;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
}
