
package exceptions;


public class PersonNotFoundException extends ClientException {
    public PersonNotFoundException(String message) {
        super(message);
    }
    
    public PersonNotFoundException(String message, int statusCode) {
        super(message, statusCode);
    }
}

