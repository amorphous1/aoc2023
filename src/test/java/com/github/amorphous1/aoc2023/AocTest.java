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

}