package uo.sdi.acciones.admin;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.acciones.Accion;
import alb.util.log.Log;

/**
 * Devuelve el conjunto de tareas pendientes para hoy tomando el usuario en sesión.
 * Eso significa que el usuario tiene que estar identificado previamente (sin problema ya que
 * si accede a esta acción significa que se ha loggeado correctamente).
 * 
 *
 */
public class EstablecerOrdenUsuariosAction implements Accion {
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		HttpSession session = request.getSession();
		
		String sortMethod= request.getParameter("modo");
		
		session.setAttribute("modo", sortMethod);
		
		Log.debug("Cambiado modo de ordenar usuarios a: %s",
				sortMethod);
		
		//We process the list again.
		resultado = new ListarUsuariosAction().execute(request, response);
		
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	
}
