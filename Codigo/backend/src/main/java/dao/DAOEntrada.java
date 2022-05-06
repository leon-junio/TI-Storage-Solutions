package dao;

import model.Entrada;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOEntrada extends DAO {
	public DAOEntrada() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}

	public boolean insert(Entrada entrada) {
		boolean status = false;
		try {
			String sql = "INSERT INTO StorageSolutionsDB.entrada (quantidade,estoque,produto,data_entrada) " + "VALUES (?,?,?,?);";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setInt(1, entrada.getQuantidade());
			st.setInt(2, entrada.getEstoque());
			st.setInt(3, entrada.getProduto());
			st.setDate(4, entrada.getDataEntrada());
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public Entrada get(int id) {
		Entrada entrada = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM StorageSolutionsDB.entrada WHERE idEntrada = " + id;
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				entrada = new Entrada(rs.getInt("idEntrada"), rs.getInt("quantidade"), rs.getInt("estoque"),
						rs.getInt("produto"), rs.getDate("data_entrada"));
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return entrada;
	}

	public List<Entrada> get() {
		return get("");
	}

	public List<Entrada> getOrderByID() {
		return get("idEntrada");
	}


	private List<Entrada> get(String orderBy) {
		List<Entrada> entradas = new ArrayList<Entrada>();

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM StorageSolutionsDB.entrada" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				Entrada p = new Entrada(rs.getInt("idEntrada"), rs.getInt("quantidade"), rs.getInt("estoque"),
						rs.getInt("produto"), rs.getDate("data_entrada"));
				entradas.add(p);
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return entradas;
	}

	public boolean update(Entrada entrada) {
		boolean status = false;
		try {
			String sql = "UPDATE StorageSolutionsDB.entrada SET quantidade = ?,estoque = ?,produto = ?,data_entrada = ? WHERE idEntrada = ?;";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setInt(1, entrada.getQuantidade());
			st.setInt(2, entrada.getEstoque());
			st.setInt(3, entrada.getProduto());
			st.setDate(4, entrada.getDataEntrada());
			st.setInt(5, entrada.getIdEntrada());
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
			st.executeUpdate("DELETE FROM StorageSolutionsDB.entrada WHERE idEntrada = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
}
