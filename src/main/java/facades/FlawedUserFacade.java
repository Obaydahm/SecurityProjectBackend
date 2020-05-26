package facades;

import DTO.UserDTO;
import entities.User;
import java.util.List;
import javax.naming.AuthenticationException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class FlawedUserFacade {

    private static FlawedUserFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private FlawedUserFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static FlawedUserFacade getFlawedUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new FlawedUserFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //impossible to do SQL injection on this or any other function in this class
    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User u1;
        try {
            em.getTransaction().begin();
            u1 = em.createQuery("SELECT a FROM User a WHERE a.userName=" + username, User.class).getSingleResult();
            if (u1 == null || !u1.verifyPassword(password, u1.getPassword())) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return u1;
    }

    /*
    ******************** is it possible to make SQL injection in these? *******************************
    
    // Create
    public UserDTO addUser(UserDTO u){
        EntityManager em = getEntityManager();
        
        User user1 = new User();
        user1.getUserName();
        user1.getPassword();
        user1.getRole();
        
        try{
            em.getTransaction().begin();
            em.persist(user1);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new UserDTO(user1);
    }
    
    
    
    // Find a User
    public UserDTO getUser(Long user_id){
        EntityManager em = getEntityManager();
        User userDTO = em.find(User.class, user_id);
        return new UserDTO(userDTO);
    }
        
   // Get all Users
    public UserDTO getAllUsers(){
        EntityManager em = getEntityManager();
        try {
            List<User> user = em.createQuery("SELECT u FROM User U", User.class).getResultList();
            return new UserDTO((User) user);
        } finally {
            em.close();
        }
    }
        
    public UserDTO remove(long id){
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            UserDTO u = em.find(UserDTO.class, id);
            em.remove(em.merge(u));
            em.getTransaction().commit();
            return u;
            
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
     */
}
