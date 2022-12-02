package com.matthewfraser.cp470_losty;

import java.nio.charset.Charset;
import java.util.Random;

public class RandomInfoGenerator {
    public String username;
    public String email;

    public RandomInfoGenerator(){
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        String username = generateString(8);
        this.username = username;
    }

    protected String generateString(int length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random randomRan = new Random();
        while (salt.length() < length) { // length of the random string.
            int index = (int) (randomRan.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}
