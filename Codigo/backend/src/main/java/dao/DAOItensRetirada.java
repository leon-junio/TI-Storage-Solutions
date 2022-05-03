package dao;

import model.ItemRetirada;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOItensRetirada extends DAO {
	public DAOItensRetirada() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}

	public boolean insert(ItemRetirada itensRetirada) {
		boolean status = false;
		try {
			String sql = "INSERT INTO Itens_Retirada (produto,retirada,quantidade) " + "VALUES (?,?,?);";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setInt(1, itensRetirada.getProduto());
			st.setInt(2, itensRetirada.getRetirada());
			st.setInt(3, itensRetirada.getQuantidade());
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public ItemRetirada get(int id) {
		ItemRetirada itensRetirada = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM Itens_Retirada WHERE id=" + id;
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				itensRetirada = new ItemRetirada(rs.getInt("produto"), rs.getInt("retirada"), rs.getInt("quantidade"));
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return itensRetirada;
	}

	public List<ItemRetirada> get() {
		return get("");
	}


	private List<ItemRetirada> get(String orderBy) {
		List<ItemRetirada> itensRetiradas = new ArrayList<ItemRetirada>();

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM Itens_Retirada" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				ItemRetirada p = new ItemRetirada(rs.getInt("produto"), rs.getInt("retirada"), rs.getInt("quantidade"));
				itensRetiradas.add(p);
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return itensRetiradas;
	}

	public boolean delete(int id) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM Itens_Retirada WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
}
