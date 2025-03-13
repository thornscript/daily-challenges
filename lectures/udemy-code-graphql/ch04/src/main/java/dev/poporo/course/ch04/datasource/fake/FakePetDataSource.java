package dev.poporo.course.ch04.datasource.fake;

import com.netflix.dgs.codegen.generated.types.Cat;
import com.netflix.dgs.codegen.generated.types.Dog;
import com.netflix.dgs.codegen.generated.types.Pet;
import com.netflix.dgs.codegen.generated.types.PetFoodType;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FakePetDataSource {

    public static final List<Pet> PET_LIST = new ArrayList<>();

    @Autowired
    private Faker faker;

    @PostConstruct
    private void postConstruct() {
        for (int i = 0; i < 10; i++) {
            Pet animal = switch (i % 2) {
                case 0:
                    yield Dog.newBuilder().name(faker.dog().name())
                            .food(PetFoodType.OMNIVORE)
                            .breed(faker.dog().breed())
                            .size(faker.dog().size())
                            .coatLength(faker.dog().coatLength())
                            .build();
                default:
                    yield Cat.newBuilder().name(faker.cat().name())
                            .food(PetFoodType.CARNIVORE)
                            .breed(faker.cat().breed())
                            .registry(faker.cat().registry())
                            .build();
            };

            PET_LIST.add(animal);
        }
    }
}
