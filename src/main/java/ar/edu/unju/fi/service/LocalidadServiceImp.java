package ar.edu.unju.fi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unju.fi.model.Localidad;
import ar.edu.unju.fi.repository.ILocalidadDAO;

/**
 * Clase que va a implementar la interface ILocalidadService.
 * 
 * @author Marcia Velarde, Juan Toconas, Jorge E. Castillo.
 *
 */
@Service
public class LocalidadServiceImp implements ILocalidadService{

	@Autowired
	private ILocalidadDAO iLocalidad;
	
	@Override
	public void crearLocalidad(Localidad localidad) throws Exception {
		String localidadMayuscula = localidad.getNombre().toUpperCase();
		localidad.setNombre(localidadMayuscula);
		if(checkNombreLocalidad(localidad)) {
			iLocalidad.save(localidad);
		}
	}

	private boolean checkNombreLocalidad (Localidad localidad) throws Exception {
		Optional<Localidad> localidadEncontrada = iLocalidad.findByNombre(localidad.getNombre());
		if (localidadEncontrada.isPresent()) {
			throw new Exception("La localidad ya esta registrada");
		}
		return true;
	}
	
	@Override
	public Iterable<Localidad> listarLocalidades() {
		return iLocalidad.findAll();
	}

	@Override
	public void eliminarLocalidad(Long id) {
		iLocalidad.deleteById(id);		
	}

	@Override
	public Localidad EditarLoc(Long id) throws Exception {
		return iLocalidad.findById(id).orElseThrow(()-> new Exception("La localidad no existe"));
	}

	@Override
	public Optional<Localidad> buscarLocalidad(Long id) {
		return iLocalidad.findById(id);
	}

	@Override
	public Localidad buscarNombreLocalidad(String nombre) throws Exception {
		
		return iLocalidad.findByNombre(nombre).orElseThrow();
	}

}
