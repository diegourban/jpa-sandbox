package br.urban.prototipo.dao;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.urban.prototipo.model.Usuario;
import br.urban.prototipo.model.Usuario_;

public class UsuarioDao {

	private final EntityManager em;

	public UsuarioDao(EntityManager em) {
		this.em = em;
	}

	public void salvar(Usuario usuario) {
		em.persist(usuario);
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
		query.where(cb.equal(from.get(Usuario_.login), login));
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
		query.where(cb.equal(from.get(Usuario_.login), login));
		return em.createQuery(query).getSingleResult();
	}

}
