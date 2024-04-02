package com.course.graphql.component.fake;

import com.course.graphql.generated.DgsConstants;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@DgsComponent
public class FakeAdditionalOnRequestDataResolver {


    @DgsData (parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.AdditionalRequest)
    public String additionalOnRequestData (
            @RequestHeader (name = "optionalHeader", required = false) String optionalHeader,
            @RequestHeader (name = "mandatoryHeader", required = true) String mandatoryHeader,
            @RequestParam (name = "optionalParam", required = false) String optionalParam,
            @RequestParam (name = "mandatoryParam", required = true) String mandatoryParam) {


        return new StringBuilder ()
                .append ("optionalHeader: ")
                .append (optionalHeader)
                .append ("\n")
                .append (" mandatoryHeader: ")
                .append (mandatoryHeader)
                .append ("\n")
                .append (" optionalParam: ")
                .append (optionalParam)
                .append ("\\n")
                .append (" mandatoryParam: ")
                .append (mandatoryParam)
                .toString ();
    }

}
