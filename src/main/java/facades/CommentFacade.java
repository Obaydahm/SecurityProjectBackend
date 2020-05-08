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
public class CommentFacade {
    
    private static CommentFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private CommentFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static CommentFacade getCommentFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CommentFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public long getCommentCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long commentCount = (long) em.createQuery("SELECT COUNT(c) FROM Comment c").getSingleResult();
            return commentCount;
        } finally {
            em.close();
        }

    }
}
