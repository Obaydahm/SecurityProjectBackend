/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import DTO.UserDTO;
import entities.BlogEntry;
import entities.Comment;
import entities.User;
import facades.FlawedBlogFacade;
//import facades.CommentFacade;
import facades.BlogFacade;
import facades.UserFacade;
import facades.FlawedUserFacade;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.AuthenticationException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.mindrot.jbcrypt.BCrypt;
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
            EMF_Creator.Strategy.CREATE); //DROP_AND_CREATE

    private static final FlawedBlogFacade FLAWED_BLOG_FACADE = FlawedBlogFacade.getFlawedBlogFacade(EMF);
    private static final BlogFacade BLOG_FACADE = BlogFacade.getBlogFacade(EMF);
    //private static final CommentFacade COMMENT_FACADE = CommentFacade.getCommentFacade(EMF);
    private static final FlawedUserFacade FLAWED_USER_FACADE = FlawedUserFacade.getFlawedUserFacade(EMF);
    private static final UserFacade USER_FACADE = UserFacade.getUserFacade(EMF);


    public static void main(String[] args) throws AuthenticationException {
        //Persistence.generateSchema("pu", null);
        EntityManager em = EMF.createEntityManager();

        //blog facade test
        //BLOG_FACADE.addBlogEntry("qwetfjg", new Date(1, 11, 2002), 3);
        //BLOG_FACADE.deleteBlogEntry(6);
        //List blogList = BLOG_FACADE.getAllBlogEntries();
        //System.out.println(blogList.get(2));
        //System.out.println(blogList.get(4));
        //List userBlogList = BLOG_FACADE.getAllBlogEntriesFromUser(1);
        //System.out.println(userBlogList.get(0));
        //System.out.println(userBlogList.get(1));
        
        //COMMENT_FACADE.deleteComment(7);
        //BLOG_FACADE.addBlogEntry("Hvorfor sker det?", "Jeg kan simpelthen ikke tro at det sker", 3);
        //BLOG_FACADE.addComment( 2, 4, "0 or 1=1 and username like('%admin%')");
        //User u = new User("DarkCrystalFan96", "user", "gelfling");
        //UserDTO uDTO = new UserDTO(u);
        //USER_FACADE.addUser(uDTO);

        //System.out.println(BCrypt.hashpw("ewegg", BCrypt.gensalt(12)));
        
        //System.out.println(USER_FACADE.getVeryfiedUser("DarkCrystalFan96", "gelfling").getUserName());
        //FLAWED_USER_FACADE.getVeryfiedUser("and or 1=1 and email like('%bender%')", "pwinef");
        
        //FLAWED_BLOG_FACADE.getBlogEntriesByUser("1 OR 1=1");
        
        /*
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
        } finally {
            em.close();
        }


        /* Ludvigs init
        BlogEntry be1 = new BlogEntry("Hej. Jeg er sej.", new Date(12, 4, 2200), u1);
        BlogEntry be2 = new BlogEntry("Whaaaat", new Date(3, 10, 1900), u2);
        BlogEntry be3 = new BlogEntry("Soief", new Date(22, 1, 2006), u3);
        BlogEntry be4 = new BlogEntry("WSor", new Date(30, 12, 2007), u1);
        BlogEntry be5 = new BlogEntry("W2ef", new Date(7, 5, 2009), u2);
        
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
         */
    }
}
