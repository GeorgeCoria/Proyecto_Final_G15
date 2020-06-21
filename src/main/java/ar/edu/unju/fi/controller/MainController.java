package ar.edu.unju.fi.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class MainController {
	
	@GetMapping("/")
	public String logIn() {
		return "login2";
	}
	
	@GetMapping("/registrador")
	public String registradorVehiculo() {
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
	
	
	
	@GetMapping("/adminPrincipal")
	public String mostrarListaAdmin() {
		
		return "adminPrincipal";
	}
	@GetMapping("/adminFormulario")
	public String mostrarFormAdmin() {
		return "adminFormulario";
	}
}
