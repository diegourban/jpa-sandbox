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
	
	@Test
	public void deveAdicionarPapel() {
		Usuario usuario = new Usuario("login");
		Papel papel = new Papel("papel");
		assertEquals(0, usuario.getQuantidadePapeis());
		usuario.adiciona(papel);
		assertEquals(1, usuario.getQuantidadePapeis());
	}
	
	@Test
	public void deveRemoverPapel() {
		Usuario usuario = new Usuario("login");
		Papel papel = new Papel("papel");
		assertEquals(0, usuario.getQuantidadePapeis());
		usuario.adiciona(papel);
		assertEquals(1, usuario.getQuantidadePapeis());
		usuario.remove(papel);
		assertEquals(0, usuario.getQuantidadePapeis());
	}
	
	@Test
	public void devePossuirPapelAdicionar() {
		Usuario usuario = new Usuario("login");
		Papel papel = new Papel("USUARIO");
		assertEquals(0, usuario.getQuantidadePapeis());
		usuario.adiciona(papel);
		assertEquals(1, usuario.getQuantidadePapeis());
		boolean possui = usuario.possui(papel);
		assertEquals(true, possui);
	}
	
	@Test
	public void naoDevePossuirPapelNaoAdicionado() {
		Usuario usuario = new Usuario("login");
		Papel papel = new Papel("USUARIO");
		assertEquals(0, usuario.getQuantidadePapeis());
		usuario.adiciona(papel);
		assertEquals(1, usuario.getQuantidadePapeis());
		
		Papel papelNaoAdicionado = new Papel("NAOADICIONADO");
		
		boolean possui = usuario.possui(papelNaoAdicionado);
		assertEquals(false, possui);
	}
	
	@Test
	public void naoDeveAdicionarPapelDuplicado() {
		Usuario usuario = new Usuario("login");
		Papel papel = new Papel("USUARIO");
		assertEquals(0, usuario.getQuantidadePapeis());
		usuario.adiciona(papel);
		assertEquals(1, usuario.getQuantidadePapeis());
		
		usuario.adiciona(papel); // duplicado
		assertEquals(1, usuario.getQuantidadePapeis());
	}

}
