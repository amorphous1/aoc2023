package com.github.amorphous1.aoc2023;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Day01 {
    private static final Pattern FIRST_DIGIT_WORD = Pattern.compile("(one|two|three|four|five|six|seven|eight|nine)");
    private static final Pattern LAST_DIGIT_WORD = Pattern.compile(".*(one|two|three|four|five|six|seven|eight|nine)");
    private static final Map<String, String> WORD_TO_DIGIT = Map.of(
            "one", "1",
            "two", "2",
            "three", "3",
            "four", "4",
            "five", "5",
            "six", "6",
            "seven", "7",
            "eight", "8",
            "nine", "9"
    );

    public static void main(String[] args) throws URISyntaxException, IOException {
        String input = Files.readString(Paths.get(Day01.class.getClassLoader().getResource("day01.input").toURI()));
        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    public static int part1(String input) {
        return input.lines().map(Day01::toCalibrationValue).mapToInt(i -> i).sum();
    }

    public static int part2(String input) {
        return input.lines().map(Day01::wordsToDigits).map(Day01::toCalibrationValue).mapToInt(i -> i).sum();
    }

    private static int toCalibrationValue(String line) {
        int firstDigit = firstDigitIn(line.chars());
        int lastDigit = firstDigitIn(new StringBuilder(line).reverse().chars());
        return 10 * firstDigit + lastDigit;
    }

    private static int firstDigitIn(IntStream chars) {
        return chars.filter(Character::isDigit).findFirst().getAsInt() - '0';
    }

    private static String wordsToDigits(String line) {
        String result = line;
        final Matcher firstDigitWordMatcher = FIRST_DIGIT_WORD.matcher(result);
        if (firstDigitWordMatcher.find()) {
            final String digitWord = firstDigitWordMatcher.group(1);
            result = result.substring(0, firstDigitWordMatcher.start(1)) + WORD_TO_DIGIT.get(digitWord) + result.substring(firstDigitWordMatcher.start(1));
        }
        final Matcher lastDigitWordMatcher = LAST_DIGIT_WORD.matcher(result);
        if (lastDigitWordMatcher.find()) {
            final String digitWord = lastDigitWordMatcher.group(1);
            result = result.substring(0, lastDigitWordMatcher.end(1)) + WORD_TO_DIGIT.get(digitWord) + result.substring(lastDigitWordMatcher.end(1));
        }
        return result;
    }
}