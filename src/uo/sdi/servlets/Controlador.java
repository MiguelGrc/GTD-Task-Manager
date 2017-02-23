package uo.sdi.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;
import uo.sdi.acciones.*;
import uo.sdi.acciones.admin.BorrarUsuarioAction;
import uo.sdi.acciones.admin.CambiarEstadoUsuarioAction;
import uo.sdi.acciones.admin.EstablecerOrdenUsuariosAction;
import uo.sdi.acciones.admin.ListarUsuariosAction;
import uo.sdi.acciones.categorias.BorrarCategoriaAction;
import uo.sdi.acciones.categorias.ComprobarCategoriaElegidaAction;
import uo.sdi.acciones.categorias.CrearCategoriaAction;
import uo.sdi.acciones.categorias.DuplicarCategoriaAction;
import uo.sdi.acciones.categorias.ModificarCategoriaAction;
import uo.sdi.acciones.listar.CambiarFiltroTareasFinalizadasAction;
import uo.sdi.acciones.listar.ListarCategoriasAction;
import uo.sdi.acciones.listar.ListarTareasCategoriaAction;
import uo.sdi.acciones.listar.ListarTareasHoyAction;
import uo.sdi.acciones.listar.ListarTareasInboxAction;
import uo.sdi.acciones.tareas.CrearTareaAction;
import uo.sdi.acciones.tareas.EditarTareaAction;
import uo.sdi.acciones.tareas.MarcarTareaComoFinalizadaAction;
import uo.sdi.acciones.tareas.PrepararEdicionTareaAction;
import uo.sdi.acciones.usuario.CerrarSesionAction;
import uo.sdi.acciones.usuario.CrearCuentaAction;
import uo.sdi.acciones.usuario.ModificarContraseñaAction;
import uo.sdi.acciones.usuario.ModificarEmailAction;
import uo.sdi.acciones.usuario.ValidarseAction;
import uo.sdi.dto.User;
import uo.sdi.persistence.PersistenceException;

