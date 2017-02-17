<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Listado de categorías</title>
</head>
<body>
	
	<!-- Se muestra toda la información menos la contraseña -->
	<form action="borrarUsuario" method="post" name="borrarUsuario_form_name">
		<table border="1" align="center">
			<tr>
				<th/>
				<th>ID</th>
				<th>Login</th>
				<th>Email</th>
				<th>Es Administrador</th>
				<th>Estatus</th>
			</tr>
				<c:forEach var="usuario" items="${listaUsuarios}" varStatus="i">
					<tr id="usuario_${i.index}">
						<td><input type="radio" name="usuarioABorrar" value="${usuario.id}"/></td>
						<td><a href="mostrarUsuario?id=${usuario.id}">${usuario.id}</a></td>
						<td>${usuario.login}</td>
						<td>${usuario.email} </td>
						<td>${usuario.isAdmin} </td>
						<td>${usuario.status} </td>
					</tr>
				</c:forEach>
		</table>
		<input type="submit" value="Borrar usuario"/>
	</form>
	<%@ include file="pieDePagina.jsp" %>
</body>
</html>