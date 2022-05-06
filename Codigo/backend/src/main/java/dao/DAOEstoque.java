package dao;

import model.Estoque;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOEstoque extends DAO {
	public DAOEstoque() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}

	public boolean insert(Estoque estoque) {
		boolean status = false;
		try {
			String sql = "";
			if (estoque.getCliente() == 0) {
				sql = "INSERT INTO StorageSolutionsDB.estoque (capacidade,fornecedor,nome) " + "VALUES (?,?,?);";
			} else {
				sql = "INSERT INTO StorageSolutionsDB.estoque (capacidade,cliente,nome) " + "VALUES (?,?,?);";
			}
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setInt(1, estoque.getCapacidade());
			if (estoque.getCliente() != 0) {
				st.setInt(2, estoque.getCliente());
			}else {
				st.setInt(2, estoque.getFornecedor());
			}
			st.setString(3, estoque.getNome());
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public Estoque get(int id) {
		Estoque estoque = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM StorageSolutionsDB.estoque WHERE idEstoque = " + id;
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				estoque = new Estoque(rs.getInt("idEstoque"), rs.getInt("capacidade"), rs.getInt("cliente"),
						rs.getInt("fornecedor"), rs.getString("nome"));
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return estoque;
	}

	public List<Estoque> get() {
		return get("");
	}

	public List<Estoque> getOrderByID() {
		return get("idEstoque");
	}

	public List<Estoque> getOrderByCliente() {
		return get("cliente");
	}

	public List<Estoque> getOrderByFornecedor() {
		return get("fornecedor");
	}

	private List<Estoque> get(String orderBy) {
		List<Estoque> estoques = new ArrayList<Estoque>();

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM StorageSolutionsDB.estoque"
					+ ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				Estoque p = new Estoque(rs.getInt("idEstoque"), rs.getInt("capacidade"), rs.getInt("cliente"),
						rs.getInt("fornecedor"), rs.getString("nome"));
				estoques.add(p);
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return estoques;
	}

	public List<Estoque> getEstoquesUser(int id, int op) {
		List<Estoque> estoques = new ArrayList<Estoque>();
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "";
			if (op == 1) {
				sql = "SELECT * FROM StorageSolutionsDB.estoque where fornecedor = " + id;
			} else if (op == 2) {
				sql = "SELECT * FROM StorageSolutionsDB.estoque where cliente = " + id;
			}

			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				Estoque p = new Estoque(rs.getInt("idEstoque"), rs.getInt("capacidade"), rs.getInt("cliente"),
						rs.getInt("fornecedor"), rs.getString("nome"));
				estoques.add(p);
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return estoques;
	}

	public boolean update(Estoque estoque) {
		boolean status = false;
		try {
			String sql = "UPDATE StorageSolutionsDB.estoque SET capacidade= ?,nome= ? WHERE idEstoque = ?;";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setInt(1, estoque.getCapacidade());
			st.setString(2, estoque.getNome());
			st.setInt(3, estoque.getIdEstoque());
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
			st.executeUpdate("DELETE FROM StorageSolutionsDB.estoque WHERE idEstoque = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
}
