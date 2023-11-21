package ru.netology;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private static final Faker faker = new Faker(new Locale("en"));

    public static String getCardNumberApproved() {
        return "4444444444444441";
    }

    public static String getCardNumberDeclined() {
        return "4444444444444442";
    }

    public static String getCardNumberNothing() {
        return faker.number().digits(16);
    }

    public static String getCardNumberNotFilled() {
        int randomNumberLength = faker.random().nextInt(16);
        return faker.number().digits(randomNumberLength);
    }

    public static String getEmptyFieldValue() {
        return "";
    }

    public static String getMonthOneMonthAgo() {
        LocalDate currentDate = LocalDate.now();
        LocalDate oneMonthAgo = currentDate.minusMonths(1);
        return oneMonthAgo.format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getCurrentYear() {
        return getYearOffset(0);
    }

    public static String getYearOffset(int offset) {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int modifiedYear = currentYear + offset;
        return String.format("%02d", modifiedYear % 100);
    }

    public static String getMonth() {
        return String.format("%02d", faker.number().numberBetween(1, 13));
    }

    public static String getInvalidMonth() {
        return "00";
    }

    public static String getOwner() {
        return faker.name().fullName();
    }

    public static String getNumberOwner() {
        return faker.number().digit();
    }

    public static String getSpecialCharactersOwner() {
        return "!@#$%^&*()_+-";
    }

    public static String getEmptyOwner() {
        return getEmptyFieldValue();
    }

    public static String getCvc() {
        return faker.number().digits(3);
    }

    public static String get1Cvc() {
        return faker.number().digits(1);
    }

    public static String get2Cvc() {
        return faker.number().digits(2);
    }

    public static String getEmptyCvc() {
        return getEmptyFieldValue();
    }
}
