package ar.edu.unju.fi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unju.fi.model.Usuario;

/**
 * Interface que nos permitira disminuir el acoplamiento de nuestra aplicación
 * interactuar con los datos almacenados en una base de datos
 * 
 * @author Marcia Velarde
 *
 */
public interface IUsuarioDAO extends JpaRepository<Usuario, Long> {

	public List<Usuario> findByTipoUsuario(String tipoUsuario);

	public Optional<Usuario> findByNombreUsuario(String nombreUsuario);

}
