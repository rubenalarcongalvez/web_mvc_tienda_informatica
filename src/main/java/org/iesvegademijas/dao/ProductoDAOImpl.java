package org.iesvegademijas.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.iesvegademijas.model.Producto;

public class ProductoDAOImpl extends AbstractDAOImpl implements ProductoDAO {

	/**
	 * Inserta en base de datos el nuevo producto, actualizando el id en el bean
	 * producto.
	 */
	@Override
	public synchronized void create(Producto producto) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rsGenKeys = null;

		try {
			conn = connectDB();

			// 1 alternativas comentadas:
			// ps = conn.prepareStatement("INSERT INTO producto (nombre) VALUES (?)", new
			// String[] {"codigo"});
			// Ver también, AbstractDAOImpl.executeInsert ...
			// Columna producto.codigo es clave primaria auto_increment, por ese motivo se
			// omite de la sentencia SQL INSERT siguiente.
			ps = conn.prepareStatement("INSERT INTO producto (nombre, precio, codigo_fabricante) VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			int idx = 1;
			ps.setString(idx++, producto.getNombre());
			ps.setBigDecimal(idx++, producto.getPrecio());
			ps.setInt(idx++, producto.getCodigoFabricante());

			int rows = ps.executeUpdate();
			if (rows == 0)
				System.out.println("INSERT de producto con 0 filas insertadas.");

			rsGenKeys = ps.getGeneratedKeys();
			if (rsGenKeys.next()) {
				producto.setCodigo(rsGenKeys.getInt(1));
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
	 * Devuelve lista con todos loa productos.
	 */
	@Override
	public List<Producto> getAll() {

		Connection conn = null;
		Statement s = null;
		ResultSet rs = null;

		List<Producto> listProd = new ArrayList<>();

		try {
			conn = connectDB();

			// Se utiliza un objeto Statement dado que no hay parámetros en la consulta.
			s = conn.createStatement();

			rs = s.executeQuery("SELECT * FROM producto");
			while (rs.next()) {
				Producto prod = new Producto();
				int idx = 1;
				prod.setCodigo(rs.getInt(idx++));
				prod.setNombre(rs.getString(idx++));
				prod.setPrecio(rs.getBigDecimal(idx++));
				listProd.add(prod);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeDb(conn, s, rs);
		}
		return listProd;

	}

	/**
	 * Devuelve Optional de producto con el ID dado.
	 */
	@Override
	public Optional<Producto> find(int id) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = connectDB();

			ps = conn.prepareStatement("SELECT * FROM producto WHERE codigo = ?");

			int idx = 1;
			ps.setInt(idx, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				Producto prod = new Producto();
				idx = 1;
				prod.setCodigo(rs.getInt(idx++));
				prod.setNombre(rs.getString(idx++));
				prod.setPrecio(rs.getBigDecimal(idx++));

				return Optional.of(prod);
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
	 * Actualiza producto con campos del bean producto según ID del mismo.
	 */
	@Override
	public void update(Producto producto) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = connectDB();

			ps = conn.prepareStatement("UPDATE producto SET nombre = ?, precio = ? WHERE codigo = ?");
			int idx = 1;
			ps.setString(idx++, producto.getNombre());
			ps.setBigDecimal(idx++, producto.getPrecio());
			ps.setInt(idx, producto.getCodigo());

			int rows = ps.executeUpdate();

			if (rows == 0)
				System.out.println("Update de producto con 0 registros actualizados.");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeDb(conn, ps, rs);
		}

	}

	/**
	 * Borra producto con ID proporcionado.
	 */
	@Override
	public void delete(int id) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = connectDB();

			ps = conn.prepareStatement("DELETE FROM producto WHERE codigo = ?");
			int idx = 1;
			ps.setInt(idx, id);

			int rows = ps.executeUpdate();

			if (rows == 0)
				System.out.println("Delete de producto con 0 registros eliminados.");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeDb(conn, ps, rs);
		}

	}

	/**
	 * Devuelve lista con los productos encontrados.
	 */
	@Override
	public List<Producto> buscar(String nombreProducto) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Producto> listaProd = new ArrayList<>();

		try {
			conn = connectDB();
			String query = """
					SELECT
					    *
					FROM
					    producto
					WHERE
					    producto.nombre LIKE CONCAT(CONCAT('%', ?), '%')
					""";

			ps = conn.prepareStatement(query);

			int idx = 1;
			ps.setString(idx, nombreProducto);

			rs = ps.executeQuery();

			while (rs.next()) {
				Producto prod = new Producto();
				// Los cojo de las columnas de la base de datos
				prod.setCodigo(rs.getInt("codigo"));
				prod.setNombre(rs.getString("nombre"));
				prod.setPrecio(rs.getBigDecimal("precio"));

				listaProd.add(prod);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeDb(conn, ps, rs);
		}

		return listaProd;

	}

	/**
	 * Devuelve lista con los productos encontrados en fulltext.
	 */
	@Override
	public List<Producto> buscarFulltext(String nombreProducto) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Producto> listaProd = new ArrayList<>();

		try {
			conn = connectDB();
			
			//En la base de datos
//			CREATE FULLTEXT INDEX idx_nombre ON producto(nombre);
			
			String query = """
					SELECT * FROM producto where match(nombre) against (? in boolean mode);
							""";

			ps = conn.prepareStatement(query);

			nombreProducto += '*';  //Para que busque el producto o todo lo que lo contenga
			
			int idx = 1;
			ps.setString(idx, nombreProducto);

			rs = ps.executeQuery();

			while (rs.next()) {
				Producto prod = new Producto();
				// Los cojo de las columnas de la base de datos
				prod.setCodigo(rs.getInt("codigo"));
				prod.setNombre(rs.getString("nombre"));
				prod.setPrecio(rs.getBigDecimal("precio"));

				listaProd.add(prod);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeDb(conn, ps, rs);
		}

		return listaProd;

	}

}
