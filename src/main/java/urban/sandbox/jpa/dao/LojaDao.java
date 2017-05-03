package urban.sandbox.jpa.dao;

import urban.sandbox.jpa.model.Loja;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class LojaDao {
	
	@PersistenceContext
	private EntityManager em;
	
	public List<Loja> getLojas() {
		TypedQuery<Loja> query = em.createQuery("from Loja", Loja.class);
		
		return query.getResultList();
	}

	public Loja getLoja(Integer lojaId) {
		return em.find(Loja.class, lojaId);
	}

}
