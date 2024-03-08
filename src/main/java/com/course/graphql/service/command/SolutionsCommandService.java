package com.course.graphql.service.command;

import com.course.graphql.datasource.entity.Solutions;
import com.course.graphql.datasource.repository.SolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Optional;
import java.util.UUID;

@Service
public class SolutionsCommandService {

    private Sinks.Many<Solutions> solutionsSink = Sinks.many ().multicast ().onBackpressureBuffer ();
    @Autowired
    private SolutionRepository solutionRepository;

    public Solutions createSolution (final Solutions solution) {
        return solutionRepository.save (solution);
    }

    public Optional<Solutions> addVoteGoodCount (final UUID solutionId) {
        solutionRepository.addVoteGoodCount (solutionId);
        final Optional<Solutions> newSolutionId = solutionRepository.findById (solutionId);

        newSolutionId.ifPresent (solutionsSink::tryEmitNext);

        return newSolutionId;
    }

    public Optional<Solutions> addVoteBadCount (final UUID solutionId) {
        solutionRepository.addVoteBadCount (solutionId);

        final Optional<Solutions> newSolutionId = solutionRepository.findById (solutionId);
        newSolutionId.ifPresent (solutionsSink::tryEmitNext);
        return newSolutionId;
    }

    public Flux<Solutions> solutionsFlux () {
        return solutionsSink.asFlux ();
    }
}
