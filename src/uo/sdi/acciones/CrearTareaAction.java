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
			task.setTitle(tituloTarea);
//			task.setPlanned()	//TODO
			task.setComments(comentarioTarea);
			User user = (User) session.getAttribute("user");
			task.setUserId(user.getId());
			TaskService taskService = Services.getTaskService();
			taskService.createTask(task);
			Log.debug("Registrada nueva tarea con título [%s]", 
					task.getTitle());
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
