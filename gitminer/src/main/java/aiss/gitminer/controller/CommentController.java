package aiss.gitminer.controller;

import aiss.gitminer.exception.EntityNotFoundException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@Tag(name = "Comment", description = "Comment Management API")
@RestController
@RequestMapping(value = "/comments", produces = "application/json")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(
            summary = "List all comments",
            description = "List all comments",
            tags = "get"
    )
    @ApiResponse(
            responseCode = "200",
            description = "The comment list"
    )
    @GetMapping
    public List<Comment> findAll(@RequestParam(required = false) String authorId,
                                 @RequestParam(required = false) Instant sinceCreatedAt,
                                 @RequestParam(required = false) Instant untilCreatedAt,
                                 @RequestParam(required = false) Instant sinceUpdatedAt,
                                 @RequestParam(required = false) Instant untilUpdatedAt,
                                 @ParameterObject Pageable pageable) {
        return commentService.findAll(authorId, sinceCreatedAt, untilCreatedAt, sinceUpdatedAt, untilUpdatedAt, pageable).getContent();
    }

    @Operation(
            summary = "Find a comment by its id",
            description = "Find a comment by its id",
            tags = "get"
    )
    @ApiResponse(
            responseCode = "200",
            description = "The found comment"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Comment not found",
            content = @Content
    )
    @GetMapping("/{id}")
    public Comment findById(@PathVariable String id) {
        return commentService.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
