/**
 * Paquetes necesarios para el funcionamiento de la clase Usuario.
 */
package ar.edu.unju.fi.model;

import org.springframework.stereotype.Component;

/**
 * Clase q representa una figura de tipo Usuario.
 * @author Castillo, Jorge Emanuel.
 *
 */

@Component
public class Usuario {

	//----------------- Variables Miembros o Atributos -----------------//
	/**
	 * Representa el nombre de la clase Usuario.
	 */
	private String nombreUsuario;
	
	/**
	 * Representa la contraseña de la clase Usuario.
	 */
	private String password;
	
	/**
	 * Representa el nombre Real de la clase Usuario.
	 */
	private String nombreReal;
	
	/**
	 * Representa el apellido Real de la clase Usuario.
	 */
	private String apellidoReal;
	
	/**
	 * Representa el tipo de usuario de la clase Usuario. (consultor-registrador-adm)
	 */
	private String tipoUsuario;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//----------------- Contructores -----------------//
	
	/**
	 * Constructor por defecto.
	 */
	public Usuario() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * Constructro parametrizado.
	 * 
	 * @param nombreUsuario, valor que se le asignara a la variable miembro this.nombreUsuario
	 * @param password, valor que se le asignara a la variable miembro this.password
	 * @param nombreReal, valor que se le asignara a la variable miembro this.nombreReal
	 * @param apellidoReal, valor que se le asignara a la variable miembro this.apellidoReal
	 * @param tipoUsuario, valor que se le asignara a la variable miembro this.tipoUsuario
	 */
	public Usuario(String nombreUsuario, String password, String nombreReal, String apellidoReal, String tipoUsuario) {
		this.nombreUsuario = nombreUsuario;
		this.password = password;
		this.nombreReal = nombreReal;
		this.apellidoReal = apellidoReal;
		this.tipoUsuario = tipoUsuario;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//----------------- Metodos Accesores -----------------//

	/**
	 * Devuelve el nombreUsuario de la clase Usuario.
	 * @return the nombreUsuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}


	/**
	 * Devuelve la password de la clase Usuario.
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * Devuelve el nombreReal de la clase Usuario.
	 * @return the nombreReal
	 */
	public String getNombreReal() {
		return nombreReal;
	}


	/**
	 * Devuelve el apellidoReal de la clase Usuario.
	 * @return the apellidoReal
	 */
	public String getApellidoReal() {
		return apellidoReal;
	}


	/**
	 * Devuelve el tipoUsuario de la clase Usuario.(consultor-registrador-adm)
	 * @return the tipoUsuario
	 */
	public String getTipoUsuario() {
		return tipoUsuario;
	}

	//-------------------------------------------------------------------------------------//

	/**
	 * Asigna un valor a this.nombreUsuario de la clase Usuario.
	 * @param nombreUsuario, valor para this.nombreUsuario
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}


	/**
	 * Asigna un valor a this.password de la clase Usuario.
	 * @param password, valor para this.password
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * Asigna un valor a this.nombreReal de la clase Usuario.
	 * @param nombreReal, valor para this.nombreReal
	 */
	public void setNombreReal(String nombreReal) {
		this.nombreReal = nombreReal;
	}


	/**
	 * Asigna un valor a this.apellidoReal de la clase Usuario.
	 * @param apellidoReal, valor para this.apellidoReal
	 */
	public void setApellidoReal(String apellidoReal) {
		this.apellidoReal = apellidoReal;
	}


	/**
	 * Asigna un valor a this.tipoUsuario de la clase Usuario.(consultor-registrador-adm)
	 * @param tipoUsuario, valor para this.tipoUsuario
	 */
	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}


	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//----------------- Metodo SobreEscrito -----------------//
	
	/**
	 * Visualiza el estado del objeto, sobreescribiendo 
	 * los datos de los atributo.
	 */
	@Override
	public String toString() {
		return "Usuario [nombreUsuario=" + nombreUsuario + ", password=" + password + ", nombreReal=" + nombreReal
				+ ", apellidoReal=" + apellidoReal + ", tipoUsuario=" + tipoUsuario + "]";
	}	
}
