package service;

import java.util.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

import spark.Request;
import spark.Response;
import utils.LeonAPI;
import dao.DAOEntrada;
import dao.DAOEstoque;
import dao.DAOProduto;
import dao.DAORetirada;
import dao.DAOToken;
import model.Cliente;
import model.Entrada;
import model.Estoque;
import model.Fornecedor;
import model.Produto;
import model.Retirada;

public class ProdutoService {

	private DAOProduto daop = new DAOProduto();
	private DAOEstoque daoe = new DAOEstoque();
	private DAOEntrada daoet = new DAOEntrada();
	private DAORetirada daort = new DAORetirada();
	static final SimpleDateFormat dateFormated = new SimpleDateFormat("yyyy-MM-dd");

	public Object cadastro(Request request, Response response) throws Exception {
		String nome = request.queryParams("nome");
		String descricao = request.queryParams("descricao");
		int qtd = Integer.parseInt(request.queryParams("quantidade"));
		String codigoBarras = request.queryParams("codBarras");
		String unidade = request.queryParams("unidade");

		int estoque = Integer.parseInt(request.queryParams("id-estoque"));

		// pegar datas passadas como string
		java.util.Date fabricacao = dateFormated.parse(request.queryParams("fabricacao"));
		java.util.Date validade = dateFormated.parse(request.queryParams("validade"));

		String marca = request.queryParams("marca");
		float peso = Float.parseFloat(request.queryParams("peso"));

		boolean resp = daop.insert(new Produto(0, qtd, estoque, nome, descricao, codigoBarras, unidade, marca, peso,
				new java.sql.Date(fabricacao.getTime()), new java.sql.Date(validade.getTime())));

		if (resp) {
			response.status(201);

			return "<script>alert('Produto cadastrado com sucesso!'); window.location.href = '" + app.Aplicacao.url
					+ "/pages/home-produtos.html?id=" + estoque + "';</script>";
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
					resp += "<a href=\"./home-form-produto.html?id=" + produto.getEstoque() + "&&idproduto=" + produto.getIdProduto() + "\">";
					resp += "<button type=\"button\" class=\"btn btn-outline-primary\"><i class=\"fas fa-eye\"></i></button>";
					resp += "</a>";
					resp += "<button type=\"button\" class=\"btn btn-outline-danger\" onclick=\"deletarProduto('/produto/deletar/" + produto.getEstoque() + "/" + produto.getIdProduto() + "')\"><i class=\"fas fa-trash\"></i></button>";
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

	public Object carregar(Request request, Response response) {
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
			
			resp = "<form class=\"form\" method=\"post\" action=\"/produto/atualizar\">";
			
			resp += "<input type=\"hidden\" id=\"id-produto\" name=\"id-produto\" value=\"" + obj.getIdProduto() + "\">"; 
			resp += "<input type=\"hidden\" id=\"id-estoque\" name=\"id-estoque\" value=\"" + obj.getEstoque() + "\">";
			
			resp += "<div class=\"row mb-3\"><div class=\"col-6\">"; 
			resp += "<label for=\"nome\" class=\"form-label\">Nome do Produto</label> <input type=\"text\" name=\"nome\" id=\"nome\" class=\"form-control\" value=\"" + obj.getNome() + "\">";
			resp += "</div><div class=\"col-6\">"; 
			resp += "<label for=\"descricao\" class=\"form-label\">Descrição do Produto</label> <input type=\"text\" name=\"descricao\" id=\"descricao\" class=\"form-control\" value=\"" + obj.getDescricao() + "\">";
			resp += "</div></div>";
			
			resp += "<div class=\"row mb-3\"><div class=\"col-6\">"; 
			resp += "<label for=\"quantidade\" class=\"form-label\">Quantidade</label> <input type=\"number\" name=\"quantidade\" id=\"quantidade\" class=\"form-control\" value=\"" + obj.getQuantidade() + "\">";
			resp += "</div><div class=\"col-6\">"; 
			resp += "<label for=\"codBarras\" class=\"form-label\">Código de Barras</label> <input type=\"text\" name=\"codBarras\" id=\"codBarras\" class=\"form-control\" value=\"" + obj.getCodigoBarras() + "\">";
			resp += "</div></div>";

			resp += "<div class=\"row mb-3\"><div class=\"col-6\">"; 
			resp += "<label for=\"unidade\" class=\"form-label\">Unidade</label>";
			
			resp += "<select id=\"unidade\" name=\"unidade\" class=\"form-select\">";
			
			resp += "<option value=\"AMPOLA\">AMPOLA</option>" + 
					"<option value=\"BARRA\">BARRA</option>" + 
					"<option value=\"BOMBONA\">BOMBONA</option>" + 
					"<option value=\"BISNAGA\">BISNAGA</option>" + 
					"<option value=\"CAIXA\">CAIXA</option>" +
					"<option value=\"CARRETEL\">CARRETEL</option>" +
					"<option value=\"CENTO\">CENTO</option>" + 
					"<option value=\"CENTIMETRO\">CENTIMETRO</option>" + 
					"<option value=\"CONJUNTO\">CONJUNTO</option>" +
					"<option value=\"CHAPA\">CHAPA</option>" + 
					"<option value=\"DUZIA\">DUZIA</option>" + 
					"<option value=\"DOSE\">DOSE</option>" + 
					"<option value=\"EMBALAGEM\">EMBALAGEM</option>" +
					"<option value=\"ENGRADADO\">ENGRADADO</option>" +
					"<option value=\"ESTOJO\">ESTOJO</option>" +
					"<option value=\"FARDO\">FARDO</option>" +
					"<option value=\"FRASCO\">FRASCO</option>" +
					"<option value=\"GALÃO\">GALÃO</option>" +
					"<option value=\"GRAMA\">GRAMA</option>" +
					"<option value=\"JOGO\">JOGO</option>" +
					"<option value=\"LATA\">LATA</option>" + 
					"<option value=\"LITRO\">LITRO</option>" +
					"<option value=\"METRO\">METRO</option>" +
					"<option value=\"MILHEIRO\">MILHEIRO</option>" +
					"<option value=\"M²\">M²</option>" +
					"<option value=\"M³\">M³</option>" +
					"<option value=\"PACOTE\">PACOTE</option>" +
					"<option value=\"PALLET\">PALLET</option>" +
					"<option value=\"PAR\">PAR</option>" +
					"<option value=\"PEÇA\">PEÇA</option>" +
					"<option value=\"QUILO\">QUILO</option>" +
					"<option value=\"RESMA\">RESMA</option>" +
					"<option value=\"ROLO\">ROLO</option>" +
					"<option value=\"SACHE\">SACHE</option>" +
					"<option value=\"SACO\">SACO</option>" +
					"<option value=\"SERINGA\">SERINGA</option>" +
					"<option value=\"TONELADA\">TONELADA</option>" +
					"<option value=\"TUBO\">TUBO</option>" +
					"<option value=\"UNIDADE\">UNIDADE</option>" +
					"<option value=\"VOLUME\">VOLUME</option>";
			
			resp += "</select>"; 
			
			resp += "<input type=\"hidden\" id=\"unidade-produto\" name=\"unidade-produto\" value=\"" + obj.getUnidade() + "\">";
								
			resp += "</div><div class=\"col-6\">"; 
			resp += "<label for=\"fabricacao\" class=\"form-label\">Ano de Fabricação</label> <input type=\"date\" name=\"fabricacao\" id=\"fabricacao\" class=\"form-control\" value=\"" + dateFormated.format(obj.getFabricacao()) + "\">";
			resp += "</div></div>";

			resp += "<div class=\"row mb-3\"><div class=\"col-6\">"; 
			resp += "<label for=\"validade\" class=\"form-label\">Ano de Validade</label> <input type=\"date\" name=\"validade\" id=\"validade\" class=\"form-control\" value=\"" + dateFormated.format(obj.getValidade()) + "\">";
			resp += "</div><div class=\"col-6\">"; 
			resp += "<label for=\"marca\" class=\"form-label\">Marca do Produto</label> <input type=\"text\" name=\"marca\" id=\"marca\" class=\"form-control\" value=\"" + obj.getMarca() + "\">";
			resp += "</div></div>";
			
			resp += "<div class=\"row mb-3\"><div class=\"col-6\">"; 
			resp += "<label for=\"peso\" class=\"form-label\">Peso</label> <input type=\"number\" min=\"0\" max=\"100\" step=\".01\" name=\"peso\" id=\"peso\" class=\"form-control\" value=\"" + obj.getPeso() + "\">";
			resp += "</div></div>";

			resp += "<div class=\"row mb-3\"><div class=\"col-6\">"; 
			resp += "<button type=\"submit\" class=\"btn btn-outline-primary p-3\">Atualizar Produto</button>";
			resp += "</div></div>";
			
			resp += "</form>";

			return utils.LeonAPI.stringToJson(resp);
		} catch (RuntimeException e) {
			response.status(203);
			return utils.LeonAPI.stringToJson("null");
		}
	}

	public Object deletar(Request request, Response response) {
		int idEstoque = Integer.parseInt(request.params(":idestoque"));
		int idProduto = Integer.parseInt(request.params(":idproduto"));
		try {
			if (daop.delete(idProduto)) {
				response.status(200);
				return "<script>alert('produto foi excluído com sucesso!'); window.location.href = '" + app.Aplicacao.url
						+  "/pages/home-produtos.html?id=" + idEstoque + "'</script>";
			} else {
				response.status(203);
				return "<script>alert('Não foi possível excluir o produto!'); window.location.href = '" + app.Aplicacao.url
						+ "/pages/home-produtos.html?id=" + idEstoque + "'</script>";
			}
		}catch(Exception e) {
			response.status(203);
			return "<script>alert('Não foi possível excluir o produto pois o mesmo possuí relações internas estabelescidas em entradas e retiradas'); window.location.href = '" + app.Aplicacao.url
					+  "/pages/home-produtos.html?id=" + idEstoque + "'</script>";
		}
	}

	public Object atualizar(Request request, Response response) {
		int idProduto = Integer.parseInt(request.queryParams("id-produto"));
		int estoque = Integer.parseInt(request.queryParams("id-estoque"));
		
		try {
			String nome = request.queryParams("nome");
			String descricao = request.queryParams("descricao");
			int qtd = Integer.parseInt(request.queryParams("quantidade"));
			String codigoBarras = request.queryParams("codBarras");
			String unidade = request.queryParams("unidade");

			// pegar datas passadas como string
			java.util.Date fabricacao = dateFormated.parse(request.queryParams("fabricacao"));
			java.util.Date validade = dateFormated.parse(request.queryParams("validade"));

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
						+ "/pages/home-produtos.html?id=" + estoque + "'</script>";
			} else {
				throw new Exception("Erro em tempo de execução!");
			}
		} catch (Exception e) {
			response.status(203);

			System.out.print(e.getMessage());
			e.printStackTrace();
			
			return "<script>alert('Não foi possível atualizar o produto!'); window.location.href = '"
					+ app.Aplicacao.url + "/pages/home-form-produto.html?id=" + estoque + "&&idproduto=" + idProduto + "'</script>";
		}

	}

