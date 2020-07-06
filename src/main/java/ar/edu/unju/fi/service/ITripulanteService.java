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

	public void crearTripulante(Tripulante tripulante) throws Exception;
	
	public List<Tripulante> buscarTodosTripulantes();
	
	public void borrarListaTripulantes();
	
	public Tripulante buscarTripulante(String documento) throws Exception;
	
	public void guardarTripulanteEncontrado(Tripulante tripulanteEncontrado);
	
}
