	<br><br>
	${requestScope.mensajeParaElUsuario!=null && !requestScope.mensajeParaElUsuario.startsWith("Error irrecuperable") ? 
	'<div class="alert alert-warning"><strong>Información: 
	</strong> <i>' += requestScope.mensajeParaElUsuario += '</i></div>' : requestScope.mensajeParaElUsuario!=null ? 
	'<div class="alert alert-danger"><strong>Error: 
	</strong> <i>' += requestScope.mensajeParaElUsuario += '</i></div>' : ''}