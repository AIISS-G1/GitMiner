package aiss.gitminer.controller;

import aiss.gitminer.exception.EntityNotFoundException;
import aiss.gitminer.model.Issue;
import aiss.gitminer.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/issues")
public class IssueController {

    @Autowired private IssueRepository issueRepository;

    @GetMapping
    public List<Issue> findAll() {
        return issueRepository.findAll();
    }

    @GetMapping("/{id}")
    public Issue findById(@PathVariable String id) {
        return issueRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
