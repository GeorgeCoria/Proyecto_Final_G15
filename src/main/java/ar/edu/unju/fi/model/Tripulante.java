package ar.edu.unju.fi.model;

import org.springframework.stereotype.Component;

@Component
public class Tripulante {

	private String documento;
	private String apellido;
	private String nombres;
	private String nacionalidad;
	
	
	//----------- CONSTRUCTORES ----------------
	
	/**
	 * Constructor por defecto sin 
	 * parametros
	 */
	public Tripulante() {
		
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor Parametrizado
	 * Permite inicializar cada atributo de la clase
	 * utilizando los parametros ingresados
	 * 
	 * @param documento del tripulante
	 * @param apellido del tripulante
	 * @param nombres del tripulante
	 * @param nacionalidad del tripulante
	 */
	public Tripulante(String documento, String apellido, String nombres, String nacionalidad) {
		
		this.documento = documento;
		this.apellido = apellido;
		this.nombres = nombres;
		this.nacionalidad = nacionalidad;
	}
	
	//--------- METODOS ACCESORES ------

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	
	
	
}
