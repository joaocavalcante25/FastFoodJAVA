package application;

import java.text.ParseException;
import java.util.Scanner;

import model.entities.Atendente;

public class Main {
	static Scanner sc = new Scanner(System.in);
	static Program program = new Program();
	public static void main(String args[]) throws ParseException {
		int pedido, mtd_pagamento, pagamento, menu = 0;
		String num;
		while(menu == 0) {
			System.out.println("Bem vindo!");
			System.out.println("Escolha uma opcao:\n1-Realizar uma compra // 2- Cancelar um pedido");
			String resposta = sc.nextLine();
			if(resposta.equals("1")) {
				System.out.println("Informe seu nome: ");
				String nomeCliente = sc.nextLine();
				pedido = program.cadastrarVenda(nomeCliente);
				boolean pedidoValido = program.verificaPedido(pedido);
				if(pedidoValido) {
					program.dadosPedido(pedido);
					System.out.println("\n");
					System.out.println("Escolha o metodo de pagamento:");
					System.out.println("1- Cartão de credito/debito // 2- QR Code");
					mtd_pagamento = sc.nextInt(); sc.nextLine();
					if(mtd_pagamento==1) {
						System.out.println("Insira o cartão");
						num = sc.nextLine();
						pagamento = program.pagamentoCartao(pedido, num);
						if(pagamento!=0) {
							System.out.println("Codigo do pagamento: " + pagamento);
							System.out.println("Pagamento efetuado com sucesso");
						}else{
							System.out.println("Cartão não aceito");
						}
					}else if(mtd_pagamento==2) {
						System.out.println("Insira o QR Code");
						num = sc.nextLine();
						pagamento = program.pagamentoCartao(pedido, num);
						if(pagamento!=0) {
							System.out.println("Codigo do pagamento: " + pagamento);
							System.out.println("Pagamento efetuado com sucesso");
						}else {
							System.out.println("QR Code não aceito");
						}
					}
				}else {
					System.out.println("Pedido sem itens adicionados");
				}
			}else if(resposta.equals("2")) {
				System.out.println("Informe o nº do seu pedido: ");
				int numeroCancelamento = sc.nextInt(); sc.nextLine();
				System.out.println("Informe o seu nome: ");
				String nomeCancelamento = sc.nextLine();
				program.cancelarPedido(numeroCancelamento, nomeCancelamento);
			}else if(resposta.equals("funcionario")) {
				System.out.println("=== Login === ");
				System.out.println("Insira seu login: ");
				String login = sc.nextLine();
				System.out.println("Insira sua senha: ");
				String senha = sc.nextLine();
				boolean processeguir = true;
				int funcionario = program.verificaFuncionario(login, senha);
				Atendente atendente = new Atendente();
				if(funcionario == 1) {
					System.out.println("Login efetuado com sucesso");
					System.out.println("Bem vindo de volta, " + program.nomeFuncionario(login, senha) + "!");
					while(processeguir == true) {
						processeguir = atendente.listaAtendente(program.idFuncionario(login, senha));
					}
				}else if(funcionario == 2) {
					System.out.println("Login efetuado com sucesso");
					System.out.println("Bem vindo de volta, " + program.nomeFuncionario(login, senha)+ "!");
					while(processeguir == true) {
						processeguir = atendente.listaAdministrador();
					}
				}else {
					System.out.println("Login não efetuado\n");
				}
			}
		}
	}
}
