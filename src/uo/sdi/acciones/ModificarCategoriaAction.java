package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class ModificarCategoriaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";
		HttpSession session = request.getSession();
		String nombreCategoria = request.getParameter("nombreCategoria");
		
		try{
			if(nombreCategoria == null){
				request.setAttribute("mensajeParaElUsuario", "Introduzca un nombre "
						+ "para la nueva categoria");
				resultado="FRACASO";
			}
			else{
				Long categoryId = (Long) session.getAttribute("categoriaSeleccionada");
				
				TaskService taskService = Services.getTaskService();
				Category categoria = taskService.findCategoryById(categoryId);
				categoria.setName(nombreCategoria);
				taskService.updateCategory(categoria);
				
				Log.debug("Actualizada categoria con el nuevo nombre [%s]", 
						categoria.getName());
				
			}
			//Volvemos a cargar la lista actualizada que escogimos anteriormente.
			resultado = new DevolverListaAnteriorAction().execute(request, response);
		}
		catch (BusinessException b){
			Log.debug("Algo ha ocurrido intentando modificar categoria con el nombre [%s]: %s", 
					nombreCategoria,b.getMessage());
			request.setAttribute("mensajeParaElUsuario", b.getMessage());
			resultado="FRACASO";
		}
		
		
		return resultado;

	}

}
