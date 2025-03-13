package dev.poporo.course.ch04.component.fake;

import com.netflix.graphql.dgs.DgsComponent;

@DgsComponent
public class FakeMobileAppDataResolver {

    // @DgsQuery(field = "mobileApps")
    // public List<MobileApp> getMobileApps(@InputArgument(name = "mobileAppFilter") Optional<MobileAppFilter> filter) {
    //     if (filter.isEmpty()) {
    //         return FakeMobileAppDataSource.MOBILE_APP_LIST;
    //     }
    //
    //     return FakeMobileAppDataSource.MOBILE_APP_LIST.stream().filter(
    //             mobileApp -> this.matchFilter(filter.get(), mobileApp)
    //     ).toList();
    // }
}
