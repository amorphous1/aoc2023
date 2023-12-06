package com.github.amorphous1.aoc2023;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day06 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String input = Files.readString(Paths.get(Day06.class.getClassLoader().getResource("day06.input").toURI()));
        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    static long part1(String input) {
        return Race.racesFrom(input).stream()
                .map(Race::possibleRecords).mapToLong(l -> l)
                .reduce(1, (a, b) -> a * b);
    }

    static long part2(String input) {
        return part1(input.replaceAll(" ", ""));
    }

    private record Race(long time, long recordDistance) {
        static List<Race> racesFrom(String input) {
            List<String> timesAndDistances = input.lines().map(line -> line.split(": *")[1]).toList();
            List<Long> times = Arrays.stream(timesAndDistances.getFirst().split(" +")).map(Long::parseLong).toList();
            List<Long> distances = Arrays.stream(timesAndDistances.getLast().split(" +")).map(Long::parseLong).toList();
            List<Race> result = new ArrayList<>();
            for (int i = 0; i < Math.min(times.size(), distances.size()); i++) {
                result.add(new Race(times.get(i), distances.get(i)));
            }
            return result;
        }

        long possibleRecords() {
            long result = 0;
            for (long holdDownTime = 1; holdDownTime < this.time; holdDownTime++) {
                if ((this.time - holdDownTime) * holdDownTime > this.recordDistance) {
                    result++;
                }
            }
            return result;
        }
    }
}