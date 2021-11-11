package model.dao;

import java.util.List;

import model.entities.Ing_Prod;

public interface Ing_ProdDao {
	boolean insert(Ing_Prod obj);
	void atualizaIngredientes(Integer id, int quantidade);
	Ing_Prod findById(Integer id);
	List<Ing_Prod> findAll();
	void updateIngrediente(Ing_Prod ip);
	void updateProduto(Ing_Prod ip);
	void updateQuantidade(Ing_Prod ip);
}
