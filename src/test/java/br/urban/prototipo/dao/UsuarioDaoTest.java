package br.urban.prototipo.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.urban.prototipo.exception.UsuarioDaoException;
import br.urban.prototipo.model.Papel;
import br.urban.prototipo.model.Usuario;

public class UsuarioDaoTest {

	private EntityManager em;
	private UsuarioDao usuarioDao;
	private PapelDao papelDao;

	@Before
	public void beforeTest() {
		em = new CriadorDeEntityManager().getEntityManager();
		usuarioDao = new UsuarioDao(em);
		papelDao = new PapelDao(em);
		em.getTransaction().begin();
	}

	@After
	public void after() {
		em.getTransaction().rollback();
		em.close();
	}
	
	@Test(expected=UsuarioDaoException.class)
	public void naoDeveSalvarNulo() throws UsuarioDaoException {
		usuarioDao.salvar(null);
	}
	
	@Test(expected=UsuarioDaoException.class)
	public void naoDeveSalvarUsuarioComLoginNull() throws UsuarioDaoException {
		Usuario usuario = new Usuario(null);
		usuarioDao.salvar(usuario);
	}
	
	@Test(expected=UsuarioDaoException.class)
	public void naoDeveSalvarUsuarioComLoginVazio() throws UsuarioDaoException {
		Usuario usuario = new Usuario("");
		usuarioDao.salvar(usuario);
	}
	
	@Test(expected=UsuarioDaoException.class)
	public void naoDeveSalvarUsuarioComLoginComEspaco() throws UsuarioDaoException {
		Usuario usuario = new Usuario(" ");
		usuarioDao.salvar(usuario);
	}

	@Test
	public void deveSalvarUsuarioNovo() throws UsuarioDaoException {
		assertEquals(0, usuarioDao.total());
		Usuario usuario = new Usuario("login");

		usuarioDao.salvar(usuario);
		assertEquals(1, usuarioDao.total());
		Optional<Usuario> usuarioOptional = usuarioDao.porLogin("login");
		assertTrue(usuarioOptional.isPresent());
		assertEquals("login", usuarioOptional.get().getLogin());
	}
	
	@Test
	public void deveDeletarUmUsuario() throws UsuarioDaoException {
		assertEquals(0, usuarioDao.total());
		
		Usuario usuario = new Usuario("deletar");
		
		usuarioDao.salvar(usuario);
		
		assertEquals(1, usuarioDao.total());
		Optional<Usuario> usuarioParaDeletarOptional = usuarioDao.porLogin("deletar");
		assertTrue(usuarioParaDeletarOptional.isPresent());
		usuarioDao.deletar(usuarioParaDeletarOptional.get());
		
		assertEquals(0, usuarioDao.total());
	}
	
	@Test
	public void deveAlterarLogin() throws UsuarioDaoException {
		Usuario usuario = new Usuario("alterar");
		
		usuarioDao.salvar(usuario);
		assertEquals(1, usuarioDao.total());
		
		Optional<Usuario> usuarioOptional = usuarioDao.porLogin("alterar");
		assertTrue(usuarioOptional.isPresent());
		usuario = usuarioOptional.get();
		usuario.setLogin("alterado");
		usuarioDao.salvar(usuario);
		
		// não deve encontrar o antigo
		assertFalse(usuarioDao.existemUsuariosComLogin("alterar"));
		
		// não deve ter criado outro usuário
		assertEquals(1, usuarioDao.total());
		
		// deve encontrar o alterado
		usuarioOptional = usuarioDao.porLogin("alterado");
		assertTrue(usuarioOptional.isPresent());
		assertEquals("alterado", usuario.getLogin());
	}
	
	@Test
	public void deveSalvarUsuarioComUmPapel() throws UsuarioDaoException {
		assertEquals(0, usuarioDao.total());
		assertEquals(0, papelDao.total());
		
		Usuario usuario = new Usuario("login");
		Papel papel = new Papel("papel");
		usuario.adiciona(papel);
		
		usuarioDao.salvar(usuario);
		assertEquals(1, usuarioDao.total());
		assertEquals(1, papelDao.total());
		
		Optional<Usuario> usuarioOptional = usuarioDao.porLogin("login");
		assertEquals(true, usuarioOptional.isPresent());
		assertEquals(1, usuarioOptional.get().getQuantidadePapeis());
		
	}
	
	@Test
	public void deveSalvarUsuarioComPapeis() throws UsuarioDaoException {
		assertEquals(0, usuarioDao.total());
		assertEquals(0, papelDao.total());
		
		Usuario usuario = new Usuario("login");
		Papel papel1 = new Papel("papel1");
		Papel papel2 = new Papel("papel2");
		usuario.adiciona(papel1);
		usuario.adiciona(papel2);
		
		usuarioDao.salvar(usuario);
		assertEquals(1, usuarioDao.total());
		assertEquals(2, papelDao.total());
		
		Optional<Usuario> usuarioOptional = usuarioDao.porLogin("login");
		assertEquals(true, usuarioOptional.isPresent());
		assertEquals(2, usuarioOptional.get().getQuantidadePapeis());
	}
	
}
