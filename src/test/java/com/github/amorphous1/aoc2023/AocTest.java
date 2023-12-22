package com.github.amorphous1.aoc2023;

import org.junit.Test;

import static org.junit.Assert.*;

public class AocTest {

    @Test
    public void day01() {
        assertEquals(142, Day01.part1("""
                1abc2
                pqr3stu8vwx
                a1b2c3d4e5f
                treb7uchet"""));

        assertEquals(281, Day01.part2("""
                two1nine
                eightwothree
                abcone2threexyz
                xtwone3four
                4nineeightseven2
                zoneight234
                7pqrstsixteen"""));
    }

    @Test
    public void day02() {
        final String sampleInput = """
                Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
                Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
                Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
                Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
                Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green""";

        assertEquals(8, Day02.part1(sampleInput));
        assertEquals(2286, Day02.part2(sampleInput));
    }

    @Test
    public void day03() {
        final String sampleInput = """
467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...$.*....
.664.598..""";

        assertEquals(4361, Day03.part1(sampleInput));
        assertEquals(467835, Day03.part2(sampleInput));
    }

    @Test
    public void day04() {
        final String sampleInput = """
Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11""";

        assertEquals(13, Day04.part1(sampleInput));
        assertEquals(30, Day04.part2(sampleInput));
    }

    @Test
    public void day05() {
        final String sampleInput = """
seeds: 79 14 55 13

seed-to-soil map:
50 98 2
52 50 48

soil-to-fertilizer map:
0 15 37
37 52 2
39 0 15

fertilizer-to-water map:
49 53 8
0 11 42
42 0 7
57 7 4

water-to-light map:
88 18 7
18 25 70

light-to-temperature map:
45 77 23
81 45 19
68 64 13

temperature-to-humidity map:
0 69 1
1 0 69

humidity-to-location map:
60 56 37
56 93 4""";

        assertEquals(35, Day05.part1(sampleInput));
        assertEquals(46, Day05.part2(sampleInput));
    }

    @Test
    public void day06() {
        final String sampleInput = """
Time:      7  15   30
Distance:  9  40  200""";

        assertEquals(288, Day06.part1(sampleInput));
        assertEquals(71503, Day06.part2(sampleInput));
    }

    @Test
    public void day07() {
        final String sampleInput = """
32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483""";

        assertEquals(6440, Day07.part1(sampleInput));
        assertEquals(5905, Day07.part2(sampleInput));
    }

    @Test
    public void day08() {
        assertEquals(6, Day08.part1("""
LLR

AAA = (BBB, BBB)
BBB = (AAA, ZZZ)
ZZZ = (ZZZ, ZZZ)"""));

        assertEquals(6, Day08.part2("""
LR

11A = (11B, XXX)
11B = (XXX, 11Z)
11Z = (11B, XXX)
22A = (22B, XXX)
22B = (22C, 22C)
22C = (22Z, 22Z)
22Z = (22B, 22B)
XXX = (XXX, XXX)"""));
    }

    @Test
    public void day09() {
        final String sampleInput = """
0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45""";

        assertEquals(114, Day09.part1(sampleInput));
        assertEquals(2, Day09.part2(sampleInput));
    }

    @Test
    public void day10() {
        assertEquals(8, Day10.part1("""
..F7.
.FJ|.
SJ.L7
|F--J
LJ..."""));

        assertEquals(4, Day10.part2("""
...........
.S-------7.
.|F-----7|.
.||.....||.
.||.....||.
.|L-7.F-J|.
.|..|.|..|.
.L--J.L--J.
..........."""));

        assertEquals(10, Day10.part2("""
FF7FSF7F7F7F7F7F---7
L|LJ||||||||||||F--J
FL-7LJLJ||||||LJL-77
F--JF--7||LJLJ7F7FJ-
L---JF-JLJ.||-FJLJJ7
|F|F-JF---7F7-L7L|7|
|FFJF7L7F-JF7|JL---7
7-L-JL7||F7|L7F-7F7|
L.L7LFJ|||||FJL7||LJ
L7JLJL-JLJLJL--JLJ.L"""));
    }

    @Test
    public void day11() {
        final String sampleInput = """
...#......
.......#..
#.........
..........
......#...
.#........
.........#
..........
.......#..
#...#.....""";

        assertEquals(374, Day11.Universe.parse(sampleInput).sumOfDistances(2));
        assertEquals(1030, Day11.Universe.parse(sampleInput).sumOfDistances(10));
        assertEquals(8410, Day11.Universe.parse(sampleInput).sumOfDistances(100));
    }

    @Test
    public void day13() {
        final String sampleInput = """
#.##..##.
..#.##.#.
##......#
##......#
..#.##.#.
..##..##.
#.#.##.#.

#...##..#
#....#..#
..##..###
#####.##.
#####.##.
..##..###
#....#..#""";

        assertEquals(405, Day13.summarise(sampleInput, 0));
        assertEquals(400, Day13.summarise(sampleInput, 1));
    }
}