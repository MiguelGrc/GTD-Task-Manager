<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
<head>
	<title>TaskManager - Crear tarea</title>
<body>
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
			<tr>
				<td><div class="form-group">
						<label for="fecha">Fecha planeada</label> <input type="date"
							class="form-control" name="fechaPlaneadaTarea" value="yyyy-mm-dd"
							id="fecha" />
					</div></td>
			</tr>
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
</body>
</html>