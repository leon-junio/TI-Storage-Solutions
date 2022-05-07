package service;

import java.util.*;
import java.text.SimpleDateFormat;

import spark.Request;
import spark.Response;
import dao.DAOProduto;
import model.Produto;

public class ProdutoService {

	private DAOProduto daop = new DAOProduto(); 
	static final SimpleDateFormat dateFormated = new SimpleDateFormat("yyyy-MM-dd"); 

	public Object cadastro(Request request, Response response) throws Exception {
		String nome = request.queryParams("nome");
 		String descricao = request.queryParams("descricao");
		int qtd = Integer.parseInt(request.queryParams("quantidade")); 
		String codigoBarras = request.queryParams("codBarras"); 
		String unidade = request.queryParams("unidade"); 

		int estoque = Integer.parseInt(request.queryParams("id-estoque"));

		// pegar datas passadas como string
		Date fabricacao = dateFormated.parse(request.queryParams("fabricacao")); 
		Date validade = dateFormated.parse(request.queryParams("validade"));
		
		String marca = request.queryParams("marca"); 
		float peso = Float.parseFloat(request.queryParams("peso"));

		boolean resp = daop.insert(new Produto(0, qtd, estoque, nome, descricao, codigoBarras, unidade, marca, peso, new java.sql.Date(fabricacao.getTime()), new java.sql.Date(validade.getTime())));

		if (resp) {
			response.status(201);

			return "<script>alert('Produto cadastrado com sucesso!'); window.location.href = '" + app.Aplicacao.url + "/pages/home-produtos.html?id=" + estoque + "';</script>";
		} else {
			response.status(203);

			return "<script>alert('Não foi possível cadastrar o Produto!'); window.location.href = '"
					+ app.Aplicacao.url + "/pages/home-form-produto.html?id=" + estoque + "';</script>";
		}
	}

	public Object listar(Request request, Response response) {
		try {
			int estoque = Integer.parseInt(request.params(":id"));
			response.header("Content-Type", "Json; charset=utf-8");

			String resp = "";
			List<model.Produto> produtos = null;
			
			produtos = daop.getProdutosEstoque(estoque);
			
			System.out.println(produtos);

			if (produtos == null) {
				throw new RuntimeException("Erro em tempo de execução!");
			}

			if (produtos.size() > 0) {
				response.status(200);

				for (model.Produto produto : produtos) {
					resp += "<tr>";
					resp += "<th scope=\"row\">" + produto.getIdProduto() + "</th>\n";
					resp += "<td>" + produto.getNome() + "</td>\n";
					resp += "<td>" + produto.getDescricao() + "</td>\n";
					resp += "<td>" + produto.getMarca() + "</td>\n";
					resp += "<td>" + produto.getQuantidade() + "</td>\n";
					resp += "<td class=\"d-flex align-items-center gap-5\">\n";
					resp += "<a href=\"./home-form-produto.html?idproduto=" + produto.getIdProduto() + "\">";
					resp += "<button type=\"button\" class=\"btn btn-outline-primary\"><i class=\"fas fa-eye\"></i></button>";
					resp += "</a>";
					resp += "<a href=\"/produto/deletar/" + produto.getIdProduto() + "\"><button type=\"button\" class=\"btn btn-outline-danger\"><i class=\"fas fa-trash\"></i></button></a>";
					resp += "</td>";
					resp += "</tr>\n";
				}
			} else {
				throw new RuntimeException("Erro em tempo de execução!");
			}
			return utils.LeonAPI.stringToJson(resp);
		} catch (RuntimeException e) {
			response.status(203);

			return utils.LeonAPI.stringToJson("<tr><td colspan=\"6\">Não existem produtos cadastrados</td></tr>");
		} catch (Exception e) {
			response.status(203); 

			return utils.LeonAPI.stringToJson("<tr><td colspan=\"6\">Não existem produtos cadastrados</td></tr>");
		}
	}

