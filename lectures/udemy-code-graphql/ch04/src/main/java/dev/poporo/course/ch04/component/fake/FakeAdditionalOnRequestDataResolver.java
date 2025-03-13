package dev.poporo.course.ch04.component.fake;

import com.netflix.dgs.codegen.generated.DgsConstants;
import com.netflix.dgs.codegen.generated.DgsConstants.QUERY;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@DgsComponent
public class FakeAdditionalOnRequestDataResolver {

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = QUERY.AdditionalOnRequest)
    public String additionalOnRequest(
            @RequestHeader(name = "optionalHeader", required = false) String optionalHeader,
            @RequestHeader(name = "mandatoryHeader", required = true) String mandatoryHeader,
            @RequestParam(name = "optionalParam", required = false) String optionalParam,
            @RequestParam(name = "mandatoryParam", required = true) String mandatoryParam
    ) {
        var sb = new StringBuilder();

        sb.append("Optional header : ").append(optionalHeader);
        sb.append(", ");
        sb.append("Mandatory header : ").append(mandatoryHeader);
        sb.append(", ");
        sb.append("Optional param : ").append(optionalParam);
        sb.append(", ");
        sb.append("Mandatory param : ").append(mandatoryParam);
        return sb.toString();
    }
}
