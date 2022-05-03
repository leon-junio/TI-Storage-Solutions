package model;

import java.sql.Date;

public class Entrada {

	private int idEntrada, quantidade, estoque, produto;
	private Date dataEntrada;
	
	public Entrada(int idEntrada, int quantidade, int estoque, int produto, Date dataEntrada) {
		this.idEntrada = idEntrada;
		this.quantidade = quantidade;
		this.estoque = estoque;
		this.produto = produto;
		this.dataEntrada = dataEntrada;
	}

	public Entrada() {
		
	}

	public int getIdEntrada() {
		return idEntrada;
	}

	public void setIdEntrada(int idEntrada) {
		this.idEntrada = idEntrada;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public int getEstoque() {
		return estoque;
	}

	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}

	public int getProduto() {
		return produto;
	}

	public void setProduto(int produto) {
		this.produto = produto;
	}

	public Date getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}
	
	

}
