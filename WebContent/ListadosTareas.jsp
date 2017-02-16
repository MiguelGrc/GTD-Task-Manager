<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Listado de categorías</title>
</head>
<body>
	<ul>
	<li><a id="listado_inbox" href="listadoInbox">Inbox</a></li>
	<li><a id="listado_Hoy" href="listadoHoy">Hoy</a></li>
	<li>Categorias
		<ul>
		<c:forEach var="entry" items="${listaCategorias}" varStatus="i">
			<>
				<td><a href="mostrarCategoria?id=${entry.id}">${entry.id}</a></td>
				<td>${entry.name}</td>
			</tr>
		</c:forEach>
		
		</ul>
	
	</li>
	</ul>
	<%@ include file="pieDePagina.jsp" %>
</body>
</html>