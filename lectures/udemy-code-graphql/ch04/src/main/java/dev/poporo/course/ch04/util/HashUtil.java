package dev.poporo.course.ch04.util;

import java.nio.charset.StandardCharsets;
import org.bouncycastle.crypto.generators.OpenBSDBCrypt;

public class HashUtil {

    public static boolean isBcryptMatch(String original, String hashValue) {
        return OpenBSDBCrypt.checkPassword(hashValue, original.getBytes(StandardCharsets.UTF_8));
    }
}
