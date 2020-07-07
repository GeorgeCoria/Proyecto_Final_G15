package ar.edu.unju.fi.utils;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import ar.edu.unju.fi.model.Localidad;

/**
 * Clase q representa una figura de tipo ConsultaVehiculo de la vista consulta.
 * @author Castillo, Jorge Emanuel.
 *
 */

@Component
public class ConsultaVehiculo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//----------------- Variables Miembros o Atributos -----------------//
	
	
	/**
	 * Representa la localidad del Objeto Localidad de la clase ConsultaVehiculo.
	 */
	@Autowired
	@NotNull(message = "Seleccione una localidad")
	private Localidad localidad;
	
	/**
	 * Representa la fechaDesde, de la clase ConsultaVehiculo.
	 */
	@NotNull(message = "Ingrese una fechaDesde dia/mes/año")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate fechaDesde;
	
	/**
	 * Representa la fechaHasta, de la clase ConsultaVehiculo.
	 */
	@NotNull(message = "Ingrese una fechaHasta dia/mes/año")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate fechaHasta;

	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//----------------- Contructores -----------------//
	
	/**
	 * Constructor por defecto.
	 */
	public ConsultaVehiculo() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * Constructro parametrizado.
	 * 
	 * @param localidad, valor que se le asignara a la variable miembro this.localidad
	 * @param fechaDesde, valor que se le asignara a la variable miembro this.fechaDesde
	 * @param fechaHasta, valor que se le asignara a la variable miembro this.fechaHasta
	 */
	public ConsultaVehiculo(Localidad localidad, LocalDate fechaDesde, LocalDate fechaHasta) {
		this.localidad = localidad;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
	}

	
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//----------------- Metodos Accesores -----------------//
	
	
	/**
	 * Devuelve el valor del atributo localidad
	 * 
	 * @return the localidad
	 */
	public Localidad getLocalidad() {
		return localidad;
	}


	/**
	 * Devuelve el valor del atributo fechaDesde
	 * 
	 * @return the fechaDesde
	 */
	public LocalDate getFechaDesde() {
		return fechaDesde;
	}


	/**
	 * Devuelve el valor del atributo fechaHasta
	 * 
	 * @return the fechaHasta
	 */
	public LocalDate getFechaHasta() {
		return fechaHasta;
	}

	//-------------------------------------------------------------------------------------//
	
	/**
	 * Asigna un valor al atributo localidad
	 * 
	 * @param localidad the localidad to set
	 */
	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}


	/**
	 * Asigna un valor al atributo fechaDesde
	 * 
	 * @param fechaDesde the fechaDesde to set
	 */
	public void setFechaDesde(LocalDate fechaDesde) {
		this.fechaDesde = fechaDesde;
	}


	/**
	 * Asigna un valor al atributo fechaHasta
	 * 
	 * @param fechaHasta the fechaHasta to set
	 */
	public void setFechaHasta(LocalDate fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////
	//----------------- Metodo SobreEscrito -----------------//
	
	/**
	 * Visualiza el estado del objeto, sobreescribiendo 
	 * los datos de los atributo.
	 */
	@Override
	public String toString() {
		return "ConsultaVehiculo [localidad=" + localidad + ", fechaDesde=" + fechaDesde + ", fechaHasta=" + fechaHasta
				+ "]";
	}
	
}
