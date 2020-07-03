package ar.edu.unju.fi.service;

import java.util.ArrayList;
import java.util.List;

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
	
	private List<Tripulante> lista = new ArrayList <Tripulante>();
	
	public void crearTripulante(Tripulante tripulante) {
		iTripulante.save(tripulante);
		lista.add(tripulante);
	}

	@Override
	public List<Tripulante> buscarTodosTripulantes() {
		return lista;
	}

	@Override
	public void borrarListaTripulantes() {
		lista.clear();
	}

	@Override
	public Tripulante buscarTripulante(String documento) throws Exception {
		return iTripulante.findByDocumento(documento).orElseThrow(() -> new Exception("El tripulante no existe"));
	}

	@Override
	public void guardarTripulanteEncontrado(Tripulante tripulanteEncontrado) {
		lista.add(tripulanteEncontrado);
	}


	
	
}
