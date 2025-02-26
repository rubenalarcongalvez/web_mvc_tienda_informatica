<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.Usuario"%>
<%@page import="java.util.Optional"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Usuario</title>
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
			<form action="/tienda_informatica/usuarios/login" method="post">
				<div class="clearfix">
					<div style="float: left; width: 50%">
						<h1>Login</h1>
					</div>
					<div
						style="float: none; width: auto; overflow: hidden; min-height: 80px; position: relative;">

						<div style="position: absolute; left: 39%; top: 39%;">
							<input type="submit" value="Loguearse" />
						</div>

					</div>
				</div>

				<div class="clearfix">
					<hr />
				</div>

				<div style="margin-top: 6px;" class="clearfix">
					<div style="float: left; width: 50%">Nombre de usuario</div>
					<div style="float: none; width: auto; overflow: hidden; margin-bottom: 5px;">
						<input name="nombre" />
					</div>
					
					<div style="float: left; width: 50%">Password</div>
					<div style="float: none; width: auto; overflow: hidden; margin-bottom: 5px;">
						<input name="password"/>
					</div>
				</div>

			</form>
		</div>
	</main>
	<%@ include file="footer.jspf"%>

</body>
</html>