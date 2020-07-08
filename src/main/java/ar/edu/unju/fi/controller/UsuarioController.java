package ar.edu.unju.fi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ar.edu.unju.fi.model.Usuario;
import ar.edu.unju.fi.service.UsuarioServiceImp;

/**
 * Clase que se encargada de tomar las peticiones y controlar que respuesta debe
 * ser presentada en la vista (paginas html: adminPrincipal.html,
 * adminFormulario.html) de nuestro proyecto.
 * 
 * @author Velarde Marcia
 * 
 */
@Controller
public class UsuarioController {

	@Autowired
	private UsuarioServiceImp usuarioService;
	
	//Anotacion asociada a la url (/adminFormulario) que se acciona al ingresar a dicha url
	@GetMapping("/adminFormulario")
	public String mostrarFormAdmin(Model model) {
		//Se manda a la vista un objeto de tipo Usuario vacio
		model.addAttribute("usuarioDelForm", new Usuario());
		return "adminFormulario";
	}
	
	//Evento luego de enviar datos del formulario
	@PostMapping("/adminFormulario") 
	public String crearUsuario(@Valid @ModelAttribute("usuarioDelForm") Usuario usuario, BindingResult result, ModelMap model) {
		//Se verifica errores de validacion en hml
		if(result.hasErrors()) {
			//Se manda a la vista el objeto usuario cargado con los datos que ingreso el usuario bd
			model.addAttribute("usuarioDelForm", usuario);
			return "adminFormulario";
		}else {
			try {
				//Al crear un usuario por defecto se le da un estado TRUE
				usuario.setEstado(true);
				//Se guarda en la base de datos el usuario agregado
				usuarioService.crear(usuario);
				return "redirect:/adminPrincipal";
			}catch (Exception e){
				//Acciones si ocurriese algun evento inesperado
				model.addAttribute("formError", e.getMessage());
				model.addAttribute("usuarioDelForm", usuario);
				return "adminFormulario";
			}
		}	
	}
	
	//Evento activado cunado se quiere editar un usuario, se pasa el id del mismo
	//para luego sus datos se muestren en el formulario para proceder con la 
	//edicion de los datos disponibles
	@GetMapping("/editarUsuario/{id}")
	public String obtenerFormularioEditarUsuario(Model model, @PathVariable(name="id") Long id ) throws Exception{
		try{
			//Se guarda en un objeto de tipo Usuario el usuario encontrado por el id
			Usuario usuarioEncontrado = usuarioService.encontrarUsuario(id);
			//Se manda a la vista los datos editables del usuario encontrado 
			model.addAttribute("usuarioDelForm", usuarioEncontrado);
			model.addAttribute("editMode", "true");
			return "adminFormulario";
		}catch(Exception e){
			//Acciones si ocurriese algun evento inesperado
			model.addAttribute("formError",e.getMessage());
			model.addAttribute("usuarioDelForm", new Usuario());
			model.addAttribute("editMode", "false");
			return "adminFormulario";
		}
	}
	
