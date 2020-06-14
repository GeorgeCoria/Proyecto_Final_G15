package ar.edu.unju.fi.model;

import org.springframework.stereotype.Component;

/**
 * Clase que permite gestionar los datos de los Tripulantes 
 * de un vehiculo, con el fin de almacenar un registro
 * de cada persona presente en un vehiculo cuando este
 * pase por un control.
 * 
 * @author George
 *
 */
@Component
public class Tripulante {
	/**
	 * Numero de documento del tripulante
	 */
	private String documento;
	/**
	 * Apellido del tripulante
	 */
	private String apellido;
	/**
	 * Nombres del Tripulante
	 */
	private String nombres;
	/**
	 * Nacionalidad del Tripulante
	 */
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
	/**
	 * Realiza captura del numero de documento del tripulante.
	 * 
	 * @return DNI
	 */
	public String getDocumento() {
		return documento;
	}
	
	/**
	 * Permite asignar un Numero de Dni a un trupulante
	 * 
	 * @param numero de documento
	 */
	public void setDocumento(String documento) {
		this.documento = documento;
	}

	/**
	 * Realiza captura del apellido del tripulante
	 * 
	 * @return apellido del trupulante
	 */
	public String getApellido() {
		return apellido;
	}
	

	/**
	 * Permite asignar un Apellido para el tripulante
	 * 
	 * @param apellido del tripulante
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	/**
	 * Realiza la captura del Nombre del tripulante
	 * 
	 * @return nombre del tripulante
	 */
	public String getNombres() {
		return nombres;
	}
	
	/**
	 * Permite asignar Nombres al Tripulante
	 * 
	 * @param nombres del tripulante
	 */
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	
	/**
	 * Realiza la captura de la nacionalidad del tripulante
	 * 
	 * @return nacionalidad del tripulante
	 */
	public String getNacionalidad() {
		return nacionalidad;
	}

	/**
	 * Permite asingar una Nacionalidad al tripulante
	 * 
	 * @param nacionalidad
	 */
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	
	//-------------------- Metodos y/o Funciones -------------------------
	
	/**
	 * Metodo toString() sobrescrito con el fin de capturar  
	 * todos lo atributos de la clase.
	 * Generalmente es llamado cuando se desea visulizar todos
	 * atributos de un tripulante
	 * 
	 * @return objeto tripulante
	 */
	@Override
	public String toString() {
		return "Tripulante [documento=" + documento + ", apellido=" + apellido + ", nombres=" + nombres
				+ ", nacionalidad=" + nacionalidad + "]";
	}
	
	
	
	
}
