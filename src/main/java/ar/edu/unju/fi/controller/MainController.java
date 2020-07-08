package ar.edu.unju.fi.controller;

/**
 * Clase que se encargada de tomar las peticiones y controlar que respuesta debe
 * ser presentada en la vista (paginas html: adminPrincipal.html,
 * adminFormulario.html) de nuestro proyecto.
 */


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	/**
	 * Muestra la pagina login ante la peticion /
	 * @return la pagina de login
	 */
	@GetMapping("/")
	public String logIn() {
		return "login2";
	}
	
	/**
	 * Muestra la pagina login ante la peticion /login
	 * @return la pagina de login
	 */
	@RequestMapping("/login")
	public String login() {
		return "login2";
	}
	
	/**
	 * Muestra la pagina sinPermisos usada por webSecurityConfig ante la peticion /sinPermisos  
	 * @return pagina sin permisos
	 */
	@RequestMapping("/sinPermisos")
	public String sinPermisos() {
		return "sinPermisos";
	}
	
}