/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTO.BlogEntryDTO;
import DTO.CommentDTO;
import entities.BlogEntry;
import entities.Comment;
import entities.User;
import exceptions.ClientException;
import exceptions.NotFoundException;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author Obaydah Mohamad
 */
public class BlogFacade {
    private static BlogFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private BlogFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static BlogFacade getBlogFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BlogFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public BlogEntryDTO getBlogEntryById(int id) throws NotFoundException{
        EntityManager em = emf.createEntityManager();
        try{
            BlogEntry blogEntry = em.find(BlogEntry.class, id);
            if(blogEntry == null) throw new NotFoundException("The requested blog entry was not found.");
            return new BlogEntryDTO(blogEntry);
        }finally{
            em.close();
        }
    }
    
    public List<BlogEntryDTO> getAllBlogEntries() {
        EntityManager em = getEntityManager();
        try {
            List<BlogEntryDTO> list =  em.createQuery("SELECT new DTO.BlogEntryDTO(b) FROM BlogEntry b", BlogEntryDTO.class).getResultList();
            return list;
        } finally {
            em.close();
        }
    }
    
    public List<BlogEntryDTO> getBlogEntriesByUser(int userId) throws NotFoundException {
        EntityManager em = getEntityManager();
        User user = em.find(User.class, userId);
        if(user == null) throw new NotFoundException("User not found."); //her bør der kastes user not found exception
        
        try {
            List<BlogEntryDTO> list =  em.createQuery("SELECT new DTO.BlogEntryDTO(b) FROM BlogEntry b WHERE b.user = :user", BlogEntryDTO.class).setParameter("user", user).getResultList();
            return list;
        } catch(Exception e) {
            return null;
        }finally {
            em.close();
        }
    }
    
    public BlogEntryDTO addBlogEntry(String title, String content, int userId) {
        EntityManager em = getEntityManager();

        User user = em.find(User.class, userId);
        BlogEntry be = new BlogEntry(title, content, user, new Date());

        try {
            em.getTransaction().begin();
            em.persist(be);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new BlogEntryDTO(be);
    }
    
    public BlogEntry deleteBlogEntry(int id) throws NotFoundException {
        EntityManager em = getEntityManager();
        BlogEntry be = em.find(BlogEntry.class, id);
        if(be == null) throw new NotFoundException("The requested blog entry was not found.");
        try {
            em.getTransaction().begin();
            em.remove(be);
            em.getTransaction().commit();
            return be;
        }finally {
            em.close();
        }
    }
    
    public BlogEntryDTO editBlogEntry(String title, String content, int blogEntryId) throws NotFoundException {
        EntityManager em = getEntityManager();
        BlogEntry blogEntry = em.find(BlogEntry.class, blogEntryId);
        if(blogEntry == null) throw new NotFoundException("The requested blog entry was not found.");
        blogEntry.setTitle(title);
        blogEntry.setContent(content);
        try{
            em.getTransaction().begin();
            em.merge(blogEntry);
            em.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            em.close();
        }
        
        return new BlogEntryDTO(blogEntry);
    }
    
        // Create 
    public CommentDTO addComment(int blogEntryId, int userId, String content) throws NotFoundException {
        EntityManager em = getEntityManager();

        BlogEntry be = em.find(BlogEntry.class, blogEntryId);
        if(be == null) throw new NotFoundException("The requested blog entry was not found.");
        
        User user = em.find(User.class, userId);
        if(user == null) throw new NotFoundException("User not found.");
        
        Comment c = new Comment(content, be, user);
        try {
            em.getTransaction().begin();
            em.persist(c);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new CommentDTO(c);
    }
    
    public Comment deleteComment(int id) throws NotFoundException{
        EntityManager em = getEntityManager();
        Comment c = em.find(Comment.class, id);
        if(c == null) throw new NotFoundException("Comment not found.");
        try {
            em.getTransaction().begin();
            em.remove(c);
            em.getTransaction().commit();
            return c;
        } finally {
            em.close();
        }
    }
    
    /*public static void main(String[] args) {
        EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
        BlogFacade bf = BlogFacade.getBlogFacade(EMF);
        
        //BlogEntry be = bf.getBlogEntryById(3);
        //System.out.println(be);
        
        //for(Comment c : be.getComments()){
        //    System.out.println(c);
        //}
        
        //for(BlogEntryDTO b : bf.getAllBlogEntries()){
        //    System.out.println(b);
        //}
        
        //bf.addBlogEntry("newEntry2", "This is a new entry", 2);
        //System.out.println(bf.deleteBlogEntry(6));
        
        //System.out.println(bf.editBlogEntry("editedTitle", "contentAlsoEdited", 2));
    }*/
}
