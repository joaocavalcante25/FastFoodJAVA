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
import model.dao.ProdutoDao;
import model.entities.Categoria;
import model.entities.Produto;

public class ProdutoDaoJDBC implements ProdutoDao{

	private Connection conn;
	
	public ProdutoDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public boolean insert(Produto obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO produto "
					+ "(nome, valor, descricao, qtd_estoque, data_fabricacao, categoria_id) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getNome());
			st.setDouble(2, obj.getValor());
			st.setString(3, obj.getDescricao());
			st.setInt(4, obj.getQtdEstoque());
			st.setString(5, obj.getDataProducao());
			st.setInt(6, obj.getCategoria().getCategoria_id());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setProduto_id(id);
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
	public Produto findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT produto_id, nome, valor, descricao, qtd_estoque, data_fabricacao, categoria_id FROM produto WHERE produto_id>1 AND produto_id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Produto prod = new Produto();
				Categoria cat = new Categoria();
				prod.setProduto_id(rs.getInt("produto_id"));
				prod.setNome(rs.getString("nome"));
				prod.setValor(rs.getDouble("valor"));
				prod.setDescricao(rs.getString("descricao"));
				prod.setQtdEstoque(rs.getInt("qtd_estoque"));
				prod.setDataProducao(rs.getString("data_fabricacao"));
				cat.setCategoria_id(rs.getInt("categoria_id"));
				cat.setNome(rs.getString("nome"));
				cat.setDescricao(rs.getString("descricao"));
				prod.setCategoria(cat);
				return prod;
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
	public List<Produto> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM produto where produto_id>1");
			rs = st.executeQuery();
			List<Produto> list = new ArrayList<>();
			while (rs.next()) {
				Produto produto = new Produto();
				Categoria categoria = new Categoria();
				produto.setProduto_id(rs.getInt("produto_id"));
				produto.setNome(rs.getString("nome"));
				produto.setValor(rs.getDouble("valor"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setQtdEstoque(rs.getInt("qtd_estoque"));
				produto.setDataProducao(rs.getString("data_fabricacao"));
				categoria.setCategoria_id(rs.getInt("categoria_id"));
				produto.setCategoria(categoria);
				list.add(produto);
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
	public void atualizaQuantidade(Produto produto, Integer quantidade) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE produto SET qtd_estoque = (qtd_estoque - ?) WHERE produto_id = ?");
			st.setInt(1, quantidade);
			st.setInt(2, produto.getProduto_id());
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
	public void updateQuantidade(Produto prod, int qtd_op, int novo_quantidade) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE produto SET qtd_estoque = ? WHERE produto_id = ?");
			if(qtd_op==1) {
				st.setInt(1, prod.getQtdEstoque()+novo_quantidade);
				st.setInt(2, prod.getProduto_id());
				st.executeUpdate();
			}else if(qtd_op==2) {
				st.setInt(1, prod.getQtdEstoque());
				st.setInt(2, prod.getProduto_id());
				st.executeUpdate();
			}
			
			System.out.println("Quantidade alterada com sucesso");
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void updateCategoria(Produto prod) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE produto SET categoria_id = ? WHERE produto_id = ?");
			st.setInt(1, prod.getCategoria().getCategoria_id());
			st.setInt(2, prod.getProduto_id());
			st.executeUpdate();
			System.out.println("Categoria alterado com sucesso");
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void updateFabricacao(Produto prod) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE produto SET data_fabricacao = ? WHERE produto_id = ?");
			st.setString(1, prod.getDataProducao());
			st.setInt(2, prod.getProduto_id());
			st.executeUpdate();
			System.out.println("Data de fabricacao alterada com sucesso");
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void updateDescricao(Produto prod) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE produto SET descricao = ? WHERE produto_id = ?");
			st.setString(1, prod.getDescricao());
			st.setInt(2, prod.getProduto_id());
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
	public void updateValor(Produto prod) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE produto SET valor = ? WHERE produto_id = ?");
			st.setDouble(1, prod.getValor());
			st.setInt(2, prod.getProduto_id());
			st.executeUpdate();
			System.out.println("Valor alterado com sucesso");
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void updateNome(Produto prod) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE produto SET nome = ? WHERE produto_id = ?");
			st.setString(1, prod.getNome());
			st.setInt(2, prod.getProduto_id());
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

}
