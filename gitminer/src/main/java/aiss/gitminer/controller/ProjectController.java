package aiss.gitminer.controller;

import aiss.gitminer.exception.EntityNotFoundException;
import aiss.gitminer.model.Commit;
import aiss.gitminer.model.Issue;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.ProjectRepository;
import aiss.gitminer.service.CommitService;
import aiss.gitminer.service.IssueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.converters.models.PageableAsQueryParam;
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
@RequestMapping("/projects")
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
            description = "Project not found",
            content = @Content()
    )
    @PageableAsQueryParam
    @GetMapping("/{id}/commits")
    public List<Commit> findCommits(@PathVariable String id,
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

    @Operation(
            summary = "Get project issues",
            description = "List of all issues of the specified project",
            tags = {"Issue", "get"}
    )
    @ApiResponse(
            responseCode = "200",
            description = "Issue list",
            content = @Content(schema = @Schema(implementation = Issue.class), mediaType = "application/json")
    )
    @ApiResponse(
            responseCode = "404",
            description = "Project not found",
            content = @Content()
    )
    @PageableAsQueryParam
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
                                  @Parameter(hidden = true) Pageable pageable) {
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
