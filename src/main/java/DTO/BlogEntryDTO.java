/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import entities.BlogEntry;
import entities.User;
import java.util.Date;

/**
 *
 * @author Ludvig
 */
public class BlogEntryDTO {
    
    private int id;
    private User u;
    private Date d;
    private String content;

    public BlogEntryDTO(BlogEntry be) {
        this.id = be.getId();
        this.u = be.getUser();
        this.d = be.getDate();
        this.content = be.getContent();
    }
}
