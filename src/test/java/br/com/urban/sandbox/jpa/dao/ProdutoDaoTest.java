package br.com.urban.sandbox.jpa.dao;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.urban.sandbox.jpa.CriadorDeProdutos;
import br.com.urban.sandbox.jpa.model.Produto;

public class ProdutoDaoTest {
	
	private EntityManager em;
	private ProdutoDao produtoDao;
	
	@Before
	public void beforeTest() {
		em = new CriadorDeEntityManager().getEntityManager();
		CriadorDeProdutos.criarProdutos(em);
		produtoDao = new ProdutoDao(em);
		em.getTransaction().begin();
	}
	
	@After
	public void after() {
		em.getTransaction().rollback();
		em.close();
	}
	
	@Test
	public void teste() {
		List<Produto> produtos = produtoDao.getProdutosComCategorias();
		assertEquals(5, produtos.size());
		System.out.println(produtos.get(0).getCategorias().get(0).getNome());
	}

}