public class Controlador extends javax.servlet.http.HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private Map<String, Map<String, Accion>> mapaDeAcciones; // <rol, <opcion, objeto Accion>>
	private Map<String, Map<String, Map<String, String>>> mapaDeNavegacion; // <rol, <opcion, <resultado, JSP>>>

	//Para asegurarnos que se inicia apropiadamente antes de recibir any request.
	public void init() throws ServletException {  
		crearMapaAcciones();
		crearMapaDeNavegacion();
    }
	

	public void doGet(HttpServletRequest request, HttpServletResponse response)
				throws IOException, ServletException {
		
		String accionNavegadorUsuario, resultado, jspSiguiente;
		Accion objetoAccion;
		String rolAntes, rolDespues;
		
		try {
			// Obtener el string que hay a la derecha de la última /
			accionNavegadorUsuario=request.getServletPath().replace("/","");
			
				
			rolAntes=obtenerRolDeSesion(request);
			
			objetoAccion=buscarObjetoAccionParaAccionNavegador(rolAntes, 
					accionNavegadorUsuario);
			
			request.removeAttribute("mensajeParaElUsuario");
				
			resultado=objetoAccion.execute(request,response);
				
			rolDespues=obtenerRolDeSesion(request);
			
			jspSiguiente=buscarJSPEnMapaNavegacionSegun(rolDespues, 
					accionNavegadorUsuario, resultado);

			//Para comprobar que para llegar a cualquier pagina .jsp pasa antes por el proceso lógico.
			//Mirar comprobarNavegacion.jsp
			request.setAttribute("jspSiguiente", jspSiguiente);

		} catch(PersistenceException e) {
			
			request.getSession().invalidate();
			
			Log.error("Se ha producido alguna excepción relacionada con la persistencia [%s]",
					e.getMessage());
			request.setAttribute("mensajeParaElUsuario", 
					"Error irrecuperable: contacte con el responsable de la aplicación");
			jspSiguiente="/login.jsp";
			
		} catch(Exception e) {
			
			request.getSession().invalidate();
			
			Log.error("Se ha producido alguna excepción no manejada [%s]",
					e.getMessage());
			request.setAttribute("mensajeParaElUsuario", 
					"Error irrecuperable: contacte con el responsable de la aplicación");
			jspSiguiente="/login.jsp";
		}
			
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(jspSiguiente); 
			
		dispatcher.forward(request, response);			
	}			
	
	
	private String obtenerRolDeSesion(HttpServletRequest req) {
		HttpSession sesion=req.getSession();
		if (sesion.getAttribute("user")==null)
			return "ANONIMO";
		else
			if (((User)sesion.getAttribute("user")).getIsAdmin())
				return "ADMIN";
			else
				return "USUARIO";
	}

	// Obtiene un objeto accion en funci�n de la opci�n
	// enviada desde el navegador
	private Accion buscarObjetoAccionParaAccionNavegador(String rol, String opcion) {
		
		Accion accion=mapaDeAcciones.get(rol).get(opcion);
		Log.debug("Elegida acción [%s] para opción [%s] y rol [%s]",accion,opcion,rol);
		return accion;
	}
	
	
	// Obtiene la p�gina JSP a la que habr� que entregar el
	// control el funci�n de la opci�n enviada desde el navegador
	// y el resultado de la ejecuci�n de la acci�n asociada
	private String buscarJSPEnMapaNavegacionSegun(String rol, String opcion, String resultado) {
		
		String jspSiguiente=mapaDeNavegacion.get(rol).get(opcion).get(resultado);
		Log.debug("Elegida página siguiente [%s] para el resultado [%s] tras realizar [%s] con rol [%s]",
				jspSiguiente,resultado,opcion,rol);
		return jspSiguiente;		
	}
		
		
	private void crearMapaAcciones() {
		
		mapaDeAcciones=new HashMap<String,Map<String,Accion>>();
		
		Map<String,Accion> mapaPublico=new HashMap<String,Accion>();
		mapaPublico.put("validarse", new ValidarseAction());
		mapaPublico.put("crearCuenta", new CrearCuentaAction());
		mapaDeAcciones.put("ANONIMO", mapaPublico);
		
		Map<String,Accion> mapaRegistrado=new HashMap<String,Accion>();
		mapaRegistrado.put("modificarEmail", new ModificarEmailAction());
		mapaRegistrado.put("modificarContrasena", new ModificarContraseñaAction());
		mapaRegistrado.put("cerrarSesion", new CerrarSesionAction());
		//Listados tareas
		mapaRegistrado.put("mostrarListados", new ListarCategoriasAction());
		mapaRegistrado.put("listarTareasHoy", new ListarTareasHoyAction());
		mapaRegistrado.put("listarTareasInbox", new ListarTareasInboxAction());
		mapaRegistrado.put("listarTareasCategoria", new ListarTareasCategoriaAction());
		mapaRegistrado.put("marcarFinalizada", new MarcarTareaComoFinalizadaAction());
		mapaRegistrado.put("crearTarea", new CrearTareaAction());
		mapaRegistrado.put("prepararEdicionTarea", new PrepararEdicionTareaAction());
		mapaRegistrado.put("editarTarea", new EditarTareaAction());
		//Categoria opciones
		mapaRegistrado.put("menuCrearCategoria", new ComprobarCategoriaElegidaAction());
		mapaRegistrado.put("menuModificarCategoria", new ComprobarCategoriaElegidaAction());
		mapaRegistrado.put("eliminarCategoria", new BorrarCategoriaAction());
		//Categoría menu
		mapaRegistrado.put("crearCategoria", new CrearCategoriaAction());
		mapaRegistrado.put("modificarCategoria", new ModificarCategoriaAction());
		//Mostrar finalizadas
		mapaRegistrado.put("cambiarFinalizadas", new CambiarFiltroTareasFinalizadasAction());
		mapaRegistrado.put("duplicarCategoria", new DuplicarCategoriaAction());
		mapaDeAcciones.put("USUARIO", mapaRegistrado);
		
		Map<String,Accion> mapaAdmin=new HashMap<String,Accion>();
		mapaAdmin.put("modificarEmail", new ModificarEmailAction());
		mapaAdmin.put("modificarContrasena", new ModificarContraseñaAction());
		mapaAdmin.put("cerrarSesion", new CerrarSesionAction());
		mapaAdmin.put("listarUsuarios", new ListarUsuariosAction());
		mapaAdmin.put("cambiarEstadoUsuario", new CambiarEstadoUsuarioAction());
		//Listados tareas
		mapaAdmin.put("ordenarUsuarios", new ListarCategoriasAction());
		mapaAdmin.put("listarTareasHoy", new ListarTareasHoyAction());
		mapaAdmin.put("listarTareasInbox", new ListarTareasInboxAction());
		mapaAdmin.put("listarTareasCategoria", new ListarTareasCategoriaAction());
		
		mapaAdmin.put("borrarUsuario", new BorrarUsuarioAction());
		mapaAdmin.put("ordenarUsuarios", new EstablecerOrdenUsuariosAction());
		
		mapaDeAcciones.put("ADMIN", mapaAdmin);
	}
	
	
	private void crearMapaDeNavegacion() {
				
		mapaDeNavegacion=new HashMap<String,Map<String, Map<String, String>>>();

		// Crear mapas auxiliares vacíos
		Map<String, Map<String, String>> opcionResultadoYJSP=new HashMap<String, Map<String, String>>();
		Map<String, String> resultadoYJSP=new HashMap<String, String>();

		// Mapa de navegación de anónimo
		resultadoYJSP.put("FRACASO","/login.jsp");
		opcionResultadoYJSP.put("validarse", resultadoYJSP);
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/login.jsp");
		resultadoYJSP.put("FRACASO", "/login.jsp");
		opcionResultadoYJSP.put("cerrarSesion", resultadoYJSP);
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/login.jsp");
		resultadoYJSP.put("FRACASO", "/registro.jsp");
		opcionResultadoYJSP.put("crearCuenta", resultadoYJSP);
		
		mapaDeNavegacion.put("ANONIMO",opcionResultadoYJSP);
		
		// Crear mapas auxiliares vacíos
		opcionResultadoYJSP=new HashMap<String, Map<String, String>>();
		resultadoYJSP=new HashMap<String, String>();
		
		// Mapa de navegación de usuarios normales
		resultadoYJSP.put("EXITO","/principalUsuario.jsp");
		opcionResultadoYJSP.put("validarse", resultadoYJSP);
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/principalUsuario.jsp");
		resultadoYJSP.put("FRACASO","/principalUsuario.jsp");
		opcionResultadoYJSP.put("modificarEmail", resultadoYJSP);
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/principalUsuario.jsp");
		resultadoYJSP.put("FRACASO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("modificarContrasena", resultadoYJSP);
		//Listados tareas (la navegación para todas es similar)
		resultadoYJSP= new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listadosTareas.jsp");
		resultadoYJSP.put("FRACASO", "/listadosTareas.jsp"); //Si falla el cargar los mapas entonces no enseñamos la pagina.
		opcionResultadoYJSP.put("listarTareasHoy", resultadoYJSP);
		opcionResultadoYJSP.put("listarTareasInbox", resultadoYJSP);
		opcionResultadoYJSP.put("mostrarListados", resultadoYJSP);
		opcionResultadoYJSP.put("listarTareasCategoria", resultadoYJSP);
		opcionResultadoYJSP.put("marcarFinalizada", resultadoYJSP);
		opcionResultadoYJSP.put("crearTarea", resultadoYJSP);
		
		resultadoYJSP= new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/editarTarea.jsp");
		resultadoYJSP.put("FRACASO", "/listadosTareas.jsp");
		opcionResultadoYJSP.put("prepararEdicionTarea", resultadoYJSP);
		resultadoYJSP= new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listadosTareas.jsp");
		resultadoYJSP.put("FRACASO", "/editarTarea.jsp");
		opcionResultadoYJSP.put("editarTarea", resultadoYJSP);
		//Categorias opciones
		resultadoYJSP= new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/modificarCategoria.jsp");
		resultadoYJSP.put("FRACASO", "/listadosTareas.jsp");
		opcionResultadoYJSP.put("menuModificarCategoria", resultadoYJSP);
		resultadoYJSP= new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listadosTareas.jsp");
		resultadoYJSP.put("FRACASO", "/listadosTareas.jsp");
		opcionResultadoYJSP.put("eliminarCategoria", resultadoYJSP);
		//Categorias resultados
		resultadoYJSP= new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listadosTareas.jsp");
		resultadoYJSP.put("FRACASO", "/listadosTareas.jsp");
		opcionResultadoYJSP.put("crearCategoria", resultadoYJSP);
		resultadoYJSP= new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listadosTareas.jsp");
		resultadoYJSP.put("FRACASO", "/modificarCategoria.jsp");
		opcionResultadoYJSP.put("modificarCategoria", resultadoYJSP);
		//Mostrar finalizadas
		resultadoYJSP= new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listadosTareas.jsp");
		resultadoYJSP.put("FRACASO", "/listadosTareas.jsp");
		opcionResultadoYJSP.put("cambiarFinalizadas", resultadoYJSP);
		
		resultadoYJSP= new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listadosTareas.jsp");
		resultadoYJSP.put("FRACASO", "/listadosTareas.jsp");
		opcionResultadoYJSP.put("duplicarCategoria", resultadoYJSP);
		
		mapaDeNavegacion.put("USUARIO",opcionResultadoYJSP);
		
		// Mapa de navegación del administrador
		
		opcionResultadoYJSP=new HashMap<String, Map<String, String>>();
		resultadoYJSP=new HashMap<String, String>();
		
		resultadoYJSP.put("EXITO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("validarse",resultadoYJSP);
		
		resultadoYJSP= new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/principalUsuario.jsp");
		resultadoYJSP.put("FRACASO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("modificarEmail", resultadoYJSP);
		opcionResultadoYJSP.put("modificarContrasena", resultadoYJSP);
		
		resultadoYJSP= new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listarUsuarios.jsp");
		resultadoYJSP.put("FRACASO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("listarUsuarios", resultadoYJSP);
		
		resultadoYJSP= new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listarUsuarios.jsp");
		resultadoYJSP.put("FRACASO", "/listarUsuarios.jsp");
		opcionResultadoYJSP.put("cambiarEstadoUsuario", resultadoYJSP);
		opcionResultadoYJSP.put("borrarUsuario", resultadoYJSP);
		opcionResultadoYJSP.put("ordenarUsuarios", resultadoYJSP);
		
		mapaDeNavegacion.put("ADMIN",opcionResultadoYJSP);
	}
			
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {

		doGet(req, res);
	}

}