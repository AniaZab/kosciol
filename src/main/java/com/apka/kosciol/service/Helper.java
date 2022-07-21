package com.apka.kosciol.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Helper {
    public static ArrayList<Character> generatePassword(int length) //private potem
    {
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ\u0104\u0118\u015A\u00D3\u0141\u017B\u0179\u0106\u0143";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyząęśółżźćń"; //ąęśółżźćń"
        String specialCharacters = "!@#$%^&*()_+-=[]{};'\":|,.\\/<>?~`";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        ArrayList<Character> password = new ArrayList<Character>();

        password.add(lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length())));
        password.add(capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length())));
        password.add(specialCharacters.charAt(random.nextInt(specialCharacters.length())));
        password.add(numbers.charAt(random.nextInt(numbers.length())));

        for(int i = 4; i<length; i++) {
            password.add(combinedChars.charAt(random.nextInt(combinedChars.length())));
        }
        Collections.shuffle(password);

        return password;
    }
}
