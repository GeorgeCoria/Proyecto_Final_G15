package ar.edu.unju.fi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unju.fi.model.Tripulante;

/**
 * Interface que nos permitira disminuir el acoplamiento de nuestra aplicación
 * interactuar con los datos almacenados en una base de datos
 * 
 *
 */
public interface ITripulanteDAO extends JpaRepository<Tripulante, Long>{

	public Optional<Tripulante> findByDocumento(String documento);
}
