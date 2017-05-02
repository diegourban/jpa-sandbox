package urban.sandbox.jpa;

import urban.sandbox.jpa.model.Produto;
import urban.sandbox.jpa.util.CriadorDeProdutos;
import urban.sandbox.jpa.util.JPAUtil;

import javax.persistence.EntityManager;

public class TestaCache {
	
	public static void main(String[] args) {
		JPAUtil criadorDeEntityManager = new JPAUtil();
		
		CriadorDeProdutos.criarProdutos();
		
		EntityManager em1 = criadorDeEntityManager.getEntityManager();
				
		Produto produto1 = em1.find(Produto.class, 1);
		Produto produto2 = em1.find(Produto.class, 1); // uses the first level cache from entity manager, avoiding a new select
		System.out.println(produto1.getNome());
		System.out.println(produto2.getNome());
		
		EntityManager em2 = criadorDeEntityManager.getEntityManager();
		
		// the first level cache has a entity manager scope, here execute be a new select
		// after enabling second level cache and defined Produto with @Cache there will be no select for Produto
		// however, any other mapped entities at Produto class without @Cache will execute a select
		Produto produto3 = em2.find(Produto.class, 1); 
		System.out.println(produto3.getNome());
		
	}

}

