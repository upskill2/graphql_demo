package com.course.graphql.service.command;

import com.course.graphql.datasource.entity.Solutions;
import com.course.graphql.datasource.repository.SolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SolutionsCommandService {

    @Autowired
    private SolutionRepository solutionRepository;

    public Solutions createSolution (final Solutions solution) {
        return solutionRepository.save (solution);
    }

    public Optional<Solutions> addVoteGoodCount (final UUID solutionId) {
      solutionRepository.addVoteGoodCount (solutionId);
        return solutionRepository.findById (solutionId);
    }

    public Optional<Solutions> addVoteBadCount (final UUID solutionId) {
        solutionRepository.addVoteBadCount (solutionId);
        return solutionRepository.findById (solutionId);
    }
}
