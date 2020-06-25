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


	@GetMapping("/vehiculo")
	public String getListLocalidad() {
		
		return "buscarVehiculo";
	}
	
	@GetMapping("/tripulante")
	public String getListTripulante() {
		return "buscarTripulante";
	}
	
	@GetMapping("/patente")
	public String getListPatente() {
		return "buscarPatente";
	}
}
