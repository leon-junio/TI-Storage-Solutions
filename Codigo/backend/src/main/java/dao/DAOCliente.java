
package dao;

import model.Cliente;
import utils.HashUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAOCliente extends DAO {
	public DAOCliente() {
			super();
			conectar();
		}

	public void finalize() {
		close();
	}

	public boolean insert(Cliente cliente) {
		boolean status = false;
		try {
			String sql = "INSERT INTO cliente (nome,email,usuario,senha) "
					+ "VALUES (?,?,?,?);";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setString(1, cliente.getNome());
			st.setString(2, cliente.getEmail());
			st.setString(3, cliente.getUsuario());
			st.setString(4, HashUtils.getHashMd5(cliente.getSenha()));
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public Cliente get(int id) {
		Cliente cliente = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM cliente WHERE idCliente = " + id;
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				cliente = new Cliente(rs.getInt("idCliente"),rs.getString("nome"), rs.getString("email"), rs.getString("usuario"),
						rs.getString("senha"));
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return cliente;
	}

	public List<Cliente> get() {
		return get("");
	}

	public List<Cliente> getOrderByID() {
		return get("idCliente");
	}

	public List<Cliente> getOrderByUsuario() {
		return get("usuario");
	}

	private List<Cliente> get(String orderBy) {
		List<Cliente> clientes = new ArrayList<Cliente>();

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM cliente" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				Cliente p = new Cliente(rs.getInt("idCliente"),rs.getString("nome"), rs.getString("email"), rs.getString("usuario"),
						rs.getString("senha"));
				clientes.add(p);
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return clientes;
	}

	public boolean update(Cliente cliente) {
		boolean status = false;
		try {
			String sql = "UPDATE cliente SET nome=?,email=?,usuario=?,senha=? WHERE idCliente = ?;";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setString(1, cliente.getNome());
			st.setString(2, cliente.getEmail());
			st.setString(3, cliente.getUsuario());
			st.setString(4, HashUtils.getHashMd5(cliente.getSenha()));
			st.setInt(5,cliente.getIdCliente());
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
			st.executeUpdate("DELETE FROM cliente WHERE idCliente = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
}
