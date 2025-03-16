package dev.poporo.course.ch04.datasource.problemz.repository;

import dev.poporo.course.ch04.datasource.problemz.entity.Solutionz;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SolutionzRepository extends CrudRepository<Solutionz, UUID> {

    @Query(nativeQuery = true, value = "select * from solutionz where upper(content) like upper(:keyword)")
    List<Solutionz> findByKeyword(@Param("keyword") String keyword);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "update solutionz set vote_bad_count = vote_bad_count + 1 where id = :solutionzId")
    void addVoteBadCount(@Param("solutionzId") UUID id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "update solutionz set vote_good_count = vote_good_count + 1 where id = :solutionzId")
    void addVoteGoodCount(@Param("solutionzId") UUID id);
}
