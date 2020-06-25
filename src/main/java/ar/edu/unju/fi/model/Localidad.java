package ar.edu.unju.fi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Entity
@Table(name = "localidades")
public class Localidad implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// ---------------VARIABLES MIEMBRO---------------//
	
	/**
	 * Atributo de tipo Long para identificar cada registro (objetos) de esta clase con un valor univoco
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name="ID")
	private Long id;
	
	/**
	 * Atributo de la clase Localidad
	 * que almacena el nombre de la 
	 * Localidad 
	 */
	@Column(name="NOMBRE",length=40,nullable=true)
	private String nombre;
	
	
	// -----------------CONSTRUCTORES-----------------//
	
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
	
	// ---------------METODOS ACCESORES---------------//
	
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
		return "Localidad [id=" + id + ", nombre=" + nombre + "]";
	}

	
	
	
	


	
	
	
	
	

}
