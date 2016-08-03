package br.urban.prototipo.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.urban.prototipo.builder.PapelBuilder;
import br.urban.prototipo.builder.UsuarioBuilder;
import br.urban.prototipo.model.Usuario;

public class UsuarioTest {
	
	private UsuarioBuilder usuarioBuilder;
	private PapelBuilder papelBuilder;
	
	@Before
	public void beforeTest() {
		this.usuarioBuilder = new UsuarioBuilder();
		this.papelBuilder = new PapelBuilder();
	}
	
	@After
	public void afterTest() {
		this.usuarioBuilder = null;
		this.papelBuilder = null;
	}
	
	@Test
	public void deveInstanciar() {
		assertNotNull(usuarioBuilder.build());
	}
	
	@Test
	public void deveSetarLogin() {
		Usuario usuario = usuarioBuilder.comLogin("root").build();
		assertEquals("root", usuario.getLogin());
	}
	
	@Test
	public void deveAdicionarPapel() {
		Usuario usuario = usuarioBuilder.comLogin("root").build();
		Papel papel = papelBuilder.comDescricao("admin").build();
		assertEquals(0, usuario.getQuantidadePapeis());
		usuario.adiciona(papel);
		assertEquals(1, usuario.getQuantidadePapeis());
	}
	
	@Test
	public void deveRemoverPapel() {
		Usuario usuario = usuarioBuilder.comLogin("root").build();
		Papel papel = papelBuilder.comDescricao("admin").build();
		assertEquals(0, usuario.getQuantidadePapeis());
		usuario.adiciona(papel);
		assertEquals(1, usuario.getQuantidadePapeis());
		usuario.remove(papel);
		assertEquals(0, usuario.getQuantidadePapeis());
	}
	
	@Test
	public void devePossuirPapelAdicionar() {
		Usuario usuario = usuarioBuilder.comLogin("root").build();
		Papel papel = papelBuilder.comDescricao("admin").build();
		assertEquals(0, usuario.getQuantidadePapeis());
		usuario.adiciona(papel);
		assertEquals(1, usuario.getQuantidadePapeis());
		boolean possui = usuario.possui(papel);
		assertEquals(true, possui);
	}
	
	@Test
	public void naoDevePossuirPapelNaoAdicionado() {
		Usuario usuario = usuarioBuilder.comLogin("root").build();
		Papel papel = papelBuilder.comDescricao("admin").build();
		assertEquals(0, usuario.getQuantidadePapeis());
		usuario.adiciona(papel);
		assertEquals(1, usuario.getQuantidadePapeis());
		
		Papel papelNaoAdicionado = new Papel("NAOADICIONADO");
		
		boolean possui = usuario.possui(papelNaoAdicionado);
		assertEquals(false, possui);
	}
	
	@Test
	public void naoDeveAdicionarPapelDuplicado() {
		Usuario usuario = usuarioBuilder.comLogin("root").build();
		Papel papel = papelBuilder.comDescricao("admin").build();
		assertEquals(0, usuario.getQuantidadePapeis());
		usuario.adiciona(papel);
		assertEquals(1, usuario.getQuantidadePapeis());
		
		usuario.adiciona(papel); // duplicado
		assertEquals(1, usuario.getQuantidadePapeis());
	}

}
