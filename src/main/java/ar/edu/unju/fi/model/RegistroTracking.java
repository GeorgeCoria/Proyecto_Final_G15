package ar.edu.unju.fi.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Clase que representa registros con sus respectivos datos
 * Permitira llevar el registro de un vehiculo con sus tripulantes,
 * la hora del registro, la localidad y sus observaciones pertienentes.
 * 
 * @author Juan
 */

@Component
public class RegistroTracking {

	//----------------- Variables Miembro -----------------//
	/**
	 * Atributo que representa la fecha y hora del registro.
	 */
	private LocalDateTime fechaHora;

	/**
	 * Atributo que representa un vehiculo y sus caracteristicas.
	 */
	@Autowired
	private Vehiculo vehiculos;


	private List<Tripulante> tripulantes = new ArrayList <Tripulante>();

	/**
	 * Atributo que representa la localidad donde se realizo el registro.
	 */
	@Autowired
	private Localidad localidad;

	/**
	 * Atributo que representa obervaciones del lugar donde se realizo el registro. 
	 */
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
		return "RegistroTracking [fechaHora=" + fechaHora + ", vehiculos=" + vehiculos + ", tripulantes=" + tripulantes
				+ ", localidad=" + localidad + ", detalleLugarRegistro=" + detalleLugarRegistro + "]";
	}
	
}
