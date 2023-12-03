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
        System.out.println(part2(input));
    }

    static int part1(String input) {
        EngineSchematic engineSchematic = EngineSchematic.from(input);
        Set<Part> partsAdjacentToSymbol = new HashSet<>();
        for (Pos symbolPosition : engineSchematic.symbols.values().stream().flatMap(List::stream).toList()) {
            for (Pos neighbour : symbolPosition.neighbours()) {
                if (engineSchematic.parts.containsKey(neighbour)) {
                    partsAdjacentToSymbol.add(engineSchematic.parts.get(neighbour));
                }
            }
        }
        return partsAdjacentToSymbol.stream().map(part -> part.partNumber).mapToInt(i -> i).sum();
    }

    static int part2(String input) {
        EngineSchematic engineSchematic = EngineSchematic.from(input);
        int gearRatios = 0;
        for (Pos gearPosition : engineSchematic.symbols.get('*')) {
            Set<Part> partsAdjacentToGear = new HashSet<>();
            for (Pos neighbour : gearPosition.neighbours()) {
                if (engineSchematic.parts.containsKey(neighbour)) {
                    partsAdjacentToGear.add(engineSchematic.parts.get(neighbour));
                }
            }
            if (partsAdjacentToGear.size() == 2) {
                gearRatios += partsAdjacentToGear.stream().map(p -> p.partNumber).reduce(1, (a,b) -> a*b);
            }
        }
        return gearRatios;
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
    private record EngineSchematic(Map<Character, List<Pos>> symbols, Map<Pos, Part> parts) {
        public static EngineSchematic from (String input) {
            Map<Character, List<Pos>> symbols = new HashMap<>();
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
                        List<Pos> positions = symbols.get(line.charAt(x));
                        if (positions == null)
                            symbols.put(line.charAt(x), new ArrayList<>(List.of((new Pos(x, y)))));
                        else
                            positions.add(new Pos(x, y));
                    }
                    x++;
                }
                y++;
            }
            return new EngineSchematic(symbols, parts);
        }
    }
}