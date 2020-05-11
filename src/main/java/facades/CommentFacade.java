/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTO.CommentDTO;
import entities.Comment;
import java.util.List;
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

    // Create 
    public CommentDTO addComment(CommentDTO c){
        EntityManager em = getEntityManager();
        
        Comment co = new Comment();
        co.getBe();
        co.getU();
        co.getContent();
        
        try {
            em.getTransaction().begin();
            em.persist(co);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new CommentDTO(co);
    }
    
    // Find a Comment
    public CommentDTO getComments(Long comment_id){
        EntityManager em = getEntityManager();
        Comment commentDTO = em.find(Comment.class, comment_id);
        return new CommentDTO(commentDTO);
    }
    
    // Get all comments
    public CommentDTO getAllComments(){
        EntityManager em = getEntityManager();
        try{
            List<Comment> comment = em.createQuery("SELECT c FROM Comment c", Comment.class).getResultList();
            return new CommentDTO(comment);
        }finally {
            em.close();
        }
    }
    
    
    // No of Comments
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
