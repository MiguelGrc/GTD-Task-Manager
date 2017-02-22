package uo.sdi.acciones.listar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.util.log.Log;
import uo.sdi.acciones.Accion;

public class CambiarFiltroTareasFinalizadasAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		if(request.getSession().getAttribute("mostrarFinalizadas")==null){
			Log.debug("No se ha encontrado el atributo mostrarFinalizadas en sesión");
			return "FRACASO";
		}
		
		boolean mostrarFinalizadas = (boolean) request.getSession().getAttribute("mostrarFinalizadas");
		
		request.getSession().setAttribute("mostrarFinalizadas", !mostrarFinalizadas);
		
		resultado= new DevolverListaAnteriorAction().execute(request, response);
		
		Log.debug("El filtro de tareas finalizadas se ha cambiado a [%s]", !mostrarFinalizadas);
		
		return resultado;
		
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}

}
