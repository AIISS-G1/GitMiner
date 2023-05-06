package aiss.gitminer.service;

import aiss.gitminer.model.Commit;
import aiss.gitminer.repository.CommitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommitService {

    @Autowired private CommitRepository commitRepository;

    public List<Commit> findAll() {
        // TODO pagination
        return commitRepository.findAll();
    }

    public List<Commit> findAll(String email) {
        if (email == null) return this.findAll();
        return commitRepository.findByAuthorEmail(email);
    }
}
