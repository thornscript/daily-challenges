package dev.poporo.course.ch04.service.command;

import dev.poporo.course.ch04.datasource.problemz.entity.Problemz;
import dev.poporo.course.ch04.datasource.problemz.repository.ProblemzRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class ProblemzCommandService {

    private Sinks.Many<Problemz> problemzSink = Sinks.many().multicast().onBackpressureBuffer();

    @Autowired
    private ProblemzRepository repository;

    public Problemz createProblemz(Problemz problemz) {
        var created = repository.save(problemz);

        problemzSink.tryEmitNext(created);

        return created;
    }

    public Flux<Problemz> problemzFlux() {
        return problemzSink.asFlux();
    }
}
