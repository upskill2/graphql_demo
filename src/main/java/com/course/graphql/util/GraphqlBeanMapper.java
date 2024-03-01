package com.course.graphql.util;


import com.course.graphql.datasource.entity.Problems;
import com.course.graphql.datasource.entity.Solutions;
import com.course.graphql.datasource.entity.Tokens;
import com.course.graphql.datasource.entity.Users;
import com.course.graphql.generated.types.Problem;
import com.course.graphql.generated.types.Solution;
import com.course.graphql.generated.types.User;
import com.course.graphql.generated.types.UserAuthToken;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.ocpsoft.prettytime.PrettyTime;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper
public interface GraphqlBeanMapper {

    public static final PrettyTime PRETTY_TIME = new PrettyTime ();
    public static final ZoneOffset ZONE_OFFSET = ZoneOffset.ofHours (7);

    @Mapping (target = "createdDateTime", source = "creationTimestamp", qualifiedByName = "toOffsetDateTime")
    User toGraphqlUser (Users user);

    @Mapping (target = "createdDateTime", source = "creationTimestamp", qualifiedByName = "toOffsetDateTime")
    @Mapping (target = "author", source = "createdBy")
    @Mapping (target = "prettyCreatedDateTime", source = "creationTimestamp", qualifiedByName = "toPrettyTime")
    @Mapping (target = "solutionCount", source = "solutions", qualifiedByName = "toSolutionCount")
    Problem toGraphqlProblem (Problems problem);

    @Mapping (target = "createdDateTime", source = "creationTime", qualifiedByName = "toOffsetDateTime")
    @Mapping (target = "voteAsGoodCount", source = "voteGoodCount")
    @Mapping (target = "voteAsBadCount", source = "voteBadCount")
    @Mapping (target = "author", source = "createdBy")
    @Mapping (target = "prettyCreatedDateTime", source = "creationTime", qualifiedByName = "toPrettyTime")
    Solution toGraphqlSolution (Solutions solution);

    @Mapping (target = "expiryTime", source = "creationTime", qualifiedByName = "toOffsetDateTime")
    @Mapping (target = "token", source = "authToken")
    UserAuthToken toGraphqlToken (Tokens tokens);


    @Named ("toOffsetDateTime")
    public static OffsetDateTime toOffsetDateTime (LocalDateTime localDateTime) {
        return localDateTime.atOffset (ZONE_OFFSET);
    }

    @Named ("toPrettyTime")
    public static String toPrettyTime (LocalDateTime localDateTime) {
        return PRETTY_TIME.format (localDateTime);
    }

    @Named ("toSolutionCount")
    public static int toSolutionCount (List<Solutions> solutionsList) {
        return solutionsList.size ();
    }


}
