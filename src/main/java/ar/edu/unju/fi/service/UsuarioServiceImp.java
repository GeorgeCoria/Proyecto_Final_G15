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
	
	
	@Override
	public Usuario crear(Usuario unUsuario) throws Exception {
		if(checkNombreUsuario(unUsuario)) {
			String contraseña = unUsuario.getPassword();
			BCryptPasswordEncoder encriptador = new BCryptPasswordEncoder(4);
			unUsuario.setPassword(encriptador.encode(contraseña));
			iUsuario.save(unUsuario);
		}
		return unUsuario;
	}

	private boolean checkNombreUsuario (Usuario usuario) throws Exception{
		Optional<Usuario> usuarioEncontrado = iUsuario.findByNombreUsuario(usuario.getNombreUsuario());
		if(usuarioEncontrado.isPresent()){
			throw new Exception("Nombre de usuario no disponible");
		}
		return true;
	}
	
	@Override
	public Usuario modificar(Usuario unUsuario) throws Exception {
		Usuario usuarioGuardar = encontrarUsuario(unUsuario.getId());
		mapearUsuario(unUsuario,usuarioGuardar);
		return iUsuario.save(usuarioGuardar);
	}

	private void mapearUsuario(Usuario desde, Usuario hacia) {
		hacia.setNombreReal(desde.getNombreReal());
		hacia.setApellidoReal(desde.getApellidoReal());
		hacia.setEstado(desde.isEstado());
		hacia.setTipoUsuario(desde.getTipoUsuario());
	}

	@Override
	public Usuario encontrarUsuario(Long id) throws Exception {
		return iUsuario.findById(id).orElseThrow(()-> new Exception("El usuario no existe"));
	}
	
	@Override
	public void eliminar(Long id) throws Exception {
		Usuario usuario= encontrarUsuario(id);
		usuario.setEstado(false);
		iUsuario.save(usuario);	
		
	}

	@Override
	public Iterable<Usuario> listarUsuarios(boolean estado) throws Exception{
		Iterable<Usuario> listaUsuarios=iUsuario.findByEstado(estado);
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
		// TODO Auto-generated method stub	
		Usuario encontrado = iUsuario.findByNombreUsuario(buscado).orElseThrow(()->new Exception ("El usuario buscado no Existe en nuestra base de Datos"));
		
		return encontrado;
	}

}
