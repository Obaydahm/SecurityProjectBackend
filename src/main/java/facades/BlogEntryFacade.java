/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTO.BlogEntryDTO;
import DTO.CommentDTO;
import DTO.UserDTO;
import entities.BlogEntry;
import entities.Comment;
import entities.User1;
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
    public BlogEntryDTO addBlogEntry(BlogEntryDTO b) {
        EntityManager em = getEntityManager();

        BlogEntry be = new BlogEntry();
        be.getDate();
        be.getContent();

        try {
            em.getTransaction().begin();
            em.persist(be);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new BlogEntryDTO(be);
    }

    // Find a BlogEntry
    public BlogEntryDTO getBlogEntry(Long blogEntry_id) {
        EntityManager em = getEntityManager();
        BlogEntry blogEntryDTO = em.find(BlogEntry.class, blogEntry_id);
        return new BlogEntryDTO(blogEntryDTO);
    }

    // Get all blogEntries
    public BlogEntryDTO getAllBlogEntries(){
        EntityManager em = getEntityManager();
        try{
            
            List<BlogEntry> blogEntry = em.createQuery("SELECT b FROM BlogEntry b", BlogEntry.class).getResultList();
            return new BlogEntryDTO((BlogEntry) blogEntry);
        }finally {
            em.close();
        }
    }
    
    
    // Remove blogentry
    public BlogEntryDTO remove(long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            BlogEntryDTO b = em.find(BlogEntryDTO.class, id);
            em.remove(em.merge(b));
            em.getTransaction().commit();
            return b;
        } finally {
            em.close();
        }
    }

    // No of BlogEntries
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
