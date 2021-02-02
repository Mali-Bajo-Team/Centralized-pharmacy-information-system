package com.pharmacy.cpis.util.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringValidator {

    public static final String VALID_EMAIL_ADDRESS_REGEX = "^(.+)@(\\S+)$";
    public static final String VALID_NUMERIC_REGEX = "[0-9]+";

    public static boolean isEmail(String emailStr) {
        return emailStr.matches(VALID_EMAIL_ADDRESS_REGEX);
    }

    static public Boolean isNumeric(String inputValue) {
        return inputValue.matches(VALID_NUMERIC_REGEX);
    }
}
