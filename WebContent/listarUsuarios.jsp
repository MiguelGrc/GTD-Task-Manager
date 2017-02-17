<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<script>
function confirmComplete() {
	var answer=confirm(`¿Estás seguro de que deseas eliminar a este usuario?
\n¡La operación será irreversible!\n`);
	var ret = (answer==true) ? true : false;
	return ret;
}
</script>
<title>TaskManager - Listado de categorías</title>
</head>
<body>
	
	<!-- Se muestra toda la información menos la contraseña -->
	<form action="borrarUsuario" method="post" name="borrarUsuario_form_name">
		<table align="center">
			<tr>
				<th/>
				<th>ID</th>
				<th>Login</th>
				<th>Email</th>
				<th>Administrador</th>
				<th>Estado</th>
			</tr>
				<c:forEach var="usuario" items="${listaUsuarios}" varStatus="i">
					<tr id="usuario_${i.index}">
						<td><input type="radio" name="usuarioId" value="${usuario.id}"/></td>
						<td>${usuario.id}</td>
						<td>${usuario.login}</td>
						<td>${usuario.email} </td>
						<td>${usuario.isAdmin} </td>
						<td>${usuario.status} </td>
					</tr>
				</c:forEach>
				<tfoot>
				<tr>
					<td><input type="submit" value="Borrar usuario" onclick="{return confirmComplete();}"/></td>
					<td><input type="submit" value="Cambiar estado" onclick="form.action='cambiarEstadoUsuario';"/></td>
				</tr>
				</tfoot>
		</table>
	</form>
		
	<%@ include file="pieDePagina.jsp" %>
</body>
</html>