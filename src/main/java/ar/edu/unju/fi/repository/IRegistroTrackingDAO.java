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

	public List<RegistroTracking> findByVehiculosAndLocalidadEstadoOrderByFecha(Vehiculo vehiculos, boolean valor);
	
	public List<RegistroTracking> findByLocalidadOrderByFecha(Localidad localidad);
		
	public List<RegistroTracking> findByFechaBetweenAndLocalidadOrderByFecha(LocalDate fechaDesde, LocalDate fechaHasta, Localidad localidad);

	public List<RegistroTracking> findAllByOrderByFecha();
}
