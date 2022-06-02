package service;

import java.util.List;

import dao.DAOFornecedor;
import dao.DAOToken;
import model.Cliente;
import model.Estoque;
import model.Fornecedor;
import spark.Request;
import spark.Response;

public class FornecedorService {

	public FornecedorService() {
	}

	DAOFornecedor daof = new DAOFornecedor();

	public Object listar(Request request, Response response) {
		try {
			boolean chk = false;
			response.header("Content-Type", "Json; charset=utf-8");
			String token = request.params("token");
			Object prop = new DAOToken().convertTokenCliente(token);
			Fornecedor fornc = null;
			Cliente cli = null;
			if (prop == null) {
				prop = new DAOToken().convertTokenFornecedor(token);
				fornc = (Fornecedor) prop;
				if (fornc != null) {
					chk = true;
				} else {
					chk = false;
				}
			} else {
				cli = (Cliente) prop;
				if (cli != null) {
					chk = true;
				} else {
					chk = false;
				}
			}
			String resp = "";
			if (chk) {
				List<model.Fornecedor> fornecedores = null;
				fornecedores = daof.get();
				if (fornecedores == null) {
					throw new RuntimeException("Erro em tempo de execução!");
				}
				if (fornecedores.size() > 0) {
					response.status(200);
					for (model.Fornecedor forn : fornecedores) {
						resp += "<a id=\"item-f\" href=\"mailto:" + forn.getEmail()
								+ "\" style=\"text-decoration: none !important;\">";
						resp += "<div class=\"card-counter btn-primary-dark\">";
						resp += "       <div class=\"row\">";
						resp += "            <div class=\"col-3\">";
						resp += "               <i class=\"fas fa-truck\"></i>";
						resp += "           </div>";
						resp += "           <div class=\"col-9\">";
						resp += "               <span class=\"count-numbers\">" + forn.getNome()
								+ "</span> <span class=\"count-name\">" + forn.getEmail() + "</span>";
						resp += "           </div>";
						resp += "</div>";
						resp += "</div>";
						resp += "</a>";
						resp += "<hr>";
					}
				} else {
					throw new RuntimeException("Nenhum fornecedor localizado!");
				}
			} else {
				throw new RuntimeException("key_failed");
			}
			return utils.LeonAPI.stringToJson(resp);
		} catch (RuntimeException e) {
			response.status(203);
			if (e.getMessage().equals("key_failed")) {
				return utils.LeonAPI.stringToJson(e.getMessage());
			}
			return utils.LeonAPI.stringToJson("<h3>Não existem fornecedores cadastrados no sistema no momento!</h3>");
		} catch (Exception e) {
			response.status(203);
			return utils.LeonAPI.stringToJson("<h3>Erro no sistema por favor tente novamente mais tarde!</h3>");
		}
	}

}
