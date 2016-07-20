package br.urban.prototipo.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class PapelTest {

	@Test
	public void deveInstanciar() {
		assertNotNull(new Papel(null));
	}

	@Test
	public void deveSetarDescricao() {
		Papel papel = new Papel("novaDescricao");
		assertEquals("novaDescricao", papel.getDescricao());
	}

}
