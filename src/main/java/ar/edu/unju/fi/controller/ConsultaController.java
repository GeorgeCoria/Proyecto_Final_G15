/**
 * 
 */
package ar.edu.unju.fi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



/**
 * @author Castillo, Jorge E.
 *
 */

@Controller
public class ConsultaController {


	@GetMapping("/localidad")
	public String getListLocalidad() {
		return "localidad";
	}
	
	@GetMapping("/tripulante")
	public String getListTripulante() {
		return "tripulante";
	}
	
	@GetMapping("/patente")
	public String getListPatente() {
		return "patente";
	}
}
