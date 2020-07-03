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
	
	@GetMapping("/adminFormulario")
	public String mostrarFormAdmin(Model model) {
		model.addAttribute("usuarioDelForm", new Usuario());
		return "adminFormulario";
	}
	
	@PostMapping("/adminFormulario") 
	public String crearUsuario(@Valid @ModelAttribute("usuarioDelForm") Usuario usuario, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("usuarioDelForm", usuario);
			return "adminFormulario";
		}else {
			try {
				usuarioService.crear(usuario);
				model.addAttribute("usuarioDelForm", new Usuario());
				model.addAttribute("listaUsuarios", usuarioService.listarUsuarios());
				model.addAttribute("usuarioBuscado", new Usuario());
				return "adminPrincipal";
			}catch (Exception e){
				model.addAttribute("formError", e.getMessage());
				model.addAttribute("usuarioDelForm", usuario);
				model.addAttribute("usuarioBuscado", new Usuario());
				return "adminFormulario";
			}
		}
	}
			
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
	
	@GetMapping("/eliminarUsuario/{id}")
	public String eliminarUsuario(Model model, @PathVariable(name="id") Long id) {
		try {
			usuarioService.eliminar(id);
		}catch(Exception e) {
				model.addAttribute("listErrorMessage",e.getMessage());
		}
		return "redirect:/adminPrincipal";
	}
	
	@GetMapping("/adminPrincipal")
	public String mostrarListaAdmin(Model model) throws Exception {
		try {
			model.addAttribute("listaUsuarios", usuarioService.listarUsuarios());	
			
		}catch(Exception e){
			model.addAttribute("listErrorMessage", e.getMessage());
		}
		model.addAttribute("principal","active");
		model.addAttribute("usuarioBuscado", new Usuario());
		return "adminPrincipal";
	}
	
	@GetMapping("/adminPrincipal/Registrador")
	public String mostrarListaRegistrador(Model model) throws Exception {
		try {
			model.addAttribute("listaUsuarios", usuarioService.findByTipoUsuario("REGISTRADOR"));
		}catch(Exception e) {
			model.addAttribute("listErrorMessage", e.getMessage());
		}
		
		model.addAttribute("registrador","active");
		model.addAttribute("usuarioBuscado", new Usuario());
		return "adminPrincipal";
	}
	
	@GetMapping("/adminPrincipal/Consultor")
	public String mostrarListaConsultores(Model model) throws Exception{
		try {
			model.addAttribute("listaUsuarios", usuarioService.findByTipoUsuario("CONSULTOR"));
		}catch(Exception e) {
			model.addAttribute("listErrorMessage", e.getMessage());
		}
		
		model.addAttribute("consultor","active");
		model.addAttribute("usuarioBuscado", new Usuario());
		return "adminPrincipal";
	}
	
	
	@PostMapping("/adminBusqueda")
	public String buscarUsuario(@ModelAttribute("usuarioBuscado") Usuario usuario, ModelMap model) throws Exception {
		if(usuario.getNombreUsuario().length()==0) {
			model.addAttribute("errorBusqueda","Ingrese Nombre de Usuario para realizar la Busqueda" );
		}else {
			try {
			Usuario buscado=usuarioService.buscarUsuario(usuario.getNombreUsuario());
			model.addAttribute("listaUsuarios",buscado);
		
			}catch(Exception e) {
				model.addAttribute("errorBusqueda",e.getMessage());
				
			}
		}
		
	model.addAttribute("usuarioBuscado", new Usuario());
	return "adminPrincipal";	
	}
	
	
}