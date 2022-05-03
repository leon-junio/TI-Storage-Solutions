package model;

import java.sql.Date;

public class FornecimentoProdutos {
	
	private int produto, fornecedor, quantidade;
	private Date fornecimento;
	
	public FornecimentoProdutos(int produto, int fornecedor, int quantidade, Date fornecimento) {
		this.produto = produto;
		this.fornecedor = fornecedor;
		this.quantidade = quantidade;
		this.fornecimento = fornecimento;
	}

	public FornecimentoProdutos() {
	
	}

	public int getProduto() {
		return produto;
	}

	public void setProduto(int produto) {
		this.produto = produto;
	}

	public int getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(int fornecedor) {
		this.fornecedor = fornecedor;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Date getFornecimento() {
		return fornecimento;
	}

	public void setFornecimento(Date fornecimento) {
		this.fornecimento = fornecimento;
	}
	
}
