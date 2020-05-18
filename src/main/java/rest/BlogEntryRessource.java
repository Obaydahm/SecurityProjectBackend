package rest;

import DTO.BlogEntryDTO;
import DTO.UserDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.BlogEntry;
import entities.User1;
import utils.EMF_Creator;
import facades.BlogEntryFacade;
import java.util.List;
import javax.naming.AuthenticationException;
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
public class BlogEntryRessource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/sec",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);

    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    private static final BlogEntryFacade FACADE = BlogEntryFacade.getBlogEntryFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @POST
    @Path("/add")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String postBlogEntry(String blogEntry) {
        BlogEntryDTO blogEntryDTO = GSON.fromJson(blogEntry, BlogEntryDTO.class); //makes PersonDTO object from PersonDTO json string
        BlogEntry added = FACADE.addBlogEntry(blogEntryDTO.getContent(), blogEntryDTO.getD(), blogEntryDTO.getU().getId());
        return GSON.toJson(new BlogEntryDTO(added));//makes and returns PersonDTO from a person object, which has id
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public BlogEntry getBlogEntryById(@PathParam("id") int id) throws Exception {
        BlogEntry be = FACADE.getBlogEntry(id);
        if (be == null) {
            throw new Exception("No information with provided id found");
        }
        return be;
    }

    @Path("delete/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String deleteBlogEntry(@PathParam("id") int id) throws Exception {
        BlogEntry be = FACADE.deleteBlogEntry(id);

        if (be == null) {
            throw new Exception("No information found with provided id");
        }
        return be.getId() + " Du slettede denne information";
    }

    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllBlogEntries() {
        List<BlogEntry> allBlogEntry = FACADE.getAllBlogEntries();
        BlogEntryDTO allBlogEntryDTOs = new BlogEntryDTO(allBlogEntry);

        return GSON.toJson(allBlogEntryDTOs.all);
    }
}
