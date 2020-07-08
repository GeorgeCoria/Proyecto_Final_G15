package ar.edu.unju.fi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import ar.edu.unju.fi.service.LoginUsuarioServiceImp;

/**
 * Configuracion de las opciones por defecto del proyeto al extender WebSecurityConfigurerAdapter
 * Se determinara que acciones son seguras o pueden permitirse. 
 * @author Toconas,Juan
 */


/**
 * Se indica que es un archivo de configuracion
 * Se habilita la opcion de websecurity
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	/**
	 * Se inyecta la clase AutenticacionSuccessHandler que determinara la redireccion que cada usuario tendra segun su rol
	 */
	@Autowired
	private AutenticacionSuccessHandler autenticacion;
	
	/**
	 * Se definen las rutas de  aquellos elementos que se podran utilizar, sin eso no podran usarse y seran ignorados en el proyecto
	 */
	String [] resources = new String[] {
		"/include/**","/css/**","/icons/**","/images/**","/js/**","/layer/**","/webjars/**",
	};
		
	/**
	 * Se SobreEscribe el metodo configure
	 * @param Recibe una peticion de tipo HTTP que sera evaluada por el Authenticacion Manager
	 */
	@Override
	protected void configure (HttpSecurity http) throws Exception{
		//A esa peticion se autoriza 
		http
			.authorizeRequests()
				//Se evaluan las peticiones html
				//Las rutas del array creado seran validas para todo el proyecto
				.antMatchers(resources).permitAll() 
				 //las peticiones para el login son permitidas a todos los usuarios
				.antMatchers("/","/login").permitAll()
							
				//SE DEFINEN LAS PAGINAS AUTORIZADAS SEGUN EL ROL DEL USUARIO
				
				.antMatchers("/adminFormulario").hasAuthority("BD")
				.antMatchers("/adminPrincipal").hasAuthority("BD")
				.antMatchers("/adminLocalidad").hasAuthority("BD")
				

				.antMatchers("/registros").hasAuthority("REGISTRADOR")
				.antMatchers("/vehiculoForm").hasAuthority("REGISTRADOR")
				.antMatchers("/cargarTripulante").hasAuthority("REGISTRADOR")
				.antMatchers("/registroTracking").hasAuthority("REGISTRADOR")
				.antMatchers("/registroNuevo").hasAuthority("REGISTRADOR")
				
				.antMatchers("/vehiculos").hasAuthority("CONSULTOR")
				.antMatchers("/patente").hasAuthority("CONSULTOR")
				.antMatchers("/tripulante").hasAuthority("CONSULTOR")
				
				//Las peticiones requieren autenticacion
				.anyRequest().authenticated() 
				
				.and()
			
			//En caso de existir una Pagina Login se consideraran las siguientes configuraciones
			.formLogin()
				//Se define la peticion que se usara para el login
				.loginPage("/login") 
				.permitAll()
				
				 //Se realiza la redireccion correcta al logearse correctamente usando la variable autentication 
				.successHandler(autenticacion)  
				
				//Pagina donde se redirecciona en caso de peticion invalida
				.failureUrl("/login?error=true") 
				
				//parametro nombre de  usuario para login que es tomado de la pagina del formulario de login
				.usernameParameter("username")   
				//parametro nombre de  usuario para login que es tomado de la pagina del formulario de login
				.passwordParameter("password")
				
				.and()
				
			//Configuraciones para el logout
			.logout()
				//Todos tienen acceso a logout
				.permitAll()
				//Permite establecer una pagina a la cual redirigir en caso de logout
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.and()
			//Configuraciones para ingreso de paginas sin autorizacion
			.exceptionHandling().accessDeniedPage("/sinPermisos");
			
			/*Ejecuta un ataque CSRF que fuerza al navegador web validado a enviar una petición a una aplicación web a comandos que podrian ser invalidos.
			 http.csrf().disable();
			*/ 
			
	}
	
	//paso 2 -> encriptacion de la clave
	//varible que permite codificar la clave por metodo matematico
	BCryptPasswordEncoder BCryptPasswordEncoder;
	
	
	/**
	 * Metodo que define el nivel de encriptacion de BCryptPasswordEncoder 
	 * @return BCryptPasswordEncoder con un nivel de encriptacion 4 que se usara para codificar una contraseña String
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		BCryptPasswordEncoder = new BCryptPasswordEncoder(4);
		return BCryptPasswordEncoder;
	}
	
	
	/**
	 * Se hace una inyeccion del servicioLoginUsuarioServiceImp que autentica a el usuario
	 */
	@Autowired
	LoginUsuarioServiceImp loginImp;
	
	//metodo que recibe el managerbuild que recupera la informacion del usuario que desea logearse
	//La configuracion global es posbile utilizando el servicio especial loginUsuarioServiceImp y la el bean BCryptPasswordEncoder
	
	/**
	 * Metodo que usara el AuthenticationManagerBuilder que recupera la informacion del usuario guardado
	 * userDetailsService recuperara la informacion del usuario que quiere entrar y crea un usuario UserDetail
	 * Finalmente los datos recuperados con usados para la configuracion global
	 * @param AuthenticationManager
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(loginImp).passwordEncoder(passwordEncoder());
	}
}
