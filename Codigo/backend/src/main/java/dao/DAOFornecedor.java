package dao;

import model.Fornecedor;
import utils.HashUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOFornecedor extends DAO {
	public DAOFornecedor() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}

	public boolean insert(Fornecedor fornecedor) {
		boolean status = false;
		try {
			String sql = "INSERT INTO fornecedor (nome,email,usuario,senha,tipoProduto) " + "VALUES (?,?,?,?);";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setString(1, fornecedor.getNome());
			st.setString(2, fornecedor.getEmail());
			st.setString(3, fornecedor.getUsuario());
			st.setString(4, HashUtils.getHashMd5(fornecedor.getSenha()));
			st.setString(5, fornecedor.getTipoProduto());
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public Fornecedor get(int id) {
		Fornecedor fornecedor = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM fornecedor WHERE id=" + id;
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				fornecedor = new Fornecedor(rs.getInt("idFornecedor"), rs.getString("nome"), rs.getString("email"),
						rs.getString("usuario"), rs.getString("senha"),rs.getString("tipoProduto"));
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return fornecedor;
	}

	public List<Fornecedor> get() {
		return get("");
	}

	public List<Fornecedor> getOrderByID() {
		return get("id");
	}

	public List<Fornecedor> getOrderByDescricao() {
		return get("descricao");
	}

	public List<Fornecedor> getOrderByPreco() {
		return get("preco");
	}

	private List<Fornecedor> get(String orderBy) {
		List<Fornecedor> fornecedors = new ArrayList<Fornecedor>();

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM fornecedor" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				Fornecedor p = new Fornecedor(rs.getInt("idFornecedor"), rs.getString("nome"), rs.getString("email"),
						rs.getString("usuario"), rs.getString("senha"),rs.getString("tipoProduto"));
				fornecedors.add(p);
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return fornecedors;
	}

	public boolean update(Fornecedor fornecedor) {
		boolean status = false;
		try {
			String sql = "UPDATE fornecedor SET nome=?,email=?,usuario=?,senha=? WHERE idFornecedor = ?;";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setString(1, fornecedor.getNome());
			st.setString(2, fornecedor.getEmail());
			st.setString(3, fornecedor.getUsuario());
			st.setString(4, HashUtils.getHashMd5(fornecedor.getSenha()));
			st.setString(5, fornecedor.getTipoProduto());
			st.setInt(6, fornecedor.getIdFornecedor());
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
			st.executeUpdate("DELETE FROM fornecedor WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
}
