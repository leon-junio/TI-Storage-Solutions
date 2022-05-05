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
