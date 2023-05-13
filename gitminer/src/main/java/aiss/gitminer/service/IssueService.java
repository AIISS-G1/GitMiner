package aiss.gitminer.service;

import aiss.gitminer.model.Issue;
import aiss.gitminer.repository.IssueRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IssueService {

    private final IssueRepository issueRepository;

    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    public Page<Issue> findAll(Pageable pageable) {
        return issueRepository.findAll(pageable);
    }

    public Page<Issue> findAll(String state, Pageable pageable) {
        if (state == null) return this.findAll(pageable);
        return issueRepository.findByState(state, pageable);
    }

    public Optional<Issue> findById(String id) {
        return issueRepository.findById(id);
    }
}
