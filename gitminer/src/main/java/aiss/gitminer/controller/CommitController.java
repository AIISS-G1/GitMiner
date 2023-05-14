package aiss.gitminer.controller;

import aiss.gitminer.exception.EntityNotFoundException;
import aiss.gitminer.model.Commit;
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
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@Tag(name = "Commit", description = "Commit Management API")
@RestController
@RequestMapping("/commits")
public class CommitController {

    private final CommitService commitService;

    public CommitController(CommitService commitService) {
        this.commitService = commitService;
    }

    @Operation(
            summary = "List of commits",
            description = "List of all commits",
            tags = {"commit", "get"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Commit list",
                    content = {@Content(schema = @Schema(implementation = Commit.class),
                            mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Commit not found",
                    content = {@Content(schema = @Schema())})})
    @PageableAsQueryParam
    @GetMapping
    public List<Commit> findAll(@RequestParam(required = false) String authorEmail,
                                @RequestParam(required = false) String committerEmail,
                                @RequestParam(required = false) Instant sinceAuthoredDate,
                                @RequestParam(required = false) Instant untilAuthoredDate,
                                @RequestParam(required = false) Instant sinceCommittedDate,
                                @RequestParam(required = false) Instant untilCommittedDate,
                                @Parameter(hidden = true) Pageable pageable) {
        return commitService.findAll(authorEmail, committerEmail, sinceAuthoredDate, untilAuthoredDate, sinceCommittedDate,
                untilCommittedDate, pageable).getContent();
    }

    @Operation(
            summary = "Commit of a given id",
            description = "Commit of a given id",
            tags = {"commit", "get"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Commit of Id",
                    content = {@Content(schema = @Schema(implementation = Commit.class),
                            mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Commit not found",
                    content = {@Content(schema = @Schema())})})
    @GetMapping("/{id}")
    public Commit findById(@PathVariable String id) {
        return commitService.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
