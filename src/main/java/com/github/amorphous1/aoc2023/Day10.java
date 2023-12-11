package com.github.amorphous1.aoc2023;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static com.github.amorphous1.aoc2023.Day10.Coord.*;

public class Day10 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String input = Files.readString(Paths.get(Day10.class.getClassLoader().getResource("day10.input").toURI()));
        System.out.println(part1(input));
    }

    static long part1(final String input) {
        return Tiles.parse(input).longestLoop() / 2;
    }


    private record Tiles(String[] rows, Coord start) {
        static final Map<Character, List<Coord>> TILE_TO_CONNECTIONS = Map.of(
                '|', List.of(NORTH, SOUTH),
                '-', List.of(EAST, WEST),
                'L', List.of(NORTH, EAST),
                'J', List.of(NORTH, WEST),
                '7', List.of(SOUTH, WEST),
                'F', List.of(SOUTH, EAST),
                '.', List.of()
        );
        static Tiles parse(String input) {
            String[] rows = input.lines().toArray(String[]::new);
            return new Tiles(rows, findStart(rows));
        }

        private static Coord findStart(String[] rows) {
            for (int y = 0; y < rows.length; y++) {
                int x = rows[y].indexOf('S');
                if (x >= 0)
                    return new Coord(x, y);
            }
            throw new IllegalArgumentException("no start symbol found in " + Arrays.toString(rows));
        }

        List<Coord> neighbours(Coord coord) {
            return TILE_TO_CONNECTIONS.get(tileAt(coord)).stream()
                    .map(coord::move)
                    .toList();
        }

        char tileAt(Coord c) {
            return c.x >=0 && c.x < rows[0].length() && c.y >=0 && c.y < rows.length
                    ? rows[c.y].charAt(c.x)
                    : '.';
        }

        int longestLoop() {
            return Stream.of(NORTH, EAST, SOUTH, WEST)
                    .filter(direction -> neighbours(start.move(direction)).contains(start))
                    .map(this::loopLength)
                    .max(Comparator.naturalOrder()).orElseThrow();
        }

        private int loopLength(Coord direction) {
            Coord location = start.move(direction);
            int step = 1;
            while (!location.equals(start)) {
                List<Coord> connections = new ArrayList<>(TILE_TO_CONNECTIONS.get(tileAt(location)));
                connections.remove(OPPOSITES.get(direction));
                assert connections.size() == 1;
                direction = connections.getFirst();
                location = location.move(direction);
                step++;
            }
            return step;
        }
    }

    record Coord(int x, int y) {
        static final Coord NORTH = new Coord(0, -1);
        static final Coord EAST = new Coord(1, 0);
        static final Coord SOUTH = new Coord(0, 1);
        static final Coord WEST = new Coord(-1, 0);
        static final Map<Coord, Coord> OPPOSITES = Map.of(NORTH, SOUTH, EAST, WEST, SOUTH, NORTH, WEST, EAST);

        public Coord move(Coord direction) {
            return new Coord(this.x + direction.x, this.y + direction.y);
        }
    }
}