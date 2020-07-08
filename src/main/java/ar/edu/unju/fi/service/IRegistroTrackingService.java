package ar.edu.unju.fi.service;

import java.time.LocalDate;
import java.util.List;

import ar.edu.unju.fi.model.Localidad;
import ar.edu.unju.fi.model.RegistroTracking;
import ar.edu.unju.fi.model.Tripulante;
import ar.edu.unju.fi.model.Vehiculo;

/**
 * Interface que nos permitira desarrollar la lógica de negocio de nuestro
 * proyecto y a su vez conectarse con los datos que se encuentran almacenados en
 * algún repositorio de datos.
 * 
 * @author Marcia Velarde, Jorge E. Castillo
 *
 */
public interface IRegistroTrackingService {

	/**
	 * Metodo que permite guardar el registro Tracking en el gestor de persistencia
	 * 
	 * @param nuevoRegistro, objeto que se desea guardar
	 */
	public void crearRegistro(RegistroTracking nuevoRegistro);
	
	/**
	 * Metodo que permite obtener los registros Tracking del vehiculo en el gestor de persistencia
	 * 
	 * @param vehiculos objeto Vehiculo a buscar.
	 * @return las listas de los registros Vehiculo encontrado.
	 */
	public List<RegistroTracking> getRegistrosVehiculo(Vehiculo vehiculos);
	
	/**
	 * Metodo que permite obtener los registros Tracking del tripulante en el gestor de persistencia
	 * 
	 * @param tripulante objeto Tripulante a buscar
	 * @return las listas de los registros Tripulante encontrado.
	 */
	public List<RegistroTracking> getRegistrosTripulante(Tripulante tripulante);
	
	/**
	 * Metodo que permite obtener los registros Tracking de la Localidad en el gestor de persistencia
	 * 
	 * @param localidad objeto Localidad a buscar
	 * @return las listas de los registro Localidad encontrado.
	 */
	public List<RegistroTracking> getRegistrosLocalidad(Localidad localidad);
	
	/**
	 * Metodo que permite obtener el registro Tracking por ID en el gestor de persistencia
	 * 
	 * @param id objeto Long a buscar 
	 * @return el RegistroTracking  del registro ID buscado.
	 * @throws Exception
	 */
	public RegistroTracking getRegistros(Long id) throws Exception;
	
	/**
	 * Metodo que permite obtener los registros Tracking por rango de fechas  y 
	 * localidad  en el gestor de persistencia
	 * 
	 * @param fechaDesde objeto LocalDate a buscar
	 * @param fechaHasta objeto LocalDate a buscar
	 * @param localidad objeto Localidad a buscar
	 * @return las listas del registro encontrado por rango Fecha y Localidad.
	 */
	public List<RegistroTracking> getRegistrosRangoFechasAndLocalidad(LocalDate fechaDesde, LocalDate fechaHasta, Localidad localidad);
	
}
