package dev.poporo.course.ch04.service.query;

import dev.poporo.course.ch04.datasource.problemz.entity.Problemz;
import dev.poporo.course.ch04.datasource.problemz.repository.ProblemzRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProblemzQueryService {

    @Autowired
    private ProblemzRepository repository;

    public List<Problemz> problemzLatestList() {
        var problemz = repository.findAllByOrderByCreationTimestampDesc();

        // problemz.forEach(
        //         p -> p.getSolutions().sort(Comparator.comparing(Solutionz::getCreationTimestamp).reversed()));

        return problemz;
    }

    public Optional<Problemz> problemzDetail(UUID problemzId) {
        return repository.findById(problemzId);
    }

    public List<Problemz> problemzByKeyword(String keyword) {
        return repository.findByKeyword("%" + keyword + "%");
    }
}
