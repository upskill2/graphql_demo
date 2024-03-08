package com.course.graphql.util;


import com.course.graphql.datasource.Category;
import com.course.graphql.datasource.entity.Problems;
import com.course.graphql.datasource.entity.Solutions;
import com.course.graphql.datasource.entity.Tokens;
import com.course.graphql.datasource.entity.Users;
import com.course.graphql.generated.types.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mapper (componentModel = "spring")
public interface GraphqlBeanMapper {

    public static final PrettyTime PRETTY_TIME = new PrettyTime ();
    public static final ZoneOffset ZONE_OFFSET = ZoneOffset.ofHours (7);

    @Mapping (target = "createdDateTime", source = "creationTimestamp", qualifiedByName = "toOffsetDateTime")
    User toGraphqlUser (Users user);

    @Mapping (target = "createdDateTime", source = "creationTimestamp", qualifiedByName = "toOffsetDateTime")
    @Mapping (target = "author", source = "createdBy")
    @Mapping (target = "prettyCreatedDateTime", source = "creationTimestamp", qualifiedByName = "toPrettyTime")
    @Mapping (target = "solutionCount", source = "solutions", qualifiedByName = "toSolutionCount")
    @Mapping (target = "tags", source = "tags", qualifiedByName = "toTagsList")
    Problem toGraphqlProblem (Problems problem);

    @Mapping (target = "createdDateTime", source = "creationTimestamp", qualifiedByName = "toOffsetDateTime")
    @Mapping (target = "author", source = "createdBy")
    @Mapping (target = "prettyCreatedDateTime", source = "creationTimestamp", qualifiedByName = "toPrettyTime")
    @Mapping (target = "solutionCount", source = "solutions", qualifiedByName = "toSolutionCount")
    @Mapping (target = "tags", source = "tags", qualifiedByName = "toTagsList")
    List<Problem> toGraphqlProblemList (List<Problems> problem);

    @Mapping (target = "tags", source = "problemCreateInput", qualifiedByName = "toTagsString")
    @Mapping (target = "id", source = "problemCreateInput", qualifiedByName = "setProblemId")
    @Mapping (target = "solutions", source = "problemCreateInput", qualifiedByName = "toEmptySolutions")
    @Mapping (target = "createdBy", source = "author")
    // @Mapping(ignore = true, target = "tags")
    public Problems toGraphqlProblemCreateInput (ProblemCreateInput problemCreateInput, Users author);



/*    public default Problems toGraphqlProblemCreateInput (ProblemCreateInput problemCreateInput, Users author){
        return Problems.builder ()
                .id (UUID.randomUUID ())
                .title (problemCreateInput.getTitle ())
                .content (problemCreateInput.getContent ())
                .tags (String.join (",", problemCreateInput.getTags ()))
                .createdBy (author)
             //   .creationTimestamp (LocalDateTime.now ())
                .solutions (new ArrayList<> ())
                .build ();
    };*/

    default ProblemResponse toGraphqlProblemCreateResponse (Problems problems) {
        ProblemResponse problemResponse = new ProblemResponse ();
        problemResponse.setProblem (toGraphqlProblem (problems));

        return problemResponse;
    }

    ;

    @Mapping (target = "createdDateTime", source = "creationTime", qualifiedByName = "toOffsetDateTime")
    @Mapping (target = "voteAsGoodCount", source = "voteGoodCount")
    @Mapping (target = "voteAsBadCount", source = "voteBadCount")
    @Mapping (target = "author", source = "createdBy")
    @Mapping (target = "prettyCreatedDateTime", source = "creationTime", qualifiedByName = "toPrettyTime")
    Solution toGraphqlSolution (Solutions solution);

    default SolutionResponse toSolutionResponse (Solutions createdSolution) {
        SolutionResponse solutionResponse = new SolutionResponse ();
        solutionResponse.setSolution (toGraphqlSolution (createdSolution));
        return solutionResponse;
    };

    default Solutions toEntitylSolutions (SolutionCreateInput solutionCreateInput, Users author, Problems problems) {

        return Solutions.builder ()
                .id (UUID.randomUUID ())
                .category (Category.valueOf (solutionCreateInput.getCategory ().name ()))
                .content (solutionCreateInput.getContent ())
                .createdBy (author)
                .voteBadCount (0)
                .voteGoodCount (0)
                .problems (problems)
                .build ();
    }

    @Mapping (target = "expiryTime", source = "creationTime", qualifiedByName = "toOffsetDateTime")
    @Mapping (target = "token", source = "authToken")
    UserAuthToken toGraphqlToken (Tokens tokens);

    Users toUsers (User user);

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

    @Named ("toTagsList")
    public static List<String> toTagsList (String tags) {
        return List.of (tags.split (","));
    }

    @Named ("toTagsString")
    public static String toTagsString (ProblemCreateInput problemCreateInput) {
        return String.join (",", problemCreateInput.getTags ());
    }

    @Named ("setProblemId")
    public static UUID toUUID (ProblemCreateInput problemCreateInput) {
        return UUID.randomUUID ();
    }

    @Named ("toEmptySolutions")
    public static List<Solutions> toEmptySolutions (ProblemCreateInput problemCreateInput) {
        return new ArrayList<> ();
    }

}
