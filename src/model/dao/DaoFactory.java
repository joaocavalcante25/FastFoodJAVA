package model.dao;

import db.DB;
import model.dao.impl.AtendenteDaoJDBC;
import model.dao.impl.CategoriaDaoJDBC;
import model.dao.impl.Ing_ProdDaoJDBC;
import model.dao.impl.IngredienteDaoJDBC;
import model.dao.impl.Item_PedidoDaoJDBC;
import model.dao.impl.PagamentoDaoJDBC;
import model.dao.impl.PedidoDaoJDBC;
import model.dao.impl.Pedido_ItemDaoJDBC;
import model.dao.impl.ProdutoDaoJDBC;
import model.dao.impl.PromocaoDaoJDBC;

public class DaoFactory {
	public static AtendenteDao createAtendenteDao() {
		return new AtendenteDaoJDBC(DB.getConnection());
	}
	public static CategoriaDao createCategoriaDao() {
		return new CategoriaDaoJDBC(DB.getConnection());
	}
	public static Ing_ProdDao createIng_ProdDao() {
		return new Ing_ProdDaoJDBC(DB.getConnection());
	}
	public static IngredienteDao createIngredienteDao() {
		return new IngredienteDaoJDBC(DB.getConnection());
	}
	public static Item_PedidoDao createItem_PedidoDao() {
		return new Item_PedidoDaoJDBC(DB.getConnection());
	}
	public static PagamentoDao createPagamentoDao() {
		return new PagamentoDaoJDBC(DB.getConnection());
	}
	public static PedidoDao createPedidoDao() {
		return new PedidoDaoJDBC(DB.getConnection());
	}
	public static ProdutoDao createProdutoDao() {
		return new ProdutoDaoJDBC(DB.getConnection());
	}
	public static PromocaoDao createPromocaoDao() {
		return new PromocaoDaoJDBC(DB.getConnection());
	}
	
	public static Pedido_ItemDao createPedido_ItemDao() {
		return new Pedido_ItemDaoJDBC(DB.getConnection());
	}
}
