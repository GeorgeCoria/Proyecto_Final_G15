package ar.edu.unju.fi.service;

import java.util.List;

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
	
	public Usuario crear(Usuario unUsuario) throws Exception;
	public Usuario modificar(Usuario unUsuario) throws Exception;
	public Usuario encontrarUsuario(Long id) throws Exception;
	public void eliminar(Long id);
	public Iterable<Usuario> listarUsuarios();
	public List<Usuario> findByTipoUsuario(String tipoUsuario);

}
