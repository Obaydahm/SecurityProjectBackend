/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Ludvig
 */
@Entity
public class BlogEntry implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private User u;
    private Date d;
    //private String content;
    
    public BlogEntry(){}

    public BlogEntry(User u, Date d) {
        this.u = u;
        this.d = d;
    }

    public User getUser() {
        return u;
    }

    public Date getDate() {
        return d;
    }

    public void setUser(User u) {
        this.u = u;
    }

    public void setDate(Date d) {
        this.d = d;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "entities.BlogEntry[ id=" + id + " ]";
    }
    
}
