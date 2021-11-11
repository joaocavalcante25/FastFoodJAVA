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
import model.dao.AtendenteDao;
import model.entities.Atendente;

public class AtendenteDaoJDBC implements AtendenteDao {
	
	private Connection conn;
	
	public AtendenteDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public boolean insert(Atendente obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO atendente "
					+ "(status, turno, senha, login, nome) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getStatus());
			st.setString(2, obj.getTurno());
			st.setString(3, obj.getSenha());
			st.setString(4, obj.getLogin());
			st.setString(5, obj.getNome());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setAtendente_id(id);
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
	public void updateNome(Atendente obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE atendente SET nome = ? WHERE atendente_id = ?");
			st.setString(1, obj.getNome());
			st.setInt(2, obj.getAtendente_id());
			System.out.println("Nome alterado com sucesso");
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}
	
	public void updateStatus(Atendente obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE atendente SET status = ? WHERE atendente_id = ?");
			st.setString(1, obj.getStatus());
			st.setInt(2, obj.getAtendente_id());
			st.executeUpdate();
			System.out.println("Status alterado com sucesso");
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}
	
	public void updateLogin(Atendente obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE atendente SET login = ? WHERE atendente_id = ?");
			st.setString(1, obj.getLogin());
			st.setInt(2, obj.getAtendente_id());
			st.executeUpdate();
			System.out.println("Login alterado com sucesso");
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}
	
	public void updateTurno(Atendente obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE atendente SET turno = ? WHERE atendente_id = ?");
			st.setString(1, obj.getTurno());
			st.setInt(2, obj.getAtendente_id());
			st.executeUpdate();
			System.out.println("Turno alterado com sucesso");
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}
	
	public void updateSenha(Atendente obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE atendente SET senha = ? WHERE atendente_id = ?");
			st.setString(1, obj.getSenha());
			st.setInt(2, obj.getAtendente_id());
			st.executeUpdate();
			System.out.println("Senha alterada com sucesso");
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Atendente findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT atendente_id, nome, status, turno FROM atendente WHERE atendente_id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Atendente atend = new Atendente();
				atend.setAtendente_id(rs.getInt("atendente_id"));
				atend.setNome(rs.getString("nome"));
				atend.setStatus(rs.getString("status"));
				atend.setTurno(rs.getString("turno"));
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

	@Override
	public List<Atendente> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM atendente");
			rs = st.executeQuery();
			List<Atendente> list = new ArrayList<>();
			while (rs.next()) {
				Atendente atendente = new Atendente();
				atendente.setAtendente_id(rs.getInt("atendente_id"));
				atendente.setNome(rs.getString("nome"));
				atendente.setStatus(rs.getString("status"));
				atendente.setTurno(rs.getString("turno"));
				list.add(atendente);
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
	public Atendente findLogin(String login, String senha) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT atendente_id, login, senha, nome FROM atendente WHERE login = ? AND senha = ?");
			st.setString(1, login);
			st.setString(2, senha);
			rs = st.executeQuery();
			if (rs.next()) {
				Atendente atend = new Atendente();
				atend.setAtendente_id(rs.getInt("atendente_id"));
				atend.setLogin(rs.getString("login"));
				atend.setSenha(rs.getString("senha"));
				atend.setNome(rs.getString("nome"));
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
}