	//Terminada la edicion se envian los datos del Formulario y se aciva este llamado
	//para asi, guardar los cambios establecidos
	@PostMapping("/editarUsuario")
	public String postEditarUsuario(@Valid @ModelAttribute("usuarioDelForm") Usuario usuario, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			//Si ocurre un error se manda a la vista de nuevo los datos del usuario
			model.addAttribute("usuarioDelForm", usuario);
			model.addAttribute("editMode", "true");
			return "adminFormulario";
		}else {
			try {
				//Se guardan los cambios en los datos del usuario
				usuarioService.modificar(usuario);
				model.addAttribute("editMode", "false");
				return "redirect:/adminPrincipal";
			}catch(Exception e) {
				//Acciones si ocurriese algun evento inesperado
				model.addAttribute("formError", e.getMessage());
				model.addAttribute("usuarioDelForm", usuario);
				model.addAttribute("editMode", "true");
				return "adminFormulario";
				}
		}
	}
	
	//Se acciona con el boton CANCELAR
	@GetMapping("/editarUsuario/cancelar")
	public String cancelEditUser() {
		return "redirect:/adminPrincipal";
	}
	
	/* Metodo que gestiona la eliminacion de un Usuario,
	 * sin embargo con eliminacion, nos referimos a que el 
	 * usuario seleccionado para a tener un estado inactivo
	 * Esto implica que no estara disponible en la lista principal
	 * de Usuarios.
	 */
	@GetMapping("/eliminarUsuario/{id}")
	public String eliminarUsuario(Model model, @PathVariable(name="id") Long id) {
		try {
			usuarioService.eliminar(id);
		}catch(Exception e) {
				model.addAttribute("listErrorMessage",e.getMessage());
		}
		return "redirect:/adminPrincipal";
	}
	
	//Llamado a la ventana Principal del Usuario administrador
	@GetMapping("/adminPrincipal")
	public String mostrarListaAdmin(Model model) throws Exception {
		try {
			//Se listan los usuarios en estado activo
			model.addAttribute("listaUsuarios", usuarioService.listarUsuarios(true));
		}catch(Exception e){
			model.addAttribute("listErrorMessage", e.getMessage());
		}
		model.addAttribute("tituloPrincipal", "LISTA DE USUARIOS");
		//Se activa la solapa "principal"
		model.addAttribute("principal","active");
		//Se limpia el input de busqueda por nombre de usuario
		model.addAttribute("usuarioBuscado", new Usuario());
		return "adminPrincipal";
	}
	
	//Muestra una lista con solo usuarios Registradores
	@GetMapping("/Registrador")
	public String mostrarListaRegistrador(Model model) throws Exception {
		try {
			model.addAttribute("listaUsuarios", usuarioService.findByTipoUsuario("REGISTRADOR",true));
		}catch(Exception e) {
			model.addAttribute("listErrorMessage", e.getMessage());
		}
		model.addAttribute("tituloPrincipal", "REGISTRADORES");
		model.addAttribute("registrador","active");
		return "adminPrincipal";
	}
	
	//Muestra una lista con solo usuarios consultores
	@GetMapping("/Consultor")
	public String mostrarListaConsultores(Model model) throws Exception{
		try {
			model.addAttribute("listaUsuarios", usuarioService.findByTipoUsuario("CONSULTOR", true));
		}catch(Exception e) {
			model.addAttribute("listErrorMessage", e.getMessage());
		}
		model.addAttribute("tituloPrincipal", "CONSULTORES");
		model.addAttribute("consultor","active");
		return "adminPrincipal";
	}
	
	/*
	 * Este evento actua cuando se hace una busqueda de Usuarios
	 * la busqueda la hace en toda la lista de Usuarios, es decir,
	 * que si el usuario buscado esta deshabilitado se mostrara de 
	 * todas formas
	 */
	@PostMapping("/adminBusqueda")
	public String buscarUsuario(@ModelAttribute("usuarioBuscado") Usuario usuario, ModelMap model) throws Exception {
		if(usuario.getNombreUsuario().length()==0) {
			model.addAttribute("errorBusqueda","Ingrese Nombre de Usuario para realizar la Busqueda" );
		}else {
			try {
			Usuario buscado=usuarioService.buscarUsuario(usuario.getNombreUsuario());
			model.addAttribute("listaUsuarios",buscado);
		
			}
			catch(Exception e) {
				model.addAttribute("errorBusqueda",e.getMessage());
			}
		}
		model.addAttribute("tituloPrincipal", "RESULTADO DE BUSQUEDA");
		model.addAttribute("busqueda", "active");
		model.addAttribute("principal","active");
		model.addAttribute("usuarioBuscado", new Usuario());
		return "adminPrincipal";
		
		
		}
	//muesta una lista con aquellos Usuarios que hayan sido eliminados de la
	//lista principal. Usuario inactivos
	@GetMapping("/Suspendido")
	public String mostrarBajas(Model model) {
		try {
			model.addAttribute("listaUsuarios",usuarioService.listarUsuarios(false));
			
		}catch(Exception e) {
			model.addAttribute("listErrorMessage", e.getMessage());
		}
		model.addAttribute("suspendido","active");
		model.addAttribute("tituloPrincipal", "USUARIOS INACTIVOS");
		return "adminPrincipal";
	}
	
	
}