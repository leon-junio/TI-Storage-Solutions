package dao;

import java.sql.*;

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
		//String username = "postgres";
		String password = "ti@cc";
		//String password = "21801886";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Uma conexao com o postgres foi executada com sucesso!");
		} catch (ClassNotFoundException e) { 
			System.err.println("Conexao nao efetuada com o postgres -- Driver nao encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexao nao efetuada com o postgres -- " + e.getMessage());
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
		if(daoc.login(email,senha)){
			login = true;
		}else if(daof.login(email,senha)){
			login = true;
		}else{
			login = false;
		}
		return login;
	}
	

}
