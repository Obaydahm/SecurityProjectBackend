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
import javax.persistence.OneToOne;

/**
 *
 * @author Ludvig
 */
@Entity
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private BlogEntry be;

    @OneToOne
    private User1 u;

    private String content;

    public Comment() {
    }

    public Comment(int id, BlogEntry be, User1 u) {
        this.id = id;
        this.be = be;
        this.u = u;
    }

    public Comment(String content) {
        this.content = content;

    }

    public BlogEntry getBe() {
        return be;
    }

    public User1 getU() {
        return u;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setBlogEntry(BlogEntry be) {
        this.be = be;
    }

    public void setUser(User1 u) {
        this.u = u;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
