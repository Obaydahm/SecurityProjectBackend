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
    private String password;
    
    UserDTO(User u){
        this.id = u.getId();
        this.userName = u.getUserName();
        this.password = u.getPassword();
    }
    
}
