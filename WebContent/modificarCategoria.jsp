<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
<head>
	<title>TaskManager - Modificar categoria</title>
<body>
	<form action="modificarCategoria" method="post" name="crearCategoria_form_name">

		<center>
			<h1>Modificar Categoria</h1>
		</center>
		<hr>
		<br>
		<table align="center">
			<tr>
				<td><div class="form-group">
						<label for="titulo">Nombre <span style="color: red">*</span></label>
						<input type="text" name="nombreCategoria" class="form-control"
							id="titulo" placeholder="Nombre de la tarea" size="30" />
					</div></td>
			</tr>
			<tr>
				<td><input type="submit" class="btn btn-success"
					value="Modificar categoria" /></td>
			</tr>
		</table>
	</form>
	<%@ include file="pieDePagina.jsp"%>
</body>
</html>