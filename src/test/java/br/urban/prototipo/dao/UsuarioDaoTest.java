package br.urban.prototipo.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.urban.prototipo.model.Papel;
import br.urban.prototipo.model.Usuario;

public class UsuarioDaoTest {

	private EntityManager em;
	private UsuarioDao usuarioDao;

	@Before
	public void beforeTest() {
		em = new CriadorDeEntityManager().getEntityManager();
		usuarioDao = new UsuarioDao(em);
		em.getTransaction().begin();
	}

	@After
	public void after() {
		em.getTransaction().rollback();
		em.close();
	}

	@Test
	public void deveSalvarUsuarioNovo() {
		assertEquals(0, usuarioDao.total());
		Usuario usuario = new Usuario();
		usuario.setLogin("login");

		usuarioDao.salvar(usuario);
		assertEquals(1, usuarioDao.total());
		Optional<Usuario> usuarioOptional = usuarioDao.porLogin("login");
		assertTrue(usuarioOptional.isPresent());
		assertEquals("login", usuarioOptional.get().getLogin());
	}
	
	@Test
	public void deveDeletarUmUsuario() {
		assertEquals(0, usuarioDao.total());
		
		Usuario usuario = new Usuario();
		usuario.setLogin("deletar");
		
		usuarioDao.salvar(usuario);
		
		assertEquals(1, usuarioDao.total());
		Optional<Usuario> usuarioParaDeletarOptional = usuarioDao.porLogin("deletar");
		assertTrue(usuarioParaDeletarOptional.isPresent());
		usuarioDao.deletar(usuarioParaDeletarOptional.get());
		
		assertEquals(0, usuarioDao.total());
	}
	
	@Test
	public void deveAlterarLogin() {
		Usuario usuario = new Usuario();
		
		usuario.setLogin("alterar");
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
	public void deveSalvarUsuarioComUmPapel() {
		Papel papel = new Papel();
		papel.setDescricao("ha");
		
		Usuario usuario = new Usuario();
		usuario.setLogin("login");
		usuario.setPapel(papel);
		
		usuarioDao.salvar(usuario);
		
		assertTrue(usuarioDao.existemUsuariosComLogin("login"));
		
		assertEquals(1, usuarioDao.total());
		Optional<Usuario> usuarioOptional = usuarioDao.porLogin("login");
		assertTrue(usuarioOptional.isPresent());
		assertEquals("login", usuario.getLogin());
		
		assertEquals("ha", usuario.getPapel().getDescricao());
	}

}
