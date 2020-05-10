/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTO.BlogEntryDTO;
import entities.BlogEntry;
import java.util.List;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Henrik
 */
public class BlogEntryFacade {

    private static BlogEntryFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private BlogEntryFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static BlogEntryFacade getBlogEntryFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BlogEntryFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Create
//    public BlogEntryDTO addBlogEntry(BlogEntryDTO b) {
//        EntityManager em = getEntityManager();
//        BlogEntry blogEntry = new BlogEntry(b.);
//
//    }

    public long getBlogEntryCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long blogEntryCount = (long) em.createQuery("SELECT COUNT(b) FROM BlogEntry b").getSingleResult();
            return blogEntryCount;
        } finally {
            em.close();
        }

    }
}
