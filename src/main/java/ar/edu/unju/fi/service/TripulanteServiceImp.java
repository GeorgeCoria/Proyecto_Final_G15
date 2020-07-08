package ar.edu.unju.fi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unju.fi.model.Tripulante;
import ar.edu.unju.fi.repository.ITripulanteDAO;

/**
 * Clase que va a implementar la interface ITripulanteService.
 * 
 * @author Marcia Velarde
 *
 */
@Service
public class TripulanteServiceImp implements ITripulanteService{

	@Autowired
	private ITripulanteDAO iTripulante;

	// Se crea una lista auxiliar que contendra los tripulantes hasta que se guarden
	// en el registro tracking
	private List<Tripulante> lista = new ArrayList<Tripulante>();

	/**
	 * Metodo que permite guardar un tripulante en el gestor de persistencia MYSQL
	 * 
	 * @param tripulante, objeto que se desea guardar
	 * @throws Exception
	 */
	public void crearTripulante(Tripulante tripulante) throws Exception {
		// Si el dni no ha sido guardado antes se guarda el nuevo tripulante
		if(checkDniTripulante(tripulante)) {
			iTripulante.save(tripulante);
			// se guarda el tripulante en la lista auxiliar
			lista.add(tripulante);
		}
	}

	/**
	 * Metodo que permite chequear si el dni del tripulante ya fue guardado
	 * previamente y verifica que el formato del dni sea de 6 a 8 numeros
	 * 
	 * @param tripulante, objeto del que se desea chequear el dni
	 * @return true, si el dni del tripulante no fue encontrado y si cumple con el
	 *         formato
	 * @throws Exception, si el dni del tripulante fue encontrado o si no cumple con
	 *                    el formato
	 */
	private boolean checkDniTripulante(Tripulante tripulante) throws Exception {
		int numero=0;
		for (int i = 0; i < tripulante.getDocumento().length(); i++) {
			// Se verifica que los caracteres ingresados sean numeros
			if(tripulante.getDocumento().charAt(i)>= '0' && tripulante.getDocumento().charAt(i) <= '9') {
				numero++;
			}else {
				throw new Exception("El dni debe tener entre 6 y 8 numeros sin puntos");
			}
		}
		// Si cumple con el formato de dni se verifica si el dni fue registrado
		if (numero >= 6) {
			Optional<Tripulante> tripulanteEncontrado = iTripulante.findByDocumento(tripulante.getDocumento());
			if (tripulanteEncontrado.isPresent()) {
				throw new Exception("El dni ya esta registrado");
			}
		}
		return true;
	}

	/**
	 * Metodo que devuelve la lista auxiliar con todos los tripulantes que se van a
	 * guardar en el registro
	 * 
	 * @return lista con los tripulantes del registro
	 */
	@Override
	public List<Tripulante> buscarTodosTripulantes() {
		return lista;
	}

	/**
	 * Metodo que limpia la lista auxiliar para un proximo registro
	 * 
	 */
	@Override
	public void borrarListaTripulantes() {
		lista.clear();
	}

	/**
	 * Metodo que permite buscar un tripulante en el gestor de persistencia de
	 * MYSQL, antes verifica que el formato del dni sea de 6 a 8 numeros
	 * 
	 * @param documento, el documento del tripulante buscado
	 * @return el tripulante encontrado o una exception
	 * @throws Exception si el dni del tripulante no cumple con el formato o si el
	 *                   tripulante no existe
	 */
	@Override
	public Tripulante buscarTripulante(String documento) throws Exception {
		int numero = 0;
		for (int i = 0; i < documento.length(); i++) {
			// Se verifica que los caracteres ingresados sean numeros
			if (documento.charAt(i) >= '0' && documento.charAt(i) <= '9') {
				numero++;
			} else {
				throw new Exception("El dni debe tener entre 6 y 8 numeros sin puntos");
			}
		}
		// Si cumple con el formato de dni se verifica si el dni fue registrado
		if (numero >= 6) {
			return iTripulante.findByDocumento(documento).orElseThrow(() -> new Exception("El tripulante no existe"));
		} else {
			throw new Exception("El dni debe tener entre 6 y 8 numeros sin puntos");
		}
	}

	/**
	 * Metodo que guarda el tripulante encontrado en la lista auxiliar verificando
	 * que no fue guardado previamente
	 * 
	 * @param tripulanteEncontrado
	 * @throws Exception, en caso del que el tripulante ya este agregado a la lista
	 */
	@Override
	public void guardarTripulanteEncontrado(Tripulante tripulanteEncontrado) throws Exception {
		boolean repetido = false;
		for (Tripulante trip : lista) {
			if (tripulanteEncontrado.getId() == trip.getId()) {
				repetido = true;
			}
		}
		if (repetido==false)
			lista.add(tripulanteEncontrado);
		else
			throw new Exception("El tripulante ya esta agregado a la lista");
	}

}
