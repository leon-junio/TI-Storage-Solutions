package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import utils.HashUtils;

public class DAOToken extends DAO {
	public DAOToken() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}

	public String insert(String email, String senha) {
		String token = "";
		try {
			String sql = "INSERT INTO StorageSolutionsDB.tokens (email,senha,token) "
					+ "VALUES (?,?,?);";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setString(1, email);
			st.setString(2, senha);
            token = utils.LeonAPI.generateToken(email.length());
			st.setString(3,token);
			st.executeUpdate();
			st.close();
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return token;
	}



	public boolean check(String email,String senha, String token) {
		boolean resp = false;
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM StorageSolutionsDB.tokens WHERE email like '" +email+"' and senha like '"+HashUtils.getHashMd5(senha)+"' and token like '"+token+"'" ;
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				resp = true;
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return resp;
	}

    public boolean check(String token) {
		boolean resp = false;
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM StorageSolutionsDB.tokens WHERE token like '"+token+"'" ;
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				resp = true;
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return resp;
	}

	public boolean delete(int id) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM StorageSolutionsDB.tokens WHERE idToken = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public Object convertToken(String token)
{
Object obj = null;
DAOCliente daoc = new DAOCliente();
DAOFornecedor daof = new DAOFornecedor();
try{
String sql = "Select * from StorageSolutionsDB.tokens where token like '"+token+"'";
Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
ResultSet rs = st.executeQuery(sql);
if(rs.next()){
	String email = rs.getString("email");
	String senha = rs.getString("senha");
	
}
st.close

daoc

}catch(Exception e){
	e.printStackTrace();
}
}
}
