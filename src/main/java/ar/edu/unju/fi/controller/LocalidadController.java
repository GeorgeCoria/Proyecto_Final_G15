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

import ar.edu.unju.fi.model.Localidad;
import ar.edu.unju.fi.service.LocalidadServiceImp;

/**
 * @author Velarde Marcia, Toconas Juan
 * 
 */
@Controller
public class LocalidadController {

	@Autowired
	private LocalidadServiceImp localidadService;
	
	@GetMapping("/adminLocalidad")
	public String mostrarLocalidad(Model model) {
		model.addAttribute("titulo", "LOCALIDAD");
		model.addAttribute("localidadForm", new Localidad());
		model.addAttribute("listaLocalidades", localidadService.listarLocalidades());
		model.addAttribute("guardarLocalidad","active");
		return "adminLocalidad";
	}
	
	@PostMapping("/adminLocalidad")
	public String crearLocalidad(@Valid @ModelAttribute("localidadForm") Localidad localidad, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("localidadForm", localidad);
		}else {
			try {
				localidadService.crearLocalidad(localidad);
				model.addAttribute("localidadForm", new Localidad());	
			}catch (Exception e){
				model.addAttribute("formErrorMessage", e.getMessage());
				model.addAttribute("localidadForm", localidad);
			}
		}
		model.addAttribute("listaLocalidades", localidadService.listarLocalidades());
		model.addAttribute("titulo", "LOCALIDAD");
		model.addAttribute("guardarLocalidad", "active");
		model.addAttribute("editMode", "false");
		return "adminLocalidad";
	}
	
	@GetMapping("/editarLoc/cancelar")
	public String cancelar() {
		return "redirect:/adminLocalidad";
	}
	
	@GetMapping("/eliminarLoc/{id}")
	public String eliminar (@PathVariable Long id, Model model) {
		localidadService.eliminarLocalidad(id);
		return "redirect:/adminLocalidad";
	}
	
	@GetMapping("/editarLoc/{id}")
	public String editar(@PathVariable Long id, Model model) throws Exception {
		try{
			Localidad localidadForm=localidadService.buscarLocalidad1(id);
			model.addAttribute("localidadForm", localidadForm);
			model.addAttribute("listaLocalidades", localidadService.listarLocalidades());
		}catch(Exception e) {
			model.addAttribute("formErrorMessage", e.getMessage());
			model.addAttribute("localidadForm", new Localidad());
		}
		model.addAttribute("editarLocalidad", "active");
		model.addAttribute("editMode", "true");
		model.addAttribute("titulo", "EDITAR LOCALIDAD");
		return "adminLocalidad";
	}
	@PostMapping("/editarLocalidad")
	public String editarLocalidad(@Valid @ModelAttribute("localidadForm") Localidad localidad, ModelMap model, BindingResult result) {
		if(result.hasErrors()) {
			model.addAttribute("localidadForm", localidad);
		}try {
			localidadService.editarLocalidad(localidad);
			model.addAttribute("locaidadForm", new Localidad());
			
		}catch(Exception e) {
			model.addAttribute("formErrorMessage", e.getMessage());
			model.addAttribute("localidadForm", localidad);
			model.addAttribute("editarLocalidad", "active");
			model.addAttribute("editMode", "true");
			model.addAttribute("titulo", "EDITAR LOCALIDAD");
		}
		
		model.addAttribute("listaLocalidades", localidadService.listarLocalidades());
		model.addAttribute("titulo", "LOCALIDAD");
		model.addAttribute("guardarLocalidad", "active");
		model.addAttribute("editMode", "false");
		return "adminLocalidad";
	}

}
