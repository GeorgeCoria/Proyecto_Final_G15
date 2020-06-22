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
 * Clase para gestionar los datos de un vehiculo controlado
 * 
 * @author Marcia Velarde
 *
 */

@Component
@Entity
@Table(name = "vehiculos")
public class Vehiculo implements Serializable {

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
	@Column
	private Long id;
	
	/**
	 * Variable de tipo String para gestionar la patente del vehiculo
	 */
	@Column
	private String patente;
	
	/**
	 * Variable de tipo String para gestionar el color del vehiculo
	 */
	@Column
	private String color;
	
	/**
	 * Variable de tipo String para gestionar el nombre del titular del vehiculo
	 */
	@Column
	private String titular;
	
	/**
	 *  Variable de tipo String para gestionar la marca del vehiculo
	 */
	@Column
	private String marca;
	
	/**
	 * Variable de tipo String para gestionar el modelo del vehiculo
	 */
	@Column
	private String modelo;
	
	/**
	 * Variable de tipo String para gestionar el tipo del vehiculo
	 */
	@Column
	private String tipo;
	
	/**
	 * Variable de tipo String para gestionar el numero de chasis del vehiculo
	 */
	@Column(name = "NUMERO_CHASIS")
	private String numeroChasis;
	
	/**
	 * Variable de tipo String para gestionar el numero de motor del vehiculo
	 */
	@Column(name = "NUMERO_MOTOR")
	private String numeroMotor;
	
	// -----------------CONSTRUCTORES-----------------//
	
	/**
	 * Constructor por defecto
	 */
	public Vehiculo() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor con parametros
	 * 
	 * @param patente, valor que se le asigna al atributo patente
	 * @param color, valor que se le asigna al atributo color
	 * @param titular, valor que se le asigna al atributo titular
	 * @param marca, valor que se le asigna al atributo marca
	 * @param modelo, valor que se le asigna al atributo modelo
	 * @param tipo, valor que se le asigna al atributo tipo
	 * @param numeroChasis, valor que se le asigna al atributo numeroChasis
	 * @param numeroMotor, valor que se le asigna al atributo numeroMotor
	 */
	public Vehiculo(String patente, String color, String titular, String marca, String modelo, String tipo,
			String numeroChasis, String numeroMotor) {
		this.patente = patente;
		this.color = color;
		this.titular = titular;
		this.marca = marca;
		this.modelo = modelo;
		this.tipo = tipo;
		this.numeroChasis = numeroChasis;
		this.numeroMotor = numeroMotor;
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
	 * Devuelve el valor del atributo patente
	 * 
	 * @return the patente
	 */
	public String getPatente() {
		return patente;
	}

	/**
	 * Asigna un valor al atributo patente
	 * 
	 * @param patente the patente to set
	 */
	public void setPatente(String patente) {
		this.patente = patente;
	}

	/**
	 * Devuelve el valor del atributo color
	 * 
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Asigna un valor al atributo color
	 * 
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Devuelve el valor del atributo titular
	 * 
	 * @return the titular
	 */
	public String getTitular() {
		return titular;
	}

	/**
	 * Asigna un valor al atributo titular
	 * 
	 * @param titular the titular to set
	 */
	public void setTitular(String titular) {
		this.titular = titular;
	}

	/**
	 * Devuelve el valor del atributo marca
	 * 
	 * @return the marca
	 */
	public String getMarca() {
		return marca;
	}

	/**
	 * Asigna un valor al atributo marca
	 * 
	 * @param marca the marca to set
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}

	/**
	 * Devuelve el valor del atributo modelo
	 * 
	 * @return the modelo
	 */
	public String getModelo() {
		return modelo;
	}

	/**Asigna un valor al atributo modelo
	 * 
	 * 
	 * @param modelo the modelo to set
	 */
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	/**
	 * Devuelve el valor del atributo tipo
	 * 
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Asigna un valor al atributo tipo
	 * 
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Devuelve el valor del atributo numeroChasis
	 * 
	 * @return the numeroChasis
	 */
	public String getNumeroChasis() {
		return numeroChasis;
	}

	/**
	 * Asigna un valor al atributo numeroChasis
	 * 
	 * @param numeroChasis the numeroChasis to set
	 */
	public void setNumeroChasis(String numeroChasis) {
		this.numeroChasis = numeroChasis;
	}

	/**
	 * Devuelve el valor del atributo numeroMotor
	 * 
	 * @return the numeroMotor
	 */
	public String getNumeroMotor() {
		return numeroMotor;
	}

	/**
	 * Asigna un valor al atributo numeroMotor
	 * 
	 * @param numeroMotor the numeroMotor to set
	 */
	public void setNumeroMotor(String numeroMotor) {
		this.numeroMotor = numeroMotor;
	}

	// ----------------METODO TOSTRING----------------//

	/**
	 * Permite visualizar los atributos del objeto Vehiculo
	 */
	@Override
	public String toString() {
		return "Vehiculo [id=" + id + ", patente=" + patente + ", color=" + color + ", titular=" + titular + ", marca=" + marca
				+ ", modelo=" + modelo + ", tipo=" + tipo + ", numeroChasis=" + numeroChasis + ", numeroMotor="
				+ numeroMotor + "]";
	}
	
}
