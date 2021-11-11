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
import model.dao.PromocaoDao;
import model.entities.Categoria;
import model.entities.Produto;
import model.entities.Promocao;

public class PromocaoDaoJDBC implements PromocaoDao {

	private Connection conn;
	
	public PromocaoDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public boolean insert(Promocao obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO promocao "
					+ "(tipo, validade, preco, descricao, produto_id) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getTipo());
			st.setString(2, obj.getDuracao());
			st.setDouble(3, obj.getPreco());
			st.setString(4, obj.getDescricao());
			st.setInt(5, obj.getProduto().getProduto_id());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setPromocao_id(id);
					return true;
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
		return false;
	}

	@Override
	public Promocao findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT promocao_id, tipo, validade, preco, descricao, produto_id FROM promocao WHERE promocao_id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Promocao promo = new Promocao();
				Produto prod = new Produto();
				promo.setPromocao_id(rs.getInt("promocao_id"));
				promo.setTipo(rs.getString("tipo"));
				promo.setDuracao(rs.getString("validade"));
				promo.setPreco(rs.getDouble("preco"));
				promo.setDescricao(rs.getString("descricao"));
				prod.setProduto_id(rs.getInt("produto_id"));
				promo.setProduto(prod);
				return promo;
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
	public List<Promocao> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM promocao");
			rs = st.executeQuery();
			List<Promocao> list = new ArrayList<>();
			while (rs.next()) {
				Promocao promocao = new Promocao();
				Produto produto = new Produto();
				promocao.setPromocao_id(rs.getInt("promocao_id"));
				promocao.setTipo(rs.getString("tipo"));
				promocao.setDuracao(rs.getString("validade"));
				promocao.setPreco(rs.getDouble("preco"));
				promocao.setDescricao(rs.getString("descricao"));
				produto.setProduto_id(rs.getInt("produto_id"));
				promocao.setProduto(produto);
				list.add(promocao);
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
	public void updateTipo(Promocao promo) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE promocao SET tipo = ? WHERE promocao_id = ?");
			st.setString(1, promo.getTipo());
			st.setInt(2, promo.getPromocao_id());
			st.executeUpdate();
			System.out.println("Tipo alterado com sucesso");
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void updateValidade(Promocao promo) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE promocao SET validade = ? WHERE promocao_id = ?");
			st.setString(1, promo.getDuracao());
			st.setInt(2, promo.getPromocao_id());
			st.executeUpdate();
			System.out.println("Validade alterada com sucesso");
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void updatePreco(Promocao promo) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE promocao SET preco = ? WHERE promocao_id = ?");
			st.setDouble(1, promo.getPreco());
			st.setInt(2, promo.getPromocao_id());
			st.executeUpdate();
			System.out.println("Preco alterado com sucesso");
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void updateDescricao(Promocao promo) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE promocao SET descricao = ? WHERE promocao_id = ?");
			st.setString(1, promo.getDescricao());
			st.setInt(2, promo.getPromocao_id());
			st.executeUpdate();
			System.out.println("Descricao alterada com sucesso");
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void updateProduto(Promocao promo) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE promocao SET produto_id = ? WHERE promocao_id = ?");
			st.setInt(1, promo.getProduto().getProduto_id());
			st.setInt(2, promo.getPromocao_id());
			st.executeUpdate();
			System.out.println("Produto alterada com sucesso");
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

}
