package uo.sdi.acciones.categorias;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.acciones.Accion;
import uo.sdi.acciones.listar.ListarCategoriasAction;
import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import alb.util.log.Log;

public class BorrarCategoriaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";
		HttpSession session = request.getSession();
		
		resultado = new ComprobarCategoriaElegidaAction().execute(request, response);

		if(resultado.equals("EXITO")) {
			Long categoryId = (Long) session
					.getAttribute("categoriaSeleccionada");
			try {

				TaskService taskService = Services.getTaskService();
				taskService.deleteCategory(categoryId);

				Log.debug("Eliminada categoría con id [%d] ", categoryId);

				resultado = new ListarCategoriasAction().execute(request, response);
			}

			catch (BusinessException b) {
				Log.debug(
						"Algo ha ocurrido intentando borrar la categoría con id [%d]: %s",
						categoryId, b.getMessage());
				request.setAttribute("mensajeParaElUsuario", b.getMessage());
				resultado = "FRACASO";
			}
		}

		return resultado;

	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}

}
