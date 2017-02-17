<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Listado de tareas hoy</title>
</head>
<body>
	<table border="1" align="center">
			<tr>
				<th>ID</th>
				<th>Título</th>
				<th>Planeado para...</th>
			</tr>
		<c:forEach var="task" items="${listaHoy}" varStatus="i">
			<tr id="item_${i.index}">
				<td><a href="mostrarCategoria?id=${task.id}">${task.id}</a></td>
				<td>${task.title}</td>
				<td>${task.planned} }</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="pieDePagina.jsp" %>
</body>
</html>