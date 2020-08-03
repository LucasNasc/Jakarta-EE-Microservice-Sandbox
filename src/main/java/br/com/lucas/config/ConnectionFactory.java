package br.com.lucas.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionFactory {

    private ConnectionFactory() {}

    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("JAKAEE");

    public static EntityManager getEntityManager(){
        return factory.createEntityManager();
    }

}
