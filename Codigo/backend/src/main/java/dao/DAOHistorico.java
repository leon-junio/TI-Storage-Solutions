package dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.sql.Date;
import java.sql.PreparedStatement;

import model.Historico;
import model.ItemRetirada;
import model.Estoque;

public class DAOHistorico extends DAO {
	public DAOHistorico() {
		super(); 
		conectar();
	}
	
	public void finalize() {
		close();
	}
	
	
	public List<Historico> get(int estoque) {
		List<Historico> historico = new ArrayList<Historico>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT A.identrada as id, A.data_entrada, A.quantidade, A.estoque, A.produto, B.nome as nome_produto, 'Entrada' as tipo "
					+ "FROM storagesolutionsdb.entrada A JOIN storagesolutionsdb.produto B ON B.idproduto = A.produto WHERE A.estoque = " + estoque
					+ " UNION "
					+ "SELECT A.idretirada as id, A.data_retirada, A.quantidade, A.estoque, A.produto, B.nome as nome_produto, 'Retirada' as tipo "
					+ "FROM storagesolutionsdb.retirada A JOIN storagesolutionsdb.produto B ON B.idproduto = A.produto WHERE A.estoque = " + estoque;
			
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				Historico p = new Historico(rs.getInt("id"), rs.getInt("quantidade"), rs.getInt("estoque"), rs.getInt("produto"), rs.getString("nome_produto"), rs.getString("tipo"), rs.getDate("data_entrada"));
				
				historico.add(p);
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return historico;
	}
}
