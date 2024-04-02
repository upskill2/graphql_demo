package com.course.graphql.component.fake;

import com.course.graphql.datasource.fake.FakeMobileAppDatasource;
import com.course.graphql.generated.DgsConstants;
import com.course.graphql.generated.types.MobileApp;
import com.course.graphql.generated.types.MobileAppFilter;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@DgsComponent
public class FakeMobileAppDataResolver {

    @DgsData (
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.MobileApps
    )
    public List<MobileApp> getMobileApps (@InputArgument (name = "mobileAppFilter", collectionType = MobileAppFilter.class)
                                          Optional<MobileAppFilter> filter) {

        if (filter.isEmpty ()) {
            return FakeMobileAppDatasource.MOBILE_APP_LIST;
        }
        return FakeMobileAppDatasource.MOBILE_APP_LIST.stream ()
                .filter (app -> this.matchFilter (filter.get (), app))
                .collect (Collectors.toList ());
    }

    private boolean matchFilter (final MobileAppFilter mobileAppFilter, final MobileApp app) {

        boolean isAppNameMatch = StringUtils.containsIgnoreCase (app.getName (), StringUtils.defaultIfBlank (mobileAppFilter.getName (), StringUtils.EMPTY))
                && StringUtils.containsIgnoreCase (app.getVersion (), StringUtils.defaultIfBlank (mobileAppFilter.getVersion (), StringUtils.EMPTY))
                && app.getReleaseDate ().isAfter (Optional.ofNullable (mobileAppFilter.getReleasedAfter ()).orElse (LocalDate.MIN))
                && app.getDownloadedTimes () >= Optional.ofNullable (mobileAppFilter.getMinDownloadedTimes ()).orElse (0)
                && app.getCategory () == Optional.ofNullable (mobileAppFilter.getCategory ()).orElse (app.getCategory ());

        if (!isAppNameMatch) {
            return false;
        }

        if (StringUtils.isNoneBlank (mobileAppFilter.getPlatform ()) &&
                !app.getPlatform ().contains (mobileAppFilter.getPlatform ().toLowerCase ())) {
            return false;
        }

        if (app.getAuthor () != null && mobileAppFilter.getAuthor () != null &&
                !StringUtils.containsIgnoreCase (app.getAuthor ().getName (),
                        StringUtils.defaultIfBlank (mobileAppFilter.getAuthor ().getName (), StringUtils.EMPTY))) {
            return false;
        }
        return true;
    }
}
