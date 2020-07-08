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
	
	/**
	 * Metodo que permite guardar el estado de una localidad en el gestor de
	 * persistencia MYSQL
	 * 
	 * @param localidad, objeto de tipo Localidad que se desea guardar
	 * @throws Exception
	 */
	@Override
	public void crearLocalidad(Localidad localidad) throws Exception {
		// Se convierte el nombre de la localidad en mayuscula para ser guarado asi en
		// la base de datos y hacer mas facil la busqueda y checkeo de errores
		String localidadMayuscula = localidad.getNombre().toUpperCase();
		localidad.setNombre(localidadMayuscula);
		// Se le asigna por defecto true al estado, es decir que estara activa
		localidad.setEstado(true);
		// Si el nombre esta disponible se guarda
		if (checkNombreLocalidad(localidad)) {
			iLocalidad.save(localidad);
		}
	}

	/**
	 * Metodo que permite checkear si el nombre de la localidad ya existe en el
	 * gestor de persistencia MYSQL o esta disponible para ser usado
	 * 
	 * @param localidad, objeto de tipo localidad que se desea verificar el nombre
	 * @return true, si el nombre de localidad esta disponible
	 * @throws Exception, si el nombre de usuario no esta disponible
	 */
	private boolean checkNombreLocalidad (Localidad localidad) throws Exception {
		// Se guarda el objeto buscado o de lo contrario null
		Optional<Localidad> localidadEncontrada = iLocalidad.findByNombre(localidad.getNombre());
		// Si el nombre de la localidad buscada esta presente en la base se devuelve el
		// mensaje de error
		if (localidadEncontrada.isPresent()) {
			throw new Exception("Error: Ya existe una localidad con el nombre indicado");
		}
		return true;
	}
	
	/**
	 * Metodo que permite listar las localidades segun el estado en el que se
	 * encuentran
	 * 
	 * @param estado, true si estan activas, false si estan inactivas
	 * @return un Iterable con la lista de localidades encontradas
	 */
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
