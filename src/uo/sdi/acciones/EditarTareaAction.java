package uo.sdi.acciones;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Task;
import alb.util.date.DateUtil;
import alb.util.log.Log;

public class EditarTareaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado = "EXITO";
		HttpSession session = request.getSession();
		Task task;
		try{
//			if(request.getAttribute("tareaSeleccionada") == null){	//TODO puede llegar aquí la request si tareaSeleccionada es null?
//				request.setAttribute("mensajeParaElUsuario", "Seleccione primero una "
//						+ "tarea para editarla");
//				resultado = "FRACASO";
//			}
			if(request.getParameter("tituloTarea").isEmpty()){
				request.setAttribute("mensajeParaElUsuario", "El título de la tarea no "
						+ "puede estar vacío");
				resultado = "FRACASO";
				session.removeAttribute("tareaSeleccionada");
				return resultado;
			}
			task = (Task) session.getAttribute("tareaSeleccionada");
			task.setTitle(request.getParameter("tituloTarea"));
			if(!request.getParameter("comentarioTarea").isEmpty()){
				task.setComments(request.getParameter("comentarioTarea"));
			}
			if(!request.getParameter("fechaPlaneadaTarea").isEmpty()){
				String f = request.getParameter("fechaPlaneadaTarea");
				int y = Integer.parseInt(f.substring(0,4));
				int m = Integer.parseInt(f.substring(5,7));
				int d = Integer.parseInt(f.substring(8,10));
				Date date = DateUtil.fromDdMmYyyy(d, m, y);
				task.setPlanned(date);
			}
			System.out.println(request.getParameter("fechaPlaneadaTarea"));
			TaskService taskService = Services.getTaskService();				
			taskService.updateTask(task);
			
			Log.debug("Editando tarea [%d]", 
					task.getId());
				
			//Volvemos a cargar la lista actualizada.
			//TODO OJO que esto depende de la lista en la que estemos!!!!!
			resultado = new ListarTareasHoyAction().execute(request, response);
		} 
		catch (BusinessException b) {
				Log.debug("Error intentando editar la tarea [%s]: %s",
						request.getParameter("tareaId"), b.getMessage());
				resultado="FRACASO";
			}
		
		session.removeAttribute("tareaSeleccionada");
		return resultado;
	}

}
