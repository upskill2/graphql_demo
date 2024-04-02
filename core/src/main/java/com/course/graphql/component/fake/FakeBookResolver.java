package com.course.graphql.component.fake;

import com.course.graphql.datasource.fake.FakeBookDatasource;
import com.course.graphql.generated.DgsConstants;
import com.course.graphql.generated.types.Book;
import com.course.graphql.generated.types.ReleaseHistory;
import com.course.graphql.generated.types.ReleaseHistoryInput;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import graphql.schema.DataFetchingEnvironment;
import io.micrometer.common.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@DgsComponent
public class FakeBookResolver {

    @DgsData (parentType = "Query", field = "books")
    public List<Book> booksWrittenBy (@InputArgument (name = "author") Optional<String> authorName) {
        if (authorName.isEmpty () || StringUtils.isBlank (authorName.orElse (""))) {
            return FakeBookDatasource.BOOK_LIST;
        }
        return FakeBookDatasource.BOOK_LIST.stream ()
                .filter (book -> book.getAuthor ().getName ().toLowerCase ().contains (authorName.get ().toLowerCase ()))
                .toList ();
    }

    @DgsData (
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.BooksReleasedHistory
    )
    public List<Book> getBooksReleasedHistory (DataFetchingEnvironment dataFetchingEnvironment) {
        final Map<String, Object> releasedMap = dataFetchingEnvironment.getArgument ("releasedInput");
        ReleaseHistoryInput releasedInput = ReleaseHistoryInput.newBuilder ()
                .printedEdition (Objects.requireNonNull ((boolean) releasedMap.get (DgsConstants.RELEASEHISTORY.PrintedEdition)))
                .year (Objects.requireNonNull ((int) releasedMap.get (DgsConstants.RELEASEHISTORY.Year)))
                .build ();


        return FakeBookDatasource.BOOK_LIST.stream ()
                .filter (book -> this.matchReleaseHistory (releasedInput, book.getReleased ()))
                .toList ();
    }

    private boolean matchReleaseHistory (ReleaseHistoryInput input, ReleaseHistory element) {
        return input.getPrintedEdition ().equals (element.getPrintedEdition ()) &&
                Objects.equals (input.getYear (), element.getYear ());
    }

}
