package ar.edu.unju.fi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unju.fi.model.RegistroTracking;

/**
 * Interface que nos permitira disminuir el acoplamiento de nuestra aplicación
 * interactuar con los datos almacenados en una base de datos
 * 
 *
 */
public interface IRegistroTrackingDAO extends JpaRepository<RegistroTracking, Long> {

}
