package ar.edu.unju.fi;

/**
 * Clase que permite controlar el exito del login de un usuario de forma que se rediriga de forma exitosa
 * @author Toconas, Juan
 */

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class AutenticacionSuccessHandler implements AuthenticationSuccessHandler{

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy(); 
	
	/**
	 * Sobrescritura del metodo onAuthenticationSuccessque recibe una peticion, una respuesta y una autenticacion que son los roles del usuario
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
			//se definen 3 variables boolean que se usan para definir que tipo de usuario se ha logeado
			boolean tipoBD = false;
			boolean tipoConsultor = false;
			boolean tipoRegistrador = false;
			
			//Se recupera el rol del usuario
			Collection <? extends GrantedAuthority > authorities = authentication.getAuthorities();
			
			//se modifican los boolean segun el rol del usuario recuperado
			for (GrantedAuthority grantedAuthority : authorities) {
				if (grantedAuthority.getAuthority().equals("BD")) {
					tipoBD = true;
					break;
				}else {
					if(grantedAuthority.getAuthority().equals("CONSULTOR")) {
						tipoConsultor = true;
						break;
					}else {
						if(grantedAuthority.getAuthority().equals("REGISTRADOR")) {
						tipoRegistrador = true;
						break;
						}
					}
				}
			}
			
			
			//Se redirige a una pagina relacionada al rol del usuario
			if (tipoBD) {
				redirectStrategy.sendRedirect(request, response, "/adminPrincipal");
			}else {
				if(tipoConsultor) {
					redirectStrategy.sendRedirect(request, response, "/patente");
				}else {
					if(tipoRegistrador) {
						redirectStrategy.sendRedirect(request, response, "/registros");
					}else {
						throw new IllegalStateException();	
					}
						}
					}
				}
			}
