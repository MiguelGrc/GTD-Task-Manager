package uo.sdi.acciones.categorias;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.acciones.Accion;
import uo.sdi.acciones.listar.DevolverListaAnteriorAction;
import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class CrearCategoriaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";
		HttpSession session= request.getSession();
		User user = (User) session.getAttribute("user");
		
		String nombreCategoria = request.getParameter("nombreCategoria");
		Category categoria= new Category();
		
		
		try{
			if(nombreCategoria.isEmpty()){
				request.setAttribute("mensajeParaElUsuario", "Introduzca un nombre "
						+ "para la nueva categoria");
				resultado="FRACASO";
			}
			else{
				
				categoria.setName(nombreCategoria);
				categoria.setUserId(user.getId());
				TaskService taskService = Services.getTaskService();
				taskService.createCategory(categoria);
				Log.debug("Registrada nueva categoria con nombre [%s] al usuario con id [%d]", 
						categoria.getName(), user.getId());
			}
			//Volvemos a cargar la lista actualizada que escogimos anteriormente.
			resultado = new DevolverListaAnteriorAction().execute(request, response);
		}
		catch (BusinessException b){
			Log.debug("Algo ha ocurrido intentando crear categoria [%s] para el usuario [%d]: %s", 
					nombreCategoria,user.getId(),b.getMessage());
			request.setAttribute("mensajeParaElUsuario", b.getMessage());
			resultado="FRACASO";
		}
		
		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}
	
}
