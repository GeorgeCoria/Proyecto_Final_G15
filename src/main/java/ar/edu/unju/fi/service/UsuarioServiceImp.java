package ar.edu.unju.fi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	}

	@Override
	public Usuario encontrarUsuario(Long id) throws Exception {
		return iUsuario.findById(id).orElseThrow(()-> new Exception("El usuario no existe"));
	}
	
	@Override
	public void eliminar(Long id) {
		iUsuario.deleteById(id);
	}

	@Override
	public Iterable<Usuario> listarUsuarios() {
		return iUsuario.findAll();
	}

	@Override
	public List<Usuario> findByTipoUsuario(String tipoUsuario) {
		List<Usuario> usuarios = iUsuario.findByTipoUsuario(tipoUsuario);
		return usuarios;
	}

}
