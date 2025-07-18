package com.example.gestionAcueducto.security;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class PasswordGenerator {

    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String DIGIT = "0123456789";
    private static final String OTHER_PUNCTUATION = "!@#$%&*()_+-=[]{}|;':\",.<>/?";
    private static final String PASSWORD_CHARS = CHAR_LOWER + CHAR_UPPER + DIGIT + OTHER_PUNCTUATION;
    private static final int PASSWORD_LENGTH = 12; // Longitud recomendada

    private static SecureRandom random = new SecureRandom();

    public String generateRandomPassword() {
        // Asegura al menos un caracter de cada tipo para mayor robustez
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        password.append(CHAR_LOWER.charAt(random.nextInt(CHAR_LOWER.length())));
        password.append(CHAR_UPPER.charAt(random.nextInt(CHAR_UPPER.length())));
        password.append(DIGIT.charAt(random.nextInt(DIGIT.length())));
        password.append(OTHER_PUNCTUATION.charAt(random.nextInt(OTHER_PUNCTUATION.length())));

        // Rellena el resto de la contraseña con caracteres aleatorios
        for (int i = 4; i < PASSWORD_LENGTH; i++) {
            password.append(PASSWORD_CHARS.charAt(random.nextInt(PASSWORD_CHARS.length())));
        }

        // Mezcla la contraseña para que los primeros caracteres no siempre estén al principio
        List<Character> charList = password.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(charList, random); // Usa SecureRandom para el shuffle

        return charList.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}