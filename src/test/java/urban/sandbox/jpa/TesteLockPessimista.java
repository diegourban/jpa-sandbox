package urban.sandbox.jpa;

import urban.sandbox.jpa.model.Produto;
import urban.sandbox.jpa.util.CriadorDeProdutos;
import urban.sandbox.jpa.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

public class TesteLockPessimista {

	public static void main(String[] args) {
		JPAUtil criadorDeEntityManager = new JPAUtil();
		
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
