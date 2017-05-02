package urban.sandbox.jpa;


import urban.sandbox.jpa.model.Produto;
import urban.sandbox.jpa.util.CriadorDeProdutos;
import urban.sandbox.jpa.util.JPAUtil;

import javax.persistence.EntityManager;

public class TesteLockOtimista {
	
	public static void main(String[] args) {
		JPAUtil criadorDeEntityManager = new JPAUtil();
		
		EntityManager em = criadorDeEntityManager.getEntityManager();
		em.getTransaction().begin();
		CriadorDeProdutos.criarProdutos(em);
		em.getTransaction().commit();
		
		EntityManager em1 = criadorDeEntityManager.getEntityManager();
		EntityManager em2 = criadorDeEntityManager.getEntityManager();
		
		em1.getTransaction().begin();
        em2.getTransaction().begin();
        
		Produto produtoDoEM1 = em1.find(Produto.class, 1);
		System.out.println(produtoDoEM1);
		Produto produtoDoEM2 = em2.find(Produto.class, 1);
		System.out.println(produtoDoEM2);
		
		produtoDoEM1.setDescricao("blabla");
		em1.persist(produtoDoEM1);
		try {
			em1.getTransaction().commit();
		} catch (Exception e) {
			Produto produtoAtualizado = em1.find(Produto.class, 1);
			produtoAtualizado.setDescricao("blabla");
			em1.persist(produtoAtualizado);
			em1.getTransaction().commit();
		}
		
		produtoDoEM2.setNome("nome");
		em2.persist(produtoDoEM2);
		try {
			em2.getTransaction().commit();
		} catch (Exception e) {
			em2.getTransaction().begin();
			Produto produtoAtualizado = em2.find(Produto.class, 1);
			produtoAtualizado.setDescricao("blabla");
			em2.persist(produtoAtualizado);
			em2.getTransaction().commit();
		}
		
		
		
	}

}
