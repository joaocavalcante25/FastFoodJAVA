package model.dao;

import java.util.List;

import model.entities.Produto;

public interface ProdutoDao {
	boolean insert(Produto obj);
	void atualizaQuantidade(Produto produto,Integer quantidade);
	Produto findById(Integer id);
	List<Produto> findAll();
	void updateQuantidade(Produto prod, int qtd_op, int novo_quantidade);
	void updateCategoria(Produto prod);
	void updateFabricacao(Produto prod);
	void updateDescricao(Produto prod);
	void updateValor(Produto prod);
	void updateNome(Produto prod);
}
