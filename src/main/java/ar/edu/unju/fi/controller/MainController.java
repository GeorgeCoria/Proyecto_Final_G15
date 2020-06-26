package ar.edu.unju.fi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ar.edu.unju.fi.service.ILocalidadService;



@Controller
public class MainController {
	
	@Autowired
	private ILocalidadService localidadService;
	
	@GetMapping("/")
	public String logIn() {
		return "login2";
	}
	
	@GetMapping("/registrador")
	public String registradorVehiculo(Model model) {
		model.addAttribute("listaLocalidades", localidadService.listarLocalidades());
		return "registradorVehiculos";
	}
	
	@GetMapping("/registrador2")
	public String RegistradorUsuario() {
		return "registradorusuario";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login2";
	}
	
}