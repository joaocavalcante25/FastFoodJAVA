package model.dao;

import java.util.List;

import model.entities.Ingrediente;

public interface IngredienteDao {
	boolean insert(Ingrediente obj);
	Ingrediente findById(Integer id);
	List<Ingrediente> findAll();
	void atualizaQuantidade(Ingrediente ingrediente,Integer quantidade);
	void updateNome(Ingrediente ing);
	void updateUnidade(Ingrediente ing);
	void updateValidade(Ingrediente ing);
	void updateValor(Ingrediente ing);
	void updateQuantidade(Ingrediente ing, int qtd_op, int novo_quantidade);
}
