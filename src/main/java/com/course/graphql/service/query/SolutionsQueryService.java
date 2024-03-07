package com.course.graphql.service.query;

import com.course.graphql.datasource.entity.Solutions;
import com.course.graphql.datasource.repository.SolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolutionsQueryService {

    @Autowired
    private SolutionRepository solutionRepository;
    public List<Solutions> findSolutionsByKeyword (final String keyword) {
        return solutionRepository.findByKeyword (keyword);
    }
}
