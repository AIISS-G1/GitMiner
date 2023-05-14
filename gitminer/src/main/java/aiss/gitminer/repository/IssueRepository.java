package aiss.gitminer.repository;

import aiss.gitminer.model.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface IssueRepository extends JpaRepository<Issue, String> {

    @Query("SELECT i FROM Issue i WHERE " +
           "(:titlePattern is null or i.title like :titlePattern) and" +
           "(:state is null or i.state = :state) and" +
           "(:authorId is null or i.author.id = :authorId) and" +
           "(:sinceCreatedAt is null or :untilCreatedAt is null or i.createdAt BETWEEN :sinceCreatedAt AND :untilCreatedAt) and" +
           "(:sinceUpdatedAt is null or :untilUpdatedAt is null or i.updatedAt BETWEEN :sinceUpdatedAt AND :untilUpdatedAt) and" +
           "(:sinceClosedAt is null or :untilClosedAt is null or i.closedAt BETWEEN :sinceClosedAt AND :untilClosedAt)")
    Page<Issue> findAll(@Param("titlePattern") String titlePattern,
                        @Param("state") String state,
                        @Param("authorId") String authorId,
                        @Param("sinceCreatedAt") Instant sinceCreatedAt,
                        @Param("untilCreatedAt") Instant untilCreatedAt,
                        @Param("sinceUpdatedAt") Instant sinceUpdatedAt,
                        @Param("untilUpdatedAt") Instant untilUpdatedAt,
                        @Param("sinceClosedAt") Instant sinceClosedAt,
                        @Param("untilClosedAt") Instant untilClosedAt,
                        Pageable pageable);

    @Query("SELECT i FROM Project p JOIN p.issues i WHERE p.id = :projectId AND" +
           "(:titlePattern is null or i.title like :titlePattern) and" +
           "(:state is null or i.state = :state) and" +
           "(:authorId is null or i.author.id = :authorId) and" +
           "(:sinceCreatedAt is null or :untilCreatedAt is null or i.createdAt BETWEEN :sinceCreatedAt AND :untilCreatedAt) and" +
           "(:sinceUpdatedAt is null or :untilUpdatedAt is null or i.updatedAt BETWEEN :sinceUpdatedAt AND :untilUpdatedAt) and" +
           "(:sinceClosedAt is null or :untilClosedAt is null or i.closedAt BETWEEN :sinceClosedAt AND :untilClosedAt)")
    Page<Issue> findAll(@Param("projectId") String projectId,
                        @Param("titlePattern") String titlePattern,
                        @Param("state") String state,
                        @Param("authorId") String authorId,
                        @Param("sinceCreatedAt") Instant sinceCreatedAt,
                        @Param("untilCreatedAt") Instant untilCreatedAt,
                        @Param("sinceUpdatedAt") Instant sinceUpdatedAt,
                        @Param("untilUpdatedAt") Instant untilUpdatedAt,
                        @Param("sinceClosedAt") Instant sinceClosedAt,
                        @Param("untilClosedAt") Instant untilClosedAt,
                        Pageable pageable);

}
