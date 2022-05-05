package dao;

import java.sql.*;

import utils.HashUtils;

public class DAO {
protected Connection conexao;
	
	public DAO() {
		conexao = null;
	}
	
	public boolean conectar() {
		String driverName = "org.postgresql.Driver";                    
		String serverName = "localhost";
		String mydatabase = "StorageSolutionsDB";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
		String username = "ti2cc";
		String password = "ti@cc";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conex�o efetuada com o postgres!");
		} catch (ClassNotFoundException e) { 
			System.err.println("Conex�o N�O efetuada com o postgres -- Driver n�o encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conex�o N�O efetuada com o postgres -- " + e.getMessage());
		}

		return status;
	}
	
	public boolean close() {
		boolean status = false;
		
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}
	
	/**
	 * Met�do de acesso ao sistema e funcionalidade de login
	 * @param usuario - Usu�rio para localizar na base de dados
	 * @param senha - Senha do usu�rio para procurar no Base de dados e validar a conex�o
	 * @return boolean verdadeiro caso encontre o usu�rio e falso caso n�o encontre
	 */
	public boolean login(String email, String senha) {
		boolean login = false;
		DAOCliente daoc = new DAOCliente();
		DAOFornecedor daof = new DAOFornecedor();
		System.out.println(senha);
		senha = HashUtils.getHashMd5(senha);
		System.out.println(senha);
		if(daoc.login(email,senha)){
			login = true;
		}else if(daof.login(email,senha)){
			login = true;
		}else{
			login = false;
		}
		System.out.println(login);
		return login;
	}

}
