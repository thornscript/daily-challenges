package dev.poporo.sec03;

import dev.poporo.models.sec03.BodyStyle;
import dev.poporo.models.sec03.Car;
import dev.poporo.models.sec03.Dealer;
import dev.poporo.models.sec03.School;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec07DefaultValues {

    private static final Logger log = LoggerFactory.getLogger(Lec07DefaultValues.class);

    public static void main(String[] args) {

        var school = School.newBuilder().build();

        log.info("{}", school.getId());
        log.info("{}", school.getName());
        log.info("{}", school.getAddress().getCity());
    }
}
