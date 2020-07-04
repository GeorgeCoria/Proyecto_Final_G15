package ar.edu.unju.fi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unju.fi.model.Localidad;

/**
 * Interface que nos permitira disminuir el acoplamiento de nuestra aplicación
 * interactuar con los datos almacenados en una base de datos
 * 
 * @author Marcia Velarde, Juan Toconas.
 *
 */
public interface ILocalidadDAO extends JpaRepository<Localidad, Long>{

	public Optional<Localidad> findByNombre (String nombre);
	
}
