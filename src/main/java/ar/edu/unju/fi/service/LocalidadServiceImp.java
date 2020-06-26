package ar.edu.unju.fi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unju.fi.model.Localidad;
import ar.edu.unju.fi.repository.ILocalidadDAO;

/**
 * Clase que va a implementar la interface ILocalidadService.
 * 
 * @author Marcia Velarde, Juan Toconas
 *
 */
@Service
public class LocalidadServiceImp implements ILocalidadService{

	@Autowired
	private ILocalidadDAO iLocalidad;
	
	@Override
	public void crearLocalidad(Localidad localidad) {
		iLocalidad.save(localidad);
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
	public Optional<Localidad> EditarLoc(Long id) {
		return iLocalidad.findById(id);
	}

}
