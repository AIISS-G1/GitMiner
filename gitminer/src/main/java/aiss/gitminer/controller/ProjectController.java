package aiss.gitminer.controller;

import aiss.gitminer.exception.EntityNotFoundException;
import aiss.gitminer.model.Project;
import aiss.gitminer.pagination.Pagination;
import aiss.gitminer.repository.ProjectRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Project", description = "Project Management API")
@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Operation(
            summary = "List of projects",
            description = "List of all projects",
            tags = {"project","get"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Project list",
                    content = { @Content(schema = @Schema(implementation = Project.class),
                            mediaType = "application/json") }),
            @ApiResponse(
                    responseCode = "404",
                    description="Project not found",
                    content = { @Content(schema = @Schema()) })})
    @GetMapping
    public List<Project> findAll(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "20") int pageSize) {
        return projectRepository.findAll(Pagination.of(page, pageSize)).getContent();
    }

    @Operation(
            summary = "Project of a given id",
            description = "Project of a given id",
            tags = {"project","get"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Project of Id",
                    content = { @Content(schema = @Schema(implementation = Project.class),
                            mediaType = "application/json") }),
            @ApiResponse(
                    responseCode = "404",
                    description="Project not found",
                    content = { @Content(schema = @Schema()) })})
    @GetMapping("/{id}")
    public Project findById(@PathVariable String id) {
        return projectRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Operation(
            summary = "Create a project",
            description = "Create a project",
            tags = {"project","post"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Project successfully created",
                    content = { @Content(schema = @Schema(implementation = Project.class),
                            mediaType = "application/json") }),
            @ApiResponse(
                    responseCode = "404",
                    description="Project not successfully created",
                    content = { @Content(schema = @Schema()) })})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Project create(@RequestBody Project project) {
        return projectRepository.save(project);
    }
}
