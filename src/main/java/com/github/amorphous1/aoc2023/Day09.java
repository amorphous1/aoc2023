package com.github.amorphous1.aoc2023;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day09 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String input = Files.readString(Paths.get(Day09.class.getClassLoader().getResource("day09.input").toURI()));
        System.out.println(part1(input));
        System.out.println(part2(input));
    }
    static long part1(final String input) {
        return input.lines().mapToLong(line -> {
            List<List<Integer>> deltas = calculateDeltas(line);
            long extrapolated = 0;
            for (int i = deltas.size() - 2; i >=0; i--)
                extrapolated = deltas.get(i).getLast() + extrapolated;
            return extrapolated;
        }).sum();
    }

    static long part2(final String input) {
        return input.lines().mapToLong(line -> {
            List<List<Integer>> deltas = calculateDeltas(line);
            long extrapolated = 0;
            for (int i = deltas.size() - 2; i >=0; i--)
                extrapolated = deltas.get(i).getFirst() - extrapolated;
            return extrapolated;
        }).sum();
    }

    private static List<List<Integer>> calculateDeltas(String line) {
        List<Integer> numbers = Arrays.stream(line.split(" ")).map(Integer::parseInt).toList();
        List<List<Integer>> deltas = new ArrayList<>(List.of(numbers));
        while (!deltas.getLast().stream().allMatch(d -> d == 0))
            deltas.add(deltasFrom(deltas.getLast()));
        return deltas;
    }

    private static List<Integer> deltasFrom(List<Integer> numbers) {
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i < numbers.size(); i++)
            result.add(numbers.get(i) - numbers.get(i-1));
        return result;
    }
}