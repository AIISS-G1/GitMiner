package aiss.gitminer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.server.ResponseStatusException;

public class AuthenticationException extends ResponseStatusException {

    public AuthenticationException(HttpClientErrorException exception) {
        this(exception instanceof Unauthorized ? HttpStatus.UNAUTHORIZED : HttpStatus.FORBIDDEN);
    }

    public AuthenticationException(HttpStatus status) {
        super(status, "Service API responded with a " + status + " error.");
    }
}
