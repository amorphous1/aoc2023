package com.github.amorphous1.aoc2023;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Day14 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String input = Files.readString(Paths.get(Day14.class.getClassLoader().getResource("day14.input").toURI()));
        System.out.println(part1(input));
    }

    static long part1(String input) {
        Platform platform = Platform.parse(input);
        platform.tiltNorth();
        return platform.totalLoad();
    }

    record Platform(String[] rows) {
        private static final Character EMPTY = '.';
        private static final Character STATIC_ROCK = '#';
        private static final Character MOVING_ROCK = 'O';

        public void tiltNorth() {
            for (int col = 0; col < rows[0].length(); col++)
                for (int row = 0; row < rows.length; row++)
                    if (rows[row].charAt(col) == EMPTY) {
                        List<Integer> movingRocks = findMovingRocks(col, row);
                        for (int movingRockIdx : movingRocks) {
                            rows[movingRockIdx] = rows[movingRockIdx].substring(0, col) + EMPTY + rows[movingRockIdx].substring(col + 1);
                            rows[row] = rows[row].substring(0, col) + MOVING_ROCK + rows[row].substring(col + 1);
                            row++;
                        }
                        row = movingRocks.isEmpty() ? row : movingRocks.getLast();
                    }
        }

        public long totalLoad() {
            return IntStream.range(0, rows.length)
                    .map(row -> rows[row].replaceAll("[^" + MOVING_ROCK + "]", "").length() * (rows.length - row))
                    .sum();
        }

        private List<Integer> findMovingRocks(int col, int startRow) {
            List<Integer> result = new ArrayList<>();
            for (int row = startRow; row < rows.length && rows[row].charAt(col) != STATIC_ROCK; row++)
                if (rows[row].charAt(col) == MOVING_ROCK)
                    result.add(row);
            return result;
        }

        static Platform parse(String input) {
            return new Platform(input.lines().toArray(String[]::new));
        }
    }
}