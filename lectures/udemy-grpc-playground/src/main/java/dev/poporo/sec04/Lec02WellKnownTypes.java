package dev.poporo.sec04;

import com.google.protobuf.Int32Value;
import com.google.protobuf.Timestamp;
import dev.poporo.models.common.Address;
import dev.poporo.models.common.BodyStyle;
import dev.poporo.models.common.Car;
import dev.poporo.models.sec04.Person;
import dev.poporo.models.sec04.Sample;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec02WellKnownTypes {

    private static final Logger log = LoggerFactory.getLogger(Lec02WellKnownTypes.class);

    public static void main(String[] args) {

        var sample = Sample.newBuilder()
                .setAge(Int32Value.of(12))
                .setLoginTime(Timestamp.newBuilder().setSeconds(Instant.now().getEpochSecond()).build())
                .build();

        log.info("{}", Instant.ofEpochSecond(sample.getLoginTime().getSeconds()));
    }
}
