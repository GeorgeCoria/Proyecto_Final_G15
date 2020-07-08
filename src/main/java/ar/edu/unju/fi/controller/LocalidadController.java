package ar.edu.unju.fi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ar.edu.unju.fi.model.Localidad;
import ar.edu.unju.fi.service.LocalidadServiceImp;

/**
 * Clase que se encargada de tomar las peticiones y controlar que respuesta debe
 * ser presentada en la vista (paginas html: adminLocalidad.html) de nuestro proyecto.
 * 
 * @author Velarde Marcia, Toconas Juan
 * 
 */
@Controller
public class LocalidadController {

	@Autowired
	private LocalidadServiceImp localidadService;
	
	/**
	 * Muestra la pagina del formulario y lista de localidades
	 * 
	 * @param model, el model que usara la pagina
	 * @return la pagina del formulario y lista de localidades
	 */
	@GetMapping("/adminLocalidad")
	public String mostrarLocalidad(Model model) {
		model.addAttribute("titulo", "LOCALIDAD");
		//Se manda a la vista un objeto de tipo Localidad vacio
		model.addAttribute("localidadForm", new Localidad());
		//Se lista las localidades activas
		model.addAttribute("listaLocalidades", localidadService.listarLocalidades(true));
		//Se activa el boton guardar
		model.addAttribute("guardarLocalidad","active");
		return "adminLocalidad";
	}

	/**
	 * Permite invocar al servicio que permite guardar la localidad, asi como a los
	 * servicios que realizan los listados necesario
	 * 
	 * @param localidad, con los datos capturados que ingreso el usuario
	 * @param result,    que realizara la validacion de los campos
	 * @param model,     el model que usara la pagina
	 * @return la pagina que se renderizara
	 */
	@PostMapping("/adminLocalidad")
	public String crearLocalidad(@Valid @ModelAttribute("localidadForm") Localidad localidad, BindingResult result,
			ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("localidadForm", localidad);
		}else {
			try {
				//Se guarda en la base de datos la localidad
				localidadService.crearLocalidad(localidad);
				//Se limpia los datos del formulario
				model.addAttribute("localidadForm", new Localidad());	
			}catch (Exception e){
				//En caso de errores se muestra el mensje correspondiente
				model.addAttribute("formErrorMessage", e.getMessage());
				model.addAttribute("localidadForm", localidad);
			}
		}
		//Se listan las localidades activas
		model.addAttribute("listaLocalidades", localidadService.listarLocalidades(true));
		model.addAttribute("titulo", "LOCALIDAD");
		//Se activa el boton guardar
		model.addAttribute("guardarLocalidad", "active");
		model.addAttribute("editMode", "false");
		return "adminLocalidad";
	}
	
	@GetMapping("/editarLoc/cancelar")
	public String cancelar() {
		return "redirect:/adminLocalidad";
	}
	
	@GetMapping("/eliminarLoc/{id}")
	public String eliminar (@PathVariable Long id, Model model) {
		try {
			localidadService.eliminarLocalidad(id);
		}catch(Exception e){
			model.addAttribute("listaErrorMessage", e.getMessage());
		}
		return "redirect:/adminLocalidad";
	}
	
	@GetMapping("/habilitarLocalidad/{id}")
		public String habilitarLocalidad(@PathVariable Long id, Model model) {
			try {
				localidadService.habilitarLocalidad(id);
			}catch(Exception e) {
				model.addAttribute("listaErrorMessage", e.getMessage());
			}
			return "redirect:/adminLocalidad";
		}
	
	
	@GetMapping("/editarLoc/{id}")
	public String editar(@PathVariable Long id, Model model) throws Exception {
		try{
			Localidad localidadForm=localidadService.buscarLocalidad1(id);
			model.addAttribute("localidadForm", localidadForm);
			model.addAttribute("listaLocalidades", localidadService.listarLocalidades(true));
		}catch(Exception e) {
			model.addAttribute("formErrorMessage", e.getMessage());
			model.addAttribute("localidadForm", new Localidad());
		}
		model.addAttribute("editarLocalidad", "active");
		model.addAttribute("editMode", "true");
		model.addAttribute("titulo", "EDITAR LOCALIDAD");
		return "adminLocalidad";
	}
	@PostMapping("/editarLocalidad")
	public String editarLocalidad(@Valid @ModelAttribute("localidadForm") Localidad localidad, ModelMap model, BindingResult result) {
		if(result.hasErrors()) {
			model.addAttribute("localidadForm", localidad);
		}try {
			localidadService.editarLocalidad(localidad);
			model.addAttribute("localidadForm", new Localidad());
			model.addAttribute("titulo", "LOCALIDAD");
			model.addAttribute("guardarLocalidad", "active");
			model.addAttribute("editMode", "false");
			
		}catch(Exception e) {
			model.addAttribute("formErrorMessage", e.getMessage());
			model.addAttribute("localidadForm", localidad);
			model.addAttribute("editarLocalidad", "active");
			model.addAttribute("editMode", "true");
			model.addAttribute("titulo", "EDITAR LOCALIDAD");
		}
		
		model.addAttribute("listaLocalidades", localidadService.listarLocalidades(true));
		
		return "adminLocalidad";
	}
	
	@GetMapping("/suspendido")
	public String listarSuspendido(Model model) {
		model.addAttribute("listaLocalidades", localidadService.listarLocalidades(false));
		model.addAttribute("suspendido", "active");	
		return "adminLocalidad";
		
	}

}
