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

			//Para comprobarque para legar a cualquier pagina .jsp se pasa antes por el proceso lógico.
			//Mirar comproarNavegavion.jsp
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
		mapaRegistrado.put("modificarDatos", new ModificarDatosAction());
		mapaRegistrado.put("modificarContraseña", new ModificarContraseñaAction());
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
		mapaDeAcciones.put("USUARIO", mapaRegistrado);
		
		Map<String,Accion> mapaAdmin=new HashMap<String,Accion>();
		mapaAdmin.put("modificarDatos", new ModificarDatosAction());
		mapaAdmin.put("modificarContraseña", new ModificarContraseñaAction());
		mapaAdmin.put("cerrarSesion", new CerrarSesionAction());
		mapaAdmin.put("listarUsuarios", new ListarUsuariosAction());
		mapaAdmin.put("cambiarEstadoUsuario", new CambiarEstadoUsuarioAction());
		//Listados tareas
		mapaAdmin.put("mostrarListados", new ListarCategoriasAction());
		mapaAdmin.put("listarTareasHoy", new ListarTareasHoyAction());
		mapaAdmin.put("listarTareasInbox", new ListarTareasInboxAction());
		mapaAdmin.put("listarTareasCategoria", new ListarTareasCategoriaAction());
		
		mapaAdmin.put("borrarUsuario", new BorrarUsuarioAction());
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
		opcionResultadoYJSP.put("modificarDatos", resultadoYJSP);
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/principalUsuario.jsp");
		resultadoYJSP.put("FRACASO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("modificarContraseña", resultadoYJSP);
		//Listados tareas
		resultadoYJSP= new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listadosTareas.jsp");
		resultadoYJSP.put("FRACASO", "/listadosTareas.jsp"); //Si falla el cargar los mapas entonces no enseñamos la pagina.
		opcionResultadoYJSP.put("listarTareasHoy", resultadoYJSP);
		resultadoYJSP= new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listadosTareas.jsp");
		resultadoYJSP.put("FRACASO", "/listadosTareas.jsp"); //Si falla el cargar los mapas entonces no enseñamos la pagina.
		opcionResultadoYJSP.put("listarTareasInbox", resultadoYJSP);
		resultadoYJSP= new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listadosTareas.jsp");
		resultadoYJSP.put("FRACASO", "/listadosTareas.jsp"); //Quizas deberia devolverle a la principal.
		opcionResultadoYJSP.put("mostrarListados", resultadoYJSP);
		resultadoYJSP= new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listadosTareas.jsp");
		resultadoYJSP.put("FRACASO", "/listadosTareas.jsp"); //Quizas deberia devolverle a la principal.
		opcionResultadoYJSP.put("listarTareasCategoria", resultadoYJSP);
		resultadoYJSP= new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listadosTareas.jsp");
		resultadoYJSP.put("FRACASO", "/listadosTareas.jsp");
		opcionResultadoYJSP.put("marcarFinalizada", resultadoYJSP);
		resultadoYJSP= new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listadosTareas.jsp");	//TODO comprobar esto
		opcionResultadoYJSP.put("crearTarea", resultadoYJSP);
		resultadoYJSP= new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/editarTarea.jsp");
		resultadoYJSP.put("FRACASO", "/listadosTareas.jsp");
		opcionResultadoYJSP.put("prepararEdicionTarea", resultadoYJSP);
		resultadoYJSP= new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listadosTareas.jsp");
		resultadoYJSP.put("FRACASO", "/editarTarea.jsp");
		opcionResultadoYJSP.put("editarTarea", resultadoYJSP);
		
		mapaDeNavegacion.put("USUARIO",opcionResultadoYJSP);
		
		// Mapa de navegación del administrador
		
		opcionResultadoYJSP=new HashMap<String, Map<String, String>>();
		resultadoYJSP=new HashMap<String, String>();
		
		resultadoYJSP.put("EXITO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("validarse",resultadoYJSP);
		resultadoYJSP= new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/principalUsuario.jsp");
		resultadoYJSP.put("FRACASO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("modificarDatos", resultadoYJSP);
		resultadoYJSP= new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/principalUsuario.jsp");
		resultadoYJSP.put("FRACASO", "/principalUsuario.jsp");
		opcionResultadoYJSP.put("modificarContraseña", resultadoYJSP);
		resultadoYJSP= new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listarUsuarios.jsp");
		resultadoYJSP.put("FRACASO", "/principalUsuario.jsp"); //Si falla el cargar los mapas entonces no enseñamos la pagina.
		opcionResultadoYJSP.put("listarUsuarios", resultadoYJSP);
		resultadoYJSP= new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listarUsuarios.jsp");
		resultadoYJSP.put("FRACASO", "/listarUsuarios.jsp"); //Le deja en la misma pagina sin cambiar nada.
		opcionResultadoYJSP.put("cambiarEstadoUsuario", resultadoYJSP);
		resultadoYJSP= new HashMap<String, String>();
		resultadoYJSP.put("EXITO", "/listarUsuarios.jsp");
		resultadoYJSP.put("FRACASO", "/listarUsuarios.jsp"); //Si falla el cargar los mapas entonces no enseñamos la pagina.
		opcionResultadoYJSP.put("borrarUsuario", resultadoYJSP);
		//listados tareas
//		//Al empezar
//		resultadoYJSP= new HashMap<String, String>();
//		resultadoYJSP.put("EXITO", "/listarUsuarios.jsp");
//		resultadoYJSP.put("FRACASO", "/listarUsuarios.jsp"); //Si falla el cargar los mapas entonces no enseñamos la pagina.
//		opcionResultadoYJSP.put("mostrarListados", resultadoYJSP);
//		//Pidiendo las de hoy
//		resultadoYJSP= new HashMap<String, String>();
//		resultadoYJSP.put("EXITO", "/listadosTareas.jsp");
//		resultadoYJSP.put("FRACASO", "/listadosTareas.jsp"); //Si falla el cargar los mapas entonces no enseñamos la pagina.
//		opcionResultadoYJSP.put("listarTareasHoy", resultadoYJSP);
//		//Pidiendo las inbox
//		resultadoYJSP= new HashMap<String, String>();
//		resultadoYJSP.put("EXITO", "/listadosTareas.jsp");
//		resultadoYJSP.put("FRACASO", "/listadosTareas.jsp"); 
//		opcionResultadoYJSP.put("listarTareasInbox", resultadoYJSP);
//		//Pidiendo las de una cierta categoria
//		resultadoYJSP= new HashMap<String, String>();
//		resultadoYJSP.put("EXITO", "/listadosTareas.jsp");
//		resultadoYJSP.put("FRACASO", "/listadosTareas.jsp"); //Quizas deberia devolverle a la principal.
//		opcionResultadoYJSP.put("mostrarTareasCategoria", resultadoYJSP);
		
		
		mapaDeNavegacion.put("ADMIN",opcionResultadoYJSP);
	}
			
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {

		doGet(req, res);
	}

}