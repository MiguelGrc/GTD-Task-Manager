package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;
import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Task;

public class PrepararEdicionTareaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";
		HttpSession session = request.getSession();
		System.out.println(request.getParameter("tareaId"));
		try{
			if(request.getParameter("tareaId") == null){
				request.setAttribute("mensajeParaElUsuario", "Seleccione primero una "
						+ "tarea para poder editarla");
				resultado = "FRACASO";
				
			}
			else{
				TaskService taskService = Services.getTaskService();
				Task task = (Task) taskService.findTaskById(Long.parseLong(request.getParameter("tareaId")));
				
				session.setAttribute("tareaSeleccionada", task);
				
				Log.debug("Abriendo página de edición para la tarea [%d]",
						task.getId());
			}
			//Volvemos a cargar la lista actualizada que escogimos anteriormente.
			new DevolverListaAnteriorAction().execute(request, response);
		} 
		catch (BusinessException b) {
				Log.debug("Error intentando abrir la página de edición para la tarea [%s]: %s",
						request.getParameter("tareaId"), b.getMessage());
				resultado="FRACASO";
			}
		
		return resultado;
		
	}

}
