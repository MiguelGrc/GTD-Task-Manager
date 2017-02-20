<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
<head>
<title>TaskManager - Inicie sesión</title>
</head>
<body>
<div class="container">
	<center>
		<h1>Inicie sesión</h1>
	</center>
	<hr>
	<br>
	<%@ include file="pieDePagina.jsp"%>
	<form action="validarse" method="post" name="validarse_form_name">
		<table align="center">
			<tr>
				<td><div class="form-group">
				<label for="username">Usuario:</label> <input type="text"
					name="nombreUsuario" class="form-control" id="username"
					placeholder="Introduzca su usuario" align="left" size="40">
				</div></td>
			</tr>
			<tr>
				<td><div class="form-group">
				<label for="pwd">Contraseña:</label> <input type="password"
					name="passUsuario" class="form-control" id="pwd"
					placeholder="Introduzca su contraseña" align="left" size="40">
				</div></td>
			</tr>
			<tr>
				<td><button type="submit" class="btn btn-success">Enviar</button>
				<a id="registro_link_id" class="btn btn-link" href="registro.jsp">Registrarse!</a></td>
			</tr>
		</table>
	</form>
</div>	
</body>
</html>