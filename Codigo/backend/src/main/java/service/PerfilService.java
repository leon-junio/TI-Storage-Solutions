package service;

import spark.Request;
import spark.Response;
import app.Aplicacao;
import dao.DAOCliente;
import dao.DAOFornecedor;
import dao.DAOToken;
import model.Cliente;
import model.Fornecedor;

public class PerfilService {

    private DAOCliente daoc = new DAOCliente();
    private DAOFornecedor daof = new DAOFornecedor();

    public Object atualizar(Request request, Response response) {
        try {
            boolean resp = false;
            String token = request.params(":token");
            String nome = request.queryParams("nome");
            String email = request.queryParams("email");
            String tipo = request.queryParams("tipo");
            Cliente cl = null;
            Fornecedor fr = null;
            if (tipo.equalsIgnoreCase("Cliente")) {
                cl = (Cliente) new DAOToken().convertTokenCliente(token);
                cl.setEmail(email);
                cl.setNome(nome);
                resp = daoc.update(cl);
            } else if (tipo.equalsIgnoreCase("Fornecedor")) {
                fr = (Fornecedor) new DAOToken().convertTokenFornecedor(token);
                fr.setEmail(email);
                fr.setNome(nome);
                resp = daof.update(fr);
            }
            if (resp) {
                return "<script src=\"" + Aplicacao.url
                        + "/acess.js\"></script><script>alert('USUARIO ALTERADO COM SUCESSO, POR FAVOR REALIZE LOGIN NOVAMENTE!');logout();</script>";
            } else {
                return "<script>alert('UM ERRO IMPEDIU QUE O USUARIO FOSSE ALTERADO');window.location.href = \""
                        + Aplicacao.url + "/pages/home-usuarios.html\";</script>";
            }
        } catch (Exception e) {
            return "<script>alert('UM ERRO IMPEDIU QUE O USUARIO FOSSE ALTERADO');window.location.href = \""
                    + Aplicacao.url + "/pages/home-usuarios.html\";</script>";
        }
    }

    public Object deletar(Request request, Response response) {
        String token = request.params(":token");
        response.header("Content-Type", "Json; charset=utf-8");
        boolean resp = false;
        Fornecedor forn = null;
        Cliente cli = null;
        Object prop = new DAOToken().convertTokenCliente(token);
        if (prop == null) {
            prop = new DAOToken().convertTokenFornecedor(token);
            forn = (Fornecedor) prop;
        } else {
            cli = (Cliente) prop;
        }
        if (cli != null) {
            resp = daoc.delete(cli.getIdCliente());
        } else {
            resp = daof.delete(forn.getIdFornecedor());
        }
        if (resp) {
            return "<script src=\"" + Aplicacao.url
                    + "/acess.js\"></script><script>alert('USUARIO EXCLUIDO COM SUCESSO!');logout();</script>";
        } else {
            return "<script>alert('UM ERRO IMPEDIU QUE O USUARIO FOSSE EXCLUIDO');window.location.href = \""
                    + Aplicacao.url + "/pages/home-usuarios.html\";</script>";
        }
    }


