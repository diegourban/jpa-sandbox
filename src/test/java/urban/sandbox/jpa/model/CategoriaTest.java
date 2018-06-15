package urban.sandbox.jpa.model;

import org.junit.Test;
import urban.sandbox.jpa.util.JPAUtil;

import javax.persistence.EntityManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CategoriaTest {

    @Test
    public void shouldSave() {
        final String name = "Test";
        final Categoria categoria = new Categoria(name);

        EntityManager em = new JPAUtil().getEntityManager();
        em.getTransaction().begin();

        em.persist(categoria);

        em.getTransaction().commit();
        em.clear(); // ensures that there is no entity at the persistence context, forcing a select

        final Categoria found = em.find(Categoria.class, categoria.getId());
        em.close();

        assertThat(found.getNome(), is(name));
    }
}
