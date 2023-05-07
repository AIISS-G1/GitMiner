package aiss.gitminer.controller;

import aiss.gitminer.exception.EntityNotFoundException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.pagination.Pagination;
import aiss.gitminer.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired private CommentRepository commentRepository;

    @GetMapping
    public List<Comment> findAll(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "20") int pageSize) {
        return commentRepository.findAll(Pagination.of(page, pageSize)).getContent();
    }

    @GetMapping("/{id}")
    public Comment findById(@PathVariable String id) {
        return commentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
