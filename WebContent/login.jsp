<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
<head>
<title>TaskManager - Inicie sesión</title>
<body>
	<div class="container">
		<form action="validarse" method="post" name="validarse_form_name">

			<center>
				<h1>Inicie sesión</h1>
			</center>
			<hr>
			<br>
			<div class="form-group">
				<label for="username">Usuario:</label> <input type="text"
					name="nombreUsuario" class="form-control" id="username"
					placeholder="Introduzca su usuario" align="left" size="15">
			</div>
			<div class="form-group">
				<label for="pwd">Contraseña:</label> <input type="password"
					name="passUsuario" class="form-control" id="pwd"
					placeholder="Introduzca su contraseña" align="left">
			</div>
			<button type="submit" class="btn btn-default">Enviar</button>
		</form>
	</div>
	<a id="registro_link_id" href="registro.jsp">Registrarse!</a>
	<br>
	<br>
	<a id="listarCategorias_link_id" href="listarCategorias">Lista de
		categorias</a>
	<%@ include file="pieDePagina.jsp"%>
</body>
</html>