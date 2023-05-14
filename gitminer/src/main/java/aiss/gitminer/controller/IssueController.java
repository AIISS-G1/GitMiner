package aiss.gitminer.controller;

import aiss.gitminer.exception.EntityNotFoundException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Issue;
import aiss.gitminer.repository.IssueRepository;
import aiss.gitminer.service.CommentService;
import aiss.gitminer.service.IssueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@Tag(name = "Issue", description = "Issue Management API")
@RestController
@RequestMapping(value = "/issues", produces = "application/json")
public class IssueController {

    private final IssueRepository issueRepository;
    private final IssueService issueService;
    private final CommentService commentService;

    public IssueController(IssueRepository issueRepository, IssueService issueService, CommentService commentService) {
        this.issueRepository = issueRepository;
        this.issueService = issueService;
        this.commentService = commentService;
    }

    @Operation(
            summary = "List all issues",
            tags = "get"
    )
    @ApiResponse(
            responseCode = "200",
            description = "The issue list"
    )
    @GetMapping
    public List<Issue> findAll(@RequestParam(required = false) String title,
                               @RequestParam(required = false) String state,
                               @RequestParam(required = false) String authorId,
                               @RequestParam(required = false) Instant sinceCreatedAt,
                               @RequestParam(required = false) Instant untilCreatedAt,
                               @RequestParam(required = false) Instant sinceUpdatedAt,
                               @RequestParam(required = false) Instant untilUpdatedAt,
                               @RequestParam(required = false) Instant sinceClosedAt,
                               @RequestParam(required = false) Instant untilClosedAt,
                               @ParameterObject Pageable pageable) {
        return issueService.findAll(title, state, authorId, sinceCreatedAt, untilCreatedAt, sinceUpdatedAt, untilUpdatedAt,
                sinceClosedAt, untilClosedAt, pageable).getContent();
    }

    @Operation(
            summary = "Find an issue by its id",
            tags = "get"
    )
    @ApiResponse(
            responseCode = "200",
            description = "The found issue"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Issue not found",
            content = @Content
    )
    @GetMapping("/{id}")
    public Issue findById(@PathVariable String id) {
        return issueService.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Operation(
            summary = "Get issue comments",
            description = "List all comments of the specified issue",
            tags = {"Comment", "get"}
    )
    @ApiResponse(
            responseCode = "200",
            description = "The comment list"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Issue not found",
            content = @Content
    )
    @GetMapping("/{id}/comments")
    public List<Comment> findComments(@PathVariable String id,
                                      @RequestParam(required = false) String authorId,
                                      @RequestParam(required = false) Instant sinceCreatedAt,
                                      @RequestParam(required = false) Instant untilCreatedAt,
                                      @RequestParam(required = false) Instant sinceUpdatedAt,
                                      @RequestParam(required = false) Instant untilUpdatedAt,
                                      @ParameterObject Pageable pageable) {
        if (!this.issueRepository.existsById(id))
            throw new EntityNotFoundException();

        return commentService.findAll(id, authorId, sinceCreatedAt, untilCreatedAt, sinceUpdatedAt, untilUpdatedAt, pageable).getContent();
    }
}
