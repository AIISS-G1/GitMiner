package aiss.gitminer.controller;

import aiss.gitminer.exception.EntityNotFoundException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.pagination.Pagination;
import aiss.gitminer.repository.CommentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comment", description = "Comment Management API")
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentRepository commentRepository;

    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Operation(
            summary = "List of comments",
            description = "List of all comments",
            tags = {"comment","get"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Comment list",
                    content = { @Content(schema = @Schema(implementation = Comment.class),
                            mediaType = "application/json") }),
            @ApiResponse(
                    responseCode = "404",
                    description="Comment not found",
                    content = { @Content(schema = @Schema()) })})
    @GetMapping
    public List<Comment> findAll(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "20") int pageSize) {
        return commentRepository.findAll(Pagination.of(page, pageSize)).getContent();
    }
    @Operation(
            summary = "Comment of a given id",
            description = "Comment of a given id",
            tags = {"comment","get"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Comment of Id",
                    content = { @Content(schema = @Schema(implementation = Comment.class),
                            mediaType = "application/json") }),
            @ApiResponse(
                    responseCode = "404",
                    description="Comment not found",
                    content = { @Content(schema = @Schema()) })})
    @GetMapping("/{id}")
    public Comment findById(@PathVariable String id) {
        return commentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
