package facades;

import utils.EMF_Creator;
import entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class UserFacadeTest {

    private static EntityManagerFactory EMF;
    private static UserFacade FACADE;
    private static List<User> USERS = new ArrayList();

    public UserFacadeTest() {
    }

    @BeforeAll
    public static void setUpClassV2(){
        EMF = EMF_Creator.createEntityManagerFactory(DbSelector.TEST,Strategy.DROP_AND_CREATE);
        FACADE = UserFacade.getUserFacade(EMF);
    }
    

    @BeforeEach
    public void setUp() {
        EntityManager em = EMF.createEntityManager();
        User f1 = new User();
        User f2 = new User();
        
        USERS.add(f1);
        USERS.add(f2);
        
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Facade.deleteAllRows").executeUpdate();
            em.persist(f1);
            em.persist(f2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
//    @AfterEach
//    public void tearDown() {
//        Remove any data after each test was run
//    }

    @Test
    public void testAFacadeMethod() {
        int exp = USERS.size();
        
        int act = FACADE.getAllUsers().size();
        assertEquals(exp, act);
    }

}
