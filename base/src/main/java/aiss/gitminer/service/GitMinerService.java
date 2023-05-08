package aiss.gitminer.service;

import aiss.gitminer.exception.GitMinerApiException;
import aiss.gitminer.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class GitMinerService {

    @Autowired(required = false) private RestTemplate restTemplate;

    public void uploadProject(Project project) {
        try {
            this.restTemplate.postForObject("http://localhost:8080/gitminer/projects/", project, Project.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new GitMinerApiException(e);
        }
    }
}
