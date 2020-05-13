package rest;

import DTO.BlogEntryDTO;
import DTO.UserDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.BlogEntry;
import utils.EMF_Creator;
import facades.BlogEntryFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("blogentry")
public class BlogEntryRessource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/sec",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
    
    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    
    private static final BlogEntryFacade FACADE =  BlogEntryFacade.getBlogEntryFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    
    // Create BlogEntry
    @Path("create")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String createBlogEntry(BlogEntryDTO blogEntryDTO) {
        FACADE.addBlogEntry(blogEntryDTO);
        return "{\"msg\":\"Login created\"}";
    }

    // Delete BlogEntry
    @Path("/{id}")
    @DELETE   
    @Produces(MediaType.APPLICATION_JSON)
    public BlogEntryDTO blogEntryDTO(@PathParam("id") Long id){
        BlogEntryDTO deletedBlogEntry = FACADE.remove(id);
        return deletedBlogEntry;
    }
    
    // Find a BlogEntry
    @Path("id/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public BlogEntryDTO findUser(@PathParam("id") Long id) {
        BlogEntryDTO b = FACADE.getBlogEntry(id);
        return b;
    }

    // Get all BlogEntries
    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<UserDTO> getAllBlogEntries(){
        return (List<UserDTO>) FACADE.getAllBlogEntries();//.getAll();
    }
            
    // Count BlogEntries 
    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getBlogEntries(){
        long count = FACADE.getBlogEntryCount();
        return "{\"count\":"+count+"}";
    }
    
    
    
}