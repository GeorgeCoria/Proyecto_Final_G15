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
 * @author Velarde Marcia
 * 
 */
@Controller
public class UsuarioController {

	@Autowired
	private UsuarioServiceImp usuarioService;
	
	//llamada al formulario
	@GetMapping("/adminFormulario")
	public String mostrarFormAdmin(Model model) {
		model.addAttribute("usuarioDelForm", new Usuario());
		return "adminFormulario";
	}
	
	//Evento luego de enviar datos del formulario
	@PostMapping("/adminFormulario") 
	public String crearUsuario(@Valid @ModelAttribute("usuarioDelForm") Usuario usuario, BindingResult result, ModelMap model) {
		//se verifica errores de validacion en hml
		if(result.hasErrors()) {
			model.addAttribute("usuarioDelForm", usuario);
			return "adminFormulario";
		}else {
			try {
				//al crear un usuario por defecto se le da un estado TRUE
				usuario.setEstado(true);
				usuarioService.crear(usuario);
				model.addAttribute("usuarioDelForm", new Usuario());
				model.addAttribute("listaUsuarios", usuarioService.listarUsuarios(true));
				model.addAttribute("usuarioBuscado", new Usuario());
				model.addAttribute("tituloPrincipal", "LISTA DE USUARIOS");
				return "adminPrincipal";
			}catch (Exception e){
				//Acciones si ocurriese algun evento inesperado
				model.addAttribute("formError", e.getMessage());
				model.addAttribute("usuarioDelForm", usuario);
				model.addAttribute("usuarioBuscado", new Usuario());
				return "adminFormulario";
			}
		}
		
		
	}
	
	//Evento activado cunado se quiere editar un usuario, se pasa el id del mismo
	//para luego sus datos se muestren en el formulario para procedes con la 
	//edicion de los datos disponible
	@GetMapping("/editarUsuario/{id}")
	public String obtenerFormularioEditarUsuario(Model model, @PathVariable(name="id") Long id ) throws Exception{
		try{
			Usuario usuarioEncontrado = usuarioService.encontrarUsuario(id);
			model.addAttribute("usuarioDelForm", usuarioEncontrado);
			model.addAttribute("editMode", "true");
			return "adminFormulario";
		}catch(Exception e){
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
			model.addAttribute("usuarioDelForm", usuario);
			model.addAttribute("editMode", "true");
			return "adminFormulario";
		}else {
			try {
					usuarioService.modificar(usuario);
					model.addAttribute("usuarioDelForm", new Usuario());
					model.addAttribute("editMode", "false");
					return "redirect:/adminPrincipal";
				}catch(Exception e) {
					model.addAttribute("formError", e.getMessage());
					model.addAttribute("usuarioDelForm", usuario);
					model.addAttribute("editMode", "true");
					return "adminFormulario";
				}
		}
	}
	
	@GetMapping("/editarUsuario/cancelar")
	public String cancelEditUser() {
		return "redirect:/adminPrincipal";
	}
	
	/*Metodo que gestiona la eliminacion de un Usuario,
	 * sin embargo con eliminacion, nos referimos a que el 
	 * usuario seleccionado para a tener un estado inactivo
	 * Esto implica que no estara disponible en la lista principal
	 * de Usuarios.
	 */
	
	@GetMapping("/eliminarUsuario/{id}")
	public String eliminarUsuario(Model model, @PathVariable(name="id") Long id) {
		try {
			//Usuario usuarioEncontrado = usuarioService.encontrarUsuario(id);
			usuarioService.eliminar(id);
		}catch(Exception e) {
				model.addAttribute("listErrorMessage",e.getMessage());
		}
		return "redirect:/adminPrincipal";
	}
	
	//llamado a la ventana Principal del Usuario administrador
	@GetMapping("/adminPrincipal")
	public String mostrarListaAdmin(Model model) throws Exception {
		try {
			//se listan usuario en estado activo
			model.addAttribute("listaUsuarios", usuarioService.listarUsuarios(true));	
			
		}catch(Exception e){
			model.addAttribute("listErrorMessage", e.getMessage());
		}
		model.addAttribute("tituloPrincipal", "LISTA DE USUARIOS");
		model.addAttribute("principal","active");
		model.addAttribute("usuarioBuscado", new Usuario());
		return "adminPrincipal";
	}
	
	//Muestra una lista de solo Usuario Registradores
	@GetMapping("/Registrador")
	public String mostrarListaRegistrador(Model model) throws Exception {
		try {
			model.addAttribute("listaUsuarios", usuarioService.findByTipoUsuario("REGISTRADOR",true));
		}catch(Exception e) {
			model.addAttribute("listErrorMessage", e.getMessage());
		}
		model.addAttribute("tituloPrincipal", "REGISTRADORES");
		model.addAttribute("registrador","active");
		model.addAttribute("usuarioBuscado", new Usuario());
		return "adminPrincipal";
	}
	
	//Muestra una lista con solo consultores
	@GetMapping("/Consultor")
	public String mostrarListaConsultores(Model model) throws Exception{
		try {
			model.addAttribute("listaUsuarios", usuarioService.findByTipoUsuario("CONSULTOR", true));
		}catch(Exception e) {
			model.addAttribute("listErrorMessage", e.getMessage());
		}
		model.addAttribute("tituloPrincipal", "CONSULTORES");
		model.addAttribute("consultor","active");
		model.addAttribute("usuarioBuscado", new Usuario());
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