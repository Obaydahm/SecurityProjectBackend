package rest;

import DTO.BlogEntryDTO;
import DTO.CommentDTO;
import DTO.UserDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.BlogEntry;
import entities.User;
import utils.EMF_Creator;
import java.util.List;
import javax.naming.AuthenticationException;
import entities.BlogEntry;
import entities.Comment;
import exceptions.ClientException;
import exceptions.InvalidInputException;
import exceptions.NotFoundException;
import utils.EMF_Creator;
import facades.BlogFacade;
import java.util.Date;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("blogentry")
public class BlogEntryResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);

    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    private static final BlogFacade FACADE = BlogFacade.getBlogFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    
    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllBlogEntries() {
        return GSON.toJson(FACADE.getAllBlogEntries());
    }
    
    @GET
    @Path("getblogentriesbyuser/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getBlogEntriesByUser(@PathParam("id") int id) throws NotFoundException {
        List<BlogEntryDTO> be = FACADE.getBlogEntriesByUser(id);
        if (be == null) {
            return "{\"status\": 404, \"msg\":\"No information with provided id found\"}";
        }
        return GSON.toJson(be);
    }
    
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getBlogEntryById(@PathParam("id") int id) throws NotFoundException {
        BlogEntryDTO be = FACADE.getBlogEntryById(id);
        return GSON.toJson(be);
    }
    
    @POST
    @Path("add")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addBlogEntry(String json) throws ClientException {
        JsonObject blogEntryJson = GSON.fromJson(json, JsonObject.class);
        
        String title = blogEntryJson.get("title").getAsString();
        if(title == null || title.isEmpty()) throw new InvalidInputException("You must enter a title.");
        
        String content = blogEntryJson.get("content").getAsString();
        if(content == null || content.isEmpty()) throw new InvalidInputException("You're not allowed to create blog entries without content.");
        
        int userId;
        try{
            userId = blogEntryJson.get("userId").getAsInt();
        }catch(Exception e){ throw new InvalidInputException("An error has occurred. Try again."); }
        
        BlogEntryDTO blogEntry = FACADE.addBlogEntry(title, content, userId);
        return GSON.toJson(blogEntry);
    }
    
    @PUT
    @Path("edit")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String editBlogEntry(String json) throws NotFoundException, InvalidInputException {
        JsonObject blogEntryJson = GSON.fromJson(json, JsonObject.class);
        String title = blogEntryJson.get("title").getAsString();
        if(title == null || title.isEmpty()) throw new InvalidInputException("You must enter a title.");
        
        String content = blogEntryJson.get("content").getAsString();
        if(content == null || content.isEmpty()) throw new InvalidInputException("Blog entries has to have content.");
        
        int id;
        try{
            id = blogEntryJson.get("id").getAsInt();
        }catch(Exception e){ throw new InvalidInputException("An error has occurred. Try again."); }
        
        BlogEntryDTO blogEntry = FACADE.editBlogEntry(title, content, id);
        return GSON.toJson(blogEntry);
    }
    
    @DELETE
    @Path("delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public String deleteBlogEntry(@PathParam("id") int id) throws NotFoundException {
        BlogEntry be = FACADE.deleteBlogEntry(id);
        return "{\"status\": 200, \"msg\":\"OK\"}";
    }
    
    @POST
    @Path("addcomment")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addComment(String json) throws NotFoundException, InvalidInputException {
        JsonObject commentJson = GSON.fromJson(json, JsonObject.class);
        int blogEntryId;
        int userId;
        try{
            blogEntryId  = commentJson.get("blogEntryId").getAsInt();
            userId = commentJson.get("userId").getAsInt();
        }catch(Exception e){ throw new InvalidInputException("An error has occurred. Try again."); }
        
        String content = commentJson.get("content").getAsString();
        if(content == null || content.isEmpty()) throw new InvalidInputException("Comments must have content.");
        
        return GSON.toJson(FACADE.addComment(blogEntryId, userId, content));
    }
    
    @DELETE
    @Path("deletecomment/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public String deleteComment(@PathParam("id") int id) throws NotFoundException {
        Comment c = FACADE.deleteComment(id); //        
        return "{\"status\": 200, \"msg\":\"OK\"}";
    }
    
}
