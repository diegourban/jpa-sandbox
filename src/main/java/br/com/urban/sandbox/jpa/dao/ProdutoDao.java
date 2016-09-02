package br.com.urban.sandbox.jpa.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.urban.sandbox.jpa.model.Loja;
import br.com.urban.sandbox.jpa.model.Produto;

public class ProdutoDao {

	private EntityManager em;

	public ProdutoDao(EntityManager em) {
		this.em = em;
	}

	public List<Produto> getProdutos() {
		return em.createQuery("from Produto", Produto.class).getResultList();
	}

	public List<Produto> getProdutosComCategorias() {
		return em.createQuery("select distinct p from Produto p", Produto.class)
				.setHint("javax.persistence.loadgraph", em.getEntityGraph("produtoComCategoria")).getResultList();
	}

	public Produto getProduto(Integer id) {
		Produto produto = em.find(Produto.class, id);
		return produto;
	}

	public List<Produto> getProdutos(String nome, Integer categoriaId, Integer lojaId) {

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Produto> query = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = query.from(Produto.class);

		Path<String> nomePath = root.<String>get("nome");
		Path<Integer> categoriaPath = root.join("categorias").<Integer>get("id");
		Path<Integer> lojaPath = root.<Loja>get("loja").<Integer>get("id");

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (!nome.isEmpty()) {
			Predicate nomeIgual = criteriaBuilder.like(nomePath, "%" + nome + "%");
			predicates.add(nomeIgual);
		}

		if (categoriaId != null) {
			Predicate categoriaIgual = criteriaBuilder.equal(categoriaPath, categoriaId);
			predicates.add(categoriaIgual);
		}

		if (lojaId != null) {
			Predicate lojaIgual = criteriaBuilder.equal(lojaPath, lojaId);
			predicates.add(lojaIgual);
		}

		query.where((Predicate[]) predicates.toArray(new Predicate[0]));

		TypedQuery<Produto> typedQuery = em.createQuery(query);

		typedQuery.setHint("org.hibernate.cacheable", "true"); // query cache
		return typedQuery.getResultList();

	}

	public void insere(Produto produto) {
		if (produto.getId() == null)
			em.persist(produto);
		else
			em.merge(produto);
	}

}
