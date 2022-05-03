package dao;

import model.FornecimentoProdutos;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOFornecimento extends DAO {
	public DAOFornecimento() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}

	public boolean insert(FornecimentoProdutos fornecimento_produtos) {
		boolean status = false;
		try {
			String sql = "INSERT INTO fornecimento_produtos (produto,fornecedor,quantidade,fornecimento) "
					+ "VALUES (?,?,?,?);";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setInt(1, fornecimento_produtos.getProduto());
			st.setInt(2, fornecimento_produtos.getFornecedor());
			st.setInt(3, fornecimento_produtos.getQuantidade());
			st.setDate(4, fornecimento_produtos.getFornecimento());
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public FornecimentoProdutos get(int idp,int idf) {
		FornecimentoProdutos fornecimento_produtos = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM fornecimento_produtos WHERE produto = " + idp + " AND fornecedor =  " + idf;
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				fornecimento_produtos = new FornecimentoProdutos(rs.getInt("produto"), rs.getInt("fornecedor"),
						rs.getInt("quantidade"), rs.getDate("fornecimento"));
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return fornecimento_produtos;
	}

	public List<FornecimentoProdutos> get() {
		return get("");
	}

	public List<FornecimentoProdutos> getOrderByProduto() {
		return get("produto");
	}

	public List<FornecimentoProdutos> getOrderByFornecedor() {
		return get("fornecedor");
	}

	private List<FornecimentoProdutos> get(String orderBy) {
		List<FornecimentoProdutos> fornecimento_produtoss = new ArrayList<FornecimentoProdutos>();

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM fornecimento_produtos"
					+ ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				FornecimentoProdutos p = new FornecimentoProdutos(rs.getInt("produto"), rs.getInt("fornecedor"),
						rs.getInt("quantidade"), rs.getDate("fornecimento"));
				fornecimento_produtoss.add(p);
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return fornecimento_produtoss;
	}

	public boolean update(FornecimentoProdutos fornecimento_produtos) {
		boolean status = false;
		try {
			String sql = "UPDATE fornecimento_produtos SET quantidade=?,fornecimento=? WHERE produto = ? AND fornecedor = ? ;";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setInt(1, fornecimento_produtos.getQuantidade());
			st.setDate(2, fornecimento_produtos.getFornecimento());
			st.setInt(3, fornecimento_produtos.getProduto());
			st.setInt(4, fornecimento_produtos.getFornecedor());
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public boolean delete(int idp, int idf) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM fornecimento_produtos WHERE produto = " + idp + " AND fornecedor =  " + idf);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
}
