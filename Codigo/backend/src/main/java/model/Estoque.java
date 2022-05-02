package model;

public class Estoque {
	
	private int idEstoque,capacidade,cliente,fornecedor;
	private String nome;
	
	public Estoque(int idEstoque, int capacidade, int cliente, int fornecedor, String nome) {
		this.idEstoque = idEstoque;
		this.capacidade = capacidade;
		this.cliente = cliente;
		this.fornecedor = fornecedor;
		this.nome = nome;
	}

	public Estoque() {
		
	}

	public int getIdEstoque() {
		return idEstoque;
	}

	public void setIdEstoque(int idEstoque) {
		this.idEstoque = idEstoque;
	}

	public int getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(int capacidade) {
		this.capacidade = capacidade;
	}

	public int getCliente() {
		return cliente;
	}

	public void setCliente(int cliente) {
		this.cliente = cliente;
	}

	public int getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(int fornecedor) {
		this.fornecedor = fornecedor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	

}
