package com.github.amorphous1.aoc2023;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Day12 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String input = Files.readString(Paths.get(Day12.class.getClassLoader().getResource("day12.input").toURI()));
        System.out.println(part1(input));
    }

    static long part1(final String input) {
        return input.lines()
                .map(ConditionRecord::parse)
                .mapToLong(ConditionRecord::possibleArrangements)
                .sum();
    }

    record ConditionRecord(String conditions, List<Integer> damagedCounts) {
        static ConditionRecord parse(String input) {
            String[] conditionsAndCounts = input.split(" ");
            return new ConditionRecord(
                    conditionsAndCounts[0],
                    Arrays.stream(conditionsAndCounts[1].split(",")).map(Integer::parseInt).toList());
        }

        private long possibleArrangements() {
            return possibleArrangements(0, conditions, new ArrayList<>(damagedCounts), Optional.empty());
        }

        private long possibleArrangements(int indent, String conds, List<Integer> damagedCounts, Optional<Character> needsNext) {
            if (conds.isEmpty()) {
                int result = damagedCounts.isEmpty() ? 1 : 0;
//                log(indent, conds, damagedCounts, needOperational, result);
                return result;
            }
            char next = conds.charAt(0);
            if (next == '.' && needsNext.orElse(' ') == '#' ||
                next == '#' && needsNext.orElse(' ') == '.' ||
                next == '#' && damagedCounts.isEmpty()) {
//                log(indent, conds, damagedCounts, needOperational, 0);
                return 0;
            }
            if (next == '.')
                return possibleArrangements(indent + 2, conds.substring(1), damagedCounts, Optional.empty());
            if (next == '#') {
                Optional<Character> nextNeedsNext = damagedCounts.getFirst() > 1 ? Optional.of('#') : Optional.of('.');
                List<Integer> nextDamagedCounts = new ArrayList<>(damagedCounts);
                nextDamagedCounts.set(0, nextDamagedCounts.getFirst() - 1);
                if (nextDamagedCounts.getFirst() == 0) nextDamagedCounts.removeFirst();
                return possibleArrangements(indent + 2, conds.substring(1), nextDamagedCounts, nextNeedsNext);
            }
            long a = possibleArrangements(indent + 2, '#' + conds.substring(1), damagedCounts, needsNext);
            long b = possibleArrangements(indent + 2, '.' + conds.substring(1), damagedCounts, needsNext);
//            log(indent, "adding " + a + " and " + b + ": " + conds, damagedCounts, needsNext, a + b);
            return a + b;
        }
    }

    private static void log(int indent, String conds, List<Integer> damagedCounts, Optional<Character> needsNext, long result) {
        System.out.println(" ".repeat(indent) + conds + ", " + damagedCounts + ", " + needsNext + " -> " + result);
    }
}