	public Object loadOptions(Request request, Response response) {
		try {
			int idEstoque = Integer.parseInt(request.params("id"));
			response.header("Content-Type", "Json; charset=utf-8");
			boolean resp = false;
			String token = request.params("token");
			Object prop = new DAOToken().convertTokenCliente(token);
			Fornecedor forn = null;
			Cliente cli = null;
			Estoque est = daoe.get(idEstoque);
			if (prop == null) {
				prop = new DAOToken().convertTokenFornecedor(token);
				forn = (Fornecedor) prop;
				if (forn != null && est.getFornecedor() == forn.getIdFornecedor()) {
					resp = true;
				} else {
					resp = false;
				}

			} else {
				cli = (Cliente) prop;
				if (cli != null && est.getCliente() == cli.getIdCliente()) {
					resp = true;
				} else {
					resp = false;
				}
			}
			if (resp) {
				String data = "<option value=\"\">Selecione um produto</option>";
				
				ArrayList<Produto> produtos = daop.getByEstoque(idEstoque);
				if (produtos.isEmpty()) {
					throw new RuntimeException("empty_list");
				} else {
					for (Produto prod : produtos) {
						data += "<option value=\"" + prod.getIdProduto() + "\"><data value=\"" + prod.getQuantidade()
								+ "\">" + prod.getNome() + " - "+prod.getQuantidade()+" unidade(s) </data></option>\n";
					}
				}
				response.status(200);
				return utils.LeonAPI.stringToJson(data);
			} else {
				throw new RuntimeException("key_failed");
			}
		} catch (RuntimeException re) {
			response.status(203);
			return utils.LeonAPI.stringToJson(re.getMessage());
		} catch (Exception e) {
			response.status(203);
			return utils.LeonAPI.stringToJson("null");
		}
	}

