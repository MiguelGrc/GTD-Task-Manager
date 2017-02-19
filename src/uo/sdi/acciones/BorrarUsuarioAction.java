package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.AdminService;
import uo.sdi.business.Services;
import uo.sdi.business.exception.BusinessException;
import alb.util.log.Log;

public class BorrarUsuarioAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado="EXITO";
		
		Long id;
		
		try {		
			if(request.getParameter("usuarioId") == null){
				request.setAttribute("mensajeParaElUsuario", "Seleccione primero un "
						+ "usuario para eliminarlo");
				resultado="FRACASO";
			}
			else{
				id = Long.valueOf(request.getParameter("usuarioId"));
				AdminService adminService = Services.getAdminService();
				adminService.deepDeleteUser(id);
				
				Log.debug("Usuario [%s] eliminado del sistema",
						id);
			}
			//Volvemos a cargar la lista actualizada.
			resultado = new ListarUsuariosAction().execute(request, response);
		}
		catch (BusinessException b) {
			Log.debug("Error intentando eliminar del sistema al usuario: %s",
					request.getParameter("usuarioId"));
			resultado="FRACASO";
		}
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	

}
