package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;
import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;
import uo.sdi.dto.util.Cloner;


public class ModificarContraseñaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		String contraseñaAntigua = request.getParameter("passAntigua");
		String primeraPass=request.getParameter("passOne");
		String segundaPass=request.getParameter("passTwo");
		HttpSession session=request.getSession();
		User user=((User)session.getAttribute("user"));
		User userClone=Cloner.clone(user);
		userClone.setPassword(primeraPass);
		try {
			
			if(!(primeraPass.equals(segundaPass)) || !(contraseñaAntigua.equals(user.getPassword()))){
				request.setAttribute("mensajeParaElUsuario", "Contraseña actual mal introducida o"
						+ " las contraseñas nuevas no coinciden.");
				resultado="FRACASO";
				return resultado;
			}
			UserService userService = Services.getUserService();
			userService.updateUserDetails(userClone);
			Log.debug("Modificado password de [%s] con el valor [%s]", 
					userClone.getLogin(), primeraPass);
			session.setAttribute("user",userClone);
		}
		catch (BusinessException b) {
			
			Log.debug("Algo ha ocurrido actualizando la contraseña de [%s] a [%s]: %s", 
					user.getPassword(),primeraPass,b.getMessage());
			
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
