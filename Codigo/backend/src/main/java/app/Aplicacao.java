package app;

import static spark.Spark.*;

import service.FornecedorService;
import service.*;

public class Aplicacao {

	private static ProdutoService produtoService = new ProdutoService();
	private static PerfilService perfilService = new PerfilService();
	private static MainService mainService = new MainService();
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
			get("/main/logout/:token", (request, response) -> mainService.logout(request, response));
			/**
			 * REQUISIÇÕES DOS SERVIÇOS DO ESTOQUE
			 */
			get("/estoque/deletar/:id", (request, response) ->estoqueService.delete(request, response));
			post("/estoque/cadastro", (request, response) -> estoqueService.cadastro(request, response));
			get("/estoque/carregar/:id", (request, response) ->estoqueService.carregar(request, response));
			post("/estoque/atualizar", (request, response) -> estoqueService.atualizar(request, response));

			
			/**
			 * REQUISIÇÕES DOS SERVIÇOS DE PRODUTO
			 */
			get("/produto/listar/:id", (request, response) -> produtoService.listar(request, response));
			get("/produto/deletar/:idestoque/:idproduto", (request, response) -> produtoService.deletar(request, response));
			post("/produto/cadastro", (request, response) -> produtoService.cadastro(request, response));
			post("/produto/atualizar", (request, response) -> produtoService.atualizar(request, response));
			get("/produto/carregar/:id", (request, response) -> produtoService.carregar(request, response));
			post("/produto/retirada/:token", (request, response) -> produtoService.retirada(request, response));
			post("/produto/entrada/:token", (request, response) -> produtoService.entrada(request, response));
			get("/produto/loadlist/:id/:token", (request, response) -> produtoService.loadOptions(request, response));
			get("/produto/getQtd/:idproduto", (request, response) -> produtoService.getQtd(request, response));
			get("/produto/historico/:id", (request, response) -> produtoService.getHistorico(request, response));
			/**
			 * REQUISIÇÕES DOS SERVI�OS DA HOME e PERFIL
			 */
			get("/perfil/carregar/:token", (request, response) -> perfilService.carregar(request, response));
			get("/perfil/delete/:token", (request, response) -> perfilService.deletar(request, response));
			post("/perfil/newsenha/:token", (request, response) -> perfilService.newPass(request, response));
			post("/perfil/atualizar/:token", (request, response) -> perfilService.atualizar(request, response));
			/**
			 * REQUISIÇÕES PARA OS FORNECEDORES
			 */
			get("/fornecedor/listar", (request, response) -> fornecedorService.listar(request, response));

		} else {
			System.out.println("O servidor não pode iniciar e por isso sua execução foi abortada\n"
					+ "Falha ao iniciar o sistema de segurança dos tokens!");
			System.exit(0);
		}

	}

	/**
	 * Sistema de gerenciamento de seguran�a de tokens de usu�rios do servi�o como
	 * clientes, esse met�do inicia um sistema que limpa tokens antigos e inicia um
	 * novo contador para a cada 24 horas limpar novamente!
	 * 
	 * @return status de execu��o do servi�o --> apenas iniciar o server se rodar
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
	 * Fun��o que acessa o banco de dados e limpa a tabela por inteira
	 * 
	 * @return status de limpeza
	 * @throws Exception Caso ocorra erro ele lan�ara para o invocador
	 */
	private static boolean clearTokens() throws Exception {
		boolean status = false;
		dao.DAOToken daot = new dao.DAOToken();
		status = daot.dropAll();
		return status;
	}

	/**
	 * Thread respons�vel por a cada 24 horas chamar a função de limpar a tabela de
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