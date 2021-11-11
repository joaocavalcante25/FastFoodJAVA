package model.dao;

import java.util.List;

import model.entities.Categoria;

public interface CategoriaDao {
	boolean insert(Categoria obj);
	Categoria findById(Integer id);
	List<Categoria> findAll();
	void updateDescricao(Categoria cat);
	void updateNome(Categoria cat);
}
