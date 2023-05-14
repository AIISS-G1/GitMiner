package aiss.gitminer.repository;

import aiss.gitminer.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {

    @Query("SELECT c FROM Comment c WHERE " +
           "(:authorId is null or c.author.id = :authorId) and" +
           "(:sinceCreatedAt is null or :untilCreatedAt is null or c.createdAt BETWEEN :sinceCreatedAt AND :untilCreatedAt) and" +
           "(:sinceUpdatedAt is null or :untilUpdatedAt is null or c.updatedAt BETWEEN :sinceUpdatedAt AND :untilUpdatedAt)")
    Page<Comment> findAll(@Param("authorId") String authorId,
                          @Param("sinceCreatedAt") Instant sinceCreatedAt,
                          @Param("untilCreatedAt") Instant untilCreatedAt,
                          @Param("sinceUpdatedAt") Instant sinceUpdatedAt,
                          @Param("untilUpdatedAt") Instant untilUpdatedAt,
                          Pageable pageable);

    @Query("SELECT c FROM Issue i JOIN i.comments c WHERE i.id = :issueId and" +
           "(:authorId is null or c.author.id = :authorId) and" +
           "(:sinceCreatedAt is null or :untilCreatedAt is null or c.createdAt BETWEEN :sinceCreatedAt AND :untilCreatedAt) and" +
           "(:sinceUpdatedAt is null or :untilUpdatedAt is null or c.updatedAt BETWEEN :sinceUpdatedAt AND :untilUpdatedAt)")
    Page<Comment> findAll(@Param("issueId") String issueId,
                          @Param("authorId") String authorId,
                          @Param("sinceCreatedAt") Instant sinceCreatedAt,
                          @Param("untilCreatedAt") Instant untilCreatedAt,
                          @Param("sinceUpdatedAt") Instant sinceUpdatedAt,
                          @Param("untilUpdatedAt") Instant untilUpdatedAt,
                          Pageable pageable);
}
