package uo.sdi.acciones.listar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;
import uo.sdi.acciones.Accion;
import uo.sdi.acciones.tipos.ListType;



public class DevolverListaAnteriorAction implements Accion {


	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		
		String resultado="EXITO";
		
		if(session.getAttribute("ultimaLista") == null){
			resultado = new ListarCategoriasAction().execute(request, response);
			Log.debug("No se ha encontrado el atributo ultimaLista en sesión ");
			resultado = "FRACASO";
			return resultado;
		}
		
		ListType previousList= (ListType) session.getAttribute("ultimaLista");
		
		switch(previousList){
			case Categoria:
				resultado= new ListarTareasCategoriaAction().execute(request, response);
				break;
			case Inbox:
				resultado = new ListarTareasInboxAction().execute(request, response);
				break;
			case Hoy:
				resultado = new ListarTareasHoyAction().execute(request, response);
				break;
		}
		
		Log.debug("Recargando lista [%s]", previousList);
		
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}

}
