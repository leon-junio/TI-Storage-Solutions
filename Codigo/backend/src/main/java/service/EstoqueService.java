package service;

import spark.Request;
import spark.Response;
import dao.DAOEstoque;
import dao.DAOToken;
import model.Cliente;
import model.Estoque;
import model.Fornecedor;

public class EstoqueService {

	private DAOEstoque daoe = new DAOEstoque();

	public Object carregar(Request request, Response response) {
		try {
			int id = Integer.parseInt(request.params(":id"));
			response.header("Content-Type", "Json; charset=utf-8");
			String resp = "";
			DAOEstoque daoe = new DAOEstoque();
			Estoque obj = daoe.get(id);
			if (obj == null) {
				throw new RuntimeException("Erro em tempo de execução!");
			}
			response.status(200);
			resp = "<form class=\"form\" method=\"post\" action=\"/estoque/atualizar\">"
					+ "<input type=\"hidden\" id=\"id-estoque\" class=\"fadeIn second\" name=\"id-estoque\" value=\""
					+ obj.getIdEstoque() + "\">"
					+ "<div class=\"row mb-3\"><div class=\"col-6\"><label for=\"nome\" class=\"form-label\">Nome do Estoque</label>"
					+ "<input type=\"text\" name=\"nome\" id=\"nome\" class=\"form-control\" value=\"" + obj.getNome()
					+ "\"></div><div class=\"col-6\">"
					+ "<label for=\"capacidade\" class=\"form-label\">Capacidade de Estoque</label><input type=\"number\" name=\"capacidade\" id=\"capacidade\" class=\"form-control\" value=\""
					+ obj.getCapacidade() + "\">"
					+ "</div></div><div class=\"row\"><div class=\"col-6\"><button type=\"submit\" class=\"btn btn-outline-primary p-3\">Atualizar Estoque</button></div></div>"
					+ "</form>";
			return utils.LeonAPI.stringToJson(resp);
		} catch (RuntimeException e) {
			response.status(203);
			return utils.LeonAPI.stringToJson("<tr><td colspan=\"4\">Não existem estoques cadastrados</td></tr>");
		}
	}
	

	public Object cadastro(Request request, Response response) {
		String nome = request.queryParams("nome");
		int capacidade = Integer.parseInt(request.queryParams("capacidade"));
		String token = request.queryParams("id-token");
		Object prop = new DAOToken().convertTokenCliente(token);
		Fornecedor forn = null;
		Cliente cli = null;
		if (prop == null) {
			prop = new DAOToken().convertTokenFornecedor(token);
			forn = (Fornecedor) prop;
		} else {
			cli = (Cliente) prop;
		}
		boolean resp = false;
		Estoque obj = null;
		if (cli != null) {
			obj = new Estoque(0, capacidade, cli.getIdCliente(), 0, nome);
		} else if (forn != null) {
			obj = new Estoque(0, capacidade, 0, forn.getIdFornecedor(), nome);
		}
		resp = daoe.insert(obj);
		if (resp) {
			response.status(201);
			return "<script>alert('Estoque cadastrado com sucesso!'); window.location.href = '" + app.Aplicacao.url
					+ "/pages/home.html';</script>";
		} else {
			response.status(203);
			return "<script>alert('Não foi possível cadastrar o estoque!'); window.location.href = '"
					+ app.Aplicacao.url + "/pages/home-form-estoque.html';</script>";
		}
	}

	public Object delete(Request request, Response response) {
		int idEstoque = Integer.parseInt(request.params(":id"));
		if (daoe.delete(idEstoque)) {
			response.status(200);
			return "<script>alert('Estoque foi excluído com sucesso!'); window.location.href = '" + app.Aplicacao.url
					+ "/pages/home.html'</script>";
		} else {
			response.status(203);
			return "<script>alert('Não foi possível excluir o estoque!'); window.location.href = '" + app.Aplicacao.url
					+ "/pages/home.html'<script>";
		}
	}

	public Object atualizar(Request request, Response response) {
		int idEstoque = Integer.parseInt(request.queryParams("id-estoque"));

		String nome = request.queryParams("nome");
		int capacidade = Integer.parseInt(request.queryParams("capacidade"));

		Estoque estoque = daoe.get(idEstoque);

		estoque.setNome(nome);
		estoque.setCapacidade(capacidade);

		if (daoe.update(estoque)) {
			response.status(200);

			return "<script>alert('Estoque atualizado com sucesso!'); window.location.href = '" + app.Aplicacao.url
					+ "/pages/home.html'</script>";
		} else {
			response.status(203);

			return "<script>alert('Não foi possível atualizar o estoque!'); window.location.href = '"
					+ app.Aplicacao.url + "/pages/home-form-estoque.html?id=" + idEstoque + "'</script>";
		}

	}

	

}
