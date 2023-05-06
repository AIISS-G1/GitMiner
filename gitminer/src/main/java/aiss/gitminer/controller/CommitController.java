package aiss.gitminer.controller;

import aiss.gitminer.exception.EntityNotFoundException;
import aiss.gitminer.model.Commit;
import aiss.gitminer.repository.CommitRepository;
import aiss.gitminer.service.CommitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommitController {

    @Autowired private CommitService commitService;
    @Autowired private CommitRepository commitRepository;

    @GetMapping("/commits")
    public List<Commit> findAll(@RequestParam(required = false) String email) {
        return commitService.findAll(email);
    }

    @PostMapping("/commits")
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
