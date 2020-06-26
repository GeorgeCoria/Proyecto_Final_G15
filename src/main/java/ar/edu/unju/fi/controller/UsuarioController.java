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
				return "adminPrincipal";
			}catch (Exception e){
				model.addAttribute("formError", e.getMessage());
				model.addAttribute("usuarioDelForm", usuario);
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
	public String mostrarListaAdmin(Model model) {
		model.addAttribute("listaUsuarios", usuarioService.listarUsuarios());
		return "adminPrincipal";
	}
	
	@GetMapping("/adminRegistrador")
	public String mostrarRegistrador(Model model) {
		model.addAttribute("listaUsuariosReg", usuarioService.findByTipoUsuario("REGISTRADOR"));
		return "adminRegistrador";
	}
	@GetMapping("/adminConsultor")
	public String mostrarConsultor(Model model) {
		model.addAttribute("listaUsuariosCon", usuarioService.findByTipoUsuario("CONSULTOR"));
		return "adminConsultor";
	}
}