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

//  private static final String inputFile = "day7-example.txt";
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
        participation = input.stream()
                .map(line -> new CamelCardParticipation(line, true))
                .toList();

        System.out.println(getTotalWinnings(participation));
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
        private static final List<Character> cardRanks       = List.of('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2');
        private static final List<Character> jokderCardRanks = List.of('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J');

        private final boolean useJoker;
        private final char [] cards;
        private final int bid;
        private final CardConstellation constellation;

        private CamelCardParticipation(String input) { this(input, false); }
        private CamelCardParticipation(String input, boolean joker) {
            String[] split = input.split(" ");

            this.cards         = split[0].toCharArray();
            this.bid           = Integer.parseInt(split[1]);
            this.useJoker      = joker;
            this.constellation = determineConstellation(cards, useJoker);
        }

        @Override
        public int compareTo(CamelCardParticipation other) {
            List<Character> ranks = useJoker ? jokderCardRanks : cardRanks;
            int winByConstellation = this.constellation.compareTo(other.constellation);
            if (winByConstellation != 0) {
                return - winByConstellation;
            }
            for (int i = 0; i < cards.length; i++) {
                int winByCard = ranks.indexOf(other.cards[i]) - ranks.indexOf(this.cards[i]);
                if (winByCard != 0) {
                    return winByCard;
                }
            }
            return 0;
        }

        private CardConstellation determineConstellation(char[] cards, boolean useJoker) {
            Map<Character, Integer> bucket = new HashMap<>();

            for (char c :cards) {
                bucket.compute(c, (k, v) -> v == null ? 1 : v + 1);
            }

            List<Map.Entry<Character, Integer>> entires = new ArrayList<>(bucket.entrySet()).stream()
                    .sorted((e1, e2) -> {
                        // reversed - most to fewest - when joker are used they are always the smallest bucket
                        // this is to prevent that 'J' are the first bucket which will be boosted.
                        boolean k1Joker = e1.getKey() == 'J';
                        boolean k2Joker = e2.getKey() == 'J';
                        if ( ! useJoker || k1Joker == k2Joker) return e2.getValue() - e1.getValue();
                        return k1Joker ? 1 : -1;
                    }).toList();

            int bucketCount = bucket.size();
            int firstBucketCount = entires.get(0).getValue();
            int jokerCount = useJoker ? (int) new String(cards).chars().filter(chr -> chr == 'J').count() : 0;
            if (0  < jokerCount && bucketCount != 1) {
                bucketCount --; // Joker is not assigned to one bucket anymore
                firstBucketCount += jokerCount; // ruleset allows to just boost the first bucket by jokerCount
            }

            if (bucketCount == 1 && firstBucketCount == 5) {
                return CardConstellation.FIVE_OF_A_KIND;

            } else if (bucketCount == 2 && firstBucketCount == 4) {
                return CardConstellation.FOUR_OF_A_KIND;

            } else if (bucketCount == 2){
                return CardConstellation.FULL_HOUSE;

            }  else if (bucketCount == 3 && firstBucketCount == 3) {
                return CardConstellation.THREE_OF_A_KIND;

            } else if (bucketCount == 3) {
                return CardConstellation.TWO_PAIR;

            } else if (bucketCount == 4) {
                return CardConstellation.ONE_PAIR;

            } else {
                return CardConstellation.HIGH_CARD;
            }
        }
    }

}