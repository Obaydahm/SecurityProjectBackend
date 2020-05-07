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

/**
 *
 * @author Ludvig
 */
@Entity
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private BlogEntry be;
    private User u;
    //private String content;

    public Comment() {
    }

    public Comment(int id, BlogEntry be, User u) {
        this.id = id;
        this.be = be;
        this.u = u;
    }

    public BlogEntry getBe() {
        return be;
    }

    public User getU() {
        return u;
    }

    public void setBe(BlogEntry be) {
        this.be = be;
    }

    public void setU(User u) {
        this.u = u;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
