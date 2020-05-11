/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import entities.BlogEntry;
import entities.User1;
import java.util.Date;

/**
 *
 * @author Ludvig
 */
public class BlogEntryDTO {
    
    private int id;
    private User1 u;
    private Date d;
    private String content;

    public BlogEntryDTO(BlogEntry be) {
        this.id = be.getId();
        this.d = be.getDate();
        this.content = be.getContent();
    }
}
