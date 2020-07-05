package ar.edu.unju.fi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.edu.unju.fi.model.Usuario;

/**
 * Interface que nos permitira disminuir el acoplamiento de nuestra aplicación
 * interactuar con los datos almacenados en una base de datos
 * 
 * @author Marcia Velarde
 *
 */
public interface IUsuarioDAO extends JpaRepository<Usuario, Long> {

	/**
	 * Este metodo tiene por objetivo retornar desde el repositorio una lista 
	 * con un tipo de Usuario y estado especificados por parametros
	 * Ej: 
	 * Listar Usuarios de Tipo Registrar Y ademas que esten Activos
	 * 
	 * @param tipoUsuario "REGISTRADOR / ADMINISTRADOR"
	 * @param estado "true (usuario activo), false (Usuario dado de baja)"
	 * @return Lista de Usuarios
	 */
	public List<Usuario> findByTipoUsuarioAndEstado(String tipoUsuario,boolean estado);

	/**
	 * Metodo definido con el fin de permitir realizar una Busqueda segun el Nombre 
	 * de Usuario en el repositorio<br>
	 * Obs: nombreUsuario <> nombreReal
	 * 
	 * @param nombreUsuario
	 * @return objeto de tipo Optional que luego sera analizado si es que contiene 
	 * un resultado valido luego de la busqueda
	 */
	public Optional<Usuario> findByNombreUsuario(String nombreUsuario);
	
	/**
	 * Metodo que permite Listar desde el repositorio todos
	 * aquellos Usuario segun su estado ( true->activo, false-> inactivo).
	 * 
	 * @param estado requerido para generar la Lista
	 * @return Lista de tipo Usuario
	 */
	public List<Usuario> findByEstado(boolean estado);

}
