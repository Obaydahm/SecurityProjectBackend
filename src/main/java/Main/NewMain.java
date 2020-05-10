/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import utils.EMF_Creator;

/**
 *
 * @author Ludvig
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/sec",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    
    public static void main(String[] args) {
        Persistence.generateSchema("pu", null);
    }

}
