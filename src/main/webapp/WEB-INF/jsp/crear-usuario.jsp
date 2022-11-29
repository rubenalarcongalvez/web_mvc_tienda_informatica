<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.Usuario"%>
<%@page import="java.util.Optional"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Crear Usuario</title>
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

	<%@ include file="header.jspf"%>

	<%@ include file="nav.jspf"%>
	<main>
		<div id="contenedora"
			style="float: none; margin: 0 auto; width: 900px;">
			<form action="/tienda_informatica/usuarios/crear/" method="post">
				<div class="clearfix">
					<div style="float: left; width: 50%">
						<h1>Crear Usuario</h1>
					</div>
					<div
						style="float: none; width: auto; overflow: hidden; min-height: 80px; position: relative;">

						<div style="position: absolute; left: 39%; top: 39%;">
							<input type="submit" value="Crear" />
						</div>

					</div>
				</div>

				<div class="clearfix">
					<hr />
				</div>

				<div style="margin-top: 6px;" class="clearfix">
					<div style="float: left; width: 50%">Nombre</div>
					<div style="float: none; width: auto; overflow: hidden; margin-bottom: 5px;">
						<input name="nombre" />
					</div>
					
					<div style="float: left; width: 50%">Password</div>
					<div style="float: none; width: auto; overflow: hidden; margin-bottom: 5px;">
						<input name="password" />
					</div>
					
					<div style="float: left; width: 50%">Rol</div>
					<div style="float: none; width: auto; overflow: hidden; margin-bottom: 5px;">
						<select name="rol">
							<option value="cliente" selected>cliente</option>
							<option value="vendedor">vendedor</option>
							<option value="administrador">administrador</option>
						</select>
					</div>
				</div>

			</form>
		</div>
	</main>
	<%@ include file="footer.jspf"%>

</body>
</html>