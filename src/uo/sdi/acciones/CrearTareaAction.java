package uo.sdi.acciones;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.acciones.tipos.ListType;
import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
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
			if(previousList == ListType.Hoy){
				String formatted = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
				Date date = DateUtil.fromString(formatted);
				task.setPlanned(date);
			}
			else{
				if(previousList == ListType.Categoria){
					task.setCategoryId(categoryId);
				}
//				if(!fechaPlaneadaTarea.isEmpty()){
//					task.setPlanned(-------);
//				}
			}
			
			User user = (User) session.getAttribute("user");
			task.setUserId(user.getId());
			task.setTitle(tituloTarea);

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

}
