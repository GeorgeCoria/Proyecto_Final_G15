package ar.edu.unju.fi.model;

import org.springframework.stereotype.Component;

/**
 * Permite la creacion de objetos de tipo Localidad.
 * De esta manera cada control que se realize en 
 * contara con el dato de la Localidad donde se 
 * realizo dicho control
 * 
 * @author George
 *
 */
@Component
public class Localidad {
	/**
	 * Atributo de la clase Localidad
	 * que almacena el nombre de la 
	 * Localidad 
	 */
	private String nombre;
	
	
	//---------- METODOS CONSTRUCTORES ------
	
	/**
	 * Constructor por defecto sin 
	 * parametros
	 */
	public Localidad() {
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * Constructor Parametrizado
	 * Se asigna nombre de Localidad
	 * 
	 * @param nombre de Localidad
	 */
	public Localidad(String nombre) {
		
		this.nombre = nombre;
	}
	
	//--------- METODOS ACCESORES ------
	
	/**
	 * Permite la capturar
	 * nombre de Localidad
	 * 
	 * @return nombre de Localidad
	 */
	public String getNombre() {
		return nombre;
	}


	/**
	 * Metodo accesor que permite asignar
	 * un nombre al atributo nombre de Localidad
	 * 
	 * @param nombre de Localidad
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	//--------------- Metodos y/o funciones -------------------
	/**
	 * Metodo toString sobrescrito con el fin de capturar  
	 * todos lo atributos de la clase.
	 * Generalmente es llamado cuando se desea visulizar todos
	 * atributos de la localidad
	 * 
	 * 
	 * 
	 */
	@Override
	public String toString() {
		return "Localidad [nombre=" + nombre + "]";
	}

	
	
	
	


	
	
	
	
	

}
