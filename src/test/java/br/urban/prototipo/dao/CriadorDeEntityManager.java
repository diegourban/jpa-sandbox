package br.urban.prototipo.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CriadorDeEntityManager {

	private static EntityManagerFactory emf;

	public EntityManager getEntityManager() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory("test");
		}

		return emf.createEntityManager();
	}
}