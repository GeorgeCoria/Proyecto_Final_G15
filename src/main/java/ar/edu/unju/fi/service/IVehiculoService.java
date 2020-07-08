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

	/**
	 * Metodo que guarda el nuevo vehiculo en el gestor de persistencia
	 * 
	 * @param vehiculo, el objeto que se desea guardar
	 */
	public void crearVehiculo(Vehiculo vehiculo);
	
	/**
	 * Metodo que checkea que la patente ingresada tenga el formato adecuado, ya sea
	 * AAA111, AA111BB o A111BBB, y la busca en el gestor de persistencia
	 * 
	 * @param vehiculo, el objeto del cual se desea checkear la patente
	 * @return true, si la patente tiene el formato correcto y no esta registrada
	 * @throws Exception, si la patente no tiene el formato correcto o esta
	 *                    registrada en el gestor de persistencia
	 */
	public boolean chekPatente(Vehiculo vehiculo) throws Exception;
	
	/**
	 * Metodo que busca un vehiculo por su patente en el gestor de persistencia
	 * 
	 * @param patente, la patente del vehiculo buscado
	 * @return el vehiculo buscado si lo encuentra
	 * @throws Exception, en caso de no encontrarlo
	 */
	public Vehiculo buscarVehiculo(String patente) throws Exception;
}
