package dev.poporo.sec03;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import dev.poporo.models.sec03.Address;
import dev.poporo.models.sec03.Person;
import dev.poporo.models.sec03.School;
import dev.poporo.models.sec03.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec04Composition {

    private static final Logger log = LoggerFactory.getLogger(Lec04Composition.class);

    public static void main(String[] args) {

        var address = Address.newBuilder()
                .setStreet("123 main st")
                .setCity("atlanta")
                .setState("GA")
                .build();

        var student = Student.newBuilder()
                .setName("sam")
                .setAddress(address)
                .build();

        var school = School.newBuilder()
                .setId(1)
                .setName("high school")
                .setAddress(address.toBuilder().setStreet("234 main st"))
                .build();

        log.info("school: {}", school);
        log.info("student: {}", student);
    }
}
