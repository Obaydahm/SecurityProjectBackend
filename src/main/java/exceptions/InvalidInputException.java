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
public class InvalidInputException extends ClientException{
    
    public InvalidInputException(String message) {
        super(message, 422);
    }
    
}
