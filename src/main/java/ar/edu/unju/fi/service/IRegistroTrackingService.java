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

	public void crearRegistro(RegistroTracking nuevoRegistro);
	
	public List<RegistroTracking> getRegistrosVehiculo(Vehiculo vehiculos);
	
	public List<RegistroTracking> getRegistrosTripulante(Tripulante tripulante);
	
	public List<RegistroTracking> getRegistrosLocalidad(Localidad localidad);
	
	public RegistroTracking getRegistros(Long id) throws Exception;
	
	public List<RegistroTracking> getRegistrosRangoFechasAndLocalidad(LocalDate fechaDesde, LocalDate fechaHasta, Localidad localidad);
	
}
