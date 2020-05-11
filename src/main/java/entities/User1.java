/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Ludvig
 */
@Entity
public class User1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userName;
    private String password;
    private String role1;
    
    public User1() {
    }
    
    public User1(String userName, String role1, String userPass) {
        this.userName = userName;
        this.role1 = role1;
        this.password = BCrypt.hashpw(userPass, BCrypt.gensalt(12));
    }
    
    public boolean verifyPassword(String plainText, String pw) {
        return (BCrypt.checkpw(plainText, password));
    }
    
    //this is for the DTO class
    //but does it make sense to have this one?
    public String getPassword(){
        return password;
    }

    public void setRole(String role1) {
        this.role1 = role1;
    }

    public String getRole() {
        return role1;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (id != null ? id.hashCode() : 0);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof User)) {
//            return false;
//        }
//        User other = (User) object;
//        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "entities.User[ id=" + id + " ]";
//    }
//    
}