package com.course.graphql.component.problems;

import com.course.graphql.generated.DgsConstants;
import com.course.graphql.generated.types.Problem;
import com.course.graphql.generated.types.SearchFilter;
import com.course.graphql.generated.types.SearchableItem;
import com.course.graphql.generated.types.Solution;
import com.course.graphql.service.query.ProblemsQueryService;
import com.course.graphql.service.query.SolutionsQueryService;
import com.course.graphql.util.GraphqlBeanMapper;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@DgsComponent
public class ItemsSearchDataResolver {

    @Autowired
    private ProblemsQueryService problemsService;

    @Autowired
    private SolutionsQueryService solutionsService;

    @Autowired
    private GraphqlBeanMapper mapper;

    @DgsQuery
    public List<SearchableItem> searchItems (@InputArgument (name = "filter") SearchFilter filter) {
        final String keyword = "%" + filter.getKeyword () + "%";
        List<Solution> solutionsByKeyWord = solutionsService.findSolutionsByKeyword (keyword)
                .stream ()
                .map (mapper::toGraphqlSolution).toList ();

        List<Problem> problemsByKeyWord = problemsService.findProblemsByKeyword (keyword)
                .stream ()
                .map (mapper::toGraphqlProblem).toList ();

        List<SearchableItem> result = new ArrayList<> ();

        result.addAll (solutionsByKeyWord);
        result.addAll (problemsByKeyWord);

        result.sort (Comparator.comparing (SearchableItem::getCreatedDateTime).reversed ());

        return result;

    }
}
