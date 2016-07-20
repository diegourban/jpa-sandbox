package br.urban.prototipo.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import br.urban.prototipo.model.Usuario;

public class UsuarioTest {
	
	@Test
	public void deveInstanciar() {
		assertNotNull(new Usuario(null));
	}
	
	@Test
	public void deveSetarLogin() {
		Usuario usuario = new Usuario("novoLogin");
		assertEquals("novoLogin", usuario.getLogin());
	}

}
