package br.urban.prototipo.dao;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.urban.prototipo.model.Papel;
import br.urban.prototipo.model.Papel_;

public class PapelDao {

	private final EntityManager em;

	public PapelDao(EntityManager em) {
		this.em = em;
	}

	public void salvar(Papel papel) {
		em.persist(papel);
	}

	public long total() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = cb.createQuery(Long.class);
		Root<Papel> from = query.from(Papel.class);
		query.select(cb.count(from));
		return em.createQuery(query).getSingleResult();
	}
	
	public Optional<Papel> porDescricao(String descricao) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Papel> query = cb.createQuery(Papel.class);
		Root<Papel> from = query.from(Papel.class);
		query.select(from);
		query.where(cb.equal(from.get(Papel_.descricao), descricao));
		return Optional.of(em.createQuery(query).getSingleResult());
	}

	public void deletar(Papel papel) {
		em.remove(papel);
	}

}
