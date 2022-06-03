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
            } else if(senha.equals("") || senha.equals(" ") || senha.length()<4){
            	 throw new Exception("A senha não pode ser vazia e nem ser menor que 4 caracteres!");
            }else {
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
    	try {
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
    	}catch(Exception e) {
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
            	resp = "<form id=\"form-user\" method=\"post\" action=\"/perfil/atualizar/" + token + "\" class=\"form\">";
            	
            	resp += "<div class=\"row mb-3\"><div class=\"col-6\">";
            	resp += "<label for=\"tipo\" class=\"form-label\">Tipo</label> <input type=\"text\" name=\"tipo\" id=\"tipo\" class=\"form-control\" value=\"Comerciante\" readonly>";
            	resp += "</div></div>"; 
            	
            	resp += "<div class=\"row mb-3\"><div class=\"col-6\">"; 
            	resp += "<label for=\"usuario\" class=\"form-label\">Usuário</label> <input type=\"text\" readonly disabled name=\"usuario\" id=\"usuario\" class=\"form-control\" value=\"" + cli.getUsuario() + "\">";
            	resp += "</div> <div class=\"col-6\">";
            	resp += "<label for=\"email\" class=\"form-label\">Email</label> <input type=\"email\" name=\"email\" id=\"email\" class=\"form-control\" value=\"" + cli.getEmail() + "\">";
            	resp += "</div></div>"; 
            	
            	resp += "<div class=\"row mb-3\"><div class=\"col-6\">"; 
            	resp += "<label for=\"nome\" class=\"form-label\">Nome</label> <input type=\"text\" name=\"nome\" id=\"nome\" class=\"form-control\" value=\"" + cli.getNome() + "\">";
            	resp += "</div> <div class=\"col-6 d-flex align-items-end\">";
            	resp += "<a href=\"./home-trocar-senha.html\"><button type=\"button\" class=\"btn btn-outline-primary p-3\">Trocar Senha</button></a>";
            	resp += "</div></div>"; 
            	
            	resp += "<div class=\"row mb-3 pb-3 border-bottom\"><div class=\"col-6\">"; 
            	resp += "<button type=\"submit\" class=\"btn btn-outline-success p-3\">Atualizar Dados</button>";
            	resp += "</div></div>";
            	
            	resp += "<div class=\"row mb-3\"><div class=\"col-6\">"; 
            	resp += "<button onclick=\"pergunta('Deseja realmente excluir todos os seus dados ?','/perfil/deletar/" + token + "');\" type=\"button\" class=\"btn btn-outline-danger p-3\">Excluir perfil</button>";
            	resp += "</div></div>";
            	
            	resp += "</form>";
            }
            if (forn != null) {
            	resp = "<form id=\"form-user\" method=\"post\" action=\"/perfil/atualizar/" + token + "\" class=\"form\">";
            	
            	resp += "<div class=\"row mb-3\"><div class=\"col-6\">";
            	resp += "<label for=\"tipo\" class=\"form-label\">Tipo</label> <input type=\"text\" name=\"tipo\" id=\"tipo\" class=\"form-control\" value=\"Fornecedor\" readonly>";
            	resp += "</div></div>"; 
            	
            	resp += "<div class=\"row mb-3\"><div class=\"col-6\">"; 
            	resp += "<label for=\"usuario\" class=\"form-label\">Usuário</label> <input type=\"text\" readonly disabled name=\"usuario\" id=\"usuario\" class=\"form-control\" value=\"" + forn.getUsuario() + "\">";
            	resp += "</div> <div class=\"col-6\">";
            	resp += "<label for=\"email\" class=\"form-label\">Email</label> <input type=\"email\" name=\"email\" id=\"email\" class=\"form-control\" value=\"" + forn.getEmail() + "\">";
            	resp += "</div></div>"; 
            	
            	resp += "<div class=\"row mb-3\"><div class=\"col-6\">"; 
            	resp += "<label for=\"nome\" class=\"form-label\">Nome</label> <input type=\"text\" name=\"nome\" id=\"nome\" class=\"form-control\" value=\"" + forn.getNome() + "\">";
            	resp += "</div> <div class=\"col-6 d-flex align-items-end\">";
            	resp += "<a href=\"./home-trocar-senha.html\"><button type=\"button\" class=\"btn btn-outline-primary p-3\">Trocar Senha</button></a>";
            	resp += "</div></div>"; 
            	
            	resp += "<div class=\"row mb-3 pb-3 border-bottom\"><div class=\"col-6\">"; 
            	resp += "<button type=\"submit\" class=\"btn btn-outline-success p-3\">Atualizar Dados</button>";
            	resp += "</div></div>";
            	
            	resp += "<div class=\"row mb-3\"><div class=\"col-6\">"; 
            	resp += "<button onclick=\"pergunta('Deseja realmente excluir todos os seus dados ?','/perfil/deletar/" + token + "');\" type=\"button\" class=\"btn btn-outline-danger p-3\">Excluir perfil</button>";
            	resp += "</div></div>";
            	
            	resp += "</form>";

            }
            return utils.LeonAPI.stringToJson(resp);
        } catch (RuntimeException re) {
            response.status(203);
            return utils.LeonAPI.stringToJson("null");
        }
    }
}
