package year2023.day7;

import common.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @see <a href="https://adventofcode.com/2023/day/7">Day 7</a>
 */
public class Day7Solution {

//  private static final String inputFile = "day7-dummy.txt";
    private static final String inputFile = "day7.txt";

    public static void main(String[] args) {

        // general
        List<String> input      = Utils.readInputFromResources(inputFile);

        // first
        List<CamelCardParticipation> participation = input.stream()
                .map(CamelCardParticipation::new)
                .toList();

        System.out.println(getTotalWinnings(participation));

        // second
        participation.stream().sorted().forEachOrdered(p -> System.out.printf("Cards :%s, type: %s%n", new String(p.cards), p.constellation));

    }

    private static long getTotalWinnings(List<CamelCardParticipation> participations) {
        participations = participations.stream().sorted().collect(Collectors.toList());

        long total = 0;
        for (int i = participations.size() -1; 0 <= i; i--) {
            total += (long) participations.get(i).bid * (i + 1);
        }
        return total;
    }

    enum CardConstellation {
        FIVE_OF_A_KIND,
        FOUR_OF_A_KIND,
        FULL_HOUSE,
        THREE_OF_A_KIND,
        TWO_PAIR,
        ONE_PAIR,
        HIGH_CARD
    }

    private static final class CamelCardParticipation implements Comparable<CamelCardParticipation> {
        private static final List<Character> cardRanks = List.of('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2');

        private final char [] cards;
        private final int bid;
        private final CardConstellation constellation;

        private CamelCardParticipation(String input) {
            String[] split = input.split(" ");

            this.cards = split[0].toCharArray();
            this.bid = Integer.parseInt(split[1]);
            this.constellation = determineConstellation(cards);
        }

        @Override
        public int compareTo(CamelCardParticipation other) {
            int winByConstellation = this.constellation.compareTo(other.constellation);
            if (winByConstellation != 0) {
                return - winByConstellation;
            }
            for (int i = 0; i < cards.length; i++) {
                int winByCard = cardRanks.indexOf(other.cards[i]) - cardRanks.indexOf(this.cards[i]);
                if (winByCard != 0) {
                    return winByCard;
                }
            }
            return 0;
        }

        private CardConstellation determineConstellation(char[] cards) {
            Map<Character, Integer> bucket = new HashMap<>();

            for (char c :cards) {
                bucket.compute(c, (k, v) -> v == null ? 1 : v + 1);
            }

            List<Map.Entry<Character, Integer>> entires = new ArrayList<>(bucket.entrySet()).stream()
                    .sorted((e1, e2) -> e2.getValue() - e1.getValue()) // reversed
                    .toList();

            if (bucket.size() == 1
                    && entires.get(0).getValue() == 5) {
                return CardConstellation.FIVE_OF_A_KIND;

            } else if (bucket.size() == 2
                    && entires.get(0).getValue() == 4) {
                return CardConstellation.FOUR_OF_A_KIND;

            } else if (bucket.size() == 2) {
                return CardConstellation.FULL_HOUSE;

            }  else if (bucket.size() == 3
                    && entires.get(0).getValue() == 3) {
                return CardConstellation.THREE_OF_A_KIND;

            } else if (bucket.size() == 3) {
                return CardConstellation.TWO_PAIR;

            } else if (bucket.size() == 4) {
                return CardConstellation.ONE_PAIR;

            } else {
                return CardConstellation.HIGH_CARD;
            }
        }
    }

}