package com.course.graphql.component.fake;

import com.course.graphql.datasource.fake.FakeBookDatasource;
import com.course.graphql.datasource.fake.FakeHelloDatasource;
import com.course.graphql.generated.DgsConstants;
import com.course.graphql.generated.types.SmartSearchResult;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DgsComponent
public class FakeSmartResolver {


    @DgsData (
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.SmartSearch
    )
    public List<SmartSearchResult> getSmartSearch (@InputArgument (name = "keyword") Optional<String> keyword) {

        List<SmartSearchResult> smartSearchResults = new ArrayList<> ();

        if (keyword.isEmpty ()) {
            smartSearchResults.addAll (FakeBookDatasource.BOOK_LIST);
            smartSearchResults.addAll (FakeHelloDatasource.HELLO_LIST);
            return smartSearchResults;
        } else {
            FakeBookDatasource.BOOK_LIST.stream ()
                    .filter (book -> StringUtils.containsIgnoreCase (book.getTitle (), keyword.get ()))
                    .forEach (smartSearchResults::add);

            FakeHelloDatasource.HELLO_LIST.stream ()
                    .filter (hello -> StringUtils.containsIgnoreCase (hello.getMessage (), keyword.get ()))
                    .forEach (smartSearchResults::add);
            return smartSearchResults;
        }
    }
}
