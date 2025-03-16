package dev.poporo.course.ch04.service.query;

import dev.poporo.course.ch04.datasource.problemz.entity.Solutionz;
import dev.poporo.course.ch04.datasource.problemz.repository.SolutionzRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolutionzQueryService {

    @Autowired
    private SolutionzRepository repository;

    public List<Solutionz> solutionzByKeyword(String keyword) {
        return repository.findByKeyword("%" + keyword + "%");
    }
}
