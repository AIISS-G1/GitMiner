package aiss.gitminer.controller;

import aiss.gitminer.exception.EntityNotFoundException;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired private ProjectRepository projectRepository;

    @GetMapping
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @GetMapping("/{id}")
    public Project findById(@PathVariable String id) {
        return projectRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping
    public Project create(@RequestBody Project project) {
        return projectRepository.save(project);
    }
}
