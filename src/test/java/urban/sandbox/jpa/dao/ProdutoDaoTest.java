package urban.sandbox.jpa.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import urban.sandbox.jpa.model.Produto;
import urban.sandbox.jpa.util.CriadorDeProdutos;
import urban.sandbox.jpa.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProdutoDaoTest {
	
	private EntityManager em;
	private ProdutoDao produtoDao;
	
	@Before
	public void beforeTest() {
		em = new JPAUtil().getEntityManager();
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
