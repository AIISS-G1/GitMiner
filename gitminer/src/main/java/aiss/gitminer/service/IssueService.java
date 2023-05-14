package aiss.gitminer.service;

import aiss.gitminer.model.Issue;
import aiss.gitminer.repository.IssueRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class IssueService {

    private final IssueRepository issueRepository;

    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    public Page<Issue> findAll(String title, String state, String authorId,
                               Instant sinceCreatedAt, Instant untilCreatedAt, Instant sinceUpdatedAt,
                               Instant untilUpdatedAt, Instant sinceClosedAt, Instant untilClosedAt, Pageable pageable) {
        return findAll(null, title, state, authorId, sinceCreatedAt, untilCreatedAt, sinceUpdatedAt, untilUpdatedAt, sinceClosedAt, untilClosedAt, pageable);
    }

    public Page<Issue> findAll(@Nullable String projectId, String title, String state, String authorId,
                               Instant sinceCreatedAt, Instant untilCreatedAt, Instant sinceUpdatedAt,
                               Instant untilUpdatedAt, Instant sinceClosedAt, Instant untilClosedAt, Pageable pageable) {
        String titlePattern = title == null ? null : "%" + title + "%";

        if (sinceCreatedAt == null && untilCreatedAt != null) sinceCreatedAt = Instant.EPOCH;
        if (untilCreatedAt == null && sinceCreatedAt != null) untilCreatedAt = Instant.now();
        if (sinceUpdatedAt == null && untilUpdatedAt != null) sinceUpdatedAt = Instant.EPOCH;
        if (untilUpdatedAt == null && sinceUpdatedAt != null) untilUpdatedAt = Instant.now();
        if (sinceClosedAt == null && untilClosedAt != null) sinceClosedAt = Instant.EPOCH;
        if (untilClosedAt == null && sinceClosedAt != null) untilClosedAt = Instant.now();

        if (projectId != null) {
            return issueRepository.findAll(projectId, titlePattern, state, authorId, sinceCreatedAt, untilCreatedAt,
                    sinceUpdatedAt, untilUpdatedAt, sinceClosedAt, untilClosedAt, pageable);
        } else {
            return issueRepository.findAll(titlePattern, state, authorId, sinceCreatedAt, untilCreatedAt,
                    sinceUpdatedAt, untilUpdatedAt, sinceClosedAt, untilClosedAt, pageable);
        }
    }

    public Optional<Issue> findById(String id) {
        return issueRepository.findById(id);
    }
}
