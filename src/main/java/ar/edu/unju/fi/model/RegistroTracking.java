package ar.edu.unju.fi.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

/**
 * Clase que representa registros con sus respectivos datos
 * Permitira llevar el registro de un vehiculo con sus tripulantes,
 * la hora del registro, la localidad y sus observaciones pertienentes.
 * 
 * @author Juan
 */

@Component
@Entity
@Table( name = "registroTracking")
public class RegistroTracking implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//----------------- Variables Miembro -----------------//
	
	/**
	 * Atributo de tipo Long para identificar cada registro (objetos) de esta clase con un valor univoco
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	/**
	 * Atributo que representa la fecha y hora del registro.
	 */
	@Column(name = "FECHA_HORA")
	@DateTimeFormat(pattern = "dd/MM/yyyy hh:mm")
	private LocalDateTime fechaHora;

	/**
	 * Atributo que representa un vehiculo y sus caracteristicas.
	 */
	@Autowired
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_VEHICULO")
	private Vehiculo vehiculos;

	/**
	 * Atributo de tipo List<Tripulante> que representa un listado de tripulantes por vehiculo
	 */
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "REGISTROTRAKING_TRIPULANTES", joinColumns = @JoinColumn(name= "ID_REGISTRO"), inverseJoinColumns = @JoinColumn(name= "ID_TRIPULANTE"))
	private List<Tripulante> tripulantes = new ArrayList <Tripulante>();

	/**
	 * Atributo que representa la localidad donde se realizo el registro.
	 */
	@Autowired
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_LOCALIDAD")
	private Localidad localidad;

	/**
	 * Atributo que representa obervaciones del lugar donde se realizo el registro. 
	 */
	@Column(name = "DETALLE_LUGAR_REGISTRO", length = 1000)
	private String detalleLugarRegistro;

	//----------------- Constructores -----------------//

	/**
	 * Constructor por defecto.
	 */
	public RegistroTracking() {

	}

	/**
	 * Constructro parametrizado.
	 * 
	 * @param fechaHora, se inicializa el atributo con la fecha del registro.
	 * @param vehiculos, se inicializa el atributo con la un objeto vehiculo.
	 * @param tripulantes, se inicializa la lista de tripulantes.
	 * @param localidad, se inicializa el atributo con el objeto localidad.
	 * @param detalleLugarRegistro, se inicializa el atributo con detalles del lugar.
	 */
	public RegistroTracking(LocalDateTime fechaHora, Vehiculo vehiculos, List<Tripulante> tripulantes,
			Localidad localidad, String detalleLugarRegistro) {
		this.fechaHora = fechaHora;
		this.vehiculos = vehiculos;
		this.tripulantes = tripulantes;
		this.localidad = localidad;
		this.detalleLugarRegistro = detalleLugarRegistro;
	}

	//----------------- Metodos accesores-----------------// 

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
	 * Permite capturar fecha y hora del registro
	 * @return fechaHora
	 */
	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	/**
	 * Permite asignar fecha y hora al atributo fechaHora
	 * @param fechaHora 
	 */
	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	/**
	 * Permite capturar el objeto vehiculos, tambien se puede acceder a sus atributos por sus metodos get.
	 * @return vehiculos
	 */
	public Vehiculo getVehiculos() {
		return vehiculos;
	}

	/**
	 * Permite asignar valor al atributo vehiculos.
	 * @param vehiculos 
	 */
	public void setVehiculos(Vehiculo vehiculos) {
		this.vehiculos = vehiculos;
	}

	/**
	 * Permite capturar el atributo Localidad
	 * @return localidad
	 */
	public Localidad getLocalidad() {
		return localidad;
	}

	/**
	 * Permite capturar la lista de tripulantes.
	 * @return tripulantes
	 */
	public List<Tripulante> getTripulantes() {
		return tripulantes;
	}

	/**
	 * @param Permite asignar valores a la lista de tripulantes
	 */
	public void setTripulantes(List<Tripulante> tripulantes) {
		this.tripulantes = tripulantes;
	}

	/**
	 * Permite asignar valor al atributo localidad
	 * @param localidad 
	 */
	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}

	/**
	 * Permite capturar los detalles del lugar de registro
	 * @return detalleLugarRegistro
	 */
	public String getDetalleLugarRegistro() {
		return detalleLugarRegistro;
	}

	/**
	 * Permite asignar los detalles del lugar de resgistro
	 * @param detalleLugarRegistro the detalleLugarRegistro to set
	 */
	public void setDetalleLugarRegistro(String detalleLugarRegistro) {
		this.detalleLugarRegistro = detalleLugarRegistro;
	}

	//----------------- Metodo ToString  -----------------//

	/**
	 * Permite visualizar los atributos de objeto RegistroTracking
	 */
	@Override
	public String toString() {
		return "RegistroTracking [id=" + id + ", fechaHora=" + fechaHora + ", vehiculos=" + vehiculos + ", tripulantes=" + tripulantes
				+ ", localidad=" + localidad + ", detalleLugarRegistro=" + detalleLugarRegistro + "]";
	}
	
}
