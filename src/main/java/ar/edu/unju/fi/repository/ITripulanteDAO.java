package ar.edu.unju.fi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unju.fi.model.Tripulante;

/**
 * Interface que nos permitira disminuir el acoplamiento de nuestra aplicación
 * interactuar con los datos almacenados en una base de datos
 * 
 * @author Marcia Velarde
 *
 */
public interface ITripulanteDAO extends JpaRepository<Tripulante, Long>{

	/**
	 * Este metodo tiene por objetivo buscar un tripulante en la base de datos de
	 * acuerdo a su numero de documento
	 * 
	 * @param documento, numero de documento del tripulante buscado
	 * @return un Optional con el tripulante encontrado o null
	 */
	public Optional<Tripulante> findByDocumento(String documento);
}
