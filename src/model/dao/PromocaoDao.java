package model.dao;

import java.util.List;

import model.entities.Promocao;

public interface PromocaoDao {
	boolean insert(Promocao obj);
	Promocao findById(Integer id);
	List<Promocao> findAll();
	void updateTipo(Promocao promo);
	void updateValidade(Promocao promo);
	void updatePreco(Promocao promo);
	void updateDescricao(Promocao promo);
	void updateProduto(Promocao promo);
}
