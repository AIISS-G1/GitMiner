package aiss.gitminer.controller;

import aiss.gitminer.exception.EntityNotFoundException;
import aiss.gitminer.model.Commit;
import aiss.gitminer.pagination.Pagination;
import aiss.gitminer.repository.CommitRepository;
import aiss.gitminer.service.CommitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommitController {

    @Autowired private CommitService commitService;
    @Autowired private CommitRepository commitRepository;

    @GetMapping("/commits")
    public List<Commit> findAll(@RequestParam(required = false) String email,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "20") int pageSize) {
        return commitService.findAll(email, Pagination.of(page, pageSize));
    }

    @PostMapping("/commits")
    @ResponseStatus(HttpStatus.CREATED)
    public Commit create(@RequestBody Commit commit) {
        return commitRepository.save(commit);
    }

    @GetMapping("/commits/{id}")
    public Commit findById(@PathVariable String id) {
        return commitRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

//    @GetMapping("/project/{id}/commits")
//    public List<Commit> getFromProject() {
//        return commitRepository.findBy()
//    }
}
