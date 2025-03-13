package dev.poporo.course.ch04.datasource;

import net.datafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

    @Bean
    Faker faker() {
        return new Faker();
    }
}
