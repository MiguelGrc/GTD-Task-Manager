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

/**
 * Devuelve el conjunto de tareas pendientes para hoy tomando el usuario en sesión.
 * Eso significa que el usuario tiene que estar identificado previamente (sin problema ya que
 * si accede a esta acción significa que se ha loggeado correctamente).
 * 
 *
 */
public class ListarTareasHoyAction implements Accion {
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		List<Task> listaHoy;
		
		try {
			TaskService taskService = Services.getTaskService();
			HttpSession session = request.getSession();
			User user =(User)session.getAttribute("user");
			listaHoy=taskService.findTodayTasksByUserId(user.getId());
			request.setAttribute("listaHoy", listaHoy);
			Log.debug("Obtenida lista de tareas para hoy con [%d] tareas", 
					listaHoy.size());
		}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido obteniendo lista de tareas para hoy: %s",
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
