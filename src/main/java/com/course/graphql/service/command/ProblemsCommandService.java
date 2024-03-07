package com.course.graphql.service.command;

import com.course.graphql.datasource.entity.Problems;
import com.course.graphql.datasource.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProblemsCommandService {

    @Autowired
    private ProblemRepository problemRepository;

    public Problems createProblem (Problems problem){
        return problemRepository.save (problem);

    }

}
