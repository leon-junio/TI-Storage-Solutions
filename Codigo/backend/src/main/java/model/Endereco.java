package model;

public class Endereco {

	public Endereco() {

	}

	public Endereco(int idEndereco, int numero, String cep, String estado, String pais, String rua, String bairro,
			String cidade) {
		this.idEndereco = idEndereco;
		this.numero = numero;
		this.cep = cep;
		this.estado = estado;
		this.pais = pais;
		this.rua = rua;
		this.bairro = bairro;
		this.cidade = cidade;
	}

	private int idEndereco, numero;
	private String cep, estado, pais, rua, bairro, cidade;

	public int getIdEndereco() {
		return idEndereco;
	}

	public void setIdEndereco(int idEndereco) {
		this.idEndereco = idEndereco;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

}
