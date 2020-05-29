/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.BlogEntry;
import entities.Comment;
import entities.User;
import facades.UserFacade;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author Obaydah Mohamad
 */
public class Main2 {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/sec",
            "dev",
            "ax2",
            EMF_Creator.Strategy.DROP_AND_CREATE);

    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    private static final UserFacade FACADE = UserFacade.getUserFacade(EMF);
    private static final EntityManager em = EMF.createEntityManager();
    public static void main(String[] args) {
        User u1 = new User("smollen", "user", "ewegg");
        User u2 = new User("TheSnarx", "user", "+wef32+0");
        User u3 = new User("AndersAnd", "user", "ipwef23f");
        User admin = new User("Admin", "admin", "admin");

        BlogEntry be1 = new BlogEntry("How to bla bla bla", "Just do bla bla bla", u1, new Date());
        BlogEntry be2 = new BlogEntry("How to bake a brownie cake", "Just bake it", u1, new Date());
        BlogEntry be3 = new BlogEntry("Learn React", "Just google how to react", u2, new Date());
        BlogEntry be4 = new BlogEntry("How to bla bla bla", "Just do bla bla bla", u3, new Date());
        BlogEntry be5 = new BlogEntry("What you need to know before buying a car", "The car has to have 4 wheelsssss", admin, new Date());

        Comment c1 = new Comment("Hello", be1, u1);
        Comment c2 = new Comment("Hi", be1, u1);
        Comment c3 = new Comment("Howdy", be1, u1);
        Comment c4 = new Comment("afsdgfdg", be2, u3);
        Comment c5 = new Comment("Hisdgdfg", be2, u2);
        Comment c6 = new Comment("fgfghfghfgh", be3, u3);
        Comment c7 = new Comment("dfgdfg", be3, u1);
        Comment c8 = new Comment("nice post", be4, admin);
        
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

            em.getTransaction().commit();
            System.out.println("done");
        } catch(Exception e){
            e.printStackTrace();
        }finally {
            em.close();
        }
        
        
    }
    
            
}
