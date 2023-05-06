package aiss.gitminer.controller;

import aiss.gitminer.exception.EntityNotFoundException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired private CommentRepository commentRepository;

    @GetMapping
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Comment findById(@PathVariable String id) {
        return commentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
