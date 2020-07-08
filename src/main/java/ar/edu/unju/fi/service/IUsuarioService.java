package ar.edu.unju.fi.service;

import ar.edu.unju.fi.model.Usuario;

/**
 * Interface que nos permitira desarrollar la lógica de negocio de nuestro
 * proyecto y a su vez conectarse con los datos que se encuentran almacenados en
 * algún repositorio de datos.
 * 
 * @author Marcia Velarde
 *
 */
public interface IUsuarioService {
	
	/**
	 * Metodo que permite guardar un usuario en el gestor de persistencia MYSQL
	 * 
	 * @param unUsuario, el usuario a guardar
	 * @return el usuario guardado
	 * @throws Exception
	 */
	public Usuario crear(Usuario unUsuario) throws Exception;
	
	/**
	 * Metodo que permite guardar los datos modificados de un usuario
	 * 
	 * @param unUsuario, usuario del que se desea editar los datos
	 * @return el usuario con los datos modificados
	 * @throws Exception
	 */
	public Usuario modificar(Usuario unUsuario) throws Exception;
	
	/**
	 * Metodo que permite buscar un usuario de acuerdo a su id
	 * 
	 * @param id, id del usuario a buscar
	 * @return el usuario encontrado
	 * @throws Exception, si el usuario no existe
	 */
	public Usuario encontrarUsuario(Long id) throws Exception;
	
	/**
	 * Metodo que permite buscar y eliminar un usuario(en este caso le cambia el
	 * estado a false y queda inactivo el usuario)
	 * 
	 * @param id, id del usuario a eliminar
	 * @throws Exception
	 */
	public void eliminar(Long id) throws Exception;
	
	/**
	 * Metodo que devuelve una lista con los usuarios de acuerdo a un estado elegido
	 * 
	 * @param estado, true(si queremos listar los usuarios activos) o false (si queremos listar los usuarios inactivos)
	 * @return la lista de los usuarios encontrados o en su defecto la exception
	 * @throws Exception, si es que no se encuentran usuarios con ese estado
	 */
	public Iterable<Usuario> listarUsuarios(boolean estado) throws Exception;
	
	public Iterable<Usuario> findByTipoUsuario(String tipoUsuario, boolean estado) throws Exception;
	
	public Usuario buscarUsuario(String buscado) throws Exception;

}
