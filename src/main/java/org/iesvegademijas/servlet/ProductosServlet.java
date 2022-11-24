package org.iesvegademijas.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iesvegademijas.dao.FabricanteDAO;
import org.iesvegademijas.dao.FabricanteDAOImpl;
import org.iesvegademijas.dao.ProductoDAO;
import org.iesvegademijas.dao.ProductoDAOImpl;
import org.iesvegademijas.model.Producto;

public class ProductosServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * HTTP Method: GET Paths: /productos/(index) - muestra listado principal con
	 * operaciones CRUD /productos/{id} - ver detalle de producto con {id}
	 * /productos/editar/{id} - acceder al formulario para editar producto con {id}
	 * /productos/crear - acceder al formulario para crear un producto nuevo
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = null;

		String pathInfo = request.getPathInfo();

		if (pathInfo == null || "/".equals(pathInfo)) {
			ProductoDAO prodDAO = new ProductoDAOImpl();

			// GET
			// /productos/?filtrar-por-nombre
			
			var listaProd = prodDAO.getAll();

			String filtroBusqueda = request.getParameter("filtrar-por-nombre");
			
			if (filtroBusqueda != null) {
				//Con stream
//				List<Producto> listaFiltrada = listaProd.stream()
//						 .filter(p -> p.getNombre().toLowerCase().contains(filtroBusqueda.toLowerCase()))
//						 .toList();
				
				//Con método
//				List<Producto> listaFiltrada = prodDAO.buscar(filtroBusqueda);
				
				//Con método fulltext
				List<Producto> listaFiltrada = prodDAO.buscarFulltext(filtroBusqueda);
				
				request.setAttribute("listaProductos", listaFiltrada);
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos.jsp");
			} else {
				request.setAttribute("listaProductos", listaProd);
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos.jsp");
			}

		} else {
			// GET
			// /productos/{id}
			// /productos/{id}/
			// /productos/edit/{id}
			// /productos/edit/{id}/
			// /productos/create
			// /productos/create/

			pathInfo = pathInfo.replaceAll("/$", "");
			String[] pathParts = pathInfo.split("/");

			if (pathParts.length == 2 && "crear".equals(pathParts[1])) {

				// GET
				// /productos/create
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/crear-producto.jsp");

				FabricanteDAO fabDAO = new FabricanteDAOImpl();

				request.setAttribute("listaFabricantes", fabDAO.getAll());

			} else if (pathParts.length == 2) {
				ProductoDAO prodDAO = new ProductoDAOImpl();
				// GET
				// /productos/{id}
				try {
					request.setAttribute("producto", prodDAO.find(Integer.parseInt(pathParts[1])));
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/detalle-producto.jsp");

				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos.jsp");
				}

			} else if (pathParts.length == 3 && "editar".equals(pathParts[1])) {
				ProductoDAO prodDAO = new ProductoDAOImpl();

				// GET
				// /productos/edit/{id}
				try {
					request.setAttribute("producto", prodDAO.find(Integer.parseInt(pathParts[2])));
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/editar-producto.jsp");

				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos.jsp");
				}

			} else {

				System.out.println("Opción POST no soportada.");
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos.jsp");

			}

		}

		dispatcher.forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher;
		String __method__ = request.getParameter("__method__");

		if (__method__ == null) {
			// Crear uno nuevo
			ProductoDAO prodDAO = new ProductoDAOImpl();

			String codigo = request.getParameter("codigo");
			String nombre = request.getParameter("nombre");
			String precio = request.getParameter("precio");
			String codigo_fabricante = request.getParameter("codigo_fabricante");

			Producto prod = new Producto();
			prod.setNombre(nombre);
			BigDecimal prec = BigDecimal.valueOf(Double.parseDouble(precio));
			prod.setPrecio(prec);
			int cod_fab = Integer.parseInt(codigo_fabricante);
			prod.setCodigoFabricante(cod_fab);
			prodDAO.create(prod);

		} else if (__method__ != null && "put".equalsIgnoreCase(__method__)) {
			// Actualizar uno existente
			// Dado que los forms de html sólo soportan method GET y POST utilizo parámetro
			// oculto para indicar la operación de actulización PUT.
			doPut(request, response);

		} else if (__method__ != null && "delete".equalsIgnoreCase(__method__)) {
			// Actualizar uno existente
			// Dado que los forms de html sólo soportan method GET y POST utilizo parámetro
			// oculto para indicar la operación de actulización DELETE.
			doDelete(request, response);

		} else {

			System.out.println("Opción POST no soportada.");

		}

		response.sendRedirect("/tienda_informatica/productos");
		// response.sendRedirect("/tienda_informatica/productos");

	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ProductoDAO prodDAO = new ProductoDAOImpl();
		String codigo = request.getParameter("codigo");
		String nombre = request.getParameter("nombre");
		String precio = request.getParameter("precio");
		String codigo_fabricante = request.getParameter("codigo_fabricante");
		Producto prod = new Producto();

		try {

			int id = Integer.parseInt(codigo);
			prod.setCodigo(id);
			prod.setNombre(nombre);
			BigDecimal prec = BigDecimal.valueOf(Double.parseDouble(precio));
			prod.setPrecio(prec);
			prodDAO.update(prod);

		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}

	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher;
		ProductoDAO prodDAO = new ProductoDAOImpl();
		String codigo = request.getParameter("codigo");

		try {

			int id = Integer.parseInt(codigo);

			prodDAO.delete(id);

		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}

	}

}
