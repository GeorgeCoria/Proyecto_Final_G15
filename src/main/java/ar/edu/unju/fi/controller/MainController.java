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
	
	
	@RequestMapping("/login")
	public String login() {
		return "login2";
	}
	
}