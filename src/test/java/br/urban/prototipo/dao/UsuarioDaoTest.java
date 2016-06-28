package br.urban.prototipo.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.urban.prototipo.model.Usuario;

public class UsuarioDaoTest {

	private EntityManager em;
	private UsuarioDao usuarioDao;

	@Before
	public void beforeTest() {
		em = new CriadorDeSessao().getEntityManager();
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
		Usuario usuario = new Usuario();
		usuario.setLogin("login");

		usuarioDao.salvar(usuario);
		assertEquals(1, usuarioDao.total().longValue());
		Optional<Usuario> usuarioOptional = usuarioDao.porLogin("login");
		assertTrue(usuarioOptional.isPresent());
		assertEquals("login", usuarioOptional.get().getLogin());
	}
	
	@Test
	public void deveDeletarUmUsuario() {
		Usuario usuario = new Usuario();
		usuario.setLogin("deletar");
		
		usuarioDao.salvar(usuario);
		
		assertEquals(1, usuarioDao.total().longValue());
		Optional<Usuario> usuarioParaDeletarOptional = usuarioDao.porLogin("deletar");
		assertTrue(usuarioParaDeletarOptional.isPresent());
		usuarioDao.deletar(usuarioParaDeletarOptional.get());
		
		assertEquals(0, usuarioDao.total().longValue());
	}
	
	@Test
	public void deveAlterarLogin() {
		Usuario usuario = new Usuario();
		
		usuario.setLogin("alterar");
		usuarioDao.salvar(usuario);
		assertEquals(1, usuarioDao.total().longValue());
		
		Optional<Usuario> usuarioOptional = usuarioDao.porLogin("alterar");
		assertTrue(usuarioOptional.isPresent());
		usuario = usuarioOptional.get();
		usuario.setLogin("alterado");
		usuarioDao.salvar(usuario);
		
		// não deve encontrar o antigo
		assertFalse(usuarioDao.existemUsuariosComLogin("alterar"));
		
		// não deve ter criado outro usuário
		assertEquals(1, usuarioDao.total().longValue());
		
		// deve encontrar o alterado
		usuarioOptional = usuarioDao.porLogin("alterado");
		assertTrue(usuarioOptional.isPresent());
		assertEquals("alterado", usuario.getLogin());
	}

}
