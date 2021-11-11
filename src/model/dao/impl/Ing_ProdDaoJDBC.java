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
import model.dao.Ing_ProdDao;
import model.entities.Ing_Prod;
import model.entities.Ingrediente;
import model.entities.Produto;
import model.entities.Promocao;

public class Ing_ProdDaoJDBC implements Ing_ProdDao{

	private Connection conn;
	
	public Ing_ProdDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	
	@Override
	public boolean insert(Ing_Prod obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO ing_prod (produto_id, ingrediente_id, qtd_ingrediente) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, obj.getProduto().getProduto_id());
			st.setInt(2, obj.getIngrediente().getIngrediente_id());
			st.setInt(3, obj.getQtdeIng());
			int rowsAffected = st.executeUpdate();
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setIng_prod_id(id);
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
	public Ing_Prod findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT ing_prod_id, produto_id, ingrediente_id, qtd_ingrediente FROM ing_prod WHERE ing_prod_id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Ing_Prod ip = new Ing_Prod();
				Produto prod = new Produto();
				Ingrediente ing = new Ingrediente();
				ip.setIng_prod_id(rs.getInt("ing_prod_id"));
				prod.setProduto_id(rs.getInt("produto_id"));
				ing.setIngrediente_id(rs.getInt("ingrediente_id"));
				ip.setIngrediente(ing);
				ip.setProduto(prod);
				ip.setQtdeIng(rs.getInt("qtd_ingrediente"));
				return ip;
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
	public List<Ing_Prod> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM ing_prod");
			rs = st.executeQuery();
			List<Ing_Prod> list = new ArrayList<>();
			while (rs.next()) {
				Ing_Prod ing_prod = new Ing_Prod();
				Ingrediente ingrediente = new Ingrediente();
				Produto produto = new Produto();
				ing_prod.setIng_prod_id(rs.getInt("ing_prod_id"));
				ingrediente.setIngrediente_id(rs.getInt("ingrediente_id"));
				produto.setProduto_id(rs.getInt("produto_id"));
				ing_prod.setQtdeIng(rs.getInt("qtd_ingrediente"));
				ing_prod.setProduto(produto);
				ing_prod.setIngrediente(ingrediente);
				list.add(ing_prod);
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
	public void atualizaIngredientes(Integer id, int quantidade) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("update ingrediente, ing_prod set ingrediente.qtd_estoque = (ingrediente.qtd_estoque - ing_prod.qtd_ingrediente*?) where ingrediente.ingrediente_id = ing_prod.ingrediente_id AND produto_id = ?");
			st.setInt(1, quantidade);
			st.setInt(2, id);
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
	public void updateIngrediente(Ing_Prod ip) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE ing_prod SET ingrediente_id = ? WHERE ing_prod_id = ?");
			st.setInt(1, ip.getIngrediente().getIngrediente_id());
			st.setInt(2, ip.getIng_prod_id());
			st.executeUpdate();
			System.out.println("Ingrediente alterado com sucesso");
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}


	@Override
	public void updateProduto(Ing_Prod ip) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE ing_prod SET produto_id = ? WHERE ing_prod_id = ?");
			st.setInt(1, ip.getProduto().getProduto_id());
			st.setInt(2, ip.getIng_prod_id());
			st.executeUpdate();
			System.out.println("Produto alterado com sucesso");
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void updateQuantidade(Ing_Prod ip) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE ing_prod SET qtd_ingrediente = ? WHERE ing_prod_id = ?");
			st.setInt(1, ip.getQtdeIng());
			st.setInt(2, ip.getIng_prod_id());
			st.executeUpdate();
			System.out.println("Quantidade de ingrediente alterada com sucesso");
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}
}
