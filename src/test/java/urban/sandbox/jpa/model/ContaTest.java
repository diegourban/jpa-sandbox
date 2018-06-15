package urban.sandbox.jpa.model;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import urban.sandbox.jpa.util.JPAUtil;

import javax.persistence.EntityManager;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ContaTest {

    @Test
    public void shouldPersist() {
        // conta is still transient
        final Conta conta = new Conta();
        conta.setTitular("Diego");
        conta.setBanco("Banco do Brasil");
        conta.setAgencia("123");
        conta.setNumero("456");

        EntityManager em = new JPAUtil().getEntityManager();

        em.getTransaction().begin();
        em.persist(conta); // managed
        em.getTransaction().commit();
        em.clear();

        final Conta persisted = em.find(Conta.class, conta.getId());
        assertThat(persisted, CoreMatchers.notNullValue());
        assertThat(persisted.getTitular(), is("Diego"));
        assertThat(persisted.getBanco(), is("Banco do Brasil"));
        assertThat(persisted.getAgencia(), is("123"));
        assertThat(persisted.getNumero(), is("456"));

        em.close();
    }

}
