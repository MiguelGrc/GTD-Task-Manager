package uo.sdi.acciones.categorias;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.acciones.Accion;
import uo.sdi.acciones.listar.DevolverListaAnteriorAction;
import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import alb.util.log.Log;

public class DuplicarCategoriaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";
		HttpSession session = request.getSession();
		Long categoriaId = (Long) session.getAttribute("categoriaSeleccionada");
		
		try{
			TaskService taskService = Services.getTaskService();
			taskService.duplicateCategory(categoriaId);
			Log.debug("Duplicada categoria con ID [%s]", categoriaId);
	
			//Volvemos a cargar la lista actualizada que escogimos anteriormente.
			resultado = new DevolverListaAnteriorAction().execute(request, response);
		}
		catch (BusinessException b){
			Log.debug("Algo ha ocurrido intentando duplicar la categoría con ID [%s]: %s", 
					categoriaId, b.getMessage());
			request.setAttribute("mensajeParaElUsuario", b.getMessage());
			resultado="FRACASO";
		}
		
		
		return resultado;

	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}

}
