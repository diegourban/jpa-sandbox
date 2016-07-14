package br.urban.prototipo.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.urban.prototipo.exception.PapelDaoException;
import br.urban.prototipo.model.Papel;
import br.urban.prototipo.model.Papel_;

public class PapelDao {

	private final EntityManager em;

	public PapelDao(EntityManager em) {
		this.em = em;
	}

	public void salvar(Papel papel) throws PapelDaoException {
		validarPapel(papel);
		em.persist(papel);
		em.flush();
	}
	
	public void deletar(Papel papel) {
		em.remove(papel);
		em.flush();
	}
	
	private void validarPapel(Papel papel) throws PapelDaoException {
		if(papel == null) {
			throw new PapelDaoException("Papel não pode ser nulo");
		}
		validarDescricao(papel.getDescricao());
	}
	
	private void validarDescricao(String descricao) throws PapelDaoException {
		if(descricao == null || descricao.trim().isEmpty()) {
			throw new PapelDaoException("Descrição é obrigatória");
		}
	}

	public long total() {
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<Long> query = cb.createQuery(Long.class);
		final Root<Papel> from = query.from(Papel.class);
		query.select(cb.count(from));
		return em.createQuery(query).getSingleResult();
	}
	
	public Optional<Papel> porDescricao(String descricao) {
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<Papel> query = cb.createQuery(Papel.class);
		final Root<Papel> from = query.from(Papel.class);
		query.select(from);
		query.where(cb.equal(from.get(Papel_.descricao), descricao));
		return Optional.of(em.createQuery(query).getSingleResult());
	}
	
	public List<Papel> listarTodos() {
		final CriteriaBuilder cb = em.getCriteriaBuilder();
		final CriteriaQuery<Papel> query = cb.createQuery(Papel.class);
		final Root<Papel> from = query.from(Papel.class);
		query.select(from);
		return em.createQuery(query).getResultList();
	}

}
