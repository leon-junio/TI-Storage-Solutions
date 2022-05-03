package dao;

import model.Produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOProduto extends DAO {
	public DAOProduto() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}

	public boolean insert(Produto produto) {
		boolean status = false;
		try {
			String sql = "INSERT INTO produto (quantidade,estoque,nome,descricao,codigoBarras,unidade,marca,peso,fabricacao,validade) "
					+ "VALUES (?,?,?,?,?,?,?,?,?,?);";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setInt(1, produto.getQuantidade());
			st.setInt(2, produto.getEstoque());
			st.setString(3, produto.getNome());
			st.setString(4, produto.getDescricao());
			st.setString(5, produto.getCodigoBarras());
			st.setString(6, produto.getUnidade());
			st.setString(7, produto.getMarca());
			st.setFloat(8, produto.getPeso());
			st.setDate(9, produto.getFabricacao());
			st.setDate(10, produto.getValidade());
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public Produto get(int id) {
		Produto produto = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM produto WHERE idProduto = " + id;
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				produto = new Produto(rs.getInt("idProduto"), rs.getInt("quantidade"), rs.getInt("estoque"),
						rs.getString("nome"), rs.getString("descricao"), rs.getString("codigoBarras"),
						rs.getString("unidade"), rs.getString("marca"), rs.getFloat("peso"), rs.getDate("fabricacao"),
						rs.getDate("validade"));
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return produto;
	}

	public List<Produto> get() {
		return get("");
	}

	public List<Produto> getOrderByID() {
		return get("idProduto");
	}

	public List<Produto> getOrderByDescricao() {
		return get("descricao");
	}

	public List<Produto> getOrderByPreco() {
		return get("preco");
	}

	private List<Produto> get(String orderBy) {
		List<Produto> produtos = new ArrayList<Produto>();

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM produto" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				Produto p = new Produto(rs.getInt("idProduto"), rs.getInt("quantidade"), rs.getInt("estoque"),
						rs.getString("nome"), rs.getString("descricao"), rs.getString("codigoBarras"),
						rs.getString("unidade"), rs.getString("marca"), rs.getFloat("peso"), rs.getDate("fabricacao"),
						rs.getDate("validade"));
				produtos.add(p);
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return produtos;
	}

	public boolean update(Produto produto) {
		boolean status = false;
		try {
			String sql = "UPDATE produto SET quantidade= ?,estoque= ?,nome= ?,descricao= ?,codigoBarras= ?,unidade= ?,marca= ?"
					+ ",peso= ?,fabricacao= ?,validade= ?" + "WHERE idProduto = ?;";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setInt(1, produto.getQuantidade());
			st.setInt(2, produto.getEstoque());
			st.setString(3, produto.getNome());
			st.setString(4, produto.getDescricao());
			st.setString(5, produto.getCodigoBarras());
			st.setString(6, produto.getUnidade());
			st.setString(7, produto.getMarca());
			st.setFloat(8, produto.getPeso());
			st.setDate(9, produto.getFabricacao());
			st.setDate(10, produto.getValidade());
			st.setInt(11, produto.getIdProduto());
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
			st.executeUpdate("DELETE FROM produto WHERE idProduto = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
}