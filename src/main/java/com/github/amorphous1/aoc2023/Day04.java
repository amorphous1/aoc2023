package com.github.amorphous1.aoc2023;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

public class Day04 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String input = Files.readString(Paths.get(Day04.class.getClassLoader().getResource("day04.input").toURI()));
        System.out.println(part1(input));
    }

    static int part1(String input) {
        return input.lines().map(Scratchcard::parse).map(Scratchcard::points).mapToInt(i -> i).sum();
    }

    private record Scratchcard(int id, Set<Integer> winningNumbers, Set<Integer> ownNumbers) {
        public static Scratchcard parse(String line) {
            String[] idAndNumbers = line.split(": +");
            int id = Integer.parseInt(idAndNumbers[0].split(" +")[1]);
            String[] winningAndOwnNumbers = idAndNumbers[1].split(" \\| +");
            Set<Integer> winningNumbers = Arrays.stream(winningAndOwnNumbers[0].split(" +")).map(Integer::parseInt).collect(toSet());
            Set<Integer> ownNumbers = Arrays.stream(winningAndOwnNumbers[1].split(" +")).map(Integer::parseInt).collect(toSet());
            return new Scratchcard(id, winningNumbers, ownNumbers);
        }

        public int points() {
            Set<Integer> winningOwnNumbers = new HashSet<>(ownNumbers);
            winningOwnNumbers.retainAll(winningNumbers);
            return winningOwnNumbers.isEmpty() ? 0 : 1 << (winningOwnNumbers.size() - 1);
        }
    }
}