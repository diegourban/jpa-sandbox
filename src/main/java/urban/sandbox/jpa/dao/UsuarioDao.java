package urban.sandbox.jpa.dao;

import urban.sandbox.jpa.exception.UsuarioDaoException;
import urban.sandbox.jpa.model.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class UsuarioDao {

	private final EntityManager em;

	public UsuarioDao(EntityManager em) {
		this.em = em;
	}

	public void salvar(Usuario usuario) throws UsuarioDaoException {
		validarUsuario(usuario);
		if(usuario.isNew()) {
			em.persist(usuario);
		} else {
			em.merge(usuario);
		}
		em.flush();
	}
	
	public void deletar(Usuario usuario) {
		em.remove(usuario);
		em.flush();
	}

	public Optional<Usuario> porLogin(String login) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Usuario> query = cb.createQuery(Usuario.class);
		Root<Usuario> from = query.from(Usuario.class);
		query.select(from);
		query.where(cb.equal(from.get("login"), login));
		return Optional.of(em.createQuery(query).getSingleResult());
	}

	public long total() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = cb.createQuery(Long.class);
		Root<Usuario> from = query.from(Usuario.class);
		query.select(cb.count(from));
		return em.createQuery(query).getSingleResult();
	}

	public boolean existemUsuariosComLogin(String login) {
		return totalCom(login) > 0;
	}

	private Long totalCom(String login) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = cb.createQuery(Long.class);
		Root<Usuario> from = query.from(Usuario.class);
		query.select(cb.count(from));
		query.where(cb.equal(from.get("login"), login));
		return em.createQuery(query).getSingleResult();
	}
	
	private void validarUsuario(Usuario usuario) throws UsuarioDaoException {
		if(usuario == null) {
			throw new UsuarioDaoException("Usu�rio n�o pode ser nulo");
		}
		validarLogin(usuario.getLogin());
	}

	private void validarLogin(String login) throws UsuarioDaoException {
		if(login == null || login.trim().isEmpty()) {
			throw new UsuarioDaoException("Login � obrigat�rio");
		}
	}

}
