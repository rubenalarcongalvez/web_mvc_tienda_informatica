<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.iesvegademijas.model.Producto"%>
<%@page import="java.util.Optional"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Detalle Producto</title>
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

	<main>

		<%@ include file="header.jspf"%>

		<%@ include file="nav.jspf"%>

		<div id="contenedora"
			style="float: none; margin: 0 auto; width: 900px;">
			<div class="clearfix">
				<div style="float: left; width: 50%">
					<h1>Detalle Producto</h1>
				</div>
				<div
					style="float: none; width: auto; overflow: hidden; min-height: 80px; position: relative;">

					<div style="position: absolute; left: 39%; top: 39%;">

						<form action="/tienda_informatica/productos">
							<input type="submit" value="Volver" />
						</form>
					</div>

				</div>
			</div>

			<div class="clearfix">
				<hr />
			</div>

			<%
			Optional<Producto> optProd = (Optional<Producto>) request.getAttribute("producto");
			if (optProd.isPresent()) {
			%>

			<div style="margin-top: 6px;" class="clearfix">
				<div style="float: left; width: 50%">
					<label>Código</label>
				</div>
				<div style="float: none; width: auto; overflow: hidden;">
					<input style="border: none;"
						value="<%=optProd.get().getCodigo()%>" readonly="readonly" />
				</div>
			</div>
			<div style="margin-top: 6px;" class="clearfix">
				<div style="float: left; width: 50%">
					<label>Nombre</label>
				</div>
				<div style="float: none; width: auto; overflow: hidden;">
					<input value="<%=optProd.get().getNombre()%>" readonly="readonly" />
				</div>
			</div>
			<div style="margin-top: 6px;" class="clearfix">
				<div style="float: left; width: 50%">
					<label>Precio</label>
				</div>
				<div style="float: none; width: auto; overflow: hidden;">
					<input value="<%=optProd.get().getPrecio()%>" readonly="readonly" />
				</div>
			</div>

			<%
			} else {
			%>

			request.sendRedirect("productos/");

			<%
			}
			%>

		</div>

	</main>

	<%@ include file="footer.jspf"%>

</body>
</html>