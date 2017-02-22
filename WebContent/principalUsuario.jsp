<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
<head>
<title>TaskManager - Página principal del usuario</title>
</head>
<body>
	<div class="container">
		<%@ include file="cerrarSesion.jsp"%>
		<center>
			<h1>Página personal</h1>
		</center>
		<hr>
		<br>
		<%@ include file="pieDePagina.jsp"%>
		<center>
			<i>Iniciaste sesión el <fmt:formatDate
					pattern="dd-MM-yyyy' a las 'HH:mm"
					value="${sessionScope.fechaInicioSesion}" /> (usuario número
				${contador})
			</i>
		</center>
		<br /> <br />
		<jsp:useBean id="user" class="uo.sdi.dto.User" scope="session" />
		<table align="center">
			<tr>
				<td id="email"><form action="modificarEmail" method="POST">
						<div class="form-group">
							<label for="email">Email</label>
							<div class="form-inline">
								<input type="text" name="email" class="form-control" id="email"
									value="<jsp:getProperty property="email" name="user"/>"
									size="20"> <input id="cambiar_email" type="submit"
									class="form-group-btn btn btn-success" value="Modificar">
							</div>
						</div>
					</form></td>
			</tr>
			<tr>
				<td id="pass"><form action="modificarContrasena" method="POST">
						<div class="form-group">
							<label for="passAntigua">Contraseña</label> <input
								type="password" class="form-control" name="passAntigua"
								size="20" id="pass" placeholder="Contraseña actual">
						</div>
						<div class="form-group">
							<input type="password" class="form-control" name="passOne"
								size="20" placeholder="Nueva contraseña">
						</div>
						<div class="form-inline">
							<input type="password" class="form-control" name="passTwo"
								size="20" placeholder="Repita nueva contraseña"> <input
								id="cambiar_pass" type="submit"
								class="form-group-btn btn btn-success" value="Modificar">
						</div>
					</form>
					<hr></td>
			</tr>
			<tr>
				<td><label>Id:</label> <jsp:getProperty property="id"
						name="user" /></td>
			</tr>
			<tr>
				<td><label>Es administrador:</label> <jsp:getProperty
						property="isAdmin" name="user" /></td>
			</tr>
			<tr>
				<td><label>Login:</label> <jsp:getProperty property="login"
						name="user" /></td>
			</tr>
			<tr>
				<td><label>Estado:</label> <jsp:getProperty property="status"
						name="user" /></td>
			</tr>

		</table>
		<hr />
		<c:if test="${user.isAdmin}">
			<center>
				<a id="mostrar_lista_usuarios" href="listarUsuarios"
					class="btn btn-warning"> Administrar a los usuarios</a>
			</center>
		</c:if>
		<c:if test="${not user.isAdmin}">
			<center>
				<a id="mostrar_listados" href="mostrarListados"
					class="btn btn-primary"> Ver tareas</a>
			</center>
		</c:if>

	</div>
</body>
</html>
