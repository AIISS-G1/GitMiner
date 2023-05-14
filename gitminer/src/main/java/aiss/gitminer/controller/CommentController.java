package aiss.gitminer.controller;

import aiss.gitminer.exception.EntityNotFoundException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.service.CommentService;
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

@Tag(name = "Comment", description = "Comment Management API")
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(
            summary = "List of comments",
            description = "List of all comments",
            tags = {"comment", "get"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Comment list",
                    content = {@Content(schema = @Schema(implementation = Comment.class),
                            mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Comment not found",
                    content = {@Content(schema = @Schema())})})
    @PageableAsQueryParam
    @GetMapping
    public List<Comment> findAll(@RequestParam(required = false) String authorId,
                                 @RequestParam(required = false) Instant sinceCreatedAt,
                                 @RequestParam(required = false) Instant untilCreatedAt,
                                 @RequestParam(required = false) Instant sinceUpdatedAt,
                                 @RequestParam(required = false) Instant untilUpdatedAt,
                                 @Parameter(hidden = true) Pageable pageable) {
        return commentService.findAll(authorId, sinceCreatedAt, untilCreatedAt, sinceUpdatedAt, untilUpdatedAt, pageable).getContent();
    }

    @Operation(
            summary = "Comment of a given id",
            description = "Comment of a given id",
            tags = {"comment", "get"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Comment of Id",
                    content = {@Content(schema = @Schema(implementation = Comment.class),
                            mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Comment not found",
                    content = {@Content(schema = @Schema())})})
    @GetMapping("/{id}")
    public Comment findById(@PathVariable String id) {
        return commentService.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
