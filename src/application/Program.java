package application;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import model.dao.AtendenteDao;
import model.dao.CategoriaDao;
import model.dao.DaoFactory;
import model.dao.Ing_ProdDao;
import model.dao.IngredienteDao;
import model.dao.Item_PedidoDao;
import model.dao.PagamentoDao;
import model.dao.PedidoDao;
import model.dao.Pedido_ItemDao;
import model.dao.ProdutoDao;
import model.dao.PromocaoDao;
import model.entities.Atendente;
import model.entities.Categoria;
import model.entities.Ing_Prod;
import model.entities.Ingrediente;
import model.entities.Item_Pedido;
import model.entities.Pagamento;
import model.entities.Pedido;
import model.entities.Pedido_Item;
import model.entities.Produto;
import model.entities.Promocao;

public class Program {
	static Scanner sc = new Scanner(System.in);
	static String timeStamp = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(Calendar.getInstance().getTime());
	static AtendenteDao atendenteDao = DaoFactory.createAtendenteDao();
	static CategoriaDao categoriaDao = DaoFactory.createCategoriaDao();
	static Ing_ProdDao ing_prodDao = DaoFactory.createIng_ProdDao();
	static IngredienteDao ingredienteDao = DaoFactory.createIngredienteDao();
	static Item_PedidoDao item_pedidoDao = DaoFactory.createItem_PedidoDao();
	static PagamentoDao pagamentoDao = DaoFactory.createPagamentoDao();
	static PedidoDao pedidoDao = DaoFactory.createPedidoDao();
	static Pedido_ItemDao pedido_itemDao = DaoFactory.createPedido_ItemDao();
	static ProdutoDao produtoDao = DaoFactory.createProdutoDao();
	static PromocaoDao promocaoDao = DaoFactory.createPromocaoDao();
	
	public int cadastrarVenda(String nomeCliente) {
		Pedido pedido = new Pedido();
		pedido.setAtendente(atendenteDao.findById(1));
		pedido.setDescricao(nomeCliente);
		pedido.setHorarioPedido(timeStamp);
		pedido.setStatusPedido("Na fila");
		int id_pedido = pedidoDao.insert(pedido);
		int con = 1;
		while(con == 1) {
			System.out.println("1- adicionar produtos");
			System.out.println("2- adicionar ingredientes");
			int pedidomenu = sc.nextInt();sc.nextLine();
			int id_item_pedido = 0;
			if(pedidomenu == 1) {
				System.out.println(produtoDao.findAll());
				int continuar = 1;
				while(continuar==1) {
					System.out.println("escolha um produto:");
					int escolhaProduto = sc.nextInt();sc.nextLine();
					System.out.println("digite a quantidade:");
					int quantidadeProduto = sc.nextInt();sc.nextLine();
					Item_Pedido item_pedido = new Item_Pedido();
					item_pedido.setQtdeProdutos(quantidadeProduto);
					item_pedido.setProduto(produtoDao.findById(escolhaProduto));
					item_pedido.setIngrediente(ingredienteDao.findById(1));
					item_pedido.setQtdeIngredientes(0);
					item_pedido.setStatus("ativo");
					id_item_pedido = item_pedidoDao.insert(item_pedido);
					pedido_itemDao.insert(pedidoDao.findById(id_pedido), item_pedidoDao.findByProduto(id_item_pedido));
					produtoDao.atualizaQuantidade(item_pedidoDao.findByProduto(id_item_pedido).getProduto(),quantidadeProduto);
					System.out.println("adicionar mais algum produto?");
					System.out.println("1-Sim // 2- Não");
					continuar=sc.nextInt();sc.nextLine();
				}
			}
			if(pedidomenu == 2) {
				System.out.println(ingredienteDao.findAll());
				int continuar = 1;
				while(continuar==1) {
					System.out.println("escolha um ingrediente:");
					int escolhaIngrediente = sc.nextInt();sc.nextLine();
					System.out.println("digite a quantidade:");
					int quantidadeIngrediente = sc.nextInt();sc.nextLine();
					Item_Pedido item_pedido = new Item_Pedido();
					item_pedido.setQtdeProdutos(0);
					item_pedido.setProduto(produtoDao.findById(1));
					item_pedido.setIngrediente(ingredienteDao.findById(escolhaIngrediente));
					item_pedido.setQtdeIngredientes(quantidadeIngrediente);
					item_pedido.setStatus("ativo");
					id_item_pedido = item_pedidoDao.insert(item_pedido);
					pedido_itemDao.insert(pedidoDao.findById(id_pedido), item_pedidoDao.findByIngrediente(id_item_pedido));
					ingredienteDao.atualizaQuantidade(item_pedidoDao.findByIngrediente(id_item_pedido).getIngrediente(), quantidadeIngrediente);
					System.out.println("adicionar mais algum ingrediente?");
					System.out.println("1-Sim // 2- Não");
					continuar=sc.nextInt();sc.nextLine();
				}
			}
			System.out.println("Deseja inserir mais algum produto/ingrediente?");
			System.out.println("1- Sim // 2- Nao");
			con = sc.nextInt(); sc.nextLine();
		}
		System.out.println("Deseja remover algum produto/ingrediente?");
		System.out.println("1- Sim // 2- Nao");
		int opRemocao = sc.nextInt();sc.nextLine();
		if(opRemocao == 1) {
			int remover = 1;
			while(remover == 1) {
				System.out.println(pedido_itemDao.carrinho(id_pedido));
				System.out.println("Código que deseja remover");
				int codRemover = sc.nextInt();sc.nextLine();
				Item_Pedido remove = item_pedidoDao.findById(codRemover);
				if(remove.getQtdeProdutos()>0) {
					pedido_itemDao.deleteById(codRemover);
					produtoDao.atualizaQuantidade(remove.getProduto(), (remove.getQtdeProdutos()*(-1)));
					item_pedidoDao.removerCarrinho(codRemover);
				}else if(remove.getQtdeIngredientes()>0) {
					pedido_itemDao.deleteById(codRemover);
					ingredienteDao.atualizaQuantidade(remove.getIngrediente(), (remove.getQtdeIngredientes()*(-1)));
					item_pedidoDao.removerCarrinho(codRemover);
				}
				System.out.println("Continuar a remover?");
				System.out.println("1- Sim // 2- Nao");
				remover = sc.nextInt();sc.nextLine();
			}
		}
		return id_pedido;
	}
	
