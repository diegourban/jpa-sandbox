package br.urban.prototipo.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.urban.prototipo.model.Papel;

public class PapelDaoTest {
	
	private EntityManager em;
	private PapelDao papelDao;
	
	@Before
	public void beforeTest() {
		em = new CriadorDeSessao().getEntityManager();
		papelDao = new PapelDao(em);
		em.getTransaction().begin();
	}
	
	@After
	public void after() {
		em.getTransaction().rollback();
		em.close();
	}
	
	@Test
	public void deveSalvarPapelNovo() {
		Papel papel = new Papel();
		papel.setDescricao("descricao");

		papelDao.salvar(papel);
		assertEquals(1, papelDao.total().longValue());
		Optional<Papel> usuarioOptional = papelDao.porDescricao("descricao");
		assertTrue(usuarioOptional.isPresent());
		assertEquals("descricao", usuarioOptional.get().getDescricao());
	}

}
