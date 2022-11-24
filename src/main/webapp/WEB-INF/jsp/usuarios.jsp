<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.Usuario"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Usuarios</title>
<style>
.clearfix::after {
	content: "";
	display: block;
	clear: both;
}
</style>

<%@ include file="style.jspf"%>

</head>
<body>
<body>

	<%@ include file="header.jspf"%>

	<%@ include file="nav.jspf"%>

	<main>
		<div id="contenedora" style="float: none; margin: 0 auto; width: 900px;">
			<div style="float: left; width: 50%">
					<a href="http://localhost:8080/tienda_informatica/usuarios">
						<h1>Usuarios</h1>
					</a>
				</div>
				<div
					style="float: none; width: auto; overflow: hidden; min-height: 80px; position: relative;">

					<div style="position: absolute; left: 70%; top: 39%;">
						<form action="/tienda_informatica/usuarios/crear">
							<input style="background-color: lightgreen; border-radius: 5px"
								type="submit" value="Crear">
						</form>
					</div>

				</div>
			
			<div class="clearfix">
				<hr />
			</div>
			<div class="clearfix">
				<div style="float: left; width: 5%">ID</div>
				<div style="float: left; width: 20%">Nombre de usuario</div>
				<div style="float: left; width: 37%">Contraseña hasheada</div>
				<div style="float: left; width: 13%">Rol</div>
				<div style="float: none; width: auto; overflow: hidden;">Acción</div>
			</div>
			<div class="clearfix">
				<hr />
			</div>
			<%
			if (request.getAttribute("listaUsuarios") != null) {
				List<Usuario> listaUsuario = (List<Usuario>) request.getAttribute("listaUsuarios");

				for (Usuario usuario : listaUsuario) {
			%>

			<div style="margin-top: 6px;" class="clearfix">
				<div style="float: left; width: 5%"><%=usuario.getId()%></div>
				<div style="float: left; width: 20%"><%=usuario.getNombre()%></div>
				<div style="float: left; width: 37%"><%=usuario.getPassword()%></div>
				<div style="float: left; width: 13%"><%=usuario.getRol()%></div>
				<div style="float: none; width: auto; overflow: hidden;">
					<form
						action="/tienda_informatica/usuarios/<%=usuario.getId()%>"
						style="display: inline;">
						<input type="submit" value="Ver Detalle" />
					</form>
					<form
						action="/tienda_informatica/usuarios/editar/<%=usuario.getId()%>"
						style="display: inline;">
						<input type="submit" value="Editar" />
					</form>
					<form action="/tienda_informatica/usuarios/borrar/" method="post"
						style="display: inline;">
						<input type="hidden" name="__method__" value="delete" /> <input
							type="hidden" name="id" value="<%=usuario.getId()%>" />
						<input type="submit" value="Eliminar" />
					</form>
				</div>
			</div>

			<%
			}
			} else {
			%>
			<style>
p {
	background-color: red;
	border: 1px solid black;
	width: fit-content;
	padding: 5px;
	border-radius: 5px;
	color: white;
}
</style>

			<p>No hay registros de usuario</p>
			<%
			}
			%>
		</div>
	</main>
</body>
</body>

<%@ include file="footer.jspf"%>

</html>