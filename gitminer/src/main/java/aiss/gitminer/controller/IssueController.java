package aiss.gitminer.controller;

import aiss.gitminer.exception.EntityNotFoundException;
import aiss.gitminer.model.Issue;
import aiss.gitminer.pagination.Pagination;
import aiss.gitminer.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issues")
public class IssueController {

    @Autowired private IssueRepository issueRepository;

    @GetMapping
    public List<Issue> findAll(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "20") int pageSize) {
        return issueRepository.findAll(Pagination.of(page, pageSize)).getContent();
    }

    @GetMapping("/{id}")
    public Issue findById(@PathVariable String id) {
        return issueRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
