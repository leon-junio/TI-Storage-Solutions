package app;

import static spark.Spark.*;

import service.FornecedorService;
import service.*;


public class Aplicacao {
	
	private static ProdutoService produtoService = new ProdutoService();
	private static HomeService homeService = new HomeService();
	private static ClienteService clienteService = new ClienteService();
	private static FornecedorService fornecedorService = new FornecedorService();
	private static EstoqueService estoqueService = new EstoqueService();
	
    public static void main(String[] args) {
        port(6789);
        
        staticFiles.location("/public");
        
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