	public Object getQtd(Request request, Response response) {
		int id = Integer.parseInt(request.params(":idproduto")); 
		
		Produto produto = daop.get(id); 
		
		return utils.LeonAPI.stringToJson("" + produto.getQuantidade());
	}
	
	// criar retirada
	public Object retirada(Request request, Response response) {
		int idEstoque = Integer.parseInt(request.queryParams("id-estoque"));
		try {
			boolean resp = false;
			String token = request.params("token");
			int produto = Integer.parseInt(request.queryParams("produtos"));
			int qtd = Integer.parseInt(request.queryParams("quantidade"));
			java.sql.Date dataRet = new java.sql.Date(LeonAPI.formatDate(request.queryParams("dtRetirada")).getTime()); //erro na formatação da data
			String obs = request.queryParams("obs"); 
			
			Retirada ret = null;
			
			Estoque estoque = daoe.get(idEstoque);
			Object prop = new DAOToken().convertTokenCliente(token);
			Fornecedor forn = null;
			Cliente cli = null;
			if (prop == null) {
				prop = new DAOToken().convertTokenFornecedor(token);
				forn = (Fornecedor) prop;
			} else {
				cli = (Cliente) prop;
			}

			if (cli != null && cli.getIdCliente() == estoque.getCliente()) {
				resp = true;
			} else if (forn != null && forn.getIdFornecedor() == estoque.getFornecedor()) {
				resp = true;
			} else {
				throw new Exception("Erro de validação e autenticação!");
			}
			
			if (resp) {
				ret = new Retirada(0, estoque.getIdEstoque(), qtd, produto, obs, dataRet);
				if (!daort.insert(ret, produto)) {
					throw new Exception("Falha ao inserir a retirada na base de dados");
				}
				response.status(200);
				return "<script>alert('Retirada de produto realizada com sucesso!'); window.location.href = '"
						+ app.Aplicacao.url + "/pages/home-estoque.html?id=" + idEstoque + "'</script>";
			} else {
				throw new Exception("Retirada não permitida");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.status(203);
			return "<script>alert('Não foi possível dar entrada no produto selecionado - erro: "+e.getMessage()+"'); window.location.href = '"
					+ app.Aplicacao.url + "/pages/home-adicionar.html?id=" + idEstoque + "'</script>";
		}
	}

	public Object entrada(Request request, Response response) {
		int idEstoque = Integer.parseInt(request.queryParams("id-estoque"));
		try {
			boolean resp = false;
			String token = request.params("token");
			int produto = Integer.parseInt(request.queryParams("produtos"));
			int qtd = Integer.parseInt(request.queryParams("quantidade"));
			java.sql.Date dataEnt = new java.sql.Date(
					LeonAPI.formatDate(request.queryParams("dtentrada")).getTime()); //erro na formatação da data
			Entrada ent = null;
			Estoque estoque = daoe.get(idEstoque);
			Object prop = new DAOToken().convertTokenCliente(token);
			Fornecedor forn = null;
			Cliente cli = null;
			if (prop == null) {
				prop = new DAOToken().convertTokenFornecedor(token);
				forn = (Fornecedor) prop;
			} else {
				cli = (Cliente) prop;
			}

			if (cli != null && cli.getIdCliente() == estoque.getCliente()) {
				resp = true;

			} else if (forn != null && forn.getIdFornecedor() == estoque.getFornecedor()) {
				resp = true;

			} else {
				throw new Exception("Erro de validação e autenticação!");
			}
			if (resp) {
				ent = new Entrada(0, qtd, estoque.getIdEstoque(), 0, dataEnt);
				if (!daoet.insert(ent, produto)) {
					throw new Exception("Falha ao inserir a entrada na base de dados");
				}
				response.status(200);
				return "<script>alert('Entrada de produto realizada com sucesso!'); window.location.href = '"
						+ app.Aplicacao.url + "/pages/home-estoque.html?id=" + idEstoque + "'</script>";
			} else {
				throw new Exception("Entrada não permitida");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.status(203);
			return "<script>alert('Não foi possível dar entrada no produto selecionado - erro: "+e.getMessage()+"'); window.location.href = '"
					+ app.Aplicacao.url + "/pages/home-adicionar.html?id=" + idEstoque + "'</script>";
		}
	}

}
