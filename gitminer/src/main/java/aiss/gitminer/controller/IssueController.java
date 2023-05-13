package aiss.gitminer.controller;

import aiss.gitminer.exception.EntityNotFoundException;
import aiss.gitminer.model.Issue;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Issue", description = "Issue Management API")
@RestController
@RequestMapping("/issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @Operation(
            summary = "List of issues",
            description = "List of all issues",
            tags = {"issue", "get"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Issue list",
                    content = {@Content(schema = @Schema(implementation = Issue.class),
                            mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Issue not found",
                    content = {@Content(schema = @Schema())})})
    @PageableAsQueryParam
    @GetMapping
    public List<Issue> findAll(@RequestParam(required = false) String state,
                               @Parameter(hidden = true) Pageable pageable) {
        return issueService.findAll(state, pageable).getContent();
    }

    @Operation(
            summary = "Issue of a given id",
            description = "Issue of a given id",
            tags = {"issue", "get"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Issue of Id",
                    content = {@Content(schema = @Schema(implementation = Issue.class),
                            mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Issue not found",
                    content = {@Content(schema = @Schema())})})
    @GetMapping("/{id}")
    public Issue findById(@PathVariable String id) {
        return issueService.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
