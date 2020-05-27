/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import entities.User;

/**
 *
 * @author Ludvig
 */
public class UserDTO {
    
    private int id;
    private String userName;
    private String role;

    public UserDTO(String userName, String role) {
        this.userName = userName;
        this.role = role;
    }
    
    public UserDTO(User u){
        this.id = u.getId();
        this.userName = u.getUserName();
        this.role = u.getRole();
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "UserDTO{" + "id=" + id + ", userName=" + userName + '}';
    }
    
    
}
