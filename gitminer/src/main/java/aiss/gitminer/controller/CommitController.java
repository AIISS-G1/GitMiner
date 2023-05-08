package aiss.gitminer.controller;

import aiss.gitminer.exception.EntityNotFoundException;
import aiss.gitminer.model.Commit;
import aiss.gitminer.model.Issue;
import aiss.gitminer.pagination.Pagination;
import aiss.gitminer.service.CommitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Commit", description = "Commit Management API")
@RestController
public class CommitController {

    @Autowired private CommitService commitService;

    @Operation(
            summary = "List of commits",
            description = "List of all commits",
            tags = {"commit","get"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Commit list",
                    content = { @Content(schema = @Schema(implementation = Commit.class),
                            mediaType = "application/json") }),
            @ApiResponse(
                    responseCode = "404",
                    description="Commit not found",
                    content = { @Content(schema = @Schema()) })})
    @GetMapping("/commits")
    public List<Commit> findAll(@RequestParam(required = false) String email,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "20") int pageSize) {
        return commitService.findAll(email, Pagination.of(page, pageSize));
    }

    @Operation(
            summary = "Commit of a given id",
            description = "Commit of a given id",
            tags = {"commit","get"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Commit of Id",
                    content = { @Content(schema = @Schema(implementation = Commit.class),
                            mediaType = "application/json") }),
            @ApiResponse(
                    responseCode = "404",
                    description="Commit not found",
                    content = { @Content(schema = @Schema()) })})
    @GetMapping("/commits/{id}")
    public Commit findById(@PathVariable String id) {
        return commitService.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Operation(
            summary = "Create a commit",
            description = "Create a commit",
            tags = {"commit","post"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Commit successfully created",
                    content = { @Content(schema = @Schema(implementation = Commit.class),
                            mediaType = "application/json") }),
            @ApiResponse(
                    responseCode = "404",
                    description="Commit not successfully created",
                    content = { @Content(schema = @Schema()) })})
    @PostMapping("/commits")
    @ResponseStatus(HttpStatus.CREATED)
    public Commit create(@RequestBody Commit commit) {
        return commitService.save(commit);
    }
}
