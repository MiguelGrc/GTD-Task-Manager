package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CambiarFiltroTareasFinalizadasAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		if(request.getSession().getAttribute("mostrarFinalizadas")==null){
			
			return "FRACASO";
		}
		
		boolean mostrarFinalizadas = (boolean) request.getSession().getAttribute("mostrarFinalizadas");
		
		request.getSession().setAttribute("mostrarFinalizadas", !mostrarFinalizadas);
		
		resultado= new DevolverListaAnteriorAction().execute(request, response);
		
		return resultado;
		
		
		
		
	}

}
