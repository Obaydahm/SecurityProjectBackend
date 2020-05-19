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
import entities.User;
import java.sql.Date;
import java.util.ArrayList;
import entities.Comment;
import entities.User;
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
    public BlogEntry addBlogEntry(String title, String content, int userId) {
        EntityManager em = getEntityManager();

        User user = em.find(User.class, userId); //this is null, because you create a user with no generated id
        //System.out.println(u1.getId());

        BlogEntry be = new BlogEntry(title, content, user, null);
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
    public BlogEntry editBlogEntry(BlogEntry updatedBlogEntry) {
        EntityManager em = getEntityManager();
        BlogEntry oldBlogEntry = getBlogEntry(updatedBlogEntry.getId());
        if(oldBlogEntry == null) return null;
        
        try{
            em.getTransaction().begin();
            em.merge(updatedBlogEntry);
            em.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            em.close();
        }
        
        
        
        return updatedBlogEntry;
    }

    // Get all 
    public List<BlogEntry> getAllBlogEntries() {
        EntityManager em = getEntityManager();
        try {
            List<BlogEntry> list =  em.createQuery("SELECT b FROM BlogEntry b", BlogEntry.class).getResultList();
            return list;
        } finally {
            em.close();
        }
    }

    /*
    public List getAllBlogEntriesFromUser(int id) {
        EntityManager em = getEntityManager();
        try {
            List<BlogEntry> list = em.createQuery("SELECT b FROM BlogEntry b WHERE b.u = '" + id + "'" , BlogEntry.class).getResultList();
            return list;

        }finally {
            em.close();
        }
    }
    */
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
