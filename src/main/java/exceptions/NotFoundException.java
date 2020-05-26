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
public class NotFoundException extends ClientException{
    public NotFoundException(String message) {
        super(message);
    }
    
    public NotFoundException(String message, int statusCode) {
        super(message, statusCode);
    }
}


