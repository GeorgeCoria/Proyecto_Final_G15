package ar.edu.unju.fi.service;

import java.util.List;
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
		localidad.setEstado(true);
		if(checkNombreLocalidad(localidad)) {
			iLocalidad.save(localidad);
		}
	}

	private boolean checkNombreLocalidad (Localidad localidad) throws Exception {
		Optional<Localidad> localidadEncontrada = iLocalidad.findByNombre(localidad.getNombre());
		if (localidadEncontrada.isPresent()) {
			throw new Exception("Error: Ya existe una locadidad con el nombre Ingrado");
		}
		return true;
	}
	
	@Override
	public Iterable<Localidad> listarLocalidades(boolean estado) {
		return iLocalidad.findByEstado(estado);
	}

	@Override
	public void eliminarLocalidad(Long id) throws Exception {
			Localidad unaLocalidad=buscarLocalidad1(id);
			unaLocalidad.setEstado(false);
			iLocalidad.save(unaLocalidad);		
	}

	@Override
	public Localidad buscarLocalidad1(Long id) throws Exception {
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

	@Override
	public void editarLocalidad(Localidad localidad) throws Exception {
		String localidadMayuscula = localidad.getNombre().toUpperCase();
		localidad.setNombre(localidadMayuscula);
		if(verificarEditarNombre(localidad)) {
			iLocalidad.save(localidad);
		}	
	}
	
	public boolean verificarEditarNombre(Localidad localidad)throws Exception{
		int cont=0;
		List<Localidad> listaLocalidad = iLocalidad.findAll();
		for(Localidad loc: listaLocalidad) {
			if(localidad.getNombre().equals(loc.getNombre())) {
				if(localidad.getId()!=loc.getId())
					cont++;
			}
		}
		if(cont!=0) {
			throw new Exception ("El nombre de Usuario ya existe");
		}
		return true;
	}

	@Override
	public void habilitarLocalidad(Long id) throws Exception {
		Localidad habilitado = buscarLocalidad1(id);
		habilitado.setEstado(true);
		iLocalidad.save(habilitado);
		
	}
	

}
