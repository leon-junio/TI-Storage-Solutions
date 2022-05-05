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
	private static boolean running = false;
	public static final String url = "http://localhost:" + porta;

	public static void main(String[] args) {

		running = true;
		port(porta);
		staticFiles.location("/public");

		if (securityService()) {
			System.out.println("Servidor ONLINE :D");
			/**
			 * REQUISIÇÕES DOS SERVIÇOS DO MAIN
			 */
			get("/main/check/:token", (request, response) -> mainService.check(request, response));
			get("/main/listar/:token", (request, response) -> mainService.listar(request, response));
			post("/main/login", (request, response) -> mainService.login(request, response));
			post("/main/cadastro", (request, response) -> mainService.cadastro(request, response));

			// CRIAR O WEBSERVICE AQUI!

			/*
			 * post("/produto/insert", (request, response) -> produtoService.insert(request,
			 * response));
			 * 
			 * get("/produto/:id", (request, response) -> produtoService.get(request,
			 * response));
			 * 
			 * get("/produto/list/:orderby", (request, response) ->
			 * produtoService.getAll(request, response));
			 * 
			 * get("/produto/update/:id", (request, response) ->
			 * produtoService.getToUpdate(request, response));
			 * 
			 * post("/produto/update/:id", (request, response) ->
			 * produtoService.update(request, response));
			 * 
			 * get("/produto/delete/:id", (request, response) ->
			 * produtoService.delete(request, response));
			 */
		} else {
			System.out.println("O servidor não pode iniciar e por isso sua execução foi abortada\n"
					+ "Falha ao iniciar o sistema de segurança dos tokens!");
			System.exit(0);
		}

	}

	/**
	 * Sistema de gerenciamento de segurança de tokens de usuários do serviço como
	 * clientes, esse metódo inicia um sistema que limpa tokens antigos e inicia um
	 * novo contador para a cada 24 horas limpar novamente!
	 * 
	 * @return status de execução do serviço --> apenas iniciar o server se rodar
	 *         esse sistema
	 */
	private static boolean securityService() {
		boolean status = false;
		try {
			System.out.println("Sistema de gerenciamento de tokens inicializando!");
			System.out.println("Limpando tokens antigos");
			if (clearTokens() == false) {
				throw new RuntimeException("Erro ao tentar limpar tokens!");
			}
			System.out.println("Iniciando Thread de limpeza");
			tokensThread();
			status = true;
		} catch (RuntimeException re) {
			System.out.println(re.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return status;
	}

	/**
	 * Função que acessa o banco de dados e limpa a tabela por inteira
	 * 
	 * @return status de limpeza
	 * @throws Exception Caso ocorra erro ele lançara para o invocador
	 */
	private static boolean clearTokens() throws Exception {
		boolean status = false;
		dao.DAOToken daot = new dao.DAOToken();
		status = daot.dropAll();
		return status;
	}

	/**
	 * Thread responsável por a cada 24 horas chamar a função de limpar a tabela de
	 * tokens
	 * 
	 * @throws Exception Caso ocorra erro evite iniciar o servidor
	 */
	private static void tokensThread() throws Exception {
		try {
			new Thread() {
				@Override
				public void run() {
					while (running) {
						try {
							sleep(86400000);
							System.out.println("Limpando tokens!");
							clearTokens();
							System.out.println("Limpos com sucesso!");
						} catch (InterruptedException e) {
							System.err.println("Erro no timer!");
							e.printStackTrace();
						} catch (Exception e) {
							System.out.println("Erro ao tentar limpar tabela de tokens");
						}
					}
				}
			}.start();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("ERRO AO INICIAR SISTEMA DE GERENCIAMENTO DE TOKENS");
			throw new Exception(ex.getMessage());
		}
	}

}