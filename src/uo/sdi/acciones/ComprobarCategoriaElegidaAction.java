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

public class ComprobarCategoriaElegidaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";
		HttpSession session = request.getSession();

		if (session.getAttribute("categoriaSeleccionada") == null
				|| session.getAttribute("ultimaLista") != ListType.Categoria) {
			request.setAttribute("mensajeParaElUsuario",
					"No se ha seleccionado ninguna cateogría "
							+ "para actuar sobre esta.");
			resultado = "FRACASO";
			//Recargamos las lsitas correspondientes.
			new DevolverListaAnteriorAction().execute(request, response);
			
		} 
		else {
			resultado="EXITO";
			}

		return resultado;

	}

}
