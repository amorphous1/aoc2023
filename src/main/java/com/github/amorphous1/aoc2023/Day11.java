package com.github.amorphous1.aoc2023;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Day11 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String input = Files.readString(Paths.get(Day11.class.getClassLoader().getResource("day11.input").toURI()));
        System.out.println(part1(input));
    }

    static long part1(final String input) {
        return Universe.parse(input).distances().stream().mapToInt(i -> i).sum();
    }

    record Universe(String[] rows, Set<Coord> galaxies) {
        static Universe parse(String input) {
            String[] rows = input.lines()
                    .flatMap(line -> line.contains("#") ? Stream.of(line) : Stream.of(line, line))
                    .toArray(String[]::new);
            AtomicInteger col = new AtomicInteger(0);
            while (col.get() < rows[0].length()) {
                boolean isEmptyCol = Arrays.stream(rows).map(row -> row.charAt(col.get())).allMatch(ch -> ch == '.');
                if (isEmptyCol) {
                    for (int y = 0; y < rows.length; y++) {
                        String pre = rows[y].substring(0, col.get());
                        String post = col.get() == rows[y].length() - 1 ? "" : rows[y].substring(col.get() + 1);
                        rows[y] = pre + ".." + post;
                    }
                    col.getAndIncrement();
                }
                col.getAndIncrement();
            }
            Set<Coord> galaxies = new HashSet<>();
            for (int y = 0; y < rows.length; y++)
                for (int x = 0; x < rows[y].length(); x++)
                    if (rows[y].charAt(x) == '#')
                        galaxies.add(new Coord(x, y));
            return new Universe(rows, galaxies);
        }

        List<Integer> distances() {
            List<Integer> result = new ArrayList<>();
            Set<Coord> remainingGalaxies = new HashSet<>(galaxies);
            while (!remainingGalaxies.isEmpty()) {
                Coord galaxy = remainingGalaxies.iterator().next();
                remainingGalaxies.remove(galaxy);
                for (Coord other : remainingGalaxies) {
                    result.add(galaxy.distance(other));
                }
            }
            return result;
        }
    }

    record Coord(int x, int y) {
        public int distance(Coord other) {
            return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
        }
    }
}