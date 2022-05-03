package dao;

import model.Endereco;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOEndereco extends DAO {
	public DAOEndereco() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}

	public boolean insert(Endereco endereco) {
		boolean status = false;
		try {
			String sql = "INSERT INTO endereco (numero,cep,estado,pais,rua,bairro,cidade) " + "VALUES (?,?,?,?,?,?,?);";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setInt(1, endereco.getNumero());
			st.setString(2, endereco.getCep());
			st.setString(3, endereco.getEstado());
			st.setString(4, endereco.getPais());
			st.setString(5, endereco.getRua());
			st.setString(6, endereco.getBairro());
			st.setString(7, endereco.getCidade());
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public Endereco get(int id) {
		Endereco endereco = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM endereco WHERE idEndereco = " + id;
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				endereco = new Endereco(rs.getInt("idEndereco"),rs.getInt("numero"), rs.getString("cep"), rs.getString("estado"),
						rs.getString("pais"), rs.getString("rua"),rs.getString("bairro"),rs.getString("cidade"));
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return endereco;
	}

	public List<Endereco> get() {
		return get("");
	}

	public List<Endereco> getOrderByID() {
		return get("idEndereco");
	}


	private List<Endereco> get(String orderBy) {
		List<Endereco> enderecos = new ArrayList<Endereco>();

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM endereco" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				Endereco p = new Endereco(rs.getInt("idEndereco"),rs.getInt("numero"), rs.getString("cep"), rs.getString("estado"),
						rs.getString("pais"), rs.getString("rua"),rs.getString("bairro"),rs.getString("cidade"));
				enderecos.add(p);
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return enderecos;
	}

	public boolean update(Endereco endereco) {
		boolean status = false;
		try {
			String sql = "UPDATE endereco SET numero = ?,cep = ?,estado = ?,pais = ?,rua = ?,bairro = ?,cidade = ? WHERE idEndereco = ?;";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setInt(1, endereco.getNumero());
			st.setString(2, endereco.getCep());
			st.setString(3, endereco.getEstado());
			st.setString(4, endereco.getPais());
			st.setString(5, endereco.getRua());
			st.setString(6, endereco.getBairro());
			st.setString(7, endereco.getCidade());
			st.setInt(8, endereco.getIdEndereco());
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public boolean delete(int id) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM endereco WHERE idEndereco = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
}
