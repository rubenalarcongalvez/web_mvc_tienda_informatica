package org.iesvegademijas.dao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.iesvegademijas.model.Usuario;

public class UsuarioDAOImpl extends AbstractDAOImpl implements UsuarioDAO {

	/**
	 * Inserta en base de datos el nuevo usuario, actualizando el id en el bean
	 * usuario.
	 *  
	 */
	@Override
	public synchronized void create(Usuario usuario) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rsGenKeys = null;

		try {
			conn = connectDB();

			//Se encripta con SHA
			ps = conn.prepareStatement("INSERT INTO usuario (nombre, password, rol) VALUES (?, SHA(?), ?)",
					Statement.RETURN_GENERATED_KEYS);

			int idx = 1; // Empieza en la columna 1
			ps.setString(idx++, usuario.getNombre()); // Hace la operación y luego suma
			ps.setString(idx++, usuario.getPassword());
			ps.setString(idx++, usuario.getRol());

			int rows = ps.executeUpdate();
			if (rows == 0)
				System.out.println("INSERT de usuario con 0 filas insertadas.");

			rsGenKeys = ps.getGeneratedKeys();
			if (rsGenKeys.next()) {
				usuario.setId(rsGenKeys.getInt(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeDb(conn, ps, rs);
		}

	}

	/**
	 * Devuelve lista con todos los usuarios.
	 * @throws NoSuchAlgorithmException 
	 */
	@Override
	public List<Usuario> getAll() {

		Connection conn = null;
		Statement s = null;
		ResultSet rs = null;

		List<Usuario> listUsr = new ArrayList<>();

		try {
			conn = connectDB();

			// Se utiliza un objeto Statement dado que no hay parámetros en la consulta.
			s = conn.createStatement();

			rs = s.executeQuery("SELECT * FROM usuario");
			while (rs.next()) {
				Usuario usr = new Usuario();
				int idx = 1;
				usr.setId(rs.getInt(idx++));
				usr.setNombre(rs.getString(idx++));
				usr.setPassword(rs.getString(idx++));
				usr.setRol(rs.getString(idx));
				listUsr.add(usr);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeDb(conn, s, rs);
		}
		return listUsr;

	}

	/**
	 * Devuelve Optional de usuario con el ID dado.
	 * @throws NoSuchAlgorithmException 
	 */
	@Override
	public Optional<Usuario> find(int id) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = connectDB();

			ps = conn.prepareStatement("SELECT * FROM usuario WHERE id = ?");

			int idx = 1;
			ps.setInt(idx, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				Usuario usr = new Usuario();
				idx = 1;
				usr.setId(rs.getInt(idx++));
				usr.setNombre(rs.getString(idx++));
				usr.setPassword(rs.getString(idx++));
				usr.setRol(rs.getString(idx++));

				return Optional.of(usr);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeDb(conn, ps, rs);
		}

		return Optional.empty();

	}
	
	/**
	 * Devuelve Optional de usuario con el nombre dado.
	 * 
	 */
	@Override
	public Optional<Usuario> getUsuario(String usuario) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = connectDB();

			ps = conn.prepareStatement("SELECT * FROM usuario WHERE nombre = ?");

			int idx = 1;
			ps.setString(idx, usuario);

			rs = ps.executeQuery();

			if (rs.next()) {
				Usuario usr = new Usuario();
				idx = 1;
				usr.setId(rs.getInt(idx++));
				usr.setNombre(rs.getString(idx++));
				usr.setPassword(rs.getString(idx++));
				usr.setRol(rs.getString(idx++));

				return Optional.of(usr);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeDb(conn, ps, rs);
		}

		return Optional.empty();

	}

	/**
	 * Actualiza usuario con campos del bean usuario según ID del mismo.
	 * 
	 */
	@Override
	public void update(Usuario usuario) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = connectDB();

			//Se encripta con SHA
			ps = conn.prepareStatement("UPDATE usuario SET nombre = ?, password = SHA(?), rol = ? WHERE id = ?");
			int idx = 1;
			ps.setString(idx++, usuario.getNombre());
			ps.setString(idx++, usuario.getPassword());
			ps.setString(idx++, usuario.getRol());
			ps.setInt(idx, usuario.getId());

			int rows = ps.executeUpdate();

			if (rows == 0)
				System.out.println("Update de usuario con 0 registros actualizados.");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeDb(conn, ps, rs);
		}

	}

	/**
	 * Borra usuario con ID proporcionado.
	 */
	@Override
	public void delete(int id) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = connectDB();

			ps = conn.prepareStatement("DELETE FROM usuario WHERE id = ?");
			int idx = 1;
			ps.setInt(idx, id);

			int rows = ps.executeUpdate();

			if (rows == 0)
				System.out.println("Delete de usuario con 0 registros eliminados.");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeDb(conn, ps, rs);
		}

	}

}