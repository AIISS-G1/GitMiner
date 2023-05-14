package aiss.gitminer.controller;

import aiss.gitminer.exception.EntityNotFoundException;
import aiss.gitminer.model.Commit;
import aiss.gitminer.model.Issue;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.ProjectRepository;
import aiss.gitminer.service.CommitService;
import aiss.gitminer.service.IssueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Tag(name = "Project", description = "Project Management API")
@RestController
@RequestMapping(value = "/projects", produces = "application/json")
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final CommitService commitService;
    private final IssueService issueService;

    public ProjectController(ProjectRepository projectRepository, CommitService commitService, IssueService issueService) {
        this.projectRepository = projectRepository;
        this.commitService = commitService;
        this.issueService = issueService;
    }

    @Operation(
            summary = "Create a project",
            tags = "post"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Project successfully created"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Project validation error",
            content = @Content(schema = @Schema(example = "{\"errors\": [\"string\"]}"), mediaType = "application/json")
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Project create(@Valid @RequestBody Project project) {
        return projectRepository.save(project);
    }

    @Operation(
            summary = "List all projects",
            tags = "get"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Project list"
    )
    @GetMapping
    public List<Project> findAll(@ParameterObject Pageable pageable) {
        return projectRepository.findAll(pageable).getContent();
    }

    @Operation(
            summary = "Find a project by its id",
            tags = "get"
    )
    @ApiResponse(
            responseCode = "200",
            description = "The found project"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Project not found",
            content = @Content
    )
    @GetMapping("/{id}")
    public Project findById(@PathVariable String id) {
        return projectRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Operation(
            summary = "Get project commits",
            description = "List all commits of the specified project",
            tags = {"Commit", "get"}
    )
    @ApiResponse(
            responseCode = "200",
            description = "The commit list"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Project not found",
            content = @Content
    )
    @GetMapping("/{id}/commits")
    public List<Commit> findCommits(@PathVariable String id,
                                    @RequestParam(required = false) String authorEmail,
                                    @RequestParam(required = false) String committerEmail,
                                    @RequestParam(required = false) Instant sinceAuthoredDate,
                                    @RequestParam(required = false) Instant untilAuthoredDate,
                                    @RequestParam(required = false) Instant sinceCommittedDate,
                                    @RequestParam(required = false) Instant untilCommittedDate,
                                    @ParameterObject Pageable pageable) {
        if (!this.projectRepository.existsById(id))
            throw new EntityNotFoundException();

        return commitService.findAll(id, authorEmail, committerEmail, sinceAuthoredDate, untilAuthoredDate,
                sinceCommittedDate, untilCommittedDate, pageable).getContent();
    }

    @Operation(
            summary = "Get project issues",
            description = "List all issues of the specified project",
            tags = {"Issue", "get"}
    )
    @ApiResponse(
            responseCode = "200",
            description = "The issue list"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Project not found",
            content = @Content
    )
    @GetMapping("/{id}/issues")
    public List<Issue> findIssues(@PathVariable String id,
                                  @RequestParam(required = false) String title,
                                  @RequestParam(required = false) String state,
                                  @RequestParam(required = false) String authorId,
                                  @RequestParam(required = false) Instant sinceCreatedAt,
                                  @RequestParam(required = false) Instant untilCreatedAt,
                                  @RequestParam(required = false) Instant sinceUpdatedAt,
                                  @RequestParam(required = false) Instant untilUpdatedAt,
                                  @RequestParam(required = false) Instant sinceClosedAt,
                                  @RequestParam(required = false) Instant untilClosedAt,
                                  @ParameterObject Pageable pageable) {
        if (!this.projectRepository.existsById(id))
            throw new EntityNotFoundException();

        return this.issueService.findAll(id, title, state, authorId, sinceCreatedAt, untilCreatedAt, sinceUpdatedAt,
                untilUpdatedAt, sinceClosedAt, untilClosedAt, pageable).getContent();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
        return new ResponseEntity<>(Map.of("errors", errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
