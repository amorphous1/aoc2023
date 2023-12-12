package com.github.amorphous1.aoc2023;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Day07 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String input = Files.readString(Paths.get(Day07.class.getClassLoader().getResource("day07.input").toURI()));
        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    static int part1(final String input) {
        return calculateWinnings(input, false);
    }
    static int part2(final String input) {
        return calculateWinnings(input, true);
    }

    private static int calculateWinnings(String input, boolean playWithJokers) {
        List<Hand> handsByStrength = input.lines().map(line -> Hand.parse(line, playWithJokers))
                .sorted(Comparator.comparing(hand -> hand.strengthSort)).toList();
        int totalWinnings = 0;
        for (int i = 0; i < handsByStrength.size(); i++)
            totalWinnings += (i + 1) * handsByStrength.get(i).bid;
        return totalWinnings;
    }

    private static final class Hand {
        final String cards;
        final int bid;
        final HandType handType;
        final String strengthSort;

        private Hand(String cards, int bid, boolean playWithJokers) {
            this.cards = cards;
            this.bid = bid;
            this.handType = HandType.of(cards, playWithJokers);
            this.strengthSort = calculateStrength(handType, cards, playWithJokers);
        }

        static Hand parse(String input, boolean playWithJokers) {
            String[] cardsAndBid = input.split(" ");
            return new Hand(cardsAndBid[0], Integer.parseInt(cardsAndBid[1]), playWithJokers);
        }

        private static String calculateStrength(HandType handType, String cards, boolean playWithJokers) {
            String strengthSortCards = cards.replaceAll("A", "E")
                    .replaceAll("K", "D")
                    .replaceAll("Q", "C")
                    .replaceAll("J", playWithJokers ? "1" : "B")
                    .replaceAll( "T", "A");
            return handType.strength + strengthSortCards;
        }
    }

    private record HandType(String description, int strength) {
        private static final Map<List<Integer>, HandType> CARD_COUNTS_TO_HAND_TYPE = Map.of(
                List.of(5), new HandType("Five of a kind", 6),
                List.of(1, 4), new HandType("Four of a kind", 5),
                List.of(2, 3), new HandType("Full house", 4),
                List.of(1, 1, 3), new HandType("Three of a kind", 3),
                List.of(1, 2, 2), new HandType("Two pair", 2),
                List.of(1, 1, 1, 2), new HandType("One pair", 1),
                List.of(1, 1, 1, 1, 1), new HandType("High Card", 0)
        );

        static HandType of(String cards, boolean playWithJokers) {
            Map<Integer, Integer> cardToCount = cards.chars()
                    .mapToObj(ch -> new SimpleEntry<>(ch, 1))
                    .collect(Collectors.<SimpleEntry<Integer, Integer>, Integer, Integer>toMap(SimpleEntry::getKey, SimpleEntry::getValue, Integer::sum));
            int jokerCount = playWithJokers && cardToCount.containsKey((int)'J') ? cardToCount.remove((int) 'J') : 0;
            List<Integer> cardCounts = cardToCount.values().stream().sorted().collect(toList());
            if (cardCounts.isEmpty())  // whole hand of jokers...
                cardCounts.add(0);
            cardCounts.set(cardCounts.size() - 1, cardCounts.getLast() + jokerCount);
            return CARD_COUNTS_TO_HAND_TYPE.get(cardCounts);
        }
    }
}