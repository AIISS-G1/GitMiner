package aiss.gitminer.service;

import aiss.gitminer.model.Commit;
import aiss.gitminer.repository.CommitRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommitService {

    private final CommitRepository commitRepository;

    public CommitService(CommitRepository commitRepository) {
        this.commitRepository = commitRepository;
    }

    public List<Commit> findAll(Pageable pageable) {
        return commitRepository.findAll(pageable).getContent();
    }

    public List<Commit> findAll(String email, Pageable pageable) {
        if (email == null) return this.findAll(pageable);
        return commitRepository.findByAuthorEmail(email, pageable);
    }

    public Optional<Commit> findById(String id) {
        return commitRepository.findById(id);
    }

    public Commit save(Commit commit) {
        return commitRepository.save(commit);
    }
}
