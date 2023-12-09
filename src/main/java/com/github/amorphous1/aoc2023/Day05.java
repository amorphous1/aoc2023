package com.github.amorphous1.aoc2023;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Day05 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String input = Files.readString(Paths.get(Day05.class.getClassLoader().getResource("day05.input").toURI()));
        System.out.println(part1(input));
    }

    static long part1(final String input) {
        return Almanac.parse(input).lowestSeedLocation();
    }

    private record Almanac(List<Long> seeds, List<RangeMap> maps) {
        static Almanac parse(String input) {
            List<Long> seeds = Arrays.stream(input.lines().iterator().next().substring(7).split(" "))
                    .map(Long::parseLong).toList();
            List<RangeMap> maps = Arrays.stream(input.split("seed-to-soil map:")[1].split("\n[^ ]+ map:"))
                    .map(mapInput -> new RangeMap(
                            mapInput.lines().filter(l -> !l.isBlank()).map(mapEntryInput -> {
                                String[] destAndSrcAndLength = mapEntryInput.split(" ");
                                long dest = Long.parseLong(destAndSrcAndLength[0]);
                                long src = Long.parseLong(destAndSrcAndLength[1]);
                                long length = Long.parseLong(destAndSrcAndLength[2]);
                                return new RangeMapEntry(new Range(src, src + length), dest - src);
                            }).collect(toList())
                    ))
                    .collect(toList());
            return new Almanac(seeds, maps);
        }

        long lowestSeedLocation() {
            return seeds.stream().map(this::toLocation).min(Comparator.naturalOrder()).orElseThrow();
        }

        long toLocation(long seed) {
            long result = seed;
            for (RangeMap map : maps)
                result = map.get(result);
            return result;
        }
    }

    private record RangeMap(List<RangeMapEntry> entries) {
        long get(long key) {
            for (RangeMapEntry entry : entries)
                if (entry.range.contains(key))
                    return key + entry.delta;
            return key;
        }
    }

    private record RangeMapEntry(Range range, long delta) {}

    private record Range(long minInclusive, long maxExclusive) {
        boolean contains(long x) {
            return minInclusive <= x && x < maxExclusive;
        }
    }
}