	public Object carregar(Request request, Response response){
		try {
			int id = Integer.parseInt(request.params(":id"));
			response.header("Content-Type", "Json; charset=utf-8");
			String resp = ""; 

			DAOProduto daop = new DAOProduto();
			Produto obj = daop.get(id); 

			if (obj == null) {
				throw new RuntimeException("Erro em tempo de execução!");
			}

			response.status(200);
			resp = "<form class=\"form\" method=\"post\" action=\"/produto/atualizar\">"
					+ "<input type=\"hidden\" id=\"id-produto\" class=\"fadeIn second\" name=\"id-produto\" value=\""
					+ obj.getIdProduto() + "\">"
					+ "<input type=\"hidden\" id=\"id-estoque\" name=\"id-estoque\" value\"" + obj.getEstoque() + "\">"
					+ "<div class=\"row mb-3\"><div class=\"col-6\"><label for=\"nome\" class=\"form-label\">Nome do Produto</label>"
					+ "<input type=\"text\" name=\"nome\" id=\"nome\" class=\"form-control\" value=\"" + obj.getNome()
					+ "\"></div><div class=\"col-6\">"
					+ "<label for=\"descricao\" class=\"form-label\">Descrição do Produto</label><input type=\"text\" name=\"descricao\" id=\"descricao\" "
					+ "class=\"form-control\" value=\"" + obj.getDescricao() + "\"></div></div>"
					
					+ "<div class=\"row mb-3\"><div class=\"col-6\"><label for=\"quantidade\" class=\"form-label\">Quantidade</label>"
					+ "<input type=\"number\" name=\"quantidade\" id=\"quantidade\" class=\"form-control\" "
					+ "value=\"" + obj.getQuantidade() + "\"></div><div class=\"col-6\">"
					+ "<label for=\"codBarras\" class=\"form-label\">Código de Barras</label><input type=\"text\" name=\"codBarras\" id=\"codBarras\" " 
					+ "class=\"form-control\" value=\"" + obj.getCodigoBarras() + "\"></div></div>"

					+ "<div class=\"row mb-3\"><label for=\"unidade\" class=\"form-label\">Unidade</label>"
					+ "<input type=\"number\" name=\"unidade\" id=\"unidade\" class=\"form-control\" "
					+ "value=\"" + obj.getUnidade() + "\"></div><div class=\"col-6\">"
					+ "<label for=\"fabricacao\" class=\"form-label\">Ano de Fabricação</label><input type=\"date\" name=\"fabricacao\" id=\"fabricacao\" " 
					+ "class=\"form-control\" value=\"" + utils.LeonAPI.formatHoras(obj.getFabricacao()) + "\"></div></div>"

					+ "<div class=\"row mb-3\"><label for=\"validade\" class=\"form-label\">Ano de Validade</label>"
					+ "<input type=\"date\" name=\"validade\" id=\"validade\" class=\"form-control\" "
					+ "value=\"" + utils.LeonAPI.formatHoras(obj.getValidade()) + "\"></div><div class=\"col-6\">"
					+ "<label for=\"marca\" class=\"form-label\">Marca do Produto</label><input type=\"number\" name=\"marca\" id=\"marca\" " 
					+ "class=\"form-control\" value=\"" + obj.getMarca() + "\"></div></div>"

					+ "<div class=\"row mb-3\"><label for=\"peso\" class=\"form-label\">Peso</label>"
					+ "<input type=\"number\" min=\"0\" max=\"100\" step=\".01\" name=\"peso\" id=\"peso\" class=\"form-control\" "
					+ "value=\"" + obj.getPeso() + "\"></div></div>"

					+ "<div class=\"row\"><div class=\"col-6\"><button type=\"submit\" class=\"btn btn-outline-primary p-3\">Atualizar Produto</button></div></div>"
					+ "</form>";
			return utils.LeonAPI.stringToJson(resp);
		} catch (RuntimeException e) {
			response.status(203);
			return utils.LeonAPI.stringToJson("null");
		}
	}

	public Object deletar(Request request, Response response) {
		int idProduto = Integer.parseInt(request.params(":id"));
		if (daop.delete(idProduto)) {
			response.status(200);
			return "<script>alert('produto foi excluído com sucesso!'); window.location.href = '" + app.Aplicacao.url
					+ "/pages/home-produto.html";
		} else {
			response.status(203);
			return "<script>alert('Não foi possível excluir o produto!'); window.location.href = '"
					+ app.Aplicacao.url + "/pages/home-form-produto.html";
		}
	}

	public Object atualizar(Request request, Response response) {
		int idProduto = Integer.parseInt(request.queryParams("id-produto"));
		
		try {
			String nome = request.queryParams("nome");
			String descricao = request.queryParams("descricao");
			int qtd = Integer.parseInt(request.queryParams("quantidade")); 
			String codigoBarras = request.queryParams("codBarras"); 
			String unidade = request.queryParams("unidade"); 

			int estoque = Integer.parseInt(request.queryParams("estoque"));

			// pegar datas passadas como string
			Date fabricacao = dateFormated.parse(request.queryParams("fabricacao")); 
			Date validade = dateFormated.parse(request.queryParams("validade"));
			
			String marca = request.queryParams("marca"); 
			float peso = Float.parseFloat(request.queryParams("peso"));

			Produto produto = daop.get(idProduto);

			produto.setNome(nome); 
			produto.setDescricao(descricao); 
			produto.setQuantidade(qtd);
			produto.setCodigoBarras(codigoBarras);
			produto.setUnidade(unidade); 
			produto.setEstoque(estoque);

			produto.setFabricacao(new java.sql.Date(fabricacao.getTime()));
			produto.setValidade(new java.sql.Date(validade.getTime()));

			produto.setMarca(marca);
			produto.setPeso(peso);

			if (daop.update(produto)) {
				response.status(200);

				return "<script>alert('Produto atualizado com sucesso!'); window.location.href = '" + app.Aplicacao.url
						+ "/pages/home-produto.html'</script>";
			} else {
				throw new Exception("Erro em tempo de execução!");
			}
		} catch (Exception e) {
			response.status(203);

			return "<script>alert('Não foi possível atualizar o produto!'); window.location.href = '"
						+ app.Aplicacao.url + "/pages/home-form-produto.html?id=" + idProduto + "'</script>";
		}

	}
}
