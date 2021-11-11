package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DaoFactory;
import model.dao.Ing_ProdDao;
import model.dao.Item_PedidoDao;
import model.entities.Ing_Prod;
import model.entities.Ingrediente;
import model.entities.Item_Pedido;
import model.entities.Produto;

public class Item_PedidoDaoJDBC implements Item_PedidoDao {
	Ing_ProdDao ing_prodDao = DaoFactory.createIng_ProdDao();
	private Connection conn;
	
	public Item_PedidoDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public int insert(Item_Pedido obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO item_pedido "
					+ "(qtd_produto, qtd_ingrediente, produto_id, ingrediente_id, status) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setInt(1, obj.getQtdeProdutos());
			st.setInt(2, obj.getQtdeIngredientes());
			if(obj.getIngrediente() == null) {
				st.setInt(3, obj.getProduto().getProduto_id());
				ing_prodDao.atualizaIngredientes(obj.getProduto().getProduto_id(), obj.getQtdeProdutos());
				st.setInt(4, 1);
			}else if(obj.getProduto() == null) {
				st.setInt(3, 1);
				st.setInt(4, obj.getIngrediente().getIngrediente_id());
			}
			st.setString(5, obj.getStatus());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setItem_pedido_id(id);
					return id;
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		return 0;
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM item_pedido WHERE item_pedido_id = ?");
			st.setInt(1, id);
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Item_Pedido findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT item_pedido_id, qtd_produto, qtd_ingrediente, produto_id, ingrediente_id FROM item_pedido WHERE item_pedido_id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Item_Pedido it_ped = new Item_Pedido();
				Ingrediente ing = new Ingrediente();
				Produto prod = new Produto();
				ing.setIngrediente_id(rs.getInt("ingrediente_id"));
				it_ped.setIngrediente(ing);
				prod.setProduto_id(rs.getInt("produto_id"));
				it_ped.setProduto(prod);
				it_ped.setQtdeIngredientes(rs.getInt("qtd_ingrediente"));
				it_ped.setQtdeProdutos(rs.getInt("qtd_produto"));
				return it_ped;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Item_Pedido> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM item_pedido");
			rs = st.executeQuery();
			List<Item_Pedido> list = new ArrayList<>();
			while (rs.next()) {
				Item_Pedido item_pedido = new Item_Pedido();
				Produto produto = new Produto();
				Ingrediente ingrediente = new Ingrediente();
				item_pedido.setItem_pedido_id(rs.getInt("item_pedido_id"));
				item_pedido.setQtdeProdutos(rs.getInt("qtd_produto"));
				item_pedido.setQtdeIngredientes(rs.getInt("qtd_ingrediente"));
				produto.setProduto_id(rs.getInt("produto_id"));
				ingrediente.setIngrediente_id(rs.getInt("ingrediente_id"));
				item_pedido.setProduto(produto);
				item_pedido.setIngrediente(ingrediente);
				list.add(item_pedido);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		
		}
	}

	@Override
	public Item_Pedido findByProduto(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT item_pedido_id, produto_id, qtd_produto FROM item_pedido WHERE item_pedido_id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Item_Pedido it_ped = new Item_Pedido();
				Produto prod = new Produto();
				it_ped.setItem_pedido_id(rs.getInt("item_pedido_id"));
				it_ped.setQtdeProdutos(rs.getInt("qtd_produto"));
				prod.setProduto_id(rs.getInt("produto_id"));
				it_ped.setProduto(prod);
				return it_ped;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public Item_Pedido findByIngrediente(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT item_pedido_id, ingrediente_id, qtd_ingrediente FROM item_pedido WHERE item_pedido_id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Item_Pedido it_ped = new Item_Pedido();
				Ingrediente ing = new Ingrediente();
				it_ped.setItem_pedido_id(rs.getInt("item_pedido_id"));
				it_ped.setQtdeIngredientes(rs.getInt("qtd_ingrediente"));
				ing.setIngrediente_id(rs.getInt("ingrediente_id"));
				it_ped.setIngrediente(ing);
				return it_ped;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public void removerCarrinho(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE item_pedido SET status = 'retirado' WHERE item_pedido_id = ?");
			st.setInt(1, id);
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}
	
	public void cancelarItens(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("update item_pedido INNER JOIN pedido_item set item_pedido.status='cancelado' "
					+ "where item_pedido.item_pedido_id = pedido_item.item_pedido_id AND pedido_item.pedido_id = ?");
			st.setInt(1, id);
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}
}
