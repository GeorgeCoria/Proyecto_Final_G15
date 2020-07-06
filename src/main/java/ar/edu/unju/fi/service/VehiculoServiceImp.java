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
		String patente = vehiculo.getPatente().toUpperCase().replaceAll("\\s", "");
		int letra=0, numero=0, letra2=0, num2=0;
		if( patente.length()==7) {
			for (int i=0; i < patente.length(); i++) {
				if ( i<=1 || i>=5) {
					if(patente.charAt(i) >= 'A' && patente.charAt(i) <= 'Z')
						letra++;
				}else {
					if(patente.charAt(i) >= '0' && patente.charAt(i) <= '9') 
						numero++;	
				}
				if ( i==0 || i>=4) {
					if(patente.charAt(i) >= 'A' && patente.charAt(i) <= 'Z')
						letra2++;
				}else {
					if(patente.charAt(i) >= '0' && patente.charAt(i) <= '9') 
						num2++;	
				}
			}
		}else {
			if (patente.length() == 6) {
				for (int i=0; i < patente.length(); i++) {
					if ( i<=2) {
						if(patente.charAt(i) >= 'A' && patente.charAt(i) <= 'Z')
							letra++;
					}else {
						if(patente.charAt(i) >= '0' && patente.charAt(i) <= '9') 
							numero++;	
					}
				}
			}else {
				throw new Exception("Debe ingresar una patente valida ej: AAA123 / AA123BB");
			}
		}
		if((letra==3 && numero==3) || (letra==4 && numero==3) || (letra2==4 && num2==3)) {
			Optional<Vehiculo> vehiculoEncontrado = iVehiculo.findByPatente(patente);
			if(!vehiculoEncontrado.isPresent()) {
				throw new Exception("La patente no esta registrada");
			}
		}else {
			throw new Exception("Debe ingresar una patente valida ej: AAA123 / AA123BB");
		}
		return true;
	}
	
	@Override
	public void crearVehiculo(Vehiculo vehiculo) {
		String patente = vehiculo.getPatente().toUpperCase().replaceAll("\\s", "");
		vehiculo.setPatente(patente);
		iVehiculo.save(vehiculo);
	}

	@Override
	public Vehiculo buscarVehiculo(String patente) throws Exception{
		String patente2 = patente.toUpperCase().replaceAll("\\s", "");
		return iVehiculo.findByPatente(patente2).orElseThrow(() -> new Exception("El vehiculo no existe"));
	}
	
}
