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

	/**
	 * Metodo que permite guardar el estado de una localidad en el gestor de
	 * persistencia
	 * 
	 * @param localidad, objeto de tipo Localidad que se desea guardar
	 * @throws Exception
	 */
	public void crearLocalidad(Localidad localidad) throws Exception;

	/**
	 * Metodo que permite listar las localidades segun el estado en el que se
	 * encuentran
	 * 
	 * @param estado, true si estan activas, false si estan inactivas
	 * @return un Iterable con la lista de localidades encontradas
	 */
	public Iterable<Localidad> listarLocalidades(boolean estado);

	public void eliminarLocalidad(Long id) throws Exception;

	public void habilitarLocalidad(Long id) throws Exception;

	public Localidad buscarLocalidad1(Long id) throws Exception;
	
	public Optional<Localidad> buscarLocalidad(Long id);
	
	public Localidad buscarNombreLocalidad(String nombre) throws Exception;
	
	public void editarLocalidad(Localidad localidad) throws Exception;
}
