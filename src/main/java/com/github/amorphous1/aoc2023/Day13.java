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
        System.out.println(part1(input));
    }

    static long part1(String input) {
        return Arrays.stream(input.split("\n\n"))
                .map(Mirror::parse)
                .map(Mirror::summarise)
                .mapToLong(l -> l).sum();
    }

    private record Mirror(String[] rows, String[] cols) {
        public long summarise() {
            long result = 0;
            for (int col = 0; col < cols.length; col++)
                if (isLineOfReflection(col, cols))
                    result+= col;
            for (int row = 0; row < rows.length; row++)
                if (isLineOfReflection(row, rows))
                    result+= 100L * row;
            return result;
        }

        private boolean isLineOfReflection(int candidate, String[] pattern) {
            for (int i = 0; i < Math.min(candidate, pattern.length - candidate); i++)
                if (!pattern[candidate + i].equals(pattern[candidate - i - 1]))
                    return false;
            return true;
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