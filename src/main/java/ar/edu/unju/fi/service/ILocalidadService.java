package ar.edu.unju.fi.service;

import java.util.Optional;

import ar.edu.unju.fi.model.Localidad;

/**
 * Interface que nos permitira desarrollar la lógica de negocio de nuestro
 * proyecto y a su vez conectarse con los datos que se encuentran almacenados en
 * algún repositorio de datos.
 * 
 * @author Marcia Velarde, Juan Toconas, Jorge E. Castillo.
 *
 */
public interface ILocalidadService {

	public void crearLocalidad(Localidad localidad) throws Exception;
	
	public Iterable<Localidad> listarLocalidades();
	
	public void eliminarLocalidad (Long id) ;
	
	public Localidad buscarLocalidad1(Long id) throws Exception;
	
	public Optional<Localidad> buscarLocalidad(Long id);
	
	public Localidad buscarNombreLocalidad(String nombre) throws Exception;
	
	public void editarLocalidad(Localidad localidad) throws Exception;
}
