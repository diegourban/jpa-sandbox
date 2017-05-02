package urban.sandbox.jpa.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import urban.sandbox.jpa.exception.PapelDaoException;
import urban.sandbox.jpa.model.Papel;
import urban.sandbox.jpa.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class PapelDaoTest {
	
	private EntityManager em;
	private PapelDao papelDao;
	
	@Before
	public void beforeTest() {
		em = new JPAUtil().getEntityManager();
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
		Papel p = new Papel.PapelBuilder().build();
		papelDao.salvar(p);
	}
	
	@Test(expected=PapelDaoException.class)
	public void naoDeveSalvarPapelComDescricaoVazia() throws PapelDaoException {
		Papel p = new Papel.PapelBuilder().comDescricao("").build();
		papelDao.salvar(p);
	}
	
	@Test(expected=PapelDaoException.class)
	public void naoDeveSalvarPapelComDescricaoComEspaco() throws PapelDaoException {
		Papel p = new Papel.PapelBuilder().comDescricao(" ").build();
		papelDao.salvar(p);
	}
	
	@Test
	public void deveSalvarPapelNovo() throws PapelDaoException {
		assertEquals(0, papelDao.total());
		Papel papel = new Papel.PapelBuilder().comDescricao("descricao").build();

		papelDao.salvar(papel);
		assertEquals(1, papelDao.total());
		Optional<Papel> papelOptional = papelDao.porDescricao("descricao");
		assertTrue(papelOptional.isPresent());
		assertEquals("descricao", papelOptional.get().getDescricao());
	}
	
	@Test
	public void deveDeletarUmPapel() throws PapelDaoException {
		assertEquals(0, papelDao.total());
		
		Papel papel = new Papel.PapelBuilder().comDescricao("paradeletar").build();
		
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
		
		Papel papel = new Papel.PapelBuilder().comDescricao("papel1").build();
		papelDao.salvar(papel);
		
		Papel papel2 = new Papel.PapelBuilder().comDescricao("papel2").build();
		papelDao.salvar(papel2);
		
		assertEquals(2, papelDao.total());
		
		List<Papel> papeis = papelDao.listarTodos();
		assertNotNull(papeis);
		assertEquals(2, papeis.size());
	}

}
