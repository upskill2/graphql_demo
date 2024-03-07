package com.course.graphql.service.query;

import com.course.graphql.datasource.entity.Problems;
import com.course.graphql.datasource.repository.ProblemRepository;
import com.course.graphql.generated.types.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProblemsQueryService {

    @Autowired
    private ProblemRepository problemRepository;

    public List<Problems> getAllProblemsOrdered () {
        return problemRepository.findAllByOrderByCreationTimestampDesc ();
    }

    public Optional<Problems> findProblemById (final String problemId) {
        return problemRepository.findById (UUID.fromString (problemId));
    }

    public List<Problems> findProblemsByKeyword (final String keyword) {
        return problemRepository.findByKeyword (keyword);
    }
}