	public int pagamentoCartao(int id_pedido, String num) {
		if(num.length() == 16) {
			Pagamento pag = new Pagamento();
			pag.setTipoDePag("Cartao de credito/debito");
			pag.setStatus("Pagamento confirmado");
			pag.setPedido(pedidoDao.findById(id_pedido));
			int codigoPagamento = pagamentoDao.insert(pag);
			return codigoPagamento;
		}else {
			return 0;
		}
	}
	
	public int pagamentoQRCode(int id_pedido, String num) {
		System.out.println("Informe o código de 4 digitos:");
		num = sc.nextLine();
		if(num.length() == 4) {
			Pagamento pag = new Pagamento();
			pag.setTipoDePag("QR Code");
			pag.setStatus("Pagamento confirmado");
			pag.setPedido(pedidoDao.findById(id_pedido));
			int codigoPagamento = pagamentoDao.insert(pag);
			return codigoPagamento;
		}else {
			return 0;
		}
	}
	
	public boolean verificaPedido(int pedido) {
		if(pedidoDao.findPrecoById(pedido) > 0) {
			return true;
		}
		return false;
	}
	
	public void dadosPedido(int pedido) {
		Pedido ped = new Pedido();
		ped = pedidoDao.findById(pedido);
		System.out.println("Realizar pagamento");
		System.out.println("Numero do pedido: " + ped.getPedido_id());
		System.out.println("Cliente: " + ped.getDescricao());
		System.out.println("Horario do pedido: " + ped.getHorarioPedido());
		System.out.println("Valor a ser pago R$:" + pedidoDao.findPrecoById(pedido));
	}
	
	public int verificaFuncionario(String login, String senha) {
		Atendente atendende = new Atendente();
		atendende = atendenteDao.findLogin(login, senha);
		if(atendende==null) {
		}else if(atendende.getLogin().equals("admin") && atendende.getSenha().equals("admin")) {
			return 2;
		}else if(atendende!=null) {
			return 1;
		}
		return 0;
	}
	
