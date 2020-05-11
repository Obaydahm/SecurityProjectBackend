/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import entities.User1;

/**
 *
 * @author Ludvig
 */
public class UserDTO {
    
    private int id;
    private String userName;
    private String password;
    private String role1;
    
    public UserDTO(User1 u){
        this.id = u.getId();
        this.userName = u.getUserName();
        this.password = u.getPassword();
        this.role1 = u.getRole();
    }
    
}
