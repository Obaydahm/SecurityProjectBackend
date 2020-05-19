/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import entities.BlogEntry;
import entities.Comment;
import entities.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import java.util.Date;
//import java.SQL.Date;

/**
 *
 * @author Ludvig
 */
public class BlogEntryDTO {

    private int id;
    private UserDTO user;
    private Date dateOfCreation;
    private String title;
    private String content;
    private List<CommentDTO> comments;

    public BlogEntryDTO(BlogEntry be) {
        this.id = be.getId();
        this.dateOfCreation = be.getDateOfCreation();
        this.title = be.getTitle();
        this.content = be.getContent();
        this.user = new UserDTO(be.getUser());
        this.comments = new ArrayList<>();
        for(Comment c : be.getComments()){
            this.comments.add(new CommentDTO(c));
        }
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "BlogEntryDTO{" + "id=" + id + ", user=" + user + ", dateOfCreation=" + dateOfCreation + ", title=" + title + ", content=" + content + ", comments=" + comments + '}';
    }
    
    
}
