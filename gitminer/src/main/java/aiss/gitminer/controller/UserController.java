package aiss.gitminer.controller;

import aiss.gitminer.exception.EntityNotFoundException;
import aiss.gitminer.model.Project;
import aiss.gitminer.model.User;
import aiss.gitminer.pagination.Pagination;
import aiss.gitminer.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User", description = "User Management API")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired private UserRepository userRepository;

    @Operation(
            summary = "List of users",
            description = "List of all users",
            tags = {"user","get"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User list",
                    content = { @Content(schema = @Schema(implementation = User.class),
                            mediaType = "application/json") }),
            @ApiResponse(
                    responseCode = "404",
                    description="User not found",
                    content = { @Content(schema = @Schema()) })})
    @GetMapping
    public List<User> findAll(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "20") int pageSize) {
        return userRepository.findAll(Pagination.of(page, pageSize)).getContent();
    }

    @Operation(
            summary = "User of a given id",
            description = "User of a given id",
            tags = {"user","get"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User of Id",
                    content = { @Content(schema = @Schema(implementation = User.class),
                            mediaType = "application/json") }),
            @ApiResponse(
                    responseCode = "404",
                    description="User not found",
                    content = { @Content(schema = @Schema()) })})
    @GetMapping("/{id}")
    public User findById(@PathVariable String id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