	public String nomeFuncionario(String login, String senha) {
		Atendente atendende = new Atendente();
		atendende = atendenteDao.findLogin(login, senha);
		return atendende.getNome();
	}
	
	public int idFuncionario(String login, String senha) {
		Atendente atendende = new Atendente();
		atendende = atendenteDao.findLogin(login, senha);
		return atendende.getAtendente_id();
	}
	
	public void vincularPedido(int numPedido, int funcionario) {
		Atendente atendente = new Atendente();
		atendente = atendenteDao.findById(funcionario);
		Pedido pedido = pedidoDao.findById(numPedido);
		if(pedidoDao.findById(numPedido).getAtendente().getAtendente_id() == 1) {
			pedido.setAtendente(atendente);
			pedidoDao.updateAtendente(pedido);
			System.out.println("Atendente vinculado com sucesso");
		}else if(pedidoDao.findById(numPedido).getAtendente().getAtendente_id() == funcionario) {
			System.out.println("Esse pedido ja estava vinculado a voce");
		}else {
			System.out.println("Esse pedido já possui um atendente cadastrado");
		}
	}
	
	public void alterarStatusPedido(int numPedido, int op) {
		Pedido pedido = new Pedido();
		pedido = pedidoDao.findById(numPedido);
		if(!pedido.getStatusPedido().equals("Na fila") && op==1) {
			pedido.setStatusPedido("Em andamento");
			pedidoDao.updateStatus(pedido);
		}else if(pedido.getStatusPedido().equals("Em andamento") && op==2) {
			pedido.setStatusPedido("Finalizado");
			pedidoDao.updateStatus(pedido);
		}else {
			System.out.println("Nao foi possivel alterar o status");
		}
	}
	
	public void cancelarPedidoAtendente() {
		
	}
	
	public boolean verificarAdmin(String login, String senha) {
		if(login.equals("admin")) {
			if(senha.equals("admin")) {
				return true;
			}
		}
		return false;
	}
	
	public void cadastrarAtendente(String nome, String status, String turno, String login, String senha) {
		Atendente atendente = new Atendente();
		atendente.setNome(nome);
		atendente.setStatus(status);
		atendente.setTurno(turno);
		atendente.setSenha(senha);
		atendente.setLogin(login);
		boolean sucesso = atendenteDao.insert(atendente);
		if(sucesso) {
			System.out.println("Atendente cadastrado com sucesso");
		}else {
			System.out.println("Atendente não foi cadastrado");
		}
	}
	
	public void cadastrarCategoria(String nome, String descricao) {
		Categoria categoria = new Categoria();
		categoria.setNome(nome);
		categoria.setDescricao(descricao);
		boolean sucesso = categoriaDao.insert(categoria);
		if(sucesso) {
			System.out.println("Categoria cadastrada com sucesso");
		}else {
			System.out.println("Categoria não foi cadastrado");
		}
	}
	
	public void cadastrarIngrediente(String nome, String unidade, String validade, double valor, int quantidade) throws ParseException {
		Ingrediente ingrediente = new Ingrediente();
		ingrediente.setNome(nome);
		ingrediente.setValidade(validade);
		ingrediente.setUnidadeMedida(unidade);
		ingrediente.setQtdeEstoque(quantidade);
		ingrediente.setValorPorcao(valor);
		boolean sucesso = ingredienteDao.insert(ingrediente);
		if(sucesso) {
			System.out.println("Ingrediente cadastrado com sucesso");
		}else {
			System.out.println("Ingrediente não foi cadastrado");
		}
	}
	
	public void cadastrarProduto(String nome_produto, double valor_produto, String descricao_produto, int quantidade_produto, String fabricacao_produto, int categoria_produto) {
		Produto produto = new Produto();
		produto.setCategoria(categoriaDao.findById(categoria_produto));
		produto.setDataProducao(fabricacao_produto);
		produto.setDescricao(descricao_produto);
		produto.setNome(nome_produto);
		produto.setQtdEstoque(quantidade_produto);
		produto.setValor(valor_produto);
		boolean sucesso = produtoDao.insert(produto);
		if(sucesso) {
			System.out.println("Produto cadastrado com sucesso");
		}else {
			System.out.println("Produto não foi cadastrado");
		}
	}
	
