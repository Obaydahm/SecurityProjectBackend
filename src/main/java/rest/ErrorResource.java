package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.PersonNotFoundException;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

class Person {

    String name;
    String email;

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

}

@Path("error")
public class ErrorResource {

    private static Map<Integer, Person> persons = new HashMap();
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public ErrorResource() {
        if (persons.isEmpty()) {
            persons.put(1, new Person("Kurt Wonnegut", "a@b.dk"));
            persons.put(2, new Person("Hanne Wonnegut", "aa@b.dk"));
        }
    }

    /**
     *
     * @param id
     * @return
     * @throws PersonNotFoundException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public String getJson(@PathParam("id") int id) throws PersonNotFoundException{
        Person found = persons.get(id);
        if (found == null){
        throw new PersonNotFoundException("Person with provided Id not found");
        }            
        return gson.toJson(found);

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String getJson() {
        return gson.toJson(persons.values());
    }

}
