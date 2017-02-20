package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.acciones.tipos.ListType;
import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;
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

				// Volvemos a cargar la lista actualizada que escogimos
				// anteriormente.
				resultado = new DevolverListaAnteriorAction().execute(request,
						response);
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

}
