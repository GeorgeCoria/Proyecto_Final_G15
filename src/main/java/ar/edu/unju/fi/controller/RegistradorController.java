package ar.edu.unju.fi.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ar.edu.unju.fi.model.Localidad;
import ar.edu.unju.fi.model.RegistroTracking;
import ar.edu.unju.fi.model.Tripulante;
import ar.edu.unju.fi.model.Vehiculo;
import ar.edu.unju.fi.service.LocalidadServiceImp;
import ar.edu.unju.fi.service.RegistroTrackingServiceImp;
import ar.edu.unju.fi.service.TripulanteServiceImp;
import ar.edu.unju.fi.service.VehiculoServiceImp;

/**
 * 
 * @author Marcia Velarde
 *
 */
@Controller
public class RegistradorController {

	@Autowired
	private RegistroTrackingServiceImp registroTrackingService;
	
	@Autowired
	private LocalidadServiceImp localidadService;
	
	@Autowired
	private VehiculoServiceImp vehiculoService; 
	
	@Autowired
	private TripulanteServiceImp tripulanteService;
	
	@Autowired
	private Vehiculo unVehiculo;
	
	@Autowired
	private RegistroTracking registro;
	
	@GetMapping("/registros")
	public String verificarVehiculo(@ModelAttribute("vehiculo") Vehiculo vehiculo, Model model) {
		model.addAttribute("vehiculo", new Vehiculo());
		model.addAttribute("botonBuscarVehiculo", "true");
		model.addAttribute("botonSiguiente", "false");
		model.addAttribute("botonGuardar", "false");
		model.addAttribute("botonCancelarVehiculo", "false");
		model.addAttribute("patenteBuscada", "false");
		return "registroVehiculo";
	}

	@PostMapping("/registros/vehiculoBuscado")
	public String buscarVehiculo(@ModelAttribute("vehiculo") Vehiculo vehiculo, ModelMap model) throws Exception {
		try { unVehiculo.setPatente(vehiculo.getPatente());
			if(vehiculoService.chekPatente(vehiculo)){
				model.addAttribute("formMessage", "LA PATENTE ESTA REGISTRADA");
				model.addAttribute("botonSiguiente", "true");
				model.addAttribute("botonGuardar", "false");
			}
		}catch(Exception e) {
			model.addAttribute("formError", e.getMessage());
			model.addAttribute("botonGuardar", "true");
			model.addAttribute("botonSiguiente", "false");
		}
		model.addAttribute("patenteBuscada", "true");
		model.addAttribute("botonBuscarVehiculo", "false");
		model.addAttribute("botonCancelarVehiculo", "true");
		return "registroVehiculo";
	}
	
	@GetMapping("/registroTracking")
	public String siguienteRegistro(@ModelAttribute("tripulanteDelForm") Tripulante tripulante, Model model) throws Exception {
		try{
			model.addAttribute("registroTracking", registro);
			model.addAttribute("listaVehiculo", vehiculoService.buscarVehiculo(unVehiculo.getPatente()));
			model.addAttribute("listaTrip",tripulanteService.buscarTodosTripulantes());
			model.addAttribute("listaLocalidades", localidadService.listarLocalidades());
			if(tripulanteService.buscarTodosTripulantes().isEmpty()) {
				model.addAttribute("botonFinalRegistro", "false");
			}else {
				model.addAttribute("botonFinalRegistro", "true");
			}
		}catch(Exception e) {
			model.addAttribute("ErrorMessage", e.getMessage());
		}
		model.addAttribute("tripulanteDelForm", new Tripulante());
		return "registroTracking";
	}
	
	@GetMapping("/vehiculoForm")
	public String vehiculoFormulario(@ModelAttribute("vehiculo") Vehiculo vehiculo, ModelMap model) {
		model.addAttribute("vehiculo", unVehiculo);
		return "vehiculoFormulario";
	}
	
	@PostMapping("/registroTracking")
	public String guardarVehiculo(@ModelAttribute("vehiculo") Vehiculo vehiculo, ModelMap model) {
		vehiculo.setPatente(unVehiculo.getPatente());
		vehiculoService.crearVehiculo(vehiculo);
		return "redirect:/registroTracking";
	}
	
	@PostMapping("/buscarTripulante")
	public String buscarTripulanteExistente(@ModelAttribute("tripulanteDelForm") Tripulante tripulante, Model model) throws Exception{
	try {
		Tripulante tripulanteEncontrado = tripulanteService.buscarTripulante(tripulante.getDocumento());
		try {
			tripulanteService.guardarTripulanteEncontrado(tripulanteEncontrado);
		} catch(Exception e) {
			model.addAttribute("ErrorMessage", e.getMessage());
		}
	}catch(Exception e) {
		model.addAttribute("ErrorMessage", e.getMessage());
	}
		return siguienteRegistro(tripulante,model);
	}
	
	@GetMapping("/cargarTripulante")
	public String formularioTripulante(Model model) {
		model.addAttribute("tripulanteForm", new Tripulante());
		model.addAttribute("listaTrip",tripulanteService.buscarTodosTripulantes());
		if(tripulanteService.buscarTodosTripulantes().isEmpty()) {
			model.addAttribute("botonTerminar", "false");
		}else {
			model.addAttribute("botonTerminar", "true");
		}
		return "registroTripulante";
	}
	
	@GetMapping("/cargarMasTripulantes")
	public String nuevoTripulante(Model model) {
		model.addAttribute("tripulanteForm", new Tripulante());
		model.addAttribute("listaTrip",tripulanteService.buscarTodosTripulantes());
		if(tripulanteService.buscarTodosTripulantes().isEmpty()) {
			model.addAttribute("botonTerminar", "false");
		}else {
			model.addAttribute("botonTerminar", "true");
		}
		return "registroTripulante";
	}
	
	@PostMapping("/formTripulante")
	public String agregarTripulante(@ModelAttribute("tripulanteForm") Tripulante tripulante, ModelMap model) {
		tripulanteService.crearTripulante(tripulante);
		model.addAttribute("botonTerminar", "true");
		return "redirect:/cargarMasTripulantes";
	}
	
	@GetMapping("/registroFinal")
	public String terminarRegistro() {
		return "redirect:/registroTracking";
	}
	
	
	@PostMapping("/registroFinalizado")
	public String tripulanteForm (@ModelAttribute("registroTracking") RegistroTracking registroT, ModelMap model) throws Exception {
		System.out.println(registroT);
		Optional<Localidad> localidadElegida = localidadService.buscarLocalidad(registroT.getLocalidad().getId());
		System.out.println(localidadElegida);
		localidadElegida.ifPresent(localidad -> {
			registroT.setLocalidad(localidad);
		});
		registroT.setVehiculos(vehiculoService.buscarVehiculo(unVehiculo.getPatente()));
		registroT.setTripulantes(tripulanteService.buscarTodosTripulantes());
		registroT.setFechaHora(LocalDateTime.now());
		System.out.println(registroT);
		registroTrackingService.crearRegistro(registroT);
		model.addAttribute("listaTrip",tripulanteService.buscarTodosTripulantes());
		model.addAttribute("listaLocalidad", registroT.getLocalidad());
		model.addAttribute("listaVehiculo", vehiculoService.buscarVehiculo(unVehiculo.getPatente()));
		return "registroVista";
	}
	
	@GetMapping("/registroNuevo")
	public String registroNuevo() {
		tripulanteService.borrarListaTripulantes();
		return "redirect:/registros";
	}
		
	
}