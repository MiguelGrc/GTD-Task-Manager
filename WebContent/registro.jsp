<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
<head> 
<title>TaskManager - Registro</title>
</head>
<body>
<div class="container">
  <form action="crearCuenta" method="post" name="registrarse_form_name">

 	<center><h1>Regístrese</h1></center>
 	<hr><br>
 	<table align="center">
    	<tr> 
    		<td><div class="form-group">
				<label for="username">Usuario</label> <input type="text"
					name="nombreUsuario" class="form-control" id="username"
					placeholder="Introduzca su usuario" size="30" />
			</div></td>
      	</tr>
      	<tr> 
      		<td><div class="form-group">
				<label for="email">Email</label> <input type="text"
					name="email" class="form-control" id="email"
					placeholder="Introduzca su email" size="30" />
			</div></td>
      	</tr>
      	<tr> 
      		<td><div class="form-group">
				<label for="password1">Contraseña</label> <input type="password"
					name="password1" class="form-control" id="password1"
					placeholder="Introduzca su contraseña" size="30" />
			</div></td>
      	</tr>
      	<tr> 
      		<td><div class="form-group">
				 <input type="password" name="password2" class="form-control"
					placeholder="Repita su contraseña" size="30" />
			</div></td>
      	</tr>
      	<tr>
    	    <td><input type="submit" class="btn btn-primary" value="Enviar"/></td>
      	</tr>
      </table>
   </form>
</div>
   <a id="listarCategorias_link_id" href="listarCategorias">Lista de categorias</a>
   <%@ include file="pieDePagina.jsp" %>
</body>
</html>