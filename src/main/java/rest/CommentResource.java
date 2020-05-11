/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import DTO.CommentDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.CommentFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

/**
 *
 * @author Henrik
 */
@Path("comment")
public class CommentResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/sec",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);

    private static final CommentFacade FACADE = CommentFacade.getCommentFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    // Create a Comment
    @Path("create")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String createComment(CommentDTO commentDTO) {
        FACADE.addComment(commentDTO);
        return "{\"msg\":\"Comment created\"}";
    }

    // Find a Comment
    @Path("id/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public CommentDTO findComment(@PathParam("id") Long id) {
        CommentDTO c = FACADE.getComments(id);
        return c;
    }
    
    // Get all Comments
    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<CommentDTO> getAllComments(){
        return FACADE.getAllComments().getAll();
    }
    
    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getCommentCount(){
        long count = FACADE.getCommentCount();
        return "{\"count\":"+count+"}";
    }
    

}
