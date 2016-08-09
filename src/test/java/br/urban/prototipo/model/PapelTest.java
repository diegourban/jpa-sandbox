package br.urban.prototipo.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PapelTest {

	@Before
	public void beforeTest() {
	}
	
	@After
	public void afterTest() {
	}
	
	@Test
	public void deveInstanciar() {
		assertNotNull(new Papel.PapelBuilder().build());
	}

	@Test
	public void deveSetarDescricao() {
		Papel papel = new Papel.PapelBuilder().comDescricao("Teste").build();
		assertEquals("Teste", papel.getDescricao());
	}

}
