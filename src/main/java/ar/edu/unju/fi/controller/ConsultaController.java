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
 * Clase que se encargada de tomar las peticiones y controlar que respuesta debe
 * ser presentada en la vista Consutal 
 * (plantillas buscarPatente - buscarTripulante - buscarVehiculos /.html).
 * 
 * @author Castillo, Jorge E.
 *
 */

@Controller
public class ConsultaController {

	//----------------- Variables Miembros o Atributos -----------------//
	
	/**
	 * Representa la interfaz Service de Vehiculo. 
	 */
	@Autowired
	private IVehiculoService vehiculoService;
	
	/**
	 * Representa la interfaz Service de RegistroTracking.
	 */
	@Autowired
	private IRegistroTrackingService trackingService;
	
	/**
	 * Representa la interfaz Service de Tripulante.
	 */
	@Autowired
	private ITripulanteService tripulanteService;
	
	/**
	 * Representa la interfaz Service de Localidad.
	 */
	@Autowired
	private ILocalidadService localidadService;
	
	/**
	 * Representa un objeto de ConsultaVehiculo.
	 */
	@Autowired
	private ConsultaVehiculo consultaVehiculo;
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//----------------- Metodos -----------------//
	
	
	//-----------------------------------------------//
	//----- Seccion buscarVehiculos (plantilla) -----//
	//-----------------------------------------------//
	

	/**
	 * Primera accion que se realizara al poner el url /vehiculos (iniciar consulta)
	 * Mostrara una lista de localidad listada, tendra un modelo de consulta para las consultas a realizar y 
	 * las tablas estaran ocultas.
	 * 
	 * @param modelo se enviara para obtener las consulta realizada por Rango fechas y Localidad.
	 * @return la plantilla buscarVehiculos.html 
	 */
	@GetMapping("/vehiculos")
	public String iniciarConsultasLocalidadAndRangoFechas(Model modelo) {
	
		//Lista todas las localidades habilitadas.
		Iterable<Localidad> localidades= localidadService.listarLocalidades(true);
		modelo.addAttribute("localidades", localidades);
		
		//Muestra el resultado de la busqueda (Deshabilitado)
		modelo.addAttribute("activo", false);
		modelo.addAttribute("modeloConsulta", new ConsultaVehiculo());
		
		return "buscarVehiculos";
	}
	
	/**
	 * Busca los vehiculos que esten en el rango de fechas y localidad.
	 * Si pasa los controles de validacion y excepcion, mostrara los resultados,
	 * caso contrario, mostrara segun el error detectado y se oculta las tablas.
	 * 
	 * @param consultaVehiculo, modelo obtenido de la consulta realizada.
	 * @param result, validara las consulta realizada.
	 * @param modelo, se utilizara modelos que se enviara como resultados.
	 * @return plantilla buscarVehiculos.html dependiendo de los resultados.
	 */
	@PostMapping("/buscarVehiculosPorRangoFechaAndLocalidad")
	public String buscarVehiculos(@Valid @ModelAttribute("modeloConsulta") ConsultaVehiculo consultaVehiculo, 
			BindingResult result, Model modelo) {
		
		
		//Lista todas las localidades.
		Iterable<Localidad> localidades= localidadService.listarLocalidades(true);
		modelo.addAttribute("localidades", localidades);
		
		//Se almacenara el resultado de la busqueda.
		this.consultaVehiculo= consultaVehiculo;
			
		//Objeto que se le asignara los tripulantes del registro buscado por localidad y rango de fechas.
		List<Tripulante> tripulantes= new ArrayList<>();
		

		//Validando
		if(result.hasErrors() == false) {
			try {
				
				//Busca el objeto de la localidad selecionada
				Localidad localidadEncontrada = localidadService.buscarNombreLocalidad(consultaVehiculo.getLocalidad().getNombre());
				
				//Busca el registro de la localidad seleccionada y rango de fechas.
				List<RegistroTracking> registrosConsulta = trackingService.getRegistrosRangoFechasAndLocalidad
						(consultaVehiculo.getFechaDesde(), consultaVehiculo.getFechaHasta(), localidadEncontrada);
				
				//Revisa si esta vacia
				if(registrosConsulta.isEmpty()){
					
					modelo.addAttribute("error", "La localidad o el rango de fechas seleccionados no tiene registro");
					//Muestra el resultado de la busqueda (Deshabilitado)
					modelo.addAttribute("activo", false);
					
					return "buscarVehiculos";
				}
				
				//Guarda las listas de los tripulantes de la localidad seleccionada y rango de fechas.
				for ( RegistroTracking n : registrosConsulta) {
					
					tripulantes.addAll(n.getTripulantes());
				}
				
				//Envia el objeto de las listas de los tripulantes de la localidad seleccionada y rango de fechas.
				modelo.addAttribute("tripulantes", tripulantes);
				
				//Envia el objeto de los registros de la localidad seleccionada y rango de fechas.
				modelo.addAttribute("registrosConsulta", registrosConsulta);
				
				//Envia el id del registro (No es necesario ahora, pero para q no se vea vacio)
				modelo.addAttribute("idRegistro", "-");
				
			} catch (Exception e) {
				
				modelo.addAttribute("error", "La localidad seleccionada no exite");
				//Cajas que muestra el resultado de la busqueda
				modelo.addAttribute("activo", false);
				
				return "buscarVehiculos";
			}
			
			//Cajas que muestra el resultado de la busqueda
			modelo.addAttribute("activo", true);
		}
		
		
		return "buscarVehiculos";
	}
	
	

