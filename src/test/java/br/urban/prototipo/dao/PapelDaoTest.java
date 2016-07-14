package br.urban.prototipo.dao;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.urban.prototipo.exception.PapelDaoException;
import br.urban.prototipo.model.Papel;

public class PapelDaoTest {
	
	private EntityManager em;
	private PapelDao papelDao;
	
	@Before
	public void beforeTest() {
		em = new CriadorDeEntityManager().getEntityManager();
		papelDao = new PapelDao(em);
		em.getTransaction().begin();
	}
	
	@After
	public void after() {
		em.getTransaction().rollback();
		em.close();
	}
	
	@Test(expected=PapelDaoException.class)
	public void naoDeveSalvarNulo() throws PapelDaoException {
		papelDao.salvar(null);
	}
	
	@Test(expected=PapelDaoException.class)
	public void naoDeveSalvarPapelComDescricaoNull() throws PapelDaoException {
		Papel p = new Papel();
		p.setDescricao(null);
		papelDao.salvar(p);
	}
	
	@Test(expected=PapelDaoException.class)
	public void naoDeveSalvarPapelComDescricaoVazia() throws PapelDaoException {
		Papel p = new Papel();
		p.setDescricao("");
		papelDao.salvar(p);
	}
	
	@Test
	public void deveSalvarPapelNovo() throws PapelDaoException {
		assertEquals(0, papelDao.total());
		Papel papel = new Papel();
		papel.setDescricao("descricao");

		papelDao.salvar(papel);
		assertEquals(1, papelDao.total());
		Optional<Papel> papelOptional = papelDao.porDescricao("descricao");
		assertTrue(papelOptional.isPresent());
		assertEquals("descricao", papelOptional.get().getDescricao());
	}
	
	@Test
	public void deveDeletarUmPapel() throws PapelDaoException {
		assertEquals(0, papelDao.total());
		
		Papel papel = new Papel();
		papel.setDescricao("paradeletar");
		
		papelDao.salvar(papel);
		assertEquals(1, papelDao.total());
		Optional<Papel> papelOptional = papelDao.porDescricao("paradeletar");
		assertTrue(papelOptional.isPresent());
		papelDao.deletar(papelOptional.get());
		
		assertEquals(0, papelDao.total());
	}
	
	@Test
	public void deveListarTodosMesmoComTabelaVazia() throws PapelDaoException {
		assertEquals(0, papelDao.total());
						
		List<Papel> papeis = papelDao.listarTodos();
		assertNotNull(papeis);
		assertEquals(0, papeis.size());
	}
	
	@Test
	public void deveListarTodos() throws PapelDaoException {
		assertEquals(0, papelDao.total());
		
		Papel papel = new Papel();
		papel.setDescricao("papel1");
		papelDao.salvar(papel);
		
		Papel papel2 = new Papel();
		papel2.setDescricao("papel1");
		papelDao.salvar(papel2);
		
		assertEquals(2, papelDao.total());
		
		List<Papel> papeis = papelDao.listarTodos();
		assertNotNull(papeis);
		assertEquals(2, papeis.size());
	}

}
