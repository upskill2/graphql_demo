package com.course.graphql.util;

import org.bouncycastle.crypto.generators.OpenBSDBCrypt;

import java.nio.charset.StandardCharsets;

public class HashUtil {

    public static boolean isBcryptMatches (String original, String hashedValue) {
        return OpenBSDBCrypt.checkPassword(hashedValue, original.getBytes(StandardCharsets.UTF_8));
    }
}
