package model.dao;

import java.util.List;

import model.entities.Pagamento;

public interface PagamentoDao {
	int insert(Pagamento obj);
	List<Pagamento> findAll();
}
