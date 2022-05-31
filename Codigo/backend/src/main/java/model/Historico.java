package model;

import java.sql.Date;

public class Historico {
	private int id, qtd, estoque, produto;
	private String nome_produto, tipo; 
	private Date data_entrada;
	
	public Historico() {
		
	}
	
	public Historico(int id, int qtd, int estoque, int produto, String nome_produto, String tipo, Date data_entrada) {
		this.id = id; 
		this.qtd = qtd; 
		this.estoque = estoque; 
		this.produto = produto; 
		this.nome_produto = nome_produto; 
		this.tipo = tipo; 
		this.data_entrada = data_entrada;
	}
	
	public int getQtd() {
		return qtd;
	}
	public void setQtd(int qtd) {
		this.qtd = qtd;
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
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNome_produto() {
		return nome_produto;
	}
	public void setNome_produto(String nome_produto) {
		this.nome_produto = nome_produto;
	}
	public Date getData_entrada() {
		return data_entrada;
	}
	public void setData_entrada(Date data_entrada) {
		this.data_entrada = data_entrada;
	} 
	
	
}
