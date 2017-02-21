<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.uniovi.es/sdi" prefix="red"%>
<%@ include file="comprobarNavegacion.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
<head>
<script>
	function confirmComplete() {
		return confirm("¿Estás seguro de que deseas eliminar esta categoría?"
				+ "\n\nSe borrarán todas sus tareas."
				+ "\n\n¡La operación será irreversible!\n");
	}
</script>
<title>TaskManager - Listados</title>
</head>
<body style="padding-bottom: 70px">
<div class="container">
	<%@ include file="cerrarSesion.jsp"%>
	<center>
		<h1>Listado de tareas</h1>
	</center>
	<hr>
	<%@ include file="pieDePagina.jsp" %>
	<div class="row">
	<div class="col-sm-2">
	<ul class="nav nav-pills nav-stacked">
	<li><b>Pseudocategorías:</b><br><br></li>
	<li><a id="listado_inbox" href="listarTareasInbox">Inbox</a></li>
	<li><a id="listado_Hoy" href="listarTareasHoy">Hoy</a></li>
	<li><hr><b>Categorías de usuario:</b><br><br>
		<ul class="nav nav-pills nav-stacked">
		<c:forEach var="entry" items="${listaCategorias}" varStatus="i">
			<li><a href="listarTareasCategoria?id=${entry.id}">${entry.name}</a></li>
		</c:forEach>
		</ul>
	</ul>
	</div>
	<div class="col-sm-10">
	<c:choose><c:when test="${listaMostrar!=null}">
	<form action="eliminarCategoria" method="post"
			name="eliminarCategoria_form_name">
		<table class="table table-bordered table-hover">
		
				<tr>
					<th />
					<th>Title</th>
					<th>Created</th>
					<th>Finished</th>
					<th>Planned</th>
					<th>Comments</th>
				</tr>
				
			<c:forEach var="task" items="${listaMostrar}" varStatus="i">
				<tr id="task_${task.id}">
					<td><label class="btn btn-default">
						<input type="radio" name="tareaId"
						 value="${task.id}" /></label>
					<td>${task.title}</td>
					<td>${task.created}</td>
					<c:choose>
						<c:when test="task.finished==null">
							<td>     </td>
						</c:when>
						<c:otherwise>
							<td>${task.finished}</td>
							
						</c:otherwise>
					</c:choose>
					<!-- Este tag prepara color en rojo y prepara celda también -->
					<red:planeado fecha="${task.planned}"/>

					<td>${task.comments}</td>
				</tr>
			</c:forEach>
				
		</table>
		<nav class="navbar navbar-inverse navbar-fixed-bottom">
			<div class="container"><center>
				<a class="btn btn-default navbar-btn" href="principalUsuario.jsp">Volver atrás</a> 
				<c:if test="${ultimaLista!='Hoy'}">
				<a href="cambiarFinalizadas" class="btn btn-info navbar-btn">
					<c:if test="${mostrarFinalizadas}">
						No mostrar finalizadas
					</c:if>
					<c:if test="${not mostrarFinalizadas}">
						Mostrar finalizadas
					</c:if>
				</a>
				</c:if>
				<div class="btn-group">&nbsp | &nbsp</div>
				<a href="crearTarea.jsp" class="btn btn-success navbar-btn">Crear tareaaaaaa</a>
				<button type="submit" class="btn btn-warning"
					onclick="form.action='prepararEdicionTarea'">Editar tarea</button>
				<button type="submit" class="btn btn-primary navbar-btn"
					onclick="form.action='marcarFinalizada'">Finalizar tarea</button>
				<div class="btn-group">&nbsp | &nbsp</div>
				<button type="submit" class ="btn btn-success navbar-btn" onclick="form.action='crearCategoria.jsp'">Crear categoría</button>
				<button type="submit" class ="btn btn-warning navbar-btn" onclick="form.action='menuModificarCategoria'">Modificar categoría</button>
				<button type="submit" class = "btn btn-danger navbar-btn" onclick="confirmComplete();">Eliminar categoría</button>
			</center></div>
		</nav>
	</form>
	</c:when>
	<c:otherwise>
		<form action="crearCategoria.jsp">
			<nav class="navbar navbar-inverse navbar-fixed-bottom">
				<div class="container"><center>
					<a class="btn btn-default navbar-btn" href="principalUsuario.jsp">Volver atrás</a> 
					<div class="btn-group">&nbsp | &nbsp</div>
					<button type="submit" class ="btn btn-success navbar-btn">Crear categoría</button>
				</center></div>
			</nav>
		</form>
	</c:otherwise>
	</c:choose>
	
	</div>
	</div>
	
</div>
</body>
</html>