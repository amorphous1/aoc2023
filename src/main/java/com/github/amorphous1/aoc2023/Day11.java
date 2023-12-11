package com.github.amorphous1.aoc2023;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;

public class Day11 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String input = Files.readString(Paths.get(Day11.class.getClassLoader().getResource("day11.input").toURI()));
        System.out.println(Universe.parse(input).sumOfDistances(2));
        System.out.println(Universe.parse(input).sumOfDistances(1000000));
    }

    record Universe(Set<Coord> galaxies, Set<Integer> emptyRows, Set<Integer> emptyCols) {
        static Universe parse(String input) {
            String[] rows = input.lines().toArray(String[]::new);
            Set<Coord> galaxies = new HashSet<>();
            Set<Integer> emptyCols = new HashSet<>();
            for (int x = 0; x < rows[0].length(); x++) {
                boolean isEmptyCol = true;
                for (int y = 0; y < rows.length; y++) {
                    if (rows[y].charAt(x) == '#') {
                        galaxies.add(new Coord(x, y));
                        isEmptyCol = false;
                    }
                }
                if (isEmptyCol) emptyCols.add(x);
            }
            Set<Integer> emptyRows = IntStream.range(0, rows.length).boxed()
                    .filter(y -> !rows[y].contains("#")).collect(toSet());
            return new Universe(galaxies, emptyRows, emptyCols);
        }

        long sumOfDistances(int expansionFactor) {
            long result = 0;
            Set<Coord> remainingGalaxies = new HashSet<>(galaxies);
            while (!remainingGalaxies.isEmpty()) {
                Coord galaxy = remainingGalaxies.iterator().next();
                remainingGalaxies.remove(galaxy);
                for (Coord other : remainingGalaxies)
                    result += distance(galaxy, other, expansionFactor);
            }
            return result;
        }

        long distance(Coord a, Coord b, int expansionFactor) {
            long result = a.distance(b);
            for (int row : emptyRows)
                if (a.y < row && b.y > row || a.y > row && b.y < row)
                    result += expansionFactor - 1;
            for (int col : emptyCols)
                if (a.x < col && b.x > col || a.x > col && b.x < col)
                    result += expansionFactor - 1;
            return result;
        }
    }

    record Coord(int x, int y) {
        public int distance(Coord other) {
            return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
        }
    }
}