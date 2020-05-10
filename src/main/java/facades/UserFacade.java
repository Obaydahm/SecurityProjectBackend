package facades;

import entities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
    
    //TODO Remove/Change this before use
    public long getUserCount(){
        EntityManager em = emf.createEntityManager();
        try{
            long userCount = (long)em.createQuery("SELECT COUNT(r) FROM User r").getSingleResult();
            return userCount;
        }finally{  
            em.close();
        }
        
    }


}
