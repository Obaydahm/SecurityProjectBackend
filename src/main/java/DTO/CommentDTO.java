/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import entities.BlogEntry;
import entities.Comment;
import entities.User;

/**
 *
 * @author Ludvig
 */
public class CommentDTO {
    
    private int id;
    private BlogEntry be;
    private User u;
    private String content;

    public CommentDTO(Comment c) {
        this.id = c.getId();
        this.be = c.getBe();
        this.u = c.getU();
        this.content = be.getContent();
    }
    
    
}
