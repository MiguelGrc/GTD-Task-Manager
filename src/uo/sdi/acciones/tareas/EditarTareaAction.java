package uo.sdi.acciones.tareas;

import java.util.Date;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.acciones.Accion;
import uo.sdi.acciones.listar.DevolverListaAnteriorAction;
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
		String fechaPlaneadaTarea = request.getParameter("fechaPlaneadaTarea");
		HttpSession session = request.getSession();
		Task task;
		try{
			if(request.getParameter("tituloTarea").isEmpty()){
				request.setAttribute("mensajeParaElUsuario", "El título de la tarea no "
						+ "puede estar vacío");
				resultado = "FRACASO";
				return resultado;
			}
			
			task = (Task) session.getAttribute("tareaSeleccionada");
			task.setTitle(request.getParameter("tituloTarea"));
			
			if(!request.getParameter("comentarioTarea").isEmpty()){
				task.setComments(request.getParameter("comentarioTarea"));
			}
			
			if(!fechaPlaneadaTarea.isEmpty()){
				if(formatoFechaCorrecto(fechaPlaneadaTarea)){
					Date date = DateUtil.fromString(fechaPlaneadaTarea);
					task.setPlanned(date);
				}
				else{
					request.setAttribute("mensajeParaElUsuario", "Introduzca la fecha "
							+ "siguiendo el formato indicado");
					resultado = "FRACASO";
					new DevolverListaAnteriorAction().execute(request, response);
					return resultado;
				}
			}
			
			if(!request.getParameter("categoria").isEmpty()){
				task.setCategoryId(Long.parseLong(request.getParameter("categoria")));
			}
			
			System.out.println(request.getParameter("fechaPlaneadaTarea"));
			TaskService taskService = Services.getTaskService();				
			taskService.updateTask(task);
			
			Log.debug("Editando tarea [%d]", task.getId());
				
			resultado = new DevolverListaAnteriorAction().execute(request, response);
		} 
		catch (BusinessException b) {
				Log.debug("Error intentando editar la tarea [%s]: %s",
						request.getParameter("tareaId"), b.getMessage());
				resultado="FRACASO";
			}

		return resultado;
	}
			
	private boolean formatoFechaCorrecto(String fecha){
		String ePattern = "^(((0[1-9]|[12]\\d|3[01])\\/(0[13578]|1[02])\\/((19|[2-9]\\d)"
				+ "\\d{2}))|((0[1-9]|[12]\\d|30)\\/(0[13456789]|1[012])\\/((19|[2-9]\\d)\\d{2}))"
				+ "|((0[1-9]|1\\d|2[0-8])\\/02\\/((19|[2-9]\\d)\\d{2}))|(29\\/02\\/((1[6-9]|[2-9]\\d)"
				+ "(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$";
		return Pattern.compile(ePattern).matcher(fecha).matches();
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}

}
