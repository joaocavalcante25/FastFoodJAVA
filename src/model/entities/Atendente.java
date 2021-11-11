package model.entities;

import java.text.ParseException;
import java.util.Scanner;

import application.Program;

public class Atendente {
	static Scanner sc = new Scanner(System.in);
	static Program program = new Program();
	
	private Integer atendente_id;
	private String turno;
	private String status;
	private String login;
	private String senha;
	private String nome;
	
	public String getNome() {
		return nome;
	}
	
	public Atendente(Integer atendente_id, String turno, String status, String login, String senha, String nome) {
		this.atendente_id = atendente_id;
		this.turno = turno;
		this.status = status;
		this.login = login;
		this.senha = senha;
		this.nome = nome;
	}
	
	public boolean listaAtendente (int funcionario) {
		System.out.println("Selecione uma opcao: ");
		System.out.println("1- Vincular-se a um pedido");
		System.out.println("2- Alterar status do pedido");
		System.out.println("3- Autorizar cancelamento");
		int escolha = sc.nextInt(); sc.nextLine();
		int numPedido = 0;
		if(escolha == 1) {
			System.out.println("Vinculando a um pedido");
			program.listar("pedido");
			System.out.println("Escolha o nº do pedido:");
			numPedido = sc.nextInt(); sc.nextLine();
			program.vincularPedido(numPedido, funcionario);
			return true;
		}else if(escolha == 2) {
			System.out.println("Alterando o status");
			program.listar("pedido");
			System.out.println("Informe o nº do pedido");
			numPedido = sc.nextInt(); sc.nextLine();
			System.out.println("Deseja alterar para qual estado?\n1- Em andamento // 2- Finalizado");
			int opStatus = sc.nextInt();sc.nextLine();
			program.alterarStatusPedido(numPedido, opStatus);
			return true;
		}else if(escolha == 3) {
			System.out.println("Informe o nº do pedido: ");
			numPedido = sc.nextInt(); sc.nextLine();
			program.aprovarCancelamento(numPedido);
			return true;
		}
		else {
			System.out.println("Saindo...");
			return false;
		}
	}
	
