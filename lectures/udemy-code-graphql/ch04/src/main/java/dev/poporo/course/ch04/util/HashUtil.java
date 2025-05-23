package dev.poporo.course.ch04.util;

import java.nio.charset.StandardCharsets;
import org.bouncycastle.crypto.generators.OpenBSDBCrypt;

public class HashUtil {

    private static final String BCRYPT_SALT = "dontDoThisOnProd";

    public static boolean isBcryptMatch(String original, String hashValue) {
        return OpenBSDBCrypt.checkPassword(hashValue, original.getBytes(StandardCharsets.UTF_8));
    }

    public static String hashBcrypt(String original) {
        return OpenBSDBCrypt.generate(original.getBytes(StandardCharsets.UTF_8),
                BCRYPT_SALT.getBytes(StandardCharsets.UTF_8), 5);
    }
}
