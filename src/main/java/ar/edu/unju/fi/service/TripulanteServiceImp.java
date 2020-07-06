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
	
	private List<Tripulante> lista = new ArrayList <Tripulante>();
	
	public void crearTripulante(Tripulante tripulante) throws Exception {
		if(checkDniTripulante(tripulante)) {
			iTripulante.save(tripulante);
			lista.add(tripulante);
		}
	}
	
	private boolean checkDniTripulante(Tripulante tripulante) throws Exception{
		int numero=0;
		for (int i = 0; i < tripulante.getDocumento().length(); i++) {
			if(tripulante.getDocumento().charAt(i)>= '0' && tripulante.getDocumento().charAt(i) <= '9') {
				numero++;
			}else {
				throw new Exception("El dni debe tener entre 6 y 8 numeros sin puntos");
			}
		}
		if(numero >= 6) {
			Optional<Tripulante> tripulanteEncontrado = iTripulante.findByDocumento(tripulante.getDocumento());
			if(tripulanteEncontrado.isPresent()){
				throw new Exception("El dni ya esta registrado");
			}
		}
		return true;
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
		int numero=0;
		for (int i = 0; i < documento.length(); i++) {
			if(documento.charAt(i)>= '0' && documento.charAt(i) <= '9') {
				numero++;
			}else {
				throw new Exception("El dni debe tener entre 6 y 8 numeros sin puntos");
			}
		}
		if(numero >= 6) {
			return iTripulante.findByDocumento(documento).orElseThrow(() -> new Exception("El tripulante no existe"));
		}else {
			throw new Exception("El dni debe tener entre 6 y 8 numeros sin puntos");
		}
	}
	
	@Override
	public void guardarTripulanteEncontrado(Tripulante tripulanteEncontrado) {
		lista.add(tripulanteEncontrado);
	}
	
}