	public void cadastrarPromocao(String tipo_promocao, String validade_promocao, double valor_promocao, String descricao_promocao, int produto_promocao) {
		Promocao promocao = new Promocao();
		promocao.setDescricao(descricao_promocao);
		promocao.setDuracao(validade_promocao);
		promocao.setPreco(valor_promocao);
		promocao.setTipo(tipo_promocao);
		promocao.setProduto(produtoDao.findById(produto_promocao));
		boolean sucesso = promocaoDao.insert(promocao);
		if(sucesso) {
			System.out.println("Promocao cadastrado com sucesso");
		}else {
			System.out.println("Promocao não foi cadastrado");
		}
	}
	
	public void vincularProdutoIngrediente(int produto, int ingrediente, int quantidade) {
		Ing_Prod ing_prod = new Ing_Prod();
		ing_prod.setProduto(produtoDao.findById(produto));
		ing_prod.setIngrediente(ingredienteDao.findById(ingrediente));
		ing_prod.setQtdeIng(quantidade);
		boolean sucesso = ing_prodDao.insert(ing_prod);
		if(sucesso) {
			System.out.println("Ingrediente vinculado com sucesso");
		}else {
			System.out.println("Ingrediente não foi vinculado");
		}
	}
	
	public void atualizarAtendente(int op_ate, int op) {
		Atendente atend = new Atendente();
		atend = atendenteDao.findById(op_ate);
		if(op==1) {
			System.out.println("Informe o novo nome: ");
			String novo_nome = sc.nextLine();
			atend.setNome(novo_nome);
			atendenteDao.updateNome(atend);
		}else if(op==2) {
			System.out.println("Informe o novo status: ");
			String novo_status = sc.nextLine();
			atend.setStatus(novo_status);
			atendenteDao.updateStatus(atend);
		}
		else if(op==3) {
			System.out.println("Informe o novo turno: ");
			String novo_turno = sc.nextLine();
			atend.setTurno(novo_turno);
			atendenteDao.updateTurno(atend);
		}
		else if(op==4) {
			System.out.println("Informe o novo login: ");
			String novo_login = sc.nextLine();
			atend.setLogin(novo_login);
			atendenteDao.updateLogin(atend);
		}
		else if(op==5) {
			System.out.println("Informe o novo senha: ");
			String novo_senha = sc.nextLine();
			atend.setSenha(novo_senha);
			atendenteDao.updateSenha(atend);
		}
	}
	
	public void atualizarCategoria(int op_id, int op) {
		Categoria cat = new Categoria();
		cat = categoriaDao.findById(op_id);
		if(op==1) {
			System.out.println("Informe o novo nome: ");
			String novo_nome = sc.nextLine();
			cat.setNome(novo_nome);
			categoriaDao.updateNome(cat);
		}else if(op==2) {
			System.out.println("Informe a nova descricao: ");
			String novo_descricao = sc.nextLine();
			cat.setDescricao(novo_descricao);
			categoriaDao.updateDescricao(cat);
		}
	}
	
	public void atualizarIngrediente(int op_id, int op) {
		Ingrediente ing = new Ingrediente();
		ing = ingredienteDao.findById(op_id);
		if(op==1) {
			System.out.println("Informe o novo nome: ");
			String novo_nome = sc.nextLine();
			ing.setNome(novo_nome);
			ingredienteDao.updateNome(ing);
		}else if(op==2) {
			System.out.println("Informe a nova unidade de medida: ");
			String novo_unidade = sc.nextLine();
			ing.setUnidadeMedida(novo_unidade);
			ingredienteDao.updateUnidade(ing);
		}else if(op==3) {
			System.out.println("Informe a nova validade: ");
			String novo_validade = sc.nextLine();
			ing.setValidade(novo_validade);
			ingredienteDao.updateValidade(ing);
		}else if(op==4) {
			System.out.println("Informe o novo valor da porcao: ");
			double novo_valor = sc.nextDouble(); sc.nextLine();
			ing.setValorPorcao(novo_valor);
			ingredienteDao.updateValor(ing);
		}else if(op==5) {
			System.out.println("Deseja:\n1- Aumentar a quantidade atual // 2- Substituir a quantidade atual");
			int qtd_op = sc.nextInt(); sc.nextLine(); 
			int novo_quantidade = 0;
			if(qtd_op==1) {
				System.out.println("Informe a quantidade a ser adicionada: ");
				novo_quantidade = sc.nextInt(); sc.nextLine();
			}else if(qtd_op==2) {
				System.out.println("Informe a nova quantidade em estoque: ");
				novo_quantidade = sc.nextInt(); sc.nextLine();
				ing.setQtdeEstoque(novo_quantidade);
			}
			ingredienteDao.updateQuantidade(ing, qtd_op, novo_quantidade);
		}
	}
	
