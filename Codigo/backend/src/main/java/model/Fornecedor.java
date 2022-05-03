package model;

public class Fornecedor {
	
	private int idFornecedor;
	private String usuario, nome, senha, tipoProduto, email;
	
	public Fornecedor(int idFornecedor, String usuario, String nome, String senha, String tipoProduto, String email) {
		this.idFornecedor = idFornecedor;
		this.usuario = usuario;
		this.nome = nome;
		this.senha = senha;
		this.tipoProduto = tipoProduto;
		this.email = email;
	}

	public Fornecedor() {
		
	}

	public int getIdFornecedor() {
		return idFornecedor;
	}

	public void setIdFornecedor(int idFornecedor) {
		this.idFornecedor = idFornecedor;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTipoProduto() {
		return tipoProduto;
	}

	public void setTipoProduto(String tipoProduto) {
		this.tipoProduto = tipoProduto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
