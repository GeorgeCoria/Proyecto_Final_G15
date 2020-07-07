package ar.edu.unju.fi.controller;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
 * Clase que se encargada de tomar las peticiones y controlar que respuesta debe
 * ser presentada en la vista (páginas html: registroTracking.html,
 * registroVehiculo.html, registroTripulante.html, registroVista.html y
 * vehiculoFormulario.html ) de nuestro proyecto.
 * 
 * @author Marcia Velarde
 *
 */
@Controller
public class RegistradorController {

	@Autowired
	private IRegistroTrackingService registroTrackingService;
	
	@Autowired
	private ILocalidadService localidadService;
	
	@Autowired
	private IVehiculoService vehiculoService; 
	
	@Autowired
	private ITripulanteService tripulanteService;
	
	@Autowired
	private Vehiculo unVehiculo;
	
	@Autowired
	private RegistroTracking registro;
	
	//Anotacion asociada a la url (/registros)
	@GetMapping("/registros")
	public String verificarVehiculo(@ModelAttribute("vehiculo") Vehiculo vehiculo, ModelMap model) {
		//Se manda a la vista el objeto vehiculo vacio
		model.addAttribute("vehiculo", new Vehiculo());
		
		//Se habilita o deshabilita la visualizacion de los botones
		model.addAttribute("botonBuscarVehiculo", "true");
		model.addAttribute("botonSiguiente", "false");
		model.addAttribute("botonGuardar", "false");
		model.addAttribute("botonCancelarVehiculo", "false");
		
		//Se habilita el input de ingreso de patente
		model.addAttribute("patenteBuscada", "false");
		return "registroVehiculo";
	}

	//Anotacion asociada a la url (/registros/vehiculoBuscado) que se acciona por el boton BUSCAR
	@PostMapping("/registros/vehiculoBuscado")
	public String buscarVehiculo(@ModelAttribute("vehiculo") Vehiculo vehiculo, ModelMap model) throws Exception {
		try { 
			unVehiculo.setPatente(vehiculo.getPatente());
			if(vehiculoService.chekPatente(vehiculo)){
				//Se avisa por un mensaje que la patente esta registrada
				model.addAttribute("formMessage", "LA PATENTE ESTA REGISTRADA");
				
				//Se habilita o deshabilita la visualizacion de los botones
				model.addAttribute("botonSiguiente", "true");
				model.addAttribute("botonGuardar", "false");
				model.addAttribute("botonBuscarVehiculo", "false");
				model.addAttribute("botonCancelarVehiculo", "true");
				
				//Se inhabilita el input de ingreso de patente
				model.addAttribute("patenteBuscada", "true");
			}
			return "registroVehiculo";
		}catch(Exception e) {
			if(e.getMessage() == "La patente no esta registrada") {
				model.addAttribute("formError", e.getMessage());
				
				//Se habilita o deshabilita la visualizacion de los botones
				model.addAttribute("botonGuardar", "true");
				model.addAttribute("botonSiguiente", "false");
				model.addAttribute("botonBuscarVehiculo", "false");
				model.addAttribute("botonCancelarVehiculo", "true");
				
				//Se inhabilita el input de ingreso de patente
				model.addAttribute("patenteBuscada", "true");
				return "registroVehiculo";
			} else {
				// Si se ingresa mal el formato de la patente se muestra el mensaje de error y
				// se llama al metodo enviando los parametros correspondientes
				model.addAttribute("formError", e.getMessage());
				return verificarVehiculo(vehiculo,model);
			}
		}
	}
	
	//Anotacion asociada a la url (/registroTracking) que se acciona al apretar el boton SIGUIENTE
	@GetMapping("/registroTracking")
	public String siguienteRegistro(@ModelAttribute("tripulanteDelForm") Tripulante tripulante, Model model) throws Exception {
		try{//Se manda a la vista un objeto de tipo registroTracking vacio
			model.addAttribute("registroTracking", registro);
			//Se muestra en la tabla de vehiculo, el vehiculo encontrado por la patente
			model.addAttribute("listaVehiculo", vehiculoService.buscarVehiculo(unVehiculo.getPatente()));
			//Se muestra en la tabla de tripulantes, los tripulantes del vehiculo
			model.addAttribute("listaTrip",tripulanteService.buscarTodosTripulantes());
			//Se muestra en el select de localidades, las localidades con estado true (activadas)
			model.addAttribute("listaLocalidades", localidadService.listarLocalidades(true));
			//Si la lista de tripulantes esta vacia
			if(tripulanteService.buscarTodosTripulantes().isEmpty()) {
				//No se visualiza el boton FINALIZAR REGISTRO
				model.addAttribute("botonFinalRegistro", "false");
			}else {
				//se visualiza el boton FINALIZAR REGISTRO
				model.addAttribute("botonFinalRegistro", "true");
			}
		}catch(Exception e) {
			model.addAttribute("ErrorMessage", e.getMessage());
		}
		model.addAttribute("tripulanteDelForm", new Tripulante());
		return "registroTracking";
	}
	
	//Anotacion asociada a la url (/vehiculoForm) que se acciona con el boton AGREGAR (un vehiculo)
	@GetMapping("/vehiculoForm")
	public String vehiculoFormulario(@ModelAttribute("vehiculo") Vehiculo vehiculo, ModelMap model) {
		//Se manda a la vista el objeto de tipo Vehiculo, cargado con la patente ingresada
		model.addAttribute("vehiculo", unVehiculo);
		return "vehiculoFormulario";
	}
	
