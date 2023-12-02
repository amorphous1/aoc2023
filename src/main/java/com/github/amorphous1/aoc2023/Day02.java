package com.github.amorphous1.aoc2023;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day02 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String input = Files.readString(Paths.get(Day02.class.getClassLoader().getResource("day02.input").toURI()));
        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    static int part1(String input) {
        return input.lines()
                .map(Day02::toCubeDraws)
                .filter(draws -> Day02.isPossible(draws, Map.of("red", 12, "green", 13, "blue", 14)))
                .map(draws -> draws.gameId)
                .mapToInt(i -> i).sum();
    }

    static int part2(String input) {
        return input.lines()
                .map(Day02::toCubeDraws)
                .map(Day02::toCubeSetPower)
                .mapToInt(i -> i).sum();
    }

    private static int toCubeSetPower(CubeDraws cubeDraws) {
        Map<String, Integer> minCubes = new HashMap<>();
        cubeDraws.draws().forEach(cubeDraw ->
                cubeDraw.forEach((color, amount) -> {
                    if (amount > minCubes.getOrDefault(color, 0)) {
                        minCubes.put(color, amount);
                    }
                }));
        return minCubes.values().stream().reduce(1, (x, y) -> x * y);
    }

    private static boolean isPossible(CubeDraws cubeDraws, Map<String, Integer> maxCubes) {
        for (Map<String, Integer> draw : cubeDraws.draws()) {
            for (Map.Entry<String, Integer> cubeDraw : draw.entrySet()) {
                if (cubeDraw.getValue() > maxCubes.get(cubeDraw.getKey())) {
                    return false;
                }
            }
        }
        return true;
    }

    private static CubeDraws toCubeDraws(String line) {
        String[] gameIdAndDraws = line.split(": ");
        int gameId = Integer.parseInt(gameIdAndDraws[0].split(" ")[1]);
        List<Map<String, Integer>> draws = new ArrayList<>();
        for (String draw : gameIdAndDraws[1].split("; ")) {
            Map<String, Integer> cubesDrawn = new HashMap<>();
            for (String singleColorDraw : draw.split(", ")) {
                String[] amountAndColor = singleColorDraw.split(" ");
                cubesDrawn.put(amountAndColor[1], Integer.parseInt(amountAndColor[0]));
            }
            draws.add(cubesDrawn);
        }
        return new CubeDraws(gameId, draws);
    }

    private record CubeDraws(int gameId, List<Map<String, Integer>> draws) {}
}