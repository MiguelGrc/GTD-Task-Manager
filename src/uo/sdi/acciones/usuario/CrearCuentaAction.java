package uo.sdi.acciones.usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.acciones.Accion;
import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class CrearCuentaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		String nombreUsuario=request.getParameter("nombreUsuario");
		String email=request.getParameter("email");
		String password1=request.getParameter("password1");
		String password2=request.getParameter("password2");
		User user = new User();
		
		try{
			if (!password1.equals(password2)){
				request.setAttribute("mensajeParaElUsuario", "Las contraseñas introducidas "
						+ "no coinciden.");
				resultado="FRACASO";
				return resultado;
			}
			user.setLogin(nombreUsuario);
			user.setEmail(email);
			user.setPassword(password1);
			UserService userService = Services.getUserService();
			userService.registerUser(user);
			Log.debug("Registrado nuevo usuario con login [%s], email [%s] y contraseña [%s]", 
					user.getLogin(), user.getEmail(), user.getPassword());
		} catch (BusinessException b) {
			Log.debug("Algo ha ocurrido intentando registrar usuario: %s", 
					b.getMessage());
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
