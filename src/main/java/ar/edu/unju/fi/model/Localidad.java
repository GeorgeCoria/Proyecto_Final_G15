package ar.edu.unju.fi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
	@Column (name = "ID_LOCALIDAD")
	private Long id;
	
	/**
	 * Atributo de la clase Localidad
	 * que almacena el nombre de la 
	 * Localidad 
	 */
	@Column(name="NOMBRE",length=40,nullable=true)
	@NotBlank(message="Debe ingresar un nombre de localidad")
	private String nombre;
	
	/**
	 * Atributo que permite definir el estado de una Localidad
	 * true->habilitado
	 * false->Inhabilitado
	 */
	@Column(name="ESTADO")
	@NotNull(message="Seleccione estado de Localidad")
	private boolean estado;
	
	
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
	 * Se asigna un estado
	 * 
	 * @param nombre de Localidad
	 * @param estado de Localidad
	 */
	
	public Localidad( String nombre, boolean estado) {
		
		this.nombre = nombre;
		this.estado = estado;
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
	
/**
 * Captura el valor del estado de la localidad
 * true implica que la localidad esta habilitada
 * false implica una localidad inactiva o inhabilitada
 * @return estado
 */
	public boolean isEstado() {
		return estado;
	}

	/**
	 * Este metodo permite incializar el estado de la 
	 * Localidad
	 * <br>true implica que la localidad esta activa, entoces
	 * por otra lado el valor false, hace referenci a una localidad 
	 * inhabilitada
	 * @param valor de estado
	 */
	public void setEstado(boolean estado) {
		this.estado = estado;
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
		return "Localidad [id=" + id + ", nombre=" + nombre +", estado= "+ estado+ "]";
	}


}
