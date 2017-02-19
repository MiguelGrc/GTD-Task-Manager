package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
				
				//TODO check before if it's already finished ????
				taskService.markTaskAsFinished(task.getId());
				
				Log.debug("Marcando tarea [%d] como finalizada", 
						task.getId());
			}
			//Volvemos a cargar la lista actualizada.
			//TODO OJO que esto depende de la lista en la que estemos!!!!!
			resultado = new ListarTareasHoyAction().execute(request, response);
		} 
		catch (BusinessException b) {
				Log.debug("Error intentando marcar la tarea [%s] como finalizada: %s",
						request.getParameter("tareaId"), b.getMessage());
				resultado="FRACASO";
			}
		
		return resultado;
	}

}
