package uo.sdi.acciones;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

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
		String fechaPlaneadaTarea = request.getParameter("fechaPlaneadaTarea");
		Long categoriaId = Long.parseLong(request.getParameter("categoria"));
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
//			if(!request.getParameter("fechaPlaneadaTarea").isEmpty()){
//				String f = request.getParameter("fechaPlaneadaTarea");
//				int y = Integer.parseInt(f.substring(0,4));
//				int m = Integer.parseInt(f.substring(5,7));
//				int d = Integer.parseInt(f.substring(8,10));
//				Date date = DateUtil.fromDdMmYyyy(d, m, y);
//				task.setPlanned(date);
//			}
			if(!fechaPlaneadaTarea.isEmpty()){	//TODO factorizar / simplificar esto?
				if(formatoFechaCorrecto(fechaPlaneadaTarea)){
					Date date = DateUtil.fromString(fechaPlaneadaTarea);
					if(date.after(new Date())){
						task.setPlanned(date);
					}
					else{
						request.setAttribute("mensajeParaElUsuario", "Introduzca una fecha "
								+ "posterior a la actual");
						resultado = "FRACASO";
						new DevolverListaAnteriorAction().execute(request, response);
						return resultado;
					}
				}
				else{
					request.setAttribute("mensajeParaElUsuario", "Introduzca la fecha "
							+ "siguiendo el formato indicado");
					resultado = "FRACASO";
					new DevolverListaAnteriorAction().execute(request, response);
					return resultado;
				}
			}
			task.setCategoryId(categoriaId);
			System.out.println(request.getParameter("fechaPlaneadaTarea"));
			TaskService taskService = Services.getTaskService();				
			taskService.updateTask(task);
			
			Log.debug("Editando tarea [%d]", 
					task.getId());
				
			resultado = new DevolverListaAnteriorAction().execute(request, response);
		} 
		catch (BusinessException b) {
				Log.debug("Error intentando editar la tarea [%s]: %s",
						request.getParameter("tareaId"), b.getMessage());
				resultado="FRACASO";
			}
		
		session.removeAttribute("tareaSeleccionada");
		return resultado;
	}
			
	private boolean formatoFechaCorrecto(String fecha){		//TODO factorizar esto, repetido aquí y en crear tarea
		String ePattern = "^(((0[1-9]|[12]\\d|3[01])\\/(0[13578]|1[02])\\/((19|[2-9]\\d)"
				+ "\\d{2}))|((0[1-9]|[12]\\d|30)\\/(0[13456789]|1[012])\\/((19|[2-9]\\d)\\d{2}))"
				+ "|((0[1-9]|1\\d|2[0-8])\\/02\\/((19|[2-9]\\d)\\d{2}))|(29\\/02\\/((1[6-9]|[2-9]\\d)"
				+ "(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$";
		return Pattern.compile(ePattern).matcher(fecha).matches();
	}

}
