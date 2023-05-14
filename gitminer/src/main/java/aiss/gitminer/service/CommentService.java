package aiss.gitminer.service;

import aiss.gitminer.model.Comment;
import aiss.gitminer.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Page<Comment> findAll(String authorId, Instant sinceCreatedAt, Instant untilCreatedAt,
                               Instant sinceUpdatedAt, Instant untilUpdatedAt, Pageable pageable) {
        return findAll(null, authorId, sinceCreatedAt, untilCreatedAt, sinceUpdatedAt, untilUpdatedAt, pageable);
    }

    public Page<Comment> findAll(@Nullable String issueId, String authorId, Instant sinceCreatedAt, Instant untilCreatedAt,
                                 Instant sinceUpdatedAt, Instant untilUpdatedAt, Pageable pageable) {
        if (sinceCreatedAt == null && untilCreatedAt != null) sinceCreatedAt = Instant.EPOCH;
        if (untilCreatedAt == null && sinceCreatedAt != null) untilCreatedAt = Instant.now();
        if (sinceUpdatedAt == null && untilUpdatedAt != null) sinceUpdatedAt = Instant.EPOCH;
        if (untilUpdatedAt == null && sinceUpdatedAt != null) untilUpdatedAt = Instant.now();

        if (issueId != null) {
            return commentRepository.findAll(issueId, authorId, sinceCreatedAt, untilCreatedAt, sinceUpdatedAt, untilUpdatedAt, pageable);
        } else {
            return commentRepository.findAll(authorId, sinceCreatedAt, untilCreatedAt, sinceUpdatedAt, untilUpdatedAt, pageable);
        }
    }

    public Optional<Comment> findById(String id) {
        return commentRepository.findById(id);
    }
}
