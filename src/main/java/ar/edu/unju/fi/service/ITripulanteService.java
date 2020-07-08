package ar.edu.unju.fi.service;

import java.util.List;

import ar.edu.unju.fi.model.Tripulante;

/**
 * Interface que nos permitira desarrollar la lógica de negocio de nuestro
 * proyecto y a su vez conectarse con los datos que se encuentran almacenados en
 * algún repositorio de datos.
 * 
 * @author Marcia Velarde
 *
 */
public interface ITripulanteService {
	
	/**
	 * Metodo que permite guardar un tripulante en el gestor de persistencia
	 * 
	 * @param tripulante, objeto que se desea guardar
	 * @throws Exception
	 */
	public void crearTripulante(Tripulante tripulante) throws Exception;

	/**
	 * Metodo que devuelve la lista auxiliar con todos los tripulantes que se van a
	 * guardar en el registro
	 * 
	 * @return lista con los tripulantes del registro
	 */
	public List<Tripulante> buscarTodosTripulantes();

	/**
	 * Metodo que limpia la lista auxiliar para un proximo registro
	 * 
	 */
	public void borrarListaTripulantes();

	/**
	 * Metodo que permite buscar un tripulante en el gestor de persistencia, antes
	 * verifica que el formato del dni sea de 6 a 8 numeros
	 * 
	 * @param documento, el documento del tripulante buscado
	 * @return el tripulante encontrado o una exception
	 * @throws Exception si el dni del tripulante no cumple con el formato o si el
	 *                   tripulante no existe
	 */
	public Tripulante buscarTripulante(String documento) throws Exception;

	/**
	 * Metodo que guarda el tripulante encontrado en la lista auxiliar verificando
	 * que no fue guardado previamente
	 * 
	 * @param tripulanteEncontrado
	 * @throws Exception, en caso del que el tripulante ya este agregado a la lista
	 */
	public void guardarTripulanteEncontrado(Tripulante tripulanteEncontrado) throws Exception;

}