    public Object carregar(Request request, Response response) {
        try {
            String token = request.params(":token");
            response.header("Content-Type", "Json; charset=utf-8");
            String resp = "";
            Fornecedor forn = null;
            Cliente cli = null;
            Object prop = new DAOToken().convertTokenCliente(token);
            if (prop == null) {
                prop = new DAOToken().convertTokenFornecedor(token);
                forn = (Fornecedor) prop;
            } else {
                cli = (Cliente) prop;
            }
            if (forn == null && cli == null) {
                throw new RuntimeException("Erro em tempo de execução!");
            }
            response.status(200);
            if (cli != null) {
                resp = "<form id=\"form-user\" action=\"/perfil/atualizar/" + token + "\" class=\"form\">" +
                        "<div class=\"row mb-3\"><div class=\"col-6\"><label for=\"tipo\" class=\"form-label\">Tipo</label> <input type=\"text\" name=\"tipo\" id=\"tipo\" class=\"form-control\" value=\"Cliente\" required>"
                        +
                        "</div></div><div class=\"row mb-3\"><div class=\"col-6\"><label for=\"nome\" class=\"form-label\">Nome</label> <input type=\"text\" name=\"nome\" id=\"nome\" value=\""
                        + cli.getNome() + "\" class=\"form-control\">"
                        +
                        "</div><div class=\"col-6\"><label for=\"email\" class=\"form-label\">Email</label> <input type=\"email\" name=\"email\" id=\"email\" class=\"form-control\" value=\""
                        + cli.getEmail() + "\"></div></div>"
                        +
                        "<div class=\"row mb-3\"><div class=\"col-6\"><label for=\"usuario\" class=\"form-label\">Usuário</label> <input type=\"text\" readonly disabled name=\"usuario\" id=\"usuario\" class=\"form-control\" value=\""
                        + cli.getUsuario() + "\"></div>"
                        +
                        "</div><div class=\"row mb-4\"><div class=\"col-6 d-flex justify-content-start align-items-end\"><a href=\"./home-trocar-senha.html\"><button type=\"button\" class=\"btn btn-outline-primary p-3\">Trocar"
                        +
                        "Senha</button></a></div></div><div class=\"row\"><div class=\"col-6\"><button type=\"submit\" class=\"btn btn-outline-success p-3\">AtualizarDados</button> </div></div>"
                        +
                        "<button onclick=\"pergunta('Deseja realmente excluir todos os seus dados ?','/perfil/deletar/"
                        + token
                        + "');\" type=\"button\" class=\"btn btn-outline-danger\"> EXCLUIR PERFIL ?<i class=\"fas fa-trash\"></i></button></form>";

            }
            if (forn != null) {
                resp = "<form id=\"form-user\" action=\"/perfil/atualizar/" + token + "\" class=\"form\">" +
                        "<div class=\"row mb-3\"><div class=\"col-6\"><label for=\"tipo\" class=\"form-label\">Tipo</label> <input type=\"text\" name=\"tipo\" id=\"tipo\" class=\"form-control\" value=\"Fornecedor\" required>"
                        +
                        "</div></div><div class=\"row mb-3\"><div class=\"col-6\"><label for=\"nome\" class=\"form-label\">Nome</label> <input type=\"text\" name=\"nome\" id=\"nome\" value=\""
                        + forn.getNome() + "\" class=\"form-control\">"
                        +
                        "</div><div class=\"col-6\"><label for=\"email\" class=\"form-label\">Email</label> <input type=\"email\" name=\"email\" id=\"email\" class=\"form-control\" value=\""
                        + forn.getEmail() + "\"></div></div>"
                        +
                        "<div class=\"row mb-3\"><div class=\"col-6\"><label for=\"usuario\" class=\"form-label\">Usuário</label> <input type=\"text\" readonly disabled name=\"usuario\" id=\"usuario\" class=\"form-control\" value=\""
                        + forn.getUsuario() + "\"></div>"
                        +
                        "</div><div class=\"row mb-4\"><div class=\"col-6 d-flex justify-content-start align-items-end\"><a href=\"./home-trocar-senha.html\"><button type=\"button\" class=\"btn btn-outline-primary p-3\">Trocar"
                        +
                        "Senha</button></a></div></div><div class=\"row\"><div class=\"col-6\"><button type=\"submit\" class=\"btn btn-outline-success p-3\">AtualizarDados</button> </div></div>"
                        +
                        "<button onclick=\"pergunta('Deseja realmente excluir todos os seus dados ?','/perfil/deletar/"
                        + token
                        + "');\" type=\"button\" class=\"btn btn-outline-danger\"> EXCLUIR PERFIL ?<i class=\"fas fa-trash\"></i></button></form>";

            }
            return utils.LeonAPI.stringToJson(resp);
        } catch (RuntimeException re) {
            response.status(203);
            return utils.LeonAPI.stringToJson("null");
        }
    }
}
