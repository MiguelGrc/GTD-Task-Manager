package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class ListarTareasInboxAction implements Accion {
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		List<Task> listaTareasInbox;
		
		try {
			TaskService taskService = Services.getTaskService();
			HttpSession session= request.getSession();
			User user =(User)session.getAttribute("user");
			
			listaTareasInbox=taskService.findInboxTasksByUserId((user.getId()));
			request.setAttribute("listaMostrar", listaTareasInbox);
			Log.debug("Obtenida lista de tareas inbox con [%d] tareas", 
					listaTareasInbox.size());
		}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido obteniendo lista de categorías: %s",
					b.getMessage());
			resultado="FRACASO";
		}
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	
}
