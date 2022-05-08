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

    // TEM QUE ATUALIZAR A SENHA SEPARADO POR QUE GEROU HASH DO HASH NO BD
    public Object atualizar(Request request, Response response) {
        try {
            boolean resp = false;
            String token = request.params(":token");
            String nome = request.queryParams("nome");
            String email = request.queryParams("email");
            String tipo = request.queryParams("tipo");
            Cliente cl = null;
            Fornecedor fr = null;
            if (tipo.equalsIgnoreCase("Comerciante")) {
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
                        + "/js/acess.js\"></script><script>alert('USUARIO ALTERADO COM SUCESSO, POR FAVOR REALIZE LOGIN NOVAMENTE!');logout();</script>";
            } else {
                throw new Exception("Erro de validação interna");
            }
        } catch (Exception e) {
        	e.printStackTrace();
            return "<script>alert('UM ERRO IMPEDIU QUE O USUARIO FOSSE ALTERADO - erro: "+e.getMessage()+"');window.location.href = \""
                    + Aplicacao.url + "/pages/home-usuarios.html\";</script>";
        }
    }

    public Object newPass(Request request, Response response) {
        try {
            boolean resp = false;
            String token = request.params(":token");
            String senha = request.queryParams("senha");
            String cfsenha = request.queryParams("confirmSenha");
            if (!senha.equals(cfsenha)) {
                throw new Exception("Senhas não conferem!");
            } else {
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
                    resp = daoc.updatePass(cli, senha);
                } else {
                    resp = daof.updatePass(forn, senha);
                }
                if (resp) {
                    return "<script src=\"" + Aplicacao.url
                            + "/js/acess.js\"></script><script>alert('SENHA ALTERADA COM SUCESSO, POR FAVOR REALIZE LOGIN NOVAMENTE!');onStart();window.location.href = '"
                            + Aplicacao.url + "/login.html'</script>";
                } else {
                    return "<script>alert('UM ERRO IMPEDIU QUE A SENHA FOSSE ALTERADA');window.location.href = \""
                            + Aplicacao.url + "/pages/home-usuarios.html\";</script>";
                }
            }
        } catch (Exception e) {
            return "<script>alert('UM ERRO IMPEDIU QUE A SENHA FOSSE ALTERADA - "+e.getMessage()+"');window.location.href = \""
                    + Aplicacao.url + "/pages/home-trocar-senha.html\";</script>";
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
                    + "/js/acess.js\"></script><script>alert('USUARIO EXCLUIDO COM SUCESSO!');onStart();window.location.href = '"
                    + Aplicacao.url + "'</script>";
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
                resp = "<form id=\"form-user\" method=\"post\" action=\"/perfil/atualizar/" + token
                        + "\" class=\"form\">" +
                        "<div class=\"row mb-3\"><div class=\"col-6\"><label for=\"tipo\" class=\"form-label\">Tipo</label> <input type=\"text\" name=\"tipo\" id=\"tipo\" class=\"form-control\" value=\"Comerciante\" readonly>"
                        +
                        "<div class=\"col-6\"><label for=\"nome\" class=\"form-label\">Nome</label> <input type=\"text\" name=\"nome\" id=\"nome\" value=\""
                        + cli.getNome() + "\" class=\"form-control\">"
                        +
                        "</div></div><div class=\"row mb-3\"><div class=\"col-6\"><label for=\"email\" class=\"form-label\">Email</label> <input type=\"email\" name=\"email\" id=\"email\" class=\"form-control\" value=\""
                        + cli.getEmail() + "\"></div>"
                        +
                        "<div class=\"col-6\"><label for=\"usuario\" class=\"form-label\">Usuário</label> <input type=\"text\" readonly disabled name=\"usuario\" id=\"usuario\" class=\"form-control\" value=\""
                        + cli.getUsuario() + "\"></div>"
                        +
                        "</div><div class=\"row mb-3\"><a href=\"./home-trocar-senha.html\"><button type=\"button\" class=\"btn btn-outline-primary p-3\">Trocar "
                        +
                        "Senha</button></a></div><div class=\"row\"><div class=\"col-6\"><button type=\"submit\" class=\"btn btn-outline-success p-3\">Atualizar Dados</button> </div></div><br>"
                        +
                        "<div class=\"row\"><hr><div class=\"row mb-3\"><div class=\"col-6\"><button onclick=\"pergunta('Deseja realmente excluir todos os seus dados ?','/perfil/deletar/"
                        + token
                        + "');\" type=\"button\" class=\"btn btn-outline-danger\">Excluir perfil</button></div></div></div></form>";

            }
            if (forn != null) {
                resp = "<form id=\"form-user\" method=\"post\" action=\"/perfil/atualizar/" + token
                        + "\" class=\"form\">" +
                        "<div class=\"row mb-3\"><div class=\"col-6\"><label for=\"tipo\" class=\"form-label\">Tipo</label> <input type=\"text\" name=\"tipo\" id=\"tipo\" class=\"form-control\" value=\"Fornecedor\" disabled>"
                        +
                        "</div></div><div class=\"row mb-3\"><div class=\"col-6\"><label for=\"nome\" class=\"form-label\">Nome</label> <input type=\"text\" name=\"nome\" id=\"nome\" value=\""
                        + forn.getNome() + "\" class=\"form-control\">"
                        +
                        "</div><div class=\"row mb-3\"><div class=\"col-6\"><label for=\"email\" class=\"form-label\">Email</label> <input type=\"email\" name=\"email\" id=\"email\" class=\"form-control\" value=\""
                        + forn.getEmail() + "\"></div></div></div>"
                        +
                        "<div class=\"row mb-3\"><div class=\"col-6\"><label for=\"usuario\" class=\"form-label\">Usuário</label> <input type=\"text\" readonly disabled name=\"usuario\" id=\"usuario\" class=\"form-control\" value=\""
                        + forn.getUsuario() + "\"></div>"
                        +
                        "</div><div class=\"row mb-3\"><a href=\"./home-trocar-senha.html\"><button type=\"button\" class=\"btn btn-outline-primary p-3\">Trocar "
                        +
                        "Senha</button></a></div><div class=\"row\"><div class=\"col-6\"><button type=\"submit\" class=\"btn btn-outline-success p-3\">Atualizar Dados</button> </div></div><br>"
                        +
                        "<div class=\"row\"><hr><div class=\"row mb-3\"><div class=\"col-6\"><button onclick=\"pergunta('Deseja realmente excluir todos os seus dados ?','/perfil/deletar/"
                        + token
                        + "');\" type=\"button\" class=\"btn btn-outline-danger\">Excluir perfil</button></div></div></div></form>";

            }
            return utils.LeonAPI.stringToJson(resp);
        } catch (RuntimeException re) {
            response.status(203);
            return utils.LeonAPI.stringToJson("null");
        }
    }
}