	/**
	 * Buscara el registro del id enviado para mostrar los tripulantes de ese
	 * registro.
	 * 
	 * Se usara los datos enviado por la consulta realizada para mostrar los demas resultado.
	 * 
	 * @param id, id enviado para realizar la busqueda de ese registro.
	 * @param modelo, se utilizara modelos que se enviara como resultados.
	 * @return la plantilla buscarVehiculos..html
	 */
	@GetMapping("/buscar/registro/{id}")		
	public String buscarRegistro(@PathVariable Long id, Model modelo) {
		
		//Lista todas las localidades.
		Iterable<Localidad> localidades= localidadService.listarLocalidades(true);
		modelo.addAttribute("localidades", localidades);
		
		//Objeto que almacenara el registro del id enviado
		RegistroTracking registroVehiculo = new RegistroTracking();
		
		List<RegistroTracking> registrosConsulta= new ArrayList<>();
		//Localidad localidadEncontrada= new Localidad();
		
		//Objeto que almacenara los tripulantes del registro buscado por id
		List<Tripulante> tripulantes = new ArrayList<>();
				
		try {
			
			//Busca el ID del registro
			registroVehiculo= trackingService.getRegistros(id);
			
			try {
				
				//Busca la localidad de la consulta anteriormente seleccionado.
				Localidad localidad= localidadService.buscarNombreLocalidad(this.consultaVehiculo.getLocalidad().getNombre());
				
				registrosConsulta = trackingService.getRegistrosRangoFechasAndLocalidad
						(this.consultaVehiculo.getFechaDesde(), this.consultaVehiculo.getFechaHasta(), localidad);

			} catch (Exception e) {
				modelo.addAttribute("error", "La localidad o el rango de fechas seleccionados no tiene registro");
				
				//Cajas que muestra el resultado de la busqueda
				modelo.addAttribute("activo", false);
				
				//Objeto para capturar el nombre de la localidad
				modelo.addAttribute("modeloConsulta", new ConsultaVehiculo());
				
				return "buscarVehiculos";
			}
			
		} catch (Exception e) {
			
			modelo.addAttribute("error", "el id seleccionado no existe");
			//Cajas que muestra el resultado de la busqueda
			modelo.addAttribute("activo", false);
			
			//Objeto para capturar el nombre de la localidad
			modelo.addAttribute("modeloConsulta", new ConsultaVehiculo());
			
			return "buscarVehiculos";
		}
		
		
		//Se guarda los tripulantes del registro ID seleccionado.
		tripulantes.addAll(registroVehiculo.getTripulantes());
		
		//Envia el objeto del listado de los tripulantes.
		modelo.addAttribute("tripulantes", tripulantes);
		
		//Envia los registros de la localidad seleccionada
		modelo.addAttribute("registrosConsulta", registrosConsulta);
				
		//Envia el id del registro
		modelo.addAttribute("idRegistro", id);
				
		//Objeto para capturar el nombre de la localidad
		modelo.addAttribute("modeloConsulta", new ConsultaVehiculo());
		
		//Cajas que muestra el resultado de la busqueda
		modelo.addAttribute("activo", true);
				
		return "buscarVehiculos";
	}
	
	
	

