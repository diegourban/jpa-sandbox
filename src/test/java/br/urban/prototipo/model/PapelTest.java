package br.urban.prototipo.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.urban.prototipo.builder.PapelBuilder;

public class PapelTest {

	private PapelBuilder papelBuilder;
	
	@Before
	public void beforeTest() {
		papelBuilder = new PapelBuilder();
	}
	
	@After
	public void afterTest() {
		papelBuilder = null;
	}
	
	@Test
	public void deveInstanciar() {
		assertNotNull(papelBuilder.build());
	}

	@Test
	public void deveSetarDescricao() {
		Papel papel = papelBuilder.comDescricao("Teste").build();
		assertEquals("Teste", papel.getDescricao());
	}

}
