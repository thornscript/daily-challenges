package dev.poporo.course.ch04.datasource.problemz.repository;

import dev.poporo.course.ch04.datasource.problemz.entity.Problemz;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemzRepository extends CrudRepository<Problemz, UUID> {

    List<Problemz> findAllByOrderByCreationTimestampDesc();

    @Query(nativeQuery = true, value = "select * from problemz p "
            + "where upper(content) like upper(:keyword) "
            + "or upper(title) like upper(:keyword) "
            + "or upper(tags) like upper(:keyword)")
    List<Problemz> findByKeyword(@Param("keyword") String keyword);
}
