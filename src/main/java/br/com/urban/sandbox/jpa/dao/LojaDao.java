package br.com.urban.sandbox.jpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.urban.sandbox.jpa.model.Loja;

public class LojaDao {
	
	@PersistenceContext
	private EntityManager em;
	
	public List<Loja> getLojas() { 
		TypedQuery<Loja> query = em.createQuery("from Loja", Loja.class);
		
		return query.getResultList();
	}

	public Loja getLoja(Integer lojaId) {
		return em.find(Loja.class, lojaId);
	}

}