	public void atualizarProduto(int op_id, int op) {
		Produto prod = new Produto();
		prod = produtoDao.findById(op_id);
		if(op==1) {
			System.out.println("Informe o novo nome: ");
			String novo_nome = sc.nextLine();
			prod.setNome(novo_nome);
			produtoDao.updateNome(prod);
		}else if(op==2) {
			System.out.println("Informe o novo valor: ");
			double novo_valor = sc.nextDouble(); sc.nextLine();
			prod.setValor(novo_valor);
			produtoDao.updateValor(prod);
		}else if(op==3) {
			System.out.println("Informe a nova descricao: ");
			String novo_descricao = sc.nextLine();
			prod.setDescricao(novo_descricao);
			produtoDao.updateDescricao(prod);
		}else if(op==4) {
			System.out.println("Deseja:\n1- Aumentar a quantidade atual // 2- Substituir a quantidade atual");
			int qtd_op = sc.nextInt(); sc.nextLine(); 
			int novo_quantidade = 0;
			if(qtd_op==1) {
				System.out.println("Informe a quantidade a ser adicionada: ");
				novo_quantidade = sc.nextInt(); sc.nextLine();
			}else if(qtd_op==2) {
				System.out.println("Informe a nova quantidade em estoque: ");
				novo_quantidade = sc.nextInt(); sc.nextLine();
				prod.setQtdEstoque(novo_quantidade);
			}
			produtoDao.updateQuantidade(prod, qtd_op, novo_quantidade);
		}else if(op==5) {
			System.out.println("Informe a nova data de fabricacao: ");
			String novo_validade = sc.nextLine();
			prod.setDataProducao(novo_validade);
			produtoDao.updateFabricacao(prod);
		}else if(op==6) {
			listar("categoria");
			System.out.println("Informe a nova categoria: ");
			int categoria_op = sc.nextInt();sc.nextLine();
			prod.setCategoria(categoriaDao.findById(categoria_op));
			produtoDao.updateCategoria(prod);
		}
	}
	
	public void atualizarPromocao(int op_id, int op) {
		Promocao promo = new Promocao();
		promo = promocaoDao.findById(op_id);
		if(op==1) {
			System.out.println("Informe o novo tipo: ");
			String novo_tipo = sc.nextLine();
			promo.setTipo(novo_tipo);
			promocaoDao.updateTipo(promo);
		}else if(op==2) {
			System.out.println("Informe a nova validade: ");
			String novo_validade = sc.nextLine();
			promo.setDuracao(novo_validade);
			promocaoDao.updateValidade(promo);
		}
		else if(op==3) {
			System.out.println("Informe o novo preco: ");
			double novo_preco = sc.nextDouble(); sc.nextLine();
			promo.setPreco(novo_preco);
			promocaoDao.updatePreco(promo);
		}
		else if(op==4) {
			System.out.println("Informe a nova descricao: ");
			String novo_descricao = sc.nextLine();
			promo.setDescricao(novo_descricao);
			promocaoDao.updateDescricao(promo);
		}
		else if(op==5) {
			listar("produto");
			System.out.println("Informe o novo produto: ");
			int novo_produto = sc.nextInt(); sc.nextLine();
			promo.setProduto(produtoDao.findById(novo_produto));
			promocaoDao.updateProduto(promo);
		}
	}
	
