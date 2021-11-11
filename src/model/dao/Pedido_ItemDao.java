package model.dao;

import java.util.List;

import model.entities.Item_Pedido;
import model.entities.Pedido;
import model.entities.Pedido_Item;

public interface Pedido_ItemDao {
	void insert(Pedido ped, Item_Pedido it_ped);
	void deleteById(Integer id);
	List<Pedido_Item> findAll();
	List<Pedido_Item> carrinho(Integer id);
	List<Pedido_Item> itensCancelamento(Integer id);
}
