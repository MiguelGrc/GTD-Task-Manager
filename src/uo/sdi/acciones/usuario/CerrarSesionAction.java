package uo.sdi.acciones.usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.acciones.Accion;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class CerrarSesionAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		HttpSession session = request.getSession();
		
		User user = (User) session.getAttribute("user");
		
		session.invalidate();
		
		Log.debug("El usuario [%d] ha cerrado sesión", user.getId());
		
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	
}
