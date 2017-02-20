package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.acciones.tipos.ListType;



public class DevolverListaAnteriorAction implements Accion {


	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		
		ListType previousList= (ListType) session.getAttribute("ultimaLista");
		
		String resultado="FRACASO";
		
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
		
		return resultado;
	}

}
