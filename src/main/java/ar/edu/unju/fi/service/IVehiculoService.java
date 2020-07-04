package ar.edu.unju.fi.service;

import ar.edu.unju.fi.model.Vehiculo;

/**
 * Interface que nos permitira desarrollar la lógica de negocio de nuestro
 * proyecto y a su vez conectarse con los datos que se encuentran almacenados en
 * algún repositorio de datos.
 * 
 * @author Marcia Velarde
 *
 */
public interface IVehiculoService {

	public void crearVehiculo(Vehiculo vehiculo);
	
	public boolean chekPatente(Vehiculo vehiculo) throws Exception;
	
	public Vehiculo buscarVehiculo(String patente) throws Exception;
}
