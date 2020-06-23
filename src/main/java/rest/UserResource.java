package rest;

import DTO.DosObject;
import DTO.UserDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import entities.BlogEntry;
import entities.Comment;
import entities.User;
import exceptions.GenericExceptionMapper;
import exceptions.InvalidInputException;
import exceptions.NotFoundException;
import exceptions.UsernameExistsException;
import exceptions.AuthenticationException;
import exceptions.UserBlockedException;
import facades.UserFacade;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import utils.EMF_Creator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import security.SharedSecret;

//Todo Remove or change relevant parts before ACTUAL use
@Path("user")
public class UserResource {
    private static HashMap<String, DosObject> LOGIN_REQS = new HashMap<>();
    private static HashMap<String, Long> BLOCKED_USERS = new HashMap<>();
    private static final long BLOCK_TIME = 60000; // 1 minute
    
    private static final int TOKEN_EXPIRE_TIME = 1000 * 60 * 30; //30 min
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
    
    private boolean dosDetect(String username){
        //return true = brugeren er blokeret
        //return false = brugeren er ikke blokeret
        //vi bruger boolean'en som returnere af denne metode i login endpointet - linje 259
        
        final Long currentTime = new Date().getTime();
        System.out.println(LOGIN_REQS);
        System.out.println(BLOCKED_USERS);
        
        //tjekker om brugeren er blokeret
        if(BLOCKED_USERS.containsKey(username)){
            //regner ud hvor langtid der er tilbage af blokeringen
            Long diff = BLOCKED_USERS.get(username) - currentTime;
            
            //hvis blokeringens tiden er overstået, fjerne vi brugeren fra vores maps, så brugeren kan logge på igen
            if(diff <= 0){
                LOGIN_REQS.remove(username);
                BLOCKED_USERS.remove(username);
                System.out.println("Removed from Blocked: " + username);
                return false;
            }else{
                //hvis blokeringstiden ikke er overstået, så returnere vi true
                System.out.println("/////////////////////////////");
                System.out.println("User still blocked: " + username);
                System.out.println(Math.abs((diff / 1000)) + " seconds left");
                System.out.println("/////////////////////////////");
                return true;
            }
        }
        
        // Tjekker om vi registreret login på brugeren
        if(LOGIN_REQS.containsKey(username)){
            //Vi har registreret login på brugeren
            //Henter bruger DosObjektet fra vores login map "register"
            DosObject userDosObject = LOGIN_REQS.get(username);
           
            //regner ud hvor langtid der er gået siden det her request og det der blev lavet lige inden
            Long diff = currentTime - userDosObject.getTime();
            
            //hvis der er gået mere end 10sekunder, så ser vi det som forsøg på bruteforce/dos
            if(diff < 10000){
                System.out.println("Request made within 10 seconds");
                //vi øger antallet af forsøg med 1
                userDosObject.incrementCount();
                
                //Hvis der er blevet lavet mere end 3 forsøg, så blokerer vi brugeren, da det er et brute force
                if(userDosObject.getCount() >= 3){
                    System.out.println("Third request made - User now blocked for 1 minute: " + username);
                    BLOCKED_USERS.put(username, currentTime + BLOCK_TIME);
                    return true;
                }
            }else{
                //der er gået mere end 10 sekunder siden sidste request
                //nulstiller brugeren i vores login map "register"
                LOGIN_REQS.put(username, new DosObject(currentTime));
            }
        }else{
            //brugeren findes ikke i vores login-register, så vi tilføjer den
            LOGIN_REQS.put(username, new DosObject(currentTime));
        }
        return false;
    }
    
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
    public String createUser(String json) throws UsernameExistsException, InvalidInputException {
        JsonObject jsonObject = GSON.fromJson(json, JsonObject.class);
        String userName = jsonObject.get("userName").getAsString();
        String password = jsonObject.get("password").getAsString();
        
        if(userName.isEmpty() || userName == null) throw new InvalidInputException("You must enter a username.");        
        if(password.isEmpty() || password == null) throw new InvalidInputException("You must enter a password.");

        
        UserDTO userDTO = new UserDTO(jsonObject.get("userName").getAsString(), "user");
        FACADE.addUser(userDTO, password);
        return "{\"msg\":\"Login created\"}";
    }

    // Delete user
    @Path("{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public UserDTO userDTO(@PathParam("id") int id) throws NotFoundException {
        UserDTO deletedUser = FACADE.remove(id);
        return deletedUser;
    }

    // Find a User
    @Path("id/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public UserDTO findUser(@PathParam("id") int id) throws NotFoundException {
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
    public String login(String jsonString) throws AuthenticationException, UserBlockedException {
        JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
        String username = json.get("userName").getAsString();
        String password = json.get("password").getAsString();
        
        boolean dosDetected = dosDetect(username);
        //kast UserBlockedException hvis brugeren er blokeret.
        //status kode 403 - forbidden
        if(dosDetected) throw new UserBlockedException();
        
        try {
            User user = FACADE.getVerifiedUser(username, password);
            String token = createToken(username, user.getRole());
            
            JsonObject responseJson = new JsonObject();
            responseJson.addProperty("userName", username);
            responseJson.addProperty("token", token);
            responseJson.addProperty("role", user.getRole());
            
            return GSON.toJson(responseJson);
            
        } catch (JOSEException | AuthenticationException e) {
            if (e instanceof AuthenticationException) {
                throw (AuthenticationException) e;
            }
            Logger.getLogger(GenericExceptionMapper.class.getName()).log(Level.SEVERE, null, e);
        }
        throw new AuthenticationException();
    }
    
    private String createToken(String userName, String role) throws JOSEException {

        StringBuilder res = new StringBuilder();
        String issuer = "4SEMEXAM";

        JWSSigner signer = new MACSigner(SharedSecret.getSharedKey());
        Date date = new Date();
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(userName)
                .claim("username", userName)
                .claim("role", role)
                .claim("issuer", issuer)
                .issueTime(date)
                .expirationTime(new Date(date.getTime() + TOKEN_EXPIRE_TIME))
                .build();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        signedJWT.sign(signer);
        return signedJWT.serialize();

    }
    
    @Path("validatetoken")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Response validateToken(@HeaderParam("x-access-token") String token) throws ParseException, JOSEException, AuthenticationException {
        System.out.println(token);
        int status = FACADE.validateToken(token);
        return Response.status(200)
                .entity("{\"code\":200, \"message\": \"Token valid\"}")
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
    
}
