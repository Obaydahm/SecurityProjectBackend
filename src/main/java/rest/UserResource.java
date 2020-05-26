package rest;

import DTO.UserDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.BlogEntry;
import entities.Comment;
import entities.User;
import facades.UserFacade;
import java.util.Date;
import utils.EMF_Creator;
import java.util.List;
import javax.naming.AuthenticationException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("user")
public class UserResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/sec",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);

    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    private static final UserFacade FACADE = UserFacade.getUserFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final EntityManager em = EMF.createEntityManager();
    
    @GET
    @Path("populate")
    @Produces({MediaType.APPLICATION_JSON})
    public String populate() {
        Persistence.generateSchema("pu", null);
        if(FACADE.getAllUsers().size() > 0) return "not necessary";
        
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
        
        return "done";
    }
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    // Create User
    @Path("create")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String createUser(UserDTO userDTO) {
        FACADE.addUser(userDTO);
        return "{\"msg\":\"Login created\"}";
    }

    // Delete user
    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public UserDTO userDTO(@PathParam("id") int id) {
        UserDTO deletedUser = FACADE.remove(id);
        return deletedUser;
    }

    // Find a User
    @Path("id/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public UserDTO findUser(@PathParam("id") int id) {
        UserDTO u = FACADE.getUser(id);
        return u;
    }

    // Get all Users
    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<UserDTO> getAllUsers() {
        return (List<UserDTO>) FACADE.getAllUsers();//.getAll();
    }

    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getUserCount() {
        long count = FACADE.getUserCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }

    @Path("login")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String login(String jsonString) throws AuthenticationException {
        JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
        String username = json.get("username").getAsString();
        String password = json.get("password").getAsString();
        try {
            FACADE.getVeryfiedUser(username, password);
        } catch (AuthenticationException e) {
            e.getMessage();
            return "wrong password or username";

        }
        return "Login succesful";
    }
}
