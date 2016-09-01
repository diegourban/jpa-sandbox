package br.com.urban.sandbox.jpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.urban.sandbox.jpa.model.Categoria;

public class CategoriaDao {

	@PersistenceContext
	private EntityManager em;

	public List<Categoria> getCategorias() {
		TypedQuery<Categoria> query = em.createQuery("from Categoria", Categoria.class);

		return query.getResultList();
	}
}
