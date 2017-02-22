package uo.sdi.acciones;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.acciones.tipos.ListType;
import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;
import alb.util.date.DateUtil;
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
		
		ListType previousList = (ListType) session.getAttribute("ultimaLista");
		Long categoryId=(Long) session.getAttribute("categoriaSeleccionada");
		
		try{
			User user = (User) session.getAttribute("user");
			task.setUserId(user.getId());
			task.setTitle(tituloTarea);
			
			if(previousList == ListType.Hoy){
				String formatted = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
				Date date = DateUtil.fromString(formatted);
				task.setPlanned(date);
			}
			else{
				if(previousList == ListType.Categoria){
					task.setCategoryId(categoryId);
				}
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
			}
			
			if(comentarioTarea != null){
				task.setComments(comentarioTarea);
			}
			TaskService taskService = Services.getTaskService();
			taskService.createTask(task);
			Log.debug("Registrada nueva tarea con título [%s]", 
					task.getTitle());

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
	
	private boolean formatoFechaCorrecto(String fecha){		//TODO factorizar esto, repetido aquí y en editar tarea
		String ePattern = "^(((0[1-9]|[12]\\d|3[01])\\/(0[13578]|1[02])\\/((19|[2-9]\\d)"
				+ "\\d{2}))|((0[1-9]|[12]\\d|30)\\/(0[13456789]|1[012])\\/((19|[2-9]\\d)\\d{2}))"
				+ "|((0[1-9]|1\\d|2[0-8])\\/02\\/((19|[2-9]\\d)\\d{2}))|(29\\/02\\/((1[6-9]|[2-9]\\d)"
				+ "(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$";
		return Pattern.compile(ePattern).matcher(fecha).matches();
	}

}
