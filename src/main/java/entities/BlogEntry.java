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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Ludvig
 */
@Entity
public class BlogEntry implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @OneToOne
    private User1 u;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date d;
    private String content;
    
    public BlogEntry(){}


   

    public BlogEntry(String content, Date d, User1 u) {
        this.content = content;
        this.d = d;
        this.u = u;
    }
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User1 getUser() {
        return u;
    }

    public Date getDate() {
        return d;
    }

    public void setUser(User1 u) {
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
