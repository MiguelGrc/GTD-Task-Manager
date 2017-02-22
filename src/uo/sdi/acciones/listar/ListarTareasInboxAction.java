package uo.sdi.acciones.listar;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.acciones.Accion;
import uo.sdi.acciones.comparators.PlannedReverseTaskComparator;
import uo.sdi.acciones.comparators.PlannedTaskComparator;
import uo.sdi.acciones.tipos.ListType;
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
			Collections.sort(listaTareasInbox, new PlannedTaskComparator());
			request.setAttribute("listaMostrar", listaTareasInbox);
			
			if((boolean) session.getAttribute("mostrarFinalizadas")){
				List<Task> tareasFinalizadas = taskService.findFinishedInboxTasksByUserId(user.getId());
				Collections.sort(tareasFinalizadas, new PlannedReverseTaskComparator());
				listaTareasInbox.addAll(tareasFinalizadas);
			}
			
			Log.debug("Obtenida lista de tareas inbox conteniendo [%d] tareas", 
					listaTareasInbox.size());
			
			resultado = new ListarCategoriasAction().execute(request, response);
			
			session.setAttribute("ultimaLista", ListType.Inbox);
			request.setAttribute("nombreCat", "Inbox");
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
