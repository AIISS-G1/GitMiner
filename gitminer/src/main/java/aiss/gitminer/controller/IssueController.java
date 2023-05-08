package aiss.gitminer.controller;

import aiss.gitminer.exception.EntityNotFoundException;
import aiss.gitminer.model.Issue;
import aiss.gitminer.pagination.Pagination;
import aiss.gitminer.repository.IssueRepository;
import aiss.gitminer.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issues")
public class IssueController {

    @Autowired private IssueService issueService;

    @GetMapping
    public List<Issue> findAll(@RequestParam(required = false) String state,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "20") int pageSize) {
        return issueService.findAll(state, Pagination.of(page, pageSize));
    }

    @GetMapping("/{id}")
    public Issue findById(@PathVariable String id) {
        return issueService.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
