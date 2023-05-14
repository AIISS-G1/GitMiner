package aiss.gitminer.controller;

import aiss.gitminer.exception.EntityNotFoundException;
import aiss.gitminer.model.User;
import aiss.gitminer.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "User", description = "User Management API")
@RestController
@RequestMapping(value = "/users", produces = "application/json")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Operation(
            summary = "List all users",
            tags = "get"
    )
    @ApiResponse(
            responseCode = "200",
            description = "The user list"
    )
    @GetMapping
    public List<User> findAll(@ParameterObject Pageable pageable) {
        return userRepository.findAll(pageable).getContent();
    }

    @Operation(
            summary = "Find an user by its id",
            tags = "get"
    )
    @ApiResponse(
            responseCode = "200",
            description = "The found user"
    )
    @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = @Content
    )
    @GetMapping("/{id}")
    public User findById(@PathVariable String id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
