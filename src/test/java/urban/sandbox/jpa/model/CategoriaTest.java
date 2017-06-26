package urban.sandbox.jpa.model;

import org.junit.Test;
import urban.sandbox.jpa.util.JPAUtil;

import javax.persistence.EntityManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CategoriaTest {

    @Test
    public void shouldSave() {
        final Categoria categoria = new Categoria("Teste");

        EntityManager em = new JPAUtil().getEntityManager();
        em.getTransaction().begin();

        em.persist(categoria);

        em.getTransaction().commit();
        final Categoria found = em.find(Categoria.class, categoria.getId());
        em.close();

        assertThat(found.getNome(), is("Teste"));
    }
}
