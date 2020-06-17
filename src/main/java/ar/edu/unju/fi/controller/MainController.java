package ar.edu.unju.fi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class MainController {
	

	@RequestMapping("/registrador")
	public String main(Model model) {
		return "registrador";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/consultas")
	public String getList() {
		return "consultas";
	}
	
	@GetMapping("/listaDeUsuarios")
	public String mostrarListaAdmin() {
		
		return "listaTiposDeUsuarios";
	}
	@GetMapping("/ventanaAdmin")
	public String mostrarFormAdmin() {
		return "agregarEditarUsuario";
	}
}
