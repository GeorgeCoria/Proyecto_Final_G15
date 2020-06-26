package ar.edu.unju.fi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ar.edu.unju.fi.model.Localidad;
import ar.edu.unju.fi.service.LocalidadServiceImp;

@Controller
public class LocalidadController {

	@Autowired
	private LocalidadServiceImp localidadService;
	
	@GetMapping("/adminLocalidad")
	public String mostrarLocalidad(Model model) {
		model.addAttribute("localidadForm", new Localidad());
		model.addAttribute("listaLocalidades", localidadService.listarLocalidades());
		return "adminLocalidad";
	}
	
	@PostMapping("/adminLocalidad")
	public String crearLocalidad(@ModelAttribute("localidadForm") Localidad localidad, ModelMap model) {
		localidadService.crearLocalidad(localidad);
		model.addAttribute("localidadForm", new Localidad());
		model.addAttribute("listaLocalidades", localidadService.listarLocalidades());
		return "adminLocalidad";
	}
	
	@GetMapping("/eliminarLoc/{id}")
	public String eliminar (@PathVariable Long id, Model model) {
		localidadService.eliminarLocalidad(id);
		return "redirect:/adminLocalidad";
	}
	
	@GetMapping("/editarLoc/{id}")
	public String editar(@PathVariable Long id, Model model) {
		Optional<Localidad>localidadForm=localidadService.EditarLoc(id);
		model.addAttribute("localidadForm", localidadForm);
		return "adminLocalidad";
	}

	
	
	
}