	//------------------------------------------------//
	//----- Seccion buscarTripulante (plantilla) -----//
	//------------------------------------------------//
	
	
	/**
	 * Primera accion que se realizara al poner el url /tripulante (iniciar consulta)
	 * Tendra un modelo para la consulta por documento y las tablas estaran escondidas
	 * 
	 * @param modelo se usara para enviar un modelo Tripulante para la consulta a realizar.
	 * @return la plantilla buscarTripulante.html
	 */
	@GetMapping("/tripulante")
	public String iniciarConsultaPorTripulante(Model modelo) {
		
		modelo.addAttribute("modeloTripulante", new Tripulante());
		
		//Cajas que muestra el resultado de la busqueda
		modelo.addAttribute("activo", false);
		
		return "buscarTripulante";
	}
	
	
	
	/**
	 * Mostrara los datos personales del tripulante y registros si
	 * pasa los controles de validacion y excepcion.
	 * 
	 * @param tripulante, se lo usara para buscar el tripulante.
	 * @param modelo, se enviara modelos dependiendo del resultado de la consulta realizado.
	 * @return la plantilla buscarTripulante.html
	 */
	@PostMapping("/searchTripulante")
	public String buscarTripulante(@ModelAttribute("modeloTripulante") Tripulante tripulante, 
			 Model modelo) {
		
		Tripulante tripulanteEncontrado= new Tripulante();
		
		List<RegistroTracking> tripulantes= new ArrayList<>();
		
		//Validando
		if (tripulante.getDocumento().isEmpty() == false)
		{
			try {
				
				//Buscando Tripulante por documento.
				tripulanteEncontrado= tripulanteService.buscarTripulante(tripulante.getDocumento());
				
				tripulantes=trackingService.getRegistrosTripulante(tripulanteEncontrado);
				
				if (tripulantes.isEmpty()) {
					//Cajas que muestra el resultado de la busqueda
					modelo.addAttribute("activo", false);
					modelo.addAttribute("error", "El numero de documento no tiene registro");
					
					return "buscarTripulante";
				}
			} catch (Exception e) {
				
				//Cajas que muestra el resultado de la busqueda
				modelo.addAttribute("activo", false);
				modelo.addAttribute("error", "El numero de documento no existe");
				
				return "buscarTripulante";
			}

			
			//Envia los datos del Tripulante.
			modelo.addAttribute("datoTripulante", tripulanteEncontrado);
			//Envia los registros del Tripulante
			modelo.addAttribute("registroTripulante", tripulantes); 
			
			//Cajas que muestra el resultado de la busqueda
			modelo.addAttribute("activo", true);
		}
		else {
			//Cajas que muestra el resultado de la busqueda
			modelo.addAttribute("activo", false);
			modelo.addAttribute("error", "Ingrese numero de documento Ej: 39123456");
		}
		
		return "buscarTripulante";
	}
	
	//---------------------------------------------//
	//----- Seccion buscarPatente (plantilla) -----//
	//---------------------------------------------//
	
