package ar.edu.unju.fi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ar.edu.unju.fi.service.ILocalidadService;
import ar.edu.unju.fi.service.LocalidadServiceImp;

@Controller
public class RegistradorController {
	
	@Autowired
	private LocalidadServiceImp localidadService;
	
	@GetMapping("/registrador")
	public String registradorVehiculo(Model model) {
		model.addAttribute("listaLocalidades", localidadService.listarLocalidades());
		return "registradorVehiculos";
	}
	
	@GetMapping("/regvis")
	public String registradorVistas(Model model) {
		model.addAttribute("listaLocalidades", localidadService.listarLocalidades());
		return "registradorVista";
	}

}
