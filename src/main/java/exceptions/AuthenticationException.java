package exceptions;

/**
 *
 * @author lam@cphbusiness.dk
 */
public class AuthenticationException extends Exception{
    private int statusCode = 400;
    public AuthenticationException(String message) {
        super(message);
    }
    
    public AuthenticationException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public AuthenticationException() {
        super("Could not be authenticated");
        this.statusCode = 500;
    }  

    public int getStatusCode() {
        return statusCode;
    }
    
}
