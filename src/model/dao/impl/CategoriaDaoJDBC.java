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
import model.dao.CategoriaDao;
import model.entities.Categoria;
import model.entities.Categoria;

public class CategoriaDaoJDBC implements CategoriaDao{

	private Connection conn;
	
	public CategoriaDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public boolean insert(Categoria obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO categoria "
					+ "(nome, descricao) "
					+ "VALUES "
					+ "(?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getDescricao());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setCategoria_id(id);
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
	public void updateNome(Categoria obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE categoria SET nome = ? WHERE categoria_id = ?");
			st.setString(1, obj.getNome());
			st.setInt(2, obj.getCategoria_id());
			st.executeUpdate();
			System.out.println("Nome alterado com sucesso");
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}
	
	@Override
	public void updateDescricao(Categoria obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE categoria SET descricao = ? WHERE categoria_id = ?");
			st.setString(1, obj.getDescricao());
			st.setInt(2, obj.getCategoria_id());
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
	public Categoria findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT categoria_id, nome, descricao FROM categoria WHERE categoria_id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Categoria atend = instanciarCategoria(rs);
				return atend;
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

	private Categoria instanciarCategoria(ResultSet rs) throws SQLException {
		Categoria atend = new Categoria();
		atend.setCategoria_id(rs.getInt("categoria_id"));
		atend.setNome(rs.getString("nome"));
		atend.setDescricao(rs.getString("descricao"));
		return atend;
	}

	@Override
	public List<Categoria> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM categoria");
			rs = st.executeQuery();
			List<Categoria> list = new ArrayList<>();
			while (rs.next()) {
				Categoria categoria = new Categoria();
				categoria.setCategoria_id(rs.getInt("categoria_id"));
				categoria.setDescricao(rs.getString("descricao"));
				categoria.setNome(rs.getString("nome"));
				list.add(categoria);
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
