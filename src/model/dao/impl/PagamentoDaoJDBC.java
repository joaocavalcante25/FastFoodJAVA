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
import model.dao.PagamentoDao;
import model.entities.Pagamento;
import model.entities.Pedido;

public class PagamentoDaoJDBC implements PagamentoDao {

	private Connection conn;
	
	public PagamentoDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	@Override
	public int insert(Pagamento obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO pagamento "
					+ "(status_pagamento, tipo_pagamento, pedido_id) "
					+ "VALUES "
					+ "(?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getStatus());
			st.setString(2, obj.getTipoDePag());
			st.setInt(3, obj.getPedido().getPedido_id());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setPagamento_id(id);
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
	public List<Pagamento> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM pagamento");
			rs = st.executeQuery();
			List<Pagamento> list = new ArrayList<>();
			while (rs.next()) {
				Pagamento pagamento = new Pagamento();
				Pedido pedido = new Pedido();
				pagamento.setPagamento_id(rs.getInt("pagamento_id"));
				pagamento.setStatus(rs.getString("status_pagamento"));
				pagamento.setTipoDePag(rs.getString("tipo_pagamento"));
				pedido.setPedido_id(rs.getInt("pedido_id"));
				pagamento.setPedido(pedido);
				list.add(pagamento);
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
