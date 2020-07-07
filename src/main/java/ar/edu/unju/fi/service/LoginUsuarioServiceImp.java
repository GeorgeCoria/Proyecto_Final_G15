package ar.edu.unju.fi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ar.edu.unju.fi.model.Usuario;
import ar.edu.unju.fi.repository.IUsuarioDAO;

/**
 * Servicio especial que implementa la clase UserDetailsService de spring security que guarda el usuario con el que va a trabajar para su autenticacion
 * Permite 3 acciones : verificar/autenticar el usuario; redirigir/mostrar segun el tipo de usuario; Encriptacion de clave de usuario
 * @author Toconas,Juan Manuel
 */

@Service
public class LoginUsuarioServiceImp implements UserDetailsService {
	
	
	@Autowired
	IUsuarioDAO usuarioImp;

	/**
	 * Se sobreescribe el metodo de UserDetailService. Recibe un nombre de usuario y si hay problemas pasa por una excepcion "UsernameNotFoundException"
	 * El UserDetail devuelve un usuario especial de su tipo homonimo
	 * @return El servicio devuelve un un UserDetails, especial de spring security
	 */
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//Se busca al usuario y si no se encuentra devuelve un de error de login (Paso 1 - Busqueda)
		Usuario encontrado = usuarioImp.findByNombreUsuario(username).orElseThrow(()-> new UsernameNotFoundException("Login Invalido"));
		
		//(Pase 2 definir lista de autorizacion) UserDetails necesita en sus paramtros una lista/set GrantedAuthority que guarda tipo/nivel de autorizacion del usuario encontrado
		List<GrantedAuthority> tipos = new ArrayList<>();
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(encontrado.getTipoUsuario());
		tipos.add(grantedAuthority);
		
		//No devuelvo el usuario encontrado sino algunos campos del mismo (Paso 3 - enviar por parametros los datos encontrados y la lista de tipos)
		//La variable tipo debe ser una lista de GrantedAuthority que guarde los tipos de usuarios
		UserDetails user = (UserDetails) new User(username, encontrado.getPassword(), tipos);
		//El usuario logueado permanecera dentro de la varaible User
		return user;
	}
}
