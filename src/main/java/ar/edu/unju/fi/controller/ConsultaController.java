/**
 * 
 */
package ar.edu.unju.fi.controller;


import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ar.edu.unju.fi.model.Localidad;
import ar.edu.unju.fi.model.RegistroTracking;
import ar.edu.unju.fi.model.Tripulante;
import ar.edu.unju.fi.model.Vehiculo;
import ar.edu.unju.fi.service.ILocalidadService;
import ar.edu.unju.fi.service.IRegistroTrackingService;
import ar.edu.unju.fi.service.ITripulanteService;
import ar.edu.unju.fi.service.IVehiculoService;



/**
 * @author Castillo, Jorge E.
 *
 */

@Controller
public class ConsultaController {


	@Autowired
	private IVehiculoService vehiculoService;
	@Autowired
	private IRegistroTrackingService trackingService;
	@Autowired
	private ITripulanteService tripulanteService;
	@Autowired
	private ILocalidadService localidadService;
	
	
	//-----------------------------------------------//
	//----- Seccion buscarVehiculos (plantilla) -----//
	//-----------------------------------------------//
	

	@GetMapping("/vehiculos")
	public String getListLocalidad(Model modelo) {
	
		//Listando todas las localidades
		Iterable<Localidad> localidades= localidadService.listarLocalidades(true);
		
		modelo.addAttribute("activoLocalidad", false);
		modelo.addAttribute("localidades", localidades);
		modelo.addAttribute("modeloLocalidad", new Localidad());
		
		return "buscarVehiculos";
	}
	
	@PostMapping("/buscarVehiculosPorLocalidadDateTime")
	public String buscarVehiculos(@ModelAttribute("modeloLocalidad") Localidad localidad, 
			 Model modelo) {
		
		Localidad localidadEncontrada= new Localidad();
		List<RegistroTracking> registrosLocalidade= new ArrayList<>();
		List<Tripulante> tripulantesLocalidade= new ArrayList<>();
		
		
		//Lista todas las localidades.
		Iterable<Localidad> localidades= localidadService.listarLocalidades(true);
		modelo.addAttribute("localidades", localidades);
		
		
		try {
			
			//Busca la localidad selecionada
			localidadEncontrada = localidadService.buscarNombreLocalidad(localidad.getNombre());
			
			//Busca el registro de la localidad seleccionada
			registrosLocalidade = trackingService.getRegistrosLocalidad(localidadEncontrada);
			
			//Revisa si esta vacia
			if(registrosLocalidade.isEmpty()){
				
				modelo.addAttribute("errorMessage", "La localidad seleccionada no tiene registro");
				modelo.addAttribute("activoLocalidad", false);
				
				return "buscarVehiculos";
			}
			
			//Guarda las listas de los tripulantes de la localidad seleccionada
			for ( RegistroTracking n : registrosLocalidade) {
				
				tripulantesLocalidade.addAll(n.getTripulantes());
			}
			
			//Envia el objeto de las listas de los tripulantes de la localidad seleccionada
			modelo.addAttribute("tripulantesLocalidad", tripulantesLocalidade);
			
			//Envia el objeto de los registros de la localidad seleccionada
			modelo.addAttribute("registrosLocalidad", registrosLocalidade);
			
			//Envia el id del registro (null)
			modelo.addAttribute("idRegistro", "-");
			
		} catch (Exception e) {
			
			modelo.addAttribute("errorMessage", "La localidad seleccionada no exite");
			modelo.addAttribute("activoLocalidad", false);
			
			return "buscarVehiculos";
		}

		
		//Activa los contenedores
		modelo.addAttribute("activoLocalidad", true);
		return "buscarVehiculos";
	}
	

	@GetMapping("/buscar/{id}")		
	public String buscarRegistroId(@PathVariable Long id, Model modelo) {
		
		RegistroTracking registroTracking = new RegistroTracking();
		List<Tripulante> registroTripulantes = new ArrayList<>();
		List<RegistroTracking> registrosLocalidade= new ArrayList<>();
		Localidad localidadEncontrada= new Localidad();
		
		try {
			
			//Busca el ID del registro
			registroTracking= trackingService.getRegistros(id);
			
			//Busca la localidad selecionada (es necesario por el ID)
			localidadEncontrada = localidadService.buscarNombreLocalidad(registroTracking.getLocalidad().getNombre());
			
		} catch (Exception e) {
			
			modelo.addAttribute("errorMessege", e.getMessage());
			modelo.addAttribute("activoLocalidad", false);
			return "buscarVehiculos";
		}
		
		//Busca el registro de la localidad seleccionada
		registrosLocalidade = trackingService.getRegistrosLocalidad(localidadEncontrada);
		
		if(registrosLocalidade.isEmpty()){
			
			modelo.addAttribute("errorMessage", "La localidad seleccionada no tiene registro");
			modelo.addAttribute("activoLocalidad", false);
			
			return "buscarVehiculos";
		}
		
		registroTripulantes.addAll(registroTracking.getTripulantes());
		
		//Envia el objeto del listado de los tripulantes de la localidad seleccionada
		modelo.addAttribute("tripulantesLocalidad", registroTripulantes);
		
		//Lista todas las localidades.
		Iterable<Localidad> localidades= localidadService.listarLocalidades(true);
		modelo.addAttribute("localidades", localidades);
		
		//Objeto para capturar el nombre de la localidad
		modelo.addAttribute("modeloLocalidad", new Localidad());
		
		//Envia los registros de la localidad seleccionada
		modelo.addAttribute("registrosLocalidad", registrosLocalidade);
		
		//Envia el id del registro
		modelo.addAttribute("idRegistro", id);
		
		//Activa los contenedores
		modelo.addAttribute("activoLocalidad", true);
		
		return "buscarVehiculos";
	}
	
	
	

