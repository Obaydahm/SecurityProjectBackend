/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Obaydah Mohamad
 */
public class UserBlockedException extends Exception{
    private int statusCode = 403;
    
    public UserBlockedException() {
        super("Log in wasn't successful. Please try again later.");
    }  

    public int getStatusCode() {
        return statusCode;
    }
}
