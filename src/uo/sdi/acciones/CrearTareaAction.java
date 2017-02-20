package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class CrearTareaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";
		HttpSession session = request.getSession();
		String tituloTarea = request.getParameter("tituloTarea");
		String fechaPlaneadaTarea = request.getParameter("fechaPlaneadaTarea");
		String comentarioTarea = request.getParameter("comentarioTarea");
		Task task = new Task();
		
		try{
			if(tituloTarea == null){
				request.setAttribute("mensajeParaElUsuario", "Introduzca un título "
						+ "para la nueva tarea");
				resultado="FRACASO";
			}
			else{
				User user = (User) session.getAttribute("user");
				task.setUserId(user.getId());
				task.setTitle(tituloTarea);
//				if(fechaPlaneadaTarea.tieneFormatoCorrecto()){
//					task.setPlanned(-----)	//TODO: se debería poder especificar aunque no sea
											//obligatorio la fecha planeada o pasamos ???
//				}
				if(comentarioTarea != null){
					task.setComments(comentarioTarea);
				}
				TaskService taskService = Services.getTaskService();
				taskService.createTask(task);
				Log.debug("Registrada nueva tarea con título [%s]", 
						task.getTitle());
			}
			//Volvemos a cargar la lista actualizada que escogimos anteriormente.
			resultado = new DevolverListaAnteriorAction().execute(request, response);
		}
		catch (BusinessException b){
			Log.debug("Algo ha ocurrido intentando crear tarea [%s]: %s", 
					tituloTarea,b.getMessage());
			request.setAttribute("mensajeParaElUsuario", b.getMessage());
			resultado="FRACASO";
		}
		
		
		return resultado;

	}

}
