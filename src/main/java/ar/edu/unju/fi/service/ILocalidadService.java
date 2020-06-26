package ar.edu.unju.fi.service;

import ar.edu.unju.fi.model.Localidad;

/**
 * Interface que nos permitira desarrollar la lógica de negocio de nuestro
 * proyecto y a su vez conectarse con los datos que se encuentran almacenados en
 * algún repositorio de datos.
 * 
 * @author Marcia Velarde, Juan Toconas
 *
 */
public interface ILocalidadService {

	public void crearLocalidad(Localidad localidad);
	public Iterable<Localidad> listarLocalidades();
	
}
