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
    private String userName;
    private String content;

    public CommentDTO(Comment c) {
        this.id = c.getId();
        this.userName = c.getUser().getUserName();
        this.content = c.getContent();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CommentDTO{" + "id=" + id + ", userName=" + userName + ", content=" + content + '}';
    }
    
    
    
}
