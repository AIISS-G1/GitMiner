package aiss.gitminer.authentication;

import aiss.gitminer.exception.AuthenticationException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

@Service
public class AuthenticationRestTemplate {

    private final RestTemplate restTemplate;

    public AuthenticationRestTemplate(Optional<RestTemplate> restTemplate) {
        this.restTemplate = restTemplate.orElse(null);
    }

    public <T> T getForObject(String url, Class<T> responseType, String token) {
        Objects.requireNonNull(this.restTemplate, "a RestTemplate bean must be defined in order to use this method.");

        if (token == null)
            throw new AuthenticationException(HttpStatus.UNAUTHORIZED);

        try {
            HttpEntity<String> httpEntity = new HttpEntity<>("parameters", buildAuthenticationHeader(token));
            return this.restTemplate.exchange(url, HttpMethod.GET, httpEntity, responseType).getBody();
        } catch (HttpClientErrorException.Unauthorized | HttpClientErrorException.Forbidden e) {
            throw new AuthenticationException(e);
        }
    }

    public static HttpHeaders buildAuthenticationHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return headers;
    }
}
