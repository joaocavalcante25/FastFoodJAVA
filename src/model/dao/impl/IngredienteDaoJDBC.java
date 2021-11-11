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
import model.dao.IngredienteDao;
import model.entities.Ingrediente;

public class IngredienteDaoJDBC implements IngredienteDao{

	private Connection conn;
	
	public IngredienteDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public boolean insert(Ingrediente obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO ingrediente "
					+ "(nome, unidade_medida, validade, valor_porcao, qtd_estoque) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getUnidadeMedida());
			st.setString(3, obj.getValidade());
			st.setDouble(4, obj.getValorPorcao());
			st.setInt(5, obj.getQtdeEstoque());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setIngrediente_id(id);
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
	public Ingrediente findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT ingrediente_id, nome, unidade_medida, validade, valor_porcao, qtd_estoque FROM ingrediente WHERE ingrediente_id>1 AND ingrediente_id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Ingrediente ing = new Ingrediente();
				ing.setIngrediente_id(rs.getInt("ingrediente_id"));
				ing.setNome(rs.getString("nome"));
				ing.setUnidadeMedida(rs.getString("unidade_medida"));
				ing.setValidade(rs.getString("validade"));
				ing.setValorPorcao(rs.getDouble("valor_porcao"));
				ing.setQtdeEstoque(rs.getInt("qtd_estoque"));
				return ing;
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
	public List<Ingrediente> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM ingrediente WHERE ingrediente_id > 1");
			rs = st.executeQuery();
			List<Ingrediente> list = new ArrayList<>();
			while (rs.next()) {
				Ingrediente ingrediente = new Ingrediente();
				ingrediente.setIngrediente_id(rs.getInt("ingrediente_id"));
				ingrediente.setNome(rs.getString("nome"));
				ingrediente.setUnidadeMedida(rs.getString("unidade_medida"));
				ingrediente.setValidade(rs.getString("validade"));
				ingrediente.setValorPorcao(rs.getDouble("valor_porcao"));
				ingrediente.setQtdeEstoque(rs.getInt("qtd_estoque"));
				list.add(ingrediente);
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
	public void atualizaQuantidade(Ingrediente ingrediente, Integer quantidade) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE ingrediente SET qtd_estoque = (qtd_estoque - ?) WHERE ingrediente_id = ?");
			st.setInt(1, quantidade);
			st.setInt(2, ingrediente.getIngrediente_id());
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
	public void updateNome(Ingrediente ing) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE ingrediente SET nome = ? WHERE ingrediente_id = ?");
			st.setString(1, ing.getNome());
			st.setInt(2, ing.getIngrediente_id());
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
	public void updateUnidade(Ingrediente ing) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE ingrediente SET unidade_medida = ? WHERE ingrediente_id = ?");
			st.setString(1, ing.getUnidadeMedida());
			st.setInt(2, ing.getIngrediente_id());
			st.executeUpdate();
			System.out.println("Unidade medida alterado com sucesso");
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void updateValidade(Ingrediente ing) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE ingrediente SET validade = ? WHERE ingrediente_id = ?");
			st.setString(1, ing.getValidade());
			st.setInt(2, ing.getIngrediente_id());
			st.executeUpdate();
			System.out.println("Validade alterado com sucesso");
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void updateValor(Ingrediente ing) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE ingrediente SET valor_porcao = ? WHERE ingrediente_id = ?");
			st.setDouble(1, ing.getValorPorcao());
			st.setInt(2, ing.getIngrediente_id());
			st.executeUpdate();
			System.out.println("Valor da porcao alterado com sucesso");
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void updateQuantidade(Ingrediente ing, int qtd_op, int novo_quantidade) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE ingrediente SET qtd_estoque = ? WHERE ingrediente_id = ?");
			if(qtd_op==1) {
				st.setInt(1, ing.getQtdeEstoque()+novo_quantidade);
				st.setInt(2, ing.getIngrediente_id());
				st.executeUpdate();
			}else if(qtd_op==2) {
				st.setInt(1, ing.getQtdeEstoque());
				st.setInt(2, ing.getIngrediente_id());
				st.executeUpdate();
			}
			
			System.out.println("Valor da porcao alterado com sucesso");
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}
}
