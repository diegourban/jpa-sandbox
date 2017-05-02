package urban.sandbox.jpa.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import urban.sandbox.jpa.exception.UsuarioDaoException;
import urban.sandbox.jpa.model.Papel;
import urban.sandbox.jpa.model.Usuario;
import urban.sandbox.jpa.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.junit.Assert.*;

public class UsuarioDaoTest {

	private EntityManager em;
	private UsuarioDao usuarioDao;
	private PapelDao papelDao;

	@Before
	public void beforeTest() {
		em = new JPAUtil().getEntityManager();
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
		Usuario usuario = new Usuario.UsuarioBuilder().build();
		usuarioDao.salvar(usuario);
	}
	
	@Test(expected=UsuarioDaoException.class)
	public void naoDeveSalvarUsuarioComLoginVazio() throws UsuarioDaoException {
		Usuario usuario = new Usuario.UsuarioBuilder().comLogin("").build();
		usuarioDao.salvar(usuario);
	}
	
	@Test(expected=UsuarioDaoException.class)
	public void naoDeveSalvarUsuarioComLoginComEspaco() throws UsuarioDaoException {
		Usuario usuario = new Usuario.UsuarioBuilder().comLogin(" ").build();
		usuarioDao.salvar(usuario);
	}

	@Test
	public void deveSalvarUsuarioNovo() throws UsuarioDaoException {
		assertEquals(0, usuarioDao.total());
		Usuario usuario = new Usuario.UsuarioBuilder().comLogin("login").build();

		usuarioDao.salvar(usuario);
		assertEquals(1, usuarioDao.total());
		Optional<Usuario> usuarioOptional = usuarioDao.porLogin("login");
		assertTrue(usuarioOptional.isPresent());
		assertEquals("login", usuarioOptional.get().getLogin());
	}
	
	@Test
	public void deveDeletarUmUsuario() throws UsuarioDaoException {
		assertEquals(0, usuarioDao.total());
		
		Usuario usuario = new Usuario.UsuarioBuilder().comLogin("deletar").build();
		
		usuarioDao.salvar(usuario);
		
		assertEquals(1, usuarioDao.total());
		Optional<Usuario> usuarioParaDeletarOptional = usuarioDao.porLogin("deletar");
		assertTrue(usuarioParaDeletarOptional.isPresent());
		usuarioDao.deletar(usuarioParaDeletarOptional.get());
		
		assertEquals(0, usuarioDao.total());
	}
	
	@Test
	public void deveAlterarLogin() throws UsuarioDaoException {
		Usuario usuario = new Usuario.UsuarioBuilder().comLogin("alterar").build();
		
		usuarioDao.salvar(usuario);
		assertEquals(1, usuarioDao.total());
		
		Optional<Usuario> usuarioOptional = usuarioDao.porLogin("alterar");
		assertTrue(usuarioOptional.isPresent());
		usuario = usuarioOptional.get();
		usuario.setLogin("alterado");
		usuarioDao.salvar(usuario);
		
		assertFalse(usuarioDao.existemUsuariosComLogin("alterar"));
		
		assertEquals(1, usuarioDao.total());
		
		usuarioOptional = usuarioDao.porLogin("alterado");
		assertTrue(usuarioOptional.isPresent());
		assertEquals("alterado", usuario.getLogin());
	}
	
	@Test
	public void deveSalvarUsuarioComUmPapel() throws UsuarioDaoException {
		assertEquals(0, usuarioDao.total());
		assertEquals(0, papelDao.total());
		
		Usuario usuario = new Usuario.UsuarioBuilder().comLogin("login").build();
		Papel papel = new Papel.PapelBuilder().comDescricao("papel").build();
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
		
		Usuario usuario = new Usuario.UsuarioBuilder().comLogin("login").build();
		Papel papel1 = new Papel.PapelBuilder().comDescricao("papel1").build();
		Papel papel2 = new Papel.PapelBuilder().comDescricao("papel2").build();
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
