package uo.sdi.acciones;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.business.AdminService;
import uo.sdi.business.Services;
import uo.sdi.business.exception.BusinessException;
import alb.util.log.Log;

public class BorrarUsuarioAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado="EXITO";
		
		Long id  = Long.valueOf(request.getParameter("usuarioABorrar"));		
		
		try {
			AdminService adminService = Services.getAdminService();
			
			adminService.deepDeleteUser(id);
			
			Log.debug("Usuario [%s] eliminado del sistema",
					id);
		}
		catch (BusinessException b) {
			Log.debug("Error intentando eliminar del sistema al usuario: %s",
					id);
			resultado="FRACASO";
		}
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	

}
