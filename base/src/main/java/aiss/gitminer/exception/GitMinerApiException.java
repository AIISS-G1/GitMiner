package aiss.gitminer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

public class GitMinerApiException extends ResponseStatusException {

    public GitMinerApiException(RestClientException cause) {
        super(HttpStatus.GATEWAY_TIMEOUT, "There was an error while performing a request to the GitMiner API.", cause);
    }
}
