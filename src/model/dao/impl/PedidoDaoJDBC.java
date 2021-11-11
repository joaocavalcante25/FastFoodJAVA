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
import model.dao.PedidoDao;
import model.entities.Atendente;
import model.entities.Pedido;

public class PedidoDaoJDBC implements PedidoDao {

	private Connection conn;
	
	public PedidoDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public int insert(Pedido obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO pedido "
					+ "(hora_pedido, descricao, status_pedido, atendente_id) "
					+ "VALUES "
					+ "(?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getHorarioPedido());
			st.setString(2, obj.getDescricao());
			st.setString(3, obj.getStatusPedido());
			st.setInt(4, obj.getAtendente().getAtendente_id());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setPedido_id(id);
					return id;
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Erro, nenhuma linha afetada");
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
	
	public void updateAtendente(Pedido obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE pedido SET atendente_id = ? WHERE pedido_id = ?");
			
			st.setInt(1, obj.getAtendente().getAtendente_id());
			st.setInt(2, obj.getPedido_id());
			st.executeUpdate();
			System.out.println("Atendente cadastrado no pedido");
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}
	
	public void updateStatus(Pedido obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE pedido SET status_pedido = ? WHERE pedido_id = ?");
			
			st.setString(1, obj.getStatusPedido());
			st.setInt(2, obj.getPedido_id());
			st.executeUpdate();
			System.out.println("Status atualizado");
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Pedido findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT pedido_id, hora_pedido, descricao, status_pedido, atendente_id FROM pedido WHERE pedido_id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Pedido ped = new Pedido();
				Atendente atend = new Atendente();
				ped.setPedido_id(rs.getInt("pedido_id"));
				ped.setHorarioPedido(rs.getString("hora_pedido"));
				ped.setDescricao(rs.getString("descricao"));
				ped.setStatusPedido(rs.getString("status_pedido"));
				atend.setAtendente_id(rs.getInt("atendente_id"));
				ped.setAtendente(atend);
				return ped;
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
	public List<Pedido> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM pedido");
			rs = st.executeQuery();
			List<Pedido> list = new ArrayList<>();
			while (rs.next()) {
				Pedido pedido = new Pedido();
				Atendente atendente = new Atendente();
				pedido.setPedido_id(rs.getInt("pedido_id"));
				pedido.setHorarioPedido(rs.getString("hora_pedido"));
				pedido.setDescricao(rs.getString("descricao"));
				pedido.setStatusPedido(rs.getString("status_pedido"));
				atendente.setAtendente_id(rs.getInt("atendente_id"));
				pedido.setAtendente(atendente);
				list.add(pedido);
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
	public double findPrecoById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT SUM((qtd_produto*produto.valor + qtd_ingrediente*ingrediente.valor_porcao)) as Total FROM item_pedido INNER JOIN produto INNER JOIN ingrediente INNER JOIN pedido_item ON item_pedido.produto_id = produto.produto_id AND item_pedido.ingrediente_id = ingrediente.ingrediente_id WHERE item_pedido.item_pedido_id = pedido_item.item_pedido_id AND pedido_item.pedido_id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Pedido ped = new Pedido();
				ped.setValor(rs.getDouble("Total"));
				return ped.getValor();
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		return 0;
	}
}
