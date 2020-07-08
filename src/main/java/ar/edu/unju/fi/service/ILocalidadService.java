package ar.edu.unju.fi.service;

import java.util.Optional;

import ar.edu.unju.fi.model.Localidad;

/**
 * Interface que nos permitira desarrollar la lógica de negocio de nuestro
 * proyecto y a su vez conectarse con los datos que se encuentran almacenados en
 * algún repositorio de datos.
 * 
 * @author Marcia Velarde, Juan Toconas, Jorge E. Castillo.
 *
 */
public interface ILocalidadService {

	/**
	 * Metodo que permite guardar el estado de una localidad en el gestor de
	 * persistencia
	 * 
	 * @param localidad, objeto de tipo Localidad que se desea guardar
	 * @throws Exception
	 */
	public void crearLocalidad(Localidad localidad) throws Exception;

	/**
	 * Metodo que permite listar las localidades segun el estado en el que se
	 * encuentran
	 * 
	 * @param estado, true si estan activas, false si estan inactivas
	 * @return un Iterable con la lista de localidades encontradas
	 */
	public Iterable<Localidad> listarLocalidades(boolean estado);
	
	/**
	 * Metodo definido para realizar la logica necesaria para llevar
	 * a cabo la eliminacion de una Localidad en el repositor
	 * 
	 * @param id de la Localidad a ser eliminada
	 * @throws Exception para el tratamiento de eventos en caso de que no se lleve a cabo
	 * la eliminacion
	 */
	public void eliminarLocalidad(Long id) throws Exception;

	/**
	 * Metodo definido para realizar la logica relacionada a la habilitacion de una 
	 * Localidad segun el Id ingresado por parametro
	 * 
	 * @param id de Localidad para ser habilitado
	 * @throws Exception definida para tratar situaciones en caso de que la operacion
	 * no sea exitosa
	 */
	public void habilitarLocalidad(Long id) throws Exception;
	
	/**
	 * Metodo definido para realizar las instrucciones necesarias
	 * para realizar una Busqueda de una Localidad especifica a 
	 * traves del Id ingresado por paramatro
	 * @param id de la Localidad a buscar
	 * @return Localidad encontrada
	 * @throws Exception que se presenta en caso de que el motodo no tenga exito en la busqueda
	 */
	public Localidad buscarLocalidad1(Long id) throws Exception;
	
	/**
	 * Metodo definido para realizar instrucciones para realizar la busqueda de 
	 * una localidad en especifica. 
	 * 
	 * @param id de la localidad buscada
	 * @return Objeto de tipo Optional que puede o no contener una Localidad
	 */
	public Optional<Localidad> buscarLocalidad(Long id);
	
	/**
	 * Metodo definido para realizar instruccioes que permita hacer 
	 * una busqueda  de una Localidad en Particular de acuerdo al nombre 
	 * ingresado por Paramtro
	 * 
	 * @param nombre de Locadidad a Buscar
	 * @return objeto de tipo Localidad
	 * @throws Exception definida para tratar situaciones en caso de que la 
	 * busqueda no sea exitosa
	 */
	public Localidad buscarNombreLocalidad(String nombre) throws Exception;
	
	/**
	 * Metodo difinido para realizar instrucciones necesarias que permitan
	 * hacer una edicion a una localidad, el cual es ingresado por parametro.
	 * @param localidad a editar
	 * @throws Exception para tratar situaciones en caso la edicion no resulte exitosa
	 */
	public void editarLocalidad(Localidad localidad) throws Exception;
}
