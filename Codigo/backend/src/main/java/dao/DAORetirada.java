package dao;

import model.Retirada;
import model.Produto;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DAORetirada extends DAO {
	public DAORetirada() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}

	public boolean insert(Retirada retirada, int idProduto) {
		boolean status = false;
		
		DAOProduto daop = new DAOProduto();
		Produto pd = daop.get(idProduto);
		
		if (retirada.getQuantidade() > pd.getQuantidade()) {
			status = false;
		} else {			
			pd.setQuantidade(pd.getQuantidade() - retirada.getQuantidade());
			retirada.setProduto(pd.getIdProduto());
			
			try {
				String sql = "INSERT INTO StorageSolutionsDB.retirada (quantidade, estoque, produto, data_retirada, observacao) "
						+ "VALUES (?,?,?,?,?);";
				
				PreparedStatement st = conexao.prepareStatement(sql);
				
				st.setInt(1, retirada.getQuantidade());
				st.setInt(2, retirada.getEstoque());
				st.setInt(3, retirada.getProduto());
				st.setDate(4, retirada.getData_retirada());
				st.setString(5, retirada.getObservacao());
				
				st.executeUpdate();
				st.close();
				status = true;
				if(status) {
					daop.update(pd);
				}
			} catch (SQLException u) {
				throw new RuntimeException(u);
			}
		}
		
		return status;
	}

}
