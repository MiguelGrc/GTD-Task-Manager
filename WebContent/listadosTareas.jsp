<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.uniovi.es/sdi" prefix="red"%>
<%@ include file="comprobarNavegacion.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Listados</title>
</head>
<body>
	<ul>
	<li><a id="listado_inbox" href="listarTareasInbox">Inbox</a></li>
	<li><a id="listado_Hoy" href="listarTareasHoy">Hoy</a></li>
	<li>Categorias
		<ul>
		<c:forEach var="entry" items="${listaCategorias}" varStatus="i">
			<li><a href="listarTareasCategoria?id=${entry.id}">${entry.name}</a></li>
		</c:forEach>
		
		</ul>
	
	</li>
	</ul>
	
	
	<c:if test="${listaMostrar!=null}">
		<table border="1" align="center">
				<tr>
					<th>Title</th>
					<th>Created</th>
					<th>Finished</th>
					<th>Planned</th>
					<th>Comments</th>
				</tr>
			<c:forEach var="task" items="${listaMostrar}" varStatus="i">
				<tr id="task_${task.id}">
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
	</c:if>
	<%@ include file="pieDePagina.jsp" %>
</body>
</html>