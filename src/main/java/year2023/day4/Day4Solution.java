package year2023.day4;

import common.Utils;

import java.util.Arrays;
import java.util.List;

/**
 * @see <a href="https://adventofcode.com/2023/day/4">Day 4</a>
 */
public class Day4Solution {

//  private static final String inputFile = "2023/provided/day4-example.txt";
    private static final String inputFile = "2023/provided/day4.txt";

    public static void main(String[] args) {

        // general
        List<String> input = Utils.readInputFromResources(inputFile);
        Card [] cards = input.stream()
                .map(Card::new)
                .toArray(Card[]::new);

        // first
        int sum = Arrays.stream(cards)
                    .mapToInt(Card::calculateWinningPoints)
                    .sum();

        System.out.println(sum);

        // second
        int [] cardsMultiplier = new int[input.size()];
        Arrays.fill(cardsMultiplier, 1);
        for (int i = 0; i < input.size(); i ++) {
            int currentMultiplier = cardsMultiplier[i];
            int matchingNumbers   = cards[i].getMatchingNumbers();

            for (int nTimes = 0; nTimes < currentMultiplier; nTimes ++) {
                for (int j = 0; j < matchingNumbers && i + j + 1 <= cardsMultiplier.length; j ++) {
                    cardsMultiplier[i + j + 1] += 1;
                }
            }
        }

        sum = Arrays.stream(cardsMultiplier)
                .sum();

        System.out.println(sum);
    }


    private static class Card {

        private final int matchingNumbers;

        private Card(String input) {
            String[] numbers = input.split(":")[1].split("\\|");

            final List<Integer> winningNumbers = Arrays.stream(numbers[0].split(" "))
                    .filter(s -> ! s.isBlank())
                    .map(Integer::parseInt)
                    .toList();

            final List<Integer> actualNumbers = Arrays.stream(numbers[1].split(" "))
                    .filter(s -> ! s.isBlank() )
                    .map(Integer::parseInt)
                    .toList();

            matchingNumbers = (int) actualNumbers.stream()
                    .filter(winningNumbers::contains)
                    .count();
        }

        private int getMatchingNumbers() { return matchingNumbers; }

        private int calculateWinningPoints() {
            return matchingNumbers == 0 ? 0 :
                   (int) Math.pow(2, matchingNumbers-1);
        }
    }


}