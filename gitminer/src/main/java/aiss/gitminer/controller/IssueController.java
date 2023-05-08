package aiss.gitminer.controller;

import aiss.gitminer.exception.EntityNotFoundException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Issue;
import aiss.gitminer.pagination.Pagination;
import aiss.gitminer.repository.IssueRepository;
import aiss.gitminer.service.IssueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Issue", description = "Issue Management API")
@RestController
@RequestMapping("/issues")
public class IssueController {

    @Autowired private IssueService issueService;

    @Operation(
            summary = "List of issues",
            description = "List of all issues",
            tags = {"issue","get"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Issue list",
                    content = { @Content(schema = @Schema(implementation = Issue.class),
                            mediaType = "application/json") }),
            @ApiResponse(
                    responseCode = "404",
                    description="Issue not found",
                    content = { @Content(schema = @Schema()) })})
    @GetMapping
    public List<Issue> findAll(@RequestParam(required = false) String state,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "20") int pageSize) {
        return issueService.findAll(state, Pagination.of(page, pageSize));
    }

    @Operation(
            summary = "Issue of a given id",
            description = "Issue of a given id",
            tags = {"issue","get"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Issue of Id",
                    content = { @Content(schema = @Schema(implementation = Issue.class),
                            mediaType = "application/json") }),
            @ApiResponse(
                    responseCode = "404",
                    description="Issue not found",
                    content = { @Content(schema = @Schema()) })})
    @GetMapping("/{id}")
    public Issue findById(@PathVariable String id) {
        return issueService.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
