package com.github.amorphous1.aoc2023;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Day14 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String input = Files.readString(Paths.get(Day14.class.getClassLoader().getResource("day14.input").toURI()));
        System.out.println(part1(input));
    }

    static long part1(String input) {
        Platform platform = Platform.parse(input);
        System.out.println(platform);
        System.out.println("------------------------------------------");
        platform.tiltNorth();
        System.out.println(platform);
        return platform.totalLoad();
    }

    record Platform(String[] rows) {
        public void tiltNorth() {
            for (int col = 0; col < rows[0].length(); col++) {
                int row = 0;
                while (row < rows.length) {
                    if (rows[row].charAt(col) == '.') { // empty space, can move rocks north
                        List<Integer> movingRocks = findMovingRocks(col, row);
                        for (int movingRockIdx : movingRocks) {
                            rows[movingRockIdx] = rows[movingRockIdx].substring(0, col) + '.' + rows[movingRockIdx].substring(col + 1);
                            rows[row] = rows[row].substring(0, col) + 'O' + rows[row].substring(col + 1);
                            row++;
                        }
                        row = movingRocks.isEmpty() ? row + 1 : movingRocks.getLast() + 1;
                    } else {
                        row++;
                    }
                }
            }
        }

        public long totalLoad() {
            return IntStream.range(0, rows.length)
                    .map(row -> rows[row].replaceAll("[.#]", "").length() * (rows.length - row))
                    .sum();
        }

        private List<Integer> findMovingRocks(int col, int startRow) {
            List<Integer> result = new ArrayList<>();
            for (int row = startRow; row < rows.length && rows[row].charAt(col) != '#'; row++)
                if (rows[row].charAt(col) == 'O')
                    result.add(row);
            return result;
        }

        static Platform parse(String input) {
            return new Platform(input.lines().toArray(String[]::new));
        }

        @Override
        public String toString() {
            return String.join("\n", rows);
        }
    }
}