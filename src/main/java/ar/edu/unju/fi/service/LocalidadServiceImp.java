package ar.edu.unju.fi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unju.fi.model.Localidad;
import ar.edu.unju.fi.repository.ILocalidadDAO;

/**
 * Clase que va a implementar la interface ILocalidadService.
 * 
 * @author Marcia Velarde, Juan Toconas, Jorge E. Castillo.
 *
 */
@Service
public class LocalidadServiceImp implements ILocalidadService{
	
	/**
	 * variable que permitira que se haga efectiva la inyeccion de dependencias
	 * a traves de la Annotations @Autowired
	 * En definitiva, cada vez que alguno de los metodos de la
	 * clase LocalidadServiceImp sea llamado, Spring hara un escaneo de
	 * un objeto Usuario para luego inyectar al metodo
	 */
	@Autowired
	private ILocalidadDAO iLocalidad;
	
	/**
	 * Metodo que permite guardar el estado de una localidad en el gestor de
	 * persistencia MYSQL
	 * 
	 * @param localidad, objeto de tipo Localidad que se desea guardar
	 * @throws Exception
	 */
	@Override
	public void crearLocalidad(Localidad localidad) throws Exception {
		// Se convierte el nombre de la localidad en mayuscula para ser guarado asi en
		// la base de datos y hacer mas facil la busqueda y checkeo de errores
		String localidadMayuscula = localidad.getNombre().toUpperCase();
		localidad.setNombre(localidadMayuscula);
		// Se le asigna por defecto true al estado, es decir que estara activa
		localidad.setEstado(true);
		// Si el nombre esta disponible se guarda
		if (checkNombreLocalidad(localidad)) {
			iLocalidad.save(localidad);
		}
	}

	/**
	 * Metodo que permite checkear si el nombre de la localidad ya existe en el
	 * gestor de persistencia MYSQL o esta disponible para ser usado
	 * 
	 * @param localidad, objeto de tipo localidad que se desea verificar el nombre
	 * @return true, si el nombre de localidad esta disponible
	 * @throws Exception, si el nombre de usuario no esta disponible
	 */
	private boolean checkNombreLocalidad (Localidad localidad) throws Exception {
		// Se guarda el objeto buscado o de lo contrario null
		Optional<Localidad> localidadEncontrada = iLocalidad.findByNombre(localidad.getNombre());
		// Si el nombre de la localidad buscada esta presente en la base se devuelve el
		// mensaje de error
		if (localidadEncontrada.isPresent()) {
			throw new Exception("Error: Ya existe una localidad con el nombre indicado");
		}
		return true;
	}
	
	/**
	 * Metodo que permite listar las localidades segun el estado en el que se
	 * encuentran
	 * 
	 * @param estado, true si estan activas, false si estan inactivas
	 * @return un Iterable con la lista de localidades encontradas
	 */
	@Override
	public Iterable<Localidad> listarLocalidades(boolean estado) {
		return iLocalidad.findByEstado(estado);
	}
	
	/**
	 * Este metodo permite realizar la el cambio de estado de un Localidad
	 * Establece un estado en "false" ya que esta manera pasaria a estar 
	 * inactivo o deshabilitado, simulando la eliminacion del registro
	 * Se pasa por parametro el Id de la Localidad a tratar, en caso de se 
	 * presente algun inconveniente este sera reflejado en la Excepcion 
	 * para informar el motivo por el cual no fue exitosa la transaccion
	 * 
	 * @param id de la Localidad a editar estado
	 */
	@Override
	public void eliminarLocalidad(Long id) throws Exception {
			//si no se encuentra La localidad actua la excepcion
			Localidad unaLocalidad=buscarLocalidad1(id);
			unaLocalidad.setEstado(false);
			iLocalidad.save(unaLocalidad);		
	}
	/**
	 * Metodo definido para realizar una busquesa de una Localidad
	 * segun el Id pasado por parametro.
	 * Para llevar a cabo este procedimiento utiliza un objeto de tipo
	 * iLocalidadDao para tener comunicacion al repositorio
	 * En caso de no encontrar resultados se notificara
	 * la excepcion
	 * 
	 * @param Id de localidad
	 * 
	 */
	@Override
	public Localidad buscarLocalidad1(Long id) throws Exception {
		return iLocalidad.findById(id).orElseThrow(()-> new Exception("La localidad no existe"));
	}
	
	/**
	 * Metodo definido para realizar la busqueda de una Localidad segun
	 * el Id pasado por paramtro
	 * utiliza un objeto de la capa Repository para llevar a cabo 
	 * la comunicacion con el repositorio
	 * 
	 * @param Id de localidad
	 * @return resultado de la busqueda
	 */
	@Override
	public Optional<Localidad> buscarLocalidad(Long id) {
		return iLocalidad.findById(id);
	}
	
	/**
	 * Metodo que permite realizar una busqueda, a traves de un objeto de la capa reposity,
	 * segun el nombre pasado por parametro.
	 * 
	 * @param Nombre de Localidad
	 * @return objeto resultante de la busqueda, puede o no contener la Localidad buscada
	 */
	@Override
	public Localidad buscarNombreLocalidad(String nombre) throws Exception {
		return iLocalidad.findByNombre(nombre).orElseThrow();
	}
	
	/**
	 * Metod que permite realizar la edicion de una Localidad, se hace
	 * un control respecto de un Nombre de Localidad con el fin de evitar
	 * la redundancia en el repositorio
	 * 
	 * @param Localidad a editar
	 */
	@Override
	public void editarLocalidad(Localidad localidad) throws Exception {
		// se pasa a mayusculas el nombre de la Localidad para mantener el criterio de
		//las localidad almacenadas
		String localidadMayuscula = localidad.getNombre().toUpperCase();
		localidad.setNombre(localidadMayuscula);
		//se verifica que el nombre ya no exista en el repositorio
		if(verificarEditarNombre(localidad)) {
			iLocalidad.save(localidad);
		}	
	}
	
	/**
	 * Este metodo tiene por finalidad determinar si un nombre de Localidad
	 * ya existe o no en el repositorio, de esta manera se evita la redundancia 
	 * de datos.
	 * 
	 * @param localidad a ser controlada
	 * @return true si no existe un registro con el mismo nombre
	 * @throws Exception definida en caso de encontrar coincidencias
	 */
	public boolean verificarEditarNombre(Localidad localidad)throws Exception{
		int cont=0;
		//se genera una lista con los registros almacenados
		List<Localidad> listaLocalidad = iLocalidad.findAll();
		for(Localidad loc: listaLocalidad) {
			//se evalua si los nombres coinciden
			if(localidad.getNombre().equals(loc.getNombre())) {
				//se evalua si se trata de un registro distinto
				if(localidad.getId()!=loc.getId())
					cont++;
			}
		}
		if(cont!=0) {
			throw new Exception ("El nombre de Usuario ya existe");
		}
		return true;
	}
	
	/**
	 * Metodo definido para habilitar una Localidad que haya sido
	 * dada de baja. 
	 * Una localidad esta activa cuando su estado sea true, es decir, 
	 * que el metodo realiza un cambio de estado en la Localidad Ingresada 
	 * por Parametro
	 * 
	 * @param Id de localidad para habilitar
	 */
	@Override
	public void habilitarLocalidad(Long id) throws Exception {
		Localidad habilitado = buscarLocalidad1(id);
		habilitado.setEstado(true);
		iLocalidad.save(habilitado);
		
	}
	

}
