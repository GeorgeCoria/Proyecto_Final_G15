package ar.edu.unju.fi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unju.fi.model.Vehiculo;

/**
 * Interface que nos permitira disminuir el acoplamiento de nuestra aplicación
 * interactuar con los datos almacenados en una base de datos
 * 
 *
 */
public interface IVehiculoDAO extends JpaRepository<Vehiculo, Long>{

	public Optional<Vehiculo> findByPatente(String patente);
	
}
