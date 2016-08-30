package br.com.urban.sandbox.jpa.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CriadorDeEntityManager {

	private static EntityManagerFactory emf;

	public EntityManager getEntityManager() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory("jpa-sandbox-hsqldb");
		}

		return emf.createEntityManager();
	}
}
