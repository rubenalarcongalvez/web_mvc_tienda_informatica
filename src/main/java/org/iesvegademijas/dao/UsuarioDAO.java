package org.iesvegademijas.dao;

import java.util.List;
import java.util.Optional;

import org.iesvegademijas.model.Usuario;

public interface UsuarioDAO {
	public void create(Usuario usuario);

	public List<Usuario> getAll();

	public Optional<Usuario> find(int id);

	Optional<Usuario> getUsuario(String usuario);
	
	public void update(Usuario usuario);

	public void delete(int id);
}