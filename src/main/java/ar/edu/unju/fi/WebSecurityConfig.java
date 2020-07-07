package ar.edu.unju.fi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.authentication.AuthenticationManagerBeanDefinitionParser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	 * @param Recibe una peticion de tipo HTTP
	 */
	@Override
	protected void configure (HttpSecurity http) throws Exception{
		//A esa peticion se autoriza 
		http
			.authorizeRequests()  //Se autoriza requerimientos
				.antMatchers(resources).permitAll() //los recursos incluidos en el array resources se permiten
				.antMatchers("/","/login").permitAll() //las peticiones para el login son permitidas a todos
								
				
				//Los formularios del admin solo se habilitan para un usuario BD
				.antMatchers("/adminFormulario").hasAuthority("BD")
				.antMatchers("/adminPrincipal").hasAuthority("BD")
				.antMatchers("/adminLocalidad").hasAuthority("BD")
				
				//Los formularios del registrador solo se habilitan para un usuario REGISTRADOR
				.antMatchers("/registros").hasAuthority("REGISTRADOR")
				.antMatchers("/vehiculoForm").hasAuthority("REGISTRADOR")
				.antMatchers("/cargarTripulante").hasAuthority("REGISTRADOR")
				.antMatchers("/registroTracking").hasAuthority("REGISTRADOR")
				.antMatchers("/registroNuevo").hasAuthority("REGISTRADOR")
				
				
				
				//Los formularios del consultor solo se habilitan para el CONSULTOR
				.antMatchers("/vehiculos").hasAuthority("CONSULTOR")
				.antMatchers("/patente").hasAuthority("CONSULTOR")
				.antMatchers("/tripulante").hasAuthority("CONSULTOR")
				
				
				.anyRequest().authenticated() //todo lo demas requiere autenticacion
				.and()
			.formLogin()
				.loginPage("/login") //Se define la pagina de Login
				.permitAll()
				.successHandler(autenticacion)   //Se realiza la redireccion correcta
				.failureUrl("/login?error=true") //donde rediroige en caso de error
				.usernameParameter("username")   //parametro nombre de  usuario para login
				.passwordParameter("password")   //parametro contraseña para login
				.and()
			.logout()
            	.clearAuthentication(true)       
				.permitAll()
				.and()
			.exceptionHandling().accessDeniedPage("/sinPermisos");    //redireccion a una pagina en caso de error 403 sin permisos
			
			http.csrf().disable(); //Permite el deslogueo de usuario
			
	}
	
	//paso 2 -> encriptacion de la clave
	
	//varible que permite codificar la clave por metodo matematico
	BCryptPasswordEncoder BCryptPasswordEncoder;
	
	//codifica la clave
	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		//se define el nivel de encriptacion y devuelve un objeto encriptado
		BCryptPasswordEncoder = new BCryptPasswordEncoder(4);
		return BCryptPasswordEncoder;
	}
	
	@Autowired
	LoginUsuarioServiceImp loginImp;
	
	//metodo que recibe el managerbuild que recupera la informacion del usuario que desea logearse
	//La configuracion global es posbile utilizando el servicio especial loginUsuarioServiceImp y la el bean BCryptPasswordEncoder
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(loginImp).passwordEncoder(passwordEncoder());
	}
}
