package com.example.retail_rocket.Utils;

import java.security.SecureRandom;
import java.util.Random;

public class RandomValues {
    public static String generateRandomValues(){
        Random random = new Random();
        StringBuilder randomLetters = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            char randomChar = (char) ('A' + random.nextInt(26)); // Generate a random uppercase letter
            randomLetters.append(randomChar);
        }
        randomLetters.append("-");
        randomLetters.append(random.nextInt(1000,10000000));
        return randomLetters.toString();
    }

    public static String generateRandomHex() {
        int bytes = 4;
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[bytes];
        secureRandom.nextBytes(randomBytes);
        StringBuilder hexString = new StringBuilder();
        for (byte b : randomBytes) {
            String hex = String.format("%02x", b & 0xff);
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
