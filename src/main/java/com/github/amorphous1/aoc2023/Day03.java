package com.github.amorphous1.aoc2023;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day03 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String input = Files.readString(Paths.get(Day03.class.getClassLoader().getResource("day03.input").toURI()));
        System.out.println(part1(input));
    }

    static int part1(String input) {
        EngineSchematic engineSchematic = EngineSchematic.from(input);
        Set<Part> partsAdjacentToSymbol = new HashSet<>();
        for (Pos symbolPosition : engineSchematic.symbols) {
            for (Pos neighbour : symbolPosition.neighbours()) {
                if (engineSchematic.parts.containsKey(neighbour)) {
                    partsAdjacentToSymbol.add(engineSchematic.parts.get(neighbour));
                }
            }
        }
        return partsAdjacentToSymbol.stream().map(part -> part.partNumber).mapToInt(i -> i).sum();
    }

    private record Pos(int x, int y) {
        public List<Pos> neighbours() {
            List<Pos> result = new ArrayList<>();
            for (int nx = x-1; nx <= x+1; nx++) {
                for (int ny = y-1; ny <= y+1; ny++) {
                    if (nx!=x || ny!=y) {
                        result.add(new Pos(nx, ny));
                    }
                }
            }
            return result;
        }
    }
    private record Part(int partId, int partNumber) {}
    private record EngineSchematic(List<Pos> symbols, Map<Pos, Part> parts) {
        public static EngineSchematic from (String input) {
            List<Pos> symbols = new ArrayList<>();
            Map<Pos, Part> parts = new HashMap<>();
            int y = 0, partId = 0;
            for (String line : input.lines().toList()) {
                int x = 0;
                while (x < line.length()) {
                    if (Character.isDigit(line.charAt(x))) {
                        partId++;
                        int partNumber = line.charAt(x) - '0', partNumberLength = 1;
                        while (x+1 < line.length() && Character.isDigit(line.charAt(x+1))) {
                            x++;
                            partNumberLength++;
                            partNumber = 10 * partNumber + line.charAt(x) - '0';
                        }
                        for (int dx = 0; dx < partNumberLength; dx++) {
                            parts.put(new Pos(x - dx, y), new Part(partId, partNumber));
                        }
                    } else if (line.charAt(x) != '.') {
                        symbols.add(new Pos(x, y));
                    }
                    x++;
                }
                y++;
            }
            return new EngineSchematic(symbols, parts);
        }
    }
}