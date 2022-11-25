<%@page import="org.iesvegademijas.dao.FabricanteDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.Fabricante"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Fabricantes</title>
<style>
.clearfix::after {
	content: "";
	display: block;
	clear: both;
}
</style>

<%@ include file="style.jspf"%>

</head>

<%@ include file="header.jspf"%>

<%@ include file="nav.jspf"%>

<main>
	<div id="contenedora"
		style="float: none; margin: 0 auto; width: 900px;">
		<div class="clearfix">
			<div style="float: left; width: 50%">
				<a href="http://localhost:8080/tienda_informatica/fabricantes">
					<h1>Fabricantes</h1>
				</a>
			</div>
			<div
				style="float: none; width: auto; overflow: hidden; min-height: 80px; position: relative;">

				<div style="position: absolute; top: 40%;">
					<form method="GET" action="/tienda_informatica/fabricantes">
						<span>Ordenar por: </span> <select name="ordenar-por">
							<%
							if ("codigo".equals(request.getParameter("ordenar-por")) || null == request.getParameter("ordenar-por")) {
							%>
							<option value="codigo" selected>Código</option>
							<option value="nombre">Nombre</option>
							<%
							} else {
							%>
							<option value="codigo">Código</option>
							<option value="nombre" selected>Nombre</option>
							<%
							}
							%>
						</select> <select name="modo-ordenar">
							<%
							if ("asc".equals(request.getParameter("modo-ordenar")) || null == request.getParameter("modo-ordenar")) {
							%>
							<option value="asc" selected>Asc</option>
							<option value="desc">Desc</option>
							<%
							} else {
							%>
							<option value="asc">Asc</option>
							<option value="desc" selected>Desc</option>
							<%
							}
							%>
						</select> <input type="submit" value="Ordenar"
							style="background-color: lightblue; border-radius: 5px;">
					</form>
				</div>

				<%
				Usuario user = null; //como este ya está creado, no hace falta crearlo de nuevo para otros filtros

				if (session != null //Seteo inline de usuario
						&& (user = (Usuario) session.getAttribute("usuario-logueado")) != null && "administrador".equals(user.getRol())) {
				%>

				<div style="position: absolute; left: 80%; top: 40%;">

					<form action="/tienda_informatica/fabricantes/crear">
						<input type="submit" value="Crear"
							style="background-color: lightgreen; border-radius: 5px;">
					</form>
				</div>

				<%
				}
				%>

			</div>
		</div>
		<div class="clearfix">
			<hr />
		</div>
		<div class="clearfix">
			<div style="float: left; width: 25%">Código</div>
			<div style="float: left; width: 25%">Nombre</div>
			<div style="float: left; width: 25%">Nº productos</div>
			<div style="float: none; width: auto; overflow: hidden;">Acción</div>
		</div>
		<div class="clearfix">
			<hr />
		</div>
		<%
		if (request.getAttribute("listaFabricantes") != null) {
			List<FabricanteDTO> listaFabricante = (List<FabricanteDTO>) request.getAttribute("listaFabricantes");

			for (FabricanteDTO fabricante : listaFabricante) {
		%>

		<div style="margin-top: 6px;" class="clearfix">
			<div style="float: left; width: 25%"><%=fabricante.getCodigo()%></div>
			<div style="float: left; width: 25%"><%=fabricante.getNombre()%></div>
			<div style="float: left; width: 25%"><%=fabricante.getNumProductos()%></div>
			<div style="float: none; width: auto; overflow: hidden;">
				<form
					action="/tienda_informatica/fabricantes/<%=fabricante.getCodigo()%>"
					style="display: inline;">
					<input type="submit" value="Ver Detalle" />
				</form>
				
				<%

				if (session != null //Seteo inline de usuario
						&& (user = (Usuario) session.getAttribute("usuario-logueado")) != null && "administrador".equals(user.getRol())) {
				%>

				<form
					action="/tienda_informatica/fabricantes/editar/<%=fabricante.getCodigo()%>"
					style="display: inline;">
					<input type="submit" value="Editar" />
				</form>
				<form action="/tienda_informatica/fabricantes/borrar/" method="post"
					style="display: inline;">
					<input type="hidden" name="__method__" value="delete" /> <input
						type="hidden" name="codigo" value="<%=fabricante.getCodigo()%>" />
					<input type="submit" value="Eliminar" />
				</form>

				<%
				}
				%>
				
			</div>
		</div>
		<%
		}
		} else {
		%>
		No hay registros de fabricante
		<%
		}
		%>
	</div>
</main>
<%@ include file="footer.jspf"%>
</body>
</html>