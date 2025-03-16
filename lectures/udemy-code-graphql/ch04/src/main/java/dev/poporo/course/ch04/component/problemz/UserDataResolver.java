package dev.poporo.course.ch04.component.problemz;

import com.netflix.dgs.codegen.generated.DgsConstants;
import com.netflix.dgs.codegen.generated.DgsConstants.MUTATION;
import com.netflix.dgs.codegen.generated.DgsConstants.QUERY;
import com.netflix.dgs.codegen.generated.types.User;
import com.netflix.dgs.codegen.generated.types.UserActivationInput;
import com.netflix.dgs.codegen.generated.types.UserActivationResponse;
import com.netflix.dgs.codegen.generated.types.UserCreateInput;
import com.netflix.dgs.codegen.generated.types.UserLoginInput;
import com.netflix.dgs.codegen.generated.types.UserResponse;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.web.bind.annotation.RequestHeader;

@DgsComponent
public class UserDataResolver {

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = QUERY.Me)
    public User accountInfo(@RequestHeader(name = "authToken", required = true) String authToken) {
        return null;
    }

    @DgsData(parentType = MUTATION.TYPE_NAME, field = MUTATION.UserCreate)
    public UserResponse createUser(@InputArgument(name = "user")UserCreateInput userCreateInput) {
        return null;
    }

    @DgsData(parentType = MUTATION.TYPE_NAME, field = MUTATION.UserLogin)
    public UserResponse userLogin(@InputArgument(name = "user")UserLoginInput userLoginInput) {
        return null;
    }

    @DgsData(parentType = MUTATION.TYPE_NAME, field = MUTATION.UserActivation)
    public UserActivationResponse userActivation(@InputArgument(name = "user") UserActivationInput userActivationInput) {
        return null;
    }
}
