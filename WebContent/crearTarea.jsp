<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.min.css" />
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css" />
<script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.min.js"></script>

	<title>TaskManager - Crear tarea</title>
<body>
<div class="container">
	<form action="crearTarea" method="post" name="crearTarea_form_name">

		<center>
			<h1>Crear tarea</h1>
		</center>
		<hr>
		<br>
		<table align="center">
			<tr>
				<td><div class="form-group">
						<label for="titulo">Título <span style="color: red">*</span></label>
						<input type="text" name="tituloTarea" class="form-control"
							id="titulo" placeholder="Título de la tarea" size="30" />
					</div></td>
			</tr>
			<c:if test="${ultimaLista!='Hoy'}">
			<tr>
				<td><div class="form-group">
						<label for="fecha">Fecha planeada</label> <input type="text"
							class="form-control" id="fecha" name="fechaPlaneadaTarea"
							placeholder="dd/mm/yyyy" />
					</div></td>
			</tr>
			</c:if>
			<tr>
				<td><div class="form-group">
						<label for="titulo">Comentario</label>
						<textarea class="form-control" name="comentarioTarea" rows="3"
							id="titulo" placeholder="Comentarios sobre la tarea" size="30"></textarea>
					</div></td>
			</tr>
			<tr>
				<td><input type="submit" class="btn btn-success"
					value="Crear tarea" /></td>
			</tr>
		</table>
	</form>
	<%@ include file="pieDePagina.jsp"%>
</div>
</body>
</html>