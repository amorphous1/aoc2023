package com.github.amorphous1.aoc2023;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.stream.Collectors;

public class Day08 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String input = Files.readString(Paths.get(Day08.class.getClassLoader().getResource("day08.input").toURI()));
        System.out.println(part1(input));
    }

    static long part1(String input) {
        final Network network = Network.parse(input);
        int step = 0;
        String currentNode = "AAA";
        while (!currentNode.equals("ZZZ")) {
            char instruction = network.instructions.charAt(step % network.instructions.length());
            Successors succ = network.nodeToSuccessors.get(currentNode);
            currentNode = instruction == 'L' ? succ.left : succ.right;
            step++;
        }
        return step;
    }

    private record Network(String instructions, Map<String, Successors> nodeToSuccessors) {
        static Network parse(String input) {
            String instructions = input.lines().findFirst().orElseThrow();
            Map<String, Successors> nodeToSuccessors = input.lines().skip(2).map(line -> {
                String[] nodeAndSuccessors = line.split(" = ");
                return new SimpleImmutableEntry<>(nodeAndSuccessors[0],
                        Successors.parse(nodeAndSuccessors[1]));
            }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            return new Network(instructions, nodeToSuccessors);
        }
    }
    private record Successors(String left, String right) {
        public static Successors parse(String input) {
            String[] leftAndRight = input.substring(1, input.length()-1).split(", ");
            return new Successors(leftAndRight[0], leftAndRight[1]);
        }
    }
}