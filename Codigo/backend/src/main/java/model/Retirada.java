package model;

import java.sql.Date;

public class Retirada {
	
	private int idRetirada,estoque;
	private String observacao;
	private Date data_retirada;
	
	public Retirada(int idRetirada, int estoque, String observacao, Date data_retirada) {
		this.idRetirada = idRetirada;
		this.estoque = estoque;
		this.observacao = observacao;
		this.data_retirada = data_retirada;
	}

	public Retirada() {
	}

	public int getIdRetirada() {
		return idRetirada;
	}

	public void setIdRetirada(int idRetirada) {
		this.idRetirada = idRetirada;
	}

	public int getEstoque() {
		return estoque;
	}

	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getData_retirada() {
		return data_retirada;
	}

	public void setData_retirada(Date data_retirada) {
		this.data_retirada = data_retirada;
	}
	
}
