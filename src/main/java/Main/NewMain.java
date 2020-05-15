/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import entities.BlogEntry;
import entities.Comment;
import entities.User1;
import facades.UserFacade;
import java.sql.Date;
import javax.naming.AuthenticationException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import utils.EMF_Creator;

/**
 *
 * @author Ludvig
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/sec",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    
    public static void main(String[] args) throws AuthenticationException {
        //Persistence.generateSchema("pu", null);
       /* EntityManager em = EMF.createEntityManager();
        
        User1 u1 = new User1("darkCrystalFan92", "user", "pqwoefm32");
        User1 u2 = new User1("TheSnarx", "user", "+wef32+0");
        User1 u3 = new User1("AndersAnd", "user", "ipwef23f");
        User1 admin = new User1("Admin", "admin", "admin");
        
        BlogEntry be1 = new BlogEntry("Hej. Jeg er sej.", new Date(12, 4, 2200));
        BlogEntry be2 = new BlogEntry("Whaaaat", new Date(3, 10, 1900));
        BlogEntry be3 = new BlogEntry("Soief", new Date(22, 1, 2006));
        BlogEntry be4 = new BlogEntry("WSor", new Date(30, 12, 2007));
        BlogEntry be5 = new BlogEntry("W2ef", new Date(7, 5, 2009));
        
        Comment c1 = new Comment("Hi");
        Comment c2 = new Comment("Great blog");
        Comment c3 = new Comment("you suck");
        Comment c4 = new Comment("LMFAO xd");
        Comment c5 = new Comment("tyranosaurus isn't real");
        Comment c6 = new Comment("yup");
        Comment c7 = new Comment("The Minoans");
        Comment c8 = new Comment("How did you do it?");
        Comment c9 = new Comment("Is it real");
        Comment c10 = new Comment("whyyyyyyy");
        
        be1.setUser(u1);
        be2.setUser(u2);
        be3.setUser(u3);
        be4.setUser(u2);
        be5.setUser(u1);
        
        c1.setUser(u1);
        c1.setBlogEntry(be1);
        c2.setUser(u2);
        c2.setBlogEntry(be2);
        c3.setUser(u3);
        c3.setBlogEntry(be3);
        c4.setUser(u1);
        c4.setBlogEntry(be4);
        c5.setUser(u2);
        c5.setBlogEntry(be5);
        c6.setUser(u3);
        c6.setBlogEntry(be1);
        c7.setUser(u1);
        c7.setBlogEntry(be2);
        c8.setUser(u2);
        c8.setBlogEntry(be3);
        c9.setUser(u3);
        c9.setBlogEntry(be4);
        c10.setUser(admin);
        c10.setBlogEntry(be5);
        
        try {
            em.getTransaction().begin();
            
            em.persist(u1);
            em.persist(u2);
            em.persist(u3);
            em.persist(admin);
            em.persist(be1);
            em.persist(be2);
            em.persist(be3);
            em.persist(be4);
            em.persist(be5);
            em.persist(c1);
            em.persist(c2);
            em.persist(c3);
            em.persist(c4);
            em.persist(c5);
            em.persist(c6);
            em.persist(c7);
            em.persist(c8);
            em.persist(c9);
            em.persist(c10);          
            
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
*/
 
       
}
}
        
