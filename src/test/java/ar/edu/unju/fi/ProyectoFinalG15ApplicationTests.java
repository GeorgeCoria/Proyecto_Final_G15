package ar.edu.unju.fi;

/**
 * Clase se prueba que se usara para crear al usuario BD dado que solo puede ingersarse manualmente
 * 
 */

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.edu.unju.fi.model.Usuario;
import ar.edu.unju.fi.service.UsuarioServiceImp;

	

@SpringBootTest
class ProyectoFinalG15ApplicationTests {
	
	@Autowired
	private UsuarioServiceImp imp;

	@Test
	void contextLoads() throws Exception {
		/*
		Usuario usuario = new Usuario();
		usuario.setApellidoReal("Adrian");
		usuario.setNombreReal("Antonio");
		usuario.setEstado(true);
		usuario.setNombreUsuario("admin");
		usuario.setPassword("1234");
		usuario.setTipoUsuario("BD");
		imp.crear(usuario);
		*/
		
		/*
		Usuario usuario2 = new Usuario();
		usuario2.setApellidoReal("Carlos");
		usuario2.setNombreReal("Carmin");
		usuario2.setNombreUsuario("c");
		usuario2.setEstado(true);
		usuario2.setPassword("1234");
		usuario2.setTipoUsuario("CONSULTOR");
		imp.crear(usuario2);
		*/
		
		/*
		Usuario usuario3 = new Usuario();
		usuario3.setApellidoReal("Raul");
		usuario3.setNombreReal("Ramos");
		usuario3.setNombreUsuario("r");
		usuario3.setEstado(true);
		usuario3.setPassword("1234");
		usuario3.setTipoUsuario("REGISTRADOR");
		imp.crear(usuario3);
		*/
	}

}
