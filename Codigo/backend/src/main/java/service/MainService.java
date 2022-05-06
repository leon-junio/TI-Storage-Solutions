package service;

import java.util.*;
import utils.HashUtils;

// imports da DAO 
import dao.DAO;
import dao.DAOToken;
import dao.DAOCliente;
import dao.DAOEstoque;
import dao.DAOFornecedor;

// imports da Model 
import model.Cliente;
import model.Fornecedor;

import spark.Request;
import spark.Response;

public class MainService {
	DAO dao = new DAO();
	DAOToken daotk = new DAOToken();
	String action = "main";

	private final String home = "/pages/home.html";

	public Object login(Request request, Response response) {
		boolean resp = false;
		String email = request.queryParams("email");
		String senha = request.queryParams("senha");
		senha = HashUtils.getHashMd5(senha);
		resp = dao.login(email, senha);
		if (resp) {
			System.out.println("Novo login: " + email);
			response.status(201);
			return "<script src=\"" + app.Aplicacao.url + "/js/acess.js" + "\"></script>\n<script>loginSucess('" + email
					+ "','" + senha + "','" + daotk.insert(email, senha) + "');\n" + "window.location.href = '"
					+ app.Aplicacao.url + home + "';</script>";
		} else {
			response.status(203);
			return "<script>alert(\"Falha ao realizar login no sistema!\"); " + "window.location.href = '"
					+ app.Aplicacao.url + "/login.html';</script>";
		}
	}

	public Object logout(Request request, Response response) {
		boolean resp = false;
		String token = request.params(":token");
		DAOToken daotk = new DAOToken();
		resp = daotk.delete(token);
		if (resp) {
			response.status(201);
			return "<script src=\"" + app.Aplicacao.url + "/js/acess.js" + "\"></script>\n<script>logout();\n"
					+ "window.location.href = '" + app.Aplicacao.url + "';</script>";
		} else {
			response.status(203);
			return "<script>alert(\"Falha ao realizar logout no sistema!\"); " + "window.location.href = '"
					+ app.Aplicacao.url + home + "';</script>";
		}
	}

	public Object check(Request request, Response response) {
		String token = request.params(":token");
		if (!token.equals("null")) {
			response.header("Content-Type", "Json; charset=utf-8");
			response.status(200);
			return utils.LeonAPI.stringToJson("" + daotk.check(token));
		} else {
			return utils.LeonAPI.stringToJson("" + daotk.check(null));
		}
	}

	public Object listar(Request request, Response response) {
		try {
			String token = request.params(":token");
			response.header("Content-Type", "Json; charset=utf-8");
			String resp = "";
			DAOEstoque daoe = new DAOEstoque();
			List<model.Estoque> estoques = null;
			Object prop = new DAOToken().convertTokenCliente(token);
			if (prop == null) {
				prop = new DAOToken().convertTokenFornecedor(token);
				Fornecedor forn = (Fornecedor) prop;
				estoques = daoe.getEstoquesUser(forn.getIdFornecedor(), 1);
			} else {
				Cliente cli = (Cliente) prop;
				estoques = daoe.getEstoquesUser(cli.getIdCliente(), 2);
			}
			System.out.println(prop + " " + estoques);
			if (prop == null || estoques == null) {
				throw new RuntimeException("Erro em tempo de execução!");
			}
			if (estoques.size() > 0) {
				response.status(200);

				for (model.Estoque estoque : estoques) {
					resp += "<tr>";
					resp += "<td>" + estoque.getNome() + "</td>\n";
					resp += "<td>Estoque ativo!</td>\n";
					resp += "<td>" + estoque.getCapacidade() + "</td>\n";
					resp += "<td class=\"d-flex align-items-center gap-5\">\n";
					resp += "<a href=\""+ app.Aplicacao.url + "/pages/home-estoque.html?name="+estoque.getNome()+"&&id=" + estoque.getIdEstoque() + "\">";
					resp += "<button type=\"button\" class=\"btn btn-outline-primary\"><i class=\"fas fa-eye\"></i></button>";
					resp += "</a>";
					resp += "<a href=\"" + app.Aplicacao.url + "/pages/home-form-estoque.html?id="
							+ estoque.getIdEstoque()
							+ "\"><button type=\"button\" class=\"btn btn-outline-warning\"><i class=\"fas fa-edit\"></i></button></a>";
					resp +=  "<button onclick=\"pergunta('Deseja realmente excluir ?','/estoque/deletar/"+estoque.getIdEstoque()+"');\" type=\"button\" class=\"btn btn-outline-danger\"><i class=\"fas fa-trash\"></i></button>";
					resp += "</td>";
					resp += "</tr>\n";
				}
			} else {
				throw new RuntimeException("Erro em tempo de execução!");
			}
			return utils.LeonAPI.stringToJson(resp);
		} catch (RuntimeException e) {
			response.status(203);
			return utils.LeonAPI.stringToJson("<tr><td>Não existem estoques cadastrados</td></tr>");
		}
	}

	public Object cadastro(Request request, Response response) {
		String tipoUsuario = request.queryParams("tipo");
		String nome = request.queryParams("nome");
		String email = request.queryParams("email");
		String senha = request.queryParams("senha");

		String userName = nome + "@" + UUID.randomUUID().toString().substring(0, 7);

		boolean resp;

		if (tipoUsuario == "fornecedor") {
			resp = new DAOFornecedor().insert(new Fornecedor(0, nome, email, userName, senha, ""));
		} else {
			resp = new DAOCliente().insert(new Cliente(0, nome, email, userName, senha));
		}

		if (resp) {
			response.status(201);

			return "<script>alert('Usuário cadastrado com sucesso!'); window.location.href = '" + app.Aplicacao.url
					+ "/login.html';</script>";
		} else {
			response.status(203);

			return "<script>alert('Não foi possível cadastrar o usuário!'); window.location.href = '"
					+ app.Aplicacao.url + "/cadastro.html';</script>";
		}
	}
}
