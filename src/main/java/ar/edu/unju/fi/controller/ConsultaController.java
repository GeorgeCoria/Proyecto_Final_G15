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
import ar.edu.unju.fi.utils.ConsultaVehiculo;



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
		modelo.addAttribute("localidades", localidades);
		
		//Cajas que muestra el resultado de la busqueda
		modelo.addAttribute("activo", false);
		modelo.addAttribute("modeloConsulta", new ConsultaVehiculo());
		
		return "buscarVehiculos";
	}
	
	@PostMapping("/buscarVehiculosPorRangoFechaAndLocalidad")
	public String buscarVehiculos(@Valid @ModelAttribute("modeloConsulta") ConsultaVehiculo consultaVehiculo, 
			BindingResult result, Model modelo) {
		
		//Objeto que se le assignara cuando encuentre localidad
		Localidad localidadEncontrada= new Localidad();
				
		//Objeto que se le asignara mas adelante, los registro de la localidad seleccionada y rango de fechas.
		List<RegistroTracking> registrosLocalidade= new ArrayList<>();
				
		//Objeto que se le asignara los tripulante del registro buscado por localidad y rango de fechas.
		List<Tripulante> tripulantesLocalidade= new ArrayList<>();
		
		
		//Lista todas las localidades.
		Iterable<Localidad> localidades= localidadService.listarLocalidades(true);
		modelo.addAttribute("localidades", localidades);
		
		//Validando
		if(result.hasErrors() == false) {
			try {
				
				//Busca el objeto de la localidad selecionada
				localidadEncontrada = localidadService.buscarNombreLocalidad(consultaVehiculo.getLocalidad().getNombre());
				
				//Busca el registro de la localidad seleccionada y rango de fechas.
				registrosLocalidade = trackingService.getRegistrosRangoFechasAndLocalidad
						(consultaVehiculo.getFechaDesde(), consultaVehiculo.getFechaHasta(), localidadEncontrada);
				
				//Revisa si esta vacia
				if(registrosLocalidade.isEmpty()){
					
					modelo.addAttribute("errorMessage", "La localidad o el rango de fechas seleccionados no tiene registro");
					//Cajas que muestra el resultado de la busqueda
					modelo.addAttribute("activo", false);
					
					return "buscarVehiculos";
				}
				
				//Guarda las listas de los tripulantes de la localidad seleccionada
				for ( RegistroTracking n : registrosLocalidade) {
					
					tripulantesLocalidade.addAll(n.getTripulantes());
				}
				
				//Envia el objeto de las listas de los tripulantes de la localidad seleccionada y rango de fechas.
				modelo.addAttribute("tripulantesLocalidad", tripulantesLocalidade);
				
				//Envia el objeto de los registros de la localidad seleccionada y rango de fechas.
				modelo.addAttribute("registrosLocalidad", registrosLocalidade);
				
				//Envia el id del registro (No es necesario ahora, pero para q no se vea vacio)
				modelo.addAttribute("idRegistro", "-");
				
			} catch (Exception e) {
				
				modelo.addAttribute("errorMessage", "La localidad seleccionada no exite");
				//Cajas que muestra el resultado de la busqueda
				modelo.addAttribute("activo", false);
				
				return "buscarVehiculos";
			}
			
			//Cajas que muestra el resultado de la busqueda
			modelo.addAttribute("activo", true);
		}
		
		
		return "buscarVehiculos";
	}
	

	@GetMapping("/buscar/vehiculo/{id}")		
	public String buscarRegistro(@PathVariable Long id, Model modelo) {
		
		//Objeto que almacenara el registro del id enviado
		RegistroTracking registroVehiculo = new RegistroTracking();
		
		//Objeto que almacenara los tripulantes del registro buscado por id
		List<Tripulante> registroTripulantes = new ArrayList<>();
		
		List<RegistroTracking> registrosLocalidade= new ArrayList<>();
		Localidad localidadEncontrada= new Localidad();
		
		//Lista todas las localidades.
		Iterable<Localidad> localidades= localidadService.listarLocalidades(true);
		modelo.addAttribute("localidades", localidades);
				
		try {
			
			//Busca el ID del registro
			registroVehiculo= trackingService.getRegistros(id);
			
			//Busca la localidad selecionada (es necesario por el ID)
			localidadEncontrada = localidadService.buscarNombreLocalidad(registroVehiculo.getLocalidad().getNombre());
			
		} catch (Exception e) {
			
			modelo.addAttribute("errorMessege", "La localidad o el rango de fechas seleccionados no tiene registro");
			//Cajas que muestra el resultado de la busqueda
			modelo.addAttribute("activo", false);
			
			//Objeto para capturar el nombre de la localidad
			modelo.addAttribute("modeloConsulta", new ConsultaVehiculo());
			
			return "buscarVehiculos";
		}
		
		//Busca el registro de la localidad seleccionada
		registrosLocalidade = trackingService.getRegistrosLocalidad(localidadEncontrada);
		
		if(registrosLocalidade.isEmpty()){
			
			modelo.addAttribute("errorMessage", "La localidad seleccionada no tiene registro");
			//Cajas que muestra el resultado de la busqueda
			modelo.addAttribute("activo", false);
			
			//Objeto para capturar el nombre de la localidad
			modelo.addAttribute("modeloConsulta", new ConsultaVehiculo());
			return "buscarVehiculos";
		}
		
		registroTripulantes.addAll(registroVehiculo.getTripulantes());
		
		//Envia el objeto del listado de los tripulantes de la localidad seleccionada
		modelo.addAttribute("tripulantesLocalidad", registroTripulantes);
		
		//Objeto para capturar el nombre de la localidad
		modelo.addAttribute("modeloConsulta", new ConsultaVehiculo());
				
		//Envia los registros de la localidad seleccionada
		modelo.addAttribute("registrosLocalidad", registrosLocalidade);
				
		//Envia el id del registro
		modelo.addAttribute("idRegistro", id);
				
		//Cajas que muestra el resultado de la busqueda
		modelo.addAttribute("activo", true);
				
		return "buscarVehiculos";
	}
	
	
	

	//------------------------------------------------//
	//----- Seccion buscarTripulante (plantilla) -----//
	//------------------------------------------------//
	
	@GetMapping("/tripulante")
	public String buscarTripulante(Model modelo) {
		
		modelo.addAttribute("modeloTripulante", new Tripulante());
		
		//Cajas que muestra el resultado de la busqueda
		modelo.addAttribute("activo", false);
		
		return "buscarTripulante";
	}
	
	@PostMapping("/searchTripulante")
	public String getTripulante(@ModelAttribute("modeloTripulante") Tripulante tripulante, 
			 Model modelo) {
		
		Tripulante tripulanteEncontrado= new Tripulante();
		
		//Validando
		//Validando
		if (tripulante.getDocumento().isEmpty() == false)
		{
			try {
				
				//Buscando Tripulante por documento.
				tripulanteEncontrado= tripulanteService.buscarTripulante(tripulante.getDocumento());
				
			} catch (Exception e) {
				
				//Cajas que muestra el resultado de la busqueda
				modelo.addAttribute("activo", false);
				modelo.addAttribute("errorMessage", "El numero de documento no existe");
				
				return "buscarTripulante";
			}

			
			//Envia los datos del Tripulante.
			modelo.addAttribute("datoTripulante", tripulanteEncontrado);
			//Envia los registros del Tripulante
			modelo.addAttribute("registroTripulante", trackingService.getRegistrosTripulante(tripulanteEncontrado)); 
			
			//Cajas que muestra el resultado de la busqueda
			modelo.addAttribute("activo", true);
		}
		else {
			//Cajas que muestra el resultado de la busqueda
			modelo.addAttribute("activo", false);
			modelo.addAttribute("errorMessage", "Ingrese numero de documento Ej: 39123456");
		}
		
		
		
		
		return "buscarTripulante";
	}
	
	//---------------------------------------------//
	//----- Seccion buscarPatente (plantilla) -----//
	//---------------------------------------------//
	
	@GetMapping("/patente")
	public String buscarPatente( Model modelo) {
		
		modelo.addAttribute("modeloVehiculo", new Vehiculo());
		//Cajas que muestra el resultado de la busqueda
		modelo.addAttribute("activo", false);
		return "buscarPatente";
	}
	
	@PostMapping("/searchPatente")
	public String getPatente (@ModelAttribute("modeloVehiculo") Vehiculo vehiculo, 
			Model modelo) {
		
		Vehiculo vehiculoEncontrado= new Vehiculo();
		List<Tripulante> vehiculoTripulantes= new ArrayList<>();
		List<RegistroTracking> registroVehiculo= new ArrayList<>();
		
		//Validando
		if(vehiculo.getPatente().isEmpty() == false) {
		
			try {
				
				//Buscando la patente del Vehiculo
				vehiculoEncontrado= vehiculoService.buscarVehiculo(vehiculo.getPatente());
					
			} catch (Exception e) {
				
				//Cajas que muestra el resultado de la busqueda
				modelo.addAttribute("activo", false);
				modelo.addAttribute("errorMessage", "La patente ingresada no existe asegurese de que este bien -> Ej: AAA123 / AAA123EE");
				
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
			
			//No necesario actualmente, pero en la busqueda de id, sera reemplazado
			modelo.addAttribute("id", "-");
				
			//Activa los contenedores
			modelo.addAttribute("activo", true);
		}
		else {
			//Cajas que muestra el resultado de la busqueda
			modelo.addAttribute("activo", false);
			modelo.addAttribute("errorMessage", "Ingrese la patente -> Ej: AAA123 / AAA123EE");
		}
		return "buscarPatente";
	}
	
	@GetMapping("/buscar/patente/{id}")		
	public String searchRegistro(@PathVariable Long id, Model modelo) {
		
		//Objeto que guardara el registro del id del vehiculo
		RegistroTracking registroVehiculo = new RegistroTracking();
		
		//Objeto que guardara los registros del id del vehiculo
		List<RegistroTracking> registrosVehiculo = new ArrayList<>();
		
		//Objeto que guaradara los tripulantes del registro del id enviado.
		List<Tripulante> tripulantesVehiculo = new ArrayList<>();
		
		try {
			
			//Se buscara el registro del id enviado
			registroVehiculo= trackingService.getRegistros(id);
			
			//Se buscara los registro del vehiculo encontrado
			registrosVehiculo= trackingService.getRegistrosVehiculo(registroVehiculo.getVehiculos());
			
		} catch (Exception e) {
			//Cajas que muestra el resultado de la busqueda
			modelo.addAttribute("activo", false);
			modelo.addAttribute("errorMessage", "La patente ingresada no existe asegurese de que este bien -> Ej: AAA123 / AAA123EE");
			modelo.addAttribute("modeloVehiculo", new Vehiculo());
			return "buscarPatente";
		}
		
		//Se almacenara los tripulantes del registro buscado por id.
		tripulantesVehiculo.addAll(registroVehiculo.getTripulantes());
		
		//Se enviara los registros del vehiculo
		modelo.addAttribute("registroVehiculo", registrosVehiculo);
		
		//Se enviara el objeto vehiculo
		modelo.addAttribute("vehiculo", registroVehiculo.getVehiculos());
		
		//Se enviara los tripulante del vehiculo
		modelo.addAttribute("tripulanteVehiculo", tripulantesVehiculo);
		
		//Se enviara el id del registro.
		modelo.addAttribute("id", id);
		modelo.addAttribute("modeloVehiculo", new Vehiculo());
		modelo.addAttribute("activo", true);
		
		return "buscarPatente";
		
		
	}
	
	
	//----------------------------------------------//
	//----- Seccion buscarVehiculos ( PARCHE ) -----//
	//----------------------------------------------//
	
	@GetMapping("/buscar/vehiculo/vehiculos")
	public String getWebVehiculo1 () {
				
		return "redirect:/vehiculos";
	}
	
	@GetMapping("/buscar/vehiculo/patente")
	public String getWebPatente1 () {
				
		return "redirect:/patente";
	}
	
	@GetMapping("/buscar/vehiculo/tripulante")
	public String getWebTripulante1 () {
				
		return "redirect:/tripulante";
	}
	
	
	@GetMapping("/buscar/patente/vehiculos")
	public String patente () {
				
		return "redirect:/vehiculos";
	}
	
	@GetMapping("/buscar/patente/patente")
	public String getWebPatente2 () {
				
		return "redirect:/patente";
	}
	
	@GetMapping("/buscar/patente/tripulante")
	public String getWebTripulante2 () {
				
		return "redirect:/tripulante";
	}
	
	
}
