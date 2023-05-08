package aiss.gitminer.service;

import aiss.gitminer.model.Issue;
import aiss.gitminer.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueService {

    @Autowired private IssueRepository issueRepository;

    public List<Issue> findAll(Pageable pageable) {
        return issueRepository.findAll(pageable).getContent();
    }

    public List<Issue> findAll(String state, Pageable pageable) {
        if (state == null) return this.findAll(pageable);
        return issueRepository.findByState(state, pageable);
    }

    public Optional<Issue> findById(String id) {
        return issueRepository.findById(id);
    }
}
