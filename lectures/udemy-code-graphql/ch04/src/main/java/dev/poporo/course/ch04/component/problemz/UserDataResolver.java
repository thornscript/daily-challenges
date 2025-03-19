package dev.poporo.course.ch04.component.problemz;

import com.netflix.dgs.codegen.generated.DgsConstants;
import com.netflix.dgs.codegen.generated.types.User;
import com.netflix.dgs.codegen.generated.types.UserActivationInput;
import com.netflix.dgs.codegen.generated.types.UserActivationResponse;
import com.netflix.dgs.codegen.generated.types.UserCreateInput;
import com.netflix.dgs.codegen.generated.types.UserLoginInput;
import com.netflix.dgs.codegen.generated.types.UserResponse;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import dev.poporo.course.ch04.service.command.UserzCommandService;
import dev.poporo.course.ch04.service.query.UserzQueryService;
import dev.poporo.course.ch04.util.GraphqlBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;

@DgsComponent
public class UserDataResolver {

    @Autowired
    private UserzCommandService userzCommandService;

    @Autowired
    private UserzQueryService userzQueryService;

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.Me)
    public User accountInfo(@RequestHeader(name = "authToken", required = true) String authToken) {
        var userz = userzQueryService.findUserzByAuthToken(authToken)
                .orElseThrow(DgsEntityNotFoundException::new);

        return GraphqlBeanMapper.mapToGraphql(userz);
    }

    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.UserCreate)
    public UserResponse createUser(@InputArgument(name = "user") UserCreateInput userCreateInput) {
        var userz = GraphqlBeanMapper.mapToEntity(userCreateInput);
        var saved = userzCommandService.createUserz(userz);
        var userResponse = UserResponse.newBuilder().user(
                GraphqlBeanMapper.mapToGraphql(saved)).build();

        return userResponse;
    }

    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.UserLogin)
    public UserResponse userLogin(@InputArgument(name = "user") UserLoginInput userLoginInput) {
        var generatedToken = userzCommandService.login(userLoginInput.getUsername(),
                userLoginInput.getPassword());
        var userAuthToken = GraphqlBeanMapper.mapToGraphql(generatedToken);
        var userInfo = accountInfo(userAuthToken.getAuthToken());
        var userResponse = UserResponse.newBuilder().authToken(userAuthToken)
                .user(userInfo).build();

        return userResponse;
    }

    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.UserActivation)
    public UserActivationResponse userActivation(
            @InputArgument(name = "user") UserActivationInput userActivationInput) {
        var updated = userzCommandService.activateUser(
                userActivationInput.getUsername(), userActivationInput.getActive()
        ).orElseThrow(DgsEntityNotFoundException::new);
        var userActivationResponse = UserActivationResponse.newBuilder()
                .isActive(updated.isActive()).build();

        return userActivationResponse;
    }
}
