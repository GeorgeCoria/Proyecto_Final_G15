package ar.edu.unju.fi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unju.fi.model.Vehiculo;
import ar.edu.unju.fi.repository.IVehiculoDAO;

/**
 * Clase que va a implementar la interface IUsuarioService.
 * 
 * @author Marcia Velarde
 *
 */
@Service
public class VehiculoServiceImp implements IVehiculoService{

	@Autowired
	private IVehiculoDAO iVehiculo;
	
	@Override
	public boolean chekPatente(Vehiculo vehiculo) throws Exception {
		Optional<Vehiculo> vehiculoEncontrado = iVehiculo.findByPatente(vehiculo.getPatente());
		if(!vehiculoEncontrado.isPresent()) {
			throw new Exception("La patente no esta registrada");
		}
		return true;
	}
	
	@Override
	public void crearVehiculo(Vehiculo vehiculo) {
		iVehiculo.save(vehiculo);
	}

	@Override
	public Vehiculo buscarVehiculo(String patente) throws Exception{
		return iVehiculo.findByPatente(patente).orElseThrow(() -> new Exception("El vehiculo no existe"));
	}
	
}
