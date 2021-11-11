package model.dao;

import java.util.List;

import model.entities.Item_Pedido;

public interface Item_PedidoDao {
	int insert(Item_Pedido obj);
	void removerCarrinho(Integer id);
	void cancelarItens(Integer id);
	void deleteById(Integer id);
	Item_Pedido findById(Integer id);
	List<Item_Pedido> findAll();
	Item_Pedido findByProduto(Integer id);
	Item_Pedido findByIngrediente(Integer id);
	
}
