package ar.edu.unju.fi.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unju.fi.model.Localidad;
import ar.edu.unju.fi.model.RegistroTracking;
import ar.edu.unju.fi.model.Vehiculo;

/**
 * Interface que nos permitira disminuir el acoplamiento de nuestra aplicación
 * interactuar con los datos almacenados en una base de datos
 * 
 * @author Marcia Velarde, Jorge E. Castillo
 */
public interface IRegistroTrackingDAO extends JpaRepository<RegistroTracking, Long> {

	
	
	/**
	 * Busca en la base de dato los registro por Vehiculo y Localidad habilitada
	 * 
	 * @param vehiculos, Objeto Vehiculo a buscar
	 * @param valor, busca todo aquello habilitado (true)
	 * @return las listas de Registros a buscar. Ordenado por fecha
	 */
	public List<RegistroTracking> findByVehiculosAndLocalidadEstadoOrderByFecha(Vehiculo vehiculos, boolean valor);
	
	/**
	 * Busca en la base de dato los registro del objeto Localidad
	 * 
	 * @param localidad objecto Localidad a buscar.
	 * @return las listas de Registros a buscar. Ordenado por fecha
	 */
	public List<RegistroTracking> findByLocalidadOrderByFecha(Localidad localidad);
	
	/**
	 * Busca en la base de datos los registros por rango de Fecha y Localidad.
	 * 
	 * @param fechaDesde objeto LocalDate a buscar.
	 * @param fechaHasta objeto LocalDate a buscar.
	 * @param localidad objeto Localidad a buscar.
	 * @return las listas de Registros a buscar. Ordenado por fecha
	 */
	public List<RegistroTracking> findByFechaBetweenAndLocalidadOrderByFecha(LocalDate fechaDesde, LocalDate fechaHasta, Localidad localidad);
	
	/**
	 * Busca en la base de datos todos registros.
	 * 
	 * @return las listas de Registros. Ordenado por fecha
	 */
	public List<RegistroTracking> findAllByOrderByFecha();
}
