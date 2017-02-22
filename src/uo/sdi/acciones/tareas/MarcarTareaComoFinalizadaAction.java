package uo.sdi.acciones.tareas;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.acciones.Accion;
import uo.sdi.acciones.listar.DevolverListaAnteriorAction;
import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Task;
import alb.util.log.Log;

public class MarcarTareaComoFinalizadaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado = "EXITO";
		
		try{
			if(request.getParameter("tareaId") == null){
				request.setAttribute("mensajeParaElUsuario", "Seleccione primero una "
						+ "tarea para marcarla como completada");
				resultado = "FRACASO";
			}
			else{
				TaskService taskService = Services.getTaskService();
				Task task = (Task) taskService.findTaskById(Long.parseLong(request.getParameter("tareaId")));
				
				if(task.getFinished() != null){
					request.setAttribute("mensajeParaElUsuario", "No es posible marcar "
							+ "como finalizada una tarea ya completada");
					resultado = "FRACASO";
				}
				else{
					taskService.markTaskAsFinished(task.getId());
					
					Log.debug("Marcando tarea [%d] como finalizada", 
							task.getId());
				}
			}
				
			//Volvemos a cargar la lista actualizada.
			new DevolverListaAnteriorAction().execute(request, response);
		} 
		catch (BusinessException b) {
				Log.debug("Error intentando marcar la tarea [%s] como finalizada: %s",
						request.getParameter("tareaId"), b.getMessage());
				resultado="FRACASO";
			}
		
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}

}
