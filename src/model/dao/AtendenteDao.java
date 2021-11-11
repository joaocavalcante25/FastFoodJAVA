package model.dao;

import java.util.List;

import model.entities.Atendente;

public interface AtendenteDao {
	boolean insert(Atendente obj);
	void updateNome(Atendente obj);
	void updateTurno(Atendente obj);
	void updateStatus(Atendente obj);
	void updateLogin(Atendente obj);
	void updateSenha(Atendente obj);
	Atendente findById(Integer id);
	List<Atendente> findAll();
	Atendente findLogin(String login, String senha);
}
