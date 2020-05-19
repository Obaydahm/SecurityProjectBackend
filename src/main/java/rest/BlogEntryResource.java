package rest;

import DTO.BlogEntryDTO;
import DTO.UserDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.BlogEntry;
import entities.User;
import utils.EMF_Creator;
import facades.BlogEntryFacade;
import java.util.List;
import javax.naming.AuthenticationException;
import entities.BlogEntry;
import utils.EMF_Creator;
import facades.BlogEntryFacade;
import facades.BlogFacade;
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
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("blogentry")
public class BlogEntryResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/sec",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);

    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
//    private static final BlogEntryFacade FACADE = BlogEntryFacade.getBlogEntryFacade(EMF);
    private static final BlogFacade FACADE = BlogFacade.getBlogFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllBlogEntries() {
        List<BlogEntryDTO> allBlogEntries = FACADE.getAllBlogEntries();
        return GSON.toJson(allBlogEntries);
    }
    
    @GET
    @Path("/getblogentriesbyuser/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getBlogEntriesByUser(@PathParam("id") int id) throws Exception {
        List<BlogEntryDTO> be = FACADE.getBlogEntriesByUser(id);
        if (be == null) {
            return "{\"status\": 404, \"msg\":\"No information with provided id found\"}";
        }
        return GSON.toJson(be);
    }
    
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getBlogEntryById(@PathParam("id") int id) throws Exception {
        BlogEntry be = FACADE.getBlogEntryById(id);
        if (be == null) {
            return "{\"status\": 404, \"msg\":\"No information with provided id found\"}";
        }
        return GSON.toJson(new BlogEntryDTO(be));
    }
    
    @POST
    @Path("/add")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String postBlogEntry(String json) {
        JsonObject blogEntryJson = GSON.fromJson(json, JsonObject.class);
        BlogEntryDTO blogEntry = FACADE.addBlogEntry(blogEntryJson.get("title").getAsString(), blogEntryJson.get("content").getAsString(), blogEntryJson.get("userId").getAsInt());
        return GSON.toJson(blogEntry);
    }

    @DELETE
    @Path("delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public String deleteBlogEntry(@PathParam("id") int id) throws Exception {
        BlogEntry be = FACADE.deleteBlogEntry(id);

        if (be == null) {
            return "{\"status\": 404, \"msg\":\"No information with provided id found\"}";
        }
        
        return "{\"status\": 200, \"msg\":\"Blog entry with id "+be.getId()+" has been deleted\"}";
    }
    
//    // Create BlogEntry
//    @Path("create")
//    @POST
//    @Produces({MediaType.APPLICATION_JSON})
//    @Consumes({MediaType.APPLICATION_JSON})
//    public String createBlogEntry(BlogEntryDTO blogEntryDTO) {
//        FACADE.addBlogEntry(blogEntryDTO);
//        return "{\"msg\":\"Login created\"}";
//    }
//
//    // Delete BlogEntry
//    @Path("/{id}")
//    @DELETE   
//    @Produces(MediaType.APPLICATION_JSON)
//    public BlogEntryDTO blogEntryDTO(@PathParam("id") Long id){
//        BlogEntryDTO deletedBlogEntry = FACADE.remove(id);
//        return deletedBlogEntry;
//    }
//    
//    // Find a BlogEntry
//    @Path("id/{id}")
//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    public BlogEntryDTO findUser(@PathParam("id") Long id) {
//        BlogEntryDTO b = FACADE.getBlogEntry(id);
//        return b;
//    }
//
//    // Get all BlogEntries
//    @Path("all")
//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    public List<UserDTO> getAllBlogEntries(){
//        return (List<UserDTO>) FACADE.getAllBlogEntries();//.getAll();
//    }
//            
//    // Count BlogEntries 
//    @Path("count")
//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    public String getBlogEntries(){
//        long count = FACADE.getBlogEntryCount();
//        return "{\"count\":"+count+"}";
//    }
    
    
    
}
