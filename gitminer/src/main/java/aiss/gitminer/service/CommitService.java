package aiss.gitminer.service;

import aiss.gitminer.model.Commit;
import aiss.gitminer.repository.CommitRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class CommitService {

    private final CommitRepository commitRepository;

    public CommitService(CommitRepository commitRepository) {
        this.commitRepository = commitRepository;
    }

    public Page<Commit> findAll(String authorEmail,
                                String committerEmail,
                                Instant sinceAuthoredDate,
                                Instant untilAuthoredDate,
                                Instant sinceCommittedDate,
                                Instant untilCommittedDate,
                                Pageable pageable) {
        if (sinceAuthoredDate == null && untilAuthoredDate != null) sinceAuthoredDate = Instant.EPOCH;
        if (untilAuthoredDate == null && sinceAuthoredDate != null) untilAuthoredDate = Instant.now();
        if (sinceCommittedDate == null && untilCommittedDate != null) sinceCommittedDate = Instant.EPOCH;
        if (untilCommittedDate == null && sinceCommittedDate != null) untilCommittedDate = Instant.now();

        return this.commitRepository.findAll(authorEmail, committerEmail, sinceAuthoredDate, untilAuthoredDate,
                sinceCommittedDate, untilCommittedDate, pageable);
    }

    public Optional<Commit> findById(String id) {
        return commitRepository.findById(id);
    }

    public Commit save(Commit commit) {
        return commitRepository.save(commit);
    }
}
