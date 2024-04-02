package com.course.graphql.util;

import lombok.NoArgsConstructor;
import org.bouncycastle.crypto.generators.OpenBSDBCrypt;

import java.nio.charset.StandardCharsets;

@NoArgsConstructor (access = lombok.AccessLevel.PRIVATE)
public class HashUtil {

    public static final String BCRYPT_SALT = "testTestTestTest";
    public static boolean isBcryptMatches (String original, String hashedValue) {
        return OpenBSDBCrypt.checkPassword(hashedValue, original.getBytes(StandardCharsets.UTF_8));
    }

    public static String hashBcrypt (String original) {
        return OpenBSDBCrypt.generate (original.getBytes (StandardCharsets.UTF_8), BCRYPT_SALT.getBytes (StandardCharsets.UTF_8), 4);
    }

}
