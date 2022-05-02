package model;

public class Cliente {

	public Cliente() {
		
	}
	
	private int idCliente;
	private String nome,email,usuario,senha;
	
	
	
	public Cliente(int idCliente, String nome, String email, String usuario, String senha) {
		this.idCliente = idCliente;
		this.nome = nome;
		this.email = email;
		this.usuario = usuario;
		this.senha = senha;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	

}
