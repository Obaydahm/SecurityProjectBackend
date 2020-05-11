package facades;

import DTO.UserDTO;
import entities.User1;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
    public static UserFacade getFacadeExample(EntityManagerFactory _emf) {
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
    public UserDTO addUser(UserDTO u){
        EntityManager em = getEntityManager();
        
        User1 user1 = new User1();
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
        User1 userDTO = em.find(User1.class, user_id);
        return new UserDTO(userDTO);
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
}


