package ar.edu.unju.fi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unju.fi.model.Vehiculo;
import ar.edu.unju.fi.repository.IVehiculoDAO;

/**
 * Clase que va a implementar la interface IVehiculoService.
 * 
 * @author Marcia Velarde
 *
 */
@Service
public class VehiculoServiceImp implements IVehiculoService{

	/**
	 * Inyección de un objeto implementador de la clase IVehiculoDAO para la conexión con la capa repository
	 */
	@Autowired
	private IVehiculoDAO iVehiculo;
	
	/**
	 * Metodo que guarda el nuevo vehiculo en el gestor de persistencia MYSQL
	 * 
	 * @param vehiculo, el objeto que se desea guardar
	 */
	@Override
	public void crearVehiculo(Vehiculo vehiculo) {
		// Transforma la patente ingresada en mayusculas y le saca los espacios para un
		// mejor control
		String patente = vehiculo.getPatente().toUpperCase().replaceAll("\\s", "");
		vehiculo.setPatente(patente);
		iVehiculo.save(vehiculo);
	}

	/**
	 * Metodo que checkea que la patente ingresada tenga el formato adecuado, ya sea
	 * AAA111, AA111BB o A111BBB, y la busca en el gestor de persistencia MYSQL
	 * 
	 * @param vehiculo, el objeto del cual se desea checkear la patente
	 * @return true, si la patente tiene el formato correcto y no esta registrada
	 * @throws Exception, si la patente no tiene el formato correcto o esta
	 *                    registrada en el gestor de persistencia MYSQL
	 */
	@Override
	public boolean chekPatente(Vehiculo vehiculo) throws Exception {
		// Se guarda en un string la patente del vehiculo sin espacios y en mayusculas
		// para hacer mas facil el analisis
		String patente = vehiculo.getPatente().toUpperCase().replaceAll("\\s", "");
		// Se declaran variables de tipo int para contar las letras y numeros de las
		// patentes
		int letra = 0, numero = 0, letra2 = 0, num2 = 0;
		if (patente.length() == 7) {
			for (int i = 0; i < patente.length(); i++) {
				if (i <= 1 || i >= 5) {
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
		//Si la cantidad de letras y numeros de la patente ingresada coinciden con el formato de una patente
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

	/**
	 * Metodo que busca un vehiculo por su patente en el gestor de persistencia MYSQL
	 * 
	 * @param patente, la patente del vehiculo buscado
	 * @return el vehiculo buscado si lo encuentra
	 * @throws Exception, en caso de no encontrarlo
	 */
	@Override
	public Vehiculo buscarVehiculo(String patente) throws Exception{
		String patente2 = patente.toUpperCase().replaceAll("\\s", "");
		return iVehiculo.findByPatente(patente2).orElseThrow(() -> new Exception("El vehiculo no existe"));
	}
	
}
