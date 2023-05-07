package aiss.gitminer.authentication;

import aiss.gitminer.exception.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthenticationRestTemplate {

    @Autowired(required = false) private RestTemplate restTemplate;

    public <T> T getForObject(String url, Class<T> responseType, String token) {
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
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return headers;
    }
}
