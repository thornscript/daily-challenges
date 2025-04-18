package dev.poporo.sec05;

import com.google.protobuf.InvalidProtocolBufferException;
import dev.poporo.models.sec05.v3.Television;
import dev.poporo.models.sec05.v3.Type;
import dev.poporo.sec05.parser.V1Parser;
import dev.poporo.sec05.parser.V2Parser;
import dev.poporo.sec05.parser.V3Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class V3VersionCompatibility {

    private static final Logger log = LoggerFactory.getLogger(V3VersionCompatibility.class);

    public static void main(String[] args) throws InvalidProtocolBufferException {

        var tv = Television.newBuilder()
                        .setBrand("samsung")
                        .setType(Type.UHD)
                        .build();

        V1Parser.parse(tv.toByteArray());
        V2Parser.parse(tv.toByteArray());
        V3Parser.parse(tv.toByteArray());
    }
}
