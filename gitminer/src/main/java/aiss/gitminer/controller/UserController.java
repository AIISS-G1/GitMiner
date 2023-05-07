package aiss.gitminer.controller;

import aiss.gitminer.exception.EntityNotFoundException;
import aiss.gitminer.model.User;
import aiss.gitminer.pagination.Pagination;
import aiss.gitminer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired private UserRepository userRepository;

    @GetMapping
    public List<User> findAll(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "20") int pageSize) {
        return userRepository.findAll(Pagination.of(page, pageSize)).getContent();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable String id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
