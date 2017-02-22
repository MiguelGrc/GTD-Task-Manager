<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>

<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script>
	function confirmComplete() {
		var answer = confirm("¿Estás seguro de que deseas eliminar a este usuario?"
				+ "\n\n¡La operación será irreversible!\n");
		var ret = (answer == true) ? true : false;
		return ret;
	}
</script>
<title>TaskManager - Listado de usuarios</title>
</head>
<body>
	<div class="container">
		<%@ include file="cerrarSesion.jsp"%>
		<!-- Se muestra toda la información menos la contraseña -->
		<form action="borrarUsuario" method="post"
			name="borrarUsuario_form_name">
			<center>
				<h1>Administrar usuarios</h1>
			</center>
			<hr>
			<%@ include file="pieDePagina.jsp"%>
			<table id="tabla_usuarios" class="table table-hover">
				<tr>
					<th />
					<th>ID</th>
					<th>Login</th>
					<th>Email</th>
					<th>Administrador</th>
					<th>Estado</th>
				</tr>
				<c:forEach var="usuario" items="${listaUsuarios}" varStatus="i">
					<tr id="usuario_${i.index}">
						<td><input type="radio" name="usuarioId"
							value="${usuario.id}" /></td>
						<td>${usuario.id}</td>
						<td>${usuario.login}</td>
						<td>${usuario.email}</td>
						<td>${usuario.isAdmin}</td>
						<td>${usuario.status}</td>
					</tr>
				</c:forEach>
			</table>
			<div class="btn-group">
				<button type="button" class="btn btn-default dropdown-toggle"
					data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					Ordenar <span class="caret"></span>
				</button>
				<ul class="dropdown-menu">
					<li><a href="ordenarUsuarios?modo=login">Por login</a></li>
					<li><a href="ordenarUsuarios?modo=email">Por email</a></li>
					<li><a href="ordenarUsuarios?modo=status">Por status</a></li>
				</ul>
			</div>
			<button id="borrar_usuario" type="submit" class="btn btn-danger"
				onclick="return confirmComplete();">Borrar usuario</button>
			<button id="cambiar_estado" type="submit" class="btn btn-warning"
				onclick="form.action='cambiarEstadoUsuario';">Cambiar
				estado</button>


			<a href="principalUsuario.jsp" class="btn btn-primary">CLICA</a>
		</form>
	</div>

</body>
</html>