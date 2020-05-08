/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Henrik
 */
public class UserFacade {
    
    private static UserFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private UserFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public long getUserCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long userCount = (long) em.createQuery("SELECT COUNT(u) FROM User u").getSingleResult();
            return userCount;
        } finally {
            em.close();
        }

    }    
}
