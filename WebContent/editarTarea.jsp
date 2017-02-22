<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.uniovi.es/sdi" prefix="red"%>
<%@ include file="comprobarNavegacion.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
<head>
<title>TaskManager - Editar tarea</title>
<body>
	<div class="container">
		<%@ include file="cerrarSesion.jsp"%>
		<form action="editarTarea" method="post" name="crearTarea_form_name"
			id="crearTarea_form">

			<center>
				<h1>Editar tarea</h1>
			</center>
			<hr>
			<%@ include file="pieDePagina.jsp"%>
			<table align="center">
				<tr>
					<td><div class="form-group">
							<label for="titulo">Título <span style="color: red">*</span></label>
							<input type="text" name="tituloTarea" class="form-control"
								id="titulo" value="${tareaSeleccionada.title}" size="30" />
						</div></td>
				</tr>
				<tr>
					<td><div class="form-group">
							<label for="fecha">Fecha planeada</label> <input type="text"
								class="form-control" name="fechaPlaneadaTarea" id="fecha"
								placeholder="dd/mm/yyyy" size="30"
								value="<red:formatdate fecha="${tareaSeleccionada.planned}"/>" />
						</div></td>
				</tr>
				<tr>
					<td><div class="form-group">
							<label for="fecha">Categoría</label> <select name=categoria
								class="form-control" from="crearTarea_form">
								<option value="" selected>&lt &gt</option>
								<c:forEach var="categoria" items="${categoriasDesplegable}"
									varStatus="i">
									<option
										<c:if test="${ultimaLista=='Categoria' and categoria.id==categoriaSeleccionada}">
								selected</c:if>
										value="${categoria.id}">${categoria.name}</option>
								</c:forEach>
							</select>
						</div></td>
				</tr>
				<tr>
					<td><div class="form-group">
							<label for="comentario">Comentario</label>
							<textarea class="form-control" name="comentarioTarea" rows="5"
								id="comentario" size="30">${tareaSeleccionada.comments}</textarea>
						</div></td>
				</tr>
				<tr>
					<td><input type="submit" class="btn btn-primary"
						value="Editar tarea" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>