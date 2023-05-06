package aiss.gitminer.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") // RestTemplate bean must be configured in subprojects
public class AuthenticationRestTemplate {

    @Autowired private RestTemplate restTemplate;

    public <T> T getForObject(String url, Class<T> responseType, String token) {
        HttpEntity<String> httpEntity = new HttpEntity<>("parameters", buildAuthenticationHeader(token));
        return this.restTemplate.exchange(url, HttpMethod.GET, httpEntity, responseType).getBody();
    }

    public static HttpHeaders buildAuthenticationHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return headers;
    }
}
