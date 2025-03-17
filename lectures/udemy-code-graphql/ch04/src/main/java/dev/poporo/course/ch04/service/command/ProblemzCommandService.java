package dev.poporo.course.ch04.service.command;

import dev.poporo.course.ch04.datasource.problemz.entity.Problemz;
import dev.poporo.course.ch04.datasource.problemz.repository.ProblemzRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProblemzCommandService {

    @Autowired
    private ProblemzRepository repository;

    public Problemz createProblemz(Problemz problemz) {
        var created = repository.save(problemz);

        return created;
    }
}
