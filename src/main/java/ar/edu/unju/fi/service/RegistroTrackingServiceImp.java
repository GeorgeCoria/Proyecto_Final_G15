package ar.edu.unju.fi.service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unju.fi.model.Localidad;
import ar.edu.unju.fi.model.RegistroTracking;
import ar.edu.unju.fi.model.Tripulante;
import ar.edu.unju.fi.model.Vehiculo;
import ar.edu.unju.fi.repository.IRegistroTrackingDAO;

/**
 * Clase que va a implementar la interface IRegistroTrackingService.
 * 
 * @author Marcia Velarde, Jorge E. Castillo
 *
 */
@Service
public class RegistroTrackingServiceImp implements IRegistroTrackingService {

	@Autowired
	private IRegistroTrackingDAO iRegistro;

	/**
	 * Metodo que permite guardar el registro Tracking en el gestor de persistencia
	 * 
	 * @param nuevoRegistro, objeto que se desea guardar
	 */
	@Override
	public void crearRegistro(RegistroTracking nuevoRegistro) {
		//Se le asigna por defecto la fecha actual al registro
		nuevoRegistro.setFecha(LocalDate.now());
		iRegistro.save(nuevoRegistro);
	}

	/**
	 * Devuelve todo los registro del mismo id vehiculo.
	 * Los datos enviados estan ordenado por Fecha.
	 * Solo devolvera si su localidad esta habilitada (true)
	 */
	@Override
	public List<RegistroTracking> getRegistrosVehiculo(Vehiculo vehiculos) {
	
		return iRegistro.findByVehiculosAndLocalidadEstadoOrderByFecha(vehiculos, true);
	}

	/**
	 * Devuelve el registro del tripulante
	 * Los datos enviado son ordenado por Fecha.
	 * Solo devolvera si su localidad esta habilitada (true)
	 */
	@Override
	public List<RegistroTracking> getRegistrosTripulante(Tripulante tripulante) {
		
		List<RegistroTracking> registroTripulante= new ArrayList<>();
		
		for ( RegistroTracking registroTracking : iRegistro.findAllByOrderByFecha()) {
			
			if ( registroTracking.getLocalidad().isEstado() == true) {
				for ( Tripulante buscarTripulante : registroTracking.getTripulantes()) {
					
					if ( buscarTripulante.getId() == tripulante.getId())
						registroTripulante.add(registroTracking);
				}
			}	
		}
		 
		return registroTripulante;
	}

	
	/**
	 * Devuelve el registro de la localidad
	 */
	@Override
	public List<RegistroTracking> getRegistrosLocalidad(Localidad localidad) {
		
		return iRegistro.findByLocalidadOrderByFecha(localidad);
	}

	/**
	 * Devuelve el Registro por ID o una Excepcion
	 */
	@Override
	public RegistroTracking getRegistros(Long id) throws Exception {
		
		return iRegistro.findById(id).orElseThrow(()-> new Exception("Registro no encontrado"));
	}

	/**
	 * Devuelve el resultado buscado por Rango de fechas y Localidad.
	 */
	@Override
	public List<RegistroTracking> getRegistrosRangoFechasAndLocalidad(LocalDate fechaDesde, LocalDate fechaHasta,
			Localidad localidad) {
		
			return iRegistro.findByFechaBetweenAndLocalidadOrderByFecha(fechaDesde, fechaHasta, localidad);
		
	}
}
