/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTO.BlogEntryDTO;
import entities.BlogEntry;
import entities.User1;
import java.sql.Date;
import java.util.ArrayList;
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
    public BlogEntry addBlogEntry(String content, Date d, int id) {
        EntityManager em = getEntityManager();

        User1 u1 = em.find(User1.class, id); //this is null, because you create a user with no generated id
        //System.out.println(u1.getId());

        BlogEntry be = new BlogEntry(content, d, u1);
        //be.setUser(u1);

        try {
            em.getTransaction().begin();
            em.persist(be);
            //em.persist(u1);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return be;
    }

    // Retrieve
    public BlogEntry getBlogEntry(int id) {
        EntityManager em = getEntityManager();
        BlogEntry blogEntry = em.find(BlogEntry.class, id);
        return blogEntry;
    }

    //Delete
    public BlogEntry deleteBlogEntry(int id) {
        EntityManager em = getEntityManager();
        BlogEntry be = em.find(BlogEntry.class, id);

        try {
            em.getTransaction().begin();
            em.remove(be);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return be;
    }

    //edit
    public BlogEntry editBlogEntry(int id, Date d, String content) {
        EntityManager em = getEntityManager();
        BlogEntry be = em.find(BlogEntry.class, id);

        be.setDate(d);
        be.setContent(content);

        try {
            em.getTransaction().begin();
            em.remove(be);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return be;
    }

    // Get all 
    public List getAllBlogEntries() {
        EntityManager em = getEntityManager();
        try {
            List list =  em.createQuery("SELECT b FROM BlogEntry b", BlogEntry.class).getResultList();
            return list;
        } finally {
            em.close();
        }
    }

    public List getAllBlogEntriesFromUser(int id /*String content*/) {
        EntityManager em = getEntityManager();
        try {
            List<BlogEntry> list = em.createQuery("SELECT b FROM BlogEntry b WHERE b.u LIKE '" + id /*content*/ + "'" , BlogEntry.class).getResultList();
            return list;
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
