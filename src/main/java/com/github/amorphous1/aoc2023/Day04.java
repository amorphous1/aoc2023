package com.github.amorphous1.aoc2023;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static java.util.stream.Collectors.toSet;

public class Day04 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String input = Files.readString(Paths.get(Day04.class.getClassLoader().getResource("day04.input").toURI()));
        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    static int part1(String input) {
        return input.lines().map(Scratchcard::parse).map(Scratchcard::points).mapToInt(i -> i).sum();
    }

    static int part2(String input) {
        List<Scratchcard> scratchcards = input.lines().map(Scratchcard::parse).toList();
        Map<Integer, Integer> scratchCardCopiesById = new HashMap<>();
        for (Scratchcard card : scratchcards) {
            int totalNumberOfThisCard = 1 + scratchCardCopiesById.getOrDefault(card.id, 0);
            for (int nextId = card.id + 1; nextId <= card.id + card.winningOwnNumbers() && nextId <= scratchcards.size(); nextId++) {
                scratchCardCopiesById.put(nextId, scratchCardCopiesById.getOrDefault(nextId, 0) + totalNumberOfThisCard);
            }
        }
        return scratchcards.size() + scratchCardCopiesById.values().stream().mapToInt(i -> i).sum();
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

        public int winningOwnNumbers() {
            Set<Integer> winningOwnNumbers = new HashSet<>(ownNumbers);
            winningOwnNumbers.retainAll(winningNumbers);
            return winningOwnNumbers.size();
        }

        public int points() {
            return winningOwnNumbers() == 0 ? 0 : 1 << (winningOwnNumbers() - 1);
        }
    }
}