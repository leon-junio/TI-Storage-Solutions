package service;

import java.util.*;
import utils.HashUtils;

// imports da DAO 
import dao.DAO;
import dao.DAOToken;
import dao.DAOCliente; 
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
        System.out.println("LOGIN INIT");
        boolean resp = false;
        String email = request.queryParams("email");
        String senha = request.queryParams("senha");
        resp = dao.login(email, senha);
        System.out.println(resp);
        if (resp) {
            response.status(201);
            return "<script src=\""+app.Aplicacao.url+"/js/acess.js"+"\"></script>\n<script>loginSucess('"+email+"','"+senha+"','"+daotk.insert(email, senha)+"');\n"+
            "window.location.href = '"+app.Aplicacao.url+home+"';</script>";
        } else {
            //response.status(404);
            response.status(203);
            return "<script>alert(\"Falha ao realizar login no sistema!\"); "+
            "window.location.href = '"+app.Aplicacao.url+"/login.html';</script>";
        }
    }

    public Object check(Request request, Response response) {
        String token = request.params(":token");
        response.header("Content-Type", "Json; charset=utf-8");
        response.status(200);
        return utils.LeonAPI.stringToJson(""+daotk.check(token));
    }
    
    public Object listar(Request request, Response response) {
        String token = request.params(":token");
        Object prop = new DAOToken().convertToken(token);

        String resp = "";

        List<model.Estoque> estoques = new DAOEstoque().get(prop.idcliente);

        response.header("Content-Type", "Json; charset=utf-8");

        if (estoques.length > 0) {
            response.status(200); 

            for (model.Estoque estoque : estoques) {
                resp += "<tr>";
                resp += "<td>" + estoque.nome + "</td>\n";
                resp += "<td>" + estoque.descricao + "</td>\n";
                resp += "<td>" + estoque.quantidade + "</td>\n";
                resp += "<td class=\"d-flex align-items-center gap-5\">\n";
                resp += "<a href=\"./home-estoque.html\">"; 
                resp += "<button type=\"button\" class=\"btn btn-outline-primary\"><i class=\"fas fa-eye\"></i></button>";
                resp += "</a>"; 
                resp += "<button type=\"button\" class=\"btn btn-outline-warning\"><i class=\"fas fa-edit\"></i></button>";
                resp += "<button type=\"button\" class=\"btn btn-outline-danger\"><i class=\"fas fa-trash\"></i></button>";
                resp += "</td>";
                resp = "</tr>\n"
            }
        } else {
            response.status(203); 

            resp += "<tr><td>Não existem estoques cadastrados</td></tr>"; 
        }

        return utils.LeonAPI.stringToJson(resp);
    }

    public Object cadastro(Request request, Response response) {
        String tipoUsuario = request.queryParams("tipo");
        String nome = request.queryParams("nome"); 
        String email = request.queryParams("email"); 
        String senha = request.queryParams("senha"); 

        String userName = nome + "@" + UUID.randomUUID().toString().substring(0,7);

        boolean resp;
        
        if (tipoUsuario == "fornecedor") {
            resp = new DAOFornecedor().insert(new Fornecedor(0, nome, email, userName, senha, ""));
        } else {
            resp = new DAOCliente().insert(new Cliente(0, nome, email, userName, senha));
        }

        if (resp) {
            response.status(201); 

            return "<script>alert('Usuário cadastrado com sucesso!'); window.location.href = '" + app.Aplicacao.url + "/login.html';</script>";
        } else {
            response.status(203);

            return "<script>alert('Não foi possível cadastrar o usuário!'); window.location.href = '" + app.Aplicacao.url + "/cadastro.html';</script>";
        }
    }
}
