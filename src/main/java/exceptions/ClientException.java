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
public class ClientException extends Exception{
    private int statusCode = 400;
    public ClientException(String message) {
        super(message);
    }
    public ClientException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
    
    
}
