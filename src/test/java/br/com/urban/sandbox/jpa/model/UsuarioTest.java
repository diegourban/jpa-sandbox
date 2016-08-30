package br.com.urban.sandbox.jpa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UsuarioTest {
	
	@Before
	public void beforeTest() {
	}
	
	@After
	public void afterTest() {
	}
	
	@Test
	public void deveInstanciar() {
		assertNotNull(new Usuario.UsuarioBuilder().build());
	}
	
	@Test
	public void deveSetarLogin() {
		Usuario usuario = new Usuario.UsuarioBuilder().comLogin("root").build();
		assertEquals("root", usuario.getLogin());
	}
	
	@Test
	public void deveAdicionarPapel() {
		Usuario usuario = new Usuario.UsuarioBuilder().comLogin("root").build();
		Papel papel = new Papel.PapelBuilder().comDescricao("admin").build();
		assertEquals(0, usuario.getQuantidadePapeis());
		usuario.adiciona(papel);
		assertEquals(1, usuario.getQuantidadePapeis());
	}
	
	@Test
	public void deveRemoverPapel() {
		Usuario usuario = new Usuario.UsuarioBuilder().comLogin("root").build();
		Papel papel = new Papel.PapelBuilder().comDescricao("admin").build();
		assertEquals(0, usuario.getQuantidadePapeis());
		usuario.adiciona(papel);
		assertEquals(1, usuario.getQuantidadePapeis());
		usuario.remove(papel);
		assertEquals(0, usuario.getQuantidadePapeis());
	}
	
	@Test
	public void devePossuirPapelAdicionar() {
		Usuario usuario = new Usuario.UsuarioBuilder().comLogin("root").build();
		Papel papel = new Papel.PapelBuilder().comDescricao("admin").build();
		assertEquals(0, usuario.getQuantidadePapeis());
		usuario.adiciona(papel);
		assertEquals(1, usuario.getQuantidadePapeis());
		boolean possui = usuario.possui(papel);
		assertEquals(true, possui);
	}
	
	@Test
	public void naoDevePossuirPapelNaoAdicionado() {
		Usuario usuario = new Usuario.UsuarioBuilder().comLogin("root").build();
		Papel papel =new Papel.PapelBuilder().comDescricao("admin").build();
		assertEquals(0, usuario.getQuantidadePapeis());
		usuario.adiciona(papel);
		assertEquals(1, usuario.getQuantidadePapeis());
		
		Papel papelNaoAdicionado = new Papel("NAOADICIONADO");
		
		boolean possui = usuario.possui(papelNaoAdicionado);
		assertEquals(false, possui);
	}
	
	@Test
	public void naoDeveAdicionarPapelDuplicado() {
		Usuario usuario = new Usuario.UsuarioBuilder().comLogin("root").build();
		Papel papel = new Papel.PapelBuilder().comDescricao("admin").build();
		assertEquals(0, usuario.getQuantidadePapeis());
		usuario.adiciona(papel);
		assertEquals(1, usuario.getQuantidadePapeis());
		
		usuario.adiciona(papel); // duplicado
		assertEquals(1, usuario.getQuantidadePapeis());
	}

}
