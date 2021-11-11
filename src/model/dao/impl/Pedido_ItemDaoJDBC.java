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
import model.dao.Pedido_ItemDao;
import model.entities.Ingrediente;
import model.entities.Item_Pedido;
import model.entities.Pedido;
import model.entities.Pedido_Item;
import model.entities.Produto;

public class Pedido_ItemDaoJDBC implements Pedido_ItemDao {

	private Connection conn;
	
	public Pedido_ItemDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Pedido ped, Item_Pedido it_ped) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO pedido_item "
					+ "(pedido_id, item_pedido_id) "
					+ "VALUES "
					+ "(?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setInt(1, ped.getPedido_id());
			st.setInt(2, it_ped.getItem_pedido_id());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
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
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM pedido_item WHERE item_pedido_id = ?");
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
	public List<Pedido_Item> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM pedido_item");
			rs = st.executeQuery();
			List<Pedido_Item> list = new ArrayList<>();
			while (rs.next()) {
				Pedido_Item pedido_Item = new Pedido_Item();
				Pedido pedido = new Pedido();
				Item_Pedido item_pedido = new Item_Pedido();
				pedido.setPedido_id(rs.getInt("pedido_id"));
				item_pedido.setItem_pedido_id(rs.getInt("item_pedido_id"));
				pedido_Item.setItem_pedido(item_pedido);
				pedido_Item.setPedido(pedido);
				list.add(pedido_Item);
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
	public List<Pedido_Item> carrinho(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT pedido_item.item_pedido_id as item_pedido_id, ingrediente.nome as nome, ingrediente.ingrediente_id, item_pedido.qtd_ingrediente as quantidade FROM pedido_item INNER JOIN item_pedido INNER JOIN ingrediente ON pedido_item.item_pedido_id = item_pedido.item_pedido_id AND item_pedido.ingrediente_id = ingrediente.ingrediente_id AND pedido_item.pedido_id = ? AND item_pedido.qtd_ingrediente > 0 AND item_pedido.status = 'ativo'");
			st.setInt(1, id);
			rs = st.executeQuery();
			List<Pedido_Item> list = new ArrayList<>();
			while (rs.next()) {
				Pedido_Item pedido_Item = new Pedido_Item();
				Pedido pedido = new Pedido();
				Item_Pedido item_pedido = new Item_Pedido();
				Ingrediente ingrediente = new Ingrediente();
				ingrediente.setIngrediente_id(rs.getInt("ingrediente_id"));
				ingrediente.setNome(rs.getString("nome"));
				item_pedido.setIngrediente(ingrediente);
				item_pedido.setQtdeIngredientes(rs.getInt("quantidade"));
				item_pedido.setItem_pedido_id(rs.getInt("item_pedido_id"));
				pedido_Item.setItem_pedido(item_pedido);
				pedido_Item.setPedido(pedido);
				list.add(pedido_Item);
			}
			st = conn.prepareStatement("SELECT pedido_item.item_pedido_id as item_pedido_id, produto.nome as nome, produto.produto_id, item_pedido.qtd_produto as quantidade FROM pedido_item INNER JOIN item_pedido INNER JOIN produto ON pedido_item.item_pedido_id = item_pedido.item_pedido_id AND item_pedido.produto_id = produto.produto_id AND pedido_item.pedido_id = ? AND item_pedido.qtd_produto > 0 AND item_pedido.status = 'ativo'");
			st.setInt(1, id);
			rs = st.executeQuery();
			while (rs.next()) {
				Pedido_Item pedido_Item = new Pedido_Item();
				Pedido pedido = new Pedido();
				Item_Pedido item_pedido = new Item_Pedido();
				Produto produto = new Produto();
				produto.setProduto_id(rs.getInt("produto_id"));
				produto.setNome(rs.getString("nome"));
				item_pedido.setProduto(produto);
				item_pedido.setQtdeProdutos(rs.getInt("quantidade"));
				item_pedido.setItem_pedido_id(rs.getInt("item_pedido_id"));
				pedido_Item.setItem_pedido(item_pedido);
				pedido_Item.setPedido(pedido);
				list.add(pedido_Item);
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
	
	public List<Pedido_Item> itensCancelamento(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("select pedido.pedido_id, pedido.hora_pedido, item_pedido.item_pedido_id, ingrediente.nome, item_pedido.qtd_ingrediente, item_pedido.status FROM item_pedido INNER JOIN ingrediente INNER JOIN pedido_item INNER JOIN pedido ON item_pedido.ingrediente_id = ingrediente.ingrediente_id AND item_pedido.item_pedido_id = pedido_item.item_pedido_id AND pedido_item.pedido_id = ? AND pedido.pedido_id = pedido_item.pedido_id AND item_pedido.qtd_ingrediente>0");
			st.setInt(1, id);
			rs = st.executeQuery();
			List<Pedido_Item> list = new ArrayList<>();
			while (rs.next()) {
				Pedido_Item pedido_Item = new Pedido_Item();
				Pedido pedido = new Pedido();
				Item_Pedido item_pedido = new Item_Pedido();
				Ingrediente ingrediente = new Ingrediente();
				pedido.setPedido_id(rs.getInt("pedido_id"));
				pedido.setHorarioPedido(rs.getString("hora_pedido"));
				ingrediente.setNome(rs.getString("nome"));
				item_pedido.setItem_pedido_id(rs.getInt("item_pedido_id"));
				item_pedido.setIngrediente(ingrediente);
				item_pedido.setQtdeIngredientes(rs.getInt("qtd_ingrediente"));
				item_pedido.setStatus(rs.getString("status"));
				pedido_Item.setItem_pedido(item_pedido);
				pedido_Item.setPedido(pedido);
				list.add(pedido_Item);
			}
			st = conn.prepareStatement("select pedido.pedido_id, pedido.hora_pedido, item_pedido.item_pedido_id, produto.nome, item_pedido.qtd_produto, item_pedido.status FROM item_pedido INNER JOIN produto INNER JOIN pedido_item INNER JOIN pedido ON item_pedido.produto_id = produto.produto_id AND item_pedido.item_pedido_id = pedido_item.item_pedido_id AND pedido_item.pedido_id = ? AND pedido.pedido_id = pedido_item.pedido_id AND item_pedido.qtd_produto>0");
			st.setInt(1, id);
			rs = st.executeQuery();
			while (rs.next()) {
				Pedido_Item pedido_Item = new Pedido_Item();
				Pedido pedido = new Pedido();
				Item_Pedido item_pedido = new Item_Pedido();
				Produto produto = new Produto();
				pedido.setPedido_id(rs.getInt("pedido_id"));
				pedido.setHorarioPedido(rs.getString("hora_pedido"));
				produto.setNome(rs.getString("nome"));
				item_pedido.setItem_pedido_id(rs.getInt("item_pedido_id"));
				item_pedido.setProduto(produto);
				item_pedido.setQtdeProdutos(rs.getInt("qtd_produto"));
				item_pedido.setStatus(rs.getString("status"));
				pedido_Item.setItem_pedido(item_pedido);
				pedido_Item.setPedido(pedido);
				list.add(pedido_Item);
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

}
