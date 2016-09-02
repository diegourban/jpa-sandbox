package br.com.urban.sandbox.jpa;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import br.com.urban.sandbox.jpa.dao.CriadorDeEntityManager;
import br.com.urban.sandbox.jpa.model.Produto;

public class TesteLockPessimista {

	public static void main(String[] args) {
		CriadorDeEntityManager criadorDeEntityManager = new CriadorDeEntityManager();
		
		CriadorDeProdutos.criarProdutos(criadorDeEntityManager.getEntityManager());
		
		EntityManager em1 = criadorDeEntityManager.getEntityManager();
		EntityManager em2 = criadorDeEntityManager.getEntityManager();

		em1.getTransaction().begin();
        em2.getTransaction().begin();

        Produto produtoDoEM1 = em1.find(Produto.class, 1);
        em1.lock(produtoDoEM1,LockModeType.PESSIMISTIC_WRITE);

        produtoDoEM1.setNome("Maria");    

        Produto produtoDoEM2 = em2.find(Produto.class, 1);
        em2.lock(produtoDoEM2, LockModeType.PESSIMISTIC_WRITE);
	}

}
