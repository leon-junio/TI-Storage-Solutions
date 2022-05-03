package dao;

import model.Retirada;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAORetirada extends DAO {
	public DAORetirada() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}

	public boolean insert(Retirada retirada) {
		boolean status = false;
		try {
			String sql = "INSERT INTO retirada (estoque,observacao,data_retirada) " + "VALUES (?,?,?);";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setInt(1, retirada.getEstoque());
			st.setString(2, retirada.getObservacao());
			st.setDate(3, retirada.getData_retirada());
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public Retirada get(int id) {
		Retirada retirada = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM retirada WWHERE idRetirada = " + id;
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				retirada = new Retirada(rs.getInt("idRetirada"), rs.getInt("estoque"), rs.getString("observacao"),
						rs.getDate("data_retirada"));
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return retirada;
	}

	public List<Retirada> get() {
		return get("");
	}

	public List<Retirada> getOrderByID() {
		return get("idRetirada");
	}

	private List<Retirada> get(String orderBy) {
		List<Retirada> retiradas = new ArrayList<Retirada>();

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM retirada" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				Retirada p = new Retirada(rs.getInt("idRetirada"), rs.getInt("estoque"), rs.getString("observacao"),
						rs.getDate("data_retirada"));
				retiradas.add(p);
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return retiradas;
	}

	public boolean update(Retirada retirada) {
		boolean status = false;
		try {
			String sql = "UPDATE retirada SET estoque= ?,observacao= ?,data_retirada= ? WHERE idRetirada = ?;";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setInt(1, retirada.getEstoque());
			st.setString(2, retirada.getObservacao());
			st.setDate(3, retirada.getData_retirada());
			st.setInt(4, retirada.getIdRetirada());
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
			st.executeUpdate("DELETE FROM retirada WHERE idRetirada = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
}
