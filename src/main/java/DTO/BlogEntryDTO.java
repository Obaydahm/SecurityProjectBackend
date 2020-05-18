/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import entities.BlogEntry;
import entities.User1;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
//import java.util.Date;
//import java.SQL.Date;

/**
 *
 * @author Ludvig
 */
public class BlogEntryDTO {

    private int id;
    private User1 u;
    private Date d;
    private String content;

    public List<BlogEntryDTO> all = new ArrayList();

    public BlogEntryDTO(List<BlogEntry> blogEntryEntities) {
        for (int i = 0; i < blogEntryEntities.size(); i++) {
            all.add(new BlogEntryDTO(blogEntryEntities.get(i)));
        }
    }

    public BlogEntryDTO(BlogEntry be) {
        this.id = be.getId();
        this.d = (Date) be.getDate();
        this.content = be.getContent();
        this.u = be.getUser();
    }

    public int getId() {
        return id;
    }

    public User1 getU() {
        return u;
    }

    public Date getD() {
        return d;
    }

    public String getContent() {
        return content;
    }

}
