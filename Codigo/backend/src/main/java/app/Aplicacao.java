package app;

import static spark.Spark.*;

import service.FornecedorService;
import service.*;


public class Aplicacao {
	
	private static ProdutoService produtoService = new ProdutoService();
	private static HomeService homeService = new HomeService();
    private static MainService mainService = new MainService();
	private static ClienteService clienteService = new ClienteService();
	private static FornecedorService fornecedorService = new FornecedorService();
	private static EstoqueService estoqueService = new EstoqueService();
	public static final int porta = 6789;
	public static final String url = "http://localhost:" + porta;
	
    public static void main(String[] args) {
       
    	
    	port(porta);
        
        staticFiles.location("/public");
        
        /**
         *  REQUISIÇÕES DOS SERVIÇOS DO MAIN 
         */
        get("/main/check/:token", (request, response) -> mainService.check(request, response));
        get("/main/listar/:token", (request, response) -> mainService.listar(request, response));
        post("/main/login", (request, response) -> mainService.login(request, response));
        post("/main/cadastro", (request, response) -> mainService.cadastro(request, response));
        
        //CRIAR O WEBSERVICE AQUI!
        
        /*
        post("/produto/insert", (request, response) -> produtoService.insert(request, response));

        get("/produto/:id", (request, response) -> produtoService.get(request, response));
        
        get("/produto/list/:orderby", (request, response) -> produtoService.getAll(request, response));

        get("/produto/update/:id", (request, response) -> produtoService.getToUpdate(request, response));
        
        post("/produto/update/:id", (request, response) -> produtoService.update(request, response));
           
        get("/produto/delete/:id", (request, response) -> produtoService.delete(request, response));
         */
             
    }
}