package ar.edu.unju.fi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ar.edu.unju.fi.model.Usuario;
import ar.edu.unju.fi.repository.IUsuarioDAO;
import java.util.Optional;

/**
 * Clase que va a implementar la interface IUsuarioService.
 * 
 * @author Marcia Velarde
 *
 */
@Service
public class UsuarioServiceImp implements IUsuarioService{

	@Autowired
	private IUsuarioDAO iUsuario;

	/**
	 * Metodo que permite guardar un usuario en el gestor de persistencia MYSQL
	 * 
	 * @param unUsuario, el usuario a guardar
	 * @return el usuario guardado
	 * @throws Exception
	 */
	@Override
	public Usuario crear(Usuario unUsuario) throws Exception {
		// Se verifica que el nombre de usuario este disponible
		if (checkNombreUsuario(unUsuario)) {
			String contraseña = unUsuario.getPassword();
			BCryptPasswordEncoder encriptador = new BCryptPasswordEncoder(4);
			// Se encripta la contraseña del usuario
			unUsuario.setPassword(encriptador.encode(contraseña));
			iUsuario.save(unUsuario);
		}
		return unUsuario;
	}

	/**
	 * 
	 * Metodo que permite determinar si el nombre de usuario esta disponible
	 * 
	 * @param usuario, usuario del que se desea verificar el nombre de usuario
	 * @return true, si el nombre de usuario esta disponible
	 * @throws Exception, sui el nombre de usuario no esta disponible
	 */
	private boolean checkNombreUsuario(Usuario usuario) throws Exception {
		//Guarda en un optional el usuario encontrado o null
		Optional<Usuario> usuarioEncontrado = iUsuario.findByNombreUsuario(usuario.getNombreUsuario());
		if (usuarioEncontrado.isPresent()) {
			throw new Exception("Nombre de usuario no disponible");
		}
		return true;
	}

	/**
	 * Metodo que permite guardar los datos modificados de un usuario
	 * 
	 * @param unUsuario, usuario del que se desea editar los datos
	 * @return el usuario con los datos modificados
	 * @throws Exception
	 */
	@Override
	public Usuario modificar(Usuario unUsuario) throws Exception {
		Usuario usuarioGuardar = encontrarUsuario(unUsuario.getId());
		mapearUsuario(unUsuario, usuarioGuardar);
		return iUsuario.save(usuarioGuardar);
	}

	/**
	 * Metodo que permite asignar a cada atributo del usuario, los nuevos datos
	 * editados
	 * 
	 * @param desde, objeto donde se encuentran los datos editados
	 * @param hacia, objeto doonde se encuentran los datos antiguos
	 */
	private void mapearUsuario(Usuario desde, Usuario hacia) {
		hacia.setNombreReal(desde.getNombreReal());
		hacia.setApellidoReal(desde.getApellidoReal());
		hacia.setEstado(desde.isEstado());
		hacia.setTipoUsuario(desde.getTipoUsuario());
	}

	/**
	 * Metodo que permite buscar un usuario de acuerdo a su id
	 * 
	 * @param id, id del usuario a buscar
	 * @return el usuario encontrado
	 * @throws Exception, si el usuario no existe
	 */
	@Override
	public Usuario encontrarUsuario(Long id) throws Exception {
		return iUsuario.findById(id).orElseThrow(() -> new Exception("El usuario no existe"));
	}

	/**
	 * Metodo que permite buscar y eliminar un usuario(en este caso le cambia el
	 * estado a false y queda inactivo el usuario)
	 * 
	 * @param id, id del usuario a eliminar
	 * @throws Exception
	 */
	@Override
	public void eliminar(Long id) throws Exception {
		Usuario usuario = encontrarUsuario(id);
		usuario.setEstado(false);
		iUsuario.save(usuario);
	}

	/**
	 * Metodo que devuelve una lista con los usuarios de acuerdo a un estado elegido
	 * 
	 * @param estado, true(si queremos listar los usuarios activos) o false (si queremos listar los usuarios inactivos)
	 * @return la lista de los usuarios encontrados o en su defecto la exception
	 * @throws Exception, si es que no se encuentran usuarios con ese estado
	 */
	@Override
	public Iterable<Usuario> listarUsuarios(boolean estado) throws Exception {
		Iterable<Usuario> listaUsuarios = iUsuario.findByEstado(estado);
		if(!listaUsuarios.iterator().hasNext()) {
			throw new Exception("Lista Vacia ");
		}
		
		return listaUsuarios; 
	}

	@Override
	public Iterable<Usuario> findByTipoUsuario(String tipoUsuario, boolean estado) throws Exception {
		Iterable<Usuario> usuarios = iUsuario.findByTipoUsuarioAndEstado(tipoUsuario, estado);
		if(!usuarios.iterator().hasNext()) {
			throw new Exception("No hay registros de este tipo de Usuario");
		}
		return usuarios;
	}

	@Override
	public Usuario buscarUsuario(String buscado) throws Exception {
		Usuario encontrado = iUsuario.findByNombreUsuario(buscado).orElseThrow(()->new Exception ("El usuario buscado no Existe en nuestra base de Datos"));
		
		return encontrado;
	}

}
