package com.course.graphql.service.query;

import com.course.graphql.datasource.entity.Problems;
import com.course.graphql.datasource.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemsQueryService {

    @Autowired
    private ProblemRepository problemRepository;

    public List<Problems> getAllProblemsOrdered () {
        return problemRepository.findAllByOrderByCreationTimestampDesc ();
    }

}
