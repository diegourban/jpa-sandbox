package urban.sandbox.jpa.model;

import org.junit.Before;
import org.junit.Test;
import urban.sandbox.jpa.util.JPAUtil;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;

public class ContaTest {

    @Before
    public void before() {
        EntityManager manager = new JPAUtil().getEntityManager();

        manager.getTransaction().begin();

        Conta conta1 = new Conta();
        Conta conta2 = new Conta();
        Conta conta3 = new Conta();
        Conta conta4 = new Conta();
        Conta conta5 = new Conta();

        conta1.setBanco("001 - BANCO DO BRASIL");
        conta1.setNumero("16987-8");
        conta1.setAgencia("6543");
        conta1.setTitular("Maria dos Santos");

        conta2.setBanco("237 - BANCO BRADESCO");
        conta2.setNumero("86759-1");
        conta2.setAgencia("1745");
        conta2.setTitular("Paulo Roberto Souza");

        conta3.setBanco("341 - BANCO ITAU UNIBANCO");
        conta3.setNumero("46346-3");
        conta3.setAgencia("4606");
        conta3.setTitular("Antonio Duraes");

        conta4.setBanco("033 - BANCO SANTANDER");
        conta4.setNumero("12345-6");
        conta4.setAgencia("9876");
        conta4.setTitular("Leandra Marques");

        conta5.setBanco("104 - CAIXA ECONOMICA FEDERAL");
        conta5.setNumero("98654-3");
        conta5.setAgencia("1234");
        conta5.setTitular("Alexandre Duarte");

        // persistindo as contas
        manager.persist(conta1);
        manager.persist(conta2);
        manager.persist(conta3);
        manager.persist(conta4);
        manager.persist(conta5);

        manager.getTransaction().commit();

        manager.close();
    }

    @Test
    public void devePersistirConta() {
        // transient
        Conta conta = new Conta();
        conta.setTitular("Diego");
        conta.setBanco("Banco do Brasil");
        conta.setAgencia("123");
        conta.setNumero("456");

        EntityManager em = new JPAUtil().getEntityManager();

        em.getTransaction().begin();
        em.persist(conta); // managed
        em.getTransaction().commit();

        em.close();
    }

    @Test
    public void deveBuscarConta() {
        EntityManager em = new JPAUtil().getEntityManager();

        em.getTransaction().begin();
        Conta conta = em.find(Conta.class, 1); // retorna o objeto em estado managed
        conta.setTitular("xpto"); // está sincronizada com banco, portanto, qualquer alteração reflete no BD
        em.getTransaction().commit();

        assertEquals("Maria dos Santos", conta.getTitular());

        em.close();
    }

    @Test
    public void deveBuscarConta2() {
        EntityManager em = new JPAUtil().getEntityManager();
        em.getTransaction().begin();

        Conta conta = em.find(Conta.class, 1);
        conta.setTitular("Josefina");
        conta.setAgencia("123");

        em.getTransaction().commit();
        em.close();

        EntityManager em2 = new JPAUtil().getEntityManager();
        em2.getTransaction().begin();

        conta.setTitular("Lautério"); // conta está detached
        em2.merge(conta); // transforma um objeto detached em merged

        em2.getTransaction().commit();
        em2.close();
    }

    @Test
    public void deveRemover() {
        EntityManager em = new JPAUtil().getEntityManager();
        em.getTransaction().begin();

        Conta conta = em.find(Conta.class, 1);
        em.remove(conta);

        em.getTransaction().commit();
        em.close();
    }
}
