package aiss.gitminer.controller;

import aiss.gitminer.exception.EntityNotFoundException;
import aiss.gitminer.model.Commit;
import aiss.gitminer.service.CommitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@Tag(name = "Commit", description = "Commit Management API")
@RestController
@RequestMapping(value = "/commits", produces = "application/json")
public class CommitController {

    private final CommitService commitService;

    public CommitController(CommitService commitService) {
        this.commitService = commitService;
    }

    @Operation(
            summary = "List all commits",
            tags = "get"
    )
    @ApiResponse(
            responseCode = "200",
            description = "The commit list"
    )
    @GetMapping
    public List<Commit> findAll(@RequestParam(required = false) String authorEmail,
                                @RequestParam(required = false) String committerEmail,
                                @RequestParam(required = false) Instant sinceAuthoredDate,
                                @RequestParam(required = false) Instant untilAuthoredDate,
                                @RequestParam(required = false) Instant sinceCommittedDate,
                                @RequestParam(required = false) Instant untilCommittedDate,
                                @ParameterObject Pageable pageable) {
        return commitService.findAll(authorEmail, committerEmail, sinceAuthoredDate, untilAuthoredDate, sinceCommittedDate,
                untilCommittedDate, pageable).getContent();
    }

    @Operation(
            summary = "Find a commit by its id",
            tags = "get"
    )
    @ApiResponse(
            responseCode = "200",
            description = "The found commit"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Commit not found",
            content = @Content
    )
    @GetMapping("/{id}")
    public Commit findById(@PathVariable String id) {
        return commitService.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
