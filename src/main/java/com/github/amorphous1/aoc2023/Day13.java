package com.github.amorphous1.aoc2023;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day13 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String input = Files.readString(Paths.get(Day13.class.getClassLoader().getResource("day13.input").toURI()));
        System.out.println(summarise(input, 0));
        System.out.println(summarise(input, 1));
    }

    static long summarise(String input, int totalDiffs) {
        return Arrays.stream(input.split("\n\n"))
                .map(Mirror::parse)
                .map(m -> m.summarise(totalDiffs))
                .mapToLong(l -> l).sum();
    }

    private record Mirror(String[] rows, String[] cols) {
        public long summarise(int totalDiffs) {
            long result = 0;
            for (int col = 0; col < cols.length; col++)
                if (isLineOfReflection(col, cols, totalDiffs))
                    result+= col;
            for (int row = 0; row < rows.length; row++)
                if (isLineOfReflection(row, rows, totalDiffs))
                    result+= 100L * row;
            return result;
        }

        private boolean isLineOfReflection(int candidate, String[] pattern, int totalDiffs) {
            int currentDiffs = 0;
            for (int i = 0; i < Math.min(candidate, pattern.length - candidate); i++) {
                currentDiffs += countDiffs(pattern[candidate + i], pattern[candidate - i - 1]);
                if (currentDiffs > totalDiffs)
                    return false;
            }
            return currentDiffs == totalDiffs;
        }

        private static int countDiffs(final String s1, final String s2) {
            assert s1.length() == s2.length();
            return IntStream.range(0, s1.length()).map(i -> s1.charAt(i) == s2.charAt(i) ? 0 : 1).sum();
        }

        static Mirror parse(String input) {
            String[] rows = input.lines().toArray(String[]::new);
            String[] cols = IntStream.range(0, rows[0].length())
                    .mapToObj(col -> Arrays.stream(rows)
                            .map(row -> "" + row.charAt(col))
                            .collect(Collectors.joining()))
                    .toArray(String[]::new);
            return new Mirror(rows, cols);
        }
    }
}