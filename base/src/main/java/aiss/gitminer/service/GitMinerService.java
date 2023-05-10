package aiss.gitminer.service;

import aiss.gitminer.exception.GitMinerApiException;
import aiss.gitminer.model.Project;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

@Service
public class GitMinerService {

    private final RestTemplate restTemplate;

    public GitMinerService(Optional<RestTemplate> restTemplate) {
        this.restTemplate = restTemplate.orElse(null);
    }

    public void uploadProject(Project project) {
        Objects.requireNonNull(this.restTemplate, "a RestTemplate bean must be defined in order to use this service.");

        try {
            this.restTemplate.postForObject("http://localhost:8080/gitminer/projects/", project, Project.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new GitMinerApiException(e);
        }
    }
}
