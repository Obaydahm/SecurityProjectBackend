
package exceptions;


public class UsernameExistsException extends ClientException {
    public UsernameExistsException(String username) {
        super("The username " + username + " already exists.", 409);
    }
    
}

