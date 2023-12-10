package com.github.amorphous1.aoc2023;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Day05 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String input = Files.readString(Paths.get(Day05.class.getClassLoader().getResource("day05.input").toURI()));
        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    static long part1(final String input) {
        return Almanac.parse(input).lowestSeedLocation();
    }
    static long part2(final String input) {
        return Almanac.parse(input).lowestSeedLocation2();
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
                            }).toList()
                    )).toList();
            return new Almanac(seeds, maps);
        }

        long lowestSeedLocation() {
            RangeMap seedToLocation = maps.getFirst();
            for (int i = 1; i < maps.size(); i++) {
                seedToLocation = seedToLocation.merge(maps.get(i));
            }
            return seeds.stream().map(seedToLocation::get).min(Comparator.naturalOrder()).orElseThrow();
        }

        long lowestSeedLocation2() {
            List<RangeMapEntry> seedRanges = new ArrayList<>();
            for (int i = 0; i < seeds.size(); i += 2) {
                long min = seeds.get(i);
                long max = min + seeds.get(i+1);
                seedRanges.add(new RangeMapEntry(new Range(min, max), 0));
            }
            RangeMap merged = new RangeMap(seedRanges);
            for (int i = 0; i < maps.size(); i++) {
                merged = merged.merge(maps.get(i));
            }
            final RangeMap seedToLocation = merged;
            return seedToLocation.entries.stream()
                    .filter(e -> seedRanges.stream().anyMatch(seedRange -> seedRange.r.contains(e.r.min)))
                    .map(e -> seedToLocation.get(e.r.min)).min(Comparator.naturalOrder()).orElseThrow();
        }
    }

    private record RangeMap(List<RangeMapEntry> entries) {
        private RangeMap(List<RangeMapEntry> entries) {
            List<RangeMapEntry> sortedEntries = entries.stream().sorted(Comparator.comparing(e -> e.r.min)).collect(toList());
            List<RangeMapEntry> dummyEntries = new ArrayList<>();
            if (sortedEntries.getFirst().r.min > 0) {
                dummyEntries.add(new RangeMapEntry(new Range(0, sortedEntries.getFirst().r.min), 0));
            }
            for (int i = 1; i < sortedEntries.size(); i++) {
                if (sortedEntries.get(i - 1).r.max < sortedEntries.get(i).r.min) {
                    dummyEntries.add(new RangeMapEntry(new Range(sortedEntries.get(i - 1).r.max, sortedEntries.get(i).r.min), 0));
                }
            }
            if (sortedEntries.getLast().r.max < Long.MAX_VALUE) {
                dummyEntries.add(new RangeMapEntry(new Range(sortedEntries.getLast().r.max, Long.MAX_VALUE), 0));
            }
            sortedEntries.addAll(dummyEntries);
            this.entries = sortedEntries.stream().sorted(Comparator.comparing(e -> e.r.min)).toList();
        }
        long get(long key) {
            for (RangeMapEntry entry : entries)
                if (entry.r.contains(key))
                    return key + entry.d;
            return key;
        }
        RangeMap merge(RangeMap other) {
            List<RangeMapEntry> merged = new ArrayList<>();
            for (RangeMapEntry entry : this.entries) {
                merged.addAll(entry.merge(other.entries));
            }
            return new RangeMap(merged);
        }
    }

    private record RangeMapEntry(Range r, long d) {
        public List<RangeMapEntry> merge(List<RangeMapEntry> entriesInDestSpace) {
            List<RangeMapEntry> result = new ArrayList<>();
            Range rangeInDestSpace = new Range(r.min + d, r.max + d);
            for (RangeMapEntry entry : entriesInDestSpace) {
                Range mergeRange = rangeInDestSpace.intersection(entry.r);
                if (!mergeRange.isEmpty()) {
                    result.add(new RangeMapEntry(new Range(mergeRange.min - d, mergeRange.max - d), d + entry.d));
                }
            }
            return result;
        }
    }

    private record Range(long min, long max) {
        boolean contains(long x) {
            return min <= x && x < max;
        }
        boolean isEmpty() {
            return min >= max;
        }
        public Range intersection(Range other) {
            return new Range(Math.max(this.min, other.min), Math.min(this.max, other.max));
        }
    }
}