	//------------------------------------------------//
	//----- Seccion buscarTripulante (plantilla) -----//
	//------------------------------------------------//
	
	@GetMapping("/tripulante")
	public String buscarTripulante(Model modelo) {
		
		modelo.addAttribute("modeloTripulante", new Tripulante());
		modelo.addAttribute("activoTripulante", false);
		
		return "buscarTripulante";
	}
	
	@PostMapping("/searchTripulante")
	public String getTripulante(@Valid @ModelAttribute("modeloTripulante") Tripulante tripulante, 
			BindingResult result, Model modelo) {
		
		Tripulante tripulanteEncontrado= new Tripulante();
		
		//Validando
		if (result.hasErrors() == false)
		{
			try {
				
				//Buscando Tripulante por documento.
				tripulanteEncontrado= tripulanteService.buscarTripulante(tripulante.getDocumento());
				
			} catch (Exception e) {
				
				modelo.addAttribute("activoTripulante", false);
				modelo.addAttribute("errorMessage", "El numero de documento no existe");
				
				return "buscarTripulante";
			}
			
			
			//Envia los datos del Tripulante.
			modelo.addAttribute("datoTripulante", tripulanteEncontrado);
			//Envia los registros del Tripulante
			modelo.addAttribute("registroTripulante", trackingService.getRegistrosTripulante(tripulanteEncontrado)); 
			
			//Activa los contenedores.
			modelo.addAttribute("activoTripulante", true);
		}
		
		
		
		
		return "buscarTripulante";
	}
	
	//---------------------------------------------//
	//----- Seccion buscarPatente (plantilla) -----//
	//---------------------------------------------//
	
	@GetMapping("/patente")
	public String buscarPatente( Model modelo) {
		
		modelo.addAttribute("modeloVehiculo", new Vehiculo());
		modelo.addAttribute("activoVehiculo", false);
		return "buscarPatente";
	}
	
	@PostMapping("/searchPatente")
	public String getPatente (@Valid @ModelAttribute("modeloVehiculo") Vehiculo vehiculo, 
			BindingResult result, Model modelo) {
		
		Vehiculo vehiculoEncontrado= new Vehiculo();
		List<Tripulante> vehiculoTripulantes= new ArrayList<>();
		List<RegistroTracking> registroVehiculo= new ArrayList<>();
		
		//Validando
		if(result.hasErrors() == false) {
		
			try {
				
				//Buscando la patente del Vehiculo
				vehiculoEncontrado= vehiculoService.buscarVehiculo(vehiculo.getPatente());
					
			} catch (Exception e) {
				
				modelo.addAttribute("activoVehiculo", false);
				modelo.addAttribute("errorMessage", "La patente ingresada no existe");
				
				return "buscarPatente";
			}
		
			//Busca los registros del vehiculoEncontrado
			registroVehiculo=trackingService.getRegistrosVehiculo(vehiculoEncontrado);
				
			//Se asigna la listas de los Tripulantes del Registro de Vehiculo buscado.
			for ( RegistroTracking n : registroVehiculo)
			{
				vehiculoTripulantes.addAll(n.getTripulantes());
			}
				
			//Envia los datos del Vehiculo
			modelo.addAttribute("vehiculo", vehiculoEncontrado);
			//Envia los registros del Vehiculo 
			modelo.addAttribute("registroVehiculo",registroVehiculo);
			//Envia los registros de los Tripulantes del Vehiculo buscado
			modelo.addAttribute("tripulanteVehiculo", vehiculoTripulantes);
				
				
			//Activa los contenedores
			modelo.addAttribute("activoVehiculo", true);
		}
		
		return "buscarPatente";
	}
	
	
	//----------------------------------------------//
	//----- Seccion buscarVehiculos ( PARCHE ) -----//
	//----------------------------------------------//
	
	@GetMapping("/buscar/vehiculos")
	public String getWebVehiculo () {
				
		return "redirect:/vehiculos";
	}
	
	@GetMapping("/buscar/patente")
	public String getWebPatente () {
				
		return "redirect:/patente";
	}
	
	@GetMapping("/buscar/tripulante")
	public String getWebTripulante () {
				
		return "redirect:/tripulante";
	}
	
	
	
}
