package rest;

import DTO.UserDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import utils.EMF_Creator;
import facades.UserFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
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
    private static final UserFacade FACADE = UserFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

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

    // Find a User
    @Path("id/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public UserDTO findUser(@PathParam("id") Long id) {
        UserDTO u = FACADE.getUser(id);
        return u;
    }

    // Get all Users
    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<UserDTO> getAllUsers(){
        return FACADE.getAllUsers().getAll();
    }
            
    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getUserCount() {
        long count = FACADE.getUserCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }

}
