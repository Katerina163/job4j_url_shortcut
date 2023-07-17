package ru.job4j.url.shortcut.random;

import java.util.Random;

public class GeneratorRandomString {
    private static int leftLimit = 48;
    private static int rightLimit = 122;
    private static int targetStringLength = 10;
    private static Random random = new Random();

    public static String generate() {
        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
