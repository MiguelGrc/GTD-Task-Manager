package uo.sdi.acciones.categorias;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;
import uo.sdi.acciones.Accion;
import uo.sdi.acciones.listar.DevolverListaAnteriorAction;
import uo.sdi.acciones.tipos.ListType;
import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;

public class ComprobarCategoriaElegidaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";
		HttpSession session = request.getSession();
		Long categoriaId = (Long) session.getAttribute("categoriaSeleccionada");

		try{
			if (session.getAttribute("categoriaSeleccionada") == null
					|| session.getAttribute("ultimaLista") != ListType.Categoria) {
				request.setAttribute("mensajeParaElUsuario",
						"No se ha seleccionado ninguna categoría de usuario "
								+ "sobre la que actuar");
				resultado = "FRACASO";
				
				Log.debug("Abriendo página de edición para una categoría");
				
				//Recargamos las listas correspondientes.
				new DevolverListaAnteriorAction().execute(request, response);
			} 
			else {
				TaskService taskService = Services.getTaskService();
				Category cat = taskService.findCategoryById(categoriaId);
				request.setAttribute("nombreCategoriaSeleccionada", cat.getName());
				
				resultado="EXITO";
				Log.debug("Abriendo página de edición para la categoría [%s]:",
						categoriaId);
			}
		}
		catch (BusinessException b) {
			Log.debug("Error intentando abrir la página de edición para la categoría [%s]: %s",
					categoriaId, b.getMessage());
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
