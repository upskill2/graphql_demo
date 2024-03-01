package com.course.graphql.component.problems;

import com.course.graphql.generated.DgsConstants;
import com.course.graphql.generated.types.SearchFilter;
import com.course.graphql.generated.types.SearchableItem;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import java.util.List;

@DgsComponent
public class ItemsSearchDataResolver {

    @DgsQuery //(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.SearchItems)
    public List<SearchableItem> searchItems (@InputArgument (name = "filter") SearchFilter filter) {
        return null;

    }
}
