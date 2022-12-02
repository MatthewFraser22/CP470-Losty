package com.matthewfraser.cp470_losty;

import java.nio.charset.Charset;
import java.util.Random;

public class RandomInfoGenerator {
    public String username;
    public String email;

    public RandomInfoGenerator(){
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String username = new String(array, Charset.forName("UTF-8"));
        this.email = username + "@gmail.com";
        this.username = username;
    }
}
