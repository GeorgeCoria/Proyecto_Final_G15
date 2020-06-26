/**
 * Paquetes necesarios para el funcionamiento de la clase Usuario.
 */
package ar.edu.unju.fi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

/**
 * Clase q representa una figura de tipo Usuario.
 * @author Castillo, Jorge Emanuel.
 *
 */

@Component
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//----------------- Variables Miembros o Atributos -----------------//
	
	/**
	 * Atributo de tipo Long para identificar cada registro (objetos) de esta clase con un valor univoco
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	/**
	 * Representa el nombre de la clase Usuario.
	 */
	@Column(name = "NOMBRE_USUARIO")
	@NotBlank(message="Debe ingresar un nombre de usuario")
	private String nombreUsuario;
	
	/**
	 * Representa la contraseña de la clase Usuario.
	 */
	@Column
	@NotBlank(message="No puede estar vacío")
	@Size(min=8, message="La contraseña debe tener como minimo 8 caracteres")
	private String password;
	
	/**
	 * Representa el nombre Real de la clase Usuario.
	 */
	@Column(name = "NOMBRE_REAL")
	@NotBlank(message="Debe ingresar el nombre real del usuario")
	private String nombreReal;
	
	/**
	 * Representa el apellido Real de la clase Usuario.
	 */
	@Column(name = "APELLIDO_REAL")
	@NotBlank(message="Debe ingresar el apellido real del usuario")
	private String apellidoReal;
	
	/**
	 * Representa el tipo de usuario de la clase Usuario. (consultor-registrador-adm)
	 */
	@Column(name = "TIPO_USUARIO")
	@NotBlank(message="Debe seleccionar un tipo de usuario")
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
	 * Devuelve el valor del atributo id
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Asigna un valor al atributo id
	 * 
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
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
		return "Usuario [id=" + id + ", nombreUsuario=" + nombreUsuario + ", password=" + password + ", nombreReal=" + nombreReal
				+ ", apellidoReal=" + apellidoReal + ", tipoUsuario=" + tipoUsuario + "]";
	}	
}
