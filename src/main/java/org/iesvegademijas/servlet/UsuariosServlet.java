package org.iesvegademijas.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.iesvegademijas.dao.UsuarioDAO;
import org.iesvegademijas.dao.UsuarioDAOImpl;
import org.iesvegademijas.model.Usuario;

public class UsuariosServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * HTTP Method: GET Paths: /usuarios/ /usuarios/{id} /usuarios/editar/{id}
	 * /usuarios/crear
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = null;

		String pathInfo = request.getPathInfo();

		if (pathInfo == null || "/".equals(pathInfo)) {
			UsuarioDAO usrDAO = new UsuarioDAOImpl();

			// GET
			// /usuarios/
			// /usuarios

			request.setAttribute("listaUsuarios", usrDAO.getAll());
			dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios.jsp");
		} else {

			// GET
			// /usuarios/{id}
			// /usuarios/{id}/
			// /usuarios/edit/{id}
			// /usuarios/edit/{id}/
			// /usuarios/create
			// /usuarios/create/

			pathInfo = pathInfo.replaceAll("/$", "");
			String[] pathParts = pathInfo.split("/");

			if (pathParts.length == 2 && "crear".equals(pathParts[1])) {

				// GET
				// /usuarios/create
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/crear-usuario.jsp");

			} else if (pathParts.length == 2) {
				UsuarioDAO usrDAO = new UsuarioDAOImpl();
				// GET
				// /usuarios/{id}
				try {
					request.setAttribute("usuario", usrDAO.find(Integer.parseInt(pathParts[1])));
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/detalle-usuario.jsp");

				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios.jsp");
				}

			} else if (pathParts.length == 3 && "editar".equals(pathParts[1])) {
				UsuarioDAO usrDAO = new UsuarioDAOImpl();

				// GET
				// /usuarios/edit/{id}
				try {
					request.setAttribute("usuario", usrDAO.find(Integer.parseInt(pathParts[2])));
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/editar-usuario.jsp");

				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios.jsp");
				}

			} else {

				System.out.println("Opción POST no soportada.");
				dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios.jsp");

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
			UsuarioDAO usrDAO = new UsuarioDAOImpl();

			String nombre = request.getParameter("nombre");
			String password = request.getParameter("password");
			String rol = request.getParameter("rol");
			Usuario nuevoUsr = new Usuario();
			nuevoUsr.setNombre(nombre);
			nuevoUsr.setPassword(password);
			nuevoUsr.setRol(rol);
			
			usrDAO.create(nuevoUsr);

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

		response.sendRedirect("/tienda_informatica/usuarios");
		// response.sendRedirect("/tienda_informatica/usuarios");

	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UsuarioDAO usrDAO = new UsuarioDAOImpl();
		String codigo = request.getParameter("id");
		String nombre = request.getParameter("nombre");
		String password = request.getParameter("password");
		String rol = request.getParameter("rol");
		Usuario usr = new Usuario();

		try {

			int id = Integer.parseInt(codigo);
			usr.setId(id);
			usr.setNombre(nombre);
			usr.setPassword(password);
			usr.setRol(rol);
			usrDAO.update(usr);

		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}

	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher;
		UsuarioDAO usrDAO = new UsuarioDAOImpl();
		String codigo = request.getParameter("id");

		try {

			int id = Integer.parseInt(codigo);

			usrDAO.delete(id);

		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}

	}

}