	public boolean listaAdministrador() throws ParseException {
		System.out.println("1- Cadastrar um atendente");
		System.out.println("2- Cadastrar uma categoria");
		System.out.println("3- Cadastrar um ingrediente");
		System.out.println("4- Cadastrar um produto");
		System.out.println("5- Cadastrar uma promocao");
		System.out.println("6- Vincular produto <-> ingrediente");
		System.out.println("7- Atualizar um atendente");
		System.out.println("8- Atualizar uma categoria");
		System.out.println("9- Atualizar um ingrediente");
		System.out.println("10- Atualizar um produto");
		System.out.println("11- Atualizar uma promocao");
		System.out.println("12- Atualzar produto <-> ingrediente");
		System.out.println("13- Cancelar pedido");
		System.out.println("14- Sair");
		int escolha = sc.nextInt(); sc.nextLine();
		int op, op_id = 0;
		switch(escolha) {
		case 1:
			System.out.println("Cadastrando um atendente");
			System.out.println("Informe o nome: ");
			String nome_atendente = sc.nextLine();
			System.out.println("Informe o status: ");
			String status_atendente = sc.nextLine();
			System.out.println("Informe o turno: ");
			String turno_atendente = sc.nextLine();
			System.out.println("Informe o login: ");
			String login_atendente = sc.nextLine();
			System.out.println("Informe a senha: ");
			String senha_atendente = sc.nextLine();
			program.cadastrarAtendente(nome_atendente, status_atendente, turno_atendente, login_atendente, senha_atendente);
			return true;
		case 2:
			System.out.println("Cadastrando uma categoria");
			System.out.println("Informe o nome: ");
			String nome_categoria = sc.nextLine();
			System.out.println("Informe a descricao: ");
			String descricao_categoria = sc.nextLine();
			program.cadastrarCategoria(nome_categoria, descricao_categoria);
			return true;
		case 3:
			System.out.println("Cadastrando um ingrediente");
			System.out.println("Informe o nome: ");
			String nome_ingrediente = sc.nextLine();
			System.out.println("Informe a unidade de medida: ");
			String unidade_ingrediente = sc.nextLine();
			System.out.println("Informe a validade: ");
			String validade_ingrediente = sc.nextLine();
			System.out.println("Informe o valor unitario: ");
			double valor_ingrediente = sc.nextDouble(); sc.nextLine();
			System.out.println("Informe a quantidade: ");
			int quantidade_ingrediente = sc.nextInt(); sc.nextLine();
			program.cadastrarIngrediente(nome_ingrediente, unidade_ingrediente, validade_ingrediente, valor_ingrediente, quantidade_ingrediente);
			return true;
		case 4:
			System.out.println("Cadastrando um produto");
			System.out.println("Informe o nome");
			String nome_produto = sc.nextLine();
			System.out.println("Informe o valor");
			double valor_produto = sc.nextDouble(); sc.nextLine();
			System.out.println("Informe o descricao");
			String descricao_produto = sc.nextLine();
			System.out.println("Informe o quantidade");
			int quantidade_produto = sc.nextInt(); sc.nextLine();
			System.out.println("Informe o data de fabricacao");
			String fabricacao_produto = sc.nextLine();
			program.listar("categoria");
			System.out.println("Informe o ID da categoria");
			int categoria_produto = sc.nextInt(); sc.nextLine();
			program.cadastrarProduto(nome_produto, valor_produto, descricao_produto, quantidade_produto, fabricacao_produto, categoria_produto);
			return true;
		case 5:
			System.out.println("Cadastrando uma promocao");
			System.out.println("Informe o tipo: ");
			String tipo_promocao = sc.nextLine();
			System.out.println("Informe a validade: ");
			String validade_promocao = sc.nextLine();
			System.out.println("Informe o preco: ");
			double valor_promocao = sc.nextDouble(); sc.nextLine();
			System.out.println("Informe a descricao: ");
			String descricao_promocao = sc.nextLine();
			program.listar("produto");
			System.out.println("Informe o ID do produto: ");
			int produto_promocao = sc.nextInt(); sc.nextLine();
			program.cadastrarPromocao(tipo_promocao, validade_promocao, valor_promocao, descricao_promocao, produto_promocao);
			return true;
		case 6:
			System.out.println("Vinculando produto com ingrediente");
			program.listar("produto");
			System.out.println("Selecione um produto: ");
			int produto_vinculo = sc.nextInt(); sc.nextLine();
			program.listar("ingrediente");
			System.out.println("Selecione um ingrediente: ");
			int ingrediente_vinculo = sc.nextInt(); sc.nextLine();
			System.out.println("Informe a quantidade do ingrediente: ");
			int quantidade_ingProd = sc.nextInt(); sc.nextLine();
			program.vincularProdutoIngrediente(produto_vinculo, ingrediente_vinculo, quantidade_ingProd);
			return true;
		case 7:
			System.out.println("Atualizando o atendente");
			program.listar("atendente");
			System.out.println("Selecione um atendente: ");
			op_id = sc.nextInt(); sc.nextLine();
			System.out.println("Atualizar:\n1-Nome // 2- Status // 3- Turno // 4- Login // 5- Senha");
			op = sc.nextInt(); sc.nextLine();
			program.atualizarAtendente(op_id, op);
			return true;
		case 8:
			System.out.println("Atualizando a categoria");
			program.listar("categoria");
			System.out.println("Selecione uma categoria: ");
			op_id = sc.nextInt(); sc.nextLine();
			System.out.println("Atualizar:\n1- Nome // 2- Descricao");
			op = sc.nextInt(); sc.nextLine();
			program.atualizarCategoria(op_id, op);
			return true;
		case 9:
			System.out.println("Atualizando o ingrediente");
			program.listar("ingrediente");
			System.out.println("Selecione um ingrediente: ");
			op_id = sc.nextInt(); sc.nextLine();
			System.out.println("Atualizar:\n1- Nome // 2- Unidade de medida // 3- Validade // 4- Valor da porcao // 5- Quantidade em estoque");
			op = sc.nextInt(); sc.nextLine();
			program.atualizarIngrediente(op_id, op);
			return true;
		case 10:
			System.out.println("Atualizando o produto");
			program.listar("produto");
			System.out.println("Selecione um produto: ");
			op_id = sc.nextInt(); sc.nextLine();
			System.out.println("Atualizar:\n1-Nome // 2- Valor // 3- Descricao // 4- Quantidade em estoque // 5- Data de fabricacao // 6- Categoria");
			op = sc.nextInt(); sc.nextLine();
			program.atualizarProduto(op_id, op);
			return true;
		case 11:
			System.out.println("Atualizando a promocao");
			program.listar("promocao");
			System.out.println("Selecione uma promocao: ");
			op_id = sc.nextInt(); sc.nextLine();
			System.out.println("Atualizar:\n1- Tipo // 2- Validade // 3- Valor // 4- Descricao // 5- Produto");
			op = sc.nextInt(); sc.nextLine();
			program.atualizarPromocao(op_id, op);
			return true;
		case 12:
			System.out.println("Atualizando produto <-> ingrediente");
			program.listar("ing_prod");
			System.out.println("Selecione o que ira ser alterado: ");
			op_id = sc.nextInt(); sc.nextLine();
			System.out.println("Atualizar:\n1- Produto // 2-Ingrediente // 3- Quantidade de ingrediente");
			op = sc.nextInt(); sc.nextLine();
			program.atualizarProdutoIngrediente(op_id, op);
			return true;
		case 13:
			System.out.println("Cancelar pedido");
			System.out.println("Informe o nº do pedido: ");
			int numPedido = sc.nextInt(); sc.nextLine();
			program.aprovarCancelamento(numPedido);
			return true;
		case 14:
			return false;
		default:
			return false;
		}
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getAtendente_id() {
		return atendente_id;
	}

	public void setAtendente_id(int atendente_id) {
		this.atendente_id = atendente_id;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + atendente_id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Atendente other = (Atendente) obj;
		if (atendente_id != other.atendente_id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "\nID do atendente: " + atendente_id + ", Nome: " + nome + ", Turno: " + turno + ", Status:" + status;
	}

	public Atendente() {
		
	}
}
