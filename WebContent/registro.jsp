<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html><head> <title>TaskManager - Registro</title>
<body>
  <form action="crearCuenta" method="post" name="registrarse_form_name">

 	<center><h1>Regístrese</h1></center>
 	<hr><br>
 	<table align="center">
    	<tr> 
    		<td align="right">Usuario</td>
	    	<td><input type="text" name="nombreUsuario" align="left" size="15"></td>
      	</tr>
      	<tr> 
    		<td align="right">Email</td>
	    	<td><input type="text" name="email" align="left" size="15"></td>
      	</tr>
      	<tr> 
    		<td align="right">Contraseña</td>
	    	<td><input type="password" name="password1" align="left" size="15"></td>
      	</tr>
      	<tr> 
    		<td align="right">Repita su contraseña</td>
	    	<td><input type="password" name="password2" align="left" size="15"></td>
      	</tr>
      	<tr>
    	    <td><input type="submit" value="Enviar"/></td>
      	</tr>
      </table>
   </form>
   <a id="listarCategorias_link_id" href="listarCategorias">Lista de categorias</a>
   <%@ include file="pieDePagina.jsp" %>
</body>
</html>