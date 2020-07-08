package ar.edu.unju.fi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.edu.unju.fi.model.Localidad;

/**
 * Interface que nos permitira disminuir el acoplamiento de nuestra aplicación
 * interactuar con los datos almacenados en una base de datos
 * 
 * @author Marcia Velarde, Juan Toconas.
 *
 */
public interface ILocalidadDAO extends JpaRepository<Localidad, Long>{

	/**
	 * Este metodo tiene por objetivo buscar una localidad en la base de datos segun
	 * su nombre
	 * 
	 * @param nombre, nombre de la localidad buscada
	 * @return un optional con la localidad encontrada o null
	 */
	public Optional<Localidad> findByNombre (String nombre);
	
	/**
	 * Este metodo tiene por objetivo buscar una localidad en la base de datos segun
	 * su estado 
	 * 
	 * @param estado, "true" si la localidad esta activa y "false" si esta inactiva
	 * @return Iterable con las localidades encontradas
	 */
	@Query("from Localidad l order by l.nombre")
	public Iterable<Localidad> findByEstado(boolean estado);
	
}
