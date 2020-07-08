package ar.edu.unju.fi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unju.fi.model.Vehiculo;

/**
 * Interface que nos permitira disminuir el acoplamiento de nuestra aplicación
 * interactuar con los datos almacenados en una base de datos
 * 
 * @author Marcia Velarde
 *
 */
public interface IVehiculoDAO extends JpaRepository<Vehiculo, Long>{

	/**
	 * Metodo que tiene por objetivo buscar un vehiculo en la base de datos de
	 * acuerdo a su patente
	 * 
	 * @param patente, patente del vehiculo buscado
	 * @return un Optional con el vehiculo encontrado o null
	 */
	public Optional<Vehiculo> findByPatente(String patente);
	
}
