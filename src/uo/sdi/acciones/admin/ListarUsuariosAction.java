package uo.sdi.acciones.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.acciones.Accion;
import uo.sdi.acciones.comparators.UserEmailComparator;
import uo.sdi.acciones.comparators.UserLoginComparator;
import uo.sdi.acciones.comparators.UserStatusComparator;
import uo.sdi.business.AdminService;
import uo.sdi.business.Services;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class ListarUsuariosAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";

		List<User> listaUsuarios;

		HttpSession session = request.getSession();
		try {
			AdminService adminService = Services.getAdminService();
			listaUsuarios = adminService.findAllUsers();

			List<User> listaUsuariosNoAdministradores = new ArrayList<>();

			for (User user : listaUsuarios) {
				if (!(user.getIsAdmin())) {
					listaUsuariosNoAdministradores.add(user);
				}
			}

			if (session.getAttribute("modo") != null) {
				String modo = (String) session.getAttribute("modo");
				Comparator<User> criterio = null;
				if (modo.equals("login")) {
					criterio = new UserLoginComparator();
				} else if (modo.equals("email")) {
					criterio = new UserEmailComparator();
				} else if (modo.equals("status")) {
					criterio = new UserStatusComparator();
				}
				
				Collections.sort(listaUsuariosNoAdministradores, criterio);
			}

			// Lo añadimos como atributo a la request ya que solo nos interesa
			// que persista hasta que se procese en la vista.
			request.setAttribute("listaUsuarios",
					listaUsuariosNoAdministradores);

			Log.debug(
					"Lista de usuarios obtenida con una cantidad de [%s] usuarios (no administradores)",
					listaUsuarios.size());
		} catch (BusinessException b) {
			Log.debug("Error intentando adquirir lista de Usuarios: %s",
					b.getMessage());
			resultado = "FRACASO";
		}
		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
