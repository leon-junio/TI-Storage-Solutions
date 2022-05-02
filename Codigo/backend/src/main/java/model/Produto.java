package model;

import java.sql.Date;

public class Produto {
	
	private int idProduto,quantidade,estoque;
	private String nome, descricao, codigoBarras, unidade, marca;
	private float peso;
	private Date fabricacao, validade;
	public Produto() {
		
	}
	public Produto(int idProduto, int quantidade, int estoque, String nome, String descricao, String codigoBarras,
			String unidade, String marca, float peso, Date fabricacao, Date validade) {
		this.idProduto = idProduto;
		this.quantidade = quantidade;
		this.estoque = estoque;
		this.nome = nome;
		this.descricao = descricao;
		this.codigoBarras = codigoBarras;
		this.unidade = unidade;
		this.marca = marca;
		this.peso = peso;
		this.fabricacao = fabricacao;
		this.validade = validade;
	}
	public int getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
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
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getCodigoBarras() {
		return codigoBarras;
	}
	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}
	public String getUnidade() {
		return unidade;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public float getPeso() {
		return peso;
	}
	public void setPeso(float peso) {
		this.peso = peso;
	}
	public Date getFabricacao() {
		return fabricacao;
	}
	public void setFabricacao(Date fabricacao) {
		this.fabricacao = fabricacao;
	}
	public Date getValidade() {
		return validade;
	}
	public void setValidade(Date validade) {
		this.validade = validade;
	}
	
}
