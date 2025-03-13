package dev.poporo.course.ch04.component.fake;

import com.netflix.dgs.codegen.generated.DgsConstants;
import com.netflix.dgs.codegen.generated.DgsConstants.QUERY;
import com.netflix.dgs.codegen.generated.types.Cat;
import com.netflix.dgs.codegen.generated.types.Dog;
import com.netflix.dgs.codegen.generated.types.Pet;
import com.netflix.dgs.codegen.generated.types.PetFilter;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import dev.poporo.course.ch04.datasource.fake.FakePetDataSource;
import io.micrometer.common.util.StringUtils;
import java.util.List;
import java.util.Optional;

@DgsComponent
public class FakePetDataResolver {

    @DgsQuery(field = "pets")
    public List<Pet> getPets(@InputArgument(name = "petFilter")Optional<PetFilter> filter) {
        if (filter.isEmpty()) {
            return FakePetDataSource.PET_LIST;
        }

        return FakePetDataSource.PET_LIST.stream().filter(
                pet -> this.matchFilter(filter.get(), pet)
        ).toList();
    }

    private boolean matchFilter(PetFilter petFilter, Pet pet) {
        if (StringUtils.isBlank(petFilter.getPetType())) {
            return true;
        }

        if (petFilter.getPetType().equalsIgnoreCase(Dog.class.getSimpleName())) {
            return pet instanceof Dog;
        } else if (petFilter.getPetType().equalsIgnoreCase(Cat.class.getSimpleName())) {
            return pet instanceof Cat;
        } else {
            return false;
        }
    }


}
