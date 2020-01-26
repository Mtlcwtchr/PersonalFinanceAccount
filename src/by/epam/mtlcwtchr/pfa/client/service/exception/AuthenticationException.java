package by.epam.mtlcwtchr.pfa.client.service.exception;

public class AuthenticationException extends ServiceException {

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(Throwable cause) {
        super(cause);
    }
}
