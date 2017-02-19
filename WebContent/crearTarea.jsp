<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>
<head> 
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
<title>TaskManager - Crear tarea</title>
<body>
  <form action="crearTarea" method="post" name="crearTarea_form_name">

 	<center><h1>Crear tarea</h1></center>
 	<hr><br>
 	<table align="center">
    	<tr> 
    		<td align="right">Título</td>
	    	<td><input type="text" class="form-control" name="tituloTarea"></td>
      	</tr>
      	<tr> 
    		<td align="right">Fecha planeada</td>
	    	<td><input type="text" class="form-control" name="fechaPlaneadaTarea"></td>
      	</tr>
      	<tr> 
    		<td align="right">Comentario</td>
	    	<td><textarea class="form-control" name="comentarioTarea" rows="3"></textarea></td>
      	</tr>
      	<tr>
    	    <td><input type="submit" class="btn btn-success" value="Crear tarea"/></td>
      	</tr>
      </table>
   </form>
   <%@ include file="pieDePagina.jsp" %>
</body>
</html>