package ar.edu.unju.fi.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unju.fi.model.RegistroTracking;
import ar.edu.unju.fi.repository.IRegistroTrackingDAO;

/**
 * Clase que va a implementar la interface IRegistroTrackingService.
 * 
 * @author Marcia Velarde
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
}
