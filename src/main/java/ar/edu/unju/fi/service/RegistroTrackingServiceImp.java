package ar.edu.unju.fi.service;


import java.time.LocalDateTime;
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

	@Override
	public void crearRegistro(RegistroTracking nuevoRegistro) {
		nuevoRegistro.setFechaHora(LocalDateTime.now());
		iRegistro.save(nuevoRegistro);
	}

	@Override
	public List<RegistroTracking> getRegistrosVehiculo(Vehiculo vehiculos) {
	
		return iRegistro.findByVehiculos(vehiculos);
	}

	@Override
	public List<RegistroTracking> getRegistrosTripulante(Tripulante tripulante) {
		
		List<RegistroTracking> registroTripulante= new ArrayList<>();
		
		for ( RegistroTracking registroTracking : iRegistro.findAll()) {
			
			for ( Tripulante buscarTripulante : registroTracking.getTripulantes()) {
				
				if ( buscarTripulante.getId() == tripulante.getId())
					registroTripulante.add(registroTracking);
			}
				
		}
		
		return registroTripulante;
	}

	
	@Override
	public List<RegistroTracking> getRegistrosLocalidad(Localidad localidad) {
		
		return iRegistro.findByLocalidad(localidad);
	}

	@Override
	public RegistroTracking getRegistros(Long id) throws Exception {
		
		return iRegistro.findById(id).orElseThrow(()-> new Exception("Registro no encontrado"));
	}

}
