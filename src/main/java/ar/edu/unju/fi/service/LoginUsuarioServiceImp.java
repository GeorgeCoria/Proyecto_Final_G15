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
 * Servicio especial que implementa la interfaz UserDetailsService de spring security que guarda el usuario con el que va a trabajar para su autenticacion
 * Permite 3 acciones : verificar/autenticar el usuario; redirigir/mostrar segun el tipo de usuario; Encriptacion de clave de usuario
 * @author Toconas,Juan Manuel
 */

@Service
public class LoginUsuarioServiceImp implements UserDetailsService {
	
	
	/**
	 * Inyeccion de la interfaz DAO del usuario
	 */
	@Autowired
	IUsuarioDAO usuarioImp;

	/**
	 * Se sobreescribe el metodo de UserDetailService indicandole los elementos que tendra que usar de nuestra clase del modelo para la
	 * poder trabajar, siendo nombre de  usuario, una contraseña y una lista de autorizaciones (roles)
	 * @param Recibe un nombre de usuario, si hay problemas pasa por una excepcion "UsernameNotFoundException"
	 * El UserDetail devuelve un usuario especial de su tipo homonimo
	 * @return El servicio devuelve un un UserDetails, especial de spring security
	 */
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		/**
		 * Paso 1
		 * Se busca y guarda en un  variable auxiliar al usuario y si no se encuentra devuelve un de error de login
		 */
		Usuario encontrado = usuarioImp.findByNombreUsuario(username).orElseThrow(()-> new UsernameNotFoundException("Login Invalido"));
		
		/**
		 * Paso 2		
		 * Definir lista de autorizacion/roles
		 *  UserDetails necesita en sus parametros una lista/set GrantedAuthority que guarda tipo/nivel de autorizacion del usuario encontrado
		 */
		List<GrantedAuthority> tipos = new ArrayList<>();
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(encontrado.getTipoUsuario());
		tipos.add(grantedAuthority);
		
		/**
		 * Paso 3
		 * Se declara un objeto UserDetails que sera el que retornara el metodo
		 * Este objeto trabaja solo ciertos datos del usuario que deben pasarse por parametros
		 * El usuario logeado sera el de esta variable.
		 */
		UserDetails user = (UserDetails) new User(username, encontrado.getPassword(), tipos);
		return user;
	}
}
