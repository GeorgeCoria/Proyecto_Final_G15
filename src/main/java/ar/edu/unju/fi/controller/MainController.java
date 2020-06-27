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
	
	@RequestMapping("/login")
	public String login() {
		return "login2";
	}
	
}