package exceptions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    @Override
    public Response toResponse(Throwable ex) {
        Logger.getLogger(GenericExceptionMapper.class.getName())
                .log(Level.SEVERE, null, ex);
                    
        ExceptionDTO err = new ExceptionDTO(500, "An error has occurred. Please try again.");
        if(ex instanceof ClientException){
            err = new ExceptionDTO(((ClientException) ex).getStatusCode(), ex.getMessage());
        }
        return Response
                .status(err.getCode())
                .entity(gson.toJson(err))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
