package facades;

import DTO.UserDTO;
import entities.User;
import exceptions.NotFoundException;
import exceptions.UsernameExistsException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import javax.naming.AuthenticationException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class UserFacade {

    private static UserFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private UserFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    // Create
    public UserDTO addUser(UserDTO u, String password) throws UsernameExistsException{
        EntityManager em = getEntityManager();
        
        User user = new User(u, password);
        
        try{
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        }catch(Exception e){
            if(e.getMessage().contains("Duplicate") && e.getMessage().contains("USERNAME")) throw new UsernameExistsException(u.getUserName());
        } finally {
            em.close();
        }
        return new UserDTO(user);
    }
    
    
    
    // Find a User
    public UserDTO getUser(int userId) throws NotFoundException{
        EntityManager em = getEntityManager();
        User user = em.find(User.class, userId);
        if(user == null) throw new NotFoundException("User not found.");
        return new UserDTO(user);
    }
        
   // Get all Users
    public List<UserDTO> getAllUsers(){
        EntityManager em = getEntityManager();
        try {
            List<UserDTO> users = em.createQuery("SELECT new DTO.UserDTO(u) FROM User U", UserDTO.class).getResultList();
            return users;
        } finally {
            em.close();
        }
    }
        
    public UserDTO remove(int id) throws NotFoundException{
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, id);
            if(user == null) throw new NotFoundException("User not found");
            em.remove(user);
            em.getTransaction().commit();
            return new UserDTO(user);
        } finally {
            em.close();
        }
    }
    
    
    // No of Users
    public long getUserCount(){
        EntityManager em = emf.createEntityManager();
        try{
            long userCount = (long)em.createQuery("SELECT COUNT(u) FROM User1 u").getSingleResult();
            return userCount;
        }finally{  
            em.close();
        }
        
    }
    
    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User u1;
        try {
            em.getTransaction().begin();
            u1 = em.createQuery("SELECT a FROM User a WHERE a.userName=:name", User.class).setParameter("name", username).getSingleResult();
            if (u1 == null || !u1.verifyPassword(password, u1.getPassword())) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return u1;

    }


}