	/**
	 * Primera accion que se realizara al poner el url /tripulante (iniciar consulta)
	 * Tendra un modelo Vehiculo para las consulta a realizar y las tablas estaran ocultas
	 * @param modelo, se enviara modelo para la consulta a realizar.
	 * @return la plantilla buscarPatente.html
	 */
	@GetMapping("/patente")
	public String inciarConsultaPatente( Model modelo) {
		
		modelo.addAttribute("modeloVehiculo", new Vehiculo());
		//Cajas que muestra el resultado de la busqueda
		modelo.addAttribute("activo", false);
		return "buscarPatente";
	}
	
	
	/**
	 * Busca el registro del vehiculo, y sus tripulantes
	 * siempre y cuando pase por la validacion y exception.
	 * 
	 * @param vehiculo, modelo tipo Vehiculo que se usara para buscar al vehiculo.
	 * @param modelo, se enviara modelos dependiendo del resultado obtenido.
	 * @return la plantilla buscarPatente.html
	 */
	@PostMapping("/searchPatente")
	public String buscarPatente (@ModelAttribute("modeloVehiculo") Vehiculo vehiculo, 
			Model modelo) {
		
		Vehiculo vehiculoEncontrado= new Vehiculo();

		List<RegistroTracking> registroVehiculo= new ArrayList<>();
		
		//Lista que se le asignara los tripulantes de los registros del vehiculo buscado.
		List<Tripulante> tripulantes= new ArrayList<>();
		
		//Validando
		if(vehiculo.getPatente().isEmpty() == false) {
		
			try {
				
				//Buscando la patente del Vehiculo
				vehiculoEncontrado= vehiculoService.buscarVehiculo(vehiculo.getPatente());
				//Busca los registros del vehiculoEncontrado
				registroVehiculo=trackingService.getRegistrosVehiculo(vehiculoEncontrado);
				
				
				System.out.println(vehiculoEncontrado);
				
				System.out.println(registroVehiculo);
				if (registroVehiculo.isEmpty()) {
					
					//Cajas que muestra el resultado de la busqueda
					modelo.addAttribute("activo", false);
					modelo.addAttribute("error", "La patente ingresada no tiene registro");
					
					return "buscarPatente";
				}
			} catch (Exception e) {
				
				//Cajas que muestra el resultado de la busqueda
				modelo.addAttribute("activo", false);
				modelo.addAttribute("error", "La patente ingresada no existe  -> Ej: AAA123 / AA123EE");
				
				return "buscarPatente";
			}
		
			
				
			//Se asigna la listas de los Tripulantes del Registro de Vehiculo buscado.
			for ( RegistroTracking n : registroVehiculo)
			{
				tripulantes.addAll(n.getTripulantes());
			}
				
			//Envia los datos del Vehiculo
			modelo.addAttribute("vehiculo", vehiculoEncontrado);
			//Envia los registros del Vehiculo 
			modelo.addAttribute("registroVehiculo",registroVehiculo);
			//Envia los registros de los Tripulantes del Vehiculo buscado
			modelo.addAttribute("tripulanteVehiculo", tripulantes);
			
			//No necesario actualmente, pero en la busqueda de id, sera reemplazado
			modelo.addAttribute("id", "-");
				
			//Activa los contenedores
			modelo.addAttribute("activo", true);
		}
		else {
			//Cajas que muestra el resultado de la busqueda
			modelo.addAttribute("activo", false);
			modelo.addAttribute("error", "Ingrese la patente -> Ej: AAA123 / AAA123EE");
		}
		return "buscarPatente";
	}
	
	/**
	 * Busca el registro del vehiculo con el id enviado, si 
	 * pasa las exception, mostrara los tripulante de ese id Registro. 
	 * 
	 * @param id, se usara para buscar el registro del vehiculo.
	 * @param modelo, se enviara modelos dependiendo del resultado optenido.
	 * @return la plantilla buscarPatente.html
	 */
	@GetMapping("/search/registro/{id}")		
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
			modelo.addAttribute("error", "La patente ingresada no existe asegurese de que este bien -> Ej: AAA123 / AAA123EE");
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
	
	/**
	 * Redirecciona la vista.
	 * 
	 * @return la vista /vehiculos
	 */
	@GetMapping("/buscar/registro/vehiculos")
	public String getWebVehiculos1 () {
				
		return "redirect:/vehiculos";
	}
	
	/**
	 * Redirecciona la vista.
	 * 
	 * @return la vista /patente
	 */
	@GetMapping("/buscar/registro/patente")
	public String getWebPatente1 () {
				
		return "redirect:/patente";
	}
	
	/**
	 * Redirecciona la vista.
	 * 
	 * @return la vista /tripulante
	 */
	@GetMapping("/buscar/registro/tripulante")
	public String getWebTripulante1 () {
				
		return "redirect:/tripulante";
	}
	
//-----------------------------------------------------------------------------//
	
	/**
	 * Redirecciona la vista.
	 * 
	 * @return la vista /vehiculos
	 */
	@GetMapping("/search/registro/vehiculos")
	public String getWebVehiculos2 () {
				
		return "redirect:/vehiculos";
	}
	
	/**
	 * Redirecciona la vista.
	 * 
	 * @return la vista /patente
	 */
	@GetMapping("/search/registro/patente")
	public String getWebPatente2 () {
				
		return "redirect:/patente";
	}
	
	/**
	 * Redirecciona la vista.
	 * 
	 * @return la vista /tripulante
	 */
	@GetMapping("/search/registro/tripulante")
	public String getWebTripulante2 () {
				
		return "redirect:/tripulante";
	}
	
	
}
