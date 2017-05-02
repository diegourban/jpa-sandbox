package urban.sandbox.jpa.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-sandbox-test");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
