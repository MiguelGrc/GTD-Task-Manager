package uo.sdi.acciones;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.acciones.tipos.ListType;
import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.comparators.PlannedReverseTaskComparator;
import uo.sdi.comparators.PlannedTaskComparator;
import uo.sdi.dto.Task;
import alb.util.log.Log;

public class ListarTareasCategoriaAction implements Accion {
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		List<Task> listaTareasCategoria;
		
		HttpSession session = request.getSession();
		
		String choosenCategory = request.getParameter("id");
		
		Long categoryId;
		
		if(choosenCategory==null){	//Si no se seleccionnó y se viene de otra opcion para volver
			                  		//a la lista anterior.
			categoryId=(Long) session.getAttribute("categoriaSeleccionada");
		}
		else{
			categoryId= Long.valueOf(request.getParameter("id"));
		}
		
		try {
			
			
			TaskService taskService = Services.getTaskService();
			listaTareasCategoria=taskService.findTasksByCategoryId(categoryId);
			request.setAttribute("listaMostrar", listaTareasCategoria);
			Collections.sort(listaTareasCategoria, new PlannedTaskComparator());
			
			if((boolean) session.getAttribute("mostrarFinalizadas")){
				List<Task> tareasFinalizadas = taskService.findFinishedTasksByCategoryId(categoryId);
				Collections.sort(tareasFinalizadas, new PlannedReverseTaskComparator());
				listaTareasCategoria.addAll(tareasFinalizadas);
			}
			
			Log.debug("Obtenida lista de tareas para categoria con id [%d] conteniendo [%d] tareas",
					categoryId,listaTareasCategoria.size());
			//Mirar esto un poco guarrada a ver si hay otra solución
			//Guardarlo en session en su porpio action? Y si se cambia cuadno hace la peticion, deberia actualizarse, no?
			resultado = new ListarCategoriasAction().execute(request, response);
		
			session.setAttribute("ultimaLista", ListType.Categoria);
			session.setAttribute("categoriaSeleccionada", categoryId);
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

