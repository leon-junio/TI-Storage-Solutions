package service;

import java.util.List;

import dao.DAOFornecedor;
import spark.Request;
import spark.Response;

public class FornecedorService {

	public FornecedorService() {
	}

	DAOFornecedor daof = new DAOFornecedor();

	public Object listar(Request request, Response response) {
		try {
			int estoque = Integer.parseInt(request.params(":id"));
			response.header("Content-Type", "Json; charset=utf-8");

			String resp = "";
			List<model.Fornecedor> fornecedores = null;
			fornecedores = daof.get();

			if (fornecedores == null) {
				throw new RuntimeException("Erro em tempo de execução!");
			}

			if (fornecedores.size() > 0) {
				response.status(200);
				for (model.Fornecedor forn : fornecedores) {
				resp+=	"<a id=\"item-f\" href=\"mailto:"+forn.getEmail()+"\" style=\"text-decoration: none !important;\">";
                resp+= "<div class=\"card-counter btn-secondary\">";
                resp+= "       <div class=\"row\">";
                resp+= "            <div class=\"col-3\">";
                 resp+="               <i class=\"fas fa-truck\"></i>";
                 resp+="           </div>";
                 resp+="           <div class=\"col-9\">";
                 resp+="               <span class=\"count-numbers\">"+forn.getNome()+"</span> <span class=\"count-name\">"+forn.getEmail()+"</span>";
                 resp+="           </div>";
                 resp+="</div>";
                    resp+="</div>";
                resp+="</a>";
				}
			} else {
				throw new RuntimeException("Erro em tempo de execução!");
			}
			return utils.LeonAPI.stringToJson(resp);
		} catch (RuntimeException e) {
			response.status(203);
			return utils.LeonAPI.stringToJson("<h3>Não existem fornecedores cadastrados no sistema no momento!</h3>");
		} catch (Exception e) {
			response.status(203);
			return utils.LeonAPI.stringToJson("<h3>Não existem fornecedores cadastrados no sistema no momento!</h3>");
		}
	}

}
