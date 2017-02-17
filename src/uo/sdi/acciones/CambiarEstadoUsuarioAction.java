package uo.sdi.acciones;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.AdminService;
import uo.sdi.business.Services;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;
import uo.sdi.dto.types.UserStatus;
import alb.util.log.Log;

public class CambiarEstadoUsuarioAction implements Accion {

	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		
		try {
			if(request.getParameter("usuarioId") == null){
				request.setAttribute("mensajeParaElUsuario", "Seleccione primero un "
						+ "usuario para cambiarle el estado");
				resultado="FRACASO";
			}
			else{
				AdminService adminService = Services.getAdminService();
				User user = (User) adminService.findUserById(Long.parseLong(request.getParameter("usuarioId")));
				
				if(user.getStatus().equals(UserStatus.ENABLED)){
					adminService.disableUser(user.getId());
				}
				else{
					adminService.enableUser(user.getId());
				}
				Log.debug("Cambiado estado de usuario [%s] a %s", 
						user.getId(), user.getStatus());
			}
			//Volvemos a cargar la lista actualizada.
			resultado = new ListarUsuariosAction().execute(request, response);
		}
		catch (BusinessException b) {
			Log.debug("Error intentando cambiando el estado de un usuario: %s",
					b.getMessage());
			resultado="FRACASO";
		}
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	
}
