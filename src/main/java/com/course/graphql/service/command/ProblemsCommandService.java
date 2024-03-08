package com.course.graphql.service.command;

import com.course.graphql.datasource.entity.Problems;
import com.course.graphql.datasource.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class ProblemsCommandService {

    private Sinks.Many<Problems> problemsSink = Sinks.many ().multicast ().onBackpressureBuffer ();

    @Autowired
    private ProblemRepository problemRepository;

    public Problems createProblem (Problems problem) {

        problemsSink.tryEmitNext (problem);
        return problemRepository.save (problem);

    }
    public Flux<Problems> problemsFlux () {
        return problemsSink.asFlux ();
    }

}
