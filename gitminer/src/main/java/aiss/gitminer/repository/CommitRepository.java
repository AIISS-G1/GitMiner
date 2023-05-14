package aiss.gitminer.repository;

import aiss.gitminer.model.Commit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface CommitRepository extends JpaRepository<Commit, String> {

    @Query("SELECT c FROM Commit c WHERE " +
           "(:authorEmail is null or c.authorEmail = :authorEmail) and" +
           "(:committerEmail is null or c.committerEmail = :committerEmail) and" +
           "(:sinceAuthoredDate is null or :untilAuthoredDate is null or c.authoredDate BETWEEN :sinceAuthoredDate AND :untilAuthoredDate) and" +
           "(:sinceCommittedDate is null or :untilCommittedDate is null or c.committedDate BETWEEN :sinceCommittedDate AND :untilCommittedDate)")
    Page<Commit> findAll(@Param("authorEmail") String authorEmail,
                         @Param("committerEmail") String committerEmail,
                         @Param("sinceAuthoredDate") Instant sinceAuthoredDate,
                         @Param("untilAuthoredDate") Instant untilAuthoredDate,
                         @Param("sinceCommittedDate") Instant sinceCommittedDate,
                         @Param("untilCommittedDate") Instant untilCommittedDate,
                         Pageable pageable);

    @Query("SELECT c FROM Project p JOIN p.commits c WHERE p.id = :projectId AND" +
           "(:authorEmail is null or c.authorEmail = :authorEmail) and" +
           "(:committerEmail is null or c.committerEmail = :committerEmail) and" +
           "(:sinceAuthoredDate is null or :untilAuthoredDate is null or c.authoredDate BETWEEN :sinceAuthoredDate AND :untilAuthoredDate) and" +
           "(:sinceCommittedDate is null or :untilCommittedDate is null or c.committedDate BETWEEN :sinceCommittedDate AND :untilCommittedDate)")
    Page<Commit> findAllByProject(@Param("projectId") String projectId,
                                  @Param("authorEmail") String authorEmail,
                                  @Param("committerEmail") String committerEmail,
                                  @Param("sinceAuthoredDate") Instant sinceAuthoredDate,
                                  @Param("untilAuthoredDate") Instant untilAuthoredDate,
                                  @Param("sinceCommittedDate") Instant sinceCommittedDate,
                                  @Param("untilCommittedDate") Instant untilCommittedDate,
                                  Pageable pageable);

}
