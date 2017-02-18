package uo.sdi.acciones;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.comparators.PlannedTaskComparator;
import uo.sdi.dto.Task;
import alb.util.log.Log;

public class ListarTareasCategoriaAction implements Accion {
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		List<Task> listaTareasCategoria;
		
		Long categoryId= Long.valueOf(request.getParameter("id"));
		try {
			
			
			TaskService taskService = Services.getTaskService();
			listaTareasCategoria=taskService.findTasksByCategoryId(categoryId);
			request.setAttribute("listaMostrar", listaTareasCategoria);
			Collections.sort(listaTareasCategoria, new PlannedTaskComparator());
			
			Log.debug("Obtenida lista de tareas para categoria con id [%d] conteniendo [%d] tareas",
					categoryId,listaTareasCategoria.size());
			//Mirar esto un poco guarrada a ver si hay otra solución
			//Guardarlo en session en su porpio action? Y si se cambia cuadno hace la peticion, deberia actualizarse, no?
			resultado = new ListarCategoriasAction().execute(request, response);
		}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido obteniendo lista de tareas "
					+ " correspondiente a la categoria con id [%d] : %s",
					categoryId,b.getMessage());
			resultado="FRACASO";
		}
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	
}

