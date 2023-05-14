package aiss.gitminer.controller;

import aiss.gitminer.exception.EntityNotFoundException;
import aiss.gitminer.model.Commit;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.ProjectRepository;
import aiss.gitminer.service.CommitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@Tag(name = "Project", description = "Project Management API")
@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final CommitService commitService;

    public ProjectController(ProjectRepository projectRepository, CommitService commitService) {
        this.projectRepository = projectRepository;
        this.commitService = commitService;
    }

    @Operation(
            summary = "Create a project",
            description = "Create a project",
            tags = {"project", "post"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Project successfully created",
                    content = {@Content(schema = @Schema(implementation = Project.class),
                            mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Project not successfully created",
                    content = {@Content(schema = @Schema())})})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Project create(@RequestBody Project project) {
        return projectRepository.save(project);
    }

    @Operation(
            summary = "List of projects",
            description = "List of all projects",
            tags = {"project", "get"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Project list",
                    content = {@Content(schema = @Schema(implementation = Project.class),
                            mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Project not found",
                    content = {@Content(schema = @Schema())})})
    @PageableAsQueryParam
    @GetMapping
    public List<Project> findAll(@Parameter(hidden = true) Pageable pageable) {
        return projectRepository.findAll(pageable).getContent();
    }

    @Operation(
            summary = "Project of a given id",
            description = "Project of a given id",
            tags = {"project", "get"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Project of Id",
                    content = {@Content(schema = @Schema(implementation = Project.class),
                            mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Project not found",
                    content = {@Content(schema = @Schema())})})
    @GetMapping("/{id}")
    public Project findById(@PathVariable String id) {
        return projectRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Operation(
            summary = "Get project commits",
            description = "List of all commits of the specified project",
            tags = {"Commit", "get"}
    )
    @ApiResponse(
            responseCode = "200",
            description = "Commit list",
            content = @Content(schema = @Schema(implementation = Commit.class), mediaType = "application/json")
    )
    @ApiResponse(
            responseCode = "404",
            description = "Commit not found",
            content = @Content()
    )
    @PageableAsQueryParam
    @GetMapping("/{id}/commits")
    public List<Commit> findAllByProject(@PathVariable String id,
                                         @RequestParam(required = false) String authorEmail,
                                         @RequestParam(required = false) String committerEmail,
                                         @RequestParam(required = false) Instant sinceAuthoredDate,
                                         @RequestParam(required = false) Instant untilAuthoredDate,
                                         @RequestParam(required = false) Instant sinceCommittedDate,
                                         @RequestParam(required = false) Instant untilCommittedDate,
                                         @Parameter(hidden = true) Pageable pageable) {
        if (!this.projectRepository.existsById(id))
            throw new EntityNotFoundException();

        return commitService.findAll(id, authorEmail, committerEmail, sinceAuthoredDate, untilAuthoredDate,
                sinceCommittedDate, untilCommittedDate, pageable).getContent();
    }
}