	public void atualizarProdutoIngrediente(int op_id, int op) {
		Ing_Prod ip = new Ing_Prod();
		ip = ing_prodDao.findById(op_id);
		if(op==1) {
			listar("produto");
			System.out.println("Informe o novo produto: ");
			int novo_produto = sc.nextInt(); sc.nextLine();
			ip.setProduto(produtoDao.findById(novo_produto));
			ing_prodDao.updateProduto(ip);
		}else if(op==2) {
			listar("ingrediente");
			System.out.println("Informe o novo ingrediente: ");
			int novo_ingrediente = sc.nextInt(); sc.nextLine();
			ip.setIngrediente(ingredienteDao.findById(novo_ingrediente));
			ing_prodDao.updateIngrediente(ip);
		}else if(op==3){
			System.out.println("Informe a nova quantidade: ");
			int novo_quantidade = sc.nextInt(); sc.nextLine();
			ip.setQtdeIng(novo_quantidade);
			ing_prodDao.updateQuantidade(ip);
		}
	}
	
	public void listar(String escolha) {
		switch(escolha) {
			case "categoria":
				System.out.println(categoriaDao.findAll());
				break;
			case "atendente":
				System.out.println(atendenteDao.findAll());
				break;
			case "ing_prod":
				System.out.println(ing_prodDao.findAll());
				break;
			case "ingrediente":
				System.out.println(ingredienteDao.findAll());
				break;
			case "item_pedido":
				System.out.println(item_pedidoDao.findAll());
				break;
			case "pagamento":
				System.out.println(pagamentoDao.findAll());
				break;
			case "pedido":
				System.out.println(pedidoDao.findAll());
				break;
			case "pedido_item":
				System.out.println(pedido_itemDao.findAll());
				break;
			case "produto":
				System.out.println(produtoDao.findAll());
				break;
			case "promocao":
				System.out.println(promocaoDao.findAll());
				break;
			default:
				break;
		}
	}

	public void cancelarPedido(int numeroCancelamento, String nomeCancelamento) {
		Pedido pedido = new Pedido();
		pedido = pedidoDao.findById(numeroCancelamento);
		if(pedido.getDescricao().equals(nomeCancelamento)) {
			pedido.setStatusPedido("Cancelamento pendente");
			pedidoDao.updateStatus(pedido);
		}else {
			System.out.println("Pedido e Nome não conferem");
		}
	}
	
	public void aprovarCancelamento(int numeroPedido) {
		Pedido pedido = new Pedido();
		pedido = pedidoDao.findById(numeroPedido);
		System.out.println("ID do Pedido: " + numeroPedido);
		System.out.println("Hora do pedido: " + pedido.getHorarioPedido());
		System.out.println(pedido_itemDao.itensCancelamento(numeroPedido));
		System.out.println("Confirmar cancelamento:\n1- Sim // 2- Nao");
		int resp = sc.nextInt(); sc.nextLine();
		if(resp == 1) {
			Atendente atend = new Atendente();
			System.out.println("Insira o login do gerente");
			String loginGerente = sc.nextLine();
			System.out.println("Insira a senha do gerente");
			String senhaGerente = sc.nextLine();
			atend = atendenteDao.findLogin(loginGerente, senhaGerente);
			if(atend.getLogin().equals("admin") && atend.getSenha().equals("admin")) {
				pedido.setStatusPedido("Cancelado");
				pedidoDao.updateStatus(pedido);
				item_pedidoDao.cancelarItens(numeroPedido);
			}
		}else if(resp == 2){
			System.out.println("Gerente não foi encontrado");
		}
	}
	
	public void cancelarPedidoAdmin(int numeroPedido) {
		Pedido pedido = new Pedido();
		pedido = pedidoDao.findById(numeroPedido);
		System.out.println("ID do Pedido: " + numeroPedido);
		System.out.println("Hora do pedido: " + pedido.getHorarioPedido());
		System.out.println(pedido_itemDao.itensCancelamento(numeroPedido));
		System.out.println("Confirmar cancelamento:\n1- Sim // 2- Nao");
		int resp = sc.nextInt(); sc.nextLine();
		pedido.setStatusPedido("Cancelado");
		pedidoDao.updateStatus(pedido);
		item_pedidoDao.cancelarItens(numeroPedido);
	}
}