	//Anotacion asociada a la url (/registroTracking) que se acciona por el boton GUARDAR en la pagina del formulario del vehiculo
	@PostMapping("/registroTracking")
	public String guardarVehiculo(@Valid @ModelAttribute("vehiculo") Vehiculo vehiculo, BindingResult result,  ModelMap model) {
		if(result.hasErrors()) {
			//Se asigna a vehiculo la patente ingresada por el usuario
			vehiculo.setPatente(unVehiculo.getPatente());
			//Se manda a la vista el objeto cargado con los datos ingresados por el usuario
			model.addAttribute("vehiculo", vehiculo);
			return "vehiculoFormulario";
		}else {
			//Se asigna a vehiculo la patente ingresada por el usuario
			vehiculo.setPatente(unVehiculo.getPatente());
			//Se guarda el vehiculo en la base de datos
			vehiculoService.crearVehiculo(vehiculo);
			return "redirect:/registroTracking";
		}
	}
	
	//Anotacion asociada a la url(/buscarTripulante) que se acciona por el boton BUSCAR de la ventada modal de tripulante
	@PostMapping("/buscarTripulante")
	public String buscarTripulanteExistente(@ModelAttribute("tripulanteDelForm") Tripulante tripulante, Model model) throws Exception{
	try {
		//Se guarda en un Objeto de tipo Tripulante el tripulante encontrado por su dni
		Tripulante tripulanteEncontrado = tripulanteService.buscarTripulante(tripulante.getDocumento());
		try {
			//Se guarda en una lista auxiliar al tripulante encontrado
			tripulanteService.guardarTripulanteEncontrado(tripulanteEncontrado);
		} catch(Exception e) {
			model.addAttribute("ErrorMessage", e.getMessage());
		}
	}catch(Exception e) {
		model.addAttribute("ErrorMessage", e.getMessage());
	}
		return siguienteRegistro(tripulante,model);
	}
	
	//Anotacion asociada a la url (/cargarTripulante) que se acciona por el boton AGREGAR TRIPULANTE 
	@GetMapping("/cargarTripulante")
	public String formularioTripulante(Model model) {
		//Se manda a la vista objeto de tipo Tripulante vacio 
		model.addAttribute("tripulanteForm", new Tripulante());
		return "registroTripulante";
	}

	//Anotacion asociada a la url(/formTripulante) que se acciona por el boton GUARDAR del formulario de tripulante
	@PostMapping("/formTripulante")
	public String agregarTripulante(@Valid @ModelAttribute("tripulanteForm") Tripulante tripulante, BindingResult result, ModelMap model) throws Exception {
		if(result.hasErrors()) {
			//Se manda a la vista el objeto cargado con los datos ingresados por el usuario
			model.addAttribute("tripulanteForm", tripulante);
			return "registroTripulante";
		}else {
			try {
				//Se guarda en la base de datos el nuevo tripulante y se lo guarda en la lista auxiliar
				tripulanteService.crearTripulante(tripulante);
				return "redirect:/registroTracking";
			}catch(Exception e) {
				model.addAttribute("formErrorTrip", e.getMessage());
				model.addAttribute("tripulanteForm", tripulante);
				return "registroTripulante";
			}
		}
	}
	
	//Anotacion asociada a la url (/registroFinalizado) que se acciona con el boton FINALIZAR REGISTRO
	@PostMapping("/registroFinalizado")
	public String tripulanteForm (@ModelAttribute("registroTracking") RegistroTracking registroT, ModelMap model) throws Exception {
		//Se guarda en un optional la localidad encontrada por el id, elegida por el usuario
		Optional<Localidad> localidadElegida = localidadService.buscarLocalidad(registroT.getLocalidad().getId());
		//Se asigna la localidad eligida al atributo localidad del registro tracking nuevo
		localidadElegida.ifPresent(localidad -> {
			registroT.setLocalidad(localidad);
		});
		//Se asigna el vehiculo elegido por el usuario al atributo vehiculos del regitro tracking nuevo
		registroT.setVehiculos(vehiculoService.buscarVehiculo(unVehiculo.getPatente()));
		//Se asigna la lista auxiliar de tripulantes a la lista de tripulantes del registro tracking nuevo
		registroT.setTripulantes(tripulanteService.buscarTodosTripulantes());
		//Se guarda en la base de datos el registroTracking nuevo
		registroTrackingService.crearRegistro(registroT);
		//Se muestra en la tabla la lista de tripulantes agregados en el registro
		model.addAttribute("listaTrip",tripulanteService.buscarTodosTripulantes());
		//Se muestra en la tabla la localidad agregada en el registro
		model.addAttribute("listaLocalidad", registroT.getLocalidad());
		//Se muestra en la tabla el vehiculo agregado en el registro
		model.addAttribute("listaVehiculo", vehiculoService.buscarVehiculo(unVehiculo.getPatente()));
		//Se muestra la fecha del registro con el formato elegido
		String fechaHora = registroT.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		model.addAttribute("fechaHora", fechaHora);
		//Se muestra el detalle del lugar del registro
		model.addAttribute("detalleLugar", registroT.getDetalleLugarRegistro());
		return "registroVista";
	}
	
	//Anotacion asociada a la url(/registroNuevo) que se acciona con el boton CARGAR OTRO REGISTRO
	@GetMapping("/registroNuevo")
	public String registroNuevo() {
		//Limpia la lista auxiliar de tripulantes
		tripulanteService.borrarListaTripulantes();
		return "redirect:/registros";
	}
		
	
}