<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.Usuario"%>
<%@page import="java.util.Optional"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Detalle Usuario</title>
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
			<div class="clearfix">
				<div style="float: left; width: 50%">
					<h1>Detalle Usuario</h1>
				</div>
				<div
					style="float: none; width: auto; overflow: hidden; min-height: 80px; position: relative;">

					<div style="position: absolute; left: 39%; top: 39%;">

						<form action="/tienda_informatica/usuarios">
							<input type="submit" value="Volver" />
						</form>
					</div>

				</div>
			</div>

			<div class="clearfix">
				<hr />
			</div>

			<%
			Optional<Usuario> optUsr = (Optional<Usuario>) request.getAttribute("usuario");
			if (optUsr.isPresent()) {
			%>

			<div style="margin-top: 6px;" class="clearfix">
				<div style="float: left; width: 50%">
					<label>ID</label>
				</div>
				<div style="float: none; width: auto; overflow: hidden;">
					<input style="border: none" value="<%=optUsr.get().getId()%>"
						readonly="readonly" />
				</div>
			</div>
			<div style="margin-top: 6px;" class="clearfix">
				<div style="float: left; width: 50%">
					<label>Nombre de usuario</label>
				</div>
				<div style="float: none; width: auto; overflow: hidden;">
					<input style="border: none" value="<%=optUsr.get().getNombre()%>"
						readonly="readonly" />
				</div>
			</div>
			<div style="margin-top: 6px;" class="clearfix">
				<div style="float: left; width: 50%">
					<label>Password</label>
				</div>
				<div style="float: none; width: auto; overflow: hidden;">
					<input style="border: none" value="<%=optUsr.get().getPassword()%>"
						readonly="readonly" />
				</div>
			</div>
			<div style="margin-top: 6px;" class="clearfix">
				<div style="float: left; width: 50%">
					<label>Rol</label>
				</div>
				<div style="float: none; width: auto; overflow: hidden;">
					<input style="border: none" value="<%=optUsr.get().getRol()%>"
						readonly="readonly" />
				</div>
			</div>

			<%
			} else {
			%>

			request.sendRedirect("usuarios/");

			<%
			}
			%>

		</div>

	</main>

	<%@ include file="footer.jspf"%>

</body>
</html>